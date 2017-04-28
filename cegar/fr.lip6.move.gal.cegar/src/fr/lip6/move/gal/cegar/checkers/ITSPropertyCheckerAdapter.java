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
import fr.lip6.move.gal.SafetyProp;
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

		if (property.getBody() instanceof SafetyProp) {
			SafetyProp sbody = (SafetyProp) property.getBody();

		// diagnose constant cases
		if (sbody instanceof ReachableProp) {
			if (sbody.getPredicate() instanceof True) {
				return new CheckResult(true, true, new String[0]);
			} else if (sbody.getPredicate() instanceof False) {
				return new CheckResult(false, false, null);				
			}
		} else if (sbody instanceof NeverProp) {
			if (sbody.getPredicate() instanceof True) {
				return new CheckResult(false, true, new String[0]);
			} else if (sbody.getPredicate() instanceof False) {
				return new CheckResult(true, false, null);				
			}
		} else if (sbody instanceof InvariantProp) {
			if (sbody.getPredicate() instanceof True) {
				return new CheckResult(true, false, null);
			} else if (sbody.getPredicate() instanceof False) {
				return new CheckResult(false, true, new String[0]);				
			}
		}

		
		// get trace info and pass it as argument to ITS trace checker
		BufferedReader in = null;
		String results[] = new String[0];
		
		launcher.setModel(gal);
		launcher.setTrace("");
		// diagnose
		if (sbody instanceof ReachableProp) {
			// could not find trace to state satisfying
			launcher.setProperty(SerializationUtil.getText(sbody.getPredicate(),true));
		} else if (sbody instanceof NeverProp) {
			// could not find trace to state satisfying
			launcher.setProperty("!(" +SerializationUtil.getText(sbody.getPredicate(),true)+")");
		} else if (sbody instanceof InvariantProp) {
			// could not find counter example trace
			launcher.setProperty("!(" +SerializationUtil.getText(sbody.getPredicate(),true)+")");
		}


		IStatus status = launcher.run();

		if (! status.isOK()) {
			throw new RuntimeException("ITS tools raised an exception when treating model property " + property.getName());
		}


		try {
			in = new BufferedReader(launcher.getResult());
			String line;
			while ((line = in.readLine()) != null) {
				getLog().fine("read : " + line);				
				if (line.contains("This shortest transition sequence")) {
					line = in.readLine();
					results = line.split(", ");

					// we got our results, stop parsing its-reach output
					in.close();
					return new CheckResult(true, true, results);
				}
			}
			in.close();
			// Not feasible !			
			return new CheckResult(false, false, null);
		} catch (IOException ie) {
			throw new RuntimeException("CEGAR procedure failed for "+ property.getName() +" could not parse trace of its-tools.");
		}
		} else {
			String msg = "Only safety properties are handled in CEGAR solution currently. Cannot handle " + property.getName();
			Logger.getLogger("fr.lip6.move.gal").warning(msg);
			throw new RuntimeException(msg);
		}
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

}
