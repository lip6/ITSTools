package fr.lip6.ltl.tgba.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.structural.expr.AtomicPropManager;
import jhoafparser.parser.HOAFParser;

public class TGBAparserHOAF {
	
	public static TGBA parseFrom (String path, AtomicPropManager atoms) throws IOException {
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(path));

		TGBA ret = parseFrom(fis, atoms);

		fis.close();
		return ret;
	}
	
	public static TGBA parseFrom (InputStream fis, AtomicPropManager atoms) throws IOException {
		HOAtoTGBAConsumer hoa2tgba = new HOAtoTGBAConsumer(atoms);
		HOAFParser.parseHOA(fis, hoa2tgba);
		return hoa2tgba.getTGBA();
	}
}
