
package fr.move.lip6;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class GalStandaloneSetup extends GalStandaloneSetupGenerated{

	public static void doSetup() {
		new GalStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

