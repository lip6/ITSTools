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
package fr.lip6.move.coloane.projects.its.checks.ui;

import fr.lip6.move.coloane.projects.its.CompositeTypeDeclaration;
import fr.lip6.move.coloane.projects.its.Concept;
import fr.lip6.move.coloane.projects.its.GALTypeDeclaration;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.ITypeListProvider;
import fr.lip6.move.coloane.projects.its.TypeDeclaration;
import fr.lip6.move.coloane.projects.its.TypeList;
import fr.lip6.move.coloane.projects.its.checks.CTLCheckService;
import fr.lip6.move.coloane.projects.its.checks.CTLFormulaDescription;
import fr.lip6.move.coloane.projects.its.checks.CheckService;
import fr.lip6.move.coloane.projects.its.checks.OrderingService;
import fr.lip6.move.coloane.projects.its.checks.ServiceResult;
import fr.lip6.move.coloane.projects.its.expression.VariableBinding;
import fr.lip6.move.coloane.projects.its.order.Group;
import fr.lip6.move.coloane.projects.its.order.Variable;
import fr.lip6.move.coloane.projects.its.ui.forms.ConceptDetailsPage;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;
import fr.lip6.move.coloane.projects.its.ui.forms.OpenEditorAction;
import fr.lip6.move.coloane.projects.its.ui.forms.TypeDeclarationDetailsPage;
import fr.lip6.move.coloane.projects.its.ui.forms.VariableBindingDetailsPage;
import fr.lip6.move.coloane.projects.its.variables.GalArrayVariable;
import fr.lip6.move.coloane.projects.its.variables.GalVariable;
import fr.lip6.move.coloane.projects.its.variables.InstanceVariable;
import fr.lip6.move.coloane.projects.its.variables.PlaceMarkingVariable;
import fr.lip6.move.coloane.projects.its.variables.ScalarInstanceVariable;
import fr.lip6.move.coloane.projects.its.variables.TransitionClockVariable;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * A page to hold a check list in a tree view + hookup to details pages.
 */
