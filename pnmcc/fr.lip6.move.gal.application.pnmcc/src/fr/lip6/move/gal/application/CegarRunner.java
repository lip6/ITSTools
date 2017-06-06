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

public class CegarRunner extends AbstractRunner implements IRunner {
	
	private String pwd;

	public CegarRunner(String pwd) {
		this.pwd = pwd;
	}

	public static Thread runCegar(final Specification spec, final String pwd, final Ender ender) {

		Thread cegarRunner = new Thread(new Runnable() {

			@Override
			public void run() {
				// current implem cannot deal with arrays
				// degeneralize, should be ok for Petri nets at least
				GALRewriter.flatten(spec, true);
				CompositeBuilder cb = CompositeBuilder.getInstance();
				cb.rewriteArraysAsVariables(spec);
				Simplifier.simplify(spec);

				final List<Property> properties = new ArrayList<Property>(spec.getProperties());
				for (Property prop : properties) {
					spec.getProperties().clear();
					spec.getProperties().add(prop);
					try {
						IResult res = CegarFrontEnd.processGal(spec, pwd);
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

	@Override
	public void solve(Ender ender ) {
		runnerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// current implem cannot deal with arrays
				// degeneralize, should be ok for Petri nets at least
				GALRewriter.flatten(spec, true);
				CompositeBuilder cb = CompositeBuilder.getInstance();
				cb.rewriteArraysAsVariables(spec);
				Simplifier.simplify(spec);

				final List<Property> properties = new ArrayList<Property>(spec.getProperties());
				for (Property prop : properties) {
					if (! doneProps.contains( prop.getName())) {
						spec.getProperties().clear();
						spec.getProperties().add(prop);
						try {
							IResult res = CegarFrontEnd.processGal(spec, pwd);
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
				}
				for (Property prop : properties) {
					if (! doneProps.contains(prop.getName())) {
						// still some work to do
						return;
					}
				}
				ender.killAll();
				
			}
		});
		runnerThread.start();
	}
	
	
}
