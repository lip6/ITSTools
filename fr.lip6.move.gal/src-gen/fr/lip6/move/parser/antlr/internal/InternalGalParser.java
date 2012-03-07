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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'system'", "'{'", "'}'", "'='", "';'", "'transition'", "'['", "'TRUE'", "']'", "'label'", "'+'", "'-'", "'*'", "'/'", "'%'", "'**'", "'('", "')'"
    };
    public static final int RULE_ID=4;
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:76:1: ruleSystem returns [EObject current=null] : (otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )? ;
    public final EObject ruleSystem() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        EObject lv_variables_3_0 = null;

        EObject lv_transitions_4_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:79:28: ( (otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )? )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:1: (otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )?
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:1: (otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==11) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:3: otherlv_0= 'system' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}'
                    {
                    otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleSystem122); 

                        	newLeafNode(otherlv_0, grammarAccess.getSystemAccess().getSystemKeyword_0());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:84:1: ( (lv_name_1_0= RULE_ID ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:85:1: (lv_name_1_0= RULE_ID )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:85:1: (lv_name_1_0= RULE_ID )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:86:3: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleSystem139); 

                    			newLeafNode(lv_name_1_0, grammarAccess.getSystemAccess().getNameIDTerminalRuleCall_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getSystemRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleSystem156); 

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
                    	    	    
                    	    pushFollow(FOLLOW_ruleVariableDeclaration_in_ruleSystem177);
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

                        if ( (LA2_0==16) ) {
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
                    	    	    
                    	    pushFollow(FOLLOW_ruleTransition_in_ruleSystem199);
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

                    otherlv_5=(Token)match(input,13,FOLLOW_13_in_ruleSystem212); 

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
            pushFollow(FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration249);
            iv_ruleVariableDeclaration=ruleVariableDeclaration();

            state._fsp--;

             current =iv_ruleVariableDeclaration; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableDeclaration259); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:163:1: ruleVariableDeclaration returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) ;
    public final EObject ruleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_initVal_2_0=null;
        Token otherlv_3=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:166:28: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:1: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:1: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:2: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';'
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:167:2: ( (lv_name_0_0= RULE_ID ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:168:1: (lv_name_0_0= RULE_ID )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:168:1: (lv_name_0_0= RULE_ID )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:169:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleVariableDeclaration301); 

            			newLeafNode(lv_name_0_0, grammarAccess.getVariableDeclarationAccess().getNameIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getVariableDeclarationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"ID");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleVariableDeclaration318); 

                	newLeafNode(otherlv_1, grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_1());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:189:1: ( (lv_initVal_2_0= RULE_INT ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:190:1: (lv_initVal_2_0= RULE_INT )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:190:1: (lv_initVal_2_0= RULE_INT )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:191:3: lv_initVal_2_0= RULE_INT
            {
            lv_initVal_2_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleVariableDeclaration335); 

            			newLeafNode(lv_initVal_2_0, grammarAccess.getVariableDeclarationAccess().getInitValINTTerminalRuleCall_2_0()); 
            		

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

            otherlv_3=(Token)match(input,15,FOLLOW_15_in_ruleVariableDeclaration352); 

                	newLeafNode(otherlv_3, grammarAccess.getVariableDeclarationAccess().getSemicolonKeyword_3());
                

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:219:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:220:2: (iv_ruleTransition= ruleTransition EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:221:2: iv_ruleTransition= ruleTransition EOF
            {
             newCompositeNode(grammarAccess.getTransitionRule()); 
            pushFollow(FOLLOW_ruleTransition_in_entryRuleTransition388);
            iv_ruleTransition=ruleTransition();

            state._fsp--;

             current =iv_ruleTransition; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransition398); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:228:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' ) ;
    public final EObject ruleTransition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_label_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_assignments_8_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:231:28: ( (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:232:1: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:232:1: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:232:3: otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_16_in_ruleTransition435); 

                	newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:236:1: ( (lv_name_1_0= RULE_ID ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:237:1: (lv_name_1_0= RULE_ID )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:237:1: (lv_name_1_0= RULE_ID )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:238:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTransition452); 

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

            otherlv_2=(Token)match(input,17,FOLLOW_17_in_ruleTransition469); 

                	newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getLeftSquareBracketKeyword_2());
                
            otherlv_3=(Token)match(input,18,FOLLOW_18_in_ruleTransition481); 

                	newLeafNode(otherlv_3, grammarAccess.getTransitionAccess().getTRUEKeyword_3());
                
            otherlv_4=(Token)match(input,19,FOLLOW_19_in_ruleTransition493); 

                	newLeafNode(otherlv_4, grammarAccess.getTransitionAccess().getRightSquareBracketKeyword_4());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:266:1: (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==20) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:266:3: otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,20,FOLLOW_20_in_ruleTransition506); 

                        	newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getLabelKeyword_5_0());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:270:1: ( (lv_label_6_0= RULE_STRING ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:271:1: (lv_label_6_0= RULE_STRING )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:271:1: (lv_label_6_0= RULE_STRING )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:272:3: lv_label_6_0= RULE_STRING
                    {
                    lv_label_6_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleTransition523); 

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

            otherlv_7=(Token)match(input,12,FOLLOW_12_in_ruleTransition542); 

                	newLeafNode(otherlv_7, grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_6());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:292:1: ( (lv_assignments_8_0= ruleAssignment ) )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==RULE_ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:293:1: (lv_assignments_8_0= ruleAssignment )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:293:1: (lv_assignments_8_0= ruleAssignment )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:294:3: lv_assignments_8_0= ruleAssignment
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getTransitionAccess().getAssignmentsAssignmentParserRuleCall_7_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleAssignment_in_ruleTransition563);
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
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);

            otherlv_9=(Token)match(input,13,FOLLOW_13_in_ruleTransition576); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:322:1: entryRuleAssignment returns [EObject current=null] : iv_ruleAssignment= ruleAssignment EOF ;
    public final EObject entryRuleAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssignment = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:323:2: (iv_ruleAssignment= ruleAssignment EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:324:2: iv_ruleAssignment= ruleAssignment EOF
            {
             newCompositeNode(grammarAccess.getAssignmentRule()); 
            pushFollow(FOLLOW_ruleAssignment_in_entryRuleAssignment612);
            iv_ruleAssignment=ruleAssignment();

            state._fsp--;

             current =iv_ruleAssignment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssignment622); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:331:1: ruleAssignment returns [EObject current=null] : ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) ;
    public final EObject ruleAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_var_0_0 = null;

        EObject lv_expr_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:334:28: ( ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:335:1: ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:335:1: ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:335:2: ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';'
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:335:2: ( (lv_var_0_0= ruleVariableRef ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:336:1: (lv_var_0_0= ruleVariableRef )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:336:1: (lv_var_0_0= ruleVariableRef )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:337:3: lv_var_0_0= ruleVariableRef
            {
             
            	        newCompositeNode(grammarAccess.getAssignmentAccess().getVarVariableRefParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleVariableRef_in_ruleAssignment668);
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

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleAssignment680); 

                	newLeafNode(otherlv_1, grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:357:1: ( (lv_expr_2_0= ruleAddition ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:358:1: (lv_expr_2_0= ruleAddition )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:358:1: (lv_expr_2_0= ruleAddition )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:359:3: lv_expr_2_0= ruleAddition
            {
             
            	        newCompositeNode(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleAddition_in_ruleAssignment701);
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

            otherlv_3=(Token)match(input,15,FOLLOW_15_in_ruleAssignment713); 

                	newLeafNode(otherlv_3, grammarAccess.getAssignmentAccess().getSemicolonKeyword_3());
                

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:387:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:388:2: (iv_ruleAddition= ruleAddition EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:389:2: iv_ruleAddition= ruleAddition EOF
            {
             newCompositeNode(grammarAccess.getAdditionRule()); 
            pushFollow(FOLLOW_ruleAddition_in_entryRuleAddition749);
            iv_ruleAddition=ruleAddition();

            state._fsp--;

             current =iv_ruleAddition; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAddition759); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:396:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_Multiplication_0 = null;

        EObject lv_right_5_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:399:28: ( (this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:400:1: (this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:400:1: (this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:401:5: this_Multiplication_0= ruleMultiplication ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition806);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;

             
                    current = this_Multiplication_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:409:1: ( ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=21 && LA7_0<=22)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:409:2: ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) ) ( (lv_right_5_0= ruleMultiplication ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:409:2: ( ( () otherlv_2= '+' ) | ( () otherlv_4= '-' ) )
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( (LA6_0==21) ) {
            	        alt6=1;
            	    }
            	    else if ( (LA6_0==22) ) {
            	        alt6=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 6, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt6) {
            	        case 1 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:409:3: ( () otherlv_2= '+' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:409:3: ( () otherlv_2= '+' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:409:4: () otherlv_2= '+'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:409:4: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:410:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0_0_0(),
            	                        current);
            	                

            	            }

            	            otherlv_2=(Token)match(input,21,FOLLOW_21_in_ruleAddition829); 

            	                	newLeafNode(otherlv_2, grammarAccess.getAdditionAccess().getPlusSignKeyword_1_0_0_1());
            	                

            	            }


            	            }
            	            break;
            	        case 2 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:6: ( () otherlv_4= '-' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:6: ( () otherlv_4= '-' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:7: () otherlv_4= '-'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:420:7: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:421:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getAdditionAccess().getSubtractionLeftAction_1_0_1_0(),
            	                        current);
            	                

            	            }

            	            otherlv_4=(Token)match(input,22,FOLLOW_22_in_ruleAddition858); 

            	                	newLeafNode(otherlv_4, grammarAccess.getAdditionAccess().getHyphenMinusKeyword_1_0_1_1());
            	                

            	            }


            	            }
            	            break;

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:430:3: ( (lv_right_5_0= ruleMultiplication ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:431:1: (lv_right_5_0= ruleMultiplication )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:431:1: (lv_right_5_0= ruleMultiplication )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:432:3: lv_right_5_0= ruleMultiplication
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition881);
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
            	    break loop7;
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:456:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:457:2: (iv_ruleMultiplication= ruleMultiplication EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:458:2: iv_ruleMultiplication= ruleMultiplication EOF
            {
             newCompositeNode(grammarAccess.getMultiplicationRule()); 
            pushFollow(FOLLOW_ruleMultiplication_in_entryRuleMultiplication919);
            iv_ruleMultiplication=ruleMultiplication();

            state._fsp--;

             current =iv_ruleMultiplication; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplication929); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:465:1: ruleMultiplication returns [EObject current=null] : (this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject this_UnitaryMinus_0 = null;

        EObject lv_right_7_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:468:28: ( (this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:469:1: (this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:469:1: (this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:470:5: this_UnitaryMinus_0= ruleUnitaryMinus ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getMultiplicationAccess().getUnitaryMinusParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleUnitaryMinus_in_ruleMultiplication976);
            this_UnitaryMinus_0=ruleUnitaryMinus();

            state._fsp--;

             
                    current = this_UnitaryMinus_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:478:1: ( ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>=23 && LA9_0<=25)) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:478:2: ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) ) ( (lv_right_7_0= ruleUnitaryMinus ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:478:2: ( ( () otherlv_2= '*' ) | ( () otherlv_4= '/' ) | ( () otherlv_6= '%' ) )
            	    int alt8=3;
            	    switch ( input.LA(1) ) {
            	    case 23:
            	        {
            	        alt8=1;
            	        }
            	        break;
            	    case 24:
            	        {
            	        alt8=2;
            	        }
            	        break;
            	    case 25:
            	        {
            	        alt8=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 8, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt8) {
            	        case 1 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:478:3: ( () otherlv_2= '*' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:478:3: ( () otherlv_2= '*' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:478:4: () otherlv_2= '*'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:478:4: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:479:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0_0_0(),
            	                        current);
            	                

            	            }

            	            otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleMultiplication999); 

            	                	newLeafNode(otherlv_2, grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_0_0_1());
            	                

            	            }


            	            }
            	            break;
            	        case 2 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:489:6: ( () otherlv_4= '/' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:489:6: ( () otherlv_4= '/' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:489:7: () otherlv_4= '/'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:489:7: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:490:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getMultiplicationAccess().getDivisionLeftAction_1_0_1_0(),
            	                        current);
            	                

            	            }

            	            otherlv_4=(Token)match(input,24,FOLLOW_24_in_ruleMultiplication1028); 

            	                	newLeafNode(otherlv_4, grammarAccess.getMultiplicationAccess().getSolidusKeyword_1_0_1_1());
            	                

            	            }


            	            }
            	            break;
            	        case 3 :
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:500:6: ( () otherlv_6= '%' )
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:500:6: ( () otherlv_6= '%' )
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:500:7: () otherlv_6= '%'
            	            {
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:500:7: ()
            	            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:501:5: 
            	            {

            	                    current = forceCreateModelElementAndSet(
            	                        grammarAccess.getMultiplicationAccess().getModuloLeftAction_1_0_2_0(),
            	                        current);
            	                

            	            }

            	            otherlv_6=(Token)match(input,25,FOLLOW_25_in_ruleMultiplication1057); 

            	                	newLeafNode(otherlv_6, grammarAccess.getMultiplicationAccess().getPercentSignKeyword_1_0_2_1());
            	                

            	            }


            	            }
            	            break;

            	    }

            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:510:3: ( (lv_right_7_0= ruleUnitaryMinus ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:511:1: (lv_right_7_0= ruleUnitaryMinus )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:511:1: (lv_right_7_0= ruleUnitaryMinus )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:512:3: lv_right_7_0= ruleUnitaryMinus
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMultiplicationAccess().getRightUnitaryMinusParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleUnitaryMinus_in_ruleMultiplication1080);
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
    // $ANTLR end "ruleMultiplication"


    // $ANTLR start "entryRuleUnitaryMinus"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:536:1: entryRuleUnitaryMinus returns [EObject current=null] : iv_ruleUnitaryMinus= ruleUnitaryMinus EOF ;
    public final EObject entryRuleUnitaryMinus() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitaryMinus = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:537:2: (iv_ruleUnitaryMinus= ruleUnitaryMinus EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:538:2: iv_ruleUnitaryMinus= ruleUnitaryMinus EOF
            {
             newCompositeNode(grammarAccess.getUnitaryMinusRule()); 
            pushFollow(FOLLOW_ruleUnitaryMinus_in_entryRuleUnitaryMinus1118);
            iv_ruleUnitaryMinus=ruleUnitaryMinus();

            state._fsp--;

             current =iv_ruleUnitaryMinus; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnitaryMinus1128); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:545:1: ruleUnitaryMinus returns [EObject current=null] : ( (otherlv_0= '-' )? this_Power_1= rulePower () ) ;
    public final EObject ruleUnitaryMinus() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject this_Power_1 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:548:28: ( ( (otherlv_0= '-' )? this_Power_1= rulePower () ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:549:1: ( (otherlv_0= '-' )? this_Power_1= rulePower () )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:549:1: ( (otherlv_0= '-' )? this_Power_1= rulePower () )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:549:2: (otherlv_0= '-' )? this_Power_1= rulePower ()
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:549:2: (otherlv_0= '-' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==22) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:549:4: otherlv_0= '-'
                    {
                    otherlv_0=(Token)match(input,22,FOLLOW_22_in_ruleUnitaryMinus1166); 

                        	newLeafNode(otherlv_0, grammarAccess.getUnitaryMinusAccess().getHyphenMinusKeyword_0());
                        

                    }
                    break;

            }

             
                    newCompositeNode(grammarAccess.getUnitaryMinusAccess().getPowerParserRuleCall_1()); 
                
            pushFollow(FOLLOW_rulePower_in_ruleUnitaryMinus1190);
            this_Power_1=rulePower();

            state._fsp--;

             
                    current = this_Power_1; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:562:1: ()
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:563:5: 
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:576:1: entryRulePower returns [EObject current=null] : iv_rulePower= rulePower EOF ;
    public final EObject entryRulePower() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePower = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:577:2: (iv_rulePower= rulePower EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:578:2: iv_rulePower= rulePower EOF
            {
             newCompositeNode(grammarAccess.getPowerRule()); 
            pushFollow(FOLLOW_rulePower_in_entryRulePower1234);
            iv_rulePower=rulePower();

            state._fsp--;

             current =iv_rulePower; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePower1244); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:585:1: rulePower returns [EObject current=null] : (this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )* ) ;
    public final EObject rulePower() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Primary_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:588:28: ( (this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:589:1: (this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:589:1: (this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:590:5: this_Primary_0= rulePrimary ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getPowerAccess().getPrimaryParserRuleCall_0()); 
                
            pushFollow(FOLLOW_rulePrimary_in_rulePower1291);
            this_Primary_0=rulePrimary();

            state._fsp--;

             
                    current = this_Primary_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:598:1: ( () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==26) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:598:2: () otherlv_2= '**' ( (lv_right_3_0= rulePrimary ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:598:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:599:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getPowerAccess().getPowerLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    otherlv_2=(Token)match(input,26,FOLLOW_26_in_rulePower1312); 

            	        	newLeafNode(otherlv_2, grammarAccess.getPowerAccess().getAsteriskAsteriskKeyword_1_1());
            	        
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:608:1: ( (lv_right_3_0= rulePrimary ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:609:1: (lv_right_3_0= rulePrimary )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:609:1: (lv_right_3_0= rulePrimary )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:610:3: lv_right_3_0= rulePrimary
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getPowerAccess().getRightPrimaryParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePrimary_in_rulePower1333);
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
    // $ANTLR end "rulePower"


    // $ANTLR start "entryRulePrimary"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:634:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:635:2: (iv_rulePrimary= rulePrimary EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:636:2: iv_rulePrimary= rulePrimary EOF
            {
             newCompositeNode(grammarAccess.getPrimaryRule()); 
            pushFollow(FOLLOW_rulePrimary_in_entryRulePrimary1371);
            iv_rulePrimary=rulePrimary();

            state._fsp--;

             current =iv_rulePrimary; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimary1381); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:643:1: rulePrimary returns [EObject current=null] : (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_VariableRef_0 = null;

        EObject this_Constante_1 = null;

        EObject this_Addition_3 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:646:28: ( (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:647:1: (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:647:1: (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) )
            int alt12=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt12=1;
                }
                break;
            case RULE_INT:
                {
                alt12=2;
                }
                break;
            case 27:
                {
                alt12=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:648:5: this_VariableRef_0= ruleVariableRef
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getVariableRefParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleVariableRef_in_rulePrimary1428);
                    this_VariableRef_0=ruleVariableRef();

                    state._fsp--;

                     
                            current = this_VariableRef_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:658:5: this_Constante_1= ruleConstante
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleConstante_in_rulePrimary1455);
                    this_Constante_1=ruleConstante();

                    state._fsp--;

                     
                            current = this_Constante_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:667:6: (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:667:6: (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:667:8: otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,27,FOLLOW_27_in_rulePrimary1473); 

                        	newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0());
                        
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getAdditionParserRuleCall_2_1()); 
                        
                    pushFollow(FOLLOW_ruleAddition_in_rulePrimary1495);
                    this_Addition_3=ruleAddition();

                    state._fsp--;

                     
                            current = this_Addition_3; 
                            afterParserOrEnumRuleCall();
                        
                    otherlv_4=(Token)match(input,28,FOLLOW_28_in_rulePrimary1506); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:692:1: entryRuleConstante returns [EObject current=null] : iv_ruleConstante= ruleConstante EOF ;
    public final EObject entryRuleConstante() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstante = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:693:2: (iv_ruleConstante= ruleConstante EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:694:2: iv_ruleConstante= ruleConstante EOF
            {
             newCompositeNode(grammarAccess.getConstanteRule()); 
            pushFollow(FOLLOW_ruleConstante_in_entryRuleConstante1543);
            iv_ruleConstante=ruleConstante();

            state._fsp--;

             current =iv_ruleConstante; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstante1553); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:701:1: ruleConstante returns [EObject current=null] : ( (lv_val_0_0= RULE_INT ) ) ;
    public final EObject ruleConstante() throws RecognitionException {
        EObject current = null;

        Token lv_val_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:704:28: ( ( (lv_val_0_0= RULE_INT ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:705:1: ( (lv_val_0_0= RULE_INT ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:705:1: ( (lv_val_0_0= RULE_INT ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:706:1: (lv_val_0_0= RULE_INT )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:706:1: (lv_val_0_0= RULE_INT )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:707:3: lv_val_0_0= RULE_INT
            {
            lv_val_0_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleConstante1594); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:731:1: entryRuleVariableRef returns [EObject current=null] : iv_ruleVariableRef= ruleVariableRef EOF ;
    public final EObject entryRuleVariableRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableRef = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:732:2: (iv_ruleVariableRef= ruleVariableRef EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:733:2: iv_ruleVariableRef= ruleVariableRef EOF
            {
             newCompositeNode(grammarAccess.getVariableRefRule()); 
            pushFollow(FOLLOW_ruleVariableRef_in_entryRuleVariableRef1634);
            iv_ruleVariableRef=ruleVariableRef();

            state._fsp--;

             current =iv_ruleVariableRef; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableRef1644); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:740:1: ruleVariableRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleVariableRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:743:28: ( ( (otherlv_0= RULE_ID ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:744:1: ( (otherlv_0= RULE_ID ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:744:1: ( (otherlv_0= RULE_ID ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:745:1: (otherlv_0= RULE_ID )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:745:1: (otherlv_0= RULE_ID )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:746:3: otherlv_0= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getVariableRefRule());
            	        }
                    
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleVariableRef1688); 

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

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleSystem_in_entryRuleSystem75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSystem85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleSystem122 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleSystem139 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleSystem156 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_ruleSystem177 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_ruleTransition_in_ruleSystem199 = new BitSet(new long[]{0x0000000000012010L});
    public static final BitSet FOLLOW_13_in_ruleSystem212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration249 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableDeclaration259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleVariableDeclaration301 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleVariableDeclaration318 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleVariableDeclaration335 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleVariableDeclaration352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransition_in_entryRuleTransition388 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransition398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleTransition435 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTransition452 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleTransition469 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleTransition481 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleTransition493 = new BitSet(new long[]{0x0000000000101000L});
    public static final BitSet FOLLOW_20_in_ruleTransition506 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleTransition523 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleTransition542 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleAssignment_in_ruleTransition563 = new BitSet(new long[]{0x0000000000002010L});
    public static final BitSet FOLLOW_13_in_ruleTransition576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_entryRuleAssignment612 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssignment622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_ruleAssignment668 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleAssignment680 = new BitSet(new long[]{0x0000000008400030L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleAssignment701 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleAssignment713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_entryRuleAddition749 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAddition759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition806 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_21_in_ruleAddition829 = new BitSet(new long[]{0x0000000008400030L});
    public static final BitSet FOLLOW_22_in_ruleAddition858 = new BitSet(new long[]{0x0000000008400030L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition881 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_entryRuleMultiplication919 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplication929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnitaryMinus_in_ruleMultiplication976 = new BitSet(new long[]{0x0000000003800002L});
    public static final BitSet FOLLOW_23_in_ruleMultiplication999 = new BitSet(new long[]{0x0000000008400030L});
    public static final BitSet FOLLOW_24_in_ruleMultiplication1028 = new BitSet(new long[]{0x0000000008400030L});
    public static final BitSet FOLLOW_25_in_ruleMultiplication1057 = new BitSet(new long[]{0x0000000008400030L});
    public static final BitSet FOLLOW_ruleUnitaryMinus_in_ruleMultiplication1080 = new BitSet(new long[]{0x0000000003800002L});
    public static final BitSet FOLLOW_ruleUnitaryMinus_in_entryRuleUnitaryMinus1118 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnitaryMinus1128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleUnitaryMinus1166 = new BitSet(new long[]{0x0000000008400030L});
    public static final BitSet FOLLOW_rulePower_in_ruleUnitaryMinus1190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePower_in_entryRulePower1234 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePower1244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_rulePower1291 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_26_in_rulePower1312 = new BitSet(new long[]{0x0000000008400030L});
    public static final BitSet FOLLOW_rulePrimary_in_rulePower1333 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_rulePrimary_in_entryRulePrimary1371 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimary1381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rulePrimary1428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_rulePrimary1455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rulePrimary1473 = new BitSet(new long[]{0x0000000008400030L});
    public static final BitSet FOLLOW_ruleAddition_in_rulePrimary1495 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_rulePrimary1506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_entryRuleConstante1543 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstante1553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleConstante1594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_entryRuleVariableRef1634 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableRef1644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleVariableRef1688 = new BitSet(new long[]{0x0000000000000002L});

}