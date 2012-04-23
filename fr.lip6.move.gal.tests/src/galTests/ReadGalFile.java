package galTests;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadGalFile {
	
	private static File 			directory;
	private static String			pattern;
	private static File[]			files;
	
	public ReadGalFile(){
		super();
	}
	
	public ReadGalFile(File directory, String pattern){
		ReadGalFile.directory = directory;
		ReadGalFile.pattern = pattern;
		setGalFiles();
	}
	
	
	private static void setGalFiles() throws RuntimeException{
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
			throw new RuntimeException("No Gal files for test!");
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
