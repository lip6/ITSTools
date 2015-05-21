package fr.lip6.move.gal.cegar.checkers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;

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


		IStatus status = launcher.run();

		if (! status.isOK()) {
			throw new RuntimeException("ITS tools raised an exception when treating model property " + property.getName());
		}


		try {
			in = new BufferedReader(launcher.getResult());
			String line;
			while ((line = in.readLine()) != null) {
				getLog().finest("read : " + line);
				if (line.contains("This shortest transition sequence")) {
					line = in.readLine();
					results = line.split(", ");

					// we got our results, stop parsing its-reach output
					in.close();
					return new CheckResult(true, true, results);
				}
			}
			in.close();
			return null;
		} catch (IOException ie) {
			throw new RuntimeException("CEGAR procedure failed for "+ property.getName() +" could not parse trace of its-tools.");
		}
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

}
