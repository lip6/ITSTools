package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.lip6.move.gal.AbstractInstance;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.GalInstance;
import fr.lip6.move.gal.ItsInstance;
import fr.lip6.move.gal.OtherInstance;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.TypeDeclaration;

public class TypeFuser {

	
	public static void fuseSimulatedTypes (Specification spec) {
		
		
		List<TypeDeclaration> types = new ArrayList<TypeDeclaration>(spec.getTypes());
		List<TypeDeclaration> res = new ArrayList<TypeDeclaration>();
		getDependentTypes(types, spec.getMain(), new HashSet<TypeDeclaration>(), res);
		Collections.sort(res, new DepthComparator());
		spec.getTypes().clear();
		spec.getTypes().addAll(res);
		
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
