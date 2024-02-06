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

		        paths.parallelStream().unordered().forEach(path -> {
		            try {
		                findAndProcessFiles(path, out, "LTLFireability");
		            } catch (Exception e) {
		                System.err.println("Error processing: " + path.toString() + " - " + e.getMessage());
		            }
		        });
		        paths.parallelStream().unordered().forEach(path -> {
		            try {
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
		generateMinMax(formulasFile,knowledgeFile,falseKnowledgeFile,model,exam,out, false);
		generateMinMax(formulasFile,knowledgeFile,falseKnowledgeFile,model,exam,out, true);

	}

	private void generateMinMax(File formulasFile, File knowledgeFile, File falseKnowledgeFile, String model, String exam, PrintStream out, boolean negateFormula)  {
		try {
//			if (! model.equals("CloudOpsManagement-PT-00080by00040"))
//				return;

			List<String> rawFormulas = Files.readAllLines(formulasFile.toPath());
			List<String> knowledgeFormulas = Files.readAllLines(knowledgeFile.toPath());
			List<String> falseKnowledgeFormulas = Files.readAllLines(falseKnowledgeFile.toPath());

			List<Set<String>> rawSupports = new ArrayList<>();
			List<Set<String>> knowledgeSupports = new ArrayList<>();
			List<Set<String>> falseKnowledgeSupports = new ArrayList<>();

			
			// Compute support sets once and store
			for (String formula : rawFormulas) {
				rawSupports.add(support(formula));
			}
			for (String formula : knowledgeFormulas) {
				knowledgeSupports.add(support(formula));
			}
			for (String formula : falseKnowledgeFormulas) {
				falseKnowledgeSupports.add(support(formula));
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
				
				
				// the assertions we keep
				ArrayList<String> selectedKnowledge = new ArrayList<>();
				// the support of these assertions
				Set<String> extendedSupport = new HashSet<>(rawSupport);
				
				// First pass to retain facts whose alphabet intersects formula
				selectKnowledge(knowledgeFormulas, knowledgeSupports, rawSupport, selectedKnowledge, extendedSupport);
				// make unique, but preserve order
				selectedKnowledge = new ArrayList<>(new LinkedHashSet<>(selectedKnowledge));

				ArrayList<String> selectedFalseKnowledge = new ArrayList<>();
				selectKnowledge(falseKnowledgeFormulas, falseKnowledgeSupports, rawSupport, selectedFalseKnowledge, extendedSupport);
				selectedFalseKnowledge = new ArrayList<>(new LinkedHashSet<>(selectedFalseKnowledge));
				
				String andknowledge = String.join(" && ", selectedKnowledge);
				if (selectedKnowledge.isEmpty()) {
					System.out.println("No knowledge for formula " + formulaName + " (negative facts :"+selectedFalseKnowledge.size()+ ")");
					
					andknowledge = "1";					
				}
				if (selectedKnowledge.isEmpty() && selectedFalseKnowledge.isEmpty()) {
					System.out.println("No knowledge for formula " + formulaName + " (negative facts :"+selectedFalseKnowledge.size()+ ")");
					continue;
				}
				
				TGBA rawTGBA = buildAndPrintAutomatonStats("raw", rawFormula, formulaName, out, sr,0);
				
				boolean withMinMax = true;
				
				if (withMinMax) {
					// NB : no existential quantification
					buildAndPrintAutomatonStats("min", "(" + rawFormula + ")&&" + andknowledge, formulaName, out, sr,selectedKnowledge.size());

					buildAndPrintAutomatonStats("max", "(" + rawFormula + ")||!(" + andknowledge + ")", formulaName, out, sr,selectedKnowledge.size());
				}
				
				Set<String> toQuantify = new HashSet<>(extendedSupport);
				toQuantify.removeAll(rawSupport);
				//andknowledge = "(G((p16||!p32)))";
				{
					// with QE
					List<String> selectedKnowledgeQE = new ArrayList<>(selectedKnowledge.size());
					for (String k:selectedKnowledge) {
						selectedKnowledgeQE.add(quantifySyntactically(k, toQuantify));
					}
					String quantifiedKnowledge = selectedKnowledgeQE.isEmpty() ? "1" : String.join(" && ", selectedKnowledgeQE);
					
					TGBA minqe = buildAndPrintAutomatonStats("minqe", "(" + rawFormula + ")&&" + quantifiedKnowledge, formulaName, out, sr,selectedKnowledge.size());
					if (minqe != null) {
						TGBA minrelax = computeStats(formulaName, minqe, GivenStrategy.STUTTER_RELAX, selectedKnowledge, out, sr, "p"+ "minqe"+GivenStrategy.STUTTER_RELAX);
					}
					String maxqeform = "(" + rawFormula + ")||!(" + quantifiedKnowledge + ")";
					TGBA maxqe = buildAndPrintAutomatonStats("maxqe", maxqeform, formulaName, out, sr,selectedKnowledge.size());
					
					/* negative knowledge */
					/* K- inter K+ inter A(phi) = empty */
					/* So negate formula, AND the quantified knowledge -> automaton */
					/* then test AND (product) the negative knowledge one by one is empty */
					if (! selectedFalseKnowledge.isEmpty()) {
						long time = System.currentTimeMillis();
						
						String negRawFormula = rawFormulas.get(lineNumber);
						if (! negateFormula) {
							negRawFormula = "!(" + negRawFormula + ")";
						}
						// cumulate positive knowledge and negated formula automaton
						String knowledgeAndPhi = "(" + negRawFormula + ")&&(" + quantifiedKnowledge + ")";
						// Build our automaton for (K+ AND phi)
						//boolean hasCounterExample = sr.testNegativeKnowledge(selectedFalseKnowledge, knowledgeAndPhi);
						boolean hasCounterExample = sr.isIncludedIn(selectedFalseKnowledge, maxqe); 
						
						// System.out.println("! MaxQE = !(" + maxqeform + ")  false knowledge : " + selectedFalseKnowledge + " verdict :" + hasCounterExample);
						
						TGBA tgba = null;
						if (hasCounterExample) {
							tgba = TGBA.makeTrue();
						}
						AutomatonStats rawStats = AutomatonStatsCalculator.computeStats(tgba, formulaName, "negative",time,selectedFalseKnowledge.size());
						synchronized (out) { out.println(rawStats.toString()); }												
					}
					
				}
				
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

	public void selectKnowledge(List<String> knowledgeFormulas, List<Set<String>> knowledgeSupports,
			Set<String> rawSupport, ArrayList<String> selectedKnowledge, Set<String> extendedSupport) {
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
	}

	private String quantifySyntactically(String form, Set<String> toQuantify) {
		StringBuilder sb = new StringBuilder(form);
		for (String ap : toQuantify) {
			if (form.matches(".*\\b"+ap+"\\b.*")) {
				StringBuilder sb2 = new StringBuilder(2*sb.length()+16);
				sb2.append("((").append(sb.toString().replaceAll("\\b"+ap+"\\b","1")).append(")")
				.append("||")
				.append("(").append(sb.toString().replaceAll("\\b"+ap+"\\b","0")).append("))");
				sb = sb2;
			}
		}
		return sb.toString();
	}


	public TGBA buildAndPrintAutomatonStats(String type, String formula, String formulaName, PrintStream out,
			SpotRunner sr, int nbFacts) {
		// Generate raw stats
		long time = System.currentTimeMillis();
		TGBA tgba = null;
		try {
			tgba = sr.buildTGBA(formula);
		} catch (IOException|InterruptedException|TimeoutException e) {
			System.out.println("Detected an error : "+e.getMessage() + " when treating "+type+" formula for "+ formulaName);					
		}
		AutomatonStats rawStats = AutomatonStatsCalculator.computeStats(tgba, formulaName, type,time,nbFacts);		
		synchronized (out) { out.println(rawStats.toString()); }
		return tgba;
	}

	public void applyGivenThat(String formulaName, TGBA rawTGBA, ArrayList<String> selectedKnowledge,
			PrintStream out, SpotRunner sr, String prefix) {
		
		boolean compositions = true;
		boolean doubleCompositions = false;
		
		TGBA minato = computeStats(formulaName, rawTGBA, GivenStrategy.MINATO, selectedKnowledge, out, sr, prefix+ GivenStrategy.MINATO);
		// minato/minato
		// computeStats(formulaName, minato, GivenStrategy.MINATO, selectedKnowledge, out, sr, prefix+ GivenStrategy.MINATO+ GivenStrategy.MINATO);
		if (compositions && minato != null) {
			TGBA minrelax = computeStats(formulaName, minato, GivenStrategy.STUTTER_RELAX, selectedKnowledge, out, sr, prefix+ GivenStrategy.MINATO+GivenStrategy.STUTTER_RELAX);
			if (doubleCompositions &&  minrelax != null) {
				TGBA minrelaxmin = computeStats(formulaName, minrelax, GivenStrategy.MINATO, selectedKnowledge, out, sr, prefix+ GivenStrategy.MINATO+ GivenStrategy.STUTTER_RELAX+ GivenStrategy.MINATO);
				if (minrelaxmin != null) { 
					TGBA minrelaxminrelax = computeStats(formulaName, minrelaxmin, GivenStrategy.STUTTER_RELAX, selectedKnowledge, out, sr, prefix+ GivenStrategy.MINATO+ GivenStrategy.STUTTER_RELAX+ GivenStrategy.MINATO + GivenStrategy.STUTTER_RELAX);
				}
			}
		}
		
		TGBA relax = computeStats(formulaName, rawTGBA, GivenStrategy.STUTTER_RELAX, selectedKnowledge, out, sr, prefix+ GivenStrategy.STUTTER_RELAX);
		if (compositions && relax != null) {
			TGBA relaxmin = computeStats(formulaName, relax, GivenStrategy.MINATO, selectedKnowledge, out, sr, prefix+ GivenStrategy.STUTTER_RELAX+ GivenStrategy.MINATO);
			if (doubleCompositions && relaxmin != null) {
				TGBA relaxminrelax = computeStats(formulaName, relaxmin, GivenStrategy.STUTTER_RELAX, selectedKnowledge, out, sr, prefix+ GivenStrategy.STUTTER_RELAX+ GivenStrategy.MINATO+ GivenStrategy.STUTTER_RELAX);
				if (relaxminrelax != null) { 
					TGBA relaxminrelaxmin = computeStats(formulaName, relaxminrelax, GivenStrategy.MINATO, selectedKnowledge, out, sr, prefix+ GivenStrategy.STUTTER_RELAX+ GivenStrategy.MINATO+ GivenStrategy.STUTTER_RELAX+ GivenStrategy.MINATO);
				}
			}
		}
		
		computeStats(formulaName, rawTGBA, GivenStrategy.STUTTER_RESTRICT, selectedKnowledge, out, sr, prefix+ GivenStrategy.STUTTER_RESTRICT);
		
//		TGBA all =computeStats(formulaName, rawTGBA, GivenStrategy.ALL, selectedKnowledge, out, sr, prefix+ GivenStrategy.ALL);
//		
//		if (compositions && all != null) {
//			TGBA allall =computeStats(formulaName, all, GivenStrategy.ALL, selectedKnowledge, out, sr, prefix+ GivenStrategy.ALL+ GivenStrategy.ALL);
//		}

	}

	public TGBA computeStats(String formulaName, TGBA rawTGBA, GivenStrategy strat, ArrayList<String> selectedKnowledge,
			PrintStream out, SpotRunner sr, String prefix) {
		
		long time = System.currentTimeMillis();
		TGBA tgbaRes = sr.givenThat(rawTGBA, selectedKnowledge, strat);
		AutomatonStats stats = AutomatonStatsCalculator.computeStats(tgbaRes, formulaName, prefix,time,selectedKnowledge.size());
		synchronized (out) {out.println(stats.toString());}
		return tgbaRes;
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
