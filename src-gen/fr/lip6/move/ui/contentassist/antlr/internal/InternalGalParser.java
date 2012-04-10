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
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalGalParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'+'", "'-'", "'/'", "'*'", "'%'", "'**'", "'>'", "'<'", "'>='", "'<='", "'=='", "'!='", "'GAL'", "'{'", "'}'", "'int'", "'='", "';'", "'array'", "'['", "']'", "'('", "')'", "'list'", "','", "'transition'", "'label'", "'.push'", "'.pop'", "'.peek'", "'||'", "'&&'", "'!'", "'.'", "'.*'", "'True'", "'False'"
    };
    public static final int RULE_ID=4;
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
    public static final int EOF=-1;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=5;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_SL_COMMENT=8;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_STRING=6;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:61:1: entryRuleSystem : ruleSystem EOF ;
    public final void entryRuleSystem() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:62:1: ( ruleSystem EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:63:1: ruleSystem EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemRule()); 
            }
            pushFollow(FOLLOW_ruleSystem_in_entryRuleSystem67);
            ruleSystem();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleSystem74); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:70:1: ruleSystem : ( ( rule__System__Group__0 ) ) ;
    public final void ruleSystem() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:74:2: ( ( ( rule__System__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:75:1: ( ( rule__System__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:75:1: ( ( rule__System__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:76:1: ( rule__System__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:77:1: ( rule__System__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:77:2: rule__System__Group__0
            {
            pushFollow(FOLLOW_rule__System__Group__0_in_ruleSystem100);
            rule__System__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getGroup()); 
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
    // $ANTLR end "ruleSystem"


    // $ANTLR start "entryRuleVariableDeclaration"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:89:1: entryRuleVariableDeclaration : ruleVariableDeclaration EOF ;
    public final void entryRuleVariableDeclaration() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:90:1: ( ruleVariableDeclaration EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:91:1: ruleVariableDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableDeclarationRule()); 
            }
            pushFollow(FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration127);
            ruleVariableDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableDeclarationRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableDeclaration134); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:98:1: ruleVariableDeclaration : ( ( rule__VariableDeclaration__Group__0 ) ) ;
    public final void ruleVariableDeclaration() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:102:2: ( ( ( rule__VariableDeclaration__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:103:1: ( ( rule__VariableDeclaration__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:103:1: ( ( rule__VariableDeclaration__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:104:1: ( rule__VariableDeclaration__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableDeclarationAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:105:1: ( rule__VariableDeclaration__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:105:2: rule__VariableDeclaration__Group__0
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__0_in_ruleVariableDeclaration160);
            rule__VariableDeclaration__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableDeclarationAccess().getGroup()); 
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
    // $ANTLR end "ruleVariableDeclaration"


    // $ANTLR start "entryRuleArrayDeclaration"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:117:1: entryRuleArrayDeclaration : ruleArrayDeclaration EOF ;
    public final void entryRuleArrayDeclaration() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:118:1: ( ruleArrayDeclaration EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:119:1: ruleArrayDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationRule()); 
            }
            pushFollow(FOLLOW_ruleArrayDeclaration_in_entryRuleArrayDeclaration187);
            ruleArrayDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleArrayDeclaration194); if (state.failed) return ;

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
    // $ANTLR end "entryRuleArrayDeclaration"


    // $ANTLR start "ruleArrayDeclaration"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:126:1: ruleArrayDeclaration : ( ( rule__ArrayDeclaration__Group__0 ) ) ;
    public final void ruleArrayDeclaration() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:130:2: ( ( ( rule__ArrayDeclaration__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:131:1: ( ( rule__ArrayDeclaration__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:131:1: ( ( rule__ArrayDeclaration__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:132:1: ( rule__ArrayDeclaration__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:133:1: ( rule__ArrayDeclaration__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:133:2: rule__ArrayDeclaration__Group__0
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__0_in_ruleArrayDeclaration220);
            rule__ArrayDeclaration__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getGroup()); 
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
    // $ANTLR end "ruleArrayDeclaration"


    // $ANTLR start "entryRuleListDeclaration"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:145:1: entryRuleListDeclaration : ruleListDeclaration EOF ;
    public final void entryRuleListDeclaration() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:146:1: ( ruleListDeclaration EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:147:1: ruleListDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationRule()); 
            }
            pushFollow(FOLLOW_ruleListDeclaration_in_entryRuleListDeclaration247);
            ruleListDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleListDeclaration254); if (state.failed) return ;

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
    // $ANTLR end "entryRuleListDeclaration"


    // $ANTLR start "ruleListDeclaration"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:154:1: ruleListDeclaration : ( ( rule__ListDeclaration__Group__0 ) ) ;
    public final void ruleListDeclaration() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:158:2: ( ( ( rule__ListDeclaration__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:159:1: ( ( rule__ListDeclaration__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:159:1: ( ( rule__ListDeclaration__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:160:1: ( rule__ListDeclaration__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:161:1: ( rule__ListDeclaration__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:161:2: rule__ListDeclaration__Group__0
            {
            pushFollow(FOLLOW_rule__ListDeclaration__Group__0_in_ruleListDeclaration280);
            rule__ListDeclaration__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getGroup()); 
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
    // $ANTLR end "ruleListDeclaration"


    // $ANTLR start "entryRuleInitValues"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:173:1: entryRuleInitValues : ruleInitValues EOF ;
    public final void entryRuleInitValues() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:174:1: ( ruleInitValues EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:175:1: ruleInitValues EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInitValuesRule()); 
            }
            pushFollow(FOLLOW_ruleInitValues_in_entryRuleInitValues307);
            ruleInitValues();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getInitValuesRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleInitValues314); if (state.failed) return ;

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
    // $ANTLR end "entryRuleInitValues"


    // $ANTLR start "ruleInitValues"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:182:1: ruleInitValues : ( ( rule__InitValues__Group__0 ) ) ;
    public final void ruleInitValues() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:186:2: ( ( ( rule__InitValues__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:187:1: ( ( rule__InitValues__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:187:1: ( ( rule__InitValues__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:188:1: ( rule__InitValues__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInitValuesAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:189:1: ( rule__InitValues__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:189:2: rule__InitValues__Group__0
            {
            pushFollow(FOLLOW_rule__InitValues__Group__0_in_ruleInitValues340);
            rule__InitValues__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getInitValuesAccess().getGroup()); 
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
    // $ANTLR end "ruleInitValues"


    // $ANTLR start "entryRuleTransition"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:201:1: entryRuleTransition : ruleTransition EOF ;
    public final void entryRuleTransition() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:202:1: ( ruleTransition EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:203:1: ruleTransition EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionRule()); 
            }
            pushFollow(FOLLOW_ruleTransition_in_entryRuleTransition367);
            ruleTransition();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransition374); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:210:1: ruleTransition : ( ( rule__Transition__Group__0 ) ) ;
    public final void ruleTransition() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:214:2: ( ( ( rule__Transition__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:215:1: ( ( rule__Transition__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:215:1: ( ( rule__Transition__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:216:1: ( rule__Transition__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:217:1: ( rule__Transition__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:217:2: rule__Transition__Group__0
            {
            pushFollow(FOLLOW_rule__Transition__Group__0_in_ruleTransition400);
            rule__Transition__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getGroup()); 
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
    // $ANTLR end "ruleTransition"


    // $ANTLR start "entryRuleActions"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:229:1: entryRuleActions : ruleActions EOF ;
    public final void entryRuleActions() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:230:1: ( ruleActions EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:231:1: ruleActions EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsRule()); 
            }
            pushFollow(FOLLOW_ruleActions_in_entryRuleActions427);
            ruleActions();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleActions434); if (state.failed) return ;

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
    // $ANTLR end "entryRuleActions"


    // $ANTLR start "ruleActions"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:238:1: ruleActions : ( ( rule__Actions__Alternatives ) ) ;
    public final void ruleActions() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:242:2: ( ( ( rule__Actions__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:243:1: ( ( rule__Actions__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:243:1: ( ( rule__Actions__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:244:1: ( rule__Actions__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getAlternatives()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:245:1: ( rule__Actions__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:245:2: rule__Actions__Alternatives
            {
            pushFollow(FOLLOW_rule__Actions__Alternatives_in_ruleActions460);
            rule__Actions__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getAlternatives()); 
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
    // $ANTLR end "ruleActions"


    // $ANTLR start "entryRulePush"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:257:1: entryRulePush : rulePush EOF ;
    public final void entryRulePush() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:258:1: ( rulePush EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:259:1: rulePush EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushRule()); 
            }
            pushFollow(FOLLOW_rulePush_in_entryRulePush487);
            rulePush();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePush494); if (state.failed) return ;

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
    // $ANTLR end "entryRulePush"


    // $ANTLR start "rulePush"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:266:1: rulePush : ( ( rule__Push__Group__0 ) ) ;
    public final void rulePush() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:270:2: ( ( ( rule__Push__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:271:1: ( ( rule__Push__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:271:1: ( ( rule__Push__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:272:1: ( rule__Push__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:273:1: ( rule__Push__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:273:2: rule__Push__Group__0
            {
            pushFollow(FOLLOW_rule__Push__Group__0_in_rulePush520);
            rule__Push__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getGroup()); 
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
    // $ANTLR end "rulePush"


    // $ANTLR start "entryRulePop"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:285:1: entryRulePop : rulePop EOF ;
    public final void entryRulePop() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:286:1: ( rulePop EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:287:1: rulePop EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPopRule()); 
            }
            pushFollow(FOLLOW_rulePop_in_entryRulePop547);
            rulePop();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPopRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePop554); if (state.failed) return ;

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
    // $ANTLR end "entryRulePop"


    // $ANTLR start "rulePop"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:294:1: rulePop : ( ( rule__Pop__Group__0 ) ) ;
    public final void rulePop() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:298:2: ( ( ( rule__Pop__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:299:1: ( ( rule__Pop__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:299:1: ( ( rule__Pop__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:300:1: ( rule__Pop__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPopAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:301:1: ( rule__Pop__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:301:2: rule__Pop__Group__0
            {
            pushFollow(FOLLOW_rule__Pop__Group__0_in_rulePop580);
            rule__Pop__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPopAccess().getGroup()); 
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
    // $ANTLR end "rulePop"


    // $ANTLR start "entryRulePeek"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:313:1: entryRulePeek : rulePeek EOF ;
    public final void entryRulePeek() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:314:1: ( rulePeek EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:315:1: rulePeek EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPeekRule()); 
            }
            pushFollow(FOLLOW_rulePeek_in_entryRulePeek607);
            rulePeek();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPeekRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePeek614); if (state.failed) return ;

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
    // $ANTLR end "entryRulePeek"


    // $ANTLR start "rulePeek"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:322:1: rulePeek : ( ( rule__Peek__Group__0 ) ) ;
    public final void rulePeek() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:326:2: ( ( ( rule__Peek__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:327:1: ( ( rule__Peek__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:327:1: ( ( rule__Peek__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:328:1: ( rule__Peek__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPeekAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:329:1: ( rule__Peek__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:329:2: rule__Peek__Group__0
            {
            pushFollow(FOLLOW_rule__Peek__Group__0_in_rulePeek640);
            rule__Peek__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPeekAccess().getGroup()); 
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
    // $ANTLR end "rulePeek"


    // $ANTLR start "entryRuleAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:341:1: entryRuleAssignment : ruleAssignment EOF ;
    public final void entryRuleAssignment() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:342:1: ( ruleAssignment EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:343:1: ruleAssignment EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentRule()); 
            }
            pushFollow(FOLLOW_ruleAssignment_in_entryRuleAssignment667);
            ruleAssignment();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssignment674); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:350:1: ruleAssignment : ( ( rule__Assignment__Group__0 ) ) ;
    public final void ruleAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:354:2: ( ( ( rule__Assignment__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:355:1: ( ( rule__Assignment__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:355:1: ( ( rule__Assignment__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:356:1: ( rule__Assignment__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:357:1: ( rule__Assignment__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:357:2: rule__Assignment__Group__0
            {
            pushFollow(FOLLOW_rule__Assignment__Group__0_in_ruleAssignment700);
            rule__Assignment__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getGroup()); 
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
    // $ANTLR end "ruleAssignment"


    // $ANTLR start "entryRuleVarAccess"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:369:1: entryRuleVarAccess : ruleVarAccess EOF ;
    public final void entryRuleVarAccess() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:370:1: ( ruleVarAccess EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:371:1: ruleVarAccess EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVarAccessRule()); 
            }
            pushFollow(FOLLOW_ruleVarAccess_in_entryRuleVarAccess727);
            ruleVarAccess();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVarAccessRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleVarAccess734); if (state.failed) return ;

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
    // $ANTLR end "entryRuleVarAccess"


    // $ANTLR start "ruleVarAccess"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:378:1: ruleVarAccess : ( ( rule__VarAccess__Alternatives ) ) ;
    public final void ruleVarAccess() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:382:2: ( ( ( rule__VarAccess__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:383:1: ( ( rule__VarAccess__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:383:1: ( ( rule__VarAccess__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:384:1: ( rule__VarAccess__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVarAccessAccess().getAlternatives()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:385:1: ( rule__VarAccess__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:385:2: rule__VarAccess__Alternatives
            {
            pushFollow(FOLLOW_rule__VarAccess__Alternatives_in_ruleVarAccess760);
            rule__VarAccess__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVarAccessAccess().getAlternatives()); 
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
    // $ANTLR end "ruleVarAccess"


    // $ANTLR start "entryRuleVariableRef"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:397:1: entryRuleVariableRef : ruleVariableRef EOF ;
    public final void entryRuleVariableRef() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:398:1: ( ruleVariableRef EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:399:1: ruleVariableRef EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableRefRule()); 
            }
            pushFollow(FOLLOW_ruleVariableRef_in_entryRuleVariableRef787);
            ruleVariableRef();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableRefRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableRef794); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:406:1: ruleVariableRef : ( ( rule__VariableRef__VarAssignment ) ) ;
    public final void ruleVariableRef() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:410:2: ( ( ( rule__VariableRef__VarAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:411:1: ( ( rule__VariableRef__VarAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:411:1: ( ( rule__VariableRef__VarAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:412:1: ( rule__VariableRef__VarAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableRefAccess().getVarAssignment()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:413:1: ( rule__VariableRef__VarAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:413:2: rule__VariableRef__VarAssignment
            {
            pushFollow(FOLLOW_rule__VariableRef__VarAssignment_in_ruleVariableRef820);
            rule__VariableRef__VarAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableRefAccess().getVarAssignment()); 
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
    // $ANTLR end "ruleVariableRef"


    // $ANTLR start "entryRuleArrayVarAccess"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:425:1: entryRuleArrayVarAccess : ruleArrayVarAccess EOF ;
    public final void entryRuleArrayVarAccess() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:426:1: ( ruleArrayVarAccess EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:427:1: ruleArrayVarAccess EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayVarAccessRule()); 
            }
            pushFollow(FOLLOW_ruleArrayVarAccess_in_entryRuleArrayVarAccess847);
            ruleArrayVarAccess();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayVarAccessRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleArrayVarAccess854); if (state.failed) return ;

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
    // $ANTLR end "entryRuleArrayVarAccess"


    // $ANTLR start "ruleArrayVarAccess"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:434:1: ruleArrayVarAccess : ( ( rule__ArrayVarAccess__Group__0 ) ) ;
    public final void ruleArrayVarAccess() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:438:2: ( ( ( rule__ArrayVarAccess__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:439:1: ( ( rule__ArrayVarAccess__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:439:1: ( ( rule__ArrayVarAccess__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:440:1: ( rule__ArrayVarAccess__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayVarAccessAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:441:1: ( rule__ArrayVarAccess__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:441:2: rule__ArrayVarAccess__Group__0
            {
            pushFollow(FOLLOW_rule__ArrayVarAccess__Group__0_in_ruleArrayVarAccess880);
            rule__ArrayVarAccess__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayVarAccessAccess().getGroup()); 
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
    // $ANTLR end "ruleArrayVarAccess"


    // $ANTLR start "entryRuleBinaryIntExpression"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:453:1: entryRuleBinaryIntExpression : ruleBinaryIntExpression EOF ;
    public final void entryRuleBinaryIntExpression() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:454:1: ( ruleBinaryIntExpression EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:455:1: ruleBinaryIntExpression EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryIntExpressionRule()); 
            }
            pushFollow(FOLLOW_ruleBinaryIntExpression_in_entryRuleBinaryIntExpression907);
            ruleBinaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryIntExpressionRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBinaryIntExpression914); if (state.failed) return ;

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
    // $ANTLR end "entryRuleBinaryIntExpression"


    // $ANTLR start "ruleBinaryIntExpression"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:462:1: ruleBinaryIntExpression : ( ( rule__BinaryIntExpression__Group__0 ) ) ;
    public final void ruleBinaryIntExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:466:2: ( ( ( rule__BinaryIntExpression__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:467:1: ( ( rule__BinaryIntExpression__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:467:1: ( ( rule__BinaryIntExpression__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:468:1: ( rule__BinaryIntExpression__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryIntExpressionAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:469:1: ( rule__BinaryIntExpression__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:469:2: rule__BinaryIntExpression__Group__0
            {
            pushFollow(FOLLOW_rule__BinaryIntExpression__Group__0_in_ruleBinaryIntExpression940);
            rule__BinaryIntExpression__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryIntExpressionAccess().getGroup()); 
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
    // $ANTLR end "ruleBinaryIntExpression"


    // $ANTLR start "entryRuleUnaryIntExpression"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:481:1: entryRuleUnaryIntExpression : ruleUnaryIntExpression EOF ;
    public final void entryRuleUnaryIntExpression() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:482:1: ( ruleUnaryIntExpression EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:483:1: ruleUnaryIntExpression EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnaryIntExpressionRule()); 
            }
            pushFollow(FOLLOW_ruleUnaryIntExpression_in_entryRuleUnaryIntExpression967);
            ruleUnaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnaryIntExpressionRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnaryIntExpression974); if (state.failed) return ;

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
    // $ANTLR end "entryRuleUnaryIntExpression"


    // $ANTLR start "ruleUnaryIntExpression"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:490:1: ruleUnaryIntExpression : ( ( rule__UnaryIntExpression__Group__0 ) ) ;
    public final void ruleUnaryIntExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:494:2: ( ( ( rule__UnaryIntExpression__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:495:1: ( ( rule__UnaryIntExpression__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:495:1: ( ( rule__UnaryIntExpression__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:496:1: ( rule__UnaryIntExpression__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnaryIntExpressionAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:497:1: ( rule__UnaryIntExpression__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:497:2: rule__UnaryIntExpression__Group__0
            {
            pushFollow(FOLLOW_rule__UnaryIntExpression__Group__0_in_ruleUnaryIntExpression1000);
            rule__UnaryIntExpression__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnaryIntExpressionAccess().getGroup()); 
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
    // $ANTLR end "ruleUnaryIntExpression"


    // $ANTLR start "entryRulePrimary"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:509:1: entryRulePrimary : rulePrimary EOF ;
    public final void entryRulePrimary() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:510:1: ( rulePrimary EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:511:1: rulePrimary EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryRule()); 
            }
            pushFollow(FOLLOW_rulePrimary_in_entryRulePrimary1027);
            rulePrimary();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimary1034); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:518:1: rulePrimary : ( ( rule__Primary__Alternatives ) ) ;
    public final void rulePrimary() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:522:2: ( ( ( rule__Primary__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:523:1: ( ( rule__Primary__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:523:1: ( ( rule__Primary__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:524:1: ( rule__Primary__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryAccess().getAlternatives()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:525:1: ( rule__Primary__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:525:2: rule__Primary__Alternatives
            {
            pushFollow(FOLLOW_rule__Primary__Alternatives_in_rulePrimary1060);
            rule__Primary__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryAccess().getAlternatives()); 
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
    // $ANTLR end "rulePrimary"


    // $ANTLR start "entryRuleConstante"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:537:1: entryRuleConstante : ruleConstante EOF ;
    public final void entryRuleConstante() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:538:1: ( ruleConstante EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:539:1: ruleConstante EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getConstanteRule()); 
            }
            pushFollow(FOLLOW_ruleConstante_in_entryRuleConstante1087);
            ruleConstante();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getConstanteRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstante1094); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:546:1: ruleConstante : ( ( rule__Constante__ValAssignment ) ) ;
    public final void ruleConstante() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:550:2: ( ( ( rule__Constante__ValAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:551:1: ( ( rule__Constante__ValAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:551:1: ( ( rule__Constante__ValAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:552:1: ( rule__Constante__ValAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getConstanteAccess().getValAssignment()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:553:1: ( rule__Constante__ValAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:553:2: rule__Constante__ValAssignment
            {
            pushFollow(FOLLOW_rule__Constante__ValAssignment_in_ruleConstante1120);
            rule__Constante__ValAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getConstanteAccess().getValAssignment()); 
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
    // $ANTLR end "ruleConstante"


    // $ANTLR start "entryRuleOr"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:565:1: entryRuleOr : ruleOr EOF ;
    public final void entryRuleOr() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:566:1: ( ruleOr EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:567:1: ruleOr EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOrRule()); 
            }
            pushFollow(FOLLOW_ruleOr_in_entryRuleOr1147);
            ruleOr();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getOrRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOr1154); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:574:1: ruleOr : ( ( rule__Or__Group__0 ) ) ;
    public final void ruleOr() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:578:2: ( ( ( rule__Or__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:579:1: ( ( rule__Or__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:579:1: ( ( rule__Or__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:580:1: ( rule__Or__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOrAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:581:1: ( rule__Or__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:581:2: rule__Or__Group__0
            {
            pushFollow(FOLLOW_rule__Or__Group__0_in_ruleOr1180);
            rule__Or__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getOrAccess().getGroup()); 
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
    // $ANTLR end "ruleOr"


    // $ANTLR start "entryRuleAnd"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:593:1: entryRuleAnd : ruleAnd EOF ;
    public final void entryRuleAnd() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:594:1: ( ruleAnd EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:595:1: ruleAnd EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAndRule()); 
            }
            pushFollow(FOLLOW_ruleAnd_in_entryRuleAnd1207);
            ruleAnd();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAndRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleAnd1214); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:602:1: ruleAnd : ( ( rule__And__Group__0 ) ) ;
    public final void ruleAnd() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:606:2: ( ( ( rule__And__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:607:1: ( ( rule__And__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:607:1: ( ( rule__And__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:608:1: ( rule__And__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAndAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:609:1: ( rule__And__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:609:2: rule__And__Group__0
            {
            pushFollow(FOLLOW_rule__And__Group__0_in_ruleAnd1240);
            rule__And__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAndAccess().getGroup()); 
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
    // $ANTLR end "ruleAnd"


    // $ANTLR start "entryRuleNot"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:621:1: entryRuleNot : ruleNot EOF ;
    public final void entryRuleNot() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:622:1: ( ruleNot EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:623:1: ruleNot EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNotRule()); 
            }
            pushFollow(FOLLOW_ruleNot_in_entryRuleNot1267);
            ruleNot();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNotRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleNot1274); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:630:1: ruleNot : ( ( rule__Not__Group__0 ) ) ;
    public final void ruleNot() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:634:2: ( ( ( rule__Not__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:635:1: ( ( rule__Not__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:635:1: ( ( rule__Not__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:636:1: ( rule__Not__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNotAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:637:1: ( rule__Not__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:637:2: rule__Not__Group__0
            {
            pushFollow(FOLLOW_rule__Not__Group__0_in_ruleNot1300);
            rule__Not__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNotAccess().getGroup()); 
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
    // $ANTLR end "ruleNot"


    // $ANTLR start "entryRulePrimaryBool"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:649:1: entryRulePrimaryBool : rulePrimaryBool EOF ;
    public final void entryRulePrimaryBool() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:650:1: ( rulePrimaryBool EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:651:1: rulePrimaryBool EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryBoolRule()); 
            }
            pushFollow(FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool1327);
            rulePrimaryBool();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryBoolRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryBool1334); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:658:1: rulePrimaryBool : ( ( rule__PrimaryBool__Alternatives ) ) ;
    public final void rulePrimaryBool() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:662:2: ( ( ( rule__PrimaryBool__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:663:1: ( ( rule__PrimaryBool__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:663:1: ( ( rule__PrimaryBool__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:664:1: ( rule__PrimaryBool__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryBoolAccess().getAlternatives()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:665:1: ( rule__PrimaryBool__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:665:2: rule__PrimaryBool__Alternatives
            {
            pushFollow(FOLLOW_rule__PrimaryBool__Alternatives_in_rulePrimaryBool1360);
            rule__PrimaryBool__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryBoolAccess().getAlternatives()); 
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
    // $ANTLR end "rulePrimaryBool"


    // $ANTLR start "entryRuleComparison"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:677:1: entryRuleComparison : ruleComparison EOF ;
    public final void entryRuleComparison() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:678:1: ( ruleComparison EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:679:1: ruleComparison EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getComparisonRule()); 
            }
            pushFollow(FOLLOW_ruleComparison_in_entryRuleComparison1387);
            ruleComparison();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getComparisonRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleComparison1394); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:686:1: ruleComparison : ( ( rule__Comparison__Group__0 ) ) ;
    public final void ruleComparison() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:690:2: ( ( ( rule__Comparison__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:691:1: ( ( rule__Comparison__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:691:1: ( ( rule__Comparison__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:692:1: ( rule__Comparison__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getComparisonAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:693:1: ( rule__Comparison__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:693:2: rule__Comparison__Group__0
            {
            pushFollow(FOLLOW_rule__Comparison__Group__0_in_ruleComparison1420);
            rule__Comparison__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getComparisonAccess().getGroup()); 
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
    // $ANTLR end "ruleComparison"


    // $ANTLR start "entryRuleTrue"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:705:1: entryRuleTrue : ruleTrue EOF ;
    public final void entryRuleTrue() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:706:1: ( ruleTrue EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:707:1: ruleTrue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTrueRule()); 
            }
            pushFollow(FOLLOW_ruleTrue_in_entryRuleTrue1447);
            ruleTrue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTrueRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleTrue1454); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:714:1: ruleTrue : ( ( rule__True__ValAssignment ) ) ;
    public final void ruleTrue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:718:2: ( ( ( rule__True__ValAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:719:1: ( ( rule__True__ValAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:719:1: ( ( rule__True__ValAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:720:1: ( rule__True__ValAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTrueAccess().getValAssignment()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:721:1: ( rule__True__ValAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:721:2: rule__True__ValAssignment
            {
            pushFollow(FOLLOW_rule__True__ValAssignment_in_ruleTrue1480);
            rule__True__ValAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTrueAccess().getValAssignment()); 
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
    // $ANTLR end "ruleTrue"


    // $ANTLR start "entryRuleFalse"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:733:1: entryRuleFalse : ruleFalse EOF ;
    public final void entryRuleFalse() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:734:1: ( ruleFalse EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:735:1: ruleFalse EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFalseRule()); 
            }
            pushFollow(FOLLOW_ruleFalse_in_entryRuleFalse1507);
            ruleFalse();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFalseRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleFalse1514); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:742:1: ruleFalse : ( ( rule__False__ValAssignment ) ) ;
    public final void ruleFalse() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:746:2: ( ( ( rule__False__ValAssignment ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:747:1: ( ( rule__False__ValAssignment ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:747:1: ( ( rule__False__ValAssignment ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:748:1: ( rule__False__ValAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFalseAccess().getValAssignment()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:749:1: ( rule__False__ValAssignment )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:749:2: rule__False__ValAssignment
            {
            pushFollow(FOLLOW_rule__False__ValAssignment_in_ruleFalse1540);
            rule__False__ValAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getFalseAccess().getValAssignment()); 
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
    // $ANTLR end "ruleFalse"


    // $ANTLR start "entryRuleQualifiedName"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:761:1: entryRuleQualifiedName : ruleQualifiedName EOF ;
    public final void entryRuleQualifiedName() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:762:1: ( ruleQualifiedName EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:763:1: ruleQualifiedName EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameRule()); 
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName1567);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedName1574); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:770:1: ruleQualifiedName : ( ( rule__QualifiedName__Group__0 ) ) ;
    public final void ruleQualifiedName() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:774:2: ( ( ( rule__QualifiedName__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:775:1: ( ( rule__QualifiedName__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:775:1: ( ( rule__QualifiedName__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:776:1: ( rule__QualifiedName__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:777:1: ( rule__QualifiedName__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:777:2: rule__QualifiedName__Group__0
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group__0_in_ruleQualifiedName1600);
            rule__QualifiedName__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameAccess().getGroup()); 
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
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleQualifiedNameWithWildCard"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:789:1: entryRuleQualifiedNameWithWildCard : ruleQualifiedNameWithWildCard EOF ;
    public final void entryRuleQualifiedNameWithWildCard() throws RecognitionException {
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:790:1: ( ruleQualifiedNameWithWildCard EOF )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:791:1: ruleQualifiedNameWithWildCard EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameWithWildCardRule()); 
            }
            pushFollow(FOLLOW_ruleQualifiedNameWithWildCard_in_entryRuleQualifiedNameWithWildCard1627);
            ruleQualifiedNameWithWildCard();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameWithWildCardRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedNameWithWildCard1634); if (state.failed) return ;

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:798:1: ruleQualifiedNameWithWildCard : ( ( rule__QualifiedNameWithWildCard__Group__0 ) ) ;
    public final void ruleQualifiedNameWithWildCard() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:802:2: ( ( ( rule__QualifiedNameWithWildCard__Group__0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:803:1: ( ( rule__QualifiedNameWithWildCard__Group__0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:803:1: ( ( rule__QualifiedNameWithWildCard__Group__0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:804:1: ( rule__QualifiedNameWithWildCard__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameWithWildCardAccess().getGroup()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:805:1: ( rule__QualifiedNameWithWildCard__Group__0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:805:2: rule__QualifiedNameWithWildCard__Group__0
            {
            pushFollow(FOLLOW_rule__QualifiedNameWithWildCard__Group__0_in_ruleQualifiedNameWithWildCard1660);
            rule__QualifiedNameWithWildCard__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameWithWildCardAccess().getGroup()); 
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
    // $ANTLR end "ruleQualifiedNameWithWildCard"


    // $ANTLR start "ruleBinaryArithmeticOperations"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:820:1: ruleBinaryArithmeticOperations : ( ( rule__BinaryArithmeticOperations__Alternatives ) ) ;
    public final void ruleBinaryArithmeticOperations() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:824:1: ( ( ( rule__BinaryArithmeticOperations__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:825:1: ( ( rule__BinaryArithmeticOperations__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:825:1: ( ( rule__BinaryArithmeticOperations__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:826:1: ( rule__BinaryArithmeticOperations__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryArithmeticOperationsAccess().getAlternatives()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:827:1: ( rule__BinaryArithmeticOperations__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:827:2: rule__BinaryArithmeticOperations__Alternatives
            {
            pushFollow(FOLLOW_rule__BinaryArithmeticOperations__Alternatives_in_ruleBinaryArithmeticOperations1699);
            rule__BinaryArithmeticOperations__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryArithmeticOperationsAccess().getAlternatives()); 
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
    // $ANTLR end "ruleBinaryArithmeticOperations"


    // $ANTLR start "ruleComparisonOperators"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:839:1: ruleComparisonOperators : ( ( rule__ComparisonOperators__Alternatives ) ) ;
    public final void ruleComparisonOperators() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:843:1: ( ( ( rule__ComparisonOperators__Alternatives ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:844:1: ( ( rule__ComparisonOperators__Alternatives ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:844:1: ( ( rule__ComparisonOperators__Alternatives ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:845:1: ( rule__ComparisonOperators__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getComparisonOperatorsAccess().getAlternatives()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:846:1: ( rule__ComparisonOperators__Alternatives )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:846:2: rule__ComparisonOperators__Alternatives
            {
            pushFollow(FOLLOW_rule__ComparisonOperators__Alternatives_in_ruleComparisonOperators1735);
            rule__ComparisonOperators__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getComparisonOperatorsAccess().getAlternatives()); 
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
    // $ANTLR end "ruleComparisonOperators"


    // $ANTLR start "rule__System__Alternatives_3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:857:1: rule__System__Alternatives_3 : ( ( ( rule__System__VariablesAssignment_3_0 ) ) | ( ( rule__System__ArraysAssignment_3_1 ) ) | ( ( rule__System__ListsAssignment_3_2 ) ) );
    public final void rule__System__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:861:1: ( ( ( rule__System__VariablesAssignment_3_0 ) ) | ( ( rule__System__ArraysAssignment_3_1 ) ) | ( ( rule__System__ListsAssignment_3_2 ) ) )
            int alt1=3;
            switch ( input.LA(1) ) {
            case 26:
                {
                alt1=1;
                }
                break;
            case 29:
                {
                alt1=2;
                }
                break;
            case 34:
                {
                alt1=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:862:1: ( ( rule__System__VariablesAssignment_3_0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:862:1: ( ( rule__System__VariablesAssignment_3_0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:863:1: ( rule__System__VariablesAssignment_3_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getSystemAccess().getVariablesAssignment_3_0()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:864:1: ( rule__System__VariablesAssignment_3_0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:864:2: rule__System__VariablesAssignment_3_0
                    {
                    pushFollow(FOLLOW_rule__System__VariablesAssignment_3_0_in_rule__System__Alternatives_31770);
                    rule__System__VariablesAssignment_3_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getSystemAccess().getVariablesAssignment_3_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:868:6: ( ( rule__System__ArraysAssignment_3_1 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:868:6: ( ( rule__System__ArraysAssignment_3_1 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:869:1: ( rule__System__ArraysAssignment_3_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getSystemAccess().getArraysAssignment_3_1()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:870:1: ( rule__System__ArraysAssignment_3_1 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:870:2: rule__System__ArraysAssignment_3_1
                    {
                    pushFollow(FOLLOW_rule__System__ArraysAssignment_3_1_in_rule__System__Alternatives_31788);
                    rule__System__ArraysAssignment_3_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getSystemAccess().getArraysAssignment_3_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:874:6: ( ( rule__System__ListsAssignment_3_2 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:874:6: ( ( rule__System__ListsAssignment_3_2 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:875:1: ( rule__System__ListsAssignment_3_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getSystemAccess().getListsAssignment_3_2()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:876:1: ( rule__System__ListsAssignment_3_2 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:876:2: rule__System__ListsAssignment_3_2
                    {
                    pushFollow(FOLLOW_rule__System__ListsAssignment_3_2_in_rule__System__Alternatives_31806);
                    rule__System__ListsAssignment_3_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getSystemAccess().getListsAssignment_3_2()); 
                    }

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
    // $ANTLR end "rule__System__Alternatives_3"


    // $ANTLR start "rule__Actions__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:885:1: rule__Actions__Alternatives : ( ( ruleAssignment ) | ( rulePush ) | ( rulePop ) );
    public final void rule__Actions__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:889:1: ( ( ruleAssignment ) | ( rulePush ) | ( rulePop ) )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==RULE_ID) ) {
                switch ( input.LA(2) ) {
                case 39:
                    {
                    alt2=3;
                    }
                    break;
                case 38:
                    {
                    alt2=2;
                    }
                    break;
                case 27:
                case 30:
                    {
                    alt2=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:890:1: ( ruleAssignment )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:890:1: ( ruleAssignment )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:891:1: ruleAssignment
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getAssignmentParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_ruleAssignment_in_rule__Actions__Alternatives1839);
                    ruleAssignment();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getAssignmentParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:896:6: ( rulePush )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:896:6: ( rulePush )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:897:1: rulePush
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getPushParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_rulePush_in_rule__Actions__Alternatives1856);
                    rulePush();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getPushParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:902:6: ( rulePop )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:902:6: ( rulePop )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:903:1: rulePop
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getPopParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_rulePop_in_rule__Actions__Alternatives1873);
                    rulePop();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getPopParserRuleCall_2()); 
                    }

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
    // $ANTLR end "rule__Actions__Alternatives"


    // $ANTLR start "rule__VarAccess__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:913:1: rule__VarAccess__Alternatives : ( ( ruleArrayVarAccess ) | ( ruleVariableRef ) );
    public final void rule__VarAccess__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:917:1: ( ( ruleArrayVarAccess ) | ( ruleVariableRef ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_ID) ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==30) ) {
                    alt3=1;
                }
                else if ( (LA3_1==EOF||(LA3_1>=11 && LA3_1<=22)||(LA3_1>=27 && LA3_1<=28)||LA3_1==31||LA3_1==33||(LA3_1>=41 && LA3_1<=42)) ) {
                    alt3=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:918:1: ( ruleArrayVarAccess )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:918:1: ( ruleArrayVarAccess )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:919:1: ruleArrayVarAccess
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVarAccessAccess().getArrayVarAccessParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_ruleArrayVarAccess_in_rule__VarAccess__Alternatives1905);
                    ruleArrayVarAccess();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVarAccessAccess().getArrayVarAccessParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:924:6: ( ruleVariableRef )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:924:6: ( ruleVariableRef )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:925:1: ruleVariableRef
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getVarAccessAccess().getVariableRefParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_ruleVariableRef_in_rule__VarAccess__Alternatives1922);
                    ruleVariableRef();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getVarAccessAccess().getVariableRefParserRuleCall_1()); 
                    }

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
    // $ANTLR end "rule__VarAccess__Alternatives"


    // $ANTLR start "rule__Primary__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:935:1: rule__Primary__Alternatives : ( ( rulePeek ) | ( ruleVarAccess ) | ( ruleConstante ) | ( ( rule__Primary__Group_3__0 ) ) );
    public final void rule__Primary__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:939:1: ( ( rulePeek ) | ( ruleVarAccess ) | ( ruleConstante ) | ( ( rule__Primary__Group_3__0 ) ) )
            int alt4=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==40) ) {
                    alt4=1;
                }
                else if ( (LA4_1==EOF||(LA4_1>=11 && LA4_1<=22)||LA4_1==28||(LA4_1>=30 && LA4_1<=31)||LA4_1==33||(LA4_1>=41 && LA4_1<=42)) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
                {
                alt4=3;
                }
                break;
            case 32:
                {
                alt4=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:940:1: ( rulePeek )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:940:1: ( rulePeek )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:941:1: rulePeek
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPrimaryAccess().getPeekParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_rulePeek_in_rule__Primary__Alternatives1954);
                    rulePeek();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPrimaryAccess().getPeekParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:946:6: ( ruleVarAccess )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:946:6: ( ruleVarAccess )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:947:1: ruleVarAccess
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPrimaryAccess().getVarAccessParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_ruleVarAccess_in_rule__Primary__Alternatives1971);
                    ruleVarAccess();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPrimaryAccess().getVarAccessParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:952:6: ( ruleConstante )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:952:6: ( ruleConstante )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:953:1: ruleConstante
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_ruleConstante_in_rule__Primary__Alternatives1988);
                    ruleConstante();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:958:6: ( ( rule__Primary__Group_3__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:958:6: ( ( rule__Primary__Group_3__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:959:1: ( rule__Primary__Group_3__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPrimaryAccess().getGroup_3()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:960:1: ( rule__Primary__Group_3__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:960:2: rule__Primary__Group_3__0
                    {
                    pushFollow(FOLLOW_rule__Primary__Group_3__0_in_rule__Primary__Alternatives2005);
                    rule__Primary__Group_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPrimaryAccess().getGroup_3()); 
                    }

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


    // $ANTLR start "rule__PrimaryBool__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:969:1: rule__PrimaryBool__Alternatives : ( ( ruleTrue ) | ( ruleFalse ) | ( ( ruleComparison ) ) | ( ( rule__PrimaryBool__Group_3__0 ) ) );
    public final void rule__PrimaryBool__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:973:1: ( ( ruleTrue ) | ( ruleFalse ) | ( ( ruleComparison ) ) | ( ( rule__PrimaryBool__Group_3__0 ) ) )
            int alt5=4;
            switch ( input.LA(1) ) {
            case 46:
                {
                alt5=1;
                }
                break;
            case 47:
                {
                alt5=2;
                }
                break;
            case RULE_ID:
            case RULE_INT:
            case 12:
                {
                alt5=3;
                }
                break;
            case 32:
                {
                int LA5_6 = input.LA(2);

                if ( (synpred11_InternalGal()) ) {
                    alt5=3;
                }
                else if ( (true) ) {
                    alt5=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 6, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:974:1: ( ruleTrue )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:974:1: ( ruleTrue )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:975:1: ruleTrue
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPrimaryBoolAccess().getTrueParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_ruleTrue_in_rule__PrimaryBool__Alternatives2038);
                    ruleTrue();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPrimaryBoolAccess().getTrueParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:980:6: ( ruleFalse )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:980:6: ( ruleFalse )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:981:1: ruleFalse
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPrimaryBoolAccess().getFalseParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_ruleFalse_in_rule__PrimaryBool__Alternatives2055);
                    ruleFalse();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPrimaryBoolAccess().getFalseParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:986:6: ( ( ruleComparison ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:986:6: ( ( ruleComparison ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:987:1: ( ruleComparison )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPrimaryBoolAccess().getComparisonParserRuleCall_2()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:988:1: ( ruleComparison )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:988:3: ruleComparison
                    {
                    pushFollow(FOLLOW_ruleComparison_in_rule__PrimaryBool__Alternatives2073);
                    ruleComparison();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPrimaryBoolAccess().getComparisonParserRuleCall_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:992:6: ( ( rule__PrimaryBool__Group_3__0 ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:992:6: ( ( rule__PrimaryBool__Group_3__0 ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:993:1: ( rule__PrimaryBool__Group_3__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPrimaryBoolAccess().getGroup_3()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:994:1: ( rule__PrimaryBool__Group_3__0 )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:994:2: rule__PrimaryBool__Group_3__0
                    {
                    pushFollow(FOLLOW_rule__PrimaryBool__Group_3__0_in_rule__PrimaryBool__Alternatives2091);
                    rule__PrimaryBool__Group_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPrimaryBoolAccess().getGroup_3()); 
                    }

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


    // $ANTLR start "rule__BinaryArithmeticOperations__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1003:1: rule__BinaryArithmeticOperations__Alternatives : ( ( ( '+' ) ) | ( ( '-' ) ) | ( ( '/' ) ) | ( ( '*' ) ) | ( ( '%' ) ) | ( ( '**' ) ) );
    public final void rule__BinaryArithmeticOperations__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1007:1: ( ( ( '+' ) ) | ( ( '-' ) ) | ( ( '/' ) ) | ( ( '*' ) ) | ( ( '%' ) ) | ( ( '**' ) ) )
            int alt6=6;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt6=1;
                }
                break;
            case 12:
                {
                alt6=2;
                }
                break;
            case 13:
                {
                alt6=3;
                }
                break;
            case 14:
                {
                alt6=4;
                }
                break;
            case 15:
                {
                alt6=5;
                }
                break;
            case 16:
                {
                alt6=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1008:1: ( ( '+' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1008:1: ( ( '+' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1009:1: ( '+' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBinaryArithmeticOperationsAccess().getPLUSEnumLiteralDeclaration_0()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1010:1: ( '+' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1010:3: '+'
                    {
                    match(input,11,FOLLOW_11_in_rule__BinaryArithmeticOperations__Alternatives2125); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBinaryArithmeticOperationsAccess().getPLUSEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1015:6: ( ( '-' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1015:6: ( ( '-' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1016:1: ( '-' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBinaryArithmeticOperationsAccess().getMINUSEnumLiteralDeclaration_1()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1017:1: ( '-' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1017:3: '-'
                    {
                    match(input,12,FOLLOW_12_in_rule__BinaryArithmeticOperations__Alternatives2146); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBinaryArithmeticOperationsAccess().getMINUSEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1022:6: ( ( '/' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1022:6: ( ( '/' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1023:1: ( '/' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBinaryArithmeticOperationsAccess().getDIVISIONEnumLiteralDeclaration_2()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1024:1: ( '/' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1024:3: '/'
                    {
                    match(input,13,FOLLOW_13_in_rule__BinaryArithmeticOperations__Alternatives2167); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBinaryArithmeticOperationsAccess().getDIVISIONEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1029:6: ( ( '*' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1029:6: ( ( '*' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1030:1: ( '*' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBinaryArithmeticOperationsAccess().getMULTEnumLiteralDeclaration_3()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1031:1: ( '*' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1031:3: '*'
                    {
                    match(input,14,FOLLOW_14_in_rule__BinaryArithmeticOperations__Alternatives2188); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBinaryArithmeticOperationsAccess().getMULTEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1036:6: ( ( '%' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1036:6: ( ( '%' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1037:1: ( '%' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBinaryArithmeticOperationsAccess().getMODULOEnumLiteralDeclaration_4()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1038:1: ( '%' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1038:3: '%'
                    {
                    match(input,15,FOLLOW_15_in_rule__BinaryArithmeticOperations__Alternatives2209); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBinaryArithmeticOperationsAccess().getMODULOEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1043:6: ( ( '**' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1043:6: ( ( '**' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1044:1: ( '**' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBinaryArithmeticOperationsAccess().getPOWEREnumLiteralDeclaration_5()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1045:1: ( '**' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1045:3: '**'
                    {
                    match(input,16,FOLLOW_16_in_rule__BinaryArithmeticOperations__Alternatives2230); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBinaryArithmeticOperationsAccess().getPOWEREnumLiteralDeclaration_5()); 
                    }

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
    // $ANTLR end "rule__BinaryArithmeticOperations__Alternatives"


    // $ANTLR start "rule__ComparisonOperators__Alternatives"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1055:1: rule__ComparisonOperators__Alternatives : ( ( ( '>' ) ) | ( ( '<' ) ) | ( ( '>=' ) ) | ( ( '<=' ) ) | ( ( '==' ) ) | ( ( '!=' ) ) );
    public final void rule__ComparisonOperators__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1059:1: ( ( ( '>' ) ) | ( ( '<' ) ) | ( ( '>=' ) ) | ( ( '<=' ) ) | ( ( '==' ) ) | ( ( '!=' ) ) )
            int alt7=6;
            switch ( input.LA(1) ) {
            case 17:
                {
                alt7=1;
                }
                break;
            case 18:
                {
                alt7=2;
                }
                break;
            case 19:
                {
                alt7=3;
                }
                break;
            case 20:
                {
                alt7=4;
                }
                break;
            case 21:
                {
                alt7=5;
                }
                break;
            case 22:
                {
                alt7=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1060:1: ( ( '>' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1060:1: ( ( '>' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1061:1: ( '>' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getComparisonOperatorsAccess().getGTEnumLiteralDeclaration_0()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1062:1: ( '>' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1062:3: '>'
                    {
                    match(input,17,FOLLOW_17_in_rule__ComparisonOperators__Alternatives2266); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getComparisonOperatorsAccess().getGTEnumLiteralDeclaration_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1067:6: ( ( '<' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1067:6: ( ( '<' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1068:1: ( '<' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getComparisonOperatorsAccess().getLTEnumLiteralDeclaration_1()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1069:1: ( '<' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1069:3: '<'
                    {
                    match(input,18,FOLLOW_18_in_rule__ComparisonOperators__Alternatives2287); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getComparisonOperatorsAccess().getLTEnumLiteralDeclaration_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1074:6: ( ( '>=' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1074:6: ( ( '>=' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1075:1: ( '>=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getComparisonOperatorsAccess().getGQEnumLiteralDeclaration_2()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1076:1: ( '>=' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1076:3: '>='
                    {
                    match(input,19,FOLLOW_19_in_rule__ComparisonOperators__Alternatives2308); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getComparisonOperatorsAccess().getGQEnumLiteralDeclaration_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1081:6: ( ( '<=' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1081:6: ( ( '<=' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1082:1: ( '<=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getComparisonOperatorsAccess().getLQEnumLiteralDeclaration_3()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1083:1: ( '<=' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1083:3: '<='
                    {
                    match(input,20,FOLLOW_20_in_rule__ComparisonOperators__Alternatives2329); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getComparisonOperatorsAccess().getLQEnumLiteralDeclaration_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1088:6: ( ( '==' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1088:6: ( ( '==' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1089:1: ( '==' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getComparisonOperatorsAccess().getEQEnumLiteralDeclaration_4()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1090:1: ( '==' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1090:3: '=='
                    {
                    match(input,21,FOLLOW_21_in_rule__ComparisonOperators__Alternatives2350); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getComparisonOperatorsAccess().getEQEnumLiteralDeclaration_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1095:6: ( ( '!=' ) )
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1095:6: ( ( '!=' ) )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1096:1: ( '!=' )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getComparisonOperatorsAccess().getNQEnumLiteralDeclaration_5()); 
                    }
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1097:1: ( '!=' )
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1097:3: '!='
                    {
                    match(input,22,FOLLOW_22_in_rule__ComparisonOperators__Alternatives2371); if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getComparisonOperatorsAccess().getNQEnumLiteralDeclaration_5()); 
                    }

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
    // $ANTLR end "rule__ComparisonOperators__Alternatives"


    // $ANTLR start "rule__System__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1109:1: rule__System__Group__0 : rule__System__Group__0__Impl rule__System__Group__1 ;
    public final void rule__System__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1113:1: ( rule__System__Group__0__Impl rule__System__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1114:2: rule__System__Group__0__Impl rule__System__Group__1
            {
            pushFollow(FOLLOW_rule__System__Group__0__Impl_in_rule__System__Group__02404);
            rule__System__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__System__Group__1_in_rule__System__Group__02407);
            rule__System__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1121:1: rule__System__Group__0__Impl : ( 'GAL' ) ;
    public final void rule__System__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1125:1: ( ( 'GAL' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1126:1: ( 'GAL' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1126:1: ( 'GAL' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1127:1: 'GAL'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getGALKeyword_0()); 
            }
            match(input,23,FOLLOW_23_in_rule__System__Group__0__Impl2435); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getGALKeyword_0()); 
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
    // $ANTLR end "rule__System__Group__0__Impl"


    // $ANTLR start "rule__System__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1140:1: rule__System__Group__1 : rule__System__Group__1__Impl rule__System__Group__2 ;
    public final void rule__System__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1144:1: ( rule__System__Group__1__Impl rule__System__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1145:2: rule__System__Group__1__Impl rule__System__Group__2
            {
            pushFollow(FOLLOW_rule__System__Group__1__Impl_in_rule__System__Group__12466);
            rule__System__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__System__Group__2_in_rule__System__Group__12469);
            rule__System__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1152:1: rule__System__Group__1__Impl : ( ( rule__System__NameAssignment_1 ) ) ;
    public final void rule__System__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1156:1: ( ( ( rule__System__NameAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1157:1: ( ( rule__System__NameAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1157:1: ( ( rule__System__NameAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1158:1: ( rule__System__NameAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getNameAssignment_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1159:1: ( rule__System__NameAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1159:2: rule__System__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__System__NameAssignment_1_in_rule__System__Group__1__Impl2496);
            rule__System__NameAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getNameAssignment_1()); 
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
    // $ANTLR end "rule__System__Group__1__Impl"


    // $ANTLR start "rule__System__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1169:1: rule__System__Group__2 : rule__System__Group__2__Impl rule__System__Group__3 ;
    public final void rule__System__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1173:1: ( rule__System__Group__2__Impl rule__System__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1174:2: rule__System__Group__2__Impl rule__System__Group__3
            {
            pushFollow(FOLLOW_rule__System__Group__2__Impl_in_rule__System__Group__22526);
            rule__System__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__System__Group__3_in_rule__System__Group__22529);
            rule__System__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1181:1: rule__System__Group__2__Impl : ( '{' ) ;
    public final void rule__System__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1185:1: ( ( '{' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1186:1: ( '{' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1186:1: ( '{' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1187:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getLeftCurlyBracketKeyword_2()); 
            }
            match(input,24,FOLLOW_24_in_rule__System__Group__2__Impl2557); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getLeftCurlyBracketKeyword_2()); 
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
    // $ANTLR end "rule__System__Group__2__Impl"


    // $ANTLR start "rule__System__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1200:1: rule__System__Group__3 : rule__System__Group__3__Impl rule__System__Group__4 ;
    public final void rule__System__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1204:1: ( rule__System__Group__3__Impl rule__System__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1205:2: rule__System__Group__3__Impl rule__System__Group__4
            {
            pushFollow(FOLLOW_rule__System__Group__3__Impl_in_rule__System__Group__32588);
            rule__System__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__System__Group__4_in_rule__System__Group__32591);
            rule__System__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1212:1: rule__System__Group__3__Impl : ( ( rule__System__Alternatives_3 )* ) ;
    public final void rule__System__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1216:1: ( ( ( rule__System__Alternatives_3 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1217:1: ( ( rule__System__Alternatives_3 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1217:1: ( ( rule__System__Alternatives_3 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1218:1: ( rule__System__Alternatives_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getAlternatives_3()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1219:1: ( rule__System__Alternatives_3 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==26||LA8_0==29||LA8_0==34) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1219:2: rule__System__Alternatives_3
            	    {
            	    pushFollow(FOLLOW_rule__System__Alternatives_3_in_rule__System__Group__3__Impl2618);
            	    rule__System__Alternatives_3();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getAlternatives_3()); 
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
    // $ANTLR end "rule__System__Group__3__Impl"


    // $ANTLR start "rule__System__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1229:1: rule__System__Group__4 : rule__System__Group__4__Impl rule__System__Group__5 ;
    public final void rule__System__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1233:1: ( rule__System__Group__4__Impl rule__System__Group__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1234:2: rule__System__Group__4__Impl rule__System__Group__5
            {
            pushFollow(FOLLOW_rule__System__Group__4__Impl_in_rule__System__Group__42649);
            rule__System__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__System__Group__5_in_rule__System__Group__42652);
            rule__System__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1241:1: rule__System__Group__4__Impl : ( ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* ) ) ;
    public final void rule__System__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1245:1: ( ( ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1246:1: ( ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1246:1: ( ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1247:1: ( ( rule__System__TransitionsAssignment_4 ) ) ( ( rule__System__TransitionsAssignment_4 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1247:1: ( ( rule__System__TransitionsAssignment_4 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1248:1: ( rule__System__TransitionsAssignment_4 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getTransitionsAssignment_4()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1249:1: ( rule__System__TransitionsAssignment_4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1249:2: rule__System__TransitionsAssignment_4
            {
            pushFollow(FOLLOW_rule__System__TransitionsAssignment_4_in_rule__System__Group__4__Impl2681);
            rule__System__TransitionsAssignment_4();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getTransitionsAssignment_4()); 
            }

            }

            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1252:1: ( ( rule__System__TransitionsAssignment_4 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1253:1: ( rule__System__TransitionsAssignment_4 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getTransitionsAssignment_4()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1254:1: ( rule__System__TransitionsAssignment_4 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==36) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1254:2: rule__System__TransitionsAssignment_4
            	    {
            	    pushFollow(FOLLOW_rule__System__TransitionsAssignment_4_in_rule__System__Group__4__Impl2693);
            	    rule__System__TransitionsAssignment_4();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getTransitionsAssignment_4()); 
            }

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1265:1: rule__System__Group__5 : rule__System__Group__5__Impl ;
    public final void rule__System__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1269:1: ( rule__System__Group__5__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1270:2: rule__System__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__System__Group__5__Impl_in_rule__System__Group__52726);
            rule__System__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1276:1: rule__System__Group__5__Impl : ( '}' ) ;
    public final void rule__System__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1280:1: ( ( '}' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1281:1: ( '}' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1281:1: ( '}' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1282:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getRightCurlyBracketKeyword_5()); 
            }
            match(input,25,FOLLOW_25_in_rule__System__Group__5__Impl2754); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getRightCurlyBracketKeyword_5()); 
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
    // $ANTLR end "rule__System__Group__5__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1307:1: rule__VariableDeclaration__Group__0 : rule__VariableDeclaration__Group__0__Impl rule__VariableDeclaration__Group__1 ;
    public final void rule__VariableDeclaration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1311:1: ( rule__VariableDeclaration__Group__0__Impl rule__VariableDeclaration__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1312:2: rule__VariableDeclaration__Group__0__Impl rule__VariableDeclaration__Group__1
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__0__Impl_in_rule__VariableDeclaration__Group__02797);
            rule__VariableDeclaration__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__1_in_rule__VariableDeclaration__Group__02800);
            rule__VariableDeclaration__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1319:1: rule__VariableDeclaration__Group__0__Impl : ( 'int' ) ;
    public final void rule__VariableDeclaration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1323:1: ( ( 'int' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1324:1: ( 'int' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1324:1: ( 'int' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1325:1: 'int'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableDeclarationAccess().getIntKeyword_0()); 
            }
            match(input,26,FOLLOW_26_in_rule__VariableDeclaration__Group__0__Impl2828); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableDeclarationAccess().getIntKeyword_0()); 
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
    // $ANTLR end "rule__VariableDeclaration__Group__0__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1338:1: rule__VariableDeclaration__Group__1 : rule__VariableDeclaration__Group__1__Impl rule__VariableDeclaration__Group__2 ;
    public final void rule__VariableDeclaration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1342:1: ( rule__VariableDeclaration__Group__1__Impl rule__VariableDeclaration__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1343:2: rule__VariableDeclaration__Group__1__Impl rule__VariableDeclaration__Group__2
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__1__Impl_in_rule__VariableDeclaration__Group__12859);
            rule__VariableDeclaration__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__2_in_rule__VariableDeclaration__Group__12862);
            rule__VariableDeclaration__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1350:1: rule__VariableDeclaration__Group__1__Impl : ( ( rule__VariableDeclaration__NameAssignment_1 ) ) ;
    public final void rule__VariableDeclaration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1354:1: ( ( ( rule__VariableDeclaration__NameAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1355:1: ( ( rule__VariableDeclaration__NameAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1355:1: ( ( rule__VariableDeclaration__NameAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1356:1: ( rule__VariableDeclaration__NameAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableDeclarationAccess().getNameAssignment_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1357:1: ( rule__VariableDeclaration__NameAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1357:2: rule__VariableDeclaration__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__NameAssignment_1_in_rule__VariableDeclaration__Group__1__Impl2889);
            rule__VariableDeclaration__NameAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableDeclarationAccess().getNameAssignment_1()); 
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
    // $ANTLR end "rule__VariableDeclaration__Group__1__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1367:1: rule__VariableDeclaration__Group__2 : rule__VariableDeclaration__Group__2__Impl rule__VariableDeclaration__Group__3 ;
    public final void rule__VariableDeclaration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1371:1: ( rule__VariableDeclaration__Group__2__Impl rule__VariableDeclaration__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1372:2: rule__VariableDeclaration__Group__2__Impl rule__VariableDeclaration__Group__3
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__2__Impl_in_rule__VariableDeclaration__Group__22919);
            rule__VariableDeclaration__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__3_in_rule__VariableDeclaration__Group__22922);
            rule__VariableDeclaration__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1379:1: rule__VariableDeclaration__Group__2__Impl : ( '=' ) ;
    public final void rule__VariableDeclaration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1383:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1384:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1384:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1385:1: '='
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_2()); 
            }
            match(input,27,FOLLOW_27_in_rule__VariableDeclaration__Group__2__Impl2950); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_2()); 
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
    // $ANTLR end "rule__VariableDeclaration__Group__2__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1398:1: rule__VariableDeclaration__Group__3 : rule__VariableDeclaration__Group__3__Impl rule__VariableDeclaration__Group__4 ;
    public final void rule__VariableDeclaration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1402:1: ( rule__VariableDeclaration__Group__3__Impl rule__VariableDeclaration__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1403:2: rule__VariableDeclaration__Group__3__Impl rule__VariableDeclaration__Group__4
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__3__Impl_in_rule__VariableDeclaration__Group__32981);
            rule__VariableDeclaration__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__4_in_rule__VariableDeclaration__Group__32984);
            rule__VariableDeclaration__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1410:1: rule__VariableDeclaration__Group__3__Impl : ( ( rule__VariableDeclaration__ValueAssignment_3 ) ) ;
    public final void rule__VariableDeclaration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1414:1: ( ( ( rule__VariableDeclaration__ValueAssignment_3 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1415:1: ( ( rule__VariableDeclaration__ValueAssignment_3 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1415:1: ( ( rule__VariableDeclaration__ValueAssignment_3 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1416:1: ( rule__VariableDeclaration__ValueAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableDeclarationAccess().getValueAssignment_3()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1417:1: ( rule__VariableDeclaration__ValueAssignment_3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1417:2: rule__VariableDeclaration__ValueAssignment_3
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__ValueAssignment_3_in_rule__VariableDeclaration__Group__3__Impl3011);
            rule__VariableDeclaration__ValueAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableDeclarationAccess().getValueAssignment_3()); 
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
    // $ANTLR end "rule__VariableDeclaration__Group__3__Impl"


    // $ANTLR start "rule__VariableDeclaration__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1427:1: rule__VariableDeclaration__Group__4 : rule__VariableDeclaration__Group__4__Impl ;
    public final void rule__VariableDeclaration__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1431:1: ( rule__VariableDeclaration__Group__4__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1432:2: rule__VariableDeclaration__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__VariableDeclaration__Group__4__Impl_in_rule__VariableDeclaration__Group__43041);
            rule__VariableDeclaration__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VariableDeclaration__Group__4"


    // $ANTLR start "rule__VariableDeclaration__Group__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1438:1: rule__VariableDeclaration__Group__4__Impl : ( ';' ) ;
    public final void rule__VariableDeclaration__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1442:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1443:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1443:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1444:1: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_4()); 
            }
            match(input,28,FOLLOW_28_in_rule__VariableDeclaration__Group__4__Impl3069); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_4()); 
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
    // $ANTLR end "rule__VariableDeclaration__Group__4__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1467:1: rule__ArrayDeclaration__Group__0 : rule__ArrayDeclaration__Group__0__Impl rule__ArrayDeclaration__Group__1 ;
    public final void rule__ArrayDeclaration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1471:1: ( rule__ArrayDeclaration__Group__0__Impl rule__ArrayDeclaration__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1472:2: rule__ArrayDeclaration__Group__0__Impl rule__ArrayDeclaration__Group__1
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__0__Impl_in_rule__ArrayDeclaration__Group__03110);
            rule__ArrayDeclaration__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__1_in_rule__ArrayDeclaration__Group__03113);
            rule__ArrayDeclaration__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__0"


    // $ANTLR start "rule__ArrayDeclaration__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1479:1: rule__ArrayDeclaration__Group__0__Impl : ( 'array' ) ;
    public final void rule__ArrayDeclaration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1483:1: ( ( 'array' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1484:1: ( 'array' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1484:1: ( 'array' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1485:1: 'array'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getArrayKeyword_0()); 
            }
            match(input,29,FOLLOW_29_in_rule__ArrayDeclaration__Group__0__Impl3141); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getArrayKeyword_0()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__0__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1498:1: rule__ArrayDeclaration__Group__1 : rule__ArrayDeclaration__Group__1__Impl rule__ArrayDeclaration__Group__2 ;
    public final void rule__ArrayDeclaration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1502:1: ( rule__ArrayDeclaration__Group__1__Impl rule__ArrayDeclaration__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1503:2: rule__ArrayDeclaration__Group__1__Impl rule__ArrayDeclaration__Group__2
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__1__Impl_in_rule__ArrayDeclaration__Group__13172);
            rule__ArrayDeclaration__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__2_in_rule__ArrayDeclaration__Group__13175);
            rule__ArrayDeclaration__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__1"


    // $ANTLR start "rule__ArrayDeclaration__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1510:1: rule__ArrayDeclaration__Group__1__Impl : ( '[' ) ;
    public final void rule__ArrayDeclaration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1514:1: ( ( '[' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1515:1: ( '[' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1515:1: ( '[' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1516:1: '['
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getLeftSquareBracketKeyword_1()); 
            }
            match(input,30,FOLLOW_30_in_rule__ArrayDeclaration__Group__1__Impl3203); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getLeftSquareBracketKeyword_1()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__1__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1529:1: rule__ArrayDeclaration__Group__2 : rule__ArrayDeclaration__Group__2__Impl rule__ArrayDeclaration__Group__3 ;
    public final void rule__ArrayDeclaration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1533:1: ( rule__ArrayDeclaration__Group__2__Impl rule__ArrayDeclaration__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1534:2: rule__ArrayDeclaration__Group__2__Impl rule__ArrayDeclaration__Group__3
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__2__Impl_in_rule__ArrayDeclaration__Group__23234);
            rule__ArrayDeclaration__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__3_in_rule__ArrayDeclaration__Group__23237);
            rule__ArrayDeclaration__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__2"


    // $ANTLR start "rule__ArrayDeclaration__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1541:1: rule__ArrayDeclaration__Group__2__Impl : ( ( rule__ArrayDeclaration__SizeAssignment_2 ) ) ;
    public final void rule__ArrayDeclaration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1545:1: ( ( ( rule__ArrayDeclaration__SizeAssignment_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1546:1: ( ( rule__ArrayDeclaration__SizeAssignment_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1546:1: ( ( rule__ArrayDeclaration__SizeAssignment_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1547:1: ( rule__ArrayDeclaration__SizeAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getSizeAssignment_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1548:1: ( rule__ArrayDeclaration__SizeAssignment_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1548:2: rule__ArrayDeclaration__SizeAssignment_2
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__SizeAssignment_2_in_rule__ArrayDeclaration__Group__2__Impl3264);
            rule__ArrayDeclaration__SizeAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getSizeAssignment_2()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__2__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1558:1: rule__ArrayDeclaration__Group__3 : rule__ArrayDeclaration__Group__3__Impl rule__ArrayDeclaration__Group__4 ;
    public final void rule__ArrayDeclaration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1562:1: ( rule__ArrayDeclaration__Group__3__Impl rule__ArrayDeclaration__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1563:2: rule__ArrayDeclaration__Group__3__Impl rule__ArrayDeclaration__Group__4
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__3__Impl_in_rule__ArrayDeclaration__Group__33294);
            rule__ArrayDeclaration__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__4_in_rule__ArrayDeclaration__Group__33297);
            rule__ArrayDeclaration__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__3"


    // $ANTLR start "rule__ArrayDeclaration__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1570:1: rule__ArrayDeclaration__Group__3__Impl : ( ']' ) ;
    public final void rule__ArrayDeclaration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1574:1: ( ( ']' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1575:1: ( ']' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1575:1: ( ']' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1576:1: ']'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getRightSquareBracketKeyword_3()); 
            }
            match(input,31,FOLLOW_31_in_rule__ArrayDeclaration__Group__3__Impl3325); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getRightSquareBracketKeyword_3()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__3__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1589:1: rule__ArrayDeclaration__Group__4 : rule__ArrayDeclaration__Group__4__Impl rule__ArrayDeclaration__Group__5 ;
    public final void rule__ArrayDeclaration__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1593:1: ( rule__ArrayDeclaration__Group__4__Impl rule__ArrayDeclaration__Group__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1594:2: rule__ArrayDeclaration__Group__4__Impl rule__ArrayDeclaration__Group__5
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__4__Impl_in_rule__ArrayDeclaration__Group__43356);
            rule__ArrayDeclaration__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__5_in_rule__ArrayDeclaration__Group__43359);
            rule__ArrayDeclaration__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__4"


    // $ANTLR start "rule__ArrayDeclaration__Group__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1601:1: rule__ArrayDeclaration__Group__4__Impl : ( ( rule__ArrayDeclaration__NameAssignment_4 ) ) ;
    public final void rule__ArrayDeclaration__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1605:1: ( ( ( rule__ArrayDeclaration__NameAssignment_4 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1606:1: ( ( rule__ArrayDeclaration__NameAssignment_4 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1606:1: ( ( rule__ArrayDeclaration__NameAssignment_4 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1607:1: ( rule__ArrayDeclaration__NameAssignment_4 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getNameAssignment_4()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1608:1: ( rule__ArrayDeclaration__NameAssignment_4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1608:2: rule__ArrayDeclaration__NameAssignment_4
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__NameAssignment_4_in_rule__ArrayDeclaration__Group__4__Impl3386);
            rule__ArrayDeclaration__NameAssignment_4();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getNameAssignment_4()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__4__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1618:1: rule__ArrayDeclaration__Group__5 : rule__ArrayDeclaration__Group__5__Impl rule__ArrayDeclaration__Group__6 ;
    public final void rule__ArrayDeclaration__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1622:1: ( rule__ArrayDeclaration__Group__5__Impl rule__ArrayDeclaration__Group__6 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1623:2: rule__ArrayDeclaration__Group__5__Impl rule__ArrayDeclaration__Group__6
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__5__Impl_in_rule__ArrayDeclaration__Group__53416);
            rule__ArrayDeclaration__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__6_in_rule__ArrayDeclaration__Group__53419);
            rule__ArrayDeclaration__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__5"


    // $ANTLR start "rule__ArrayDeclaration__Group__5__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1630:1: rule__ArrayDeclaration__Group__5__Impl : ( '=' ) ;
    public final void rule__ArrayDeclaration__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1634:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1635:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1635:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1636:1: '='
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getEqualsSignKeyword_5()); 
            }
            match(input,27,FOLLOW_27_in_rule__ArrayDeclaration__Group__5__Impl3447); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getEqualsSignKeyword_5()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__5__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__6"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1649:1: rule__ArrayDeclaration__Group__6 : rule__ArrayDeclaration__Group__6__Impl rule__ArrayDeclaration__Group__7 ;
    public final void rule__ArrayDeclaration__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1653:1: ( rule__ArrayDeclaration__Group__6__Impl rule__ArrayDeclaration__Group__7 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1654:2: rule__ArrayDeclaration__Group__6__Impl rule__ArrayDeclaration__Group__7
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__6__Impl_in_rule__ArrayDeclaration__Group__63478);
            rule__ArrayDeclaration__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__7_in_rule__ArrayDeclaration__Group__63481);
            rule__ArrayDeclaration__Group__7();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__6"


    // $ANTLR start "rule__ArrayDeclaration__Group__6__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1661:1: rule__ArrayDeclaration__Group__6__Impl : ( '(' ) ;
    public final void rule__ArrayDeclaration__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1665:1: ( ( '(' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1666:1: ( '(' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1666:1: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1667:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getLeftParenthesisKeyword_6()); 
            }
            match(input,32,FOLLOW_32_in_rule__ArrayDeclaration__Group__6__Impl3509); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getLeftParenthesisKeyword_6()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__6__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__7"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1680:1: rule__ArrayDeclaration__Group__7 : rule__ArrayDeclaration__Group__7__Impl rule__ArrayDeclaration__Group__8 ;
    public final void rule__ArrayDeclaration__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1684:1: ( rule__ArrayDeclaration__Group__7__Impl rule__ArrayDeclaration__Group__8 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1685:2: rule__ArrayDeclaration__Group__7__Impl rule__ArrayDeclaration__Group__8
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__7__Impl_in_rule__ArrayDeclaration__Group__73540);
            rule__ArrayDeclaration__Group__7__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__8_in_rule__ArrayDeclaration__Group__73543);
            rule__ArrayDeclaration__Group__8();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__7"


    // $ANTLR start "rule__ArrayDeclaration__Group__7__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1692:1: rule__ArrayDeclaration__Group__7__Impl : ( ( rule__ArrayDeclaration__ValuesAssignment_7 )? ) ;
    public final void rule__ArrayDeclaration__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1696:1: ( ( ( rule__ArrayDeclaration__ValuesAssignment_7 )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1697:1: ( ( rule__ArrayDeclaration__ValuesAssignment_7 )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1697:1: ( ( rule__ArrayDeclaration__ValuesAssignment_7 )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1698:1: ( rule__ArrayDeclaration__ValuesAssignment_7 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getValuesAssignment_7()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1699:1: ( rule__ArrayDeclaration__ValuesAssignment_7 )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_INT) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1699:2: rule__ArrayDeclaration__ValuesAssignment_7
                    {
                    pushFollow(FOLLOW_rule__ArrayDeclaration__ValuesAssignment_7_in_rule__ArrayDeclaration__Group__7__Impl3570);
                    rule__ArrayDeclaration__ValuesAssignment_7();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getValuesAssignment_7()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__7__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__8"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1709:1: rule__ArrayDeclaration__Group__8 : rule__ArrayDeclaration__Group__8__Impl rule__ArrayDeclaration__Group__9 ;
    public final void rule__ArrayDeclaration__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1713:1: ( rule__ArrayDeclaration__Group__8__Impl rule__ArrayDeclaration__Group__9 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1714:2: rule__ArrayDeclaration__Group__8__Impl rule__ArrayDeclaration__Group__9
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__8__Impl_in_rule__ArrayDeclaration__Group__83601);
            rule__ArrayDeclaration__Group__8__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__9_in_rule__ArrayDeclaration__Group__83604);
            rule__ArrayDeclaration__Group__9();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__8"


    // $ANTLR start "rule__ArrayDeclaration__Group__8__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1721:1: rule__ArrayDeclaration__Group__8__Impl : ( ')' ) ;
    public final void rule__ArrayDeclaration__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1725:1: ( ( ')' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1726:1: ( ')' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1726:1: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1727:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getRightParenthesisKeyword_8()); 
            }
            match(input,33,FOLLOW_33_in_rule__ArrayDeclaration__Group__8__Impl3632); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getRightParenthesisKeyword_8()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__8__Impl"


    // $ANTLR start "rule__ArrayDeclaration__Group__9"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1740:1: rule__ArrayDeclaration__Group__9 : rule__ArrayDeclaration__Group__9__Impl ;
    public final void rule__ArrayDeclaration__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1744:1: ( rule__ArrayDeclaration__Group__9__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1745:2: rule__ArrayDeclaration__Group__9__Impl
            {
            pushFollow(FOLLOW_rule__ArrayDeclaration__Group__9__Impl_in_rule__ArrayDeclaration__Group__93663);
            rule__ArrayDeclaration__Group__9__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayDeclaration__Group__9"


    // $ANTLR start "rule__ArrayDeclaration__Group__9__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1751:1: rule__ArrayDeclaration__Group__9__Impl : ( ';' ) ;
    public final void rule__ArrayDeclaration__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1755:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1756:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1756:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1757:1: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getSemicolonKeyword_9()); 
            }
            match(input,28,FOLLOW_28_in_rule__ArrayDeclaration__Group__9__Impl3691); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getSemicolonKeyword_9()); 
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
    // $ANTLR end "rule__ArrayDeclaration__Group__9__Impl"


    // $ANTLR start "rule__ListDeclaration__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1790:1: rule__ListDeclaration__Group__0 : rule__ListDeclaration__Group__0__Impl rule__ListDeclaration__Group__1 ;
    public final void rule__ListDeclaration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1794:1: ( rule__ListDeclaration__Group__0__Impl rule__ListDeclaration__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1795:2: rule__ListDeclaration__Group__0__Impl rule__ListDeclaration__Group__1
            {
            pushFollow(FOLLOW_rule__ListDeclaration__Group__0__Impl_in_rule__ListDeclaration__Group__03742);
            rule__ListDeclaration__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ListDeclaration__Group__1_in_rule__ListDeclaration__Group__03745);
            rule__ListDeclaration__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ListDeclaration__Group__0"


    // $ANTLR start "rule__ListDeclaration__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1802:1: rule__ListDeclaration__Group__0__Impl : ( 'list' ) ;
    public final void rule__ListDeclaration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1806:1: ( ( 'list' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1807:1: ( 'list' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1807:1: ( 'list' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1808:1: 'list'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getListKeyword_0()); 
            }
            match(input,34,FOLLOW_34_in_rule__ListDeclaration__Group__0__Impl3773); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getListKeyword_0()); 
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
    // $ANTLR end "rule__ListDeclaration__Group__0__Impl"


    // $ANTLR start "rule__ListDeclaration__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1821:1: rule__ListDeclaration__Group__1 : rule__ListDeclaration__Group__1__Impl rule__ListDeclaration__Group__2 ;
    public final void rule__ListDeclaration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1825:1: ( rule__ListDeclaration__Group__1__Impl rule__ListDeclaration__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1826:2: rule__ListDeclaration__Group__1__Impl rule__ListDeclaration__Group__2
            {
            pushFollow(FOLLOW_rule__ListDeclaration__Group__1__Impl_in_rule__ListDeclaration__Group__13804);
            rule__ListDeclaration__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ListDeclaration__Group__2_in_rule__ListDeclaration__Group__13807);
            rule__ListDeclaration__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ListDeclaration__Group__1"


    // $ANTLR start "rule__ListDeclaration__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1833:1: rule__ListDeclaration__Group__1__Impl : ( ( rule__ListDeclaration__NameAssignment_1 ) ) ;
    public final void rule__ListDeclaration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1837:1: ( ( ( rule__ListDeclaration__NameAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1838:1: ( ( rule__ListDeclaration__NameAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1838:1: ( ( rule__ListDeclaration__NameAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1839:1: ( rule__ListDeclaration__NameAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getNameAssignment_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1840:1: ( rule__ListDeclaration__NameAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1840:2: rule__ListDeclaration__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__ListDeclaration__NameAssignment_1_in_rule__ListDeclaration__Group__1__Impl3834);
            rule__ListDeclaration__NameAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getNameAssignment_1()); 
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
    // $ANTLR end "rule__ListDeclaration__Group__1__Impl"


    // $ANTLR start "rule__ListDeclaration__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1850:1: rule__ListDeclaration__Group__2 : rule__ListDeclaration__Group__2__Impl rule__ListDeclaration__Group__3 ;
    public final void rule__ListDeclaration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1854:1: ( rule__ListDeclaration__Group__2__Impl rule__ListDeclaration__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1855:2: rule__ListDeclaration__Group__2__Impl rule__ListDeclaration__Group__3
            {
            pushFollow(FOLLOW_rule__ListDeclaration__Group__2__Impl_in_rule__ListDeclaration__Group__23864);
            rule__ListDeclaration__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ListDeclaration__Group__3_in_rule__ListDeclaration__Group__23867);
            rule__ListDeclaration__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ListDeclaration__Group__2"


    // $ANTLR start "rule__ListDeclaration__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1862:1: rule__ListDeclaration__Group__2__Impl : ( ( rule__ListDeclaration__Group_2__0 )? ) ;
    public final void rule__ListDeclaration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1866:1: ( ( ( rule__ListDeclaration__Group_2__0 )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1867:1: ( ( rule__ListDeclaration__Group_2__0 )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1867:1: ( ( rule__ListDeclaration__Group_2__0 )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1868:1: ( rule__ListDeclaration__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getGroup_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1869:1: ( rule__ListDeclaration__Group_2__0 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==27) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1869:2: rule__ListDeclaration__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__ListDeclaration__Group_2__0_in_rule__ListDeclaration__Group__2__Impl3894);
                    rule__ListDeclaration__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getGroup_2()); 
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
    // $ANTLR end "rule__ListDeclaration__Group__2__Impl"


    // $ANTLR start "rule__ListDeclaration__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1879:1: rule__ListDeclaration__Group__3 : rule__ListDeclaration__Group__3__Impl ;
    public final void rule__ListDeclaration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1883:1: ( rule__ListDeclaration__Group__3__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1884:2: rule__ListDeclaration__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__ListDeclaration__Group__3__Impl_in_rule__ListDeclaration__Group__33925);
            rule__ListDeclaration__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ListDeclaration__Group__3"


    // $ANTLR start "rule__ListDeclaration__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1890:1: rule__ListDeclaration__Group__3__Impl : ( ';' ) ;
    public final void rule__ListDeclaration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1894:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1895:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1895:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1896:1: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getSemicolonKeyword_3()); 
            }
            match(input,28,FOLLOW_28_in_rule__ListDeclaration__Group__3__Impl3953); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getSemicolonKeyword_3()); 
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
    // $ANTLR end "rule__ListDeclaration__Group__3__Impl"


    // $ANTLR start "rule__ListDeclaration__Group_2__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1917:1: rule__ListDeclaration__Group_2__0 : rule__ListDeclaration__Group_2__0__Impl rule__ListDeclaration__Group_2__1 ;
    public final void rule__ListDeclaration__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1921:1: ( rule__ListDeclaration__Group_2__0__Impl rule__ListDeclaration__Group_2__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1922:2: rule__ListDeclaration__Group_2__0__Impl rule__ListDeclaration__Group_2__1
            {
            pushFollow(FOLLOW_rule__ListDeclaration__Group_2__0__Impl_in_rule__ListDeclaration__Group_2__03992);
            rule__ListDeclaration__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ListDeclaration__Group_2__1_in_rule__ListDeclaration__Group_2__03995);
            rule__ListDeclaration__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ListDeclaration__Group_2__0"


    // $ANTLR start "rule__ListDeclaration__Group_2__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1929:1: rule__ListDeclaration__Group_2__0__Impl : ( '=' ) ;
    public final void rule__ListDeclaration__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1933:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1934:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1934:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1935:1: '='
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getEqualsSignKeyword_2_0()); 
            }
            match(input,27,FOLLOW_27_in_rule__ListDeclaration__Group_2__0__Impl4023); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getEqualsSignKeyword_2_0()); 
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
    // $ANTLR end "rule__ListDeclaration__Group_2__0__Impl"


    // $ANTLR start "rule__ListDeclaration__Group_2__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1948:1: rule__ListDeclaration__Group_2__1 : rule__ListDeclaration__Group_2__1__Impl rule__ListDeclaration__Group_2__2 ;
    public final void rule__ListDeclaration__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1952:1: ( rule__ListDeclaration__Group_2__1__Impl rule__ListDeclaration__Group_2__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1953:2: rule__ListDeclaration__Group_2__1__Impl rule__ListDeclaration__Group_2__2
            {
            pushFollow(FOLLOW_rule__ListDeclaration__Group_2__1__Impl_in_rule__ListDeclaration__Group_2__14054);
            rule__ListDeclaration__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ListDeclaration__Group_2__2_in_rule__ListDeclaration__Group_2__14057);
            rule__ListDeclaration__Group_2__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ListDeclaration__Group_2__1"


    // $ANTLR start "rule__ListDeclaration__Group_2__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1960:1: rule__ListDeclaration__Group_2__1__Impl : ( '(' ) ;
    public final void rule__ListDeclaration__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1964:1: ( ( '(' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1965:1: ( '(' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1965:1: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1966:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getLeftParenthesisKeyword_2_1()); 
            }
            match(input,32,FOLLOW_32_in_rule__ListDeclaration__Group_2__1__Impl4085); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getLeftParenthesisKeyword_2_1()); 
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
    // $ANTLR end "rule__ListDeclaration__Group_2__1__Impl"


    // $ANTLR start "rule__ListDeclaration__Group_2__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1979:1: rule__ListDeclaration__Group_2__2 : rule__ListDeclaration__Group_2__2__Impl rule__ListDeclaration__Group_2__3 ;
    public final void rule__ListDeclaration__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1983:1: ( rule__ListDeclaration__Group_2__2__Impl rule__ListDeclaration__Group_2__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1984:2: rule__ListDeclaration__Group_2__2__Impl rule__ListDeclaration__Group_2__3
            {
            pushFollow(FOLLOW_rule__ListDeclaration__Group_2__2__Impl_in_rule__ListDeclaration__Group_2__24116);
            rule__ListDeclaration__Group_2__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ListDeclaration__Group_2__3_in_rule__ListDeclaration__Group_2__24119);
            rule__ListDeclaration__Group_2__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ListDeclaration__Group_2__2"


    // $ANTLR start "rule__ListDeclaration__Group_2__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1991:1: rule__ListDeclaration__Group_2__2__Impl : ( ( rule__ListDeclaration__ValuesAssignment_2_2 )? ) ;
    public final void rule__ListDeclaration__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1995:1: ( ( ( rule__ListDeclaration__ValuesAssignment_2_2 )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1996:1: ( ( rule__ListDeclaration__ValuesAssignment_2_2 )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1996:1: ( ( rule__ListDeclaration__ValuesAssignment_2_2 )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1997:1: ( rule__ListDeclaration__ValuesAssignment_2_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getValuesAssignment_2_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1998:1: ( rule__ListDeclaration__ValuesAssignment_2_2 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_INT) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1998:2: rule__ListDeclaration__ValuesAssignment_2_2
                    {
                    pushFollow(FOLLOW_rule__ListDeclaration__ValuesAssignment_2_2_in_rule__ListDeclaration__Group_2__2__Impl4146);
                    rule__ListDeclaration__ValuesAssignment_2_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getValuesAssignment_2_2()); 
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
    // $ANTLR end "rule__ListDeclaration__Group_2__2__Impl"


    // $ANTLR start "rule__ListDeclaration__Group_2__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2008:1: rule__ListDeclaration__Group_2__3 : rule__ListDeclaration__Group_2__3__Impl ;
    public final void rule__ListDeclaration__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2012:1: ( rule__ListDeclaration__Group_2__3__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2013:2: rule__ListDeclaration__Group_2__3__Impl
            {
            pushFollow(FOLLOW_rule__ListDeclaration__Group_2__3__Impl_in_rule__ListDeclaration__Group_2__34177);
            rule__ListDeclaration__Group_2__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ListDeclaration__Group_2__3"


    // $ANTLR start "rule__ListDeclaration__Group_2__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2019:1: rule__ListDeclaration__Group_2__3__Impl : ( ')' ) ;
    public final void rule__ListDeclaration__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2023:1: ( ( ')' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2024:1: ( ')' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2024:1: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2025:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getRightParenthesisKeyword_2_3()); 
            }
            match(input,33,FOLLOW_33_in_rule__ListDeclaration__Group_2__3__Impl4205); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getRightParenthesisKeyword_2_3()); 
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
    // $ANTLR end "rule__ListDeclaration__Group_2__3__Impl"


    // $ANTLR start "rule__InitValues__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2046:1: rule__InitValues__Group__0 : rule__InitValues__Group__0__Impl rule__InitValues__Group__1 ;
    public final void rule__InitValues__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2050:1: ( rule__InitValues__Group__0__Impl rule__InitValues__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2051:2: rule__InitValues__Group__0__Impl rule__InitValues__Group__1
            {
            pushFollow(FOLLOW_rule__InitValues__Group__0__Impl_in_rule__InitValues__Group__04244);
            rule__InitValues__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__InitValues__Group__1_in_rule__InitValues__Group__04247);
            rule__InitValues__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitValues__Group__0"


    // $ANTLR start "rule__InitValues__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2058:1: rule__InitValues__Group__0__Impl : ( ( rule__InitValues__ValuesAssignment_0 ) ) ;
    public final void rule__InitValues__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2062:1: ( ( ( rule__InitValues__ValuesAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2063:1: ( ( rule__InitValues__ValuesAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2063:1: ( ( rule__InitValues__ValuesAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2064:1: ( rule__InitValues__ValuesAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInitValuesAccess().getValuesAssignment_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2065:1: ( rule__InitValues__ValuesAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2065:2: rule__InitValues__ValuesAssignment_0
            {
            pushFollow(FOLLOW_rule__InitValues__ValuesAssignment_0_in_rule__InitValues__Group__0__Impl4274);
            rule__InitValues__ValuesAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getInitValuesAccess().getValuesAssignment_0()); 
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
    // $ANTLR end "rule__InitValues__Group__0__Impl"


    // $ANTLR start "rule__InitValues__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2075:1: rule__InitValues__Group__1 : rule__InitValues__Group__1__Impl ;
    public final void rule__InitValues__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2079:1: ( rule__InitValues__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2080:2: rule__InitValues__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__InitValues__Group__1__Impl_in_rule__InitValues__Group__14304);
            rule__InitValues__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitValues__Group__1"


    // $ANTLR start "rule__InitValues__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2086:1: rule__InitValues__Group__1__Impl : ( ( rule__InitValues__Group_1__0 )* ) ;
    public final void rule__InitValues__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2090:1: ( ( ( rule__InitValues__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2091:1: ( ( rule__InitValues__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2091:1: ( ( rule__InitValues__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2092:1: ( rule__InitValues__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInitValuesAccess().getGroup_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2093:1: ( rule__InitValues__Group_1__0 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==35) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2093:2: rule__InitValues__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__InitValues__Group_1__0_in_rule__InitValues__Group__1__Impl4331);
            	    rule__InitValues__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getInitValuesAccess().getGroup_1()); 
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
    // $ANTLR end "rule__InitValues__Group__1__Impl"


    // $ANTLR start "rule__InitValues__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2107:1: rule__InitValues__Group_1__0 : rule__InitValues__Group_1__0__Impl rule__InitValues__Group_1__1 ;
    public final void rule__InitValues__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2111:1: ( rule__InitValues__Group_1__0__Impl rule__InitValues__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2112:2: rule__InitValues__Group_1__0__Impl rule__InitValues__Group_1__1
            {
            pushFollow(FOLLOW_rule__InitValues__Group_1__0__Impl_in_rule__InitValues__Group_1__04366);
            rule__InitValues__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__InitValues__Group_1__1_in_rule__InitValues__Group_1__04369);
            rule__InitValues__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitValues__Group_1__0"


    // $ANTLR start "rule__InitValues__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2119:1: rule__InitValues__Group_1__0__Impl : ( ',' ) ;
    public final void rule__InitValues__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2123:1: ( ( ',' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2124:1: ( ',' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2124:1: ( ',' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2125:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInitValuesAccess().getCommaKeyword_1_0()); 
            }
            match(input,35,FOLLOW_35_in_rule__InitValues__Group_1__0__Impl4397); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getInitValuesAccess().getCommaKeyword_1_0()); 
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
    // $ANTLR end "rule__InitValues__Group_1__0__Impl"


    // $ANTLR start "rule__InitValues__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2138:1: rule__InitValues__Group_1__1 : rule__InitValues__Group_1__1__Impl ;
    public final void rule__InitValues__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2142:1: ( rule__InitValues__Group_1__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2143:2: rule__InitValues__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__InitValues__Group_1__1__Impl_in_rule__InitValues__Group_1__14428);
            rule__InitValues__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitValues__Group_1__1"


    // $ANTLR start "rule__InitValues__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2149:1: rule__InitValues__Group_1__1__Impl : ( ( rule__InitValues__ValuesAssignment_1_1 ) ) ;
    public final void rule__InitValues__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2153:1: ( ( ( rule__InitValues__ValuesAssignment_1_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2154:1: ( ( rule__InitValues__ValuesAssignment_1_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2154:1: ( ( rule__InitValues__ValuesAssignment_1_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2155:1: ( rule__InitValues__ValuesAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInitValuesAccess().getValuesAssignment_1_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2156:1: ( rule__InitValues__ValuesAssignment_1_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2156:2: rule__InitValues__ValuesAssignment_1_1
            {
            pushFollow(FOLLOW_rule__InitValues__ValuesAssignment_1_1_in_rule__InitValues__Group_1__1__Impl4455);
            rule__InitValues__ValuesAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getInitValuesAccess().getValuesAssignment_1_1()); 
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
    // $ANTLR end "rule__InitValues__Group_1__1__Impl"


    // $ANTLR start "rule__Transition__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2170:1: rule__Transition__Group__0 : rule__Transition__Group__0__Impl rule__Transition__Group__1 ;
    public final void rule__Transition__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2174:1: ( rule__Transition__Group__0__Impl rule__Transition__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2175:2: rule__Transition__Group__0__Impl rule__Transition__Group__1
            {
            pushFollow(FOLLOW_rule__Transition__Group__0__Impl_in_rule__Transition__Group__04489);
            rule__Transition__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Transition__Group__1_in_rule__Transition__Group__04492);
            rule__Transition__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2182:1: rule__Transition__Group__0__Impl : ( 'transition' ) ;
    public final void rule__Transition__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2186:1: ( ( 'transition' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2187:1: ( 'transition' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2187:1: ( 'transition' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2188:1: 'transition'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getTransitionKeyword_0()); 
            }
            match(input,36,FOLLOW_36_in_rule__Transition__Group__0__Impl4520); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getTransitionKeyword_0()); 
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
    // $ANTLR end "rule__Transition__Group__0__Impl"


    // $ANTLR start "rule__Transition__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2201:1: rule__Transition__Group__1 : rule__Transition__Group__1__Impl rule__Transition__Group__2 ;
    public final void rule__Transition__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2205:1: ( rule__Transition__Group__1__Impl rule__Transition__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2206:2: rule__Transition__Group__1__Impl rule__Transition__Group__2
            {
            pushFollow(FOLLOW_rule__Transition__Group__1__Impl_in_rule__Transition__Group__14551);
            rule__Transition__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Transition__Group__2_in_rule__Transition__Group__14554);
            rule__Transition__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2213:1: rule__Transition__Group__1__Impl : ( ( rule__Transition__NameAssignment_1 ) ) ;
    public final void rule__Transition__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2217:1: ( ( ( rule__Transition__NameAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2218:1: ( ( rule__Transition__NameAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2218:1: ( ( rule__Transition__NameAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2219:1: ( rule__Transition__NameAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getNameAssignment_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2220:1: ( rule__Transition__NameAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2220:2: rule__Transition__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__Transition__NameAssignment_1_in_rule__Transition__Group__1__Impl4581);
            rule__Transition__NameAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getNameAssignment_1()); 
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
    // $ANTLR end "rule__Transition__Group__1__Impl"


    // $ANTLR start "rule__Transition__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2230:1: rule__Transition__Group__2 : rule__Transition__Group__2__Impl rule__Transition__Group__3 ;
    public final void rule__Transition__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2234:1: ( rule__Transition__Group__2__Impl rule__Transition__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2235:2: rule__Transition__Group__2__Impl rule__Transition__Group__3
            {
            pushFollow(FOLLOW_rule__Transition__Group__2__Impl_in_rule__Transition__Group__24611);
            rule__Transition__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Transition__Group__3_in_rule__Transition__Group__24614);
            rule__Transition__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2242:1: rule__Transition__Group__2__Impl : ( '[' ) ;
    public final void rule__Transition__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2246:1: ( ( '[' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2247:1: ( '[' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2247:1: ( '[' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2248:1: '['
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getLeftSquareBracketKeyword_2()); 
            }
            match(input,30,FOLLOW_30_in_rule__Transition__Group__2__Impl4642); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getLeftSquareBracketKeyword_2()); 
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
    // $ANTLR end "rule__Transition__Group__2__Impl"


    // $ANTLR start "rule__Transition__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2261:1: rule__Transition__Group__3 : rule__Transition__Group__3__Impl rule__Transition__Group__4 ;
    public final void rule__Transition__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2265:1: ( rule__Transition__Group__3__Impl rule__Transition__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2266:2: rule__Transition__Group__3__Impl rule__Transition__Group__4
            {
            pushFollow(FOLLOW_rule__Transition__Group__3__Impl_in_rule__Transition__Group__34673);
            rule__Transition__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Transition__Group__4_in_rule__Transition__Group__34676);
            rule__Transition__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2273:1: rule__Transition__Group__3__Impl : ( ( rule__Transition__GuardAssignment_3 ) ) ;
    public final void rule__Transition__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2277:1: ( ( ( rule__Transition__GuardAssignment_3 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2278:1: ( ( rule__Transition__GuardAssignment_3 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2278:1: ( ( rule__Transition__GuardAssignment_3 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2279:1: ( rule__Transition__GuardAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getGuardAssignment_3()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2280:1: ( rule__Transition__GuardAssignment_3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2280:2: rule__Transition__GuardAssignment_3
            {
            pushFollow(FOLLOW_rule__Transition__GuardAssignment_3_in_rule__Transition__Group__3__Impl4703);
            rule__Transition__GuardAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getGuardAssignment_3()); 
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
    // $ANTLR end "rule__Transition__Group__3__Impl"


    // $ANTLR start "rule__Transition__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2290:1: rule__Transition__Group__4 : rule__Transition__Group__4__Impl rule__Transition__Group__5 ;
    public final void rule__Transition__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2294:1: ( rule__Transition__Group__4__Impl rule__Transition__Group__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2295:2: rule__Transition__Group__4__Impl rule__Transition__Group__5
            {
            pushFollow(FOLLOW_rule__Transition__Group__4__Impl_in_rule__Transition__Group__44733);
            rule__Transition__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Transition__Group__5_in_rule__Transition__Group__44736);
            rule__Transition__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2302:1: rule__Transition__Group__4__Impl : ( ']' ) ;
    public final void rule__Transition__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2306:1: ( ( ']' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2307:1: ( ']' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2307:1: ( ']' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2308:1: ']'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getRightSquareBracketKeyword_4()); 
            }
            match(input,31,FOLLOW_31_in_rule__Transition__Group__4__Impl4764); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getRightSquareBracketKeyword_4()); 
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
    // $ANTLR end "rule__Transition__Group__4__Impl"


    // $ANTLR start "rule__Transition__Group__5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2321:1: rule__Transition__Group__5 : rule__Transition__Group__5__Impl rule__Transition__Group__6 ;
    public final void rule__Transition__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2325:1: ( rule__Transition__Group__5__Impl rule__Transition__Group__6 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2326:2: rule__Transition__Group__5__Impl rule__Transition__Group__6
            {
            pushFollow(FOLLOW_rule__Transition__Group__5__Impl_in_rule__Transition__Group__54795);
            rule__Transition__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Transition__Group__6_in_rule__Transition__Group__54798);
            rule__Transition__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2333:1: rule__Transition__Group__5__Impl : ( ( rule__Transition__Group_5__0 )? ) ;
    public final void rule__Transition__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2337:1: ( ( ( rule__Transition__Group_5__0 )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2338:1: ( ( rule__Transition__Group_5__0 )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2338:1: ( ( rule__Transition__Group_5__0 )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2339:1: ( rule__Transition__Group_5__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getGroup_5()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2340:1: ( rule__Transition__Group_5__0 )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==37) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2340:2: rule__Transition__Group_5__0
                    {
                    pushFollow(FOLLOW_rule__Transition__Group_5__0_in_rule__Transition__Group__5__Impl4825);
                    rule__Transition__Group_5__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getGroup_5()); 
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
    // $ANTLR end "rule__Transition__Group__5__Impl"


    // $ANTLR start "rule__Transition__Group__6"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2350:1: rule__Transition__Group__6 : rule__Transition__Group__6__Impl rule__Transition__Group__7 ;
    public final void rule__Transition__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2354:1: ( rule__Transition__Group__6__Impl rule__Transition__Group__7 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2355:2: rule__Transition__Group__6__Impl rule__Transition__Group__7
            {
            pushFollow(FOLLOW_rule__Transition__Group__6__Impl_in_rule__Transition__Group__64856);
            rule__Transition__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Transition__Group__7_in_rule__Transition__Group__64859);
            rule__Transition__Group__7();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2362:1: rule__Transition__Group__6__Impl : ( '{' ) ;
    public final void rule__Transition__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2366:1: ( ( '{' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2367:1: ( '{' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2367:1: ( '{' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2368:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_6()); 
            }
            match(input,24,FOLLOW_24_in_rule__Transition__Group__6__Impl4887); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_6()); 
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
    // $ANTLR end "rule__Transition__Group__6__Impl"


    // $ANTLR start "rule__Transition__Group__7"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2381:1: rule__Transition__Group__7 : rule__Transition__Group__7__Impl rule__Transition__Group__8 ;
    public final void rule__Transition__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2385:1: ( rule__Transition__Group__7__Impl rule__Transition__Group__8 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2386:2: rule__Transition__Group__7__Impl rule__Transition__Group__8
            {
            pushFollow(FOLLOW_rule__Transition__Group__7__Impl_in_rule__Transition__Group__74918);
            rule__Transition__Group__7__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Transition__Group__8_in_rule__Transition__Group__74921);
            rule__Transition__Group__8();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2393:1: rule__Transition__Group__7__Impl : ( ( rule__Transition__ActionsAssignment_7 )* ) ;
    public final void rule__Transition__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2397:1: ( ( ( rule__Transition__ActionsAssignment_7 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2398:1: ( ( rule__Transition__ActionsAssignment_7 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2398:1: ( ( rule__Transition__ActionsAssignment_7 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2399:1: ( rule__Transition__ActionsAssignment_7 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getActionsAssignment_7()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2400:1: ( rule__Transition__ActionsAssignment_7 )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==RULE_ID) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2400:2: rule__Transition__ActionsAssignment_7
            	    {
            	    pushFollow(FOLLOW_rule__Transition__ActionsAssignment_7_in_rule__Transition__Group__7__Impl4948);
            	    rule__Transition__ActionsAssignment_7();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getActionsAssignment_7()); 
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2410:1: rule__Transition__Group__8 : rule__Transition__Group__8__Impl ;
    public final void rule__Transition__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2414:1: ( rule__Transition__Group__8__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2415:2: rule__Transition__Group__8__Impl
            {
            pushFollow(FOLLOW_rule__Transition__Group__8__Impl_in_rule__Transition__Group__84979);
            rule__Transition__Group__8__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2421:1: rule__Transition__Group__8__Impl : ( '}' ) ;
    public final void rule__Transition__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2425:1: ( ( '}' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2426:1: ( '}' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2426:1: ( '}' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2427:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getRightCurlyBracketKeyword_8()); 
            }
            match(input,25,FOLLOW_25_in_rule__Transition__Group__8__Impl5007); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getRightCurlyBracketKeyword_8()); 
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
    // $ANTLR end "rule__Transition__Group__8__Impl"


    // $ANTLR start "rule__Transition__Group_5__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2458:1: rule__Transition__Group_5__0 : rule__Transition__Group_5__0__Impl rule__Transition__Group_5__1 ;
    public final void rule__Transition__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2462:1: ( rule__Transition__Group_5__0__Impl rule__Transition__Group_5__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2463:2: rule__Transition__Group_5__0__Impl rule__Transition__Group_5__1
            {
            pushFollow(FOLLOW_rule__Transition__Group_5__0__Impl_in_rule__Transition__Group_5__05056);
            rule__Transition__Group_5__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Transition__Group_5__1_in_rule__Transition__Group_5__05059);
            rule__Transition__Group_5__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2470:1: rule__Transition__Group_5__0__Impl : ( 'label' ) ;
    public final void rule__Transition__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2474:1: ( ( 'label' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2475:1: ( 'label' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2475:1: ( 'label' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2476:1: 'label'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getLabelKeyword_5_0()); 
            }
            match(input,37,FOLLOW_37_in_rule__Transition__Group_5__0__Impl5087); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getLabelKeyword_5_0()); 
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
    // $ANTLR end "rule__Transition__Group_5__0__Impl"


    // $ANTLR start "rule__Transition__Group_5__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2489:1: rule__Transition__Group_5__1 : rule__Transition__Group_5__1__Impl ;
    public final void rule__Transition__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2493:1: ( rule__Transition__Group_5__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2494:2: rule__Transition__Group_5__1__Impl
            {
            pushFollow(FOLLOW_rule__Transition__Group_5__1__Impl_in_rule__Transition__Group_5__15118);
            rule__Transition__Group_5__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2500:1: rule__Transition__Group_5__1__Impl : ( ( rule__Transition__LabelAssignment_5_1 ) ) ;
    public final void rule__Transition__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2504:1: ( ( ( rule__Transition__LabelAssignment_5_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2505:1: ( ( rule__Transition__LabelAssignment_5_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2505:1: ( ( rule__Transition__LabelAssignment_5_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2506:1: ( rule__Transition__LabelAssignment_5_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getLabelAssignment_5_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2507:1: ( rule__Transition__LabelAssignment_5_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2507:2: rule__Transition__LabelAssignment_5_1
            {
            pushFollow(FOLLOW_rule__Transition__LabelAssignment_5_1_in_rule__Transition__Group_5__1__Impl5145);
            rule__Transition__LabelAssignment_5_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getLabelAssignment_5_1()); 
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
    // $ANTLR end "rule__Transition__Group_5__1__Impl"


    // $ANTLR start "rule__Push__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2521:1: rule__Push__Group__0 : rule__Push__Group__0__Impl rule__Push__Group__1 ;
    public final void rule__Push__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2525:1: ( rule__Push__Group__0__Impl rule__Push__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2526:2: rule__Push__Group__0__Impl rule__Push__Group__1
            {
            pushFollow(FOLLOW_rule__Push__Group__0__Impl_in_rule__Push__Group__05179);
            rule__Push__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Push__Group__1_in_rule__Push__Group__05182);
            rule__Push__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Push__Group__0"


    // $ANTLR start "rule__Push__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2533:1: rule__Push__Group__0__Impl : ( ( rule__Push__ListeAssignment_0 ) ) ;
    public final void rule__Push__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2537:1: ( ( ( rule__Push__ListeAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2538:1: ( ( rule__Push__ListeAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2538:1: ( ( rule__Push__ListeAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2539:1: ( rule__Push__ListeAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getListeAssignment_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2540:1: ( rule__Push__ListeAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2540:2: rule__Push__ListeAssignment_0
            {
            pushFollow(FOLLOW_rule__Push__ListeAssignment_0_in_rule__Push__Group__0__Impl5209);
            rule__Push__ListeAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getListeAssignment_0()); 
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
    // $ANTLR end "rule__Push__Group__0__Impl"


    // $ANTLR start "rule__Push__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2550:1: rule__Push__Group__1 : rule__Push__Group__1__Impl rule__Push__Group__2 ;
    public final void rule__Push__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2554:1: ( rule__Push__Group__1__Impl rule__Push__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2555:2: rule__Push__Group__1__Impl rule__Push__Group__2
            {
            pushFollow(FOLLOW_rule__Push__Group__1__Impl_in_rule__Push__Group__15239);
            rule__Push__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Push__Group__2_in_rule__Push__Group__15242);
            rule__Push__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Push__Group__1"


    // $ANTLR start "rule__Push__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2562:1: rule__Push__Group__1__Impl : ( '.push' ) ;
    public final void rule__Push__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2566:1: ( ( '.push' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2567:1: ( '.push' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2567:1: ( '.push' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2568:1: '.push'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getPushKeyword_1()); 
            }
            match(input,38,FOLLOW_38_in_rule__Push__Group__1__Impl5270); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getPushKeyword_1()); 
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
    // $ANTLR end "rule__Push__Group__1__Impl"


    // $ANTLR start "rule__Push__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2581:1: rule__Push__Group__2 : rule__Push__Group__2__Impl rule__Push__Group__3 ;
    public final void rule__Push__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2585:1: ( rule__Push__Group__2__Impl rule__Push__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2586:2: rule__Push__Group__2__Impl rule__Push__Group__3
            {
            pushFollow(FOLLOW_rule__Push__Group__2__Impl_in_rule__Push__Group__25301);
            rule__Push__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Push__Group__3_in_rule__Push__Group__25304);
            rule__Push__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Push__Group__2"


    // $ANTLR start "rule__Push__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2593:1: rule__Push__Group__2__Impl : ( '(' ) ;
    public final void rule__Push__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2597:1: ( ( '(' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2598:1: ( '(' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2598:1: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2599:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getLeftParenthesisKeyword_2()); 
            }
            match(input,32,FOLLOW_32_in_rule__Push__Group__2__Impl5332); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getLeftParenthesisKeyword_2()); 
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
    // $ANTLR end "rule__Push__Group__2__Impl"


    // $ANTLR start "rule__Push__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2612:1: rule__Push__Group__3 : rule__Push__Group__3__Impl rule__Push__Group__4 ;
    public final void rule__Push__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2616:1: ( rule__Push__Group__3__Impl rule__Push__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2617:2: rule__Push__Group__3__Impl rule__Push__Group__4
            {
            pushFollow(FOLLOW_rule__Push__Group__3__Impl_in_rule__Push__Group__35363);
            rule__Push__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Push__Group__4_in_rule__Push__Group__35366);
            rule__Push__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Push__Group__3"


    // $ANTLR start "rule__Push__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2624:1: rule__Push__Group__3__Impl : ( ( rule__Push__ValueAssignment_3 ) ) ;
    public final void rule__Push__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2628:1: ( ( ( rule__Push__ValueAssignment_3 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2629:1: ( ( rule__Push__ValueAssignment_3 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2629:1: ( ( rule__Push__ValueAssignment_3 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2630:1: ( rule__Push__ValueAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getValueAssignment_3()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2631:1: ( rule__Push__ValueAssignment_3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2631:2: rule__Push__ValueAssignment_3
            {
            pushFollow(FOLLOW_rule__Push__ValueAssignment_3_in_rule__Push__Group__3__Impl5393);
            rule__Push__ValueAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getValueAssignment_3()); 
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
    // $ANTLR end "rule__Push__Group__3__Impl"


    // $ANTLR start "rule__Push__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2641:1: rule__Push__Group__4 : rule__Push__Group__4__Impl rule__Push__Group__5 ;
    public final void rule__Push__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2645:1: ( rule__Push__Group__4__Impl rule__Push__Group__5 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2646:2: rule__Push__Group__4__Impl rule__Push__Group__5
            {
            pushFollow(FOLLOW_rule__Push__Group__4__Impl_in_rule__Push__Group__45423);
            rule__Push__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Push__Group__5_in_rule__Push__Group__45426);
            rule__Push__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Push__Group__4"


    // $ANTLR start "rule__Push__Group__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2653:1: rule__Push__Group__4__Impl : ( ')' ) ;
    public final void rule__Push__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2657:1: ( ( ')' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2658:1: ( ')' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2658:1: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2659:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getRightParenthesisKeyword_4()); 
            }
            match(input,33,FOLLOW_33_in_rule__Push__Group__4__Impl5454); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getRightParenthesisKeyword_4()); 
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
    // $ANTLR end "rule__Push__Group__4__Impl"


    // $ANTLR start "rule__Push__Group__5"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2672:1: rule__Push__Group__5 : rule__Push__Group__5__Impl ;
    public final void rule__Push__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2676:1: ( rule__Push__Group__5__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2677:2: rule__Push__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__Push__Group__5__Impl_in_rule__Push__Group__55485);
            rule__Push__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Push__Group__5"


    // $ANTLR start "rule__Push__Group__5__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2683:1: rule__Push__Group__5__Impl : ( ';' ) ;
    public final void rule__Push__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2687:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2688:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2688:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2689:1: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getSemicolonKeyword_5()); 
            }
            match(input,28,FOLLOW_28_in_rule__Push__Group__5__Impl5513); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getSemicolonKeyword_5()); 
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
    // $ANTLR end "rule__Push__Group__5__Impl"


    // $ANTLR start "rule__Pop__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2714:1: rule__Pop__Group__0 : rule__Pop__Group__0__Impl rule__Pop__Group__1 ;
    public final void rule__Pop__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2718:1: ( rule__Pop__Group__0__Impl rule__Pop__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2719:2: rule__Pop__Group__0__Impl rule__Pop__Group__1
            {
            pushFollow(FOLLOW_rule__Pop__Group__0__Impl_in_rule__Pop__Group__05556);
            rule__Pop__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Pop__Group__1_in_rule__Pop__Group__05559);
            rule__Pop__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Pop__Group__0"


    // $ANTLR start "rule__Pop__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2726:1: rule__Pop__Group__0__Impl : ( ( rule__Pop__ListeAssignment_0 ) ) ;
    public final void rule__Pop__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2730:1: ( ( ( rule__Pop__ListeAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2731:1: ( ( rule__Pop__ListeAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2731:1: ( ( rule__Pop__ListeAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2732:1: ( rule__Pop__ListeAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPopAccess().getListeAssignment_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2733:1: ( rule__Pop__ListeAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2733:2: rule__Pop__ListeAssignment_0
            {
            pushFollow(FOLLOW_rule__Pop__ListeAssignment_0_in_rule__Pop__Group__0__Impl5586);
            rule__Pop__ListeAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPopAccess().getListeAssignment_0()); 
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
    // $ANTLR end "rule__Pop__Group__0__Impl"


    // $ANTLR start "rule__Pop__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2743:1: rule__Pop__Group__1 : rule__Pop__Group__1__Impl rule__Pop__Group__2 ;
    public final void rule__Pop__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2747:1: ( rule__Pop__Group__1__Impl rule__Pop__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2748:2: rule__Pop__Group__1__Impl rule__Pop__Group__2
            {
            pushFollow(FOLLOW_rule__Pop__Group__1__Impl_in_rule__Pop__Group__15616);
            rule__Pop__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Pop__Group__2_in_rule__Pop__Group__15619);
            rule__Pop__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Pop__Group__1"


    // $ANTLR start "rule__Pop__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2755:1: rule__Pop__Group__1__Impl : ( '.pop' ) ;
    public final void rule__Pop__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2759:1: ( ( '.pop' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2760:1: ( '.pop' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2760:1: ( '.pop' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2761:1: '.pop'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPopAccess().getPopKeyword_1()); 
            }
            match(input,39,FOLLOW_39_in_rule__Pop__Group__1__Impl5647); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPopAccess().getPopKeyword_1()); 
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
    // $ANTLR end "rule__Pop__Group__1__Impl"


    // $ANTLR start "rule__Pop__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2774:1: rule__Pop__Group__2 : rule__Pop__Group__2__Impl rule__Pop__Group__3 ;
    public final void rule__Pop__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2778:1: ( rule__Pop__Group__2__Impl rule__Pop__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2779:2: rule__Pop__Group__2__Impl rule__Pop__Group__3
            {
            pushFollow(FOLLOW_rule__Pop__Group__2__Impl_in_rule__Pop__Group__25678);
            rule__Pop__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Pop__Group__3_in_rule__Pop__Group__25681);
            rule__Pop__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Pop__Group__2"


    // $ANTLR start "rule__Pop__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2786:1: rule__Pop__Group__2__Impl : ( '(' ) ;
    public final void rule__Pop__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2790:1: ( ( '(' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2791:1: ( '(' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2791:1: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2792:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPopAccess().getLeftParenthesisKeyword_2()); 
            }
            match(input,32,FOLLOW_32_in_rule__Pop__Group__2__Impl5709); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPopAccess().getLeftParenthesisKeyword_2()); 
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
    // $ANTLR end "rule__Pop__Group__2__Impl"


    // $ANTLR start "rule__Pop__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2805:1: rule__Pop__Group__3 : rule__Pop__Group__3__Impl rule__Pop__Group__4 ;
    public final void rule__Pop__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2809:1: ( rule__Pop__Group__3__Impl rule__Pop__Group__4 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2810:2: rule__Pop__Group__3__Impl rule__Pop__Group__4
            {
            pushFollow(FOLLOW_rule__Pop__Group__3__Impl_in_rule__Pop__Group__35740);
            rule__Pop__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Pop__Group__4_in_rule__Pop__Group__35743);
            rule__Pop__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Pop__Group__3"


    // $ANTLR start "rule__Pop__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2817:1: rule__Pop__Group__3__Impl : ( ')' ) ;
    public final void rule__Pop__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2821:1: ( ( ')' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2822:1: ( ')' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2822:1: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2823:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPopAccess().getRightParenthesisKeyword_3()); 
            }
            match(input,33,FOLLOW_33_in_rule__Pop__Group__3__Impl5771); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPopAccess().getRightParenthesisKeyword_3()); 
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
    // $ANTLR end "rule__Pop__Group__3__Impl"


    // $ANTLR start "rule__Pop__Group__4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2836:1: rule__Pop__Group__4 : rule__Pop__Group__4__Impl ;
    public final void rule__Pop__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2840:1: ( rule__Pop__Group__4__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2841:2: rule__Pop__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__Pop__Group__4__Impl_in_rule__Pop__Group__45802);
            rule__Pop__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Pop__Group__4"


    // $ANTLR start "rule__Pop__Group__4__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2847:1: rule__Pop__Group__4__Impl : ( ';' ) ;
    public final void rule__Pop__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2851:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2852:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2852:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2853:1: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPopAccess().getSemicolonKeyword_4()); 
            }
            match(input,28,FOLLOW_28_in_rule__Pop__Group__4__Impl5830); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPopAccess().getSemicolonKeyword_4()); 
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
    // $ANTLR end "rule__Pop__Group__4__Impl"


    // $ANTLR start "rule__Peek__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2876:1: rule__Peek__Group__0 : rule__Peek__Group__0__Impl rule__Peek__Group__1 ;
    public final void rule__Peek__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2880:1: ( rule__Peek__Group__0__Impl rule__Peek__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2881:2: rule__Peek__Group__0__Impl rule__Peek__Group__1
            {
            pushFollow(FOLLOW_rule__Peek__Group__0__Impl_in_rule__Peek__Group__05871);
            rule__Peek__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Peek__Group__1_in_rule__Peek__Group__05874);
            rule__Peek__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Peek__Group__0"


    // $ANTLR start "rule__Peek__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2888:1: rule__Peek__Group__0__Impl : ( ( rule__Peek__ListeAssignment_0 ) ) ;
    public final void rule__Peek__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2892:1: ( ( ( rule__Peek__ListeAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2893:1: ( ( rule__Peek__ListeAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2893:1: ( ( rule__Peek__ListeAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2894:1: ( rule__Peek__ListeAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPeekAccess().getListeAssignment_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2895:1: ( rule__Peek__ListeAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2895:2: rule__Peek__ListeAssignment_0
            {
            pushFollow(FOLLOW_rule__Peek__ListeAssignment_0_in_rule__Peek__Group__0__Impl5901);
            rule__Peek__ListeAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPeekAccess().getListeAssignment_0()); 
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
    // $ANTLR end "rule__Peek__Group__0__Impl"


    // $ANTLR start "rule__Peek__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2905:1: rule__Peek__Group__1 : rule__Peek__Group__1__Impl rule__Peek__Group__2 ;
    public final void rule__Peek__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2909:1: ( rule__Peek__Group__1__Impl rule__Peek__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2910:2: rule__Peek__Group__1__Impl rule__Peek__Group__2
            {
            pushFollow(FOLLOW_rule__Peek__Group__1__Impl_in_rule__Peek__Group__15931);
            rule__Peek__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Peek__Group__2_in_rule__Peek__Group__15934);
            rule__Peek__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Peek__Group__1"


    // $ANTLR start "rule__Peek__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2917:1: rule__Peek__Group__1__Impl : ( '.peek' ) ;
    public final void rule__Peek__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2921:1: ( ( '.peek' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2922:1: ( '.peek' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2922:1: ( '.peek' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2923:1: '.peek'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPeekAccess().getPeekKeyword_1()); 
            }
            match(input,40,FOLLOW_40_in_rule__Peek__Group__1__Impl5962); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPeekAccess().getPeekKeyword_1()); 
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
    // $ANTLR end "rule__Peek__Group__1__Impl"


    // $ANTLR start "rule__Peek__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2936:1: rule__Peek__Group__2 : rule__Peek__Group__2__Impl rule__Peek__Group__3 ;
    public final void rule__Peek__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2940:1: ( rule__Peek__Group__2__Impl rule__Peek__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2941:2: rule__Peek__Group__2__Impl rule__Peek__Group__3
            {
            pushFollow(FOLLOW_rule__Peek__Group__2__Impl_in_rule__Peek__Group__25993);
            rule__Peek__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Peek__Group__3_in_rule__Peek__Group__25996);
            rule__Peek__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Peek__Group__2"


    // $ANTLR start "rule__Peek__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2948:1: rule__Peek__Group__2__Impl : ( '(' ) ;
    public final void rule__Peek__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2952:1: ( ( '(' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2953:1: ( '(' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2953:1: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2954:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPeekAccess().getLeftParenthesisKeyword_2()); 
            }
            match(input,32,FOLLOW_32_in_rule__Peek__Group__2__Impl6024); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPeekAccess().getLeftParenthesisKeyword_2()); 
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
    // $ANTLR end "rule__Peek__Group__2__Impl"


    // $ANTLR start "rule__Peek__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2967:1: rule__Peek__Group__3 : rule__Peek__Group__3__Impl ;
    public final void rule__Peek__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2971:1: ( rule__Peek__Group__3__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2972:2: rule__Peek__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Peek__Group__3__Impl_in_rule__Peek__Group__36055);
            rule__Peek__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Peek__Group__3"


    // $ANTLR start "rule__Peek__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2978:1: rule__Peek__Group__3__Impl : ( ')' ) ;
    public final void rule__Peek__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2982:1: ( ( ')' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2983:1: ( ')' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2983:1: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:2984:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPeekAccess().getRightParenthesisKeyword_3()); 
            }
            match(input,33,FOLLOW_33_in_rule__Peek__Group__3__Impl6083); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPeekAccess().getRightParenthesisKeyword_3()); 
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
    // $ANTLR end "rule__Peek__Group__3__Impl"


    // $ANTLR start "rule__Assignment__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3005:1: rule__Assignment__Group__0 : rule__Assignment__Group__0__Impl rule__Assignment__Group__1 ;
    public final void rule__Assignment__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3009:1: ( rule__Assignment__Group__0__Impl rule__Assignment__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3010:2: rule__Assignment__Group__0__Impl rule__Assignment__Group__1
            {
            pushFollow(FOLLOW_rule__Assignment__Group__0__Impl_in_rule__Assignment__Group__06122);
            rule__Assignment__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Assignment__Group__1_in_rule__Assignment__Group__06125);
            rule__Assignment__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3017:1: rule__Assignment__Group__0__Impl : ( ( rule__Assignment__LeftAssignment_0 ) ) ;
    public final void rule__Assignment__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3021:1: ( ( ( rule__Assignment__LeftAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3022:1: ( ( rule__Assignment__LeftAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3022:1: ( ( rule__Assignment__LeftAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3023:1: ( rule__Assignment__LeftAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getLeftAssignment_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3024:1: ( rule__Assignment__LeftAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3024:2: rule__Assignment__LeftAssignment_0
            {
            pushFollow(FOLLOW_rule__Assignment__LeftAssignment_0_in_rule__Assignment__Group__0__Impl6152);
            rule__Assignment__LeftAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getLeftAssignment_0()); 
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
    // $ANTLR end "rule__Assignment__Group__0__Impl"


    // $ANTLR start "rule__Assignment__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3034:1: rule__Assignment__Group__1 : rule__Assignment__Group__1__Impl rule__Assignment__Group__2 ;
    public final void rule__Assignment__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3038:1: ( rule__Assignment__Group__1__Impl rule__Assignment__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3039:2: rule__Assignment__Group__1__Impl rule__Assignment__Group__2
            {
            pushFollow(FOLLOW_rule__Assignment__Group__1__Impl_in_rule__Assignment__Group__16182);
            rule__Assignment__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Assignment__Group__2_in_rule__Assignment__Group__16185);
            rule__Assignment__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3046:1: rule__Assignment__Group__1__Impl : ( '=' ) ;
    public final void rule__Assignment__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3050:1: ( ( '=' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3051:1: ( '=' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3051:1: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3052:1: '='
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1()); 
            }
            match(input,27,FOLLOW_27_in_rule__Assignment__Group__1__Impl6213); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1()); 
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
    // $ANTLR end "rule__Assignment__Group__1__Impl"


    // $ANTLR start "rule__Assignment__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3065:1: rule__Assignment__Group__2 : rule__Assignment__Group__2__Impl rule__Assignment__Group__3 ;
    public final void rule__Assignment__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3069:1: ( rule__Assignment__Group__2__Impl rule__Assignment__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3070:2: rule__Assignment__Group__2__Impl rule__Assignment__Group__3
            {
            pushFollow(FOLLOW_rule__Assignment__Group__2__Impl_in_rule__Assignment__Group__26244);
            rule__Assignment__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Assignment__Group__3_in_rule__Assignment__Group__26247);
            rule__Assignment__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3077:1: rule__Assignment__Group__2__Impl : ( ( rule__Assignment__RightAssignment_2 ) ) ;
    public final void rule__Assignment__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3081:1: ( ( ( rule__Assignment__RightAssignment_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3082:1: ( ( rule__Assignment__RightAssignment_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3082:1: ( ( rule__Assignment__RightAssignment_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3083:1: ( rule__Assignment__RightAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getRightAssignment_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3084:1: ( rule__Assignment__RightAssignment_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3084:2: rule__Assignment__RightAssignment_2
            {
            pushFollow(FOLLOW_rule__Assignment__RightAssignment_2_in_rule__Assignment__Group__2__Impl6274);
            rule__Assignment__RightAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getRightAssignment_2()); 
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
    // $ANTLR end "rule__Assignment__Group__2__Impl"


    // $ANTLR start "rule__Assignment__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3094:1: rule__Assignment__Group__3 : rule__Assignment__Group__3__Impl ;
    public final void rule__Assignment__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3098:1: ( rule__Assignment__Group__3__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3099:2: rule__Assignment__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Assignment__Group__3__Impl_in_rule__Assignment__Group__36304);
            rule__Assignment__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3105:1: rule__Assignment__Group__3__Impl : ( ';' ) ;
    public final void rule__Assignment__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3109:1: ( ( ';' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3110:1: ( ';' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3110:1: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3111:1: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getSemicolonKeyword_3()); 
            }
            match(input,28,FOLLOW_28_in_rule__Assignment__Group__3__Impl6332); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getSemicolonKeyword_3()); 
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
    // $ANTLR end "rule__Assignment__Group__3__Impl"


    // $ANTLR start "rule__ArrayVarAccess__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3132:1: rule__ArrayVarAccess__Group__0 : rule__ArrayVarAccess__Group__0__Impl rule__ArrayVarAccess__Group__1 ;
    public final void rule__ArrayVarAccess__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3136:1: ( rule__ArrayVarAccess__Group__0__Impl rule__ArrayVarAccess__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3137:2: rule__ArrayVarAccess__Group__0__Impl rule__ArrayVarAccess__Group__1
            {
            pushFollow(FOLLOW_rule__ArrayVarAccess__Group__0__Impl_in_rule__ArrayVarAccess__Group__06371);
            rule__ArrayVarAccess__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayVarAccess__Group__1_in_rule__ArrayVarAccess__Group__06374);
            rule__ArrayVarAccess__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayVarAccess__Group__0"


    // $ANTLR start "rule__ArrayVarAccess__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3144:1: rule__ArrayVarAccess__Group__0__Impl : ( ( rule__ArrayVarAccess__PrefixAssignment_0 ) ) ;
    public final void rule__ArrayVarAccess__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3148:1: ( ( ( rule__ArrayVarAccess__PrefixAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3149:1: ( ( rule__ArrayVarAccess__PrefixAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3149:1: ( ( rule__ArrayVarAccess__PrefixAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3150:1: ( rule__ArrayVarAccess__PrefixAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayVarAccessAccess().getPrefixAssignment_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3151:1: ( rule__ArrayVarAccess__PrefixAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3151:2: rule__ArrayVarAccess__PrefixAssignment_0
            {
            pushFollow(FOLLOW_rule__ArrayVarAccess__PrefixAssignment_0_in_rule__ArrayVarAccess__Group__0__Impl6401);
            rule__ArrayVarAccess__PrefixAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayVarAccessAccess().getPrefixAssignment_0()); 
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
    // $ANTLR end "rule__ArrayVarAccess__Group__0__Impl"


    // $ANTLR start "rule__ArrayVarAccess__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3161:1: rule__ArrayVarAccess__Group__1 : rule__ArrayVarAccess__Group__1__Impl rule__ArrayVarAccess__Group__2 ;
    public final void rule__ArrayVarAccess__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3165:1: ( rule__ArrayVarAccess__Group__1__Impl rule__ArrayVarAccess__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3166:2: rule__ArrayVarAccess__Group__1__Impl rule__ArrayVarAccess__Group__2
            {
            pushFollow(FOLLOW_rule__ArrayVarAccess__Group__1__Impl_in_rule__ArrayVarAccess__Group__16431);
            rule__ArrayVarAccess__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayVarAccess__Group__2_in_rule__ArrayVarAccess__Group__16434);
            rule__ArrayVarAccess__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayVarAccess__Group__1"


    // $ANTLR start "rule__ArrayVarAccess__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3173:1: rule__ArrayVarAccess__Group__1__Impl : ( '[' ) ;
    public final void rule__ArrayVarAccess__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3177:1: ( ( '[' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3178:1: ( '[' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3178:1: ( '[' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3179:1: '['
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayVarAccessAccess().getLeftSquareBracketKeyword_1()); 
            }
            match(input,30,FOLLOW_30_in_rule__ArrayVarAccess__Group__1__Impl6462); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayVarAccessAccess().getLeftSquareBracketKeyword_1()); 
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
    // $ANTLR end "rule__ArrayVarAccess__Group__1__Impl"


    // $ANTLR start "rule__ArrayVarAccess__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3192:1: rule__ArrayVarAccess__Group__2 : rule__ArrayVarAccess__Group__2__Impl rule__ArrayVarAccess__Group__3 ;
    public final void rule__ArrayVarAccess__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3196:1: ( rule__ArrayVarAccess__Group__2__Impl rule__ArrayVarAccess__Group__3 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3197:2: rule__ArrayVarAccess__Group__2__Impl rule__ArrayVarAccess__Group__3
            {
            pushFollow(FOLLOW_rule__ArrayVarAccess__Group__2__Impl_in_rule__ArrayVarAccess__Group__26493);
            rule__ArrayVarAccess__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__ArrayVarAccess__Group__3_in_rule__ArrayVarAccess__Group__26496);
            rule__ArrayVarAccess__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayVarAccess__Group__2"


    // $ANTLR start "rule__ArrayVarAccess__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3204:1: rule__ArrayVarAccess__Group__2__Impl : ( ( rule__ArrayVarAccess__IndexAssignment_2 ) ) ;
    public final void rule__ArrayVarAccess__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3208:1: ( ( ( rule__ArrayVarAccess__IndexAssignment_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3209:1: ( ( rule__ArrayVarAccess__IndexAssignment_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3209:1: ( ( rule__ArrayVarAccess__IndexAssignment_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3210:1: ( rule__ArrayVarAccess__IndexAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayVarAccessAccess().getIndexAssignment_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3211:1: ( rule__ArrayVarAccess__IndexAssignment_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3211:2: rule__ArrayVarAccess__IndexAssignment_2
            {
            pushFollow(FOLLOW_rule__ArrayVarAccess__IndexAssignment_2_in_rule__ArrayVarAccess__Group__2__Impl6523);
            rule__ArrayVarAccess__IndexAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayVarAccessAccess().getIndexAssignment_2()); 
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
    // $ANTLR end "rule__ArrayVarAccess__Group__2__Impl"


    // $ANTLR start "rule__ArrayVarAccess__Group__3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3221:1: rule__ArrayVarAccess__Group__3 : rule__ArrayVarAccess__Group__3__Impl ;
    public final void rule__ArrayVarAccess__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3225:1: ( rule__ArrayVarAccess__Group__3__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3226:2: rule__ArrayVarAccess__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__ArrayVarAccess__Group__3__Impl_in_rule__ArrayVarAccess__Group__36553);
            rule__ArrayVarAccess__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArrayVarAccess__Group__3"


    // $ANTLR start "rule__ArrayVarAccess__Group__3__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3232:1: rule__ArrayVarAccess__Group__3__Impl : ( ']' ) ;
    public final void rule__ArrayVarAccess__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3236:1: ( ( ']' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3237:1: ( ']' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3237:1: ( ']' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3238:1: ']'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayVarAccessAccess().getRightSquareBracketKeyword_3()); 
            }
            match(input,31,FOLLOW_31_in_rule__ArrayVarAccess__Group__3__Impl6581); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayVarAccessAccess().getRightSquareBracketKeyword_3()); 
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
    // $ANTLR end "rule__ArrayVarAccess__Group__3__Impl"


    // $ANTLR start "rule__BinaryIntExpression__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3259:1: rule__BinaryIntExpression__Group__0 : rule__BinaryIntExpression__Group__0__Impl rule__BinaryIntExpression__Group__1 ;
    public final void rule__BinaryIntExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3263:1: ( rule__BinaryIntExpression__Group__0__Impl rule__BinaryIntExpression__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3264:2: rule__BinaryIntExpression__Group__0__Impl rule__BinaryIntExpression__Group__1
            {
            pushFollow(FOLLOW_rule__BinaryIntExpression__Group__0__Impl_in_rule__BinaryIntExpression__Group__06620);
            rule__BinaryIntExpression__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__BinaryIntExpression__Group__1_in_rule__BinaryIntExpression__Group__06623);
            rule__BinaryIntExpression__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BinaryIntExpression__Group__0"


    // $ANTLR start "rule__BinaryIntExpression__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3271:1: rule__BinaryIntExpression__Group__0__Impl : ( ruleUnaryIntExpression ) ;
    public final void rule__BinaryIntExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3275:1: ( ( ruleUnaryIntExpression ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3276:1: ( ruleUnaryIntExpression )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3276:1: ( ruleUnaryIntExpression )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3277:1: ruleUnaryIntExpression
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryIntExpressionAccess().getUnaryIntExpressionParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_ruleUnaryIntExpression_in_rule__BinaryIntExpression__Group__0__Impl6650);
            ruleUnaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryIntExpressionAccess().getUnaryIntExpressionParserRuleCall_0()); 
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
    // $ANTLR end "rule__BinaryIntExpression__Group__0__Impl"


    // $ANTLR start "rule__BinaryIntExpression__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3288:1: rule__BinaryIntExpression__Group__1 : rule__BinaryIntExpression__Group__1__Impl ;
    public final void rule__BinaryIntExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3292:1: ( rule__BinaryIntExpression__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3293:2: rule__BinaryIntExpression__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__BinaryIntExpression__Group__1__Impl_in_rule__BinaryIntExpression__Group__16679);
            rule__BinaryIntExpression__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BinaryIntExpression__Group__1"


    // $ANTLR start "rule__BinaryIntExpression__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3299:1: rule__BinaryIntExpression__Group__1__Impl : ( ( rule__BinaryIntExpression__Group_1__0 )* ) ;
    public final void rule__BinaryIntExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3303:1: ( ( ( rule__BinaryIntExpression__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3304:1: ( ( rule__BinaryIntExpression__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3304:1: ( ( rule__BinaryIntExpression__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3305:1: ( rule__BinaryIntExpression__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryIntExpressionAccess().getGroup_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3306:1: ( rule__BinaryIntExpression__Group_1__0 )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=11 && LA16_0<=16)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3306:2: rule__BinaryIntExpression__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__BinaryIntExpression__Group_1__0_in_rule__BinaryIntExpression__Group__1__Impl6706);
            	    rule__BinaryIntExpression__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryIntExpressionAccess().getGroup_1()); 
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
    // $ANTLR end "rule__BinaryIntExpression__Group__1__Impl"


    // $ANTLR start "rule__BinaryIntExpression__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3320:1: rule__BinaryIntExpression__Group_1__0 : rule__BinaryIntExpression__Group_1__0__Impl rule__BinaryIntExpression__Group_1__1 ;
    public final void rule__BinaryIntExpression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3324:1: ( rule__BinaryIntExpression__Group_1__0__Impl rule__BinaryIntExpression__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3325:2: rule__BinaryIntExpression__Group_1__0__Impl rule__BinaryIntExpression__Group_1__1
            {
            pushFollow(FOLLOW_rule__BinaryIntExpression__Group_1__0__Impl_in_rule__BinaryIntExpression__Group_1__06741);
            rule__BinaryIntExpression__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__BinaryIntExpression__Group_1__1_in_rule__BinaryIntExpression__Group_1__06744);
            rule__BinaryIntExpression__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BinaryIntExpression__Group_1__0"


    // $ANTLR start "rule__BinaryIntExpression__Group_1__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3332:1: rule__BinaryIntExpression__Group_1__0__Impl : ( () ) ;
    public final void rule__BinaryIntExpression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3336:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3337:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3337:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3338:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryIntExpressionAccess().getBinaryIntExpressionLeftAction_1_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3339:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3341:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryIntExpressionAccess().getBinaryIntExpressionLeftAction_1_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BinaryIntExpression__Group_1__0__Impl"


    // $ANTLR start "rule__BinaryIntExpression__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3351:1: rule__BinaryIntExpression__Group_1__1 : rule__BinaryIntExpression__Group_1__1__Impl rule__BinaryIntExpression__Group_1__2 ;
    public final void rule__BinaryIntExpression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3355:1: ( rule__BinaryIntExpression__Group_1__1__Impl rule__BinaryIntExpression__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3356:2: rule__BinaryIntExpression__Group_1__1__Impl rule__BinaryIntExpression__Group_1__2
            {
            pushFollow(FOLLOW_rule__BinaryIntExpression__Group_1__1__Impl_in_rule__BinaryIntExpression__Group_1__16802);
            rule__BinaryIntExpression__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__BinaryIntExpression__Group_1__2_in_rule__BinaryIntExpression__Group_1__16805);
            rule__BinaryIntExpression__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BinaryIntExpression__Group_1__1"


    // $ANTLR start "rule__BinaryIntExpression__Group_1__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3363:1: rule__BinaryIntExpression__Group_1__1__Impl : ( ( rule__BinaryIntExpression__OpAssignment_1_1 ) ) ;
    public final void rule__BinaryIntExpression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3367:1: ( ( ( rule__BinaryIntExpression__OpAssignment_1_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3368:1: ( ( rule__BinaryIntExpression__OpAssignment_1_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3368:1: ( ( rule__BinaryIntExpression__OpAssignment_1_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3369:1: ( rule__BinaryIntExpression__OpAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryIntExpressionAccess().getOpAssignment_1_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3370:1: ( rule__BinaryIntExpression__OpAssignment_1_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3370:2: rule__BinaryIntExpression__OpAssignment_1_1
            {
            pushFollow(FOLLOW_rule__BinaryIntExpression__OpAssignment_1_1_in_rule__BinaryIntExpression__Group_1__1__Impl6832);
            rule__BinaryIntExpression__OpAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryIntExpressionAccess().getOpAssignment_1_1()); 
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
    // $ANTLR end "rule__BinaryIntExpression__Group_1__1__Impl"


    // $ANTLR start "rule__BinaryIntExpression__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3380:1: rule__BinaryIntExpression__Group_1__2 : rule__BinaryIntExpression__Group_1__2__Impl ;
    public final void rule__BinaryIntExpression__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3384:1: ( rule__BinaryIntExpression__Group_1__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3385:2: rule__BinaryIntExpression__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__BinaryIntExpression__Group_1__2__Impl_in_rule__BinaryIntExpression__Group_1__26862);
            rule__BinaryIntExpression__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BinaryIntExpression__Group_1__2"


    // $ANTLR start "rule__BinaryIntExpression__Group_1__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3391:1: rule__BinaryIntExpression__Group_1__2__Impl : ( ( rule__BinaryIntExpression__RightAssignment_1_2 ) ) ;
    public final void rule__BinaryIntExpression__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3395:1: ( ( ( rule__BinaryIntExpression__RightAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3396:1: ( ( rule__BinaryIntExpression__RightAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3396:1: ( ( rule__BinaryIntExpression__RightAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3397:1: ( rule__BinaryIntExpression__RightAssignment_1_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryIntExpressionAccess().getRightAssignment_1_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3398:1: ( rule__BinaryIntExpression__RightAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3398:2: rule__BinaryIntExpression__RightAssignment_1_2
            {
            pushFollow(FOLLOW_rule__BinaryIntExpression__RightAssignment_1_2_in_rule__BinaryIntExpression__Group_1__2__Impl6889);
            rule__BinaryIntExpression__RightAssignment_1_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryIntExpressionAccess().getRightAssignment_1_2()); 
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
    // $ANTLR end "rule__BinaryIntExpression__Group_1__2__Impl"


    // $ANTLR start "rule__UnaryIntExpression__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3414:1: rule__UnaryIntExpression__Group__0 : rule__UnaryIntExpression__Group__0__Impl rule__UnaryIntExpression__Group__1 ;
    public final void rule__UnaryIntExpression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3418:1: ( rule__UnaryIntExpression__Group__0__Impl rule__UnaryIntExpression__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3419:2: rule__UnaryIntExpression__Group__0__Impl rule__UnaryIntExpression__Group__1
            {
            pushFollow(FOLLOW_rule__UnaryIntExpression__Group__0__Impl_in_rule__UnaryIntExpression__Group__06925);
            rule__UnaryIntExpression__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__UnaryIntExpression__Group__1_in_rule__UnaryIntExpression__Group__06928);
            rule__UnaryIntExpression__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryIntExpression__Group__0"


    // $ANTLR start "rule__UnaryIntExpression__Group__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3426:1: rule__UnaryIntExpression__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__UnaryIntExpression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3430:1: ( ( ( '-' )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3431:1: ( ( '-' )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3431:1: ( ( '-' )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3432:1: ( '-' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnaryIntExpressionAccess().getHyphenMinusKeyword_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3433:1: ( '-' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==12) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3434:2: '-'
                    {
                    match(input,12,FOLLOW_12_in_rule__UnaryIntExpression__Group__0__Impl6957); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnaryIntExpressionAccess().getHyphenMinusKeyword_0()); 
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
    // $ANTLR end "rule__UnaryIntExpression__Group__0__Impl"


    // $ANTLR start "rule__UnaryIntExpression__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3445:1: rule__UnaryIntExpression__Group__1 : rule__UnaryIntExpression__Group__1__Impl rule__UnaryIntExpression__Group__2 ;
    public final void rule__UnaryIntExpression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3449:1: ( rule__UnaryIntExpression__Group__1__Impl rule__UnaryIntExpression__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3450:2: rule__UnaryIntExpression__Group__1__Impl rule__UnaryIntExpression__Group__2
            {
            pushFollow(FOLLOW_rule__UnaryIntExpression__Group__1__Impl_in_rule__UnaryIntExpression__Group__16990);
            rule__UnaryIntExpression__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__UnaryIntExpression__Group__2_in_rule__UnaryIntExpression__Group__16993);
            rule__UnaryIntExpression__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryIntExpression__Group__1"


    // $ANTLR start "rule__UnaryIntExpression__Group__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3457:1: rule__UnaryIntExpression__Group__1__Impl : ( rulePrimary ) ;
    public final void rule__UnaryIntExpression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3461:1: ( ( rulePrimary ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3462:1: ( rulePrimary )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3462:1: ( rulePrimary )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3463:1: rulePrimary
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnaryIntExpressionAccess().getPrimaryParserRuleCall_1()); 
            }
            pushFollow(FOLLOW_rulePrimary_in_rule__UnaryIntExpression__Group__1__Impl7020);
            rulePrimary();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnaryIntExpressionAccess().getPrimaryParserRuleCall_1()); 
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
    // $ANTLR end "rule__UnaryIntExpression__Group__1__Impl"


    // $ANTLR start "rule__UnaryIntExpression__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3474:1: rule__UnaryIntExpression__Group__2 : rule__UnaryIntExpression__Group__2__Impl ;
    public final void rule__UnaryIntExpression__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3478:1: ( rule__UnaryIntExpression__Group__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3479:2: rule__UnaryIntExpression__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__UnaryIntExpression__Group__2__Impl_in_rule__UnaryIntExpression__Group__27049);
            rule__UnaryIntExpression__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryIntExpression__Group__2"


    // $ANTLR start "rule__UnaryIntExpression__Group__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3485:1: rule__UnaryIntExpression__Group__2__Impl : ( () ) ;
    public final void rule__UnaryIntExpression__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3489:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3490:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3490:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3491:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getUnaryIntExpressionAccess().getUnaryIntExpressionValAction_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3492:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3494:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getUnaryIntExpressionAccess().getUnaryIntExpressionValAction_2()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UnaryIntExpression__Group__2__Impl"


    // $ANTLR start "rule__Primary__Group_3__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3510:1: rule__Primary__Group_3__0 : rule__Primary__Group_3__0__Impl rule__Primary__Group_3__1 ;
    public final void rule__Primary__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3514:1: ( rule__Primary__Group_3__0__Impl rule__Primary__Group_3__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3515:2: rule__Primary__Group_3__0__Impl rule__Primary__Group_3__1
            {
            pushFollow(FOLLOW_rule__Primary__Group_3__0__Impl_in_rule__Primary__Group_3__07113);
            rule__Primary__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Primary__Group_3__1_in_rule__Primary__Group_3__07116);
            rule__Primary__Group_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Group_3__0"


    // $ANTLR start "rule__Primary__Group_3__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3522:1: rule__Primary__Group_3__0__Impl : ( '(' ) ;
    public final void rule__Primary__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3526:1: ( ( '(' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3527:1: ( '(' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3527:1: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3528:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0()); 
            }
            match(input,32,FOLLOW_32_in_rule__Primary__Group_3__0__Impl7144); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0()); 
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
    // $ANTLR end "rule__Primary__Group_3__0__Impl"


    // $ANTLR start "rule__Primary__Group_3__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3541:1: rule__Primary__Group_3__1 : rule__Primary__Group_3__1__Impl rule__Primary__Group_3__2 ;
    public final void rule__Primary__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3545:1: ( rule__Primary__Group_3__1__Impl rule__Primary__Group_3__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3546:2: rule__Primary__Group_3__1__Impl rule__Primary__Group_3__2
            {
            pushFollow(FOLLOW_rule__Primary__Group_3__1__Impl_in_rule__Primary__Group_3__17175);
            rule__Primary__Group_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Primary__Group_3__2_in_rule__Primary__Group_3__17178);
            rule__Primary__Group_3__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Group_3__1"


    // $ANTLR start "rule__Primary__Group_3__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3553:1: rule__Primary__Group_3__1__Impl : ( ruleBinaryIntExpression ) ;
    public final void rule__Primary__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3557:1: ( ( ruleBinaryIntExpression ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3558:1: ( ruleBinaryIntExpression )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3558:1: ( ruleBinaryIntExpression )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3559:1: ruleBinaryIntExpression
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryAccess().getBinaryIntExpressionParserRuleCall_3_1()); 
            }
            pushFollow(FOLLOW_ruleBinaryIntExpression_in_rule__Primary__Group_3__1__Impl7205);
            ruleBinaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryAccess().getBinaryIntExpressionParserRuleCall_3_1()); 
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
    // $ANTLR end "rule__Primary__Group_3__1__Impl"


    // $ANTLR start "rule__Primary__Group_3__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3570:1: rule__Primary__Group_3__2 : rule__Primary__Group_3__2__Impl ;
    public final void rule__Primary__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3574:1: ( rule__Primary__Group_3__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3575:2: rule__Primary__Group_3__2__Impl
            {
            pushFollow(FOLLOW_rule__Primary__Group_3__2__Impl_in_rule__Primary__Group_3__27234);
            rule__Primary__Group_3__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary__Group_3__2"


    // $ANTLR start "rule__Primary__Group_3__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3581:1: rule__Primary__Group_3__2__Impl : ( ')' ) ;
    public final void rule__Primary__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3585:1: ( ( ')' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3586:1: ( ')' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3586:1: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3587:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_3_2()); 
            }
            match(input,33,FOLLOW_33_in_rule__Primary__Group_3__2__Impl7262); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_3_2()); 
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
    // $ANTLR end "rule__Primary__Group_3__2__Impl"


    // $ANTLR start "rule__Or__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3606:1: rule__Or__Group__0 : rule__Or__Group__0__Impl rule__Or__Group__1 ;
    public final void rule__Or__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3610:1: ( rule__Or__Group__0__Impl rule__Or__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3611:2: rule__Or__Group__0__Impl rule__Or__Group__1
            {
            pushFollow(FOLLOW_rule__Or__Group__0__Impl_in_rule__Or__Group__07299);
            rule__Or__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Or__Group__1_in_rule__Or__Group__07302);
            rule__Or__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3618:1: rule__Or__Group__0__Impl : ( ruleAnd ) ;
    public final void rule__Or__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3622:1: ( ( ruleAnd ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3623:1: ( ruleAnd )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3623:1: ( ruleAnd )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3624:1: ruleAnd
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOrAccess().getAndParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_ruleAnd_in_rule__Or__Group__0__Impl7329);
            ruleAnd();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getOrAccess().getAndParserRuleCall_0()); 
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
    // $ANTLR end "rule__Or__Group__0__Impl"


    // $ANTLR start "rule__Or__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3635:1: rule__Or__Group__1 : rule__Or__Group__1__Impl ;
    public final void rule__Or__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3639:1: ( rule__Or__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3640:2: rule__Or__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Or__Group__1__Impl_in_rule__Or__Group__17358);
            rule__Or__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3646:1: rule__Or__Group__1__Impl : ( ( rule__Or__Group_1__0 )* ) ;
    public final void rule__Or__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3650:1: ( ( ( rule__Or__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3651:1: ( ( rule__Or__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3651:1: ( ( rule__Or__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3652:1: ( rule__Or__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOrAccess().getGroup_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3653:1: ( rule__Or__Group_1__0 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==41) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3653:2: rule__Or__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Or__Group_1__0_in_rule__Or__Group__1__Impl7385);
            	    rule__Or__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getOrAccess().getGroup_1()); 
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
    // $ANTLR end "rule__Or__Group__1__Impl"


    // $ANTLR start "rule__Or__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3667:1: rule__Or__Group_1__0 : rule__Or__Group_1__0__Impl rule__Or__Group_1__1 ;
    public final void rule__Or__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3671:1: ( rule__Or__Group_1__0__Impl rule__Or__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3672:2: rule__Or__Group_1__0__Impl rule__Or__Group_1__1
            {
            pushFollow(FOLLOW_rule__Or__Group_1__0__Impl_in_rule__Or__Group_1__07420);
            rule__Or__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Or__Group_1__1_in_rule__Or__Group_1__07423);
            rule__Or__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3679:1: rule__Or__Group_1__0__Impl : ( () ) ;
    public final void rule__Or__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3683:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3684:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3684:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3685:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOrAccess().getOrLeftAction_1_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3686:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3688:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getOrAccess().getOrLeftAction_1_0()); 
            }

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3698:1: rule__Or__Group_1__1 : rule__Or__Group_1__1__Impl rule__Or__Group_1__2 ;
    public final void rule__Or__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3702:1: ( rule__Or__Group_1__1__Impl rule__Or__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3703:2: rule__Or__Group_1__1__Impl rule__Or__Group_1__2
            {
            pushFollow(FOLLOW_rule__Or__Group_1__1__Impl_in_rule__Or__Group_1__17481);
            rule__Or__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Or__Group_1__2_in_rule__Or__Group_1__17484);
            rule__Or__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3710:1: rule__Or__Group_1__1__Impl : ( '||' ) ;
    public final void rule__Or__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3714:1: ( ( '||' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3715:1: ( '||' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3715:1: ( '||' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3716:1: '||'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOrAccess().getVerticalLineVerticalLineKeyword_1_1()); 
            }
            match(input,41,FOLLOW_41_in_rule__Or__Group_1__1__Impl7512); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getOrAccess().getVerticalLineVerticalLineKeyword_1_1()); 
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
    // $ANTLR end "rule__Or__Group_1__1__Impl"


    // $ANTLR start "rule__Or__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3729:1: rule__Or__Group_1__2 : rule__Or__Group_1__2__Impl ;
    public final void rule__Or__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3733:1: ( rule__Or__Group_1__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3734:2: rule__Or__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__Or__Group_1__2__Impl_in_rule__Or__Group_1__27543);
            rule__Or__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3740:1: rule__Or__Group_1__2__Impl : ( ( rule__Or__RightAssignment_1_2 ) ) ;
    public final void rule__Or__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3744:1: ( ( ( rule__Or__RightAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3745:1: ( ( rule__Or__RightAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3745:1: ( ( rule__Or__RightAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3746:1: ( rule__Or__RightAssignment_1_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOrAccess().getRightAssignment_1_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3747:1: ( rule__Or__RightAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3747:2: rule__Or__RightAssignment_1_2
            {
            pushFollow(FOLLOW_rule__Or__RightAssignment_1_2_in_rule__Or__Group_1__2__Impl7570);
            rule__Or__RightAssignment_1_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getOrAccess().getRightAssignment_1_2()); 
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
    // $ANTLR end "rule__Or__Group_1__2__Impl"


    // $ANTLR start "rule__And__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3763:1: rule__And__Group__0 : rule__And__Group__0__Impl rule__And__Group__1 ;
    public final void rule__And__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3767:1: ( rule__And__Group__0__Impl rule__And__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3768:2: rule__And__Group__0__Impl rule__And__Group__1
            {
            pushFollow(FOLLOW_rule__And__Group__0__Impl_in_rule__And__Group__07606);
            rule__And__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__And__Group__1_in_rule__And__Group__07609);
            rule__And__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3775:1: rule__And__Group__0__Impl : ( ruleNot ) ;
    public final void rule__And__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3779:1: ( ( ruleNot ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3780:1: ( ruleNot )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3780:1: ( ruleNot )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3781:1: ruleNot
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAndAccess().getNotParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_ruleNot_in_rule__And__Group__0__Impl7636);
            ruleNot();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAndAccess().getNotParserRuleCall_0()); 
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
    // $ANTLR end "rule__And__Group__0__Impl"


    // $ANTLR start "rule__And__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3792:1: rule__And__Group__1 : rule__And__Group__1__Impl ;
    public final void rule__And__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3796:1: ( rule__And__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3797:2: rule__And__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__And__Group__1__Impl_in_rule__And__Group__17665);
            rule__And__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3803:1: rule__And__Group__1__Impl : ( ( rule__And__Group_1__0 )* ) ;
    public final void rule__And__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3807:1: ( ( ( rule__And__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3808:1: ( ( rule__And__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3808:1: ( ( rule__And__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3809:1: ( rule__And__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAndAccess().getGroup_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3810:1: ( rule__And__Group_1__0 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==42) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3810:2: rule__And__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__And__Group_1__0_in_rule__And__Group__1__Impl7692);
            	    rule__And__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAndAccess().getGroup_1()); 
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
    // $ANTLR end "rule__And__Group__1__Impl"


    // $ANTLR start "rule__And__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3824:1: rule__And__Group_1__0 : rule__And__Group_1__0__Impl rule__And__Group_1__1 ;
    public final void rule__And__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3828:1: ( rule__And__Group_1__0__Impl rule__And__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3829:2: rule__And__Group_1__0__Impl rule__And__Group_1__1
            {
            pushFollow(FOLLOW_rule__And__Group_1__0__Impl_in_rule__And__Group_1__07727);
            rule__And__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__And__Group_1__1_in_rule__And__Group_1__07730);
            rule__And__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3836:1: rule__And__Group_1__0__Impl : ( () ) ;
    public final void rule__And__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3840:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3841:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3841:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3842:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAndAccess().getAndLeftAction_1_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3843:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3845:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAndAccess().getAndLeftAction_1_0()); 
            }

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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3855:1: rule__And__Group_1__1 : rule__And__Group_1__1__Impl rule__And__Group_1__2 ;
    public final void rule__And__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3859:1: ( rule__And__Group_1__1__Impl rule__And__Group_1__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3860:2: rule__And__Group_1__1__Impl rule__And__Group_1__2
            {
            pushFollow(FOLLOW_rule__And__Group_1__1__Impl_in_rule__And__Group_1__17788);
            rule__And__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__And__Group_1__2_in_rule__And__Group_1__17791);
            rule__And__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3867:1: rule__And__Group_1__1__Impl : ( '&&' ) ;
    public final void rule__And__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3871:1: ( ( '&&' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3872:1: ( '&&' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3872:1: ( '&&' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3873:1: '&&'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAndAccess().getAmpersandAmpersandKeyword_1_1()); 
            }
            match(input,42,FOLLOW_42_in_rule__And__Group_1__1__Impl7819); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAndAccess().getAmpersandAmpersandKeyword_1_1()); 
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
    // $ANTLR end "rule__And__Group_1__1__Impl"


    // $ANTLR start "rule__And__Group_1__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3886:1: rule__And__Group_1__2 : rule__And__Group_1__2__Impl ;
    public final void rule__And__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3890:1: ( rule__And__Group_1__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3891:2: rule__And__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__And__Group_1__2__Impl_in_rule__And__Group_1__27850);
            rule__And__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3897:1: rule__And__Group_1__2__Impl : ( ( rule__And__RightAssignment_1_2 ) ) ;
    public final void rule__And__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3901:1: ( ( ( rule__And__RightAssignment_1_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3902:1: ( ( rule__And__RightAssignment_1_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3902:1: ( ( rule__And__RightAssignment_1_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3903:1: ( rule__And__RightAssignment_1_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAndAccess().getRightAssignment_1_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3904:1: ( rule__And__RightAssignment_1_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3904:2: rule__And__RightAssignment_1_2
            {
            pushFollow(FOLLOW_rule__And__RightAssignment_1_2_in_rule__And__Group_1__2__Impl7877);
            rule__And__RightAssignment_1_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAndAccess().getRightAssignment_1_2()); 
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
    // $ANTLR end "rule__And__Group_1__2__Impl"


    // $ANTLR start "rule__Not__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3920:1: rule__Not__Group__0 : rule__Not__Group__0__Impl rule__Not__Group__1 ;
    public final void rule__Not__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3924:1: ( rule__Not__Group__0__Impl rule__Not__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3925:2: rule__Not__Group__0__Impl rule__Not__Group__1
            {
            pushFollow(FOLLOW_rule__Not__Group__0__Impl_in_rule__Not__Group__07913);
            rule__Not__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Not__Group__1_in_rule__Not__Group__07916);
            rule__Not__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3932:1: rule__Not__Group__0__Impl : ( ( '!' )? ) ;
    public final void rule__Not__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3936:1: ( ( ( '!' )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3937:1: ( ( '!' )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3937:1: ( ( '!' )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3938:1: ( '!' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNotAccess().getExclamationMarkKeyword_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3939:1: ( '!' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==43) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3940:2: '!'
                    {
                    match(input,43,FOLLOW_43_in_rule__Not__Group__0__Impl7945); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNotAccess().getExclamationMarkKeyword_0()); 
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
    // $ANTLR end "rule__Not__Group__0__Impl"


    // $ANTLR start "rule__Not__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3951:1: rule__Not__Group__1 : rule__Not__Group__1__Impl rule__Not__Group__2 ;
    public final void rule__Not__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3955:1: ( rule__Not__Group__1__Impl rule__Not__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3956:2: rule__Not__Group__1__Impl rule__Not__Group__2
            {
            pushFollow(FOLLOW_rule__Not__Group__1__Impl_in_rule__Not__Group__17978);
            rule__Not__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Not__Group__2_in_rule__Not__Group__17981);
            rule__Not__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3963:1: rule__Not__Group__1__Impl : ( rulePrimaryBool ) ;
    public final void rule__Not__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3967:1: ( ( rulePrimaryBool ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3968:1: ( rulePrimaryBool )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3968:1: ( rulePrimaryBool )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3969:1: rulePrimaryBool
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNotAccess().getPrimaryBoolParserRuleCall_1()); 
            }
            pushFollow(FOLLOW_rulePrimaryBool_in_rule__Not__Group__1__Impl8008);
            rulePrimaryBool();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNotAccess().getPrimaryBoolParserRuleCall_1()); 
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
    // $ANTLR end "rule__Not__Group__1__Impl"


    // $ANTLR start "rule__Not__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3980:1: rule__Not__Group__2 : rule__Not__Group__2__Impl ;
    public final void rule__Not__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3984:1: ( rule__Not__Group__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3985:2: rule__Not__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Not__Group__2__Impl_in_rule__Not__Group__28037);
            rule__Not__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3991:1: rule__Not__Group__2__Impl : ( () ) ;
    public final void rule__Not__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3995:1: ( ( () ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3996:1: ( () )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3996:1: ( () )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3997:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNotAccess().getNotValAction_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:3998:1: ()
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4000:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNotAccess().getNotValAction_2()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Not__Group__2__Impl"


    // $ANTLR start "rule__PrimaryBool__Group_3__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4016:1: rule__PrimaryBool__Group_3__0 : rule__PrimaryBool__Group_3__0__Impl rule__PrimaryBool__Group_3__1 ;
    public final void rule__PrimaryBool__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4020:1: ( rule__PrimaryBool__Group_3__0__Impl rule__PrimaryBool__Group_3__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4021:2: rule__PrimaryBool__Group_3__0__Impl rule__PrimaryBool__Group_3__1
            {
            pushFollow(FOLLOW_rule__PrimaryBool__Group_3__0__Impl_in_rule__PrimaryBool__Group_3__08101);
            rule__PrimaryBool__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__PrimaryBool__Group_3__1_in_rule__PrimaryBool__Group_3__08104);
            rule__PrimaryBool__Group_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryBool__Group_3__0"


    // $ANTLR start "rule__PrimaryBool__Group_3__0__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4028:1: rule__PrimaryBool__Group_3__0__Impl : ( '(' ) ;
    public final void rule__PrimaryBool__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4032:1: ( ( '(' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4033:1: ( '(' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4033:1: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4034:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0()); 
            }
            match(input,32,FOLLOW_32_in_rule__PrimaryBool__Group_3__0__Impl8132); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0()); 
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
    // $ANTLR end "rule__PrimaryBool__Group_3__0__Impl"


    // $ANTLR start "rule__PrimaryBool__Group_3__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4047:1: rule__PrimaryBool__Group_3__1 : rule__PrimaryBool__Group_3__1__Impl rule__PrimaryBool__Group_3__2 ;
    public final void rule__PrimaryBool__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4051:1: ( rule__PrimaryBool__Group_3__1__Impl rule__PrimaryBool__Group_3__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4052:2: rule__PrimaryBool__Group_3__1__Impl rule__PrimaryBool__Group_3__2
            {
            pushFollow(FOLLOW_rule__PrimaryBool__Group_3__1__Impl_in_rule__PrimaryBool__Group_3__18163);
            rule__PrimaryBool__Group_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__PrimaryBool__Group_3__2_in_rule__PrimaryBool__Group_3__18166);
            rule__PrimaryBool__Group_3__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryBool__Group_3__1"


    // $ANTLR start "rule__PrimaryBool__Group_3__1__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4059:1: rule__PrimaryBool__Group_3__1__Impl : ( ruleOr ) ;
    public final void rule__PrimaryBool__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4063:1: ( ( ruleOr ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4064:1: ( ruleOr )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4064:1: ( ruleOr )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4065:1: ruleOr
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryBoolAccess().getOrParserRuleCall_3_1()); 
            }
            pushFollow(FOLLOW_ruleOr_in_rule__PrimaryBool__Group_3__1__Impl8193);
            ruleOr();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryBoolAccess().getOrParserRuleCall_3_1()); 
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
    // $ANTLR end "rule__PrimaryBool__Group_3__1__Impl"


    // $ANTLR start "rule__PrimaryBool__Group_3__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4076:1: rule__PrimaryBool__Group_3__2 : rule__PrimaryBool__Group_3__2__Impl ;
    public final void rule__PrimaryBool__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4080:1: ( rule__PrimaryBool__Group_3__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4081:2: rule__PrimaryBool__Group_3__2__Impl
            {
            pushFollow(FOLLOW_rule__PrimaryBool__Group_3__2__Impl_in_rule__PrimaryBool__Group_3__28222);
            rule__PrimaryBool__Group_3__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryBool__Group_3__2"


    // $ANTLR start "rule__PrimaryBool__Group_3__2__Impl"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4087:1: rule__PrimaryBool__Group_3__2__Impl : ( ')' ) ;
    public final void rule__PrimaryBool__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4091:1: ( ( ')' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4092:1: ( ')' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4092:1: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4093:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPrimaryBoolAccess().getRightParenthesisKeyword_3_2()); 
            }
            match(input,33,FOLLOW_33_in_rule__PrimaryBool__Group_3__2__Impl8250); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPrimaryBoolAccess().getRightParenthesisKeyword_3_2()); 
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
    // $ANTLR end "rule__PrimaryBool__Group_3__2__Impl"


    // $ANTLR start "rule__Comparison__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4112:1: rule__Comparison__Group__0 : rule__Comparison__Group__0__Impl rule__Comparison__Group__1 ;
    public final void rule__Comparison__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4116:1: ( rule__Comparison__Group__0__Impl rule__Comparison__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4117:2: rule__Comparison__Group__0__Impl rule__Comparison__Group__1
            {
            pushFollow(FOLLOW_rule__Comparison__Group__0__Impl_in_rule__Comparison__Group__08287);
            rule__Comparison__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Comparison__Group__1_in_rule__Comparison__Group__08290);
            rule__Comparison__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4124:1: rule__Comparison__Group__0__Impl : ( ( rule__Comparison__LeftAssignment_0 ) ) ;
    public final void rule__Comparison__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4128:1: ( ( ( rule__Comparison__LeftAssignment_0 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4129:1: ( ( rule__Comparison__LeftAssignment_0 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4129:1: ( ( rule__Comparison__LeftAssignment_0 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4130:1: ( rule__Comparison__LeftAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getComparisonAccess().getLeftAssignment_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4131:1: ( rule__Comparison__LeftAssignment_0 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4131:2: rule__Comparison__LeftAssignment_0
            {
            pushFollow(FOLLOW_rule__Comparison__LeftAssignment_0_in_rule__Comparison__Group__0__Impl8317);
            rule__Comparison__LeftAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getComparisonAccess().getLeftAssignment_0()); 
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
    // $ANTLR end "rule__Comparison__Group__0__Impl"


    // $ANTLR start "rule__Comparison__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4141:1: rule__Comparison__Group__1 : rule__Comparison__Group__1__Impl rule__Comparison__Group__2 ;
    public final void rule__Comparison__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4145:1: ( rule__Comparison__Group__1__Impl rule__Comparison__Group__2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4146:2: rule__Comparison__Group__1__Impl rule__Comparison__Group__2
            {
            pushFollow(FOLLOW_rule__Comparison__Group__1__Impl_in_rule__Comparison__Group__18347);
            rule__Comparison__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Comparison__Group__2_in_rule__Comparison__Group__18350);
            rule__Comparison__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4153:1: rule__Comparison__Group__1__Impl : ( ( rule__Comparison__OperatorAssignment_1 ) ) ;
    public final void rule__Comparison__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4157:1: ( ( ( rule__Comparison__OperatorAssignment_1 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4158:1: ( ( rule__Comparison__OperatorAssignment_1 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4158:1: ( ( rule__Comparison__OperatorAssignment_1 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4159:1: ( rule__Comparison__OperatorAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getComparisonAccess().getOperatorAssignment_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4160:1: ( rule__Comparison__OperatorAssignment_1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4160:2: rule__Comparison__OperatorAssignment_1
            {
            pushFollow(FOLLOW_rule__Comparison__OperatorAssignment_1_in_rule__Comparison__Group__1__Impl8377);
            rule__Comparison__OperatorAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getComparisonAccess().getOperatorAssignment_1()); 
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
    // $ANTLR end "rule__Comparison__Group__1__Impl"


    // $ANTLR start "rule__Comparison__Group__2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4170:1: rule__Comparison__Group__2 : rule__Comparison__Group__2__Impl ;
    public final void rule__Comparison__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4174:1: ( rule__Comparison__Group__2__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4175:2: rule__Comparison__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Comparison__Group__2__Impl_in_rule__Comparison__Group__28407);
            rule__Comparison__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4181:1: rule__Comparison__Group__2__Impl : ( ( rule__Comparison__RightAssignment_2 ) ) ;
    public final void rule__Comparison__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4185:1: ( ( ( rule__Comparison__RightAssignment_2 ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4186:1: ( ( rule__Comparison__RightAssignment_2 ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4186:1: ( ( rule__Comparison__RightAssignment_2 ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4187:1: ( rule__Comparison__RightAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getComparisonAccess().getRightAssignment_2()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4188:1: ( rule__Comparison__RightAssignment_2 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4188:2: rule__Comparison__RightAssignment_2
            {
            pushFollow(FOLLOW_rule__Comparison__RightAssignment_2_in_rule__Comparison__Group__2__Impl8434);
            rule__Comparison__RightAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getComparisonAccess().getRightAssignment_2()); 
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
    // $ANTLR end "rule__Comparison__Group__2__Impl"


    // $ANTLR start "rule__QualifiedName__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4204:1: rule__QualifiedName__Group__0 : rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1 ;
    public final void rule__QualifiedName__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4208:1: ( rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4209:2: rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group__0__Impl_in_rule__QualifiedName__Group__08470);
            rule__QualifiedName__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__QualifiedName__Group__1_in_rule__QualifiedName__Group__08473);
            rule__QualifiedName__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4216:1: rule__QualifiedName__Group__0__Impl : ( RULE_ID ) ;
    public final void rule__QualifiedName__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4220:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4221:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4221:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4222:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__QualifiedName__Group__0__Impl8500); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
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
    // $ANTLR end "rule__QualifiedName__Group__0__Impl"


    // $ANTLR start "rule__QualifiedName__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4233:1: rule__QualifiedName__Group__1 : rule__QualifiedName__Group__1__Impl ;
    public final void rule__QualifiedName__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4237:1: ( rule__QualifiedName__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4238:2: rule__QualifiedName__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group__1__Impl_in_rule__QualifiedName__Group__18529);
            rule__QualifiedName__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4244:1: rule__QualifiedName__Group__1__Impl : ( ( rule__QualifiedName__Group_1__0 )* ) ;
    public final void rule__QualifiedName__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4248:1: ( ( ( rule__QualifiedName__Group_1__0 )* ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4249:1: ( ( rule__QualifiedName__Group_1__0 )* )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4249:1: ( ( rule__QualifiedName__Group_1__0 )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4250:1: ( rule__QualifiedName__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameAccess().getGroup_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4251:1: ( rule__QualifiedName__Group_1__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==44) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4251:2: rule__QualifiedName__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__QualifiedName__Group_1__0_in_rule__QualifiedName__Group__1__Impl8556);
            	    rule__QualifiedName__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameAccess().getGroup_1()); 
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
    // $ANTLR end "rule__QualifiedName__Group__1__Impl"


    // $ANTLR start "rule__QualifiedName__Group_1__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4265:1: rule__QualifiedName__Group_1__0 : rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1 ;
    public final void rule__QualifiedName__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4269:1: ( rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4270:2: rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group_1__0__Impl_in_rule__QualifiedName__Group_1__08591);
            rule__QualifiedName__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__QualifiedName__Group_1__1_in_rule__QualifiedName__Group_1__08594);
            rule__QualifiedName__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4277:1: rule__QualifiedName__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifiedName__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4281:1: ( ( '.' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4282:1: ( '.' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4282:1: ( '.' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4283:1: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
            }
            match(input,44,FOLLOW_44_in_rule__QualifiedName__Group_1__0__Impl8622); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
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
    // $ANTLR end "rule__QualifiedName__Group_1__0__Impl"


    // $ANTLR start "rule__QualifiedName__Group_1__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4296:1: rule__QualifiedName__Group_1__1 : rule__QualifiedName__Group_1__1__Impl ;
    public final void rule__QualifiedName__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4300:1: ( rule__QualifiedName__Group_1__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4301:2: rule__QualifiedName__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__QualifiedName__Group_1__1__Impl_in_rule__QualifiedName__Group_1__18653);
            rule__QualifiedName__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4307:1: rule__QualifiedName__Group_1__1__Impl : ( RULE_ID ) ;
    public final void rule__QualifiedName__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4311:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4312:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4312:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4313:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__QualifiedName__Group_1__1__Impl8680); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
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
    // $ANTLR end "rule__QualifiedName__Group_1__1__Impl"


    // $ANTLR start "rule__QualifiedNameWithWildCard__Group__0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4328:1: rule__QualifiedNameWithWildCard__Group__0 : rule__QualifiedNameWithWildCard__Group__0__Impl rule__QualifiedNameWithWildCard__Group__1 ;
    public final void rule__QualifiedNameWithWildCard__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4332:1: ( rule__QualifiedNameWithWildCard__Group__0__Impl rule__QualifiedNameWithWildCard__Group__1 )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4333:2: rule__QualifiedNameWithWildCard__Group__0__Impl rule__QualifiedNameWithWildCard__Group__1
            {
            pushFollow(FOLLOW_rule__QualifiedNameWithWildCard__Group__0__Impl_in_rule__QualifiedNameWithWildCard__Group__08713);
            rule__QualifiedNameWithWildCard__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__QualifiedNameWithWildCard__Group__1_in_rule__QualifiedNameWithWildCard__Group__08716);
            rule__QualifiedNameWithWildCard__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4340:1: rule__QualifiedNameWithWildCard__Group__0__Impl : ( ruleQualifiedName ) ;
    public final void rule__QualifiedNameWithWildCard__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4344:1: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4345:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4345:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4346:1: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameWithWildCardAccess().getQualifiedNameParserRuleCall_0()); 
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_rule__QualifiedNameWithWildCard__Group__0__Impl8743);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameWithWildCardAccess().getQualifiedNameParserRuleCall_0()); 
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
    // $ANTLR end "rule__QualifiedNameWithWildCard__Group__0__Impl"


    // $ANTLR start "rule__QualifiedNameWithWildCard__Group__1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4357:1: rule__QualifiedNameWithWildCard__Group__1 : rule__QualifiedNameWithWildCard__Group__1__Impl ;
    public final void rule__QualifiedNameWithWildCard__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4361:1: ( rule__QualifiedNameWithWildCard__Group__1__Impl )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4362:2: rule__QualifiedNameWithWildCard__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__QualifiedNameWithWildCard__Group__1__Impl_in_rule__QualifiedNameWithWildCard__Group__18772);
            rule__QualifiedNameWithWildCard__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
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
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4368:1: rule__QualifiedNameWithWildCard__Group__1__Impl : ( ( '.*' )? ) ;
    public final void rule__QualifiedNameWithWildCard__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4372:1: ( ( ( '.*' )? ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4373:1: ( ( '.*' )? )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4373:1: ( ( '.*' )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4374:1: ( '.*' )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getQualifiedNameWithWildCardAccess().getFullStopAsteriskKeyword_1()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4375:1: ( '.*' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==45) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4376:2: '.*'
                    {
                    match(input,45,FOLLOW_45_in_rule__QualifiedNameWithWildCard__Group__1__Impl8801); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getQualifiedNameWithWildCardAccess().getFullStopAsteriskKeyword_1()); 
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
    // $ANTLR end "rule__QualifiedNameWithWildCard__Group__1__Impl"


    // $ANTLR start "rule__System__NameAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4393:1: rule__System__NameAssignment_1 : ( ruleQualifiedName ) ;
    public final void rule__System__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4397:1: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4398:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4398:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4399:1: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getNameQualifiedNameParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_rule__System__NameAssignment_18844);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getNameQualifiedNameParserRuleCall_1_0()); 
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
    // $ANTLR end "rule__System__NameAssignment_1"


    // $ANTLR start "rule__System__VariablesAssignment_3_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4408:1: rule__System__VariablesAssignment_3_0 : ( ruleVariableDeclaration ) ;
    public final void rule__System__VariablesAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4412:1: ( ( ruleVariableDeclaration ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4413:1: ( ruleVariableDeclaration )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4413:1: ( ruleVariableDeclaration )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4414:1: ruleVariableDeclaration
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getVariablesVariableDeclarationParserRuleCall_3_0_0()); 
            }
            pushFollow(FOLLOW_ruleVariableDeclaration_in_rule__System__VariablesAssignment_3_08875);
            ruleVariableDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getVariablesVariableDeclarationParserRuleCall_3_0_0()); 
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
    // $ANTLR end "rule__System__VariablesAssignment_3_0"


    // $ANTLR start "rule__System__ArraysAssignment_3_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4423:1: rule__System__ArraysAssignment_3_1 : ( ruleArrayDeclaration ) ;
    public final void rule__System__ArraysAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4427:1: ( ( ruleArrayDeclaration ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4428:1: ( ruleArrayDeclaration )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4428:1: ( ruleArrayDeclaration )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4429:1: ruleArrayDeclaration
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getArraysArrayDeclarationParserRuleCall_3_1_0()); 
            }
            pushFollow(FOLLOW_ruleArrayDeclaration_in_rule__System__ArraysAssignment_3_18906);
            ruleArrayDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getArraysArrayDeclarationParserRuleCall_3_1_0()); 
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
    // $ANTLR end "rule__System__ArraysAssignment_3_1"


    // $ANTLR start "rule__System__ListsAssignment_3_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4438:1: rule__System__ListsAssignment_3_2 : ( ruleListDeclaration ) ;
    public final void rule__System__ListsAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4442:1: ( ( ruleListDeclaration ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4443:1: ( ruleListDeclaration )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4443:1: ( ruleListDeclaration )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4444:1: ruleListDeclaration
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getListsListDeclarationParserRuleCall_3_2_0()); 
            }
            pushFollow(FOLLOW_ruleListDeclaration_in_rule__System__ListsAssignment_3_28937);
            ruleListDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getListsListDeclarationParserRuleCall_3_2_0()); 
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
    // $ANTLR end "rule__System__ListsAssignment_3_2"


    // $ANTLR start "rule__System__TransitionsAssignment_4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4453:1: rule__System__TransitionsAssignment_4 : ( ruleTransition ) ;
    public final void rule__System__TransitionsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4457:1: ( ( ruleTransition ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4458:1: ( ruleTransition )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4458:1: ( ruleTransition )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4459:1: ruleTransition
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getSystemAccess().getTransitionsTransitionParserRuleCall_4_0()); 
            }
            pushFollow(FOLLOW_ruleTransition_in_rule__System__TransitionsAssignment_48968);
            ruleTransition();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getSystemAccess().getTransitionsTransitionParserRuleCall_4_0()); 
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
    // $ANTLR end "rule__System__TransitionsAssignment_4"


    // $ANTLR start "rule__VariableDeclaration__NameAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4468:1: rule__VariableDeclaration__NameAssignment_1 : ( ruleQualifiedName ) ;
    public final void rule__VariableDeclaration__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4472:1: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4473:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4473:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4474:1: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableDeclarationAccess().getNameQualifiedNameParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_rule__VariableDeclaration__NameAssignment_18999);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableDeclarationAccess().getNameQualifiedNameParserRuleCall_1_0()); 
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
    // $ANTLR end "rule__VariableDeclaration__NameAssignment_1"


    // $ANTLR start "rule__VariableDeclaration__ValueAssignment_3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4483:1: rule__VariableDeclaration__ValueAssignment_3 : ( RULE_INT ) ;
    public final void rule__VariableDeclaration__ValueAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4487:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4488:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4488:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4489:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableDeclarationAccess().getValueINTTerminalRuleCall_3_0()); 
            }
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__VariableDeclaration__ValueAssignment_39030); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableDeclarationAccess().getValueINTTerminalRuleCall_3_0()); 
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
    // $ANTLR end "rule__VariableDeclaration__ValueAssignment_3"


    // $ANTLR start "rule__ArrayDeclaration__SizeAssignment_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4498:1: rule__ArrayDeclaration__SizeAssignment_2 : ( RULE_INT ) ;
    public final void rule__ArrayDeclaration__SizeAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4502:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4503:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4503:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4504:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getSizeINTTerminalRuleCall_2_0()); 
            }
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__ArrayDeclaration__SizeAssignment_29061); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getSizeINTTerminalRuleCall_2_0()); 
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
    // $ANTLR end "rule__ArrayDeclaration__SizeAssignment_2"


    // $ANTLR start "rule__ArrayDeclaration__NameAssignment_4"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4513:1: rule__ArrayDeclaration__NameAssignment_4 : ( ruleQualifiedName ) ;
    public final void rule__ArrayDeclaration__NameAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4517:1: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4518:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4518:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4519:1: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getNameQualifiedNameParserRuleCall_4_0()); 
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_rule__ArrayDeclaration__NameAssignment_49092);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getNameQualifiedNameParserRuleCall_4_0()); 
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
    // $ANTLR end "rule__ArrayDeclaration__NameAssignment_4"


    // $ANTLR start "rule__ArrayDeclaration__ValuesAssignment_7"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4528:1: rule__ArrayDeclaration__ValuesAssignment_7 : ( ruleInitValues ) ;
    public final void rule__ArrayDeclaration__ValuesAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4532:1: ( ( ruleInitValues ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4533:1: ( ruleInitValues )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4533:1: ( ruleInitValues )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4534:1: ruleInitValues
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayDeclarationAccess().getValuesInitValuesParserRuleCall_7_0()); 
            }
            pushFollow(FOLLOW_ruleInitValues_in_rule__ArrayDeclaration__ValuesAssignment_79123);
            ruleInitValues();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayDeclarationAccess().getValuesInitValuesParserRuleCall_7_0()); 
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
    // $ANTLR end "rule__ArrayDeclaration__ValuesAssignment_7"


    // $ANTLR start "rule__ListDeclaration__NameAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4543:1: rule__ListDeclaration__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__ListDeclaration__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4547:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4548:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4548:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4549:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getNameIDTerminalRuleCall_1_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ListDeclaration__NameAssignment_19154); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getNameIDTerminalRuleCall_1_0()); 
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
    // $ANTLR end "rule__ListDeclaration__NameAssignment_1"


    // $ANTLR start "rule__ListDeclaration__ValuesAssignment_2_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4558:1: rule__ListDeclaration__ValuesAssignment_2_2 : ( ruleInitValues ) ;
    public final void rule__ListDeclaration__ValuesAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4562:1: ( ( ruleInitValues ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4563:1: ( ruleInitValues )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4563:1: ( ruleInitValues )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4564:1: ruleInitValues
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getListDeclarationAccess().getValuesInitValuesParserRuleCall_2_2_0()); 
            }
            pushFollow(FOLLOW_ruleInitValues_in_rule__ListDeclaration__ValuesAssignment_2_29185);
            ruleInitValues();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getListDeclarationAccess().getValuesInitValuesParserRuleCall_2_2_0()); 
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
    // $ANTLR end "rule__ListDeclaration__ValuesAssignment_2_2"


    // $ANTLR start "rule__InitValues__ValuesAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4573:1: rule__InitValues__ValuesAssignment_0 : ( RULE_INT ) ;
    public final void rule__InitValues__ValuesAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4577:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4578:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4578:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4579:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInitValuesAccess().getValuesINTTerminalRuleCall_0_0()); 
            }
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__InitValues__ValuesAssignment_09216); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getInitValuesAccess().getValuesINTTerminalRuleCall_0_0()); 
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
    // $ANTLR end "rule__InitValues__ValuesAssignment_0"


    // $ANTLR start "rule__InitValues__ValuesAssignment_1_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4588:1: rule__InitValues__ValuesAssignment_1_1 : ( RULE_INT ) ;
    public final void rule__InitValues__ValuesAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4592:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4593:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4593:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4594:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInitValuesAccess().getValuesINTTerminalRuleCall_1_1_0()); 
            }
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__InitValues__ValuesAssignment_1_19247); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getInitValuesAccess().getValuesINTTerminalRuleCall_1_1_0()); 
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
    // $ANTLR end "rule__InitValues__ValuesAssignment_1_1"


    // $ANTLR start "rule__Transition__NameAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4603:1: rule__Transition__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Transition__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4607:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4608:1: ( RULE_ID )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4608:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4609:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Transition__NameAssignment_19278); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0()); 
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
    // $ANTLR end "rule__Transition__NameAssignment_1"


    // $ANTLR start "rule__Transition__GuardAssignment_3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4618:1: rule__Transition__GuardAssignment_3 : ( ruleOr ) ;
    public final void rule__Transition__GuardAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4622:1: ( ( ruleOr ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4623:1: ( ruleOr )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4623:1: ( ruleOr )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4624:1: ruleOr
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getGuardOrParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_ruleOr_in_rule__Transition__GuardAssignment_39309);
            ruleOr();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getGuardOrParserRuleCall_3_0()); 
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
    // $ANTLR end "rule__Transition__GuardAssignment_3"


    // $ANTLR start "rule__Transition__LabelAssignment_5_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4633:1: rule__Transition__LabelAssignment_5_1 : ( RULE_STRING ) ;
    public final void rule__Transition__LabelAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4637:1: ( ( RULE_STRING ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4638:1: ( RULE_STRING )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4638:1: ( RULE_STRING )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4639:1: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getLabelSTRINGTerminalRuleCall_5_1_0()); 
            }
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Transition__LabelAssignment_5_19340); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getLabelSTRINGTerminalRuleCall_5_1_0()); 
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
    // $ANTLR end "rule__Transition__LabelAssignment_5_1"


    // $ANTLR start "rule__Transition__ActionsAssignment_7"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4648:1: rule__Transition__ActionsAssignment_7 : ( ruleActions ) ;
    public final void rule__Transition__ActionsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4652:1: ( ( ruleActions ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4653:1: ( ruleActions )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4653:1: ( ruleActions )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4654:1: ruleActions
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTransitionAccess().getActionsActionsParserRuleCall_7_0()); 
            }
            pushFollow(FOLLOW_ruleActions_in_rule__Transition__ActionsAssignment_79371);
            ruleActions();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTransitionAccess().getActionsActionsParserRuleCall_7_0()); 
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
    // $ANTLR end "rule__Transition__ActionsAssignment_7"


    // $ANTLR start "rule__Push__ListeAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4663:1: rule__Push__ListeAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__Push__ListeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4667:1: ( ( ( RULE_ID ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4668:1: ( ( RULE_ID ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4668:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4669:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getListeListCrossReference_0_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4670:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4671:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getListeListIDTerminalRuleCall_0_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Push__ListeAssignment_09406); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getListeListIDTerminalRuleCall_0_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getListeListCrossReference_0_0()); 
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
    // $ANTLR end "rule__Push__ListeAssignment_0"


    // $ANTLR start "rule__Push__ValueAssignment_3"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4682:1: rule__Push__ValueAssignment_3 : ( ruleBinaryIntExpression ) ;
    public final void rule__Push__ValueAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4686:1: ( ( ruleBinaryIntExpression ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4687:1: ( ruleBinaryIntExpression )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4687:1: ( ruleBinaryIntExpression )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4688:1: ruleBinaryIntExpression
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPushAccess().getValueBinaryIntExpressionParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_ruleBinaryIntExpression_in_rule__Push__ValueAssignment_39441);
            ruleBinaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPushAccess().getValueBinaryIntExpressionParserRuleCall_3_0()); 
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
    // $ANTLR end "rule__Push__ValueAssignment_3"


    // $ANTLR start "rule__Pop__ListeAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4697:1: rule__Pop__ListeAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__Pop__ListeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4701:1: ( ( ( RULE_ID ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4702:1: ( ( RULE_ID ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4702:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4703:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPopAccess().getListeListCrossReference_0_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4704:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4705:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPopAccess().getListeListIDTerminalRuleCall_0_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Pop__ListeAssignment_09476); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPopAccess().getListeListIDTerminalRuleCall_0_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPopAccess().getListeListCrossReference_0_0()); 
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
    // $ANTLR end "rule__Pop__ListeAssignment_0"


    // $ANTLR start "rule__Peek__ListeAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4716:1: rule__Peek__ListeAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__Peek__ListeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4720:1: ( ( ( RULE_ID ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4721:1: ( ( RULE_ID ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4721:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4722:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPeekAccess().getListeListCrossReference_0_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4723:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4724:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPeekAccess().getListeListIDTerminalRuleCall_0_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Peek__ListeAssignment_09515); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPeekAccess().getListeListIDTerminalRuleCall_0_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPeekAccess().getListeListCrossReference_0_0()); 
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
    // $ANTLR end "rule__Peek__ListeAssignment_0"


    // $ANTLR start "rule__Assignment__LeftAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4735:1: rule__Assignment__LeftAssignment_0 : ( ruleVarAccess ) ;
    public final void rule__Assignment__LeftAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4739:1: ( ( ruleVarAccess ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4740:1: ( ruleVarAccess )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4740:1: ( ruleVarAccess )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4741:1: ruleVarAccess
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getLeftVarAccessParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_ruleVarAccess_in_rule__Assignment__LeftAssignment_09550);
            ruleVarAccess();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getLeftVarAccessParserRuleCall_0_0()); 
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
    // $ANTLR end "rule__Assignment__LeftAssignment_0"


    // $ANTLR start "rule__Assignment__RightAssignment_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4750:1: rule__Assignment__RightAssignment_2 : ( ruleBinaryIntExpression ) ;
    public final void rule__Assignment__RightAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4754:1: ( ( ruleBinaryIntExpression ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4755:1: ( ruleBinaryIntExpression )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4755:1: ( ruleBinaryIntExpression )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4756:1: ruleBinaryIntExpression
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getRightBinaryIntExpressionParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_ruleBinaryIntExpression_in_rule__Assignment__RightAssignment_29581);
            ruleBinaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getRightBinaryIntExpressionParserRuleCall_2_0()); 
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
    // $ANTLR end "rule__Assignment__RightAssignment_2"


    // $ANTLR start "rule__VariableRef__VarAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4765:1: rule__VariableRef__VarAssignment : ( ( RULE_ID ) ) ;
    public final void rule__VariableRef__VarAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4769:1: ( ( ( RULE_ID ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4770:1: ( ( RULE_ID ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4770:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4771:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableRefAccess().getVarVariableCrossReference_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4772:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4773:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariableRefAccess().getVarVariableIDTerminalRuleCall_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__VariableRef__VarAssignment9616); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableRefAccess().getVarVariableIDTerminalRuleCall_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariableRefAccess().getVarVariableCrossReference_0()); 
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
    // $ANTLR end "rule__VariableRef__VarAssignment"


    // $ANTLR start "rule__ArrayVarAccess__PrefixAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4784:1: rule__ArrayVarAccess__PrefixAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__ArrayVarAccess__PrefixAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4788:1: ( ( ( RULE_ID ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4789:1: ( ( RULE_ID ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4789:1: ( ( RULE_ID ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4790:1: ( RULE_ID )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayVarAccessAccess().getPrefixArrayPrefixCrossReference_0_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4791:1: ( RULE_ID )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4792:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayVarAccessAccess().getPrefixArrayPrefixIDTerminalRuleCall_0_0_1()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ArrayVarAccess__PrefixAssignment_09655); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayVarAccessAccess().getPrefixArrayPrefixIDTerminalRuleCall_0_0_1()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayVarAccessAccess().getPrefixArrayPrefixCrossReference_0_0()); 
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
    // $ANTLR end "rule__ArrayVarAccess__PrefixAssignment_0"


    // $ANTLR start "rule__ArrayVarAccess__IndexAssignment_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4803:1: rule__ArrayVarAccess__IndexAssignment_2 : ( ruleBinaryIntExpression ) ;
    public final void rule__ArrayVarAccess__IndexAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4807:1: ( ( ruleBinaryIntExpression ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4808:1: ( ruleBinaryIntExpression )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4808:1: ( ruleBinaryIntExpression )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4809:1: ruleBinaryIntExpression
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArrayVarAccessAccess().getIndexBinaryIntExpressionParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_ruleBinaryIntExpression_in_rule__ArrayVarAccess__IndexAssignment_29690);
            ruleBinaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArrayVarAccessAccess().getIndexBinaryIntExpressionParserRuleCall_2_0()); 
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
    // $ANTLR end "rule__ArrayVarAccess__IndexAssignment_2"


    // $ANTLR start "rule__BinaryIntExpression__OpAssignment_1_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4818:1: rule__BinaryIntExpression__OpAssignment_1_1 : ( ruleBinaryArithmeticOperations ) ;
    public final void rule__BinaryIntExpression__OpAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4822:1: ( ( ruleBinaryArithmeticOperations ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4823:1: ( ruleBinaryArithmeticOperations )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4823:1: ( ruleBinaryArithmeticOperations )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4824:1: ruleBinaryArithmeticOperations
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryIntExpressionAccess().getOpBinaryArithmeticOperationsEnumRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_ruleBinaryArithmeticOperations_in_rule__BinaryIntExpression__OpAssignment_1_19721);
            ruleBinaryArithmeticOperations();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryIntExpressionAccess().getOpBinaryArithmeticOperationsEnumRuleCall_1_1_0()); 
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
    // $ANTLR end "rule__BinaryIntExpression__OpAssignment_1_1"


    // $ANTLR start "rule__BinaryIntExpression__RightAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4833:1: rule__BinaryIntExpression__RightAssignment_1_2 : ( ruleUnaryIntExpression ) ;
    public final void rule__BinaryIntExpression__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4837:1: ( ( ruleUnaryIntExpression ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4838:1: ( ruleUnaryIntExpression )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4838:1: ( ruleUnaryIntExpression )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4839:1: ruleUnaryIntExpression
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBinaryIntExpressionAccess().getRightUnaryIntExpressionParserRuleCall_1_2_0()); 
            }
            pushFollow(FOLLOW_ruleUnaryIntExpression_in_rule__BinaryIntExpression__RightAssignment_1_29752);
            ruleUnaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBinaryIntExpressionAccess().getRightUnaryIntExpressionParserRuleCall_1_2_0()); 
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
    // $ANTLR end "rule__BinaryIntExpression__RightAssignment_1_2"


    // $ANTLR start "rule__Constante__ValAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4848:1: rule__Constante__ValAssignment : ( RULE_INT ) ;
    public final void rule__Constante__ValAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4852:1: ( ( RULE_INT ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4853:1: ( RULE_INT )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4853:1: ( RULE_INT )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4854:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getConstanteAccess().getValINTTerminalRuleCall_0()); 
            }
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__Constante__ValAssignment9783); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getConstanteAccess().getValINTTerminalRuleCall_0()); 
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
    // $ANTLR end "rule__Constante__ValAssignment"


    // $ANTLR start "rule__Or__RightAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4863:1: rule__Or__RightAssignment_1_2 : ( ruleAnd ) ;
    public final void rule__Or__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4867:1: ( ( ruleAnd ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4868:1: ( ruleAnd )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4868:1: ( ruleAnd )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4869:1: ruleAnd
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0()); 
            }
            pushFollow(FOLLOW_ruleAnd_in_rule__Or__RightAssignment_1_29814);
            ruleAnd();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0()); 
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
    // $ANTLR end "rule__Or__RightAssignment_1_2"


    // $ANTLR start "rule__And__RightAssignment_1_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4878:1: rule__And__RightAssignment_1_2 : ( ruleNot ) ;
    public final void rule__And__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4882:1: ( ( ruleNot ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4883:1: ( ruleNot )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4883:1: ( ruleNot )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4884:1: ruleNot
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAndAccess().getRightNotParserRuleCall_1_2_0()); 
            }
            pushFollow(FOLLOW_ruleNot_in_rule__And__RightAssignment_1_29845);
            ruleNot();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAndAccess().getRightNotParserRuleCall_1_2_0()); 
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
    // $ANTLR end "rule__And__RightAssignment_1_2"


    // $ANTLR start "rule__Comparison__LeftAssignment_0"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4893:1: rule__Comparison__LeftAssignment_0 : ( ruleBinaryIntExpression ) ;
    public final void rule__Comparison__LeftAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4897:1: ( ( ruleBinaryIntExpression ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4898:1: ( ruleBinaryIntExpression )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4898:1: ( ruleBinaryIntExpression )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4899:1: ruleBinaryIntExpression
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getComparisonAccess().getLeftBinaryIntExpressionParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_ruleBinaryIntExpression_in_rule__Comparison__LeftAssignment_09876);
            ruleBinaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getComparisonAccess().getLeftBinaryIntExpressionParserRuleCall_0_0()); 
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
    // $ANTLR end "rule__Comparison__LeftAssignment_0"


    // $ANTLR start "rule__Comparison__OperatorAssignment_1"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4908:1: rule__Comparison__OperatorAssignment_1 : ( ruleComparisonOperators ) ;
    public final void rule__Comparison__OperatorAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4912:1: ( ( ruleComparisonOperators ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4913:1: ( ruleComparisonOperators )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4913:1: ( ruleComparisonOperators )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4914:1: ruleComparisonOperators
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getComparisonAccess().getOperatorComparisonOperatorsEnumRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruleComparisonOperators_in_rule__Comparison__OperatorAssignment_19907);
            ruleComparisonOperators();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getComparisonAccess().getOperatorComparisonOperatorsEnumRuleCall_1_0()); 
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
    // $ANTLR end "rule__Comparison__OperatorAssignment_1"


    // $ANTLR start "rule__Comparison__RightAssignment_2"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4923:1: rule__Comparison__RightAssignment_2 : ( ruleBinaryIntExpression ) ;
    public final void rule__Comparison__RightAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4927:1: ( ( ruleBinaryIntExpression ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4928:1: ( ruleBinaryIntExpression )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4928:1: ( ruleBinaryIntExpression )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4929:1: ruleBinaryIntExpression
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getComparisonAccess().getRightBinaryIntExpressionParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_ruleBinaryIntExpression_in_rule__Comparison__RightAssignment_29938);
            ruleBinaryIntExpression();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getComparisonAccess().getRightBinaryIntExpressionParserRuleCall_2_0()); 
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
    // $ANTLR end "rule__Comparison__RightAssignment_2"


    // $ANTLR start "rule__True__ValAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4938:1: rule__True__ValAssignment : ( ( 'True' ) ) ;
    public final void rule__True__ValAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4942:1: ( ( ( 'True' ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4943:1: ( ( 'True' ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4943:1: ( ( 'True' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4944:1: ( 'True' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTrueAccess().getValTrueKeyword_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4945:1: ( 'True' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4946:1: 'True'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTrueAccess().getValTrueKeyword_0()); 
            }
            match(input,46,FOLLOW_46_in_rule__True__ValAssignment9974); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTrueAccess().getValTrueKeyword_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTrueAccess().getValTrueKeyword_0()); 
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
    // $ANTLR end "rule__True__ValAssignment"


    // $ANTLR start "rule__False__ValAssignment"
    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4961:1: rule__False__ValAssignment : ( ( 'False' ) ) ;
    public final void rule__False__ValAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4965:1: ( ( ( 'False' ) ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4966:1: ( ( 'False' ) )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4966:1: ( ( 'False' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4967:1: ( 'False' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFalseAccess().getValFalseKeyword_0()); 
            }
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4968:1: ( 'False' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4969:1: 'False'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFalseAccess().getValFalseKeyword_0()); 
            }
            match(input,47,FOLLOW_47_in_rule__False__ValAssignment10018); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFalseAccess().getValFalseKeyword_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getFalseAccess().getValFalseKeyword_0()); 
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
    // $ANTLR end "rule__False__ValAssignment"

    // $ANTLR start synpred11_InternalGal
    public final void synpred11_InternalGal_fragment() throws RecognitionException {   
        // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:986:6: ( ( ( ruleComparison ) ) )
        // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:986:6: ( ( ruleComparison ) )
        {
        // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:986:6: ( ( ruleComparison ) )
        // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:987:1: ( ruleComparison )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getPrimaryBoolAccess().getComparisonParserRuleCall_2()); 
        }
        // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:988:1: ( ruleComparison )
        // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:988:3: ruleComparison
        {
        pushFollow(FOLLOW_ruleComparison_in_synpred11_InternalGal2073);
        ruleComparison();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred11_InternalGal

    // Delegated rules

    public final boolean synpred11_InternalGal() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalGal_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_ruleSystem_in_entryRuleSystem67 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSystem74 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__0_in_ruleSystem100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration127 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableDeclaration134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__0_in_ruleVariableDeclaration160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayDeclaration_in_entryRuleArrayDeclaration187 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArrayDeclaration194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__0_in_ruleArrayDeclaration220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleListDeclaration_in_entryRuleListDeclaration247 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleListDeclaration254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group__0_in_ruleListDeclaration280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInitValues_in_entryRuleInitValues307 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInitValues314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__InitValues__Group__0_in_ruleInitValues340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransition_in_entryRuleTransition367 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransition374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__0_in_ruleTransition400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleActions_in_entryRuleActions427 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleActions434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Actions__Alternatives_in_ruleActions460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePush_in_entryRulePush487 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePush494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Push__Group__0_in_rulePush520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePop_in_entryRulePop547 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePop554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Pop__Group__0_in_rulePop580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePeek_in_entryRulePeek607 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePeek614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Peek__Group__0_in_rulePeek640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_entryRuleAssignment667 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssignment674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__0_in_ruleAssignment700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVarAccess_in_entryRuleVarAccess727 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVarAccess734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VarAccess__Alternatives_in_ruleVarAccess760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_entryRuleVariableRef787 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableRef794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableRef__VarAssignment_in_ruleVariableRef820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayVarAccess_in_entryRuleArrayVarAccess847 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArrayVarAccess854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__Group__0_in_ruleArrayVarAccess880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinaryIntExpression_in_entryRuleBinaryIntExpression907 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBinaryIntExpression914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group__0_in_ruleBinaryIntExpression940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryIntExpression_in_entryRuleUnaryIntExpression967 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnaryIntExpression974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryIntExpression__Group__0_in_ruleUnaryIntExpression1000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_entryRulePrimary1027 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimary1034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Alternatives_in_rulePrimary1060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_entryRuleConstante1087 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstante1094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Constante__ValAssignment_in_ruleConstante1120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_entryRuleOr1147 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOr1154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group__0_in_ruleOr1180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_entryRuleAnd1207 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAnd1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group__0_in_ruleAnd1240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_entryRuleNot1267 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNot1274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Not__Group__0_in_ruleNot1300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool1327 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryBool1334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryBool__Alternatives_in_rulePrimaryBool1360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_entryRuleComparison1387 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComparison1394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__Group__0_in_ruleComparison1420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_entryRuleTrue1447 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTrue1454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__True__ValAssignment_in_ruleTrue1480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_entryRuleFalse1507 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFalse1514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__False__ValAssignment_in_ruleFalse1540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName1567 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedName1574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group__0_in_ruleQualifiedName1600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedNameWithWildCard_in_entryRuleQualifiedNameWithWildCard1627 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedNameWithWildCard1634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedNameWithWildCard__Group__0_in_ruleQualifiedNameWithWildCard1660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__BinaryArithmeticOperations__Alternatives_in_ruleBinaryArithmeticOperations1699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ComparisonOperators__Alternatives_in_ruleComparisonOperators1735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__VariablesAssignment_3_0_in_rule__System__Alternatives_31770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__ArraysAssignment_3_1_in_rule__System__Alternatives_31788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__ListsAssignment_3_2_in_rule__System__Alternatives_31806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_rule__Actions__Alternatives1839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePush_in_rule__Actions__Alternatives1856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePop_in_rule__Actions__Alternatives1873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayVarAccess_in_rule__VarAccess__Alternatives1905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rule__VarAccess__Alternatives1922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePeek_in_rule__Primary__Alternatives1954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVarAccess_in_rule__Primary__Alternatives1971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_rule__Primary__Alternatives1988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_3__0_in_rule__Primary__Alternatives2005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_rule__PrimaryBool__Alternatives2038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_rule__PrimaryBool__Alternatives2055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_rule__PrimaryBool__Alternatives2073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryBool__Group_3__0_in_rule__PrimaryBool__Alternatives2091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__BinaryArithmeticOperations__Alternatives2125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__BinaryArithmeticOperations__Alternatives2146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__BinaryArithmeticOperations__Alternatives2167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__BinaryArithmeticOperations__Alternatives2188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__BinaryArithmeticOperations__Alternatives2209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__BinaryArithmeticOperations__Alternatives2230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__ComparisonOperators__Alternatives2266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__ComparisonOperators__Alternatives2287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__ComparisonOperators__Alternatives2308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__ComparisonOperators__Alternatives2329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__ComparisonOperators__Alternatives2350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__ComparisonOperators__Alternatives2371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__0__Impl_in_rule__System__Group__02404 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__System__Group__1_in_rule__System__Group__02407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__System__Group__0__Impl2435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__1__Impl_in_rule__System__Group__12466 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_rule__System__Group__2_in_rule__System__Group__12469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__NameAssignment_1_in_rule__System__Group__1__Impl2496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__2__Impl_in_rule__System__Group__22526 = new BitSet(new long[]{0x0000001424000000L});
    public static final BitSet FOLLOW_rule__System__Group__3_in_rule__System__Group__22529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__System__Group__2__Impl2557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Group__3__Impl_in_rule__System__Group__32588 = new BitSet(new long[]{0x0000001424000000L});
    public static final BitSet FOLLOW_rule__System__Group__4_in_rule__System__Group__32591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__Alternatives_3_in_rule__System__Group__3__Impl2618 = new BitSet(new long[]{0x0000000424000002L});
    public static final BitSet FOLLOW_rule__System__Group__4__Impl_in_rule__System__Group__42649 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__System__Group__5_in_rule__System__Group__42652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__System__TransitionsAssignment_4_in_rule__System__Group__4__Impl2681 = new BitSet(new long[]{0x0000001424000002L});
    public static final BitSet FOLLOW_rule__System__TransitionsAssignment_4_in_rule__System__Group__4__Impl2693 = new BitSet(new long[]{0x0000001424000002L});
    public static final BitSet FOLLOW_rule__System__Group__5__Impl_in_rule__System__Group__52726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__System__Group__5__Impl2754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__0__Impl_in_rule__VariableDeclaration__Group__02797 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__1_in_rule__VariableDeclaration__Group__02800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__VariableDeclaration__Group__0__Impl2828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__1__Impl_in_rule__VariableDeclaration__Group__12859 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__2_in_rule__VariableDeclaration__Group__12862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__NameAssignment_1_in_rule__VariableDeclaration__Group__1__Impl2889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__2__Impl_in_rule__VariableDeclaration__Group__22919 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__3_in_rule__VariableDeclaration__Group__22922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__VariableDeclaration__Group__2__Impl2950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__3__Impl_in_rule__VariableDeclaration__Group__32981 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__4_in_rule__VariableDeclaration__Group__32984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__ValueAssignment_3_in_rule__VariableDeclaration__Group__3__Impl3011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__VariableDeclaration__Group__4__Impl_in_rule__VariableDeclaration__Group__43041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__VariableDeclaration__Group__4__Impl3069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__0__Impl_in_rule__ArrayDeclaration__Group__03110 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__1_in_rule__ArrayDeclaration__Group__03113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__ArrayDeclaration__Group__0__Impl3141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__1__Impl_in_rule__ArrayDeclaration__Group__13172 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__2_in_rule__ArrayDeclaration__Group__13175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__ArrayDeclaration__Group__1__Impl3203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__2__Impl_in_rule__ArrayDeclaration__Group__23234 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__3_in_rule__ArrayDeclaration__Group__23237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__SizeAssignment_2_in_rule__ArrayDeclaration__Group__2__Impl3264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__3__Impl_in_rule__ArrayDeclaration__Group__33294 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__4_in_rule__ArrayDeclaration__Group__33297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__ArrayDeclaration__Group__3__Impl3325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__4__Impl_in_rule__ArrayDeclaration__Group__43356 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__5_in_rule__ArrayDeclaration__Group__43359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__NameAssignment_4_in_rule__ArrayDeclaration__Group__4__Impl3386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__5__Impl_in_rule__ArrayDeclaration__Group__53416 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__6_in_rule__ArrayDeclaration__Group__53419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__ArrayDeclaration__Group__5__Impl3447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__6__Impl_in_rule__ArrayDeclaration__Group__63478 = new BitSet(new long[]{0x0000000200000020L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__7_in_rule__ArrayDeclaration__Group__63481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__ArrayDeclaration__Group__6__Impl3509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__7__Impl_in_rule__ArrayDeclaration__Group__73540 = new BitSet(new long[]{0x0000000200000020L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__8_in_rule__ArrayDeclaration__Group__73543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__ValuesAssignment_7_in_rule__ArrayDeclaration__Group__7__Impl3570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__8__Impl_in_rule__ArrayDeclaration__Group__83601 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__9_in_rule__ArrayDeclaration__Group__83604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__ArrayDeclaration__Group__8__Impl3632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayDeclaration__Group__9__Impl_in_rule__ArrayDeclaration__Group__93663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__ArrayDeclaration__Group__9__Impl3691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group__0__Impl_in_rule__ListDeclaration__Group__03742 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group__1_in_rule__ListDeclaration__Group__03745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__ListDeclaration__Group__0__Impl3773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group__1__Impl_in_rule__ListDeclaration__Group__13804 = new BitSet(new long[]{0x0000000018000000L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group__2_in_rule__ListDeclaration__Group__13807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__NameAssignment_1_in_rule__ListDeclaration__Group__1__Impl3834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group__2__Impl_in_rule__ListDeclaration__Group__23864 = new BitSet(new long[]{0x0000000018000000L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group__3_in_rule__ListDeclaration__Group__23867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group_2__0_in_rule__ListDeclaration__Group__2__Impl3894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group__3__Impl_in_rule__ListDeclaration__Group__33925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__ListDeclaration__Group__3__Impl3953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group_2__0__Impl_in_rule__ListDeclaration__Group_2__03992 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group_2__1_in_rule__ListDeclaration__Group_2__03995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__ListDeclaration__Group_2__0__Impl4023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group_2__1__Impl_in_rule__ListDeclaration__Group_2__14054 = new BitSet(new long[]{0x0000000200000020L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group_2__2_in_rule__ListDeclaration__Group_2__14057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__ListDeclaration__Group_2__1__Impl4085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group_2__2__Impl_in_rule__ListDeclaration__Group_2__24116 = new BitSet(new long[]{0x0000000200000020L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group_2__3_in_rule__ListDeclaration__Group_2__24119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__ValuesAssignment_2_2_in_rule__ListDeclaration__Group_2__2__Impl4146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ListDeclaration__Group_2__3__Impl_in_rule__ListDeclaration__Group_2__34177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__ListDeclaration__Group_2__3__Impl4205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__InitValues__Group__0__Impl_in_rule__InitValues__Group__04244 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_rule__InitValues__Group__1_in_rule__InitValues__Group__04247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__InitValues__ValuesAssignment_0_in_rule__InitValues__Group__0__Impl4274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__InitValues__Group__1__Impl_in_rule__InitValues__Group__14304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__InitValues__Group_1__0_in_rule__InitValues__Group__1__Impl4331 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_rule__InitValues__Group_1__0__Impl_in_rule__InitValues__Group_1__04366 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__InitValues__Group_1__1_in_rule__InitValues__Group_1__04369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_rule__InitValues__Group_1__0__Impl4397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__InitValues__Group_1__1__Impl_in_rule__InitValues__Group_1__14428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__InitValues__ValuesAssignment_1_1_in_rule__InitValues__Group_1__1__Impl4455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__0__Impl_in_rule__Transition__Group__04489 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Transition__Group__1_in_rule__Transition__Group__04492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_rule__Transition__Group__0__Impl4520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__1__Impl_in_rule__Transition__Group__14551 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__Transition__Group__2_in_rule__Transition__Group__14554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__NameAssignment_1_in_rule__Transition__Group__1__Impl4581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__2__Impl_in_rule__Transition__Group__24611 = new BitSet(new long[]{0x0000C80100001030L});
    public static final BitSet FOLLOW_rule__Transition__Group__3_in_rule__Transition__Group__24614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__Transition__Group__2__Impl4642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__3__Impl_in_rule__Transition__Group__34673 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rule__Transition__Group__4_in_rule__Transition__Group__34676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__GuardAssignment_3_in_rule__Transition__Group__3__Impl4703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__4__Impl_in_rule__Transition__Group__44733 = new BitSet(new long[]{0x0000002001000000L});
    public static final BitSet FOLLOW_rule__Transition__Group__5_in_rule__Transition__Group__44736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__Transition__Group__4__Impl4764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__5__Impl_in_rule__Transition__Group__54795 = new BitSet(new long[]{0x0000002001000000L});
    public static final BitSet FOLLOW_rule__Transition__Group__6_in_rule__Transition__Group__54798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group_5__0_in_rule__Transition__Group__5__Impl4825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__6__Impl_in_rule__Transition__Group__64856 = new BitSet(new long[]{0x0000000002000010L});
    public static final BitSet FOLLOW_rule__Transition__Group__7_in_rule__Transition__Group__64859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__Transition__Group__6__Impl4887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group__7__Impl_in_rule__Transition__Group__74918 = new BitSet(new long[]{0x0000000002000010L});
    public static final BitSet FOLLOW_rule__Transition__Group__8_in_rule__Transition__Group__74921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__ActionsAssignment_7_in_rule__Transition__Group__7__Impl4948 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Transition__Group__8__Impl_in_rule__Transition__Group__84979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__Transition__Group__8__Impl5007 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group_5__0__Impl_in_rule__Transition__Group_5__05056 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_rule__Transition__Group_5__1_in_rule__Transition__Group_5__05059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__Transition__Group_5__0__Impl5087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__Group_5__1__Impl_in_rule__Transition__Group_5__15118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Transition__LabelAssignment_5_1_in_rule__Transition__Group_5__1__Impl5145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Push__Group__0__Impl_in_rule__Push__Group__05179 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_rule__Push__Group__1_in_rule__Push__Group__05182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Push__ListeAssignment_0_in_rule__Push__Group__0__Impl5209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Push__Group__1__Impl_in_rule__Push__Group__15239 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_rule__Push__Group__2_in_rule__Push__Group__15242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__Push__Group__1__Impl5270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Push__Group__2__Impl_in_rule__Push__Group__25301 = new BitSet(new long[]{0x0000000100001030L});
    public static final BitSet FOLLOW_rule__Push__Group__3_in_rule__Push__Group__25304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__Push__Group__2__Impl5332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Push__Group__3__Impl_in_rule__Push__Group__35363 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_rule__Push__Group__4_in_rule__Push__Group__35366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Push__ValueAssignment_3_in_rule__Push__Group__3__Impl5393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Push__Group__4__Impl_in_rule__Push__Group__45423 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Push__Group__5_in_rule__Push__Group__45426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__Push__Group__4__Impl5454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Push__Group__5__Impl_in_rule__Push__Group__55485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Push__Group__5__Impl5513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Pop__Group__0__Impl_in_rule__Pop__Group__05556 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_rule__Pop__Group__1_in_rule__Pop__Group__05559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Pop__ListeAssignment_0_in_rule__Pop__Group__0__Impl5586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Pop__Group__1__Impl_in_rule__Pop__Group__15616 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_rule__Pop__Group__2_in_rule__Pop__Group__15619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_rule__Pop__Group__1__Impl5647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Pop__Group__2__Impl_in_rule__Pop__Group__25678 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_rule__Pop__Group__3_in_rule__Pop__Group__25681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__Pop__Group__2__Impl5709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Pop__Group__3__Impl_in_rule__Pop__Group__35740 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Pop__Group__4_in_rule__Pop__Group__35743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__Pop__Group__3__Impl5771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Pop__Group__4__Impl_in_rule__Pop__Group__45802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Pop__Group__4__Impl5830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Peek__Group__0__Impl_in_rule__Peek__Group__05871 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_rule__Peek__Group__1_in_rule__Peek__Group__05874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Peek__ListeAssignment_0_in_rule__Peek__Group__0__Impl5901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Peek__Group__1__Impl_in_rule__Peek__Group__15931 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_rule__Peek__Group__2_in_rule__Peek__Group__15934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_rule__Peek__Group__1__Impl5962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Peek__Group__2__Impl_in_rule__Peek__Group__25993 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_rule__Peek__Group__3_in_rule__Peek__Group__25996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__Peek__Group__2__Impl6024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Peek__Group__3__Impl_in_rule__Peek__Group__36055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__Peek__Group__3__Impl6083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__0__Impl_in_rule__Assignment__Group__06122 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__Assignment__Group__1_in_rule__Assignment__Group__06125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__LeftAssignment_0_in_rule__Assignment__Group__0__Impl6152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__1__Impl_in_rule__Assignment__Group__16182 = new BitSet(new long[]{0x0000000100001030L});
    public static final BitSet FOLLOW_rule__Assignment__Group__2_in_rule__Assignment__Group__16185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__Assignment__Group__1__Impl6213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__2__Impl_in_rule__Assignment__Group__26244 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_rule__Assignment__Group__3_in_rule__Assignment__Group__26247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__RightAssignment_2_in_rule__Assignment__Group__2__Impl6274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__3__Impl_in_rule__Assignment__Group__36304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__Assignment__Group__3__Impl6332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__Group__0__Impl_in_rule__ArrayVarAccess__Group__06371 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__Group__1_in_rule__ArrayVarAccess__Group__06374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__PrefixAssignment_0_in_rule__ArrayVarAccess__Group__0__Impl6401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__Group__1__Impl_in_rule__ArrayVarAccess__Group__16431 = new BitSet(new long[]{0x0000000100001030L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__Group__2_in_rule__ArrayVarAccess__Group__16434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__ArrayVarAccess__Group__1__Impl6462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__Group__2__Impl_in_rule__ArrayVarAccess__Group__26493 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__Group__3_in_rule__ArrayVarAccess__Group__26496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__IndexAssignment_2_in_rule__ArrayVarAccess__Group__2__Impl6523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ArrayVarAccess__Group__3__Impl_in_rule__ArrayVarAccess__Group__36553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__ArrayVarAccess__Group__3__Impl6581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group__0__Impl_in_rule__BinaryIntExpression__Group__06620 = new BitSet(new long[]{0x000000000001F800L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group__1_in_rule__BinaryIntExpression__Group__06623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryIntExpression_in_rule__BinaryIntExpression__Group__0__Impl6650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group__1__Impl_in_rule__BinaryIntExpression__Group__16679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group_1__0_in_rule__BinaryIntExpression__Group__1__Impl6706 = new BitSet(new long[]{0x000000000001F802L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group_1__0__Impl_in_rule__BinaryIntExpression__Group_1__06741 = new BitSet(new long[]{0x000000000001F800L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group_1__1_in_rule__BinaryIntExpression__Group_1__06744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group_1__1__Impl_in_rule__BinaryIntExpression__Group_1__16802 = new BitSet(new long[]{0x0000000100001030L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group_1__2_in_rule__BinaryIntExpression__Group_1__16805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__OpAssignment_1_1_in_rule__BinaryIntExpression__Group_1__1__Impl6832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__Group_1__2__Impl_in_rule__BinaryIntExpression__Group_1__26862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__BinaryIntExpression__RightAssignment_1_2_in_rule__BinaryIntExpression__Group_1__2__Impl6889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryIntExpression__Group__0__Impl_in_rule__UnaryIntExpression__Group__06925 = new BitSet(new long[]{0x0000000100001030L});
    public static final BitSet FOLLOW_rule__UnaryIntExpression__Group__1_in_rule__UnaryIntExpression__Group__06928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__UnaryIntExpression__Group__0__Impl6957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryIntExpression__Group__1__Impl_in_rule__UnaryIntExpression__Group__16990 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_rule__UnaryIntExpression__Group__2_in_rule__UnaryIntExpression__Group__16993 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_rule__UnaryIntExpression__Group__1__Impl7020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__UnaryIntExpression__Group__2__Impl_in_rule__UnaryIntExpression__Group__27049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_3__0__Impl_in_rule__Primary__Group_3__07113 = new BitSet(new long[]{0x0000000100001030L});
    public static final BitSet FOLLOW_rule__Primary__Group_3__1_in_rule__Primary__Group_3__07116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__Primary__Group_3__0__Impl7144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_3__1__Impl_in_rule__Primary__Group_3__17175 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_rule__Primary__Group_3__2_in_rule__Primary__Group_3__17178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinaryIntExpression_in_rule__Primary__Group_3__1__Impl7205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Primary__Group_3__2__Impl_in_rule__Primary__Group_3__27234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__Primary__Group_3__2__Impl7262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group__0__Impl_in_rule__Or__Group__07299 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_rule__Or__Group__1_in_rule__Or__Group__07302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_rule__Or__Group__0__Impl7329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group__1__Impl_in_rule__Or__Group__17358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group_1__0_in_rule__Or__Group__1__Impl7385 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_rule__Or__Group_1__0__Impl_in_rule__Or__Group_1__07420 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_rule__Or__Group_1__1_in_rule__Or__Group_1__07423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group_1__1__Impl_in_rule__Or__Group_1__17481 = new BitSet(new long[]{0x0000C80100001030L});
    public static final BitSet FOLLOW_rule__Or__Group_1__2_in_rule__Or__Group_1__17484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_rule__Or__Group_1__1__Impl7512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__Group_1__2__Impl_in_rule__Or__Group_1__27543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Or__RightAssignment_1_2_in_rule__Or__Group_1__2__Impl7570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group__0__Impl_in_rule__And__Group__07606 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_rule__And__Group__1_in_rule__And__Group__07609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_rule__And__Group__0__Impl7636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group__1__Impl_in_rule__And__Group__17665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group_1__0_in_rule__And__Group__1__Impl7692 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_rule__And__Group_1__0__Impl_in_rule__And__Group_1__07727 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_rule__And__Group_1__1_in_rule__And__Group_1__07730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group_1__1__Impl_in_rule__And__Group_1__17788 = new BitSet(new long[]{0x0000C80100001030L});
    public static final BitSet FOLLOW_rule__And__Group_1__2_in_rule__And__Group_1__17791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_rule__And__Group_1__1__Impl7819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__Group_1__2__Impl_in_rule__And__Group_1__27850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__And__RightAssignment_1_2_in_rule__And__Group_1__2__Impl7877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Not__Group__0__Impl_in_rule__Not__Group__07913 = new BitSet(new long[]{0x0000C80100001030L});
    public static final BitSet FOLLOW_rule__Not__Group__1_in_rule__Not__Group__07916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_rule__Not__Group__0__Impl7945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Not__Group__1__Impl_in_rule__Not__Group__17978 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_rule__Not__Group__2_in_rule__Not__Group__17981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_rule__Not__Group__1__Impl8008 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Not__Group__2__Impl_in_rule__Not__Group__28037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryBool__Group_3__0__Impl_in_rule__PrimaryBool__Group_3__08101 = new BitSet(new long[]{0x0000C80100001030L});
    public static final BitSet FOLLOW_rule__PrimaryBool__Group_3__1_in_rule__PrimaryBool__Group_3__08104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__PrimaryBool__Group_3__0__Impl8132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryBool__Group_3__1__Impl_in_rule__PrimaryBool__Group_3__18163 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_rule__PrimaryBool__Group_3__2_in_rule__PrimaryBool__Group_3__18166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_rule__PrimaryBool__Group_3__1__Impl8193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PrimaryBool__Group_3__2__Impl_in_rule__PrimaryBool__Group_3__28222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__PrimaryBool__Group_3__2__Impl8250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__Group__0__Impl_in_rule__Comparison__Group__08287 = new BitSet(new long[]{0x00000000007E0000L});
    public static final BitSet FOLLOW_rule__Comparison__Group__1_in_rule__Comparison__Group__08290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__LeftAssignment_0_in_rule__Comparison__Group__0__Impl8317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__Group__1__Impl_in_rule__Comparison__Group__18347 = new BitSet(new long[]{0x0000000100001030L});
    public static final BitSet FOLLOW_rule__Comparison__Group__2_in_rule__Comparison__Group__18350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__OperatorAssignment_1_in_rule__Comparison__Group__1__Impl8377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__Group__2__Impl_in_rule__Comparison__Group__28407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comparison__RightAssignment_2_in_rule__Comparison__Group__2__Impl8434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group__0__Impl_in_rule__QualifiedName__Group__08470 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group__1_in_rule__QualifiedName__Group__08473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__QualifiedName__Group__0__Impl8500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group__1__Impl_in_rule__QualifiedName__Group__18529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group_1__0_in_rule__QualifiedName__Group__1__Impl8556 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group_1__0__Impl_in_rule__QualifiedName__Group_1__08591 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group_1__1_in_rule__QualifiedName__Group_1__08594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_rule__QualifiedName__Group_1__0__Impl8622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedName__Group_1__1__Impl_in_rule__QualifiedName__Group_1__18653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__QualifiedName__Group_1__1__Impl8680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedNameWithWildCard__Group__0__Impl_in_rule__QualifiedNameWithWildCard__Group__08713 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_rule__QualifiedNameWithWildCard__Group__1_in_rule__QualifiedNameWithWildCard__Group__08716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rule__QualifiedNameWithWildCard__Group__0__Impl8743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__QualifiedNameWithWildCard__Group__1__Impl_in_rule__QualifiedNameWithWildCard__Group__18772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_rule__QualifiedNameWithWildCard__Group__1__Impl8801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rule__System__NameAssignment_18844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_rule__System__VariablesAssignment_3_08875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayDeclaration_in_rule__System__ArraysAssignment_3_18906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleListDeclaration_in_rule__System__ListsAssignment_3_28937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransition_in_rule__System__TransitionsAssignment_48968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rule__VariableDeclaration__NameAssignment_18999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__VariableDeclaration__ValueAssignment_39030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__ArrayDeclaration__SizeAssignment_29061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rule__ArrayDeclaration__NameAssignment_49092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInitValues_in_rule__ArrayDeclaration__ValuesAssignment_79123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ListDeclaration__NameAssignment_19154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInitValues_in_rule__ListDeclaration__ValuesAssignment_2_29185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__InitValues__ValuesAssignment_09216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__InitValues__ValuesAssignment_1_19247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Transition__NameAssignment_19278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_rule__Transition__GuardAssignment_39309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Transition__LabelAssignment_5_19340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleActions_in_rule__Transition__ActionsAssignment_79371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Push__ListeAssignment_09406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinaryIntExpression_in_rule__Push__ValueAssignment_39441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Pop__ListeAssignment_09476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Peek__ListeAssignment_09515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVarAccess_in_rule__Assignment__LeftAssignment_09550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinaryIntExpression_in_rule__Assignment__RightAssignment_29581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__VariableRef__VarAssignment9616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ArrayVarAccess__PrefixAssignment_09655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinaryIntExpression_in_rule__ArrayVarAccess__IndexAssignment_29690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinaryArithmeticOperations_in_rule__BinaryIntExpression__OpAssignment_1_19721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnaryIntExpression_in_rule__BinaryIntExpression__RightAssignment_1_29752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__Constante__ValAssignment9783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_rule__Or__RightAssignment_1_29814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_rule__And__RightAssignment_1_29845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinaryIntExpression_in_rule__Comparison__LeftAssignment_09876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparisonOperators_in_rule__Comparison__OperatorAssignment_19907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinaryIntExpression_in_rule__Comparison__RightAssignment_29938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_rule__True__ValAssignment9974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_rule__False__ValAssignment10018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_synpred11_InternalGal2073 = new BitSet(new long[]{0x0000000000000002L});

}