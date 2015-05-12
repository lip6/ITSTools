package fr.lip6.move.gal.cegar.popup.actions;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.cegar.frontend.CegarFrontEnd;
import fr.lip6.move.gal.flatten.popup.actions.GalAction;

public class CegarAction extends GalAction {

	@Override
	protected void workWithSystem(final Specification spec) throws IOException {		
		
		Job job = new Job("CEGAR") {
			protected IStatus run(IProgressMonitor monitor) { 
				monitor.beginTask("Job started...", 10); 
				try {
					CegarFrontEnd.processGal(spec, getWorkFolder());
				} catch (IOException e) {
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}

				monitor.done(); 
				return Status.OK_STATUS; 
			} 
		}; 
		job.setUser(true);
		job.schedule();
	}

	@Override
	protected String getAdditionalExtension() {
		return ".cegar";
	}

	@Override
	protected String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

}
