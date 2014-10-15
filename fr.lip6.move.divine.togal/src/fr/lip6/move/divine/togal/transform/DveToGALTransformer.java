package fr.lip6.move.divine.togal.transform;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.divine.divine.Assign;
import fr.lip6.move.divine.divine.Channel;
import fr.lip6.move.divine.divine.ChannelDeclaration;
import fr.lip6.move.divine.divine.ConstantDeclaration;
import fr.lip6.move.divine.divine.DivineSpecification;
import fr.lip6.move.divine.divine.NumberLiteral;
import fr.lip6.move.divine.divine.Process;
import fr.lip6.move.divine.divine.Send;
import fr.lip6.move.divine.divine.State;
import fr.lip6.move.divine.divine.VariableDeclaration;
import fr.lip6.move.divine.divine.impl.DivineFactoryImpl;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Call;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
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
						
			Variable pstate = conv.getImage(proc);
			
			for (fr.lip6.move.divine.divine.Transition tr : proc.getTransitions()) {
				Transition ttr = GalFactory.eINSTANCE.createTransition();
				ttr.setName(computeName(proc,tr));

				// grab var describing proc state
				VariableRef vref= GalFactory.eINSTANCE.createVariableRef();
				vref.setReferencedVar(pstate);				

				// grab index of source state
				int index = proc.getStateDeclaration().getStates().indexOf(tr.getSrc());
				Constant valstate = GalFactory.eINSTANCE.createConstant();
				valstate.setValue(index);

				// build comparison : state == tr.src
				BooleanExpression guard = createComparison(vref,ComparisonOperators.EQ,valstate);

				if (tr.getGuard() != null) {
					// need to cumulate with test of source state
					BooleanExpression sguard = conv.convertToGalBool(tr.getGuard().getExpression());
					And and = GalFactory.eINSTANCE.createAnd();
					and.setLeft(sguard);
					and.setRight(guard);
					guard = and;
				}

				ttr.setGuard(guard);

				// assign target state
				Assignment ass = GalFactory.eINSTANCE.createAssignment();
				ass.setLeft(EcoreUtil.copy(vref));
				index = proc.getStateDeclaration().getStates().indexOf(tr.getDest());
				valstate = GalFactory.eINSTANCE.createConstant();
				valstate.setValue(index);
				ass.setRight(valstate);
				ttr.getActions().add(ass);

				// build actions (transformation des effect vers gal)
				if (tr.getEffect() != null) {
					for (Assign as : tr.getEffect().getAssignments()) {
						Assignment tas = GalFactory.eINSTANCE.createAssignment();
						
						// case variable = expression
						if (as.getVarRef() instanceof fr.lip6.move.divine.divine.VariableRef) {
							fr.lip6.move.divine.divine.VariableRef varRef = 
								(fr.lip6.move.divine.divine.VariableRef) as.getVarRef();
							VariableRef tref = (VariableRef) conv.getImage(varRef.getReferencedVar());
							tas.setLeft(EcoreUtil.copy(tref));
							tas.setRight(conv.convertToGalInt(as.getExpression()));
						}
						
						// case array[index] = expression
						else if (as.getVarRef() instanceof fr.lip6.move.divine.divine.ArrayVarAccess) {
							fr.lip6.move.divine.divine.ArrayVarAccess ava = 
									(fr.lip6.move.divine.divine.ArrayVarAccess) as.getVarRef();
							ArrayVarAccess tava = conv.getImage(ava.getPrefix());
							tava.setIndex(conv.convertToGalInt(ava.getIndex()));
							tas.setLeft(EcoreUtil.copy(tava));
							tas.setRight(conv.convertToGalInt(as.getExpression()));
						}
						
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

	private void buildChannels(DivineSpecification divine, GALTypeDeclaration gal) {
		
		for (ChannelDeclaration chandecl : divine.getChannelsDecl()) {
			for (Channel channel : chandecl.getChannels()) {
				fr.lip6.move.gal.Transition gtransition =
						GalFactory.eINSTANCE.createTransition();
				gtransition.setName("chan"+SEP+channel.getName());
				
				// no guard
				gtransition.setGuard(GalFactory.eINSTANCE.createTrue());
				
				// label : a discrete transition
				Label label = GalFactory.eINSTANCE.createLabel();
				label.setName("dtrans");
				
				gtransition.setLabel(label);
				
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
			}
		}
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


