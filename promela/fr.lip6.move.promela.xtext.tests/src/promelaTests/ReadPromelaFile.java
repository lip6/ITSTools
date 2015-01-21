package promelaTests;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadPromelaFile {
	
	private static File 			directory;
	private static String			pattern;
	private static File[]			files;
	
	public ReadPromelaFile(){
		super();
	}
	
	// TODO: refactor ceci avec nouvelle API file de Java7?
	
	public ReadPromelaFile(File directory, String pattern){
		ReadPromelaFile.directory = directory;
		ReadPromelaFile.pattern = pattern;
		setPromelaFiles();
	}
	
	
	private static void setPromelaFiles() throws RuntimeException{
		final Pattern p = Pattern.compile("^" + pattern + "$");
		
		final FilenameFilter ff = new FilenameFilter() {
			@Override
			public boolean accept(File directory, String name) {
				final Matcher m = p.matcher(name);
				return m.matches();
			}
		};
		
		files = directory.listFiles(ff);
		
		if (files.length == 0){
			throw new RuntimeException("No Promela files for test!");
		}
		
		
		java.util.Arrays.sort(files, new java.util.Comparator<File>(){
			public int compare (File f1, File f2) {
				return f1.getName().compareTo(f2.getName());
			}
			
			@Override
            public boolean equals (Object comparator) {
                return false;
            }
		});
	}
                
	
	
	public static File[] getFiles(){
		return files;
	}

}
