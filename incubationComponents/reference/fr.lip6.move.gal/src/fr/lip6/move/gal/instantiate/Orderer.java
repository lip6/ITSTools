package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Variable;

public class Orderer {

	public static void lexicoSortGALVariables(GALTypeDeclaration gal) {
		
		List<Variable> vars = new ArrayList<Variable>(gal.getVariables());
		Collections.sort(vars, new Comparator<Variable>() {

			
			@Override
			public int compare(Variable o1, Variable o2) {
				// the special lexico-numeric sort
				int vala = parseLastInt(o1.getName()) ;
				int valb = parseLastInt(o2.getName()) ;
				// Case 1 : global vars (no ints in them) are heavy, they go to top of structure.
				if ( vala == -1 && valb >= 0 ) {
					return -1;
				} else if ( valb == -1 && vala >= 0 ) {
					return 1;
				} else if ( vala == valb ) {
					// case 2 : we have two indexed variables, same index, revert to lexico
					return o1.getName().compareTo(o2.getName());
				} else {
					// case 3 : sort by value
					return new Integer(vala).compareTo(valb);
				}
			}
		});
		
		gal.getVariables().clear();
		gal.getVariables().addAll(vars);
		
	}

	
	static final Pattern p = Pattern.compile("\\d+");
	
	static int parseLastInt (String s) {
		Matcher m = p.matcher(s);
		int val = -1 ;
		while (m.find()) {
			val = Integer.parseInt(m.group());					
		}
		return val;
	}
}
