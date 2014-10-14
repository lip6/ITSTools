package fr.lip6.move.promela.togal.transform.representations;

import static fr.lip6.move.promela.togal.utils.DotConfig.*;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeAnd;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeGALInt;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeRef;
import static fr.lip6.move.promela.togal.utils.TransfoUtils.illegal;
import static fr.lip6.move.promela.togal.utils.TransfoUtils.unsupported;
import static fr.lip6.move.promela.utils.PromelaUtils.asInstruction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.promela.promela.Assert;
import fr.lip6.move.promela.promela.Assignment;
import fr.lip6.move.promela.promela.Atomic;
import fr.lip6.move.promela.promela.Break;
import fr.lip6.move.promela.promela.ChanVariable;
import fr.lip6.move.promela.promela.Condition;
import fr.lip6.move.promela.promela.Expression;
import fr.lip6.move.promela.promela.Goto;
import fr.lip6.move.promela.promela.InitProcess;
import fr.lip6.move.promela.promela.Instruction;
import fr.lip6.move.promela.promela.Iteration;
import fr.lip6.move.promela.promela.Label;
import fr.lip6.move.promela.promela.LabeledInstruction;
import fr.lip6.move.promela.promela.MemVariable;
import fr.lip6.move.promela.promela.NamedProcess;
import fr.lip6.move.promela.promela.Not;
import fr.lip6.move.promela.promela.Print;
import fr.lip6.move.promela.promela.ProcessDefinition;
import fr.lip6.move.promela.promela.PromelaFactory;
import fr.lip6.move.promela.promela.PromelaPackage;
import fr.lip6.move.promela.promela.Receive;
import fr.lip6.move.promela.promela.Selection;
import fr.lip6.move.promela.promela.Send;
import fr.lip6.move.promela.promela.Sequence;
import fr.lip6.move.promela.promela.Skip;
import fr.lip6.move.promela.promela.Step;
import fr.lip6.move.promela.promela.VariableDeclaration;
import fr.lip6.move.promela.togal.transform.Converter;
import fr.lip6.move.promela.togal.transform.environment.EnvironmentAdapter;
import fr.lip6.move.promela.togal.transform.environment.LocalEnvironment;

/**
 * Representation d'un processus Promela, sorte de builder, recette pour
 * traduire instance process en ensemble de transitions GAL
 * 
 * @author adriean
 * 
 */
public class ProcessRepresentation {

	// MOVE: factorise config class
	private static String SEP = "_";

	private int nbInstantion;
	private final ProcessDefinition processDef;
	private final Set<PTransition> transitions = new HashSet<PTransition>();
	private final Set<VariableDeclaration> vars = new HashSet<VariableDeclaration>();
	private final Map<String, PC> labels = new HashMap<String, PC>();
	private final Map<PC, PC> gotoMap = new HashMap<PC, PC>();
	private final Stack<PC> breakStack = new Stack<PC>();

	private String name;

	private final AtomicInteger _nbPC = new AtomicInteger(0);
	private final Converter conv;

