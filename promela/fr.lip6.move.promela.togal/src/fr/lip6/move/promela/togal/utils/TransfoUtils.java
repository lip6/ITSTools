package fr.lip6.move.promela.togal.utils;

import org.eclipse.emf.ecore.EObject;

public class TransfoUtils {
	// Note: package dedié les as rendu visible partout
	public static UnsupportedOperationException unsupported(EObject objet) {
		return new UnsupportedOperationException("La Classe "
				+ objet.getClass().getCanonicalName()
				+ " n'est actuellement pas supportée");
	}

	public static UnsupportedOperationException unsupported(EObject objet,
			String message) {
		return new UnsupportedOperationException("La Classe "
				+ objet.getClass().getCanonicalName()
				+ " n'est actuellement pas supportée\n" + message);
	}

	public static UnsupportedOperationException unsupported(String message) {
		return new UnsupportedOperationException(message);
	}

	// MAYBE: switch to IllegalArg or InvalidModel..
	public static UnsupportedOperationException illegal(String message) {
		return new UnsupportedOperationException(message);
	}

}
