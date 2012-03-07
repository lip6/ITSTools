package fr.lip6.move.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import fr.lip6.move.services.GalGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalGalParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'{'", "'}'", "'System'", "'='", "';'", "'*'", "'('", "')'", "'+'", "'transition'", "'['", "'TRUE'", "']'", "'label'"
    };
    public static final int RULE_ID=4;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__19=19;
    public static final int RULE_STRING=6;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=5;
    public static final int RULE_WS=9;

    // delegates
    // delegators


        public InternalGalParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalGalParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalGalParser.tokenNames; }
    public String getGrammarFileName() { return "../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g"; }


     
     	private GalGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(GalGrammarAccess grammarAccess) {
        	this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected Grammar getGrammar() {
        	return grammarAccess.getGrammar();
        }
        
        @Override
        protected String getValueForTokenName(String tokenName) {
        	return tokenName;
        }




    // $ANTLR start "entryRulePROGRAM"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:60:1: entryRulePROGRAM : rulePROGRAM EOF ;
    public final void entryRulePROGRAM() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:61:1: ( rulePROGRAM EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:62:1: rulePROGRAM EOF
            {
             before(grammarAccess.getPROGRAMRule()); 
            pushFollow(FOLLOW_rulePROGRAM_in_entryRulePROGRAM61);
            rulePROGRAM();

            state._fsp--;

             after(grammarAccess.getPROGRAMRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePROGRAM68); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePROGRAM"


    // $ANTLR start "rulePROGRAM"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:69:1: rulePROGRAM : ( ( rule__PROGRAM__Group__0 )? ) ;
    public final void rulePROGRAM() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:73:2: ( ( ( rule__PROGRAM__Group__0 )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:74:1: ( ( rule__PROGRAM__Group__0 )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:74:1: ( ( rule__PROGRAM__Group__0 )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:75:1: ( rule__PROGRAM__Group__0 )?
            {
             before(grammarAccess.getPROGRAMAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:76:1: ( rule__PROGRAM__Group__0 )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==13) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:76:2: rule__PROGRAM__Group__0
                    {
                    pushFollow(FOLLOW_rule__PROGRAM__Group__0_in_rulePROGRAM94);
                    rule__PROGRAM__Group__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPROGRAMAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePROGRAM"


    // $ANTLR start "entryRuleVariableDeclaration"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:88:1: entryRuleVariableDeclaration : ruleVariableDeclaration EOF ;
    public final void entryRuleVariableDeclaration() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:89:1: ( ruleVariableDeclaration EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:90:1: ruleVariableDeclaration EOF
            {
             before(grammarAccess.getVariableDeclarationRule()); 
            pushFollow(FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration122);
            ruleVariableDeclaration();

            state._fsp--;

             after(grammarAccess.getVariableDeclarationRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableDeclaration129); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVariableDeclaration"


    // $ANTLR start "ruleVariableDeclaration"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:97:1: ruleVariableDeclaration : ( ( rule__VariableDeclaration__Group__0 ) ) ;
    public final void ruleVariableDeclaration() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:101:2: ( ( ( rule__VariableDeclaration__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:102:1: ( ( rule__VariableDeclaration__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:102:1: ( ( rule__VariableDeclaration__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:103:1: ( rule__VariableDeclaration__Group__0 )
            {
             before(grammarAccess.getVariableDeclarationAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:104:1: ( rule__VariableDeclaration__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:104:2: rule__VariableDeclaration__Group__0
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__0_in_ruleVariableDeclaration155);
            rule__VariableDeclaration__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVariableDeclarationAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVariableDeclaration"


    // $ANTLR start "entryRuleDEBUT"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:116:1: entryRuleDEBUT : ruleDEBUT EOF ;
    public final void entryRuleDEBUT() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:117:1: ( ruleDEBUT EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:118:1: ruleDEBUT EOF
            {
             before(grammarAccess.getDEBUTRule()); 
            pushFollow(FOLLOW_ruleDEBUT_in_entryRuleDEBUT182);
            ruleDEBUT();

            state._fsp--;

             after(grammarAccess.getDEBUTRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDEBUT189); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDEBUT"


    // $ANTLR start "ruleDEBUT"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:125:1: ruleDEBUT : ( '{' ) ;
    public final void ruleDEBUT() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:129:2: ( ( '{' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:130:1: ( '{' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:130:1: ( '{' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:131:1: '{'
            {
             before(grammarAccess.getDEBUTAccess().getLeftCurlyBracketKeyword()); 
            match(input,11,FOLLOW_11_in_ruleDEBUT216); 
             after(grammarAccess.getDEBUTAccess().getLeftCurlyBracketKeyword()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDEBUT"


    // $ANTLR start "entryRuleFIN"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:146:1: entryRuleFIN : ruleFIN EOF ;
    public final void entryRuleFIN() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:147:1: ( ruleFIN EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:148:1: ruleFIN EOF
            {
             before(grammarAccess.getFINRule()); 
            pushFollow(FOLLOW_ruleFIN_in_entryRuleFIN244);
            ruleFIN();

            state._fsp--;

             after(grammarAccess.getFINRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFIN251); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFIN"


    // $ANTLR start "ruleFIN"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:155:1: ruleFIN : ( '}' ) ;
    public final void ruleFIN() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:159:2: ( ( '}' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:160:1: ( '}' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:160:1: ( '}' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:161:1: '}'
            {
             before(grammarAccess.getFINAccess().getRightCurlyBracketKeyword()); 
            match(input,12,FOLLOW_12_in_ruleFIN278); 
             after(grammarAccess.getFINAccess().getRightCurlyBracketKeyword()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFIN"


    // $ANTLR start "entryRuleConstante"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:176:1: entryRuleConstante : ruleConstante EOF ;
    public final void entryRuleConstante() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:177:1: ( ruleConstante EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:178:1: ruleConstante EOF
            {
             before(grammarAccess.getConstanteRule()); 
            pushFollow(FOLLOW_ruleConstante_in_entryRuleConstante306);
            ruleConstante();

            state._fsp--;

             after(grammarAccess.getConstanteRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstante313); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleConstante"


    // $ANTLR start "ruleConstante"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:185:1: ruleConstante : ( ( rule__Constante__ValAssignment ) ) ;
    public final void ruleConstante() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:189:2: ( ( ( rule__Constante__ValAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:190:1: ( ( rule__Constante__ValAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:190:1: ( ( rule__Constante__ValAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:191:1: ( rule__Constante__ValAssignment )
            {
             before(grammarAccess.getConstanteAccess().getValAssignment()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:192:1: ( rule__Constante__ValAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:192:2: rule__Constante__ValAssignment
            {
            pushFollow(FOLLOW_rule__Constante__ValAssignment_in_ruleConstante339);
            rule__Constante__ValAssignment();

            state._fsp--;


            }

             after(grammarAccess.getConstanteAccess().getValAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleConstante"


    // $ANTLR start "entryRuleMultiplication"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:204:1: entryRuleMultiplication : ruleMultiplication EOF ;
    public final void entryRuleMultiplication() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:205:1: ( ruleMultiplication EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:206:1: ruleMultiplication EOF
            {
             before(grammarAccess.getMultiplicationRule()); 
            pushFollow(FOLLOW_ruleMultiplication_in_entryRuleMultiplication366);
            ruleMultiplication();

            state._fsp--;

             after(grammarAccess.getMultiplicationRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplication373); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMultiplication"


    // $ANTLR start "ruleMultiplication"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:213:1: ruleMultiplication : ( ( rule__Multiplication__Group__0 ) ) ;
    public final void ruleMultiplication() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:217:2: ( ( ( rule__Multiplication__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:218:1: ( ( rule__Multiplication__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:218:1: ( ( rule__Multiplication__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:219:1: ( rule__Multiplication__Group__0 )
            {
             before(grammarAccess.getMultiplicationAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:220:1: ( rule__Multiplication__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:220:2: rule__Multiplication__Group__0
            {
            pushFollow(FOLLOW_rule__Multiplication__Group__0_in_ruleMultiplication399);
            rule__Multiplication__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicationAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMultiplication"


    // $ANTLR start "entryRulePrimary"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:232:1: entryRulePrimary : rulePrimary EOF ;
    public final void entryRulePrimary() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:233:1: ( rulePrimary EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:234:1: rulePrimary EOF
            {
             before(grammarAccess.getPrimaryRule()); 
            pushFollow(FOLLOW_rulePrimary_in_entryRulePrimary426);
            rulePrimary();

            state._fsp--;

             after(grammarAccess.getPrimaryRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimary433); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePrimary"


    // $ANTLR start "rulePrimary"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:241:1: rulePrimary : ( ( rule__Primary__Alternatives ) ) ;
    public final void rulePrimary() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:245:2: ( ( ( rule__Primary__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:246:1: ( ( rule__Primary__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:246:1: ( ( rule__Primary__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:247:1: ( rule__Primary__Alternatives )
            {
             before(grammarAccess.getPrimaryAccess().getAlternatives()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:248:1: ( rule__Primary__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:248:2: rule__Primary__Alternatives
            {
            pushFollow(FOLLOW_rule__Primary__Alternatives_in_rulePrimary459);
            rule__Primary__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getPrimaryAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePrimary"


    // $ANTLR start "entryRuleVariableRef"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:260:1: entryRuleVariableRef : ruleVariableRef EOF ;
    public final void entryRuleVariableRef() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:261:1: ( ruleVariableRef EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:262:1: ruleVariableRef EOF
            {
             before(grammarAccess.getVariableRefRule()); 
            pushFollow(FOLLOW_ruleVariableRef_in_entryRuleVariableRef486);
            ruleVariableRef();

            state._fsp--;

             after(grammarAccess.getVariableRefRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableRef493); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVariableRef"


    // $ANTLR start "ruleVariableRef"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:269:1: ruleVariableRef : ( ( rule__VariableRef__VarAssignment ) ) ;
    public final void ruleVariableRef() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:273:2: ( ( ( rule__VariableRef__VarAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:274:1: ( ( rule__VariableRef__VarAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:274:1: ( ( rule__VariableRef__VarAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:275:1: ( rule__VariableRef__VarAssignment )
            {
             before(grammarAccess.getVariableRefAccess().getVarAssignment()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:276:1: ( rule__VariableRef__VarAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:276:2: rule__VariableRef__VarAssignment
            {
            pushFollow(FOLLOW_rule__VariableRef__VarAssignment_in_ruleVariableRef519);
            rule__VariableRef__VarAssignment();

            state._fsp--;


            }

             after(grammarAccess.getVariableRefAccess().getVarAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVariableRef"


    // $ANTLR start "entryRuleAddition"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:288:1: entryRuleAddition : ruleAddition EOF ;
    public final void entryRuleAddition() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:289:1: ( ruleAddition EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:290:1: ruleAddition EOF
            {
             before(grammarAccess.getAdditionRule()); 
            pushFollow(FOLLOW_ruleAddition_in_entryRuleAddition546);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getAdditionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAddition553); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAddition"


    // $ANTLR start "ruleAddition"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:297:1: ruleAddition : ( ( rule__Addition__Group__0 ) ) ;
    public final void ruleAddition() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:301:2: ( ( ( rule__Addition__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:302:1: ( ( rule__Addition__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:302:1: ( ( rule__Addition__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:303:1: ( rule__Addition__Group__0 )
            {
             before(grammarAccess.getAdditionAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:304:1: ( rule__Addition__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:304:2: rule__Addition__Group__0
            {
            pushFollow(FOLLOW_rule__Addition__Group__0_in_ruleAddition579);
            rule__Addition__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAdditionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAddition"


    // $ANTLR start "entryRuleTRANSITION"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:316:1: entryRuleTRANSITION : ruleTRANSITION EOF ;
    public final void entryRuleTRANSITION() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:317:1: ( ruleTRANSITION EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:318:1: ruleTRANSITION EOF
            {
             before(grammarAccess.getTRANSITIONRule()); 
            pushFollow(FOLLOW_ruleTRANSITION_in_entryRuleTRANSITION606);
            ruleTRANSITION();

            state._fsp--;

             after(grammarAccess.getTRANSITIONRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTRANSITION613); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTRANSITION"


    // $ANTLR start "ruleTRANSITION"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:325:1: ruleTRANSITION : ( ( rule__TRANSITION__Group__0 ) ) ;
    public final void ruleTRANSITION() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:329:2: ( ( ( rule__TRANSITION__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:330:1: ( ( rule__TRANSITION__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:330:1: ( ( rule__TRANSITION__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:331:1: ( rule__TRANSITION__Group__0 )
            {
             before(grammarAccess.getTRANSITIONAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:332:1: ( rule__TRANSITION__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:332:2: rule__TRANSITION__Group__0
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__0_in_ruleTRANSITION639);
            rule__TRANSITION__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTRANSITIONAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTRANSITION"


    // $ANTLR start "entryRuleAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:344:1: entryRuleAssignment : ruleAssignment EOF ;
    public final void entryRuleAssignment() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:345:1: ( ruleAssignment EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:346:1: ruleAssignment EOF
            {
             before(grammarAccess.getAssignmentRule()); 
            pushFollow(FOLLOW_ruleAssignment_in_entryRuleAssignment666);
            ruleAssignment();

            state._fsp--;

             after(grammarAccess.getAssignmentRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssignment673); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAssignment"


    // $ANTLR start "ruleAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:353:1: ruleAssignment : ( ( rule__Assignment__Group__0 ) ) ;
    public final void ruleAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:357:2: ( ( ( rule__Assignment__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:358:1: ( ( rule__Assignment__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:358:1: ( ( rule__Assignment__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:359:1: ( rule__Assignment__Group__0 )
            {
             before(grammarAccess.getAssignmentAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:360:1: ( rule__Assignment__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:360:2: rule__Assignment__Group__0
            {
            pushFollow(FOLLOW_rule__Assignment__Group__0_in_ruleAssignment699);
            rule__Assignment__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAssignmentAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAssignment"


    // $ANTLR start "rule__Primary__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:372:1: rule__Primary__Alternatives : ( ( ruleVariableRef ) | ( ruleConstante ) | ( ( rule__Primary__Group_2__0 ) ) );
    public final void rule__Primary__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:376:1: ( ( ruleVariableRef ) | ( ruleConstante ) | ( ( rule__Primary__Group_2__0 ) ) )
            int alt2=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt2=1;
                }
                break;
            case RULE_INT:
                {
                alt2=2;
                }
                break;
            case 17:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:377:1: ( ruleVariableRef )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:377:1: ( ruleVariableRef )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:378:1: ruleVariableRef
                    {
                     before(grammarAccess.getPrimaryAccess().getVariableRefParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleVariableRef_in_rule__Primary__Alternatives735);
                    ruleVariableRef();

                    state._fsp--;

                     after(grammarAccess.getPrimaryAccess().getVariableRefParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:383:6: ( ruleConstante )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:383:6: ( ruleConstante )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:384:1: ruleConstante
                    {
                     before(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleConstante_in_rule__Primary__Alternatives752);
                    ruleConstante();

                    state._fsp--;

                     after(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:389:6: ( ( rule__Primary__Group_2__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:389:6: ( ( rule__Primary__Group_2__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:390:1: ( rule__Primary__Group_2__0 )
                    {
                     before(grammarAccess.getPrimaryAccess().getGroup_2()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:391:1: ( rule__Primary__Group_2__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:391:2: rule__Primary__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Primary__Group_2__0_in_rule__Primary__Alternatives769);
                    rule__Primary__Group_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getPrimaryAccess().getGroup_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Alternatives"


    // $ANTLR start "rule__PROGRAM__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:402:1: rule__PROGRAM__Group__0 : rule__PROGRAM__Group__0__Impl rule__PROGRAM__Group__1 ;
    public final void rule__PROGRAM__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:406:1: ( rule__PROGRAM__Group__0__Impl rule__PROGRAM__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:407:2: rule__PROGRAM__Group__0__Impl rule__PROGRAM__Group__1
            {
            pushFollow(FOLLOW_rule__PROGRAM__Group__0__Impl_in_rule__PROGRAM__Group__0800);
            rule__PROGRAM__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PROGRAM__Group__1_in_rule__PROGRAM__Group__0803);
            rule__PROGRAM__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__0"


    // $ANTLR start "rule__PROGRAM__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:414:1: rule__PROGRAM__Group__0__Impl : ( 'System' ) ;
    public final void rule__PROGRAM__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:418:1: ( ( 'System' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:419:1: ( 'System' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:419:1: ( 'System' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:420:1: 'System'
            {
             before(grammarAccess.getPROGRAMAccess().getSystemKeyword_0()); 
            match(input,13,FOLLOW_13_in_rule__PROGRAM__Group__0__Impl831); 
             after(grammarAccess.getPROGRAMAccess().getSystemKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__0__Impl"


    // $ANTLR start "rule__PROGRAM__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:433:1: rule__PROGRAM__Group__1 : rule__PROGRAM__Group__1__Impl rule__PROGRAM__Group__2 ;
    public final void rule__PROGRAM__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:437:1: ( rule__PROGRAM__Group__1__Impl rule__PROGRAM__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:438:2: rule__PROGRAM__Group__1__Impl rule__PROGRAM__Group__2
            {
            pushFollow(FOLLOW_rule__PROGRAM__Group__1__Impl_in_rule__PROGRAM__Group__1862);
            rule__PROGRAM__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PROGRAM__Group__2_in_rule__PROGRAM__Group__1865);
            rule__PROGRAM__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__1"


    // $ANTLR start "rule__PROGRAM__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:445:1: rule__PROGRAM__Group__1__Impl : ( ( rule__PROGRAM__NameAssignment_1 ) ) ;
    public final void rule__PROGRAM__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:449:1: ( ( ( rule__PROGRAM__NameAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:450:1: ( ( rule__PROGRAM__NameAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:450:1: ( ( rule__PROGRAM__NameAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:451:1: ( rule__PROGRAM__NameAssignment_1 )
            {
             before(grammarAccess.getPROGRAMAccess().getNameAssignment_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:452:1: ( rule__PROGRAM__NameAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:452:2: rule__PROGRAM__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__PROGRAM__NameAssignment_1_in_rule__PROGRAM__Group__1__Impl892);
            rule__PROGRAM__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getPROGRAMAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__1__Impl"


    // $ANTLR start "rule__PROGRAM__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:462:1: rule__PROGRAM__Group__2 : rule__PROGRAM__Group__2__Impl rule__PROGRAM__Group__3 ;
    public final void rule__PROGRAM__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:466:1: ( rule__PROGRAM__Group__2__Impl rule__PROGRAM__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:467:2: rule__PROGRAM__Group__2__Impl rule__PROGRAM__Group__3
            {
            pushFollow(FOLLOW_rule__PROGRAM__Group__2__Impl_in_rule__PROGRAM__Group__2922);
            rule__PROGRAM__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PROGRAM__Group__3_in_rule__PROGRAM__Group__2925);
            rule__PROGRAM__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__2"


    // $ANTLR start "rule__PROGRAM__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:474:1: rule__PROGRAM__Group__2__Impl : ( ruleDEBUT ) ;
    public final void rule__PROGRAM__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:478:1: ( ( ruleDEBUT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:479:1: ( ruleDEBUT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:479:1: ( ruleDEBUT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:480:1: ruleDEBUT
            {
             before(grammarAccess.getPROGRAMAccess().getDEBUTParserRuleCall_2()); 
            pushFollow(FOLLOW_ruleDEBUT_in_rule__PROGRAM__Group__2__Impl952);
            ruleDEBUT();

            state._fsp--;

             after(grammarAccess.getPROGRAMAccess().getDEBUTParserRuleCall_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__2__Impl"


    // $ANTLR start "rule__PROGRAM__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:491:1: rule__PROGRAM__Group__3 : rule__PROGRAM__Group__3__Impl rule__PROGRAM__Group__4 ;
    public final void rule__PROGRAM__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:495:1: ( rule__PROGRAM__Group__3__Impl rule__PROGRAM__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:496:2: rule__PROGRAM__Group__3__Impl rule__PROGRAM__Group__4
            {
            pushFollow(FOLLOW_rule__PROGRAM__Group__3__Impl_in_rule__PROGRAM__Group__3981);
            rule__PROGRAM__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PROGRAM__Group__4_in_rule__PROGRAM__Group__3984);
            rule__PROGRAM__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__3"


    // $ANTLR start "rule__PROGRAM__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:503:1: rule__PROGRAM__Group__3__Impl : ( ( rule__PROGRAM__VariablesAssignment_3 )* ) ;
    public final void rule__PROGRAM__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:507:1: ( ( ( rule__PROGRAM__VariablesAssignment_3 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:508:1: ( ( rule__PROGRAM__VariablesAssignment_3 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:508:1: ( ( rule__PROGRAM__VariablesAssignment_3 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:509:1: ( rule__PROGRAM__VariablesAssignment_3 )*
            {
             before(grammarAccess.getPROGRAMAccess().getVariablesAssignment_3()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:510:1: ( rule__PROGRAM__VariablesAssignment_3 )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==RULE_ID) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:510:2: rule__PROGRAM__VariablesAssignment_3
            	    {
            	    pushFollow(FOLLOW_rule__PROGRAM__VariablesAssignment_3_in_rule__PROGRAM__Group__3__Impl1011);
            	    rule__PROGRAM__VariablesAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

             after(grammarAccess.getPROGRAMAccess().getVariablesAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__3__Impl"


    // $ANTLR start "rule__PROGRAM__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:520:1: rule__PROGRAM__Group__4 : rule__PROGRAM__Group__4__Impl rule__PROGRAM__Group__5 ;
    public final void rule__PROGRAM__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:524:1: ( rule__PROGRAM__Group__4__Impl rule__PROGRAM__Group__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:525:2: rule__PROGRAM__Group__4__Impl rule__PROGRAM__Group__5
            {
            pushFollow(FOLLOW_rule__PROGRAM__Group__4__Impl_in_rule__PROGRAM__Group__41042);
            rule__PROGRAM__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PROGRAM__Group__5_in_rule__PROGRAM__Group__41045);
            rule__PROGRAM__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__4"


    // $ANTLR start "rule__PROGRAM__Group__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:532:1: rule__PROGRAM__Group__4__Impl : ( ( ( rule__PROGRAM__TransitionsAssignment_4 ) ) ( ( rule__PROGRAM__TransitionsAssignment_4 )* ) ) ;
    public final void rule__PROGRAM__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:536:1: ( ( ( ( rule__PROGRAM__TransitionsAssignment_4 ) ) ( ( rule__PROGRAM__TransitionsAssignment_4 )* ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:537:1: ( ( ( rule__PROGRAM__TransitionsAssignment_4 ) ) ( ( rule__PROGRAM__TransitionsAssignment_4 )* ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:537:1: ( ( ( rule__PROGRAM__TransitionsAssignment_4 ) ) ( ( rule__PROGRAM__TransitionsAssignment_4 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:538:1: ( ( rule__PROGRAM__TransitionsAssignment_4 ) ) ( ( rule__PROGRAM__TransitionsAssignment_4 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:538:1: ( ( rule__PROGRAM__TransitionsAssignment_4 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:539:1: ( rule__PROGRAM__TransitionsAssignment_4 )
            {
             before(grammarAccess.getPROGRAMAccess().getTransitionsAssignment_4()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:540:1: ( rule__PROGRAM__TransitionsAssignment_4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:540:2: rule__PROGRAM__TransitionsAssignment_4
            {
            pushFollow(FOLLOW_rule__PROGRAM__TransitionsAssignment_4_in_rule__PROGRAM__Group__4__Impl1074);
            rule__PROGRAM__TransitionsAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getPROGRAMAccess().getTransitionsAssignment_4()); 

            }

            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:543:1: ( ( rule__PROGRAM__TransitionsAssignment_4 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:544:1: ( rule__PROGRAM__TransitionsAssignment_4 )*
            {
             before(grammarAccess.getPROGRAMAccess().getTransitionsAssignment_4()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:545:1: ( rule__PROGRAM__TransitionsAssignment_4 )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==20) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:545:2: rule__PROGRAM__TransitionsAssignment_4
            	    {
            	    pushFollow(FOLLOW_rule__PROGRAM__TransitionsAssignment_4_in_rule__PROGRAM__Group__4__Impl1086);
            	    rule__PROGRAM__TransitionsAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

             after(grammarAccess.getPROGRAMAccess().getTransitionsAssignment_4()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__4__Impl"


    // $ANTLR start "rule__PROGRAM__Group__5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:556:1: rule__PROGRAM__Group__5 : rule__PROGRAM__Group__5__Impl ;
    public final void rule__PROGRAM__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:560:1: ( rule__PROGRAM__Group__5__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:561:2: rule__PROGRAM__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__PROGRAM__Group__5__Impl_in_rule__PROGRAM__Group__51119);
            rule__PROGRAM__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__5"


    // $ANTLR start "rule__PROGRAM__Group__5__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:567:1: rule__PROGRAM__Group__5__Impl : ( ruleFIN ) ;
    public final void rule__PROGRAM__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:571:1: ( ( ruleFIN ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:572:1: ( ruleFIN )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:572:1: ( ruleFIN )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:573:1: ruleFIN
            {
             before(grammarAccess.getPROGRAMAccess().getFINParserRuleCall_5()); 
            pushFollow(FOLLOW_ruleFIN_in_rule__PROGRAM__Group__5__Impl1146);
            ruleFIN();

            state._fsp--;

             after(grammarAccess.getPROGRAMAccess().getFINParserRuleCall_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__Group__5__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:596:1: rule__VariableDeclaration__Group__0 : rule__VariableDeclaration__Group__0__Impl rule__VariableDeclaration__Group__1 ;
    public final void rule__VariableDeclaration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:600:1: ( rule__VariableDeclaration__Group__0__Impl rule__VariableDeclaration__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:601:2: rule__VariableDeclaration__Group__0__Impl rule__VariableDeclaration__Group__1
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__0__Impl_in_rule__VariableDeclaration__Group__01187);
            rule__VariableDeclaration__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group__1_in_rule__VariableDeclaration__Group__01190);
            rule__VariableDeclaration__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__Group__0"


    // $ANTLR start "rule__VariableDeclaration__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:608:1: rule__VariableDeclaration__Group__0__Impl : ( ( rule__VariableDeclaration__NameAssignment_0 ) ) ;
    public final void rule__VariableDeclaration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:612:1: ( ( ( rule__VariableDeclaration__NameAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:613:1: ( ( rule__VariableDeclaration__NameAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:613:1: ( ( rule__VariableDeclaration__NameAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:614:1: ( rule__VariableDeclaration__NameAssignment_0 )
            {
             before(grammarAccess.getVariableDeclarationAccess().getNameAssignment_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:615:1: ( rule__VariableDeclaration__NameAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:615:2: rule__VariableDeclaration__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__NameAssignment_0_in_rule__VariableDeclaration__Group__0__Impl1217);
            rule__VariableDeclaration__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getVariableDeclarationAccess().getNameAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__Group__0__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:625:1: rule__VariableDeclaration__Group__1 : rule__VariableDeclaration__Group__1__Impl rule__VariableDeclaration__Group__2 ;
    public final void rule__VariableDeclaration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:629:1: ( rule__VariableDeclaration__Group__1__Impl rule__VariableDeclaration__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:630:2: rule__VariableDeclaration__Group__1__Impl rule__VariableDeclaration__Group__2
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__1__Impl_in_rule__VariableDeclaration__Group__11247);
            rule__VariableDeclaration__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group__2_in_rule__VariableDeclaration__Group__11250);
            rule__VariableDeclaration__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__Group__1"


    // $ANTLR start "rule__VariableDeclaration__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:637:1: rule__VariableDeclaration__Group__1__Impl : ( '=' ) ;
    public final void rule__VariableDeclaration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:641:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:642:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:642:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:643:1: '='
            {
             before(grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_1()); 
            match(input,14,FOLLOW_14_in_rule__VariableDeclaration__Group__1__Impl1278); 
             after(grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__Group__1__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:656:1: rule__VariableDeclaration__Group__2 : rule__VariableDeclaration__Group__2__Impl rule__VariableDeclaration__Group__3 ;
    public final void rule__VariableDeclaration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:660:1: ( rule__VariableDeclaration__Group__2__Impl rule__VariableDeclaration__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:661:2: rule__VariableDeclaration__Group__2__Impl rule__VariableDeclaration__Group__3
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__2__Impl_in_rule__VariableDeclaration__Group__21309);
            rule__VariableDeclaration__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group__3_in_rule__VariableDeclaration__Group__21312);
            rule__VariableDeclaration__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__Group__2"


    // $ANTLR start "rule__VariableDeclaration__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:668:1: rule__VariableDeclaration__Group__2__Impl : ( ( rule__VariableDeclaration__InitValAssignment_2 ) ) ;
    public final void rule__VariableDeclaration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:672:1: ( ( ( rule__VariableDeclaration__InitValAssignment_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:673:1: ( ( rule__VariableDeclaration__InitValAssignment_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:673:1: ( ( rule__VariableDeclaration__InitValAssignment_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:674:1: ( rule__VariableDeclaration__InitValAssignment_2 )
            {
             before(grammarAccess.getVariableDeclarationAccess().getInitValAssignment_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:675:1: ( rule__VariableDeclaration__InitValAssignment_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:675:2: rule__VariableDeclaration__InitValAssignment_2
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__InitValAssignment_2_in_rule__VariableDeclaration__Group__2__Impl1339);
            rule__VariableDeclaration__InitValAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getVariableDeclarationAccess().getInitValAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__Group__2__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:685:1: rule__VariableDeclaration__Group__3 : rule__VariableDeclaration__Group__3__Impl ;
    public final void rule__VariableDeclaration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:689:1: ( rule__VariableDeclaration__Group__3__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:690:2: rule__VariableDeclaration__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__3__Impl_in_rule__VariableDeclaration__Group__31369);
            rule__VariableDeclaration__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__Group__3"


    // $ANTLR start "rule__VariableDeclaration__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:696:1: rule__VariableDeclaration__Group__3__Impl : ( ';' ) ;
    public final void rule__VariableDeclaration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:700:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:701:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:701:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:702:1: ';'
            {
             before(grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_3()); 
            match(input,15,FOLLOW_15_in_rule__VariableDeclaration__Group__3__Impl1397); 
             after(grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__Group__3__Impl"


    // $ANTLR start "rule__Multiplication__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:723:1: rule__Multiplication__Group__0 : rule__Multiplication__Group__0__Impl rule__Multiplication__Group__1 ;
    public final void rule__Multiplication__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:727:1: ( rule__Multiplication__Group__0__Impl rule__Multiplication__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:728:2: rule__Multiplication__Group__0__Impl rule__Multiplication__Group__1
            {
            pushFollow(FOLLOW_rule__Multiplication__Group__0__Impl_in_rule__Multiplication__Group__01436);
            rule__Multiplication__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Multiplication__Group__1_in_rule__Multiplication__Group__01439);
            rule__Multiplication__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group__0"


    // $ANTLR start "rule__Multiplication__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:735:1: rule__Multiplication__Group__0__Impl : ( rulePrimary ) ;
    public final void rule__Multiplication__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:739:1: ( ( rulePrimary ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:740:1: ( rulePrimary )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:740:1: ( rulePrimary )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:741:1: rulePrimary
            {
             before(grammarAccess.getMultiplicationAccess().getPrimaryParserRuleCall_0()); 
            pushFollow(FOLLOW_rulePrimary_in_rule__Multiplication__Group__0__Impl1466);
            rulePrimary();

            state._fsp--;

             after(grammarAccess.getMultiplicationAccess().getPrimaryParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group__0__Impl"


    // $ANTLR start "rule__Multiplication__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:752:1: rule__Multiplication__Group__1 : rule__Multiplication__Group__1__Impl ;
    public final void rule__Multiplication__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:756:1: ( rule__Multiplication__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:757:2: rule__Multiplication__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Multiplication__Group__1__Impl_in_rule__Multiplication__Group__11495);
            rule__Multiplication__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group__1"


    // $ANTLR start "rule__Multiplication__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:763:1: rule__Multiplication__Group__1__Impl : ( ( rule__Multiplication__Group_1__0 )* ) ;
    public final void rule__Multiplication__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:767:1: ( ( ( rule__Multiplication__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:768:1: ( ( rule__Multiplication__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:768:1: ( ( rule__Multiplication__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:769:1: ( rule__Multiplication__Group_1__0 )*
            {
             before(grammarAccess.getMultiplicationAccess().getGroup_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:770:1: ( rule__Multiplication__Group_1__0 )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==16) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:770:2: rule__Multiplication__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Multiplication__Group_1__0_in_rule__Multiplication__Group__1__Impl1522);
            	    rule__Multiplication__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

             after(grammarAccess.getMultiplicationAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group__1__Impl"


    // $ANTLR start "rule__Multiplication__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:784:1: rule__Multiplication__Group_1__0 : rule__Multiplication__Group_1__0__Impl rule__Multiplication__Group_1__1 ;
    public final void rule__Multiplication__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:788:1: ( rule__Multiplication__Group_1__0__Impl rule__Multiplication__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:789:2: rule__Multiplication__Group_1__0__Impl rule__Multiplication__Group_1__1
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1__0__Impl_in_rule__Multiplication__Group_1__01557);
            rule__Multiplication__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Multiplication__Group_1__1_in_rule__Multiplication__Group_1__01560);
            rule__Multiplication__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group_1__0"


    // $ANTLR start "rule__Multiplication__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:796:1: rule__Multiplication__Group_1__0__Impl : ( () ) ;
    public final void rule__Multiplication__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:800:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:801:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:801:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:802:1: ()
            {
             before(grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:803:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:805:1: 
            {
            }

             after(grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group_1__0__Impl"


    // $ANTLR start "rule__Multiplication__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:815:1: rule__Multiplication__Group_1__1 : rule__Multiplication__Group_1__1__Impl rule__Multiplication__Group_1__2 ;
    public final void rule__Multiplication__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:819:1: ( rule__Multiplication__Group_1__1__Impl rule__Multiplication__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:820:2: rule__Multiplication__Group_1__1__Impl rule__Multiplication__Group_1__2
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1__1__Impl_in_rule__Multiplication__Group_1__11618);
            rule__Multiplication__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Multiplication__Group_1__2_in_rule__Multiplication__Group_1__11621);
            rule__Multiplication__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group_1__1"


    // $ANTLR start "rule__Multiplication__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:827:1: rule__Multiplication__Group_1__1__Impl : ( '*' ) ;
    public final void rule__Multiplication__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:831:1: ( ( '*' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:832:1: ( '*' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:832:1: ( '*' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:833:1: '*'
            {
             before(grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_1()); 
            match(input,16,FOLLOW_16_in_rule__Multiplication__Group_1__1__Impl1649); 
             after(grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group_1__1__Impl"


    // $ANTLR start "rule__Multiplication__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:846:1: rule__Multiplication__Group_1__2 : rule__Multiplication__Group_1__2__Impl ;
    public final void rule__Multiplication__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:850:1: ( rule__Multiplication__Group_1__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:851:2: rule__Multiplication__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1__2__Impl_in_rule__Multiplication__Group_1__21680);
            rule__Multiplication__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group_1__2"


    // $ANTLR start "rule__Multiplication__Group_1__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:857:1: rule__Multiplication__Group_1__2__Impl : ( ( rule__Multiplication__RightAssignment_1_2 ) ) ;
    public final void rule__Multiplication__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:861:1: ( ( ( rule__Multiplication__RightAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:862:1: ( ( rule__Multiplication__RightAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:862:1: ( ( rule__Multiplication__RightAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:863:1: ( rule__Multiplication__RightAssignment_1_2 )
            {
             before(grammarAccess.getMultiplicationAccess().getRightAssignment_1_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:864:1: ( rule__Multiplication__RightAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:864:2: rule__Multiplication__RightAssignment_1_2
            {
            pushFollow(FOLLOW_rule__Multiplication__RightAssignment_1_2_in_rule__Multiplication__Group_1__2__Impl1707);
            rule__Multiplication__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicationAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group_1__2__Impl"


    // $ANTLR start "rule__Primary__Group_2__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:880:1: rule__Primary__Group_2__0 : rule__Primary__Group_2__0__Impl rule__Primary__Group_2__1 ;
    public final void rule__Primary__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:884:1: ( rule__Primary__Group_2__0__Impl rule__Primary__Group_2__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:885:2: rule__Primary__Group_2__0__Impl rule__Primary__Group_2__1
            {
            pushFollow(FOLLOW_rule__Primary__Group_2__0__Impl_in_rule__Primary__Group_2__01743);
            rule__Primary__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Primary__Group_2__1_in_rule__Primary__Group_2__01746);
            rule__Primary__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Group_2__0"


    // $ANTLR start "rule__Primary__Group_2__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:892:1: rule__Primary__Group_2__0__Impl : ( '(' ) ;
    public final void rule__Primary__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:896:1: ( ( '(' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:897:1: ( '(' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:897:1: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:898:1: '('
            {
             before(grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0()); 
            match(input,17,FOLLOW_17_in_rule__Primary__Group_2__0__Impl1774); 
             after(grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Group_2__0__Impl"


    // $ANTLR start "rule__Primary__Group_2__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:911:1: rule__Primary__Group_2__1 : rule__Primary__Group_2__1__Impl rule__Primary__Group_2__2 ;
    public final void rule__Primary__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:915:1: ( rule__Primary__Group_2__1__Impl rule__Primary__Group_2__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:916:2: rule__Primary__Group_2__1__Impl rule__Primary__Group_2__2
            {
            pushFollow(FOLLOW_rule__Primary__Group_2__1__Impl_in_rule__Primary__Group_2__11805);
            rule__Primary__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Primary__Group_2__2_in_rule__Primary__Group_2__11808);
            rule__Primary__Group_2__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Group_2__1"


    // $ANTLR start "rule__Primary__Group_2__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:923:1: rule__Primary__Group_2__1__Impl : ( ruleAddition ) ;
    public final void rule__Primary__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:927:1: ( ( ruleAddition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:928:1: ( ruleAddition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:928:1: ( ruleAddition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:929:1: ruleAddition
            {
             before(grammarAccess.getPrimaryAccess().getAdditionParserRuleCall_2_1()); 
            pushFollow(FOLLOW_ruleAddition_in_rule__Primary__Group_2__1__Impl1835);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getPrimaryAccess().getAdditionParserRuleCall_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Group_2__1__Impl"


    // $ANTLR start "rule__Primary__Group_2__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:940:1: rule__Primary__Group_2__2 : rule__Primary__Group_2__2__Impl ;
    public final void rule__Primary__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:944:1: ( rule__Primary__Group_2__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:945:2: rule__Primary__Group_2__2__Impl
            {
            pushFollow(FOLLOW_rule__Primary__Group_2__2__Impl_in_rule__Primary__Group_2__21864);
            rule__Primary__Group_2__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Group_2__2"


    // $ANTLR start "rule__Primary__Group_2__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:951:1: rule__Primary__Group_2__2__Impl : ( ')' ) ;
    public final void rule__Primary__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:955:1: ( ( ')' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:956:1: ( ')' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:956:1: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:957:1: ')'
            {
             before(grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_2_2()); 
            match(input,18,FOLLOW_18_in_rule__Primary__Group_2__2__Impl1892); 
             after(grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Group_2__2__Impl"


    // $ANTLR start "rule__Addition__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:976:1: rule__Addition__Group__0 : rule__Addition__Group__0__Impl rule__Addition__Group__1 ;
    public final void rule__Addition__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:980:1: ( rule__Addition__Group__0__Impl rule__Addition__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:981:2: rule__Addition__Group__0__Impl rule__Addition__Group__1
            {
            pushFollow(FOLLOW_rule__Addition__Group__0__Impl_in_rule__Addition__Group__01929);
            rule__Addition__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Addition__Group__1_in_rule__Addition__Group__01932);
            rule__Addition__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group__0"


    // $ANTLR start "rule__Addition__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:988:1: rule__Addition__Group__0__Impl : ( ruleMultiplication ) ;
    public final void rule__Addition__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:992:1: ( ( ruleMultiplication ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:993:1: ( ruleMultiplication )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:993:1: ( ruleMultiplication )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:994:1: ruleMultiplication
            {
             before(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleMultiplication_in_rule__Addition__Group__0__Impl1959);
            ruleMultiplication();

            state._fsp--;

             after(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group__0__Impl"


    // $ANTLR start "rule__Addition__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1005:1: rule__Addition__Group__1 : rule__Addition__Group__1__Impl ;
    public final void rule__Addition__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1009:1: ( rule__Addition__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1010:2: rule__Addition__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Addition__Group__1__Impl_in_rule__Addition__Group__11988);
            rule__Addition__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group__1"


    // $ANTLR start "rule__Addition__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1016:1: rule__Addition__Group__1__Impl : ( ( rule__Addition__Group_1__0 )* ) ;
    public final void rule__Addition__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1020:1: ( ( ( rule__Addition__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1021:1: ( ( rule__Addition__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1021:1: ( ( rule__Addition__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1022:1: ( rule__Addition__Group_1__0 )*
            {
             before(grammarAccess.getAdditionAccess().getGroup_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1023:1: ( rule__Addition__Group_1__0 )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==19) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1023:2: rule__Addition__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Addition__Group_1__0_in_rule__Addition__Group__1__Impl2015);
            	    rule__Addition__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

             after(grammarAccess.getAdditionAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group__1__Impl"


    // $ANTLR start "rule__Addition__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1037:1: rule__Addition__Group_1__0 : rule__Addition__Group_1__0__Impl rule__Addition__Group_1__1 ;
    public final void rule__Addition__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1041:1: ( rule__Addition__Group_1__0__Impl rule__Addition__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1042:2: rule__Addition__Group_1__0__Impl rule__Addition__Group_1__1
            {
            pushFollow(FOLLOW_rule__Addition__Group_1__0__Impl_in_rule__Addition__Group_1__02050);
            rule__Addition__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Addition__Group_1__1_in_rule__Addition__Group_1__02053);
            rule__Addition__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group_1__0"


    // $ANTLR start "rule__Addition__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1049:1: rule__Addition__Group_1__0__Impl : ( () ) ;
    public final void rule__Addition__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1053:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1054:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1054:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1055:1: ()
            {
             before(grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1056:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1058:1: 
            {
            }

             after(grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group_1__0__Impl"


    // $ANTLR start "rule__Addition__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1068:1: rule__Addition__Group_1__1 : rule__Addition__Group_1__1__Impl rule__Addition__Group_1__2 ;
    public final void rule__Addition__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1072:1: ( rule__Addition__Group_1__1__Impl rule__Addition__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1073:2: rule__Addition__Group_1__1__Impl rule__Addition__Group_1__2
            {
            pushFollow(FOLLOW_rule__Addition__Group_1__1__Impl_in_rule__Addition__Group_1__12111);
            rule__Addition__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Addition__Group_1__2_in_rule__Addition__Group_1__12114);
            rule__Addition__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group_1__1"


    // $ANTLR start "rule__Addition__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1080:1: rule__Addition__Group_1__1__Impl : ( '+' ) ;
    public final void rule__Addition__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1084:1: ( ( '+' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1085:1: ( '+' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1085:1: ( '+' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1086:1: '+'
            {
             before(grammarAccess.getAdditionAccess().getPlusSignKeyword_1_1()); 
            match(input,19,FOLLOW_19_in_rule__Addition__Group_1__1__Impl2142); 
             after(grammarAccess.getAdditionAccess().getPlusSignKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group_1__1__Impl"


    // $ANTLR start "rule__Addition__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1099:1: rule__Addition__Group_1__2 : rule__Addition__Group_1__2__Impl ;
    public final void rule__Addition__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1103:1: ( rule__Addition__Group_1__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1104:2: rule__Addition__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__Addition__Group_1__2__Impl_in_rule__Addition__Group_1__22173);
            rule__Addition__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group_1__2"


    // $ANTLR start "rule__Addition__Group_1__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1110:1: rule__Addition__Group_1__2__Impl : ( ( rule__Addition__RightAssignment_1_2 ) ) ;
    public final void rule__Addition__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1114:1: ( ( ( rule__Addition__RightAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1115:1: ( ( rule__Addition__RightAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1115:1: ( ( rule__Addition__RightAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1116:1: ( rule__Addition__RightAssignment_1_2 )
            {
             before(grammarAccess.getAdditionAccess().getRightAssignment_1_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1117:1: ( rule__Addition__RightAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1117:2: rule__Addition__RightAssignment_1_2
            {
            pushFollow(FOLLOW_rule__Addition__RightAssignment_1_2_in_rule__Addition__Group_1__2__Impl2200);
            rule__Addition__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getAdditionAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group_1__2__Impl"


    // $ANTLR start "rule__TRANSITION__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1133:1: rule__TRANSITION__Group__0 : rule__TRANSITION__Group__0__Impl rule__TRANSITION__Group__1 ;
    public final void rule__TRANSITION__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1137:1: ( rule__TRANSITION__Group__0__Impl rule__TRANSITION__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1138:2: rule__TRANSITION__Group__0__Impl rule__TRANSITION__Group__1
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__0__Impl_in_rule__TRANSITION__Group__02236);
            rule__TRANSITION__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TRANSITION__Group__1_in_rule__TRANSITION__Group__02239);
            rule__TRANSITION__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__0"


    // $ANTLR start "rule__TRANSITION__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1145:1: rule__TRANSITION__Group__0__Impl : ( 'transition' ) ;
    public final void rule__TRANSITION__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1149:1: ( ( 'transition' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1150:1: ( 'transition' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1150:1: ( 'transition' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1151:1: 'transition'
            {
             before(grammarAccess.getTRANSITIONAccess().getTransitionKeyword_0()); 
            match(input,20,FOLLOW_20_in_rule__TRANSITION__Group__0__Impl2267); 
             after(grammarAccess.getTRANSITIONAccess().getTransitionKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__0__Impl"


    // $ANTLR start "rule__TRANSITION__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1164:1: rule__TRANSITION__Group__1 : rule__TRANSITION__Group__1__Impl rule__TRANSITION__Group__2 ;
    public final void rule__TRANSITION__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1168:1: ( rule__TRANSITION__Group__1__Impl rule__TRANSITION__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1169:2: rule__TRANSITION__Group__1__Impl rule__TRANSITION__Group__2
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__1__Impl_in_rule__TRANSITION__Group__12298);
            rule__TRANSITION__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TRANSITION__Group__2_in_rule__TRANSITION__Group__12301);
            rule__TRANSITION__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__1"


    // $ANTLR start "rule__TRANSITION__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1176:1: rule__TRANSITION__Group__1__Impl : ( ( rule__TRANSITION__NameAssignment_1 ) ) ;
    public final void rule__TRANSITION__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1180:1: ( ( ( rule__TRANSITION__NameAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1181:1: ( ( rule__TRANSITION__NameAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1181:1: ( ( rule__TRANSITION__NameAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1182:1: ( rule__TRANSITION__NameAssignment_1 )
            {
             before(grammarAccess.getTRANSITIONAccess().getNameAssignment_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1183:1: ( rule__TRANSITION__NameAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1183:2: rule__TRANSITION__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__TRANSITION__NameAssignment_1_in_rule__TRANSITION__Group__1__Impl2328);
            rule__TRANSITION__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getTRANSITIONAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__1__Impl"


    // $ANTLR start "rule__TRANSITION__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1193:1: rule__TRANSITION__Group__2 : rule__TRANSITION__Group__2__Impl rule__TRANSITION__Group__3 ;
    public final void rule__TRANSITION__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1197:1: ( rule__TRANSITION__Group__2__Impl rule__TRANSITION__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1198:2: rule__TRANSITION__Group__2__Impl rule__TRANSITION__Group__3
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__2__Impl_in_rule__TRANSITION__Group__22358);
            rule__TRANSITION__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TRANSITION__Group__3_in_rule__TRANSITION__Group__22361);
            rule__TRANSITION__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__2"


    // $ANTLR start "rule__TRANSITION__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1205:1: rule__TRANSITION__Group__2__Impl : ( '[' ) ;
    public final void rule__TRANSITION__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1209:1: ( ( '[' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1210:1: ( '[' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1210:1: ( '[' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1211:1: '['
            {
             before(grammarAccess.getTRANSITIONAccess().getLeftSquareBracketKeyword_2()); 
            match(input,21,FOLLOW_21_in_rule__TRANSITION__Group__2__Impl2389); 
             after(grammarAccess.getTRANSITIONAccess().getLeftSquareBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__2__Impl"


    // $ANTLR start "rule__TRANSITION__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1224:1: rule__TRANSITION__Group__3 : rule__TRANSITION__Group__3__Impl rule__TRANSITION__Group__4 ;
    public final void rule__TRANSITION__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1228:1: ( rule__TRANSITION__Group__3__Impl rule__TRANSITION__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1229:2: rule__TRANSITION__Group__3__Impl rule__TRANSITION__Group__4
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__3__Impl_in_rule__TRANSITION__Group__32420);
            rule__TRANSITION__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TRANSITION__Group__4_in_rule__TRANSITION__Group__32423);
            rule__TRANSITION__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__3"


    // $ANTLR start "rule__TRANSITION__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1236:1: rule__TRANSITION__Group__3__Impl : ( 'TRUE' ) ;
    public final void rule__TRANSITION__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1240:1: ( ( 'TRUE' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1241:1: ( 'TRUE' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1241:1: ( 'TRUE' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1242:1: 'TRUE'
            {
             before(grammarAccess.getTRANSITIONAccess().getTRUEKeyword_3()); 
            match(input,22,FOLLOW_22_in_rule__TRANSITION__Group__3__Impl2451); 
             after(grammarAccess.getTRANSITIONAccess().getTRUEKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__3__Impl"


    // $ANTLR start "rule__TRANSITION__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1255:1: rule__TRANSITION__Group__4 : rule__TRANSITION__Group__4__Impl rule__TRANSITION__Group__5 ;
    public final void rule__TRANSITION__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1259:1: ( rule__TRANSITION__Group__4__Impl rule__TRANSITION__Group__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1260:2: rule__TRANSITION__Group__4__Impl rule__TRANSITION__Group__5
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__4__Impl_in_rule__TRANSITION__Group__42482);
            rule__TRANSITION__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TRANSITION__Group__5_in_rule__TRANSITION__Group__42485);
            rule__TRANSITION__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__4"


    // $ANTLR start "rule__TRANSITION__Group__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1267:1: rule__TRANSITION__Group__4__Impl : ( ']' ) ;
    public final void rule__TRANSITION__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1271:1: ( ( ']' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1272:1: ( ']' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1272:1: ( ']' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1273:1: ']'
            {
             before(grammarAccess.getTRANSITIONAccess().getRightSquareBracketKeyword_4()); 
            match(input,23,FOLLOW_23_in_rule__TRANSITION__Group__4__Impl2513); 
             after(grammarAccess.getTRANSITIONAccess().getRightSquareBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__4__Impl"


    // $ANTLR start "rule__TRANSITION__Group__5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1286:1: rule__TRANSITION__Group__5 : rule__TRANSITION__Group__5__Impl rule__TRANSITION__Group__6 ;
    public final void rule__TRANSITION__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1290:1: ( rule__TRANSITION__Group__5__Impl rule__TRANSITION__Group__6 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1291:2: rule__TRANSITION__Group__5__Impl rule__TRANSITION__Group__6
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__5__Impl_in_rule__TRANSITION__Group__52544);
            rule__TRANSITION__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TRANSITION__Group__6_in_rule__TRANSITION__Group__52547);
            rule__TRANSITION__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__5"


    // $ANTLR start "rule__TRANSITION__Group__5__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1298:1: rule__TRANSITION__Group__5__Impl : ( ( rule__TRANSITION__Group_5__0 )? ) ;
    public final void rule__TRANSITION__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1302:1: ( ( ( rule__TRANSITION__Group_5__0 )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1303:1: ( ( rule__TRANSITION__Group_5__0 )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1303:1: ( ( rule__TRANSITION__Group_5__0 )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1304:1: ( rule__TRANSITION__Group_5__0 )?
            {
             before(grammarAccess.getTRANSITIONAccess().getGroup_5()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1305:1: ( rule__TRANSITION__Group_5__0 )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==24) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1305:2: rule__TRANSITION__Group_5__0
                    {
                    pushFollow(FOLLOW_rule__TRANSITION__Group_5__0_in_rule__TRANSITION__Group__5__Impl2574);
                    rule__TRANSITION__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTRANSITIONAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__5__Impl"


    // $ANTLR start "rule__TRANSITION__Group__6"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1315:1: rule__TRANSITION__Group__6 : rule__TRANSITION__Group__6__Impl rule__TRANSITION__Group__7 ;
    public final void rule__TRANSITION__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1319:1: ( rule__TRANSITION__Group__6__Impl rule__TRANSITION__Group__7 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1320:2: rule__TRANSITION__Group__6__Impl rule__TRANSITION__Group__7
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__6__Impl_in_rule__TRANSITION__Group__62605);
            rule__TRANSITION__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TRANSITION__Group__7_in_rule__TRANSITION__Group__62608);
            rule__TRANSITION__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__6"


    // $ANTLR start "rule__TRANSITION__Group__6__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1327:1: rule__TRANSITION__Group__6__Impl : ( ruleDEBUT ) ;
    public final void rule__TRANSITION__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1331:1: ( ( ruleDEBUT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1332:1: ( ruleDEBUT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1332:1: ( ruleDEBUT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1333:1: ruleDEBUT
            {
             before(grammarAccess.getTRANSITIONAccess().getDEBUTParserRuleCall_6()); 
            pushFollow(FOLLOW_ruleDEBUT_in_rule__TRANSITION__Group__6__Impl2635);
            ruleDEBUT();

            state._fsp--;

             after(grammarAccess.getTRANSITIONAccess().getDEBUTParserRuleCall_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__6__Impl"


    // $ANTLR start "rule__TRANSITION__Group__7"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1344:1: rule__TRANSITION__Group__7 : rule__TRANSITION__Group__7__Impl rule__TRANSITION__Group__8 ;
    public final void rule__TRANSITION__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1348:1: ( rule__TRANSITION__Group__7__Impl rule__TRANSITION__Group__8 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1349:2: rule__TRANSITION__Group__7__Impl rule__TRANSITION__Group__8
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__7__Impl_in_rule__TRANSITION__Group__72664);
            rule__TRANSITION__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TRANSITION__Group__8_in_rule__TRANSITION__Group__72667);
            rule__TRANSITION__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__7"


    // $ANTLR start "rule__TRANSITION__Group__7__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1356:1: rule__TRANSITION__Group__7__Impl : ( ( ( rule__TRANSITION__AssignmentsAssignment_7 ) ) ( ( rule__TRANSITION__AssignmentsAssignment_7 )* ) ) ;
    public final void rule__TRANSITION__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1360:1: ( ( ( ( rule__TRANSITION__AssignmentsAssignment_7 ) ) ( ( rule__TRANSITION__AssignmentsAssignment_7 )* ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1361:1: ( ( ( rule__TRANSITION__AssignmentsAssignment_7 ) ) ( ( rule__TRANSITION__AssignmentsAssignment_7 )* ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1361:1: ( ( ( rule__TRANSITION__AssignmentsAssignment_7 ) ) ( ( rule__TRANSITION__AssignmentsAssignment_7 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1362:1: ( ( rule__TRANSITION__AssignmentsAssignment_7 ) ) ( ( rule__TRANSITION__AssignmentsAssignment_7 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1362:1: ( ( rule__TRANSITION__AssignmentsAssignment_7 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1363:1: ( rule__TRANSITION__AssignmentsAssignment_7 )
            {
             before(grammarAccess.getTRANSITIONAccess().getAssignmentsAssignment_7()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1364:1: ( rule__TRANSITION__AssignmentsAssignment_7 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1364:2: rule__TRANSITION__AssignmentsAssignment_7
            {
            pushFollow(FOLLOW_rule__TRANSITION__AssignmentsAssignment_7_in_rule__TRANSITION__Group__7__Impl2696);
            rule__TRANSITION__AssignmentsAssignment_7();

            state._fsp--;


            }

             after(grammarAccess.getTRANSITIONAccess().getAssignmentsAssignment_7()); 

            }

            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1367:1: ( ( rule__TRANSITION__AssignmentsAssignment_7 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1368:1: ( rule__TRANSITION__AssignmentsAssignment_7 )*
            {
             before(grammarAccess.getTRANSITIONAccess().getAssignmentsAssignment_7()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1369:1: ( rule__TRANSITION__AssignmentsAssignment_7 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_ID) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1369:2: rule__TRANSITION__AssignmentsAssignment_7
            	    {
            	    pushFollow(FOLLOW_rule__TRANSITION__AssignmentsAssignment_7_in_rule__TRANSITION__Group__7__Impl2708);
            	    rule__TRANSITION__AssignmentsAssignment_7();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

             after(grammarAccess.getTRANSITIONAccess().getAssignmentsAssignment_7()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__7__Impl"


    // $ANTLR start "rule__TRANSITION__Group__8"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1380:1: rule__TRANSITION__Group__8 : rule__TRANSITION__Group__8__Impl ;
    public final void rule__TRANSITION__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1384:1: ( rule__TRANSITION__Group__8__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1385:2: rule__TRANSITION__Group__8__Impl
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group__8__Impl_in_rule__TRANSITION__Group__82741);
            rule__TRANSITION__Group__8__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__8"


    // $ANTLR start "rule__TRANSITION__Group__8__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1391:1: rule__TRANSITION__Group__8__Impl : ( ruleFIN ) ;
    public final void rule__TRANSITION__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1395:1: ( ( ruleFIN ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1396:1: ( ruleFIN )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1396:1: ( ruleFIN )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1397:1: ruleFIN
            {
             before(grammarAccess.getTRANSITIONAccess().getFINParserRuleCall_8()); 
            pushFollow(FOLLOW_ruleFIN_in_rule__TRANSITION__Group__8__Impl2768);
            ruleFIN();

            state._fsp--;

             after(grammarAccess.getTRANSITIONAccess().getFINParserRuleCall_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group__8__Impl"


    // $ANTLR start "rule__TRANSITION__Group_5__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1426:1: rule__TRANSITION__Group_5__0 : rule__TRANSITION__Group_5__0__Impl rule__TRANSITION__Group_5__1 ;
    public final void rule__TRANSITION__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1430:1: ( rule__TRANSITION__Group_5__0__Impl rule__TRANSITION__Group_5__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1431:2: rule__TRANSITION__Group_5__0__Impl rule__TRANSITION__Group_5__1
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group_5__0__Impl_in_rule__TRANSITION__Group_5__02815);
            rule__TRANSITION__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__TRANSITION__Group_5__1_in_rule__TRANSITION__Group_5__02818);
            rule__TRANSITION__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group_5__0"


    // $ANTLR start "rule__TRANSITION__Group_5__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1438:1: rule__TRANSITION__Group_5__0__Impl : ( 'label' ) ;
    public final void rule__TRANSITION__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1442:1: ( ( 'label' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1443:1: ( 'label' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1443:1: ( 'label' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1444:1: 'label'
            {
             before(grammarAccess.getTRANSITIONAccess().getLabelKeyword_5_0()); 
            match(input,24,FOLLOW_24_in_rule__TRANSITION__Group_5__0__Impl2846); 
             after(grammarAccess.getTRANSITIONAccess().getLabelKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group_5__0__Impl"


    // $ANTLR start "rule__TRANSITION__Group_5__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1457:1: rule__TRANSITION__Group_5__1 : rule__TRANSITION__Group_5__1__Impl ;
    public final void rule__TRANSITION__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1461:1: ( rule__TRANSITION__Group_5__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1462:2: rule__TRANSITION__Group_5__1__Impl
            {
            pushFollow(FOLLOW_rule__TRANSITION__Group_5__1__Impl_in_rule__TRANSITION__Group_5__12877);
            rule__TRANSITION__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group_5__1"


    // $ANTLR start "rule__TRANSITION__Group_5__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1468:1: rule__TRANSITION__Group_5__1__Impl : ( ( rule__TRANSITION__LabelAssignment_5_1 ) ) ;
    public final void rule__TRANSITION__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1472:1: ( ( ( rule__TRANSITION__LabelAssignment_5_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1473:1: ( ( rule__TRANSITION__LabelAssignment_5_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1473:1: ( ( rule__TRANSITION__LabelAssignment_5_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1474:1: ( rule__TRANSITION__LabelAssignment_5_1 )
            {
             before(grammarAccess.getTRANSITIONAccess().getLabelAssignment_5_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1475:1: ( rule__TRANSITION__LabelAssignment_5_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1475:2: rule__TRANSITION__LabelAssignment_5_1
            {
            pushFollow(FOLLOW_rule__TRANSITION__LabelAssignment_5_1_in_rule__TRANSITION__Group_5__1__Impl2904);
            rule__TRANSITION__LabelAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getTRANSITIONAccess().getLabelAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__Group_5__1__Impl"


    // $ANTLR start "rule__Assignment__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1489:1: rule__Assignment__Group__0 : rule__Assignment__Group__0__Impl rule__Assignment__Group__1 ;
    public final void rule__Assignment__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1493:1: ( rule__Assignment__Group__0__Impl rule__Assignment__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1494:2: rule__Assignment__Group__0__Impl rule__Assignment__Group__1
            {
            pushFollow(FOLLOW_rule__Assignment__Group__0__Impl_in_rule__Assignment__Group__02938);
            rule__Assignment__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group__1_in_rule__Assignment__Group__02941);
            rule__Assignment__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__Group__0"


    // $ANTLR start "rule__Assignment__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1501:1: rule__Assignment__Group__0__Impl : ( ( rule__Assignment__VarAssignment_0 ) ) ;
    public final void rule__Assignment__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1505:1: ( ( ( rule__Assignment__VarAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1506:1: ( ( rule__Assignment__VarAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1506:1: ( ( rule__Assignment__VarAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1507:1: ( rule__Assignment__VarAssignment_0 )
            {
             before(grammarAccess.getAssignmentAccess().getVarAssignment_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1508:1: ( rule__Assignment__VarAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1508:2: rule__Assignment__VarAssignment_0
            {
            pushFollow(FOLLOW_rule__Assignment__VarAssignment_0_in_rule__Assignment__Group__0__Impl2968);
            rule__Assignment__VarAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getAssignmentAccess().getVarAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__Group__0__Impl"


    // $ANTLR start "rule__Assignment__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1518:1: rule__Assignment__Group__1 : rule__Assignment__Group__1__Impl rule__Assignment__Group__2 ;
    public final void rule__Assignment__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1522:1: ( rule__Assignment__Group__1__Impl rule__Assignment__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1523:2: rule__Assignment__Group__1__Impl rule__Assignment__Group__2
            {
            pushFollow(FOLLOW_rule__Assignment__Group__1__Impl_in_rule__Assignment__Group__12998);
            rule__Assignment__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group__2_in_rule__Assignment__Group__13001);
            rule__Assignment__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__Group__1"


    // $ANTLR start "rule__Assignment__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1530:1: rule__Assignment__Group__1__Impl : ( '=' ) ;
    public final void rule__Assignment__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1534:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1535:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1535:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1536:1: '='
            {
             before(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1()); 
            match(input,14,FOLLOW_14_in_rule__Assignment__Group__1__Impl3029); 
             after(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__Group__1__Impl"


    // $ANTLR start "rule__Assignment__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1549:1: rule__Assignment__Group__2 : rule__Assignment__Group__2__Impl rule__Assignment__Group__3 ;
    public final void rule__Assignment__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1553:1: ( rule__Assignment__Group__2__Impl rule__Assignment__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1554:2: rule__Assignment__Group__2__Impl rule__Assignment__Group__3
            {
            pushFollow(FOLLOW_rule__Assignment__Group__2__Impl_in_rule__Assignment__Group__23060);
            rule__Assignment__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group__3_in_rule__Assignment__Group__23063);
            rule__Assignment__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__Group__2"


    // $ANTLR start "rule__Assignment__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1561:1: rule__Assignment__Group__2__Impl : ( ( rule__Assignment__ExprAssignment_2 ) ) ;
    public final void rule__Assignment__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1565:1: ( ( ( rule__Assignment__ExprAssignment_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1566:1: ( ( rule__Assignment__ExprAssignment_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1566:1: ( ( rule__Assignment__ExprAssignment_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1567:1: ( rule__Assignment__ExprAssignment_2 )
            {
             before(grammarAccess.getAssignmentAccess().getExprAssignment_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1568:1: ( rule__Assignment__ExprAssignment_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1568:2: rule__Assignment__ExprAssignment_2
            {
            pushFollow(FOLLOW_rule__Assignment__ExprAssignment_2_in_rule__Assignment__Group__2__Impl3090);
            rule__Assignment__ExprAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getAssignmentAccess().getExprAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__Group__2__Impl"


    // $ANTLR start "rule__Assignment__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1578:1: rule__Assignment__Group__3 : rule__Assignment__Group__3__Impl ;
    public final void rule__Assignment__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1582:1: ( rule__Assignment__Group__3__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1583:2: rule__Assignment__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Assignment__Group__3__Impl_in_rule__Assignment__Group__33120);
            rule__Assignment__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__Group__3"


    // $ANTLR start "rule__Assignment__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1589:1: rule__Assignment__Group__3__Impl : ( ';' ) ;
    public final void rule__Assignment__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1593:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1594:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1594:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1595:1: ';'
            {
             before(grammarAccess.getAssignmentAccess().getSemicolonKeyword_3()); 
            match(input,15,FOLLOW_15_in_rule__Assignment__Group__3__Impl3148); 
             after(grammarAccess.getAssignmentAccess().getSemicolonKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__Group__3__Impl"


    // $ANTLR start "rule__PROGRAM__NameAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1617:1: rule__PROGRAM__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__PROGRAM__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1621:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1622:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1622:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1623:1: RULE_ID
            {
             before(grammarAccess.getPROGRAMAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PROGRAM__NameAssignment_13192); 
             after(grammarAccess.getPROGRAMAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__NameAssignment_1"


    // $ANTLR start "rule__PROGRAM__VariablesAssignment_3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1632:1: rule__PROGRAM__VariablesAssignment_3 : ( ruleVariableDeclaration ) ;
    public final void rule__PROGRAM__VariablesAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1636:1: ( ( ruleVariableDeclaration ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1637:1: ( ruleVariableDeclaration )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1637:1: ( ruleVariableDeclaration )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1638:1: ruleVariableDeclaration
            {
             before(grammarAccess.getPROGRAMAccess().getVariablesVariableDeclarationParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleVariableDeclaration_in_rule__PROGRAM__VariablesAssignment_33223);
            ruleVariableDeclaration();

            state._fsp--;

             after(grammarAccess.getPROGRAMAccess().getVariablesVariableDeclarationParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__VariablesAssignment_3"


    // $ANTLR start "rule__PROGRAM__TransitionsAssignment_4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1647:1: rule__PROGRAM__TransitionsAssignment_4 : ( ruleTRANSITION ) ;
    public final void rule__PROGRAM__TransitionsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1651:1: ( ( ruleTRANSITION ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1652:1: ( ruleTRANSITION )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1652:1: ( ruleTRANSITION )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1653:1: ruleTRANSITION
            {
             before(grammarAccess.getPROGRAMAccess().getTransitionsTRANSITIONParserRuleCall_4_0()); 
            pushFollow(FOLLOW_ruleTRANSITION_in_rule__PROGRAM__TransitionsAssignment_43254);
            ruleTRANSITION();

            state._fsp--;

             after(grammarAccess.getPROGRAMAccess().getTransitionsTRANSITIONParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PROGRAM__TransitionsAssignment_4"


    // $ANTLR start "rule__VariableDeclaration__NameAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1662:1: rule__VariableDeclaration__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__VariableDeclaration__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1666:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1667:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1667:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1668:1: RULE_ID
            {
             before(grammarAccess.getVariableDeclarationAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__VariableDeclaration__NameAssignment_03285); 
             after(grammarAccess.getVariableDeclarationAccess().getNameIDTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__NameAssignment_0"


    // $ANTLR start "rule__VariableDeclaration__InitValAssignment_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1677:1: rule__VariableDeclaration__InitValAssignment_2 : ( RULE_INT ) ;
    public final void rule__VariableDeclaration__InitValAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1681:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1682:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1682:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1683:1: RULE_INT
            {
             before(grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_2_0()); 
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__VariableDeclaration__InitValAssignment_23316); 
             after(grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__InitValAssignment_2"


    // $ANTLR start "rule__Constante__ValAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1692:1: rule__Constante__ValAssignment : ( RULE_INT ) ;
    public final void rule__Constante__ValAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1696:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1697:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1697:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1698:1: RULE_INT
            {
             before(grammarAccess.getConstanteAccess().getValINTTerminalRuleCall_0()); 
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__Constante__ValAssignment3347); 
             after(grammarAccess.getConstanteAccess().getValINTTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Constante__ValAssignment"


    // $ANTLR start "rule__Multiplication__RightAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1707:1: rule__Multiplication__RightAssignment_1_2 : ( rulePrimary ) ;
    public final void rule__Multiplication__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1711:1: ( ( rulePrimary ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1712:1: ( rulePrimary )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1712:1: ( rulePrimary )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1713:1: rulePrimary
            {
             before(grammarAccess.getMultiplicationAccess().getRightPrimaryParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_rulePrimary_in_rule__Multiplication__RightAssignment_1_23378);
            rulePrimary();

            state._fsp--;

             after(grammarAccess.getMultiplicationAccess().getRightPrimaryParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__RightAssignment_1_2"


    // $ANTLR start "rule__VariableRef__VarAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1722:1: rule__VariableRef__VarAssignment : ( ( RULE_ID ) ) ;
    public final void rule__VariableRef__VarAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1726:1: ( ( ( RULE_ID ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1727:1: ( ( RULE_ID ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1727:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1728:1: ( RULE_ID )
            {
             before(grammarAccess.getVariableRefAccess().getVarVariableCrossReference_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1729:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1730:1: RULE_ID
            {
             before(grammarAccess.getVariableRefAccess().getVarVariableIDTerminalRuleCall_0_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__VariableRef__VarAssignment3413); 
             after(grammarAccess.getVariableRefAccess().getVarVariableIDTerminalRuleCall_0_1()); 

            }

             after(grammarAccess.getVariableRefAccess().getVarVariableCrossReference_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableRef__VarAssignment"


    // $ANTLR start "rule__Addition__RightAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1741:1: rule__Addition__RightAssignment_1_2 : ( ruleMultiplication ) ;
    public final void rule__Addition__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1745:1: ( ( ruleMultiplication ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1746:1: ( ruleMultiplication )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1746:1: ( ruleMultiplication )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1747:1: ruleMultiplication
            {
             before(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_ruleMultiplication_in_rule__Addition__RightAssignment_1_23448);
            ruleMultiplication();

            state._fsp--;

             after(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__RightAssignment_1_2"


    // $ANTLR start "rule__TRANSITION__NameAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1756:1: rule__TRANSITION__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__TRANSITION__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1760:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1761:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1761:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1762:1: RULE_ID
            {
             before(grammarAccess.getTRANSITIONAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__TRANSITION__NameAssignment_13479); 
             after(grammarAccess.getTRANSITIONAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__NameAssignment_1"


    // $ANTLR start "rule__TRANSITION__LabelAssignment_5_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1771:1: rule__TRANSITION__LabelAssignment_5_1 : ( RULE_STRING ) ;
    public final void rule__TRANSITION__LabelAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1775:1: ( ( RULE_STRING ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1776:1: ( RULE_STRING )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1776:1: ( RULE_STRING )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1777:1: RULE_STRING
            {
             before(grammarAccess.getTRANSITIONAccess().getLabelSTRINGTerminalRuleCall_5_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__TRANSITION__LabelAssignment_5_13510); 
             after(grammarAccess.getTRANSITIONAccess().getLabelSTRINGTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__LabelAssignment_5_1"


    // $ANTLR start "rule__TRANSITION__AssignmentsAssignment_7"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1786:1: rule__TRANSITION__AssignmentsAssignment_7 : ( ruleAssignment ) ;
    public final void rule__TRANSITION__AssignmentsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1790:1: ( ( ruleAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1791:1: ( ruleAssignment )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1791:1: ( ruleAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1792:1: ruleAssignment
            {
             before(grammarAccess.getTRANSITIONAccess().getAssignmentsAssignmentParserRuleCall_7_0()); 
            pushFollow(FOLLOW_ruleAssignment_in_rule__TRANSITION__AssignmentsAssignment_73541);
            ruleAssignment();

            state._fsp--;

             after(grammarAccess.getTRANSITIONAccess().getAssignmentsAssignmentParserRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TRANSITION__AssignmentsAssignment_7"


    // $ANTLR start "rule__Assignment__VarAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1801:1: rule__Assignment__VarAssignment_0 : ( ruleVariableRef ) ;
    public final void rule__Assignment__VarAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1805:1: ( ( ruleVariableRef ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1806:1: ( ruleVariableRef )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1806:1: ( ruleVariableRef )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1807:1: ruleVariableRef
            {
             before(grammarAccess.getAssignmentAccess().getVarVariableRefParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleVariableRef_in_rule__Assignment__VarAssignment_03572);
            ruleVariableRef();

            state._fsp--;

             after(grammarAccess.getAssignmentAccess().getVarVariableRefParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__VarAssignment_0"


    // $ANTLR start "rule__Assignment__ExprAssignment_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1816:1: rule__Assignment__ExprAssignment_2 : ( ruleAddition ) ;
    public final void rule__Assignment__ExprAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1820:1: ( ( ruleAddition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1821:1: ( ruleAddition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1821:1: ( ruleAddition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1822:1: ruleAddition
            {
             before(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleAddition_in_rule__Assignment__ExprAssignment_23603);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assignment__ExprAssignment_2"

    // Delegated rules


 

    public static final BitSet FOLLOW_rulePROGRAM_in_entryRulePROGRAM61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePROGRAM68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__0_in_rulePROGRAM94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration122 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableDeclaration129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__0_in_ruleVariableDeclaration155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDEBUT_in_entryRuleDEBUT182 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDEBUT189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleDEBUT216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFIN_in_entryRuleFIN244 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFIN251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleFIN278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_entryRuleConstante306 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstante313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Constante__ValAssignment_in_ruleConstante339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_entryRuleMultiplication366 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplication373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group__0_in_ruleMultiplication399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_entryRulePrimary426 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimary433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Alternatives_in_rulePrimary459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_entryRuleVariableRef486 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableRef493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableRef__VarAssignment_in_ruleVariableRef519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_entryRuleAddition546 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAddition553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group__0_in_ruleAddition579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTRANSITION_in_entryRuleTRANSITION606 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTRANSITION613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__0_in_ruleTRANSITION639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_entryRuleAssignment666 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssignment673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__0_in_ruleAssignment699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rule__Primary__Alternatives735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_rule__Primary__Alternatives752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__0_in_rule__Primary__Alternatives769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__0__Impl_in_rule__PROGRAM__Group__0800 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__1_in_rule__PROGRAM__Group__0803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__PROGRAM__Group__0__Impl831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__1__Impl_in_rule__PROGRAM__Group__1862 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__2_in_rule__PROGRAM__Group__1865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PROGRAM__NameAssignment_1_in_rule__PROGRAM__Group__1__Impl892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__2__Impl_in_rule__PROGRAM__Group__2922 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__3_in_rule__PROGRAM__Group__2925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDEBUT_in_rule__PROGRAM__Group__2__Impl952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__3__Impl_in_rule__PROGRAM__Group__3981 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__4_in_rule__PROGRAM__Group__3984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PROGRAM__VariablesAssignment_3_in_rule__PROGRAM__Group__3__Impl1011 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__4__Impl_in_rule__PROGRAM__Group__41042 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__5_in_rule__PROGRAM__Group__41045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PROGRAM__TransitionsAssignment_4_in_rule__PROGRAM__Group__4__Impl1074 = new BitSet(new long[]{0x0000000000100012L});
    public static final BitSet FOLLOW_rule__PROGRAM__TransitionsAssignment_4_in_rule__PROGRAM__Group__4__Impl1086 = new BitSet(new long[]{0x0000000000100012L});
    public static final BitSet FOLLOW_rule__PROGRAM__Group__5__Impl_in_rule__PROGRAM__Group__51119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFIN_in_rule__PROGRAM__Group__5__Impl1146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__0__Impl_in_rule__VariableDeclaration__Group__01187 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__1_in_rule__VariableDeclaration__Group__01190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__NameAssignment_0_in_rule__VariableDeclaration__Group__0__Impl1217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__1__Impl_in_rule__VariableDeclaration__Group__11247 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__2_in_rule__VariableDeclaration__Group__11250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__VariableDeclaration__Group__1__Impl1278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__2__Impl_in_rule__VariableDeclaration__Group__21309 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__3_in_rule__VariableDeclaration__Group__21312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__InitValAssignment_2_in_rule__VariableDeclaration__Group__2__Impl1339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__3__Impl_in_rule__VariableDeclaration__Group__31369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__VariableDeclaration__Group__3__Impl1397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group__0__Impl_in_rule__Multiplication__Group__01436 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_rule__Multiplication__Group__1_in_rule__Multiplication__Group__01439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_rule__Multiplication__Group__0__Impl1466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group__1__Impl_in_rule__Multiplication__Group__11495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__0_in_rule__Multiplication__Group__1__Impl1522 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__0__Impl_in_rule__Multiplication__Group_1__01557 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__1_in_rule__Multiplication__Group_1__01560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__1__Impl_in_rule__Multiplication__Group_1__11618 = new BitSet(new long[]{0x0000000000020030L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__2_in_rule__Multiplication__Group_1__11621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__Multiplication__Group_1__1__Impl1649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__2__Impl_in_rule__Multiplication__Group_1__21680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__RightAssignment_1_2_in_rule__Multiplication__Group_1__2__Impl1707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__0__Impl_in_rule__Primary__Group_2__01743 = new BitSet(new long[]{0x0000000000020030L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__1_in_rule__Primary__Group_2__01746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__Primary__Group_2__0__Impl1774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__1__Impl_in_rule__Primary__Group_2__11805 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__2_in_rule__Primary__Group_2__11808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_rule__Primary__Group_2__1__Impl1835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__2__Impl_in_rule__Primary__Group_2__21864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__Primary__Group_2__2__Impl1892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group__0__Impl_in_rule__Addition__Group__01929 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__Addition__Group__1_in_rule__Addition__Group__01932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_rule__Addition__Group__0__Impl1959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group__1__Impl_in_rule__Addition__Group__11988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__0_in_rule__Addition__Group__1__Impl2015 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__0__Impl_in_rule__Addition__Group_1__02050 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__1_in_rule__Addition__Group_1__02053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__1__Impl_in_rule__Addition__Group_1__12111 = new BitSet(new long[]{0x0000000000020030L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__2_in_rule__Addition__Group_1__12114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__Addition__Group_1__1__Impl2142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__2__Impl_in_rule__Addition__Group_1__22173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__RightAssignment_1_2_in_rule__Addition__Group_1__2__Impl2200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__0__Impl_in_rule__TRANSITION__Group__02236 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__1_in_rule__TRANSITION__Group__02239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__TRANSITION__Group__0__Impl2267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__1__Impl_in_rule__TRANSITION__Group__12298 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__2_in_rule__TRANSITION__Group__12301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__NameAssignment_1_in_rule__TRANSITION__Group__1__Impl2328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__2__Impl_in_rule__TRANSITION__Group__22358 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__3_in_rule__TRANSITION__Group__22361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__TRANSITION__Group__2__Impl2389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__3__Impl_in_rule__TRANSITION__Group__32420 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__4_in_rule__TRANSITION__Group__32423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__TRANSITION__Group__3__Impl2451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__4__Impl_in_rule__TRANSITION__Group__42482 = new BitSet(new long[]{0x0000000001000800L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__5_in_rule__TRANSITION__Group__42485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__TRANSITION__Group__4__Impl2513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__5__Impl_in_rule__TRANSITION__Group__52544 = new BitSet(new long[]{0x0000000001000800L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__6_in_rule__TRANSITION__Group__52547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group_5__0_in_rule__TRANSITION__Group__5__Impl2574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__6__Impl_in_rule__TRANSITION__Group__62605 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__7_in_rule__TRANSITION__Group__62608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDEBUT_in_rule__TRANSITION__Group__6__Impl2635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__7__Impl_in_rule__TRANSITION__Group__72664 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__8_in_rule__TRANSITION__Group__72667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__AssignmentsAssignment_7_in_rule__TRANSITION__Group__7__Impl2696 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__TRANSITION__AssignmentsAssignment_7_in_rule__TRANSITION__Group__7__Impl2708 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group__8__Impl_in_rule__TRANSITION__Group__82741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFIN_in_rule__TRANSITION__Group__8__Impl2768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group_5__0__Impl_in_rule__TRANSITION__Group_5__02815 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group_5__1_in_rule__TRANSITION__Group_5__02818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__TRANSITION__Group_5__0__Impl2846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__Group_5__1__Impl_in_rule__TRANSITION__Group_5__12877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TRANSITION__LabelAssignment_5_1_in_rule__TRANSITION__Group_5__1__Impl2904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__0__Impl_in_rule__Assignment__Group__02938 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_rule__Assignment__Group__1_in_rule__Assignment__Group__02941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__VarAssignment_0_in_rule__Assignment__Group__0__Impl2968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__1__Impl_in_rule__Assignment__Group__12998 = new BitSet(new long[]{0x0000000000020030L});
    public static final BitSet FOLLOW_rule__Assignment__Group__2_in_rule__Assignment__Group__13001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__Assignment__Group__1__Impl3029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__2__Impl_in_rule__Assignment__Group__23060 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__Assignment__Group__3_in_rule__Assignment__Group__23063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__ExprAssignment_2_in_rule__Assignment__Group__2__Impl3090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__3__Impl_in_rule__Assignment__Group__33120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Assignment__Group__3__Impl3148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PROGRAM__NameAssignment_13192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_rule__PROGRAM__VariablesAssignment_33223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTRANSITION_in_rule__PROGRAM__TransitionsAssignment_43254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__VariableDeclaration__NameAssignment_03285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__VariableDeclaration__InitValAssignment_23316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__Constante__ValAssignment3347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_rule__Multiplication__RightAssignment_1_23378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__VariableRef__VarAssignment3413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_rule__Addition__RightAssignment_1_23448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__TRANSITION__NameAssignment_13479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__TRANSITION__LabelAssignment_5_13510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_rule__TRANSITION__AssignmentsAssignment_73541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rule__Assignment__VarAssignment_03572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_rule__Assignment__ExprAssignment_23603 = new BitSet(new long[]{0x0000000000000002L});

}