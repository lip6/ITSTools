/**
 * Copyright (c) 2006-2010 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Baptiste VORON (LIP6) - Project Head / Initial contributor
 *   Clément DÉMOULINS (LIP6) - Project Manager
 *   Yann THIERRY-MIEG (LIP6)
 *
 * Official contacts:
 *   coloane@lip6.fr
 *   http://coloane.lip6.fr
 */
package fr.lip6.move.coloane.projects.its.plugin.editors;


import fr.lip6.move.coloane.core.main.Coloane;
import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.ITypeListProvider;
import fr.lip6.move.coloane.projects.its.TypeDeclaration;
import fr.lip6.move.coloane.projects.its.TypeList;
import fr.lip6.move.coloane.projects.its.actions.AddTypeAction;
import fr.lip6.move.coloane.projects.its.actions.FlattenModelAction;
import fr.lip6.move.coloane.projects.its.checks.CheckList;
import fr.lip6.move.coloane.projects.its.checks.ui.ChecksMasterDetailsPage;
import fr.lip6.move.coloane.projects.its.io.model.ITSModelWriter;
import fr.lip6.move.coloane.projects.its.obs.ISimpleObserver;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;
import fr.lip6.move.coloane.projects.its.ui.forms.MasterDetailsPage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The main editor for .xmlits ITS model descriptions.
 * Features advanced content assist and enhanced user experience with dedicated details pages.
 */