	private ProcessRepresentation(ProcessDefinition proc, Converter c) {
		nbInstantion = 0;
		this.name = "_Init";
		this.processDef = proc;
		this.conv = c;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public static ProcessRepresentation createRepresentation(
			ProcessDefinition proc, Converter conv) {
		// TODO PARAMETER
		ProcessRepresentation pr = new ProcessRepresentation(proc, conv);

		if (proc instanceof NamedProcess) {
			NamedProcess np = (NamedProcess) proc;
			pr.name = np.getName();

			// HERE modif: passer au dessus. dans le transformer
			// Parameter: just create variable nom identique.
			// Version basique (non finaux)
			pr.vars.addAll(np.getParametres());

		}

		Sequence s = proc.getCorps();

		// Collect variables
		pr.vars.addAll((Collection<? extends VariableDeclaration>) EcoreUtil
				.getObjectsByType(s.getSteps(),
						PromelaPackage.eINSTANCE.getVariableDeclaration()));

		PC currentPC = pr.new PCid();
		// Parcours Instruction construisant la représentation

		for (Instruction i : (Collection<? extends Instruction>) EcoreUtil
				.getObjectsByType(s.getSteps(), // TODO check
						PromelaPackage.eINSTANCE.getInstruction())) {
			// Parcours instructions de premier niveau
			currentPC = pr.process(i, currentPC);
		}

		// Goto Normalisation
		// Ecrase le PC, par le PC du label ciblé
		for (PTransition t : pr.transitions) {
			// Choix de boucler en premier sur transitions plutot que labels.
			PC tmp = pr.gotoMap.get(t.after);
			if (t.after instanceof PClabel) {
				PClabel pclab = (PClabel) t.after;
				t.after = pr.labels.get(pclab.label);
			} else if (tmp != null) {
				if (tmp instanceof PClabel) {
					// si c'est un Pc Label on récupère le Pc correspondant
					PClabel pclab = (PClabel) tmp;
					tmp = pr.labels.get(pclab.label);
					// LATER: copie natures
				}
				t.after = tmp;
			}
			
			//NOTE: normalisation hybride. (unification Goto Système éventuellement à revoi
			// n'ai pu supprimer le goto Map car parfois le PCLabel se perd dans les remontée de PC.
			
		}

		// Goto Tagging
		for (PC p : pr.labels.values())
			p.nature.add(PCTAG.LABEL);

		// Renvoi la représentation crée
		return pr;
	}

	/**
	 * Ajoute les GAL objet preprésentant une nouvelle instance active du
	 * Process GAL
	 * 
	 * @param gal
	 *            le fichier Gal en construction
	 */
	public void instantiate(GALTypeDeclaration gal) {
		instantiate(gal, false);
	}

	/**
	 * Ajoute les GAL objet preprésentant une nouvelle instance du Process GAL
	 * 
	 * Suis la recette de la représentation pour instantier le process.
	 * 
	 * @param gal
	 *            le fichier Gal en construction
	 * @param inactive
	 */
	public void instantiate(GALTypeDeclaration gal, boolean inactive) {
		// Proc Name : pin = Process Instance Name
		String pin;
		int trCpt = 0; // compteur des transitions

		// Program counter.
		Variable pcVar;

		if (inactive) {
			RunRepresentation rr = conv.retrieveRepresentation(processDef);
			pcVar = rr.getPcVar();
			pin = rr.getName();
		} else {
			pin = this.name + SEP + nbInstantion;
			pcVar = GalFactory.eINSTANCE.createVariable();
			pcVar.setName(pin + SEP + "pcVar" + SEP);
			pcVar.setComment("/** @pcvar process " + pin + " */");
			pcVar.setValue(makeGALInt(0));
		}

		gal.getVariables().add(pcVar);

		LocalEnvironment locals = new LocalEnvironment();
		EnvironmentAdapter envAd = EnvironmentAdapter.getLocalAdapter(locals,
				conv, pin);

		// bind variables:
		for (VariableDeclaration vd : this.vars) {
			// NOTE: DRY (idem que globals dans PromToGal
			if (vd instanceof MemVariable) {
				// TODO: à rafiner selon le type!!
				envAd.handleMemVar((MemVariable) vd);
			} else if (vd instanceof ChanVariable) {
				envAd.handleChan((ChanVariable) vd);
			} else
				throw illegal("Noting else is a VD!! :" + vd);

		}
		conv.getEnv().setLocal(locals);
		locals.addToGal(gal);

		/*
		 * Traduction de l'ensemble des instructions sous la forme de transition
		 */
		for (PTransition t : this.transitions) {
			Transition curT = GalFactory.eINSTANCE.createTransition();

			// nom forme __pin__t42__from_2_to_5

			curT.setName(pin + SEP + SEP + "t" + (trCpt++) + SEP + SEP + "from"
					+ SEP + t.before.id + "_to_" + t.after.id);
			// @proctrans name s1->s2:
			curT.setComment("/** @proctrans " + pin + "   " + t.before.id
					+ " -> " + t.after.id + " : " + t.classType + " */");
			// TODO: get Label
			// Old one:
			// curT.setComment("/** Process " + pin + " transition <"
			// + t.classType + "> de " + t.before.id + " à " + t.after.id
			// + " */"); //

			// Création de la garde
			BooleanExpression currentGard = t.before.makePCGuard(pcVar);
			for (Expression e : t.extraCondition) {
				currentGard = makeAnd(currentGard, conv.convertBoolean(e));
			}

			BooleanExpression actionGuard = conv.getGuard(t.instr);
			if (!(actionGuard instanceof True))
				currentGard = makeAnd(currentGard, actionGuard);

			curT.setGuard(currentGard);

			// Création du corps, actions
			EList<Actions> acs = curT.getActions();
			for (Actions a : conv.convertInstr(t.instr)) {
				acs.add(a);
			}

			// Mise à jour du compteur PC
			acs.add(t.after.makePCUpdate(pcVar));

			// Ajout de la transition construite.
			gal.getTransitions().add(curT);

		} // fin for

		conv.getEnv().unsetLocal();
		// Incrémente nombre d'instantiation
		nbInstantion++;
	}

	public static void createInitProcess(InitProcess i, GALTypeDeclaration g,
			Converter conv) {
		ProcessRepresentation tmp = createRepresentation(i, conv);
		tmp.instantiate(g);
		System.out.println("Instantiated initialization process init.");
	}

	/**
	 * Fonction récursive d'analyse des Instructions
	 */

	// >> Valeurs par défault
	private PC process(Instruction i, PC initialPC) {
		return process(i, initialPC, null, new ArrayList<Expression>());
		// BEWARE should send
	}

	private PC process(Instruction i, PC initialPC, PC eventualFinal,
			List<Expression> conds) {

		// Instructions monoblock.
		if (i instanceof Assignment || i instanceof Send
				|| i instanceof Receive || i instanceof Print
				|| i instanceof Condition // CHECK
				|| i instanceof Assert) {
			// eventual final pour les fins de séquences.
			PC next = (eventualFinal == null) ? new PCid() : eventualFinal;
			PTransition tmp = new PTransition(initialPC, i, next, conds);
			transitions.add(tmp);
			return next;

		} else if (i instanceof Atomic) {
			Atomic a = (Atomic) i;
			PC next = (eventualFinal == null) ? new PCid() : eventualFinal;

			// Récupère un éventuel Goto dans le corps.
			Step todel = null;
			for (Step s : a.getCorps().getSteps()) {
				if (s instanceof Goto) {
					String lblname = ((Goto) s).getLabel().getName();
					next = new PClabel(lblname);
					// pas à mettre dans gotomap, directement géré.
					// flag suppression de la séquence
					todel = s;
				}
			}
			if (todel != null)
				a.getCorps().getSteps().remove(todel); // concurrent modif

			PTransition tmp = new PTransition(initialPC, i, next, conds);
			transitions.add(tmp);
			return next;

		} else if (i instanceof LabeledInstruction) {
			LabeledInstruction linst = (LabeledInstruction) i;
			Instruction inst = linst.getInstruction();
			for (Label lab : linst.getLabels()) {
				this.labels.put(lab.getName(), initialPC);
				initialPC.label = lab.getName();
			}
			
			PC tmp = process(inst, initialPC, eventualFinal, conds);
			return tmp;

		} else if (i instanceof Skip) {
			// MAYBE: empty step. (dual mode?)
			return initialPC;

		} else if (i instanceof Goto) {
			Goto gotoo = (Goto) i;
			// GET Label.
			String lblname = gotoo.getLabel().getName();
			PC lbl = new PClabel(lblname);
			// register that position initial actually is position lbl.
			gotoMap.put(initialPC, lbl);
			return lbl; // BEWARE
			// /////////////////
			// pb si seule instruction séquence..
			// Voir gestion dans options

		} else if (i instanceof Break) {
			// MiMicGoto? CHECK
			PC breakPC = breakStack.peek();
			return breakPC; // BEWARE

		} else if (i instanceof Selection) {
			Selection sel = (Selection) i;
			PC exitIf = (eventualFinal == null) ? new PCid() : eventualFinal;
			exitIf.nature.add(PCTAG.IFOUT);
			initialPC.nature.add(PCTAG.IFIN);

			EList<Sequence> opts = sel.getOptions();
			List<Expression> optConditions = new ArrayList<Expression>();

			// Boucle sur les Options
			for (Sequence opt : opts) {
				// Copie la liste...
				processOpt(opt, initialPC, exitIf, optConditions,
						new ArrayList<Expression>(conds));
				// Might lost the label...
			}

			// Traite l'eventuel Else
			Sequence els = sel.getElse();
			if (els != null) {
				processElseOpt(els, initialPC, exitIf, optConditions,
						new ArrayList<Expression>(conds));
			}
			return exitIf;

		} else if (i instanceof Iteration) {
			Iteration loop = (Iteration) i;

			PC exitLoop = (eventualFinal == null) ? new PCid() : eventualFinal;
			// Break is gonna be a stack
			breakStack.push(exitLoop);
			exitLoop.nature.add(PCTAG.LOOPOUT);
			initialPC.nature.add(PCTAG.LOOPIN);

			EList<Sequence> opts = loop.getOptions();
			List<Expression> optConditions = new ArrayList<Expression>();
			// Need a copy
			// Boucle sur les Options
			for (Sequence opt : opts) {
				processOpt(opt, initialPC, initialPC, optConditions,
						new ArrayList<Expression>(conds));
			}

			// Traite l'eventuel Else
			Sequence els = loop.getElse();
			if (els != null) {
				processElseOpt(els, initialPC, initialPC, optConditions,
						new ArrayList<Expression>(conds));
			}
			breakStack.pop();
			return exitLoop;
		}

		throw unsupported(i);

	}

	private PC processOpt(Sequence opt, PC initialPC, PC exitOpt,
			List<Expression> optConditions, List<Expression> conds) {

		ListIterator<Step> it = opt.getSteps().listIterator();

		PC nextInOpt = new PCid();
		Step nextStep = it.next();
		if (nextStep instanceof Condition) {
			Condition cond = (Condition) nextStep;

			optConditions.add(cond.getCond());
			conds.add(cond.getCond());
			if (it.hasNext())
				nextStep = it.next();
			else
				nextStep = PromelaFactory.eINSTANCE.createSkip();
		}

		Instruction instr = asInstruction(nextStep);

		if (nextStep instanceof Goto) {
			return processGoto(initialPC, conds, (Goto) nextStep);
		} else if (nextStep instanceof Break) {
			return processBreak(initialPC, conds, (Break) nextStep);
		}

		// Création première instruction
		if (!it.hasNext()) {
			nextInOpt = process(instr, initialPC, exitOpt, conds);
			// BEWARE (persmodif) // CHECK

		} else {
			nextInOpt = process(instr, initialPC, nextInOpt, conds);
		}
		// ajout transition traité appel recursif

		// Boucle sur les autres instructions de l'option!! les next.
		return processIntrList(it, nextInOpt, exitOpt);
	}

	// Assez proche du processOpt
	private PC processElseOpt(Sequence elsOpt, PC initialPC, PC exitIf,
			List<Expression> conditions, List<Expression> conds) {

		Iterator<Step> it = elsOpt.getSteps().listIterator();

		// Création première instruction
		PC nextInOpt = new PCid();

		Step nextStep = it.next();
		Instruction instr = asInstruction(nextStep);

		// Création Garde
		for (int i = 0; i < conditions.size(); i++) {
			Not negC = PromelaFactory.eINSTANCE.createNot();
			negC.setValue(conditions.get(i));
			conds.add(negC);
		}

		// Gestion des séquences à un élem avec Goto
		if (nextStep instanceof Goto) {
			return processGoto(initialPC, conds, (Goto) nextStep);
		} else if (nextStep instanceof Break) {
			return processBreak(initialPC, conds, (Break) nextStep);
		}

		if (!it.hasNext()) {
			nextInOpt = process(instr, initialPC, exitIf, conds);
		} else {
			nextInOpt = process(instr, initialPC, nextInOpt, conds);
		}

		// Boucle sur les autres instructions de l'option!! les next.
		return processIntrList(it, nextInOpt, exitIf);
	}

	// Gestion du Goto Break si qu'une instruction
	public PC processGoto(PC initialPC, List<Expression> conds, Goto gotoo) {
		String lblname = gotoo.getLabel().getName();
		PC lbl = new PClabel(lblname);
		// Create void instruction
		PTransition tmp = new PTransition(initialPC, gotoo, lbl, conds);
		transitions.add(tmp);
		return lbl;
	}

	public PC processBreak(PC initialPC, List<Expression> conds, Break br) {
		PC breakPC = breakStack.peek();
		PTransition tmp = new PTransition(initialPC, br, breakPC, conds);
		transitions.add(tmp);
		return breakPC;
	}

	private PC processIntrList(Iterator<Step> stepIt, PC first, PC last) {
		// à priori pas de condition
		PC currentPC = first;

		while (stepIt.hasNext()) {
			Step nextStep = stepIt.next();
			Instruction instr = asInstruction(nextStep);

			if (!stepIt.hasNext()) {
				return process(instr, currentPC, last,
						new ArrayList<Expression>());
				// CHECK
			} else {
				// récupère futur compteur
				currentPC = process(instr, currentPC);
			}
		}
		// si initialmeent rien à transférer
		return last;
	}

	// toDOt Générateur: (visualisation du processs!!!!
	public String toDot() {
		StringBuffer sb = new StringBuffer();
		toDot(sb);
		return sb.toString();
	}

	// HERE
	public void toDot(StringBuffer sb) {
		Set<PC> pcSet = new HashSet<ProcessRepresentation.PC>();
		sb.append("\ndigraph  " + this.name + " {\n");
		sb.append("// Liste des transitions\n\n");

		// Transitions
		for (PTransition t : transitions) {
			sb.append("    ").append(t.toDotEdge()).append("\n");

			if (t.before != null) {
				pcSet.add(t.before);
			} else {
				System.err.println("unexpected null value t.before");
			}
			if (t.after != null)
				pcSet.add(t.after);
			else {
				System.err.println("unexpected null value t.after");
			}
			// LATER: si pc.before ou after un in or exit. donné une forme
			// particulière?

			// LATER: group DO in subgraph!!
			// subgraph $name { style=...:color=...;label = ....; EdgeList!!
			// Pb: information perdue lors de la génération, metadonnée à
			// ajouter.
			// Garder lien, à l'intérieur séquenvce

		}

		sb.append("// Liste des etats\n\n");
		// Liste des EStats mis en forme
		int i = 0;
		SortedSet<PC> spcSet = new TreeSet<ProcessRepresentation.PC>(pcSet);
		// wasn't build at first, blocking on duplicates
		// need a comparator at instantiation if element not Comparable

		for (PC p : spcSet) {
			sb.append("    ").append(p.toDotState(i++)).append("\n");
		}

		sb.append("}");
	}

	/**
	 * Classe pour réprésenter les transitions promelas lien entre deux PC par
	 * une instruction
	 * 
	 */
	class PTransition {
		private static final String NONE = "none";
		private String classType;
		private PC before, after;
		private Instruction instr;
		private List<Expression> extraCondition;
		private String name;

		// MAYBE Goto flag

		public PTransition(PC before, Instruction instr, PC after, String name,
				List<Expression> conds) {
			super();
			this.before = before;
			this.after = after;
			this.instr = instr;
			this.name = name;
			String tmp = instr.getClass().getSimpleName();
			this.classType = tmp.substring(0, tmp.length() - 4);
			this.extraCondition = new ArrayList<Expression>();

			// Ajout des conditions
			extraCondition.addAll(conds);
		}

		public PTransition(PC before, Instruction instr, PC after,
				List<Expression> conds) {
			this(before, instr, after, NONE, conds);
		}

		public PTransition(PC before, Instruction instr, PC after) {
			this(before, instr, after, NONE, new ArrayList<Expression>());
		}

		public void addCondition(Expression e) {
			extraCondition.add(e);
		}

		// NO GETTER

		public String toDotEdge() {
			return before + "  ->  " + after + "  [ label = \""
					+ (!(name.equals(NONE)) ? "label:" + name + ":" : "")
					+ classType + "\" ];";
			// LATER: fact, improve detgails
		}

	}

	abstract class PC implements Comparable<PC> {
		// interface Markeur.
		String label = null;
		final int id;
		final Set<PCTAG> nature;

		private PC(int id) {
			this.id = id;
			this.nature = new HashSet<ProcessRepresentation.PCTAG>();
		}

		abstract BooleanExpression makePCGuard(Variable pc);

		abstract Actions makePCUpdate(Variable pc);

		public String toDotState() {
			return toDotState(id);
		}

		abstract String toDotState(int nb);

		@Override
		public int compareTo(PC o) {
			return this.id - o.id;
		}
	}

	class PCid extends PC { // Program Counter

		protected PCid() {
			super(_nbPC.getAndIncrement());
		}

		protected PCid(String label) {
			super(_nbPC.getAndIncrement());
			this.label = label;
		}

		@Override
		public String toDotState(int nb) {
			// nb: pour donner numéro particulier.
			StringBuffer sb = new StringBuffer();
			Set<String> styles = new HashSet<String>();
			String name;
			List<String> addAttributes = new ArrayList<String>();
			// MAYBE: properties?

			name = "Pc:" + nb;

			if (isLabel()) {
				name += (" : " + label);
				styles.add("bold");
				addAttributes.add("color = \"" + LABEL_COLOR + "\"");
			}

			if (isLoopIn()) {
				// CHECK combos
				styles.add("filled");
				addAttributes.add("fillcolor = \"" + LOOP_FILLCOLOR + "\"");
				addAttributes.add("shape = \"" + LOOP_SHAPE + "\"");
			} else if (isIfIn()) {
				styles.add("filled");
				addAttributes.add("fillcolor = \"" + IF_FILLCOLOR + "\"");
				addAttributes.add("shape = \"" + IF_SHAPE + "\"");
			}

			sb.append(this).append("[ label = \"").append(name).append("\"");

			Iterator<String> it = styles.iterator();
			if (it.hasNext()) {
				sb.append(", style = \"");
				// Apache Utils would have been utile...
				String tmp = it.next();
				while (it.hasNext()) {
					sb.append(tmp).append(",");
					tmp = it.next();
				}
				sb.append(tmp).append("\"");
			}
			for (String a : addAttributes) {
				sb.append(", ").append(a);
			}

			sb.append("]");

			return sb.toString();

		}

		public String toString() {
			return "PC_" + id;
		};

		boolean isLoopIn() {
			// for (PCTAG p : nature) {
			// if (p.equals(PCTAG.LOOPIN))
			// return true;
			// }
			// return false;
			return nature.contains(PCTAG.LOOPIN);
		}

		boolean isIfIn() {
			return nature.contains(PCTAG.IFIN);
		}

		boolean isLabel() {
			return label != null;
			// for (PCTAG p : nature) {
			// if (p.equals(PCTAG.LABEL))
			// return true;
			// }
			// return false;
		}

		// Ambivalent method?

		// Factories
		@Override
		BooleanExpression makePCGuard(Variable pc) {
			Comparison c = GalFactory.eINSTANCE.createComparison();
			c.setOperator(ComparisonOperators.EQ);

			// Crée var
			VariableRef vref = makeRef(pc);
			c.setLeft(vref);
			c.setRight(makeGALInt(this.id));
			return c;
		}

		@Override
		Actions makePCUpdate(Variable pc) {
			fr.lip6.move.gal.Assignment a = GalFactory.eINSTANCE
					.createAssignment();
			// Crée var
			VariableRef vref = makeRef(pc);
			a.setLeft(vref);
			a.setRight(makeGALInt(this.id));
			a.setComment("/**  @PCUpdate " + this.id + " */");
			return a;
		}

	}

	class PClabel extends PC {
		// TODO: adapt! (pour pas utiliser compteur!!
		// Quand meme appel à private?
		// HERE ;: test avec sisout
		public PClabel(String label) {
			super(-1);
			this.label = label;

		}

		@Override
		BooleanExpression makePCGuard(Variable pc) {
			throw illegal("Can't create PC Guard of label");
		}

		@Override
		Actions makePCUpdate(Variable pc) {
			throw illegal("Can't make PC update of label");
		}

		@Override
		String toDotState(int i) {
			throw illegal("Can't toDot of label");
		}

	}

	/**
	 * Tag pour colorer spécifiquement les branchements. LATER
	 */
	enum PCTAG {
		IFIN, IFOUT, LOOPIN, LOOPOUT, LABEL;
		// LATER: START, END

		// BEWARE: double in?

		public boolean isIn() {
			return this.equals(IFIN) || this.equals(LOOPIN);
		}

		public boolean isOut() {
			return this.equals(IFOUT) || this.equals(LOOPOUT);
		}

	}

}
