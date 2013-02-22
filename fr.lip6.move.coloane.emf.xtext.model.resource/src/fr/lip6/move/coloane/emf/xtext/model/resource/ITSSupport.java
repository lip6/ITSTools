package fr.lip6.move.coloane.emf.xtext.model.resource;

import org.eclipse.xtext.resource.generic.AbstractGenericResourceSupport;

import com.google.inject.Module;

public class ITSSupport extends AbstractGenericResourceSupport {

	@Override
	protected Module createGuiceModule() {
		return new ITSRuntimeModule();
	}

}
