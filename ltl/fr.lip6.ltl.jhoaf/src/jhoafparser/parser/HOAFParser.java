package jhoafparser.parser;

import java.io.InputStream;

import jhoafparser.consumer.HOAConsumer;
import jhoafparser.consumer.HOAConsumerFactory;
import jhoafparser.parser.generated.ParseException;

/**
 * Public interface to the HOA format parser.
 * <p>
 * Note that the parser is non-reentrant, i.e., it is
 * not possible to parse two streams at the same time!
 */
public class HOAFParser
{

	/**
	 * Entry point for parsing a single automaton in HOA format (with default settings).
	 * <br> Note: this parser is non-reentrant, i.e., it is
	 * not possible to parse two streams at the same time!
	 *
	 * @param str The input stream with the automaton description
	 * @param userConsumer The consumer that receives the notifications about the parsed elements from the parser
	 */
	public static void parseHOA(InputStream str, HOAConsumer userConsumer) throws ParseException {
		jhoafparser.parser.generated.HOAFParserCC.parseHOA(str, userConsumer);
	}

	/**
	 * Entry point for parsing a single automaton in HOA format.
	 * <br> Note: this parser is non-reentrant, i.e., it is
	 * not possible to parse two streams at the same time!
	 *
	 * @param str The input stream with the automaton description
	 * @param userConsumer The consumer that receives the notifications about the parsed elements from the parser
	 * @param settings Settings for the parser (may be {@code null}) 
	 */
	public static void parseHOA(InputStream str,
	                            HOAConsumer userConsumer,
	                            HOAFParserSettings settings) throws ParseException {
		jhoafparser.parser.generated.HOAFParserCC.parseHOA(str, userConsumer, settings);
	}

	/**
	 * Entry point for parsing a stream of automata in HOA format.
	 * <br> Note: this parser is non-reentrant, i.e., it is
	 * not possible to parse two streams at the same time!
	 *
	 * @param str The input stream with the automaton description
	 * @param userFactory A factory that produces HOAConsumers, one for each automaton encountered,
	 *                      that receive the notifications about the parsed elements from the parser
	 */
	public static void parseHOA(InputStream str,
	                            HOAConsumerFactory userFactory) throws ParseException {
		jhoafparser.parser.generated.HOAFParserCC.parseHOA(str, userFactory, null);
	}

	/**
	 * Entry point for parsing a stream of automata in HOA format.
	 * <br> Note: this parser is non-reentrant, i.e., it is
	 * not possible to parse two streams at the same time!
	 *
	 * @param str The input stream with the automaton description
	 * @param userFactory A factory that produces HOAConsumers, one for each automaton encountered,
	 *                      that receive the notifications about the parsed elements from the parser
	 * @param settings Settings for the parser (may be {@code null}) 
	 */
	public static void parseHOA(InputStream str,
	                            HOAConsumerFactory userFactory,
	                            HOAFParserSettings settings) throws ParseException {
		jhoafparser.parser.generated.HOAFParserCC.parseHOA(str, userFactory, settings);
	}


	/**
	 * Checks whether the argument is a valid HOA identifier.
	 * @param s the String to be checked
	 * @return true if s is a valid identifier
	 */
	public static boolean isValidIdentifier(String s) {
		return s.matches("^[_a-zA-Z][_a-zA-Z0-9-]*$");
	}

}
