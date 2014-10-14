package fr.lip6.move.promela.togal.transform;

import static fr.lip6.move.promela.togal.utils.TransfoUtils.illegal;
import static fr.lip6.move.promela.utils.PromelaUtils.getIntValue;
import static fr.lip6.move.promela.utils.PromelaUtils.makePmlInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.promela.promela.Active;
import fr.lip6.move.promela.promela.ChanVariable;
import fr.lip6.move.promela.promela.DefineMacro;
import fr.lip6.move.promela.promela.InitProcess;
import fr.lip6.move.promela.promela.MTypeDef;
import fr.lip6.move.promela.promela.MemVariable;
import fr.lip6.move.promela.promela.NamedProcess;
import fr.lip6.move.promela.promela.ProcessDefinition;
import fr.lip6.move.promela.promela.PromelaSpecification;
import fr.lip6.move.promela.promela.Run;
import fr.lip6.move.promela.promela.VariableDeclaration;
import fr.lip6.move.promela.togal.transform.environment.Environment;
import fr.lip6.move.promela.togal.transform.environment.EnvironmentAdapter;
import fr.lip6.move.promela.togal.transform.environment.GlobalEnvironment;
import fr.lip6.move.promela.togal.transform.representations.ProcessRepresentation;

public class PromelaToGALTransformer {

	private Map<ProcessDefinition, ProcessRepresentation> processReps = new HashMap<ProcessDefinition, ProcessRepresentation>();
	private List<Run> runs = new ArrayList<Run>();

	/**
	 * Convertion du MM Promela en MM Gal
	 * 
	 * @param ospec
	 *            La Spécification Promela initiale
	 * @param name
	 *            le nom a donner à la spécification
	 * @return La spécification GAL résultante
	 */
	public Specification transformToGAL(PromelaSpecification ospec, String name) {
		Specification spec = GalFactory.eINSTANCE.createSpecification();

		// Normalisation of specification (et contruction des runs)
		normalizePromelaSpec(ospec);

		// Creation of environments and converter
		GlobalEnvironment globals = new GlobalEnvironment();
		Environment env = new Environment(globals);
		Converter conv = new Converter(env);

		EnvironmentAdapter envAd = EnvironmentAdapter.getGlobalAdapter(globals,
				conv);
		GALTypeDeclaration gal = GalFactory.eINSTANCE
				.createGALTypeDeclaration();
		gal.setName(name.replaceAll("-", "_"));
		spec.getTypes().add(gal);

		/**
		 * Gestion des Déclarations globales
		 */
		for (DefineMacro macro : ospec.getMacros()) {
			envAd.handleMacro(macro);
		}

		for (MTypeDef m : ospec.getMtypes()) {
			envAd.handleMType(m);
		}

		for (VariableDeclaration ovar : ospec.getGlobalVariables()) {
			if (ovar instanceof MemVariable) {
				// TODO: à rafiner selon le type!! (gestion struct)
				envAd.handleMemVar((MemVariable) ovar);
				
			} else if (ovar instanceof ChanVariable) {
				envAd.handleChan((ChanVariable) ovar);
			} else {
				throw illegal("Shouldn't get there");
			}
		}

		// Ajout des variables au gal
		globals.addToGal(gal);

		/**
		 * Gestion des Process
		 */

		// Crée les pointeurs pour les process inactifs, et les ajoute au
		// converter.
		for (Run r : runs) {
			conv.registerRun(r);
		}

		for (ProcessDefinition p : ospec.getProcesses()) {
			// Init
			if (p instanceof InitProcess) {
				InitProcess np = (InitProcess) p;

				System.out.println("Init Process In There");
				ProcessRepresentation.createInitProcess(np, gal, conv);

			} else { // NamedProcess

				NamedProcess np = (NamedProcess) p;
				System.out.println("Named Process '" + np.getName()
						+ "' in There");

				ProcessRepresentation pr = ProcessRepresentation
						.createRepresentation(p, conv);

				// Stocke la structure (notamment pour gérer les runs)
				processReps.put(np, pr);

				// Initialise selon active.
				Active a = np.getActive();
				// MAYBE : modify grammar. (suppress structure)

				// si pas actif on skip
				if (a == null)
					continue;

				// instancie le process le nombre de fois nécessaire.
				int nbInst = getIntValue(a.getActiveVal());
				System.out.println("Instantiation de " + np.getName() + " "
						+ nbInst + " fois");
				for (int i = 0; i < nbInst; i++) {
					pr.instantiate(gal);
				}
			}
		} // end for

		// Gère les processus initiallement non actifs.
		for (Run r : runs) {
			ProcessDefinition pd = r.getProcess();
			ProcessRepresentation pr = processReps.get(pd);
			pr.instantiate(gal, true);
		}

		return spec;

	}

	/**
	 * Extend as needed to normalize the input spec and avoid limit cases.
	 * 
	 * Also Used to Collect the run needs.
	 * 
	 * @param ospec
	 *            La Spécification Promela
	 */
	private void normalizePromelaSpec(PromelaSpecification ospec) {

		for (TreeIterator<EObject> it = ospec.eAllContents(); it.hasNext();) {
			EObject obj = it.next();

			// Normalise les actives
			if (obj instanceof NamedProcess) {
				NamedProcess np = (NamedProcess) obj;

				Active active = np.getActive();
				if (active != null && active.getActiveVal() == null) {
					active.setActiveVal(makePmlInt(1));
				}

				// Collectionne les runs
			} else if (obj instanceof Run) {
				Run r = (Run) obj;
				runs.add(r);

				// HERE

			}
		}
	}

}
