package fr.lip6.move.serializer;

import com.google.inject.Inject;
import fr.lip6.move.services.GalGrammarAccess;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AlternativeAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("restriction")
public class AbstractGalSyntacticSequencer extends AbstractSyntacticSequencer {

	protected GalGrammarAccess grammarAccess;
	protected AbstractElementAlias match_BitComplement_HyphenMinusKeyword_1_0_or_TildeKeyword_0_0;
	protected AbstractElementAlias match_BitComplement_Primary_TildeKeyword_0_0_or_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__a_TildeKeyword_0_0__;
	protected AbstractElementAlias match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q__a;
	protected AbstractElementAlias match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___TildeKeyword_0_0_q_LeftParenthesisKeyword_3_0_0_0__a_HyphenMinusKeyword_1_0__a;
	protected AbstractElementAlias match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a__q;
	protected AbstractElementAlias match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q____q;
	protected AbstractElementAlias match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0____a;
	protected AbstractElementAlias match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__p;
	protected AbstractElementAlias match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a;
	protected AbstractElementAlias match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__p;
	protected AbstractElementAlias match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a;
	protected AbstractElementAlias match_BitComplement___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q;
	protected AbstractElementAlias match_ListDeclaration___EqualsSignKeyword_2_0_LeftParenthesisKeyword_2_1_RightParenthesisKeyword_2_3__q;
	protected AbstractElementAlias match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0__p;
	protected AbstractElementAlias match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0_p__p;
	protected AbstractElementAlias match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a;
	protected AbstractElementAlias match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__p;
	protected AbstractElementAlias match_Not_PrimaryBool___LeftParenthesisKeyword_3_0_ExclamationMarkKeyword_0_0__a;
	protected AbstractElementAlias match_Not_PrimaryBool___LeftParenthesisKeyword_3_0___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a__q;
	protected AbstractElementAlias match_Primary_LeftParenthesisKeyword_3_0_0_0_a;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (GalGrammarAccess) access;
		match_BitComplement_HyphenMinusKeyword_1_0_or_TildeKeyword_0_0 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0()));
		match_BitComplement_Primary_TildeKeyword_0_0_or_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__a_TildeKeyword_0_0__ = new AlternativeAlias(false, false, new GroupAlias(false, false, new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())), new TokenAlias(true, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()), new GroupAlias(true, true, new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())), new TokenAlias(true, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0())), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0()));
		match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q__a = new GroupAlias(true, true, new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()), new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())));
		match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___TildeKeyword_0_0_q_LeftParenthesisKeyword_3_0_0_0__a_HyphenMinusKeyword_1_0__a = new GroupAlias(true, true, new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()), new GroupAlias(true, true, new TokenAlias(false, true, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0()), new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0())), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()));
		match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()), new GroupAlias(true, true, new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())), new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0())));
		match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q____q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()), new GroupAlias(true, true, new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, true, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())), new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0())), new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, true, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())));
		match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0____a = new GroupAlias(true, true, new TokenAlias(true, true, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()), new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())));
		match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__p = new GroupAlias(true, false, new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())), new TokenAlias(true, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()));
		match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a = new GroupAlias(true, true, new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())), new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()));
		match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__p = new GroupAlias(true, false, new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())), new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()));
		match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a = new GroupAlias(true, true, new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, true, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0())), new TokenAlias(false, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0()));
		match_BitComplement___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q = new AlternativeAlias(false, true, new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0()));
		match_ListDeclaration___EqualsSignKeyword_2_0_LeftParenthesisKeyword_2_1_RightParenthesisKeyword_2_3__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getListDeclarationAccess().getEqualsSignKeyword_2_0()), new TokenAlias(false, false, grammarAccess.getListDeclarationAccess().getLeftParenthesisKeyword_2_1()), new TokenAlias(false, false, grammarAccess.getListDeclarationAccess().getRightParenthesisKeyword_2_3()));
		match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0__p = new GroupAlias(true, false, new TokenAlias(false, false, grammarAccess.getNotAccess().getExclamationMarkKeyword_0_0()), new TokenAlias(false, false, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0()));
		match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0_p__p = new GroupAlias(true, false, new TokenAlias(false, false, grammarAccess.getNotAccess().getExclamationMarkKeyword_0_0()), new TokenAlias(true, false, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0()));
		match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a = new GroupAlias(true, true, new TokenAlias(false, true, grammarAccess.getNotAccess().getExclamationMarkKeyword_0_0()), new TokenAlias(false, false, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0()));
		match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__p = new GroupAlias(true, false, new TokenAlias(false, true, grammarAccess.getNotAccess().getExclamationMarkKeyword_0_0()), new TokenAlias(false, false, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0()));
		match_Not_PrimaryBool___LeftParenthesisKeyword_3_0_ExclamationMarkKeyword_0_0__a = new GroupAlias(true, true, new TokenAlias(false, false, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0()), new TokenAlias(false, false, grammarAccess.getNotAccess().getExclamationMarkKeyword_0_0()));
		match_Not_PrimaryBool___LeftParenthesisKeyword_3_0___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0()), new GroupAlias(true, true, new TokenAlias(false, true, grammarAccess.getNotAccess().getExclamationMarkKeyword_0_0()), new TokenAlias(false, false, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0())));
		match_Primary_LeftParenthesisKeyword_3_0_0_0_a = new TokenAlias(true, true, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0());
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
			if(match_BitComplement_HyphenMinusKeyword_1_0_or_TildeKeyword_0_0.equals(syntax))
				emit_BitComplement_HyphenMinusKeyword_1_0_or_TildeKeyword_0_0(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary_TildeKeyword_0_0_or_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__a_TildeKeyword_0_0__.equals(syntax))
				emit_BitComplement_Primary_TildeKeyword_0_0_or_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__a_TildeKeyword_0_0__(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q__a.equals(syntax))
				emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___TildeKeyword_0_0_q_LeftParenthesisKeyword_3_0_0_0__a_HyphenMinusKeyword_1_0__a.equals(syntax))
				emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___TildeKeyword_0_0_q_LeftParenthesisKeyword_3_0_0_0__a_HyphenMinusKeyword_1_0__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a__q.equals(syntax))
				emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q____q.equals(syntax))
				emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q____q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0____a.equals(syntax))
				emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0____a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__p.equals(syntax))
				emit_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a.equals(syntax))
				emit_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__p.equals(syntax))
				emit_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a.equals(syntax))
				emit_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_BitComplement___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q.equals(syntax))
				emit_BitComplement___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_ListDeclaration___EqualsSignKeyword_2_0_LeftParenthesisKeyword_2_1_RightParenthesisKeyword_2_3__q.equals(syntax))
				emit_ListDeclaration___EqualsSignKeyword_2_0_LeftParenthesisKeyword_2_1_RightParenthesisKeyword_2_3__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0__p.equals(syntax))
				emit_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0__p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0_p__p.equals(syntax))
				emit_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0_p__p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a.equals(syntax))
				emit_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__p.equals(syntax))
				emit_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__p(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Not_PrimaryBool___LeftParenthesisKeyword_3_0_ExclamationMarkKeyword_0_0__a.equals(syntax))
				emit_Not_PrimaryBool___LeftParenthesisKeyword_3_0_ExclamationMarkKeyword_0_0__a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Not_PrimaryBool___LeftParenthesisKeyword_3_0___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a__q.equals(syntax))
				emit_Not_PrimaryBool___LeftParenthesisKeyword_3_0___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Primary_LeftParenthesisKeyword_3_0_0_0_a.equals(syntax))
				emit_Primary_LeftParenthesisKeyword_3_0_0_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     '-' | '~'
	 */
	protected void emit_BitComplement_HyphenMinusKeyword_1_0_or_TildeKeyword_0_0(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     (('-' | '~') '('+ (('-' | '~') '('+)* '~') | '~'
	 */
	protected void emit_BitComplement_Primary_TildeKeyword_0_0_or_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__a_TildeKeyword_0_0__(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('(' ('-' | '~')?)*
	 */
	protected void emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('(' ('~'? '(')* '-')*
	 */
	protected void emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0___TildeKeyword_0_0_q_LeftParenthesisKeyword_3_0_0_0__a_HyphenMinusKeyword_1_0__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('(' (('-' | '~')? '(')*)?
	 */
	protected void emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('(' (('-' | '~'?) '(')* ('-' | '~'?))?
	 */
	protected void emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q____q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('('* ('-' | '~'))*
	 */
	protected void emit_BitComplement_Primary___LeftParenthesisKeyword_3_0_0_0_a___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0____a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     (('-' | '~') '('+)+
	 */
	protected void emit_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0___LeftParenthesisKeyword_3_0_0_0_p__p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     (('-' | '~')? '(')*
	 */
	protected void emit_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     (('-' | '~')? '(')+
	 */
	protected void emit_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q_LeftParenthesisKeyword_3_0_0_0__p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     (('-' | '~'?) '(')*
	 */
	protected void emit_BitComplement_Primary_____HyphenMinusKeyword_1_0_or_TildeKeyword_0_0_q___LeftParenthesisKeyword_3_0_0_0__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('-' | '~')?
	 */
	protected void emit_BitComplement___HyphenMinusKeyword_1_0_or_TildeKeyword_0_0__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('=' '(' ')')?
	 */
	protected void emit_ListDeclaration___EqualsSignKeyword_2_0_LeftParenthesisKeyword_2_1_RightParenthesisKeyword_2_3__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('!' '(')+
	 */
	protected void emit_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0__p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('!' '('+)+
	 */
	protected void emit_Not_PrimaryBool___ExclamationMarkKeyword_0_0_LeftParenthesisKeyword_3_0_p__p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('!'? '(')*
	 */
	protected void emit_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('!'? '(')+
	 */
	protected void emit_Not_PrimaryBool___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('(' '!')*
	 */
	protected void emit_Not_PrimaryBool___LeftParenthesisKeyword_3_0_ExclamationMarkKeyword_0_0__a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('(' ('!'? '(')*)?
	 */
	protected void emit_Not_PrimaryBool___LeftParenthesisKeyword_3_0___ExclamationMarkKeyword_0_0_q_LeftParenthesisKeyword_3_0__a__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     '('*
	 */
	protected void emit_Primary_LeftParenthesisKeyword_3_0_0_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
