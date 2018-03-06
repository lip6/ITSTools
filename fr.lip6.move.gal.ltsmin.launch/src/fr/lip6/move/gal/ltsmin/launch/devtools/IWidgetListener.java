package fr.lip6.move.gal.ltsmin.launch.devtools;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;


/**
 *  For ease of use : a single listener that can be attached to various sorts of controls.
 *  Mostly all our listeners do the same thing, i.e. notify the framework something has happened. 
 */
public interface IWidgetListener extends ModifyListener, SelectionListener {
}
