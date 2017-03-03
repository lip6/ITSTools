package fr.lip6.move.coloane.projects.its.checks;

import java.util.ArrayList;
import java.util.List;

import main.antlr3.fr.lip6.move.coloane.projects.its.ctl.parser.CTLParserLexer;
import main.antlr3.fr.lip6.move.coloane.projects.its.ctl.parser.CTLParserParser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import fr.lip6.move.coloane.projects.its.antlrutil.ErrorReporter;
import fr.lip6.move.coloane.projects.its.checks.ui.controls.ParseError;
import fr.lip6.move.coloane.projects.its.ctl.CTLFormula;
import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;

public class ParsedCTLFormula extends SimpleObservable {

	private CTLFormula parsedForm;
	private String inputForm;
	// current parse errors
	private List<ParseError> errors = new ArrayList<ParseError>();
	private CheckList parent;

	public ParsedCTLFormula(String formulaString, CheckList parent) {
		setFormulaString(formulaString);
		this.parent = parent;
	}

	public void reportError(String msg, int charAt, int len, List<String> suggs) {
		errors.add(new ParseError(msg,charAt,len, suggs));
	}


	public void setFormulaString(String formulaString) {
		if (formulaString==null) {
			formulaString = "TRUE;";
		}
		if (formulaString != null && (! formulaString.equals(inputForm)|| parsedForm==null)) {
			errors.clear();
			
			inputForm = formulaString;
			CTLParserLexer lexer;
			lexer = new CTLParserLexer(new ANTLRStringStream(inputForm));

			CommonTokenStream tokens = new CommonTokenStream(lexer);

			CTLParserParser parser = new CTLParserParser(tokens);
			parsedForm = null;
			ErrorReporter report = new ErrorReporter();
			parser.setErrorReporter(this);
			parser.setCheckList(parent);
			try {
				parsedForm = parser.ctlformula();

				for (String error : report) {
					System.err.println(error);
					// addCheckFail(elt, att, error, result);
					// testok = false;
				}
			} catch (RecognitionException ee) {
				System.err.println(ee + ee.getMessage());
				ee.printStackTrace();
				// addCheckFail(elt, att, e.getLocalizedMessage(), result);
				// testok = false;
			} catch (NullPointerException error) {
				error.printStackTrace();
				System.err.println("No formula to parse, model not set yet ?");				
			}

			notifyObservers();
		}
	}

	public List<ParseError> getErrors() {
		return errors;
	}

	public String getFormulaString() {
		return inputForm;
	}

	public CTLFormula getParsedFormula() {
		if (parsedForm==null) {
			// try to reparse
			setFormulaString(inputForm);
		}
		return parsedForm;
	}
	
	@Override
	public String toString() {
		if (getParsedFormula() != null)
			return getParsedFormula().toString();
		return inputForm;
	}

}