public final class MultiPageEditor
extends FormEditor
implements IResourceChangeListener, ISimpleObserver, ITypeListProvider, IPageChangedListener {

	/** The text editor used for control/debug. */
	private TextEditor editor;

	/** The main model object root of this editor */
	private TypeList types = null;

	private boolean isDirty = false;

	/** singleton for add type wizard */
	private AddTypeAction addAction = null;

	/** The main view */
	private MasterDetailsPage treePage;

	/** The checks pages */
	private List<ChecksMasterDetailsPage> checkPages = new LinkedList<ChecksMasterDetailsPage>();

	private Map<ITypeDeclaration, Integer> checkPagesIndex = new HashMap<ITypeDeclaration, Integer>();

	private FlattenModelAction flattenAction;

	/**
	 * Creates the editor.
	 */
	public MultiPageEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		addPageChangedListener(this);
	}

	/** 
	 * Handle setting of types + registering of observer.
	 * @param types the types to set as input.
	 */
	public void setTypes(TypeList types) {
		if (types != null) {
			types.deleteObserver(this);
		}
		this.types = types;
		types.addObserver(this);
	}
	/**
	 * Creates the textEditor page,
	 * which contains a text editor.
	 */
	void createPageTextEditor() {
		try {
			editor = new TextEditor();
			int index = addPage(editor, getEditorInput());
			setPageText(index, editor.getTitle());
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating nested text editor",
					null,
					e.getStatus());
		}
	}




	/**
	 * Export a given type to SDD XML format : starts a small directory chooser dialog.
	 * @param td the type to export.
	 */
	public void exportToSDD(TypeDeclaration td) {
		ITSModelWriter mw = new ITSModelWriter();
			try {
				DirectoryDialog dialog = new DirectoryDialog(getSite().getShell());
				String directory = dialog.open();
				mw.exportITSModel(types, td, directory);
			} catch (ExtensionException e) {
				e.printStackTrace();
			}

	}

	/**
	 * Return the unique add action associated to this editor.
	 * {@link AddTypeAction}
	 * @return the add action.
	 */
	public AddTypeAction getAddAction() {
		if (addAction == null) {
			addAction = new AddTypeAction(this);
		}
		return addAction;
	}

	/**
	 * Return the unique flatten action associated to this editor.
	 * {@link AddTypeAction}
	 * @return the add action.
	 */
	public FlattenModelAction getFlattenAction() {
		if (flattenAction == null) {
			flattenAction = new FlattenModelAction();
			IFile path =  ((FileEditorInput) getEditorInput()).getFile() ;
			flattenAction.setPath( path );
		}
		return flattenAction;
	}



	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	/**
	 * Saves the multi-page editor's document.
	 * {@inheritDoc}
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			String xmlStr = fr.lip6.move.coloane.projects.its.io.ModelWriter.translateToXML(types);
			InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
			((FileEditorInput) getEditorInput()).getFile().setContents(is, false, false, null);
		} catch (CoreException e) {
			Coloane.showWarningMsg("Could not save file ! " + e.getMessage());
		}
		setDirty(false);
	}
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	@Override
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
		setDirty(false);
	}
	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 * {@inheritDoc}
	 */
	@Override
	public void init(IEditorSite site, IEditorInput editorInput)
	throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput)) {
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		}
		super.init(site, editorInput);
		setPartName(editorInput.getName());
	}
	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}
	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (IWorkbenchPage page : pages) {
						if (((FileEditorInput) editor.getEditorInput()).getFile().getProject().equals(event.getResource()))
						{
							IEditorPart editorPart = page.findEditor(editor.getEditorInput());
							page.closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}
	/**
	 * Returns the types that is central model of this editor.
	 * @return the current type list
	 */
	public TypeList getTypes() {
		return types;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDirty() {
		return editor.isDirty() || isDirty;
	}

	/**
	 * set state to dirty (should save)
	 * @param isDirty the state
	 */
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
		refresh();
	}
	/**
	 * {@inheritDoc}
	 */
	public void update() {
		setDirty(true);
	}

	/**
	 * {@inheritDoc}
	 */
	public void refresh() {
		if (treePage != null) {
			if (treePage.getPartControl() != null) {
				treePage.getPartControl().redraw();
				treePage.refresh();
			}
			for (ChecksMasterDetailsPage p : checkPages) {
				p.refresh();
			}
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void addPages() {
		TypeList tmptypes = fr.lip6.move.coloane.projects.its.io.ModelLoader.loadFromXML(((FileEditorInput) getEditorInput()).getFile().getLocationURI());
		if (tmptypes == null) {
			tmptypes = new TypeList(((FileEditorInput) getEditorInput()).getFile().getLocationURI());
		}
		setTypes(tmptypes);
		try {
			treePage = new MasterDetailsPage(this, this);
			addPage(treePage);
			for (CheckList cl : types.getChecks()) {
				addCheckPage(cl);
				cl.addObserver(this);
			}

		} catch (PartInitException e) {
			e.printStackTrace();
		}
		createPageTextEditor();
	}

	/**
	 * Add a new "checks" page for a type declaration.
	 * Tests are performed to only create one check page per main type.
	 * @param td the main type to analyze.
	 */
	public void createCheckPage(ITypeDeclaration td) {
		if (td != null) {
			if (!checkPagesIndex .containsKey(td)) {
				CheckList cl = new CheckList(td);
				cl.addObserver(this);
				types.addCheckList(cl);
				addCheckPage(cl);
			} else {
				setActivePage(checkPagesIndex.get(td));
			}
		}
	}

	/**
	 * Add a new "checks" page for a given CheckList.
	 * Tests are performed to only create one check page per main type.
	 * @param cl the checklist model object
	 */
	private void addCheckPage(CheckList cl) {
		try {
			int pageIndex;
			if (!checkPagesIndex .containsKey(cl.getType())) {
				ChecksMasterDetailsPage newPage = new ChecksMasterDetailsPage(this, this, cl);
				pageIndex = addPage(newPage);
				this.checkPages.add(newPage);
				checkPagesIndex.put(cl.getType(), pageIndex);
			} else {
				pageIndex = checkPagesIndex.get(cl.getType());
			}
			setActivePage(pageIndex);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}


	public void pageChanged(PageChangedEvent event) {
		if (event.getSource()==this) {
			ITSEditorPlugin a = ITSEditorPlugin.getDefault();
			if (a != null)
				a.setCurrentModel(getTypes());			
		}
		
	}

}

