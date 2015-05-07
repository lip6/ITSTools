package fr.lip6.move.gal.gal2smt.popup;

import java.util.List;

import org.smtlib.ICommand;

import fr.lip6.move.gal.Property;

public interface IGalToSMTAction{
	public List<ICommand> getCommandList();	
	public void setPropertiesList(List<Property> properties);	
}
