package fr.lip6.move.gal.pnml.togreatspn;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


import fr.lip6.move.pnml.ptnet.Arc;
import fr.lip6.move.pnml.ptnet.PTMarking;
import fr.lip6.move.pnml.ptnet.Page;
import fr.lip6.move.pnml.ptnet.PetriNet;
import fr.lip6.move.pnml.ptnet.Place;
import fr.lip6.move.pnml.ptnet.PnObject;
import fr.lip6.move.pnml.ptnet.Transition;

public class PNMLToGreatSPN {

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	
	public void transform(PetriNet petriNet, String path) throws IOException {

		
		PrintWriter pwdef = new PrintWriter(new File(path+".def"));
		PrintWriter pwnet = new PrintWriter(new File(path+".net"));
		for (Page p : petriNet.getPages()) {
			handlePage(p, pwdef, pwnet);
		}
		pwnet.close();
		pwdef.close();
		
	}

	public static String normalizeName(String text) {
		String res = text.replace(' ', '_');
		res = res.replace('-', '_');
		res = res.replace('/', '_');
		res = res.replace('*', 'x');
		res = res.replace('=', '_');
		
		return res;
	}

	private void handlePage(Page page, PrintWriter pwdef, PrintWriter pwnet) {
		Map<Place, Integer> placeMap = new HashMap<>();
		
		pwdef.println("|256\n%\n|\n") ; // header for def files
		
		pwnet.append("|0|\n|\nf   0   ") ; // number of mrking parameters
		
		long nbplaces = page.getObjects().stream().filter(x -> x instanceof Place).count();
		pwnet.append(nbplaces+"   ");
		pwnet.append("0   "); // number of rate parameters
		long nbtrans = page.getObjects().stream().filter(x -> x instanceof Transition).count();
		pwnet.append(nbtrans+ "   ");
		pwnet.append("0   "); // number of groups
		pwnet.append("0   "); // free 0 !!! gratos !! "&@`#  GSPN
		pwnet.append("0\n"); // number of layers seems to be optional
		
		
		for (PnObject n : page.getObjects()) {
			if (n instanceof Place) {
				Place p = (Place) n;

//				if (p.getName() != null)
//					ap.setComment("/**" + normalizeName(p.getName().getText()) + "*/");
				
				String pname = normalizeName(p.getId());
				int value = interpretMarking(p.getInitialMarking());
				
				pwnet.append(pname).append("    ") ;
				pwnet.append(value + " ");
				pwnet.append("0.0 0.0 ") ; // pos of place circle center
				pwnet.append("0.0 0.0 ") ; // pos of place name tag 
				pwnet.append("0 \n"); // free 0 !!! gratos !! "&@`#  GSPN			
			}
		}

		getLog().info("Transformed "+ nbplaces + " places.");

		for (PnObject pnobj : page.getObjects()) {
			if (pnobj instanceof Transition) {
				Transition t = (Transition) pnobj;
//				if (t.getName() != null)
//					tr.setComment("/**" + Utils.normalizeName(t.getName().getText()) + "*/");
				String tname = normalizeName(t.getId());
				pwnet.println(tname);
//				BooleanExpression guard = gf.createTrue();
//
//				for (Arc arc : t.getInArcs()) {
//					Place pl = (Place) arc.getSource();
//					Variable var = placeMap.get(pl);
//					int value = 1;
//					if (arc.getInscription() != null
//							&& arc.getInscription().getText() != null) {
//						value = Math.toIntExact(arc.getInscription().getText());
//					}
//					guard = GF2.and(guard, GF2.createComparison(
//							GF2.createVariableRef(var), ComparisonOperators.GE,
//							GF2.constant(value)));
//
//					tr.getActions().add(
//							GF2.createIncrement(GF2.createVariableRef(var), -value));
//				}
//
//				
//
//				for (Arc arc : t.getOutArcs()) {
//					Place pl = (Place) arc.getTarget();
//					Variable var = placeMap.get(pl);
//					int value = 1;
//					if (arc.getInscription() != null
//							&& arc.getInscription().getText() != null) {
//						value = Math.toIntExact(arc.getInscription().getText());
//					}
//					
//					// ensure transition relation is reversible by asserting positive variables
//					if (reversible)
//						guard = GF2.and(guard, GF2.createComparison(
//							GF2.createVariableRef(var), ComparisonOperators.GE,
//							GF2.constant(0)));
//					
//					tr.getActions().add(
//							GF2.createIncrement(GF2.createVariableRef(var), value));
//				}
//				
//				tr.setGuard(guard);
//				gal.getTransitions().add(tr);

			}
		}
		getLog().info("Transformed " + nbtrans + " transitions.");

	}


	private int interpretMarking(PTMarking ptMarking) {
		if (ptMarking == null || ptMarking.getText() == null) {
			return 0;
		}
		return Math.toIntExact(ptMarking.getText());
	}


}

