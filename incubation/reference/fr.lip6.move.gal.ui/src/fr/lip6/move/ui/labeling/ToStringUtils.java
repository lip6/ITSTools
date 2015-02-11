package fr.lip6.move.ui.labeling;

import org.eclipse.jface.viewers.ILabelProvider;

import fr.lip6.move.ui.internal.GalActivator;

public class ToStringUtils {
	
	public static String getTextString (Object o) {		
		//Guice.createInjector(new GalUiModule(plugin)).getInstance(ILabelProvider.class)
		return GalActivator.getInstance().getInjector(GalActivator.FR_LIP6_MOVE_GAL).getInstance(ILabelProvider.class).getText(o);
	}
}

