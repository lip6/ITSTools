package fr.lip6.move.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import fr.lip6.move.services.GalGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalGalParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'GAL'", "'{'", "'}'", "'TRANSIENT'", "'='", "';'", "'int'", "'array'", "'['", "']'", "'('", "')'", "'list'", "','", "'transition'", "'label'", "'.push'", "'.pop'", "'|'", "'^'", "'&'", "'<<'", "'>>'", "'+'", "'-'", "'/'", "'*'", "'%'", "'~'", "'**'", "'.peek'", "'||'", "'&&'", "'!'", "'True'", "'False'", "'.'", "'>'", "'<'", "'>='", "'<='", "'=='", "'!='"
    };
    public static final int RULE_ID=5;
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
    public static final int T__51=51;
    public static final int T__16=16;
    public static final int T__52=52;
    public static final int T__15=15;
    public static final int T__53=53;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=4;
    public static final int T__50=50;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int RULE_SL_COMMENT=8;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int RULE_STRING=6;
    public static final int T__32=32;
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
    public String getGrammarFileName() { return "../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g"; }



     	private GalGrammarAccess grammarAccess;
     	
        public InternalGalParser(TokenStream input, GalGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "System";	
       	}
       	
       	@Override
       	protected GalGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleSystem"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:68:1: entryRuleSystem returns [EObject current=null] : iv_ruleSystem= ruleSystem EOF ;
    public final EObject entryRuleSystem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSystem = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:69:2: (iv_ruleSystem= ruleSystem EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:70:2: iv_ruleSystem= ruleSystem EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSystemRule()); 
            }
            pushFollow(FOLLOW_ruleSystem_in_entryRuleSystem75);
            iv_ruleSystem=ruleSystem();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSystem; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleSystem85); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSystem"


    // $ANTLR start "ruleSystem"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:77:1: ruleSystem returns [EObject current=null] : (otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* ) ) ) otherlv_9= '}' ) ;
    public final EObject ruleSystem() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_variables_4_0 = null;

        EObject lv_arrays_5_0 = null;

        EObject lv_lists_6_0 = null;

        EObject lv_transitions_7_0 = null;

        EObject lv_transient_8_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:28: ( (otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* ) ) ) otherlv_9= '}' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:81:1: (otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* ) ) ) otherlv_9= '}' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:81:1: (otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* ) ) ) otherlv_9= '}' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:81:3: otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* ) ) ) otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleSystem122); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getSystemAccess().getGALKeyword_0());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:85:1: ( (lv_name_1_0= ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:86:1: (lv_name_1_0= ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:86:1: (lv_name_1_0= ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:87:3: lv_name_1_0= ruleQualifiedName
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getSystemAccess().getNameQualifiedNameParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleSystem143);
            lv_name_1_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getSystemRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"QualifiedName");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleSystem155); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getSystemAccess().getLeftCurlyBracketKeyword_2());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:107:1: ( ( ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:109:1: ( ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:109:1: ( ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:110:2: ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getSystemAccess().getUnorderedGroup_3());
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:113:2: ( ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:114:3: ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )*
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:114:3: ( ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) ) | ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) ) )*
            loop5:
            do {
                int alt5=6;
                int LA5_0 = input.LA(1);

                if ( LA5_0 ==17 && getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 0) ) {
                    alt5=1;
                }
                else if ( LA5_0 ==18 && getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 1) ) {
                    alt5=2;
                }
                else if ( LA5_0 ==23 && getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 2) ) {
                    alt5=3;
                }
                else if ( LA5_0 ==25 && getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 3) ) {
                    alt5=4;
                }
                else if ( LA5_0 ==14 && getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 4) ) {
                    alt5=5;
                }


                switch (alt5) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:116:4: ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:116:4: ({...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:117:5: {...}? => ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleSystem", "getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 0)");
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:117:103: ( ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+ )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:118:6: ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 0);
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:121:6: ({...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) ) )+
            	    int cnt1=0;
            	    loop1:
            	    do {
            	        int alt1=2;
            	        int LA1_0 = input.LA(1);

            	        if ( (LA1_0==17) ) {
            	            int LA1_2 = input.LA(2);

            	            if ( ((true)) ) {
            	                alt1=1;
            	            }


            	        }


            	        switch (alt1) {
            	    	case 1 :
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:121:7: {...}? => ( (lv_variables_4_0= ruleVariableDeclaration ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "ruleSystem", "true");
            	    	    }
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:121:16: ( (lv_variables_4_0= ruleVariableDeclaration ) )
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:122:1: (lv_variables_4_0= ruleVariableDeclaration )
            	    	    {
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:122:1: (lv_variables_4_0= ruleVariableDeclaration )
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:123:3: lv_variables_4_0= ruleVariableDeclaration
            	    	    {
            	    	    if ( state.backtracking==0 ) {
            	    	       
            	    	      	        newCompositeNode(grammarAccess.getSystemAccess().getVariablesVariableDeclarationParserRuleCall_3_0_0()); 
            	    	      	    
            	    	    }
            	    	    pushFollow(FOLLOW_ruleVariableDeclaration_in_ruleSystem221);
            	    	    lv_variables_4_0=ruleVariableDeclaration();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      	        if (current==null) {
            	    	      	            current = createModelElementForParent(grammarAccess.getSystemRule());
            	    	      	        }
            	    	             		add(
            	    	             			current, 
            	    	             			"variables",
            	    	              		lv_variables_4_0, 
            	    	              		"VariableDeclaration");
            	    	      	        afterParserOrEnumRuleCall();
            	    	      	    
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt1 >= 1 ) break loop1;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(1, input);
            	                throw eee;
            	        }
            	        cnt1++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getSystemAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:146:4: ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:146:4: ({...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:147:5: {...}? => ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleSystem", "getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 1)");
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:147:103: ( ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+ )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:148:6: ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 1);
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:151:6: ({...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) ) )+
            	    int cnt2=0;
            	    loop2:
            	    do {
            	        int alt2=2;
            	        int LA2_0 = input.LA(1);

            	        if ( (LA2_0==18) ) {
            	            int LA2_2 = input.LA(2);

            	            if ( ((true)) ) {
            	                alt2=1;
            	            }


            	        }


            	        switch (alt2) {
            	    	case 1 :
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:151:7: {...}? => ( (lv_arrays_5_0= ruleArrayDeclaration ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "ruleSystem", "true");
            	    	    }
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:151:16: ( (lv_arrays_5_0= ruleArrayDeclaration ) )
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:152:1: (lv_arrays_5_0= ruleArrayDeclaration )
            	    	    {
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:152:1: (lv_arrays_5_0= ruleArrayDeclaration )
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:153:3: lv_arrays_5_0= ruleArrayDeclaration
            	    	    {
            	    	    if ( state.backtracking==0 ) {
            	    	       
            	    	      	        newCompositeNode(grammarAccess.getSystemAccess().getArraysArrayDeclarationParserRuleCall_3_1_0()); 
            	    	      	    
            	    	    }
            	    	    pushFollow(FOLLOW_ruleArrayDeclaration_in_ruleSystem297);
            	    	    lv_arrays_5_0=ruleArrayDeclaration();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      	        if (current==null) {
            	    	      	            current = createModelElementForParent(grammarAccess.getSystemRule());
            	    	      	        }
            	    	             		add(
            	    	             			current, 
            	    	             			"arrays",
            	    	              		lv_arrays_5_0, 
            	    	              		"ArrayDeclaration");
            	    	      	        afterParserOrEnumRuleCall();
            	    	      	    
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt2 >= 1 ) break loop2;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(2, input);
            	                throw eee;
            	        }
            	        cnt2++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getSystemAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:176:4: ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:176:4: ({...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:177:5: {...}? => ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleSystem", "getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 2)");
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:177:103: ( ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+ )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:178:6: ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 2);
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:181:6: ({...}? => ( (lv_lists_6_0= ruleListDeclaration ) ) )+
            	    int cnt3=0;
            	    loop3:
            	    do {
            	        int alt3=2;
            	        int LA3_0 = input.LA(1);

            	        if ( (LA3_0==23) ) {
            	            int LA3_2 = input.LA(2);

            	            if ( ((true)) ) {
            	                alt3=1;
            	            }


            	        }


            	        switch (alt3) {
            	    	case 1 :
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:181:7: {...}? => ( (lv_lists_6_0= ruleListDeclaration ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "ruleSystem", "true");
            	    	    }
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:181:16: ( (lv_lists_6_0= ruleListDeclaration ) )
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:182:1: (lv_lists_6_0= ruleListDeclaration )
            	    	    {
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:182:1: (lv_lists_6_0= ruleListDeclaration )
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:183:3: lv_lists_6_0= ruleListDeclaration
            	    	    {
            	    	    if ( state.backtracking==0 ) {
            	    	       
            	    	      	        newCompositeNode(grammarAccess.getSystemAccess().getListsListDeclarationParserRuleCall_3_2_0()); 
            	    	      	    
            	    	    }
            	    	    pushFollow(FOLLOW_ruleListDeclaration_in_ruleSystem373);
            	    	    lv_lists_6_0=ruleListDeclaration();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      	        if (current==null) {
            	    	      	            current = createModelElementForParent(grammarAccess.getSystemRule());
            	    	      	        }
            	    	             		add(
            	    	             			current, 
            	    	             			"lists",
            	    	              		lv_lists_6_0, 
            	    	              		"ListDeclaration");
            	    	      	        afterParserOrEnumRuleCall();
            	    	      	    
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt3 >= 1 ) break loop3;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(3, input);
            	                throw eee;
            	        }
            	        cnt3++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getSystemAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:206:4: ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:206:4: ({...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:207:5: {...}? => ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleSystem", "getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 3)");
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:207:103: ( ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+ )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:208:6: ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 3);
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:211:6: ({...}? => ( (lv_transitions_7_0= ruleTransition ) ) )+
            	    int cnt4=0;
            	    loop4:
            	    do {
            	        int alt4=2;
            	        int LA4_0 = input.LA(1);

            	        if ( (LA4_0==25) ) {
            	            int LA4_2 = input.LA(2);

            	            if ( ((true)) ) {
            	                alt4=1;
            	            }


            	        }


            	        switch (alt4) {
            	    	case 1 :
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:211:7: {...}? => ( (lv_transitions_7_0= ruleTransition ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "ruleSystem", "true");
            	    	    }
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:211:16: ( (lv_transitions_7_0= ruleTransition ) )
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:212:1: (lv_transitions_7_0= ruleTransition )
            	    	    {
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:212:1: (lv_transitions_7_0= ruleTransition )
            	    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:213:3: lv_transitions_7_0= ruleTransition
            	    	    {
            	    	    if ( state.backtracking==0 ) {
            	    	       
            	    	      	        newCompositeNode(grammarAccess.getSystemAccess().getTransitionsTransitionParserRuleCall_3_3_0()); 
            	    	      	    
            	    	    }
            	    	    pushFollow(FOLLOW_ruleTransition_in_ruleSystem449);
            	    	    lv_transitions_7_0=ruleTransition();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      	        if (current==null) {
            	    	      	            current = createModelElementForParent(grammarAccess.getSystemRule());
            	    	      	        }
            	    	             		add(
            	    	             			current, 
            	    	             			"transitions",
            	    	              		lv_transitions_7_0, 
            	    	              		"Transition");
            	    	      	        afterParserOrEnumRuleCall();
            	    	      	    
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt4 >= 1 ) break loop4;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(4, input);
            	                throw eee;
            	        }
            	        cnt4++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getSystemAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:236:4: ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:236:4: ({...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:237:5: {...}? => ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleSystem", "getUnorderedGroupHelper().canSelect(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 4)");
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:237:103: ( ({...}? => ( (lv_transient_8_0= ruleTransient ) ) ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:238:6: ({...}? => ( (lv_transient_8_0= ruleTransient ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getSystemAccess().getUnorderedGroup_3(), 4);
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:241:6: ({...}? => ( (lv_transient_8_0= ruleTransient ) ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:241:7: {...}? => ( (lv_transient_8_0= ruleTransient ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "ruleSystem", "true");
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:241:16: ( (lv_transient_8_0= ruleTransient ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:242:1: (lv_transient_8_0= ruleTransient )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:242:1: (lv_transient_8_0= ruleTransient )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:243:3: lv_transient_8_0= ruleTransient
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getSystemAccess().getTransientTransientParserRuleCall_3_4_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleTransient_in_ruleSystem525);
            	    lv_transient_8_0=ruleTransient();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getSystemRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"transient",
            	              		lv_transient_8_0, 
            	              		"Transient");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getSystemAccess().getUnorderedGroup_3());

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }


            }

            getUnorderedGroupHelper().leave(grammarAccess.getSystemAccess().getUnorderedGroup_3());

            }

            otherlv_9=(Token)match(input,13,FOLLOW_13_in_ruleSystem577); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_9, grammarAccess.getSystemAccess().getRightCurlyBracketKeyword_4());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSystem"


    // $ANTLR start "entryRuleTransient"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:285:1: entryRuleTransient returns [EObject current=null] : iv_ruleTransient= ruleTransient EOF ;
    public final EObject entryRuleTransient() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransient = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:286:2: (iv_ruleTransient= ruleTransient EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:287:2: iv_ruleTransient= ruleTransient EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTransientRule()); 
            }
            pushFollow(FOLLOW_ruleTransient_in_entryRuleTransient613);
            iv_ruleTransient=ruleTransient();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTransient; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransient623); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTransient"


    // $ANTLR start "ruleTransient"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:294:1: ruleTransient returns [EObject current=null] : (otherlv_0= 'TRANSIENT' otherlv_1= '=' ( (lv_value_2_0= ruleOr ) ) otherlv_3= ';' ) ;
    public final EObject ruleTransient() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:297:28: ( (otherlv_0= 'TRANSIENT' otherlv_1= '=' ( (lv_value_2_0= ruleOr ) ) otherlv_3= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:298:1: (otherlv_0= 'TRANSIENT' otherlv_1= '=' ( (lv_value_2_0= ruleOr ) ) otherlv_3= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:298:1: (otherlv_0= 'TRANSIENT' otherlv_1= '=' ( (lv_value_2_0= ruleOr ) ) otherlv_3= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:298:3: otherlv_0= 'TRANSIENT' otherlv_1= '=' ( (lv_value_2_0= ruleOr ) ) otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,14,FOLLOW_14_in_ruleTransient660); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getTransientAccess().getTRANSIENTKeyword_0());
                  
            }
            otherlv_1=(Token)match(input,15,FOLLOW_15_in_ruleTransient672); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getTransientAccess().getEqualsSignKeyword_1());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:306:1: ( (lv_value_2_0= ruleOr ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:307:1: (lv_value_2_0= ruleOr )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:307:1: (lv_value_2_0= ruleOr )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:308:3: lv_value_2_0= ruleOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getTransientAccess().getValueOrParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleOr_in_ruleTransient693);
            lv_value_2_0=ruleOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getTransientRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_2_0, 
                      		"Or");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,16,FOLLOW_16_in_ruleTransient705); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getTransientAccess().getSemicolonKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTransient"


    // $ANTLR start "entryRuleVariableDeclaration"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:336:1: entryRuleVariableDeclaration returns [EObject current=null] : iv_ruleVariableDeclaration= ruleVariableDeclaration EOF ;
    public final EObject entryRuleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableDeclaration = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:337:2: (iv_ruleVariableDeclaration= ruleVariableDeclaration EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:338:2: iv_ruleVariableDeclaration= ruleVariableDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVariableDeclarationRule()); 
            }
            pushFollow(FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration741);
            iv_ruleVariableDeclaration=ruleVariableDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVariableDeclaration; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableDeclaration751); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableDeclaration"


    // $ANTLR start "ruleVariableDeclaration"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:345:1: ruleVariableDeclaration returns [EObject current=null] : (otherlv_0= 'int' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '=' ( (lv_value_3_0= ruleInteger ) ) otherlv_4= ';' ) ;
    public final EObject ruleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_value_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:348:28: ( (otherlv_0= 'int' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '=' ( (lv_value_3_0= ruleInteger ) ) otherlv_4= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:349:1: (otherlv_0= 'int' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '=' ( (lv_value_3_0= ruleInteger ) ) otherlv_4= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:349:1: (otherlv_0= 'int' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '=' ( (lv_value_3_0= ruleInteger ) ) otherlv_4= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:349:3: otherlv_0= 'int' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '=' ( (lv_value_3_0= ruleInteger ) ) otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_17_in_ruleVariableDeclaration788); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getVariableDeclarationAccess().getIntKeyword_0());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:353:1: ( (lv_name_1_0= ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:354:1: (lv_name_1_0= ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:354:1: (lv_name_1_0= ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:355:3: lv_name_1_0= ruleQualifiedName
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getVariableDeclarationAccess().getNameQualifiedNameParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleVariableDeclaration809);
            lv_name_1_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"QualifiedName");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleVariableDeclaration821); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_2());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:375:1: ( (lv_value_3_0= ruleInteger ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:376:1: (lv_value_3_0= ruleInteger )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:376:1: (lv_value_3_0= ruleInteger )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:377:3: lv_value_3_0= ruleInteger
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getVariableDeclarationAccess().getValueIntegerParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleInteger_in_ruleVariableDeclaration842);
            lv_value_3_0=ruleInteger();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_3_0, 
                      		"Integer");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_4=(Token)match(input,16,FOLLOW_16_in_ruleVariableDeclaration854); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_4());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableDeclaration"


    // $ANTLR start "entryRuleArrayDeclaration"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:405:1: entryRuleArrayDeclaration returns [EObject current=null] : iv_ruleArrayDeclaration= ruleArrayDeclaration EOF ;
    public final EObject entryRuleArrayDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayDeclaration = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:406:2: (iv_ruleArrayDeclaration= ruleArrayDeclaration EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:407:2: iv_ruleArrayDeclaration= ruleArrayDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrayDeclarationRule()); 
            }
            pushFollow(FOLLOW_ruleArrayDeclaration_in_entryRuleArrayDeclaration890);
            iv_ruleArrayDeclaration=ruleArrayDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArrayDeclaration; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleArrayDeclaration900); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArrayDeclaration"


    // $ANTLR start "ruleArrayDeclaration"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:414:1: ruleArrayDeclaration returns [EObject current=null] : (otherlv_0= 'array' otherlv_1= '[' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ']' ( (lv_name_4_0= ruleQualifiedName ) ) otherlv_5= '=' otherlv_6= '(' ( (lv_values_7_0= ruleInitValues ) )? otherlv_8= ')' otherlv_9= ';' ) ;
    public final EObject ruleArrayDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_size_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_name_4_0 = null;

        EObject lv_values_7_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:417:28: ( (otherlv_0= 'array' otherlv_1= '[' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ']' ( (lv_name_4_0= ruleQualifiedName ) ) otherlv_5= '=' otherlv_6= '(' ( (lv_values_7_0= ruleInitValues ) )? otherlv_8= ')' otherlv_9= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:418:1: (otherlv_0= 'array' otherlv_1= '[' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ']' ( (lv_name_4_0= ruleQualifiedName ) ) otherlv_5= '=' otherlv_6= '(' ( (lv_values_7_0= ruleInitValues ) )? otherlv_8= ')' otherlv_9= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:418:1: (otherlv_0= 'array' otherlv_1= '[' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ']' ( (lv_name_4_0= ruleQualifiedName ) ) otherlv_5= '=' otherlv_6= '(' ( (lv_values_7_0= ruleInitValues ) )? otherlv_8= ')' otherlv_9= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:418:3: otherlv_0= 'array' otherlv_1= '[' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ']' ( (lv_name_4_0= ruleQualifiedName ) ) otherlv_5= '=' otherlv_6= '(' ( (lv_values_7_0= ruleInitValues ) )? otherlv_8= ')' otherlv_9= ';'
            {
            otherlv_0=(Token)match(input,18,FOLLOW_18_in_ruleArrayDeclaration937); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getArrayDeclarationAccess().getArrayKeyword_0());
                  
            }
            otherlv_1=(Token)match(input,19,FOLLOW_19_in_ruleArrayDeclaration949); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getArrayDeclarationAccess().getLeftSquareBracketKeyword_1());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:426:1: ( (lv_size_2_0= RULE_INT ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:427:1: (lv_size_2_0= RULE_INT )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:427:1: (lv_size_2_0= RULE_INT )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:428:3: lv_size_2_0= RULE_INT
            {
            lv_size_2_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleArrayDeclaration966); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_size_2_0, grammarAccess.getArrayDeclarationAccess().getSizeINTTerminalRuleCall_2_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getArrayDeclarationRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"size",
                      		lv_size_2_0, 
                      		"INT");
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,20,FOLLOW_20_in_ruleArrayDeclaration983); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getArrayDeclarationAccess().getRightSquareBracketKeyword_3());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:448:1: ( (lv_name_4_0= ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:449:1: (lv_name_4_0= ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:449:1: (lv_name_4_0= ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:450:3: lv_name_4_0= ruleQualifiedName
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getArrayDeclarationAccess().getNameQualifiedNameParserRuleCall_4_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleArrayDeclaration1004);
            lv_name_4_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getArrayDeclarationRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_4_0, 
                      		"QualifiedName");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_5=(Token)match(input,15,FOLLOW_15_in_ruleArrayDeclaration1016); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_5, grammarAccess.getArrayDeclarationAccess().getEqualsSignKeyword_5());
                  
            }
            otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleArrayDeclaration1028); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_6, grammarAccess.getArrayDeclarationAccess().getLeftParenthesisKeyword_6());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:474:1: ( (lv_values_7_0= ruleInitValues ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_INT||LA6_0==35) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:475:1: (lv_values_7_0= ruleInitValues )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:475:1: (lv_values_7_0= ruleInitValues )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:476:3: lv_values_7_0= ruleInitValues
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getArrayDeclarationAccess().getValuesInitValuesParserRuleCall_7_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_ruleInitValues_in_ruleArrayDeclaration1049);
                    lv_values_7_0=ruleInitValues();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getArrayDeclarationRule());
                      	        }
                             		set(
                             			current, 
                             			"values",
                              		lv_values_7_0, 
                              		"InitValues");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,22,FOLLOW_22_in_ruleArrayDeclaration1062); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_8, grammarAccess.getArrayDeclarationAccess().getRightParenthesisKeyword_8());
                  
            }
            otherlv_9=(Token)match(input,16,FOLLOW_16_in_ruleArrayDeclaration1074); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_9, grammarAccess.getArrayDeclarationAccess().getSemicolonKeyword_9());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArrayDeclaration"


    // $ANTLR start "entryRuleListDeclaration"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:508:1: entryRuleListDeclaration returns [EObject current=null] : iv_ruleListDeclaration= ruleListDeclaration EOF ;
    public final EObject entryRuleListDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleListDeclaration = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:509:2: (iv_ruleListDeclaration= ruleListDeclaration EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:510:2: iv_ruleListDeclaration= ruleListDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getListDeclarationRule()); 
            }
            pushFollow(FOLLOW_ruleListDeclaration_in_entryRuleListDeclaration1110);
            iv_ruleListDeclaration=ruleListDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleListDeclaration; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleListDeclaration1120); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleListDeclaration"


    // $ANTLR start "ruleListDeclaration"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:517:1: ruleListDeclaration returns [EObject current=null] : (otherlv_0= 'list' ( (lv_name_1_0= ruleQualifiedName ) ) (otherlv_2= '=' otherlv_3= '(' ( (lv_values_4_0= ruleInitValues ) )? otherlv_5= ')' )? otherlv_6= ';' ) ;
    public final EObject ruleListDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_values_4_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:520:28: ( (otherlv_0= 'list' ( (lv_name_1_0= ruleQualifiedName ) ) (otherlv_2= '=' otherlv_3= '(' ( (lv_values_4_0= ruleInitValues ) )? otherlv_5= ')' )? otherlv_6= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:521:1: (otherlv_0= 'list' ( (lv_name_1_0= ruleQualifiedName ) ) (otherlv_2= '=' otherlv_3= '(' ( (lv_values_4_0= ruleInitValues ) )? otherlv_5= ')' )? otherlv_6= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:521:1: (otherlv_0= 'list' ( (lv_name_1_0= ruleQualifiedName ) ) (otherlv_2= '=' otherlv_3= '(' ( (lv_values_4_0= ruleInitValues ) )? otherlv_5= ')' )? otherlv_6= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:521:3: otherlv_0= 'list' ( (lv_name_1_0= ruleQualifiedName ) ) (otherlv_2= '=' otherlv_3= '(' ( (lv_values_4_0= ruleInitValues ) )? otherlv_5= ')' )? otherlv_6= ';'
            {
            otherlv_0=(Token)match(input,23,FOLLOW_23_in_ruleListDeclaration1157); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getListDeclarationAccess().getListKeyword_0());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:525:1: ( (lv_name_1_0= ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:526:1: (lv_name_1_0= ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:526:1: (lv_name_1_0= ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:527:3: lv_name_1_0= ruleQualifiedName
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getListDeclarationAccess().getNameQualifiedNameParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleListDeclaration1178);
            lv_name_1_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getListDeclarationRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"QualifiedName");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:543:2: (otherlv_2= '=' otherlv_3= '(' ( (lv_values_4_0= ruleInitValues ) )? otherlv_5= ')' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==15) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:543:4: otherlv_2= '=' otherlv_3= '(' ( (lv_values_4_0= ruleInitValues ) )? otherlv_5= ')'
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleListDeclaration1191); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_2, grammarAccess.getListDeclarationAccess().getEqualsSignKeyword_2_0());
                          
                    }
                    otherlv_3=(Token)match(input,21,FOLLOW_21_in_ruleListDeclaration1203); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getListDeclarationAccess().getLeftParenthesisKeyword_2_1());
                          
                    }
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:551:1: ( (lv_values_4_0= ruleInitValues ) )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==RULE_INT||LA7_0==35) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:552:1: (lv_values_4_0= ruleInitValues )
                            {
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:552:1: (lv_values_4_0= ruleInitValues )
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:553:3: lv_values_4_0= ruleInitValues
                            {
                            if ( state.backtracking==0 ) {
                               
                              	        newCompositeNode(grammarAccess.getListDeclarationAccess().getValuesInitValuesParserRuleCall_2_2_0()); 
                              	    
                            }
                            pushFollow(FOLLOW_ruleInitValues_in_ruleListDeclaration1224);
                            lv_values_4_0=ruleInitValues();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              	        if (current==null) {
                              	            current = createModelElementForParent(grammarAccess.getListDeclarationRule());
                              	        }
                                     		set(
                                     			current, 
                                     			"values",
                                      		lv_values_4_0, 
                                      		"InitValues");
                              	        afterParserOrEnumRuleCall();
                              	    
                            }

                            }


                            }
                            break;

                    }

                    otherlv_5=(Token)match(input,22,FOLLOW_22_in_ruleListDeclaration1237); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getListDeclarationAccess().getRightParenthesisKeyword_2_3());
                          
                    }

                    }
                    break;

            }

            otherlv_6=(Token)match(input,16,FOLLOW_16_in_ruleListDeclaration1251); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_6, grammarAccess.getListDeclarationAccess().getSemicolonKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleListDeclaration"


    // $ANTLR start "entryRuleInitValues"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:585:1: entryRuleInitValues returns [EObject current=null] : iv_ruleInitValues= ruleInitValues EOF ;
    public final EObject entryRuleInitValues() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitValues = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:586:2: (iv_ruleInitValues= ruleInitValues EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:587:2: iv_ruleInitValues= ruleInitValues EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getInitValuesRule()); 
            }
            pushFollow(FOLLOW_ruleInitValues_in_entryRuleInitValues1287);
            iv_ruleInitValues=ruleInitValues();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleInitValues; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleInitValues1297); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInitValues"


    // $ANTLR start "ruleInitValues"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:594:1: ruleInitValues returns [EObject current=null] : ( ( (lv_values_0_0= ruleInteger ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleInteger ) ) )* ) ;
    public final EObject ruleInitValues() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_values_0_0 = null;

        AntlrDatatypeRuleToken lv_values_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:597:28: ( ( ( (lv_values_0_0= ruleInteger ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleInteger ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:598:1: ( ( (lv_values_0_0= ruleInteger ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleInteger ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:598:1: ( ( (lv_values_0_0= ruleInteger ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleInteger ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:598:2: ( (lv_values_0_0= ruleInteger ) ) (otherlv_1= ',' ( (lv_values_2_0= ruleInteger ) ) )*
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:598:2: ( (lv_values_0_0= ruleInteger ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:599:1: (lv_values_0_0= ruleInteger )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:599:1: (lv_values_0_0= ruleInteger )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:600:3: lv_values_0_0= ruleInteger
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getInitValuesAccess().getValuesIntegerParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleInteger_in_ruleInitValues1343);
            lv_values_0_0=ruleInteger();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getInitValuesRule());
              	        }
                     		add(
                     			current, 
                     			"values",
                      		lv_values_0_0, 
                      		"Integer");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:616:2: (otherlv_1= ',' ( (lv_values_2_0= ruleInteger ) ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==24) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:616:4: otherlv_1= ',' ( (lv_values_2_0= ruleInteger ) )
            	    {
            	    otherlv_1=(Token)match(input,24,FOLLOW_24_in_ruleInitValues1356); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_1, grammarAccess.getInitValuesAccess().getCommaKeyword_1_0());
            	          
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:620:1: ( (lv_values_2_0= ruleInteger ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:621:1: (lv_values_2_0= ruleInteger )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:621:1: (lv_values_2_0= ruleInteger )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:622:3: lv_values_2_0= ruleInteger
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getInitValuesAccess().getValuesIntegerParserRuleCall_1_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleInteger_in_ruleInitValues1377);
            	    lv_values_2_0=ruleInteger();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getInitValuesRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"values",
            	              		lv_values_2_0, 
            	              		"Integer");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInitValues"


    // $ANTLR start "entryRuleTransition"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:646:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:647:2: (iv_ruleTransition= ruleTransition EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:648:2: iv_ruleTransition= ruleTransition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTransitionRule()); 
            }
            pushFollow(FOLLOW_ruleTransition_in_entryRuleTransition1415);
            iv_ruleTransition=ruleTransition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTransition; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransition1425); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTransition"


    // $ANTLR start "ruleTransition"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:655:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= ruleQualifiedName ) ) )? otherlv_7= '{' ( (lv_actions_8_0= ruleActions ) )* otherlv_9= '}' ) ;
    public final EObject ruleTransition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_guard_3_0 = null;

        AntlrDatatypeRuleToken lv_label_6_0 = null;

        EObject lv_actions_8_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:658:28: ( (otherlv_0= 'transition' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= ruleQualifiedName ) ) )? otherlv_7= '{' ( (lv_actions_8_0= ruleActions ) )* otherlv_9= '}' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:659:1: (otherlv_0= 'transition' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= ruleQualifiedName ) ) )? otherlv_7= '{' ( (lv_actions_8_0= ruleActions ) )* otherlv_9= '}' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:659:1: (otherlv_0= 'transition' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= ruleQualifiedName ) ) )? otherlv_7= '{' ( (lv_actions_8_0= ruleActions ) )* otherlv_9= '}' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:659:3: otherlv_0= 'transition' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= ruleQualifiedName ) ) )? otherlv_7= '{' ( (lv_actions_8_0= ruleActions ) )* otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,25,FOLLOW_25_in_ruleTransition1462); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:663:1: ( (lv_name_1_0= ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:664:1: (lv_name_1_0= ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:664:1: (lv_name_1_0= ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:665:3: lv_name_1_0= ruleQualifiedName
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getTransitionAccess().getNameQualifiedNameParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleTransition1483);
            lv_name_1_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getTransitionRule());
              	        }
                     		set(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"QualifiedName");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,19,FOLLOW_19_in_ruleTransition1495); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getLeftSquareBracketKeyword_2());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:685:1: ( (lv_guard_3_0= ruleOr ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:686:1: (lv_guard_3_0= ruleOr )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:686:1: (lv_guard_3_0= ruleOr )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:687:3: lv_guard_3_0= ruleOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getTransitionAccess().getGuardOrParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleOr_in_ruleTransition1516);
            lv_guard_3_0=ruleOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getTransitionRule());
              	        }
                     		set(
                     			current, 
                     			"guard",
                      		lv_guard_3_0, 
                      		"Or");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_4=(Token)match(input,20,FOLLOW_20_in_ruleTransition1528); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getTransitionAccess().getRightSquareBracketKeyword_4());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:707:1: (otherlv_5= 'label' ( (lv_label_6_0= ruleQualifiedName ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==26) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:707:3: otherlv_5= 'label' ( (lv_label_6_0= ruleQualifiedName ) )
                    {
                    otherlv_5=(Token)match(input,26,FOLLOW_26_in_ruleTransition1541); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getLabelKeyword_5_0());
                          
                    }
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:711:1: ( (lv_label_6_0= ruleQualifiedName ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:712:1: (lv_label_6_0= ruleQualifiedName )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:712:1: (lv_label_6_0= ruleQualifiedName )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:713:3: lv_label_6_0= ruleQualifiedName
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTransitionAccess().getLabelQualifiedNameParserRuleCall_5_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_ruleQualifiedName_in_ruleTransition1562);
                    lv_label_6_0=ruleQualifiedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTransitionRule());
                      	        }
                             		set(
                             			current, 
                             			"label",
                              		lv_label_6_0, 
                              		"QualifiedName");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

            }

            otherlv_7=(Token)match(input,12,FOLLOW_12_in_ruleTransition1576); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_6());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:733:1: ( (lv_actions_8_0= ruleActions ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_ID) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:734:1: (lv_actions_8_0= ruleActions )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:734:1: (lv_actions_8_0= ruleActions )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:735:3: lv_actions_8_0= ruleActions
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getTransitionAccess().getActionsActionsParserRuleCall_7_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleActions_in_ruleTransition1597);
            	    lv_actions_8_0=ruleActions();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getTransitionRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"actions",
            	              		lv_actions_8_0, 
            	              		"Actions");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            otherlv_9=(Token)match(input,13,FOLLOW_13_in_ruleTransition1610); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_9, grammarAccess.getTransitionAccess().getRightCurlyBracketKeyword_8());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTransition"


    // $ANTLR start "entryRuleActions"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:763:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:764:2: (iv_ruleActions= ruleActions EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:765:2: iv_ruleActions= ruleActions EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionsRule()); 
            }
            pushFollow(FOLLOW_ruleActions_in_entryRuleActions1646);
            iv_ruleActions=ruleActions();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActions; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleActions1656); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActions"


    // $ANTLR start "ruleActions"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:772:1: ruleActions returns [EObject current=null] : (this_Assignment_0= ruleAssignment | this_Push_1= rulePush | this_Pop_2= rulePop ) ;
    public final EObject ruleActions() throws RecognitionException {
        EObject current = null;

        EObject this_Assignment_0 = null;

        EObject this_Push_1 = null;

        EObject this_Pop_2 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:775:28: ( (this_Assignment_0= ruleAssignment | this_Push_1= rulePush | this_Pop_2= rulePop ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:776:1: (this_Assignment_0= ruleAssignment | this_Push_1= rulePush | this_Pop_2= rulePop )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:776:1: (this_Assignment_0= ruleAssignment | this_Push_1= rulePush | this_Pop_2= rulePop )
            int alt12=3;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:777:5: this_Assignment_0= ruleAssignment
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getActionsAccess().getAssignmentParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_ruleAssignment_in_ruleActions1703);
                    this_Assignment_0=ruleAssignment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Assignment_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:787:5: this_Push_1= rulePush
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getActionsAccess().getPushParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_rulePush_in_ruleActions1730);
                    this_Push_1=rulePush();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Push_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:797:5: this_Pop_2= rulePop
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getActionsAccess().getPopParserRuleCall_2()); 
                          
                    }
                    pushFollow(FOLLOW_rulePop_in_ruleActions1757);
                    this_Pop_2=rulePop();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Pop_2; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActions"


    // $ANTLR start "entryRulePush"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:813:1: entryRulePush returns [EObject current=null] : iv_rulePush= rulePush EOF ;
    public final EObject entryRulePush() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePush = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:814:2: (iv_rulePush= rulePush EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:815:2: iv_rulePush= rulePush EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPushRule()); 
            }
            pushFollow(FOLLOW_rulePush_in_entryRulePush1792);
            iv_rulePush=rulePush();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePush; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePush1802); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePush"


    // $ANTLR start "rulePush"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:822:1: rulePush returns [EObject current=null] : ( ( ( ruleQualifiedName ) ) otherlv_1= '.push' otherlv_2= '(' ( (lv_value_3_0= ruleBitOr ) ) otherlv_4= ')' otherlv_5= ';' ) ;
    public final EObject rulePush() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        EObject lv_value_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:825:28: ( ( ( ( ruleQualifiedName ) ) otherlv_1= '.push' otherlv_2= '(' ( (lv_value_3_0= ruleBitOr ) ) otherlv_4= ')' otherlv_5= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:826:1: ( ( ( ruleQualifiedName ) ) otherlv_1= '.push' otherlv_2= '(' ( (lv_value_3_0= ruleBitOr ) ) otherlv_4= ')' otherlv_5= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:826:1: ( ( ( ruleQualifiedName ) ) otherlv_1= '.push' otherlv_2= '(' ( (lv_value_3_0= ruleBitOr ) ) otherlv_4= ')' otherlv_5= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:826:2: ( ( ruleQualifiedName ) ) otherlv_1= '.push' otherlv_2= '(' ( (lv_value_3_0= ruleBitOr ) ) otherlv_4= ')' otherlv_5= ';'
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:826:2: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:827:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:827:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:828:3: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getPushRule());
              	        }
                      
            }
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getPushAccess().getListListCrossReference_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_rulePush1850);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_27_in_rulePush1862); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getPushAccess().getPushKeyword_1());
                  
            }
            otherlv_2=(Token)match(input,21,FOLLOW_21_in_rulePush1874); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getPushAccess().getLeftParenthesisKeyword_2());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:849:1: ( (lv_value_3_0= ruleBitOr ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:850:1: (lv_value_3_0= ruleBitOr )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:850:1: (lv_value_3_0= ruleBitOr )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:851:3: lv_value_3_0= ruleBitOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getPushAccess().getValueBitOrParserRuleCall_3_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBitOr_in_rulePush1895);
            lv_value_3_0=ruleBitOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getPushRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_3_0, 
                      		"BitOr");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_4=(Token)match(input,22,FOLLOW_22_in_rulePush1907); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getPushAccess().getRightParenthesisKeyword_4());
                  
            }
            otherlv_5=(Token)match(input,16,FOLLOW_16_in_rulePush1919); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_5, grammarAccess.getPushAccess().getSemicolonKeyword_5());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePush"


    // $ANTLR start "entryRulePop"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:883:1: entryRulePop returns [EObject current=null] : iv_rulePop= rulePop EOF ;
    public final EObject entryRulePop() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePop = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:884:2: (iv_rulePop= rulePop EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:885:2: iv_rulePop= rulePop EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPopRule()); 
            }
            pushFollow(FOLLOW_rulePop_in_entryRulePop1955);
            iv_rulePop=rulePop();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePop; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePop1965); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePop"


    // $ANTLR start "rulePop"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:892:1: rulePop returns [EObject current=null] : ( ( ( ruleQualifiedName ) ) otherlv_1= '.pop' otherlv_2= '(' otherlv_3= ')' otherlv_4= ';' ) ;
    public final EObject rulePop() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:895:28: ( ( ( ( ruleQualifiedName ) ) otherlv_1= '.pop' otherlv_2= '(' otherlv_3= ')' otherlv_4= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:896:1: ( ( ( ruleQualifiedName ) ) otherlv_1= '.pop' otherlv_2= '(' otherlv_3= ')' otherlv_4= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:896:1: ( ( ( ruleQualifiedName ) ) otherlv_1= '.pop' otherlv_2= '(' otherlv_3= ')' otherlv_4= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:896:2: ( ( ruleQualifiedName ) ) otherlv_1= '.pop' otherlv_2= '(' otherlv_3= ')' otherlv_4= ';'
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:896:2: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:897:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:897:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:898:3: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getPopRule());
              	        }
                      
            }
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getPopAccess().getListListCrossReference_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_rulePop2013);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_1=(Token)match(input,28,FOLLOW_28_in_rulePop2025); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getPopAccess().getPopKeyword_1());
                  
            }
            otherlv_2=(Token)match(input,21,FOLLOW_21_in_rulePop2037); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getPopAccess().getLeftParenthesisKeyword_2());
                  
            }
            otherlv_3=(Token)match(input,22,FOLLOW_22_in_rulePop2049); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getPopAccess().getRightParenthesisKeyword_3());
                  
            }
            otherlv_4=(Token)match(input,16,FOLLOW_16_in_rulePop2061); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getPopAccess().getSemicolonKeyword_4());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePop"


    // $ANTLR start "entryRuleAssignment"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:935:1: entryRuleAssignment returns [EObject current=null] : iv_ruleAssignment= ruleAssignment EOF ;
    public final EObject entryRuleAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssignment = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:936:2: (iv_ruleAssignment= ruleAssignment EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:937:2: iv_ruleAssignment= ruleAssignment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAssignmentRule()); 
            }
            pushFollow(FOLLOW_ruleAssignment_in_entryRuleAssignment2097);
            iv_ruleAssignment=ruleAssignment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAssignment; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssignment2107); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAssignment"


    // $ANTLR start "ruleAssignment"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:944:1: ruleAssignment returns [EObject current=null] : ( ( (lv_left_0_0= ruleVarAccess ) ) otherlv_1= '=' ( (lv_right_2_0= ruleBitOr ) ) otherlv_3= ';' ) ;
    public final EObject ruleAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:947:28: ( ( ( (lv_left_0_0= ruleVarAccess ) ) otherlv_1= '=' ( (lv_right_2_0= ruleBitOr ) ) otherlv_3= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:948:1: ( ( (lv_left_0_0= ruleVarAccess ) ) otherlv_1= '=' ( (lv_right_2_0= ruleBitOr ) ) otherlv_3= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:948:1: ( ( (lv_left_0_0= ruleVarAccess ) ) otherlv_1= '=' ( (lv_right_2_0= ruleBitOr ) ) otherlv_3= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:948:2: ( (lv_left_0_0= ruleVarAccess ) ) otherlv_1= '=' ( (lv_right_2_0= ruleBitOr ) ) otherlv_3= ';'
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:948:2: ( (lv_left_0_0= ruleVarAccess ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:949:1: (lv_left_0_0= ruleVarAccess )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:949:1: (lv_left_0_0= ruleVarAccess )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:950:3: lv_left_0_0= ruleVarAccess
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getAssignmentAccess().getLeftVarAccessParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleVarAccess_in_ruleAssignment2153);
            lv_left_0_0=ruleVarAccess();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getAssignmentRule());
              	        }
                     		set(
                     			current, 
                     			"left",
                      		lv_left_0_0, 
                      		"VarAccess");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_1=(Token)match(input,15,FOLLOW_15_in_ruleAssignment2165); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:970:1: ( (lv_right_2_0= ruleBitOr ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:971:1: (lv_right_2_0= ruleBitOr )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:971:1: (lv_right_2_0= ruleBitOr )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:972:3: lv_right_2_0= ruleBitOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getAssignmentAccess().getRightBitOrParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBitOr_in_ruleAssignment2186);
            lv_right_2_0=ruleBitOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getAssignmentRule());
              	        }
                     		set(
                     			current, 
                     			"right",
                      		lv_right_2_0, 
                      		"BitOr");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,16,FOLLOW_16_in_ruleAssignment2198); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getAssignmentAccess().getSemicolonKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAssignment"


    // $ANTLR start "entryRuleVarAccess"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1000:1: entryRuleVarAccess returns [EObject current=null] : iv_ruleVarAccess= ruleVarAccess EOF ;
    public final EObject entryRuleVarAccess() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarAccess = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1001:2: (iv_ruleVarAccess= ruleVarAccess EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1002:2: iv_ruleVarAccess= ruleVarAccess EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVarAccessRule()); 
            }
            pushFollow(FOLLOW_ruleVarAccess_in_entryRuleVarAccess2234);
            iv_ruleVarAccess=ruleVarAccess();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVarAccess; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleVarAccess2244); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVarAccess"


    // $ANTLR start "ruleVarAccess"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1009:1: ruleVarAccess returns [EObject current=null] : (this_ArrayVarAccess_0= ruleArrayVarAccess | this_VariableRef_1= ruleVariableRef ) ;
    public final EObject ruleVarAccess() throws RecognitionException {
        EObject current = null;

        EObject this_ArrayVarAccess_0 = null;

        EObject this_VariableRef_1 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1012:28: ( (this_ArrayVarAccess_0= ruleArrayVarAccess | this_VariableRef_1= ruleVariableRef ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1013:1: (this_ArrayVarAccess_0= ruleArrayVarAccess | this_VariableRef_1= ruleVariableRef )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1013:1: (this_ArrayVarAccess_0= ruleArrayVarAccess | this_VariableRef_1= ruleVariableRef )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1014:5: this_ArrayVarAccess_0= ruleArrayVarAccess
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getVarAccessAccess().getArrayVarAccessParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_ruleArrayVarAccess_in_ruleVarAccess2291);
                    this_ArrayVarAccess_0=ruleArrayVarAccess();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_ArrayVarAccess_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1024:5: this_VariableRef_1= ruleVariableRef
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getVarAccessAccess().getVariableRefParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleVariableRef_in_ruleVarAccess2318);
                    this_VariableRef_1=ruleVariableRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_VariableRef_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVarAccess"


    // $ANTLR start "entryRuleVariableRef"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1040:1: entryRuleVariableRef returns [EObject current=null] : iv_ruleVariableRef= ruleVariableRef EOF ;
    public final EObject entryRuleVariableRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableRef = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1041:2: (iv_ruleVariableRef= ruleVariableRef EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1042:2: iv_ruleVariableRef= ruleVariableRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVariableRefRule()); 
            }
            pushFollow(FOLLOW_ruleVariableRef_in_entryRuleVariableRef2353);
            iv_ruleVariableRef=ruleVariableRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVariableRef; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableRef2363); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableRef"


    // $ANTLR start "ruleVariableRef"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1049:1: ruleVariableRef returns [EObject current=null] : ( ( ruleQualifiedName ) ) ;
    public final EObject ruleVariableRef() throws RecognitionException {
        EObject current = null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1052:28: ( ( ( ruleQualifiedName ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1053:1: ( ( ruleQualifiedName ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1053:1: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1054:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1054:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1055:3: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getVariableRefRule());
              	        }
                      
            }
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getVariableRefAccess().getReferencedVarVariableCrossReference_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleVariableRef2410);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableRef"


    // $ANTLR start "entryRuleArrayVarAccess"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1076:1: entryRuleArrayVarAccess returns [EObject current=null] : iv_ruleArrayVarAccess= ruleArrayVarAccess EOF ;
    public final EObject entryRuleArrayVarAccess() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayVarAccess = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1077:2: (iv_ruleArrayVarAccess= ruleArrayVarAccess EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1078:2: iv_ruleArrayVarAccess= ruleArrayVarAccess EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrayVarAccessRule()); 
            }
            pushFollow(FOLLOW_ruleArrayVarAccess_in_entryRuleArrayVarAccess2445);
            iv_ruleArrayVarAccess=ruleArrayVarAccess();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArrayVarAccess; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleArrayVarAccess2455); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArrayVarAccess"


    // $ANTLR start "ruleArrayVarAccess"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1085:1: ruleArrayVarAccess returns [EObject current=null] : ( ( ( ruleQualifiedName ) ) otherlv_1= '[' ( (lv_index_2_0= ruleBitOr ) ) otherlv_3= ']' ) ;
    public final EObject ruleArrayVarAccess() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_index_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1088:28: ( ( ( ( ruleQualifiedName ) ) otherlv_1= '[' ( (lv_index_2_0= ruleBitOr ) ) otherlv_3= ']' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1089:1: ( ( ( ruleQualifiedName ) ) otherlv_1= '[' ( (lv_index_2_0= ruleBitOr ) ) otherlv_3= ']' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1089:1: ( ( ( ruleQualifiedName ) ) otherlv_1= '[' ( (lv_index_2_0= ruleBitOr ) ) otherlv_3= ']' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1089:2: ( ( ruleQualifiedName ) ) otherlv_1= '[' ( (lv_index_2_0= ruleBitOr ) ) otherlv_3= ']'
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1089:2: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1090:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1090:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1091:3: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getArrayVarAccessRule());
              	        }
                      
            }
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getArrayVarAccessAccess().getPrefixArrayPrefixCrossReference_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleArrayVarAccess2503);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_1=(Token)match(input,19,FOLLOW_19_in_ruleArrayVarAccess2515); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getArrayVarAccessAccess().getLeftSquareBracketKeyword_1());
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1108:1: ( (lv_index_2_0= ruleBitOr ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1109:1: (lv_index_2_0= ruleBitOr )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1109:1: (lv_index_2_0= ruleBitOr )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1110:3: lv_index_2_0= ruleBitOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getArrayVarAccessAccess().getIndexBitOrParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBitOr_in_ruleArrayVarAccess2536);
            lv_index_2_0=ruleBitOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getArrayVarAccessRule());
              	        }
                     		set(
                     			current, 
                     			"index",
                      		lv_index_2_0, 
                      		"BitOr");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,20,FOLLOW_20_in_ruleArrayVarAccess2548); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getArrayVarAccessAccess().getRightSquareBracketKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArrayVarAccess"


    // $ANTLR start "entryRuleBitOr"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1138:1: entryRuleBitOr returns [EObject current=null] : iv_ruleBitOr= ruleBitOr EOF ;
    public final EObject entryRuleBitOr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitOr = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1139:2: (iv_ruleBitOr= ruleBitOr EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1140:2: iv_ruleBitOr= ruleBitOr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitOrRule()); 
            }
            pushFollow(FOLLOW_ruleBitOr_in_entryRuleBitOr2584);
            iv_ruleBitOr=ruleBitOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitOr; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitOr2594); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBitOr"


    // $ANTLR start "ruleBitOr"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1147:1: ruleBitOr returns [EObject current=null] : (this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )* ) ;
    public final EObject ruleBitOr() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_BitXor_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1150:28: ( (this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1151:1: (this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1151:1: (this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1152:5: this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getBitOrAccess().getBitXorParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleBitXor_in_ruleBitOr2641);
            this_BitXor_0=ruleBitXor();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_BitXor_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1160:1: ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==29) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1160:2: () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1160:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1161:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getBitOrAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1166:2: ( (lv_op_2_0= '|' ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1167:1: (lv_op_2_0= '|' )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1167:1: (lv_op_2_0= '|' )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1168:3: lv_op_2_0= '|'
            	    {
            	    lv_op_2_0=(Token)match(input,29,FOLLOW_29_in_ruleBitOr2668); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	              newLeafNode(lv_op_2_0, grammarAccess.getBitOrAccess().getOpVerticalLineKeyword_1_1_0());
            	          
            	    }
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElement(grammarAccess.getBitOrRule());
            	      	        }
            	             		setWithLastConsumed(current, "op", lv_op_2_0, "|");
            	      	    
            	    }

            	    }


            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1181:2: ( (lv_right_3_0= ruleBitXor ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1182:1: (lv_right_3_0= ruleBitXor )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1182:1: (lv_right_3_0= ruleBitXor )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1183:3: lv_right_3_0= ruleBitXor
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getBitOrAccess().getRightBitXorParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleBitXor_in_ruleBitOr2702);
            	    lv_right_3_0=ruleBitXor();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getBitOrRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_3_0, 
            	              		"BitXor");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBitOr"


    // $ANTLR start "entryRuleBitXor"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1207:1: entryRuleBitXor returns [EObject current=null] : iv_ruleBitXor= ruleBitXor EOF ;
    public final EObject entryRuleBitXor() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitXor = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1208:2: (iv_ruleBitXor= ruleBitXor EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1209:2: iv_ruleBitXor= ruleBitXor EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitXorRule()); 
            }
            pushFollow(FOLLOW_ruleBitXor_in_entryRuleBitXor2740);
            iv_ruleBitXor=ruleBitXor();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitXor; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitXor2750); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBitXor"


    // $ANTLR start "ruleBitXor"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1216:1: ruleBitXor returns [EObject current=null] : (this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )* ) ;
    public final EObject ruleBitXor() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_BitAnd_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1219:28: ( (this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1220:1: (this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1220:1: (this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1221:5: this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getBitXorAccess().getBitAndParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleBitAnd_in_ruleBitXor2797);
            this_BitAnd_0=ruleBitAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_BitAnd_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1229:1: ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==30) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1229:2: () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1229:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1230:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getBitXorAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1235:2: ( (lv_op_2_0= '^' ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1236:1: (lv_op_2_0= '^' )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1236:1: (lv_op_2_0= '^' )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1237:3: lv_op_2_0= '^'
            	    {
            	    lv_op_2_0=(Token)match(input,30,FOLLOW_30_in_ruleBitXor2824); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	              newLeafNode(lv_op_2_0, grammarAccess.getBitXorAccess().getOpCircumflexAccentKeyword_1_1_0());
            	          
            	    }
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElement(grammarAccess.getBitXorRule());
            	      	        }
            	             		setWithLastConsumed(current, "op", lv_op_2_0, "^");
            	      	    
            	    }

            	    }


            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1250:2: ( (lv_right_3_0= ruleBitAnd ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1251:1: (lv_right_3_0= ruleBitAnd )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1251:1: (lv_right_3_0= ruleBitAnd )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1252:3: lv_right_3_0= ruleBitAnd
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getBitXorAccess().getRightBitAndParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleBitAnd_in_ruleBitXor2858);
            	    lv_right_3_0=ruleBitAnd();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getBitXorRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_3_0, 
            	              		"BitAnd");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBitXor"


    // $ANTLR start "entryRuleBitAnd"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1276:1: entryRuleBitAnd returns [EObject current=null] : iv_ruleBitAnd= ruleBitAnd EOF ;
    public final EObject entryRuleBitAnd() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitAnd = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1277:2: (iv_ruleBitAnd= ruleBitAnd EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1278:2: iv_ruleBitAnd= ruleBitAnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitAndRule()); 
            }
            pushFollow(FOLLOW_ruleBitAnd_in_entryRuleBitAnd2896);
            iv_ruleBitAnd=ruleBitAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitAnd; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitAnd2906); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBitAnd"


    // $ANTLR start "ruleBitAnd"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1285:1: ruleBitAnd returns [EObject current=null] : (this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )* ) ;
    public final EObject ruleBitAnd() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_BitShift_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1288:28: ( (this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1289:1: (this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1289:1: (this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1290:5: this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getBitAndAccess().getBitShiftParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleBitShift_in_ruleBitAnd2953);
            this_BitShift_0=ruleBitShift();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_BitShift_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1298:1: ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==31) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1298:2: () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1298:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1299:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getBitAndAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1304:2: ( (lv_op_2_0= '&' ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1305:1: (lv_op_2_0= '&' )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1305:1: (lv_op_2_0= '&' )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1306:3: lv_op_2_0= '&'
            	    {
            	    lv_op_2_0=(Token)match(input,31,FOLLOW_31_in_ruleBitAnd2980); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	              newLeafNode(lv_op_2_0, grammarAccess.getBitAndAccess().getOpAmpersandKeyword_1_1_0());
            	          
            	    }
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElement(grammarAccess.getBitAndRule());
            	      	        }
            	             		setWithLastConsumed(current, "op", lv_op_2_0, "&");
            	      	    
            	    }

            	    }


            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1319:2: ( (lv_right_3_0= ruleBitShift ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1320:1: (lv_right_3_0= ruleBitShift )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1320:1: (lv_right_3_0= ruleBitShift )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1321:3: lv_right_3_0= ruleBitShift
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getBitAndAccess().getRightBitShiftParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleBitShift_in_ruleBitAnd3014);
            	    lv_right_3_0=ruleBitShift();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getBitAndRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_3_0, 
            	              		"BitShift");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBitAnd"


    // $ANTLR start "entryRuleBitShift"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1345:1: entryRuleBitShift returns [EObject current=null] : iv_ruleBitShift= ruleBitShift EOF ;
    public final EObject entryRuleBitShift() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitShift = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1346:2: (iv_ruleBitShift= ruleBitShift EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1347:2: iv_ruleBitShift= ruleBitShift EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitShiftRule()); 
            }
            pushFollow(FOLLOW_ruleBitShift_in_entryRuleBitShift3052);
            iv_ruleBitShift=ruleBitShift();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitShift; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitShift3062); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBitShift"


    // $ANTLR start "ruleBitShift"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1354:1: ruleBitShift returns [EObject current=null] : (this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )* ) ;
    public final EObject ruleBitShift() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_Addition_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1357:28: ( (this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1358:1: (this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1358:1: (this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1359:5: this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getBitShiftAccess().getAdditionParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleAddition_in_ruleBitShift3109);
            this_Addition_0=ruleAddition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Addition_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1367:1: ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=32 && LA18_0<=33)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1367:2: () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1367:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1368:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getBitShiftAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1373:2: ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1374:1: ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1374:1: ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1375:1: (lv_op_2_1= '<<' | lv_op_2_2= '>>' )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1375:1: (lv_op_2_1= '<<' | lv_op_2_2= '>>' )
            	    int alt17=2;
            	    int LA17_0 = input.LA(1);

            	    if ( (LA17_0==32) ) {
            	        alt17=1;
            	    }
            	    else if ( (LA17_0==33) ) {
            	        alt17=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 17, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt17) {
            	        case 1 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1376:3: lv_op_2_1= '<<'
            	            {
            	            lv_op_2_1=(Token)match(input,32,FOLLOW_32_in_ruleBitShift3138); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                      newLeafNode(lv_op_2_1, grammarAccess.getBitShiftAccess().getOpLessThanSignLessThanSignKeyword_1_1_0_0());
            	                  
            	            }
            	            if ( state.backtracking==0 ) {

            	              	        if (current==null) {
            	              	            current = createModelElement(grammarAccess.getBitShiftRule());
            	              	        }
            	                     		setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              	    
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1388:8: lv_op_2_2= '>>'
            	            {
            	            lv_op_2_2=(Token)match(input,33,FOLLOW_33_in_ruleBitShift3167); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                      newLeafNode(lv_op_2_2, grammarAccess.getBitShiftAccess().getOpGreaterThanSignGreaterThanSignKeyword_1_1_0_1());
            	                  
            	            }
            	            if ( state.backtracking==0 ) {

            	              	        if (current==null) {
            	              	            current = createModelElement(grammarAccess.getBitShiftRule());
            	              	        }
            	                     		setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              	    
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1403:2: ( (lv_right_3_0= ruleAddition ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1404:1: (lv_right_3_0= ruleAddition )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1404:1: (lv_right_3_0= ruleAddition )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1405:3: lv_right_3_0= ruleAddition
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getBitShiftAccess().getRightAdditionParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleAddition_in_ruleBitShift3204);
            	    lv_right_3_0=ruleAddition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getBitShiftRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_3_0, 
            	              		"Addition");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBitShift"


    // $ANTLR start "entryRuleAddition"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1429:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1430:2: (iv_ruleAddition= ruleAddition EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1431:2: iv_ruleAddition= ruleAddition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAdditionRule()); 
            }
            pushFollow(FOLLOW_ruleAddition_in_entryRuleAddition3242);
            iv_ruleAddition=ruleAddition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAddition; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleAddition3252); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAddition"


    // $ANTLR start "ruleAddition"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1438:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_Multiplication_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1441:28: ( (this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1442:1: (this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1442:1: (this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1443:5: this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition3299);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Multiplication_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1451:1: ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=34 && LA20_0<=35)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1451:2: () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1451:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1452:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getAdditionAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1457:2: ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1458:1: ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1458:1: ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1459:1: (lv_op_2_1= '+' | lv_op_2_2= '-' )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1459:1: (lv_op_2_1= '+' | lv_op_2_2= '-' )
            	    int alt19=2;
            	    int LA19_0 = input.LA(1);

            	    if ( (LA19_0==34) ) {
            	        alt19=1;
            	    }
            	    else if ( (LA19_0==35) ) {
            	        alt19=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 19, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt19) {
            	        case 1 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1460:3: lv_op_2_1= '+'
            	            {
            	            lv_op_2_1=(Token)match(input,34,FOLLOW_34_in_ruleAddition3328); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                      newLeafNode(lv_op_2_1, grammarAccess.getAdditionAccess().getOpPlusSignKeyword_1_1_0_0());
            	                  
            	            }
            	            if ( state.backtracking==0 ) {

            	              	        if (current==null) {
            	              	            current = createModelElement(grammarAccess.getAdditionRule());
            	              	        }
            	                     		setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              	    
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1472:8: lv_op_2_2= '-'
            	            {
            	            lv_op_2_2=(Token)match(input,35,FOLLOW_35_in_ruleAddition3357); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                      newLeafNode(lv_op_2_2, grammarAccess.getAdditionAccess().getOpHyphenMinusKeyword_1_1_0_1());
            	                  
            	            }
            	            if ( state.backtracking==0 ) {

            	              	        if (current==null) {
            	              	            current = createModelElement(grammarAccess.getAdditionRule());
            	              	        }
            	                     		setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              	    
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1487:2: ( (lv_right_3_0= ruleMultiplication ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1488:1: (lv_right_3_0= ruleMultiplication )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1488:1: (lv_right_3_0= ruleMultiplication )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1489:3: lv_right_3_0= ruleMultiplication
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition3394);
            	    lv_right_3_0=ruleMultiplication();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getAdditionRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_3_0, 
            	              		"Multiplication");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAddition"


    // $ANTLR start "entryRuleMultiplication"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1513:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1514:2: (iv_ruleMultiplication= ruleMultiplication EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1515:2: iv_ruleMultiplication= ruleMultiplication EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMultiplicationRule()); 
            }
            pushFollow(FOLLOW_ruleMultiplication_in_entryRuleMultiplication3432);
            iv_ruleMultiplication=ruleMultiplication();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMultiplication; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplication3442); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMultiplication"


    // $ANTLR start "ruleMultiplication"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1522:1: ruleMultiplication returns [EObject current=null] : (this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        Token lv_op_2_3=null;
        EObject this_BitComplement_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1525:28: ( (this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1526:1: (this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1526:1: (this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1527:5: this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getMultiplicationAccess().getBitComplementParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleBitComplement_in_ruleMultiplication3489);
            this_BitComplement_0=ruleBitComplement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_BitComplement_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1535:1: ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>=36 && LA22_0<=38)) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1535:2: () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1535:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1536:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getMultiplicationAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1541:2: ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1542:1: ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1542:1: ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1543:1: (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1543:1: (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' )
            	    int alt21=3;
            	    switch ( input.LA(1) ) {
            	    case 36:
            	        {
            	        alt21=1;
            	        }
            	        break;
            	    case 37:
            	        {
            	        alt21=2;
            	        }
            	        break;
            	    case 38:
            	        {
            	        alt21=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 21, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt21) {
            	        case 1 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1544:3: lv_op_2_1= '/'
            	            {
            	            lv_op_2_1=(Token)match(input,36,FOLLOW_36_in_ruleMultiplication3518); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                      newLeafNode(lv_op_2_1, grammarAccess.getMultiplicationAccess().getOpSolidusKeyword_1_1_0_0());
            	                  
            	            }
            	            if ( state.backtracking==0 ) {

            	              	        if (current==null) {
            	              	            current = createModelElement(grammarAccess.getMultiplicationRule());
            	              	        }
            	                     		setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              	    
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1556:8: lv_op_2_2= '*'
            	            {
            	            lv_op_2_2=(Token)match(input,37,FOLLOW_37_in_ruleMultiplication3547); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                      newLeafNode(lv_op_2_2, grammarAccess.getMultiplicationAccess().getOpAsteriskKeyword_1_1_0_1());
            	                  
            	            }
            	            if ( state.backtracking==0 ) {

            	              	        if (current==null) {
            	              	            current = createModelElement(grammarAccess.getMultiplicationRule());
            	              	        }
            	                     		setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              	    
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1568:8: lv_op_2_3= '%'
            	            {
            	            lv_op_2_3=(Token)match(input,38,FOLLOW_38_in_ruleMultiplication3576); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	                      newLeafNode(lv_op_2_3, grammarAccess.getMultiplicationAccess().getOpPercentSignKeyword_1_1_0_2());
            	                  
            	            }
            	            if ( state.backtracking==0 ) {

            	              	        if (current==null) {
            	              	            current = createModelElement(grammarAccess.getMultiplicationRule());
            	              	        }
            	                     		setWithLastConsumed(current, "op", lv_op_2_3, null);
            	              	    
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1583:2: ( (lv_right_3_0= ruleBitComplement ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1584:1: (lv_right_3_0= ruleBitComplement )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1584:1: (lv_right_3_0= ruleBitComplement )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1585:3: lv_right_3_0= ruleBitComplement
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getMultiplicationAccess().getRightBitComplementParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleBitComplement_in_ruleMultiplication3613);
            	    lv_right_3_0=ruleBitComplement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getMultiplicationRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_3_0, 
            	              		"BitComplement");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMultiplication"


    // $ANTLR start "entryRuleBitComplement"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1609:1: entryRuleBitComplement returns [EObject current=null] : iv_ruleBitComplement= ruleBitComplement EOF ;
    public final EObject entryRuleBitComplement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitComplement = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1610:2: (iv_ruleBitComplement= ruleBitComplement EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1611:2: iv_ruleBitComplement= ruleBitComplement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitComplementRule()); 
            }
            pushFollow(FOLLOW_ruleBitComplement_in_entryRuleBitComplement3651);
            iv_ruleBitComplement=ruleBitComplement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitComplement; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitComplement3661); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBitComplement"


    // $ANTLR start "ruleBitComplement"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1618:1: ruleBitComplement returns [EObject current=null] : ( (otherlv_0= '~' this_Power_1= rulePower () ) | (otherlv_3= '-' this_Power_4= rulePower () ) | this_Power_6= rulePower ) ;
    public final EObject ruleBitComplement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        EObject this_Power_1 = null;

        EObject this_Power_4 = null;

        EObject this_Power_6 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1621:28: ( ( (otherlv_0= '~' this_Power_1= rulePower () ) | (otherlv_3= '-' this_Power_4= rulePower () ) | this_Power_6= rulePower ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1622:1: ( (otherlv_0= '~' this_Power_1= rulePower () ) | (otherlv_3= '-' this_Power_4= rulePower () ) | this_Power_6= rulePower )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1622:1: ( (otherlv_0= '~' this_Power_1= rulePower () ) | (otherlv_3= '-' this_Power_4= rulePower () ) | this_Power_6= rulePower )
            int alt23=3;
            switch ( input.LA(1) ) {
            case 39:
                {
                alt23=1;
                }
                break;
            case 35:
                {
                alt23=2;
                }
                break;
            case RULE_INT:
            case RULE_ID:
            case 21:
                {
                alt23=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1622:2: (otherlv_0= '~' this_Power_1= rulePower () )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1622:2: (otherlv_0= '~' this_Power_1= rulePower () )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1622:4: otherlv_0= '~' this_Power_1= rulePower ()
                    {
                    otherlv_0=(Token)match(input,39,FOLLOW_39_in_ruleBitComplement3699); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_0, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBitComplementAccess().getPowerParserRuleCall_0_1()); 
                          
                    }
                    pushFollow(FOLLOW_rulePower_in_ruleBitComplement3721);
                    this_Power_1=rulePower();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Power_1; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1635:1: ()
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1636:5: 
                    {
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndSet(
                                  grammarAccess.getBitComplementAccess().getBitComplementValueAction_0_2(),
                                  current);
                          
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1642:6: (otherlv_3= '-' this_Power_4= rulePower () )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1642:6: (otherlv_3= '-' this_Power_4= rulePower () )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1642:8: otherlv_3= '-' this_Power_4= rulePower ()
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_35_in_ruleBitComplement3749); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBitComplementAccess().getPowerParserRuleCall_1_1()); 
                          
                    }
                    pushFollow(FOLLOW_rulePower_in_ruleBitComplement3771);
                    this_Power_4=rulePower();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Power_4; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1655:1: ()
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1656:5: 
                    {
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndSet(
                                  grammarAccess.getBitComplementAccess().getUnaryMinusValueAction_1_2(),
                                  current);
                          
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1663:5: this_Power_6= rulePower
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBitComplementAccess().getPowerParserRuleCall_2()); 
                          
                    }
                    pushFollow(FOLLOW_rulePower_in_ruleBitComplement3808);
                    this_Power_6=rulePower();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Power_6; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBitComplement"


    // $ANTLR start "entryRulePower"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1679:1: entryRulePower returns [EObject current=null] : iv_rulePower= rulePower EOF ;
    public final EObject entryRulePower() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePower = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1680:2: (iv_rulePower= rulePower EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1681:2: iv_rulePower= rulePower EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPowerRule()); 
            }
            pushFollow(FOLLOW_rulePower_in_entryRulePower3843);
            iv_rulePower=rulePower();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePower; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePower3853); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePower"


    // $ANTLR start "rulePower"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1688:1: rulePower returns [EObject current=null] : (this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )* ) ;
    public final EObject rulePower() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_Primary_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1691:28: ( (this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1692:1: (this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1692:1: (this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1693:5: this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getPowerAccess().getPrimaryParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_rulePrimary_in_rulePower3900);
            this_Primary_0=rulePrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Primary_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1701:1: ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==40) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1701:2: () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1701:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1702:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getPowerAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1707:2: ( (lv_op_2_0= '**' ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1708:1: (lv_op_2_0= '**' )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1708:1: (lv_op_2_0= '**' )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1709:3: lv_op_2_0= '**'
            	    {
            	    lv_op_2_0=(Token)match(input,40,FOLLOW_40_in_rulePower3927); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	              newLeafNode(lv_op_2_0, grammarAccess.getPowerAccess().getOpAsteriskAsteriskKeyword_1_1_0());
            	          
            	    }
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElement(grammarAccess.getPowerRule());
            	      	        }
            	             		setWithLastConsumed(current, "op", lv_op_2_0, "**");
            	      	    
            	    }

            	    }


            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1722:2: ( (lv_right_3_0= rulePrimary ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1723:1: (lv_right_3_0= rulePrimary )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1723:1: (lv_right_3_0= rulePrimary )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1724:3: lv_right_3_0= rulePrimary
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getPowerAccess().getRightPrimaryParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_rulePrimary_in_rulePower3961);
            	    lv_right_3_0=rulePrimary();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getPowerRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_3_0, 
            	              		"Primary");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePower"


    // $ANTLR start "entryRulePrimary"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1748:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1749:2: (iv_rulePrimary= rulePrimary EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1750:2: iv_rulePrimary= rulePrimary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPrimaryRule()); 
            }
            pushFollow(FOLLOW_rulePrimary_in_entryRulePrimary3999);
            iv_rulePrimary=rulePrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePrimary; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimary4009); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimary"


    // $ANTLR start "rulePrimary"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1757:1: rulePrimary returns [EObject current=null] : (this_Peek_0= rulePeek | this_VarAccess_1= ruleVarAccess | this_Constant_2= ruleConstant | ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' ) ) | (otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')' ) ) ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject this_Peek_0 = null;

        EObject this_VarAccess_1 = null;

        EObject this_Constant_2 = null;

        EObject this_BitOr_4 = null;

        EObject this_WrapBoolExpr_7 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1760:28: ( (this_Peek_0= rulePeek | this_VarAccess_1= ruleVarAccess | this_Constant_2= ruleConstant | ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' ) ) | (otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')' ) ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1761:1: (this_Peek_0= rulePeek | this_VarAccess_1= ruleVarAccess | this_Constant_2= ruleConstant | ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' ) ) | (otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')' ) ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1761:1: (this_Peek_0= rulePeek | this_VarAccess_1= ruleVarAccess | this_Constant_2= ruleConstant | ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' ) ) | (otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')' ) ) )
            int alt26=4;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1762:5: this_Peek_0= rulePeek
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryAccess().getPeekParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_rulePeek_in_rulePrimary4056);
                    this_Peek_0=rulePeek();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Peek_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1772:5: this_VarAccess_1= ruleVarAccess
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryAccess().getVarAccessParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleVarAccess_in_rulePrimary4083);
                    this_VarAccess_1=ruleVarAccess();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_VarAccess_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1782:5: this_Constant_2= ruleConstant
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryAccess().getConstantParserRuleCall_2()); 
                          
                    }
                    pushFollow(FOLLOW_ruleConstant_in_rulePrimary4110);
                    this_Constant_2=ruleConstant();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Constant_2; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1791:6: ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' ) ) | (otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')' ) )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1791:6: ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' ) ) | (otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')' ) )
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==21) ) {
                        int LA25_1 = input.LA(2);

                        if ( (synpred1_InternalGal()) ) {
                            alt25=1;
                        }
                        else if ( (true) ) {
                            alt25=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 25, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 25, 0, input);

                        throw nvae;
                    }
                    switch (alt25) {
                        case 1 :
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1791:7: ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' ) )
                            {
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1791:7: ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' ) )
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1791:8: ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' )
                            {
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1793:5: (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' )
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1793:7: otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')'
                            {
                            otherlv_3=(Token)match(input,21,FOLLOW_21_in_rulePrimary4145); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_3, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_0_0_0());
                                  
                            }
                            if ( state.backtracking==0 ) {
                               
                                      newCompositeNode(grammarAccess.getPrimaryAccess().getBitOrParserRuleCall_3_0_0_1()); 
                                  
                            }
                            pushFollow(FOLLOW_ruleBitOr_in_rulePrimary4167);
                            this_BitOr_4=ruleBitOr();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {
                               
                                      current = this_BitOr_4; 
                                      afterParserOrEnumRuleCall();
                                  
                            }
                            otherlv_5=(Token)match(input,22,FOLLOW_22_in_rulePrimary4178); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_5, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_3_0_0_2());
                                  
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1811:6: (otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')' )
                            {
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1811:6: (otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')' )
                            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1811:8: otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')'
                            {
                            otherlv_6=(Token)match(input,21,FOLLOW_21_in_rulePrimary4199); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_6, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_3_1_0());
                                  
                            }
                            if ( state.backtracking==0 ) {
                               
                                      newCompositeNode(grammarAccess.getPrimaryAccess().getWrapBoolExprParserRuleCall_3_1_1()); 
                                  
                            }
                            pushFollow(FOLLOW_ruleWrapBoolExpr_in_rulePrimary4221);
                            this_WrapBoolExpr_7=ruleWrapBoolExpr();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {
                               
                                      current = this_WrapBoolExpr_7; 
                                      afterParserOrEnumRuleCall();
                                  
                            }
                            otherlv_8=(Token)match(input,22,FOLLOW_22_in_rulePrimary4232); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_8, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_3_1_2());
                                  
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimary"


    // $ANTLR start "entryRuleWrapBoolExpr"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1836:1: entryRuleWrapBoolExpr returns [EObject current=null] : iv_ruleWrapBoolExpr= ruleWrapBoolExpr EOF ;
    public final EObject entryRuleWrapBoolExpr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWrapBoolExpr = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1837:2: (iv_ruleWrapBoolExpr= ruleWrapBoolExpr EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1838:2: iv_ruleWrapBoolExpr= ruleWrapBoolExpr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWrapBoolExprRule()); 
            }
            pushFollow(FOLLOW_ruleWrapBoolExpr_in_entryRuleWrapBoolExpr4270);
            iv_ruleWrapBoolExpr=ruleWrapBoolExpr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWrapBoolExpr; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleWrapBoolExpr4280); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWrapBoolExpr"


    // $ANTLR start "ruleWrapBoolExpr"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1845:1: ruleWrapBoolExpr returns [EObject current=null] : ( (lv_value_0_0= ruleOr ) ) ;
    public final EObject ruleWrapBoolExpr() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1848:28: ( ( (lv_value_0_0= ruleOr ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1849:1: ( (lv_value_0_0= ruleOr ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1849:1: ( (lv_value_0_0= ruleOr ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1850:1: (lv_value_0_0= ruleOr )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1850:1: (lv_value_0_0= ruleOr )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1851:3: lv_value_0_0= ruleOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getWrapBoolExprAccess().getValueOrParserRuleCall_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleOr_in_ruleWrapBoolExpr4325);
            lv_value_0_0=ruleOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getWrapBoolExprRule());
              	        }
                     		set(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"Or");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWrapBoolExpr"


    // $ANTLR start "entryRuleConstant"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1875:1: entryRuleConstant returns [EObject current=null] : iv_ruleConstant= ruleConstant EOF ;
    public final EObject entryRuleConstant() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstant = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1876:2: (iv_ruleConstant= ruleConstant EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1877:2: iv_ruleConstant= ruleConstant EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getConstantRule()); 
            }
            pushFollow(FOLLOW_ruleConstant_in_entryRuleConstant4360);
            iv_ruleConstant=ruleConstant();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleConstant; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstant4370); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConstant"


    // $ANTLR start "ruleConstant"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1884:1: ruleConstant returns [EObject current=null] : ( (lv_value_0_0= RULE_INT ) ) ;
    public final EObject ruleConstant() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1887:28: ( ( (lv_value_0_0= RULE_INT ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1888:1: ( (lv_value_0_0= RULE_INT ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1888:1: ( (lv_value_0_0= RULE_INT ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1889:1: (lv_value_0_0= RULE_INT )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1889:1: (lv_value_0_0= RULE_INT )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1890:3: lv_value_0_0= RULE_INT
            {
            lv_value_0_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleConstant4411); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_value_0_0, grammarAccess.getConstantAccess().getValueINTTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getConstantRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"value",
                      		lv_value_0_0, 
                      		"INT");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConstant"


    // $ANTLR start "entryRulePeek"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1914:1: entryRulePeek returns [EObject current=null] : iv_rulePeek= rulePeek EOF ;
    public final EObject entryRulePeek() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePeek = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1915:2: (iv_rulePeek= rulePeek EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1916:2: iv_rulePeek= rulePeek EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPeekRule()); 
            }
            pushFollow(FOLLOW_rulePeek_in_entryRulePeek4451);
            iv_rulePeek=rulePeek();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePeek; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePeek4461); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePeek"


    // $ANTLR start "rulePeek"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1923:1: rulePeek returns [EObject current=null] : ( ( ( ruleQualifiedName ) ) otherlv_1= '.peek' otherlv_2= '(' otherlv_3= ')' ) ;
    public final EObject rulePeek() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1926:28: ( ( ( ( ruleQualifiedName ) ) otherlv_1= '.peek' otherlv_2= '(' otherlv_3= ')' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1927:1: ( ( ( ruleQualifiedName ) ) otherlv_1= '.peek' otherlv_2= '(' otherlv_3= ')' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1927:1: ( ( ( ruleQualifiedName ) ) otherlv_1= '.peek' otherlv_2= '(' otherlv_3= ')' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1927:2: ( ( ruleQualifiedName ) ) otherlv_1= '.peek' otherlv_2= '(' otherlv_3= ')'
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1927:2: ( ( ruleQualifiedName ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1928:1: ( ruleQualifiedName )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1928:1: ( ruleQualifiedName )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1929:3: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getPeekRule());
              	        }
                      
            }
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getPeekAccess().getListListCrossReference_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_rulePeek4509);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_1=(Token)match(input,41,FOLLOW_41_in_rulePeek4521); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getPeekAccess().getPeekKeyword_1());
                  
            }
            otherlv_2=(Token)match(input,21,FOLLOW_21_in_rulePeek4533); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getPeekAccess().getLeftParenthesisKeyword_2());
                  
            }
            otherlv_3=(Token)match(input,22,FOLLOW_22_in_rulePeek4545); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getPeekAccess().getRightParenthesisKeyword_3());
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePeek"


    // $ANTLR start "entryRuleOr"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1962:1: entryRuleOr returns [EObject current=null] : iv_ruleOr= ruleOr EOF ;
    public final EObject entryRuleOr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOr = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1963:2: (iv_ruleOr= ruleOr EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1964:2: iv_ruleOr= ruleOr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrRule()); 
            }
            pushFollow(FOLLOW_ruleOr_in_entryRuleOr4581);
            iv_ruleOr=ruleOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOr; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOr4591); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOr"


    // $ANTLR start "ruleOr"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1971:1: ruleOr returns [EObject current=null] : (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* ) ;
    public final EObject ruleOr() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_And_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1974:28: ( (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1975:1: (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1975:1: (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1976:5: this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getOrAccess().getAndParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleAnd_in_ruleOr4638);
            this_And_0=ruleAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_And_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1984:1: ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==42) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1984:2: () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1984:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1985:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getOrAccess().getOrLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    otherlv_2=(Token)match(input,42,FOLLOW_42_in_ruleOr4659); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_2, grammarAccess.getOrAccess().getVerticalLineVerticalLineKeyword_1_1());
            	          
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1994:1: ( (lv_right_3_0= ruleAnd ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1995:1: (lv_right_3_0= ruleAnd )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1995:1: (lv_right_3_0= ruleAnd )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1996:3: lv_right_3_0= ruleAnd
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleAnd_in_ruleOr4680);
            	    lv_right_3_0=ruleAnd();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getOrRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_3_0, 
            	              		"And");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOr"


    // $ANTLR start "entryRuleAnd"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2020:1: entryRuleAnd returns [EObject current=null] : iv_ruleAnd= ruleAnd EOF ;
    public final EObject entryRuleAnd() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnd = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2021:2: (iv_ruleAnd= ruleAnd EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2022:2: iv_ruleAnd= ruleAnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAndRule()); 
            }
            pushFollow(FOLLOW_ruleAnd_in_entryRuleAnd4718);
            iv_ruleAnd=ruleAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnd; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleAnd4728); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnd"


    // $ANTLR start "ruleAnd"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2029:1: ruleAnd returns [EObject current=null] : (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* ) ;
    public final EObject ruleAnd() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Not_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2032:28: ( (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2033:1: (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2033:1: (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2034:5: this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getAndAccess().getNotParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleNot_in_ruleAnd4775);
            this_Not_0=ruleNot();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Not_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2042:1: ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==43) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2042:2: () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2042:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2043:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getAndAccess().getAndLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    otherlv_2=(Token)match(input,43,FOLLOW_43_in_ruleAnd4796); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_2, grammarAccess.getAndAccess().getAmpersandAmpersandKeyword_1_1());
            	          
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2052:1: ( (lv_right_3_0= ruleNot ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2053:1: (lv_right_3_0= ruleNot )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2053:1: (lv_right_3_0= ruleNot )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2054:3: lv_right_3_0= ruleNot
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getAndAccess().getRightNotParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleNot_in_ruleAnd4817);
            	    lv_right_3_0=ruleNot();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getAndRule());
            	      	        }
            	             		set(
            	             			current, 
            	             			"right",
            	              		lv_right_3_0, 
            	              		"Not");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnd"


    // $ANTLR start "entryRuleNot"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2078:1: entryRuleNot returns [EObject current=null] : iv_ruleNot= ruleNot EOF ;
    public final EObject entryRuleNot() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNot = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2079:2: (iv_ruleNot= ruleNot EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2080:2: iv_ruleNot= ruleNot EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNotRule()); 
            }
            pushFollow(FOLLOW_ruleNot_in_entryRuleNot4855);
            iv_ruleNot=ruleNot();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNot; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleNot4865); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNot"


    // $ANTLR start "ruleNot"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2087:1: ruleNot returns [EObject current=null] : ( (otherlv_0= '!' this_PrimaryBool_1= rulePrimaryBool () ) | this_PrimaryBool_3= rulePrimaryBool ) ;
    public final EObject ruleNot() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject this_PrimaryBool_1 = null;

        EObject this_PrimaryBool_3 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2090:28: ( ( (otherlv_0= '!' this_PrimaryBool_1= rulePrimaryBool () ) | this_PrimaryBool_3= rulePrimaryBool ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2091:1: ( (otherlv_0= '!' this_PrimaryBool_1= rulePrimaryBool () ) | this_PrimaryBool_3= rulePrimaryBool )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2091:1: ( (otherlv_0= '!' this_PrimaryBool_1= rulePrimaryBool () ) | this_PrimaryBool_3= rulePrimaryBool )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==44) ) {
                alt29=1;
            }
            else if ( ((LA29_0>=RULE_INT && LA29_0<=RULE_ID)||LA29_0==21||LA29_0==35||LA29_0==39||(LA29_0>=45 && LA29_0<=46)) ) {
                alt29=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2091:2: (otherlv_0= '!' this_PrimaryBool_1= rulePrimaryBool () )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2091:2: (otherlv_0= '!' this_PrimaryBool_1= rulePrimaryBool () )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2091:4: otherlv_0= '!' this_PrimaryBool_1= rulePrimaryBool ()
                    {
                    otherlv_0=(Token)match(input,44,FOLLOW_44_in_ruleNot4903); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_0, grammarAccess.getNotAccess().getExclamationMarkKeyword_0_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getNotAccess().getPrimaryBoolParserRuleCall_0_1()); 
                          
                    }
                    pushFollow(FOLLOW_rulePrimaryBool_in_ruleNot4925);
                    this_PrimaryBool_1=rulePrimaryBool();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_PrimaryBool_1; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2104:1: ()
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2105:5: 
                    {
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndSet(
                                  grammarAccess.getNotAccess().getNotValueAction_0_2(),
                                  current);
                          
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2112:5: this_PrimaryBool_3= rulePrimaryBool
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getNotAccess().getPrimaryBoolParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_rulePrimaryBool_in_ruleNot4962);
                    this_PrimaryBool_3=rulePrimaryBool();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_PrimaryBool_3; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNot"


    // $ANTLR start "entryRulePrimaryBool"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2128:1: entryRulePrimaryBool returns [EObject current=null] : iv_rulePrimaryBool= rulePrimaryBool EOF ;
    public final EObject entryRulePrimaryBool() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryBool = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2129:2: (iv_rulePrimaryBool= rulePrimaryBool EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2130:2: iv_rulePrimaryBool= rulePrimaryBool EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPrimaryBoolRule()); 
            }
            pushFollow(FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool4997);
            iv_rulePrimaryBool=rulePrimaryBool();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePrimaryBool; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryBool5007); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimaryBool"


    // $ANTLR start "rulePrimaryBool"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2137:1: rulePrimaryBool returns [EObject current=null] : (this_True_0= ruleTrue | this_False_1= ruleFalse | ( ( ruleComparison )=>this_Comparison_2= ruleComparison ) | (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' ) ) ;
    public final EObject rulePrimaryBool() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject this_True_0 = null;

        EObject this_False_1 = null;

        EObject this_Comparison_2 = null;

        EObject this_Or_4 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2140:28: ( (this_True_0= ruleTrue | this_False_1= ruleFalse | ( ( ruleComparison )=>this_Comparison_2= ruleComparison ) | (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2141:1: (this_True_0= ruleTrue | this_False_1= ruleFalse | ( ( ruleComparison )=>this_Comparison_2= ruleComparison ) | (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2141:1: (this_True_0= ruleTrue | this_False_1= ruleFalse | ( ( ruleComparison )=>this_Comparison_2= ruleComparison ) | (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' ) )
            int alt30=4;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==45) ) {
                alt30=1;
            }
            else if ( (LA30_0==46) ) {
                alt30=2;
            }
            else if ( (LA30_0==39) && (synpred2_InternalGal())) {
                alt30=3;
            }
            else if ( (LA30_0==35) && (synpred2_InternalGal())) {
                alt30=3;
            }
            else if ( (LA30_0==RULE_ID) && (synpred2_InternalGal())) {
                alt30=3;
            }
            else if ( (LA30_0==RULE_INT) && (synpred2_InternalGal())) {
                alt30=3;
            }
            else if ( (LA30_0==21) ) {
                int LA30_7 = input.LA(2);

                if ( (synpred2_InternalGal()) ) {
                    alt30=3;
                }
                else if ( (true) ) {
                    alt30=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 7, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2142:5: this_True_0= ruleTrue
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryBoolAccess().getTrueParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_ruleTrue_in_rulePrimaryBool5054);
                    this_True_0=ruleTrue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_True_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2152:5: this_False_1= ruleFalse
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryBoolAccess().getFalseParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleFalse_in_rulePrimaryBool5081);
                    this_False_1=ruleFalse();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_False_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2161:6: ( ( ruleComparison )=>this_Comparison_2= ruleComparison )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2161:6: ( ( ruleComparison )=>this_Comparison_2= ruleComparison )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2161:7: ( ruleComparison )=>this_Comparison_2= ruleComparison
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryBoolAccess().getComparisonParserRuleCall_2()); 
                          
                    }
                    pushFollow(FOLLOW_ruleComparison_in_rulePrimaryBool5114);
                    this_Comparison_2=ruleComparison();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Comparison_2; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2171:6: (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2171:6: (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2171:8: otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,21,FOLLOW_21_in_rulePrimaryBool5133); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryBoolAccess().getOrParserRuleCall_3_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleOr_in_rulePrimaryBool5155);
                    this_Or_4=ruleOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Or_4; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    otherlv_5=(Token)match(input,22,FOLLOW_22_in_rulePrimaryBool5166); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_5, grammarAccess.getPrimaryBoolAccess().getRightParenthesisKeyword_3_2());
                          
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimaryBool"


    // $ANTLR start "entryRuleComparison"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2196:1: entryRuleComparison returns [EObject current=null] : iv_ruleComparison= ruleComparison EOF ;
    public final EObject entryRuleComparison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparison = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2197:2: (iv_ruleComparison= ruleComparison EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2198:2: iv_ruleComparison= ruleComparison EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getComparisonRule()); 
            }
            pushFollow(FOLLOW_ruleComparison_in_entryRuleComparison5203);
            iv_ruleComparison=ruleComparison();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleComparison; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleComparison5213); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleComparison"


    // $ANTLR start "ruleComparison"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2205:1: ruleComparison returns [EObject current=null] : ( ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) ) ) ;
    public final EObject ruleComparison() throws RecognitionException {
        EObject current = null;

        EObject lv_left_0_0 = null;

        Enumerator lv_operator_1_0 = null;

        EObject lv_right_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2208:28: ( ( ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2209:1: ( ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2209:1: ( ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2209:2: ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2209:2: ( (lv_left_0_0= ruleBitOr ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2210:1: (lv_left_0_0= ruleBitOr )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2210:1: (lv_left_0_0= ruleBitOr )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2211:3: lv_left_0_0= ruleBitOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getComparisonAccess().getLeftBitOrParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBitOr_in_ruleComparison5259);
            lv_left_0_0=ruleBitOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getComparisonRule());
              	        }
                     		set(
                     			current, 
                     			"left",
                      		lv_left_0_0, 
                      		"BitOr");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2227:2: ( (lv_operator_1_0= ruleComparisonOperators ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2228:1: (lv_operator_1_0= ruleComparisonOperators )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2228:1: (lv_operator_1_0= ruleComparisonOperators )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2229:3: lv_operator_1_0= ruleComparisonOperators
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getComparisonAccess().getOperatorComparisonOperatorsEnumRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleComparisonOperators_in_ruleComparison5280);
            lv_operator_1_0=ruleComparisonOperators();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getComparisonRule());
              	        }
                     		set(
                     			current, 
                     			"operator",
                      		lv_operator_1_0, 
                      		"ComparisonOperators");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2245:2: ( (lv_right_2_0= ruleBitOr ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2246:1: (lv_right_2_0= ruleBitOr )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2246:1: (lv_right_2_0= ruleBitOr )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2247:3: lv_right_2_0= ruleBitOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getComparisonAccess().getRightBitOrParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBitOr_in_ruleComparison5301);
            lv_right_2_0=ruleBitOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getComparisonRule());
              	        }
                     		set(
                     			current, 
                     			"right",
                      		lv_right_2_0, 
                      		"BitOr");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComparison"


    // $ANTLR start "entryRuleTrue"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2271:1: entryRuleTrue returns [EObject current=null] : iv_ruleTrue= ruleTrue EOF ;
    public final EObject entryRuleTrue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTrue = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2272:2: (iv_ruleTrue= ruleTrue EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2273:2: iv_ruleTrue= ruleTrue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTrueRule()); 
            }
            pushFollow(FOLLOW_ruleTrue_in_entryRuleTrue5337);
            iv_ruleTrue=ruleTrue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTrue; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleTrue5347); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTrue"


    // $ANTLR start "ruleTrue"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2280:1: ruleTrue returns [EObject current=null] : ( (lv_value_0_0= 'True' ) ) ;
    public final EObject ruleTrue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2283:28: ( ( (lv_value_0_0= 'True' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2284:1: ( (lv_value_0_0= 'True' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2284:1: ( (lv_value_0_0= 'True' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2285:1: (lv_value_0_0= 'True' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2285:1: (lv_value_0_0= 'True' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2286:3: lv_value_0_0= 'True'
            {
            lv_value_0_0=(Token)match(input,45,FOLLOW_45_in_ruleTrue5389); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      newLeafNode(lv_value_0_0, grammarAccess.getTrueAccess().getValueTrueKeyword_0());
                  
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getTrueRule());
              	        }
                     		setWithLastConsumed(current, "value", lv_value_0_0, "True");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTrue"


    // $ANTLR start "entryRuleFalse"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2307:1: entryRuleFalse returns [EObject current=null] : iv_ruleFalse= ruleFalse EOF ;
    public final EObject entryRuleFalse() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFalse = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2308:2: (iv_ruleFalse= ruleFalse EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2309:2: iv_ruleFalse= ruleFalse EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFalseRule()); 
            }
            pushFollow(FOLLOW_ruleFalse_in_entryRuleFalse5437);
            iv_ruleFalse=ruleFalse();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFalse; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleFalse5447); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFalse"


    // $ANTLR start "ruleFalse"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2316:1: ruleFalse returns [EObject current=null] : ( (lv_value_0_0= 'False' ) ) ;
    public final EObject ruleFalse() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2319:28: ( ( (lv_value_0_0= 'False' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2320:1: ( (lv_value_0_0= 'False' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2320:1: ( (lv_value_0_0= 'False' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2321:1: (lv_value_0_0= 'False' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2321:1: (lv_value_0_0= 'False' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2322:3: lv_value_0_0= 'False'
            {
            lv_value_0_0=(Token)match(input,46,FOLLOW_46_in_ruleFalse5489); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      newLeafNode(lv_value_0_0, grammarAccess.getFalseAccess().getValueFalseKeyword_0());
                  
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getFalseRule());
              	        }
                     		setWithLastConsumed(current, "value", lv_value_0_0, "False");
              	    
            }

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFalse"


    // $ANTLR start "entryRuleQualifiedName"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2343:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2344:2: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2345:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedNameRule()); 
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName5538);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifiedName.getText(); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedName5549); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2352:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' (this_INT_2= RULE_INT | this_ID_3= RULE_ID ) )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_ID_3=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2355:28: ( (this_ID_0= RULE_ID (kw= '.' (this_INT_2= RULE_INT | this_ID_3= RULE_ID ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2356:1: (this_ID_0= RULE_ID (kw= '.' (this_INT_2= RULE_INT | this_ID_3= RULE_ID ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2356:1: (this_ID_0= RULE_ID (kw= '.' (this_INT_2= RULE_INT | this_ID_3= RULE_ID ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2356:6: this_ID_0= RULE_ID (kw= '.' (this_INT_2= RULE_INT | this_ID_3= RULE_ID ) )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleQualifiedName5589); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_ID_0);
                  
            }
            if ( state.backtracking==0 ) {
               
                  newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
                  
            }
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2363:1: (kw= '.' (this_INT_2= RULE_INT | this_ID_3= RULE_ID ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==47) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2364:2: kw= '.' (this_INT_2= RULE_INT | this_ID_3= RULE_ID )
            	    {
            	    kw=(Token)match(input,47,FOLLOW_47_in_ruleQualifiedName5608); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	              current.merge(kw);
            	              newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
            	          
            	    }
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2369:1: (this_INT_2= RULE_INT | this_ID_3= RULE_ID )
            	    int alt31=2;
            	    int LA31_0 = input.LA(1);

            	    if ( (LA31_0==RULE_INT) ) {
            	        alt31=1;
            	    }
            	    else if ( (LA31_0==RULE_ID) ) {
            	        alt31=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 31, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt31) {
            	        case 1 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2369:6: this_INT_2= RULE_INT
            	            {
            	            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleQualifiedName5624); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              		current.merge(this_INT_2);
            	                  
            	            }
            	            if ( state.backtracking==0 ) {
            	               
            	                  newLeafNode(this_INT_2, grammarAccess.getQualifiedNameAccess().getINTTerminalRuleCall_1_1_0()); 
            	                  
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2377:10: this_ID_3= RULE_ID
            	            {
            	            this_ID_3=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleQualifiedName5650); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              		current.merge(this_ID_3);
            	                  
            	            }
            	            if ( state.backtracking==0 ) {
            	               
            	                  newLeafNode(this_ID_3, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1_1()); 
            	                  
            	            }

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleInteger"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2392:1: entryRuleInteger returns [String current=null] : iv_ruleInteger= ruleInteger EOF ;
    public final String entryRuleInteger() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleInteger = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2393:2: (iv_ruleInteger= ruleInteger EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2394:2: iv_ruleInteger= ruleInteger EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIntegerRule()); 
            }
            pushFollow(FOLLOW_ruleInteger_in_entryRuleInteger5699);
            iv_ruleInteger=ruleInteger();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleInteger.getText(); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleInteger5710); if (state.failed) return current;

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInteger"


    // $ANTLR start "ruleInteger"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2401:1: ruleInteger returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleInteger() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2404:28: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2405:1: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2405:1: ( (kw= '-' )? this_INT_1= RULE_INT )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2405:2: (kw= '-' )? this_INT_1= RULE_INT
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2405:2: (kw= '-' )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==35) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2406:2: kw= '-'
                    {
                    kw=(Token)match(input,35,FOLLOW_35_in_ruleInteger5749); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getIntegerAccess().getHyphenMinusKeyword_0()); 
                          
                    }

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleInteger5766); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_INT_1);
                  
            }
            if ( state.backtracking==0 ) {
               
                  newLeafNode(this_INT_1, grammarAccess.getIntegerAccess().getINTTerminalRuleCall_1()); 
                  
            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInteger"


    // $ANTLR start "ruleComparisonOperators"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2426:1: ruleComparisonOperators returns [Enumerator current=null] : ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) | (enumLiteral_5= '!=' ) ) ;
    public final Enumerator ruleComparisonOperators() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;

         enterRule(); 
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2428:28: ( ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) | (enumLiteral_5= '!=' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2429:1: ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) | (enumLiteral_5= '!=' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2429:1: ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) | (enumLiteral_5= '!=' ) )
            int alt34=6;
            switch ( input.LA(1) ) {
            case 48:
                {
                alt34=1;
                }
                break;
            case 49:
                {
                alt34=2;
                }
                break;
            case 50:
                {
                alt34=3;
                }
                break;
            case 51:
                {
                alt34=4;
                }
                break;
            case 52:
                {
                alt34=5;
                }
                break;
            case 53:
                {
                alt34=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }

            switch (alt34) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2429:2: (enumLiteral_0= '>' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2429:2: (enumLiteral_0= '>' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2429:4: enumLiteral_0= '>'
                    {
                    enumLiteral_0=(Token)match(input,48,FOLLOW_48_in_ruleComparisonOperators5825); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getGTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_0, grammarAccess.getComparisonOperatorsAccess().getGTEnumLiteralDeclaration_0()); 
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2435:6: (enumLiteral_1= '<' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2435:6: (enumLiteral_1= '<' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2435:8: enumLiteral_1= '<'
                    {
                    enumLiteral_1=(Token)match(input,49,FOLLOW_49_in_ruleComparisonOperators5842); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getLTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_1, grammarAccess.getComparisonOperatorsAccess().getLTEnumLiteralDeclaration_1()); 
                          
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2441:6: (enumLiteral_2= '>=' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2441:6: (enumLiteral_2= '>=' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2441:8: enumLiteral_2= '>='
                    {
                    enumLiteral_2=(Token)match(input,50,FOLLOW_50_in_ruleComparisonOperators5859); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getGEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_2, grammarAccess.getComparisonOperatorsAccess().getGEEnumLiteralDeclaration_2()); 
                          
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2447:6: (enumLiteral_3= '<=' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2447:6: (enumLiteral_3= '<=' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2447:8: enumLiteral_3= '<='
                    {
                    enumLiteral_3=(Token)match(input,51,FOLLOW_51_in_ruleComparisonOperators5876); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getLEEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_3, grammarAccess.getComparisonOperatorsAccess().getLEEnumLiteralDeclaration_3()); 
                          
                    }

                    }


                    }
                    break;
                case 5 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2453:6: (enumLiteral_4= '==' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2453:6: (enumLiteral_4= '==' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2453:8: enumLiteral_4= '=='
                    {
                    enumLiteral_4=(Token)match(input,52,FOLLOW_52_in_ruleComparisonOperators5893); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getEQEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_4, grammarAccess.getComparisonOperatorsAccess().getEQEnumLiteralDeclaration_4()); 
                          
                    }

                    }


                    }
                    break;
                case 6 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2459:6: (enumLiteral_5= '!=' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2459:6: (enumLiteral_5= '!=' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2459:8: enumLiteral_5= '!='
                    {
                    enumLiteral_5=(Token)match(input,53,FOLLOW_53_in_ruleComparisonOperators5910); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getNEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_5, grammarAccess.getComparisonOperatorsAccess().getNEEnumLiteralDeclaration_5()); 
                          
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule(); 
            }
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComparisonOperators"

    // $ANTLR start synpred1_InternalGal
    public final void synpred1_InternalGal_fragment() throws RecognitionException {   
        // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1791:8: ( ( '(' ruleBitOr ')' ) )
        // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1791:9: ( '(' ruleBitOr ')' )
        {
        // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1791:9: ( '(' ruleBitOr ')' )
        // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1791:11: '(' ruleBitOr ')'
        {
        match(input,21,FOLLOW_21_in_synpred1_InternalGal4129); if (state.failed) return ;
        pushFollow(FOLLOW_ruleBitOr_in_synpred1_InternalGal4133);
        ruleBitOr();

        state._fsp--;
        if (state.failed) return ;
        match(input,22,FOLLOW_22_in_synpred1_InternalGal4135); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalGal

    // $ANTLR start synpred2_InternalGal
    public final void synpred2_InternalGal_fragment() throws RecognitionException {   
        // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2161:7: ( ruleComparison )
        // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:2161:9: ruleComparison
        {
        pushFollow(FOLLOW_ruleComparison_in_synpred2_InternalGal5098);
        ruleComparison();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalGal

    // Delegated rules

    public final boolean synpred2_InternalGal() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalGal_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalGal() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalGal_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA12 dfa12 = new DFA12(this);
    protected DFA13 dfa13 = new DFA13(this);
    protected DFA26 dfa26 = new DFA26(this);
    static final String DFA12_eotS =
        "\10\uffff";
    static final String DFA12_eofS =
        "\10\uffff";
    static final String DFA12_minS =
        "\1\5\1\17\1\4\3\uffff\2\17";
    static final String DFA12_maxS =
        "\1\5\1\57\1\5\3\uffff\2\57";
    static final String DFA12_acceptS =
        "\3\uffff\1\3\1\2\1\1\2\uffff";
    static final String DFA12_specialS =
        "\10\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\1",
            "\1\5\3\uffff\1\5\7\uffff\1\4\1\3\22\uffff\1\2",
            "\1\6\1\7",
            "",
            "",
            "",
            "\1\5\3\uffff\1\5\7\uffff\1\4\1\3\22\uffff\1\2",
            "\1\5\3\uffff\1\5\7\uffff\1\4\1\3\22\uffff\1\2"
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "776:1: (this_Assignment_0= ruleAssignment | this_Push_1= rulePush | this_Pop_2= rulePop )";
        }
    }
    static final String DFA13_eotS =
        "\7\uffff";
    static final String DFA13_eofS =
        "\1\uffff\1\4\3\uffff\2\4";
    static final String DFA13_minS =
        "\1\5\1\17\1\4\2\uffff\2\17";
    static final String DFA13_maxS =
        "\1\5\1\65\1\5\2\uffff\2\65";
    static final String DFA13_acceptS =
        "\3\uffff\1\1\1\2\2\uffff";
    static final String DFA13_specialS =
        "\7\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\1",
            "\2\4\2\uffff\1\3\1\4\1\uffff\1\4\6\uffff\12\4\1\uffff\1\4\1"+
            "\uffff\2\4\3\uffff\1\2\6\4",
            "\1\5\1\6",
            "",
            "",
            "\2\4\2\uffff\1\3\1\4\1\uffff\1\4\6\uffff\12\4\1\uffff\1\4\1"+
            "\uffff\2\4\3\uffff\1\2\6\4",
            "\2\4\2\uffff\1\3\1\4\1\uffff\1\4\6\uffff\12\4\1\uffff\1\4\1"+
            "\uffff\2\4\3\uffff\1\2\6\4"
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "1013:1: (this_ArrayVarAccess_0= ruleArrayVarAccess | this_VariableRef_1= ruleVariableRef )";
        }
    }
    static final String DFA26_eotS =
        "\11\uffff";
    static final String DFA26_eofS =
        "\1\uffff\1\5\5\uffff\2\5";
    static final String DFA26_minS =
        "\1\4\1\20\2\uffff\1\4\2\uffff\2\20";
    static final String DFA26_maxS =
        "\1\25\1\65\2\uffff\1\5\2\uffff\2\65";
    static final String DFA26_acceptS =
        "\2\uffff\1\3\1\4\1\uffff\1\2\1\1\2\uffff";
    static final String DFA26_specialS =
        "\11\uffff}>";
    static final String[] DFA26_transitionS = {
            "\1\2\1\1\17\uffff\1\3",
            "\1\5\2\uffff\2\5\1\uffff\1\5\6\uffff\12\5\1\uffff\1\5\1\6\2"+
            "\5\3\uffff\1\4\6\5",
            "",
            "",
            "\1\7\1\10",
            "",
            "",
            "\1\5\2\uffff\2\5\1\uffff\1\5\6\uffff\12\5\1\uffff\1\5\1\6\2"+
            "\5\3\uffff\1\4\6\5",
            "\1\5\2\uffff\2\5\1\uffff\1\5\6\uffff\12\5\1\uffff\1\5\1\6\2"+
            "\5\3\uffff\1\4\6\5"
    };

    static final short[] DFA26_eot = DFA.unpackEncodedString(DFA26_eotS);
    static final short[] DFA26_eof = DFA.unpackEncodedString(DFA26_eofS);
    static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars(DFA26_minS);
    static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars(DFA26_maxS);
    static final short[] DFA26_accept = DFA.unpackEncodedString(DFA26_acceptS);
    static final short[] DFA26_special = DFA.unpackEncodedString(DFA26_specialS);
    static final short[][] DFA26_transition;

    static {
        int numStates = DFA26_transitionS.length;
        DFA26_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
        }
    }

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = DFA26_eot;
            this.eof = DFA26_eof;
            this.min = DFA26_min;
            this.max = DFA26_max;
            this.accept = DFA26_accept;
            this.special = DFA26_special;
            this.transition = DFA26_transition;
        }
        public String getDescription() {
            return "1761:1: (this_Peek_0= rulePeek | this_VarAccess_1= ruleVarAccess | this_Constant_2= ruleConstant | ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_3= '(' this_BitOr_4= ruleBitOr otherlv_5= ')' ) ) | (otherlv_6= '(' this_WrapBoolExpr_7= ruleWrapBoolExpr otherlv_8= ')' ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_ruleSystem_in_entryRuleSystem75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSystem85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleSystem122 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleSystem143 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleSystem155 = new BitSet(new long[]{0x0000000002866000L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_ruleSystem221 = new BitSet(new long[]{0x0000000002866000L});
    public static final BitSet FOLLOW_ruleArrayDeclaration_in_ruleSystem297 = new BitSet(new long[]{0x0000000002866000L});
    public static final BitSet FOLLOW_ruleListDeclaration_in_ruleSystem373 = new BitSet(new long[]{0x0000000002866000L});
    public static final BitSet FOLLOW_ruleTransition_in_ruleSystem449 = new BitSet(new long[]{0x0000000002866000L});
    public static final BitSet FOLLOW_ruleTransient_in_ruleSystem525 = new BitSet(new long[]{0x0000000002866000L});
    public static final BitSet FOLLOW_13_in_ruleSystem577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransient_in_entryRuleTransient613 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransient623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleTransient660 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleTransient672 = new BitSet(new long[]{0x0000708800200030L});
    public static final BitSet FOLLOW_ruleOr_in_ruleTransient693 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleTransient705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration741 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableDeclaration751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleVariableDeclaration788 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleVariableDeclaration809 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleVariableDeclaration821 = new BitSet(new long[]{0x0000000800000010L});
    public static final BitSet FOLLOW_ruleInteger_in_ruleVariableDeclaration842 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleVariableDeclaration854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayDeclaration_in_entryRuleArrayDeclaration890 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArrayDeclaration900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleArrayDeclaration937 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleArrayDeclaration949 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleArrayDeclaration966 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleArrayDeclaration983 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleArrayDeclaration1004 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleArrayDeclaration1016 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_ruleArrayDeclaration1028 = new BitSet(new long[]{0x0000000800400010L});
    public static final BitSet FOLLOW_ruleInitValues_in_ruleArrayDeclaration1049 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_ruleArrayDeclaration1062 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleArrayDeclaration1074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleListDeclaration_in_entryRuleListDeclaration1110 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleListDeclaration1120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleListDeclaration1157 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleListDeclaration1178 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_15_in_ruleListDeclaration1191 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_ruleListDeclaration1203 = new BitSet(new long[]{0x0000000800400010L});
    public static final BitSet FOLLOW_ruleInitValues_in_ruleListDeclaration1224 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_ruleListDeclaration1237 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleListDeclaration1251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInitValues_in_entryRuleInitValues1287 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInitValues1297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInteger_in_ruleInitValues1343 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_24_in_ruleInitValues1356 = new BitSet(new long[]{0x0000000800000010L});
    public static final BitSet FOLLOW_ruleInteger_in_ruleInitValues1377 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_ruleTransition_in_entryRuleTransition1415 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransition1425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleTransition1462 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleTransition1483 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleTransition1495 = new BitSet(new long[]{0x0000708800200030L});
    public static final BitSet FOLLOW_ruleOr_in_ruleTransition1516 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleTransition1528 = new BitSet(new long[]{0x0000000004001000L});
    public static final BitSet FOLLOW_26_in_ruleTransition1541 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleTransition1562 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleTransition1576 = new BitSet(new long[]{0x0000000000002020L});
    public static final BitSet FOLLOW_ruleActions_in_ruleTransition1597 = new BitSet(new long[]{0x0000000000002020L});
    public static final BitSet FOLLOW_13_in_ruleTransition1610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleActions_in_entryRuleActions1646 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleActions1656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_ruleActions1703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePush_in_ruleActions1730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePop_in_ruleActions1757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePush_in_entryRulePush1792 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePush1802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rulePush1850 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_rulePush1862 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_rulePush1874 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitOr_in_rulePush1895 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_rulePush1907 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_rulePush1919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePop_in_entryRulePop1955 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePop1965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rulePop2013 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_rulePop2025 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_rulePop2037 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_rulePop2049 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_rulePop2061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_entryRuleAssignment2097 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssignment2107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVarAccess_in_ruleAssignment2153 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleAssignment2165 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleAssignment2186 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleAssignment2198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVarAccess_in_entryRuleVarAccess2234 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVarAccess2244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayVarAccess_in_ruleVarAccess2291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_ruleVarAccess2318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_entryRuleVariableRef2353 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableRef2363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleVariableRef2410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayVarAccess_in_entryRuleArrayVarAccess2445 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArrayVarAccess2455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleArrayVarAccess2503 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleArrayVarAccess2515 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleArrayVarAccess2536 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleArrayVarAccess2548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitOr_in_entryRuleBitOr2584 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitOr2594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitXor_in_ruleBitOr2641 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_29_in_ruleBitOr2668 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitXor_in_ruleBitOr2702 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_ruleBitXor_in_entryRuleBitXor2740 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitXor2750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitAnd_in_ruleBitXor2797 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_30_in_ruleBitXor2824 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitAnd_in_ruleBitXor2858 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_ruleBitAnd_in_entryRuleBitAnd2896 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitAnd2906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitShift_in_ruleBitAnd2953 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_31_in_ruleBitAnd2980 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitShift_in_ruleBitAnd3014 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_ruleBitShift_in_entryRuleBitShift3052 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitShift3062 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleBitShift3109 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_32_in_ruleBitShift3138 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_33_in_ruleBitShift3167 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleBitShift3204 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_ruleAddition_in_entryRuleAddition3242 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAddition3252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition3299 = new BitSet(new long[]{0x0000000C00000002L});
    public static final BitSet FOLLOW_34_in_ruleAddition3328 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_35_in_ruleAddition3357 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition3394 = new BitSet(new long[]{0x0000000C00000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_entryRuleMultiplication3432 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplication3442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitComplement_in_ruleMultiplication3489 = new BitSet(new long[]{0x0000007000000002L});
    public static final BitSet FOLLOW_36_in_ruleMultiplication3518 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_37_in_ruleMultiplication3547 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_38_in_ruleMultiplication3576 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitComplement_in_ruleMultiplication3613 = new BitSet(new long[]{0x0000007000000002L});
    public static final BitSet FOLLOW_ruleBitComplement_in_entryRuleBitComplement3651 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitComplement3661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleBitComplement3699 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_rulePower_in_ruleBitComplement3721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleBitComplement3749 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_rulePower_in_ruleBitComplement3771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePower_in_ruleBitComplement3808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePower_in_entryRulePower3843 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePower3853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_rulePower3900 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_40_in_rulePower3927 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_rulePrimary_in_rulePower3961 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_entryRulePrimary3999 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimary4009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePeek_in_rulePrimary4056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVarAccess_in_rulePrimary4083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstant_in_rulePrimary4110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rulePrimary4145 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitOr_in_rulePrimary4167 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_rulePrimary4178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rulePrimary4199 = new BitSet(new long[]{0x0000708800200030L});
    public static final BitSet FOLLOW_ruleWrapBoolExpr_in_rulePrimary4221 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_rulePrimary4232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleWrapBoolExpr_in_entryRuleWrapBoolExpr4270 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleWrapBoolExpr4280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_ruleWrapBoolExpr4325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstant_in_entryRuleConstant4360 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstant4370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleConstant4411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePeek_in_entryRulePeek4451 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePeek4461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_rulePeek4509 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_rulePeek4521 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_rulePeek4533 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_rulePeek4545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_entryRuleOr4581 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOr4591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_ruleOr4638 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_42_in_ruleOr4659 = new BitSet(new long[]{0x0000708800200030L});
    public static final BitSet FOLLOW_ruleAnd_in_ruleOr4680 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_entryRuleAnd4718 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAnd4728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_ruleAnd4775 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_43_in_ruleAnd4796 = new BitSet(new long[]{0x0000708800200030L});
    public static final BitSet FOLLOW_ruleNot_in_ruleAnd4817 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_ruleNot_in_entryRuleNot4855 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNot4865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_ruleNot4903 = new BitSet(new long[]{0x0000708800200030L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_ruleNot4925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_ruleNot4962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool4997 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryBool5007 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_rulePrimaryBool5054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_rulePrimaryBool5081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_rulePrimaryBool5114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rulePrimaryBool5133 = new BitSet(new long[]{0x0000708800200030L});
    public static final BitSet FOLLOW_ruleOr_in_rulePrimaryBool5155 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_rulePrimaryBool5166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_entryRuleComparison5203 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComparison5213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleComparison5259 = new BitSet(new long[]{0x003F000000000000L});
    public static final BitSet FOLLOW_ruleComparisonOperators_in_ruleComparison5280 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleComparison5301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_entryRuleTrue5337 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTrue5347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruleTrue5389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_entryRuleFalse5437 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFalse5447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_ruleFalse5489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName5538 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedName5549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleQualifiedName5589 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_47_in_ruleQualifiedName5608 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleQualifiedName5624 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleQualifiedName5650 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_ruleInteger_in_entryRuleInteger5699 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInteger5710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleInteger5749 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleInteger5766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_ruleComparisonOperators5825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_ruleComparisonOperators5842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_ruleComparisonOperators5859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_ruleComparisonOperators5876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_ruleComparisonOperators5893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_ruleComparisonOperators5910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_synpred1_InternalGal4129 = new BitSet(new long[]{0x0000008800200030L});
    public static final BitSet FOLLOW_ruleBitOr_in_synpred1_InternalGal4133 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_synpred1_InternalGal4135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_synpred2_InternalGal5098 = new BitSet(new long[]{0x0000000000000002L});

}
