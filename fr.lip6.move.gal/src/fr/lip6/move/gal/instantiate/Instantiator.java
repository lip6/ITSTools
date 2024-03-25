package fr.lip6.move.gal.instantiate;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AbstractParameter;
import fr.lip6.move.gal.ArrayInstanceDeclaration;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.Event;
import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.InstanceDeclaration;
import fr.lip6.move.gal.LabelCall;
import fr.lip6.move.gal.NamedDeclaration;
import fr.lip6.move.gal.ParamDef;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.For;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.TypedefDeclaration;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.impl.VariableReferenceImpl;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.gal.support.SupportAnalyzer;
import fr.lip6.move.scoping.GalScopeProvider;


/**
 * Instantiation of parameters with some variations on the theme.
 * 
 * Instantiation is a process that removes parameters from a Specification.
 * 
 * Step 1 : start from main type, recurse into referred types
 * a) replace all ParamRef to ConstParam by their value (with simplifications)
 * contextually, this only concerns Spec level and current type level constants
 * fully ensuring constants in initializations and typedefs min..max

 * Step 5 : compute variable array sizes, initialize variables and local typedef
 * Step 6 : unroll For loops 
 * Step 7 : apply init block (unimplemented currently)

 * b) resolve  and instantiate all types, i.e. look at ParamDef of InstanceDecl and build appropriate type instantiations :
 * this just means copy the type, give it a new name and new initial value for type parameter.
 * 
 * Separate parameters if so indicated.

 *  * Step 8 : iteratively build transitions (syncs) for each value of cross-product of parameters
 * Step 8 bis (mingled for efficiency) : simplify all constant expressions, killing transitions with false guard 
 *
 * ArrayInstance refs should now be constant up to simplification
 * leave calls and labels alone, beyond work of separate.
 *
 * e) destroy current type's parameters and typedefs, recurse into nested types
 * 
 * 
 * Step 1 : replace all Spec level parameters by their values. Destroy these parameters.
 * Step 3 : starting from main, instantiate constParam, then
 *  // We selectively patch crossrefs of instanceCall to this new type.
 * 	// necessary ? normalize crossrefs might do it right ...
 * Step 4 : Recurse Step 3 on any type referred to from current type
 * At this stage, we no longer have any ConstParameter refs, only for loop and transition parameters remain
 * Sizes in particular should be constant, as well as min and max of typedefs.
 *
 * We end by only working on all non referenced types not visited in this traversal ?
 *
 * 
 * Step 9 : Instantiate calls : replace call to "label"(0,1) by call to a new Label "label_0_1"
 * Step 10 : Instantiate labels : replace Event labels same as step 10
 * Step 11 : call normalizeCalls, which will : 
 * a) resolve calls since 9 and 10 seriously messed with links 
 * b) replace calls to (now) non existent labels by "abort"
 * c) iterate, as propagating abort could kill more labels
 * Step 12 : clean up all typedefs, its-tools can't parse them, nor cares any more
 * 
 * 
 * Variant "Separate"
 * 
 * Before Step 8 :
 * Separate parameters of Events, building possibly
 * 
 * @author Yann
 *
 */
public class Instantiator {

