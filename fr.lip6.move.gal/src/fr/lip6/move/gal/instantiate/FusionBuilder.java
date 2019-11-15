package fr.lip6.move.gal.instantiate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.ArrayInstanceDeclaration;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.InstanceDeclaration;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.semantics.INext;
import fr.lip6.move.gal.semantics.INextBuilder;

public abstract class FusionBuilder {

	
	public static void toSingleGAL (Specification spec) {
		if (spec.getMain() instanceof GALTypeDeclaration) {
			return;
		}
		
		String oriname =spec.getMain().getName();
		GALTypeDeclaration fused = GalFactory.eINSTANCE.createGALTypeDeclaration();
		fused.setName(oriname+"f");
		
		CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) spec.getMain();
		for (InstanceDecl inst : ctd.getInstances()) {
			TypeDeclaration type = inst.getType();
			String iname = inst.getName();
			if (type instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration subctd = (CompositeTypeDeclaration) EcoreUtil.copy(type);
				Specification tmp = GalFactory.eINSTANCE.createSpecification();
				tmp.setMain(subctd);
				toSingleGAL(tmp);
				type = tmp.getMain();
			}
			// single instance we are fine
			GALTypeDeclaration subgal = (GALTypeDeclaration) type;
			if (inst instanceof InstanceDeclaration) {				
				createInstanceOf(fused, "g" + iname, subgal);
			} else if (inst instanceof ArrayInstanceDeclaration) {
				ArrayInstanceDeclaration aid = (ArrayInstanceDeclaration) inst;
				int sz = ((Constant)aid.getSize()).getValue();
				for (int i = 0 ; i < sz ; i++) {
					createInstanceOf(fused, iname + "." +i , subgal);
				}
			}
		}
		for (Synchronization sync : ctd.getSynchronizations()) {
			Transition image = GF2.createTransition(sync.getName());
			if (sync.getLabel() != null && ! "".equals(sync.getLabel().getName()))
				image.setLabel(GF2.createLabel(sync.getLabel().getName()));
			image.setGuard(GalFactory.eINSTANCE.createTrue());
			for (Statement act : sync.getActions()) {
				if (act instanceof SelfCall) {
					SelfCall sc = (SelfCall) act;
					image.getActions().add(GF2.createSelfCall(GF2.createLabel(sc.getLabel().getName())));
				} else if (act instanceof InstanceCall) {
					InstanceCall icall = (InstanceCall) act;
					if (icall.getInstance().getIndex() == null) {
						image.getActions().add(GF2.createSelfCall(GF2.createLabel("g"+ icall.getInstance().getRef().getName() +"."+ icall.getLabel().getName())));
					} else {
						int index = ((Constant) icall.getInstance().getIndex()).getValue();
						image.getActions().add(GF2.createSelfCall(GF2.createLabel( icall.getInstance().getRef().getName() +"." + index + "."+ icall.getLabel().getName())));
					}
				}
			}
			fused.getTransitions().add(image);
		}
				
