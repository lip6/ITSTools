package fr.lip6.move.coloane.projects.its;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;

import fr.lip6.move.coloane.projects.its.expression.IEvaluationContext;
import fr.lip6.move.coloane.projects.its.obs.ISimpleObservable;
import fr.lip6.move.coloane.projects.its.variables.IModelVariable;

public interface ITypeDeclaration extends ISimpleObservable {

	/**
	 * Accessor, return the (unique in the types list) name of this type.
	 * 
	 * @return the name of this type
	 */
	public abstract String getTypeName();

	/**
	 * Update the type name, notify observers.
	 * 
	 * @param typeName
	 *            the new name
	 */
	public abstract void setTypeName(String typeName);

	/**
	 * Workspace path to file resource.
	 * 
	 * @return path to the resource
	 */
	public abstract String getTypePath();

	/**
	 * The resource this type is built upon.
	 * 
	 * @return the file resource of the coloane model
	 */
	public abstract URI getTypeFile();

	/**
	 * The formalism name of this type's graph.
	 * 
	 * @return qualified formalism name
	 */
	public abstract String getTypeType();

	/**
	 * Handle caching of computeLabels.
	 * 
	 * @return the interface (ITS action alphabet) of this type
	 */
	public abstract Collection<String> getLabels();

	/**
	 * Handle caching of computeVariables.
	 * 
	 * @return the interface (ITS action alphabet) of this type
	 */
	public abstract List<IModelVariable> getVariables();

	/**
	 * Attempt to resolve a name as a qualified variable. 
	 * e.g. V1.V2.X
	 * @param name the variable fully qualified name
	 * @return the corresponding variable or null if it could not be resolved.
	 */
	public abstract IModelVariable findQualifiedVariable(String name);

	/**
	 * Specifies if all the concepts of this type have an effective realization.
	 * 
	 * @return true for a basic type declaration
	 */
	public abstract boolean isSatisfied();

	/**
	 * Return the parent types list.
	 * 
	 * @return the parent types instance
	 */
	public abstract TypeList getTypeList();

	/**
	 * Return the evaluation context that allow to resolve all integer
	 * expressions in the model. side effect: load the attributes that use these
	 * int expressions if not done already.
	 * 
	 * @return the integer parameters of this type
	 */
	public abstract IEvaluationContext getParameters();

	
	/**
	 * Force to reload all model types from their respective files, ensuring
	 * that we are up to date w.r.t. changes outside this program.
	 * @throws IOException if any model files have disappeared, are corrupted etc...
	 */
	public abstract void reload() throws IOException;

	/**
	 * Clear any references to this type (see behavior in composite type decl.
	 * 
	 * @param t
	 *            the type to be removed
	 */
	public void unsetTypeDeclaration(ITypeDeclaration t) ;

	/**
	 * Returns a path relative to the TypeList
	 * @return a normalized path, w.r.t. the owning TypeList.
	 */
	public String getRelativePath();

	
}
