package fr.lip6.ltl.stats;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

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
            	System.out.println("Working on "+dir+" for " + formulaType);
                calculateBenchmarkStats(formulasFile, knowledgeFile, falseKnowledgeFile, outputDir);
                nbTreated++;
            } else {
                System.err.println("Incomplete set of files in folder: " + benchmarkDir.toString());
            }
        }
        
    }

    private void calculateBenchmarkStats(File formulasFile, File knowledgeFile, File falseKnowledgeFile, Path outputDir) {
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
                    String derivedName = formulasFile.getParentFile().getName() + "-" + formulasFile.getName().replace("raw", "").replace(".ltl", "") + "-" + String.format("%02d", lineNumber);

                    // Compute stats
                    AutomatonStats stats = AutomatonStatsCalculator.computeStats(tgba, derivedName);

                    // Print the stats (to System.out for now)
                    System.out.println(stats.toString());
                } else {
                    System.err.println("Failed to build TGBA for formula on line " + lineNumber);
                }
                lineNumber++;
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }


	public void calculateStats(String inputFolder, String outputFolder) {
		calculateStats(inputFolder, outputFolder, "LTLFireability");
		
		calculateStats(inputFolder, outputFolder, "LTLCardinality");
		
		System.out.println("In total out of "+nbFolder+" found knowledge information in "+ nbTreated + " cases.");
		
	}

}
