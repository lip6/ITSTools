package fr.lip6.move.serializer;

import com.google.inject.Inject;
import fr.lip6.move.services.TimedAutomataGrammarAccess;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class TimedAutomataSyntacticSequencer extends AbstractSyntacticSequencer {

	protected TimedAutomataGrammarAccess grammarAccess;
	protected AbstractElementAlias match_PrimaryBool_LeftParenthesisKeyword_3_0_a;
	protected AbstractElementAlias match_PrimaryBool_LeftParenthesisKeyword_3_0_p;
	protected AbstractElementAlias match_Primary_LeftParenthesisKeyword_2_0_0_0_a;
	protected AbstractElementAlias match_Primary_LeftParenthesisKeyword_2_0_0_0_p;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (TimedAutomataGrammarAccess) access;
		match_PrimaryBool_LeftParenthesisKeyword_3_0_a = new TokenAlias(true, true, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0());
		match_PrimaryBool_LeftParenthesisKeyword_3_0_p = new TokenAlias(true, false, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0());
		match_Primary_LeftParenthesisKeyword_2_0_0_0_a = new TokenAlias(true, true, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0_0_0());
		match_Primary_LeftParenthesisKeyword_2_0_0_0_p = new TokenAlias(true, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0_0_0());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if(ruleCall.getRule() == grammarAccess.getASSIGNMENTRule())
			return getASSIGNMENTToken(semanticObject, ruleCall, node);
		return "";
	}
	
	/**
	 * ASSIGNMENT : 
	 * 	'='   
	 * 	| ':='  
	 * ;
	 */
	protected String getASSIGNMENTToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "=";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if(match_PrimaryBool_LeftParenthesisKeyword_3_0_a.equals(syntax))
				emit_PrimaryBool_LeftParenthesisKeyword_3_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_PrimaryBool_LeftParenthesisKeyword_3_0_p.equals(syntax))
				emit_PrimaryBool_LeftParenthesisKeyword_3_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Primary_LeftParenthesisKeyword_2_0_0_0_a.equals(syntax))
				emit_Primary_LeftParenthesisKeyword_2_0_0_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Primary_LeftParenthesisKeyword_2_0_0_0_p.equals(syntax))
				emit_Primary_LeftParenthesisKeyword_2_0_0_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     '('*
	 */
	protected void emit_PrimaryBool_LeftParenthesisKeyword_3_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     '('+
	 */
	protected void emit_PrimaryBool_LeftParenthesisKeyword_3_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     '('*
	 */
	protected void emit_Primary_LeftParenthesisKeyword_2_0_0_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     '('+
	 */
	protected void emit_Primary_LeftParenthesisKeyword_2_0_0_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
