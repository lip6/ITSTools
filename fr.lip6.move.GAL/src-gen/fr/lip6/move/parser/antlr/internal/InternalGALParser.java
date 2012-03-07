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
import fr.lip6.move.services.GALGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalGALParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'System'", "'{'", "'}'", "'='", "';'", "'transition'", "'['", "'TRUE'", "']'", "'label'", "'*'", "'('", "')'", "'+'"
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


        public InternalGALParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalGALParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalGALParser.tokenNames; }
    public String getGrammarFileName() { return "../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g"; }



     	private GALGrammarAccess grammarAccess;
     	
        public InternalGALParser(TokenStream input, GALGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "System";	
       	}
       	
       	@Override
       	protected GALGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleSystem"
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:67:1: entryRuleSystem returns [EObject current=null] : iv_ruleSystem= ruleSystem EOF ;
    public final EObject entryRuleSystem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSystem = null;


        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:68:2: (iv_ruleSystem= ruleSystem EOF )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:69:2: iv_ruleSystem= ruleSystem EOF
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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:76:1: ruleSystem returns [EObject current=null] : (otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )? ;
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
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:79:28: ( (otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )? )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:80:1: (otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )?
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:80:1: (otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==11) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:80:3: otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTransition ) )+ otherlv_5= '}'
                    {
                    otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleSystem122); 

                        	newLeafNode(otherlv_0, grammarAccess.getSystemAccess().getSystemKeyword_0());
                        
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:84:1: ( (lv_name_1_0= RULE_ID ) )
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:85:1: (lv_name_1_0= RULE_ID )
                    {
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:85:1: (lv_name_1_0= RULE_ID )
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:86:3: lv_name_1_0= RULE_ID
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
                        
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:106:1: ( (lv_variables_3_0= ruleVariableDeclaration ) )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==RULE_ID) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:107:1: (lv_variables_3_0= ruleVariableDeclaration )
                    	    {
                    	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:107:1: (lv_variables_3_0= ruleVariableDeclaration )
                    	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:108:3: lv_variables_3_0= ruleVariableDeclaration
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

                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:124:3: ( (lv_transitions_4_0= ruleTransition ) )+
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
                    	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:125:1: (lv_transitions_4_0= ruleTransition )
                    	    {
                    	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:125:1: (lv_transitions_4_0= ruleTransition )
                    	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:126:3: lv_transitions_4_0= ruleTransition
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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:154:1: entryRuleVariableDeclaration returns [EObject current=null] : iv_ruleVariableDeclaration= ruleVariableDeclaration EOF ;
    public final EObject entryRuleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableDeclaration = null;


        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:155:2: (iv_ruleVariableDeclaration= ruleVariableDeclaration EOF )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:156:2: iv_ruleVariableDeclaration= ruleVariableDeclaration EOF
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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:163:1: ruleVariableDeclaration returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) ;
    public final EObject ruleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_initVal_2_0=null;
        Token otherlv_3=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:166:28: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:167:1: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:167:1: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:167:2: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';'
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:167:2: ( (lv_name_0_0= RULE_ID ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:168:1: (lv_name_0_0= RULE_ID )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:168:1: (lv_name_0_0= RULE_ID )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:169:3: lv_name_0_0= RULE_ID
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
                
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:189:1: ( (lv_initVal_2_0= RULE_INT ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:190:1: (lv_initVal_2_0= RULE_INT )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:190:1: (lv_initVal_2_0= RULE_INT )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:191:3: lv_initVal_2_0= RULE_INT
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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:219:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:220:2: (iv_ruleTransition= ruleTransition EOF )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:221:2: iv_ruleTransition= ruleTransition EOF
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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:228:1: ruleTransition returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' ) ;
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
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:231:28: ( (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:232:1: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:232:1: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}' )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:232:3: otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? otherlv_7= '{' ( (lv_assignments_8_0= ruleAssignment ) )+ otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_16_in_ruleTransition435); 

                	newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getTransitionKeyword_0());
                
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:236:1: ( (lv_name_1_0= RULE_ID ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:237:1: (lv_name_1_0= RULE_ID )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:237:1: (lv_name_1_0= RULE_ID )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:238:3: lv_name_1_0= RULE_ID
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
                
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:266:1: (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==20) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:266:3: otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,20,FOLLOW_20_in_ruleTransition506); 

                        	newLeafNode(otherlv_5, grammarAccess.getTransitionAccess().getLabelKeyword_5_0());
                        
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:270:1: ( (lv_label_6_0= RULE_STRING ) )
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:271:1: (lv_label_6_0= RULE_STRING )
                    {
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:271:1: (lv_label_6_0= RULE_STRING )
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:272:3: lv_label_6_0= RULE_STRING
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
                
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:292:1: ( (lv_assignments_8_0= ruleAssignment ) )+
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
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:293:1: (lv_assignments_8_0= ruleAssignment )
            	    {
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:293:1: (lv_assignments_8_0= ruleAssignment )
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:294:3: lv_assignments_8_0= ruleAssignment
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


    // $ANTLR start "entryRuleConstante"
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:322:1: entryRuleConstante returns [EObject current=null] : iv_ruleConstante= ruleConstante EOF ;
    public final EObject entryRuleConstante() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstante = null;


        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:323:2: (iv_ruleConstante= ruleConstante EOF )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:324:2: iv_ruleConstante= ruleConstante EOF
            {
             newCompositeNode(grammarAccess.getConstanteRule()); 
            pushFollow(FOLLOW_ruleConstante_in_entryRuleConstante612);
            iv_ruleConstante=ruleConstante();

            state._fsp--;

             current =iv_ruleConstante; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstante622); 

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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:331:1: ruleConstante returns [EObject current=null] : ( (lv_value_0_0= RULE_INT ) ) ;
    public final EObject ruleConstante() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:334:28: ( ( (lv_value_0_0= RULE_INT ) ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:335:1: ( (lv_value_0_0= RULE_INT ) )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:335:1: ( (lv_value_0_0= RULE_INT ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:336:1: (lv_value_0_0= RULE_INT )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:336:1: (lv_value_0_0= RULE_INT )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:337:3: lv_value_0_0= RULE_INT
            {
            lv_value_0_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleConstante663); 

            			newLeafNode(lv_value_0_0, grammarAccess.getConstanteAccess().getValueINTTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getConstanteRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
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


    // $ANTLR start "entryRuleMultiplication"
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:361:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:362:2: (iv_ruleMultiplication= ruleMultiplication EOF )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:363:2: iv_ruleMultiplication= ruleMultiplication EOF
            {
             newCompositeNode(grammarAccess.getMultiplicationRule()); 
            pushFollow(FOLLOW_ruleMultiplication_in_entryRuleMultiplication703);
            iv_ruleMultiplication=ruleMultiplication();

            state._fsp--;

             current =iv_ruleMultiplication; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplication713); 

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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:370:1: ruleMultiplication returns [EObject current=null] : (this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Primary_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:373:28: ( (this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )* ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:374:1: (this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )* )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:374:1: (this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )* )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:375:5: this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getMultiplicationAccess().getPrimaryParserRuleCall_0()); 
                
            pushFollow(FOLLOW_rulePrimary_in_ruleMultiplication760);
            this_Primary_0=rulePrimary();

            state._fsp--;

             
                    current = this_Primary_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:383:1: ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==21) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:383:2: () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) )
            	    {
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:383:2: ()
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:384:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    otherlv_2=(Token)match(input,21,FOLLOW_21_in_ruleMultiplication781); 

            	        	newLeafNode(otherlv_2, grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_1());
            	        
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:393:1: ( (lv_right_3_0= rulePrimary ) )
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:394:1: (lv_right_3_0= rulePrimary )
            	    {
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:394:1: (lv_right_3_0= rulePrimary )
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:395:3: lv_right_3_0= rulePrimary
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMultiplicationAccess().getRightPrimaryParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePrimary_in_ruleMultiplication802);
            	    lv_right_3_0=rulePrimary();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMultiplicationRule());
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
            	    break loop6;
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


    // $ANTLR start "entryRulePrimary"
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:419:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:420:2: (iv_rulePrimary= rulePrimary EOF )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:421:2: iv_rulePrimary= rulePrimary EOF
            {
             newCompositeNode(grammarAccess.getPrimaryRule()); 
            pushFollow(FOLLOW_rulePrimary_in_entryRulePrimary840);
            iv_rulePrimary=rulePrimary();

            state._fsp--;

             current =iv_rulePrimary; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimary850); 

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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:428:1: rulePrimary returns [EObject current=null] : (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_VariableRef_0 = null;

        EObject this_Constante_1 = null;

        EObject this_Addition_3 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:431:28: ( (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:432:1: (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:432:1: (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) )
            int alt7=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt7=1;
                }
                break;
            case RULE_INT:
                {
                alt7=2;
                }
                break;
            case 22:
                {
                alt7=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:433:5: this_VariableRef_0= ruleVariableRef
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getVariableRefParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleVariableRef_in_rulePrimary897);
                    this_VariableRef_0=ruleVariableRef();

                    state._fsp--;

                     
                            current = this_VariableRef_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:443:5: this_Constante_1= ruleConstante
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleConstante_in_rulePrimary924);
                    this_Constante_1=ruleConstante();

                    state._fsp--;

                     
                            current = this_Constante_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:452:6: (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' )
                    {
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:452:6: (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' )
                    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:452:8: otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,22,FOLLOW_22_in_rulePrimary942); 

                        	newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0());
                        
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getAdditionParserRuleCall_2_1()); 
                        
                    pushFollow(FOLLOW_ruleAddition_in_rulePrimary964);
                    this_Addition_3=ruleAddition();

                    state._fsp--;

                     
                            current = this_Addition_3; 
                            afterParserOrEnumRuleCall();
                        
                    otherlv_4=(Token)match(input,23,FOLLOW_23_in_rulePrimary975); 

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


    // $ANTLR start "entryRuleVariableRef"
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:477:1: entryRuleVariableRef returns [EObject current=null] : iv_ruleVariableRef= ruleVariableRef EOF ;
    public final EObject entryRuleVariableRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableRef = null;


        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:478:2: (iv_ruleVariableRef= ruleVariableRef EOF )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:479:2: iv_ruleVariableRef= ruleVariableRef EOF
            {
             newCompositeNode(grammarAccess.getVariableRefRule()); 
            pushFollow(FOLLOW_ruleVariableRef_in_entryRuleVariableRef1012);
            iv_ruleVariableRef=ruleVariableRef();

            state._fsp--;

             current =iv_ruleVariableRef; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableRef1022); 

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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:486:1: ruleVariableRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleVariableRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:489:28: ( ( (otherlv_0= RULE_ID ) ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:490:1: ( (otherlv_0= RULE_ID ) )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:490:1: ( (otherlv_0= RULE_ID ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:491:1: (otherlv_0= RULE_ID )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:491:1: (otherlv_0= RULE_ID )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:492:3: otherlv_0= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getVariableRefRule());
            	        }
                    
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleVariableRef1066); 

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


    // $ANTLR start "entryRuleAddition"
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:511:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:512:2: (iv_ruleAddition= ruleAddition EOF )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:513:2: iv_ruleAddition= ruleAddition EOF
            {
             newCompositeNode(grammarAccess.getAdditionRule()); 
            pushFollow(FOLLOW_ruleAddition_in_entryRuleAddition1101);
            iv_ruleAddition=ruleAddition();

            state._fsp--;

             current =iv_ruleAddition; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAddition1111); 

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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:520:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Multiplication_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:523:28: ( (this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )* ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:524:1: (this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )* )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:524:1: (this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )* )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:525:5: this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition1158);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;

             
                    current = this_Multiplication_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:533:1: ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==24) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:533:2: () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) )
            	    {
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:533:2: ()
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:534:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    otherlv_2=(Token)match(input,24,FOLLOW_24_in_ruleAddition1179); 

            	        	newLeafNode(otherlv_2, grammarAccess.getAdditionAccess().getPlusSignKeyword_1_1());
            	        
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:543:1: ( (lv_right_3_0= ruleMultiplication ) )
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:544:1: (lv_right_3_0= ruleMultiplication )
            	    {
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:544:1: (lv_right_3_0= ruleMultiplication )
            	    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:545:3: lv_right_3_0= ruleMultiplication
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition1200);
            	    lv_right_3_0=ruleMultiplication();

            	    state._fsp--;


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
            	    break;

            	default :
            	    break loop8;
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


    // $ANTLR start "entryRuleAssignment"
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:569:1: entryRuleAssignment returns [EObject current=null] : iv_ruleAssignment= ruleAssignment EOF ;
    public final EObject entryRuleAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssignment = null;


        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:570:2: (iv_ruleAssignment= ruleAssignment EOF )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:571:2: iv_ruleAssignment= ruleAssignment EOF
            {
             newCompositeNode(grammarAccess.getAssignmentRule()); 
            pushFollow(FOLLOW_ruleAssignment_in_entryRuleAssignment1238);
            iv_ruleAssignment=ruleAssignment();

            state._fsp--;

             current =iv_ruleAssignment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssignment1248); 

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
    // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:578:1: ruleAssignment returns [EObject current=null] : ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) ;
    public final EObject ruleAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_var_0_0 = null;

        EObject lv_expr_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:581:28: ( ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:582:1: ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:582:1: ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:582:2: ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';'
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:582:2: ( (lv_var_0_0= ruleVariableRef ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:583:1: (lv_var_0_0= ruleVariableRef )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:583:1: (lv_var_0_0= ruleVariableRef )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:584:3: lv_var_0_0= ruleVariableRef
            {
             
            	        newCompositeNode(grammarAccess.getAssignmentAccess().getVarVariableRefParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleVariableRef_in_ruleAssignment1294);
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

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleAssignment1306); 

                	newLeafNode(otherlv_1, grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1());
                
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:604:1: ( (lv_expr_2_0= ruleAddition ) )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:605:1: (lv_expr_2_0= ruleAddition )
            {
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:605:1: (lv_expr_2_0= ruleAddition )
            // ../fr.lip6.move.GAL/src-gen/fr/lip6/move/parser/antlr/internal/InternalGAL.g:606:3: lv_expr_2_0= ruleAddition
            {
             
            	        newCompositeNode(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleAddition_in_ruleAssignment1327);
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

            otherlv_3=(Token)match(input,15,FOLLOW_15_in_ruleAssignment1339); 

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
    public static final BitSet FOLLOW_ruleConstante_in_entryRuleConstante612 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstante622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleConstante663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_entryRuleMultiplication703 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplication713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_ruleMultiplication760 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_21_in_ruleMultiplication781 = new BitSet(new long[]{0x0000000000400030L});
    public static final BitSet FOLLOW_rulePrimary_in_ruleMultiplication802 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_rulePrimary_in_entryRulePrimary840 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimary850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rulePrimary897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_rulePrimary924 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rulePrimary942 = new BitSet(new long[]{0x0000000000400030L});
    public static final BitSet FOLLOW_ruleAddition_in_rulePrimary964 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_rulePrimary975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_entryRuleVariableRef1012 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableRef1022 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleVariableRef1066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_entryRuleAddition1101 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAddition1111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition1158 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_24_in_ruleAddition1179 = new BitSet(new long[]{0x0000000000400030L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition1200 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_entryRuleAssignment1238 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssignment1248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_ruleAssignment1294 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleAssignment1306 = new BitSet(new long[]{0x0000000000400030L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleAssignment1327 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleAssignment1339 = new BitSet(new long[]{0x0000000000000002L});

}