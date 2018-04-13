package fr.lip6.move.gal.greatspn.order;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.runtime.IStatus;

//import fr.lip6.move.gal.itstools.CommandLine;
//import fr.lip6.move.gal.itstools.ProcessController.TimeOutException;
//import fr.lip6.move.gal.itstools.Runner;

import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.ProcessController;
import fr.lip6.move.gal.process.ProcessController.TimeOutException;
import fr.lip6.move.gal.process.Runner;


public class GreatSPNRunner {

	private String path;
	private String workFolder;
	private String modelPath;
	private String[] order;
	private ArrayList<String> config;
	private String binpath;



	public GreatSPNRunner(String workFolder, String modelPath,String path) throws IOException {//, String binPath
		this.path = path;
		this.binpath = new File(path).getParentFile().getCanonicalPath();
		this.workFolder = workFolder;
		this.modelPath = modelPath;
		config = new ArrayList<String>();
	}




	public GreatSPNRunner(String workFolder, String modelPath) {//, String binPath
		Scanner sc=new Scanner(System.in);
		String s=sc.nextLine(); 
		this.path = s;
		this.workFolder = workFolder;
		this.modelPath = modelPath;
		config = new ArrayList<String>();
	}






	public void configure(List<String> ohs) {
		for(String oh : ohs)
			config.add(oh);
	}






	public void configure(String oh) {
		config.add(oh);
	}





	public void run () {
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));		
		cl.addArg(path);
		cl.addArg(modelPath);
		for(String oh : config)
			cl.addArg(oh);
		cl.addArg("-varord");
		System.out.println("Running greatSPN : " + cl);
		try {
			String stdOutput = workFolder +"/outPut.txt"; 
			Map<String, String> envLib = new HashMap<>();
			String LIB = "LD_LIBRARY_PATH";
			String env = binpath;
			String old = System.getenv(LIB);
			if (old != null) {
				env = binpath + ":" + old;
			}
			envLib.put(LIB, env);
			int status = Runner.runTool(100, cl, new File(stdOutput), true, envLib );
			System.out.println("Run of greatSPN captured in " +stdOutput);
			BufferedReader reader = new BufferedReader(new FileReader(stdOutput));
			String line;
			while ((line = reader.readLine()) != null) {

				if (line.startsWith("VARORDER")) {
					line=line.replace("VARORDER:  ", "");
					String[] orders = line.split(" ");
					this.order = orders;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getOrder() {
		return order;
	}
}
