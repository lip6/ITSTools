package fr.lip6.move.divine.togal.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.divine.divine.Assign;
import fr.lip6.move.divine.divine.Channel;
import fr.lip6.move.divine.divine.ChannelDeclaration;
import fr.lip6.move.divine.divine.ConstantDeclaration;
import fr.lip6.move.divine.divine.DivineSpecification;
import fr.lip6.move.divine.divine.NumberLiteral;
import fr.lip6.move.divine.divine.Process;
import fr.lip6.move.divine.divine.Recv;
import fr.lip6.move.divine.divine.Send;
import fr.lip6.move.divine.divine.State;
import fr.lip6.move.divine.divine.Sync;
import fr.lip6.move.divine.divine.VariableDeclaration;
import fr.lip6.move.divine.divine.impl.DivineFactoryImpl;
import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Call;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.gal.instantiate.Instantiator;



public class DveToGALTransformer {


	private static final String SEP = "_";

	private Converter conv;
	private Interpreter inter;

	public GALTypeDeclaration transformToGAL(DivineSpecification divine, String name) {

		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
		String systemname = name.replaceAll("[-\\.]", SEP);
		gal.setName(systemname);
		conv = new Converter();
		inter = new Interpreter(); 

		buildGlobalVars(divine, gal);
		buildChannels(divine, gal);

		// on declare tous les process avant d'entrer dans la boucle car divine permet 
		//  de faire appel à un state d'un process dans un autre process
		declareProcesses(divine, gal);

		for (fr.lip6.move.divine.divine.Process proc : divine.getProcesses()) {
			buildLocalVars(proc, gal);
		}
		
		Set<fr.lip6.move.divine.divine.Transition> seen = new HashSet<fr.lip6.move.divine.divine.Transition>();
		// handle synchronizations
		for ( Entry<Channel, List<fr.lip6.move.divine.divine.Transition>> sendSet : senders.entrySet()) {
			Channel chan = sendSet.getKey();
			List<fr.lip6.move.divine.divine.Transition> sends = sendSet.getValue();
			List<fr.lip6.move.divine.divine.Transition> recvs = receivers.get(chan);
			seen.addAll(sends);
			seen.addAll(recvs);
			for (fr.lip6.move.divine.divine.Transition tsend :sends) {
				for (fr.lip6.move.divine.divine.Transition trecv : recvs) {
					Transition ttr = GalFactory.eINSTANCE.createTransition();
					ttr.setName(computeSyncName(tsend,trecv));
					ttr.setComment("/** Synchronization on channel "+ chan.getName()+ " of sender " + shortName(tsend) + " and receiver " + shortName(trecv)+ "*/");

					// guard is combination of guards
					BooleanExpression guard = computeGuard(tsend);
					guard = GF2.and(guard, computeGuard(trecv));
					ttr.setGuard(guard);
					
					// effects of sender
					if (tsend.getEffect() != null) {
						for (Assign as : tsend.getEffect().getAssignments()) {
							Actions tas = conv.convertAssign(as);
							ttr.getActions().add(tas);
						}
					}
					// assign target state
					ttr.getActions().add( setProcState(tsend.getDest()));
					

					// write into appropriate place
					ttr.getActions().add(
							GF2.createAssignment(  conv.convertVarAccess((((Recv) trecv.getSync()).getVarRef())), 
										   conv.convertToGalInt(((Send) tsend.getSync()).getExpression()))
										   );
					
					// effects of receiver
					if (trecv.getEffect() != null) {
						for (Assign as : trecv.getEffect().getAssignments()) {
							Actions tas = conv.convertAssign(as);
							ttr.getActions().add(tas);
						}
					}
					// assign target state
					ttr.getActions().add( setProcState(trecv.getDest()));
					
					
					gal.getTransitions().add(ttr);					
				}
			}
			
		}
		
		for (fr.lip6.move.divine.divine.Process proc : divine.getProcesses()) {			
			for (fr.lip6.move.divine.divine.Transition tr : proc.getTransitions()) {								
				if (seen.contains(tr)) {
					continue;
				}
				Transition ttr = GalFactory.eINSTANCE.createTransition();
				ttr.setName(computeName(proc,tr));
				ttr.setComment("/**" + proc.getName() + "(t" + (proc.getTransitions().indexOf(tr)) + ") : "  + tr.getSrc().getName() + "->" + tr.getDest().getName() + "*/"); 
				ttr.setGuard(computeGuard(tr));

				// assign target state
				Actions ass = setProcState(tr.getDest());
				ttr.getActions().add(ass);

				// build actions (transformation des effect vers gal)
				if (tr.getEffect() != null) {
					for (Assign as : tr.getEffect().getAssignments()) {
						Actions tas = conv.convertAssign(as);
						ttr.getActions().add(tas);
					}
				}

				// transformation des synchro
				if (tr.getSync() != null) {
					Label label = computeLabel(tr);
					ttr.setLabel(label);
				}

				gal.getTransitions().add(ttr);
			}

		}

		Instantiator.normalizeCalls(gal);	

		return gal;

	}

