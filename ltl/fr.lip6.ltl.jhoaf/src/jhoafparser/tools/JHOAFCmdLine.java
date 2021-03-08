//==============================================================================
//	
//	Copyright (c) 2014-
//	Authors:
//	* Joachim Klein <klein@tcs.inf.tu-dresden.de>
//	* David Mueller <david.mueller@tcs.inf.tu-dresden.de>
//	
//------------------------------------------------------------------------------
//	
//	This file is part of the jhoafparser library, http://automata.tools/hoa/jhoafparser/
//
//	The jhoafparser library is free software; you can redistribute it and/or
//	modify it under the terms of the GNU Lesser General Public
//	License as published by the Free Software Foundation; either
//	version 2.1 of the License, or (at your option) any later version.
//	
//	The jhoafparser library is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//	Lesser General Public License for more details.
//	
//	You should have received a copy of the GNU Lesser General Public
//	License along with this library; if not, write to the Free Software
//	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
//	
//==============================================================================

package jhoafparser.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import jhoafparser.analysis.DeduceAccName;
import jhoafparser.analysis.SyntacticProperties;
import jhoafparser.consumer.HOAConsumer;
import jhoafparser.consumer.HOAConsumerFactory;
import jhoafparser.consumer.HOAConsumerNull;
import jhoafparser.consumer.HOAConsumerPrint;
import jhoafparser.consumer.HOAIntermediateResolveAliases;
import jhoafparser.consumer.HOAIntermediateStoreAndManipulate;
import jhoafparser.consumer.HOAIntermediateTrace;
import jhoafparser.parser.HOAFParserSettings;
import jhoafparser.parser.HOAFParser;
import jhoafparser.parser.generated.ParseException;
import jhoafparser.storage.StoredAutomatonManipulator;
import jhoafparser.transformations.AddProperty;
import jhoafparser.transformations.DNFAcceptance;
import jhoafparser.transformations.ToExplicitLabels;
import jhoafparser.transformations.ToNondetBuchi;
import jhoafparser.transformations.ToTransitionAcceptance;
import jhoafparser.transformations.ToStateAcceptance;

/** The basic jhoaf command line tool */
public class JHOAFCmdLine
{
	/** The version */
	private static final String version = "1.1.1";

