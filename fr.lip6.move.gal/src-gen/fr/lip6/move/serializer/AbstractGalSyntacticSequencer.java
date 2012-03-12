package fr.lip6.move.serializer;

import com.google.inject.Inject;
import fr.lip6.move.services.GalGrammarAccess;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("restriction")
public class AbstractGalSyntacticSequencer extends AbstractSyntacticSequencer {

	protected GalGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Not_ExclamationMarkKeyword_0_q;
	protected AbstractElementAlias match_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__a;
	protected AbstractElementAlias match_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__p;
	protected AbstractElementAlias match_Primary_UnitaryMinus___LeftParenthesisKeyword_2_0_HyphenMinusKeyword_0_q__a;
	protected AbstractElementAlias match_UnitaryMinus_HyphenMinusKeyword_0_q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (GalGrammarAccess) access;
		match_Not_ExclamationMarkKeyword_0_q = new TokenAlias(false, true, grammarAccess.getNotAccess().getExclamationMarkKeyword_0());
		match_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__a = new GroupAlias(true, true, new TokenAlias(false, true, grammarAccess.getUnitaryMinusAccess().getHyphenMinusKeyword_0()), new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0()));
		match_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__p = new GroupAlias(true, false, new TokenAlias(false, true, grammarAccess.getUnitaryMinusAccess().getHyphenMinusKeyword_0()), new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0()));
		match_Primary_UnitaryMinus___LeftParenthesisKeyword_2_0_HyphenMinusKeyword_0_q__a = new GroupAlias(true, true, new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0()), new TokenAlias(false, true, grammarAccess.getUnitaryMinusAccess().getHyphenMinusKeyword_0()));
		match_UnitaryMinus_HyphenMinusKeyword_0_q = new TokenAlias(false, true, grammarAccess.getUnitaryMinusAccess().getHyphenMinusKeyword_0());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if(match_Not_ExclamationMarkKeyword_0_q.equals(syntax))
				emit_Not_ExclamationMarkKeyword_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__a.equals(syntax))
				emit_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__p.equals(syntax))
				emit_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Primary_UnitaryMinus___LeftParenthesisKeyword_2_0_HyphenMinusKeyword_0_q__a.equals(syntax))
				emit_Primary_UnitaryMinus___LeftParenthesisKeyword_2_0_HyphenMinusKeyword_0_q__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_UnitaryMinus_HyphenMinusKeyword_0_q.equals(syntax))
				emit_UnitaryMinus_HyphenMinusKeyword_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     '!'?
	 */
	protected void emit_Not_ExclamationMarkKeyword_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('-'? '(')*
	 */
	protected void emit_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('-'? '(')+
	 */
	protected void emit_Primary_UnitaryMinus___HyphenMinusKeyword_0_q_LeftParenthesisKeyword_2_0__p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('(' '-'?)*
	 */
	protected void emit_Primary_UnitaryMinus___LeftParenthesisKeyword_2_0_HyphenMinusKeyword_0_q__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     '-'?
	 */
	protected void emit_UnitaryMinus_HyphenMinusKeyword_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
