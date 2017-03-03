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
package fr.lip6.move.coloane.projects.its.ui.forms;

import fr.lip6.move.coloane.projects.its.CompositeTypeDeclaration;
import fr.lip6.move.coloane.projects.its.Concept;
import fr.lip6.move.coloane.projects.its.GALTypeDeclaration;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.ITypeListProvider;
import fr.lip6.move.coloane.projects.its.TypeDeclaration;
import fr.lip6.move.coloane.projects.its.TypeList;
import fr.lip6.move.coloane.projects.its.actions.AddTypeAction;
import fr.lip6.move.coloane.projects.its.actions.FlattenModelAction;
import fr.lip6.move.coloane.projects.its.actions.RemoveTypeAction;
import fr.lip6.move.coloane.projects.its.expression.VariableBinding;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ResourceTransfer;
/**
 * A page to hold a type list in a tree view + hookup to details pages.
 */
public final class ScrolledPropertiesBlock extends MasterDetailsBlock implements ITypeListProvider {
	private MasterDetailsPage page;
	private TreeViewer viewer;
	/**
	 * Ctor.
	 * @param page parent page
	 */
	public ScrolledPropertiesBlock(MasterDetailsPage page) {
		this.page = page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {
		//final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION | ExpandableComposite.TITLE_BAR);
		section.setText("Type Declarations"); //$NON-NLS-1$
		section
		.setDescription("Models imported into the ITS referential."); //$NON-NLS-1$
		section.marginWidth = 10;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING);
		gd.heightHint = 20;
		gd.widthHint = 30;

		//		Table t = toolkit.createTable(client, SWT.NULL);
		Tree tree = toolkit.createTree(client, SWT.NULL | SWT.BORDER);
		tree.setLayoutData(gd);


//		toolkit.paintBordersFor(client);
//		Composite buttonZone = toolkit.createComposite(client);
//		layout = new GridLayout();
//		layout.numColumns = 1;
//		layout.marginWidth = 2;
//		layout.marginHeight = 2;
//		layout.verticalSpacing = 3;
//		buttonZone.setLayout(layout);
//		
//		Button b3 = toolkit.createButton(buttonZone, "Flatten Model", SWT.PUSH); //$NON-NLS-1$
//		//		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
//		b3.setLayoutData(gd);
//		final FlattenModelAction flat = new FlattenModelAction();
//		b3.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent event) {
//				try {
//					TypeDeclaration td = (TypeDeclaration) ((TreeSelection) viewer.getSelection()).getFirstElement();
//					flat.setTypeDeclaration(td);
//					flat.run();
//				} catch (ClassCastException e) {
//					System.err.println("Select a type");
//				}
//			}
//		});
//
//		Button b4 = toolkit.createButton(buttonZone, "Analysis", SWT.PUSH); //$NON-NLS-1$
//		//		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
//		b4.setLayoutData(gd);
//		b4.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent event) {
//				try {
//					TypeDeclaration td = (TypeDeclaration) ((TreeSelection) viewer.getSelection()).getFirstElement();
//					((MultiPageEditor) page.getEditor()).createCheckPage(td);
//				} catch (ClassCastException e) {
//					System.err.println("Select a type");
//				}
//			}
//		});


		Composite bottomZone = toolkit.createComposite(client);
		layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 3;		
		bottomZone.setLayout(layout);

		
		Button breload = toolkit.createButton(bottomZone, "Refresh", SWT.PUSH); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		breload.setLayoutData(gd);
		breload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				page.getMpe().getTypes().reload();
			}
		});
		breload.setImage(ITSEditorPlugin.getDefault().getImage(ITSEditorPlugin.IMG_REFRESH));

		Button b = toolkit.createButton(bottomZone, "Add a type", SWT.PUSH); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		b.setLayoutData(gd);
		final AddTypeAction add = new AddTypeAction(page);
		b.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				add.run();
			}
		});

		Button b2 = toolkit.createButton(bottomZone, "Remove a type", SWT.PUSH); //$NON-NLS-1$
		//		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		b2.setLayoutData(gd);
		b2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					TypeDeclaration td = (TypeDeclaration) ((TreeSelection) viewer.getSelection()).getFirstElement();
					new RemoveTypeAction(page.getMpe().getTypes(), td).run();
					page.getMpe().getTypes().reload();
				} catch (ClassCastException e) {
					System.err.println("Select a type");
				}
			}
		});

		
		viewer = new TreeViewer(tree);
		viewer.setContentProvider(new TypeListTreeProvider());
		viewer.setLabelProvider(new TypeTreeLabelProvider());
		viewer.setInput(page.getMpe().getTypes());

		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});

		viewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				try {
					TreeSelection o = (TreeSelection) event.getSelection();
					ITypeDeclaration td = (ITypeDeclaration) o.getFirstElement();
					OpenEditorAction.openEditor(td);
				} catch (ClassCastException e) {
					// a concept was double clicked
					return;
				}
			}
		});
		
		MenuManager menuMgr = new MenuManager();

        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        menuMgr.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager manager) {
                // IWorkbench wb = PlatformUI.getWorkbench();
                // IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
                if (viewer.getSelection().isEmpty()) {
                    return;
                }

                if (viewer.getSelection() instanceof TreeSelection) {
                	try {
                	TreeSelection selection = (TreeSelection) viewer.getSelection();
                	TypeDeclaration td = (TypeDeclaration) selection.getFirstElement();
                	FlattenModelAction fma = new FlattenModelAction();
                	fma.setTypeDeclaration(td);
                	IFile path = ((FileEditorInput) getPage().getEditorInput()).getFile();
                	fma.setPath(path.getProject());
					manager.add(fma);
                	} catch (ClassCastException e) {
                		// not the right element for a menu
                	}
                }
            }
        });
        menuMgr.setRemoveAllWhenShown(true);
        viewer.getControl().setMenu(menu);

		int ops = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK | DND.DROP_DEFAULT;
		Transfer[] transfers = new Transfer[] {ResourceTransfer.getInstance(), LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance() };
		TypesViewDropAdapter adapter = new TypesViewDropAdapter(viewer, this);
		viewer.addDropSupport(ops, transfers, adapter);

	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		Action haction = new Action("hor", IAction.AS_RADIO_BUTTON) { //$NON-NLS-1$
			@Override
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(true);
		haction.setToolTipText("Switch to horizontal"); //$NON-NLS-1$
		haction.setImageDescriptor(ITSEditorPlugin.getDefault()
				.getImageRegistry()
				.getDescriptor(ITSEditorPlugin.IMG_HORIZONTAL));
		Action vaction = new Action("ver", IAction.AS_RADIO_BUTTON) { //$NON-NLS-1$
			@Override
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText("Switch to vertical"); //$NON-NLS-1$
		vaction.setImageDescriptor(ITSEditorPlugin.getDefault()
				.getImageRegistry().getDescriptor(ITSEditorPlugin.IMG_VERTICAL));
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(CompositeTypeDeclaration.class, new TypeDeclarationDetailsPage());
		detailsPart.registerPage(TypeDeclaration.class, new TypeDeclarationDetailsPage());
		detailsPart.registerPage(GALTypeDeclaration.class, new TypeDeclarationDetailsPage());
		detailsPart.registerPage(Concept.class, new ConceptDetailsPage(this));
		detailsPart.registerPage(VariableBinding.class, new VariableBindingDetailsPage());
	}
	/**
	 * refresh the view
	 */
	public void refresh() {
		viewer.refresh();
	}
	/**
	 * accessor to get the master page.
	 * @return parent page
	 */
	public MasterDetailsPage getPage() {
		return page;
	}

	public TypeList getTypes() {
		return page.getTypes();
	}
}

