package fr.lip6.move.gal.nupn;

import java.io.FileInputStream;
import java.io.IOException;

import fr.lip6.move.gal.order.IOrder;

public class TestNupn {

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("tests/nupn.xml");

			IOrder order = NupnReader.loadFromXML(fis);
			System.out.println("Read :" + order);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
