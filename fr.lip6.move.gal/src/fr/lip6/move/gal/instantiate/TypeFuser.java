package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AbstractInstance;
import fr.lip6.move.gal.Action;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.GALParamDef;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.GalInstance;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.ItsInstance;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.OtherInstance;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.TypeDeclaration;

public class TypeFuser {

	
	public static void fuseSimulatedTypes (Specification spec) {
		
		sortTypes(spec);
		sortInstances(spec);
		
		buildArchetypes(spec);
	}
	
	private static void sortInstances(Specification spec) {
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) td;
				
				List<AbstractInstance> insts = new ArrayList<AbstractInstance>(ctd.getInstances());
				Collections.sort(insts,new InstanceComparator(spec));
				ctd.getInstances().clear();
				ctd.getInstances().addAll(insts);
				
				List<Synchronization> syncs = new ArrayList<Synchronization>(ctd.getSynchronizations());
				Collections.sort(syncs,new SynchronizationComparator());
				ctd.getSynchronizations().clear();
				ctd.getSynchronizations().addAll(syncs);
			}
		}		
	}

	private static void sortTypes(Specification spec) {
		List<TypeDeclaration> types = new ArrayList<TypeDeclaration>(spec.getTypes());
		//List<TypeDeclaration> res = new ArrayList<TypeDeclaration>();
		//getDependentTypes(types, spec.getMain(), new HashSet<TypeDeclaration>(), res);
		Collections.sort(types, new DepthComparator());
		spec.getTypes().clear();
		spec.getTypes().addAll(types);
	}

	private static void buildArchetypes(Specification spec) {
		Map<Label,Label> labMap = new HashMap<Label, Label>();
		Map<CompositeTypeDeclaration,CompositeTypeDeclaration> typeMap = new HashMap<CompositeTypeDeclaration, CompositeTypeDeclaration>();
		
		Map<String,ArcheType> archeTypes = new HashMap<String, ArcheType>();
		
		DepthFathomer df = new DepthFathomer();
		int maxdepth = 1;
		for (int depth = 1; depth <= maxdepth ; depth++) {
			sortTypes(spec);
			sortInstances(spec);
			typeMap.clear();
			labMap.clear();

		for (TypeDeclaration td : new ArrayList<TypeDeclaration>(spec.getTypes())) {
			int dt = df.getDepth(td);
			maxdepth = Math.max(dt, maxdepth);
			if (dt == depth) {
				if (td instanceof CompositeTypeDeclaration) {
					CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) td;
					if (! hasSingleNonLocalLabels(ctd)) {
						continue;
					}
					String tdesc = computeTypeString(ctd);
					ArcheType arch = archeTypes.get(tdesc);
					if (arch == null) {
						arch = new ArcheType();
						arch.type = GalFactory.eINSTANCE.createCompositeTypeDeclaration();
						arch.type.setName("Archetype"+archeTypes.size());
						int i =0;
						for (AbstractInstance ai : ctd.getInstances()) {
							AbstractInstance bi = EcoreUtil.copy(ai);
							bi.setName("i"+ i++);
							arch.type.getInstances().add(bi);
						}
						archeTypes.put(tdesc, arch);
						spec.getTypes().add(arch.type);
					}
					spec.getTypes().remove(ctd);
					
					for (Synchronization sync : ctd.getSynchronizations()) {
						String sdesc = computeSyncString(sync);
						Synchronization archsync = arch.archSyncs.get(sdesc);
						if (archsync == null) {
							archsync = GalFactory.eINSTANCE.createSynchronization();
							archsync.setName("s" + arch.type.getSynchronizations().size());
							archsync.setLabel(GF2.createLabel(sdesc));
							for (Action act : sync.getActions()) {
								if (act instanceof InstanceCall) {
									InstanceCall icall = (InstanceCall) act;
									InstanceCall icall2 = GalFactory.eINSTANCE.createInstanceCall();
									icall2.setInstance(arch.type.getInstances().get(getIndex(icall.getInstance())));
									icall2.setLabel(icall.getLabel());
									archsync.getActions().add(icall2);
								}
							}
							arch.archSyncs.put(sdesc, archsync);
							arch.type.getSynchronizations().add(archsync);
						}
						labMap.put(sync.getLabel(), archsync.getLabel());
					}
					
					typeMap.put(ctd, arch.type);
				}
			}
		}
		
		System.out.println("Fusing "+ typeMap.size() + " types at depth" + depth);
		
		for (TreeIterator<EObject> it = spec.eAllContents(); it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof ItsInstance) {
				ItsInstance itsi = (ItsInstance) obj;
				CompositeTypeDeclaration target = typeMap.get(itsi.getType());
				if (target != null) {
					itsi.setType(target);
				}
			} else if (obj instanceof InstanceCall) {
				InstanceCall icall = (InstanceCall) obj;
				Label target = labMap.get(icall.getLabel());
				if (target != null) {
					icall.setLabel(target);
				}
			}
		}
		}
		
	}

	private static boolean hasSingleNonLocalLabels(CompositeTypeDeclaration ctd) {
		Set<String> labs = new HashSet<String>();
		for (Synchronization sync : ctd.getSynchronizations()) {
			String lab = sync.getLabel().getName();
			if ("".equals(lab) || labs.contains(lab)) {
				return false;
			}
			labs.add(lab);
			for (Action act : sync.getActions()) {
				if (act instanceof SelfCall) {
					return false;
				}
			}
		}
		return true;
	}

	public static String computeSyncString (Synchronization sync) {
		StringBuilder sb = new StringBuilder();
		for (Action act : sync.getActions()) {
			if (act instanceof InstanceCall) {
				InstanceCall icall = (InstanceCall) act;
				if (icall.getLabel() instanceof Label) {
					Label lab = (Label) icall.getLabel();
					sb.append(getIndex(icall.getInstance())+"."+lab.getName());
				}
			}
		}
		return sb.toString();
	}
	
	private static int getIndex(AbstractInstance instance) {
		if (instance.eContainer() instanceof CompositeTypeDeclaration) {
			CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) instance.eContainer();
			return ctd.getInstances().indexOf(instance);
		}
		return -1;
	}

	public static String computeTypeString (CompositeTypeDeclaration ctd) {
		StringBuilder sb = new StringBuilder();
		for (AbstractInstance ai : ctd.getInstances()) {
			sb.append(computeInstanceTypeString(ai));
		}
		return sb.toString();
	}
	
	public static String computeInstanceTypeString(AbstractInstance ai) {
		StringBuilder sb = new StringBuilder();
		if (ai instanceof GalInstance) {
			GalInstance gali = (GalInstance) ai;
			sb.append(gali.getType().getName());
			for (GALParamDef pdef : gali.getParamDefs()) {
				sb.append(pdef.getParam().getName().replace("$", "")+pdef.getValue());
			}
		} else if (ai instanceof ItsInstance) {
			ItsInstance itsi = (ItsInstance) ai;
			sb.append(itsi.getType().getName());
		} else if (ai instanceof OtherInstance) {
			OtherInstance otheri = (OtherInstance) ai;
			sb.append("other");				
		} else {
			sb.append("template");
		}
		return sb.toString();
	}

	private static void getDependentTypes(List<TypeDeclaration> types, TypeDeclaration type, Set<TypeDeclaration> todo, List<TypeDeclaration> res) {

		if (type instanceof CompositeTypeDeclaration) {
			CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) type;
			for (AbstractInstance ai : ctd.getInstances()) {
				if (ai instanceof GalInstance) {
					GalInstance gali = (GalInstance) ai;
					if (!todo.contains(gali.getType())) {
						getDependentTypes(types, gali.getType(), todo, res);
					}
				}
				if (ai instanceof ItsInstance) {
					ItsInstance gali = (ItsInstance) ai;
					if (!todo.contains(gali.getType())) {
						getDependentTypes(types, gali.getType(), todo, res);
					}
				}
				if (ai instanceof OtherInstance) {
					OtherInstance gali = (OtherInstance) ai;
				}
			}
		}
		res.add(type);
		todo.add(type);
	}

}


class ArcheType {
	CompositeTypeDeclaration type;
	Map<String,Synchronization> archSyncs = new HashMap<String, Synchronization>();
}