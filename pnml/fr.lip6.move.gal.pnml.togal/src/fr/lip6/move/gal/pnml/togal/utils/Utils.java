package fr.lip6.move.gal.pnml.togal.utils;

public class Utils {
	public static String normalizeName(String text) {
		String res = text.replace(' ', '_');
		res = res.replace('-', '_');
		res = res.replace('/', '_');
		res = res.replace('*', 'x');
		return res;
	}

}
