package fr.lip6.move.gal.contribution.orders;

public interface IOrederGeneratorFactory {
	IOrderGenerator build (IOrderHeuristic h);
}