	private String computeSyncName(fr.lip6.move.divine.divine.Transition tsend,
									fr.lip6.move.divine.divine.Transition trecv) {
		return "sync"+SEP+"chan"+SEP+tsend.getSync().getChannel().getName()+SEP+shortName(tsend)+SEP+shortName(trecv);
	}

	private String shortName(fr.lip6.move.divine.divine.Transition t) {
		Process proc = (Process) t.eContainer();
		int index = proc.getTransitions().indexOf(t);
		return proc.getName()+index+SEP+t.getSrc().getName()+SEP+t.getDest().getName();
	}

	private Actions setProcState(State dest) {
		Process proc = (Process) dest.eContainer().eContainer();
		return GF2.createAssignment( GF2.createVariableRef(conv.getImage(proc)), GF2.constant( proc.getStateDeclaration().getStates().indexOf(dest)));
	}

	private BooleanExpression computeGuard(fr.lip6.move.divine.divine.Transition tr) {
		Process proc = (Process) tr.eContainer();
		// grab var describing proc state
		Variable pstate = conv.getImage(proc);
		VariableRef vref= GF2.createVariableRef(pstate);				
		// grab index of source state
		int index = proc.getStateDeclaration().getStates().indexOf(tr.getSrc());
		Constant valstate = GalFactory.eINSTANCE.createConstant();
		valstate.setValue(index);

		// build comparison : state == tr.src
		BooleanExpression guard = createComparison(vref,ComparisonOperators.EQ,valstate);

		if (tr.getGuard() != null) {
			// need to cumulate with test of source state
			BooleanExpression sguard = conv.convertToGalBool(tr.getGuard().getExpression());
			guard = GF2.and(sguard,guard);
		}

		return guard;
	}

	private void declareProcesses(DivineSpecification divine,
			GALTypeDeclaration gal) {
		for (fr.lip6.move.divine.divine.Process proc : divine.getProcesses()) {
			Variable pstate = GalFactory.eINSTANCE.createVariable();
			pstate.setName(proc.getName()+SEP+"state");

			State initState = proc.getStateDeclaration().getInitState();
			if (initState != null) {
				int index = proc.getStateDeclaration().getStates().indexOf(initState);
				Constant valstate = GalFactory.eINSTANCE.createConstant();
				valstate.setValue(index);
				pstate.setValue(valstate);
			}
			else
				pstate.setValue(galConstant(0));

			gal.getVariables().add(pstate);

			conv.declareStateVar(proc,pstate);
		}
	}

	private BooleanExpression createComparison(VariableRef vref,
			ComparisonOperators op, Constant valstate) {
		Comparison guard = GalFactory.eINSTANCE.createComparison();
		guard.setLeft(vref);
		guard.setOperator(op);
		guard.setRight(valstate);
		return guard;
	}

	// compute process's name
	private String computeName(fr.lip6.move.divine.divine.Process proc, fr.lip6.move.divine.divine.Transition tr) {
		return proc.getName() + SEP + "t" + (proc.getTransitions().indexOf(tr)+1) + SEP + tr.getSrc().getName() + "_" + tr.getDest().getName(); 
	}

	private IntExpression galConstant(int i) {
		Constant cte = GalFactory.eINSTANCE.createConstant();
		cte.setValue(i);
		return cte;
	}

