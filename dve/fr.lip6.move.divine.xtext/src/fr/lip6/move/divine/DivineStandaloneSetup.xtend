/*
 * generated by Xtext 2.11.0
 */
package fr.lip6.move.divine


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class DivineStandaloneSetup extends DivineStandaloneSetupGenerated {

	def static void doSetup() {
		new DivineStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
