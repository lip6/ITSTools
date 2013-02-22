/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package fr.lip6.move.coloane.emf.xtext.model.resource.ui;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

public class ItsEditorOpener extends LanguageSpecificURIEditorOpener {
	
	@Override
	protected void selectAndReveal(IEditorPart openEditor, URI uri,
			EReference crossReference, int indexInList, boolean select) {
//		UMLEditor umlEditor = (UMLEditor) openEditor.getAdapter(UMLEditor.class);
//		if (umlEditor != null) {
//			EObject eObject = umlEditor.getEditingDomain().getResourceSet().getEObject(uri, true);
//			umlEditor.setSelectionToViewer(Collections.singletonList(eObject));
//		}
	}

}