	private void buildGlobalVars(DivineSpecification divine, GALTypeDeclaration gal) {

		//conv.clearGlobals(); //TODO

		// cas des constantes
		for (ConstantDeclaration constDecl : divine.getConstantsDecl()) {
			for (fr.lip6.move.divine.divine.Constant c : constDecl.getConstants()) {
				String constname = c.getName();
				ConstParameter cp = GalFactory.eINSTANCE.createConstParameter();
				cp.setName("$"+constname);
				IntExpression initValue = galConstant(0);
				int value = 0;
				if (c.getInitValue() != null){
					// Gal Constant must be initialized with integer
					NumberLiteral nl = new DivineFactoryImpl().createNumberLiteral();
					nl.setValue(inter.interpret(c.getInitValue()));
					initValue = conv.convertToGalInt(nl);
					if (initValue instanceof Constant) {
						Constant cte = (Constant) initValue;
						value = cte.getValue();
					} else {
						throw new UnsupportedOperationException("Initial values of constants should be constants");
					}
				}
				cp.setValue(value);
				gal.getParams().add(cp);

				ParamRef pref = GalFactory.eINSTANCE.createParamRef();
				pref.setRefParam(cp);
				conv.addGlobal(c, pref);
			}
		}

		for (VariableDeclaration vardecl : divine.getVariablesDecl()) {
			// cas de variable
			for (fr.lip6.move.divine.divine.Variable var : vardecl.getVariables()) {
				String varname = var.getName();
				Variable tvar = GalFactory.eINSTANCE.createVariable();
				tvar.setName("glob"+SEP+varname);

				IntExpression initValue = galConstant(0);
				if (var.getInitValue() != null){					
					initValue = conv.convertToGalInt(var.getInitValue()); 
				}
				tvar.setValue(initValue);
				gal.getVariables().add(tvar);

				VariableRef vr = GalFactory.eINSTANCE.createVariableRef(); 
				vr.setReferencedVar(tvar);
				conv.addGlobal(var, vr); 
			}

			// cas de tableau 
			for(fr.lip6.move.divine.divine.Array darray : vardecl.getArrays()) {

				ArrayPrefix gar = GalFactory.eINSTANCE.createArrayPrefix();
				gar.setName("glob"+SEP+darray.getName());
				gar.setSize(darray.getSize());
				IntExpression gvalue = GalFactory.eINSTANCE.createIntExpression();

				for (fr.lip6.move.divine.divine.Expression expr : darray.getInitValue()) {
					gvalue = conv.convertToGalInt(expr);
					gar.getValues().add(gvalue);
				}

				// si le tableau n'est pas initialisé on l'initialise
				if (gar.getValues().isEmpty()) 
					for (int i = 0; i < gar.getSize(); i++)
						gar.getValues().add(galConstant(0));

				gal.getArrays().add(gar);
				ArrayVarAccess gava = GalFactory.eINSTANCE.createArrayVarAccess();
				gava.setPrefix(gar);
				conv.addGlobal(darray, gava);
			}
		}
	}

	enum SyncType { PureSync, IntSync, UnusedSync };
	private Map<Channel,SyncType> syncTypes = new HashMap<Channel, DveToGALTransformer.SyncType>();
	private Map<Channel,List<fr.lip6.move.divine.divine.Transition>> senders = new HashMap<Channel, List<fr.lip6.move.divine.divine.Transition>>();
	private Map<Channel,List<fr.lip6.move.divine.divine.Transition>> receivers = new HashMap<Channel, List<fr.lip6.move.divine.divine.Transition>>();
	
	
	private void buildChannels(DivineSpecification divine, GALTypeDeclaration gal) {

		//initially, suppose all channels are Unused
		for (ChannelDeclaration chandecl : divine.getChannelsDecl()) {
			for (Channel channel : chandecl.getChannels()) {
				syncTypes.put(channel, SyncType.UnusedSync);
			}
		}
		// scan for type of channels
		for (TreeIterator<EObject> it = divine.eAllContents() ; it.hasNext();  ) {
			EObject o = it.next();
			if (o instanceof Sync) {
				Sync sync = (Sync) o;
				SyncType oldType = syncTypes.get(sync.getChannel());
				SyncType newType = SyncType.PureSync;
				if (sync instanceof Send) {
					Send send = (Send) sync;
					if (send.getExpression() != null) {
						newType = SyncType.IntSync;
						addToList(senders,sync.getChannel(),(fr.lip6.move.divine.divine.Transition)sync.eContainer());
					}
				}
				if (sync instanceof Recv) {
					Recv recv = (Recv) sync;
					if (recv.getVarRef() != null) {
						newType = SyncType.IntSync;
						addToList(receivers,sync.getChannel(),(fr.lip6.move.divine.divine.Transition)sync.eContainer());
					}
				}
				if (oldType == SyncType.UnusedSync) {
					syncTypes.put(sync.getChannel(), newType);
				} else {
					if (newType != oldType) {
						throw new RuntimeException("Send and receives inconsistent : in channel "+ sync.getChannel().getName() + " send/receiving ints which are not read or reading unwritten values.");
					}
				}				
			}
		}


		for (ChannelDeclaration chandecl : divine.getChannelsDecl()) {
			for (Channel channel : chandecl.getChannels()) {
				SyncType syncType = syncTypes.get(channel);
				if (syncType == SyncType.PureSync) {
					fr.lip6.move.gal.Transition gtransition =
							GalFactory.eINSTANCE.createTransition();
					gtransition.setName("chan"+SEP+channel.getName());

					// no guard
					gtransition.setGuard(GalFactory.eINSTANCE.createTrue());

					// label : a discrete transition
////					Label label = GalFactory.eINSTANCE.createLabel();
////					label.setName("dtrans");
//
//					gtransition.setLabel(label);

					// call : chan !
					Label sendlab = GalFactory.eINSTANCE.createLabel();
					sendlab.setName("Send"+SEP+channel.getName());
					Call sendcall = GalFactory.eINSTANCE.createCall();
					sendcall.setLabel(sendlab);
					gtransition.getActions().add(sendcall);

					// call : chan ?
					Label recvlab = GalFactory.eINSTANCE.createLabel();
					recvlab.setName("Recv"+SEP+channel.getName());
					Call recvcall = GalFactory.eINSTANCE.createCall();
					recvcall.setLabel(recvlab);
					gtransition.getActions().add(recvcall);

					gal.getTransitions().add(gtransition);
				} else if (syncType == SyncType.IntSync) {
					System.out.println("Channel "+ channel.getName() + " has integer type");
					
				}
			}
		}

	}

