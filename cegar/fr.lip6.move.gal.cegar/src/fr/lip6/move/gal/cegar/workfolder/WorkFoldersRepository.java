package fr.lip6.move.gal.cegar.workfolder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import fr.lip6.move.gal.Specification;

/**
 * Utility class to set up folders
 * 
 * @author Anonymous
 *
 */
public class WorkFoldersRepository {
	private static WorkFoldersRepository instance;
	private final Map<Specification, String> workFolders;
	
	private WorkFoldersRepository() {
		workFolders = new HashMap<Specification, String>();
	}
	
	public static WorkFoldersRepository getInstance() {
		if (instance == null)
			instance = new WorkFoldersRepository();
		
		return instance;
	}
	
	public void setWorkFolder(Specification spec, String folder) {
		setUp(folder);
		workFolders.put(spec, folder);
	}
	
	public String getWorkFolder(Specification spec) {
		return workFolders.get(spec);
	}
	
	private void setUp(String path) {
		File file = new File(path);
		if( ! file.exists())
			file.mkdirs();
	}
}
