package fr.lip6.move.gal.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.cegar.frontend.CegarFrontEnd;
import fr.lip6.move.gal.cegar.interfaces.IResult;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.Simplifier;

public class CegarRunner {

	
	public static Thread runCegar(final Specification specNoProp, final String pwd, final Ender ender) {

		Thread cegarRunner = new Thread(new Runnable() {

			@Override
			public void run() {
				// current implem cannot deal with arrays
				// degeneralize, should be ok for Petri nets at least
				GALRewriter.flatten(specNoProp, true);
				CompositeBuilder cb = CompositeBuilder.getInstance();
				cb.rewriteArraysAsVariables(specNoProp);
				Simplifier.simplify(specNoProp);

				final List<Property> properties = new ArrayList<Property>(specNoProp.getProperties());
				for (Property prop : properties) {
					specNoProp.getProperties().clear();
					specNoProp.getProperties().add(prop);
					try {
						IResult res = CegarFrontEnd.processGal(specNoProp, pwd);
						String ress = "FALSE";
						if (res.isPropertyTrue()) {
							ress = "TRUE";
						}

						System.out.println("FORMULA "+prop.getName()+ " "+ ress + " TECHNIQUES DECISION_DIAGRAMS COLLATERAL_PROCESSING TOPOLOGICAL CEGAR ");

					} catch (IOException e) {
						e.printStackTrace();
						getLog().warning("Aborting CEGAR due to an exception");
						return;
					} catch (RuntimeException re) {
						re.printStackTrace();
						getLog().warning("Aborting CEGAR check of property " + prop.getName() + " due to an exception when running procedure.");
					}
				}
				ender.killAll();
				
			}
		});
		cegarRunner.start();
		return cegarRunner;
	}
	
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
		
	}
}
