package fr.lip6.move.gal.instantiate;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AbstractInstance;
import fr.lip6.move.gal.Action;
import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Call;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.GalInstance;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.VarAccess;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

public class CompositeBuilder {


	private static CompositeBuilder instance = new CompositeBuilder();
	
	private CompositeBuilder() {}
	
	public static CompositeBuilder getInstance() {
		return instance;
	}

	private GALTypeDeclaration gal=null;
	// a cache holding total number of variables
	private int galSize=-1;
	
	
	public Specification buildComposite (GALTypeDeclaration galori) {

		gal = galori ; 
		Specification spec = (Specification) gal.eContainer(); //GalFactory.eINSTANCE.createSpecification();
		
		if (galori.getTransient() != null && ! (galori.getTransient().getValue() instanceof False)) {
			// skip, we don't know how to handle transient currently
			return spec;
		}
		Partition p = buildPartition();
		
		System.err.println("Partition obtained :" + p);
		
		List<ArrayPrefix> totreat = new ArrayList<ArrayPrefix> ();
		for (ArrayPrefix ap : gal.getArrays()) {
			// create a dummy array ref to use the getVarIndex API
			Variable v = GalFactory.eINSTANCE.createVariable();
			VariableRef vref = GalFactory.eINSTANCE.createVariableRef();
			vref.setReferencedVar(v);
			ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
			ava.setPrefix(ap);
			ava.setIndex(vref);
			
			// a target list representing the whole array
			TargetList tl = new TargetList();
			tl.addAll(getVarIndex(ava));
			
			for (TargetList tp : p.getParts()) {
				if (tp.intersects(tl) &&  ! tp.contains(tl)) {
					// so a partition element overlaps only part of the array
					// this can only happen if the array is only referred to statically in the whole spec
					// rewrite it
					totreat.add(ap);
					break;
				}
			}			
		}
		for (ArrayPrefix ap : totreat) {
			System.err.println("Rewriting array " + ap.getName()+ " to variables to allow decomposition.");
			rewriteArrayAsVariables (ap);
		}
		if (!totreat.isEmpty()) {
			p = buildPartition();
		}
		
		spec.getTypes().remove(gal);
		
		// create a GAL type to hold the variables and transition parts of each partition element
		for (int pindex = 0; pindex < p.getParts().size() ; pindex++) {
			GALTypeDeclaration pgal = GalFactory.eINSTANCE.createGALTypeDeclaration();
			pgal.setName("p"+pindex);
			spec.getTypes().add(pgal);
		}
		
		CompositeTypeDeclaration ctd = GalFactory.eINSTANCE.createCompositeTypeDeclaration();
		String cname = galori.getName()+"_mod";
		cname = cname.replaceAll("\\.", "_");
		ctd.setName(cname);
		spec.getTypes().add(ctd);
		for (int i=0 ; i  < p.getParts().size() ; i++) {
			GalInstance gi = GalFactory.eINSTANCE.createGalInstance();
			gi.setName("i"+i);
			gi.setType((GALTypeDeclaration) spec.getTypes().get(i));
			ctd.getInstances().add(gi);
		}
		
		for (Transition t : gal.getTransitions()) {		
			// collect guard edges and statement edges.
			List<Edge<BooleanExpression>> guardEdges = new ArrayList<Edge<BooleanExpression>>();
			List<Edge<Actions>> actionEdges = new ArrayList<Edge<Actions>>();
			
			collectGuardTerms (t.getGuard(), guardEdges);
			for (Actions a : t.getActions()) {
				collectStatements (a,actionEdges);
			}
			// compute full support of transition
			TargetList support = new TargetList();
			for (Edge<BooleanExpression> edge : guardEdges) {
				support.targets.or(edge.targets.targets);
			}
			for (Edge<Actions> edge : actionEdges) {
				support.targets.or(edge.targets.targets);
			}
			
			Synchronization sync = GalFactory.eINSTANCE.createSynchronization();
			sync.setName(t.getName());
			if (t.getLabel() != null) {
				sync.setLabel(t.getLabel());
			} else {
				Label lab = GalFactory.eINSTANCE.createLabel();
				lab.setName("");
				sync.setLabel(lab);
			}
			ctd.getSynchronizations().add(sync);
			
			for (int pindex = 0 ; pindex < p.getParts().size() ; pindex++) {
				TargetList tl = p.parts.get(pindex);
				if (support.intersects(tl)) {
					GALTypeDeclaration galoc = (GALTypeDeclaration) spec.getTypes().get(pindex);
					
					Transition tloc = GalFactory.eINSTANCE.createTransition();
					tloc.setName(t.getName());
					Label lab = GalFactory.eINSTANCE.createLabel();
					lab.setName(t.getName());
					tloc.setLabel(lab );
					
					InstanceCall icall = GalFactory.eINSTANCE.createInstanceCall();
					icall.setInstance(ctd.getInstances().get(pindex));
					icall.setLabel(lab);
					sync.getActions().add(icall);

					BooleanExpression guard = GalFactory.eINSTANCE.createTrue();
					
					for (Edge<BooleanExpression> edge : guardEdges) {
						if (edge.targets.intersects(tl)) {
							if (guard instanceof True) {
								guard = edge.expression;
							} else {
								And and = GalFactory.eINSTANCE.createAnd();
								and.setLeft(guard);
								and.setRight(edge.expression);
								guard = and;
							}
						}
					}
					tloc.setGuard(guard);
	
					for (Edge<Actions> edge : actionEdges) {
						if (edge.targets.intersects(tl)) {
							tloc.getActions().add(edge.expression);
						}
					}
					
					galoc.getTransitions().add(tloc);
				}
			}
			
			// add any remaining calls to labels as self calls
			List<Actions> todrop = new ArrayList<Actions>();
			for (Actions a : t.getActions()) {
				if (a instanceof Call) {
					Call call = (Call) a;
					SelfCall scall = GalFactory.eINSTANCE.createSelfCall();
					scall.setLabel(call.getLabel());
					sync.getActions().add(scall);
					todrop.add(a);
				}
			}
			t.getActions().removeAll(todrop);
			
		}
		
		Map<Variable,Integer> varmap = new HashMap<Variable, Integer>();
		for (Variable var : gal.getVariables()) {
			varmap.put(var, p.getIndex(var));
		}
		Map<ArrayPrefix,Integer> arrmap = new HashMap<ArrayPrefix, Integer>();
		for (ArrayPrefix ap : gal.getArrays()) {
			arrmap.put(ap, p.getIndex(ap));
		}
		
		for (Entry<Variable, Integer> entry : varmap.entrySet()) {
			GALTypeDeclaration galloc = (GALTypeDeclaration) spec.getTypes().get(entry.getValue());
			galloc.getVariables().add(entry.getKey());
		}
		
		for (Entry<ArrayPrefix, Integer> entry : arrmap.entrySet()) {
			GALTypeDeclaration galloc = (GALTypeDeclaration) spec.getTypes().get(entry.getValue());
			galloc.getArrays().add(entry.getKey());
		}		

		
		for (Transition t : gal.getTransitions()) {
				t.setGuard(GalFactory.eINSTANCE.createTrue());
			
		}
		
		Simplifier.simplify(spec);
		gal = null;
		galSize = -1 ;
		
		printDependencyMatrix(ctd);
		return spec;
	}

