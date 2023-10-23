package fr.lip6.ltl.stats;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.application.runner.spot.SpotRunner.GivenStrategy;

public class KnowledgeStatsCalculator {

	AtomicInteger nbFolder = new AtomicInteger(0);
	AtomicInteger nbTreated = new AtomicInteger(0);

	public void calculateStats(String inputFolder, String outputFolder) {
		Path inputPath = Paths.get(inputFolder);
		Path outputDir = Paths.get(outputFolder);

		// Ensure output folder exists
		try {
			Files.createDirectories(outputDir);
		} catch (IOException e) {
			System.err.println("Could not create output directory: " + e.getMessage());
			return;
		}

		File outputFile = outputDir.resolve("formulaStats.csv").toFile();
		try (PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)))) {
		    synchronized (out) {
		        out.println(AutomatonStats.buildHeaderLine());
		    }

		    // Traverse the input directory
		    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(inputPath)) {
		        List<Path> paths = new ArrayList<>();
		        for (Path path : directoryStream) {
		            if (Files.isDirectory(path)) {
		                paths.add(path);
		            }
		        }

		        paths.parallelStream().forEach(path -> {
		            try {
		                findAndProcessFiles(path, out, "LTLFireability");
		                findAndProcessFiles(path, out, "LTLCardinality");
		            } catch (Exception e) {
		                System.err.println("Error processing: " + path.toString() + " - " + e.getMessage());
		            }
		        });
		    } catch (IOException e) {
		        System.err.println("Error reading input directory: " + e.getMessage());
		    }
		    synchronized (out) {
		    	out.flush();
		    }
		} catch (FileNotFoundException e) {
		    System.err.println("Error opening output file: " + e.getMessage());
		}
		System.out.println("In total out of "+nbFolder+" found knowledge information in "+ nbTreated + " cases.");

	}

	private void findAndProcessFiles(Path benchmarkDir, PrintStream out, String formulaType) {
		File dir = benchmarkDir.toFile();
		File[] files = dir.listFiles();

		File formulasFile = null;
		File knowledgeFile = null;
		File falseKnowledgeFile = null;


		if (files != null) {
			for (File file : files) {
				if (file.getName().endsWith(formulaType + ".ltl")) {
					if (file.getName().startsWith("raw")) {
						formulasFile = file;
						nbFolder.incrementAndGet();
					} else if (file.getName().startsWith("knowledge")) {
						knowledgeFile = file;
					} else if (file.getName().startsWith("falseKnowledge")) {
						falseKnowledgeFile = file;
					}
				}
			}

			if (formulasFile != null && knowledgeFile != null && falseKnowledgeFile != null) {
				// System.out.println("Working on "+dir+" for " + formulaType);
				calculateBenchmarkStats(formulasFile, knowledgeFile, falseKnowledgeFile, out);
				nbTreated.incrementAndGet();
			    synchronized (out) {
			    	out.flush();
			    }
			} else {
				System.err.println("Incomplete set of files in folder: " + benchmarkDir.toString());
			}
		}

	}

	private void calculateBenchmarkStats(File formulasFile, File knowledgeFile, File falseKnowledgeFile, PrintStream out) {

		String model = formulasFile.getParentFile().getName();
		String exam = formulasFile.getName().replace("raw", "").replace(".ltl", "");
		String type = "raw";

		//  generateStats(formulasFile, model, exam, type, out);
		generateMinMax(formulasFile,knowledgeFile,model,exam,out,false);
		generateMinMax(formulasFile,knowledgeFile,model,exam,out,true);

	}

	private void generateMinMax(File formulasFile, File knowledgeFile, String model, String exam, PrintStream out, boolean negateFormula)  {
		try {
//			if (! model.equals("CloudOpsManagement-PT-00080by00040"))
//				return;

			List<String> rawFormulas = Files.readAllLines(formulasFile.toPath());
			List<String> knowledgeFormulas = Files.readAllLines(knowledgeFile.toPath());

			List<Set<String>> rawSupports = new ArrayList<>();
			List<Set<String>> knowledgeSupports = new ArrayList<>();

			// Compute support sets once and store
			for (String formula : rawFormulas) {
				rawSupports.add(support(formula));
			}
			for (String formula : knowledgeFormulas) {
				knowledgeSupports.add(support(formula));
			}

			SpotRunner sr = new SpotRunner(10);

			if (negateFormula) {
				model = "n"+model;
			}

			for (int lineNumber = 0; lineNumber < rawFormulas.size(); ++lineNumber) {
				String rawFormula = rawFormulas.get(lineNumber);
				if (negateFormula) {
					rawFormula = "!(" + rawFormula + ")";
				}
				Set<String> rawSupport = rawSupports.get(lineNumber);

				String formulaName = model + "-" + exam + "-" + String.format("%02d", lineNumber);

				// Generate raw stats
				long time = System.currentTimeMillis();
				TGBA rawTGBA = null;
				try {
					rawTGBA = sr.buildTGBA(rawFormula);
				} catch (IOException|InterruptedException|TimeoutException e) {
					e.printStackTrace();
				}
				AutomatonStats rawStats = AutomatonStatsCalculator.computeStats(rawTGBA, formulaName, "raw",time);
				synchronized (out) { out.println(rawStats.toString()); out.flush();}


				
				// the assertions we keep
				ArrayList<String> selectedKnowledge = new ArrayList<>();
				// the support of these assertions
				Set<String> extendedSupport = new HashSet<>(rawSupport);
				
				// First pass to retain facts whose alphabet intersects formula
				{	
					
					boolean[] toadd = new boolean[knowledgeFormulas.size()];				
					// first pass, keep in toadd if it intersects raw
					for (int i = 0; i < knowledgeFormulas.size(); ++i) {
						Set<String> kSupport = new HashSet<>(knowledgeSupports.get(i));
						kSupport.retainAll(rawSupport);
						if (!kSupport.isEmpty()) {
							// cumulate in extended support the AP we see
							extendedSupport.addAll(knowledgeSupports.get(i));
							toadd[i]=true;
						}
					}
					
					// update so we can existentially quantify these AP
					for (int i =0; i < knowledgeFormulas.size() ; i++) {
						if (toadd[i])
							selectedKnowledge.add("(" + knowledgeFormulas.get(i) + ")");
					}
				}

				
				// second pass, keep if it intersects formulas we kept in first pass : touches extendedSupport
				// currently disabled
				boolean secondPass = false;
				if (secondPass) {
					
					boolean[] toadd2 = new boolean[knowledgeFormulas.size()];
					Set<String> finalSupport = new HashSet<>(extendedSupport);
					for (int i = 0; i < knowledgeFormulas.size(); ++i) {						
						Set<String> kSupport = new HashSet<>(knowledgeSupports.get(i));
						kSupport.retainAll(extendedSupport);
						if (!kSupport.isEmpty()) {
							finalSupport.addAll(kSupport);
							toadd2[i]=true;
						}
					}

					for (int i =0; i < knowledgeFormulas.size() ; i++) {					
						if (toadd2[i])
							selectedKnowledge.add("(" + knowledgeFormulas.get(i) + ")");
					}
					
					// update alphabet for "--remove-ap" invocations
					extendedSupport = finalSupport;
				}
				
				// make unique, but preserve order
				selectedKnowledge = new ArrayList<>(new LinkedHashSet<>(selectedKnowledge));
				
				String combinedMinFormula;
				String combinedMaxFormula;
				String andknowledge = String.join(" && ", selectedKnowledge);
				if (selectedKnowledge.isEmpty()) {
					andknowledge = "true";
				}
				combinedMinFormula = "(" + rawFormula + ") && " + andknowledge ;
				combinedMaxFormula = "(" + rawFormula + ") || ! (" + andknowledge + ")";
				
				
				Set<String> toQuantify = new HashSet<>(extendedSupport);
				toQuantify.removeAll(rawSupport);

				time = System.currentTimeMillis();
				// Min language
				TGBA tgbaMin = null;
				try {
					tgbaMin = sr.buildTGBAwithAlphabet(combinedMinFormula, toQuantify);
				} catch (IOException|InterruptedException|TimeoutException e) {
					System.out.println("Detected an error : "+e.getMessage() + " when treating Min formula for "+ model+ "-" + exam + "-" + lineNumber);
				}
				AutomatonStats statsMin = AutomatonStatsCalculator.computeStats(tgbaMin, formulaName, "min",time);
				synchronized (out) {out.println(statsMin.toString());}

				// Max language
				time = System.currentTimeMillis();
				TGBA tgbaMax = null;
				try {
					tgbaMax = sr.buildTGBAwithAlphabet(combinedMaxFormula, toQuantify);
				} catch (IOException|InterruptedException|TimeoutException e) {
					System.out.println("Detected an error : "+e.getMessage() + " when treating Max formula for "+ model+ "-" + exam + "-" + lineNumber);					
				}
				AutomatonStats statsMax = AutomatonStatsCalculator.computeStats(tgbaMax, formulaName, "max",time);
				synchronized (out) {out.println(statsMax.toString());}
				
				// incremental
				applyGivenThat(formulaName, rawTGBA, selectedKnowledge, out, sr,"");
				// global
				selectedKnowledge.clear();
				selectedKnowledge.add(andknowledge);
				// p stands for "precise"
				applyGivenThat(formulaName, rawTGBA, selectedKnowledge, out, sr,"p");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("What is the problem ??");
			e.printStackTrace();
		}
	}

	public void applyGivenThat(String formulaName, TGBA rawTGBA, ArrayList<String> selectedKnowledge,
			PrintStream out, SpotRunner sr, String prefix) {
		
		computeStats(formulaName, rawTGBA, GivenStrategy.MINATO, selectedKnowledge, out, sr, prefix);
		computeStats(formulaName, rawTGBA, GivenStrategy.STUTTER, selectedKnowledge, out, sr, prefix);
		computeStats(formulaName, rawTGBA, GivenStrategy.ALL, selectedKnowledge, out, sr, prefix);

	}

	public void computeStats(String formulaName, TGBA rawTGBA, GivenStrategy strat, ArrayList<String> selectedKnowledge,
			PrintStream out, SpotRunner sr, String prefix) {
		
		long time = System.currentTimeMillis();
		TGBA tgbaRes = sr.givenThat(rawTGBA, selectedKnowledge, strat);
		AutomatonStats stats = AutomatonStatsCalculator.computeStats(tgbaRes, formulaName, prefix + strat,time);
		synchronized (out) {out.println(stats.toString());}
	}


	private static Set<String> support(String formula) {
		Set<String> aps = new HashSet<>();
		Pattern pattern = Pattern.compile("p\\d+");
		Matcher matcher = pattern.matcher(formula);

		while (matcher.find()) {
			aps.add(matcher.group());
		}

		return aps;
	}




}
