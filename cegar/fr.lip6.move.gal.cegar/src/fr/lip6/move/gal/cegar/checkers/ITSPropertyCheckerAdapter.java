package fr.lip6.move.gal.cegar.checkers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import fr.lip6.move.gal.False;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.cegar.interfaces.IPropertyChecker;
import fr.lip6.move.gal.cegar.interfaces.IResult;
import fr.lip6.move.serialization.SerializationUtil;

public class ITSPropertyCheckerAdapter implements IPropertyChecker {

	private ITSLauncher launcher;

	public ITSPropertyCheckerAdapter(ITSLauncher launcher) {
		this.launcher = launcher;
	}

	@Override
	public IResult check(Specification gal, Property property) {

		// diagnose constant cases
		if (property.getBody() instanceof ReachableProp) {
			if (property.getBody().getPredicate() instanceof True) {
				return new CheckResult(true, true, new String[0]);
			} else if (property.getBody().getPredicate() instanceof False) {
				return new CheckResult(false, false, null);				
			}
		} else if (property.getBody() instanceof NeverProp) {
			if (property.getBody().getPredicate() instanceof True) {
				return new CheckResult(false, true, new String[0]);
			} else if (property.getBody().getPredicate() instanceof False) {
				return new CheckResult(true, false, null);				
			}
		} else if (property.getBody() instanceof InvariantProp) {
			if (property.getBody().getPredicate() instanceof True) {
				return new CheckResult(true, false, null);
			} else if (property.getBody().getPredicate() instanceof False) {
				return new CheckResult(false, true, new String[0]);				
			}
		}

		
		// get trace info and pass it as argument to ITS trace checker
		BufferedReader in = null;
		String results[] = new String[0];
		
		try {
			launcher.setModel(gal);
			launcher.setTrace("");
			// diagnose
			if (property.getBody() instanceof ReachableProp) {
				// could not find trace to state satisfying
				launcher.setProperty(SerializationUtil.getText(property.getBody().getPredicate(),true));
			} else if (property.getBody() instanceof NeverProp) {
				// could not find trace to state satisfying
				launcher.setProperty("!(" +SerializationUtil.getText(property.getBody().getPredicate(),true)+")");
			} else if (property.getBody() instanceof InvariantProp) {
				// could not find counter example trace
				launcher.setProperty("!(" +SerializationUtil.getText(property.getBody().getPredicate(),true)+")");
			}


			launcher.run();
			

			in = new BufferedReader(launcher.getResult());
			String line;
			while ((line = in.readLine()) != null) {
				 getLog().finest("read : " + line);
				 if (line.contains("This shortest transition sequence")) {
					 line = in.readLine();
					 results = line.split(", ");
					 // we got our results, stop parsing its-reach output
					 return new CheckResult(true, true, results);
				 }
				 
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// free resources
			if (in != null) { 
				try {
					in.close();
				} catch (IOException e) {
					getLog().warning("Problem while closing trace file"+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return new CheckResult(false, false, null);
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

}
