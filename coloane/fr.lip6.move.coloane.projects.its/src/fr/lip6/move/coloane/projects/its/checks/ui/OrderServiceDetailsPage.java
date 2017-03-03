package fr.lip6.move.coloane.projects.its.checks.ui;

import org.eclipse.swt.widgets.Composite;

public class OrderServiceDetailsPage extends CheckServiceDetailsPage {

	/**
	 * {@inheritDoc} (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createContents(Composite parent) {
		createRunButton(parent);
		super.createContents(parent);

		parent.pack();
	}
	
	
}
