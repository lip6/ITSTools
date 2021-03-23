package jhoafparser.examples;

import java.util.List;

import jhoafparser.ast.AtomLabel;
import jhoafparser.ast.BooleanExpression;
import jhoafparser.consumer.HOAConsumer;
import jhoafparser.consumer.HOAConsumerException;
import jhoafparser.consumer.HOAConsumerNull;
import jhoafparser.consumer.HOAIntermediate;
import jhoafparser.parser.HOAFParser;
import jhoafparser.parser.ParseException;

/** Demonstrating the use of HOAIntermediates */
public class BasicParser2
{
	static class CountStates extends HOAIntermediate {
		public int count = 0;

		public CountStates(HOAConsumer next) {
			super(next);
		}

		@Override
		public void addState(int id,
		                     String info,
		                     BooleanExpression<AtomLabel> labelExpr,
		                     List<Integer> accSignature) throws HOAConsumerException {
			count++;
			next.addState(id, info, labelExpr, accSignature);
		}
	}

	public static void main(String[] args) {
		try {
			CountStates counter = new CountStates(new HOAConsumerNull());
			HOAFParser.parseHOA(System.in, counter);

			System.out.println("Number of state definitions = " + counter.count);
		} catch (ParseException e) {
			System.out.println("ParseException: "+e);
		}
	}
}
