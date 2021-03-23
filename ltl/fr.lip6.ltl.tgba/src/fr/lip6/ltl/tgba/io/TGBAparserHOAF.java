package fr.lip6.ltl.tgba.io;

import java.io.FileInputStream;
import java.io.IOException;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.structural.expr.AtomicPropManager;
import jhoafparser.parser.HOAFParser;

public class TGBAparserHOAF {
	
	public static TGBA parseFrom (String path, AtomicPropManager atoms) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		
		HOAtoTGBAConsumer hoa2tgba = new HOAtoTGBAConsumer(atoms);
		HOAFParser.parseHOA(fis, hoa2tgba);

		fis.close();
		return hoa2tgba.getTGBA();
	}
}
