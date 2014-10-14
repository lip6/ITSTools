package fr.lip6.move.promela.togal.transform.representations;

import static fr.lip6.move.promela.togal.utils.GALUtils.*;
import static fr.lip6.move.promela.togal.utils.TransfoUtils.*;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.promela.promela.Expression;
import fr.lip6.move.promela.promela.NamedProcess;
import fr.lip6.move.promela.promela.Run;
import fr.lip6.move.promela.promela.VariableDeclaration;
import fr.lip6.move.promela.togal.transform.Converter;

//MAYBE RENAME
public class RunRepresentation {

	// MOVE: factorise config class
	private static String SEP = "_";
	private static int nbRun = 0;

	final private Run run;
	final private NamedProcess procDef;
	final private String name;
	final private Variable pcVar;
	private boolean used;
	private boolean init;

	public RunRepresentation(Run run) {
		super();
		this.run = run;
		this.procDef = run.getProcess();
		this.name = procDef.getName() + SEP + "Run" + (++nbRun);

		this.pcVar = GalFactory.eINSTANCE.createVariable();
		pcVar.setName(name + SEP + "pcVar" + SEP);
		pcVar.setComment("/** @pcvar process" + name + " */");
		pcVar.setValue(makeGALInt(-1));
		this.used = false;
		this.init = false;
	}

	public Run getRun() {
		return run;
	}

	public NamedProcess getProcDef() {
		return procDef;
	}

	public String getName() {
		return name;
	}

	public Variable getPcVar() {
		return pcVar;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed() {
		this.used = true;
	}

	public List<Actions> makeInit(Converter c) {
		if (init)
			throw illegal("Cannot start same run twice");

		Assignment start = makeAssign(makeRef(pcVar), makeGALInt(0));
		start.setComment("/** @start process " + procDef.getName() + " */");

		// Draft. params doivent etre stocké en globaux.
//		// HERE: parametre setting
//		EList<VariableDeclaration> params = procDef.getParametres();
//		EList<Expression> args = run.getArgs();
//		Variable arg;
//		VariableDeclaration prm;
//		int nbParams = params.size();
//		for (int i = 0; i < nbParams; i++) {
//			prm = params.get(i);
//			// crée var accès
//			arg = GalFactory.eINSTANCE.createVariable();
//			
//			arg.setValue(c.convertInt(args.get(i))); 
//			// crée afactation
//
//		}

		init = true;
		return Collections.<Actions> singletonList(start);
	}

}
