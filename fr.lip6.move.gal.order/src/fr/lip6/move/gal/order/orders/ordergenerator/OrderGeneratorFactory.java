package fr.lip6.move.gal.order.orders.ordergenerator;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.order.orders.flag.OrderHeuristic;
import fr.lip6.move.gal.order.orders.ordergeneratorbuilder.GspnRunner;
import fr.lip6.move.gal.semantics.INextBuilder;

public class OrderGeneratorFactory {
	private Specification s;
	private INextBuilder inb;
	GspnRunner gspn;
	
	public OrderGeneratorFactory(Specification s) {
		this.s = s;
		this.inb = INextBuilder.build(s);
		gspn = null;
	}
	
	IOrderGenerator build (OrderHeuristic h) {
		/*
		switch (h) {
		case IDENTITY:
			return new IdentityOrderGenerator(inb);
		case GSPN_X:
		case GSPN_Y:
			//...
			if (gspn == null) gspn = new GspnRunner(inb);
			return gspn.createGenerator(h);
		}
		*/
		
		return null;
	}
}

