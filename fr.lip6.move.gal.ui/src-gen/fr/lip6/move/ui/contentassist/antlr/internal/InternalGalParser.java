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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'<'", "'>'", "'>='", "'<='", "'=='", "'!='", "'GAL'", "'{'", "'}'", "'='", "';'", "'['", "']'", "'transition'", "'label'", "'+'", "'-'", "'*'", "'/'", "'%'", "'**'", "'('", "')'", "'||'", "'&&'", "'!'", "'.'", "'.*'", "'True'", "'False'"
    };
    public static final int RULE_ID=4;
    public static final int T__40=40;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__30=30;
    public static final int T__19=19;
    public static final int T__31=31;
    public static final int RULE_STRING=6;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__16=16;
    public static final int T__34=34;
    public static final int T__15=15;
    public static final int T__35=35;
    public static final int T__18=18;
    public static final int T__36=36;
    public static final int T__17=17;
    public static final int T__37=37;
    public static final int T__12=12;
    public static final int T__38=38;
    public static final int T__11=11;
    public static final int T__39=39;
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




    // $ANTLR start "entryRuleSystem"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:60:1: entryRuleSystem : ruleSystem EOF ;
    public final void entryRuleSystem() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:61:1: ( ruleSystem EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:62:1: ruleSystem EOF
            {
             before(grammarAccess.getSystemRule()); 
            pushFollow(FOLLOW_ruleSystem_in_entryRuleSystem61);
            ruleSystem();

            state._fsp--;

             after(grammarAccess.getSystemRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSystem68); 

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
    // $ANTLR end "entryRuleSystem"


    // $ANTLR start "ruleSystem"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:69:1: ruleSystem : ( ( rule__System__Group__0 )? ) ;
    public final void ruleSystem() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:73:2: ( ( ( rule__System__Group__0 )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:74:1: ( ( rule__System__Group__0 )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:74:1: ( ( rule__System__Group__0 )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:75:1: ( rule__System__Group__0 )?
            {
             before(grammarAccess.getSystemAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:76:1: ( rule__System__Group__0 )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==17) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:76:2: rule__System__Group__0
                    {
                    pushFollow(FOLLOW_rule__System__Group__0_in_ruleSystem94);
                    rule__System__Group__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getSystemAccess().getGroup()); 

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
    // $ANTLR end "ruleSystem"


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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:97:1: ruleVariableDeclaration : ( ( rule__VariableDeclaration__Alternatives ) ) ;
    public final void ruleVariableDeclaration() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:101:2: ( ( ( rule__VariableDeclaration__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:102:1: ( ( rule__VariableDeclaration__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:102:1: ( ( rule__VariableDeclaration__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:103:1: ( rule__VariableDeclaration__Alternatives )
            {
             before(grammarAccess.getVariableDeclarationAccess().getAlternatives()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:104:1: ( rule__VariableDeclaration__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:104:2: rule__VariableDeclaration__Alternatives
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Alternatives_in_ruleVariableDeclaration155);
            rule__VariableDeclaration__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getVariableDeclarationAccess().getAlternatives()); 

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


    // $ANTLR start "entryRuleTransition"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:116:1: entryRuleTransition : ruleTransition EOF ;
    public final void entryRuleTransition() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:117:1: ( ruleTransition EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:118:1: ruleTransition EOF
            {
             before(grammarAccess.getTransitionRule()); 
            pushFollow(FOLLOW_ruleTransition_in_entryRuleTransition182);
            ruleTransition();

            state._fsp--;

             after(grammarAccess.getTransitionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransition189); 

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
    // $ANTLR end "entryRuleTransition"


    // $ANTLR start "ruleTransition"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:125:1: ruleTransition : ( ( rule__Transition__Group__0 ) ) ;
    public final void ruleTransition() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:129:2: ( ( ( rule__Transition__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:130:1: ( ( rule__Transition__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:130:1: ( ( rule__Transition__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:131:1: ( rule__Transition__Group__0 )
            {
             before(grammarAccess.getTransitionAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:132:1: ( rule__Transition__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:132:2: rule__Transition__Group__0
            {
            pushFollow(FOLLOW_rule__Transition__Group__0_in_ruleTransition215);
            rule__Transition__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTransitionAccess().getGroup()); 

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
    // $ANTLR end "ruleTransition"


    // $ANTLR start "entryRuleAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:144:1: entryRuleAssignment : ruleAssignment EOF ;
    public final void entryRuleAssignment() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:145:1: ( ruleAssignment EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:146:1: ruleAssignment EOF
            {
             before(grammarAccess.getAssignmentRule()); 
            pushFollow(FOLLOW_ruleAssignment_in_entryRuleAssignment242);
            ruleAssignment();

            state._fsp--;

             after(grammarAccess.getAssignmentRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssignment249); 

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:153:1: ruleAssignment : ( ( rule__Assignment__Alternatives ) ) ;
    public final void ruleAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:157:2: ( ( ( rule__Assignment__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:158:1: ( ( rule__Assignment__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:158:1: ( ( rule__Assignment__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:159:1: ( rule__Assignment__Alternatives )
            {
             before(grammarAccess.getAssignmentAccess().getAlternatives()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:160:1: ( rule__Assignment__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:160:2: rule__Assignment__Alternatives
            {
            pushFollow(FOLLOW_rule__Assignment__Alternatives_in_ruleAssignment275);
            rule__Assignment__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getAssignmentAccess().getAlternatives()); 

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


    // $ANTLR start "entryRuleAddition"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:172:1: entryRuleAddition : ruleAddition EOF ;
    public final void entryRuleAddition() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:173:1: ( ruleAddition EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:174:1: ruleAddition EOF
            {
             before(grammarAccess.getAdditionRule()); 
            pushFollow(FOLLOW_ruleAddition_in_entryRuleAddition302);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getAdditionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAddition309); 

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:181:1: ruleAddition : ( ( rule__Addition__Group__0 ) ) ;
    public final void ruleAddition() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:185:2: ( ( ( rule__Addition__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:186:1: ( ( rule__Addition__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:186:1: ( ( rule__Addition__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:187:1: ( rule__Addition__Group__0 )
            {
             before(grammarAccess.getAdditionAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:188:1: ( rule__Addition__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:188:2: rule__Addition__Group__0
            {
            pushFollow(FOLLOW_rule__Addition__Group__0_in_ruleAddition335);
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


    // $ANTLR start "entryRuleMultiplication"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:200:1: entryRuleMultiplication : ruleMultiplication EOF ;
    public final void entryRuleMultiplication() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:201:1: ( ruleMultiplication EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:202:1: ruleMultiplication EOF
            {
             before(grammarAccess.getMultiplicationRule()); 
            pushFollow(FOLLOW_ruleMultiplication_in_entryRuleMultiplication362);
            ruleMultiplication();

            state._fsp--;

             after(grammarAccess.getMultiplicationRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplication369); 

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:209:1: ruleMultiplication : ( ( rule__Multiplication__Group__0 ) ) ;
    public final void ruleMultiplication() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:213:2: ( ( ( rule__Multiplication__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:214:1: ( ( rule__Multiplication__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:214:1: ( ( rule__Multiplication__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:215:1: ( rule__Multiplication__Group__0 )
            {
             before(grammarAccess.getMultiplicationAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:216:1: ( rule__Multiplication__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:216:2: rule__Multiplication__Group__0
            {
            pushFollow(FOLLOW_rule__Multiplication__Group__0_in_ruleMultiplication395);
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


    // $ANTLR start "entryRuleUnitaryMinus"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:228:1: entryRuleUnitaryMinus : ruleUnitaryMinus EOF ;
    public final void entryRuleUnitaryMinus() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:229:1: ( ruleUnitaryMinus EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:230:1: ruleUnitaryMinus EOF
            {
             before(grammarAccess.getUnitaryMinusRule()); 
            pushFollow(FOLLOW_ruleUnitaryMinus_in_entryRuleUnitaryMinus422);
            ruleUnitaryMinus();

            state._fsp--;

             after(grammarAccess.getUnitaryMinusRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnitaryMinus429); 

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
    // $ANTLR end "entryRuleUnitaryMinus"


    // $ANTLR start "ruleUnitaryMinus"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:237:1: ruleUnitaryMinus : ( ( rule__UnitaryMinus__Group__0 ) ) ;
    public final void ruleUnitaryMinus() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:241:2: ( ( ( rule__UnitaryMinus__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:242:1: ( ( rule__UnitaryMinus__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:242:1: ( ( rule__UnitaryMinus__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:243:1: ( rule__UnitaryMinus__Group__0 )
            {
             before(grammarAccess.getUnitaryMinusAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:244:1: ( rule__UnitaryMinus__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:244:2: rule__UnitaryMinus__Group__0
            {
            pushFollow(FOLLOW_rule__UnitaryMinus__Group__0_in_ruleUnitaryMinus455);
            rule__UnitaryMinus__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getUnitaryMinusAccess().getGroup()); 

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
    // $ANTLR end "ruleUnitaryMinus"


    // $ANTLR start "entryRulePower"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:256:1: entryRulePower : rulePower EOF ;
    public final void entryRulePower() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:257:1: ( rulePower EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:258:1: rulePower EOF
            {
             before(grammarAccess.getPowerRule()); 
            pushFollow(FOLLOW_rulePower_in_entryRulePower482);
            rulePower();

            state._fsp--;

             after(grammarAccess.getPowerRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePower489); 

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
    // $ANTLR end "entryRulePower"


    // $ANTLR start "rulePower"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:265:1: rulePower : ( ( rule__Power__Group__0 ) ) ;
    public final void rulePower() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:269:2: ( ( ( rule__Power__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:270:1: ( ( rule__Power__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:270:1: ( ( rule__Power__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:271:1: ( rule__Power__Group__0 )
            {
             before(grammarAccess.getPowerAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:272:1: ( rule__Power__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:272:2: rule__Power__Group__0
            {
            pushFollow(FOLLOW_rule__Power__Group__0_in_rulePower515);
            rule__Power__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPowerAccess().getGroup()); 

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
    // $ANTLR end "rulePower"


    // $ANTLR start "entryRulePrimary"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:284:1: entryRulePrimary : rulePrimary EOF ;
    public final void entryRulePrimary() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:285:1: ( rulePrimary EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:286:1: rulePrimary EOF
            {
             before(grammarAccess.getPrimaryRule()); 
            pushFollow(FOLLOW_rulePrimary_in_entryRulePrimary542);
            rulePrimary();

            state._fsp--;

             after(grammarAccess.getPrimaryRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimary549); 

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:293:1: rulePrimary : ( ( rule__Primary__Alternatives ) ) ;
    public final void rulePrimary() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:297:2: ( ( ( rule__Primary__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:298:1: ( ( rule__Primary__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:298:1: ( ( rule__Primary__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:299:1: ( rule__Primary__Alternatives )
            {
             before(grammarAccess.getPrimaryAccess().getAlternatives()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:300:1: ( rule__Primary__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:300:2: rule__Primary__Alternatives
            {
            pushFollow(FOLLOW_rule__Primary__Alternatives_in_rulePrimary575);
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


    // $ANTLR start "entryRuleConstante"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:312:1: entryRuleConstante : ruleConstante EOF ;
    public final void entryRuleConstante() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:313:1: ( ruleConstante EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:314:1: ruleConstante EOF
            {
             before(grammarAccess.getConstanteRule()); 
            pushFollow(FOLLOW_ruleConstante_in_entryRuleConstante602);
            ruleConstante();

            state._fsp--;

             after(grammarAccess.getConstanteRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstante609); 

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:321:1: ruleConstante : ( ( rule__Constante__ValAssignment ) ) ;
    public final void ruleConstante() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:325:2: ( ( ( rule__Constante__ValAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:326:1: ( ( rule__Constante__ValAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:326:1: ( ( rule__Constante__ValAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:327:1: ( rule__Constante__ValAssignment )
            {
             before(grammarAccess.getConstanteAccess().getValAssignment()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:328:1: ( rule__Constante__ValAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:328:2: rule__Constante__ValAssignment
            {
            pushFollow(FOLLOW_rule__Constante__ValAssignment_in_ruleConstante635);
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


    // $ANTLR start "entryRuleVariableRef"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:340:1: entryRuleVariableRef : ruleVariableRef EOF ;
    public final void entryRuleVariableRef() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:341:1: ( ruleVariableRef EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:342:1: ruleVariableRef EOF
            {
             before(grammarAccess.getVariableRefRule()); 
            pushFollow(FOLLOW_ruleVariableRef_in_entryRuleVariableRef662);
            ruleVariableRef();

            state._fsp--;

             after(grammarAccess.getVariableRefRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableRef669); 

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:349:1: ruleVariableRef : ( ( rule__VariableRef__VarAssignment ) ) ;
    public final void ruleVariableRef() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:353:2: ( ( ( rule__VariableRef__VarAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:354:1: ( ( rule__VariableRef__VarAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:354:1: ( ( rule__VariableRef__VarAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:355:1: ( rule__VariableRef__VarAssignment )
            {
             before(grammarAccess.getVariableRefAccess().getVarAssignment()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:356:1: ( rule__VariableRef__VarAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:356:2: rule__VariableRef__VarAssignment
            {
            pushFollow(FOLLOW_rule__VariableRef__VarAssignment_in_ruleVariableRef695);
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


    // $ANTLR start "entryRuleOr"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:368:1: entryRuleOr : ruleOr EOF ;
    public final void entryRuleOr() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:369:1: ( ruleOr EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:370:1: ruleOr EOF
            {
             before(grammarAccess.getOrRule()); 
            pushFollow(FOLLOW_ruleOr_in_entryRuleOr722);
            ruleOr();

            state._fsp--;

             after(grammarAccess.getOrRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOr729); 

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
    // $ANTLR end "entryRuleOr"


    // $ANTLR start "ruleOr"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:377:1: ruleOr : ( ( rule__Or__Group__0 ) ) ;
    public final void ruleOr() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:381:2: ( ( ( rule__Or__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:382:1: ( ( rule__Or__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:382:1: ( ( rule__Or__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:383:1: ( rule__Or__Group__0 )
            {
             before(grammarAccess.getOrAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:384:1: ( rule__Or__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:384:2: rule__Or__Group__0
            {
            pushFollow(FOLLOW_rule__Or__Group__0_in_ruleOr755);
            rule__Or__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getOrAccess().getGroup()); 

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
    // $ANTLR end "ruleOr"


    // $ANTLR start "entryRuleAnd"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:396:1: entryRuleAnd : ruleAnd EOF ;
    public final void entryRuleAnd() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:397:1: ( ruleAnd EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:398:1: ruleAnd EOF
            {
             before(grammarAccess.getAndRule()); 
            pushFollow(FOLLOW_ruleAnd_in_entryRuleAnd782);
            ruleAnd();

            state._fsp--;

             after(grammarAccess.getAndRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAnd789); 

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
    // $ANTLR end "entryRuleAnd"


    // $ANTLR start "ruleAnd"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:405:1: ruleAnd : ( ( rule__And__Group__0 ) ) ;
    public final void ruleAnd() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:409:2: ( ( ( rule__And__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:410:1: ( ( rule__And__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:410:1: ( ( rule__And__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:411:1: ( rule__And__Group__0 )
            {
             before(grammarAccess.getAndAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:412:1: ( rule__And__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:412:2: rule__And__Group__0
            {
            pushFollow(FOLLOW_rule__And__Group__0_in_ruleAnd815);
            rule__And__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAndAccess().getGroup()); 

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
    // $ANTLR end "ruleAnd"


    // $ANTLR start "entryRuleNot"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:424:1: entryRuleNot : ruleNot EOF ;
    public final void entryRuleNot() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:425:1: ( ruleNot EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:426:1: ruleNot EOF
            {
             before(grammarAccess.getNotRule()); 
            pushFollow(FOLLOW_ruleNot_in_entryRuleNot842);
            ruleNot();

            state._fsp--;

             after(grammarAccess.getNotRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNot849); 

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
    // $ANTLR end "entryRuleNot"


    // $ANTLR start "ruleNot"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:433:1: ruleNot : ( ( rule__Not__Group__0 ) ) ;
    public final void ruleNot() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:437:2: ( ( ( rule__Not__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:438:1: ( ( rule__Not__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:438:1: ( ( rule__Not__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:439:1: ( rule__Not__Group__0 )
            {
             before(grammarAccess.getNotAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:440:1: ( rule__Not__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:440:2: rule__Not__Group__0
            {
            pushFollow(FOLLOW_rule__Not__Group__0_in_ruleNot875);
            rule__Not__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getNotAccess().getGroup()); 

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
    // $ANTLR end "ruleNot"


    // $ANTLR start "entryRulePrimaryBool"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:452:1: entryRulePrimaryBool : rulePrimaryBool EOF ;
    public final void entryRulePrimaryBool() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:453:1: ( rulePrimaryBool EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:454:1: rulePrimaryBool EOF
            {
             before(grammarAccess.getPrimaryBoolRule()); 
            pushFollow(FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool902);
            rulePrimaryBool();

            state._fsp--;

             after(grammarAccess.getPrimaryBoolRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryBool909); 

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
    // $ANTLR end "entryRulePrimaryBool"


    // $ANTLR start "rulePrimaryBool"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:461:1: rulePrimaryBool : ( ( rule__PrimaryBool__Alternatives ) ) ;
    public final void rulePrimaryBool() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:465:2: ( ( ( rule__PrimaryBool__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:466:1: ( ( rule__PrimaryBool__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:466:1: ( ( rule__PrimaryBool__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:467:1: ( rule__PrimaryBool__Alternatives )
            {
             before(grammarAccess.getPrimaryBoolAccess().getAlternatives()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:468:1: ( rule__PrimaryBool__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:468:2: rule__PrimaryBool__Alternatives
            {
            pushFollow(FOLLOW_rule__PrimaryBool__Alternatives_in_rulePrimaryBool935);
            rule__PrimaryBool__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getPrimaryBoolAccess().getAlternatives()); 

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
    // $ANTLR end "rulePrimaryBool"


    // $ANTLR start "entryRuleTrue"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:480:1: entryRuleTrue : ruleTrue EOF ;
    public final void entryRuleTrue() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:481:1: ( ruleTrue EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:482:1: ruleTrue EOF
            {
             before(grammarAccess.getTrueRule()); 
            pushFollow(FOLLOW_ruleTrue_in_entryRuleTrue962);
            ruleTrue();

            state._fsp--;

             after(grammarAccess.getTrueRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTrue969); 

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
    // $ANTLR end "entryRuleTrue"


    // $ANTLR start "ruleTrue"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:489:1: ruleTrue : ( ( rule__True__ValueAssignment ) ) ;
    public final void ruleTrue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:493:2: ( ( ( rule__True__ValueAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:494:1: ( ( rule__True__ValueAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:494:1: ( ( rule__True__ValueAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:495:1: ( rule__True__ValueAssignment )
            {
             before(grammarAccess.getTrueAccess().getValueAssignment()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:496:1: ( rule__True__ValueAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:496:2: rule__True__ValueAssignment
            {
            pushFollow(FOLLOW_rule__True__ValueAssignment_in_ruleTrue995);
            rule__True__ValueAssignment();

            state._fsp--;


            }

             after(grammarAccess.getTrueAccess().getValueAssignment()); 

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
    // $ANTLR end "ruleTrue"


    // $ANTLR start "entryRuleFalse"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:508:1: entryRuleFalse : ruleFalse EOF ;
    public final void entryRuleFalse() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:509:1: ( ruleFalse EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:510:1: ruleFalse EOF
            {
             before(grammarAccess.getFalseRule()); 
            pushFollow(FOLLOW_ruleFalse_in_entryRuleFalse1022);
            ruleFalse();

            state._fsp--;

             after(grammarAccess.getFalseRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFalse1029); 

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
    // $ANTLR end "entryRuleFalse"


    // $ANTLR start "ruleFalse"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:517:1: ruleFalse : ( ( rule__False__ValueAssignment ) ) ;
    public final void ruleFalse() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:521:2: ( ( ( rule__False__ValueAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:522:1: ( ( rule__False__ValueAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:522:1: ( ( rule__False__ValueAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:523:1: ( rule__False__ValueAssignment )
            {
             before(grammarAccess.getFalseAccess().getValueAssignment()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:524:1: ( rule__False__ValueAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:524:2: rule__False__ValueAssignment
            {
            pushFollow(FOLLOW_rule__False__ValueAssignment_in_ruleFalse1055);
            rule__False__ValueAssignment();

            state._fsp--;


            }

             after(grammarAccess.getFalseAccess().getValueAssignment()); 

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
    // $ANTLR end "ruleFalse"


    // $ANTLR start "entryRuleComparison"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:536:1: entryRuleComparison : ruleComparison EOF ;
    public final void entryRuleComparison() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:537:1: ( ruleComparison EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:538:1: ruleComparison EOF
            {
             before(grammarAccess.getComparisonRule()); 
            pushFollow(FOLLOW_ruleComparison_in_entryRuleComparison1082);
            ruleComparison();

            state._fsp--;

             after(grammarAccess.getComparisonRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleComparison1089); 

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
    // $ANTLR end "entryRuleComparison"


    // $ANTLR start "ruleComparison"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:545:1: ruleComparison : ( ( rule__Comparison__Group__0 ) ) ;
    public final void ruleComparison() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:549:2: ( ( ( rule__Comparison__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:550:1: ( ( rule__Comparison__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:550:1: ( ( rule__Comparison__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:551:1: ( rule__Comparison__Group__0 )
            {
             before(grammarAccess.getComparisonAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:552:1: ( rule__Comparison__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:552:2: rule__Comparison__Group__0
            {
            pushFollow(FOLLOW_rule__Comparison__Group__0_in_ruleComparison1115);
            rule__Comparison__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getComparisonAccess().getGroup()); 

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
    // $ANTLR end "ruleComparison"


    // $ANTLR start "entryRuleCompOperators"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:564:1: entryRuleCompOperators : ruleCompOperators EOF ;
    public final void entryRuleCompOperators() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:565:1: ( ruleCompOperators EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:566:1: ruleCompOperators EOF
            {
             before(grammarAccess.getCompOperatorsRule()); 
            pushFollow(FOLLOW_ruleCompOperators_in_entryRuleCompOperators1142);
            ruleCompOperators();

            state._fsp--;

             after(grammarAccess.getCompOperatorsRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCompOperators1149); 

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
    // $ANTLR end "entryRuleCompOperators"


    // $ANTLR start "ruleCompOperators"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:573:1: ruleCompOperators : ( ( rule__CompOperators__Alternatives ) ) ;
    public final void ruleCompOperators() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:577:2: ( ( ( rule__CompOperators__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:578:1: ( ( rule__CompOperators__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:578:1: ( ( rule__CompOperators__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:579:1: ( rule__CompOperators__Alternatives )
            {
             before(grammarAccess.getCompOperatorsAccess().getAlternatives()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:580:1: ( rule__CompOperators__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:580:2: rule__CompOperators__Alternatives
            {
            pushFollow(FOLLOW_rule__CompOperators__Alternatives_in_ruleCompOperators1175);
            rule__CompOperators__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getCompOperatorsAccess().getAlternatives()); 

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
    // $ANTLR end "ruleCompOperators"


    // $ANTLR start "entryRuleQualifiedName"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:592:1: entryRuleQualifiedName : ruleQualifiedName EOF ;
    public final void entryRuleQualifiedName() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:593:1: ( ruleQualifiedName EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:594:1: ruleQualifiedName EOF
            {
             before(grammarAccess.getQualifiedNameRule()); 
            pushFollow(FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName1202);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getQualifiedNameRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedName1209); 

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
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:601:1: ruleQualifiedName : ( ( rule__QualifiedName__Group__0 ) ) ;
    public final void ruleQualifiedName() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:605:2: ( ( ( rule__QualifiedName__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:606:1: ( ( rule__QualifiedName__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:606:1: ( ( rule__QualifiedName__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:607:1: ( rule__QualifiedName__Group__0 )
            {
             before(grammarAccess.getQualifiedNameAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:608:1: ( rule__QualifiedName__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:608:2: rule__QualifiedName__Group__0
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group__0_in_ruleQualifiedName1235);
            rule__QualifiedName__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getQualifiedNameAccess().getGroup()); 

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
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleQualifiedNameWithWildCard"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:620:1: entryRuleQualifiedNameWithWildCard : ruleQualifiedNameWithWildCard EOF ;
    public final void entryRuleQualifiedNameWithWildCard() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:621:1: ( ruleQualifiedNameWithWildCard EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:622:1: ruleQualifiedNameWithWildCard EOF
            {
             before(grammarAccess.getQualifiedNameWithWildCardRule()); 
            pushFollow(FOLLOW_ruleQualifiedNameWithWildCard_in_entryRuleQualifiedNameWithWildCard1262);
            ruleQualifiedNameWithWildCard();

            state._fsp--;

             after(grammarAccess.getQualifiedNameWithWildCardRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedNameWithWildCard1269); 

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
    // $ANTLR end "entryRuleQualifiedNameWithWildCard"


    // $ANTLR start "ruleQualifiedNameWithWildCard"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:629:1: ruleQualifiedNameWithWildCard : ( ( rule__QualifiedNameWithWildCard__Group__0 ) ) ;
    public final void ruleQualifiedNameWithWildCard() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:633:2: ( ( ( rule__QualifiedNameWithWildCard__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:634:1: ( ( rule__QualifiedNameWithWildCard__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:634:1: ( ( rule__QualifiedNameWithWildCard__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:635:1: ( rule__QualifiedNameWithWildCard__Group__0 )
            {
             before(grammarAccess.getQualifiedNameWithWildCardAccess().getGroup()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:636:1: ( rule__QualifiedNameWithWildCard__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:636:2: rule__QualifiedNameWithWildCard__Group__0
            {
            pushFollow(FOLLOW_rule__QualifiedNameWithWildCard__Group__0_in_ruleQualifiedNameWithWildCard1295);
            rule__QualifiedNameWithWildCard__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getQualifiedNameWithWildCardAccess().getGroup()); 

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
    // $ANTLR end "ruleQualifiedNameWithWildCard"


    // $ANTLR start "rule__VariableDeclaration__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:650:1: rule__VariableDeclaration__Alternatives : ( ( ( rule__VariableDeclaration__Group_0__0 ) ) | ( ( rule__VariableDeclaration__Group_1__0 ) ) );
    public final void rule__VariableDeclaration__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:654:1: ( ( ( rule__VariableDeclaration__Group_0__0 ) ) | ( ( rule__VariableDeclaration__Group_1__0 ) ) )
            int alt2=2;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:655:1: ( ( rule__VariableDeclaration__Group_0__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:655:1: ( ( rule__VariableDeclaration__Group_0__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:656:1: ( rule__VariableDeclaration__Group_0__0 )
                    {
                     before(grammarAccess.getVariableDeclarationAccess().getGroup_0()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:657:1: ( rule__VariableDeclaration__Group_0__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:657:2: rule__VariableDeclaration__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__VariableDeclaration__Group_0__0_in_rule__VariableDeclaration__Alternatives1333);
                    rule__VariableDeclaration__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getVariableDeclarationAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:661:6: ( ( rule__VariableDeclaration__Group_1__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:661:6: ( ( rule__VariableDeclaration__Group_1__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:662:1: ( rule__VariableDeclaration__Group_1__0 )
                    {
                     before(grammarAccess.getVariableDeclarationAccess().getGroup_1()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:663:1: ( rule__VariableDeclaration__Group_1__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:663:2: rule__VariableDeclaration__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__0_in_rule__VariableDeclaration__Alternatives1351);
                    rule__VariableDeclaration__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getVariableDeclarationAccess().getGroup_1()); 

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
    // $ANTLR end "rule__VariableDeclaration__Alternatives"


    // $ANTLR start "rule__Assignment__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:672:1: rule__Assignment__Alternatives : ( ( ( rule__Assignment__Group_0__0 ) ) | ( ( rule__Assignment__Group_1__0 ) ) );
    public final void rule__Assignment__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:676:1: ( ( ( rule__Assignment__Group_0__0 ) ) | ( ( rule__Assignment__Group_1__0 ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_ID) ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==20) ) {
                    alt3=1;
                }
                else if ( (LA3_1==22) ) {
                    alt3=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:677:1: ( ( rule__Assignment__Group_0__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:677:1: ( ( rule__Assignment__Group_0__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:678:1: ( rule__Assignment__Group_0__0 )
                    {
                     before(grammarAccess.getAssignmentAccess().getGroup_0()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:679:1: ( rule__Assignment__Group_0__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:679:2: rule__Assignment__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__Assignment__Group_0__0_in_rule__Assignment__Alternatives1384);
                    rule__Assignment__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getAssignmentAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:683:6: ( ( rule__Assignment__Group_1__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:683:6: ( ( rule__Assignment__Group_1__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:684:1: ( rule__Assignment__Group_1__0 )
                    {
                     before(grammarAccess.getAssignmentAccess().getGroup_1()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:685:1: ( rule__Assignment__Group_1__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:685:2: rule__Assignment__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__Assignment__Group_1__0_in_rule__Assignment__Alternatives1402);
                    rule__Assignment__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getAssignmentAccess().getGroup_1()); 

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
    // $ANTLR end "rule__Assignment__Alternatives"


    // $ANTLR start "rule__Addition__Alternatives_1_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:694:1: rule__Addition__Alternatives_1_0 : ( ( ( rule__Addition__Group_1_0_0__0 ) ) | ( ( rule__Addition__Group_1_0_1__0 ) ) );
    public final void rule__Addition__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:698:1: ( ( ( rule__Addition__Group_1_0_0__0 ) ) | ( ( rule__Addition__Group_1_0_1__0 ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==26) ) {
                alt4=1;
            }
            else if ( (LA4_0==27) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:699:1: ( ( rule__Addition__Group_1_0_0__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:699:1: ( ( rule__Addition__Group_1_0_0__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:700:1: ( rule__Addition__Group_1_0_0__0 )
                    {
                     before(grammarAccess.getAdditionAccess().getGroup_1_0_0()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:701:1: ( rule__Addition__Group_1_0_0__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:701:2: rule__Addition__Group_1_0_0__0
                    {
                    pushFollow(FOLLOW_rule__Addition__Group_1_0_0__0_in_rule__Addition__Alternatives_1_01435);
                    rule__Addition__Group_1_0_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getAdditionAccess().getGroup_1_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:705:6: ( ( rule__Addition__Group_1_0_1__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:705:6: ( ( rule__Addition__Group_1_0_1__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:706:1: ( rule__Addition__Group_1_0_1__0 )
                    {
                     before(grammarAccess.getAdditionAccess().getGroup_1_0_1()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:707:1: ( rule__Addition__Group_1_0_1__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:707:2: rule__Addition__Group_1_0_1__0
                    {
                    pushFollow(FOLLOW_rule__Addition__Group_1_0_1__0_in_rule__Addition__Alternatives_1_01453);
                    rule__Addition__Group_1_0_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getAdditionAccess().getGroup_1_0_1()); 

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
    // $ANTLR end "rule__Addition__Alternatives_1_0"


    // $ANTLR start "rule__Multiplication__Alternatives_1_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:716:1: rule__Multiplication__Alternatives_1_0 : ( ( ( rule__Multiplication__Group_1_0_0__0 ) ) | ( ( rule__Multiplication__Group_1_0_1__0 ) ) | ( ( rule__Multiplication__Group_1_0_2__0 ) ) );
    public final void rule__Multiplication__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:720:1: ( ( ( rule__Multiplication__Group_1_0_0__0 ) ) | ( ( rule__Multiplication__Group_1_0_1__0 ) ) | ( ( rule__Multiplication__Group_1_0_2__0 ) ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 28:
                {
                alt5=1;
                }
                break;
            case 29:
                {
                alt5=2;
                }
                break;
            case 30:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:721:1: ( ( rule__Multiplication__Group_1_0_0__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:721:1: ( ( rule__Multiplication__Group_1_0_0__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:722:1: ( rule__Multiplication__Group_1_0_0__0 )
                    {
                     before(grammarAccess.getMultiplicationAccess().getGroup_1_0_0()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:723:1: ( rule__Multiplication__Group_1_0_0__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:723:2: rule__Multiplication__Group_1_0_0__0
                    {
                    pushFollow(FOLLOW_rule__Multiplication__Group_1_0_0__0_in_rule__Multiplication__Alternatives_1_01486);
                    rule__Multiplication__Group_1_0_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMultiplicationAccess().getGroup_1_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:727:6: ( ( rule__Multiplication__Group_1_0_1__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:727:6: ( ( rule__Multiplication__Group_1_0_1__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:728:1: ( rule__Multiplication__Group_1_0_1__0 )
                    {
                     before(grammarAccess.getMultiplicationAccess().getGroup_1_0_1()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:729:1: ( rule__Multiplication__Group_1_0_1__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:729:2: rule__Multiplication__Group_1_0_1__0
                    {
                    pushFollow(FOLLOW_rule__Multiplication__Group_1_0_1__0_in_rule__Multiplication__Alternatives_1_01504);
                    rule__Multiplication__Group_1_0_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMultiplicationAccess().getGroup_1_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:733:6: ( ( rule__Multiplication__Group_1_0_2__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:733:6: ( ( rule__Multiplication__Group_1_0_2__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:734:1: ( rule__Multiplication__Group_1_0_2__0 )
                    {
                     before(grammarAccess.getMultiplicationAccess().getGroup_1_0_2()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:735:1: ( rule__Multiplication__Group_1_0_2__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:735:2: rule__Multiplication__Group_1_0_2__0
                    {
                    pushFollow(FOLLOW_rule__Multiplication__Group_1_0_2__0_in_rule__Multiplication__Alternatives_1_01522);
                    rule__Multiplication__Group_1_0_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMultiplicationAccess().getGroup_1_0_2()); 

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
    // $ANTLR end "rule__Multiplication__Alternatives_1_0"


    // $ANTLR start "rule__Primary__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:744:1: rule__Primary__Alternatives : ( ( ruleVariableRef ) | ( ruleConstante ) | ( ( rule__Primary__Group_2__0 ) ) );
    public final void rule__Primary__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:748:1: ( ( ruleVariableRef ) | ( ruleConstante ) | ( ( rule__Primary__Group_2__0 ) ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt6=1;
                }
                break;
            case RULE_INT:
                {
                alt6=2;
                }
                break;
            case 32:
                {
                alt6=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:749:1: ( ruleVariableRef )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:749:1: ( ruleVariableRef )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:750:1: ruleVariableRef
                    {
                     before(grammarAccess.getPrimaryAccess().getVariableRefParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleVariableRef_in_rule__Primary__Alternatives1555);
                    ruleVariableRef();

                    state._fsp--;

                     after(grammarAccess.getPrimaryAccess().getVariableRefParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:755:6: ( ruleConstante )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:755:6: ( ruleConstante )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:756:1: ruleConstante
                    {
                     before(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleConstante_in_rule__Primary__Alternatives1572);
                    ruleConstante();

                    state._fsp--;

                     after(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:761:6: ( ( rule__Primary__Group_2__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:761:6: ( ( rule__Primary__Group_2__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:762:1: ( rule__Primary__Group_2__0 )
                    {
                     before(grammarAccess.getPrimaryAccess().getGroup_2()); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:763:1: ( rule__Primary__Group_2__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:763:2: rule__Primary__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Primary__Group_2__0_in_rule__Primary__Alternatives1589);
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


    // $ANTLR start "rule__Not__Alternatives_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:772:1: rule__Not__Alternatives_1 : ( ( rulePrimaryBool ) | ( ruleComparison ) );
    public final void rule__Not__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:776:1: ( ( rulePrimaryBool ) | ( ruleComparison ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( ((LA7_0>=39 && LA7_0<=40)) ) {
                alt7=1;
            }
            else if ( ((LA7_0>=RULE_ID && LA7_0<=RULE_INT)||LA7_0==27||LA7_0==32) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:777:1: ( rulePrimaryBool )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:777:1: ( rulePrimaryBool )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:778:1: rulePrimaryBool
                    {
                     before(grammarAccess.getNotAccess().getPrimaryBoolParserRuleCall_1_0()); 
                    pushFollow(FOLLOW_rulePrimaryBool_in_rule__Not__Alternatives_11622);
                    rulePrimaryBool();

                    state._fsp--;

                     after(grammarAccess.getNotAccess().getPrimaryBoolParserRuleCall_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:783:6: ( ruleComparison )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:783:6: ( ruleComparison )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:784:1: ruleComparison
                    {
                     before(grammarAccess.getNotAccess().getComparisonParserRuleCall_1_1()); 
                    pushFollow(FOLLOW_ruleComparison_in_rule__Not__Alternatives_11639);
                    ruleComparison();

                    state._fsp--;

                     after(grammarAccess.getNotAccess().getComparisonParserRuleCall_1_1()); 

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
    // $ANTLR end "rule__Not__Alternatives_1"


    // $ANTLR start "rule__PrimaryBool__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:794:1: rule__PrimaryBool__Alternatives : ( ( ruleTrue ) | ( ruleFalse ) );
    public final void rule__PrimaryBool__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:798:1: ( ( ruleTrue ) | ( ruleFalse ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==39) ) {
                alt8=1;
            }
            else if ( (LA8_0==40) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:799:1: ( ruleTrue )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:799:1: ( ruleTrue )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:800:1: ruleTrue
                    {
                     before(grammarAccess.getPrimaryBoolAccess().getTrueParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleTrue_in_rule__PrimaryBool__Alternatives1671);
                    ruleTrue();

                    state._fsp--;

                     after(grammarAccess.getPrimaryBoolAccess().getTrueParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:805:6: ( ruleFalse )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:805:6: ( ruleFalse )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:806:1: ruleFalse
                    {
                     before(grammarAccess.getPrimaryBoolAccess().getFalseParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleFalse_in_rule__PrimaryBool__Alternatives1688);
                    ruleFalse();

                    state._fsp--;

                     after(grammarAccess.getPrimaryBoolAccess().getFalseParserRuleCall_1()); 

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
    // $ANTLR end "rule__PrimaryBool__Alternatives"


    // $ANTLR start "rule__CompOperators__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:816:1: rule__CompOperators__Alternatives : ( ( '<' ) | ( '>' ) | ( '>=' ) | ( '<=' ) | ( '==' ) | ( '!=' ) );
    public final void rule__CompOperators__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:820:1: ( ( '<' ) | ( '>' ) | ( '>=' ) | ( '<=' ) | ( '==' ) | ( '!=' ) )
            int alt9=6;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt9=1;
                }
                break;
            case 12:
                {
                alt9=2;
                }
                break;
            case 13:
                {
                alt9=3;
                }
                break;
            case 14:
                {
                alt9=4;
                }
                break;
            case 15:
                {
                alt9=5;
                }
                break;
            case 16:
                {
                alt9=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:821:1: ( '<' )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:821:1: ( '<' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:822:1: '<'
                    {
                     before(grammarAccess.getCompOperatorsAccess().getLessThanSignKeyword_0()); 
                    match(input,11,FOLLOW_11_in_rule__CompOperators__Alternatives1721); 
                     after(grammarAccess.getCompOperatorsAccess().getLessThanSignKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:829:6: ( '>' )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:829:6: ( '>' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:830:1: '>'
                    {
                     before(grammarAccess.getCompOperatorsAccess().getGreaterThanSignKeyword_1()); 
                    match(input,12,FOLLOW_12_in_rule__CompOperators__Alternatives1741); 
                     after(grammarAccess.getCompOperatorsAccess().getGreaterThanSignKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:837:6: ( '>=' )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:837:6: ( '>=' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:838:1: '>='
                    {
                     before(grammarAccess.getCompOperatorsAccess().getGreaterThanSignEqualsSignKeyword_2()); 
                    match(input,13,FOLLOW_13_in_rule__CompOperators__Alternatives1761); 
                     after(grammarAccess.getCompOperatorsAccess().getGreaterThanSignEqualsSignKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:845:6: ( '<=' )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:845:6: ( '<=' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:846:1: '<='
                    {
                     before(grammarAccess.getCompOperatorsAccess().getLessThanSignEqualsSignKeyword_3()); 
                    match(input,14,FOLLOW_14_in_rule__CompOperators__Alternatives1781); 
                     after(grammarAccess.getCompOperatorsAccess().getLessThanSignEqualsSignKeyword_3()); 

                    }


                    }
                    break;
                case 5 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:853:6: ( '==' )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:853:6: ( '==' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:854:1: '=='
                    {
                     before(grammarAccess.getCompOperatorsAccess().getEqualsSignEqualsSignKeyword_4()); 
                    match(input,15,FOLLOW_15_in_rule__CompOperators__Alternatives1801); 
                     after(grammarAccess.getCompOperatorsAccess().getEqualsSignEqualsSignKeyword_4()); 

                    }


                    }
                    break;
                case 6 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:861:6: ( '!=' )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:861:6: ( '!=' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:862:1: '!='
                    {
                     before(grammarAccess.getCompOperatorsAccess().getExclamationMarkEqualsSignKeyword_5()); 
                    match(input,16,FOLLOW_16_in_rule__CompOperators__Alternatives1821); 
                     after(grammarAccess.getCompOperatorsAccess().getExclamationMarkEqualsSignKeyword_5()); 

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
    // $ANTLR end "rule__CompOperators__Alternatives"


    // $ANTLR start "rule__System__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:876:1: rule__System__Group__0 : rule__System__Group__0__Impl rule__System__Group__1 ;
    public final void rule__System__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:880:1: ( rule__System__Group__0__Impl rule__System__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:881:2: rule__System__Group__0__Impl rule__System__Group__1
            {
            pushFollow(FOLLOW_rule__System__Group__0__Impl_in_rule__System__Group__01853);
            rule__System__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__System__Group__1_in_rule__System__Group__01856);
            rule__System__Group__1();

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
    // $ANTLR end "rule__System__Group__0"


    // $ANTLR start "rule__System__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:888:1: rule__System__Group__0__Impl : ( 'GAL' ) ;
    public final void rule__System__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:892:1: ( ( 'GAL' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:893:1: ( 'GAL' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:893:1: ( 'GAL' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:894:1: 'GAL'
            {
             before(grammarAccess.getSystemAccess().getGALKeyword_0()); 
            match(input,17,FOLLOW_17_in_rule__System__Group__0__Impl1884); 
             after(grammarAccess.getSystemAccess().getGALKeyword_0()); 

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
    // $ANTLR end "rule__System__Group__0__Impl"


    // $ANTLR start "rule__System__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:907:1: rule__System__Group__1 : rule__System__Group__1__Impl rule__System__Group__2 ;
    public final void rule__System__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:911:1: ( rule__System__Group__1__Impl rule__System__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:912:2: rule__System__Group__1__Impl rule__System__Group__2
            {
            pushFollow(FOLLOW_rule__System__Group__1__Impl_in_rule__System__Group__11915);
            rule__System__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__System__Group__2_in_rule__System__Group__11918);
            rule__System__Group__2();

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
    // $ANTLR end "rule__System__Group__1"


    // $ANTLR start "rule__System__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:919:1: rule__System__Group__1__Impl : ( ( rule__System__NameAssignment_1 ) ) ;
    public final void rule__System__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:923:1: ( ( ( rule__System__NameAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:924:1: ( ( rule__System__NameAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:924:1: ( ( rule__System__NameAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:925:1: ( rule__System__NameAssignment_1 )
            {
             before(grammarAccess.getSystemAccess().getNameAssignment_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:926:1: ( rule__System__NameAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:926:2: rule__System__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__System__NameAssignment_1_in_rule__System__Group__1__Impl1945);
            rule__System__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getSystemAccess().getNameAssignment_1()); 

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
    // $ANTLR end "rule__System__Group__1__Impl"


    // $ANTLR start "rule__System__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:936:1: rule__System__Group__2 : rule__System__Group__2__Impl rule__System__Group__3 ;
    public final void rule__System__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:940:1: ( rule__System__Group__2__Impl rule__System__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:941:2: rule__System__Group__2__Impl rule__System__Group__3
            {
            pushFollow(FOLLOW_rule__System__Group__2__Impl_in_rule__System__Group__21975);
            rule__System__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__System__Group__3_in_rule__System__Group__21978);
            rule__System__Group__3();

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
    // $ANTLR end "rule__System__Group__2"


    // $ANTLR start "rule__System__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:948:1: rule__System__Group__2__Impl : ( '{' ) ;
    public final void rule__System__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:952:1: ( ( '{' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:953:1: ( '{' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:953:1: ( '{' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:954:1: '{'
            {
             before(grammarAccess.getSystemAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,18,FOLLOW_18_in_rule__System__Group__2__Impl2006); 
             after(grammarAccess.getSystemAccess().getLeftCurlyBracketKeyword_2()); 

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
    // $ANTLR end "rule__System__Group__2__Impl"


    // $ANTLR start "rule__System__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:967:1: rule__System__Group__3 : rule__System__Group__3__Impl rule__System__Group__4 ;
    public final void rule__System__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:971:1: ( rule__System__Group__3__Impl rule__System__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:972:2: rule__System__Group__3__Impl rule__System__Group__4
            {
            pushFollow(FOLLOW_rule__System__Group__3__Impl_in_rule__System__Group__32037);
            rule__System__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__System__Group__4_in_rule__System__Group__32040);
            rule__System__Group__4();

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
    // $ANTLR end "rule__System__Group__3"


    // $ANTLR start "rule__System__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:979:1: rule__System__Group__3__Impl : ( ( rule__System__VariablesAssignment_3 )* ) ;
    public final void rule__System__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:983:1: ( ( ( rule__System__VariablesAssignment_3 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:984:1: ( ( rule__System__VariablesAssignment_3 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:984:1: ( ( rule__System__VariablesAssignment_3 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:985:1: ( rule__System__VariablesAssignment_3 )*
            {
             before(grammarAccess.getSystemAccess().getVariablesAssignment_3()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:986:1: ( rule__System__VariablesAssignment_3 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_ID) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:986:2: rule__System__VariablesAssignment_3
            	    {
            	    pushFollow(FOLLOW_rule__System__VariablesAssignment_3_in_rule__System__Group__3__Impl2067);
            	    rule__System__VariablesAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getSystemAccess().getVariablesAssignment_3()); 

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
    // $ANTLR end "rule__System__Group__3__Impl"


    // $ANTLR start "rule__System__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:996:1: rule__System__Group__4 : rule__System__Group__4__Impl rule__System__Group__5 ;
    public final void rule__System__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1000:1: ( rule__System__Group__4__Impl rule__System__Group__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1001:2: rule__System__Group__4__Impl rule__System__Group__5
            {
            pushFollow(FOLLOW_rule__System__Group__4__Impl_in_rule__System__Group__42098);
            rule__System__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__System__Group__5_in_rule__System__Group__42101);
            rule__System__Group__5();

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
    // $ANTLR end "rule__System__Group__4"


    // $ANTLR start "rule__System__Group__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1008:1: rule__System__Group__4__Impl : ( ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* ) ) ;
    public final void rule__System__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1012:1: ( ( ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1013:1: ( ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1013:1: ( ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1014:1: ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1014:1: ( ( rule__System__TransitionsAssignment_4 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1015:1: ( rule__System__TransitionsAssignment_4 )
            {
             before(grammarAccess.getSystemAccess().getTransitionsAssignment_4()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1016:1: ( rule__System__TransitionsAssignment_4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1016:2: rule__System__TransitionsAssignment_4
            {
            pushFollow(FOLLOW_rule__System__TransitionsAssignment_4_in_rule__System__Group__4__Impl2130);
            rule__System__TransitionsAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getSystemAccess().getTransitionsAssignment_4()); 

            }

            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1019:1: ( ( rule__System__TransitionsAssignment_4 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1020:1: ( rule__System__TransitionsAssignment_4 )*
            {
             before(grammarAccess.getSystemAccess().getTransitionsAssignment_4()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1021:1: ( rule__System__TransitionsAssignment_4 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==24) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1021:2: rule__System__TransitionsAssignment_4
            	    {
            	    pushFollow(FOLLOW_rule__System__TransitionsAssignment_4_in_rule__System__Group__4__Impl2142);
            	    rule__System__TransitionsAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

             after(grammarAccess.getSystemAccess().getTransitionsAssignment_4()); 

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
    // $ANTLR end "rule__System__Group__4__Impl"


    // $ANTLR start "rule__System__Group__5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1032:1: rule__System__Group__5 : rule__System__Group__5__Impl ;
    public final void rule__System__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1036:1: ( rule__System__Group__5__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1037:2: rule__System__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__System__Group__5__Impl_in_rule__System__Group__52175);
            rule__System__Group__5__Impl();

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
    // $ANTLR end "rule__System__Group__5"


    // $ANTLR start "rule__System__Group__5__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1043:1: rule__System__Group__5__Impl : ( '}' ) ;
    public final void rule__System__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1047:1: ( ( '}' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1048:1: ( '}' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1048:1: ( '}' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1049:1: '}'
            {
             before(grammarAccess.getSystemAccess().getRightCurlyBracketKeyword_5()); 
            match(input,19,FOLLOW_19_in_rule__System__Group__5__Impl2203); 
             after(grammarAccess.getSystemAccess().getRightCurlyBracketKeyword_5()); 

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
    // $ANTLR end "rule__System__Group__5__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_0__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1074:1: rule__VariableDeclaration__Group_0__0 : rule__VariableDeclaration__Group_0__0__Impl rule__VariableDeclaration__Group_0__1 ;
    public final void rule__VariableDeclaration__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1078:1: ( rule__VariableDeclaration__Group_0__0__Impl rule__VariableDeclaration__Group_0__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1079:2: rule__VariableDeclaration__Group_0__0__Impl rule__VariableDeclaration__Group_0__1
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_0__0__Impl_in_rule__VariableDeclaration__Group_0__02246);
            rule__VariableDeclaration__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group_0__1_in_rule__VariableDeclaration__Group_0__02249);
            rule__VariableDeclaration__Group_0__1();

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
    // $ANTLR end "rule__VariableDeclaration__Group_0__0"


    // $ANTLR start "rule__VariableDeclaration__Group_0__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1086:1: rule__VariableDeclaration__Group_0__0__Impl : ( ( rule__VariableDeclaration__NameAssignment_0_0 ) ) ;
    public final void rule__VariableDeclaration__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1090:1: ( ( ( rule__VariableDeclaration__NameAssignment_0_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1091:1: ( ( rule__VariableDeclaration__NameAssignment_0_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1091:1: ( ( rule__VariableDeclaration__NameAssignment_0_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1092:1: ( rule__VariableDeclaration__NameAssignment_0_0 )
            {
             before(grammarAccess.getVariableDeclarationAccess().getNameAssignment_0_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1093:1: ( rule__VariableDeclaration__NameAssignment_0_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1093:2: rule__VariableDeclaration__NameAssignment_0_0
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__NameAssignment_0_0_in_rule__VariableDeclaration__Group_0__0__Impl2276);
            rule__VariableDeclaration__NameAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getVariableDeclarationAccess().getNameAssignment_0_0()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_0__0__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_0__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1103:1: rule__VariableDeclaration__Group_0__1 : rule__VariableDeclaration__Group_0__1__Impl rule__VariableDeclaration__Group_0__2 ;
    public final void rule__VariableDeclaration__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1107:1: ( rule__VariableDeclaration__Group_0__1__Impl rule__VariableDeclaration__Group_0__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1108:2: rule__VariableDeclaration__Group_0__1__Impl rule__VariableDeclaration__Group_0__2
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_0__1__Impl_in_rule__VariableDeclaration__Group_0__12306);
            rule__VariableDeclaration__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group_0__2_in_rule__VariableDeclaration__Group_0__12309);
            rule__VariableDeclaration__Group_0__2();

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
    // $ANTLR end "rule__VariableDeclaration__Group_0__1"


    // $ANTLR start "rule__VariableDeclaration__Group_0__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1115:1: rule__VariableDeclaration__Group_0__1__Impl : ( '=' ) ;
    public final void rule__VariableDeclaration__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1119:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1120:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1120:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1121:1: '='
            {
             before(grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_0_1()); 
            match(input,20,FOLLOW_20_in_rule__VariableDeclaration__Group_0__1__Impl2337); 
             after(grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_0_1()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_0__1__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_0__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1134:1: rule__VariableDeclaration__Group_0__2 : rule__VariableDeclaration__Group_0__2__Impl rule__VariableDeclaration__Group_0__3 ;
    public final void rule__VariableDeclaration__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1138:1: ( rule__VariableDeclaration__Group_0__2__Impl rule__VariableDeclaration__Group_0__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1139:2: rule__VariableDeclaration__Group_0__2__Impl rule__VariableDeclaration__Group_0__3
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_0__2__Impl_in_rule__VariableDeclaration__Group_0__22368);
            rule__VariableDeclaration__Group_0__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group_0__3_in_rule__VariableDeclaration__Group_0__22371);
            rule__VariableDeclaration__Group_0__3();

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
    // $ANTLR end "rule__VariableDeclaration__Group_0__2"


    // $ANTLR start "rule__VariableDeclaration__Group_0__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1146:1: rule__VariableDeclaration__Group_0__2__Impl : ( ( rule__VariableDeclaration__InitValAssignment_0_2 ) ) ;
    public final void rule__VariableDeclaration__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1150:1: ( ( ( rule__VariableDeclaration__InitValAssignment_0_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1151:1: ( ( rule__VariableDeclaration__InitValAssignment_0_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1151:1: ( ( rule__VariableDeclaration__InitValAssignment_0_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1152:1: ( rule__VariableDeclaration__InitValAssignment_0_2 )
            {
             before(grammarAccess.getVariableDeclarationAccess().getInitValAssignment_0_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1153:1: ( rule__VariableDeclaration__InitValAssignment_0_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1153:2: rule__VariableDeclaration__InitValAssignment_0_2
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__InitValAssignment_0_2_in_rule__VariableDeclaration__Group_0__2__Impl2398);
            rule__VariableDeclaration__InitValAssignment_0_2();

            state._fsp--;


            }

             after(grammarAccess.getVariableDeclarationAccess().getInitValAssignment_0_2()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_0__2__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_0__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1163:1: rule__VariableDeclaration__Group_0__3 : rule__VariableDeclaration__Group_0__3__Impl ;
    public final void rule__VariableDeclaration__Group_0__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1167:1: ( rule__VariableDeclaration__Group_0__3__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1168:2: rule__VariableDeclaration__Group_0__3__Impl
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_0__3__Impl_in_rule__VariableDeclaration__Group_0__32428);
            rule__VariableDeclaration__Group_0__3__Impl();

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
    // $ANTLR end "rule__VariableDeclaration__Group_0__3"


    // $ANTLR start "rule__VariableDeclaration__Group_0__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1174:1: rule__VariableDeclaration__Group_0__3__Impl : ( ';' ) ;
    public final void rule__VariableDeclaration__Group_0__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1178:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1179:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1179:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1180:1: ';'
            {
             before(grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_0_3()); 
            match(input,21,FOLLOW_21_in_rule__VariableDeclaration__Group_0__3__Impl2456); 
             after(grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_0_3()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_0__3__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1201:1: rule__VariableDeclaration__Group_1__0 : rule__VariableDeclaration__Group_1__0__Impl rule__VariableDeclaration__Group_1__1 ;
    public final void rule__VariableDeclaration__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1205:1: ( rule__VariableDeclaration__Group_1__0__Impl rule__VariableDeclaration__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1206:2: rule__VariableDeclaration__Group_1__0__Impl rule__VariableDeclaration__Group_1__1
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__0__Impl_in_rule__VariableDeclaration__Group_1__02495);
            rule__VariableDeclaration__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__1_in_rule__VariableDeclaration__Group_1__02498);
            rule__VariableDeclaration__Group_1__1();

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__0"


    // $ANTLR start "rule__VariableDeclaration__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1213:1: rule__VariableDeclaration__Group_1__0__Impl : ( ( rule__VariableDeclaration__AnameAssignment_1_0 ) ) ;
    public final void rule__VariableDeclaration__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1217:1: ( ( ( rule__VariableDeclaration__AnameAssignment_1_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1218:1: ( ( rule__VariableDeclaration__AnameAssignment_1_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1218:1: ( ( rule__VariableDeclaration__AnameAssignment_1_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1219:1: ( rule__VariableDeclaration__AnameAssignment_1_0 )
            {
             before(grammarAccess.getVariableDeclarationAccess().getAnameAssignment_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1220:1: ( rule__VariableDeclaration__AnameAssignment_1_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1220:2: rule__VariableDeclaration__AnameAssignment_1_0
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__AnameAssignment_1_0_in_rule__VariableDeclaration__Group_1__0__Impl2525);
            rule__VariableDeclaration__AnameAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getVariableDeclarationAccess().getAnameAssignment_1_0()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__0__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1230:1: rule__VariableDeclaration__Group_1__1 : rule__VariableDeclaration__Group_1__1__Impl rule__VariableDeclaration__Group_1__2 ;
    public final void rule__VariableDeclaration__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1234:1: ( rule__VariableDeclaration__Group_1__1__Impl rule__VariableDeclaration__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1235:2: rule__VariableDeclaration__Group_1__1__Impl rule__VariableDeclaration__Group_1__2
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__1__Impl_in_rule__VariableDeclaration__Group_1__12555);
            rule__VariableDeclaration__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__2_in_rule__VariableDeclaration__Group_1__12558);
            rule__VariableDeclaration__Group_1__2();

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__1"


    // $ANTLR start "rule__VariableDeclaration__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1242:1: rule__VariableDeclaration__Group_1__1__Impl : ( '[' ) ;
    public final void rule__VariableDeclaration__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1246:1: ( ( '[' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1247:1: ( '[' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1247:1: ( '[' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1248:1: '['
            {
             before(grammarAccess.getVariableDeclarationAccess().getLeftSquareBracketKeyword_1_1()); 
            match(input,22,FOLLOW_22_in_rule__VariableDeclaration__Group_1__1__Impl2586); 
             after(grammarAccess.getVariableDeclarationAccess().getLeftSquareBracketKeyword_1_1()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__1__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1261:1: rule__VariableDeclaration__Group_1__2 : rule__VariableDeclaration__Group_1__2__Impl rule__VariableDeclaration__Group_1__3 ;
    public final void rule__VariableDeclaration__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1265:1: ( rule__VariableDeclaration__Group_1__2__Impl rule__VariableDeclaration__Group_1__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1266:2: rule__VariableDeclaration__Group_1__2__Impl rule__VariableDeclaration__Group_1__3
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__2__Impl_in_rule__VariableDeclaration__Group_1__22617);
            rule__VariableDeclaration__Group_1__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__3_in_rule__VariableDeclaration__Group_1__22620);
            rule__VariableDeclaration__Group_1__3();

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__2"


    // $ANTLR start "rule__VariableDeclaration__Group_1__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1273:1: rule__VariableDeclaration__Group_1__2__Impl : ( ( rule__VariableDeclaration__IndexAssignment_1_2 ) ) ;
    public final void rule__VariableDeclaration__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1277:1: ( ( ( rule__VariableDeclaration__IndexAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1278:1: ( ( rule__VariableDeclaration__IndexAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1278:1: ( ( rule__VariableDeclaration__IndexAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1279:1: ( rule__VariableDeclaration__IndexAssignment_1_2 )
            {
             before(grammarAccess.getVariableDeclarationAccess().getIndexAssignment_1_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1280:1: ( rule__VariableDeclaration__IndexAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1280:2: rule__VariableDeclaration__IndexAssignment_1_2
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__IndexAssignment_1_2_in_rule__VariableDeclaration__Group_1__2__Impl2647);
            rule__VariableDeclaration__IndexAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getVariableDeclarationAccess().getIndexAssignment_1_2()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__2__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_1__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1290:1: rule__VariableDeclaration__Group_1__3 : rule__VariableDeclaration__Group_1__3__Impl rule__VariableDeclaration__Group_1__4 ;
    public final void rule__VariableDeclaration__Group_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1294:1: ( rule__VariableDeclaration__Group_1__3__Impl rule__VariableDeclaration__Group_1__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1295:2: rule__VariableDeclaration__Group_1__3__Impl rule__VariableDeclaration__Group_1__4
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__3__Impl_in_rule__VariableDeclaration__Group_1__32677);
            rule__VariableDeclaration__Group_1__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__4_in_rule__VariableDeclaration__Group_1__32680);
            rule__VariableDeclaration__Group_1__4();

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__3"


    // $ANTLR start "rule__VariableDeclaration__Group_1__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1302:1: rule__VariableDeclaration__Group_1__3__Impl : ( ']' ) ;
    public final void rule__VariableDeclaration__Group_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1306:1: ( ( ']' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1307:1: ( ']' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1307:1: ( ']' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1308:1: ']'
            {
             before(grammarAccess.getVariableDeclarationAccess().getRightSquareBracketKeyword_1_3()); 
            match(input,23,FOLLOW_23_in_rule__VariableDeclaration__Group_1__3__Impl2708); 
             after(grammarAccess.getVariableDeclarationAccess().getRightSquareBracketKeyword_1_3()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__3__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_1__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1321:1: rule__VariableDeclaration__Group_1__4 : rule__VariableDeclaration__Group_1__4__Impl rule__VariableDeclaration__Group_1__5 ;
    public final void rule__VariableDeclaration__Group_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1325:1: ( rule__VariableDeclaration__Group_1__4__Impl rule__VariableDeclaration__Group_1__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1326:2: rule__VariableDeclaration__Group_1__4__Impl rule__VariableDeclaration__Group_1__5
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__4__Impl_in_rule__VariableDeclaration__Group_1__42739);
            rule__VariableDeclaration__Group_1__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__5_in_rule__VariableDeclaration__Group_1__42742);
            rule__VariableDeclaration__Group_1__5();

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__4"


    // $ANTLR start "rule__VariableDeclaration__Group_1__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1333:1: rule__VariableDeclaration__Group_1__4__Impl : ( '=' ) ;
    public final void rule__VariableDeclaration__Group_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1337:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1338:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1338:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1339:1: '='
            {
             before(grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_1_4()); 
            match(input,20,FOLLOW_20_in_rule__VariableDeclaration__Group_1__4__Impl2770); 
             after(grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_1_4()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__4__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_1__5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1352:1: rule__VariableDeclaration__Group_1__5 : rule__VariableDeclaration__Group_1__5__Impl rule__VariableDeclaration__Group_1__6 ;
    public final void rule__VariableDeclaration__Group_1__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1356:1: ( rule__VariableDeclaration__Group_1__5__Impl rule__VariableDeclaration__Group_1__6 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1357:2: rule__VariableDeclaration__Group_1__5__Impl rule__VariableDeclaration__Group_1__6
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__5__Impl_in_rule__VariableDeclaration__Group_1__52801);
            rule__VariableDeclaration__Group_1__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__6_in_rule__VariableDeclaration__Group_1__52804);
            rule__VariableDeclaration__Group_1__6();

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__5"


    // $ANTLR start "rule__VariableDeclaration__Group_1__5__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1364:1: rule__VariableDeclaration__Group_1__5__Impl : ( ( rule__VariableDeclaration__InitValAssignment_1_5 ) ) ;
    public final void rule__VariableDeclaration__Group_1__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1368:1: ( ( ( rule__VariableDeclaration__InitValAssignment_1_5 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1369:1: ( ( rule__VariableDeclaration__InitValAssignment_1_5 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1369:1: ( ( rule__VariableDeclaration__InitValAssignment_1_5 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1370:1: ( rule__VariableDeclaration__InitValAssignment_1_5 )
            {
             before(grammarAccess.getVariableDeclarationAccess().getInitValAssignment_1_5()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1371:1: ( rule__VariableDeclaration__InitValAssignment_1_5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1371:2: rule__VariableDeclaration__InitValAssignment_1_5
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__InitValAssignment_1_5_in_rule__VariableDeclaration__Group_1__5__Impl2831);
            rule__VariableDeclaration__InitValAssignment_1_5();

            state._fsp--;


            }

             after(grammarAccess.getVariableDeclarationAccess().getInitValAssignment_1_5()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__5__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group_1__6"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1381:1: rule__VariableDeclaration__Group_1__6 : rule__VariableDeclaration__Group_1__6__Impl ;
    public final void rule__VariableDeclaration__Group_1__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1385:1: ( rule__VariableDeclaration__Group_1__6__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1386:2: rule__VariableDeclaration__Group_1__6__Impl
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group_1__6__Impl_in_rule__VariableDeclaration__Group_1__62861);
            rule__VariableDeclaration__Group_1__6__Impl();

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__6"


    // $ANTLR start "rule__VariableDeclaration__Group_1__6__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1392:1: rule__VariableDeclaration__Group_1__6__Impl : ( ';' ) ;
    public final void rule__VariableDeclaration__Group_1__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1396:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1397:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1397:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1398:1: ';'
            {
             before(grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_1_6()); 
            match(input,21,FOLLOW_21_in_rule__VariableDeclaration__Group_1__6__Impl2889); 
             after(grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_1_6()); 

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
    // $ANTLR end "rule__VariableDeclaration__Group_1__6__Impl"


    // $ANTLR start "rule__Transition__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1425:1: rule__Transition__Group__0 : rule__Transition__Group__0__Impl rule__Transition__Group__1 ;
    public final void rule__Transition__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1429:1: ( rule__Transition__Group__0__Impl rule__Transition__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1430:2: rule__Transition__Group__0__Impl rule__Transition__Group__1
            {
            pushFollow(FOLLOW_rule__Transition__Group__0__Impl_in_rule__Transition__Group__02934);
            rule__Transition__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Transition__Group__1_in_rule__Transition__Group__02937);
            rule__Transition__Group__1();

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
    // $ANTLR end "rule__Transition__Group__0"


    // $ANTLR start "rule__Transition__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1437:1: rule__Transition__Group__0__Impl : ( 'transition' ) ;
    public final void rule__Transition__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1441:1: ( ( 'transition' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1442:1: ( 'transition' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1442:1: ( 'transition' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1443:1: 'transition'
            {
             before(grammarAccess.getTransitionAccess().getTransitionKeyword_0()); 
            match(input,24,FOLLOW_24_in_rule__Transition__Group__0__Impl2965); 
             after(grammarAccess.getTransitionAccess().getTransitionKeyword_0()); 

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
    // $ANTLR end "rule__Transition__Group__0__Impl"


    // $ANTLR start "rule__Transition__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1456:1: rule__Transition__Group__1 : rule__Transition__Group__1__Impl rule__Transition__Group__2 ;
    public final void rule__Transition__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1460:1: ( rule__Transition__Group__1__Impl rule__Transition__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1461:2: rule__Transition__Group__1__Impl rule__Transition__Group__2
            {
            pushFollow(FOLLOW_rule__Transition__Group__1__Impl_in_rule__Transition__Group__12996);
            rule__Transition__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Transition__Group__2_in_rule__Transition__Group__12999);
            rule__Transition__Group__2();

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
    // $ANTLR end "rule__Transition__Group__1"


    // $ANTLR start "rule__Transition__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1468:1: rule__Transition__Group__1__Impl : ( ( rule__Transition__NameAssignment_1 ) ) ;
    public final void rule__Transition__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1472:1: ( ( ( rule__Transition__NameAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1473:1: ( ( rule__Transition__NameAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1473:1: ( ( rule__Transition__NameAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1474:1: ( rule__Transition__NameAssignment_1 )
            {
             before(grammarAccess.getTransitionAccess().getNameAssignment_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1475:1: ( rule__Transition__NameAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1475:2: rule__Transition__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__Transition__NameAssignment_1_in_rule__Transition__Group__1__Impl3026);
            rule__Transition__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getTransitionAccess().getNameAssignment_1()); 

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
    // $ANTLR end "rule__Transition__Group__1__Impl"


    // $ANTLR start "rule__Transition__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1485:1: rule__Transition__Group__2 : rule__Transition__Group__2__Impl rule__Transition__Group__3 ;
    public final void rule__Transition__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1489:1: ( rule__Transition__Group__2__Impl rule__Transition__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1490:2: rule__Transition__Group__2__Impl rule__Transition__Group__3
            {
            pushFollow(FOLLOW_rule__Transition__Group__2__Impl_in_rule__Transition__Group__23056);
            rule__Transition__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Transition__Group__3_in_rule__Transition__Group__23059);
            rule__Transition__Group__3();

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
    // $ANTLR end "rule__Transition__Group__2"


    // $ANTLR start "rule__Transition__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1497:1: rule__Transition__Group__2__Impl : ( '[' ) ;
    public final void rule__Transition__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1501:1: ( ( '[' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1502:1: ( '[' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1502:1: ( '[' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1503:1: '['
            {
             before(grammarAccess.getTransitionAccess().getLeftSquareBracketKeyword_2()); 
            match(input,22,FOLLOW_22_in_rule__Transition__Group__2__Impl3087); 
             after(grammarAccess.getTransitionAccess().getLeftSquareBracketKeyword_2()); 

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
    // $ANTLR end "rule__Transition__Group__2__Impl"


    // $ANTLR start "rule__Transition__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1516:1: rule__Transition__Group__3 : rule__Transition__Group__3__Impl rule__Transition__Group__4 ;
    public final void rule__Transition__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1520:1: ( rule__Transition__Group__3__Impl rule__Transition__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1521:2: rule__Transition__Group__3__Impl rule__Transition__Group__4
            {
            pushFollow(FOLLOW_rule__Transition__Group__3__Impl_in_rule__Transition__Group__33118);
            rule__Transition__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Transition__Group__4_in_rule__Transition__Group__33121);
            rule__Transition__Group__4();

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
    // $ANTLR end "rule__Transition__Group__3"


    // $ANTLR start "rule__Transition__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1528:1: rule__Transition__Group__3__Impl : ( ( rule__Transition__GuardAssignment_3 ) ) ;
    public final void rule__Transition__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1532:1: ( ( ( rule__Transition__GuardAssignment_3 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1533:1: ( ( rule__Transition__GuardAssignment_3 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1533:1: ( ( rule__Transition__GuardAssignment_3 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1534:1: ( rule__Transition__GuardAssignment_3 )
            {
             before(grammarAccess.getTransitionAccess().getGuardAssignment_3()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1535:1: ( rule__Transition__GuardAssignment_3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1535:2: rule__Transition__GuardAssignment_3
            {
            pushFollow(FOLLOW_rule__Transition__GuardAssignment_3_in_rule__Transition__Group__3__Impl3148);
            rule__Transition__GuardAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getTransitionAccess().getGuardAssignment_3()); 

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
    // $ANTLR end "rule__Transition__Group__3__Impl"


    // $ANTLR start "rule__Transition__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1545:1: rule__Transition__Group__4 : rule__Transition__Group__4__Impl rule__Transition__Group__5 ;
    public final void rule__Transition__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1549:1: ( rule__Transition__Group__4__Impl rule__Transition__Group__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1550:2: rule__Transition__Group__4__Impl rule__Transition__Group__5
            {
            pushFollow(FOLLOW_rule__Transition__Group__4__Impl_in_rule__Transition__Group__43178);
            rule__Transition__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Transition__Group__5_in_rule__Transition__Group__43181);
            rule__Transition__Group__5();

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
    // $ANTLR end "rule__Transition__Group__4"


    // $ANTLR start "rule__Transition__Group__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1557:1: rule__Transition__Group__4__Impl : ( ']' ) ;
    public final void rule__Transition__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1561:1: ( ( ']' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1562:1: ( ']' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1562:1: ( ']' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1563:1: ']'
            {
             before(grammarAccess.getTransitionAccess().getRightSquareBracketKeyword_4()); 
            match(input,23,FOLLOW_23_in_rule__Transition__Group__4__Impl3209); 
             after(grammarAccess.getTransitionAccess().getRightSquareBracketKeyword_4()); 

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
    // $ANTLR end "rule__Transition__Group__4__Impl"


    // $ANTLR start "rule__Transition__Group__5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1576:1: rule__Transition__Group__5 : rule__Transition__Group__5__Impl rule__Transition__Group__6 ;
    public final void rule__Transition__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1580:1: ( rule__Transition__Group__5__Impl rule__Transition__Group__6 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1581:2: rule__Transition__Group__5__Impl rule__Transition__Group__6
            {
            pushFollow(FOLLOW_rule__Transition__Group__5__Impl_in_rule__Transition__Group__53240);
            rule__Transition__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Transition__Group__6_in_rule__Transition__Group__53243);
            rule__Transition__Group__6();

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
    // $ANTLR end "rule__Transition__Group__5"


    // $ANTLR start "rule__Transition__Group__5__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1588:1: rule__Transition__Group__5__Impl : ( ( rule__Transition__Group_5__0 )? ) ;
    public final void rule__Transition__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1592:1: ( ( ( rule__Transition__Group_5__0 )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1593:1: ( ( rule__Transition__Group_5__0 )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1593:1: ( ( rule__Transition__Group_5__0 )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1594:1: ( rule__Transition__Group_5__0 )?
            {
             before(grammarAccess.getTransitionAccess().getGroup_5()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1595:1: ( rule__Transition__Group_5__0 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==25) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1595:2: rule__Transition__Group_5__0
                    {
                    pushFollow(FOLLOW_rule__Transition__Group_5__0_in_rule__Transition__Group__5__Impl3270);
                    rule__Transition__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTransitionAccess().getGroup_5()); 

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
    // $ANTLR end "rule__Transition__Group__5__Impl"


    // $ANTLR start "rule__Transition__Group__6"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1605:1: rule__Transition__Group__6 : rule__Transition__Group__6__Impl rule__Transition__Group__7 ;
    public final void rule__Transition__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1609:1: ( rule__Transition__Group__6__Impl rule__Transition__Group__7 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1610:2: rule__Transition__Group__6__Impl rule__Transition__Group__7
            {
            pushFollow(FOLLOW_rule__Transition__Group__6__Impl_in_rule__Transition__Group__63301);
            rule__Transition__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Transition__Group__7_in_rule__Transition__Group__63304);
            rule__Transition__Group__7();

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
    // $ANTLR end "rule__Transition__Group__6"


    // $ANTLR start "rule__Transition__Group__6__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1617:1: rule__Transition__Group__6__Impl : ( '{' ) ;
    public final void rule__Transition__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1621:1: ( ( '{' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1622:1: ( '{' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1622:1: ( '{' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1623:1: '{'
            {
             before(grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_6()); 
            match(input,18,FOLLOW_18_in_rule__Transition__Group__6__Impl3332); 
             after(grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_6()); 

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
    // $ANTLR end "rule__Transition__Group__6__Impl"


    // $ANTLR start "rule__Transition__Group__7"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1636:1: rule__Transition__Group__7 : rule__Transition__Group__7__Impl rule__Transition__Group__8 ;
    public final void rule__Transition__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1640:1: ( rule__Transition__Group__7__Impl rule__Transition__Group__8 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1641:2: rule__Transition__Group__7__Impl rule__Transition__Group__8
            {
            pushFollow(FOLLOW_rule__Transition__Group__7__Impl_in_rule__Transition__Group__73363);
            rule__Transition__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Transition__Group__8_in_rule__Transition__Group__73366);
            rule__Transition__Group__8();

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
    // $ANTLR end "rule__Transition__Group__7"


    // $ANTLR start "rule__Transition__Group__7__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1648:1: rule__Transition__Group__7__Impl : ( ( ( rule__Transition__AssignmentsAssignment_7 ) ) ( ( rule__Transition__AssignmentsAssignment_7 )* ) ) ;
    public final void rule__Transition__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1652:1: ( ( ( ( rule__Transition__AssignmentsAssignment_7 ) ) ( ( rule__Transition__AssignmentsAssignment_7 )* ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1653:1: ( ( ( rule__Transition__AssignmentsAssignment_7 ) ) ( ( rule__Transition__AssignmentsAssignment_7 )* ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1653:1: ( ( ( rule__Transition__AssignmentsAssignment_7 ) ) ( ( rule__Transition__AssignmentsAssignment_7 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1654:1: ( ( rule__Transition__AssignmentsAssignment_7 ) ) ( ( rule__Transition__AssignmentsAssignment_7 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1654:1: ( ( rule__Transition__AssignmentsAssignment_7 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1655:1: ( rule__Transition__AssignmentsAssignment_7 )
            {
             before(grammarAccess.getTransitionAccess().getAssignmentsAssignment_7()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1656:1: ( rule__Transition__AssignmentsAssignment_7 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1656:2: rule__Transition__AssignmentsAssignment_7
            {
            pushFollow(FOLLOW_rule__Transition__AssignmentsAssignment_7_in_rule__Transition__Group__7__Impl3395);
            rule__Transition__AssignmentsAssignment_7();

            state._fsp--;


            }

             after(grammarAccess.getTransitionAccess().getAssignmentsAssignment_7()); 

            }

            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1659:1: ( ( rule__Transition__AssignmentsAssignment_7 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1660:1: ( rule__Transition__AssignmentsAssignment_7 )*
            {
             before(grammarAccess.getTransitionAccess().getAssignmentsAssignment_7()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1661:1: ( rule__Transition__AssignmentsAssignment_7 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_ID) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1661:2: rule__Transition__AssignmentsAssignment_7
            	    {
            	    pushFollow(FOLLOW_rule__Transition__AssignmentsAssignment_7_in_rule__Transition__Group__7__Impl3407);
            	    rule__Transition__AssignmentsAssignment_7();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

             after(grammarAccess.getTransitionAccess().getAssignmentsAssignment_7()); 

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
    // $ANTLR end "rule__Transition__Group__7__Impl"


    // $ANTLR start "rule__Transition__Group__8"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1672:1: rule__Transition__Group__8 : rule__Transition__Group__8__Impl ;
    public final void rule__Transition__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1676:1: ( rule__Transition__Group__8__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1677:2: rule__Transition__Group__8__Impl
            {
            pushFollow(FOLLOW_rule__Transition__Group__8__Impl_in_rule__Transition__Group__83440);
            rule__Transition__Group__8__Impl();

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
    // $ANTLR end "rule__Transition__Group__8"


    // $ANTLR start "rule__Transition__Group__8__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1683:1: rule__Transition__Group__8__Impl : ( '}' ) ;
    public final void rule__Transition__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1687:1: ( ( '}' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1688:1: ( '}' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1688:1: ( '}' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1689:1: '}'
            {
             before(grammarAccess.getTransitionAccess().getRightCurlyBracketKeyword_8()); 
            match(input,19,FOLLOW_19_in_rule__Transition__Group__8__Impl3468); 
             after(grammarAccess.getTransitionAccess().getRightCurlyBracketKeyword_8()); 

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
    // $ANTLR end "rule__Transition__Group__8__Impl"


    // $ANTLR start "rule__Transition__Group_5__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1720:1: rule__Transition__Group_5__0 : rule__Transition__Group_5__0__Impl rule__Transition__Group_5__1 ;
    public final void rule__Transition__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1724:1: ( rule__Transition__Group_5__0__Impl rule__Transition__Group_5__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1725:2: rule__Transition__Group_5__0__Impl rule__Transition__Group_5__1
            {
            pushFollow(FOLLOW_rule__Transition__Group_5__0__Impl_in_rule__Transition__Group_5__03517);
            rule__Transition__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Transition__Group_5__1_in_rule__Transition__Group_5__03520);
            rule__Transition__Group_5__1();

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
    // $ANTLR end "rule__Transition__Group_5__0"


    // $ANTLR start "rule__Transition__Group_5__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1732:1: rule__Transition__Group_5__0__Impl : ( 'label' ) ;
    public final void rule__Transition__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1736:1: ( ( 'label' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1737:1: ( 'label' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1737:1: ( 'label' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1738:1: 'label'
            {
             before(grammarAccess.getTransitionAccess().getLabelKeyword_5_0()); 
            match(input,25,FOLLOW_25_in_rule__Transition__Group_5__0__Impl3548); 
             after(grammarAccess.getTransitionAccess().getLabelKeyword_5_0()); 

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
    // $ANTLR end "rule__Transition__Group_5__0__Impl"


    // $ANTLR start "rule__Transition__Group_5__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1751:1: rule__Transition__Group_5__1 : rule__Transition__Group_5__1__Impl ;
    public final void rule__Transition__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1755:1: ( rule__Transition__Group_5__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1756:2: rule__Transition__Group_5__1__Impl
            {
            pushFollow(FOLLOW_rule__Transition__Group_5__1__Impl_in_rule__Transition__Group_5__13579);
            rule__Transition__Group_5__1__Impl();

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
    // $ANTLR end "rule__Transition__Group_5__1"


    // $ANTLR start "rule__Transition__Group_5__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1762:1: rule__Transition__Group_5__1__Impl : ( ( rule__Transition__LabelAssignment_5_1 ) ) ;
    public final void rule__Transition__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1766:1: ( ( ( rule__Transition__LabelAssignment_5_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1767:1: ( ( rule__Transition__LabelAssignment_5_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1767:1: ( ( rule__Transition__LabelAssignment_5_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1768:1: ( rule__Transition__LabelAssignment_5_1 )
            {
             before(grammarAccess.getTransitionAccess().getLabelAssignment_5_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1769:1: ( rule__Transition__LabelAssignment_5_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1769:2: rule__Transition__LabelAssignment_5_1
            {
            pushFollow(FOLLOW_rule__Transition__LabelAssignment_5_1_in_rule__Transition__Group_5__1__Impl3606);
            rule__Transition__LabelAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getTransitionAccess().getLabelAssignment_5_1()); 

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
    // $ANTLR end "rule__Transition__Group_5__1__Impl"


    // $ANTLR start "rule__Assignment__Group_0__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1783:1: rule__Assignment__Group_0__0 : rule__Assignment__Group_0__0__Impl rule__Assignment__Group_0__1 ;
    public final void rule__Assignment__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1787:1: ( rule__Assignment__Group_0__0__Impl rule__Assignment__Group_0__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1788:2: rule__Assignment__Group_0__0__Impl rule__Assignment__Group_0__1
            {
            pushFollow(FOLLOW_rule__Assignment__Group_0__0__Impl_in_rule__Assignment__Group_0__03640);
            rule__Assignment__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group_0__1_in_rule__Assignment__Group_0__03643);
            rule__Assignment__Group_0__1();

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
    // $ANTLR end "rule__Assignment__Group_0__0"


    // $ANTLR start "rule__Assignment__Group_0__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1795:1: rule__Assignment__Group_0__0__Impl : ( ( rule__Assignment__VarAssignment_0_0 ) ) ;
    public final void rule__Assignment__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1799:1: ( ( ( rule__Assignment__VarAssignment_0_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1800:1: ( ( rule__Assignment__VarAssignment_0_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1800:1: ( ( rule__Assignment__VarAssignment_0_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1801:1: ( rule__Assignment__VarAssignment_0_0 )
            {
             before(grammarAccess.getAssignmentAccess().getVarAssignment_0_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1802:1: ( rule__Assignment__VarAssignment_0_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1802:2: rule__Assignment__VarAssignment_0_0
            {
            pushFollow(FOLLOW_rule__Assignment__VarAssignment_0_0_in_rule__Assignment__Group_0__0__Impl3670);
            rule__Assignment__VarAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getAssignmentAccess().getVarAssignment_0_0()); 

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
    // $ANTLR end "rule__Assignment__Group_0__0__Impl"


    // $ANTLR start "rule__Assignment__Group_0__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1812:1: rule__Assignment__Group_0__1 : rule__Assignment__Group_0__1__Impl rule__Assignment__Group_0__2 ;
    public final void rule__Assignment__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1816:1: ( rule__Assignment__Group_0__1__Impl rule__Assignment__Group_0__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1817:2: rule__Assignment__Group_0__1__Impl rule__Assignment__Group_0__2
            {
            pushFollow(FOLLOW_rule__Assignment__Group_0__1__Impl_in_rule__Assignment__Group_0__13700);
            rule__Assignment__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group_0__2_in_rule__Assignment__Group_0__13703);
            rule__Assignment__Group_0__2();

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
    // $ANTLR end "rule__Assignment__Group_0__1"


    // $ANTLR start "rule__Assignment__Group_0__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1824:1: rule__Assignment__Group_0__1__Impl : ( '=' ) ;
    public final void rule__Assignment__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1828:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1829:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1829:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1830:1: '='
            {
             before(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_0_1()); 
            match(input,20,FOLLOW_20_in_rule__Assignment__Group_0__1__Impl3731); 
             after(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_0_1()); 

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
    // $ANTLR end "rule__Assignment__Group_0__1__Impl"


    // $ANTLR start "rule__Assignment__Group_0__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1843:1: rule__Assignment__Group_0__2 : rule__Assignment__Group_0__2__Impl rule__Assignment__Group_0__3 ;
    public final void rule__Assignment__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1847:1: ( rule__Assignment__Group_0__2__Impl rule__Assignment__Group_0__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1848:2: rule__Assignment__Group_0__2__Impl rule__Assignment__Group_0__3
            {
            pushFollow(FOLLOW_rule__Assignment__Group_0__2__Impl_in_rule__Assignment__Group_0__23762);
            rule__Assignment__Group_0__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group_0__3_in_rule__Assignment__Group_0__23765);
            rule__Assignment__Group_0__3();

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
    // $ANTLR end "rule__Assignment__Group_0__2"


    // $ANTLR start "rule__Assignment__Group_0__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1855:1: rule__Assignment__Group_0__2__Impl : ( ( rule__Assignment__ExprAssignment_0_2 ) ) ;
    public final void rule__Assignment__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1859:1: ( ( ( rule__Assignment__ExprAssignment_0_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1860:1: ( ( rule__Assignment__ExprAssignment_0_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1860:1: ( ( rule__Assignment__ExprAssignment_0_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1861:1: ( rule__Assignment__ExprAssignment_0_2 )
            {
             before(grammarAccess.getAssignmentAccess().getExprAssignment_0_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1862:1: ( rule__Assignment__ExprAssignment_0_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1862:2: rule__Assignment__ExprAssignment_0_2
            {
            pushFollow(FOLLOW_rule__Assignment__ExprAssignment_0_2_in_rule__Assignment__Group_0__2__Impl3792);
            rule__Assignment__ExprAssignment_0_2();

            state._fsp--;


            }

             after(grammarAccess.getAssignmentAccess().getExprAssignment_0_2()); 

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
    // $ANTLR end "rule__Assignment__Group_0__2__Impl"


    // $ANTLR start "rule__Assignment__Group_0__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1872:1: rule__Assignment__Group_0__3 : rule__Assignment__Group_0__3__Impl ;
    public final void rule__Assignment__Group_0__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1876:1: ( rule__Assignment__Group_0__3__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1877:2: rule__Assignment__Group_0__3__Impl
            {
            pushFollow(FOLLOW_rule__Assignment__Group_0__3__Impl_in_rule__Assignment__Group_0__33822);
            rule__Assignment__Group_0__3__Impl();

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
    // $ANTLR end "rule__Assignment__Group_0__3"


    // $ANTLR start "rule__Assignment__Group_0__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1883:1: rule__Assignment__Group_0__3__Impl : ( ';' ) ;
    public final void rule__Assignment__Group_0__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1887:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1888:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1888:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1889:1: ';'
            {
             before(grammarAccess.getAssignmentAccess().getSemicolonKeyword_0_3()); 
            match(input,21,FOLLOW_21_in_rule__Assignment__Group_0__3__Impl3850); 
             after(grammarAccess.getAssignmentAccess().getSemicolonKeyword_0_3()); 

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
    // $ANTLR end "rule__Assignment__Group_0__3__Impl"


    // $ANTLR start "rule__Assignment__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1910:1: rule__Assignment__Group_1__0 : rule__Assignment__Group_1__0__Impl rule__Assignment__Group_1__1 ;
    public final void rule__Assignment__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1914:1: ( rule__Assignment__Group_1__0__Impl rule__Assignment__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1915:2: rule__Assignment__Group_1__0__Impl rule__Assignment__Group_1__1
            {
            pushFollow(FOLLOW_rule__Assignment__Group_1__0__Impl_in_rule__Assignment__Group_1__03889);
            rule__Assignment__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group_1__1_in_rule__Assignment__Group_1__03892);
            rule__Assignment__Group_1__1();

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
    // $ANTLR end "rule__Assignment__Group_1__0"


    // $ANTLR start "rule__Assignment__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1922:1: rule__Assignment__Group_1__0__Impl : ( ( rule__Assignment__AVarAssignment_1_0 ) ) ;
    public final void rule__Assignment__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1926:1: ( ( ( rule__Assignment__AVarAssignment_1_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1927:1: ( ( rule__Assignment__AVarAssignment_1_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1927:1: ( ( rule__Assignment__AVarAssignment_1_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1928:1: ( rule__Assignment__AVarAssignment_1_0 )
            {
             before(grammarAccess.getAssignmentAccess().getAVarAssignment_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1929:1: ( rule__Assignment__AVarAssignment_1_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1929:2: rule__Assignment__AVarAssignment_1_0
            {
            pushFollow(FOLLOW_rule__Assignment__AVarAssignment_1_0_in_rule__Assignment__Group_1__0__Impl3919);
            rule__Assignment__AVarAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getAssignmentAccess().getAVarAssignment_1_0()); 

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
    // $ANTLR end "rule__Assignment__Group_1__0__Impl"


    // $ANTLR start "rule__Assignment__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1939:1: rule__Assignment__Group_1__1 : rule__Assignment__Group_1__1__Impl rule__Assignment__Group_1__2 ;
    public final void rule__Assignment__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1943:1: ( rule__Assignment__Group_1__1__Impl rule__Assignment__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1944:2: rule__Assignment__Group_1__1__Impl rule__Assignment__Group_1__2
            {
            pushFollow(FOLLOW_rule__Assignment__Group_1__1__Impl_in_rule__Assignment__Group_1__13949);
            rule__Assignment__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group_1__2_in_rule__Assignment__Group_1__13952);
            rule__Assignment__Group_1__2();

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
    // $ANTLR end "rule__Assignment__Group_1__1"


    // $ANTLR start "rule__Assignment__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1951:1: rule__Assignment__Group_1__1__Impl : ( '[' ) ;
    public final void rule__Assignment__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1955:1: ( ( '[' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1956:1: ( '[' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1956:1: ( '[' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1957:1: '['
            {
             before(grammarAccess.getAssignmentAccess().getLeftSquareBracketKeyword_1_1()); 
            match(input,22,FOLLOW_22_in_rule__Assignment__Group_1__1__Impl3980); 
             after(grammarAccess.getAssignmentAccess().getLeftSquareBracketKeyword_1_1()); 

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
    // $ANTLR end "rule__Assignment__Group_1__1__Impl"


    // $ANTLR start "rule__Assignment__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1970:1: rule__Assignment__Group_1__2 : rule__Assignment__Group_1__2__Impl rule__Assignment__Group_1__3 ;
    public final void rule__Assignment__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1974:1: ( rule__Assignment__Group_1__2__Impl rule__Assignment__Group_1__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1975:2: rule__Assignment__Group_1__2__Impl rule__Assignment__Group_1__3
            {
            pushFollow(FOLLOW_rule__Assignment__Group_1__2__Impl_in_rule__Assignment__Group_1__24011);
            rule__Assignment__Group_1__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group_1__3_in_rule__Assignment__Group_1__24014);
            rule__Assignment__Group_1__3();

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
    // $ANTLR end "rule__Assignment__Group_1__2"


    // $ANTLR start "rule__Assignment__Group_1__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1982:1: rule__Assignment__Group_1__2__Impl : ( ( rule__Assignment__IndexAssignment_1_2 ) ) ;
    public final void rule__Assignment__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1986:1: ( ( ( rule__Assignment__IndexAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1987:1: ( ( rule__Assignment__IndexAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1987:1: ( ( rule__Assignment__IndexAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1988:1: ( rule__Assignment__IndexAssignment_1_2 )
            {
             before(grammarAccess.getAssignmentAccess().getIndexAssignment_1_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1989:1: ( rule__Assignment__IndexAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1989:2: rule__Assignment__IndexAssignment_1_2
            {
            pushFollow(FOLLOW_rule__Assignment__IndexAssignment_1_2_in_rule__Assignment__Group_1__2__Impl4041);
            rule__Assignment__IndexAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getAssignmentAccess().getIndexAssignment_1_2()); 

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
    // $ANTLR end "rule__Assignment__Group_1__2__Impl"


    // $ANTLR start "rule__Assignment__Group_1__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1999:1: rule__Assignment__Group_1__3 : rule__Assignment__Group_1__3__Impl rule__Assignment__Group_1__4 ;
    public final void rule__Assignment__Group_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2003:1: ( rule__Assignment__Group_1__3__Impl rule__Assignment__Group_1__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2004:2: rule__Assignment__Group_1__3__Impl rule__Assignment__Group_1__4
            {
            pushFollow(FOLLOW_rule__Assignment__Group_1__3__Impl_in_rule__Assignment__Group_1__34071);
            rule__Assignment__Group_1__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group_1__4_in_rule__Assignment__Group_1__34074);
            rule__Assignment__Group_1__4();

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
    // $ANTLR end "rule__Assignment__Group_1__3"


    // $ANTLR start "rule__Assignment__Group_1__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2011:1: rule__Assignment__Group_1__3__Impl : ( ']' ) ;
    public final void rule__Assignment__Group_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2015:1: ( ( ']' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2016:1: ( ']' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2016:1: ( ']' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2017:1: ']'
            {
             before(grammarAccess.getAssignmentAccess().getRightSquareBracketKeyword_1_3()); 
            match(input,23,FOLLOW_23_in_rule__Assignment__Group_1__3__Impl4102); 
             after(grammarAccess.getAssignmentAccess().getRightSquareBracketKeyword_1_3()); 

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
    // $ANTLR end "rule__Assignment__Group_1__3__Impl"


    // $ANTLR start "rule__Assignment__Group_1__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2030:1: rule__Assignment__Group_1__4 : rule__Assignment__Group_1__4__Impl rule__Assignment__Group_1__5 ;
    public final void rule__Assignment__Group_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2034:1: ( rule__Assignment__Group_1__4__Impl rule__Assignment__Group_1__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2035:2: rule__Assignment__Group_1__4__Impl rule__Assignment__Group_1__5
            {
            pushFollow(FOLLOW_rule__Assignment__Group_1__4__Impl_in_rule__Assignment__Group_1__44133);
            rule__Assignment__Group_1__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group_1__5_in_rule__Assignment__Group_1__44136);
            rule__Assignment__Group_1__5();

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
    // $ANTLR end "rule__Assignment__Group_1__4"


    // $ANTLR start "rule__Assignment__Group_1__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2042:1: rule__Assignment__Group_1__4__Impl : ( '=' ) ;
    public final void rule__Assignment__Group_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2046:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2047:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2047:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2048:1: '='
            {
             before(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1_4()); 
            match(input,20,FOLLOW_20_in_rule__Assignment__Group_1__4__Impl4164); 
             after(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1_4()); 

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
    // $ANTLR end "rule__Assignment__Group_1__4__Impl"


    // $ANTLR start "rule__Assignment__Group_1__5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2061:1: rule__Assignment__Group_1__5 : rule__Assignment__Group_1__5__Impl rule__Assignment__Group_1__6 ;
    public final void rule__Assignment__Group_1__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2065:1: ( rule__Assignment__Group_1__5__Impl rule__Assignment__Group_1__6 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2066:2: rule__Assignment__Group_1__5__Impl rule__Assignment__Group_1__6
            {
            pushFollow(FOLLOW_rule__Assignment__Group_1__5__Impl_in_rule__Assignment__Group_1__54195);
            rule__Assignment__Group_1__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Assignment__Group_1__6_in_rule__Assignment__Group_1__54198);
            rule__Assignment__Group_1__6();

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
    // $ANTLR end "rule__Assignment__Group_1__5"


    // $ANTLR start "rule__Assignment__Group_1__5__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2073:1: rule__Assignment__Group_1__5__Impl : ( ( rule__Assignment__ExprAssignment_1_5 ) ) ;
    public final void rule__Assignment__Group_1__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2077:1: ( ( ( rule__Assignment__ExprAssignment_1_5 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2078:1: ( ( rule__Assignment__ExprAssignment_1_5 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2078:1: ( ( rule__Assignment__ExprAssignment_1_5 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2079:1: ( rule__Assignment__ExprAssignment_1_5 )
            {
             before(grammarAccess.getAssignmentAccess().getExprAssignment_1_5()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2080:1: ( rule__Assignment__ExprAssignment_1_5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2080:2: rule__Assignment__ExprAssignment_1_5
            {
            pushFollow(FOLLOW_rule__Assignment__ExprAssignment_1_5_in_rule__Assignment__Group_1__5__Impl4225);
            rule__Assignment__ExprAssignment_1_5();

            state._fsp--;


            }

             after(grammarAccess.getAssignmentAccess().getExprAssignment_1_5()); 

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
    // $ANTLR end "rule__Assignment__Group_1__5__Impl"


    // $ANTLR start "rule__Assignment__Group_1__6"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2090:1: rule__Assignment__Group_1__6 : rule__Assignment__Group_1__6__Impl ;
    public final void rule__Assignment__Group_1__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2094:1: ( rule__Assignment__Group_1__6__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2095:2: rule__Assignment__Group_1__6__Impl
            {
            pushFollow(FOLLOW_rule__Assignment__Group_1__6__Impl_in_rule__Assignment__Group_1__64255);
            rule__Assignment__Group_1__6__Impl();

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
    // $ANTLR end "rule__Assignment__Group_1__6"


    // $ANTLR start "rule__Assignment__Group_1__6__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2101:1: rule__Assignment__Group_1__6__Impl : ( ';' ) ;
    public final void rule__Assignment__Group_1__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2105:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2106:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2106:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2107:1: ';'
            {
             before(grammarAccess.getAssignmentAccess().getSemicolonKeyword_1_6()); 
            match(input,21,FOLLOW_21_in_rule__Assignment__Group_1__6__Impl4283); 
             after(grammarAccess.getAssignmentAccess().getSemicolonKeyword_1_6()); 

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
    // $ANTLR end "rule__Assignment__Group_1__6__Impl"


    // $ANTLR start "rule__Addition__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2134:1: rule__Addition__Group__0 : rule__Addition__Group__0__Impl rule__Addition__Group__1 ;
    public final void rule__Addition__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2138:1: ( rule__Addition__Group__0__Impl rule__Addition__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2139:2: rule__Addition__Group__0__Impl rule__Addition__Group__1
            {
            pushFollow(FOLLOW_rule__Addition__Group__0__Impl_in_rule__Addition__Group__04328);
            rule__Addition__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Addition__Group__1_in_rule__Addition__Group__04331);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2146:1: rule__Addition__Group__0__Impl : ( ruleMultiplication ) ;
    public final void rule__Addition__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2150:1: ( ( ruleMultiplication ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2151:1: ( ruleMultiplication )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2151:1: ( ruleMultiplication )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2152:1: ruleMultiplication
            {
             before(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleMultiplication_in_rule__Addition__Group__0__Impl4358);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2163:1: rule__Addition__Group__1 : rule__Addition__Group__1__Impl ;
    public final void rule__Addition__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2167:1: ( rule__Addition__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2168:2: rule__Addition__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Addition__Group__1__Impl_in_rule__Addition__Group__14387);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2174:1: rule__Addition__Group__1__Impl : ( ( rule__Addition__Group_1__0 )* ) ;
    public final void rule__Addition__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2178:1: ( ( ( rule__Addition__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2179:1: ( ( rule__Addition__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2179:1: ( ( rule__Addition__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2180:1: ( rule__Addition__Group_1__0 )*
            {
             before(grammarAccess.getAdditionAccess().getGroup_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2181:1: ( rule__Addition__Group_1__0 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>=26 && LA14_0<=27)) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2181:2: rule__Addition__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Addition__Group_1__0_in_rule__Addition__Group__1__Impl4414);
            	    rule__Addition__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2195:1: rule__Addition__Group_1__0 : rule__Addition__Group_1__0__Impl rule__Addition__Group_1__1 ;
    public final void rule__Addition__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2199:1: ( rule__Addition__Group_1__0__Impl rule__Addition__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2200:2: rule__Addition__Group_1__0__Impl rule__Addition__Group_1__1
            {
            pushFollow(FOLLOW_rule__Addition__Group_1__0__Impl_in_rule__Addition__Group_1__04449);
            rule__Addition__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Addition__Group_1__1_in_rule__Addition__Group_1__04452);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2207:1: rule__Addition__Group_1__0__Impl : ( ( rule__Addition__Alternatives_1_0 ) ) ;
    public final void rule__Addition__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2211:1: ( ( ( rule__Addition__Alternatives_1_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2212:1: ( ( rule__Addition__Alternatives_1_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2212:1: ( ( rule__Addition__Alternatives_1_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2213:1: ( rule__Addition__Alternatives_1_0 )
            {
             before(grammarAccess.getAdditionAccess().getAlternatives_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2214:1: ( rule__Addition__Alternatives_1_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2214:2: rule__Addition__Alternatives_1_0
            {
            pushFollow(FOLLOW_rule__Addition__Alternatives_1_0_in_rule__Addition__Group_1__0__Impl4479);
            rule__Addition__Alternatives_1_0();

            state._fsp--;


            }

             after(grammarAccess.getAdditionAccess().getAlternatives_1_0()); 

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
    // $ANTLR end "rule__Addition__Group_1__0__Impl"


    // $ANTLR start "rule__Addition__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2224:1: rule__Addition__Group_1__1 : rule__Addition__Group_1__1__Impl ;
    public final void rule__Addition__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2228:1: ( rule__Addition__Group_1__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2229:2: rule__Addition__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Addition__Group_1__1__Impl_in_rule__Addition__Group_1__14509);
            rule__Addition__Group_1__1__Impl();

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2235:1: rule__Addition__Group_1__1__Impl : ( ( rule__Addition__RightAssignment_1_1 ) ) ;
    public final void rule__Addition__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2239:1: ( ( ( rule__Addition__RightAssignment_1_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2240:1: ( ( rule__Addition__RightAssignment_1_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2240:1: ( ( rule__Addition__RightAssignment_1_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2241:1: ( rule__Addition__RightAssignment_1_1 )
            {
             before(grammarAccess.getAdditionAccess().getRightAssignment_1_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2242:1: ( rule__Addition__RightAssignment_1_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2242:2: rule__Addition__RightAssignment_1_1
            {
            pushFollow(FOLLOW_rule__Addition__RightAssignment_1_1_in_rule__Addition__Group_1__1__Impl4536);
            rule__Addition__RightAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getAdditionAccess().getRightAssignment_1_1()); 

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


    // $ANTLR start "rule__Addition__Group_1_0_0__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2256:1: rule__Addition__Group_1_0_0__0 : rule__Addition__Group_1_0_0__0__Impl rule__Addition__Group_1_0_0__1 ;
    public final void rule__Addition__Group_1_0_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2260:1: ( rule__Addition__Group_1_0_0__0__Impl rule__Addition__Group_1_0_0__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2261:2: rule__Addition__Group_1_0_0__0__Impl rule__Addition__Group_1_0_0__1
            {
            pushFollow(FOLLOW_rule__Addition__Group_1_0_0__0__Impl_in_rule__Addition__Group_1_0_0__04570);
            rule__Addition__Group_1_0_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Addition__Group_1_0_0__1_in_rule__Addition__Group_1_0_0__04573);
            rule__Addition__Group_1_0_0__1();

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
    // $ANTLR end "rule__Addition__Group_1_0_0__0"


    // $ANTLR start "rule__Addition__Group_1_0_0__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2268:1: rule__Addition__Group_1_0_0__0__Impl : ( () ) ;
    public final void rule__Addition__Group_1_0_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2272:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2273:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2273:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2274:1: ()
            {
             before(grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2275:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2277:1: 
            {
            }

             after(grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group_1_0_0__0__Impl"


    // $ANTLR start "rule__Addition__Group_1_0_0__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2287:1: rule__Addition__Group_1_0_0__1 : rule__Addition__Group_1_0_0__1__Impl ;
    public final void rule__Addition__Group_1_0_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2291:1: ( rule__Addition__Group_1_0_0__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2292:2: rule__Addition__Group_1_0_0__1__Impl
            {
            pushFollow(FOLLOW_rule__Addition__Group_1_0_0__1__Impl_in_rule__Addition__Group_1_0_0__14631);
            rule__Addition__Group_1_0_0__1__Impl();

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
    // $ANTLR end "rule__Addition__Group_1_0_0__1"


    // $ANTLR start "rule__Addition__Group_1_0_0__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2298:1: rule__Addition__Group_1_0_0__1__Impl : ( '+' ) ;
    public final void rule__Addition__Group_1_0_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2302:1: ( ( '+' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2303:1: ( '+' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2303:1: ( '+' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2304:1: '+'
            {
             before(grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_0_1()); 
            match(input,26,FOLLOW_26_in_rule__Addition__Group_1_0_0__1__Impl4659); 
             after(grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_0_1()); 

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
    // $ANTLR end "rule__Addition__Group_1_0_0__1__Impl"


    // $ANTLR start "rule__Addition__Group_1_0_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2321:1: rule__Addition__Group_1_0_1__0 : rule__Addition__Group_1_0_1__0__Impl rule__Addition__Group_1_0_1__1 ;
    public final void rule__Addition__Group_1_0_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2325:1: ( rule__Addition__Group_1_0_1__0__Impl rule__Addition__Group_1_0_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2326:2: rule__Addition__Group_1_0_1__0__Impl rule__Addition__Group_1_0_1__1
            {
            pushFollow(FOLLOW_rule__Addition__Group_1_0_1__0__Impl_in_rule__Addition__Group_1_0_1__04694);
            rule__Addition__Group_1_0_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Addition__Group_1_0_1__1_in_rule__Addition__Group_1_0_1__04697);
            rule__Addition__Group_1_0_1__1();

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
    // $ANTLR end "rule__Addition__Group_1_0_1__0"


    // $ANTLR start "rule__Addition__Group_1_0_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2333:1: rule__Addition__Group_1_0_1__0__Impl : ( () ) ;
    public final void rule__Addition__Group_1_0_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2337:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2338:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2338:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2339:1: ()
            {
             before(grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2340:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2342:1: 
            {
            }

             after(grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Addition__Group_1_0_1__0__Impl"


    // $ANTLR start "rule__Addition__Group_1_0_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2352:1: rule__Addition__Group_1_0_1__1 : rule__Addition__Group_1_0_1__1__Impl ;
    public final void rule__Addition__Group_1_0_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2356:1: ( rule__Addition__Group_1_0_1__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2357:2: rule__Addition__Group_1_0_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Addition__Group_1_0_1__1__Impl_in_rule__Addition__Group_1_0_1__14755);
            rule__Addition__Group_1_0_1__1__Impl();

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
    // $ANTLR end "rule__Addition__Group_1_0_1__1"


    // $ANTLR start "rule__Addition__Group_1_0_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2363:1: rule__Addition__Group_1_0_1__1__Impl : ( '-' ) ;
    public final void rule__Addition__Group_1_0_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2367:1: ( ( '-' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2368:1: ( '-' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2368:1: ( '-' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2369:1: '-'
            {
             before(grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_0_1_1()); 
            match(input,27,FOLLOW_27_in_rule__Addition__Group_1_0_1__1__Impl4783); 
             after(grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_0_1_1()); 

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
    // $ANTLR end "rule__Addition__Group_1_0_1__1__Impl"


    // $ANTLR start "rule__Multiplication__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2386:1: rule__Multiplication__Group__0 : rule__Multiplication__Group__0__Impl rule__Multiplication__Group__1 ;
    public final void rule__Multiplication__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2390:1: ( rule__Multiplication__Group__0__Impl rule__Multiplication__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2391:2: rule__Multiplication__Group__0__Impl rule__Multiplication__Group__1
            {
            pushFollow(FOLLOW_rule__Multiplication__Group__0__Impl_in_rule__Multiplication__Group__04818);
            rule__Multiplication__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Multiplication__Group__1_in_rule__Multiplication__Group__04821);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2398:1: rule__Multiplication__Group__0__Impl : ( ruleUnitaryMinus ) ;
    public final void rule__Multiplication__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2402:1: ( ( ruleUnitaryMinus ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2403:1: ( ruleUnitaryMinus )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2403:1: ( ruleUnitaryMinus )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2404:1: ruleUnitaryMinus
            {
             before(grammarAccess.getMultiplicationAccess().getUnitaryMinusParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleUnitaryMinus_in_rule__Multiplication__Group__0__Impl4848);
            ruleUnitaryMinus();

            state._fsp--;

             after(grammarAccess.getMultiplicationAccess().getUnitaryMinusParserRuleCall_0()); 

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2415:1: rule__Multiplication__Group__1 : rule__Multiplication__Group__1__Impl ;
    public final void rule__Multiplication__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2419:1: ( rule__Multiplication__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2420:2: rule__Multiplication__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Multiplication__Group__1__Impl_in_rule__Multiplication__Group__14877);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2426:1: rule__Multiplication__Group__1__Impl : ( ( rule__Multiplication__Group_1__0 )* ) ;
    public final void rule__Multiplication__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2430:1: ( ( ( rule__Multiplication__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2431:1: ( ( rule__Multiplication__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2431:1: ( ( rule__Multiplication__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2432:1: ( rule__Multiplication__Group_1__0 )*
            {
             before(grammarAccess.getMultiplicationAccess().getGroup_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2433:1: ( rule__Multiplication__Group_1__0 )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>=28 && LA15_0<=30)) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2433:2: rule__Multiplication__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Multiplication__Group_1__0_in_rule__Multiplication__Group__1__Impl4904);
            	    rule__Multiplication__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2447:1: rule__Multiplication__Group_1__0 : rule__Multiplication__Group_1__0__Impl rule__Multiplication__Group_1__1 ;
    public final void rule__Multiplication__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2451:1: ( rule__Multiplication__Group_1__0__Impl rule__Multiplication__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2452:2: rule__Multiplication__Group_1__0__Impl rule__Multiplication__Group_1__1
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1__0__Impl_in_rule__Multiplication__Group_1__04939);
            rule__Multiplication__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Multiplication__Group_1__1_in_rule__Multiplication__Group_1__04942);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2459:1: rule__Multiplication__Group_1__0__Impl : ( ( rule__Multiplication__Alternatives_1_0 ) ) ;
    public final void rule__Multiplication__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2463:1: ( ( ( rule__Multiplication__Alternatives_1_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2464:1: ( ( rule__Multiplication__Alternatives_1_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2464:1: ( ( rule__Multiplication__Alternatives_1_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2465:1: ( rule__Multiplication__Alternatives_1_0 )
            {
             before(grammarAccess.getMultiplicationAccess().getAlternatives_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2466:1: ( rule__Multiplication__Alternatives_1_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2466:2: rule__Multiplication__Alternatives_1_0
            {
            pushFollow(FOLLOW_rule__Multiplication__Alternatives_1_0_in_rule__Multiplication__Group_1__0__Impl4969);
            rule__Multiplication__Alternatives_1_0();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicationAccess().getAlternatives_1_0()); 

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
    // $ANTLR end "rule__Multiplication__Group_1__0__Impl"


    // $ANTLR start "rule__Multiplication__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2476:1: rule__Multiplication__Group_1__1 : rule__Multiplication__Group_1__1__Impl ;
    public final void rule__Multiplication__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2480:1: ( rule__Multiplication__Group_1__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2481:2: rule__Multiplication__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1__1__Impl_in_rule__Multiplication__Group_1__14999);
            rule__Multiplication__Group_1__1__Impl();

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2487:1: rule__Multiplication__Group_1__1__Impl : ( ( rule__Multiplication__RightAssignment_1_1 ) ) ;
    public final void rule__Multiplication__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2491:1: ( ( ( rule__Multiplication__RightAssignment_1_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2492:1: ( ( rule__Multiplication__RightAssignment_1_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2492:1: ( ( rule__Multiplication__RightAssignment_1_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2493:1: ( rule__Multiplication__RightAssignment_1_1 )
            {
             before(grammarAccess.getMultiplicationAccess().getRightAssignment_1_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2494:1: ( rule__Multiplication__RightAssignment_1_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2494:2: rule__Multiplication__RightAssignment_1_1
            {
            pushFollow(FOLLOW_rule__Multiplication__RightAssignment_1_1_in_rule__Multiplication__Group_1__1__Impl5026);
            rule__Multiplication__RightAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getMultiplicationAccess().getRightAssignment_1_1()); 

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


    // $ANTLR start "rule__Multiplication__Group_1_0_0__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2508:1: rule__Multiplication__Group_1_0_0__0 : rule__Multiplication__Group_1_0_0__0__Impl rule__Multiplication__Group_1_0_0__1 ;
    public final void rule__Multiplication__Group_1_0_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2512:1: ( rule__Multiplication__Group_1_0_0__0__Impl rule__Multiplication__Group_1_0_0__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2513:2: rule__Multiplication__Group_1_0_0__0__Impl rule__Multiplication__Group_1_0_0__1
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1_0_0__0__Impl_in_rule__Multiplication__Group_1_0_0__05060);
            rule__Multiplication__Group_1_0_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Multiplication__Group_1_0_0__1_in_rule__Multiplication__Group_1_0_0__05063);
            rule__Multiplication__Group_1_0_0__1();

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
    // $ANTLR end "rule__Multiplication__Group_1_0_0__0"


    // $ANTLR start "rule__Multiplication__Group_1_0_0__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2520:1: rule__Multiplication__Group_1_0_0__0__Impl : ( () ) ;
    public final void rule__Multiplication__Group_1_0_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2524:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2525:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2525:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2526:1: ()
            {
             before(grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0_0_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2527:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2529:1: 
            {
            }

             after(grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0_0_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group_1_0_0__0__Impl"


    // $ANTLR start "rule__Multiplication__Group_1_0_0__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2539:1: rule__Multiplication__Group_1_0_0__1 : rule__Multiplication__Group_1_0_0__1__Impl ;
    public final void rule__Multiplication__Group_1_0_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2543:1: ( rule__Multiplication__Group_1_0_0__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2544:2: rule__Multiplication__Group_1_0_0__1__Impl
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1_0_0__1__Impl_in_rule__Multiplication__Group_1_0_0__15121);
            rule__Multiplication__Group_1_0_0__1__Impl();

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
    // $ANTLR end "rule__Multiplication__Group_1_0_0__1"


    // $ANTLR start "rule__Multiplication__Group_1_0_0__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2550:1: rule__Multiplication__Group_1_0_0__1__Impl : ( '*' ) ;
    public final void rule__Multiplication__Group_1_0_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2554:1: ( ( '*' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2555:1: ( '*' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2555:1: ( '*' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2556:1: '*'
            {
             before(grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_0_1()); 
            match(input,28,FOLLOW_28_in_rule__Multiplication__Group_1_0_0__1__Impl5149); 
             after(grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_0_1()); 

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
    // $ANTLR end "rule__Multiplication__Group_1_0_0__1__Impl"


    // $ANTLR start "rule__Multiplication__Group_1_0_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2573:1: rule__Multiplication__Group_1_0_1__0 : rule__Multiplication__Group_1_0_1__0__Impl rule__Multiplication__Group_1_0_1__1 ;
    public final void rule__Multiplication__Group_1_0_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2577:1: ( rule__Multiplication__Group_1_0_1__0__Impl rule__Multiplication__Group_1_0_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2578:2: rule__Multiplication__Group_1_0_1__0__Impl rule__Multiplication__Group_1_0_1__1
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1_0_1__0__Impl_in_rule__Multiplication__Group_1_0_1__05184);
            rule__Multiplication__Group_1_0_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Multiplication__Group_1_0_1__1_in_rule__Multiplication__Group_1_0_1__05187);
            rule__Multiplication__Group_1_0_1__1();

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
    // $ANTLR end "rule__Multiplication__Group_1_0_1__0"


    // $ANTLR start "rule__Multiplication__Group_1_0_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2585:1: rule__Multiplication__Group_1_0_1__0__Impl : ( () ) ;
    public final void rule__Multiplication__Group_1_0_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2589:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2590:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2590:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2591:1: ()
            {
             before(grammarAccess.getMultiplicationAccess().getDivisionLeftAction_1_0_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2592:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2594:1: 
            {
            }

             after(grammarAccess.getMultiplicationAccess().getDivisionLeftAction_1_0_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group_1_0_1__0__Impl"


    // $ANTLR start "rule__Multiplication__Group_1_0_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2604:1: rule__Multiplication__Group_1_0_1__1 : rule__Multiplication__Group_1_0_1__1__Impl ;
    public final void rule__Multiplication__Group_1_0_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2608:1: ( rule__Multiplication__Group_1_0_1__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2609:2: rule__Multiplication__Group_1_0_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1_0_1__1__Impl_in_rule__Multiplication__Group_1_0_1__15245);
            rule__Multiplication__Group_1_0_1__1__Impl();

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
    // $ANTLR end "rule__Multiplication__Group_1_0_1__1"


    // $ANTLR start "rule__Multiplication__Group_1_0_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2615:1: rule__Multiplication__Group_1_0_1__1__Impl : ( '/' ) ;
    public final void rule__Multiplication__Group_1_0_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2619:1: ( ( '/' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2620:1: ( '/' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2620:1: ( '/' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2621:1: '/'
            {
             before(grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_0_1_1()); 
            match(input,29,FOLLOW_29_in_rule__Multiplication__Group_1_0_1__1__Impl5273); 
             after(grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_0_1_1()); 

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
    // $ANTLR end "rule__Multiplication__Group_1_0_1__1__Impl"


    // $ANTLR start "rule__Multiplication__Group_1_0_2__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2638:1: rule__Multiplication__Group_1_0_2__0 : rule__Multiplication__Group_1_0_2__0__Impl rule__Multiplication__Group_1_0_2__1 ;
    public final void rule__Multiplication__Group_1_0_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2642:1: ( rule__Multiplication__Group_1_0_2__0__Impl rule__Multiplication__Group_1_0_2__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2643:2: rule__Multiplication__Group_1_0_2__0__Impl rule__Multiplication__Group_1_0_2__1
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1_0_2__0__Impl_in_rule__Multiplication__Group_1_0_2__05308);
            rule__Multiplication__Group_1_0_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Multiplication__Group_1_0_2__1_in_rule__Multiplication__Group_1_0_2__05311);
            rule__Multiplication__Group_1_0_2__1();

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
    // $ANTLR end "rule__Multiplication__Group_1_0_2__0"


    // $ANTLR start "rule__Multiplication__Group_1_0_2__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2650:1: rule__Multiplication__Group_1_0_2__0__Impl : ( () ) ;
    public final void rule__Multiplication__Group_1_0_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2654:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2655:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2655:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2656:1: ()
            {
             before(grammarAccess.getMultiplicationAccess().getModuloLeftAction_1_0_2_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2657:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2659:1: 
            {
            }

             after(grammarAccess.getMultiplicationAccess().getModuloLeftAction_1_0_2_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multiplication__Group_1_0_2__0__Impl"


    // $ANTLR start "rule__Multiplication__Group_1_0_2__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2669:1: rule__Multiplication__Group_1_0_2__1 : rule__Multiplication__Group_1_0_2__1__Impl ;
    public final void rule__Multiplication__Group_1_0_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2673:1: ( rule__Multiplication__Group_1_0_2__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2674:2: rule__Multiplication__Group_1_0_2__1__Impl
            {
            pushFollow(FOLLOW_rule__Multiplication__Group_1_0_2__1__Impl_in_rule__Multiplication__Group_1_0_2__15369);
            rule__Multiplication__Group_1_0_2__1__Impl();

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
    // $ANTLR end "rule__Multiplication__Group_1_0_2__1"


    // $ANTLR start "rule__Multiplication__Group_1_0_2__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2680:1: rule__Multiplication__Group_1_0_2__1__Impl : ( '%' ) ;
    public final void rule__Multiplication__Group_1_0_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2684:1: ( ( '%' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2685:1: ( '%' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2685:1: ( '%' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2686:1: '%'
            {
             before(grammarAccess.getMultiplicationAccess().getPercentSignKeyword_1_0_2_1()); 
            match(input,30,FOLLOW_30_in_rule__Multiplication__Group_1_0_2__1__Impl5397); 
             after(grammarAccess.getMultiplicationAccess().getPercentSignKeyword_1_0_2_1()); 

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
    // $ANTLR end "rule__Multiplication__Group_1_0_2__1__Impl"


    // $ANTLR start "rule__UnitaryMinus__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2703:1: rule__UnitaryMinus__Group__0 : rule__UnitaryMinus__Group__0__Impl rule__UnitaryMinus__Group__1 ;
    public final void rule__UnitaryMinus__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2707:1: ( rule__UnitaryMinus__Group__0__Impl rule__UnitaryMinus__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2708:2: rule__UnitaryMinus__Group__0__Impl rule__UnitaryMinus__Group__1
            {
            pushFollow(FOLLOW_rule__UnitaryMinus__Group__0__Impl_in_rule__UnitaryMinus__Group__05432);
            rule__UnitaryMinus__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__UnitaryMinus__Group__1_in_rule__UnitaryMinus__Group__05435);
            rule__UnitaryMinus__Group__1();

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
    // $ANTLR end "rule__UnitaryMinus__Group__0"


    // $ANTLR start "rule__UnitaryMinus__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2715:1: rule__UnitaryMinus__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__UnitaryMinus__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2719:1: ( ( ( '-' )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2720:1: ( ( '-' )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2720:1: ( ( '-' )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2721:1: ( '-' )?
            {
             before(grammarAccess.getUnitaryMinusAccess().getHyphenMinusKeyword_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2722:1: ( '-' )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==27) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2723:2: '-'
                    {
                    match(input,27,FOLLOW_27_in_rule__UnitaryMinus__Group__0__Impl5464); 

                    }
                    break;

            }

             after(grammarAccess.getUnitaryMinusAccess().getHyphenMinusKeyword_0()); 

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
    // $ANTLR end "rule__UnitaryMinus__Group__0__Impl"


    // $ANTLR start "rule__UnitaryMinus__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2734:1: rule__UnitaryMinus__Group__1 : rule__UnitaryMinus__Group__1__Impl rule__UnitaryMinus__Group__2 ;
    public final void rule__UnitaryMinus__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2738:1: ( rule__UnitaryMinus__Group__1__Impl rule__UnitaryMinus__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2739:2: rule__UnitaryMinus__Group__1__Impl rule__UnitaryMinus__Group__2
            {
            pushFollow(FOLLOW_rule__UnitaryMinus__Group__1__Impl_in_rule__UnitaryMinus__Group__15497);
            rule__UnitaryMinus__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__UnitaryMinus__Group__2_in_rule__UnitaryMinus__Group__15500);
            rule__UnitaryMinus__Group__2();

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
    // $ANTLR end "rule__UnitaryMinus__Group__1"


    // $ANTLR start "rule__UnitaryMinus__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2746:1: rule__UnitaryMinus__Group__1__Impl : ( rulePower ) ;
    public final void rule__UnitaryMinus__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2750:1: ( ( rulePower ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2751:1: ( rulePower )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2751:1: ( rulePower )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2752:1: rulePower
            {
             before(grammarAccess.getUnitaryMinusAccess().getPowerParserRuleCall_1()); 
            pushFollow(FOLLOW_rulePower_in_rule__UnitaryMinus__Group__1__Impl5527);
            rulePower();

            state._fsp--;

             after(grammarAccess.getUnitaryMinusAccess().getPowerParserRuleCall_1()); 

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
    // $ANTLR end "rule__UnitaryMinus__Group__1__Impl"


    // $ANTLR start "rule__UnitaryMinus__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2763:1: rule__UnitaryMinus__Group__2 : rule__UnitaryMinus__Group__2__Impl ;
    public final void rule__UnitaryMinus__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2767:1: ( rule__UnitaryMinus__Group__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2768:2: rule__UnitaryMinus__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__UnitaryMinus__Group__2__Impl_in_rule__UnitaryMinus__Group__25556);
            rule__UnitaryMinus__Group__2__Impl();

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
    // $ANTLR end "rule__UnitaryMinus__Group__2"


    // $ANTLR start "rule__UnitaryMinus__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2774:1: rule__UnitaryMinus__Group__2__Impl : ( () ) ;
    public final void rule__UnitaryMinus__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2778:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2779:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2779:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2780:1: ()
            {
             before(grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2781:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2783:1: 
            {
            }

             after(grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnitaryMinus__Group__2__Impl"


    // $ANTLR start "rule__Power__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2799:1: rule__Power__Group__0 : rule__Power__Group__0__Impl rule__Power__Group__1 ;
    public final void rule__Power__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2803:1: ( rule__Power__Group__0__Impl rule__Power__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2804:2: rule__Power__Group__0__Impl rule__Power__Group__1
            {
            pushFollow(FOLLOW_rule__Power__Group__0__Impl_in_rule__Power__Group__05620);
            rule__Power__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Power__Group__1_in_rule__Power__Group__05623);
            rule__Power__Group__1();

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
    // $ANTLR end "rule__Power__Group__0"


    // $ANTLR start "rule__Power__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2811:1: rule__Power__Group__0__Impl : ( rulePrimary ) ;
    public final void rule__Power__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2815:1: ( ( rulePrimary ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2816:1: ( rulePrimary )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2816:1: ( rulePrimary )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2817:1: rulePrimary
            {
             before(grammarAccess.getPowerAccess().getPrimaryParserRuleCall_0()); 
            pushFollow(FOLLOW_rulePrimary_in_rule__Power__Group__0__Impl5650);
            rulePrimary();

            state._fsp--;

             after(grammarAccess.getPowerAccess().getPrimaryParserRuleCall_0()); 

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
    // $ANTLR end "rule__Power__Group__0__Impl"


    // $ANTLR start "rule__Power__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2828:1: rule__Power__Group__1 : rule__Power__Group__1__Impl ;
    public final void rule__Power__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2832:1: ( rule__Power__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2833:2: rule__Power__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Power__Group__1__Impl_in_rule__Power__Group__15679);
            rule__Power__Group__1__Impl();

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
    // $ANTLR end "rule__Power__Group__1"


    // $ANTLR start "rule__Power__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2839:1: rule__Power__Group__1__Impl : ( ( rule__Power__Group_1__0 )* ) ;
    public final void rule__Power__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2843:1: ( ( ( rule__Power__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2844:1: ( ( rule__Power__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2844:1: ( ( rule__Power__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2845:1: ( rule__Power__Group_1__0 )*
            {
             before(grammarAccess.getPowerAccess().getGroup_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2846:1: ( rule__Power__Group_1__0 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==31) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2846:2: rule__Power__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Power__Group_1__0_in_rule__Power__Group__1__Impl5706);
            	    rule__Power__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

             after(grammarAccess.getPowerAccess().getGroup_1()); 

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
    // $ANTLR end "rule__Power__Group__1__Impl"


    // $ANTLR start "rule__Power__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2860:1: rule__Power__Group_1__0 : rule__Power__Group_1__0__Impl rule__Power__Group_1__1 ;
    public final void rule__Power__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2864:1: ( rule__Power__Group_1__0__Impl rule__Power__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2865:2: rule__Power__Group_1__0__Impl rule__Power__Group_1__1
            {
            pushFollow(FOLLOW_rule__Power__Group_1__0__Impl_in_rule__Power__Group_1__05741);
            rule__Power__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Power__Group_1__1_in_rule__Power__Group_1__05744);
            rule__Power__Group_1__1();

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
    // $ANTLR end "rule__Power__Group_1__0"


    // $ANTLR start "rule__Power__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2872:1: rule__Power__Group_1__0__Impl : ( () ) ;
    public final void rule__Power__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2876:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2877:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2877:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2878:1: ()
            {
             before(grammarAccess.getPowerAccess().getPowerLeftAction_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2879:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2881:1: 
            {
            }

             after(grammarAccess.getPowerAccess().getPowerLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power__Group_1__0__Impl"


    // $ANTLR start "rule__Power__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2891:1: rule__Power__Group_1__1 : rule__Power__Group_1__1__Impl rule__Power__Group_1__2 ;
    public final void rule__Power__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2895:1: ( rule__Power__Group_1__1__Impl rule__Power__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2896:2: rule__Power__Group_1__1__Impl rule__Power__Group_1__2
            {
            pushFollow(FOLLOW_rule__Power__Group_1__1__Impl_in_rule__Power__Group_1__15802);
            rule__Power__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Power__Group_1__2_in_rule__Power__Group_1__15805);
            rule__Power__Group_1__2();

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
    // $ANTLR end "rule__Power__Group_1__1"


    // $ANTLR start "rule__Power__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2903:1: rule__Power__Group_1__1__Impl : ( '**' ) ;
    public final void rule__Power__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2907:1: ( ( '**' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2908:1: ( '**' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2908:1: ( '**' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2909:1: '**'
            {
             before(grammarAccess.getPowerAccess().getAsteriskAsteriskKeyword_1_1()); 
            match(input,31,FOLLOW_31_in_rule__Power__Group_1__1__Impl5833); 
             after(grammarAccess.getPowerAccess().getAsteriskAsteriskKeyword_1_1()); 

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
    // $ANTLR end "rule__Power__Group_1__1__Impl"


    // $ANTLR start "rule__Power__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2922:1: rule__Power__Group_1__2 : rule__Power__Group_1__2__Impl ;
    public final void rule__Power__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2926:1: ( rule__Power__Group_1__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2927:2: rule__Power__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__Power__Group_1__2__Impl_in_rule__Power__Group_1__25864);
            rule__Power__Group_1__2__Impl();

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
    // $ANTLR end "rule__Power__Group_1__2"


    // $ANTLR start "rule__Power__Group_1__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2933:1: rule__Power__Group_1__2__Impl : ( ( rule__Power__RightAssignment_1_2 ) ) ;
    public final void rule__Power__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2937:1: ( ( ( rule__Power__RightAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2938:1: ( ( rule__Power__RightAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2938:1: ( ( rule__Power__RightAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2939:1: ( rule__Power__RightAssignment_1_2 )
            {
             before(grammarAccess.getPowerAccess().getRightAssignment_1_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2940:1: ( rule__Power__RightAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2940:2: rule__Power__RightAssignment_1_2
            {
            pushFollow(FOLLOW_rule__Power__RightAssignment_1_2_in_rule__Power__Group_1__2__Impl5891);
            rule__Power__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getPowerAccess().getRightAssignment_1_2()); 

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
    // $ANTLR end "rule__Power__Group_1__2__Impl"


    // $ANTLR start "rule__Primary__Group_2__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2956:1: rule__Primary__Group_2__0 : rule__Primary__Group_2__0__Impl rule__Primary__Group_2__1 ;
    public final void rule__Primary__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2960:1: ( rule__Primary__Group_2__0__Impl rule__Primary__Group_2__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2961:2: rule__Primary__Group_2__0__Impl rule__Primary__Group_2__1
            {
            pushFollow(FOLLOW_rule__Primary__Group_2__0__Impl_in_rule__Primary__Group_2__05927);
            rule__Primary__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Primary__Group_2__1_in_rule__Primary__Group_2__05930);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2968:1: rule__Primary__Group_2__0__Impl : ( '(' ) ;
    public final void rule__Primary__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2972:1: ( ( '(' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2973:1: ( '(' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2973:1: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2974:1: '('
            {
             before(grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0()); 
            match(input,32,FOLLOW_32_in_rule__Primary__Group_2__0__Impl5958); 
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2987:1: rule__Primary__Group_2__1 : rule__Primary__Group_2__1__Impl rule__Primary__Group_2__2 ;
    public final void rule__Primary__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2991:1: ( rule__Primary__Group_2__1__Impl rule__Primary__Group_2__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2992:2: rule__Primary__Group_2__1__Impl rule__Primary__Group_2__2
            {
            pushFollow(FOLLOW_rule__Primary__Group_2__1__Impl_in_rule__Primary__Group_2__15989);
            rule__Primary__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Primary__Group_2__2_in_rule__Primary__Group_2__15992);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2999:1: rule__Primary__Group_2__1__Impl : ( ruleAddition ) ;
    public final void rule__Primary__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3003:1: ( ( ruleAddition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3004:1: ( ruleAddition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3004:1: ( ruleAddition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3005:1: ruleAddition
            {
             before(grammarAccess.getPrimaryAccess().getAdditionParserRuleCall_2_1()); 
            pushFollow(FOLLOW_ruleAddition_in_rule__Primary__Group_2__1__Impl6019);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3016:1: rule__Primary__Group_2__2 : rule__Primary__Group_2__2__Impl ;
    public final void rule__Primary__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3020:1: ( rule__Primary__Group_2__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3021:2: rule__Primary__Group_2__2__Impl
            {
            pushFollow(FOLLOW_rule__Primary__Group_2__2__Impl_in_rule__Primary__Group_2__26048);
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3027:1: rule__Primary__Group_2__2__Impl : ( ')' ) ;
    public final void rule__Primary__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3031:1: ( ( ')' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3032:1: ( ')' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3032:1: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3033:1: ')'
            {
             before(grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_2_2()); 
            match(input,33,FOLLOW_33_in_rule__Primary__Group_2__2__Impl6076); 
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


    // $ANTLR start "rule__Or__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3052:1: rule__Or__Group__0 : rule__Or__Group__0__Impl rule__Or__Group__1 ;
    public final void rule__Or__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3056:1: ( rule__Or__Group__0__Impl rule__Or__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3057:2: rule__Or__Group__0__Impl rule__Or__Group__1
            {
            pushFollow(FOLLOW_rule__Or__Group__0__Impl_in_rule__Or__Group__06113);
            rule__Or__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Or__Group__1_in_rule__Or__Group__06116);
            rule__Or__Group__1();

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
    // $ANTLR end "rule__Or__Group__0"


    // $ANTLR start "rule__Or__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3064:1: rule__Or__Group__0__Impl : ( ruleAnd ) ;
    public final void rule__Or__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3068:1: ( ( ruleAnd ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3069:1: ( ruleAnd )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3069:1: ( ruleAnd )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3070:1: ruleAnd
            {
             before(grammarAccess.getOrAccess().getAndParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleAnd_in_rule__Or__Group__0__Impl6143);
            ruleAnd();

            state._fsp--;

             after(grammarAccess.getOrAccess().getAndParserRuleCall_0()); 

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
    // $ANTLR end "rule__Or__Group__0__Impl"


    // $ANTLR start "rule__Or__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3081:1: rule__Or__Group__1 : rule__Or__Group__1__Impl ;
    public final void rule__Or__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3085:1: ( rule__Or__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3086:2: rule__Or__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Or__Group__1__Impl_in_rule__Or__Group__16172);
            rule__Or__Group__1__Impl();

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
    // $ANTLR end "rule__Or__Group__1"


    // $ANTLR start "rule__Or__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3092:1: rule__Or__Group__1__Impl : ( ( rule__Or__Group_1__0 )* ) ;
    public final void rule__Or__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3096:1: ( ( ( rule__Or__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3097:1: ( ( rule__Or__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3097:1: ( ( rule__Or__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3098:1: ( rule__Or__Group_1__0 )*
            {
             before(grammarAccess.getOrAccess().getGroup_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3099:1: ( rule__Or__Group_1__0 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==34) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3099:2: rule__Or__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Or__Group_1__0_in_rule__Or__Group__1__Impl6199);
            	    rule__Or__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

             after(grammarAccess.getOrAccess().getGroup_1()); 

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
    // $ANTLR end "rule__Or__Group__1__Impl"


    // $ANTLR start "rule__Or__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3113:1: rule__Or__Group_1__0 : rule__Or__Group_1__0__Impl rule__Or__Group_1__1 ;
    public final void rule__Or__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3117:1: ( rule__Or__Group_1__0__Impl rule__Or__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3118:2: rule__Or__Group_1__0__Impl rule__Or__Group_1__1
            {
            pushFollow(FOLLOW_rule__Or__Group_1__0__Impl_in_rule__Or__Group_1__06234);
            rule__Or__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Or__Group_1__1_in_rule__Or__Group_1__06237);
            rule__Or__Group_1__1();

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
    // $ANTLR end "rule__Or__Group_1__0"


    // $ANTLR start "rule__Or__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3125:1: rule__Or__Group_1__0__Impl : ( () ) ;
    public final void rule__Or__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3129:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3130:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3130:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3131:1: ()
            {
             before(grammarAccess.getOrAccess().getOrLeftAction_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3132:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3134:1: 
            {
            }

             after(grammarAccess.getOrAccess().getOrLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or__Group_1__0__Impl"


    // $ANTLR start "rule__Or__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3144:1: rule__Or__Group_1__1 : rule__Or__Group_1__1__Impl rule__Or__Group_1__2 ;
    public final void rule__Or__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3148:1: ( rule__Or__Group_1__1__Impl rule__Or__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3149:2: rule__Or__Group_1__1__Impl rule__Or__Group_1__2
            {
            pushFollow(FOLLOW_rule__Or__Group_1__1__Impl_in_rule__Or__Group_1__16295);
            rule__Or__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Or__Group_1__2_in_rule__Or__Group_1__16298);
            rule__Or__Group_1__2();

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
    // $ANTLR end "rule__Or__Group_1__1"


    // $ANTLR start "rule__Or__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3156:1: rule__Or__Group_1__1__Impl : ( '||' ) ;
    public final void rule__Or__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3160:1: ( ( '||' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3161:1: ( '||' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3161:1: ( '||' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3162:1: '||'
            {
             before(grammarAccess.getOrAccess().getVerticalLineVerticalLineKeyword_1_1()); 
            match(input,34,FOLLOW_34_in_rule__Or__Group_1__1__Impl6326); 
             after(grammarAccess.getOrAccess().getVerticalLineVerticalLineKeyword_1_1()); 

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
    // $ANTLR end "rule__Or__Group_1__1__Impl"


    // $ANTLR start "rule__Or__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3175:1: rule__Or__Group_1__2 : rule__Or__Group_1__2__Impl ;
    public final void rule__Or__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3179:1: ( rule__Or__Group_1__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3180:2: rule__Or__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__Or__Group_1__2__Impl_in_rule__Or__Group_1__26357);
            rule__Or__Group_1__2__Impl();

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
    // $ANTLR end "rule__Or__Group_1__2"


    // $ANTLR start "rule__Or__Group_1__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3186:1: rule__Or__Group_1__2__Impl : ( ( rule__Or__RightAssignment_1_2 ) ) ;
    public final void rule__Or__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3190:1: ( ( ( rule__Or__RightAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3191:1: ( ( rule__Or__RightAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3191:1: ( ( rule__Or__RightAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3192:1: ( rule__Or__RightAssignment_1_2 )
            {
             before(grammarAccess.getOrAccess().getRightAssignment_1_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3193:1: ( rule__Or__RightAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3193:2: rule__Or__RightAssignment_1_2
            {
            pushFollow(FOLLOW_rule__Or__RightAssignment_1_2_in_rule__Or__Group_1__2__Impl6384);
            rule__Or__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getOrAccess().getRightAssignment_1_2()); 

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
    // $ANTLR end "rule__Or__Group_1__2__Impl"


    // $ANTLR start "rule__And__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3209:1: rule__And__Group__0 : rule__And__Group__0__Impl rule__And__Group__1 ;
    public final void rule__And__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3213:1: ( rule__And__Group__0__Impl rule__And__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3214:2: rule__And__Group__0__Impl rule__And__Group__1
            {
            pushFollow(FOLLOW_rule__And__Group__0__Impl_in_rule__And__Group__06420);
            rule__And__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__And__Group__1_in_rule__And__Group__06423);
            rule__And__Group__1();

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
    // $ANTLR end "rule__And__Group__0"


    // $ANTLR start "rule__And__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3221:1: rule__And__Group__0__Impl : ( ruleNot ) ;
    public final void rule__And__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3225:1: ( ( ruleNot ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3226:1: ( ruleNot )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3226:1: ( ruleNot )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3227:1: ruleNot
            {
             before(grammarAccess.getAndAccess().getNotParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleNot_in_rule__And__Group__0__Impl6450);
            ruleNot();

            state._fsp--;

             after(grammarAccess.getAndAccess().getNotParserRuleCall_0()); 

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
    // $ANTLR end "rule__And__Group__0__Impl"


    // $ANTLR start "rule__And__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3238:1: rule__And__Group__1 : rule__And__Group__1__Impl ;
    public final void rule__And__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3242:1: ( rule__And__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3243:2: rule__And__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__And__Group__1__Impl_in_rule__And__Group__16479);
            rule__And__Group__1__Impl();

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
    // $ANTLR end "rule__And__Group__1"


    // $ANTLR start "rule__And__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3249:1: rule__And__Group__1__Impl : ( ( rule__And__Group_1__0 )* ) ;
    public final void rule__And__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3253:1: ( ( ( rule__And__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3254:1: ( ( rule__And__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3254:1: ( ( rule__And__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3255:1: ( rule__And__Group_1__0 )*
            {
             before(grammarAccess.getAndAccess().getGroup_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3256:1: ( rule__And__Group_1__0 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==35) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3256:2: rule__And__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__And__Group_1__0_in_rule__And__Group__1__Impl6506);
            	    rule__And__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

             after(grammarAccess.getAndAccess().getGroup_1()); 

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
    // $ANTLR end "rule__And__Group__1__Impl"


    // $ANTLR start "rule__And__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3270:1: rule__And__Group_1__0 : rule__And__Group_1__0__Impl rule__And__Group_1__1 ;
    public final void rule__And__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3274:1: ( rule__And__Group_1__0__Impl rule__And__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3275:2: rule__And__Group_1__0__Impl rule__And__Group_1__1
            {
            pushFollow(FOLLOW_rule__And__Group_1__0__Impl_in_rule__And__Group_1__06541);
            rule__And__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__And__Group_1__1_in_rule__And__Group_1__06544);
            rule__And__Group_1__1();

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
    // $ANTLR end "rule__And__Group_1__0"


    // $ANTLR start "rule__And__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3282:1: rule__And__Group_1__0__Impl : ( () ) ;
    public final void rule__And__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3286:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3287:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3287:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3288:1: ()
            {
             before(grammarAccess.getAndAccess().getAndLeftAction_1_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3289:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3291:1: 
            {
            }

             after(grammarAccess.getAndAccess().getAndLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And__Group_1__0__Impl"


    // $ANTLR start "rule__And__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3301:1: rule__And__Group_1__1 : rule__And__Group_1__1__Impl rule__And__Group_1__2 ;
    public final void rule__And__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3305:1: ( rule__And__Group_1__1__Impl rule__And__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3306:2: rule__And__Group_1__1__Impl rule__And__Group_1__2
            {
            pushFollow(FOLLOW_rule__And__Group_1__1__Impl_in_rule__And__Group_1__16602);
            rule__And__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__And__Group_1__2_in_rule__And__Group_1__16605);
            rule__And__Group_1__2();

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
    // $ANTLR end "rule__And__Group_1__1"


    // $ANTLR start "rule__And__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3313:1: rule__And__Group_1__1__Impl : ( '&&' ) ;
    public final void rule__And__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3317:1: ( ( '&&' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3318:1: ( '&&' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3318:1: ( '&&' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3319:1: '&&'
            {
             before(grammarAccess.getAndAccess().getAmpersandAmpersandKeyword_1_1()); 
            match(input,35,FOLLOW_35_in_rule__And__Group_1__1__Impl6633); 
             after(grammarAccess.getAndAccess().getAmpersandAmpersandKeyword_1_1()); 

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
    // $ANTLR end "rule__And__Group_1__1__Impl"


    // $ANTLR start "rule__And__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3332:1: rule__And__Group_1__2 : rule__And__Group_1__2__Impl ;
    public final void rule__And__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3336:1: ( rule__And__Group_1__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3337:2: rule__And__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__And__Group_1__2__Impl_in_rule__And__Group_1__26664);
            rule__And__Group_1__2__Impl();

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
    // $ANTLR end "rule__And__Group_1__2"


    // $ANTLR start "rule__And__Group_1__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3343:1: rule__And__Group_1__2__Impl : ( ( rule__And__RightAssignment_1_2 ) ) ;
    public final void rule__And__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3347:1: ( ( ( rule__And__RightAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3348:1: ( ( rule__And__RightAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3348:1: ( ( rule__And__RightAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3349:1: ( rule__And__RightAssignment_1_2 )
            {
             before(grammarAccess.getAndAccess().getRightAssignment_1_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3350:1: ( rule__And__RightAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3350:2: rule__And__RightAssignment_1_2
            {
            pushFollow(FOLLOW_rule__And__RightAssignment_1_2_in_rule__And__Group_1__2__Impl6691);
            rule__And__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getAndAccess().getRightAssignment_1_2()); 

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
    // $ANTLR end "rule__And__Group_1__2__Impl"


    // $ANTLR start "rule__Not__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3366:1: rule__Not__Group__0 : rule__Not__Group__0__Impl rule__Not__Group__1 ;
    public final void rule__Not__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3370:1: ( rule__Not__Group__0__Impl rule__Not__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3371:2: rule__Not__Group__0__Impl rule__Not__Group__1
            {
            pushFollow(FOLLOW_rule__Not__Group__0__Impl_in_rule__Not__Group__06727);
            rule__Not__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Not__Group__1_in_rule__Not__Group__06730);
            rule__Not__Group__1();

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
    // $ANTLR end "rule__Not__Group__0"


    // $ANTLR start "rule__Not__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3378:1: rule__Not__Group__0__Impl : ( ( '!' )? ) ;
    public final void rule__Not__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3382:1: ( ( ( '!' )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3383:1: ( ( '!' )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3383:1: ( ( '!' )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3384:1: ( '!' )?
            {
             before(grammarAccess.getNotAccess().getExclamationMarkKeyword_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3385:1: ( '!' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==36) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3386:2: '!'
                    {
                    match(input,36,FOLLOW_36_in_rule__Not__Group__0__Impl6759); 

                    }
                    break;

            }

             after(grammarAccess.getNotAccess().getExclamationMarkKeyword_0()); 

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
    // $ANTLR end "rule__Not__Group__0__Impl"


    // $ANTLR start "rule__Not__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3397:1: rule__Not__Group__1 : rule__Not__Group__1__Impl rule__Not__Group__2 ;
    public final void rule__Not__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3401:1: ( rule__Not__Group__1__Impl rule__Not__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3402:2: rule__Not__Group__1__Impl rule__Not__Group__2
            {
            pushFollow(FOLLOW_rule__Not__Group__1__Impl_in_rule__Not__Group__16792);
            rule__Not__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Not__Group__2_in_rule__Not__Group__16795);
            rule__Not__Group__2();

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
    // $ANTLR end "rule__Not__Group__1"


    // $ANTLR start "rule__Not__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3409:1: rule__Not__Group__1__Impl : ( ( rule__Not__Alternatives_1 ) ) ;
    public final void rule__Not__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3413:1: ( ( ( rule__Not__Alternatives_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3414:1: ( ( rule__Not__Alternatives_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3414:1: ( ( rule__Not__Alternatives_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3415:1: ( rule__Not__Alternatives_1 )
            {
             before(grammarAccess.getNotAccess().getAlternatives_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3416:1: ( rule__Not__Alternatives_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3416:2: rule__Not__Alternatives_1
            {
            pushFollow(FOLLOW_rule__Not__Alternatives_1_in_rule__Not__Group__1__Impl6822);
            rule__Not__Alternatives_1();

            state._fsp--;


            }

             after(grammarAccess.getNotAccess().getAlternatives_1()); 

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
    // $ANTLR end "rule__Not__Group__1__Impl"


    // $ANTLR start "rule__Not__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3426:1: rule__Not__Group__2 : rule__Not__Group__2__Impl ;
    public final void rule__Not__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3430:1: ( rule__Not__Group__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3431:2: rule__Not__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Not__Group__2__Impl_in_rule__Not__Group__26852);
            rule__Not__Group__2__Impl();

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
    // $ANTLR end "rule__Not__Group__2"


    // $ANTLR start "rule__Not__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3437:1: rule__Not__Group__2__Impl : ( () ) ;
    public final void rule__Not__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3441:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3442:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3442:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3443:1: ()
            {
             before(grammarAccess.getNotAccess().getNotValAction_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3444:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3446:1: 
            {
            }

             after(grammarAccess.getNotAccess().getNotValAction_2()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Not__Group__2__Impl"


    // $ANTLR start "rule__Comparison__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3462:1: rule__Comparison__Group__0 : rule__Comparison__Group__0__Impl rule__Comparison__Group__1 ;
    public final void rule__Comparison__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3466:1: ( rule__Comparison__Group__0__Impl rule__Comparison__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3467:2: rule__Comparison__Group__0__Impl rule__Comparison__Group__1
            {
            pushFollow(FOLLOW_rule__Comparison__Group__0__Impl_in_rule__Comparison__Group__06916);
            rule__Comparison__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Comparison__Group__1_in_rule__Comparison__Group__06919);
            rule__Comparison__Group__1();

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
    // $ANTLR end "rule__Comparison__Group__0"


    // $ANTLR start "rule__Comparison__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3474:1: rule__Comparison__Group__0__Impl : ( ( rule__Comparison__LeftAssignment_0 ) ) ;
    public final void rule__Comparison__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3478:1: ( ( ( rule__Comparison__LeftAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3479:1: ( ( rule__Comparison__LeftAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3479:1: ( ( rule__Comparison__LeftAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3480:1: ( rule__Comparison__LeftAssignment_0 )
            {
             before(grammarAccess.getComparisonAccess().getLeftAssignment_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3481:1: ( rule__Comparison__LeftAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3481:2: rule__Comparison__LeftAssignment_0
            {
            pushFollow(FOLLOW_rule__Comparison__LeftAssignment_0_in_rule__Comparison__Group__0__Impl6946);
            rule__Comparison__LeftAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getComparisonAccess().getLeftAssignment_0()); 

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
    // $ANTLR end "rule__Comparison__Group__0__Impl"


    // $ANTLR start "rule__Comparison__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3491:1: rule__Comparison__Group__1 : rule__Comparison__Group__1__Impl rule__Comparison__Group__2 ;
    public final void rule__Comparison__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3495:1: ( rule__Comparison__Group__1__Impl rule__Comparison__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3496:2: rule__Comparison__Group__1__Impl rule__Comparison__Group__2
            {
            pushFollow(FOLLOW_rule__Comparison__Group__1__Impl_in_rule__Comparison__Group__16976);
            rule__Comparison__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Comparison__Group__2_in_rule__Comparison__Group__16979);
            rule__Comparison__Group__2();

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
    // $ANTLR end "rule__Comparison__Group__1"


    // $ANTLR start "rule__Comparison__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3503:1: rule__Comparison__Group__1__Impl : ( ( rule__Comparison__OperatorAssignment_1 ) ) ;
    public final void rule__Comparison__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3507:1: ( ( ( rule__Comparison__OperatorAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3508:1: ( ( rule__Comparison__OperatorAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3508:1: ( ( rule__Comparison__OperatorAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3509:1: ( rule__Comparison__OperatorAssignment_1 )
            {
             before(grammarAccess.getComparisonAccess().getOperatorAssignment_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3510:1: ( rule__Comparison__OperatorAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3510:2: rule__Comparison__OperatorAssignment_1
            {
            pushFollow(FOLLOW_rule__Comparison__OperatorAssignment_1_in_rule__Comparison__Group__1__Impl7006);
            rule__Comparison__OperatorAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getComparisonAccess().getOperatorAssignment_1()); 

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
    // $ANTLR end "rule__Comparison__Group__1__Impl"


    // $ANTLR start "rule__Comparison__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3520:1: rule__Comparison__Group__2 : rule__Comparison__Group__2__Impl ;
    public final void rule__Comparison__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3524:1: ( rule__Comparison__Group__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3525:2: rule__Comparison__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Comparison__Group__2__Impl_in_rule__Comparison__Group__27036);
            rule__Comparison__Group__2__Impl();

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
    // $ANTLR end "rule__Comparison__Group__2"


    // $ANTLR start "rule__Comparison__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3531:1: rule__Comparison__Group__2__Impl : ( ( rule__Comparison__RightAssignment_2 ) ) ;
    public final void rule__Comparison__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3535:1: ( ( ( rule__Comparison__RightAssignment_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3536:1: ( ( rule__Comparison__RightAssignment_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3536:1: ( ( rule__Comparison__RightAssignment_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3537:1: ( rule__Comparison__RightAssignment_2 )
            {
             before(grammarAccess.getComparisonAccess().getRightAssignment_2()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3538:1: ( rule__Comparison__RightAssignment_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3538:2: rule__Comparison__RightAssignment_2
            {
            pushFollow(FOLLOW_rule__Comparison__RightAssignment_2_in_rule__Comparison__Group__2__Impl7063);
            rule__Comparison__RightAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getComparisonAccess().getRightAssignment_2()); 

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
    // $ANTLR end "rule__Comparison__Group__2__Impl"


    // $ANTLR start "rule__QualifiedName__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3554:1: rule__QualifiedName__Group__0 : rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1 ;
    public final void rule__QualifiedName__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3558:1: ( rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3559:2: rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group__0__Impl_in_rule__QualifiedName__Group__07099);
            rule__QualifiedName__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__QualifiedName__Group__1_in_rule__QualifiedName__Group__07102);
            rule__QualifiedName__Group__1();

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
    // $ANTLR end "rule__QualifiedName__Group__0"


    // $ANTLR start "rule__QualifiedName__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3566:1: rule__QualifiedName__Group__0__Impl : ( RULE_ID ) ;
    public final void rule__QualifiedName__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3570:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3571:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3571:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3572:1: RULE_ID
            {
             before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__QualifiedName__Group__0__Impl7129); 
             after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 

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
    // $ANTLR end "rule__QualifiedName__Group__0__Impl"


    // $ANTLR start "rule__QualifiedName__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3583:1: rule__QualifiedName__Group__1 : rule__QualifiedName__Group__1__Impl ;
    public final void rule__QualifiedName__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3587:1: ( rule__QualifiedName__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3588:2: rule__QualifiedName__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group__1__Impl_in_rule__QualifiedName__Group__17158);
            rule__QualifiedName__Group__1__Impl();

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
    // $ANTLR end "rule__QualifiedName__Group__1"


    // $ANTLR start "rule__QualifiedName__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3594:1: rule__QualifiedName__Group__1__Impl : ( ( rule__QualifiedName__Group_1__0 )* ) ;
    public final void rule__QualifiedName__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3598:1: ( ( ( rule__QualifiedName__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3599:1: ( ( rule__QualifiedName__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3599:1: ( ( rule__QualifiedName__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3600:1: ( rule__QualifiedName__Group_1__0 )*
            {
             before(grammarAccess.getQualifiedNameAccess().getGroup_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3601:1: ( rule__QualifiedName__Group_1__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==37) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3601:2: rule__QualifiedName__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__QualifiedName__Group_1__0_in_rule__QualifiedName__Group__1__Impl7185);
            	    rule__QualifiedName__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

             after(grammarAccess.getQualifiedNameAccess().getGroup_1()); 

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
    // $ANTLR end "rule__QualifiedName__Group__1__Impl"


    // $ANTLR start "rule__QualifiedName__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3615:1: rule__QualifiedName__Group_1__0 : rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1 ;
    public final void rule__QualifiedName__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3619:1: ( rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3620:2: rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group_1__0__Impl_in_rule__QualifiedName__Group_1__07220);
            rule__QualifiedName__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__QualifiedName__Group_1__1_in_rule__QualifiedName__Group_1__07223);
            rule__QualifiedName__Group_1__1();

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
    // $ANTLR end "rule__QualifiedName__Group_1__0"


    // $ANTLR start "rule__QualifiedName__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3627:1: rule__QualifiedName__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifiedName__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3631:1: ( ( '.' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3632:1: ( '.' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3632:1: ( '.' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3633:1: '.'
            {
             before(grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
            match(input,37,FOLLOW_37_in_rule__QualifiedName__Group_1__0__Impl7251); 
             after(grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 

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
    // $ANTLR end "rule__QualifiedName__Group_1__0__Impl"


    // $ANTLR start "rule__QualifiedName__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3646:1: rule__QualifiedName__Group_1__1 : rule__QualifiedName__Group_1__1__Impl ;
    public final void rule__QualifiedName__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3650:1: ( rule__QualifiedName__Group_1__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3651:2: rule__QualifiedName__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group_1__1__Impl_in_rule__QualifiedName__Group_1__17282);
            rule__QualifiedName__Group_1__1__Impl();

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
    // $ANTLR end "rule__QualifiedName__Group_1__1"


    // $ANTLR start "rule__QualifiedName__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3657:1: rule__QualifiedName__Group_1__1__Impl : ( RULE_ID ) ;
    public final void rule__QualifiedName__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3661:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3662:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3662:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3663:1: RULE_ID
            {
             before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__QualifiedName__Group_1__1__Impl7309); 
             after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 

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
    // $ANTLR end "rule__QualifiedName__Group_1__1__Impl"


    // $ANTLR start "rule__QualifiedNameWithWildCard__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3678:1: rule__QualifiedNameWithWildCard__Group__0 : rule__QualifiedNameWithWildCard__Group__0__Impl rule__QualifiedNameWithWildCard__Group__1 ;
    public final void rule__QualifiedNameWithWildCard__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3682:1: ( rule__QualifiedNameWithWildCard__Group__0__Impl rule__QualifiedNameWithWildCard__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3683:2: rule__QualifiedNameWithWildCard__Group__0__Impl rule__QualifiedNameWithWildCard__Group__1
            {
            pushFollow(FOLLOW_rule__QualifiedNameWithWildCard__Group__0__Impl_in_rule__QualifiedNameWithWildCard__Group__07342);
            rule__QualifiedNameWithWildCard__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__QualifiedNameWithWildCard__Group__1_in_rule__QualifiedNameWithWildCard__Group__07345);
            rule__QualifiedNameWithWildCard__Group__1();

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
    // $ANTLR end "rule__QualifiedNameWithWildCard__Group__0"


    // $ANTLR start "rule__QualifiedNameWithWildCard__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3690:1: rule__QualifiedNameWithWildCard__Group__0__Impl : ( ruleQualifiedName ) ;
    public final void rule__QualifiedNameWithWildCard__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3694:1: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3695:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3695:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3696:1: ruleQualifiedName
            {
             before(grammarAccess.getQualifiedNameWithWildCardAccess().getQualifiedNameParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleQualifiedName_in_rule__QualifiedNameWithWildCard__Group__0__Impl7372);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getQualifiedNameWithWildCardAccess().getQualifiedNameParserRuleCall_0()); 

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
    // $ANTLR end "rule__QualifiedNameWithWildCard__Group__0__Impl"


    // $ANTLR start "rule__QualifiedNameWithWildCard__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3707:1: rule__QualifiedNameWithWildCard__Group__1 : rule__QualifiedNameWithWildCard__Group__1__Impl ;
    public final void rule__QualifiedNameWithWildCard__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3711:1: ( rule__QualifiedNameWithWildCard__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3712:2: rule__QualifiedNameWithWildCard__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__QualifiedNameWithWildCard__Group__1__Impl_in_rule__QualifiedNameWithWildCard__Group__17401);
            rule__QualifiedNameWithWildCard__Group__1__Impl();

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
    // $ANTLR end "rule__QualifiedNameWithWildCard__Group__1"


    // $ANTLR start "rule__QualifiedNameWithWildCard__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3718:1: rule__QualifiedNameWithWildCard__Group__1__Impl : ( ( '.*' )? ) ;
    public final void rule__QualifiedNameWithWildCard__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3722:1: ( ( ( '.*' )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3723:1: ( ( '.*' )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3723:1: ( ( '.*' )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3724:1: ( '.*' )?
            {
             before(grammarAccess.getQualifiedNameWithWildCardAccess().getFullStopAsteriskKeyword_1()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3725:1: ( '.*' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==38) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3726:2: '.*'
                    {
                    match(input,38,FOLLOW_38_in_rule__QualifiedNameWithWildCard__Group__1__Impl7430); 

                    }
                    break;

            }

             after(grammarAccess.getQualifiedNameWithWildCardAccess().getFullStopAsteriskKeyword_1()); 

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
    // $ANTLR end "rule__QualifiedNameWithWildCard__Group__1__Impl"


    // $ANTLR start "rule__System__NameAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3743:1: rule__System__NameAssignment_1 : ( ruleQualifiedName ) ;
    public final void rule__System__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3747:1: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3748:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3748:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3749:1: ruleQualifiedName
            {
             before(grammarAccess.getSystemAccess().getNameQualifiedNameParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleQualifiedName_in_rule__System__NameAssignment_17473);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getSystemAccess().getNameQualifiedNameParserRuleCall_1_0()); 

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
    // $ANTLR end "rule__System__NameAssignment_1"


    // $ANTLR start "rule__System__VariablesAssignment_3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3758:1: rule__System__VariablesAssignment_3 : ( ruleVariableDeclaration ) ;
    public final void rule__System__VariablesAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3762:1: ( ( ruleVariableDeclaration ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3763:1: ( ruleVariableDeclaration )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3763:1: ( ruleVariableDeclaration )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3764:1: ruleVariableDeclaration
            {
             before(grammarAccess.getSystemAccess().getVariablesVariableDeclarationParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleVariableDeclaration_in_rule__System__VariablesAssignment_37504);
            ruleVariableDeclaration();

            state._fsp--;

             after(grammarAccess.getSystemAccess().getVariablesVariableDeclarationParserRuleCall_3_0()); 

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
    // $ANTLR end "rule__System__VariablesAssignment_3"


    // $ANTLR start "rule__System__TransitionsAssignment_4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3773:1: rule__System__TransitionsAssignment_4 : ( ruleTransition ) ;
    public final void rule__System__TransitionsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3777:1: ( ( ruleTransition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3778:1: ( ruleTransition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3778:1: ( ruleTransition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3779:1: ruleTransition
            {
             before(grammarAccess.getSystemAccess().getTransitionsTransitionParserRuleCall_4_0()); 
            pushFollow(FOLLOW_ruleTransition_in_rule__System__TransitionsAssignment_47535);
            ruleTransition();

            state._fsp--;

             after(grammarAccess.getSystemAccess().getTransitionsTransitionParserRuleCall_4_0()); 

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
    // $ANTLR end "rule__System__TransitionsAssignment_4"


    // $ANTLR start "rule__VariableDeclaration__NameAssignment_0_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3788:1: rule__VariableDeclaration__NameAssignment_0_0 : ( ruleQualifiedName ) ;
    public final void rule__VariableDeclaration__NameAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3792:1: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3793:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3793:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3794:1: ruleQualifiedName
            {
             before(grammarAccess.getVariableDeclarationAccess().getNameQualifiedNameParserRuleCall_0_0_0()); 
            pushFollow(FOLLOW_ruleQualifiedName_in_rule__VariableDeclaration__NameAssignment_0_07566);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getVariableDeclarationAccess().getNameQualifiedNameParserRuleCall_0_0_0()); 

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
    // $ANTLR end "rule__VariableDeclaration__NameAssignment_0_0"


    // $ANTLR start "rule__VariableDeclaration__InitValAssignment_0_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3803:1: rule__VariableDeclaration__InitValAssignment_0_2 : ( RULE_INT ) ;
    public final void rule__VariableDeclaration__InitValAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3807:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3808:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3808:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3809:1: RULE_INT
            {
             before(grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_0_2_0()); 
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__VariableDeclaration__InitValAssignment_0_27597); 
             after(grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_0_2_0()); 

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
    // $ANTLR end "rule__VariableDeclaration__InitValAssignment_0_2"


    // $ANTLR start "rule__VariableDeclaration__AnameAssignment_1_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3818:1: rule__VariableDeclaration__AnameAssignment_1_0 : ( ruleQualifiedName ) ;
    public final void rule__VariableDeclaration__AnameAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3822:1: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3823:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3823:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3824:1: ruleQualifiedName
            {
             before(grammarAccess.getVariableDeclarationAccess().getAnameQualifiedNameParserRuleCall_1_0_0()); 
            pushFollow(FOLLOW_ruleQualifiedName_in_rule__VariableDeclaration__AnameAssignment_1_07628);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getVariableDeclarationAccess().getAnameQualifiedNameParserRuleCall_1_0_0()); 

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
    // $ANTLR end "rule__VariableDeclaration__AnameAssignment_1_0"


    // $ANTLR start "rule__VariableDeclaration__IndexAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3833:1: rule__VariableDeclaration__IndexAssignment_1_2 : ( ruleAddition ) ;
    public final void rule__VariableDeclaration__IndexAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3837:1: ( ( ruleAddition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3838:1: ( ruleAddition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3838:1: ( ruleAddition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3839:1: ruleAddition
            {
             before(grammarAccess.getVariableDeclarationAccess().getIndexAdditionParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_ruleAddition_in_rule__VariableDeclaration__IndexAssignment_1_27659);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getVariableDeclarationAccess().getIndexAdditionParserRuleCall_1_2_0()); 

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
    // $ANTLR end "rule__VariableDeclaration__IndexAssignment_1_2"


    // $ANTLR start "rule__VariableDeclaration__InitValAssignment_1_5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3848:1: rule__VariableDeclaration__InitValAssignment_1_5 : ( RULE_INT ) ;
    public final void rule__VariableDeclaration__InitValAssignment_1_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3852:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3853:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3853:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3854:1: RULE_INT
            {
             before(grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_1_5_0()); 
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__VariableDeclaration__InitValAssignment_1_57690); 
             after(grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_1_5_0()); 

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
    // $ANTLR end "rule__VariableDeclaration__InitValAssignment_1_5"


    // $ANTLR start "rule__Transition__NameAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3863:1: rule__Transition__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Transition__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3867:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3868:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3868:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3869:1: RULE_ID
            {
             before(grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Transition__NameAssignment_17721); 
             after(grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0()); 

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
    // $ANTLR end "rule__Transition__NameAssignment_1"


    // $ANTLR start "rule__Transition__GuardAssignment_3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3878:1: rule__Transition__GuardAssignment_3 : ( ruleOr ) ;
    public final void rule__Transition__GuardAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3882:1: ( ( ruleOr ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3883:1: ( ruleOr )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3883:1: ( ruleOr )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3884:1: ruleOr
            {
             before(grammarAccess.getTransitionAccess().getGuardOrParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleOr_in_rule__Transition__GuardAssignment_37752);
            ruleOr();

            state._fsp--;

             after(grammarAccess.getTransitionAccess().getGuardOrParserRuleCall_3_0()); 

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
    // $ANTLR end "rule__Transition__GuardAssignment_3"


    // $ANTLR start "rule__Transition__LabelAssignment_5_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3893:1: rule__Transition__LabelAssignment_5_1 : ( RULE_STRING ) ;
    public final void rule__Transition__LabelAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3897:1: ( ( RULE_STRING ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3898:1: ( RULE_STRING )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3898:1: ( RULE_STRING )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3899:1: RULE_STRING
            {
             before(grammarAccess.getTransitionAccess().getLabelSTRINGTerminalRuleCall_5_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Transition__LabelAssignment_5_17783); 
             after(grammarAccess.getTransitionAccess().getLabelSTRINGTerminalRuleCall_5_1_0()); 

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
    // $ANTLR end "rule__Transition__LabelAssignment_5_1"


    // $ANTLR start "rule__Transition__AssignmentsAssignment_7"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3908:1: rule__Transition__AssignmentsAssignment_7 : ( ruleAssignment ) ;
    public final void rule__Transition__AssignmentsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3912:1: ( ( ruleAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3913:1: ( ruleAssignment )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3913:1: ( ruleAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3914:1: ruleAssignment
            {
             before(grammarAccess.getTransitionAccess().getAssignmentsAssignmentParserRuleCall_7_0()); 
            pushFollow(FOLLOW_ruleAssignment_in_rule__Transition__AssignmentsAssignment_77814);
            ruleAssignment();

            state._fsp--;

             after(grammarAccess.getTransitionAccess().getAssignmentsAssignmentParserRuleCall_7_0()); 

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
    // $ANTLR end "rule__Transition__AssignmentsAssignment_7"


    // $ANTLR start "rule__Assignment__VarAssignment_0_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3923:1: rule__Assignment__VarAssignment_0_0 : ( ruleVariableRef ) ;
    public final void rule__Assignment__VarAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3927:1: ( ( ruleVariableRef ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3928:1: ( ruleVariableRef )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3928:1: ( ruleVariableRef )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3929:1: ruleVariableRef
            {
             before(grammarAccess.getAssignmentAccess().getVarVariableRefParserRuleCall_0_0_0()); 
            pushFollow(FOLLOW_ruleVariableRef_in_rule__Assignment__VarAssignment_0_07845);
            ruleVariableRef();

            state._fsp--;

             after(grammarAccess.getAssignmentAccess().getVarVariableRefParserRuleCall_0_0_0()); 

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
    // $ANTLR end "rule__Assignment__VarAssignment_0_0"


    // $ANTLR start "rule__Assignment__ExprAssignment_0_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3938:1: rule__Assignment__ExprAssignment_0_2 : ( ruleAddition ) ;
    public final void rule__Assignment__ExprAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3942:1: ( ( ruleAddition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3943:1: ( ruleAddition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3943:1: ( ruleAddition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3944:1: ruleAddition
            {
             before(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_0_2_0()); 
            pushFollow(FOLLOW_ruleAddition_in_rule__Assignment__ExprAssignment_0_27876);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_0_2_0()); 

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
    // $ANTLR end "rule__Assignment__ExprAssignment_0_2"


    // $ANTLR start "rule__Assignment__AVarAssignment_1_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3953:1: rule__Assignment__AVarAssignment_1_0 : ( ruleVariableRef ) ;
    public final void rule__Assignment__AVarAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3957:1: ( ( ruleVariableRef ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3958:1: ( ruleVariableRef )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3958:1: ( ruleVariableRef )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3959:1: ruleVariableRef
            {
             before(grammarAccess.getAssignmentAccess().getAVarVariableRefParserRuleCall_1_0_0()); 
            pushFollow(FOLLOW_ruleVariableRef_in_rule__Assignment__AVarAssignment_1_07907);
            ruleVariableRef();

            state._fsp--;

             after(grammarAccess.getAssignmentAccess().getAVarVariableRefParserRuleCall_1_0_0()); 

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
    // $ANTLR end "rule__Assignment__AVarAssignment_1_0"


    // $ANTLR start "rule__Assignment__IndexAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3968:1: rule__Assignment__IndexAssignment_1_2 : ( ruleAddition ) ;
    public final void rule__Assignment__IndexAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3972:1: ( ( ruleAddition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3973:1: ( ruleAddition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3973:1: ( ruleAddition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3974:1: ruleAddition
            {
             before(grammarAccess.getAssignmentAccess().getIndexAdditionParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_ruleAddition_in_rule__Assignment__IndexAssignment_1_27938);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getAssignmentAccess().getIndexAdditionParserRuleCall_1_2_0()); 

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
    // $ANTLR end "rule__Assignment__IndexAssignment_1_2"


    // $ANTLR start "rule__Assignment__ExprAssignment_1_5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3983:1: rule__Assignment__ExprAssignment_1_5 : ( ruleAddition ) ;
    public final void rule__Assignment__ExprAssignment_1_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3987:1: ( ( ruleAddition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3988:1: ( ruleAddition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3988:1: ( ruleAddition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3989:1: ruleAddition
            {
             before(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_1_5_0()); 
            pushFollow(FOLLOW_ruleAddition_in_rule__Assignment__ExprAssignment_1_57969);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_1_5_0()); 

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
    // $ANTLR end "rule__Assignment__ExprAssignment_1_5"


    // $ANTLR start "rule__Addition__RightAssignment_1_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3998:1: rule__Addition__RightAssignment_1_1 : ( ruleMultiplication ) ;
    public final void rule__Addition__RightAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4002:1: ( ( ruleMultiplication ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4003:1: ( ruleMultiplication )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4003:1: ( ruleMultiplication )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4004:1: ruleMultiplication
            {
             before(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleMultiplication_in_rule__Addition__RightAssignment_1_18000);
            ruleMultiplication();

            state._fsp--;

             after(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_1_0()); 

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
    // $ANTLR end "rule__Addition__RightAssignment_1_1"


    // $ANTLR start "rule__Multiplication__RightAssignment_1_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4013:1: rule__Multiplication__RightAssignment_1_1 : ( ruleUnitaryMinus ) ;
    public final void rule__Multiplication__RightAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4017:1: ( ( ruleUnitaryMinus ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4018:1: ( ruleUnitaryMinus )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4018:1: ( ruleUnitaryMinus )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4019:1: ruleUnitaryMinus
            {
             before(grammarAccess.getMultiplicationAccess().getRightUnitaryMinusParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleUnitaryMinus_in_rule__Multiplication__RightAssignment_1_18031);
            ruleUnitaryMinus();

            state._fsp--;

             after(grammarAccess.getMultiplicationAccess().getRightUnitaryMinusParserRuleCall_1_1_0()); 

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
    // $ANTLR end "rule__Multiplication__RightAssignment_1_1"


    // $ANTLR start "rule__Power__RightAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4028:1: rule__Power__RightAssignment_1_2 : ( rulePrimary ) ;
    public final void rule__Power__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4032:1: ( ( rulePrimary ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4033:1: ( rulePrimary )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4033:1: ( rulePrimary )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4034:1: rulePrimary
            {
             before(grammarAccess.getPowerAccess().getRightPrimaryParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_rulePrimary_in_rule__Power__RightAssignment_1_28062);
            rulePrimary();

            state._fsp--;

             after(grammarAccess.getPowerAccess().getRightPrimaryParserRuleCall_1_2_0()); 

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
    // $ANTLR end "rule__Power__RightAssignment_1_2"


    // $ANTLR start "rule__Constante__ValAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4043:1: rule__Constante__ValAssignment : ( RULE_INT ) ;
    public final void rule__Constante__ValAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4047:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4048:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4048:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4049:1: RULE_INT
            {
             before(grammarAccess.getConstanteAccess().getValINTTerminalRuleCall_0()); 
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__Constante__ValAssignment8093); 
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


    // $ANTLR start "rule__VariableRef__VarAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4058:1: rule__VariableRef__VarAssignment : ( ( RULE_ID ) ) ;
    public final void rule__VariableRef__VarAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4062:1: ( ( ( RULE_ID ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4063:1: ( ( RULE_ID ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4063:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4064:1: ( RULE_ID )
            {
             before(grammarAccess.getVariableRefAccess().getVarVariableCrossReference_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4065:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4066:1: RULE_ID
            {
             before(grammarAccess.getVariableRefAccess().getVarVariableIDTerminalRuleCall_0_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__VariableRef__VarAssignment8128); 
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


    // $ANTLR start "rule__Or__RightAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4077:1: rule__Or__RightAssignment_1_2 : ( ruleAnd ) ;
    public final void rule__Or__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4081:1: ( ( ruleAnd ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4082:1: ( ruleAnd )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4082:1: ( ruleAnd )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4083:1: ruleAnd
            {
             before(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_ruleAnd_in_rule__Or__RightAssignment_1_28163);
            ruleAnd();

            state._fsp--;

             after(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0()); 

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
    // $ANTLR end "rule__Or__RightAssignment_1_2"


    // $ANTLR start "rule__And__RightAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4092:1: rule__And__RightAssignment_1_2 : ( ruleNot ) ;
    public final void rule__And__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4096:1: ( ( ruleNot ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4097:1: ( ruleNot )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4097:1: ( ruleNot )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4098:1: ruleNot
            {
             before(grammarAccess.getAndAccess().getRightNotParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_ruleNot_in_rule__And__RightAssignment_1_28194);
            ruleNot();

            state._fsp--;

             after(grammarAccess.getAndAccess().getRightNotParserRuleCall_1_2_0()); 

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
    // $ANTLR end "rule__And__RightAssignment_1_2"


    // $ANTLR start "rule__True__ValueAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4107:1: rule__True__ValueAssignment : ( ( 'True' ) ) ;
    public final void rule__True__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4111:1: ( ( ( 'True' ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4112:1: ( ( 'True' ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4112:1: ( ( 'True' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4113:1: ( 'True' )
            {
             before(grammarAccess.getTrueAccess().getValueTrueKeyword_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4114:1: ( 'True' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4115:1: 'True'
            {
             before(grammarAccess.getTrueAccess().getValueTrueKeyword_0()); 
            match(input,39,FOLLOW_39_in_rule__True__ValueAssignment8230); 
             after(grammarAccess.getTrueAccess().getValueTrueKeyword_0()); 

            }

             after(grammarAccess.getTrueAccess().getValueTrueKeyword_0()); 

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
    // $ANTLR end "rule__True__ValueAssignment"


    // $ANTLR start "rule__False__ValueAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4130:1: rule__False__ValueAssignment : ( ( 'False' ) ) ;
    public final void rule__False__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4134:1: ( ( ( 'False' ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4135:1: ( ( 'False' ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4135:1: ( ( 'False' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4136:1: ( 'False' )
            {
             before(grammarAccess.getFalseAccess().getValueFalseKeyword_0()); 
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4137:1: ( 'False' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4138:1: 'False'
            {
             before(grammarAccess.getFalseAccess().getValueFalseKeyword_0()); 
            match(input,40,FOLLOW_40_in_rule__False__ValueAssignment8274); 
             after(grammarAccess.getFalseAccess().getValueFalseKeyword_0()); 

            }

             after(grammarAccess.getFalseAccess().getValueFalseKeyword_0()); 

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
    // $ANTLR end "rule__False__ValueAssignment"


    // $ANTLR start "rule__Comparison__LeftAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4153:1: rule__Comparison__LeftAssignment_0 : ( ruleAddition ) ;
    public final void rule__Comparison__LeftAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4157:1: ( ( ruleAddition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4158:1: ( ruleAddition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4158:1: ( ruleAddition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4159:1: ruleAddition
            {
             before(grammarAccess.getComparisonAccess().getLeftAdditionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleAddition_in_rule__Comparison__LeftAssignment_08313);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getComparisonAccess().getLeftAdditionParserRuleCall_0_0()); 

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
    // $ANTLR end "rule__Comparison__LeftAssignment_0"


    // $ANTLR start "rule__Comparison__OperatorAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4168:1: rule__Comparison__OperatorAssignment_1 : ( ruleCompOperators ) ;
    public final void rule__Comparison__OperatorAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4172:1: ( ( ruleCompOperators ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4173:1: ( ruleCompOperators )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4173:1: ( ruleCompOperators )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4174:1: ruleCompOperators
            {
             before(grammarAccess.getComparisonAccess().getOperatorCompOperatorsParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleCompOperators_in_rule__Comparison__OperatorAssignment_18344);
            ruleCompOperators();

            state._fsp--;

             after(grammarAccess.getComparisonAccess().getOperatorCompOperatorsParserRuleCall_1_0()); 

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
    // $ANTLR end "rule__Comparison__OperatorAssignment_1"


    // $ANTLR start "rule__Comparison__RightAssignment_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4183:1: rule__Comparison__RightAssignment_2 : ( ruleAddition ) ;
    public final void rule__Comparison__RightAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4187:1: ( ( ruleAddition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4188:1: ( ruleAddition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4188:1: ( ruleAddition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4189:1: ruleAddition
            {
             before(grammarAccess.getComparisonAccess().getRightAdditionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleAddition_in_rule__Comparison__RightAssignment_28375);
            ruleAddition();

            state._fsp--;

             after(grammarAccess.getComparisonAccess().getRightAdditionParserRuleCall_2_0()); 

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
    // $ANTLR end "rule__Comparison__RightAssignment_2"

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    static final String DFA2_eotS =
        "\6\uffff";
    static final String DFA2_eofS =
        "\6\uffff";
    static final String DFA2_minS =
        "\1\4\1\24\1\4\2\uffff\1\24";
    static final String DFA2_maxS =
        "\1\4\1\45\1\4\2\uffff\1\45";
    static final String DFA2_acceptS =
        "\3\uffff\1\1\1\2\1\uffff";
    static final String DFA2_specialS =
        "\6\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1",
            "\1\3\1\uffff\1\4\16\uffff\1\2",
            "\1\5",
            "",
            "",
            "\1\3\1\uffff\1\4\16\uffff\1\2"
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "650:1: rule__VariableDeclaration__Alternatives : ( ( ( rule__VariableDeclaration__Group_0__0 ) ) | ( ( rule__VariableDeclaration__Group_1__0 ) ) );";
        }
    }
 

    public static final BitSet FOLLOW_ruleSystem_in_entryRuleSystem61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSystem68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__0_in_ruleSystem94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration122 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableDeclaration129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Alternatives_in_ruleVariableDeclaration155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransition_in_entryRuleTransition182 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransition189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__0_in_ruleTransition215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_entryRuleAssignment242 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssignment249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Alternatives_in_ruleAssignment275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_entryRuleAddition302 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAddition309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group__0_in_ruleAddition335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_entryRuleMultiplication362 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplication369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group__0_in_ruleMultiplication395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnitaryMinus_in_entryRuleUnitaryMinus422 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnitaryMinus429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnitaryMinus__Group__0_in_ruleUnitaryMinus455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePower_in_entryRulePower482 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePower489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Power__Group__0_in_rulePower515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_entryRulePrimary542 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimary549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Alternatives_in_rulePrimary575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_entryRuleConstante602 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstante609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Constante__ValAssignment_in_ruleConstante635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_entryRuleVariableRef662 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableRef669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableRef__VarAssignment_in_ruleVariableRef695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_entryRuleOr722 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOr729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group__0_in_ruleOr755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_entryRuleAnd782 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAnd789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group__0_in_ruleAnd815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_entryRuleNot842 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNot849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Not__Group__0_in_ruleNot875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool902 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryBool909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryBool__Alternatives_in_rulePrimaryBool935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_entryRuleTrue962 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTrue969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__True__ValueAssignment_in_ruleTrue995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_entryRuleFalse1022 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFalse1029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__False__ValueAssignment_in_ruleFalse1055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_entryRuleComparison1082 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComparison1089 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__Group__0_in_ruleComparison1115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCompOperators_in_entryRuleCompOperators1142 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCompOperators1149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__CompOperators__Alternatives_in_ruleCompOperators1175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName1202 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedName1209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group__0_in_ruleQualifiedName1235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedNameWithWildCard_in_entryRuleQualifiedNameWithWildCard1262 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedNameWithWildCard1269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedNameWithWildCard__Group__0_in_ruleQualifiedNameWithWildCard1295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_0__0_in_rule__VariableDeclaration__Alternatives1333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__0_in_rule__VariableDeclaration__Alternatives1351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_0__0_in_rule__Assignment__Alternatives1384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__0_in_rule__Assignment__Alternatives1402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1_0_0__0_in_rule__Addition__Alternatives_1_01435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1_0_1__0_in_rule__Addition__Alternatives_1_01453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_0__0_in_rule__Multiplication__Alternatives_1_01486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_1__0_in_rule__Multiplication__Alternatives_1_01504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_2__0_in_rule__Multiplication__Alternatives_1_01522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rule__Primary__Alternatives1555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_rule__Primary__Alternatives1572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__0_in_rule__Primary__Alternatives1589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_rule__Not__Alternatives_11622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_rule__Not__Alternatives_11639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_rule__PrimaryBool__Alternatives1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_rule__PrimaryBool__Alternatives1688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__CompOperators__Alternatives1721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__CompOperators__Alternatives1741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__CompOperators__Alternatives1761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__CompOperators__Alternatives1781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__CompOperators__Alternatives1801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__CompOperators__Alternatives1821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__0__Impl_in_rule__System__Group__01853 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__System__Group__1_in_rule__System__Group__01856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__System__Group__0__Impl1884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__1__Impl_in_rule__System__Group__11915 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__System__Group__2_in_rule__System__Group__11918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__NameAssignment_1_in_rule__System__Group__1__Impl1945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__2__Impl_in_rule__System__Group__21975 = new BitSet(new long[]{0x0000000001000010L});
    public static final BitSet FOLLOW_rule__System__Group__3_in_rule__System__Group__21978 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__System__Group__2__Impl2006 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__3__Impl_in_rule__System__Group__32037 = new BitSet(new long[]{0x0000000001000010L});
    public static final BitSet FOLLOW_rule__System__Group__4_in_rule__System__Group__32040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__VariablesAssignment_3_in_rule__System__Group__3__Impl2067 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__System__Group__4__Impl_in_rule__System__Group__42098 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__System__Group__5_in_rule__System__Group__42101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__TransitionsAssignment_4_in_rule__System__Group__4__Impl2130 = new BitSet(new long[]{0x0000000001000012L});
    public static final BitSet FOLLOW_rule__System__TransitionsAssignment_4_in_rule__System__Group__4__Impl2142 = new BitSet(new long[]{0x0000000001000012L});
    public static final BitSet FOLLOW_rule__System__Group__5__Impl_in_rule__System__Group__52175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__System__Group__5__Impl2203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_0__0__Impl_in_rule__VariableDeclaration__Group_0__02246 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_0__1_in_rule__VariableDeclaration__Group_0__02249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__NameAssignment_0_0_in_rule__VariableDeclaration__Group_0__0__Impl2276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_0__1__Impl_in_rule__VariableDeclaration__Group_0__12306 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_0__2_in_rule__VariableDeclaration__Group_0__12309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__VariableDeclaration__Group_0__1__Impl2337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_0__2__Impl_in_rule__VariableDeclaration__Group_0__22368 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_0__3_in_rule__VariableDeclaration__Group_0__22371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__InitValAssignment_0_2_in_rule__VariableDeclaration__Group_0__2__Impl2398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_0__3__Impl_in_rule__VariableDeclaration__Group_0__32428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__VariableDeclaration__Group_0__3__Impl2456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__0__Impl_in_rule__VariableDeclaration__Group_1__02495 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__1_in_rule__VariableDeclaration__Group_1__02498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__AnameAssignment_1_0_in_rule__VariableDeclaration__Group_1__0__Impl2525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__1__Impl_in_rule__VariableDeclaration__Group_1__12555 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__2_in_rule__VariableDeclaration__Group_1__12558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__VariableDeclaration__Group_1__1__Impl2586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__2__Impl_in_rule__VariableDeclaration__Group_1__22617 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__3_in_rule__VariableDeclaration__Group_1__22620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__IndexAssignment_1_2_in_rule__VariableDeclaration__Group_1__2__Impl2647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__3__Impl_in_rule__VariableDeclaration__Group_1__32677 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__4_in_rule__VariableDeclaration__Group_1__32680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__VariableDeclaration__Group_1__3__Impl2708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__4__Impl_in_rule__VariableDeclaration__Group_1__42739 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__5_in_rule__VariableDeclaration__Group_1__42742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__VariableDeclaration__Group_1__4__Impl2770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__5__Impl_in_rule__VariableDeclaration__Group_1__52801 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__6_in_rule__VariableDeclaration__Group_1__52804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__InitValAssignment_1_5_in_rule__VariableDeclaration__Group_1__5__Impl2831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group_1__6__Impl_in_rule__VariableDeclaration__Group_1__62861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__VariableDeclaration__Group_1__6__Impl2889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__0__Impl_in_rule__Transition__Group__02934 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Transition__Group__1_in_rule__Transition__Group__02937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__Transition__Group__0__Impl2965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__1__Impl_in_rule__Transition__Group__12996 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_rule__Transition__Group__2_in_rule__Transition__Group__12999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__NameAssignment_1_in_rule__Transition__Group__1__Impl3026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__2__Impl_in_rule__Transition__Group__23056 = new BitSet(new long[]{0x0000019108000030L});
    public static final BitSet FOLLOW_rule__Transition__Group__3_in_rule__Transition__Group__23059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__Transition__Group__2__Impl3087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__3__Impl_in_rule__Transition__Group__33118 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_rule__Transition__Group__4_in_rule__Transition__Group__33121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__GuardAssignment_3_in_rule__Transition__Group__3__Impl3148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__4__Impl_in_rule__Transition__Group__43178 = new BitSet(new long[]{0x0000000002040000L});
    public static final BitSet FOLLOW_rule__Transition__Group__5_in_rule__Transition__Group__43181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__Transition__Group__4__Impl3209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__5__Impl_in_rule__Transition__Group__53240 = new BitSet(new long[]{0x0000000002040000L});
    public static final BitSet FOLLOW_rule__Transition__Group__6_in_rule__Transition__Group__53243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group_5__0_in_rule__Transition__Group__5__Impl3270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__6__Impl_in_rule__Transition__Group__63301 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Transition__Group__7_in_rule__Transition__Group__63304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__Transition__Group__6__Impl3332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__7__Impl_in_rule__Transition__Group__73363 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__Transition__Group__8_in_rule__Transition__Group__73366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__AssignmentsAssignment_7_in_rule__Transition__Group__7__Impl3395 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Transition__AssignmentsAssignment_7_in_rule__Transition__Group__7__Impl3407 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Transition__Group__8__Impl_in_rule__Transition__Group__83440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__Transition__Group__8__Impl3468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group_5__0__Impl_in_rule__Transition__Group_5__03517 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_rule__Transition__Group_5__1_in_rule__Transition__Group_5__03520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__Transition__Group_5__0__Impl3548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group_5__1__Impl_in_rule__Transition__Group_5__13579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__LabelAssignment_5_1_in_rule__Transition__Group_5__1__Impl3606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_0__0__Impl_in_rule__Assignment__Group_0__03640 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_rule__Assignment__Group_0__1_in_rule__Assignment__Group_0__03643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__VarAssignment_0_0_in_rule__Assignment__Group_0__0__Impl3670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_0__1__Impl_in_rule__Assignment__Group_0__13700 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__Assignment__Group_0__2_in_rule__Assignment__Group_0__13703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Assignment__Group_0__1__Impl3731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_0__2__Impl_in_rule__Assignment__Group_0__23762 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__Assignment__Group_0__3_in_rule__Assignment__Group_0__23765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__ExprAssignment_0_2_in_rule__Assignment__Group_0__2__Impl3792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_0__3__Impl_in_rule__Assignment__Group_0__33822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Assignment__Group_0__3__Impl3850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__0__Impl_in_rule__Assignment__Group_1__03889 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__1_in_rule__Assignment__Group_1__03892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__AVarAssignment_1_0_in_rule__Assignment__Group_1__0__Impl3919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__1__Impl_in_rule__Assignment__Group_1__13949 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__2_in_rule__Assignment__Group_1__13952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__Assignment__Group_1__1__Impl3980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__2__Impl_in_rule__Assignment__Group_1__24011 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__3_in_rule__Assignment__Group_1__24014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__IndexAssignment_1_2_in_rule__Assignment__Group_1__2__Impl4041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__3__Impl_in_rule__Assignment__Group_1__34071 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__4_in_rule__Assignment__Group_1__34074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__Assignment__Group_1__3__Impl4102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__4__Impl_in_rule__Assignment__Group_1__44133 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__5_in_rule__Assignment__Group_1__44136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Assignment__Group_1__4__Impl4164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__5__Impl_in_rule__Assignment__Group_1__54195 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__6_in_rule__Assignment__Group_1__54198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__ExprAssignment_1_5_in_rule__Assignment__Group_1__5__Impl4225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group_1__6__Impl_in_rule__Assignment__Group_1__64255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Assignment__Group_1__6__Impl4283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group__0__Impl_in_rule__Addition__Group__04328 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_rule__Addition__Group__1_in_rule__Addition__Group__04331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_rule__Addition__Group__0__Impl4358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group__1__Impl_in_rule__Addition__Group__14387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__0_in_rule__Addition__Group__1__Impl4414 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__0__Impl_in_rule__Addition__Group_1__04449 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__1_in_rule__Addition__Group_1__04452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Alternatives_1_0_in_rule__Addition__Group_1__0__Impl4479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1__1__Impl_in_rule__Addition__Group_1__14509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__RightAssignment_1_1_in_rule__Addition__Group_1__1__Impl4536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1_0_0__0__Impl_in_rule__Addition__Group_1_0_0__04570 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_rule__Addition__Group_1_0_0__1_in_rule__Addition__Group_1_0_0__04573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1_0_0__1__Impl_in_rule__Addition__Group_1_0_0__14631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__Addition__Group_1_0_0__1__Impl4659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1_0_1__0__Impl_in_rule__Addition__Group_1_0_1__04694 = new BitSet(new long[]{0x000000000C000000L});
    public static final BitSet FOLLOW_rule__Addition__Group_1_0_1__1_in_rule__Addition__Group_1_0_1__04697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Addition__Group_1_0_1__1__Impl_in_rule__Addition__Group_1_0_1__14755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__Addition__Group_1_0_1__1__Impl4783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group__0__Impl_in_rule__Multiplication__Group__04818 = new BitSet(new long[]{0x0000000070000000L});
    public static final BitSet FOLLOW_rule__Multiplication__Group__1_in_rule__Multiplication__Group__04821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnitaryMinus_in_rule__Multiplication__Group__0__Impl4848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group__1__Impl_in_rule__Multiplication__Group__14877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__0_in_rule__Multiplication__Group__1__Impl4904 = new BitSet(new long[]{0x0000000070000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__0__Impl_in_rule__Multiplication__Group_1__04939 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__1_in_rule__Multiplication__Group_1__04942 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Alternatives_1_0_in_rule__Multiplication__Group_1__0__Impl4969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1__1__Impl_in_rule__Multiplication__Group_1__14999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__RightAssignment_1_1_in_rule__Multiplication__Group_1__1__Impl5026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_0__0__Impl_in_rule__Multiplication__Group_1_0_0__05060 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_0__1_in_rule__Multiplication__Group_1_0_0__05063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_0__1__Impl_in_rule__Multiplication__Group_1_0_0__15121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Multiplication__Group_1_0_0__1__Impl5149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_1__0__Impl_in_rule__Multiplication__Group_1_0_1__05184 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_1__1_in_rule__Multiplication__Group_1_0_1__05187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_1__1__Impl_in_rule__Multiplication__Group_1_0_1__15245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__Multiplication__Group_1_0_1__1__Impl5273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_2__0__Impl_in_rule__Multiplication__Group_1_0_2__05308 = new BitSet(new long[]{0x0000000070000000L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_2__1_in_rule__Multiplication__Group_1_0_2__05311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Multiplication__Group_1_0_2__1__Impl_in_rule__Multiplication__Group_1_0_2__15369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__Multiplication__Group_1_0_2__1__Impl5397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnitaryMinus__Group__0__Impl_in_rule__UnitaryMinus__Group__05432 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__UnitaryMinus__Group__1_in_rule__UnitaryMinus__Group__05435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__UnitaryMinus__Group__0__Impl5464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnitaryMinus__Group__1__Impl_in_rule__UnitaryMinus__Group__15497 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_rule__UnitaryMinus__Group__2_in_rule__UnitaryMinus__Group__15500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePower_in_rule__UnitaryMinus__Group__1__Impl5527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnitaryMinus__Group__2__Impl_in_rule__UnitaryMinus__Group__25556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Power__Group__0__Impl_in_rule__Power__Group__05620 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rule__Power__Group__1_in_rule__Power__Group__05623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_rule__Power__Group__0__Impl5650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Power__Group__1__Impl_in_rule__Power__Group__15679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Power__Group_1__0_in_rule__Power__Group__1__Impl5706 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_rule__Power__Group_1__0__Impl_in_rule__Power__Group_1__05741 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rule__Power__Group_1__1_in_rule__Power__Group_1__05744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Power__Group_1__1__Impl_in_rule__Power__Group_1__15802 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__Power__Group_1__2_in_rule__Power__Group_1__15805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__Power__Group_1__1__Impl5833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Power__Group_1__2__Impl_in_rule__Power__Group_1__25864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Power__RightAssignment_1_2_in_rule__Power__Group_1__2__Impl5891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__0__Impl_in_rule__Primary__Group_2__05927 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__1_in_rule__Primary__Group_2__05930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__Primary__Group_2__0__Impl5958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__1__Impl_in_rule__Primary__Group_2__15989 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__2_in_rule__Primary__Group_2__15992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_rule__Primary__Group_2__1__Impl6019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_2__2__Impl_in_rule__Primary__Group_2__26048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__Primary__Group_2__2__Impl6076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group__0__Impl_in_rule__Or__Group__06113 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_rule__Or__Group__1_in_rule__Or__Group__06116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_rule__Or__Group__0__Impl6143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group__1__Impl_in_rule__Or__Group__16172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group_1__0_in_rule__Or__Group__1__Impl6199 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_rule__Or__Group_1__0__Impl_in_rule__Or__Group_1__06234 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_rule__Or__Group_1__1_in_rule__Or__Group_1__06237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group_1__1__Impl_in_rule__Or__Group_1__16295 = new BitSet(new long[]{0x0000019108000030L});
    public static final BitSet FOLLOW_rule__Or__Group_1__2_in_rule__Or__Group_1__16298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__Or__Group_1__1__Impl6326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group_1__2__Impl_in_rule__Or__Group_1__26357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__RightAssignment_1_2_in_rule__Or__Group_1__2__Impl6384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group__0__Impl_in_rule__And__Group__06420 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_rule__And__Group__1_in_rule__And__Group__06423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_rule__And__Group__0__Impl6450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group__1__Impl_in_rule__And__Group__16479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group_1__0_in_rule__And__Group__1__Impl6506 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_rule__And__Group_1__0__Impl_in_rule__And__Group_1__06541 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_rule__And__Group_1__1_in_rule__And__Group_1__06544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group_1__1__Impl_in_rule__And__Group_1__16602 = new BitSet(new long[]{0x0000019108000030L});
    public static final BitSet FOLLOW_rule__And__Group_1__2_in_rule__And__Group_1__16605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_rule__And__Group_1__1__Impl6633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group_1__2__Impl_in_rule__And__Group_1__26664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__RightAssignment_1_2_in_rule__And__Group_1__2__Impl6691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Not__Group__0__Impl_in_rule__Not__Group__06727 = new BitSet(new long[]{0x0000019108000030L});
    public static final BitSet FOLLOW_rule__Not__Group__1_in_rule__Not__Group__06730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_rule__Not__Group__0__Impl6759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Not__Group__1__Impl_in_rule__Not__Group__16792 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_rule__Not__Group__2_in_rule__Not__Group__16795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Not__Alternatives_1_in_rule__Not__Group__1__Impl6822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Not__Group__2__Impl_in_rule__Not__Group__26852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__Group__0__Impl_in_rule__Comparison__Group__06916 = new BitSet(new long[]{0x000000000001F800L});
    public static final BitSet FOLLOW_rule__Comparison__Group__1_in_rule__Comparison__Group__06919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__LeftAssignment_0_in_rule__Comparison__Group__0__Impl6946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__Group__1__Impl_in_rule__Comparison__Group__16976 = new BitSet(new long[]{0x0000000108000030L});
    public static final BitSet FOLLOW_rule__Comparison__Group__2_in_rule__Comparison__Group__16979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__OperatorAssignment_1_in_rule__Comparison__Group__1__Impl7006 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__Group__2__Impl_in_rule__Comparison__Group__27036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__RightAssignment_2_in_rule__Comparison__Group__2__Impl7063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group__0__Impl_in_rule__QualifiedName__Group__07099 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group__1_in_rule__QualifiedName__Group__07102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__QualifiedName__Group__0__Impl7129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group__1__Impl_in_rule__QualifiedName__Group__17158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group_1__0_in_rule__QualifiedName__Group__1__Impl7185 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group_1__0__Impl_in_rule__QualifiedName__Group_1__07220 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group_1__1_in_rule__QualifiedName__Group_1__07223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__QualifiedName__Group_1__0__Impl7251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group_1__1__Impl_in_rule__QualifiedName__Group_1__17282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__QualifiedName__Group_1__1__Impl7309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedNameWithWildCard__Group__0__Impl_in_rule__QualifiedNameWithWildCard__Group__07342 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_rule__QualifiedNameWithWildCard__Group__1_in_rule__QualifiedNameWithWildCard__Group__07345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rule__QualifiedNameWithWildCard__Group__0__Impl7372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedNameWithWildCard__Group__1__Impl_in_rule__QualifiedNameWithWildCard__Group__17401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__QualifiedNameWithWildCard__Group__1__Impl7430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rule__System__NameAssignment_17473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_rule__System__VariablesAssignment_37504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransition_in_rule__System__TransitionsAssignment_47535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rule__VariableDeclaration__NameAssignment_0_07566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__VariableDeclaration__InitValAssignment_0_27597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rule__VariableDeclaration__AnameAssignment_1_07628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_rule__VariableDeclaration__IndexAssignment_1_27659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__VariableDeclaration__InitValAssignment_1_57690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Transition__NameAssignment_17721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_rule__Transition__GuardAssignment_37752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Transition__LabelAssignment_5_17783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_rule__Transition__AssignmentsAssignment_77814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rule__Assignment__VarAssignment_0_07845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_rule__Assignment__ExprAssignment_0_27876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rule__Assignment__AVarAssignment_1_07907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_rule__Assignment__IndexAssignment_1_27938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_rule__Assignment__ExprAssignment_1_57969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_rule__Addition__RightAssignment_1_18000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnitaryMinus_in_rule__Multiplication__RightAssignment_1_18031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_rule__Power__RightAssignment_1_28062 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__Constante__ValAssignment8093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__VariableRef__VarAssignment8128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_rule__Or__RightAssignment_1_28163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_rule__And__RightAssignment_1_28194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_rule__True__ValueAssignment8230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_rule__False__ValueAssignment8274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_rule__Comparison__LeftAssignment_08313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCompOperators_in_rule__Comparison__OperatorAssignment_18344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_rule__Comparison__RightAssignment_28375 = new BitSet(new long[]{0x0000000000000002L});

}