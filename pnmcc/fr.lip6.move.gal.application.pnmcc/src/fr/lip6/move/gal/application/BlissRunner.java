package fr.lip6.move.gal.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.FlowDimacsPrinter;
import fr.lip6.move.gal.structural.StructuralReduction;

public class BlissRunner {

	
	private String pathToBliss;
	private String workFolder;
	private long timeout;
	
	public BlissRunner(String pathToBliss, String workFolder, long timeout) {
		super();
		this.pathToBliss = pathToBliss;
		this.workFolder = workFolder;
		this.timeout = timeout;
	}
	
	public void computeMatrixForm (List<List<List<Integer>>> generators) {
		List<Set<List<Integer>>> fused = new ArrayList<>();
				
		List<List<List<Integer>>> remains = new ArrayList<>();
		for (List<List<Integer>> gen : generators) {
			if (gen.stream().allMatch(l->l.size() == 2)) {
				
				List<Integer> l = new ArrayList<>();
				List<Integer> r = new ArrayList<>();
				for (List<Integer> pair : gen) {
					l.add(pair.get(0));
					r.add(pair.get(1));
				}
				Set<List<Integer>> tree = new HashSet<>();
				tree.add(l);
				tree.add(r);
				fused.add(tree);
			} else {
				remains.add(gen);
			}
		}
		System.out.println("Fused before start :" + fused);
		
		for (int i = fused.size()-1 ; i > 0 ; i--) {
			// see if set i can be fused into any of the preceding sets
			for (int  j=0; j < i ; j++) {
				if (fused.get(i).stream().anyMatch(fused.get(j)::contains)) {
					fused.get(j).addAll(fused.get(i));
					fused.remove(i);
					System.out.println("Fusion successful, obtained a set of size : " + fused.get(j).size());
					break;
				}
			}
		}
		System.out.println("Built matrix of size "+ fused.size() + " starting from " + generators.size() + " generators :" +  fused);
		System.out.println("Remains :" + remains);
		
	}

	public List<List<List<Integer>>> run (StructuralReduction sr) throws TimeoutException {				
		List<List<List<Integer>>> list = new ArrayList<>();
		
		String graphff = FlowDimacsPrinter.drawNet(sr);
		
		CommandLine cl = new CommandLine();
		cl.setWorkingDir(new File(workFolder));
		cl.addArg(pathToBliss);
		cl.addArg("-directed");
		cl.addArg(graphff);
		long time = System.currentTimeMillis();
		System.out.println("Running Bliss : " + cl);
		
		try {
			String stdOutput = workFolder + "/petri.sym";
			int status = Runner.runTool(timeout, cl, new File(stdOutput), true);
			System.out.println("Run of Bliss took "+ (System.currentTimeMillis() -time) + " ms captured in " + stdOutput);
			BufferedReader reader = new BufferedReader(new FileReader(stdOutput));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Generator:")) {
					line = line.replace("Generator: ", "");
					// kill first and last parenthesis					
					line = line.substring(1,line.length() -1);
					String[] gens = line.split("\\)\\(");
					List<List<Integer>> genline = new ArrayList<>();
					list.add(genline);
					for (String gen : gens) {
						List<Integer> local = new ArrayList<>(); 
						String[] gparts = gen.split(",");
						for (String s : gparts) {
							local.add(Integer.parseInt(s));
						}
						genline.add(local);
					}
				} else if (line.startsWith("|Aut|")) {
					System.out.println("Bliss found a total of " + line + " encoded by " + list.size() + " generators."); 
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