	private void printDependencyMatrix(CompositeTypeDeclaration ctd) {
		
		int [][] deps = new int [ctd.getInstances().size()][ctd.getSynchronizations().size()];
		
		for (int i = 0; i < ctd.getSynchronizations().size() ; i++) {
			Synchronization synci = ctd.getSynchronizations().get(i);
			for (Action a : synci.getActions()) {
				if (a instanceof InstanceCall) {
					InstanceCall icall = (InstanceCall) a;
					deps[ctd.getInstances().indexOf(icall.getInstance())][i] = 1;
				}
			}
		}
		PrintStream trace = System.err;
		// title line 
		trace.append("Variable");
		for (Synchronization s : ctd.getSynchronizations()) {
			trace.append("\t"+s.getName());
		}
		trace.append("\n");
		// data lines
		int j=0;
		for (AbstractInstance instance : ctd.getInstances()) {
			trace.append(instance.getName());
			for (int i = 0; i < ctd.getSynchronizations().size() ; i++) {
				trace.append("\t"+deps[j][i]);
			}
			trace.append("\n");
			j++;
		}
		trace.append("\n");
	}

	private Partition buildPartition() {
		Partition p = new Partition();
		for (Transition t : gal.getTransitions()) {		
			// collect guard edges and statement edges.
			List<Edge<BooleanExpression>> guardEdges = new ArrayList<Edge<BooleanExpression>>();
			List<Edge<Actions>> actionEdges = new ArrayList<Edge<Actions>>();
			
			collectGuardTerms (t.getGuard(), guardEdges);
			for (Actions a : t.getActions()) {
				collectStatements (a,actionEdges);
			}
			
			for (Edge<BooleanExpression> edge : guardEdges) {
				p.addRelation(edge.targets);
			}
			for (Edge<Actions> edge : actionEdges) {
				p.addRelation(edge.targets);
			}
			
		}
		return p;
	}
	
	


