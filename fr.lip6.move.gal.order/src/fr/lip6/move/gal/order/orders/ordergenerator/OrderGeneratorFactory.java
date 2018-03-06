package fr.lip6.move.gal.order.orders.OrderGeneratorFactory;

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

