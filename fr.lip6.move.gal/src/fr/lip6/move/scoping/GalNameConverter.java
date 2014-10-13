package fr.lip6.move.scoping;

import java.util.Collections;

import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

public class GalNameConverter implements IQualifiedNameConverter {

	@Override
	public String toString(QualifiedName name) {		
		return name.toString(".");
	}

	@Override
	public QualifiedName toQualifiedName(String qualifiedNameAsText) {
		if (qualifiedNameAsText == null)
			qualifiedNameAsText = "";
		return QualifiedName.create(Collections.singletonList(qualifiedNameAsText));
	}


	
}