///* Export to .def and .net files */
//int PNet::ExportToGSPN (const string &dir) {
//  int numligne = 0;
//  string pathnet = dir + "model.net" ;
//  string pathdef = dir + "model.def" ;
//  ofstream netff(pathnet.c_str());
//  ofstream deff(pathdef.c_str());
//  
//  netff.precision(4);
//  deff.precision(4);
//
//  deff << "|256\n%\n|\n" ; // header for def files
//    
//  netff << "|0|\n|\nf   0   " ; // number of mrking parameters
//  netff << LPlace.size() << "   ";
//  netff << "0   "; // number of rate parameters
//  netff << LTrans.size() << "   ";
//  netff << "0   "; // number of groups
//  netff << "0   "; // free 0 !!! gratos !! "&@`#  GSPN
//  netff << "0" << endl ; // number of layers seems to be optional
//
//  
//
//  // export classes
//  LClasse.ExportToGSPN(deff) ;
//
//  numligne = LClasse.size() ;
//  // export markings + places
//  LPlace.ExportToGSPN(deff,netff,numligne);
//  LTrans.renameTrans ();
//  LTrans.ExportToGSPN(netff,deff,numligne);
//
//  return 0;
//}

// LClasses unused : PT only
// LPlaces
//int Places::ExportToGSPN(ostream &deff,ostream &netff,int &numligne) {
//
//  list<Place>::iterator it;
//  for (it = lst.begin();it!= lst.end();it++ ) {
//    it->ExportMarkGSPN(deff,numligne); // resolves to : os << mult;
//    it->ExportToGSPN(netff,numligne); // 
//    netff << endl ;
//  }
//  return 0;
//
//}
//void Place::ExportToGSPN(ostream &os,int numligne ) {
//	  bool isBW = !dom || (dom->Name() == "null");
//
//	  os << name << "    " ;
//	  if ( marking == NULL ) { os << "0 "; }
//	  else if (isBW) { os << *marking << " " ; } //export number of marks for B/W places
//	  else { os << "-" << numligne+10000 << " " ; }
//	  os << pos.getGspn()  ; // pos of place circle center
//	  os << namepos.getGspn() ; // pos of place name tag 
//	  os << "0 "; // free 0 !!! gratos !! "&@`#  GSPN
//	  if (!isBW) {
//	    os << dompos.getGspn() ; // pos of domain tag
//	    dom->ExportToGSPN (os);
//	  }
//	}

//int Transition::ExportToGSPN (ostream &os,ostream &deff,int & numligne) {
//  if (name != "")
//    os << name ;
//  else 
//    os << "T_" << id ;
//  os << "  " ;
//  os << "1.0   " ; // rate of the transition
//  os << nserv << "   ";  // number of servers
//  os << priority << "   " ; // Stochastic transition kind  : 0 = exponential ; 127 = deterministic ; 0 < p < 127 = relative priority of trans
//  list<Arc *>::iterator it ;
//  list<Arc *> arcInhibitor ;
//  for (it = arcIn.begin() ; it != arcIn.end() ;it ++) {
//    if ( (*it)->IsInhibitor() ) arcInhibitor.push_back(*it);
//  }
//  os << arcIn.size()- arcInhibitor.size() << " ";
//  os << "0 " ; // rotation coef of the trans  in graphical interface
//  os << pos.getGspn() << " " ; // position of trans
//  os << namepos.getGspn() << " " ; // position of name tag
//  os << "1.0 1.0 " ; // position of ??
//  // Definition not clear on order of pos  ??? cimer GSPN
//  guard->ExportToGSPN(os);  
//
//
//  
//  for (it = arcIn.begin() ; it != arcIn.end() ;it ++) {
//    if ( ! (*it)->IsInhibitor() ) (*it)->ExportToGSPN(os,deff,numligne);
//  }
//  os << "   " << arcOut.size() << endl ;
//  for (it = arcOut.begin() ; it != arcOut.end() ;it ++) {
//    (*it)->ExportToGSPN(os,deff,numligne);
//  }
//  os << "   " << arcInhibitor.size() << endl ;
//  for (it = arcInhibitor.begin() ; it != arcInhibitor.end() ;it ++) {
//    (*it)->ExportToGSPN(os,deff,numligne);
//  }
//
//
//  return 0;
//}
//int Arc::ExportToGSPN (ostream &os,ostream &deff,int & numligne) {
//	  
//	  if (!place->Dom() || place->Dom()->Name() == "null") {
//	    os << "   ";
//	    os << valuation.getMultiplicityBWfunc () << "   "; // multiplicity of the arc
//	    os << place->Id()+1 << "   "; 
//	    os << pi.size() << " " ; // number of inflexion points
//	    os << "0" ; // delimiter gratos cimer GSPN. NOTE : no whitespace after this 0 or gspn will crash !
//	    os << endl;
//	  } else {
//	    os << "   ";
//	    os << "1   "; // multiplicity of the arc
//	    os << place->Id()+1 << "   "; 
//	    os << pi.size() << " " ; // number of inflexion points
//	    os << "0 " ; // delimiter gratos cimer GSPN
//	    os << valpos.getGspn() ; // function color position
//	    os << valuation.ExportToGSPN(); // color function
//	/*    // This block outputs the color function in .def file
//	    {
//	      ostringstream ost;
//	      ost << "F" << numligne++;
//	      string fname = ost.str();
//	      
//	      os << fname;
//
//	      deff <<  "(" << fname << " f " ;
//	      deff << "1.0 1.0 (@f\n" ;
//	      deff << valuation.ExportToGSPN(); // color function
//	      deff << "\n))\n";
//	    }
//	*/
//	    os << endl;
//	  }
//	  for (list<Position>::iterator it = pi.begin() ; it!=pi.end() ; it++ )
//	    os << it->getposV().first << " " << it->getposV().second << endl ;
//	  
//	  return 1;
//	}


