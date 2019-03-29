package fr.lip6.move.gal.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.FlowDimacsPrinter;
import fr.lip6.move.gal.structural.StructuralReduction;

public class BlissRunner {

	
	private String pathToBliss;
	private String workFolder;
	private long timeout;


	
	public List<List<List<Integer>>> run (StructuralReduction sr) throws TimeoutException {				
		List<List<List<Integer>>> list = new ArrayList<>();
		
		String graphff = FlowDimacsPrinter.drawNet(sr);
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToBliss);
		cl.addArg("-directed");
		cl.addArg(graphff);
		System.out.println("Running Bliss : " + cl);
		
		try {
			String stdOutput = workFolder + "/petri.sym";
			int status = Runner.runTool(timeout, cl, new File(stdOutput), true);
			System.out.println("Run of Bliss captured in " + stdOutput);
			BufferedReader reader = new BufferedReader(new FileReader(stdOutput));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("generator")) {
					line = line.replace("VARORDER:  ", "");
					String[] orders = line.split(" ");
					//this.order = orders;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}

}
