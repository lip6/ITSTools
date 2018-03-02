package fr.lip6.move.gal.greatspn.order.ajout.OrderGenerator;

import fr.lip6.move.gal.greatspn.order.GreatSPNRunner;
import fr.lip6.move.gal.greatspn.order.ajout.flag.OrderHeuristic;
import fr.lip6.move.gal.greatspn.order.ajout.order.IOrder;
import fr.lip6.move.gal.greatspn.order.ajout.order.Order;
import fr.lip6.move.gal.greatspn.order.ajout.order.OrderFactory;
import fr.lip6.move.gal.semantics.INextBuilder;

public class GreatSPNOrderGen implements IOrderGenerator {

	private GreatSPNRunner runner;
	private INextBuilder nb;
	String workFolder, modelPath;
	
	public GreatSPNOrderGen(String workFolder, String modelPath, OrderHeuristic o) {
		super();
		runner= new GreatSPNRunner(workFolder,modelPath);
		runner.configure(o);
	}
	public GreatSPNOrderGen(String workFolder, String modelPath) {
		super();
		runner= new GreatSPNRunner(workFolder,modelPath);
		
	}
	@Override
	public void configure(INextBuilder n) {
		// TODO Auto-generated method stub
		nb=n;
	}

	@Override
	public IOrder compute() {
		// TODO Auto-generated method stub
		runner.run();
		return  OrderFactory.create(nb.getVariableNames(),runner.getOrder());
		
	}

}
