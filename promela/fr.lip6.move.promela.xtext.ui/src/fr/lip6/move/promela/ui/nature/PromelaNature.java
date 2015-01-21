package fr.lip6.move.promela.ui.nature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

// Recipe from Eclispe 4 Developpment Book
public class PromelaNature implements IProjectNature {
	public static final String ID = "fr.lip6.move.promela.ui.nature.PromelaNature";
	private IProject project;

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public void configure() throws CoreException {
		// When Builder
//		IProjectDescription desc = project.getDescription();
//		List<ICommand> commands = new ArrayList<ICommand>(Arrays.asList(desc
//				.getBuildSpec()));
//		Iterator<ICommand> iterator = commands.iterator();
//		while (iterator.hasNext()) {
//			ICommand command = iterator.next();
//			if (PromelaBuilder.ID.equals(command.getBuilderName())) {
//				return;
//			}
//		}
//		ICommand newCommand = desc.newCommand();
//		newCommand.setBuilderName(PromelaBuilder.ID);
//		commands.add(newCommand);
//		desc.setBuildSpec(commands.toArray(new ICommand[0]));
//		project.setDescription(desc, null);
	}

	public void deconfigure() throws CoreException {
		// When Builder
		// IProjectDescription desc = project.getDescription();
		// List<ICommand> commands = new ArrayList<ICommand>(Arrays.asList(desc
		// .getBuildSpec()));
		// Iterator<ICommand> iterator = commands.iterator();
		// while (iterator.hasNext()) {
		// ICommand command = iterator.next();
		// if (PromelaBuilder.ID.equals(command.getBuilderName())) {
		// iterator.remove();
		// }
		// }
		// desc.setBuildSpec(commands.toArray(new ICommand[0]));
		// project.setDescription(desc, null);
	}
}
