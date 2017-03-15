package fr.lip6.move.promela.tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadPromelaFile {
	
	private File 			directory;
	private String			pattern;
	private File[]			files;
	
	// TODO: refactor ceci avec nouvelle API file de Java7?
	
	public ReadPromelaFile(File directory, String pattern){
		this.directory = directory;
		this.pattern = pattern;
		setPromelaFiles();
	}
	
	
	public static String readFileAsString(File filePath) throws IOException {
		byte[] buffer = new byte[(int) filePath.length()];
		BufferedInputStream f = null;
		try {
			f = new BufferedInputStream(new FileInputStream(filePath));
			f.read(buffer);
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException ignored) {
				}
		}
		return new String(buffer);
	}
	
	private void setPromelaFiles() throws RuntimeException{
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
                
	
	
	public File[] getFiles(){
		return files;
	}

}
