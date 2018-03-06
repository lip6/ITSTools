package fr.lip6.move.gal.order.application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import fr.lip6.move.gal.contribution.orders.flag.OrderHeuristic;
import fr.lip6.move.gal.nupn.PTNetReader;
import fr.lip6.move.pnml.ptnet.PetriNet;


public class Application implements IApplication {
	private static final String APPARGS = "application.args";
	private static final String INPUT_FILE = "-i";
	private static final String INPUT_TYPE = "-t";
	private static final String ORDER_FLAG = "-order";
	private static final String GSPN_PATH = "-greatspnpath";
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		
		String [] args = (String[]) context.getArguments().get(APPARGS);
		
		String inputff = null;
		String inputType = null;
		String gspnpath = null;
		
		List<OrderHeuristic> heuristics = new ArrayList<>();
		
		for (int i=0; i < args.length ; i++) {
			if (INPUT_FILE.equals(args[i])) {
				inputff = args[++i];
			} else if (INPUT_TYPE.equals(args[i])) {
				inputType = args[++i]; 
			} else if (ORDER_FLAG.equals(args[i])) {
				String orders = args[++i];
				for (String o : orders.split(",")) {
					heuristics.add(OrderHeuristic.valueOf(o));
				}
			} else if (GSPN_PATH.equals(args[i])) {
				gspnpath = args[++i];
			}
		}
		
		if (inputff == null) {
			System.err.println("Please provide input file with -i option");
			return null;
		}
		
		File ff = new File(inputff);
		if (! ff.exists()) {
			System.err.println("Input file "+inputff +" does not exist");
			return null;
		}
		String pwd = ff.getParent();
		String modelName = ff.getName().replace(".pnml", "");
		
		
		long time = System.currentTimeMillis();
		
		PTNetReader ptreader = new PTNetReader();
		PetriNet ptnet = null; 
		ptnet = ptreader.loadFromXML(new BufferedInputStream(new FileInputStream(ff)));
		

		String path = ff.getCanonicalPath();
		String workFolder = ff.getParent();
		
		String outpath =  workFolder + "/" ;

		Logger.getLogger("fr.lip6.move.gal").info("Running Import pnml on target :" + path + " to output in "+outpath);

		
		
		System.out.println("Successfully read input file : " + inputff +" in " + (time - System.currentTimeMillis()) + " ms.");
		
//		String cwd = pwd + "/work";
//		File fcwd = new File(cwd);
//		if (! fcwd.exists()) {
//			if (! fcwd.mkdir()) {
//				System.err.println("Could not set up work folder in "+cwd);
//			}
//		}				

		time = System.currentTimeMillis();
		
		// Invoke GreatSPN
		
		// produce one output for each heuristic selected
				
		return IApplication.EXIT_OK;
	}
	

	
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
	}
}
