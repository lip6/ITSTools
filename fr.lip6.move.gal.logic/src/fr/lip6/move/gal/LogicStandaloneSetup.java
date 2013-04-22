
package fr.lip6.move.gal;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class LogicStandaloneSetup extends LogicStandaloneSetupGenerated{

	public static void doSetup() {
		new LogicStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