		spec.getTypes().clear();
		spec.getTypes().add(fused);
		spec.setMain(fused);
		for (Property prop : spec.getProperties()) {
			for (TreeIterator<EObject> it = prop.getBody().eAllContents() ; it.hasNext() ; /*NOP*/) {
				EObject obj = it.next();
				if (obj instanceof QualifiedReference) {
					QualifiedReference qref = (QualifiedReference) obj;
					String qname = "g" + qref.getQualifier().getRef().getName() + ".";
					for (Reference ref = qref.getNext() ; ; ref = ((QualifiedReference) ref).getNext()) {
						if (ref instanceof QualifiedReference) { 
							qname += "g" + ((QualifiedReference) ref).getQualifier().getRef().getName() + ".";
						} else if (ref instanceof VariableReference) {
							VariableReference vref = (VariableReference) ref;
							qname += vref.getRef().getName();
							if (vref.getIndex() == null) {
								for (Variable var : fused.getVariables()) {
									if (var.getName().equals(qname)) {
										vref.setRef(var);
										break;
									}
								}
							} else {
								for (ArrayPrefix var : fused.getArrays()) {
									if (var.getName().equals(qname)) {
										vref.setRef(var);
										break;
									}
								}
							}
							EcoreUtil.replace(qref, vref);
							break;
						}
					}					
					it.prune();
				}
			}
		}
		Instantiator.normalizeCalls(spec);
		spec.getMain().setName(oriname+"f");
	}

	private static void createInstanceOf(GALTypeDeclaration fused, String iname, GALTypeDeclaration subgal) {
		GALTypeDeclaration copy = EcoreUtil.copy(subgal);
		for (Variable var : copy.getVariables()) {
			var.setName(iname + "." + var.getName());					
		}
		for (ArrayPrefix ap : copy.getArrays()) {
			ap.setName(iname + "." + ap.getName());
		}				
		for (Transition t : copy.getTransitions()) {
			for (TreeIterator<EObject> it = t.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof SelfCall) {
					SelfCall sc = (SelfCall) obj;
					sc.setLabel(GF2.createLabel(iname + "." +sc.getLabel().getName()));
				} else if (obj instanceof BooleanExpression || obj instanceof IntExpression) {
					it.prune();
				}		
			}
		}
		for (Transition t : copy.getTransitions()) {
			t.setName(iname + "." + t.getName());
			if (t.getLabel() != null) {
				// copy to avoid messing up crossrefs
				t.setLabel(GF2.createLabel(iname + "." + t.getLabel().getName()));
			}				
		}
		fused.getVariables().addAll(copy.getVariables());
		fused.getArrays().addAll(copy.getArrays());
		fused.getTransitions().addAll(copy.getTransitions());
	}
	
	public static Specification toGALMain (Specification spec) {
		INextBuilder inb = INextBuilder.build(spec);
		
		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
		gal.setName("fused");
		
		// retro compile variable names
		int asize =0; // size of array if we are in one
		
		List<String> vnames = inb.getVariableNames();
		int sz = vnames.size();
		List<Integer> init = inb.getInitial();
		for (int vindex = 0; vindex < sz ; vindex++) {
			String vname = vnames.get(vindex);
			if (!vname.endsWith("[0]")) {
				Variable var = GalFactory.eINSTANCE.createVariable();
				var.setName(vname);
				var.setValue(GF2.constant(init.get(vindex)));
				gal.getVariables().add(var );
			} else {
				String aname = vname.substring(0, vname.length()-3);
				int asz=1;
				for (int j=vindex+1; j <sz ; j++ ) {
					if (! vnames.get(j).startsWith(aname)) {						
						break;
					} else {
						asz++;
					}
				}
				ArrayPrefix ap = GalFactory.eINSTANCE.createArrayPrefix();
				ap.setName(aname);
				ap.setSize(GF2.constant(asz));
				for (int j=0; j < asz ; j++) {
					ap.getValues().add(GF2.constant(init.get(vindex+j)));
				}
				gal.getArrays().add(ap);				
				vindex += asz -1; // -1 for the loop increment
			}
		}
		for (INext next : inb.getNextForLabel("")) {
			
		}
		
		spec.getTypes().clear();
		spec.getTypes().add(gal);
		spec.setMain(gal);
		return spec;
	}
	
	
	public static GALTypeDeclaration fuseIntoGal (CompositeTypeDeclaration ctd) {
		
		GALTypeDeclaration target = GalFactory.eINSTANCE.createGALTypeDeclaration();
		target.setName(ctd.getName());
		
		

		while (! ctd.getInstances().isEmpty()) {
			InstanceDecl idecl = ctd.getInstances().get(0);
			if (idecl instanceof InstanceDeclaration) {
				InstanceDeclaration ideclare = (InstanceDeclaration) idecl;
				fuseInstanceInto(target, ctd, ideclare);				
			}
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
