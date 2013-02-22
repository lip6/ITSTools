
package fr.lip6.move.coloane.itsdsl;



/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class ItsDslStandaloneSetup extends ItsDslStandaloneSetupGenerated{

	public static void doSetup() {
		new ItsDslStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

