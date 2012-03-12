package fr.lip6.move.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import fr.lip6.move.services.GalGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalGalParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'GAL'", "'{'", "'}'", "'='", "';'", "'['", "']'", "'transition'", "'label'", "'+'", "'-'", "'*'", "'/'", "'%'", "'**'", "'('", "')'", "'||'", "'&&'", "'!'", "'True'", "'False'", "'<'", "'>'", "'>='", "'<='", "'=='", "'!='", "'.'", "'.*'"
    };
    public static final int RULE_ID=5;
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
    public static final int RULE_INT=4;
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:67:1: entryRuleSystem returns [EObject current=null] : iv_ruleSystem= ruleSystem EOF ;
    public final EObject entryRuleSystem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSystem = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:68:2: (iv_ruleSystem= ruleSystem EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:69:2: iv_ruleSystem= ruleSystem EOF
            {
             newCompositeNode(grammarAccess.getSystemRule()); 
            pushFollow(FOLLOW_ruleSystem_in_entryRuleSystem75);
            iv_ruleSystem=ruleSystem();

            state._fsp--;

             current =iv_ruleSystem; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSystem85); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:76:1: ruleSystem returns [EObject current=null] : (otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )? ;
    public final EObject ruleSystem() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_variables_3_0 = null;

        EObject lv_transitions_4_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:79:28: ( (otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )? )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:1: (otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )?
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:1: (otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==11) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:3: otherlv_0= 'GAL' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}'
                    {
                    otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleSystem122); 

                        	newLeafNode(otherlv_0, grammarAccess.getSystemAccess().getGALKeyword_0());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:84:1: ( (lv_name_1_0= ruleQualifiedName ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:85:1: (lv_name_1_0= ruleQualifiedName )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:85:1: (lv_name_1_0= ruleQualifiedName )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:86:3: lv_name_1_0= ruleQualifiedName
                    {
                     
                    	        newCompositeNode(grammarAccess.getSystemAccess().getNameQualifiedNameParserRuleCall_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleQualifiedName_in_ruleSystem143);
                    lv_name_1_0=ruleQualifiedName();

                    state._fsp--;


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

                    otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleSystem155); 

                        	newLeafNode(otherlv_2, grammarAccess.getSystemAccess().getLeftCurlyBracketKeyword_2());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:106:1: ( (lv_variables_3_0= ruleVariableDeclaration ) )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==RULE_ID) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:107:1: (lv_variables_3_0= ruleVariableDeclaration )
                    	    {
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:107:1: (lv_variables_3_0= ruleVariableDeclaration )
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:108:3: lv_variables_3_0= ruleVariableDeclaration
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getSystemAccess().getVariablesVariableDeclarationParserRuleCall_3_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleVariableDeclaration_in_ruleSystem176);
                    	    lv_variables_3_0=ruleVariableDeclaration();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getSystemRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"variables",
                    	            		lv_variables_3_0, 
                    	            		"VariableDeclaration");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:124:3: ( (lv_transitions_4_0= ruleTransition ) )+
                    int cnt2=0;
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==18) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:125:1: (lv_transitions_4_0= ruleTransition )
                    	    {
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:125:1: (lv_transitions_4_0= ruleTransition )
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:126:3: lv_transitions_4_0= ruleTransition
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getSystemAccess().getTransitionsTransitionParserRuleCall_4_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleTransition_in_ruleSystem198);
                    	    lv_transitions_4_0=ruleTransition();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getSystemRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"transitions",
                    	            		lv_transitions_4_0, 
                    	            		"Transition");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt2 >= 1 ) break loop2;
                                EarlyExitException eee =
                                    new EarlyExitException(2, input);
                                throw eee;
                        }
                        cnt2++;
                    } while (true);

                    otherlv_5=(Token)match(input,13,FOLLOW_13_in_ruleSystem211); 

                        	newLeafNode(otherlv_5, grammarAccess.getSystemAccess().getRightCurlyBracketKeyword_5());
                        

                    }
                    break;

            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleVariableDeclaration"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:154:1: entryRuleVariableDeclaration returns [EObject current=null] : iv_ruleVariableDeclaration= ruleVariableDeclaration EOF ;
    public final EObject entryRuleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableDeclaration = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:155:2: (iv_ruleVariableDeclaration= ruleVariableDeclaration EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:156:2: iv_ruleVariableDeclaration= ruleVariableDeclaration EOF
            {
             newCompositeNode(grammarAccess.getVariableDeclarationRule()); 
            pushFollow(FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration248);
            iv_ruleVariableDeclaration=ruleVariableDeclaration();

            state._fsp--;

             current =iv_ruleVariableDeclaration; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableDeclaration258); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:163:1: ruleVariableDeclaration returns [EObject current=null] : ( ( ( (lv_name_0_0= ruleQualifiedName ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) | ( ( (lv_aname_4_0= ruleQualifiedName ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_initVal_9_0= RULE_INT ) ) otherlv_10= ';' ) ) ;
    public final EObject ruleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_initVal_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token lv_initVal_9_0=null;
        Token otherlv_10=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        AntlrDatatypeRuleToken lv_aname_4_0 = null;

        EObject lv_index_6_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:166:28: ( ( ( ( (lv_name_0_0= ruleQualifiedName ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) | ( ( (lv_aname_4_0= ruleQualifiedName ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_initVal_9_0= RULE_INT ) ) otherlv_10= ';' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:1: ( ( ( (lv_name_0_0= ruleQualifiedName ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) | ( ( (lv_aname_4_0= ruleQualifiedName ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_initVal_9_0= RULE_INT ) ) otherlv_10= ';' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:1: ( ( ( (lv_name_0_0= ruleQualifiedName ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) | ( ( (lv_aname_4_0= ruleQualifiedName ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_initVal_9_0= RULE_INT ) ) otherlv_10= ';' ) )
            int alt4=2;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:2: ( ( (lv_name_0_0= ruleQualifiedName ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:2: ( ( (lv_name_0_0= ruleQualifiedName ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:3: ( (lv_name_0_0= ruleQualifiedName ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';'
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:3: ( (lv_name_0_0= ruleQualifiedName ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:168:1: (lv_name_0_0= ruleQualifiedName )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:168:1: (lv_name_0_0= ruleQualifiedName )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:169:3: lv_name_0_0= ruleQualifiedName
                    {
                     
                    	        newCompositeNode(grammarAccess.getVariableDeclarationAccess().getNameQualifiedNameParserRuleCall_0_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleQualifiedName_in_ruleVariableDeclaration305);
                    lv_name_0_0=ruleQualifiedName();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
                    	        }
                           		set(
                           			current, 
                           			"name",
                            		lv_name_0_0, 
                            		"QualifiedName");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleVariableDeclaration317); 

                        	newLeafNode(otherlv_1, grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_0_1());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:189:1: ( (lv_initVal_2_0= RULE_INT ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:190:1: (lv_initVal_2_0= RULE_INT )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:190:1: (lv_initVal_2_0= RULE_INT )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:191:3: lv_initVal_2_0= RULE_INT
                    {
                    lv_initVal_2_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleVariableDeclaration334); 

                    			newLeafNode(lv_initVal_2_0, grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_0_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getVariableDeclarationRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"initVal",
                            		lv_initVal_2_0, 
                            		"INT");
                    	    

                    }


                    }

                    otherlv_3=(Token)match(input,15,FOLLOW_15_in_ruleVariableDeclaration351); 

                        	newLeafNode(otherlv_3, grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_0_3());
                        

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:212:6: ( ( (lv_aname_4_0= ruleQualifiedName ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_initVal_9_0= RULE_INT ) ) otherlv_10= ';' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:212:6: ( ( (lv_aname_4_0= ruleQualifiedName ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_initVal_9_0= RULE_INT ) ) otherlv_10= ';' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:212:7: ( (lv_aname_4_0= ruleQualifiedName ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_initVal_9_0= RULE_INT ) ) otherlv_10= ';'
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:212:7: ( (lv_aname_4_0= ruleQualifiedName ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:213:1: (lv_aname_4_0= ruleQualifiedName )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:213:1: (lv_aname_4_0= ruleQualifiedName )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:214:3: lv_aname_4_0= ruleQualifiedName
                    {
                     
                    	        newCompositeNode(grammarAccess.getVariableDeclarationAccess().getAnameQualifiedNameParserRuleCall_1_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleQualifiedName_in_ruleVariableDeclaration380);
                    lv_aname_4_0=ruleQualifiedName();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
                    	        }
                           		set(
                           			current, 
                           			"aname",
                            		lv_aname_4_0, 
                            		"QualifiedName");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_5=(Token)match(input,16,FOLLOW_16_in_ruleVariableDeclaration392); 

                        	newLeafNode(otherlv_5, grammarAccess.getVariableDeclarationAccess().getLeftSquareBracketKeyword_1_1());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:234:1: ( (lv_index_6_0= ruleAddition ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:235:1: (lv_index_6_0= ruleAddition )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:235:1: (lv_index_6_0= ruleAddition )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:236:3: lv_index_6_0= ruleAddition
                    {
                     
                    	        newCompositeNode(grammarAccess.getVariableDeclarationAccess().getIndexAdditionParserRuleCall_1_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAddition_in_ruleVariableDeclaration413);
                    lv_index_6_0=ruleAddition();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
                    	        }
                           		set(
                           			current, 
                           			"index",
                            		lv_index_6_0, 
                            		"Addition");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_7=(Token)match(input,17,FOLLOW_17_in_ruleVariableDeclaration425); 

                        	newLeafNode(otherlv_7, grammarAccess.getVariableDeclarationAccess().getRightSquareBracketKeyword_1_3());
                        
                    otherlv_8=(Token)match(input,14,FOLLOW_14_in_ruleVariableDeclaration437); 

                        	newLeafNode(otherlv_8, grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_1_4());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:260:1: ( (lv_initVal_9_0= RULE_INT ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:261:1: (lv_initVal_9_0= RULE_INT )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:261:1: (lv_initVal_9_0= RULE_INT )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:262:3: lv_initVal_9_0= RULE_INT
                    {
                    lv_initVal_9_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleVariableDeclaration454); 

                    			newLeafNode(lv_initVal_9_0, grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_1_5_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getVariableDeclarationRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"initVal",
                            		lv_initVal_9_0, 
                            		"INT");
                    	    

                    }


                    }

                    otherlv_10=(Token)match(input,15,FOLLOW_15_in_ruleVariableDeclaration471); 

                        	newLeafNode(otherlv_10, grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_1_6());
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleTransition"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:290:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:291:2: (iv_ruleTransition= ruleTransition EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:292:2: iv_ruleTransition= ruleTransition EOF
            {
             newCompositeNode(grammarAccess.getTransitionRule()); 
            pushFollow(FOLLOW_ruleTransition_in_entryRuleTransition508);
            iv_ruleTransition=ruleTransition();

            state._fsp--;

             current =iv_ruleTransition; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransition518); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:299:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' ) ;
    public final EObject ruleTransition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_label_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_guard_3_0 = null;

        EObject lv_assignments_8_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:302:28: ( (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:303:1: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:303:1: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:303:3: otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' ( (lv_guard_3_0= ruleOr ) ) otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,18,FOLLOW_18_in_ruleTransition555); 

                	newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:307:1: ( (lv_name_1_0= RULE_ID ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:308:1: (lv_name_1_0= RULE_ID )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:308:1: (lv_name_1_0= RULE_ID )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:309:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTransition572); 

            			newLeafNode(lv_name_1_0, grammarAccess.getTransitionAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getTransitionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,16,FOLLOW_16_in_ruleTransition589); 

                	newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getLeftSquareBracketKeyword_2());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:329:1: ( (lv_guard_3_0= ruleOr ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:330:1: (lv_guard_3_0= ruleOr )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:330:1: (lv_guard_3_0= ruleOr )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:331:3: lv_guard_3_0= ruleOr
            {
             
            	        newCompositeNode(grammarAccess.getTransitionAccess().getGuardOrParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleOr_in_ruleTransition610);
            lv_guard_3_0=ruleOr();

            state._fsp--;


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

            otherlv_4=(Token)match(input,17,FOLLOW_17_in_ruleTransition622); 

                	newLeafNode(otherlv_4, grammarAccess.getTransitionAccess().getRightSquareBracketKeyword_4());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:351:1: (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==19) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:351:3: otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,19,FOLLOW_19_in_ruleTransition635); 

                        	newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getLabelKeyword_5_0());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:355:1: ( (lv_label_6_0= RULE_STRING ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:356:1: (lv_label_6_0= RULE_STRING )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:356:1: (lv_label_6_0= RULE_STRING )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:357:3: lv_label_6_0= RULE_STRING
                    {
                    lv_label_6_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleTransition652); 

                    			newLeafNode(lv_label_6_0, grammarAccess.getTransitionAccess().getLabelSTRINGTerminalRuleCall_5_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getTransitionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"label",
                            		lv_label_6_0, 
                            		"STRING");
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_7=(Token)match(input,12,FOLLOW_12_in_ruleTransition671); 

                	newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_6());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:377:1: ( (lv_assignments_8_0= ruleAssignment ) )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==RULE_ID) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:378:1: (lv_assignments_8_0= ruleAssignment )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:378:1: (lv_assignments_8_0= ruleAssignment )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:379:3: lv_assignments_8_0= ruleAssignment
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getTransitionAccess().getAssignmentsAssignmentParserRuleCall_7_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleAssignment_in_ruleTransition692);
            	    lv_assignments_8_0=ruleAssignment();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getTransitionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"assignments",
            	            		lv_assignments_8_0, 
            	            		"Assignment");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);

            otherlv_9=(Token)match(input,13,FOLLOW_13_in_ruleTransition705); 

                	newLeafNode(otherlv_9, grammarAccess.getTransitionAccess().getRightCurlyBracketKeyword_8());
                

            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleAssignment"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:407:1: entryRuleAssignment returns [EObject current=null] : iv_ruleAssignment= ruleAssignment EOF ;
    public final EObject entryRuleAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssignment = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:408:2: (iv_ruleAssignment= ruleAssignment EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:409:2: iv_ruleAssignment= ruleAssignment EOF
            {
             newCompositeNode(grammarAccess.getAssignmentRule()); 
            pushFollow(FOLLOW_ruleAssignment_in_entryRuleAssignment741);
            iv_ruleAssignment=ruleAssignment();

            state._fsp--;

             current =iv_ruleAssignment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssignment751); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:416:1: ruleAssignment returns [EObject current=null] : ( ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) | ( ( (lv_aVar_4_0= ruleVariableRef ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_expr_9_0= ruleAddition ) ) otherlv_10= ';' ) ) ;
    public final EObject ruleAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        EObject lv_var_0_0 = null;

        EObject lv_expr_2_0 = null;

        EObject lv_aVar_4_0 = null;

        EObject lv_index_6_0 = null;

        EObject lv_expr_9_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:419:28: ( ( ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) | ( ( (lv_aVar_4_0= ruleVariableRef ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_expr_9_0= ruleAddition ) ) otherlv_10= ';' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:1: ( ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) | ( ( (lv_aVar_4_0= ruleVariableRef ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_expr_9_0= ruleAddition ) ) otherlv_10= ';' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:1: ( ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) | ( ( (lv_aVar_4_0= ruleVariableRef ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_expr_9_0= ruleAddition ) ) otherlv_10= ';' ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_ID) ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==14) ) {
                    alt7=1;
                }
                else if ( (LA7_1==16) ) {
                    alt7=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:2: ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:2: ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:3: ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';'
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:3: ( (lv_var_0_0= ruleVariableRef ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:421:1: (lv_var_0_0= ruleVariableRef )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:421:1: (lv_var_0_0= ruleVariableRef )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:422:3: lv_var_0_0= ruleVariableRef
                    {
                     
                    	        newCompositeNode(grammarAccess.getAssignmentAccess().getVarVariableRefParserRuleCall_0_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleVariableRef_in_ruleAssignment798);
                    lv_var_0_0=ruleVariableRef();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAssignmentRule());
                    	        }
                           		set(
                           			current, 
                           			"var",
                            		lv_var_0_0, 
                            		"VariableRef");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleAssignment810); 

                        	newLeafNode(otherlv_1, grammarAccess.getAssignmentAccess().getEqualsSignKeyword_0_1());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:442:1: ( (lv_expr_2_0= ruleAddition ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:443:1: (lv_expr_2_0= ruleAddition )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:443:1: (lv_expr_2_0= ruleAddition )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:444:3: lv_expr_2_0= ruleAddition
                    {
                     
                    	        newCompositeNode(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_0_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAddition_in_ruleAssignment831);
                    lv_expr_2_0=ruleAddition();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAssignmentRule());
                    	        }
                           		set(
                           			current, 
                           			"expr",
                            		lv_expr_2_0, 
                            		"Addition");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_3=(Token)match(input,15,FOLLOW_15_in_ruleAssignment843); 

                        	newLeafNode(otherlv_3, grammarAccess.getAssignmentAccess().getSemicolonKeyword_0_3());
                        

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:465:6: ( ( (lv_aVar_4_0= ruleVariableRef ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_expr_9_0= ruleAddition ) ) otherlv_10= ';' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:465:6: ( ( (lv_aVar_4_0= ruleVariableRef ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_expr_9_0= ruleAddition ) ) otherlv_10= ';' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:465:7: ( (lv_aVar_4_0= ruleVariableRef ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_expr_9_0= ruleAddition ) ) otherlv_10= ';'
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:465:7: ( (lv_aVar_4_0= ruleVariableRef ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:466:1: (lv_aVar_4_0= ruleVariableRef )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:466:1: (lv_aVar_4_0= ruleVariableRef )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:467:3: lv_aVar_4_0= ruleVariableRef
                    {
                     
                    	        newCompositeNode(grammarAccess.getAssignmentAccess().getAVarVariableRefParserRuleCall_1_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleVariableRef_in_ruleAssignment872);
                    lv_aVar_4_0=ruleVariableRef();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAssignmentRule());
                    	        }
                           		add(
                           			current, 
                           			"aVar",
                            		lv_aVar_4_0, 
                            		"VariableRef");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_5=(Token)match(input,16,FOLLOW_16_in_ruleAssignment884); 

                        	newLeafNode(otherlv_5, grammarAccess.getAssignmentAccess().getLeftSquareBracketKeyword_1_1());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:487:1: ( (lv_index_6_0= ruleAddition ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:488:1: (lv_index_6_0= ruleAddition )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:488:1: (lv_index_6_0= ruleAddition )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:489:3: lv_index_6_0= ruleAddition
                    {
                     
                    	        newCompositeNode(grammarAccess.getAssignmentAccess().getIndexAdditionParserRuleCall_1_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAddition_in_ruleAssignment905);
                    lv_index_6_0=ruleAddition();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAssignmentRule());
                    	        }
                           		set(
                           			current, 
                           			"index",
                            		lv_index_6_0, 
                            		"Addition");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_7=(Token)match(input,17,FOLLOW_17_in_ruleAssignment917); 

                        	newLeafNode(otherlv_7, grammarAccess.getAssignmentAccess().getRightSquareBracketKeyword_1_3());
                        
                    otherlv_8=(Token)match(input,14,FOLLOW_14_in_ruleAssignment929); 

                        	newLeafNode(otherlv_8, grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1_4());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:513:1: ( (lv_expr_9_0= ruleAddition ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:514:1: (lv_expr_9_0= ruleAddition )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:514:1: (lv_expr_9_0= ruleAddition )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:515:3: lv_expr_9_0= ruleAddition
                    {
                     
                    	        newCompositeNode(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_1_5_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAddition_in_ruleAssignment950);
                    lv_expr_9_0=ruleAddition();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getAssignmentRule());
                    	        }
                           		set(
                           			current, 
                           			"expr",
                            		lv_expr_9_0, 
                            		"Addition");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_10=(Token)match(input,15,FOLLOW_15_in_ruleAssignment962); 

                        	newLeafNode(otherlv_10, grammarAccess.getAssignmentAccess().getSemicolonKeyword_1_6());
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleAddition"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:543:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:544:2: (iv_ruleAddition= ruleAddition EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:545:2: iv_ruleAddition= ruleAddition EOF
            {
             newCompositeNode(grammarAccess.getAdditionRule()); 
            pushFollow(FOLLOW_ruleAddition_in_entryRuleAddition999);
            iv_ruleAddition=ruleAddition();

            state._fsp--;

             current =iv_ruleAddition; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAddition1009); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:552:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_Multiplication_0 = null;

        EObject lv_right_5_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:555:28: ( (this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:556:1: (this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:556:1: (this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:557:5: this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition1056);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;

             
                    current = this_Multiplication_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:565:1: ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>=20 && LA9_0<=21)) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:565:2: ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:565:2: ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) )
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==20) ) {
            	        alt8=1;
            	    }
            	    else if ( (LA8_0==21) ) {
            	        alt8=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 8, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt8) {
            	        case 1 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:565:3: ( () otherlv_2= '+' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:565:3: ( () otherlv_2= '+' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:565:4: () otherlv_2= '+'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:565:4: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:566:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0(),
            	                        current);
            	                

            	            }

            	            otherlv_2=(Token)match(input,20,FOLLOW_20_in_ruleAddition1079); 

            	                	newLeafNode(otherlv_2, grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_0_1());
            	                

            	            }


            	            }
            	            break;
            	        case 2 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:576:6: ( () otherlv_4= '-' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:576:6: ( () otherlv_4= '-' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:576:7: () otherlv_4= '-'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:576:7: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:577:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0(),
            	                        current);
            	                

            	            }

            	            otherlv_4=(Token)match(input,21,FOLLOW_21_in_ruleAddition1108); 

            	                	newLeafNode(otherlv_4, grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_0_1_1());
            	                

            	            }


            	            }
            	            break;

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:586:3: ( (lv_right_5_0= ruleMultiplication ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:587:1: (lv_right_5_0= ruleMultiplication )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:587:1: (lv_right_5_0= ruleMultiplication )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:588:3: lv_right_5_0= ruleMultiplication
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition1131);
            	    lv_right_5_0=ruleMultiplication();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAdditionRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_5_0, 
            	            		"Multiplication");
            	    	        afterParserOrEnumRuleCall();
            	    	    

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

             leaveRule(); 
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:612:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:613:2: (iv_ruleMultiplication= ruleMultiplication EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:614:2: iv_ruleMultiplication= ruleMultiplication EOF
            {
             newCompositeNode(grammarAccess.getMultiplicationRule()); 
            pushFollow(FOLLOW_ruleMultiplication_in_entryRuleMultiplication1169);
            iv_ruleMultiplication=ruleMultiplication();

            state._fsp--;

             current =iv_ruleMultiplication; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplication1179); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:621:1: ruleMultiplication returns [EObject current=null] : (this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject this_UnitaryMinus_0 = null;

        EObject lv_right_7_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:624:28: ( (this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:625:1: (this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:625:1: (this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:626:5: this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getMultiplicationAccess().getUnitaryMinusParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleUnitaryMinus_in_ruleMultiplication1226);
            this_UnitaryMinus_0=ruleUnitaryMinus();

            state._fsp--;

             
                    current = this_UnitaryMinus_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:634:1: ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>=22 && LA11_0<=24)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:634:2: ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:634:2: ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) )
            	    int alt10=3;
            	    switch ( input.LA(1) ) {
            	    case 22:
            	        {
            	        alt10=1;
            	        }
            	        break;
            	    case 23:
            	        {
            	        alt10=2;
            	        }
            	        break;
            	    case 24:
            	        {
            	        alt10=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt10) {
            	        case 1 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:634:3: ( () otherlv_2= '*' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:634:3: ( () otherlv_2= '*' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:634:4: () otherlv_2= '*'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:634:4: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:635:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0_0_0(),
            	                        current);
            	                

            	            }

            	            otherlv_2=(Token)match(input,22,FOLLOW_22_in_ruleMultiplication1249); 

            	                	newLeafNode(otherlv_2, grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_0_1());
            	                

            	            }


            	            }
            	            break;
            	        case 2 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:645:6: ( () otherlv_4= '/' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:645:6: ( () otherlv_4= '/' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:645:7: () otherlv_4= '/'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:645:7: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:646:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getMultiplicationAccess().getDivisionLeftAction_1_0_1_0(),
            	                        current);
            	                

            	            }

            	            otherlv_4=(Token)match(input,23,FOLLOW_23_in_ruleMultiplication1278); 

            	                	newLeafNode(otherlv_4, grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_0_1_1());
            	                

            	            }


            	            }
            	            break;
            	        case 3 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:656:6: ( () otherlv_6= '%' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:656:6: ( () otherlv_6= '%' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:656:7: () otherlv_6= '%'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:656:7: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:657:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getMultiplicationAccess().getModuloLeftAction_1_0_2_0(),
            	                        current);
            	                

            	            }

            	            otherlv_6=(Token)match(input,24,FOLLOW_24_in_ruleMultiplication1307); 

            	                	newLeafNode(otherlv_6, grammarAccess.getMultiplicationAccess().getPercentSignKeyword_1_0_2_1());
            	                

            	            }


            	            }
            	            break;

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:666:3: ( (lv_right_7_0= ruleUnitaryMinus ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:667:1: (lv_right_7_0= ruleUnitaryMinus )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:667:1: (lv_right_7_0= ruleUnitaryMinus )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:668:3: lv_right_7_0= ruleUnitaryMinus
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMultiplicationAccess().getRightUnitaryMinusParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnitaryMinus_in_ruleMultiplication1330);
            	    lv_right_7_0=ruleUnitaryMinus();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMultiplicationRule());
            	    	        }
            	           		set(
            	           			current, 
            	           			"right",
            	            		lv_right_7_0, 
            	            		"UnitaryMinus");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleUnitaryMinus"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:692:1: entryRuleUnitaryMinus returns [EObject current=null] : iv_ruleUnitaryMinus= ruleUnitaryMinus EOF ;
    public final EObject entryRuleUnitaryMinus() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitaryMinus = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:693:2: (iv_ruleUnitaryMinus= ruleUnitaryMinus EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:694:2: iv_ruleUnitaryMinus= ruleUnitaryMinus EOF
            {
             newCompositeNode(grammarAccess.getUnitaryMinusRule()); 
            pushFollow(FOLLOW_ruleUnitaryMinus_in_entryRuleUnitaryMinus1368);
            iv_ruleUnitaryMinus=ruleUnitaryMinus();

            state._fsp--;

             current =iv_ruleUnitaryMinus; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnitaryMinus1378); 

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
    // $ANTLR end "entryRuleUnitaryMinus"


    // $ANTLR start "ruleUnitaryMinus"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:701:1: ruleUnitaryMinus returns [EObject current=null] : ( (otherlv_0= '-' )? this_Power_1= rulePower () ) ;
    public final EObject ruleUnitaryMinus() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject this_Power_1 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:704:28: ( ( (otherlv_0= '-' )? this_Power_1= rulePower () ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:705:1: ( (otherlv_0= '-' )? this_Power_1= rulePower () )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:705:1: ( (otherlv_0= '-' )? this_Power_1= rulePower () )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:705:2: (otherlv_0= '-' )? this_Power_1= rulePower ()
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:705:2: (otherlv_0= '-' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==21) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:705:4: otherlv_0= '-'
                    {
                    otherlv_0=(Token)match(input,21,FOLLOW_21_in_ruleUnitaryMinus1416); 

                        	newLeafNode(otherlv_0, grammarAccess.getUnitaryMinusAccess().getHyphenMinusKeyword_0());
                        

                    }
                    break;

            }

             
                    newCompositeNode(grammarAccess.getUnitaryMinusAccess().getPowerParserRuleCall_1()); 
                
            pushFollow(FOLLOW_rulePower_in_ruleUnitaryMinus1440);
            this_Power_1=rulePower();

            state._fsp--;

             
                    current = this_Power_1; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:718:1: ()
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:719:5: 
            {

                    current = forceCreateModelElementAndSet(
                        grammarAccess.getUnitaryMinusAccess().getUnitaryMinusValAction_2(),
                        current);
                

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnitaryMinus"


    // $ANTLR start "entryRulePower"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:732:1: entryRulePower returns [EObject current=null] : iv_rulePower= rulePower EOF ;
    public final EObject entryRulePower() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePower = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:733:2: (iv_rulePower= rulePower EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:734:2: iv_rulePower= rulePower EOF
            {
             newCompositeNode(grammarAccess.getPowerRule()); 
            pushFollow(FOLLOW_rulePower_in_entryRulePower1484);
            iv_rulePower=rulePower();

            state._fsp--;

             current =iv_rulePower; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePower1494); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:741:1: rulePower returns [EObject current=null] : (this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )* ) ;
    public final EObject rulePower() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Primary_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:744:28: ( (this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:745:1: (this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:745:1: (this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:746:5: this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getPowerAccess().getPrimaryParserRuleCall_0()); 
                
            pushFollow(FOLLOW_rulePrimary_in_rulePower1541);
            this_Primary_0=rulePrimary();

            state._fsp--;

             
                    current = this_Primary_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:754:1: ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==25) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:754:2: () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:754:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:755:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getPowerAccess().getPowerLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    otherlv_2=(Token)match(input,25,FOLLOW_25_in_rulePower1562); 

            	        	newLeafNode(otherlv_2, grammarAccess.getPowerAccess().getAsteriskAsteriskKeyword_1_1());
            	        
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:764:1: ( (lv_right_3_0= rulePrimary ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:765:1: (lv_right_3_0= rulePrimary )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:765:1: (lv_right_3_0= rulePrimary )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:766:3: lv_right_3_0= rulePrimary
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getPowerAccess().getRightPrimaryParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePrimary_in_rulePower1583);
            	    lv_right_3_0=rulePrimary();

            	    state._fsp--;


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
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }


            }

             leaveRule(); 
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:790:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:791:2: (iv_rulePrimary= rulePrimary EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:792:2: iv_rulePrimary= rulePrimary EOF
            {
             newCompositeNode(grammarAccess.getPrimaryRule()); 
            pushFollow(FOLLOW_rulePrimary_in_entryRulePrimary1621);
            iv_rulePrimary=rulePrimary();

            state._fsp--;

             current =iv_rulePrimary; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimary1631); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:799:1: rulePrimary returns [EObject current=null] : (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_VariableRef_0 = null;

        EObject this_Constante_1 = null;

        EObject this_Addition_3 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:802:28: ( (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:803:1: (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:803:1: (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) )
            int alt14=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt14=1;
                }
                break;
            case RULE_INT:
                {
                alt14=2;
                }
                break;
            case 26:
                {
                alt14=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:804:5: this_VariableRef_0= ruleVariableRef
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getVariableRefParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleVariableRef_in_rulePrimary1678);
                    this_VariableRef_0=ruleVariableRef();

                    state._fsp--;

                     
                            current = this_VariableRef_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:814:5: this_Constante_1= ruleConstante
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleConstante_in_rulePrimary1705);
                    this_Constante_1=ruleConstante();

                    state._fsp--;

                     
                            current = this_Constante_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:823:6: (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:823:6: (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:823:8: otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,26,FOLLOW_26_in_rulePrimary1723); 

                        	newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0());
                        
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getAdditionParserRuleCall_2_1()); 
                        
                    pushFollow(FOLLOW_ruleAddition_in_rulePrimary1745);
                    this_Addition_3=ruleAddition();

                    state._fsp--;

                     
                            current = this_Addition_3; 
                            afterParserOrEnumRuleCall();
                        
                    otherlv_4=(Token)match(input,27,FOLLOW_27_in_rulePrimary1756); 

                        	newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_2_2());
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleConstante"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:848:1: entryRuleConstante returns [EObject current=null] : iv_ruleConstante= ruleConstante EOF ;
    public final EObject entryRuleConstante() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstante = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:849:2: (iv_ruleConstante= ruleConstante EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:850:2: iv_ruleConstante= ruleConstante EOF
            {
             newCompositeNode(grammarAccess.getConstanteRule()); 
            pushFollow(FOLLOW_ruleConstante_in_entryRuleConstante1793);
            iv_ruleConstante=ruleConstante();

            state._fsp--;

             current =iv_ruleConstante; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstante1803); 

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
    // $ANTLR end "entryRuleConstante"


    // $ANTLR start "ruleConstante"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:857:1: ruleConstante returns [EObject current=null] : ( (lv_val_0_0= RULE_INT ) ) ;
    public final EObject ruleConstante() throws RecognitionException {
        EObject current = null;

        Token lv_val_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:860:28: ( ( (lv_val_0_0= RULE_INT ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:861:1: ( (lv_val_0_0= RULE_INT ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:861:1: ( (lv_val_0_0= RULE_INT ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:862:1: (lv_val_0_0= RULE_INT )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:862:1: (lv_val_0_0= RULE_INT )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:863:3: lv_val_0_0= RULE_INT
            {
            lv_val_0_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleConstante1844); 

            			newLeafNode(lv_val_0_0, grammarAccess.getConstanteAccess().getValINTTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getConstanteRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"val",
                    		lv_val_0_0, 
                    		"INT");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConstante"


    // $ANTLR start "entryRuleVariableRef"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:887:1: entryRuleVariableRef returns [EObject current=null] : iv_ruleVariableRef= ruleVariableRef EOF ;
    public final EObject entryRuleVariableRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableRef = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:888:2: (iv_ruleVariableRef= ruleVariableRef EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:889:2: iv_ruleVariableRef= ruleVariableRef EOF
            {
             newCompositeNode(grammarAccess.getVariableRefRule()); 
            pushFollow(FOLLOW_ruleVariableRef_in_entryRuleVariableRef1884);
            iv_ruleVariableRef=ruleVariableRef();

            state._fsp--;

             current =iv_ruleVariableRef; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableRef1894); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:896:1: ruleVariableRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleVariableRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:899:28: ( ( (otherlv_0= RULE_ID ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:900:1: ( (otherlv_0= RULE_ID ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:900:1: ( (otherlv_0= RULE_ID ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:901:1: (otherlv_0= RULE_ID )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:901:1: (otherlv_0= RULE_ID )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:902:3: otherlv_0= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getVariableRefRule());
            	        }
                    
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleVariableRef1938); 

            		newLeafNode(otherlv_0, grammarAccess.getVariableRefAccess().getVarVariableCrossReference_0()); 
            	

            }


            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleOr"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:921:1: entryRuleOr returns [EObject current=null] : iv_ruleOr= ruleOr EOF ;
    public final EObject entryRuleOr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOr = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:922:2: (iv_ruleOr= ruleOr EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:923:2: iv_ruleOr= ruleOr EOF
            {
             newCompositeNode(grammarAccess.getOrRule()); 
            pushFollow(FOLLOW_ruleOr_in_entryRuleOr1973);
            iv_ruleOr=ruleOr();

            state._fsp--;

             current =iv_ruleOr; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOr1983); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:930:1: ruleOr returns [EObject current=null] : (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* ) ;
    public final EObject ruleOr() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_And_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:933:28: ( (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:934:1: (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:934:1: (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:935:5: this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getOrAccess().getAndParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleAnd_in_ruleOr2030);
            this_And_0=ruleAnd();

            state._fsp--;

             
                    current = this_And_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:943:1: ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==28) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:943:2: () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:943:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:944:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getOrAccess().getOrLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    otherlv_2=(Token)match(input,28,FOLLOW_28_in_ruleOr2051); 

            	        	newLeafNode(otherlv_2, grammarAccess.getOrAccess().getVerticalLineVerticalLineKeyword_1_1());
            	        
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:953:1: ( (lv_right_3_0= ruleAnd ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:954:1: (lv_right_3_0= ruleAnd )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:954:1: (lv_right_3_0= ruleAnd )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:955:3: lv_right_3_0= ruleAnd
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleAnd_in_ruleOr2072);
            	    lv_right_3_0=ruleAnd();

            	    state._fsp--;


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
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            }


            }

             leaveRule(); 
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:979:1: entryRuleAnd returns [EObject current=null] : iv_ruleAnd= ruleAnd EOF ;
    public final EObject entryRuleAnd() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnd = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:980:2: (iv_ruleAnd= ruleAnd EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:981:2: iv_ruleAnd= ruleAnd EOF
            {
             newCompositeNode(grammarAccess.getAndRule()); 
            pushFollow(FOLLOW_ruleAnd_in_entryRuleAnd2110);
            iv_ruleAnd=ruleAnd();

            state._fsp--;

             current =iv_ruleAnd; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAnd2120); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:988:1: ruleAnd returns [EObject current=null] : (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* ) ;
    public final EObject ruleAnd() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Not_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:991:28: ( (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:992:1: (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:992:1: (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:993:5: this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getAndAccess().getNotParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleNot_in_ruleAnd2167);
            this_Not_0=ruleNot();

            state._fsp--;

             
                    current = this_Not_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1001:1: ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==29) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1001:2: () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1001:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1002:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getAndAccess().getAndLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    otherlv_2=(Token)match(input,29,FOLLOW_29_in_ruleAnd2188); 

            	        	newLeafNode(otherlv_2, grammarAccess.getAndAccess().getAmpersandAmpersandKeyword_1_1());
            	        
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1011:1: ( (lv_right_3_0= ruleNot ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1012:1: (lv_right_3_0= ruleNot )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1012:1: (lv_right_3_0= ruleNot )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1013:3: lv_right_3_0= ruleNot
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAndAccess().getRightNotParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleNot_in_ruleAnd2209);
            	    lv_right_3_0=ruleNot();

            	    state._fsp--;


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
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }


            }

             leaveRule(); 
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1037:1: entryRuleNot returns [EObject current=null] : iv_ruleNot= ruleNot EOF ;
    public final EObject entryRuleNot() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNot = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1038:2: (iv_ruleNot= ruleNot EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1039:2: iv_ruleNot= ruleNot EOF
            {
             newCompositeNode(grammarAccess.getNotRule()); 
            pushFollow(FOLLOW_ruleNot_in_entryRuleNot2247);
            iv_ruleNot=ruleNot();

            state._fsp--;

             current =iv_ruleNot; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNot2257); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1046:1: ruleNot returns [EObject current=null] : ( (otherlv_0= '!' )? (this_PrimaryBool_1= rulePrimaryBool | this_Comparison_2= ruleComparison ) () ) ;
    public final EObject ruleNot() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject this_PrimaryBool_1 = null;

        EObject this_Comparison_2 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1049:28: ( ( (otherlv_0= '!' )? (this_PrimaryBool_1= rulePrimaryBool | this_Comparison_2= ruleComparison ) () ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1050:1: ( (otherlv_0= '!' )? (this_PrimaryBool_1= rulePrimaryBool | this_Comparison_2= ruleComparison ) () )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1050:1: ( (otherlv_0= '!' )? (this_PrimaryBool_1= rulePrimaryBool | this_Comparison_2= ruleComparison ) () )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1050:2: (otherlv_0= '!' )? (this_PrimaryBool_1= rulePrimaryBool | this_Comparison_2= ruleComparison ) ()
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1050:2: (otherlv_0= '!' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==30) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1050:4: otherlv_0= '!'
                    {
                    otherlv_0=(Token)match(input,30,FOLLOW_30_in_ruleNot2295); 

                        	newLeafNode(otherlv_0, grammarAccess.getNotAccess().getExclamationMarkKeyword_0());
                        

                    }
                    break;

            }

            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1054:3: (this_PrimaryBool_1= rulePrimaryBool | this_Comparison_2= ruleComparison )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=31 && LA18_0<=32)) ) {
                alt18=1;
            }
            else if ( ((LA18_0>=RULE_INT && LA18_0<=RULE_ID)||LA18_0==21||LA18_0==26) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1055:5: this_PrimaryBool_1= rulePrimaryBool
                    {
                     
                            newCompositeNode(grammarAccess.getNotAccess().getPrimaryBoolParserRuleCall_1_0()); 
                        
                    pushFollow(FOLLOW_rulePrimaryBool_in_ruleNot2320);
                    this_PrimaryBool_1=rulePrimaryBool();

                    state._fsp--;

                     
                            current = this_PrimaryBool_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1065:5: this_Comparison_2= ruleComparison
                    {
                     
                            newCompositeNode(grammarAccess.getNotAccess().getComparisonParserRuleCall_1_1()); 
                        
                    pushFollow(FOLLOW_ruleComparison_in_ruleNot2347);
                    this_Comparison_2=ruleComparison();

                    state._fsp--;

                     
                            current = this_Comparison_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1073:2: ()
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1074:5: 
            {

                    current = forceCreateModelElementAndSet(
                        grammarAccess.getNotAccess().getNotValAction_2(),
                        current);
                

            }


            }


            }

             leaveRule(); 
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1087:1: entryRulePrimaryBool returns [EObject current=null] : iv_rulePrimaryBool= rulePrimaryBool EOF ;
    public final EObject entryRulePrimaryBool() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryBool = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1088:2: (iv_rulePrimaryBool= rulePrimaryBool EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1089:2: iv_rulePrimaryBool= rulePrimaryBool EOF
            {
             newCompositeNode(grammarAccess.getPrimaryBoolRule()); 
            pushFollow(FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool2392);
            iv_rulePrimaryBool=rulePrimaryBool();

            state._fsp--;

             current =iv_rulePrimaryBool; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryBool2402); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1096:1: rulePrimaryBool returns [EObject current=null] : (this_True_0= ruleTrue | this_False_1= ruleFalse ) ;
    public final EObject rulePrimaryBool() throws RecognitionException {
        EObject current = null;

        EObject this_True_0 = null;

        EObject this_False_1 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1099:28: ( (this_True_0= ruleTrue | this_False_1= ruleFalse ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1100:1: (this_True_0= ruleTrue | this_False_1= ruleFalse )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1100:1: (this_True_0= ruleTrue | this_False_1= ruleFalse )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==31) ) {
                alt19=1;
            }
            else if ( (LA19_0==32) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1101:5: this_True_0= ruleTrue
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryBoolAccess().getTrueParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleTrue_in_rulePrimaryBool2449);
                    this_True_0=ruleTrue();

                    state._fsp--;

                     
                            current = this_True_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1111:5: this_False_1= ruleFalse
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryBoolAccess().getFalseParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleFalse_in_rulePrimaryBool2476);
                    this_False_1=ruleFalse();

                    state._fsp--;

                     
                            current = this_False_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleTrue"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1127:1: entryRuleTrue returns [EObject current=null] : iv_ruleTrue= ruleTrue EOF ;
    public final EObject entryRuleTrue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTrue = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1128:2: (iv_ruleTrue= ruleTrue EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1129:2: iv_ruleTrue= ruleTrue EOF
            {
             newCompositeNode(grammarAccess.getTrueRule()); 
            pushFollow(FOLLOW_ruleTrue_in_entryRuleTrue2511);
            iv_ruleTrue=ruleTrue();

            state._fsp--;

             current =iv_ruleTrue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTrue2521); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1136:1: ruleTrue returns [EObject current=null] : ( (lv_value_0_0= 'True' ) ) ;
    public final EObject ruleTrue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1139:28: ( ( (lv_value_0_0= 'True' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1140:1: ( (lv_value_0_0= 'True' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1140:1: ( (lv_value_0_0= 'True' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1141:1: (lv_value_0_0= 'True' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1141:1: (lv_value_0_0= 'True' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1142:3: lv_value_0_0= 'True'
            {
            lv_value_0_0=(Token)match(input,31,FOLLOW_31_in_ruleTrue2563); 

                    newLeafNode(lv_value_0_0, grammarAccess.getTrueAccess().getValueTrueKeyword_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getTrueRule());
            	        }
                   		setWithLastConsumed(current, "value", lv_value_0_0, "True");
            	    

            }


            }


            }

             leaveRule(); 
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1163:1: entryRuleFalse returns [EObject current=null] : iv_ruleFalse= ruleFalse EOF ;
    public final EObject entryRuleFalse() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFalse = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1164:2: (iv_ruleFalse= ruleFalse EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1165:2: iv_ruleFalse= ruleFalse EOF
            {
             newCompositeNode(grammarAccess.getFalseRule()); 
            pushFollow(FOLLOW_ruleFalse_in_entryRuleFalse2611);
            iv_ruleFalse=ruleFalse();

            state._fsp--;

             current =iv_ruleFalse; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFalse2621); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1172:1: ruleFalse returns [EObject current=null] : ( (lv_value_0_0= 'False' ) ) ;
    public final EObject ruleFalse() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1175:28: ( ( (lv_value_0_0= 'False' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1176:1: ( (lv_value_0_0= 'False' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1176:1: ( (lv_value_0_0= 'False' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1177:1: (lv_value_0_0= 'False' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1177:1: (lv_value_0_0= 'False' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1178:3: lv_value_0_0= 'False'
            {
            lv_value_0_0=(Token)match(input,32,FOLLOW_32_in_ruleFalse2663); 

                    newLeafNode(lv_value_0_0, grammarAccess.getFalseAccess().getValueFalseKeyword_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getFalseRule());
            	        }
                   		setWithLastConsumed(current, "value", lv_value_0_0, "False");
            	    

            }


            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleComparison"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1199:1: entryRuleComparison returns [EObject current=null] : iv_ruleComparison= ruleComparison EOF ;
    public final EObject entryRuleComparison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparison = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1200:2: (iv_ruleComparison= ruleComparison EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1201:2: iv_ruleComparison= ruleComparison EOF
            {
             newCompositeNode(grammarAccess.getComparisonRule()); 
            pushFollow(FOLLOW_ruleComparison_in_entryRuleComparison2711);
            iv_ruleComparison=ruleComparison();

            state._fsp--;

             current =iv_ruleComparison; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleComparison2721); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1208:1: ruleComparison returns [EObject current=null] : ( ( (lv_left_0_0= ruleAddition ) ) ( (lv_operator_1_0= ruleCompOperators ) ) ( (lv_right_2_0= ruleAddition ) ) ) ;
    public final EObject ruleComparison() throws RecognitionException {
        EObject current = null;

        EObject lv_left_0_0 = null;

        AntlrDatatypeRuleToken lv_operator_1_0 = null;

        EObject lv_right_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1211:28: ( ( ( (lv_left_0_0= ruleAddition ) ) ( (lv_operator_1_0= ruleCompOperators ) ) ( (lv_right_2_0= ruleAddition ) ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1212:1: ( ( (lv_left_0_0= ruleAddition ) ) ( (lv_operator_1_0= ruleCompOperators ) ) ( (lv_right_2_0= ruleAddition ) ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1212:1: ( ( (lv_left_0_0= ruleAddition ) ) ( (lv_operator_1_0= ruleCompOperators ) ) ( (lv_right_2_0= ruleAddition ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1212:2: ( (lv_left_0_0= ruleAddition ) ) ( (lv_operator_1_0= ruleCompOperators ) ) ( (lv_right_2_0= ruleAddition ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1212:2: ( (lv_left_0_0= ruleAddition ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1213:1: (lv_left_0_0= ruleAddition )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1213:1: (lv_left_0_0= ruleAddition )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1214:3: lv_left_0_0= ruleAddition
            {
             
            	        newCompositeNode(grammarAccess.getComparisonAccess().getLeftAdditionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleAddition_in_ruleComparison2767);
            lv_left_0_0=ruleAddition();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getComparisonRule());
            	        }
                   		set(
                   			current, 
                   			"left",
                    		lv_left_0_0, 
                    		"Addition");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1230:2: ( (lv_operator_1_0= ruleCompOperators ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1231:1: (lv_operator_1_0= ruleCompOperators )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1231:1: (lv_operator_1_0= ruleCompOperators )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1232:3: lv_operator_1_0= ruleCompOperators
            {
             
            	        newCompositeNode(grammarAccess.getComparisonAccess().getOperatorCompOperatorsParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleCompOperators_in_ruleComparison2788);
            lv_operator_1_0=ruleCompOperators();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getComparisonRule());
            	        }
                   		set(
                   			current, 
                   			"operator",
                    		lv_operator_1_0, 
                    		"CompOperators");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1248:2: ( (lv_right_2_0= ruleAddition ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1249:1: (lv_right_2_0= ruleAddition )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1249:1: (lv_right_2_0= ruleAddition )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1250:3: lv_right_2_0= ruleAddition
            {
             
            	        newCompositeNode(grammarAccess.getComparisonAccess().getRightAdditionParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleAddition_in_ruleComparison2809);
            lv_right_2_0=ruleAddition();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getComparisonRule());
            	        }
                   		set(
                   			current, 
                   			"right",
                    		lv_right_2_0, 
                    		"Addition");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleCompOperators"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1274:1: entryRuleCompOperators returns [String current=null] : iv_ruleCompOperators= ruleCompOperators EOF ;
    public final String entryRuleCompOperators() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCompOperators = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1275:2: (iv_ruleCompOperators= ruleCompOperators EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1276:2: iv_ruleCompOperators= ruleCompOperators EOF
            {
             newCompositeNode(grammarAccess.getCompOperatorsRule()); 
            pushFollow(FOLLOW_ruleCompOperators_in_entryRuleCompOperators2846);
            iv_ruleCompOperators=ruleCompOperators();

            state._fsp--;

             current =iv_ruleCompOperators.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCompOperators2857); 

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
    // $ANTLR end "entryRuleCompOperators"


    // $ANTLR start "ruleCompOperators"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1283:1: ruleCompOperators returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '<' | kw= '>' | kw= '>=' | kw= '<=' | kw= '==' | kw= '!=' ) ;
    public final AntlrDatatypeRuleToken ruleCompOperators() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1286:28: ( (kw= '<' | kw= '>' | kw= '>=' | kw= '<=' | kw= '==' | kw= '!=' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1287:1: (kw= '<' | kw= '>' | kw= '>=' | kw= '<=' | kw= '==' | kw= '!=' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1287:1: (kw= '<' | kw= '>' | kw= '>=' | kw= '<=' | kw= '==' | kw= '!=' )
            int alt20=6;
            switch ( input.LA(1) ) {
            case 33:
                {
                alt20=1;
                }
                break;
            case 34:
                {
                alt20=2;
                }
                break;
            case 35:
                {
                alt20=3;
                }
                break;
            case 36:
                {
                alt20=4;
                }
                break;
            case 37:
                {
                alt20=5;
                }
                break;
            case 38:
                {
                alt20=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1288:2: kw= '<'
                    {
                    kw=(Token)match(input,33,FOLLOW_33_in_ruleCompOperators2895); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getCompOperatorsAccess().getLessThanSignKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1295:2: kw= '>'
                    {
                    kw=(Token)match(input,34,FOLLOW_34_in_ruleCompOperators2914); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getCompOperatorsAccess().getGreaterThanSignKeyword_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1302:2: kw= '>='
                    {
                    kw=(Token)match(input,35,FOLLOW_35_in_ruleCompOperators2933); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getCompOperatorsAccess().getGreaterThanSignEqualsSignKeyword_2()); 
                        

                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1309:2: kw= '<='
                    {
                    kw=(Token)match(input,36,FOLLOW_36_in_ruleCompOperators2952); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getCompOperatorsAccess().getLessThanSignEqualsSignKeyword_3()); 
                        

                    }
                    break;
                case 5 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1316:2: kw= '=='
                    {
                    kw=(Token)match(input,37,FOLLOW_37_in_ruleCompOperators2971); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getCompOperatorsAccess().getEqualsSignEqualsSignKeyword_4()); 
                        

                    }
                    break;
                case 6 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1323:2: kw= '!='
                    {
                    kw=(Token)match(input,38,FOLLOW_38_in_ruleCompOperators2990); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getCompOperatorsAccess().getExclamationMarkEqualsSignKeyword_5()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCompOperators"


    // $ANTLR start "entryRuleQualifiedName"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1336:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1337:2: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1338:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameRule()); 
            pushFollow(FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName3031);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;

             current =iv_ruleQualifiedName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedName3042); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1345:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1348:28: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1349:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1349:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1349:6: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleQualifiedName3082); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1356:1: (kw= '.' this_ID_2= RULE_ID )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==39) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1357:2: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,39,FOLLOW_39_in_ruleQualifiedName3101); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
            	        
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleQualifiedName3116); 

            	    		current.merge(this_ID_2);
            	        
            	     
            	        newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleQualifiedNameWithWildCard"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1377:1: entryRuleQualifiedNameWithWildCard returns [String current=null] : iv_ruleQualifiedNameWithWildCard= ruleQualifiedNameWithWildCard EOF ;
    public final String entryRuleQualifiedNameWithWildCard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildCard = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1378:2: (iv_ruleQualifiedNameWithWildCard= ruleQualifiedNameWithWildCard EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1379:2: iv_ruleQualifiedNameWithWildCard= ruleQualifiedNameWithWildCard EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameWithWildCardRule()); 
            pushFollow(FOLLOW_ruleQualifiedNameWithWildCard_in_entryRuleQualifiedNameWithWildCard3164);
            iv_ruleQualifiedNameWithWildCard=ruleQualifiedNameWithWildCard();

            state._fsp--;

             current =iv_ruleQualifiedNameWithWildCard.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedNameWithWildCard3175); 

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
    // $ANTLR end "entryRuleQualifiedNameWithWildCard"


    // $ANTLR start "ruleQualifiedNameWithWildCard"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1386:1: ruleQualifiedNameWithWildCard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildCard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1389:28: ( (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1390:1: (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1390:1: (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1391:5: this_QualifiedName_0= ruleQualifiedName (kw= '.*' )?
            {
             
                    newCompositeNode(grammarAccess.getQualifiedNameWithWildCardAccess().getQualifiedNameParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleQualifiedNameWithWildCard3222);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;


            		current.merge(this_QualifiedName_0);
                
             
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1401:1: (kw= '.*' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==40) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:1402:2: kw= '.*'
                    {
                    kw=(Token)match(input,40,FOLLOW_40_in_ruleQualifiedNameWithWildCard3241); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQualifiedNameWithWildCardAccess().getFullStopAsteriskKeyword_1()); 
                        

                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedNameWithWildCard"

    // Delegated rules


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\6\uffff";
    static final String DFA4_eofS =
        "\6\uffff";
    static final String DFA4_minS =
        "\1\5\1\16\1\5\2\uffff\1\16";
    static final String DFA4_maxS =
        "\1\5\1\47\1\5\2\uffff\1\47";
    static final String DFA4_acceptS =
        "\3\uffff\1\1\1\2\1\uffff";
    static final String DFA4_specialS =
        "\6\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1",
            "\1\3\1\uffff\1\4\26\uffff\1\2",
            "\1\5",
            "",
            "",
            "\1\3\1\uffff\1\4\26\uffff\1\2"
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "167:1: ( ( ( (lv_name_0_0= ruleQualifiedName ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) | ( ( (lv_aname_4_0= ruleQualifiedName ) ) otherlv_5= '[' ( (lv_index_6_0= ruleAddition ) ) otherlv_7= ']' otherlv_8= '=' ( (lv_initVal_9_0= RULE_INT ) ) otherlv_10= ';' ) )";
        }
    }
 

    public static final BitSet FOLLOW_ruleSystem_in_entryRuleSystem75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSystem85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleSystem122 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleSystem143 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleSystem155 = new BitSet(new long[]{0x0000000000040020L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_ruleSystem176 = new BitSet(new long[]{0x0000000000040020L});
    public static final BitSet FOLLOW_ruleTransition_in_ruleSystem198 = new BitSet(new long[]{0x0000000000042020L});
    public static final BitSet FOLLOW_13_in_ruleSystem211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration248 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableDeclaration258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleVariableDeclaration305 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleVariableDeclaration317 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleVariableDeclaration334 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleVariableDeclaration351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleVariableDeclaration380 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleVariableDeclaration392 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleVariableDeclaration413 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleVariableDeclaration425 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleVariableDeclaration437 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleVariableDeclaration454 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleVariableDeclaration471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransition_in_entryRuleTransition508 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransition518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleTransition555 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTransition572 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleTransition589 = new BitSet(new long[]{0x00000001C4200030L});
    public static final BitSet FOLLOW_ruleOr_in_ruleTransition610 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleTransition622 = new BitSet(new long[]{0x0000000000081000L});
    public static final BitSet FOLLOW_19_in_ruleTransition635 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleTransition652 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleTransition671 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleAssignment_in_ruleTransition692 = new BitSet(new long[]{0x0000000000002020L});
    public static final BitSet FOLLOW_13_in_ruleTransition705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_entryRuleAssignment741 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssignment751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_ruleAssignment798 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleAssignment810 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleAssignment831 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleAssignment843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_ruleAssignment872 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleAssignment884 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleAssignment905 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleAssignment917 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleAssignment929 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleAssignment950 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleAssignment962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_entryRuleAddition999 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAddition1009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition1056 = new BitSet(new long[]{0x0000000000300002L});
    public static final BitSet FOLLOW_20_in_ruleAddition1079 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_21_in_ruleAddition1108 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition1131 = new BitSet(new long[]{0x0000000000300002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_entryRuleMultiplication1169 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplication1179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnitaryMinus_in_ruleMultiplication1226 = new BitSet(new long[]{0x0000000001C00002L});
    public static final BitSet FOLLOW_22_in_ruleMultiplication1249 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_23_in_ruleMultiplication1278 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_24_in_ruleMultiplication1307 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_ruleUnitaryMinus_in_ruleMultiplication1330 = new BitSet(new long[]{0x0000000001C00002L});
    public static final BitSet FOLLOW_ruleUnitaryMinus_in_entryRuleUnitaryMinus1368 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnitaryMinus1378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleUnitaryMinus1416 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_rulePower_in_ruleUnitaryMinus1440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePower_in_entryRulePower1484 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePower1494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_rulePower1541 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_rulePower1562 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_rulePrimary_in_rulePower1583 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_rulePrimary_in_entryRulePrimary1621 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimary1631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rulePrimary1678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_rulePrimary1705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rulePrimary1723 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_ruleAddition_in_rulePrimary1745 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_rulePrimary1756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_entryRuleConstante1793 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstante1803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleConstante1844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_entryRuleVariableRef1884 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableRef1894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleVariableRef1938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_entryRuleOr1973 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOr1983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_ruleOr2030 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_ruleOr2051 = new BitSet(new long[]{0x00000001C4200030L});
    public static final BitSet FOLLOW_ruleAnd_in_ruleOr2072 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_ruleAnd_in_entryRuleAnd2110 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAnd2120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_ruleAnd2167 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_29_in_ruleAnd2188 = new BitSet(new long[]{0x00000001C4200030L});
    public static final BitSet FOLLOW_ruleNot_in_ruleAnd2209 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_ruleNot_in_entryRuleNot2247 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNot2257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_ruleNot2295 = new BitSet(new long[]{0x00000001C4200030L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_ruleNot2320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_ruleNot2347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool2392 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryBool2402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_rulePrimaryBool2449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_rulePrimaryBool2476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_entryRuleTrue2511 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTrue2521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleTrue2563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_entryRuleFalse2611 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFalse2621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleFalse2663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_entryRuleComparison2711 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComparison2721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleComparison2767 = new BitSet(new long[]{0x0000007E00000000L});
    public static final BitSet FOLLOW_ruleCompOperators_in_ruleComparison2788 = new BitSet(new long[]{0x0000000004200030L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleComparison2809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleCompOperators_in_entryRuleCompOperators2846 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCompOperators2857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_ruleCompOperators2895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_ruleCompOperators2914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleCompOperators2933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleCompOperators2952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleCompOperators2971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_ruleCompOperators2990 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName3031 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedName3042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleQualifiedName3082 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_39_in_ruleQualifiedName3101 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleQualifiedName3116 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_ruleQualifiedNameWithWildCard_in_entryRuleQualifiedNameWithWildCard3164 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedNameWithWildCard3175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleQualifiedNameWithWildCard3222 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_40_in_ruleQualifiedNameWithWildCard3241 = new BitSet(new long[]{0x0000000000000002L});

}