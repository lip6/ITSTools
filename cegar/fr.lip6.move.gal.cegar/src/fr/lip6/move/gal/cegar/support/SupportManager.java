package fr.lip6.move.gal.cegar.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.cegar.graph.Graph;
import fr.lip6.move.gal.cegar.interfaces.IGraph;
import fr.lip6.move.gal.cegar.interfaces.IPropertySupport;
import fr.lip6.move.gal.cegar.interfaces.IPropertySupports;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.gal.support.SupportAnalyzer;

public class SupportManager implements IPropertySupports {
	private final IGraph<Variable> connexityGraph;
	private final Specification spec;
	private final Map<Property, IPropertySupport> propSupports;
	
	public SupportManager(Specification spec) {
		this.spec = spec;
		this.connexityGraph = buildConnexityGraph();
		this.propSupports = new HashMap<Property, IPropertySupport>();
	}
	
	/**
	 * Compute the support based on the property with the specified depth.
	 */
	public IPropertySupport getPropertySupport(Property prop) {
		if ( ! this.propSupports.containsKey(prop)) {
			IPropertySupport ps = new PropertySupport(this, computeSupport(prop, 0), 0);
			this.propSupports.put(prop, ps);
		}
		return this.propSupports.get(prop);
	}
		
	public void refineSupport(Property prop, String failedTransition) throws InexistantTransitionException {
		Support toAdd = getTransitionGuardSupport(failedTransition);
		propSupports.get(prop).refine(toAdd);
	}

	public void refineSupport(Property prop) {
		IPropertySupport propSup = propSupports.get(prop);
		Support toAdd = computeSupport(prop, propSup.getIncreasedDepth());
		propSup.refine(toAdd);
	}
	
	protected Specification getSpecification() {
		return this.spec;
	}
		
	private IGraph<Variable> buildConnexityGraph() {
		IGraph<Variable> connexityGraph = new Graph<Variable>();
		List<List<Variable>> edges = new ArrayList<List<Variable>>();
		
		for (TypeDeclaration td : spec.getTypes()) {

			if (td instanceof GALTypeDeclaration) {

				GALTypeDeclaration gtd = (GALTypeDeclaration) td;

				for (Transition t : gtd.getTransitions()) {
					Support support = new Support();
					SupportAnalyzer.computeSupport(t, support);
					List<Variable> edge = SupportVariablesComputer.getVariablesFromSupport(support);
					edges.add(edge);
				}
			}
		}
		
		connexityGraph.setEdges(edges);
		
		return connexityGraph;
	}
		
	private Support getTransitionGuardSupport(String transName) throws InexistantTransitionException {
		Support transitionSupport = new Support();
		
		for (TypeDeclaration typeDeclaration : spec.getTypes()) {

			if (typeDeclaration instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration)typeDeclaration;
				
				for (Transition transition : gal.getTransitions()) {
					if (transition.getName().equals(transName)) {
						SupportAnalyzer.computeSupport(transition.getGuard(), transitionSupport);
						
						return transitionSupport;
					}
				}
				
				throw new InexistantTransitionException(transName);
			}
		}
		return null;
	}
		
	/**
	 * Compute the Property support for the specification at the specified depth.
	 * 
	 * @param specification
	 * @param depth
	 * @return
	 */
	private Support computeSupport(Property property, int depth) {		
		Support propertySupport = new Support();
		Support toKeep = new Support();
		
		
		Logger log = Logger.getLogger("fr.lip6.move.gal");

		SupportAnalyzer.computeSupport(property, propertySupport);
		
		log.fine("For property " + property.getName() + " found Support " + propertySupport);
		
		for (Variable variable : SupportVariablesComputer.getVariablesFromSupport(propertySupport)) {
			for(Variable componentVariable : connexityGraph.getConnectedComponent(variable, depth))
				toKeep.add(componentVariable);
		}
		
		log.info("Computing abstraction support, depth " + depth + ", found support " + toKeep.size());
		log.fine("Support :" + toKeep);
		
		return toKeep;
	}	

}
