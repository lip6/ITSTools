
package fr.lip6.move;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class GALStandaloneSetup extends GALStandaloneSetupGenerated{

	public static void doSetup() {
		new GALStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

