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

public class GreatSPNRunner {

	private String path;
	private String workFolder;
	private String modelPath;
	private String[] order;
	private ArrayList<String> config;

	public GreatSPNRunner(String workFolder, String modelPath, String path) throws IOException {// , String binPath
		this.path = path;
		this.workFolder = workFolder;
		this.modelPath = modelPath;
		config = new ArrayList<String>();
	}
	
	public void configure(List<String> ohs) {
		for (String oh : ohs)
			config.add(oh);
	}

	public void configure(String oh) {
		config.add(oh);
	}

	public void run() throws TimeoutException {
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(path);
		cl.addArg(modelPath);
		for (String oh : config)
			cl.addArg(oh);
		System.out.println("Running greatSPN : " + cl);
		try {
			String stdOutput = workFolder + "/outPut.txt";
			int status = Runner.runTool(100, cl, new File(stdOutput), true);
			System.out.println("Run of greatSPN captured in " + stdOutput);
			BufferedReader reader = new BufferedReader(new FileReader(stdOutput));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("VARORDER")) {
					line = line.replace("VARORDER:  ", "");
					String[] orders = line.split(" ");
					this.order = orders;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String[] getOrder() {
		return order;
	}
}
