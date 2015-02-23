package fr.lip6.move.gal.flatten.popup.actions;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;


/** Captures Logger outputs, and prints them to a Eclipse ui console */
public class ConsoleAdder extends Handler {

	private MessageConsole mcon;
	private String pluginId;
	private MessageConsoleStream out;

	private static ConsoleAdder listener = null;

	public static void startConsole() {
		if (listener == null) {
	
			listener = new ConsoleAdder("fr.lip6.move.gal");
			Logger.getLogger("fr.lip6.move.gal").addHandler(listener);
		}
	}

	public static void stopconsole() {
		if (listener != null) {
			Logger.getLogger("fr.lip6.move.gal").removeHandler(listener);
			listener = null;
		}
	}

	public ConsoleAdder(String pluginid) {
		this.pluginId = pluginid;
		mcon = findConsole(pluginId);
		out = mcon.newMessageStream();
		out.println("Started Logging "+ pluginId);

		
		// force refresh
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
				IWorkbenchPage page = win.getActivePage();
				IConsoleView view;
				try {
					view = (IConsoleView) page.showView(pluginId);
					view.display(mcon);		
				} catch (PartInitException e) {
					System.err.println("could not force display of console view.");
					e.printStackTrace();
				}				
			}
			
		});
	}

	@Override
	public void close() throws SecurityException {
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SecurityException(e);
		}
		mcon = null;		
	}

	@Override
	public void flush() {
		try {
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(LogRecord record) {
		out.println(record.getLevel() + ":" + record.getMessage());
	}


	private MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		//no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[]{myConsole});
		return myConsole;
	}
}
