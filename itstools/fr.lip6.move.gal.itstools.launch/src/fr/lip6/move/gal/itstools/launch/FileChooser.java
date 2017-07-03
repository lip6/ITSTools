package fr.lip6.move.gal.itstools.launch;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
 
public class FileChooser extends Composite {
 
	Text mText;
	Button mButton;
	String title = null;
 
	public FileChooser(Composite parent) {
		super(parent, SWT.NULL);

		String[] tab = {"txt"};
		setExtensions(tab);
		
		createContent();
	}
 
	public void createContent() {
		GridLayout layout = new GridLayout(2, false);
		setLayout(layout);
 
		mText = new Text(this, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		mText.setLayoutData(gd);
 
 
		mButton = new Button(this, SWT.NONE);
		mButton.setText("...");
		mButton.addSelectionListener(new SelectionListener() {
 
			public void widgetDefaultSelected(SelectionEvent e) {
			}
 
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(mButton.getShell(),  SWT.OPEN  );
				dlg.setText("Open");
				dlg.setFilterExtensions(getExtensions());
				String path = dlg.open();
				if (path == null) return;
				mText.setText(path);
			}
		});
	}
	
	
	private String[] extensions;
	
	public String[] getExtensions() {
		return extensions;
	}
	
	public void setExtensions(String[] extensions) {
		this.extensions = extensions;
	}
 
	public String getText() {
		return mText.getText();
 
	}
 
	public String getTitle() {
		return title;
	}
 
	public void setTitle(String title) {
		this.title = title;
	}
}