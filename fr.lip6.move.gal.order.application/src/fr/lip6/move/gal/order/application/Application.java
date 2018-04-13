package fr.lip6.move.gal.order.application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import fr.lip6.move.gal.nupn.PTNetReader;
import fr.lip6.move.gal.order.flag.OrderHeuristic;
import fr.lip6.move.gal.order.ordergenerator.IOrderGenerator;
import fr.lip6.move.gal.order.ordergenerator.OrderGeneratorFactory;
import fr.lip6.move.gal.pnml.togreatspn.PNMLToGreatSPN;
import fr.lip6.move.pnml.ptnet.PetriNet;
import fr.lip6.move.gal.order.order.IOrder;
import fr.lip6.move.gal.order.PTGALTransformer;;

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
		String name=new File(pwd).getName();
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
		time = System.currentTimeMillis();
		PNMLToGreatSPN p2gspn = new PNMLToGreatSPN();
		p2gspn .transform(ptnet, path);
		List<IOrderGenerator> orderGens = OrderGeneratorFactory.build(heuristics, workFolder, path,gspnpath,p2gspn.getInitialOrder(), name);
		List<IOrder> orders = new ArrayList<>();
		for(IOrderGenerator og : orderGens)
			orders.add(og.compute());
		System.out.println("3");
		for(IOrder o : orders)
			o.printOrder(workFolder);
		System.out.println("4");
		return IApplication.EXIT_OK;
	}
	

	
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
	}
}