public final class ChecksScrolledPropertiesBlock extends MasterDetailsBlock
		implements ITypeListProvider {
	private ChecksMasterDetailsPage page;
	private TreeViewer viewer;

	/**
	 * Ctor.
	 * 
	 * @param page
	 *            parent page
	 */
	public ChecksScrolledPropertiesBlock(ChecksMasterDetailsPage page) {
		this.page = page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {
		// final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| ExpandableComposite.TITLE_BAR);
		section.setText("Analysis"); //$NON-NLS-1$
		section.setDescription("Model being analyzed."); //$NON-NLS-1$
		section.marginWidth = 10;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.heightHint = 20;
		gd.widthHint = 30;

		// Table t = toolkit.createTable(client, SWT.NULL);
		Tree tree = toolkit.createTree(client, SWT.NULL);
		tree.setLayoutData(gd);

		toolkit.paintBordersFor(client);
		Composite buttonZone = toolkit.createComposite(client);
		layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 3;
		buttonZone.setLayout(layout);

		Button breload = toolkit.createButton(buttonZone, "Refresh", SWT.PUSH); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		breload.setLayoutData(gd);
		breload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				page.getTypes().reload();
			}
		});
		breload.setImage(ITSEditorPlugin.getDefault().getImage(
				ITSEditorPlugin.IMG_REFRESH));

		/**
		 * Button b = toolkit.createButton(buttonZone, "Reachability",
		 * SWT.PUSH); //$NON-NLS-1$ gd = new GridData(GridData.FILL_HORIZONTAL);
		 * b.setLayoutData(gd); b.addSelectionListener(new SelectionAdapter() {
		 * 
		 * @Override public void widgetSelected(SelectionEvent event) {
		 *           CheckService cs = new CheckService(page.getCheckList());
		 *           cs.setWorkdir(""); page.getCheckList().addCheck(cs); } });
		 * 
		 *           Button b2 = toolkit.createButton(buttonZone,
		 *           "CTL Properties", SWT.PUSH); //$NON-NLS-1$ gd = new
		 *           GridData(GridData.FILL_HORIZONTAL); b2.setLayoutData(gd);
		 *           b2.addSelectionListener(new SelectionAdapter() {
		 * @Override public void widgetSelected(SelectionEvent event) {
		 *           AbstractCheckService cs = new
		 *           CTLCheckService(page.getCheckList()); cs.setWorkdir("");
		 *           page.getCheckList().addCheck(cs); } });
		 * 
		 *           Button b3 = toolkit.createButton(buttonZone,
		 *           "Variable Ordering", SWT.PUSH); //$NON-NLS-1$ gd = new
		 *           GridData(GridData.FILL_HORIZONTAL); b3.setLayoutData(gd);
		 *           b3.addSelectionListener(new SelectionAdapter() {
		 * @Override public void widgetSelected(SelectionEvent event) {
		 *           AbstractCheckService cs = new
		 *           OrderingService(page.getCheckList()); cs.setWorkdir("");
		 *           page.getCheckList().addCheck(cs); } });
		 */
		//		Button b2 = toolkit.createButton(buttonZone, "Remove a type", SWT.PUSH); //$NON-NLS-1$
		// // gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		// b2.setLayoutData(gd);
		// b2.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent event) {
		// try {
		// TypeDeclaration td = (TypeDeclaration) ((TreeSelection)
		// viewer.getSelection()).getFirstElement();
		// new RemoveTypeAction(page.getMpe().getTypes(), td).run();
		// } catch (ClassCastException e) {
		// System.err.println("Select a type");
		// }
		// }
		// });
		//
		//
		//		Button b3 = toolkit.createButton(buttonZone, "Export to SDD", SWT.PUSH); //$NON-NLS-1$
		// // gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		// b3.setLayoutData(gd);
		// b3.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent event) {
		// try {
		// TypeDeclaration td = (TypeDeclaration) ((TreeSelection)
		// viewer.getSelection()).getFirstElement();
		// page.getMpe().exportToSDD(td);
		// } catch (ClassCastException e) {
		// System.err.println("Select a type");
		// }
		// }
		// });

		toolkit.paintBordersFor(client);

		viewer = new TreeViewer(tree);
		viewer.setContentProvider(new CheckListTreeProvider());
		viewer.setLabelProvider(new CheckListTreeLabelProvider());
		viewer.setInput(page.getCheckList());

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
		detailsPart.registerPage(CompositeTypeDeclaration.class,
				new TypeDeclarationDetailsPage());
		detailsPart.registerPage(TypeDeclaration.class,
				new TypeDeclarationDetailsPage());
		detailsPart.registerPage(GALTypeDeclaration.class,
				new TypeDeclarationDetailsPage());
		detailsPart.registerPage(Concept.class, new ConceptDetailsPage(this));
		detailsPart.registerPage(VariableBinding.class,
				new VariableBindingDetailsPage());
		// checks
		detailsPart.registerPage(CheckService.class,
				new ITSReachServiceDetailsPage());
		detailsPart.registerPage(CTLCheckService.class,
				new CTLCheckServiceDetailsPage());
		detailsPart.registerPage(ServiceResult.class,
				new ServiceResultDetailsPage());
		detailsPart.registerPage(OrderingService.class,
				new OrderServiceDetailsPage());
		// orders
		detailsPart.registerPage(Group.class, new OrderingDetailsPage());
		detailsPart.registerPage(Variable.class, new OrderingDetailsPage());
		// CTL formula
		detailsPart.registerPage(CTLFormulaDescription.class,
				new CTLFormulaDescriptionDetailsPage());
		// Variables
		detailsPart.registerPage(PlaceMarkingVariable.class,
				new VariableDetailsPage());
		detailsPart.registerPage(GalVariable.class,
				new VariableDetailsPage());
		detailsPart.registerPage(TransitionClockVariable.class,
				new VariableDetailsPage());
		detailsPart.registerPage(InstanceVariable.class,
				new VariableDetailsPage());
		detailsPart.registerPage(ScalarInstanceVariable.class,
				new VariableDetailsPage());
		detailsPart.registerPage(GalArrayVariable.class,
				new VariableDetailsPage());
		
	}

	/**
	 * refresh the view
	 */
	public void refresh() {
		viewer.refresh();
	}

	/**
	 * accessor to get the master page.
	 * 
	 * @return parent page
	 */
	public ChecksMasterDetailsPage getPage() {
		return page;
	}

	public TypeList getTypes() {
		return page.getTypes();
	}
}