	private static final int DEBUG =0;
	// to count number of skipped transitions
	private static double nbskipped=0;

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	public static Support instantiateParameters(Specification spec, boolean withSeparation)  {
		// to store variables simplified away
		Support toret = new Support();
		// ensure we have a main and a job
		if (spec.getMain() == null) {
			if (spec.getTypes().isEmpty()) {
				return toret;
			} else {
				spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
			}
		}
		
		// make sure there are parameters in the first place
		if (spec.getTypedefs().isEmpty() && spec.getParams().isEmpty() && spec.getTypes().stream().allMatch(t -> {
			if (t instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) t;
				return gal.getParams().isEmpty() && gal.getTypedefs().isEmpty();
			} else if (t instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) t;
				return ctd.getParams().isEmpty() && ctd.getTypedefs().isEmpty();
			} else {
				return false;
			}
		} )) {
			return toret;
		}
		
		// these are the types we need to instantiate
		// sorted by treatment order, external to internal
		Set<TypeDeclaration> todo = new LinkedHashSet<TypeDeclaration>();
		// these are the finished types we keep at end of procedure
		// sorted external to internal
		Set<TypeDeclaration> doneTypes = new LinkedHashSet<TypeDeclaration>();
		
		
		// clear Spec Level constants
		for (EObject tdef : spec.getTypedefs()) {
			int nbsub = replaceConstParam(tdef);
			if (nbsub > 0) {
				Simplifier.simplifyAllExpressions(tdef);
			}
		}
		
		// initialize with main
		todo.add(spec.getMain());
		
		
		Map<String, TypeDeclaration> instanceTypeMap = new HashMap<String, TypeDeclaration>();
		
		while (!todo.isEmpty()) {
			TypeDeclaration type = todo.iterator().next();
			todo.remove(type);

			// step 1 : initialize variables to have relevant types, clear constant parameters
			initializeParametersAndVariables(type);
			
			// step 2 : for nested instance declarations, make copies of referred types when param def are used
			// i.e. look at ParamDef of InstanceDecl and build appropriate type instantiations :
			// this just means copy the type, give it a new name and new initial value for type parameter.
			// At this stage, we no longer have any ConstParameter refs, only for loop and transition parameters remain
			// Sizes in particular should be constant, as well as min and max of typedefs.

			if (type instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) type;
				
				Map<TypeDeclaration, List<InstanceDeclaration>> redefs = new HashMap<TypeDeclaration, List<InstanceDeclaration>>();
				for (InstanceDecl inst : ctd.getInstances()) {
					if (inst instanceof InstanceDeclaration) {
						InstanceDeclaration idecl = (InstanceDeclaration) inst;
						List<InstanceDeclaration> decls = redefs.get(idecl.getType());
						if (decls == null) {
							decls = new ArrayList<InstanceDeclaration>();
							redefs.put(idecl.getType(), decls);
						}
						decls.add(idecl);
					} else if (inst instanceof ArrayInstanceDeclaration) {
						ArrayInstanceDeclaration ainst = (ArrayInstanceDeclaration) inst;
						
						todo.add(ainst.getType());
					}
				}
				
				// list types used in these redefs
				for (Entry<TypeDeclaration, List<InstanceDeclaration>> entry : redefs.entrySet()) {
					if (entry.getKey() instanceof GALTypeDeclaration) {
						GALTypeDeclaration gal = (GALTypeDeclaration) entry.getKey();

						// group similar type instantiations
						Map<String,List<InstanceDeclaration>> decls = new HashMap<String,List<InstanceDeclaration>>();
						for (InstanceDeclaration idecl : entry.getValue()) {
							String tname = TypeFuser.computeInstanceTypeString(idecl);
							List<InstanceDeclaration> list = decls.get(tname);
							if (list == null) {
								list = new ArrayList<InstanceDeclaration>();
								decls.put(tname,list);
							}
							list.add(idecl);
						}

						// do the actual instantiation of types
						for (Entry<String, List<InstanceDeclaration>> toinst : decls.entrySet()) {
							
							TypeDeclaration target = instanceTypeMap.get(toinst.getKey());
							if (target == null) {
								GALTypeDeclaration newtype = EcoreUtil.copy(gal);
								newtype.setName(toinst.getKey());
								InstanceDeclaration first = toinst.getValue().get(0);
								for (ParamDef pdef : first.getParamDefs()) {
									for (ConstParameter cp : newtype.getParams()) {
										if (cp.getName().equals(pdef.getParam().getName())) {
											cp.setValue(pdef.getValue());
											break;
										}
									}
								}
								todo.add(newtype);
								target = newtype;
								instanceTypeMap.put(toinst.getKey(), target);
							}
							
							for (InstanceDeclaration idecl : toinst.getValue()) {
								idecl.setType(target);
								idecl.getParamDefs().clear();
							}
						}
					} else {
						todo.add(entry.getKey());
						for (InstanceDeclaration idecl : entry.getValue()) {
							if (! idecl.getParamDefs().isEmpty()) {
								getLog().warning("Cannot handle parameters of composite type");
							}
						}
					}
				}
				
				
				
				
			}
			

			
			// Step 7 : separate parameters
			if (withSeparation) {
				separateParameters(type);
			} else if (type instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration)type; 
				for (Transition t : gal.getTransitions()) {
					fuseEqualParameters(t);
				}
			}
						
			// Step 6 : unroll For loops 
			if (! spec.getTypedefs().isEmpty() || ! type.getTypedefs().isEmpty()) {
				instantiateForLoops(type);
			}
			
			doneTypes.add(type);
		}
		
		 //Step 1 : replace all Spec level parameters by their values. Destroy these parameters.
		//instantiateTypeParameters(spec);
		
		/*  Step 3 : starting from main, instantiate constParam, then
		 * instantiate all types, i.e. look at ParamDef of InstanceDecl and build appropriate type instantiations :
		 * this just means copy the type, give it a new name and new initial value for type parameter.
		 *  // We selectively patch crossrefs of instanceCall to this new type.
		 * 	// necessary ? normalize crossrefs might do it right ...
		 * Step 4 : Recurse Step 3 on any type referred to from current type
		 * At this stage, we no longer have any ConstParameter refs, only for loop and transition parameters remain
		 * Sizes in particular should be constant, as well as min and max of typedefs.*/


		//		instantiateHotBit(spec);

		// Step 8 : go for crossproduct instantiation
		// leave Label defs alone, but update calls "act"(0) to a new Label "act_0"
		nbskipped = 0;
		int doneAll = 0;
		List<Event> totreat = new ArrayList<Event>(); 

		for (TypeDeclaration td : doneTypes) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;

				Set<Variable> constvars = new HashSet<Variable>(gal.getVariables());
				Map<ArrayPrefix, Set<Integer>> constantArrs = new HashMap<ArrayPrefix, Set<Integer>>();
				Set<NamedDeclaration> dontremove = new HashSet<NamedDeclaration>();
				int totalVars = Simplifier.computeConstants(gal, constvars, constantArrs, dontremove, toret);
				Simplifier.printConstantVars(gal, constvars, constantArrs, totalVars); 
				
				List<Transition> done = new ArrayList<Transition>();
				for (Transition t : gal.getTransitions()) {
					if (t.getLabel() != null) {
						List<Transition> list = instantiateParameters(t, constvars, constantArrs);
						for (Transition evt : list) {
							instantiateLabel(evt.getLabel(), evt.getLabel().getParams());
						}
						done.addAll(list);
						totreat.addAll(list);
					}
				}
				for (Transition t : gal.getTransitions()) {
					if (t.getLabel() == null) {
						List<Transition> list = instantiateParameters(t, constvars, constantArrs);
						done.addAll(list);
					}
				}
				gal.getTransitions().clear();								
				
				gal.getTransitions().addAll(done);

				doneAll += done.size();

			} else 	if (td instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration s = (CompositeTypeDeclaration) td;


				List<Synchronization> done = new ArrayList<Synchronization>();
				for (Synchronization t : s.getSynchronizations()) {
					List<Synchronization> list = instantiateParameters(t,null,null);
					done.addAll(list);
					if (t.getLabel() != null) {
						for (Event evt : list) {
							instantiateLabel(evt.getLabel(), evt.getLabel().getParams());
						}
					}
				}
	

				
				s.getSynchronizations().clear();
				s.getSynchronizations().addAll(done);
				doneAll += done.size();
			}
		}

		if (nbskipped > 0) {
			getLog().info("On-the-fly reduction of False transitions avoided exploring " + nbskipped + " instantiations of transitions. Total transitions/syncs built is " + doneAll);
			// we might have destroyed labeled transitions that were called.
			Support nbpropagated = normalizeCalls(spec);
			if (nbpropagated.size() > 0) {
				toret.addAll(nbpropagated);
				toret.addAll(Simplifier.simplify(spec));
//				getLog().info("False transitions propagation removed an additional " + nbpropagated + " instantiations of transitions. total transiitons in result is "+ s.getTransitions().size());
			}
		}

		spec.getTypes().clear();
		ArrayList<TypeDeclaration> finished = new ArrayList<TypeDeclaration>(doneTypes);
		Collections.reverse(finished);
		spec.getTypes().addAll(finished);

		normalizeCalls(spec);

		for (Property prop : spec.getProperties()) {
			replaceConstParam(prop);
			if (prop.getBody() instanceof SafetyProp) {
				SafetyProp sp = ((SafetyProp) prop.getBody());
				BooleanExpression body = sp.getPredicate();
				sp.setPredicate(Simplifier.simplify(body));
			}
		}
		
		// We should no longer need these typedefs.
		spec.getTypedefs().clear();
		spec.getParams().clear();
		
		return toret;
	}

	public static void fuseEqualParameters(Transition t) {
		if (hasParam(t) && t.getParams().size() >= 1) {
			Map<BooleanExpression,List<Parameter>> guardedges= new LinkedHashMap<BooleanExpression, List<Parameter>>();

			if (addGuardTerms(t.getGuard(),guardedges)) {
				// We might have equality of two params in guard... refactor to only have one param
				fuseEqualParameters(t, guardedges);
			}
		}
	}

	public static void normalizeProperties (Specification spec) {
		if (spec.getTypes().size() == 1  && ! spec.getProperties().isEmpty()) {
			Map<String,VarDecl> map = new HashMap<>();
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getTypes().get(0);
			for (Variable var : gal.getVariables()) {
				map.put(var.getName(), var);
			}
			for (ArrayPrefix var : gal.getArrays()) {
				map.put(var.getName(), var);
			}
			for (Property prop:spec.getProperties()) {
				for (TreeIterator<EObject> ti = prop.eAllContents() ; ti.hasNext() ; ) {
					EObject obj = ti.next();
					if (obj instanceof VariableReference) {
						VariableReference vref = (VariableReference) obj;
						VarDecl ind = map.get(vref.getRef().getName());
						if (ind == null && vref.getIndex() != null) {
							ind = map.get(vref.getRef().getName()+"_"+evalConst(vref.getIndex()));
							((VariableReferenceImpl)vref).basicSetIndex(null, null);
						}
						vref.setRef(ind);
					}
				}
			}
		}		
	}
	
	private static void separateParameters(TypeDeclaration type) {

		if (type instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) type;
			separateParameters(gal);
		}
	}

	/** Ensures that all instance declarations refer to an appropriate type, and that array sizes are known. */
	private static void initializeParametersAndVariables(TypeDeclaration type) {		
		//first substitute const param by value
		// replace all parameters by values
		int nbsub = replaceConstParam(type);
		
		if (nbsub > 0) {
			Simplifier.simplifyAllExpressions(type);
		}
		
		if (type instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) type;
			gal.getParams().clear();
							
			for (Variable var : gal.getVariables()) {
				if (var.getValue() == null) {
					var.setValue(GF2.constant(0));
				}
			}

			for (ArrayPrefix array : gal.getArrays()) {
				EcoreUtil.replace(array.getSize(), Simplifier.simplify(array.getSize()));

				if (array.getValues().isEmpty()) {																		
					int size = ((Constant) array.getSize()).getValue();
					for (int i=0 ; i < size  ; i++) {
						array.getValues().add(GF2.constant(0));
					}
				}										
			}
		} else if (type instanceof CompositeTypeDeclaration) {
			CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) type;
			ctd.getParams().clear();
		}
		
		
	}
	
	private static int replaceConstParam(EObject parent) {
		int nbsub = 0;
		for (TreeIterator<EObject> it = parent.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam() instanceof ConstParameter) {
					EcoreUtil.replace(obj, GF2.constant(((ConstParameter) pr.getRefParam()).getValue()));
					nbsub++;
				}
			}
		}
		return nbsub;
	}

	public static int normalizeCalls(GALTypeDeclaration gal) { 
		Map<String,Label> map = new HashMap<String, Label>();
		for (Transition t : gal.getTransitions()) {
			if (t.getLabel() != null) {
				instantiateLabel(t.getLabel(), t.getLabel().getParams());
				if ( ! map.containsKey(t.getLabel().getName()) ) {
					map.put(t.getLabel().getName(), t.getLabel());
				}
			}
		}
		if (map.isEmpty()) {
			return 0;
		}
		List<Statement> totreat = new ArrayList<>();
		for (Transition t : gal.getTransitions()) {
			for (Statement st : t.getActions()) {
				Simplifier.collectStatements(st, x -> x instanceof SelfCall, totreat);
			}
		}
		
		List<Statement> toabort = new ArrayList<Statement>();
		for (Statement st : totreat) {
			SelfCall call = (SelfCall) st;
			instantiateCallLabel(call);
			String targetname = call.getLabel().getName();

			Label target = map.get(targetname);
			if (target == null) {
				getLog().finer("Could not find appropriate target for call to "+targetname+ " . Assuming it was false/destroyed, replaced by abort.");
				// We used to delete stuff but due to nested statements, we should abort.
				toabort.add(call);
				continue;
			}
			call.setLabel(target);
		}
		if (! toabort.isEmpty()) {
			getLog().info("Calls to non existing labels (possibly due to false guards) leads to "+ toabort.size()+ " abort statements.");
			for (Statement a : toabort) {
				EcoreUtil.replace(a, GalFactory.eINSTANCE.createAbort());				
			}
			int nbrem = Simplifier.simplifyAbort(gal.getTransitions());
			if (nbrem > 0) {
				// one more pass for propagation
				nbrem += normalizeCalls(gal);
			}
			return nbrem;
		}
		return 0;
	}

	public static Support normalizeCalls(Specification spec) { 
		Support toret = new Support();
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration)td;
				if (normalizeCalls(gal) > 0) {
					toret.addAll(Simplifier.simplify(gal));
				}
			}
		}
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) td;
				normalizeCalls(ctd);
			}
		}
		return toret;
	}

	/**
	 * 
	 * @param ctd composite to simplify
	 */
	private static void normalizeCalls(CompositeTypeDeclaration ctd) {
		List<Statement> toabort = new ArrayList<Statement>();
		Map<TypeDeclaration,Iterable<Label>> cache = new HashMap<TypeDeclaration, Iterable<Label>>();
		for (Synchronization sync : ctd.getSynchronizations()) {
			for (TreeIterator<EObject> it = sync.eAllContents() ; it.hasNext() ;) {
				EObject obj = it.next();
				if (obj instanceof LabelCall ) {
					LabelCall call = (LabelCall) obj;
					TypeDeclaration type ;
					Label called;
					if (obj instanceof InstanceCall) {
						InstanceCall icall = (InstanceCall) obj;
						instantiateCallLabel(icall);
						called = icall.getLabel();
						VariableReference ref = icall.getInstance();
						type = GalScopeProvider.getInstanceType(ref);
					} else { // if (obj instanceof SelfCall){
						type = ctd;
						called = ((SelfCall) obj).getLabel();
					}
					boolean ok = false;
					Iterable<Label> lablist = cache.get(type);
					if (lablist == null) {
						lablist = GalScopeProvider.getLabels(type);
						cache.put(type,lablist);
					}
					for (Label totry : lablist ) {
						if (called == totry) {
							ok = true;
							break;
						}
						if (called.getName().equals(totry.getName())) {
							
							call.setLabel(totry);
							ok=true;
							break;
						}
					}

					if (!ok) {
						toabort.add(call);
						getLog().finer("No target found in type "+ type.getName() +" of instance for call to "+ called.getName()+ ". Destroying synchronization "+sync.getName());
					}
					it.prune();
				}
			}

		}
		if (! toabort.isEmpty()) {
			getLog().info("Calls to non existing labels in type " + ctd.getName() + " (possibly due to false guards) leads to "+ toabort.size()+ " abort statements in synchronizations.");
			for (Statement a : toabort) {
				EcoreUtil.replace(a, GalFactory.eINSTANCE.createAbort());				
			}
			int nbrem = Simplifier.simplifyAbort(ctd.getSynchronizations());
			if (nbrem > 0) {
				// one more pass for propagation
				normalizeCalls(ctd);
			}
			
		}
		
	}

	/**
	 * Navigates over whole spec, replaces any ParamRef pr to a ConstParam cp, by 
	 * the Constant cp.getValue(). Then destroys the ConstParameters. 
	 */
	public static void instantiateTypeParameters(Specification spec) {
		// find any uses of instance declaration with redefined values
		Map<TypeDeclaration, List<InstanceDeclaration>> redefs = new HashMap<TypeDeclaration, List<InstanceDeclaration>>();
		for (TypeDeclaration type : spec.getTypes()) {
			if (type instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) type;
				for (InstanceDecl inst : ctd.getInstances()) {
					if (inst instanceof InstanceDeclaration) {
						InstanceDeclaration idecl = (InstanceDeclaration) inst;
						if (! idecl.getParamDefs().isEmpty()) {
							List<InstanceDeclaration> decls = redefs.get(idecl.getType());
							if (decls == null) {
								decls = new ArrayList<InstanceDeclaration>();
								redefs.put(idecl.getType(), decls);
							}
							decls.add(idecl);
						}
					}
				}
			}
		}
		// list types used in these redefs
		for (Entry<TypeDeclaration, List<InstanceDeclaration>> entry : redefs.entrySet()) {
			if (entry.getKey() instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) entry.getKey();

				// group similar type instantiations
				Map<String,List<InstanceDeclaration>> decls = new HashMap<String,List<InstanceDeclaration>>();
				for (InstanceDeclaration idecl : entry.getValue()) {
					String tname = TypeFuser.computeInstanceTypeString(idecl);
					List<InstanceDeclaration> list = decls.get(tname);
					if (list == null) {
						list = new ArrayList<InstanceDeclaration>();
						decls.put(tname,list);
					}
					list.add(idecl);
				}

				// do the actual instantiation of types
				for (Entry<String, List<InstanceDeclaration>> toinst : decls.entrySet()) {
					GALTypeDeclaration newtype = EcoreUtil.copy(gal);
					newtype.setName(toinst.getKey());
					InstanceDeclaration first = toinst.getValue().get(0);
					for (ParamDef pdef : first.getParamDefs()) {
						for (ConstParameter cp : newtype.getParams()) {
							if (cp.getName().equals(pdef.getParam().getName())) {
								cp.setValue(pdef.getValue());
								break;
							}
						}
					}
					spec.getTypes().add(0,newtype);

					for (InstanceDeclaration idecl : toinst.getValue()) {
						idecl.setType(newtype);
						idecl.getParamDefs().clear();
					}
				}
			} else {
				getLog().warning("Cannot handle parameters of composite type");
			}
		}
		if (! redefs.isEmpty()) {
			normalizeCalls(spec);
		}
		
		// replace all parameters by values
		List<ConstParameter> params = new ArrayList<ConstParameter>();
		for (TreeIterator<EObject> it = spec.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam() instanceof ConstParameter) {
					EcoreUtil.replace(obj, GF2.constant(((ConstParameter) pr.getRefParam()).getValue()));
				}
			} else if (obj instanceof ConstParameter) {
				params.add((ConstParameter) obj);
			}
		}
		instantiateForLoops(spec);
		// delete the parameters
		for (ConstParameter cp : params) {
			EcoreUtil.delete(cp);
		}
		
		initializeAllVariables (spec);
		
	}

	private static void initializeAllVariables(Specification spec) {
		for (TypeDeclaration type : spec.getTypes()) {
			
			if (type instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) type;
				
				for (Variable var : gal.getVariables()) {
					if (var.getValue() == null) {
						var.setValue(GF2.constant(0));
					}
				}
				
				for (ArrayPrefix array : gal.getArrays()) {
					EcoreUtil.replace(array.getSize(), Simplifier.simplify(array.getSize()));
					
					if (array.getValues().isEmpty()) {																		
						int size = ((Constant) array.getSize()).getValue();
						for (int i=0 ; i < size  ; i++) {
							array.getValues().add(GF2.constant(0));
						}
					}										
				}
				
			}
			
		}
	
	}

	/**
	 * Run through the system once, looking for "For" instructions. 
	 * Due to traversal order, we can then unroll them in reverse order to get most 
	 * deeply nested first. Just duplicate body as many times as needed + replace parameter by its value.
	 */
	private static void instantiateForLoops(EObject s) {
		List<For> forinstr = new ArrayList<For>();
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof For) {
				forinstr.add((For) obj);
			} else if (obj instanceof BooleanExpression || obj instanceof IntExpression || obj instanceof Assignment || obj instanceof NamedDeclaration) {
				it.prune();
			}
		}
		// treat deepest first
		Collections.reverse(forinstr);
		for (For forLoop : forinstr ) {
			Parameter p = forLoop.getParam();
			Bounds b= computeBounds(p.getType());

			// ok so we have min and max, we'll create max-min copies of the body statements
			// in each one we replace the param by its value
			// we cumulate into a temporary container
			List<Statement> bodies = new ArrayList<Statement>();
			for(int i = b.min; i <= b.max; i++){
				for (Statement asrc : forLoop.getActions()) {
					Statement adest = EcoreUtil.copy(asrc);
					instantiateParameter(adest, p, i);

					// add adest at end of bodies
					bodies.add(adest);
				}
			}

			// then, we want to substitute to the For instruction the sequence "bodies"
			// Because we do not currently have a nested "sequence" for a plain nested body Actions class,
			// this means deleting the For from its containing Elist (a sequene of actions) and inserting all instructions in bodies
			// Tricky part, identify where to insert the result
			Object oacts = forLoop.eContainer().eGet(forLoop.eContainingFeature());
			if (oacts instanceof EList<?>) {
				@SuppressWarnings("unchecked")
				EList<EObject> acts = (EList<EObject>) oacts;					
				int pos = acts.indexOf(forLoop);
				if (pos != -1) {
					acts.remove(pos);
					acts.addAll(pos, bodies);
				}
			}

		}


	}

	static Bounds computeBounds(TypedefDeclaration type) {

		int min = evalConst(type.getMin());
		int max = evalConst(type.getMax());
		if (min == -1 || max == -1) {
			throw new ArrayIndexOutOfBoundsException("Expected constant as both min and max bounds of type def "+type.getName());
		}
		return new Bounds(min, max);
	}

	public static int evalConst (IntExpression sexpr) {
		IntExpression expr = Simplifier.simplify(sexpr);
		if (expr instanceof Constant) {
			Constant cte = (Constant) expr;
			return cte.getValue();
		} else if (expr instanceof UnaryMinus && ((UnaryMinus)expr).getValue() instanceof Constant ) {
			Constant cte = (Constant) ((UnaryMinus)expr).getValue();
			return -cte.getValue();
		} else if (expr instanceof ParamRef  && ((ParamRef) expr).getRefParam() instanceof ConstParameter) {
				ConstParameter cp = (ConstParameter) ((ParamRef) expr).getRefParam();
				return cp.getValue();
		} else {
			throw new ArrayIndexOutOfBoundsException("Expected expression to resolve to a constant " + expr);
		}
	}

	public static <T extends Event> 
		List<T> instantiateParameters(T t2, Set<Variable> constvars, Map<ArrayPrefix, Set<Integer>> constantArrs) {

		Queue<BoundTransition<T>> todo  = new ArrayDeque<>();
		java.util.List<T> done  = new ArrayList<T>();
		double expected = 1;
		Map<TypedefDeclaration, Bounds> boundsPerType =new HashMap<>();
		if (hasParam(t2)) {
			// sort by increasing domain size
			Integer [] perm = new Integer [t2.getParams().size()];
			for (int i = 0 ; i < perm.length; i++) perm[i] = i;
			
			int [] sizes = new int [t2.getParams().size()];
			
			int i=0;
			for (Parameter p : t2.getParams()) {				
				Bounds b= boundsPerType.get(p.getType());
				if (b==null) {
					b = computeBounds(p.getType());
					boundsPerType.put(p.getType(), b);
				}
				sizes[i] = b.max - b.min + 1;
				expected *= sizes[i];
				i++;
			}
						
			Arrays.sort(perm, (a,b) -> Integer.compare(sizes[a], sizes[b]));
			
			List<Parameter> params = new ArrayList<>(t2.getParams().size());
			for (i = 0 ; i < perm.length; i++) params.add(t2.getParams().get(perm[i]));
			t2.getParams().clear();
			t2.getParams().addAll(params);
			
			
			todo.add(new BoundTransition<>(t2,new ArrayList<>(t2.getParams())));
		} else {
			done.add(t2);
		}
		long time=System.currentTimeMillis();
		if (DEBUG >=2 || expected >= 1000000) {
			if (expected >= 2) {								
				getLog().info("Started instantiation of " + t2.getName() + "(" + t2.getParams().stream().map(p->p.getName()).collect(Collectors.toList())  +") into " + expected + " transitions.");
			}
		}
		
		while (! todo.isEmpty()) {
			
			BoundTransition<T> bt = todo.poll();
			T t = bt.trans;
			Parameter p = bt.params.get(0);
			Bounds b= boundsPerType.get(bt.params.get(0).getType());
			// ok so we have min and max, we'll create max-min copies of the body statements
			// in each one we replace the param by its value
			// we cumulate into a temporary container
			for(int i = b.min; i <= b.max; i++){
				EventCopier copier = new EventCopier(p,i,constvars,constantArrs);
				T tcopy = (T) copier.doSwitch(t);
				List<Parameter> pcopy = new ArrayList<>(bt.params);
				
				Parameter param = pcopy.remove(0);
				if (tcopy instanceof Transition) {
					Transition tr = (Transition) tcopy;
					
					// avoid producing copies for False transitions.
					if (tr.getGuard() instanceof False) {						
						continue;
					}	
				} 
								
				if (tcopy instanceof Synchronization) {
					Simplifier.simplifyAllExpressions(tcopy);
				}
				
				if (tcopy.getLabel() != null) {
					Simplifier.simplifyAllExpressions(tcopy.getLabel());
					// do this a posteriori, after all calls are safely instantiated
					// instantiateLabel(tcopy.getLabel(), tcopy.getLabel().getParams());
				}
								
				tcopy.setName(tcopy.getName()+"_"+ param.getName().substring(1) +i );
				if (! pcopy.isEmpty()) {
					todo.add(new BoundTransition<>(tcopy, pcopy));
				} else {
					done.add(tcopy);
				}
			}
		}
		
		nbskipped += expected - done.size();
		if (DEBUG >=1 || expected > 500) {
			getLog().info("Instantiating " + t2.getName() + t2.getParams().stream().map(p->p.getName()).collect(Collectors.toList())  +" into " + expected + " transitions. Finally " + done.size() + " transitions in " + (System.currentTimeMillis() -time) + "ms" );			
		}
		Simplifier.simplifyConstantIte(done);
		Simplifier.simplifyAbort(done);
		return done;
	}



	private static boolean hasParam(Event t) {
		return t.getParams()!=null && ! t.getParams().isEmpty();
	}

	private static int instantiateParameter(EObject src, AbstractParameter param, int value) {
		List<EObject> totreat = new ArrayList<EObject>();
		int sum = replaceParam(src, param, value,totreat);
		for (TreeIterator<EObject> it = src.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			sum += replaceParam(obj, param, value,totreat);
		}
		for (EObject obj : totreat) {
			if (obj instanceof SelfCall) {
				SelfCall call = (SelfCall) obj;				
				Label target = GF2.createLabel(call.getLabel().getName());
				instantiateLabel(target, call.getParams());
				call.setLabel(target);
			} else if (obj instanceof InstanceCall) {
				InstanceCall call = (InstanceCall) obj;
				Label target = GF2.createLabel(call.getLabel().getName());
				instantiateLabel(target, call.getParams());
				call.setLabel(target);
			}
		}
		return sum + totreat.size();
	}

	private static int replaceParam(EObject src, AbstractParameter param,
			int value, List<EObject> totreat) {
		if (src instanceof ParamRef) {
			ParamRef pr = (ParamRef) src;
			if (pr.getRefParam().getName().equals(param.getName())) {
				EcoreUtil.replace(src, GF2.constant(value));
				return 1;
			}
		}
		if (src instanceof SelfCall || src instanceof InstanceCall) {
			totreat.add(src);
		}
		return 0;
	}



	static void instantiateLabel(Label label, EList<IntExpression> params) { 
		if (params.isEmpty())
			return;
		for (IntExpression p : params) {
			EcoreUtil.replace(p,Simplifier.simplify(p));
		}
		StringBuilder sb = new StringBuilder(label.getName());
		for (IntExpression par : params) {
			if (par instanceof Constant) {
				sb.append("_");
				sb.append(Integer.toString(((Constant) par).getValue()));
			} else {
				return;
			}			
		}
		label.setName(sb.toString());
		params.clear();
		//		String paramStr = param.getName();
		//		if (label != null) {
		//			label.setName( label.getName().replace(paramStr, paramStr.replace("$", "")+ Integer.toString(i)));
		//		}
	}


	private static void instantiateCallLabel(SelfCall call) {
		if (call.getParams().isEmpty())
			return;
		Label target = GF2.createLabel(call.getLabel().getName());
		instantiateLabel(target, call.getParams());
		if (! call.getLabel().getName().equals(target.getName())) {
			call.setLabel(target);
		}
	}

	private static void instantiateCallLabel(InstanceCall call) {
		if (call.getParams().isEmpty())
			return;
		Label target = GF2.createLabel(call.getLabel().getName());
		instantiateLabel(target, call.getParams());
		if (! call.getLabel().getName().equals(target.getName())) {
			call.setLabel(target);
		}
	}

	
	
	public static void fuseIsomorphicEffects (Specification spec) {
		// remap the label of the destroyed transitions to a transition with similar effect
		Map<Label,Label> labelMap = new HashMap<Label, Label>();

		int nbremoved = 0;
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				sortParameters(gal);
				nbremoved += fuseIsomorphicEffects(gal.getTransitions(), labelMap);
			} else if (td instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) td;
				nbremoved += fuseIsomorphicEffects(ctd.getSynchronizations(), labelMap);
			}
		}

		if (nbremoved > 0) {
			getLog().info("Removed a total of "+nbremoved + " redundant transitions.");
			for (TreeIterator<EObject> it = spec.eAllContents() ; it.hasNext() ;  ) {
				EObject obj = it.next();
				if (obj instanceof SelfCall) {
					SelfCall call = (SelfCall) obj;
					Label target = labelMap.get(call.getLabel()) ;
					Label dtarget = labelMap.get(target);
					while (dtarget != null) {
						target = dtarget;
						dtarget = labelMap.get(target);
					}
					if (target != null) {
						call.setLabel(target);
					}
				} else if (obj instanceof InstanceCall) {
					InstanceCall icall = (InstanceCall) obj;
					if (icall.getLabel() instanceof Label) {
						Label lab = (Label) icall.getLabel();
						Label target = labelMap.get(lab) ;
						Label dtarget = labelMap.get(target);
						while (dtarget != null) {
							target = dtarget;
							dtarget = labelMap.get(target);
						}
						if (target != null) {
							icall.setLabel(target);
						}
					}
				}
			}
		}
	}

	public static <T extends Event> int fuseIsomorphicEffects (List<T> events, Map<Label, Label> labelMap) {
		

		Map<String,List<Integer>> labmap = new HashMap<String,List<Integer>>();

		// pre scan all transitions to reduce number of comparisons necessary
		for (int i=0; i < events.size() ; ++i ) {
			T tr = events.get(i);
			String key = "";
			if (tr.getLabel() != null) {
				key = tr.getLabel().getName();
			} else {
				continue;
			}
			labmap.computeIfAbsent(key, k -> new ArrayList<>()).add(i);				
		}

		// collect indexes of transitions with unique label
		List<Integer> uniqueLabel = new ArrayList<Integer>();		
		for (Entry<String, List<Integer>> e: labmap.entrySet() ) {
			if (e.getValue().size()==1 && ! e.getKey().contains("$")) {
				uniqueLabel.addAll(e.getValue());
			}
		}
		Collections.sort(uniqueLabel);

		int nbremoved = 0;
		List<Integer> todrop = new ArrayList<Integer>();

		// find labeled events with a single action = self-call
		// event ev label "a" { self."b"; }
		// => destroy "ev", redirect "a" to "b".
		for (int i= uniqueLabel.size()-1 ; i >=0 ; i--) {
			int tid = uniqueLabel.get(i);
			T t1 = events.get(tid);
			if (t1.getActions().size() == 1 && t1.getActions().get(0) instanceof SelfCall) {
				todrop.add(tid);
				uniqueLabel.remove(i);
				labelMap.put(t1.getLabel(), ((SelfCall) t1.getActions().get(0)).getLabel());
				nbremoved++;								
			}
		}
		
		// fuse two transitions with unique label iff : they are identical up to renaming of parameters and label.
		// Destruction is performed at the end to avoid shifting transition indexes		
		// test all pairs
		for (int i=0; i < uniqueLabel.size() ; ++i ) {
			for (int j=i+1; j < uniqueLabel.size() ; ++j ) {
				T t1 = events.get(uniqueLabel.get(i));
				T t2 = events.get(uniqueLabel.get(j));

				if (	t1.getLabel() != null && t2.getLabel() != null
						&& t1.getActions().size() == t2.getActions().size()
						&& t1.getParams() !=null && t2.getParams() != null
						&& t1.getParams().size() == t2.getParams().size() ) {
					EList<Parameter> pl1 = t1.getParams();
					EList<Parameter> pl2 = t2.getParams();

					int size = pl1.size();
					boolean areCompat = true;
					for (int k = 0 ; k < size ; k++) {
						if (pl1.get(k).getType() != pl2.get(k).getType()) {
							areCompat = false;
							break;
						}
					}
					if (!areCompat)
						break;

					// looks good, labeled transitions, same number of parameters, with pair wise type match, same number of actions
					// attempt a rename
					String lab2name = t2.getLabel().getName();
					t2.getLabel().setName(t1.getLabel().getName());

					String t2name = t2.getName();
					t2.setName(t1.getName());

					// Attempt a rename + relabel.					
					// rename parameters
					List<String> pnames = new ArrayList<String>();
					for (int k = 0 ; k < size ; k++) {
						Parameter pk = t2.getParams().get(k);
						pnames.add(pk.getName());
						pk.setName(pl1.get(k).getName());
					}

					// test for identity : this test should be true if the two transitions actually have the same body
					if (EcoreUtil.equals(t1, t2)) {
						// So test is successful : we can happily discard t2, provided we update calls
						todrop.add(uniqueLabel.get(j));
						uniqueLabel.remove(j);
						labelMap.put(t2.getLabel(), t1.getLabel());
						// undo rename so trace in dropEvents remains readable.
						t2.setName(t2name);
						// to ensure correct position in t1/t2 loop
						j--;
						
						nbremoved ++;
					} else {
						// undo renames
						t2.setName(t2name);					
						t2.getLabel().setName(lab2name);
						for (int k = 0 ; k < size ; k++) {
							Parameter pk = t2.getParams().get(k);
							pk.setName(pnames.get(k));
						}
					}					
				}
			}
		}
		
		
		dropEvents(events, todrop);
		

		return nbremoved;
	}

	private static <T extends Event> void dropEvents(List<T> events, List<Integer> todrop) {
		if (! todrop.isEmpty()) {
			Collections.sort(todrop, Collections.reverseOrder());
			StringBuffer sb = new StringBuffer();
			for (Integer trindex : todrop) {
				if (getLog().getLevel() == Level.FINE) {
					sb.append(events.get(trindex).getName()+ ",");
				}
				events.remove(trindex.intValue());
			}
			getLog().fine("Dropping " + todrop.size() + " events :" + sb.toString());
		}
	}

	public static void separateParameters(Specification spec) {

		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration system = (GALTypeDeclaration) td;


				// sortParameters(system);


				separateParameters(system);

			}
		}
		fuseIsomorphicEffects(spec);
		normalizeCalls(spec);

	}

	private static void separateParameters(GALTypeDeclaration gal) {
		List<Transition> toadd = new ArrayList<Transition>();
		
		Map<String, Support> labread = new HashMap<String, Support>();
		Map<String, Support> labwrite= new HashMap<String, Support>();
		Map<String, List<Transition>> labmap = SupportAnalyzer.computeLabelMap(gal);		
		for (Transition t : gal.getTransitions()) {
			SupportAnalyzer.computeTransitionSupport(t, new Support(), new Support(), labread, labwrite, labmap);
		}
		
		//		if (Simplifier.simplifyPetriStyleAssignments(system)) {
		for (Transition t : gal.getTransitions()) {
			if (hasParam(t) && t.getParams().size() >= 1) {
				Map<BooleanExpression,List<Parameter>> guardedges= new LinkedHashMap<BooleanExpression, List<Parameter>>();
				Map<Statement,List<Parameter>> actionedges= new LinkedHashMap<Statement, List<Parameter>>();

				if (addGuardTerms(t.getGuard(),guardedges)) {


					// We might have equality of two params in guard... refactor to only have one param
					fuseEqualParameters(t, guardedges);


					for (Statement a : t.getActions()) {
						List<Parameter> targets = grabParamRefs(a);
						targets.retainAll(t.getParams());
						actionedges.put(a, targets);
					}							

					// So we now have a hypergraph, with edges relating parameters that are linked through 
					// an action or guard condition


					// in general this graph is not quite enough : we also need to include in our reasoning
					// the transitive partial order resulting from constraints on guard terms that need to
					// be evaluated before certain statements.
					// If we ignore this, we may test some guard conditions AFTER the variables tested have been
					// updated, which messes up the semantics.
					Map<EObject, Set<EObject>> precedes = SupportAnalyzer.computePrecedence(guardedges.keySet(), actionedges.keySet(), labread, labwrite, labmap);
					for (Entry<BooleanExpression, List<Parameter>> entry : guardedges.entrySet()) {
						Set<EObject> set = precedes.get(entry.getKey());
						if ( set != null) {
							Set<Parameter> pres = new HashSet<Parameter>(entry.getValue());
							for (EObject obj : set) {
								List<Parameter> plist = actionedges.get(obj);
								pres.addAll(plist);
							}
							for (EObject obj : set) {
								List<Parameter> plist = actionedges.get(obj);
								plist.clear();
								plist.addAll(pres);
							}
							entry.setValue(new ArrayList<Parameter>(pres));
						}
					}
					//							
					for (Entry<Statement, List<Parameter>> entry : actionedges.entrySet()) {
						Set<EObject> set = precedes.get(entry.getKey());
						if ( set != null) {
							Set<Parameter> pres = new HashSet<Parameter>(entry.getValue());
							for (EObject obj : set) {
								List<Parameter> plist = actionedges.get(obj);
								pres.addAll(plist);
							}
							for (EObject obj : set) {
								List<Parameter> plist = actionedges.get(obj);
								plist.clear();
								plist.addAll(pres);
							}
							entry.setValue(new ArrayList<Parameter>(pres));
						}
					}
					////									if (Collections.disjoint(entry.getValue(),plist)) {
					////											
					////											if (entry.getValue().isEmpty()) {
					////												entry.getValue().addAll(plist);
					////											} else if (plist.isEmpty()) {
					////												plist.addAll(entry.getValue());
					////											} else {
					////												Set<Parameter> pres = new HashSet<Parameter>(plist);
					////												pres.addAll(entry.getValue());
					////												entry.getValue().clear();
					////												plist.clear();
					////												entry.getValue().addAll(pres);
					////												plist.addAll(pres);
					////												System.err.println("potential commutativity issue solved by fusing :"+pres );
					////											}
					////										}
					////									}
					////								}
					//								
					//							}


					// build a reverse map, with just simple edges to reason on the underlying graph.
					Map<Parameter, Set<Parameter>> neighbors = new LinkedHashMap<Parameter, Set<Parameter>>();
					for (Parameter p : t.getParams()) {
						neighbors.put(p, new HashSet<Parameter>());
					}
					for (List<Parameter> edge : guardedges.values()) {
						for (Parameter p1 : edge) {
							for (Parameter p2 : edge) {
								//if (p1 != p2)
								neighbors.get(p1).add(p2);
							}
						}

					}
					for (List<Parameter> edge : actionedges.values()) {
						for (Parameter p1 : edge) {
							for (Parameter p2 : edge) {
								//if (p1 != p2)
								neighbors.get(p1).add(p2);
							}
						}
					}

					// So neighbors now tells us who is connected and how strongly 
					Set<Parameter> used = new HashSet<Parameter>();
					for (Entry<Parameter, Set<Parameter>> entry : neighbors.entrySet()) {
						int nbnear = entry.getValue().size();
						Parameter param = entry.getKey();
						if (! used.contains(param)) {
							if (nbnear <= 2) {
								Parameter other = null;

								if (t.getLabel() != null && ! noparamInLabel(t.getLabel(),param)) {
									// getLog().info("Free parameter : " + param.getName() + " is used in label and cannot be separated.");

									// we'll mess with calls if we go ahead
									continue;
								}

								if (nbnear==0) {
									// this means the parameter is not used, in guard or actions of the transition
									// check whether it is used at all ?
									if (t.getLabel() == null || noparamInLabel(t.getLabel(),param)) {
										// Really unused !
										t.getParams().remove(param);
										continue;
									} else {
										// used only in label : forget about separation
										continue;
									}

								} else if (nbnear==1) {

									// a single parameter
									if (t.getParams().size() == 1) {
										// all actions use it
										if (allConcernParam(actionedges,guardedges,param)) {
											// we'll just create an empty caller shell if we go ahead
											break;
										}
									}
									//											getLog().info("Found a free parameter : " + param.getName() +" in transition " + t.getName());											
								} else {
									for (Parameter pother : entry.getValue()) {
										if (pother!=param)
											other = pother;
									}
									if (allConcernParam(actionedges,guardedges,param) && allConcernParam(actionedges,guardedges,other) ) {
										// we'll just create an empty caller shell if we go ahead
										continue;
									}
									
									//										if (neighbors.get(other).size() == 2) {
									//											getLog().info("Skipping parameter : " + param.getName());
									//											getLog().info("It is in binary relation with  : " + other.getName());
									//											continue;
									//										}
									//											getLog().info("Found a separable parameter : " + param.getName());
									//											getLog().info("It is related to : " + other.getName());
								}

								Transition sep = GalFactory.eINSTANCE.createTransition();
								sep.setName(t.getName()+param.getName().replace("$", ""));
								Map<Parameter,Parameter> paramMap = new HashMap<Parameter,Parameter>();
								for (Parameter p : entry.getValue()) {
									Parameter copy = EcoreUtil.copy(p);
									paramMap.put(p, copy);
									sep.getParams().add(copy);
								}


								BooleanExpression guard = GalFactory.eINSTANCE.createTrue();
								List<BooleanExpression> todrop = new ArrayList<BooleanExpression>();
								for (Iterator<Entry<BooleanExpression, List<Parameter>>> it = guardedges.entrySet().iterator() ; it.hasNext() ;) {
									Entry<BooleanExpression, List<Parameter>> guardelt = it.next();
									if (guardelt.getValue().contains(param)) {
										BooleanExpression elt =EcoreUtil.copy(guardelt.getKey()) ;										
										todrop.add(guardelt.getKey());
										guard = GF2.and(guard, elt);
									}
								}
								for (BooleanExpression be : todrop) {
									guardedges.remove(be);
								}
								sep.setGuard(guard);

								Set<Statement> toremove = new HashSet<Statement>();
								for (Iterator<Entry<Statement, List<Parameter>>> it = actionedges.entrySet().iterator() ; it.hasNext() ;) {
									Entry<Statement, List<Parameter>> actelt = it.next();
									if (actelt.getValue().contains(param)) {
										Statement elt =EcoreUtil.copy(actelt.getKey()) ; 
										sep.getActions().add(elt);
										toremove.add(actelt.getKey());
										//it.remove();
									}
								}
								for (Statement a : toremove) {
									actionedges.remove(a);
								}
								Simplifier.removeAll(t.getActions(), toremove);
								t.getParams().remove(param);


								// normalize refs
								for (TreeIterator<EObject> it = sep.eAllContents() ; it.hasNext() ; ) {
									EObject obj = it.next();
									if (obj instanceof ParamRef) {
										ParamRef pr = (ParamRef) obj;
										if (pr.getRefParam() instanceof Parameter) {
											Parameter pold = (Parameter) pr.getRefParam();
											Parameter pnew = paramMap.get(pold);
											if (pnew != null)
												pr.setRefParam(pnew);
										}
									}
								}

								Label lab ;
								if (nbnear==1) { 
									lab = GF2.createLabel(sep.getName());

								} else {
									//										used.add(other);	
									neighbors.get(other).remove(param);
									lab = GF2.createLabel(sep.getName());
									lab.getParams().add(GF2.createParamRef(other));
								}
								sep.setLabel(lab);
								toadd.add(sep);
								SelfCall call = GalFactory.eINSTANCE.createSelfCall();
								call.setLabel(lab);
								if (nbnear != 1) {
									call.getParams().add(GF2.createParamRef(other));											
								}
								t.getActions().add(0,call);
								actionedges.put(call, Collections.singletonList(other));

							} else {
								getLog().finer("Found a deeply bound parameter : " + entry.getKey().getName());
							}
						}
					}

					// rebuild t guard
					BooleanExpression guard =  GalFactory.eINSTANCE.createTrue();
					for (BooleanExpression be : guardedges.keySet()) {
						be = EcoreUtil.copy(be);
						guard = GF2.and(guard, be);
					}
					t.setGuard(guard);

				}
			}
			//					}
		}

		gal.getTransitions().addAll(toadd);
	}

	/**
	 * Scans a transition guard for equality of two parameters, and fuses their representation.
	 * @param t a transition, might be modified
	 * @param guardedges as returned by a successful addGuardTerm call
	 */
	private static void fuseEqualParameters(Transition t, Map<BooleanExpression, List<Parameter>> guardedges) {
		List<BooleanExpression> todel =new ArrayList<BooleanExpression>();

		for (Entry<BooleanExpression, List<Parameter>> ent : guardedges.entrySet()) {
			BooleanExpression term = ent.getKey();
			if (term instanceof Comparison) {
				Comparison cmp = (Comparison) term;

				if (cmp.getOperator()== ComparisonOperators.EQ && cmp.getLeft() instanceof ParamRef && cmp.getRight() instanceof ParamRef) {
					AbstractParameter p1 = ((ParamRef)cmp.getLeft()).getRefParam();
					AbstractParameter p2 = ((ParamRef)cmp.getRight()).getRefParam();
					// set guard term to true
					todel.add(cmp);
					// map all refs to p2 to p1
					for (TreeIterator<EObject> it = t.eAllContents(); it.hasNext() ; ) {
						EObject obj = it.next();
						if (obj instanceof ParamRef) {
							ParamRef pr = (ParamRef) obj;
							if (pr.getRefParam() == p2) {
								pr.setRefParam(p1);
							}
						}
					}
					// drop p2
					t.getParams().remove(p2);
					getLog().info("Fused parameters : " + p1.getName() +" and " + p2.getName() + " of transition " + t.getName());
				}
			}
		}

		if (!todel.isEmpty()) {
			for (BooleanExpression be : todel) {
				EcoreUtil.replace(be, GalFactory.eINSTANCE.createTrue());
			}
			todel.clear();
			guardedges.clear();
			t.setGuard(Simplifier.simplify(t.getGuard()));
			addGuardTerms(t.getGuard(), guardedges);
		}
	}


	private static boolean noparamInLabel(Label label, Parameter param) {
		for (TreeIterator<EObject> it = label.eAllContents()  ; it.hasNext();) {
			EObject obj = it.next();
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam()== param) {
					return false;					
				}
			}
		}
		return true;
	}

	private static boolean allConcernParam(
			Map<Statement, List<Parameter>> actionedges,
			Map<BooleanExpression, List<Parameter>> guardedges, Parameter param) {
		// is every action for param ?
		for (Entry<Statement, List<Parameter>> ae : actionedges.entrySet()) {
			if (!ae.getValue().contains(param)) {
				return false;
			}
		}
		// is every term of guard for param ?
		for (Entry<BooleanExpression, List<Parameter>> ae : guardedges.entrySet()) {
			if (!ae.getValue().contains(param)) {
				return false;
			}
		}
		// getLog().info("Free parameter : " + param.getName() + " is isolated.");

		return true;
	}


	private static void sortParameters(GALTypeDeclaration system) {
		// sorting parameters helps identify repeated structures.
		for (Transition t : system.getTransitions()) {
			if (t.getParams() != null) {
				List<Parameter> plist = new ArrayList<Parameter>(t.getParams());
				Collections.sort(plist, new Comparator<Parameter>() {

					@Override
					public int compare(Parameter p1, Parameter p2) {
						int tc= p1.getType().getName().compareTo(p2.getType().getName());
						if (tc != 0 )
							return tc;
						return p1.getName().compareTo(p2.getName());
					}
				});
				t.getParams().clear();
				t.getParams().addAll(plist);
			}
		}
	}

	/**
	 * Check that guard is a conjunction of conditions, and add the dependencies induced on parameters to them.
	 * @param guard
	 * @param guardedges
	 * @return
	 */
	private static boolean addGuardTerms(BooleanExpression guard,	Map<BooleanExpression, List<Parameter>> guardedges) {
		if (guard instanceof And) {
			And and = (And) guard;
			return addGuardTerms(and.getLeft(), guardedges) && addGuardTerms(and.getRight(), guardedges);				
		} else if (guard instanceof Comparison) {
			Comparison cmp = (Comparison) guard;

			List<Parameter> targets = grabParamRefs(cmp);

			guardedges.put(cmp, targets);
			return true;
		} else if (guard instanceof True) {
			return true;
		}

		return false;
	}

	private static List<Parameter> grabParamRefs(EObject cmp) {
		List<Parameter> targets = new ArrayList<Parameter>();
		for (TreeIterator<EObject> it = cmp.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam() instanceof Parameter) {
					if (!targets.contains(pr.getRefParam())) {
						targets.add((Parameter) pr.getRefParam());
					}
				}
			} 
		}
		return targets;
	}

	public static void instantiateParametersWithAbstractColors(Specification s) {


		instantiateTypeParameters(s);

		List<Parameter> params = abstractArraystoSingleCell(s);

		for (Parameter p : params) {
			EcoreUtil.delete(p);
		}

	}

	public static List<Parameter> abstractArraystoSingleCell (EObject s) {
		List<Parameter> params = new ArrayList<Parameter>();
		for (TreeIterator<EObject> it = s.eAllContents(); it.hasNext();) {
			EObject obj = it.next();

			if (obj instanceof ArrayPrefix) {
				ArrayPrefix ap = (ArrayPrefix) obj;
				ap.setSize(GF2.constant(1));
				int sum =0;				
				for (IntExpression e : ap.getValues()) {
					EcoreUtil.replace(e, Simplifier.simplify(e));
				}
				for (IntExpression e : ap.getValues()) {

					if (e instanceof Constant) {
						Constant cte = (Constant) e;
						sum += cte.getValue();
					}
				}
				ap.getValues().clear();
				ap.getValues().add(GF2.constant(sum));
				it.prune();
			} else if (obj instanceof VariableReference) {
				VariableReference av = (VariableReference) obj;
				if (av.getIndex() != null) {
					av.setIndex(GF2.constant(0));
					it.prune();
				}
			} else if (obj instanceof Parameter) {
				params.add((Parameter) obj);
			}
		}
		return params;
	}

	public static void clearTypedefs(Specification spec) {
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				gal.getTypedefs().clear();	
			} else if (td instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) td;
				ctd.getTypedefs().clear();
			}						
		}
		spec.getTypedefs().clear();
	}

	public static List<Transition> instantiateParameters(Transition tdec) {
		Set<Variable> constvars = new HashSet<Variable>();
		Map<ArrayPrefix, Set<Integer>> constantArrs = new HashMap<ArrayPrefix, Set<Integer>>();
		return instantiateParameters(tdec, constvars, constantArrs);
	}

}

class BoundTransition<T> {
	T trans;
	List<Parameter> params;
	public BoundTransition(T trans, List<Parameter> params) {
		this.trans = trans;
		this.params = params;
	}
}

class Bounds {
	int min;
	int max;
	public Bounds(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public String toString() {
		return "[" + min + "," + max +"]";
	}
}