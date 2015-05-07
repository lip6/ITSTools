package fr.lip6.move.gal.gal2smt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


import org.smtlib.ICommand;


public class SmtSerializationUtil {
	
	private static Logger getLog() { return Logger.getLogger(""); }
	
	public static void commandListToFile(List<ICommand> commandList, final String filename) throws IOException{
			long debut = System.currentTimeMillis();			
		
			if(! filename.endsWith(".smt2")){				
				getLog().warning("Warning: filename '" + filename + "' should end with .smt2 extension ");
				return;
			}
			
			
			try {
			    BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			    
			    for (int i = 0; i < commandList.size(); i++) {
			    	out.write(commandList.get(i).toString());
			        out.newLine();
				}			    
			    out.flush();
			    out.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			getLog().info("SMT model written to file : " +filename);			
			getLog().info("Time to serialize smt : " + (System.currentTimeMillis() - debut) + " ms");
			
		
	}
	
	public static void benchmarkToFile(String directory, List<String> s){
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(directory+"/benchmark"));
		    
		    for (int i = 0; i < s.size(); i++) {
		    	out.write(s.get(i));
		        out.newLine();
			}			    
		    out.flush();
		    out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}		
	}
	
	
	public static void deleteFiles(String directory, String end) {
		File folder = new File(directory);
		for (File f : folder.listFiles()) {
		    if (f.getName().endsWith(end)) {
		        f.delete(); // may fail mysteriously - returns boolean you may want to check
		    }
		}
	}
}
