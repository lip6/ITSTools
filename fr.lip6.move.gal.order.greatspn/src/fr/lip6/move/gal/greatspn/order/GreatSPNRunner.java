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
		//String s;
		
//		Scanner sc=new Scanner(System.in);
//		System.out.println("saisir chemin bin");
//		//System.out.print("\nEntrer une ligne : ");
//		String s=sc.nextLine(); 
//		//System.out.println("La ligne est : "+s); } }
		this.path = path;
		this.binpath = new File(path).getParentFile().getCanonicalPath();
		//"/home/joseph/Documents/GreatSPN/usr/local/GreatSPN/bin/RGMEDD2";
		//"/data/ythierry/gspn/usr/local/GreatSPN/bin/RGMEDD2";
		//"/home/joe/Documents/LTSmin/greatSPN/usr/local/GreatSPN/bin/RGMEDD2
		this.workFolder = workFolder;
		this.modelPath = modelPath;
		config = new ArrayList<String>();
		
	}
	public GreatSPNRunner(String workFolder, String modelPath) {//, String binPath
		//String s;
		
		Scanner sc=new Scanner(System.in);
//		System.out.println("saisir chemin bin");
//		//System.out.print("\nEntrer une ligne : ");
		String s=sc.nextLine(); 
//		//System.out.println("La ligne est : "+s); } }
		this.path = s;
		//"/home/joseph/Documents/GreatSPN/usr/local/GreatSPN/bin/RGMEDD2";
		//"/data/ythierry/gspn/usr/local/GreatSPN/bin/RGMEDD2";
		//"/home/joe/Documents/LTSmin/greatSPN/usr/local/GreatSPN/bin/RGMEDD2
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
		// String cmd = path+ " " + modelPath + " -FR -varord" ;
		// link
		//System.load("/home/joseph/Documents/GreatSPN/usr/local/lib/libmeddly.so.0" );
		//System.load("/home/joseph/Documents/GreatSPN/usr/local/lib/libmeddly.so.0.0.0" );
		//System.load("/home/joseph/Documents/GreatSPN/usr/local/lib/libmeddly.la" );
		//System.load("/home/joseph/Documents/GreatSPN/usr/local/lib/libmeddly.a" );
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));		
		cl.addArg(path);
		cl.addArg(modelPath);
		for(String oh : config)
			cl.addArg(oh);
		//cl.addArg("-FR");
		cl.addArg("-varord");
		
		System.out.println("Running greatSPN : " + cl);
//		IStatus status = null;
		
		try {
//			ByteArrayOutputStream stdOutput = new ByteArrayOutputStream();
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
			
//			Runner.runTool(100, cl);
			System.out.println("Run of greatSPN captured in " +stdOutput);
			
			BufferedReader reader = new BufferedReader(new FileReader(stdOutput));
			String line;
			while ((line = reader.readLine()) != null) {
                
		          if (line.startsWith("VARORDER")) {
		        	  line=line.replace("VARORDER:  ", "");
		        	  String[] orders = line.split(" ");
		        	  this.order = orders;
		        	  break;
		          }
		                 
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		} catch (TimeOutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (status == null || !status.isOK()) {
//			throw new RuntimeException("Could run greatspn executable ." + cl);
//		}
		catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
