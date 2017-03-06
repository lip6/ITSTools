
package fr.lip6.move;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class GalStandaloneSetup extends GalStandaloneSetupGenerated{

	public static void doSetup() {
		new GalStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

