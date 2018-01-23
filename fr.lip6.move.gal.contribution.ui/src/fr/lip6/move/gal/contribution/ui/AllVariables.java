package fr.lip6.move.gal.contribution.ui;

import java.io.IOException;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.contribution.orders.OrderBuilder;

public class AllVariables extends ContributionAction {


	@Override
	protected String getServiceName() {		
		return "Contribution";
	}

	@Override
	protected void workWithSystem(Specification s) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getAdditionalExtension() {
		return "";
	}

	@Override
	public void workOnSpec(Specification s, String outpath) throws IOException {
		OrderBuilder ob = new OrderBuilder();
		List<String> order = ob.buildOrder(s);
		ob.printOrder(outpath, order);
	}

}
