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

import fr.lip6.move.coloane.projects.its.checks.CTLFormulaDescription;
import fr.lip6.move.coloane.projects.its.checks.ui.controls.CTLText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

public class CTLSection {

	private FormToolkit toolkit;
	private Composite parent;
	private CTLFormulaDescription input;
	private Section section;

	private Text nameField;
	private Text commentsField;
	private CTLText ctlField;
	private Section helpSection;

	public CTLSection(final FormToolkit formToolkit, Composite parent) {
		toolkit = formToolkit;
		this.parent = parent;
	}

	public CTLFormulaDescription getInput() {
		return input;
	}

	public void setInput(CTLFormulaDescription input) {
		if (input != this.input) {
			this.input = input;
			createDetails(parent);
		}
		update();
	}

	private void createDetails(Composite parent2) {

		if (section != null) {
			return;
//			try  {
//				section.dispose();
//				section = null;
//				helpSection.dispose();
//				helpSection = null;
//			} catch (SWTException e) {
//				// might happen that the widgets are already disposed. This is not really a problem.
//			}
		}
		{
			section = toolkit.createSection(parent,
					ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE
							| ExpandableComposite.EXPANDED);
			section.marginWidth = 4;
			section.marginHeight = 4;
			section.setText("CTL Formula description"); //$NON-NLS-1$
			TableWrapData td = new TableWrapData(TableWrapData.FILL,
					TableWrapData.TOP);
			td.grabHorizontal = true;
			section.setLayoutData(td);
			Composite client = toolkit.createComposite(section);
			GridLayout glayout = new GridLayout();
			glayout.marginWidth = 10;
			glayout.marginHeight = 5;
			glayout.numColumns = 2;
			client.setLayout(glayout);

			// settings for text fields
			GridData gd;
			gd = new GridData(GridData.FILL_HORIZONTAL
					| GridData.VERTICAL_ALIGN_BEGINNING);
			gd.widthHint = 10;

			toolkit.createLabel(client, "Formula Title");
			nameField = toolkit.createText(client, "");
			nameField.setLayoutData(gd);
			nameField.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					if (getInput() != null) {
						getInput().setName(nameField.getText());
					}
				}
			});

			toolkit.createLabel(client, "Formula Description");
			commentsField = toolkit.createText(client, "", SWT.MULTI);
			GridData gdhigh = new GridData(GridData.FILL_HORIZONTAL
					| GridData.VERTICAL_ALIGN_BEGINNING);
			gdhigh.widthHint = 10;
			gdhigh.heightHint = 50;
			commentsField.setLayoutData(gdhigh);
			commentsField.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					if (getInput() != null) {
						getInput().setComments(commentsField.getText());
					}
				}
			});

			toolkit.createLabel(client, "CTL formula"); //$NON-NLS-1$

			gd.heightHint = 25;
			ctlField = new CTLText(client, gd);

			Button runb = toolkit.createButton(client, "Run check", SWT.PUSH);
			gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING
					| GridData.HORIZONTAL_ALIGN_END);
			runb.setLayoutData(gd);
			runb.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					getInput().getParent().run(
							getInput().getCtlFormula().toString() + ";", getInput());
				}
			});

			toolkit.paintBordersFor(section);
			toolkit.paintBordersFor(client);
			section.setClient(client);
		}
		{
			helpSection = toolkit.createSection(parent,
					ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE
							| ExpandableComposite.COMPACT);
			helpSection.marginWidth = 4;
			helpSection.marginHeight = 4;
			helpSection.setText("CTL syntax help"); //$NON-NLS-1$
			//		s1.setDescription(Messages.getString("TypeOneDetailsPage.name")); //$NON-NLS-1$
			TableWrapData td = new TableWrapData(TableWrapData.FILL,
					TableWrapData.TOP);
			td.grabHorizontal = true;
			helpSection.setLayoutData(td);
			Composite client = toolkit.createComposite(helpSection);
			GridLayout glayout = new GridLayout();
			glayout.marginWidth = 10;
			glayout.marginHeight = 5;
			glayout.numColumns = 1;
			client.setLayout(glayout);

			//			toolkit.createLabel(client, "CTL syntax"); //$NON-NLS-1$

			Browser helpField = new Browser(client, SWT.NONE);
			helpField.setText(getHelpText());

			GridData gd;
			gd = new GridData(GridData.FILL_BOTH
					| GridData.VERTICAL_ALIGN_BEGINNING);
			gd.widthHint = 10;
			gd.heightHint = 200;
			helpField.setLayoutData(gd);

			toolkit.paintBordersFor(helpSection);
			toolkit.paintBordersFor(client);
			helpSection.setClient(client);
		}

		parent.pack();
	}

	public void update() {
		if (input != null) {
			nameField.setText(input.getName());
			ctlField.setInput(input.getCtlFormula());
			commentsField.setText(input.getComments());
		}
	}

	private String getHelpText() {
		return "<strong>Computation Tree Logic (CTL)</strong> <br>"
				+ "Some syntax examples <br>"
				+ "AF (procs.0.cs=1);<br>"
				+ "DEADLOCK;<br><br>"
				+ "Note that there is syntax help built into the text field for the formula, hover with the mouse over your formula to see syntax check result.<br>"
				+ "DEADLOCK;  is a special formula to ask for deadlock states. <br>" 
				+ "<blockquote>If f and g are CTL formulas, then so are the following:<br></blockquote>"
				+ "<pre>	(f), f * g, f + g, f ^ g, !f, f -&gt; g, f &lt;-&gt; g, AG f,<br>	AF f, AX f, EG f, EF f, EX f, A(f U g) and E(f U g).<br></pre>"
				+ "<br>The symbols have the following meanings.<br>"
				+ "<pre>	* -- AND, + -- OR, ^ -- XOR, ! -- NOT, -&gt; -- IMPLY, &lt;-&gt; -- EQUIV</pre>"
				+ "<br>The syntax for atomic propositions is :"
				+ "<ul>" 
				+ "<li>	TRUE,  FALSE are the usual Boolean constants</li>"
				+ "<li><em>var-name=value</em> where  <em>var-name</em>  is  the  fully qualified name of a variable (unfold the model at the top of the tree view to the left to display them),  and <em>value</em> is an integer.</li>"
				+ "</ul>"
				+ "<br>An entire formula should be followed by a semicolon."
				+ "All text from <tt>#</tt> to the end of a line is treated as a comment. <br>"
				+ "<br>Binary operators must  be surrounded by  spaces, i.e.  <tt>f + g</tt> is a CTL formula while<tt> f+g</tt> is  not. The  same is true  for <tt>U</tt> in until formulas. Once  parentheses are inserted, the spaces  can be omitted, i.e. (f)+(g) is a valid formula. "
				+ "<br>Unary  temporal  operators  and  their arguments must be separated by spaces unless parentheses are used."				
				+ "<br><em>For more examples have a look at those provided in \"New->Example->Coloane Example\" (available if you have installed the Examples plugin).</em><br>"				
				+ "<blockquote><u>Operator Precedence for CTL:</u><br><br>High<br></blockquote><pre>    	!<br><br>    	AG, AF, AX, EG, EF, EX<br><br>    	*<br><br>    	+<br><br>    	^<br><br>    	&lt;-&gt;<br><br>    	-&gt;<br><br>    	U<br><br>      Low<br>&gt;</pre>"
				+ "For the semantics of CTL and more information on the language, you might visit the <a href=\"http://en.wikipedia.org/wiki/Computation_tree_logic\">wikipedia page on CTL</a>.<br>";
	}

}
