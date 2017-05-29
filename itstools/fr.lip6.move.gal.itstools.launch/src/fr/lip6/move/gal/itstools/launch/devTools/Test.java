package fr.lip6.move.gal.itstools.launch.devTools;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.Option;

public class Test {

	public static void main(String[] args) {
		AttributeSet a = new SimpleAttributeSet();
		((SimpleAttributeSet) a).addAttribute("bool", new Boolean[]{true, false});
		Option o = new Option(a);
		System.out.println(a.getAttribute("bool"));
	

	}

}