	private void addToList(
			Map<Channel, List<fr.lip6.move.divine.divine.Transition>> map,
			Channel chan, fr.lip6.move.divine.divine.Transition t) {
		List<fr.lip6.move.divine.divine.Transition> list = map.get(chan);
		if (list == null) {
			list = new ArrayList<fr.lip6.move.divine.divine.Transition>();
			map.put(chan,list);
		}
		list.add(t);
	}

	// calculer le label selon le type de la synchronisation
	private Label computeLabel(fr.lip6.move.divine.divine.Transition tr) {
		Label lab = GalFactory.eINSTANCE.createLabel();
		if (tr.getSync() != null) {
			String channelName = tr.getSync().getChannel().getName();
			if (tr.getSync() instanceof Send) {
				channelName = "Send"+SEP+channelName;
			} else {
				channelName = "Recv"+SEP+channelName;
			}
			lab.setName(channelName);
		}
		return lab;
	}

	private void buildLocalVars(Process process, GALTypeDeclaration gal) {

		// cas des variables
		for (VariableDeclaration vardecl : process.getVariablesDec()) {
			for (fr.lip6.move.divine.divine.Variable var : vardecl.getVariables()) {
				String varname = process.getName()+SEP+var.getName();
				Variable tvar = GalFactory.eINSTANCE.createVariable();
				tvar.setName(varname);

				IntExpression initValue = galConstant(0); //Instantiator.constant(0);
				if (var.getInitValue() != null){					
					initValue = conv.convertToGalInt(var.getInitValue()); 
				}
				tvar.setValue(initValue);
				gal.getVariables().add(tvar);

				VariableRef vr = GalFactory.eINSTANCE.createVariableRef(); 
				vr.setReferencedVar(tvar);
				conv.addLocal(var, vr); 
			}

			// cas de tableau 
			for(fr.lip6.move.divine.divine.Array darray : vardecl.getArrays()) {

				ArrayPrefix gar = GalFactory.eINSTANCE.createArrayPrefix();
				gar.setName(process.getName()+SEP+darray.getName());
				gar.setSize(darray.getSize());
				IntExpression gvalue = GalFactory.eINSTANCE.createIntExpression();

				for (fr.lip6.move.divine.divine.Expression expr : darray.getInitValue()) {
					gvalue = conv.convertToGalInt(expr);
					gar.getValues().add(gvalue);
				}

				// si le tableau n'est pas initialisé on l'initialise
				if (gar.getValues().isEmpty()) 
					for (int i = 0; i < gar.getSize(); i++)
						gar.getValues().add(galConstant(0));

				gal.getArrays().add(gar);
				ArrayVarAccess gava = GalFactory.eINSTANCE.createArrayVarAccess();
				gava.setPrefix(gar);
				conv.addLocal(darray, gava);
			}
		}
	}
}


