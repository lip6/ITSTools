package fr.lip6.move.gal.greatspn.order;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.eclipse.core.runtime.IStatus;

import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.ProcessController.TimeOutException;
import fr.lip6.move.gal.itstools.Runner;

public class GreatSPNRunner {
	
	
	private String path;
	private String workFolder;
	private String modelPath;
	private String[] order;

	public GreatSPNRunner(String workFolder, String modelPath) {
		this.path = "/data/ythierry/gspn/usr/local/GreatSPN/bin/RGMEDD2";
		//"/home/joe/Documents/LTSmin/greatSPN/usr/local/GreatSPN/bin/RGMEDD2
		this.workFolder = workFolder;
		this.modelPath = modelPath;
	}

	public void run () {
		// String cmd = path+ " " + modelPath + " -FR -varord" ;
		// link
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));		
		cl.addArg(path);
		cl.addArg(modelPath);
		cl.addArg("-FR");
		cl.addArg("-varord");
		
		System.out.println("Running greatSPN : " + cl);
		IStatus status = null;
		try {
			status = Runner.runTool(100, cl);
			ByteArrayOutputStream stdOutput = new ByteArrayOutputStream();

			Runner.runTool(100, cl, stdOutput, true);
			
			//System.out.println(stdOutput.toString());
			BufferedReader reader = new BufferedReader(new StringReader(stdOutput.toString()));
			String line;
			while ((line = reader.readLine()) != null) {
                
		          if (line.startsWith("VARORDER")) {
		        	  line.replace("VARORDER:  ", "");
		        	  String[] orders = line.split(" ");
		        	  this.order = orders;
		        	  break;
		          }
		                 
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (status == null || !status.isOK()) {
			throw new RuntimeException("Could run greatspn executable ." + cl);
		}
		
		
		//| sed -n /\"VARORDER\"/p | sed -E s/^\"VARORDER:  \"// > order.txt";
		// String[] args = { "/bin/sh", "-c", cmd};
//
//		try {
//			Runtime.getRuntime().exec(args);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  	
		
	}
	
	
	public String[] getOrder() {
		return order;
	}
}
