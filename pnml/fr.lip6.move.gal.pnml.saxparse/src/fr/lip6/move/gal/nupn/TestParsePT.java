package fr.lip6.move.gal.nupn;

import java.io.FileInputStream;
import java.io.IOException;

import fr.lip6.move.pnml.ptnet.PetriNet;

public class TestParsePT {

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("tests/armCache.pnml");

			PTNetReader ptreader = new PTNetReader();
			PetriNet order = ptreader.loadFromXML(fis);
			System.out.println("Read :" + order);
			System.out.println("with order :" + ptreader.getOrder());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
