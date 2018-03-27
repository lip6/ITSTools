package fr.lip6.move.gal.instantiate;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.InstanceDeclaration;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.VarDecl;

public abstract class FusionBuilder {

	
	public static GALTypeDeclaration fuseIntoGal (CompositeTypeDeclaration ctd) {
		
		
		// to track labels ; map instances to renamed labels appropriate for call resolution
		Map<InstanceDecl, Map<String,Label>> ilabMap = new HashMap<InstanceDecl, Map<String,Label>>();
		
		GALTypeDeclaration target = GalFactory.eINSTANCE.createGALTypeDeclaration();
		target.setName("fused");
		
		for (InstanceDecl idecl : ctd.getInstances()) {
			
		}
		
		return target;
	}
	
	public static void fuseInstanceInto (GALTypeDeclaration gal, CompositeTypeDeclaration ctd, InstanceDeclaration inst) {
		TypeDeclaration td = inst.getType();
		if (td instanceof GALTypeDeclaration) {
			GALTypeDeclaration itype = EcoreUtil.copy((GALTypeDeclaration) td);
			for (VarDecl id : itype.getArrays()) {
				id.setName(inst.getName()+"_"+id.getName());
			}
			gal.getArrays().addAll(itype.getArrays());
			for (VarDecl id : itype.getVariables()) {
				id.setName(inst.getName()+"_"+id.getName());
			}
			gal.getVariables().addAll(itype.getVariables());

			Map<String,Label> labMap = new HashMap<String, Label>(); 
			for (Transition trans : itype.getTransitions()) {
				if (trans.getLabel() != null && ! "".equals(trans.getLabel().getName())) {
					labMap.put(trans.getLabel().getName(), trans.getLabel());
					trans.getLabel().setName(inst.getName()+"."+trans.getLabel().getName());
				}
				trans.setName(inst.getName()+"_"+trans.getName());
			}
			gal.getTransitions().addAll(itype.getTransitions());
			
			for (Synchronization sync : ctd.getSynchronizations()) {
				for (int i=0 ;  i < sync.getActions().size() ; ++i) {
					Statement stat = sync.getActions().get(i);
					if (stat instanceof InstanceCall) {
						InstanceCall icall = (InstanceCall) stat;
						if (icall.getInstance().getRef() == inst) {
							Statement sc = GF2.createSelfCall(labMap.get(icall.getLabel().getName()));
							sync.getActions().set(i, sc);
						}
					}
				}
			}
			ctd.getInstances().remove(inst);
			
			
//			ctd.getInstances().addAll(itype.getInstances());
//			ctd.getSynchronizations().addAll(itype.getSynchronizations());
		}
		
	
	}
	
	
	public static void fuseInstance (CompositeTypeDeclaration ctd, InstanceDeclaration inst) {
		TypeDeclaration td = inst.getType();
		if (td instanceof CompositeTypeDeclaration) {
			CompositeTypeDeclaration itype = EcoreUtil.copy((CompositeTypeDeclaration) td);
			for (InstanceDecl id : itype.getInstances()) {
				id.setName(inst.getName()+"_"+id.getName());
			}
			Map<String,Label> labMap = new HashMap<String, Label>(); 
			for (Synchronization sync : itype.getSynchronizations()) {
				if (sync.getLabel() != null && ! "".equals(sync.getLabel().getName())) {
					labMap.put(sync.getLabel().getName(), sync.getLabel());
					sync.getLabel().setName(inst.getName()+"."+sync.getLabel().getName());
				}
				sync.setName(inst.getName()+"_"+sync.getName());
			}
			
			
			for (Synchronization sync : ctd.getSynchronizations()) {
				for (int i=0 ;  i < sync.getActions().size() ; ++i) {
					Statement stat = sync.getActions().get(i);
					if (stat instanceof InstanceCall) {
						InstanceCall icall = (InstanceCall) stat;
						if (icall.getInstance().getRef() == inst) {
							Statement sc = GF2.createSelfCall(labMap.get(icall.getLabel().getName()));
							sync.getActions().set(i, sc);
						}
					}
				}
			}
			ctd.getInstances().remove(inst);
			
			
			ctd.getInstances().addAll(itype.getInstances());
			ctd.getSynchronizations().addAll(itype.getSynchronizations());
		}
	}
}
