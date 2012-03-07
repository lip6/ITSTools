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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'System'", "'='", "';'", "'{'", "'}'", "'*'", "'('", "')'", "'+'", "'transition'", "'['", "'TRUE'", "']'", "'label'"
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
    public String getGrammarFileName() { return "../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g"; }



     	private GalGrammarAccess grammarAccess;
     	
        public InternalGalParser(TokenStream input, GalGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "PROGRAM";	
       	}
       	
       	@Override
       	protected GalGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRulePROGRAM"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:67:1: entryRulePROGRAM returns [EObject current=null] : iv_rulePROGRAM= rulePROGRAM EOF ;
    public final EObject entryRulePROGRAM() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePROGRAM = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:68:2: (iv_rulePROGRAM= rulePROGRAM EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:69:2: iv_rulePROGRAM= rulePROGRAM EOF
            {
             newCompositeNode(grammarAccess.getPROGRAMRule()); 
            pushFollow(FOLLOW_rulePROGRAM_in_entryRulePROGRAM75);
            iv_rulePROGRAM=rulePROGRAM();

            state._fsp--;

             current =iv_rulePROGRAM; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePROGRAM85); 

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
    // $ANTLR end "entryRulePROGRAM"


    // $ANTLR start "rulePROGRAM"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:76:1: rulePROGRAM returns [EObject current=null] : (otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) ruleDEBUT ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTRANSITION ) )+ ruleFIN )? ;
    public final EObject rulePROGRAM() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        EObject lv_variables_3_0 = null;

        EObject lv_transitions_4_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:79:28: ( (otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) ruleDEBUT ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTRANSITION ) )+ ruleFIN )? )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:1: (otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) ruleDEBUT ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTRANSITION ) )+ ruleFIN )?
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:1: (otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) ruleDEBUT ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTRANSITION ) )+ ruleFIN )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==11) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:80:3: otherlv_0= 'System' ( (lv_name_1_0= RULE_ID ) ) ruleDEBUT ( (lv_variables_3_0= ruleVariableDeclaration ) )* ( (lv_transitions_4_0= ruleTRANSITION ) )+ ruleFIN
                    {
                    otherlv_0=(Token)match(input,11,FOLLOW_11_in_rulePROGRAM122); 

                        	newLeafNode(otherlv_0, grammarAccess.getPROGRAMAccess().getSystemKeyword_0());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:84:1: ( (lv_name_1_0= RULE_ID ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:85:1: (lv_name_1_0= RULE_ID )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:85:1: (lv_name_1_0= RULE_ID )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:86:3: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePROGRAM139); 

                    			newLeafNode(lv_name_1_0, grammarAccess.getPROGRAMAccess().getNameIDTerminalRuleCall_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPROGRAMRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_1_0, 
                            		"ID");
                    	    

                    }


                    }

                     
                            newCompositeNode(grammarAccess.getPROGRAMAccess().getDEBUTParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleDEBUT_in_rulePROGRAM160);
                    ruleDEBUT();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:110:1: ( (lv_variables_3_0= ruleVariableDeclaration ) )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==RULE_ID) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:111:1: (lv_variables_3_0= ruleVariableDeclaration )
                    	    {
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:111:1: (lv_variables_3_0= ruleVariableDeclaration )
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:112:3: lv_variables_3_0= ruleVariableDeclaration
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getPROGRAMAccess().getVariablesVariableDeclarationParserRuleCall_3_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleVariableDeclaration_in_rulePROGRAM180);
                    	    lv_variables_3_0=ruleVariableDeclaration();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getPROGRAMRule());
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

                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:128:3: ( (lv_transitions_4_0= ruleTRANSITION ) )+
                    int cnt2=0;
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==20) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:129:1: (lv_transitions_4_0= ruleTRANSITION )
                    	    {
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:129:1: (lv_transitions_4_0= ruleTRANSITION )
                    	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:130:3: lv_transitions_4_0= ruleTRANSITION
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getPROGRAMAccess().getTransitionsTRANSITIONParserRuleCall_4_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleTRANSITION_in_rulePROGRAM202);
                    	    lv_transitions_4_0=ruleTRANSITION();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getPROGRAMRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"transitions",
                    	            		lv_transitions_4_0, 
                    	            		"TRANSITION");
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

                     
                            newCompositeNode(grammarAccess.getPROGRAMAccess().getFINParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleFIN_in_rulePROGRAM219);
                    ruleFIN();

                    state._fsp--;

                     
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
    // $ANTLR end "rulePROGRAM"


    // $ANTLR start "entryRuleVariableDeclaration"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:162:1: entryRuleVariableDeclaration returns [EObject current=null] : iv_ruleVariableDeclaration= ruleVariableDeclaration EOF ;
    public final EObject entryRuleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableDeclaration = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:163:2: (iv_ruleVariableDeclaration= ruleVariableDeclaration EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:164:2: iv_ruleVariableDeclaration= ruleVariableDeclaration EOF
            {
             newCompositeNode(grammarAccess.getVariableDeclarationRule()); 
            pushFollow(FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration255);
            iv_ruleVariableDeclaration=ruleVariableDeclaration();

            state._fsp--;

             current =iv_ruleVariableDeclaration; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableDeclaration265); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:171:1: ruleVariableDeclaration returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) ;
    public final EObject ruleVariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_initVal_2_0=null;
        Token otherlv_3=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:174:28: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:175:1: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:175:1: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:175:2: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_initVal_2_0= RULE_INT ) ) otherlv_3= ';'
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:175:2: ( (lv_name_0_0= RULE_ID ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:176:1: (lv_name_0_0= RULE_ID )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:176:1: (lv_name_0_0= RULE_ID )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:177:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleVariableDeclaration307); 

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

            otherlv_1=(Token)match(input,12,FOLLOW_12_in_ruleVariableDeclaration324); 

                	newLeafNode(otherlv_1, grammarAccess.getVariableDeclarationAccess().getEqualsSignKeyword_1());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:197:1: ( (lv_initVal_2_0= RULE_INT ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:198:1: (lv_initVal_2_0= RULE_INT )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:198:1: (lv_initVal_2_0= RULE_INT )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:199:3: lv_initVal_2_0= RULE_INT
            {
            lv_initVal_2_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleVariableDeclaration341); 

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

            otherlv_3=(Token)match(input,13,FOLLOW_13_in_ruleVariableDeclaration358); 

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


    // $ANTLR start "entryRuleDEBUT"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:227:1: entryRuleDEBUT returns [String current=null] : iv_ruleDEBUT= ruleDEBUT EOF ;
    public final String entryRuleDEBUT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDEBUT = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:228:2: (iv_ruleDEBUT= ruleDEBUT EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:229:2: iv_ruleDEBUT= ruleDEBUT EOF
            {
             newCompositeNode(grammarAccess.getDEBUTRule()); 
            pushFollow(FOLLOW_ruleDEBUT_in_entryRuleDEBUT395);
            iv_ruleDEBUT=ruleDEBUT();

            state._fsp--;

             current =iv_ruleDEBUT.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDEBUT406); 

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
    // $ANTLR end "entryRuleDEBUT"


    // $ANTLR start "ruleDEBUT"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:236:1: ruleDEBUT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '{' ;
    public final AntlrDatatypeRuleToken ruleDEBUT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:239:28: (kw= '{' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:241:2: kw= '{'
            {
            kw=(Token)match(input,14,FOLLOW_14_in_ruleDEBUT443); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getDEBUTAccess().getLeftCurlyBracketKeyword()); 
                

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
    // $ANTLR end "ruleDEBUT"


    // $ANTLR start "entryRuleFIN"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:254:1: entryRuleFIN returns [String current=null] : iv_ruleFIN= ruleFIN EOF ;
    public final String entryRuleFIN() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFIN = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:255:2: (iv_ruleFIN= ruleFIN EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:256:2: iv_ruleFIN= ruleFIN EOF
            {
             newCompositeNode(grammarAccess.getFINRule()); 
            pushFollow(FOLLOW_ruleFIN_in_entryRuleFIN483);
            iv_ruleFIN=ruleFIN();

            state._fsp--;

             current =iv_ruleFIN.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFIN494); 

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
    // $ANTLR end "entryRuleFIN"


    // $ANTLR start "ruleFIN"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:263:1: ruleFIN returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '}' ;
    public final AntlrDatatypeRuleToken ruleFIN() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:266:28: (kw= '}' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:268:2: kw= '}'
            {
            kw=(Token)match(input,15,FOLLOW_15_in_ruleFIN531); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getFINAccess().getRightCurlyBracketKeyword()); 
                

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
    // $ANTLR end "ruleFIN"


    // $ANTLR start "entryRuleConstante"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:281:1: entryRuleConstante returns [EObject current=null] : iv_ruleConstante= ruleConstante EOF ;
    public final EObject entryRuleConstante() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstante = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:282:2: (iv_ruleConstante= ruleConstante EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:283:2: iv_ruleConstante= ruleConstante EOF
            {
             newCompositeNode(grammarAccess.getConstanteRule()); 
            pushFollow(FOLLOW_ruleConstante_in_entryRuleConstante570);
            iv_ruleConstante=ruleConstante();

            state._fsp--;

             current =iv_ruleConstante; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstante580); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:290:1: ruleConstante returns [EObject current=null] : ( (lv_val_0_0= RULE_INT ) ) ;
    public final EObject ruleConstante() throws RecognitionException {
        EObject current = null;

        Token lv_val_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:293:28: ( ( (lv_val_0_0= RULE_INT ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:294:1: ( (lv_val_0_0= RULE_INT ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:294:1: ( (lv_val_0_0= RULE_INT ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:295:1: (lv_val_0_0= RULE_INT )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:295:1: (lv_val_0_0= RULE_INT )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:296:3: lv_val_0_0= RULE_INT
            {
            lv_val_0_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleConstante621); 

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


    // $ANTLR start "entryRuleMultiplication"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:320:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:321:2: (iv_ruleMultiplication= ruleMultiplication EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:322:2: iv_ruleMultiplication= ruleMultiplication EOF
            {
             newCompositeNode(grammarAccess.getMultiplicationRule()); 
            pushFollow(FOLLOW_ruleMultiplication_in_entryRuleMultiplication661);
            iv_ruleMultiplication=ruleMultiplication();

            state._fsp--;

             current =iv_ruleMultiplication; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplication671); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:329:1: ruleMultiplication returns [EObject current=null] : (this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Primary_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:332:28: ( (this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:333:1: (this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:333:1: (this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:334:5: this_Primary_0= rulePrimary ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getMultiplicationAccess().getPrimaryParserRuleCall_0()); 
                
            pushFollow(FOLLOW_rulePrimary_in_ruleMultiplication718);
            this_Primary_0=rulePrimary();

            state._fsp--;

             
                    current = this_Primary_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:342:1: ( () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==16) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:342:2: () otherlv_2= '*' ( (lv_right_3_0= rulePrimary ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:342:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:343:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getMultiplicationAccess().getMultiplicationLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    otherlv_2=(Token)match(input,16,FOLLOW_16_in_ruleMultiplication739); 

            	        	newLeafNode(otherlv_2, grammarAccess.getMultiplicationAccess().getAsteriskKeyword_1_1());
            	        
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:352:1: ( (lv_right_3_0= rulePrimary ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:353:1: (lv_right_3_0= rulePrimary )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:353:1: (lv_right_3_0= rulePrimary )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:354:3: lv_right_3_0= rulePrimary
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMultiplicationAccess().getRightPrimaryParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePrimary_in_ruleMultiplication760);
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
            	    break loop4;
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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:378:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:379:2: (iv_rulePrimary= rulePrimary EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:380:2: iv_rulePrimary= rulePrimary EOF
            {
             newCompositeNode(grammarAccess.getPrimaryRule()); 
            pushFollow(FOLLOW_rulePrimary_in_entryRulePrimary798);
            iv_rulePrimary=rulePrimary();

            state._fsp--;

             current =iv_rulePrimary; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimary808); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:387:1: rulePrimary returns [EObject current=null] : (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_VariableRef_0 = null;

        EObject this_Constante_1 = null;

        EObject this_Addition_3 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:390:28: ( (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:391:1: (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:391:1: (this_VariableRef_0= ruleVariableRef | this_Constante_1= ruleConstante | (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt5=1;
                }
                break;
            case RULE_INT:
                {
                alt5=2;
                }
                break;
            case 17:
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
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:392:5: this_VariableRef_0= ruleVariableRef
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getVariableRefParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleVariableRef_in_rulePrimary855);
                    this_VariableRef_0=ruleVariableRef();

                    state._fsp--;

                     
                            current = this_VariableRef_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:402:5: this_Constante_1= ruleConstante
                    {
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getConstanteParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleConstante_in_rulePrimary882);
                    this_Constante_1=ruleConstante();

                    state._fsp--;

                     
                            current = this_Constante_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:411:6: (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:411:6: (otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')' )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:411:8: otherlv_2= '(' this_Addition_3= ruleAddition otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,17,FOLLOW_17_in_rulePrimary900); 

                        	newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0());
                        
                     
                            newCompositeNode(grammarAccess.getPrimaryAccess().getAdditionParserRuleCall_2_1()); 
                        
                    pushFollow(FOLLOW_ruleAddition_in_rulePrimary922);
                    this_Addition_3=ruleAddition();

                    state._fsp--;

                     
                            current = this_Addition_3; 
                            afterParserOrEnumRuleCall();
                        
                    otherlv_4=(Token)match(input,18,FOLLOW_18_in_rulePrimary933); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:436:1: entryRuleVariableRef returns [EObject current=null] : iv_ruleVariableRef= ruleVariableRef EOF ;
    public final EObject entryRuleVariableRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableRef = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:437:2: (iv_ruleVariableRef= ruleVariableRef EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:438:2: iv_ruleVariableRef= ruleVariableRef EOF
            {
             newCompositeNode(grammarAccess.getVariableRefRule()); 
            pushFollow(FOLLOW_ruleVariableRef_in_entryRuleVariableRef970);
            iv_ruleVariableRef=ruleVariableRef();

            state._fsp--;

             current =iv_ruleVariableRef; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableRef980); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:445:1: ruleVariableRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleVariableRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:448:28: ( ( (otherlv_0= RULE_ID ) ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:449:1: ( (otherlv_0= RULE_ID ) )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:449:1: ( (otherlv_0= RULE_ID ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:450:1: (otherlv_0= RULE_ID )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:450:1: (otherlv_0= RULE_ID )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:451:3: otherlv_0= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getVariableRefRule());
            	        }
                    
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleVariableRef1024); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:470:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:471:2: (iv_ruleAddition= ruleAddition EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:472:2: iv_ruleAddition= ruleAddition EOF
            {
             newCompositeNode(grammarAccess.getAdditionRule()); 
            pushFollow(FOLLOW_ruleAddition_in_entryRuleAddition1059);
            iv_ruleAddition=ruleAddition();

            state._fsp--;

             current =iv_ruleAddition; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAddition1069); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:479:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Multiplication_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:482:28: ( (this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )* ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:483:1: (this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )* )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:483:1: (this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )* )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:484:5: this_Multiplication_0= ruleMultiplication ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )*
            {
             
                    newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition1116);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;

             
                    current = this_Multiplication_0; 
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:492:1: ( () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==19) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:492:2: () otherlv_2= '+' ( (lv_right_3_0= ruleMultiplication ) )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:492:2: ()
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:493:5: 
            	    {

            	            current = forceCreateModelElementAndSet(
            	                grammarAccess.getAdditionAccess().getAdditionLeftAction_1_0(),
            	                current);
            	        

            	    }

            	    otherlv_2=(Token)match(input,19,FOLLOW_19_in_ruleAddition1137); 

            	        	newLeafNode(otherlv_2, grammarAccess.getAdditionAccess().getPlusSignKeyword_1_1());
            	        
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:502:1: ( (lv_right_3_0= ruleMultiplication ) )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:503:1: (lv_right_3_0= ruleMultiplication )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:503:1: (lv_right_3_0= ruleMultiplication )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:504:3: lv_right_3_0= ruleMultiplication
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition1158);
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
    // $ANTLR end "ruleAddition"


    // $ANTLR start "entryRuleTRANSITION"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:528:1: entryRuleTRANSITION returns [EObject current=null] : iv_ruleTRANSITION= ruleTRANSITION EOF ;
    public final EObject entryRuleTRANSITION() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTRANSITION = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:529:2: (iv_ruleTRANSITION= ruleTRANSITION EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:530:2: iv_ruleTRANSITION= ruleTRANSITION EOF
            {
             newCompositeNode(grammarAccess.getTRANSITIONRule()); 
            pushFollow(FOLLOW_ruleTRANSITION_in_entryRuleTRANSITION1196);
            iv_ruleTRANSITION=ruleTRANSITION();

            state._fsp--;

             current =iv_ruleTRANSITION; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTRANSITION1206); 

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
    // $ANTLR end "entryRuleTRANSITION"


    // $ANTLR start "ruleTRANSITION"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:537:1: ruleTRANSITION returns [EObject current=null] : (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ruleDEBUT ( (lv_assignments_8_0= ruleAssignment ) )+ ruleFIN ) ;
    public final EObject ruleTRANSITION() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_label_6_0=null;
        EObject lv_assignments_8_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:540:28: ( (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ruleDEBUT ( (lv_assignments_8_0= ruleAssignment ) )+ ruleFIN ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:541:1: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ruleDEBUT ( (lv_assignments_8_0= ruleAssignment ) )+ ruleFIN )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:541:1: (otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ruleDEBUT ( (lv_assignments_8_0= ruleAssignment ) )+ ruleFIN )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:541:3: otherlv_0= 'transition' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '[' otherlv_3= 'TRUE' otherlv_4= ']' (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )? ruleDEBUT ( (lv_assignments_8_0= ruleAssignment ) )+ ruleFIN
            {
            otherlv_0=(Token)match(input,20,FOLLOW_20_in_ruleTRANSITION1243); 

                	newLeafNode(otherlv_0, grammarAccess.getTRANSITIONAccess().getTransitionKeyword_0());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:545:1: ( (lv_name_1_0= RULE_ID ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:546:1: (lv_name_1_0= RULE_ID )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:546:1: (lv_name_1_0= RULE_ID )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:547:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTRANSITION1260); 

            			newLeafNode(lv_name_1_0, grammarAccess.getTRANSITIONAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getTRANSITIONRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,21,FOLLOW_21_in_ruleTRANSITION1277); 

                	newLeafNode(otherlv_2, grammarAccess.getTRANSITIONAccess().getLeftSquareBracketKeyword_2());
                
            otherlv_3=(Token)match(input,22,FOLLOW_22_in_ruleTRANSITION1289); 

                	newLeafNode(otherlv_3, grammarAccess.getTRANSITIONAccess().getTRUEKeyword_3());
                
            otherlv_4=(Token)match(input,23,FOLLOW_23_in_ruleTRANSITION1301); 

                	newLeafNode(otherlv_4, grammarAccess.getTRANSITIONAccess().getRightSquareBracketKeyword_4());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:575:1: (otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==24) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:575:3: otherlv_5= 'label' ( (lv_label_6_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,24,FOLLOW_24_in_ruleTRANSITION1314); 

                        	newLeafNode(otherlv_5, grammarAccess.getTRANSITIONAccess().getLabelKeyword_5_0());
                        
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:579:1: ( (lv_label_6_0= RULE_STRING ) )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:580:1: (lv_label_6_0= RULE_STRING )
                    {
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:580:1: (lv_label_6_0= RULE_STRING )
                    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:581:3: lv_label_6_0= RULE_STRING
                    {
                    lv_label_6_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleTRANSITION1331); 

                    			newLeafNode(lv_label_6_0, grammarAccess.getTRANSITIONAccess().getLabelSTRINGTerminalRuleCall_5_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getTRANSITIONRule());
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

             
                    newCompositeNode(grammarAccess.getTRANSITIONAccess().getDEBUTParserRuleCall_6()); 
                
            pushFollow(FOLLOW_ruleDEBUT_in_ruleTRANSITION1354);
            ruleDEBUT();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:605:1: ( (lv_assignments_8_0= ruleAssignment ) )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_ID) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:606:1: (lv_assignments_8_0= ruleAssignment )
            	    {
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:606:1: (lv_assignments_8_0= ruleAssignment )
            	    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:607:3: lv_assignments_8_0= ruleAssignment
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getTRANSITIONAccess().getAssignmentsAssignmentParserRuleCall_7_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleAssignment_in_ruleTRANSITION1374);
            	    lv_assignments_8_0=ruleAssignment();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getTRANSITIONRule());
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
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);

             
                    newCompositeNode(grammarAccess.getTRANSITIONAccess().getFINParserRuleCall_8()); 
                
            pushFollow(FOLLOW_ruleFIN_in_ruleTRANSITION1391);
            ruleFIN();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                

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
    // $ANTLR end "ruleTRANSITION"


    // $ANTLR start "entryRuleAssignment"
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:639:1: entryRuleAssignment returns [EObject current=null] : iv_ruleAssignment= ruleAssignment EOF ;
    public final EObject entryRuleAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssignment = null;


        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:640:2: (iv_ruleAssignment= ruleAssignment EOF )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:641:2: iv_ruleAssignment= ruleAssignment EOF
            {
             newCompositeNode(grammarAccess.getAssignmentRule()); 
            pushFollow(FOLLOW_ruleAssignment_in_entryRuleAssignment1426);
            iv_ruleAssignment=ruleAssignment();

            state._fsp--;

             current =iv_ruleAssignment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssignment1436); 

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
    // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:648:1: ruleAssignment returns [EObject current=null] : ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) ;
    public final EObject ruleAssignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_var_0_0 = null;

        EObject lv_expr_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:651:28: ( ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:652:1: ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:652:1: ( ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';' )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:652:2: ( (lv_var_0_0= ruleVariableRef ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleAddition ) ) otherlv_3= ';'
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:652:2: ( (lv_var_0_0= ruleVariableRef ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:653:1: (lv_var_0_0= ruleVariableRef )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:653:1: (lv_var_0_0= ruleVariableRef )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:654:3: lv_var_0_0= ruleVariableRef
            {
             
            	        newCompositeNode(grammarAccess.getAssignmentAccess().getVarVariableRefParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleVariableRef_in_ruleAssignment1482);
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

            otherlv_1=(Token)match(input,12,FOLLOW_12_in_ruleAssignment1494); 

                	newLeafNode(otherlv_1, grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1());
                
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:674:1: ( (lv_expr_2_0= ruleAddition ) )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:675:1: (lv_expr_2_0= ruleAddition )
            {
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:675:1: (lv_expr_2_0= ruleAddition )
            // ../fr.lip6.move.gal/src-gen/fr/lip6/move/parser/antlr/internal/InternalGal.g:676:3: lv_expr_2_0= ruleAddition
            {
             
            	        newCompositeNode(grammarAccess.getAssignmentAccess().getExprAdditionParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleAddition_in_ruleAssignment1515);
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

            otherlv_3=(Token)match(input,13,FOLLOW_13_in_ruleAssignment1527); 

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


 

    public static final BitSet FOLLOW_rulePROGRAM_in_entryRulePROGRAM75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePROGRAM85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rulePROGRAM122 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePROGRAM139 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ruleDEBUT_in_rulePROGRAM160 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_rulePROGRAM180 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_ruleTRANSITION_in_rulePROGRAM202 = new BitSet(new long[]{0x0000000000108010L});
    public static final BitSet FOLLOW_ruleFIN_in_rulePROGRAM219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDeclaration_in_entryRuleVariableDeclaration255 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableDeclaration265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleVariableDeclaration307 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleVariableDeclaration324 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleVariableDeclaration341 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleVariableDeclaration358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDEBUT_in_entryRuleDEBUT395 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDEBUT406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleDEBUT443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFIN_in_entryRuleFIN483 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFIN494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleFIN531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_entryRuleConstante570 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstante580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleConstante621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_entryRuleMultiplication661 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplication671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_ruleMultiplication718 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_16_in_ruleMultiplication739 = new BitSet(new long[]{0x0000000000020030L});
    public static final BitSet FOLLOW_rulePrimary_in_ruleMultiplication760 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rulePrimary_in_entryRulePrimary798 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimary808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_rulePrimary855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstante_in_rulePrimary882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rulePrimary900 = new BitSet(new long[]{0x0000000000020030L});
    public static final BitSet FOLLOW_ruleAddition_in_rulePrimary922 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_rulePrimary933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_entryRuleVariableRef970 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableRef980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleVariableRef1024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_entryRuleAddition1059 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAddition1069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition1116 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_19_in_ruleAddition1137 = new BitSet(new long[]{0x0000000000020030L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition1158 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_ruleTRANSITION_in_entryRuleTRANSITION1196 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTRANSITION1206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_ruleTRANSITION1243 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTRANSITION1260 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_ruleTRANSITION1277 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_ruleTRANSITION1289 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleTRANSITION1301 = new BitSet(new long[]{0x0000000001004000L});
    public static final BitSet FOLLOW_24_in_ruleTRANSITION1314 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleTRANSITION1331 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ruleDEBUT_in_ruleTRANSITION1354 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleAssignment_in_ruleTRANSITION1374 = new BitSet(new long[]{0x0000000000008010L});
    public static final BitSet FOLLOW_ruleFIN_in_ruleTRANSITION1391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignment_in_entryRuleAssignment1426 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssignment1436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableRef_in_ruleAssignment1482 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleAssignment1494 = new BitSet(new long[]{0x0000000000020030L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleAssignment1515 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleAssignment1527 = new BitSet(new long[]{0x0000000000000002L});

}