package fr.lip6.move.gal.greatspn.order;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.core.runtime.IStatus;

import fr.lip6.move.gal.greatspn.order.ajout.flag.OrderHeuristic;
import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.ProcessController.TimeOutException;
import fr.lip6.move.gal.itstools.Runner;

public class GreatSPNRunner {
	
	
	private String path;
	private String workFolder;
	private String modelPath;
	private String[] order;
	private ArrayList<OrderHeuristic> config;
	public GreatSPNRunner(String workFolder, String modelPath) {//, String binPath
		//String s;
		
		Scanner sc=new Scanner(System.in);
		System.out.println("saisir chemin bin");
		//System.out.print("\nEntrer une ligne : ");
		String s=sc.nextLine(); 
		//System.out.println("La ligne est : "+s); } }
		this.path =  s;
		//"/home/joseph/Documents/GreatSPN/usr/local/GreatSPN/bin/RGMEDD2";
		//"/data/ythierry/gspn/usr/local/GreatSPN/bin/RGMEDD2";
		//"/home/joe/Documents/LTSmin/greatSPN/usr/local/GreatSPN/bin/RGMEDD2
		this.workFolder = workFolder;
		this.modelPath = modelPath;
		config = new ArrayList<OrderHeuristic>();
		
	}
	
	public void configure(List<OrderHeuristic> ohs) {
		for(OrderHeuristic oh : ohs)
			config.add(oh);
	}
	public void configure(OrderHeuristic oh) {
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
		for(OrderHeuristic oh : config)
			cl.addArg(oh.toString());
		//cl.addArg("-FR");
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
		        	  line=line.replace("VARORDER:  ", "");
		        	  String[] orders = line.split(", ");
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
