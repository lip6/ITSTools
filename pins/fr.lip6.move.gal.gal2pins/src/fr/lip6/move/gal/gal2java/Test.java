package fr.lip6.move.gal.gal2java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.semantics.Alternative;
import fr.lip6.move.gal.semantics.Determinizer;
import fr.lip6.move.gal.semantics.INext;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.serialization.SerializationUtil;

public class Test {

	
	public static void main(String[] args) {
		SerializationUtil.setStandalone(true);
		//Specification spec = SerializationUtil.fileToGalSystem("tests/philo-5-col.pnml.flat.gal");
		//Specification spec = SerializationUtil.fileToGalSystem("tests/philo-5-col.pnml.gal");
		
		//Specification spec = SerializationUtil.fileToGalSystem("tests/kanban5.gal");
		//Specification spec = SerializationUtil.fileToGalSystem("tests/csRepeat-2-col.pnml.flat.gal");
		 Specification spec = SerializationUtil.fileToGalSystem("tests/csRepeat-2-col.pnml.gal");
		//Specification spec = SerializationUtil.fileToGalSystem("tests/elevator.3.flat.gal");
		
		INextBuilder nb = INextBuilder.build(spec);
		
		List<INext> list = nb.getNextForLabel("");
		INext allTrans = Alternative.alt(list);
		
		long detsize= nb.getDeterministicNext().size();
		
		System.out.println("As composition (GAL) : "+list.size() + " Deterministic size : " + detsize); 
		
		IState init = new State(nb.getInitial());

		Set<IState> seen = ConcurrentHashMap.newKeySet(4096);
		List<IState> todo = new ArrayList<>();
		todo.add(init);
		seen.add(init);

		while (! todo.isEmpty()) {
			List<IState> nexttodo = new ArrayList<>();
			Successors nsb = new Successors(todo.parallelStream());
			allTrans.accept(nsb)		
			.forEach(s -> { if (seen.add(s)) nexttodo.add(s); });

			todo = nexttodo;

			System.out.println("seen :" + seen.size());
		}
		System.out.println("State count : " + seen.size());
	}
	
}