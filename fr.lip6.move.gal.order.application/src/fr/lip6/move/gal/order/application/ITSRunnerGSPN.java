package fr.lip6.move.gal.order.application;

import java.io.IOException;
import java.util.Set;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.application.ITSRunner;
import fr.lip6.move.gal.application.MccTranslator;
import fr.lip6.move.gal.process.CommandLine;

public class ITSRunnerGSPN extends ITSRunner {

	private String file;
	public ITSRunnerGSPN(String examination, MccTranslator reader, boolean doITS, boolean onlyGal, String workFolder,
			long timeout,String f) {
		super(examination, reader, doITS, onlyGal, workFolder, timeout);
		file=f;
	}
	
	
	@Override
	public void configure(Specification spec, Set<String> doneProps) throws IOException {
		super.configure(spec, doneProps);
		
		cl.addArg("--load-order");
		cl.addArg(file);
	}
	

}
