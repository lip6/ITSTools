package fr.lip6.move.gal.cegar.frontend;

import java.io.IOException;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.cegar.factory.ABSTRACTION_STRAT;
import fr.lip6.move.gal.cegar.interfaces.IAbstractor;
import fr.lip6.move.gal.cegar.interfaces.ICegarFactory;
import fr.lip6.move.gal.cegar.interfaces.IPropertyChecker;
import fr.lip6.move.gal.cegar.interfaces.IResult;
import fr.lip6.move.gal.cegar.workfolder.WorkFoldersRepository;

public class CegarFrontEnd {
	public static IResult processGal(Specification spec, String workFolder) throws IOException {
		System.out.println("work folder: " + workFolder);
		final String path = workFolder + "/work/";
		WorkFoldersRepository.getInstance().setWorkFolder(spec, path);
		
		ICegarFactory.getSingleton.configure(ABSTRACTION_STRAT.TRACE, path + "abstract.gal");
		// TODO strategy and depth are hard coded
		
		IAbstractor abstractor = ICegarFactory.getSingleton.createAbstractor(spec, spec.getProperties().get(0));				
		final IPropertyChecker cegar = ICegarFactory.getSingleton.createCegarChecker(abstractor);
		return cegar.check(spec, spec.getProperties().get(0));				
	}
}
