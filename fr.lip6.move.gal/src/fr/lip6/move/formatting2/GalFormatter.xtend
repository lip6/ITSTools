package fr.lip6.move.formatting2

import fr.lip6.move.gal.CompositeTypeDeclaration
import fr.lip6.move.gal.GALTypeDeclaration
import fr.lip6.move.gal.GalPackage
import fr.lip6.move.gal.Reference
import fr.lip6.move.gal.Statement
import fr.lip6.move.gal.Synchronization
import fr.lip6.move.gal.Transition
import fr.lip6.move.gal.TypeDeclaration
import fr.lip6.move.gal.Variable
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument

class GalFormatter extends AbstractFormatter2 {
	
	
	def dispatch void format(Statement stat, extension IFormattableDocument document) { 		
		for (k : stat.regionFor.keywords(";")) {
			k.prepend[noSpace].append[newLine]
		}
	}		
	
	def dispatch void format(Variable stat, extension IFormattableDocument document) {
		stat.regionFor.keyword("int").prepend[noSpace].append[oneSpace]
		stat.regionFor.feature(GalPackage.Literals.NAMED_DECLARATION__NAME).prepend[oneSpace];
		stat.regionFor.keyword(";").prepend[noSpace].append[newLine]
	} 		
	
	def dispatch void format(GALTypeDeclaration type, extension IFormattableDocument document) { 
		interior(
			type.regionFor.keyword('{').append[newLine],
			type.regionFor.keyword('}').append[newLine],
			[indent]
		)
		
		for (v : type.variables) {
			v.format;
		}
		for (a : type.arrays) {
			a.format;
		}
		for (t: type.transitions) {
			t.format
		}		
	}
	
	def dispatch void format(CompositeTypeDeclaration type, extension IFormattableDocument document) { 
		interior(
			type.regionFor.keyword('{').append[newLine],
			type.regionFor.keyword('}').append[newLine],
			[indent]
		)
		
		for (v : type.instances) {
			v.format;
		}
		for (a : type.synchronizations) {
			a.format;
		}
	}
	
	
	def dispatch void format(TypeDeclaration type, extension IFormattableDocument document) { 
		type.interior[indent];
	}
	
	def dispatch void format(Transition trans, extension IFormattableDocument document) { 
		interior(
			trans.regionFor.keyword('{').append[newLine],
			trans.regionFor.keyword('}').append[newLine],
			[indent]
		)
		for (s : trans.actions) {
			s.format
		}
		//trans.interior[indent];
	}	
	
	def dispatch void format(Synchronization trans, extension IFormattableDocument document) { 
		interior(
			trans.regionFor.keyword('{').append[newLine],
			trans.regionFor.keyword('}').append[newLine],
			[indent]
		)
		for (s : trans.actions) {
			s.format
		}
		//trans.interior[indent];
	}	
	
	def dispatch void format(Reference ref, extension IFormattableDocument document) {		
		
		for (k : ref.regionFor.keywords("[")) {
			k.prepend[noSpace].append[noSpace]
		}
		for (k : ref.regionFor.keywords("]")) {
			k.prepend[noSpace].append[noSpace]
		}
		
		for (k : ref.regionFor.keywords("(")) {
			k.append[noSpace]
		}
		for (k : ref.regionFor.keywords(")")) {
			k.prepend[noSpace]
		}

		for (k : ref.regionFor.keywords(",")) {
			k.prepend[noSpace].append[oneSpace; autowrap]
		}
		
		for (k : ref.regionFor.keywords(":")) {
			k.prepend[noSpace].append[noSpace]
		}
		
		for (k : ref.regionFor.keywords(".")) {
			k.prepend[noSpace].append[noSpace]
		}
		
		for (k : ref.regionFor.keywords(";")) {
			k.prepend[noSpace].append[newLine]
		}
		
	}

	
	
}


//	protected void configureFormatting(FormattingConfig c) 
//	{
//
//		IGrammarAccess ga = getGrammarAccess() ; 
//		
//		
//		c.setAutoLinewrap(120);
//		
//		// find common keywords an specify formatting for them
//		for (Pair<Keyword, Keyword> pair : ga.findKeywordPairs("(", ")")) 
//		{
//			c.setNoSpace().after(pair.getFirst());
//			c.setNoSpace().before(pair.getSecond());
//		}
//		for (Pair<Keyword, Keyword> pair : ga.findKeywordPairs("[", "]")) 
//		{
//			c.setNoSpace().after(pair.getFirst());
//			c.setNoSpace().before(pair.getSecond());
//		}
//		
//		for (Keyword comma : ga.findKeywords(",")) 
//		{
//			c.setNoSpace().before(comma);
//		}
//		for (Keyword comma : ga.findKeywords(".")) 
//		{
//			c.setNoSpace().before(comma);
//			c.setNoSpace().after(comma);			
//		}
//		
//		
//		// brackets content treatment
//		for(Keyword kw : ga.findKeywords(";"))
//		{
//			c.setLinewrap(1).after(kw) ;
//		}
//		
//		
//		List<Pair<Keyword, Keyword>> bracketsList = ga.findKeywordPairs("{", "}") ; 
//		
//		//c.setLinewrap(1).after(GrammarUtil.findRuleForName(ga.getGrammar(), "ML_COMMENT"));
//		//c.setLinewrap(1).before(GrammarUtil.findRuleForName(ga.getGrammar(), "ML_COMMENT"));
//		
//		
//		for (TerminalRule tr : GrammarUtil.allTerminalRules(ga.getGrammar())) {
//			if (isCommentRule(tr)) {
//				c.setIndentation(tr.getAlternatives(), tr.getAlternatives());
//
//			}
//		}
//		for(Keyword kw : ga.findKeywords("int", "list", "array"))
//		{
//			c.setLinewrap(1).before(kw) ;
//		}
//
//		
//		for(Keyword kw : ga.findKeywords("transition"))
//		{
//			c.setLinewrap(1).before(kw) ;
//		}
//		
//		c.setLinewrap(1).after(GrammarUtil.findRuleForName(ga.getGrammar(), "COMMENT"));
//		
//		// brackets treatment
//		for(Pair<Keyword, Keyword> pair : bracketsList)
//		{
//			// a space before the first '{'
//			c.setSpace(" ").before(pair.getFirst());
//			// Indentation between
//			c.setIndentation(pair.getFirst(), pair.getSecond());
//			
//			// newline after '{'
//			c.setLinewrap(1).after(pair.getFirst()) ;
//			
//			// newline before and after '}'
//			c.setLinewrap(1).before(pair.getSecond()) ;
//			c.setLinewrap(1).after(pair.getSecond())  ;
//			
//		}
//		
//		for(Keyword kw : ga.findKeywords("else"))
//		{
//			c.setLinewrap(0).before(kw) ;
//		} 
//		c.setLinewrap(0, 1, 2).before(GrammarUtil.findRuleForName(ga.getGrammar(), "SL_COMMENT")) ; 
//		c.setLinewrap(0, 1, 1).after (GrammarUtil.findRuleForName(ga.getGrammar(), "ML_COMMENT")) ;
//	}
//
//	
//	private boolean isCommentRule(TerminalRule term) {
//		
//		return "ML_COMMENT".equals(term.getName()) 
//			|| "SL_COMMENT".equals(term.getName())
//			|| "COMMENT".equals(term.getName());
//	}
