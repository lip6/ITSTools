package fr.lip6.move.gal.application.solver.ltl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.application.solver.ReachabilitySolver;
import fr.lip6.move.gal.mcc.properties.MCCExporter;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;

/**
 * For each property of the model that is lengthening-insensitive XOR shortening-insensitive, build two versions of the model
 * reduced with LTL and LI_LTL respectively.
 * Also output some CSV stats on the reductions effectiveness.
 * @author ythierry
 *
 */
public class LTLLengthSensitivityAnalyzer {

	/**
	 * If property is short/length insensitive, but not both, build two model/property pairs, one reduced with SLCL_LTL, one reduced with LTL.	 *
	 * Also produce some CSV stats. See FORTE'22 paper for more details.
	 * @param reader the input model
	 * @param spot to run spot scripts
	 * @param pwd work dir
	 * @param examination in MCC format
	 * @param solverPath path for Z3
	 */
	public static void doSensitivityAnalysis(MccTranslator reader, SpotRunner spot, String pwd, String examination, String solverPath) {

		int nbP,nbT,nbP2,nbT2,nbP3,nbT3;
		long redTime;
		long analysisTime;
		boolean isSI,isSh,isLen;
		boolean reducedLTL=false;
		boolean reducedSLCL=false;

		try {
			// read the LTL in
			reader.loadProperties(examination);

			// unfold the net if colored, do very basic simplifications.
			reader.createSPN();
			SparsePetriNet spn = reader.getSPN();

			// ok so this is our input size.
			nbP=spn.getPlaceCount();
			nbT=spn.getTransitionCount();

			// with auto flush, start building the stats file.
			PrintWriter out = new PrintWriter(new FileOutputStream(pwd+"/"+examination+"stats.csv"),true);
			out.println("model,examination,property id,init places,init trans,reduced LTL,out places,out trans,reduced short long,red places,red trans,time to red(ms),Stutter Ins,Short Ins,Leng Ins,analysis time(ms)");

			// for each property in the input
			for (int propid = 0; propid < spn.getProperties().size() ; propid++) {

				// run on a clean copy with just the current property
				SparsePetriNet spnProp = new SparsePetriNet(reader.getSPN());
				fr.lip6.move.gal.structural.Property prop = spnProp.getProperties().get(propid);
				spnProp.getProperties().clear();
				spnProp.getProperties().add(prop);

				// run sensitivity analysis and report the time it took
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

				// Compute a model reduced for the current property only, in LTL mode.
				{
					StructuralReduction sr = new StructuralReduction (spnProp);
					try {
						// only apply LTL reduction rules, so no agglomerations and no prefix/suffix slicing in particular.
						reducedLTL = ReachabilitySolver.applyReductions(sr, ReductionType.LTL, solverPath, true, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					spnProp.readFrom(sr);
					spnProp.simplifyLogic();

					// so this might be a slightly smaller model than the original
					// mostly due to redundant places/transition rules or symmetric choice rule.
					nbP2=spnProp.getPlaceCount();
					nbT2=spnProp.getTransitionCount();
				}

				// we only actually build the output files if it is relevant for length sensitivity experiment
				if (!isSI &&  (isSh || isLen)) {
					String outform = pwd + "/" + examination + "." + propid + ".xml";
					String outpnml = pwd + "/model."+examination+"."+propid +".pnml";
					MCCExporter.exportToMCCFormat(outpnml, outform, spnProp);
				}

				// we chain the LI_LTL reductions on top of previous ones, all LTL preserving rules are of course LI_LTL compliant.
				{
					long time = System.currentTimeMillis();
					StructuralReduction sr = new StructuralReduction (spnProp);
					try {
						reducedSLCL = ReachabilitySolver.applyReductions(sr, ReductionType.LI_LTL, solverPath, true, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
					spnProp.readFrom(sr);
					spnProp.simplifyLogic();

					// this is the smallest model we could build under LI_LTL assumptions
					nbP3=spnProp.getPlaceCount();
					nbT3=spnProp.getTransitionCount();
					redTime = System.currentTimeMillis() - time;
				}

				// again only output the reduced model if it is relevant.
				if (!isSI &&  (isSh || isLen)) {
					String outform = pwd + "/" + examination + "." + propid + ".red.xml";
					String outsr = pwd + "/model."+examination+"."+ propid +".red.pnml";
					MCCExporter.exportToMCCFormat(outsr, outform, spnProp);
				}

				String name = pwd.replaceAll(".*/", "");
				// always print the stats line, regardless of whether we were short-ins XOR length-ins or not.
				out.println(name+","+examination+","+propid+","+nbP+","+nbT+","+reducedLTL+","+nbP2+","+nbT2+","+reducedSLCL+","+nbP3+","+nbT3+","+redTime+","+(isSI?"1":"0")+","+(isSh?"1":"0")+","+(isLen?"1":"0")+","+analysisTime);

			}
			// force to flush as well.
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}



