package fr.lip6.ltl.tgba.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import jhoafparser.parser.HOAFParser;

public class TGBAparserHOAF {
	
	public static TGBA parseFrom (String path, List<AtomicProp> atoms) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		
		HOAtoTGBAConsumer hoa2tgba = new HOAtoTGBAConsumer(atoms);
		HOAFParser.parseHOA(fis, hoa2tgba);

		fis.close();
		return hoa2tgba.getTGBA();
	}
}
