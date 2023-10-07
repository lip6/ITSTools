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
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.application.runner.spot.SpotRunner.GivenStrategy;

public class KnowledgeStatsCalculator {

	int nbFolder = 0;
	int nbTreated = 0;

	public void calculateStats(String inputFolder, String outputFolder, String formulaType) {
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
			out.println(AutomatonStats.buildHeaderLine());

			// Traverse the input directory
			try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(inputPath)) {
				for (Path path : directoryStream) {
					if (Files.isDirectory(path)) {
						findAndProcessFiles(path, out, formulaType);
					}
				}
			} catch (IOException e) {
				System.err.println("Error reading input directory: " + e.getMessage());
			}

		} catch (FileNotFoundException e) {
			System.err.println("Error opening output file: " + e.getMessage());
		}

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
						nbFolder++;
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
				nbTreated++;
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

				// Generate raw stats
				long time = System.currentTimeMillis();
				TGBA rawTGBA = sr.buildTGBA(rawFormula);
				String formulaName = model + "-" + exam + String.format("%02d", lineNumber);
				AutomatonStats rawStats = AutomatonStatsCalculator.computeStats(rawTGBA, formulaName, "raw",time);
				out.println(rawStats.toString());


				ArrayList<String> selectedKnowledge = new ArrayList<>();

				// First and second pass
				Set<String> extendedSupport = new HashSet<>(rawSupport);
				for (int i = 0; i < knowledgeFormulas.size(); ++i) {
					Set<String> kSupport = new HashSet<>(knowledgeSupports.get(i));
					kSupport.retainAll(extendedSupport);
					if (!kSupport.isEmpty()) {
						selectedKnowledge.add("(" + knowledgeFormulas.get(i) + ")");
						extendedSupport.addAll(kSupport);
					}
				}

				String combinedMinFormula;
				String combinedMaxFormula;

				if (selectedKnowledge.isEmpty()) {
					combinedMinFormula = "(" + rawFormula + ")";
					combinedMaxFormula = "(" + rawFormula + ")";
				} else {
					combinedMinFormula = "(" + rawFormula + ") && " + String.join(" && ", selectedKnowledge);
					combinedMaxFormula = "(" + rawFormula + ") || ! (" + String.join(" && ", selectedKnowledge) + ")";
				}
				Set<String> toQuantify = new HashSet<>(extendedSupport);
				toQuantify.removeAll(rawSupport);

				time = System.currentTimeMillis();
				// Min language
				TGBA tgbaMin = sr.buildTGBAwithAlphabet(combinedMinFormula, toQuantify);
				AutomatonStats statsMin = AutomatonStatsCalculator.computeStats(tgbaMin, formulaName, "min",time);
				out.println(statsMin.toString());

				// Max language
				time = System.currentTimeMillis();
				TGBA tgbaMax = sr.buildTGBAwithAlphabet(combinedMaxFormula, toQuantify);
				AutomatonStats statsMax = AutomatonStatsCalculator.computeStats(tgbaMax, formulaName, "max",time);
				out.println(statsMax.toString());

				// Minato
				time = System.currentTimeMillis();
				TGBA tgbaMinato = sr.givenThat(rawTGBA, selectedKnowledge, GivenStrategy.MINATO);
				AutomatonStats statsMinato = AutomatonStatsCalculator.computeStats(tgbaMinato, formulaName, "Minato",time);
				out.println(statsMinato.toString());

				// Stutter
				time = System.currentTimeMillis();
				TGBA tgbaStutter = sr.givenThat(rawTGBA, selectedKnowledge, GivenStrategy.STUTTER);
				AutomatonStats statsStutter = AutomatonStatsCalculator.computeStats(tgbaStutter, formulaName, "si", time);
				out.println(statsStutter.toString());

				// All
				time = System.currentTimeMillis();
				TGBA tgbaAll = sr.givenThat(rawTGBA, selectedKnowledge, GivenStrategy.ALL);
				AutomatonStats statsAll = AutomatonStatsCalculator.computeStats(tgbaAll, formulaName, "all", time);
				out.println(statsAll.toString());

			}
		} catch (IOException|TimeoutException|InterruptedException e) {
			e.printStackTrace();
		}
	}


	//	public void generateStats(File formulasFile, String model, String exam, String type, PrintStream out) {
	//		// Initialize the SpotRunner with a 10-second timeout
	//        SpotRunner sr = new SpotRunner(10);
	//
	//        try (BufferedReader br = new BufferedReader(new FileReader(formulasFile))) {
	//            String formula;
	//            int lineNumber = 0;
	//            while ((formula = br.readLine()) != null) {
	//                // Build TGBA
	//                TGBA tgba = sr.buildTGBA(formula);
	//                
	//                if (tgba != null) {
	//                    // Derive name for stats
	//                    String derivedName =  model + "-" + exam + "-" + String.format("%02d", lineNumber);
	//
	//                    // Compute stats
	//                    AutomatonStats stats = AutomatonStatsCalculator.computeStats(tgba, derivedName,type);
	//
	//                    // Print the stats (to System.out for now)
	//                    out.println(stats.toString());
	//                } else {
	//                    System.err.println("Failed to build TGBA for formula on line " + lineNumber);
	//                }
	//                lineNumber++;
	//            }
	//        } catch (IOException | TimeoutException | InterruptedException e) {
	//            System.err.println("Error occurred: " + e.getMessage());
	//        }
	//	}

	private static Set<String> support(String formula) {
		Set<String> aps = new HashSet<>();
		Pattern pattern = Pattern.compile("p\\d+");
		Matcher matcher = pattern.matcher(formula);

		while (matcher.find()) {
			aps.add(matcher.group());
		}

		return aps;
	}


	public void calculateStats(String inputFolder, String outputFolder) {

		calculateStats(inputFolder, outputFolder, "LTLFireability");

		calculateStats(inputFolder, outputFolder, "LTLCardinality");

		System.out.println("In total out of "+nbFolder+" found knowledge information in "+ nbTreated + " cases.");

	}

}
