package jhoafparser.examples;

import java.util.List;

import jhoafparser.ast.AtomLabel;
import jhoafparser.ast.BooleanExpression;
import jhoafparser.consumer.HOAConsumer;
import jhoafparser.consumer.HOAConsumerException;
import jhoafparser.consumer.HOAConsumerFactory;
import jhoafparser.consumer.HOAConsumerNull;
import jhoafparser.consumer.HOAIntermediate;
import jhoafparser.parser.HOAFParser;
import jhoafparser.parser.generated.ParseException;

/** Demonstrating the use of HOAConsumerFactory */
public class BasicParser3
{
	static class CountStates extends HOAIntermediate {
		public int count = 0;

		public CountStates(HOAConsumer next) {
			super(next);
		}

		public static HOAConsumerFactory getFactory(final HOAConsumerFactory next)
		{
			return new HOAConsumerFactory() {
				@Override
				public HOAConsumer getNewHOAConsumer()
				{
					return new CountStates(next.getNewHOAConsumer());
				}
			};
		}

		@Override
		public void addState(int id,
		                     String info,
		                     BooleanExpression<AtomLabel> labelExpr,
		                     List<Integer> accSignature) throws HOAConsumerException {
			count++;
			next.addState(id, info, labelExpr, accSignature);
		}

		@Override
		public void notifyEnd() {
			System.out.println("Number of state definitions = " + count);
		}
	}

	public static void main(String[] args) {
		try {
			HOAFParser.parseHOA(System.in,
			                    CountStates.getFactory(HOAConsumerNull.getFactory()));
		} catch (ParseException e) {
			System.out.println("ParseException: "+e);
		}
	}
}