	/**
	 * Goal is to rewrite all accesses to array ap as accesses to a set of plain variables.
	 * It's supposed to be possible, i.e. all accesses are actually of the form ap[Constant].
	 * @param ap
	 */
	private void rewriteArrayAsVariables(ArrayPrefix ap) {
		// replacements for ava
		List<VariableRef> vrefs = new ArrayList<VariableRef>();
		// build the new set of variables, and refs upon them.
		int index = 0;
		for (IntExpression value : ap.getValues()) {
			Variable vi = GalFactory.eINSTANCE.createVariable();
			vi.setName(ap.getName()+"_"+index++);
			vi.setValue(EcoreUtil.copy(value));
			gal.getVariables().add(vi);
			VariableRef vref = GalFactory.eINSTANCE.createVariableRef();
			vref.setReferencedVar(vi);
			vrefs.add(vref);
		}
		// Pickup all accesses to the array in the spec
		List<ArrayVarAccess> totreat = new ArrayList<ArrayVarAccess>();
		// a first pass to collect without causing concurrent modif exception
		for (TreeIterator<EObject> it = gal.eAllContents(); it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof ArrayVarAccess) {
				ArrayVarAccess ava = (ArrayVarAccess) obj;
				if (ava.getPrefix() == ap) {
					assert ( ava.getIndex() instanceof Constant);
					totreat.add(ava);
				}
			}
		}
		// now replace
		for (ArrayVarAccess ava : totreat) {
			EcoreUtil.replace(ava, EcoreUtil.copy(vrefs.get(((Constant) ava.getIndex()).getValue())));
		}
		// kill ap now
		gal.getArrays().remove(ap);
	}

	private void collectStatements(Actions a, List<Edge<Actions>> actionEdges) {
		
		TargetList tlist = new TargetList();
		
		for ( TreeIterator<EObject> it = a.eAllContents(); it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof VarAccess) {
				VarAccess va = (VarAccess) obj;
				List<Integer> targets = getVarIndex(va);
				tlist.addAll(targets);
			}				
		}
		actionEdges.add(new Edge<Actions>(a, tlist));
	}

	/**
	 * Check that guard is a conjunction of conditions, and add the dependencies induced on parameters to them.
	 * @param guard
	 * @param guardEdges
	 * @return
	 */
	private boolean collectGuardTerms(BooleanExpression guard,	List<Edge<BooleanExpression>> guardEdges) {
		if (guard instanceof And) {
			And and = (And) guard;
			return collectGuardTerms(and.getLeft(), guardEdges) && collectGuardTerms(and.getRight(), guardEdges);				
		} else if (guard instanceof True) {
			return true;
		} else {
			TargetList tlist = new TargetList();
			
			for ( TreeIterator<EObject> it = guard.eAllContents(); it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof VarAccess) {
					VarAccess va = (VarAccess) obj;
					List<Integer> targets = getVarIndex(va);
					tlist.addAll(targets);
				}				
			}
			guardEdges.add(new Edge<BooleanExpression>(guard, tlist));
			return true;
		} 

		//return false;
	}
	


	private int getGalSize() {
		if (galSize == -1) {
			int sz = gal.getVariables().size();
			for (ArrayPrefix ap : gal.getArrays()) {
				sz += ap.getSize();
			}
			galSize = sz;
		}
		return galSize;
	}
	private List<Integer> getVarIndex(VarAccess e) {
		if (e instanceof VariableRef) {
			VariableRef vref = (VariableRef) e;
			return Collections.singletonList(gal.getVariables().indexOf(vref.getReferencedVar()));
		} else if (e instanceof ArrayVarAccess) {
			ArrayVarAccess ava = (ArrayVarAccess) e;
			int start = gal.getVariables().size();
			for (ArrayPrefix ap : gal.getArrays()) {
				if (ap != ava.getPrefix()) {
					start += ap.getSize();
				} else {
					break;
				}
			}
			if (ava.getIndex() instanceof Constant) {
				Constant cte = (Constant) ava.getIndex();
				return Collections.singletonList(start + cte.getValue());
			} else {
				List<Integer> toret = new ArrayList<Integer>(ava.getPrefix().getSize());
				for (int i = 0 ; i < ava.getPrefix().getSize() ; i++) {
					toret.add(start + i);
				}
				return toret;
			}
		}
		return Collections.emptyList();		
	}
	
	private String getVarName (int index) {
		if (index < gal.getVariables().size()) {
			return gal.getVariables().get(index).getName();
		} else {
			index -= gal.getVariables().size();
			for (ArrayPrefix ap : gal.getArrays()) {
				if (index < ap.getSize()) {
					return ap.getName() + "[" + index + "]";					
				} else {
					index -= ap.getSize();
				}
			}
		}
		return "OOB VARIABLE";
	}
	
	
	class TargetList {
		private BitSet targets = new BitSet(getGalSize());

		public void addAll(List<Integer> more) {
			for (Integer i : more) {
				targets.set(i);
			}
		}
		
		int size() {
			return targets.cardinality();
		}
		
		public boolean contains (TargetList tl) {
			BitSet tmp = (BitSet) targets.clone();
			tmp.and(tl.targets);
			/** if a && b == a, b is superset of a */
			return tmp.equals(tl.targets);
		}
		
		@Override
		public String toString() { 
			StringBuilder sb = new StringBuilder();
			for (int i = targets.nextSetBit(0); i >= 0; i = targets.nextSetBit(i+1)) {
				sb.append(getVarName(i)+", ");
			}
			return sb.toString();
		}

		public boolean intersects(TargetList tl) {
			return targets.intersects(tl.targets);
		}
		
		@Override
		public boolean equals(Object obj) {
			return targets.equals(((TargetList)obj).targets);
		}

		public void add(int indexOf) {
			targets.set(indexOf);
		}
		
	}
	
	/** represent a partition of variables */
	class Partition {
		private List<TargetList> parts = new ArrayList<CompositeBuilder.TargetList>();
		
		public List<TargetList> getParts() {
			return parts;
		}
		
		public Integer getIndex(ArrayPrefix ap) {
			TargetList tst = new TargetList();
			ArrayVarAccess dummy = GalFactory.eINSTANCE.createArrayVarAccess();
			dummy.setPrefix(ap);
			Constant zero = GalFactory.eINSTANCE.createConstant();
			zero.setValue(0);
			dummy.setIndex(zero);
			tst.addAll(getVarIndex(dummy));
			
			for (int i = 0; i < parts.size(); i++) {
				if (parts.get(i).intersects(tst)) {
					return i;
				}
			}
			return -1;
		}

		public Integer getIndex(Variable var) {
			TargetList tst = new TargetList();
			tst.add(gal.getVariables().indexOf(var));
			for (int i = 0; i < parts.size(); i++) {
				if (parts.get(i).intersects(tst)) {
					return i;
				}
			}
			return -1;
		}

		void addRelation (TargetList tl) {
			if (tl.targets.isEmpty())
				return;
			// we suppose parts is already a partition initially
			List<TargetList> newparts = new ArrayList<CompositeBuilder.TargetList>();
			for (TargetList part : parts) {
				if (part.equals(tl)) {
					/** never mind, this constraint tl is already taken into account */
					return;
				} else if (! part.intersects(tl)) {
					/** skip this entry */
					newparts.add(part);
				} else {
					
					if (part.contains(tl)) {
						/** we already have stronger constraint, stop here */
						return;
					} else {
						/** so we need to fuse into a stronger constraint */
						tl.targets.or(part.targets);
					}
				}
			}
			newparts.add(tl);
			parts = newparts;			
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (TargetList tl : parts) {
				sb.append(tl + "\n");
			}
			return sb.toString();
		}
	}
	
	class Edge<T> {
		T expression;
		TargetList targets;
		public Edge(T expression, TargetList targets) {
			this.expression = expression;
			this.targets = targets;
		}
		public TargetList getTargetList() {			
			return targets;
		}		
		
	}

	