	/** Get factory for HOAConsumerNull that nevertheless outputs warnings */
	private static HOAConsumerFactory getNullFactory(final PrintStream err) {
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new HOAConsumerNull() {
					@Override
					public void notifyWarning(String warning) {
						err.println("Warning: "+warning);
					}
				};
			}
		};
	}

	/** Command-line interface */
	public static void main(String[] args)
	{
		HOAConsumerFactory factory = null;
		InputStream input = null;
		
		PrintStream errorStream = System.err;
		PrintStream outputStream = System.out;

		Boolean resolveAliases = null;
		Boolean validate = null;
		Boolean trace = null;
		boolean verbose = false;
		
		ArrayList<String> transformations = new ArrayList<String>();
		ArrayList<String> transformationArguments = new ArrayList<String>();

		HashSet<String> knownTransformations = new HashSet<String>();
		knownTransformations.add("state-acc");
		knownTransformations.add("trans-acc");
		knownTransformations.add("resolve-aliases");
		knownTransformations.add("explicit-labels");
		knownTransformations.add("deduce-accname");
		knownTransformations.add("dnf-acceptance");
		knownTransformations.add("deduce-properties");
		knownTransformations.add("add-property");

		List<String> convertAcceptanceAllowed = null;

		try {
			ArrayList<String> arguments = new ArrayList<String>(args.length);
			for (String arg : args) arguments.add(arg);

			String command = (arguments.size() > 0) ? arguments.remove(0) : null;
			if (command == null || command.equals("help") || command.equals("--help")) {
				usage(null);
				System.exit(2);
			}
			if (command.equals("version") || command.equals("--version")) {
				printVersion(outputStream, true);
				System.exit(0);
			}

			if (command.equals("convert-acceptance")) {
				String allowedAcceptance = (arguments.size() > 0) ? arguments.remove(0) : null;
				if (allowedAcceptance == null) {
					usage("Command 'convert-acceptance' requires list of acceptance conditions");
					System.exit(2);
				}

				convertAcceptanceAllowed = Arrays.asList(allowedAcceptance.split(","));
				if (convertAcceptanceAllowed.size()==0) {
					usage("Empty list of allowed acceptance conditions");
					System.exit(2);
				}
			}

			while (arguments.size() > 0 && arguments.get(0).startsWith("--")) {
				String flag = arguments.remove(0);
				if (flag.equals("--")) break;

				switch (flag) {
				case "--help":
					usage(null);
					System.exit(2);
				case "--resolve-aliases":
					resolveAliases = true;
					break;
				case "--no-validate":
					validate = false;
					break;
				case "--trace":
					trace = true;
					break;
				case "--verbose":
					verbose = true;
					break;
				default: {
					if (flag.startsWith("--transform")) {
						if (!flag.startsWith("--transform=")) {
							usage("Specify transformations via --transform=transformation");
							System.exit(2);
						}
						String[] list = flag.substring("--transform=".length()).split(",");
						if (list.length==0) {
							usage("Empty list of transformations: "+flag);
							System.exit(2);
						}
						for (String transformation : list) {
							String[] t = transformation.split("\\(");
							String argument = null;
							if (t.length == 1) {
								argument = null;
							} else if (t.length == 2) {
								argument = t[1];
								if (!t[1].endsWith(")")) {
									usage("Illegal argument syntax: "+transformation);
									System.exit(2);
								}
								argument = argument.substring(0, argument.length()-1);
								transformation = t[0];
							} else {
								usage("Illegal argument syntax: "+transformation);
								System.exit(2);
							}

							if (knownTransformations.contains(transformation)) {
								transformations.add(transformation);
								transformationArguments.add(argument);
							} else {
								usage("Unknown transformation: "+transformation+"\n");
								System.exit(2);
							}
						}
					} else {
						usage("Unknown/unsupported argument: "+flag);
						System.exit(2);
					}
				}
				}
			}
			
			// defaults

			if (resolveAliases == null) {
				resolveAliases = false;
			}
			if (validate == null) {
				validate = true;
			}
			
			if (trace == null) {
				trace = false;
			}

			switch (command) {
			case "print":
			case "convert-acceptance":
			case "convert-to-buchi": {
				factory = HOAConsumerPrint.getFactory(outputStream);

				if (transformations.size() != 0) {
					boolean deduceProperties = false;
					ArrayList<StoredAutomatonManipulator> manipulators = new ArrayList<StoredAutomatonManipulator>();

					for (int i=0; i < transformations.size(); i++) {
						String transformation = transformations.get(i);
						String argument = transformationArguments.get(i);
					
						switch (transformation) {
						case "deduce-properties":
							if (argument != null) {
								usage("Transformation " + transformation +" takes no argument!");
								System.exit(2);
							}
							deduceProperties = true;
							break;
						case "deduce-accname":
							if (argument != null) {
								usage("Transformation " + transformation +" takes no argument!");
								System.exit(2);
							}
							factory = DeduceAccName.getFactory(factory);
							break;
						case "trans-acc":
							if (argument != null) {
								usage("Transformation " + transformation +" takes no argument!");
								System.exit(2);
							}
							manipulators.add(ToTransitionAcceptance.getStoredAutomatonManipulator());
							break;
						case "state-acc":
							if (argument != null) {
								usage("Transformation " + transformation +" takes no argument!");
								System.exit(2);
							}
							manipulators.add(new ToStateAcceptance());
							break;
						case "explicit-labels":
							if (argument != null) {
								usage("Transformation " + transformation +" takes no argument!");
								System.exit(2);
							}
							manipulators.add(new ToExplicitLabels(true));
							break;
						case "dnf-acceptance":
							if (argument != null) {
								usage("Transformation " + transformation +" takes no argument!");
								System.exit(2);
							}
							factory = DNFAcceptance.getFactory(factory);
						case "add-property":
							if (argument == null) {
								usage("Missing argument for transformation add-property()");
								System.exit(2);
							}
							if (!HOAFParser.isValidIdentifier(argument)) {
								usage("Property '"+argument+"' for add-property is not a valid HOA identifier.");
								System.exit(2);
							}
							factory = AddProperty.getFactory(factory, argument);
							break;
						case "resolve-aliases":
							if (argument != null) {
								usage("Transformation " + transformation +" takes no argument!");
								System.exit(2);
							}
							factory = HOAIntermediateResolveAliases.getFactory(factory);
							break;
						default:
							usage("Unknown transformation: "+transformation);
							System.exit(2);
						}
					}
					
					if (deduceProperties)
						manipulators.add(new SyntacticProperties());

					if (manipulators.size() > 0) {
						factory = HOAIntermediateStoreAndManipulate.getFactory(factory, manipulators.toArray(new StoredAutomatonManipulator[0]));
					}
				}

				if (command.equals("convert-acceptance")) {
					factory = DeduceAccName.getFactory(factory, convertAcceptanceAllowed);
				} else if (command.equals("convert-to-buchi")) {
					factory = HOAIntermediateStoreAndManipulate.getFactory(factory, new ToNondetBuchi());
					// prepend a deduction of the acceptance type
					factory = DeduceAccName.getFactory(factory, Arrays.asList("Buchi", "generalized-Buchi"));
				}

				break;
			}
			case "parse": {
				errorStream = outputStream;
				factory = getNullFactory(errorStream);
				break;
			}
			case "add-properties":
				factory = HOAIntermediateStoreAndManipulate.getFactory(HOAConsumerPrint.getFactory(outputStream),
							new SyntacticProperties());
				break;
			default:
				usage("Unknown command: "+command);
				System.exit(2);
			}

			if (resolveAliases) {
				factory = HOAIntermediateResolveAliases.getFactory(factory);
			}

			if (trace) {
				factory = HOAIntermediateTrace.getFactory(factory, errorStream);
			}

			if (arguments.isEmpty()) {
				usage("Missing filename (or - for standard input)");
				System.exit(2);
			}

			HOAFParserSettings settings = new HOAFParserSettings();
			settings.setFlagValidate(validate);
		
			for (String filename : arguments) {
				if (filename.equals("-")) {
					input = System.in;
					filename = null;
				} else {
					input = new FileInputStream(filename);
				}
				if (verbose) {
					errorStream.println("Reading from " + (filename != null ? "file "+filename : "standard input"));
				}

				HOAFParser.parseHOA(input, factory, settings);
			}

			switch (command) {
			case "parse":
				outputStream.println("Parsing OK");
			}
		}
		catch (ParseException e) {
			errorStream.println(e); System.exit(1);
		}
		catch (FileNotFoundException e) {
			errorStream.println(e); System.exit(1);
		}
	}
	
	/** Print version and license */
	private static void printVersion(PrintStream out, boolean verbose) {
		out.println("jhoafparser library - command line tool (version "+version + ")");
		out.println(" (C) Copyright 2014- Joachim Klein, David Mueller");
		out.println(" http://automata.tools/hoa/jhoafparser/");
		out.println();

		if (verbose) {
			out.println("The jhoafparser library is free software; you can redistribute it and/or");
			out.println("modify it under the terms of the GNU Lesser General Public");
			out.println("License as published by the Free Software Foundation; either");
			out.println("version 2.1 of the License, or (at your option) any later version.");

			out.println();
			out.println("The jhoafparser library is distributed in the hope that it will be useful,");
			out.println("but WITHOUT ANY WARRANTY; without even the implied warranty of");
			out.println("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU");
			out.println("Lesser General Public License for more details.");
		}
	}

	/** Print usage information */
	private static void usage(String error)
	{
		printVersion(System.err, false);

		if (error != null) {
			System.err.println("Command-line arguments error: "+error);
			System.err.println("Use argument 'help' to get usage information.");
			return;
		}

		System.err.println("Arguments:");
		System.err.println("  command [options]* file[s]");
		System.err.println();
		System.err.println(" Valid commands:");
		System.err.println("  parse               Parse the HOAF file and check for errors");
		System.err.println("  print               Parse the HOAF file, perform any specified transformations");
		System.err.println("                        and print the parsed automata to standard out");
		System.err.println("  convert-acceptance <list of acc-names>");
		System.err.println("                      Parse the HOAF file, try to convert to one of the given acceptance");
		System.err.println("                        conditions, print the automaton. The list is given as a comma-seperated");
		System.err.println("                        list conditions, print the automaton. The list is given as a comma-seperated");
		System.err.println("                        list of names that could appear in acc-name headers, e.g., Rabin,Streett");
		System.err.println("  convert-to-buchi");
		System.err.println("                      Parse the HOAF file, apply transformations and convert to a");
		System.err.println("                        non-deterministic Buchi automaton (might be inefficient).");
		System.err.println("                        The original automaton has to be deterministic or non-deterministic");
		System.err.println("  version             Print the version and exit");
		System.err.println("  help                Print this help screen and exit");
		System.err.println();
		System.err.println(" file can be a filename or - to request reading from standard input");
		System.err.println();
		System.err.println(" Valid options:");
		System.err.println("  --transform=<transformations>    Perform transformations (in the given order). Multiple transformations");
		System.err.println("                                   can be specified as a comma-seperated list or using multiple");
		System.err.println("                                   --transform options. See below for a list of supported transformations");
		System.err.println();
		System.err.println("  --resolve-aliases                Resolve aliases");
		System.err.println("  --no-validate                    Disable semantic validation of the automatons");
		System.err.println();
		System.err.println("  --trace                          Debugging: Trace the function calls to HOAConsumer");
		System.err.println("  --verbose                        Increase verbosity");
		System.err.println();

		System.err.println(" Valid transformations:");
		System.err.println("   state-acc                       Transform to state-based acceptance");
		System.err.println("   trans-acc                       Transform to transition-based acceptance");
		System.err.println("   explicit-labels                 Transform to explicit labels");
		System.err.println("   deduce-properties               Deduce properties and add in the header (will always be applied last)");
		System.err.println("   deduce-accname                  Deduce the acc-name");
		System.err.println("   dnf-acceptance                  Transform Acceptance condition into DNF");
		System.err.println("   add-property(p)                 Add property p to the automaton");
		System.err.println("   resolve-aliases                 Resolve all aliases");
		
	}
}
