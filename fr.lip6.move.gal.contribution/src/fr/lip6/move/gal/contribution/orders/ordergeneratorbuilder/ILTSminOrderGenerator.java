package fr.lip6.move.gal.contribution.orders;

/**
 * INextBuilder 
 * gal2ltsmin -> PINS
 */
public interface ILTSminOrderGenerator extends IOrederGeneratorFactory{
	/**
	 * INextBuilder 
	 * gal2ltsmin -> PINS
	 */
	void configure ();
	/**
	 * pin2lts -r -rs -f
	 * parse la sortie --> orederfactory.create
	 */
	void compute();
}
