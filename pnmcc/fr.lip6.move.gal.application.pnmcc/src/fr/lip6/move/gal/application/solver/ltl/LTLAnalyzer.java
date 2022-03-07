package fr.lip6.move.gal.application.solver.ltl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.application.solver.ReachabilitySolver;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.PropertiesToPNML;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralToPNML;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;

public class LTLAnalyzer {

	public static void doSensitivityAnalysis(MccTranslator reader, SpotRunner spot, String pwd, String examination, String solverPath) {

		int nbP,nbT,nbP2,nbT2,nbP3,nbT3;
		long redTime;
		long analysisTime;
		boolean isSI,isSh,isLen;
		boolean reducedLTL=false;
		boolean reducedSLCL=false;
		
		try {
			reader.loadProperties(examination);

			reader.createSPN();
			SparsePetriNet spn = reader.getSPN();
			nbP=spn.getPlaceCount();
			nbT=spn.getTransitionCount();

			// with auto flush
			PrintWriter out = new PrintWriter(new FileOutputStream(pwd+"/"+examination+"stats.csv"),true);
			out.println("model,examination,property id,init places,init trans,reduced LTL,out places,out trans,reduced short long,red places,red trans,time to red(ms),Stutter Ins,Short Ins,Leng Ins,analysis time(ms)");
			
			for (int propid = 0; propid < spn.getProperties().size() ; propid++) {
				MccTranslator copy = reader.copy();
				fr.lip6.move.gal.structural.Property prop = copy.getSPN().getProperties().get(propid);
				copy.getSPN().getProperties().clear();
				copy.getSPN().getProperties().add(prop);

				{
					long time = System.currentTimeMillis();
					TGBA tgba = spot.transformToTGBA(prop);
					try {
						spot.analyzeCLSL(tgba);
					} catch (IOException e) {
						System.out.println("Warning : SL/CL computation failed.");
					}
					isSI = tgba.isStutterInvariant();
					isSh = tgba.isCLInvariant();
					isLen = tgba.isSLInvariant();
					analysisTime = System.currentTimeMillis() - time;
					
				}
				
				// try some property specific reductions
				MccTranslator reader2 = reader.copy();
				{
					SparsePetriNet spnProp = reader2.getSPN();
					spnProp.getProperties().clear();
					spnProp.getProperties().add(prop.copy());
					StructuralReduction sr = new StructuralReduction (spnProp);
					try {
						reducedLTL = ReachabilitySolver.applyReductions(sr, ReductionType.LTL, solverPath, true, true);
						//sr.reduce(ReductionType.LTL);
					} catch (Exception e) {
						e.printStackTrace();
					}
					spnProp.readFrom(sr);
					spnProp.simplifyLogic();
					nbP2=spnProp.getPlaceCount();
					nbT2=spnProp.getTransitionCount();
				}

				if (!isSI &&  (isSh || isLen)) {
					String outform = pwd + "/" + examination + "." + propid + ".xml";
					try {
						boolean usesConstants = PropertiesToPNML.transform(reader2.getSPN(), outform, new ConcurrentHashDoneProperties());
						if (usesConstants) {
							// we exported constants to a place with index = current place count
							// to be consistent now add a trivially constant place with initial marking 1
							// token
							System.out.println("Added a place called one to the net.");
							spn.addPlace("one", 1);
						}
						String outsr = pwd + "/model."+examination+"."+propid +".pnml";
						StructuralToPNML.transform(reader2.getSPN(), outsr);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// chain reductions
				// reader2 = reader.copy();
				{
					SparsePetriNet spnProp = reader2.getSPN();
					
					long time = System.currentTimeMillis();
					StructuralReduction sr = new StructuralReduction (spnProp);
					try {
						reducedSLCL = ReachabilitySolver.applyReductions(sr, ReductionType.SLCL_LTL, solverPath, true, false);
						// sr.reduce(ReductionType.SI_LTL);
					} catch (Exception e) {
						e.printStackTrace();
					}
					spnProp.readFrom(sr);
					spnProp.simplifyLogic();
					nbP3=spnProp.getPlaceCount();
					nbT3=spnProp.getTransitionCount();
					redTime = System.currentTimeMillis() - time;
				}

				if (!isSI &&  (isSh || isLen)) {
					String outform = pwd + "/" + examination + "." + propid + ".red.xml";
					try {
						boolean usesConstants = PropertiesToPNML.transform(reader2.getSPN(), outform, new ConcurrentHashDoneProperties());
						if (usesConstants) {
							// we exported constants to a place with index = current place count
							// to be consistent now add a trivially constant place with initial marking 1
							// token
							System.out.println("Added a place called one to the net.");
							spn.addPlace("one", 1);
						}
						String outsr = pwd + "/model."+examination+"."+ propid +".red.pnml";
						StructuralToPNML.transform(reader2.getSPN(), outsr);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				String name = pwd.replaceAll(".*/", "");
				out.println(name+","+examination+","+propid+","+nbP+","+nbT+","+reducedLTL+","+nbP2+","+nbT2+","+reducedSLCL+","+nbP3+","+nbT3+","+redTime+","+(isSI?"1":"0")+","+(isSh?"1":"0")+","+(isLen?"1":"0")+","+analysisTime);
				
			}
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}



