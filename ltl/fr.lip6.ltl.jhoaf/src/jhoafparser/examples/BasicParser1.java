package jhoafparser.examples;

import jhoafparser.consumer.HOAConsumerPrint;
import jhoafparser.parser.HOAFParser;
import jhoafparser.parser.ParseException;

/** The most basic HOA parser: Read an automaton from input and print it to the output. */
public class BasicParser1
{
	public static void main(String[] args) {
		try {
			HOAFParser.parseHOA(System.in, new HOAConsumerPrint(System.out));
		} catch (ParseException e) {
			System.out.println("ParseException: "+e);
		}
	}
}
