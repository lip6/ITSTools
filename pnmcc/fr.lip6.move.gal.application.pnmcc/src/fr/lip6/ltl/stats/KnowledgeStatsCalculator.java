package fr.lip6.ltl.stats;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

public class KnowledgeStatsCalculator {

    int nbFolder = 0;
    int nbTreated = 0;
	
    public void calculateStats(String inputFolder, String outputFolder, String formulaType) {
        Path inputPath = Paths.get(inputFolder);
        Path outputPath = Paths.get(outputFolder);

        // Ensure output folder exists
        try {
            Files.createDirectories(outputPath);
        } catch (IOException e) {
            System.err.println("Could not create output directory: " + e.getMessage());
            return;
        }

        // Traverse the input directory
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(inputPath)) {
            for (Path path : directoryStream) {
                if (Files.isDirectory(path)) {
                    findAndProcessFiles(path, outputPath, formulaType);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input directory: " + e.getMessage());
        }
    }

    private void findAndProcessFiles(Path benchmarkDir, Path outputDir, String formulaType) {
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
                calculateBenchmarkStats(formulasFile, knowledgeFile, falseKnowledgeFile, outputDir);
                nbTreated++;
            } else {
                System.err.println("Incomplete set of files in folder: " + benchmarkDir.toString());
            }
        }
        
    }

    private void calculateBenchmarkStats(File formulasFile, File knowledgeFile, File falseKnowledgeFile, Path outputDir) {
        
    	String model = formulasFile.getParentFile().getName();
    	String exam = formulasFile.getName().replace("raw", "").replace(".ltl", "");
    	
        String type = "raw";
        
        PrintStream out = System.out;
        
      //  generateStats(formulasFile, model, exam, type, out);
        
        generateMinMax(formulasFile,knowledgeFile,model,exam,out);
        
    }

    private void generateMinMax(File formulasFile, File knowledgeFile, String model, String exam, PrintStream out)  {
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

        for (int lineNumber = 0; lineNumber < rawFormulas.size(); ++lineNumber) {
            String rawFormula = rawFormulas.get(lineNumber);
            Set<String> rawSupport = rawSupports.get(lineNumber);

            List<String> selectedKnowledge = new ArrayList<>();

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

            TGBA tgbaMin = sr.buildTGBAwithAlphabet(combinedMinFormula, toQuantify);
            TGBA tgbaMax = sr.buildTGBAwithAlphabet(combinedMaxFormula, toQuantify);
            // Min language
            String derivedMinName = model + "-" + exam + "-min-" + String.format("%02d", lineNumber);
            AutomatonStats statsMin = AutomatonStatsCalculator.computeStats(tgbaMin, derivedMinName, "min");
            out.println(statsMin.toString());

            // Max language
            String derivedMaxName = model + "-" + exam + "-max-" + String.format("%02d", lineNumber);
            AutomatonStats statsMax = AutomatonStatsCalculator.computeStats(tgbaMax, derivedMaxName, "max");
            out.println(statsMax.toString());
        }
        } catch (IOException|TimeoutException|InterruptedException e) {
			e.printStackTrace();
		}
    }


	public void generateStats(File formulasFile, String model, String exam, String type, PrintStream out) {
		// Initialize the SpotRunner with a 10-second timeout
        SpotRunner sr = new SpotRunner(10);

        try (BufferedReader br = new BufferedReader(new FileReader(formulasFile))) {
            String formula;
            int lineNumber = 0;
            while ((formula = br.readLine()) != null) {
                // Build TGBA
                TGBA tgba = sr.buildTGBA(formula);
                
                if (tgba != null) {
                    // Derive name for stats
                    String derivedName =  model + "-" + exam + "-" + String.format("%02d", lineNumber);

                    // Compute stats
                    AutomatonStats stats = AutomatonStatsCalculator.computeStats(tgba, derivedName,type);

                    // Print the stats (to System.out for now)
                    out.println(stats.toString());
                } else {
                    System.err.println("Failed to build TGBA for formula on line " + lineNumber);
                }
                lineNumber++;
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
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
	

	public void calculateStats(String inputFolder, String outputFolder) {
		System.out.println(AutomatonStats.buildHeaderLine());
		calculateStats(inputFolder, outputFolder, "LTLFireability");
		
		calculateStats(inputFolder, outputFolder, "LTLCardinality");
		
		System.out.println("In total out of "+nbFolder+" found knowledge information in "+ nbTreated + " cases.");
		
	}

}