//	private static void addArrayAccessToTrans(
//			Map<Transition, Map<ArrayPrefix, Set<Integer>>> arrEdges,
//			Transition owner,
//			ArrayVarAccess av) {
//		Map<ArrayPrefix, Set<Integer>> arrmap = arrEdges.get(owner);
//		if (arrmap == null) {
//			arrmap = new HashMap<ArrayPrefix,Set<Integer>> ();
//			arrEdges.put(owner, arrmap);
//		}
//		ArrayPrefix ap = av.getPrefix();
//		Set<Integer> list = arrmap.get(ap);
//		if (list == null) {
//			list = new TreeSet<Integer>();
//			arrmap.put(av.getPrefix(), list);
//		}
//		if (av.getIndex() instanceof Constant) {
//			Constant cte = (Constant) av.getIndex();
//			list.add(cte.getValue());						
//		} else {
////					hasComplexAccess.put(av.getPrefix(), true);Set<Integer> vals = new HashSet<Integer>();
//			for (int i = 0 ; i < ap.getSize(); i++) {
//				list.add(i);
//			}
//		}
//	}
//
//	private static void addVarRefToTrans(
//			Map<Transition, Set<Variable>> varEdges, Transition owner,
//			VariableRef va) {
//		Set<Variable> refs = varEdges.get(owner);
//		if (refs == null) {
//			refs = new HashSet<Variable>();
//			varEdges.put(owner, refs);
//		}
//		refs.add(va.getReferencedVar());
//	}
//	
//	private static<T> T pop(Set<T> todo) {
//		T seed = todo.iterator().next();
//		todo.remove(seed);
//		return seed;
//	}
//	
//	
//	for (EObject obj : Instantiator.getAllChildren(gal)) {
//		for (ArrayPrefix ap : gal.getArrays()) {
//			Set<Integer> vals = new HashSet<Integer>();
//			for (int i = 0 ; i < ap.getSize(); i++) {
//				vals.add(i);
//			}
//		}			
//	}
//	
//	// build hypergraph of transition to variable dependency
//	Map<Transition, Set<Variable>> varEdges = new HashMap<Transition, Set<Variable>>();
//	Map<Transition, Map<ArrayPrefix,Set<Integer>>> arrEdges = new HashMap<Transition, Map<ArrayPrefix,Set<Integer>>>();
//
//
//
//	Map<ArrayPrefix, Set<Integer>> constantArrs = new HashMap<ArrayPrefix, Set<Integer>>();
//
////	Map<ArrayPrefix,Boolean> hasComplexAccess = new HashMap<ArrayPrefix,Boolean>();
//	
////	int totalVars = gal.getVariables().size();		
//	for (ArrayPrefix ap : gal.getArrays()) {
//		Set<Integer> vals = new HashSet<Integer>();
//		for (int i = 0 ; i < ap.getSize(); i++) {
//			vals.add(i);
//		}
//		constantArrs.put(ap, vals);
////		totalVars += ap.getSize();
////		hasComplexAccess.put(ap, false);
//	}
//
//	
//	// compute hypergraph into Edges
//	Transition owner = null;
//	for (TreeIterator<EObject> it = gal.eAllContents() ; it.hasNext() ; ) {
//		EObject obj = it.next();
//		if (obj instanceof Transition) {
//			owner = (Transition) obj;				
//		} else if (obj instanceof VariableRef) {
//			VariableRef va = (VariableRef) obj;
//			addVarRefToTrans(varEdges, owner, va);
//		} else if (obj instanceof ArrayVarAccess) {
//			ArrayVarAccess av = (ArrayVarAccess) obj;
//			addArrayAccessToTrans(arrEdges, owner, av);
//		}
//	}		
//	
//
//	// now deduce underlying connectivity graph between variables
//	// collect components : two lists with matching indexes.
//	List<Set<Variable>> components = new ArrayList<Set<Variable>>();
//	List<Map<ArrayPrefix,Set<Integer>>> arrComponents = new ArrayList<Map<ArrayPrefix,Set<Integer>>>();
//	
//	// we iterate thru all variables of GAL; these todo are emptied as the algorithm processes transitions
//	// initially all vars and array cells need to be treated
//	Set<Variable> todo = new HashSet<Variable>(gal.getVariables());
//	Map<ArrayPrefix, Set<Integer>> todoArrs = new HashMap<ArrayPrefix, Set<Integer>>(constantArrs);
//	
//	while (! todo.isEmpty() || ! todoArrs.isEmpty()) {
//		// this will be the new component 
//		Set<Variable> component = new HashSet<Variable>();
//		Map<ArrayPrefix, Set<Integer>> arrcomponent = new HashMap<ArrayPrefix,Set<Integer>>();
//		
//		// pop any variable from todo, and add it to the component
//		if (!todo.isEmpty()) {
//			Variable seed = pop(todo);
//			component.add(seed);
//		} else {
//			Entry<ArrayPrefix, Set<Integer>> arrtarget = todoArrs.entrySet().iterator().next();
//			ArrayPrefix target = arrtarget.getKey();
//			Set<Integer> tvalues = arrtarget.getValue();
//			Integer tindex = pop(tvalues);
//			if (tvalues.isEmpty()) {
//				todoArrs.remove(target);
//			}
//			
//			Set<Integer> valueSet = new HashSet<Integer>();
//			valueSet.add(tindex);
//			arrcomponent.put(target, valueSet);
//		}
//					
//		// iterate thru transitions : add all related variables of seed (transitively) to component.
//		for (Transition t : gal.getTransitions()) {
//			boolean domerge = false;
//			Set<Variable> vars = varEdges.get(t);
//			if (vars != null) {
//				Set<Variable> varscopy = new HashSet<Variable>(vars);
//				varscopy.retainAll(component);
//				if (! varscopy.isEmpty()) {
//					domerge = true;
//				}
//			} else {
//				vars = Collections.emptySet();
//			}
//			
//			Map<ArrayPrefix, Set<Integer>> arrs = arrEdges.get(t);
//			if (arrs != null) {
//				for (Entry<ArrayPrefix, Set<Integer>> entry : arrs.entrySet()) {
//					Set<Integer> incomp = arrcomponent.get(entry.getKey());
//					if (incomp != null) {
//						Set<Integer> targetcopy = new HashSet<Integer>(entry.getValue()) ;
//						targetcopy.retainAll(incomp);
//						if (! targetcopy.isEmpty()) {
//							domerge = true;
//							break;
//						}
//					}
//				}
//			}
//			
//			if (domerge){
//				Map<ArrayPrefix, Set<Integer>> toadd = arrEdges.get(t);
//				if (toadd != null) {
//					for (Entry<ArrayPrefix, Set<Integer>> entry : toadd.entrySet()) {
//						ArrayPrefix target = entry.getKey();
//						Set<Integer> incomp = arrcomponent.get(target);
//						if (incomp == null) {
//							incomp = new HashSet<Integer>(entry.getValue());
//							arrcomponent.put(entry.getKey(), incomp);
//						} else {
//							incomp.addAll(entry.getValue());
//						}
//						
//						Set<Integer> set = todoArrs.get(target);
//						if (set != null) {
//							set.removeAll(incomp);
//							if (set.isEmpty()) 
//								todoArrs.remove(target);
//						}
//					}
//				}
//				
//				component.addAll(vars);						
//				todo.removeAll(vars);
//			}
//		}
//		components.add(component);
//		arrComponents.add(arrcomponent);
//		
//	}
//	
//	Specification spec = null;
//	
//	if (components.size() > 1 ) {
//		System.err.println("Found separable sub components !");
//		spec = GalFactory.eINSTANCE.createSpecification();
//		
//		
//		for (int i = 0; i < components.size(); i++) {
//			System.err.println("\nComponent "+i);
//			GALTypeDeclaration subgal = GalFactory.eINSTANCE.createGALTypeDeclaration();
//			subgal.setName("Sub"+i);
//			spec.getTypes().add(subgal);
//			Map<Variable,Variable> mapvars = new HashMap<Variable, Variable>();
//			for (Variable var :components.get(i)) {
//				Variable varimg = EcoreUtil.copy(var);
//				subgal.getVariables().add(varimg );
//				mapvars.put(var, varimg);
//				System.err.println(var.getName());
//			}
//			for (Entry<ArrayPrefix, Set<Integer>> entry : arrComponents.get(i).entrySet()) {
//				System.err.println(entry.getKey().getName() + entry.getValue());					
//			}
//		}
//	}
//	
//	
	
}



