package fr.lip6.move.promela.togal.utils;

import java.io.IOException;

/**
 * <h3>Config of Dot production</h3>
 * 
 * Ressources: List of Dot property:
 * 
 * @attributes http://www.graphviz.org/content/attrs
 * @shape: http://www.graphviz.org/doc/info/shapes.html
 * @coloNames: http://www.graphviz.org/doc/info/colors.html
 * @arrowShape http://www.graphviz.org/content/arrow-shapes
 */
public class DotConfig {

	/**
	 * Entrée de boucle
	 */
	public static String LOOP_SHAPE = "tripleoctagon";
	public static String LOOP_FILLCOLOR = "palegreen:spinggreen";
	
	public static String LABEL_COLOR = "dodgerblue4";
	
	public static String IF_SHAPE = "doubleoctagon";
	public static String IF_FILLCOLOR = "paleturquoise:paleturquoise2";
	//LATER: préférences
	
	/**
	 * Booléen pour savoir si programme installé sur le système Déterminé au
	 * chargement de la classe
	 */
	private static boolean dotProgram = false;
	
	public static boolean isDotLaunchable(){
		return dotProgram;
	}

	// Establish whether dot is installed on the machine or not
	static {
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("which dot"); // Bonux: put up static

			pr.waitFor();
			dotProgram = (pr.exitValue() == 0);

			if (!dotProgram) {
				System.err
						.println("Les graphes ne pourront être générés, dot(graphviz) n'étant pas installé sur cette machine");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IOException e) {
			// causé par erreur du processus (windows.
			e.printStackTrace();
			System.err
					.println("Les graphes ne pourront être générés (not Unix OS)");
		}

	}

	
	
}
