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
import fr.lip6.move.services.TimedAutomataGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalTimedAutomataParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'('", "','", "')'", "';'", "'system'", "'process'", "'{'", "'}'", "'typedef'", "'chan'", "'urgent'", "'broadcast'", "'const'", "'int'", "'bool'", "'['", "']'", "'clock'", "'state'", "'commit'", "'init'", "'trans'", "'->'", "'guard'", "'sync'", "'!'", "'?'", "'assign'", "'|'", "'^'", "'&'", "'<<'", "'>>'", "'+'", "'-'", "'/'", "'*'", "'%'", "'~'", "'**'", "'||'", "'&&'", "'true'", "'false'", "'='", "':='", "'>'", "'<'", "'>='", "'<='", "'=='", "'!='"
    };
    public static final int RULE_ID=4;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__62=62;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int EOF=-1;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__19=19;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__16=16;
    public static final int T__52=52;
    public static final int T__15=15;
    public static final int T__53=53;
    public static final int T__18=18;
    public static final int T__54=54;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__59=59;
    public static final int RULE_INT=5;
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


        public InternalTimedAutomataParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalTimedAutomataParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalTimedAutomataParser.tokenNames; }
    public String getGrammarFileName() { return "../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g"; }



     	private TimedAutomataGrammarAccess grammarAccess;
     	
        public InternalTimedAutomataParser(TokenStream input, TimedAutomataGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "XTA";	
       	}
       	
       	@Override
       	protected TimedAutomataGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleXTA"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:68:1: entryRuleXTA returns [EObject current=null] : iv_ruleXTA= ruleXTA EOF ;
    public final EObject entryRuleXTA() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXTA = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:69:2: (iv_ruleXTA= ruleXTA EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:70:2: iv_ruleXTA= ruleXTA EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXTARule()); 
            }
            pushFollow(FOLLOW_ruleXTA_in_entryRuleXTA75);
            iv_ruleXTA=ruleXTA();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXTA; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXTA85); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXTA"


    // $ANTLR start "ruleXTA"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:77:1: ruleXTA returns [EObject current=null] : ( ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_channels_1_0= ruleChannelDecl ) ) | ( (lv_types_2_0= ruleTypeDecl ) ) | ( (lv_templates_3_0= ruleProcDecl ) ) )* ( (lv_instances_4_0= ruleInstance ) )* ( (lv_system_5_0= ruleSystem ) ) ) ;
    public final EObject ruleXTA() throws RecognitionException {
        EObject current = null;

        EObject lv_variables_0_0 = null;

        EObject lv_channels_1_0 = null;

        EObject lv_types_2_0 = null;

        EObject lv_templates_3_0 = null;

        EObject lv_instances_4_0 = null;

        EObject lv_system_5_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:80:28: ( ( ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_channels_1_0= ruleChannelDecl ) ) | ( (lv_types_2_0= ruleTypeDecl ) ) | ( (lv_templates_3_0= ruleProcDecl ) ) )* ( (lv_instances_4_0= ruleInstance ) )* ( (lv_system_5_0= ruleSystem ) ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:81:1: ( ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_channels_1_0= ruleChannelDecl ) ) | ( (lv_types_2_0= ruleTypeDecl ) ) | ( (lv_templates_3_0= ruleProcDecl ) ) )* ( (lv_instances_4_0= ruleInstance ) )* ( (lv_system_5_0= ruleSystem ) ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:81:1: ( ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_channels_1_0= ruleChannelDecl ) ) | ( (lv_types_2_0= ruleTypeDecl ) ) | ( (lv_templates_3_0= ruleProcDecl ) ) )* ( (lv_instances_4_0= ruleInstance ) )* ( (lv_system_5_0= ruleSystem ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:81:2: ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_channels_1_0= ruleChannelDecl ) ) | ( (lv_types_2_0= ruleTypeDecl ) ) | ( (lv_templates_3_0= ruleProcDecl ) ) )* ( (lv_instances_4_0= ruleInstance ) )* ( (lv_system_5_0= ruleSystem ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:81:2: ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_channels_1_0= ruleChannelDecl ) ) | ( (lv_types_2_0= ruleTypeDecl ) ) | ( (lv_templates_3_0= ruleProcDecl ) ) )*
            loop1:
            do {
                int alt1=5;
                switch ( input.LA(1) ) {
                case 23:
                case 24:
                case 25:
                case 28:
                    {
                    alt1=1;
                    }
                    break;
                case 20:
                    {
                    alt1=2;
                    }
                    break;
                case 19:
                    {
                    alt1=3;
                    }
                    break;
                case 16:
                    {
                    alt1=4;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:81:3: ( (lv_variables_0_0= ruleVariableDecl ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:81:3: ( (lv_variables_0_0= ruleVariableDecl ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:82:1: (lv_variables_0_0= ruleVariableDecl )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:82:1: (lv_variables_0_0= ruleVariableDecl )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:83:3: lv_variables_0_0= ruleVariableDecl
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getXTAAccess().getVariablesVariableDeclParserRuleCall_0_0_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleVariableDecl_in_ruleXTA132);
            	    lv_variables_0_0=ruleVariableDecl();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getXTARule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"variables",
            	              		lv_variables_0_0, 
            	              		"VariableDecl");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:100:6: ( (lv_channels_1_0= ruleChannelDecl ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:100:6: ( (lv_channels_1_0= ruleChannelDecl ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:101:1: (lv_channels_1_0= ruleChannelDecl )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:101:1: (lv_channels_1_0= ruleChannelDecl )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:102:3: lv_channels_1_0= ruleChannelDecl
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getXTAAccess().getChannelsChannelDeclParserRuleCall_0_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleChannelDecl_in_ruleXTA159);
            	    lv_channels_1_0=ruleChannelDecl();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getXTARule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"channels",
            	              		lv_channels_1_0, 
            	              		"ChannelDecl");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:119:6: ( (lv_types_2_0= ruleTypeDecl ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:119:6: ( (lv_types_2_0= ruleTypeDecl ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:120:1: (lv_types_2_0= ruleTypeDecl )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:120:1: (lv_types_2_0= ruleTypeDecl )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:121:3: lv_types_2_0= ruleTypeDecl
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getXTAAccess().getTypesTypeDeclParserRuleCall_0_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleTypeDecl_in_ruleXTA186);
            	    lv_types_2_0=ruleTypeDecl();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getXTARule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"types",
            	              		lv_types_2_0, 
            	              		"TypeDecl");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:138:6: ( (lv_templates_3_0= ruleProcDecl ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:138:6: ( (lv_templates_3_0= ruleProcDecl ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:139:1: (lv_templates_3_0= ruleProcDecl )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:139:1: (lv_templates_3_0= ruleProcDecl )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:140:3: lv_templates_3_0= ruleProcDecl
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getXTAAccess().getTemplatesProcDeclParserRuleCall_0_3_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleProcDecl_in_ruleXTA213);
            	    lv_templates_3_0=ruleProcDecl();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getXTARule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"templates",
            	              		lv_templates_3_0, 
            	              		"ProcDecl");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:156:4: ( (lv_instances_4_0= ruleInstance ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==RULE_ID) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:157:1: (lv_instances_4_0= ruleInstance )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:157:1: (lv_instances_4_0= ruleInstance )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:158:3: lv_instances_4_0= ruleInstance
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getXTAAccess().getInstancesInstanceParserRuleCall_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleInstance_in_ruleXTA236);
            	    lv_instances_4_0=ruleInstance();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getXTARule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"instances",
            	              		lv_instances_4_0, 
            	              		"Instance");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:174:3: ( (lv_system_5_0= ruleSystem ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:175:1: (lv_system_5_0= ruleSystem )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:175:1: (lv_system_5_0= ruleSystem )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:176:3: lv_system_5_0= ruleSystem
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getXTAAccess().getSystemSystemParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleSystem_in_ruleXTA258);
            lv_system_5_0=ruleSystem();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getXTARule());
              	        }
                     		set(
                     			current, 
                     			"system",
                      		lv_system_5_0, 
                      		"System");
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
    // $ANTLR end "ruleXTA"


    // $ANTLR start "entryRuleInstance"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:202:1: entryRuleInstance returns [EObject current=null] : iv_ruleInstance= ruleInstance EOF ;
    public final EObject entryRuleInstance() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstance = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:203:2: (iv_ruleInstance= ruleInstance EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:204:2: iv_ruleInstance= ruleInstance EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getInstanceRule()); 
            }
            pushFollow(FOLLOW_ruleInstance_in_entryRuleInstance296);
            iv_ruleInstance=ruleInstance();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleInstance; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleInstance306); if (state.failed) return current;

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
    // $ANTLR end "entryRuleInstance"


    // $ANTLR start "ruleInstance"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:211:1: ruleInstance returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ruleASSIGNMENT ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_args_4_0= ruleBitOr ) ) (otherlv_5= ',' ( (lv_args_6_0= ruleBitOr ) ) )* )? otherlv_7= ')' otherlv_8= ';' ) ;
    public final EObject ruleInstance() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        EObject lv_args_4_0 = null;

        EObject lv_args_6_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:214:28: ( ( ( (lv_name_0_0= RULE_ID ) ) ruleASSIGNMENT ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_args_4_0= ruleBitOr ) ) (otherlv_5= ',' ( (lv_args_6_0= ruleBitOr ) ) )* )? otherlv_7= ')' otherlv_8= ';' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:215:1: ( ( (lv_name_0_0= RULE_ID ) ) ruleASSIGNMENT ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_args_4_0= ruleBitOr ) ) (otherlv_5= ',' ( (lv_args_6_0= ruleBitOr ) ) )* )? otherlv_7= ')' otherlv_8= ';' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:215:1: ( ( (lv_name_0_0= RULE_ID ) ) ruleASSIGNMENT ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_args_4_0= ruleBitOr ) ) (otherlv_5= ',' ( (lv_args_6_0= ruleBitOr ) ) )* )? otherlv_7= ')' otherlv_8= ';' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:215:2: ( (lv_name_0_0= RULE_ID ) ) ruleASSIGNMENT ( (otherlv_2= RULE_ID ) ) otherlv_3= '(' ( ( (lv_args_4_0= ruleBitOr ) ) (otherlv_5= ',' ( (lv_args_6_0= ruleBitOr ) ) )* )? otherlv_7= ')' otherlv_8= ';'
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:215:2: ( (lv_name_0_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:216:1: (lv_name_0_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:216:1: (lv_name_0_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:217:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleInstance348); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_name_0_0, grammarAccess.getInstanceAccess().getNameIDTerminalRuleCall_0_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getInstanceRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"name",
                      		lv_name_0_0, 
                      		"ID");
              	    
            }

            }


            }

            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getInstanceAccess().getASSIGNMENTParserRuleCall_1()); 
                  
            }
            pushFollow(FOLLOW_ruleASSIGNMENT_in_ruleInstance369);
            ruleASSIGNMENT();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:241:1: ( (otherlv_2= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:242:1: (otherlv_2= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:242:1: (otherlv_2= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:243:3: otherlv_2= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getInstanceRule());
              	        }
                      
            }
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleInstance388); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_2, grammarAccess.getInstanceAccess().getTypeProcDeclCrossReference_2_0()); 
              	
            }

            }


            }

            otherlv_3=(Token)match(input,11,FOLLOW_11_in_ruleInstance400); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getInstanceAccess().getLeftParenthesisKeyword_3());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:258:1: ( ( (lv_args_4_0= ruleBitOr ) ) (otherlv_5= ',' ( (lv_args_6_0= ruleBitOr ) ) )* )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=RULE_ID && LA4_0<=RULE_INT)||LA4_0==11||LA4_0==45||LA4_0==49) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:258:2: ( (lv_args_4_0= ruleBitOr ) ) (otherlv_5= ',' ( (lv_args_6_0= ruleBitOr ) ) )*
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:258:2: ( (lv_args_4_0= ruleBitOr ) )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:259:1: (lv_args_4_0= ruleBitOr )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:259:1: (lv_args_4_0= ruleBitOr )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:260:3: lv_args_4_0= ruleBitOr
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getInstanceAccess().getArgsBitOrParserRuleCall_4_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_ruleBitOr_in_ruleInstance422);
                    lv_args_4_0=ruleBitOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getInstanceRule());
                      	        }
                             		add(
                             			current, 
                             			"args",
                              		lv_args_4_0, 
                              		"BitOr");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:276:2: (otherlv_5= ',' ( (lv_args_6_0= ruleBitOr ) ) )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==12) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:276:4: otherlv_5= ',' ( (lv_args_6_0= ruleBitOr ) )
                    	    {
                    	    otherlv_5=(Token)match(input,12,FOLLOW_12_in_ruleInstance435); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_5, grammarAccess.getInstanceAccess().getCommaKeyword_4_1_0());
                    	          
                    	    }
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:280:1: ( (lv_args_6_0= ruleBitOr ) )
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:281:1: (lv_args_6_0= ruleBitOr )
                    	    {
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:281:1: (lv_args_6_0= ruleBitOr )
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:282:3: lv_args_6_0= ruleBitOr
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getInstanceAccess().getArgsBitOrParserRuleCall_4_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_ruleBitOr_in_ruleInstance456);
                    	    lv_args_6_0=ruleBitOr();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getInstanceRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"args",
                    	              		lv_args_6_0, 
                    	              		"BitOr");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_7=(Token)match(input,13,FOLLOW_13_in_ruleInstance472); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_7, grammarAccess.getInstanceAccess().getRightParenthesisKeyword_5());
                  
            }
            otherlv_8=(Token)match(input,14,FOLLOW_14_in_ruleInstance484); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_8, grammarAccess.getInstanceAccess().getSemicolonKeyword_6());
                  
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
    // $ANTLR end "ruleInstance"


    // $ANTLR start "entryRuleSystem"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:314:1: entryRuleSystem returns [EObject current=null] : iv_ruleSystem= ruleSystem EOF ;
    public final EObject entryRuleSystem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSystem = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:315:2: (iv_ruleSystem= ruleSystem EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:316:2: iv_ruleSystem= ruleSystem EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSystemRule()); 
            }
            pushFollow(FOLLOW_ruleSystem_in_entryRuleSystem520);
            iv_ruleSystem=ruleSystem();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSystem; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleSystem530); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:323:1: ruleSystem returns [EObject current=null] : (otherlv_0= 'system' ( (otherlv_1= RULE_ID ) ) (otherlv_2= ',' ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ';' ) ;
    public final EObject ruleSystem() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:326:28: ( (otherlv_0= 'system' ( (otherlv_1= RULE_ID ) ) (otherlv_2= ',' ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ';' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:327:1: (otherlv_0= 'system' ( (otherlv_1= RULE_ID ) ) (otherlv_2= ',' ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ';' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:327:1: (otherlv_0= 'system' ( (otherlv_1= RULE_ID ) ) (otherlv_2= ',' ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ';' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:327:3: otherlv_0= 'system' ( (otherlv_1= RULE_ID ) ) (otherlv_2= ',' ( (otherlv_3= RULE_ID ) ) )* otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_15_in_ruleSystem567); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getSystemAccess().getSystemKeyword_0());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:331:1: ( (otherlv_1= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:332:1: (otherlv_1= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:332:1: (otherlv_1= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:333:3: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getSystemRule());
              	        }
                      
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleSystem587); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_1, grammarAccess.getSystemAccess().getInstancesInstantiableInSystemCrossReference_1_0()); 
              	
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:344:2: (otherlv_2= ',' ( (otherlv_3= RULE_ID ) ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==12) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:344:4: otherlv_2= ',' ( (otherlv_3= RULE_ID ) )
            	    {
            	    otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleSystem600); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_2, grammarAccess.getSystemAccess().getCommaKeyword_2_0());
            	          
            	    }
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:348:1: ( (otherlv_3= RULE_ID ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:349:1: (otherlv_3= RULE_ID )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:349:1: (otherlv_3= RULE_ID )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:350:3: otherlv_3= RULE_ID
            	    {
            	    if ( state.backtracking==0 ) {

            	      			if (current==null) {
            	      	            current = createModelElement(grammarAccess.getSystemRule());
            	      	        }
            	              
            	    }
            	    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleSystem620); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      		newLeafNode(otherlv_3, grammarAccess.getSystemAccess().getInstancesInstantiableInSystemCrossReference_2_1_0()); 
            	      	
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            otherlv_4=(Token)match(input,14,FOLLOW_14_in_ruleSystem634); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getSystemAccess().getSemicolonKeyword_3());
                  
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


    // $ANTLR start "entryRuleProcDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:373:1: entryRuleProcDecl returns [EObject current=null] : iv_ruleProcDecl= ruleProcDecl EOF ;
    public final EObject entryRuleProcDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProcDecl = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:374:2: (iv_ruleProcDecl= ruleProcDecl EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:375:2: iv_ruleProcDecl= ruleProcDecl EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getProcDeclRule()); 
            }
            pushFollow(FOLLOW_ruleProcDecl_in_entryRuleProcDecl670);
            iv_ruleProcDecl=ruleProcDecl();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleProcDecl; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleProcDecl680); if (state.failed) return current;

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
    // $ANTLR end "entryRuleProcDecl"


    // $ANTLR start "ruleProcDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:382:1: ruleProcDecl returns [EObject current=null] : (otherlv_0= 'process' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_params_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_params_5_0= ruleParameter ) ) )* )? otherlv_6= ')' otherlv_7= '{' ( (lv_body_8_0= ruleProcBody ) ) otherlv_9= '}' ) ;
    public final EObject ruleProcDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_params_3_0 = null;

        EObject lv_params_5_0 = null;

        EObject lv_body_8_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:385:28: ( (otherlv_0= 'process' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_params_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_params_5_0= ruleParameter ) ) )* )? otherlv_6= ')' otherlv_7= '{' ( (lv_body_8_0= ruleProcBody ) ) otherlv_9= '}' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:386:1: (otherlv_0= 'process' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_params_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_params_5_0= ruleParameter ) ) )* )? otherlv_6= ')' otherlv_7= '{' ( (lv_body_8_0= ruleProcBody ) ) otherlv_9= '}' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:386:1: (otherlv_0= 'process' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_params_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_params_5_0= ruleParameter ) ) )* )? otherlv_6= ')' otherlv_7= '{' ( (lv_body_8_0= ruleProcBody ) ) otherlv_9= '}' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:386:3: otherlv_0= 'process' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( ( (lv_params_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_params_5_0= ruleParameter ) ) )* )? otherlv_6= ')' otherlv_7= '{' ( (lv_body_8_0= ruleProcBody ) ) otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_16_in_ruleProcDecl717); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getProcDeclAccess().getProcessKeyword_0());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:390:1: ( (lv_name_1_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:391:1: (lv_name_1_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:391:1: (lv_name_1_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:392:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleProcDecl734); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_name_1_0, grammarAccess.getProcDeclAccess().getNameIDTerminalRuleCall_1_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getProcDeclRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"ID");
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,11,FOLLOW_11_in_ruleProcDecl751); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getProcDeclAccess().getLeftParenthesisKeyword_2());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:412:1: ( ( (lv_params_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_params_5_0= ruleParameter ) ) )* )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_ID||(LA7_0>=23 && LA7_0<=25)||LA7_0==28) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:412:2: ( (lv_params_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_params_5_0= ruleParameter ) ) )*
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:412:2: ( (lv_params_3_0= ruleParameter ) )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:413:1: (lv_params_3_0= ruleParameter )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:413:1: (lv_params_3_0= ruleParameter )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:414:3: lv_params_3_0= ruleParameter
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getProcDeclAccess().getParamsParameterParserRuleCall_3_0_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_ruleParameter_in_ruleProcDecl773);
                    lv_params_3_0=ruleParameter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getProcDeclRule());
                      	        }
                             		add(
                             			current, 
                             			"params",
                              		lv_params_3_0, 
                              		"Parameter");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:430:2: (otherlv_4= ',' ( (lv_params_5_0= ruleParameter ) ) )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==12) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:430:4: otherlv_4= ',' ( (lv_params_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleProcDecl786); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_4, grammarAccess.getProcDeclAccess().getCommaKeyword_3_1_0());
                    	          
                    	    }
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:434:1: ( (lv_params_5_0= ruleParameter ) )
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:435:1: (lv_params_5_0= ruleParameter )
                    	    {
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:435:1: (lv_params_5_0= ruleParameter )
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:436:3: lv_params_5_0= ruleParameter
                    	    {
                    	    if ( state.backtracking==0 ) {
                    	       
                    	      	        newCompositeNode(grammarAccess.getProcDeclAccess().getParamsParameterParserRuleCall_3_1_1_0()); 
                    	      	    
                    	    }
                    	    pushFollow(FOLLOW_ruleParameter_in_ruleProcDecl807);
                    	    lv_params_5_0=ruleParameter();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      	        if (current==null) {
                    	      	            current = createModelElementForParent(grammarAccess.getProcDeclRule());
                    	      	        }
                    	             		add(
                    	             			current, 
                    	             			"params",
                    	              		lv_params_5_0, 
                    	              		"Parameter");
                    	      	        afterParserOrEnumRuleCall();
                    	      	    
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,13,FOLLOW_13_in_ruleProcDecl823); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_6, grammarAccess.getProcDeclAccess().getRightParenthesisKeyword_4());
                  
            }
            otherlv_7=(Token)match(input,17,FOLLOW_17_in_ruleProcDecl835); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_7, grammarAccess.getProcDeclAccess().getLeftCurlyBracketKeyword_5());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:460:1: ( (lv_body_8_0= ruleProcBody ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:461:1: (lv_body_8_0= ruleProcBody )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:461:1: (lv_body_8_0= ruleProcBody )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:462:3: lv_body_8_0= ruleProcBody
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getProcDeclAccess().getBodyProcBodyParserRuleCall_6_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleProcBody_in_ruleProcDecl856);
            lv_body_8_0=ruleProcBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getProcDeclRule());
              	        }
                     		set(
                     			current, 
                     			"body",
                      		lv_body_8_0, 
                      		"ProcBody");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            otherlv_9=(Token)match(input,18,FOLLOW_18_in_ruleProcDecl868); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_9, grammarAccess.getProcDeclAccess().getRightCurlyBracketKeyword_7());
                  
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
    // $ANTLR end "ruleProcDecl"


    // $ANTLR start "entryRuleParameter"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:492:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:493:2: (iv_ruleParameter= ruleParameter EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:494:2: iv_ruleParameter= ruleParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterRule()); 
            }
            pushFollow(FOLLOW_ruleParameter_in_entryRuleParameter906);
            iv_ruleParameter=ruleParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameter; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleParameter916); if (state.failed) return current;

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
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:501:1: ruleParameter returns [EObject current=null] : ( ( (lv_type_0_0= ruleType ) ) ( (lv_name_1_0= RULE_ID ) ) ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;
        EObject lv_type_0_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:504:28: ( ( ( (lv_type_0_0= ruleType ) ) ( (lv_name_1_0= RULE_ID ) ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:505:1: ( ( (lv_type_0_0= ruleType ) ) ( (lv_name_1_0= RULE_ID ) ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:505:1: ( ( (lv_type_0_0= ruleType ) ) ( (lv_name_1_0= RULE_ID ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:505:2: ( (lv_type_0_0= ruleType ) ) ( (lv_name_1_0= RULE_ID ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:505:2: ( (lv_type_0_0= ruleType ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:506:1: (lv_type_0_0= ruleType )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:506:1: (lv_type_0_0= ruleType )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:507:3: lv_type_0_0= ruleType
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getParameterAccess().getTypeTypeParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleType_in_ruleParameter962);
            lv_type_0_0=ruleType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getParameterRule());
              	        }
                     		set(
                     			current, 
                     			"type",
                      		lv_type_0_0, 
                      		"Type");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:523:2: ( (lv_name_1_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:524:1: (lv_name_1_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:524:1: (lv_name_1_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:525:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameter979); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_name_1_0, grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_1_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getParameterRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"name",
                      		lv_name_1_0, 
                      		"ID");
              	    
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
    // $ANTLR end "ruleParameter"


    // $ANTLR start "entryRuleTypeDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:549:1: entryRuleTypeDecl returns [EObject current=null] : iv_ruleTypeDecl= ruleTypeDecl EOF ;
    public final EObject entryRuleTypeDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeDecl = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:550:2: (iv_ruleTypeDecl= ruleTypeDecl EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:551:2: iv_ruleTypeDecl= ruleTypeDecl EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeDeclRule()); 
            }
            pushFollow(FOLLOW_ruleTypeDecl_in_entryRuleTypeDecl1020);
            iv_ruleTypeDecl=ruleTypeDecl();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeDecl; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleTypeDecl1030); if (state.failed) return current;

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
    // $ANTLR end "entryRuleTypeDecl"


    // $ANTLR start "ruleTypeDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:558:1: ruleTypeDecl returns [EObject current=null] : (otherlv_0= 'typedef' ( (lv_type_1_0= ruleType ) ) ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ';' ) ;
    public final EObject ruleTypeDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        EObject lv_type_1_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:561:28: ( (otherlv_0= 'typedef' ( (lv_type_1_0= ruleType ) ) ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ';' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:562:1: (otherlv_0= 'typedef' ( (lv_type_1_0= ruleType ) ) ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ';' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:562:1: (otherlv_0= 'typedef' ( (lv_type_1_0= ruleType ) ) ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ';' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:562:3: otherlv_0= 'typedef' ( (lv_type_1_0= ruleType ) ) ( (lv_name_2_0= RULE_ID ) ) otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_19_in_ruleTypeDecl1067); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getTypeDeclAccess().getTypedefKeyword_0());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:566:1: ( (lv_type_1_0= ruleType ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:567:1: (lv_type_1_0= ruleType )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:567:1: (lv_type_1_0= ruleType )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:568:3: lv_type_1_0= ruleType
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getTypeDeclAccess().getTypeTypeParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleType_in_ruleTypeDecl1088);
            lv_type_1_0=ruleType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getTypeDeclRule());
              	        }
                     		set(
                     			current, 
                     			"type",
                      		lv_type_1_0, 
                      		"Type");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:584:2: ( (lv_name_2_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:585:1: (lv_name_2_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:585:1: (lv_name_2_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:586:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTypeDecl1105); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_name_2_0, grammarAccess.getTypeDeclAccess().getNameIDTerminalRuleCall_2_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getTypeDeclRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"name",
                      		lv_name_2_0, 
                      		"ID");
              	    
            }

            }


            }

            otherlv_3=(Token)match(input,14,FOLLOW_14_in_ruleTypeDecl1122); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getTypeDeclAccess().getSemicolonKeyword_3());
                  
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
    // $ANTLR end "ruleTypeDecl"


    // $ANTLR start "entryRuleType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:614:1: entryRuleType returns [EObject current=null] : iv_ruleType= ruleType EOF ;
    public final EObject entryRuleType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleType = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:615:2: (iv_ruleType= ruleType EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:616:2: iv_ruleType= ruleType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeRule()); 
            }
            pushFollow(FOLLOW_ruleType_in_entryRuleType1158);
            iv_ruleType=ruleType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleType; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleType1168); if (state.failed) return current;

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
    // $ANTLR end "entryRuleType"


    // $ANTLR start "ruleType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:623:1: ruleType returns [EObject current=null] : (this_BasicType_0= ruleBasicType | this_TypedefRef_1= ruleTypedefRef ) ;
    public final EObject ruleType() throws RecognitionException {
        EObject current = null;

        EObject this_BasicType_0 = null;

        EObject this_TypedefRef_1 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:626:28: ( (this_BasicType_0= ruleBasicType | this_TypedefRef_1= ruleTypedefRef ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:627:1: (this_BasicType_0= ruleBasicType | this_TypedefRef_1= ruleTypedefRef )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:627:1: (this_BasicType_0= ruleBasicType | this_TypedefRef_1= ruleTypedefRef )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=23 && LA8_0<=25)||LA8_0==28) ) {
                alt8=1;
            }
            else if ( (LA8_0==RULE_ID) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:628:5: this_BasicType_0= ruleBasicType
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getTypeAccess().getBasicTypeParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_ruleBasicType_in_ruleType1215);
                    this_BasicType_0=ruleBasicType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_BasicType_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:638:5: this_TypedefRef_1= ruleTypedefRef
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getTypeAccess().getTypedefRefParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleTypedefRef_in_ruleType1242);
                    this_TypedefRef_1=ruleTypedefRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_TypedefRef_1; 
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
    // $ANTLR end "ruleType"


    // $ANTLR start "entryRuleBasicType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:654:1: entryRuleBasicType returns [EObject current=null] : iv_ruleBasicType= ruleBasicType EOF ;
    public final EObject entryRuleBasicType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBasicType = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:655:2: (iv_ruleBasicType= ruleBasicType EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:656:2: iv_ruleBasicType= ruleBasicType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBasicTypeRule()); 
            }
            pushFollow(FOLLOW_ruleBasicType_in_entryRuleBasicType1277);
            iv_ruleBasicType=ruleBasicType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBasicType; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBasicType1287); if (state.failed) return current;

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
    // $ANTLR end "entryRuleBasicType"


    // $ANTLR start "ruleBasicType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:663:1: ruleBasicType returns [EObject current=null] : (this_IntegerType_0= ruleIntegerType | this_BoolType_1= ruleBoolType | this_ClockType_2= ruleClockType | this_RangeType_3= ruleRangeType ) ;
    public final EObject ruleBasicType() throws RecognitionException {
        EObject current = null;

        EObject this_IntegerType_0 = null;

        EObject this_BoolType_1 = null;

        EObject this_ClockType_2 = null;

        EObject this_RangeType_3 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:666:28: ( (this_IntegerType_0= ruleIntegerType | this_BoolType_1= ruleBoolType | this_ClockType_2= ruleClockType | this_RangeType_3= ruleRangeType ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:667:1: (this_IntegerType_0= ruleIntegerType | this_BoolType_1= ruleBoolType | this_ClockType_2= ruleClockType | this_RangeType_3= ruleRangeType )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:667:1: (this_IntegerType_0= ruleIntegerType | this_BoolType_1= ruleBoolType | this_ClockType_2= ruleClockType | this_RangeType_3= ruleRangeType )
            int alt9=4;
            switch ( input.LA(1) ) {
            case 23:
                {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==24) ) {
                    int LA9_2 = input.LA(3);

                    if ( (LA9_2==26) ) {
                        alt9=4;
                    }
                    else if ( (LA9_2==EOF||LA9_2==RULE_ID) ) {
                        alt9=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA9_1==25) ) {
                    alt9=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }
                }
                break;
            case 24:
                {
                int LA9_2 = input.LA(2);

                if ( (LA9_2==26) ) {
                    alt9=4;
                }
                else if ( (LA9_2==EOF||LA9_2==RULE_ID) ) {
                    alt9=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 2, input);

                    throw nvae;
                }
                }
                break;
            case 25:
                {
                alt9=2;
                }
                break;
            case 28:
                {
                alt9=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:668:5: this_IntegerType_0= ruleIntegerType
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBasicTypeAccess().getIntegerTypeParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_ruleIntegerType_in_ruleBasicType1334);
                    this_IntegerType_0=ruleIntegerType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_IntegerType_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:678:5: this_BoolType_1= ruleBoolType
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBasicTypeAccess().getBoolTypeParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleBoolType_in_ruleBasicType1361);
                    this_BoolType_1=ruleBoolType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_BoolType_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:688:5: this_ClockType_2= ruleClockType
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBasicTypeAccess().getClockTypeParserRuleCall_2()); 
                          
                    }
                    pushFollow(FOLLOW_ruleClockType_in_ruleBasicType1388);
                    this_ClockType_2=ruleClockType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_ClockType_2; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:698:5: this_RangeType_3= ruleRangeType
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBasicTypeAccess().getRangeTypeParserRuleCall_3()); 
                          
                    }
                    pushFollow(FOLLOW_ruleRangeType_in_ruleBasicType1415);
                    this_RangeType_3=ruleRangeType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_RangeType_3; 
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
    // $ANTLR end "ruleBasicType"


    // $ANTLR start "entryRuleTypedefRef"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:714:1: entryRuleTypedefRef returns [EObject current=null] : iv_ruleTypedefRef= ruleTypedefRef EOF ;
    public final EObject entryRuleTypedefRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypedefRef = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:715:2: (iv_ruleTypedefRef= ruleTypedefRef EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:716:2: iv_ruleTypedefRef= ruleTypedefRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypedefRefRule()); 
            }
            pushFollow(FOLLOW_ruleTypedefRef_in_entryRuleTypedefRef1450);
            iv_ruleTypedefRef=ruleTypedefRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypedefRef; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleTypedefRef1460); if (state.failed) return current;

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
    // $ANTLR end "entryRuleTypedefRef"


    // $ANTLR start "ruleTypedefRef"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:723:1: ruleTypedefRef returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleTypedefRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:726:28: ( ( (otherlv_0= RULE_ID ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:727:1: ( (otherlv_0= RULE_ID ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:727:1: ( (otherlv_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:728:1: (otherlv_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:728:1: (otherlv_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:729:3: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getTypedefRefRule());
              	        }
                      
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTypedefRef1504); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_0, grammarAccess.getTypedefRefAccess().getRefTypeDeclCrossReference_0()); 
              	
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
    // $ANTLR end "ruleTypedefRef"


    // $ANTLR start "entryRuleChannelDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:748:1: entryRuleChannelDecl returns [EObject current=null] : iv_ruleChannelDecl= ruleChannelDecl EOF ;
    public final EObject entryRuleChannelDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChannelDecl = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:749:2: (iv_ruleChannelDecl= ruleChannelDecl EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:750:2: iv_ruleChannelDecl= ruleChannelDecl EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getChannelDeclRule()); 
            }
            pushFollow(FOLLOW_ruleChannelDecl_in_entryRuleChannelDecl1539);
            iv_ruleChannelDecl=ruleChannelDecl();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleChannelDecl; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleChannelDecl1549); if (state.failed) return current;

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
    // $ANTLR end "entryRuleChannelDecl"


    // $ANTLR start "ruleChannelDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:757:1: ruleChannelDecl returns [EObject current=null] : (this_ChannelType_0= ruleChannelType ( (lv_chans_1_0= ruleChanId ) ) (otherlv_2= ',' ( (lv_chans_3_0= ruleChanId ) ) )* otherlv_4= ';' ) ;
    public final EObject ruleChannelDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_ChannelType_0 = null;

        EObject lv_chans_1_0 = null;

        EObject lv_chans_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:760:28: ( (this_ChannelType_0= ruleChannelType ( (lv_chans_1_0= ruleChanId ) ) (otherlv_2= ',' ( (lv_chans_3_0= ruleChanId ) ) )* otherlv_4= ';' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:761:1: (this_ChannelType_0= ruleChannelType ( (lv_chans_1_0= ruleChanId ) ) (otherlv_2= ',' ( (lv_chans_3_0= ruleChanId ) ) )* otherlv_4= ';' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:761:1: (this_ChannelType_0= ruleChannelType ( (lv_chans_1_0= ruleChanId ) ) (otherlv_2= ',' ( (lv_chans_3_0= ruleChanId ) ) )* otherlv_4= ';' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:762:5: this_ChannelType_0= ruleChannelType ( (lv_chans_1_0= ruleChanId ) ) (otherlv_2= ',' ( (lv_chans_3_0= ruleChanId ) ) )* otherlv_4= ';'
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getChannelDeclAccess().getChannelTypeParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleChannelType_in_ruleChannelDecl1596);
            this_ChannelType_0=ruleChannelType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_ChannelType_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:770:1: ( (lv_chans_1_0= ruleChanId ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:771:1: (lv_chans_1_0= ruleChanId )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:771:1: (lv_chans_1_0= ruleChanId )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:772:3: lv_chans_1_0= ruleChanId
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getChannelDeclAccess().getChansChanIdParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleChanId_in_ruleChannelDecl1616);
            lv_chans_1_0=ruleChanId();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getChannelDeclRule());
              	        }
                     		add(
                     			current, 
                     			"chans",
                      		lv_chans_1_0, 
                      		"ChanId");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:788:2: (otherlv_2= ',' ( (lv_chans_3_0= ruleChanId ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==12) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:788:4: otherlv_2= ',' ( (lv_chans_3_0= ruleChanId ) )
            	    {
            	    otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleChannelDecl1629); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_2, grammarAccess.getChannelDeclAccess().getCommaKeyword_2_0());
            	          
            	    }
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:792:1: ( (lv_chans_3_0= ruleChanId ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:793:1: (lv_chans_3_0= ruleChanId )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:793:1: (lv_chans_3_0= ruleChanId )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:794:3: lv_chans_3_0= ruleChanId
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getChannelDeclAccess().getChansChanIdParserRuleCall_2_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleChanId_in_ruleChannelDecl1650);
            	    lv_chans_3_0=ruleChanId();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getChannelDeclRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"chans",
            	              		lv_chans_3_0, 
            	              		"ChanId");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            otherlv_4=(Token)match(input,14,FOLLOW_14_in_ruleChannelDecl1664); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getChannelDeclAccess().getSemicolonKeyword_3());
                  
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
    // $ANTLR end "ruleChannelDecl"


    // $ANTLR start "entryRuleChanId"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:822:1: entryRuleChanId returns [EObject current=null] : iv_ruleChanId= ruleChanId EOF ;
    public final EObject entryRuleChanId() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChanId = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:823:2: (iv_ruleChanId= ruleChanId EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:824:2: iv_ruleChanId= ruleChanId EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getChanIdRule()); 
            }
            pushFollow(FOLLOW_ruleChanId_in_entryRuleChanId1700);
            iv_ruleChanId=ruleChanId();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleChanId; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleChanId1710); if (state.failed) return current;

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
    // $ANTLR end "entryRuleChanId"


    // $ANTLR start "ruleChanId"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:831:1: ruleChanId returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleChanId() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:834:28: ( ( (lv_name_0_0= RULE_ID ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:835:1: ( (lv_name_0_0= RULE_ID ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:835:1: ( (lv_name_0_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:836:1: (lv_name_0_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:836:1: (lv_name_0_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:837:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleChanId1751); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_name_0_0, grammarAccess.getChanIdAccess().getNameIDTerminalRuleCall_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getChanIdRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"name",
                      		lv_name_0_0, 
                      		"ID");
              	    
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
    // $ANTLR end "ruleChanId"


    // $ANTLR start "entryRuleChannelType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:861:1: entryRuleChannelType returns [EObject current=null] : iv_ruleChannelType= ruleChannelType EOF ;
    public final EObject entryRuleChannelType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChannelType = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:862:2: (iv_ruleChannelType= ruleChannelType EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:863:2: iv_ruleChannelType= ruleChannelType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getChannelTypeRule()); 
            }
            pushFollow(FOLLOW_ruleChannelType_in_entryRuleChannelType1791);
            iv_ruleChannelType=ruleChannelType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleChannelType; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleChannelType1801); if (state.failed) return current;

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
    // $ANTLR end "entryRuleChannelType"


    // $ANTLR start "ruleChannelType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:870:1: ruleChannelType returns [EObject current=null] : ( () otherlv_1= 'chan' ( (lv_urgent_2_0= 'urgent' ) )? ( (lv_broadcast_3_0= 'broadcast' ) )? ) ;
    public final EObject ruleChannelType() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_urgent_2_0=null;
        Token lv_broadcast_3_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:873:28: ( ( () otherlv_1= 'chan' ( (lv_urgent_2_0= 'urgent' ) )? ( (lv_broadcast_3_0= 'broadcast' ) )? ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:874:1: ( () otherlv_1= 'chan' ( (lv_urgent_2_0= 'urgent' ) )? ( (lv_broadcast_3_0= 'broadcast' ) )? )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:874:1: ( () otherlv_1= 'chan' ( (lv_urgent_2_0= 'urgent' ) )? ( (lv_broadcast_3_0= 'broadcast' ) )? )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:874:2: () otherlv_1= 'chan' ( (lv_urgent_2_0= 'urgent' ) )? ( (lv_broadcast_3_0= 'broadcast' ) )?
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:874:2: ()
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:875:5: 
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getChannelTypeAccess().getChannelTypeAction_0(),
                          current);
                  
            }

            }

            otherlv_1=(Token)match(input,20,FOLLOW_20_in_ruleChannelType1847); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getChannelTypeAccess().getChanKeyword_1());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:884:1: ( (lv_urgent_2_0= 'urgent' ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==21) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:885:1: (lv_urgent_2_0= 'urgent' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:885:1: (lv_urgent_2_0= 'urgent' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:886:3: lv_urgent_2_0= 'urgent'
                    {
                    lv_urgent_2_0=(Token)match(input,21,FOLLOW_21_in_ruleChannelType1865); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_urgent_2_0, grammarAccess.getChannelTypeAccess().getUrgentUrgentKeyword_2_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getChannelTypeRule());
                      	        }
                             		setWithLastConsumed(current, "urgent", true, "urgent");
                      	    
                    }

                    }


                    }
                    break;

            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:899:3: ( (lv_broadcast_3_0= 'broadcast' ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==22) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:900:1: (lv_broadcast_3_0= 'broadcast' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:900:1: (lv_broadcast_3_0= 'broadcast' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:901:3: lv_broadcast_3_0= 'broadcast'
                    {
                    lv_broadcast_3_0=(Token)match(input,22,FOLLOW_22_in_ruleChannelType1897); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_broadcast_3_0, grammarAccess.getChannelTypeAccess().getBroadcastBroadcastKeyword_3_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getChannelTypeRule());
                      	        }
                             		setWithLastConsumed(current, "broadcast", true, "broadcast");
                      	    
                    }

                    }


                    }
                    break;

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
    // $ANTLR end "ruleChannelType"


    // $ANTLR start "entryRuleIntegerType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:922:1: entryRuleIntegerType returns [EObject current=null] : iv_ruleIntegerType= ruleIntegerType EOF ;
    public final EObject entryRuleIntegerType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntegerType = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:923:2: (iv_ruleIntegerType= ruleIntegerType EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:924:2: iv_ruleIntegerType= ruleIntegerType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIntegerTypeRule()); 
            }
            pushFollow(FOLLOW_ruleIntegerType_in_entryRuleIntegerType1947);
            iv_ruleIntegerType=ruleIntegerType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIntegerType; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleIntegerType1957); if (state.failed) return current;

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
    // $ANTLR end "entryRuleIntegerType"


    // $ANTLR start "ruleIntegerType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:931:1: ruleIntegerType returns [EObject current=null] : ( () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'int' ) ;
    public final EObject ruleIntegerType() throws RecognitionException {
        EObject current = null;

        Token lv_const_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:934:28: ( ( () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'int' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:935:1: ( () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'int' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:935:1: ( () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'int' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:935:2: () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'int'
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:935:2: ()
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:936:5: 
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getIntegerTypeAccess().getIntegerTypeAction_0(),
                          current);
                  
            }

            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:941:2: ( (lv_const_1_0= 'const' ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==23) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:942:1: (lv_const_1_0= 'const' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:942:1: (lv_const_1_0= 'const' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:943:3: lv_const_1_0= 'const'
                    {
                    lv_const_1_0=(Token)match(input,23,FOLLOW_23_in_ruleIntegerType2009); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_const_1_0, grammarAccess.getIntegerTypeAccess().getConstConstKeyword_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getIntegerTypeRule());
                      	        }
                             		setWithLastConsumed(current, "const", true, "const");
                      	    
                    }

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,24,FOLLOW_24_in_ruleIntegerType2035); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getIntegerTypeAccess().getIntKeyword_2());
                  
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
    // $ANTLR end "ruleIntegerType"


    // $ANTLR start "entryRuleBoolType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:968:1: entryRuleBoolType returns [EObject current=null] : iv_ruleBoolType= ruleBoolType EOF ;
    public final EObject entryRuleBoolType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBoolType = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:969:2: (iv_ruleBoolType= ruleBoolType EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:970:2: iv_ruleBoolType= ruleBoolType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBoolTypeRule()); 
            }
            pushFollow(FOLLOW_ruleBoolType_in_entryRuleBoolType2071);
            iv_ruleBoolType=ruleBoolType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBoolType; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBoolType2081); if (state.failed) return current;

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
    // $ANTLR end "entryRuleBoolType"


    // $ANTLR start "ruleBoolType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:977:1: ruleBoolType returns [EObject current=null] : ( () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'bool' ) ;
    public final EObject ruleBoolType() throws RecognitionException {
        EObject current = null;

        Token lv_const_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:980:28: ( ( () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'bool' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:981:1: ( () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'bool' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:981:1: ( () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'bool' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:981:2: () ( (lv_const_1_0= 'const' ) )? otherlv_2= 'bool'
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:981:2: ()
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:982:5: 
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getBoolTypeAccess().getBoolTypeAction_0(),
                          current);
                  
            }

            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:987:2: ( (lv_const_1_0= 'const' ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==23) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:988:1: (lv_const_1_0= 'const' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:988:1: (lv_const_1_0= 'const' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:989:3: lv_const_1_0= 'const'
                    {
                    lv_const_1_0=(Token)match(input,23,FOLLOW_23_in_ruleBoolType2133); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_const_1_0, grammarAccess.getBoolTypeAccess().getConstConstKeyword_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getBoolTypeRule());
                      	        }
                             		setWithLastConsumed(current, "const", true, "const");
                      	    
                    }

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,25,FOLLOW_25_in_ruleBoolType2159); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getBoolTypeAccess().getBoolKeyword_2());
                  
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
    // $ANTLR end "ruleBoolType"


    // $ANTLR start "entryRuleRangeType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1014:1: entryRuleRangeType returns [EObject current=null] : iv_ruleRangeType= ruleRangeType EOF ;
    public final EObject entryRuleRangeType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRangeType = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1015:2: (iv_ruleRangeType= ruleRangeType EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1016:2: iv_ruleRangeType= ruleRangeType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRangeTypeRule()); 
            }
            pushFollow(FOLLOW_ruleRangeType_in_entryRuleRangeType2195);
            iv_ruleRangeType=ruleRangeType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRangeType; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleRangeType2205); if (state.failed) return current;

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
    // $ANTLR end "entryRuleRangeType"


    // $ANTLR start "ruleRangeType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1023:1: ruleRangeType returns [EObject current=null] : ( ( (lv_const_0_0= 'const' ) )? otherlv_1= 'int' otherlv_2= '[' ( (lv_min_3_0= RULE_INT ) ) otherlv_4= ',' ( (lv_max_5_0= RULE_INT ) ) otherlv_6= ']' ) ;
    public final EObject ruleRangeType() throws RecognitionException {
        EObject current = null;

        Token lv_const_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_min_3_0=null;
        Token otherlv_4=null;
        Token lv_max_5_0=null;
        Token otherlv_6=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1026:28: ( ( ( (lv_const_0_0= 'const' ) )? otherlv_1= 'int' otherlv_2= '[' ( (lv_min_3_0= RULE_INT ) ) otherlv_4= ',' ( (lv_max_5_0= RULE_INT ) ) otherlv_6= ']' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1027:1: ( ( (lv_const_0_0= 'const' ) )? otherlv_1= 'int' otherlv_2= '[' ( (lv_min_3_0= RULE_INT ) ) otherlv_4= ',' ( (lv_max_5_0= RULE_INT ) ) otherlv_6= ']' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1027:1: ( ( (lv_const_0_0= 'const' ) )? otherlv_1= 'int' otherlv_2= '[' ( (lv_min_3_0= RULE_INT ) ) otherlv_4= ',' ( (lv_max_5_0= RULE_INT ) ) otherlv_6= ']' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1027:2: ( (lv_const_0_0= 'const' ) )? otherlv_1= 'int' otherlv_2= '[' ( (lv_min_3_0= RULE_INT ) ) otherlv_4= ',' ( (lv_max_5_0= RULE_INT ) ) otherlv_6= ']'
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1027:2: ( (lv_const_0_0= 'const' ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==23) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1028:1: (lv_const_0_0= 'const' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1028:1: (lv_const_0_0= 'const' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1029:3: lv_const_0_0= 'const'
                    {
                    lv_const_0_0=(Token)match(input,23,FOLLOW_23_in_ruleRangeType2248); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_const_0_0, grammarAccess.getRangeTypeAccess().getConstConstKeyword_0_0());
                          
                    }
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElement(grammarAccess.getRangeTypeRule());
                      	        }
                             		setWithLastConsumed(current, "const", true, "const");
                      	    
                    }

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,24,FOLLOW_24_in_ruleRangeType2274); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getRangeTypeAccess().getIntKeyword_1());
                  
            }
            otherlv_2=(Token)match(input,26,FOLLOW_26_in_ruleRangeType2286); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getRangeTypeAccess().getLeftSquareBracketKeyword_2());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1050:1: ( (lv_min_3_0= RULE_INT ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1051:1: (lv_min_3_0= RULE_INT )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1051:1: (lv_min_3_0= RULE_INT )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1052:3: lv_min_3_0= RULE_INT
            {
            lv_min_3_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleRangeType2303); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_min_3_0, grammarAccess.getRangeTypeAccess().getMinINTTerminalRuleCall_3_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getRangeTypeRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"min",
                      		lv_min_3_0, 
                      		"INT");
              	    
            }

            }


            }

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleRangeType2320); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getRangeTypeAccess().getCommaKeyword_4());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1072:1: ( (lv_max_5_0= RULE_INT ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1073:1: (lv_max_5_0= RULE_INT )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1073:1: (lv_max_5_0= RULE_INT )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1074:3: lv_max_5_0= RULE_INT
            {
            lv_max_5_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleRangeType2337); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_max_5_0, grammarAccess.getRangeTypeAccess().getMaxINTTerminalRuleCall_5_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getRangeTypeRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"max",
                      		lv_max_5_0, 
                      		"INT");
              	    
            }

            }


            }

            otherlv_6=(Token)match(input,27,FOLLOW_27_in_ruleRangeType2354); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_6, grammarAccess.getRangeTypeAccess().getRightSquareBracketKeyword_6());
                  
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
    // $ANTLR end "ruleRangeType"


    // $ANTLR start "entryRuleClockType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1102:1: entryRuleClockType returns [EObject current=null] : iv_ruleClockType= ruleClockType EOF ;
    public final EObject entryRuleClockType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClockType = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1103:2: (iv_ruleClockType= ruleClockType EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1104:2: iv_ruleClockType= ruleClockType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getClockTypeRule()); 
            }
            pushFollow(FOLLOW_ruleClockType_in_entryRuleClockType2390);
            iv_ruleClockType=ruleClockType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleClockType; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleClockType2400); if (state.failed) return current;

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
    // $ANTLR end "entryRuleClockType"


    // $ANTLR start "ruleClockType"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1111:1: ruleClockType returns [EObject current=null] : ( () otherlv_1= 'clock' ) ;
    public final EObject ruleClockType() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1114:28: ( ( () otherlv_1= 'clock' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1115:1: ( () otherlv_1= 'clock' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1115:1: ( () otherlv_1= 'clock' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1115:2: () otherlv_1= 'clock'
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1115:2: ()
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1116:5: 
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getClockTypeAccess().getClockTypeAction_0(),
                          current);
                  
            }

            }

            otherlv_1=(Token)match(input,28,FOLLOW_28_in_ruleClockType2446); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getClockTypeAccess().getClockKeyword_1());
                  
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
    // $ANTLR end "ruleClockType"


    // $ANTLR start "entryRuleProcBody"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1133:1: entryRuleProcBody returns [EObject current=null] : iv_ruleProcBody= ruleProcBody EOF ;
    public final EObject entryRuleProcBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProcBody = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1134:2: (iv_ruleProcBody= ruleProcBody EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1135:2: iv_ruleProcBody= ruleProcBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getProcBodyRule()); 
            }
            pushFollow(FOLLOW_ruleProcBody_in_entryRuleProcBody2482);
            iv_ruleProcBody=ruleProcBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleProcBody; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleProcBody2492); if (state.failed) return current;

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
    // $ANTLR end "entryRuleProcBody"


    // $ANTLR start "ruleProcBody"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1142:1: ruleProcBody returns [EObject current=null] : ( ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_types_1_0= ruleTypeDecl ) ) )* otherlv_2= 'state' ( (lv_states_3_0= ruleStateDecl ) ) (otherlv_4= ',' ( (lv_states_5_0= ruleStateDecl ) ) )* otherlv_6= ';' (otherlv_7= 'commit' ( (otherlv_8= RULE_ID ) ) (otherlv_9= ',' ( (otherlv_10= RULE_ID ) ) )* otherlv_11= ';' )? (otherlv_12= 'urgent' ( (otherlv_13= RULE_ID ) ) (otherlv_14= ',' ( (otherlv_15= RULE_ID ) ) )* otherlv_16= ';' )? otherlv_17= 'init' ( (otherlv_18= RULE_ID ) ) otherlv_19= ';' (otherlv_20= 'trans' ( (lv_transitions_21_0= ruleTransition ) ) (otherlv_22= ',' ( (lv_transitions_23_0= ruleTransition ) ) )* otherlv_24= ';' ) ) ;
    public final EObject ruleProcBody() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token otherlv_24=null;
        EObject lv_variables_0_0 = null;

        EObject lv_types_1_0 = null;

        EObject lv_states_3_0 = null;

        EObject lv_states_5_0 = null;

        EObject lv_transitions_21_0 = null;

        EObject lv_transitions_23_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1145:28: ( ( ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_types_1_0= ruleTypeDecl ) ) )* otherlv_2= 'state' ( (lv_states_3_0= ruleStateDecl ) ) (otherlv_4= ',' ( (lv_states_5_0= ruleStateDecl ) ) )* otherlv_6= ';' (otherlv_7= 'commit' ( (otherlv_8= RULE_ID ) ) (otherlv_9= ',' ( (otherlv_10= RULE_ID ) ) )* otherlv_11= ';' )? (otherlv_12= 'urgent' ( (otherlv_13= RULE_ID ) ) (otherlv_14= ',' ( (otherlv_15= RULE_ID ) ) )* otherlv_16= ';' )? otherlv_17= 'init' ( (otherlv_18= RULE_ID ) ) otherlv_19= ';' (otherlv_20= 'trans' ( (lv_transitions_21_0= ruleTransition ) ) (otherlv_22= ',' ( (lv_transitions_23_0= ruleTransition ) ) )* otherlv_24= ';' ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1146:1: ( ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_types_1_0= ruleTypeDecl ) ) )* otherlv_2= 'state' ( (lv_states_3_0= ruleStateDecl ) ) (otherlv_4= ',' ( (lv_states_5_0= ruleStateDecl ) ) )* otherlv_6= ';' (otherlv_7= 'commit' ( (otherlv_8= RULE_ID ) ) (otherlv_9= ',' ( (otherlv_10= RULE_ID ) ) )* otherlv_11= ';' )? (otherlv_12= 'urgent' ( (otherlv_13= RULE_ID ) ) (otherlv_14= ',' ( (otherlv_15= RULE_ID ) ) )* otherlv_16= ';' )? otherlv_17= 'init' ( (otherlv_18= RULE_ID ) ) otherlv_19= ';' (otherlv_20= 'trans' ( (lv_transitions_21_0= ruleTransition ) ) (otherlv_22= ',' ( (lv_transitions_23_0= ruleTransition ) ) )* otherlv_24= ';' ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1146:1: ( ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_types_1_0= ruleTypeDecl ) ) )* otherlv_2= 'state' ( (lv_states_3_0= ruleStateDecl ) ) (otherlv_4= ',' ( (lv_states_5_0= ruleStateDecl ) ) )* otherlv_6= ';' (otherlv_7= 'commit' ( (otherlv_8= RULE_ID ) ) (otherlv_9= ',' ( (otherlv_10= RULE_ID ) ) )* otherlv_11= ';' )? (otherlv_12= 'urgent' ( (otherlv_13= RULE_ID ) ) (otherlv_14= ',' ( (otherlv_15= RULE_ID ) ) )* otherlv_16= ';' )? otherlv_17= 'init' ( (otherlv_18= RULE_ID ) ) otherlv_19= ';' (otherlv_20= 'trans' ( (lv_transitions_21_0= ruleTransition ) ) (otherlv_22= ',' ( (lv_transitions_23_0= ruleTransition ) ) )* otherlv_24= ';' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1146:2: ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_types_1_0= ruleTypeDecl ) ) )* otherlv_2= 'state' ( (lv_states_3_0= ruleStateDecl ) ) (otherlv_4= ',' ( (lv_states_5_0= ruleStateDecl ) ) )* otherlv_6= ';' (otherlv_7= 'commit' ( (otherlv_8= RULE_ID ) ) (otherlv_9= ',' ( (otherlv_10= RULE_ID ) ) )* otherlv_11= ';' )? (otherlv_12= 'urgent' ( (otherlv_13= RULE_ID ) ) (otherlv_14= ',' ( (otherlv_15= RULE_ID ) ) )* otherlv_16= ';' )? otherlv_17= 'init' ( (otherlv_18= RULE_ID ) ) otherlv_19= ';' (otherlv_20= 'trans' ( (lv_transitions_21_0= ruleTransition ) ) (otherlv_22= ',' ( (lv_transitions_23_0= ruleTransition ) ) )* otherlv_24= ';' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1146:2: ( ( (lv_variables_0_0= ruleVariableDecl ) ) | ( (lv_types_1_0= ruleTypeDecl ) ) )*
            loop16:
            do {
                int alt16=3;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=23 && LA16_0<=25)||LA16_0==28) ) {
                    alt16=1;
                }
                else if ( (LA16_0==19) ) {
                    alt16=2;
                }


                switch (alt16) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1146:3: ( (lv_variables_0_0= ruleVariableDecl ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1146:3: ( (lv_variables_0_0= ruleVariableDecl ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1147:1: (lv_variables_0_0= ruleVariableDecl )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1147:1: (lv_variables_0_0= ruleVariableDecl )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1148:3: lv_variables_0_0= ruleVariableDecl
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getProcBodyAccess().getVariablesVariableDeclParserRuleCall_0_0_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleVariableDecl_in_ruleProcBody2539);
            	    lv_variables_0_0=ruleVariableDecl();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getProcBodyRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"variables",
            	              		lv_variables_0_0, 
            	              		"VariableDecl");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1165:6: ( (lv_types_1_0= ruleTypeDecl ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1165:6: ( (lv_types_1_0= ruleTypeDecl ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1166:1: (lv_types_1_0= ruleTypeDecl )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1166:1: (lv_types_1_0= ruleTypeDecl )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1167:3: lv_types_1_0= ruleTypeDecl
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getProcBodyAccess().getTypesTypeDeclParserRuleCall_0_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleTypeDecl_in_ruleProcBody2566);
            	    lv_types_1_0=ruleTypeDecl();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getProcBodyRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"types",
            	              		lv_types_1_0, 
            	              		"TypeDecl");
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

            otherlv_2=(Token)match(input,29,FOLLOW_29_in_ruleProcBody2580); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getProcBodyAccess().getStateKeyword_1());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1187:1: ( (lv_states_3_0= ruleStateDecl ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1188:1: (lv_states_3_0= ruleStateDecl )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1188:1: (lv_states_3_0= ruleStateDecl )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1189:3: lv_states_3_0= ruleStateDecl
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getProcBodyAccess().getStatesStateDeclParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleStateDecl_in_ruleProcBody2601);
            lv_states_3_0=ruleStateDecl();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getProcBodyRule());
              	        }
                     		add(
                     			current, 
                     			"states",
                      		lv_states_3_0, 
                      		"StateDecl");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1205:2: (otherlv_4= ',' ( (lv_states_5_0= ruleStateDecl ) ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==12) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1205:4: otherlv_4= ',' ( (lv_states_5_0= ruleStateDecl ) )
            	    {
            	    otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleProcBody2614); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_4, grammarAccess.getProcBodyAccess().getCommaKeyword_3_0());
            	          
            	    }
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1209:1: ( (lv_states_5_0= ruleStateDecl ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1210:1: (lv_states_5_0= ruleStateDecl )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1210:1: (lv_states_5_0= ruleStateDecl )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1211:3: lv_states_5_0= ruleStateDecl
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getProcBodyAccess().getStatesStateDeclParserRuleCall_3_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleStateDecl_in_ruleProcBody2635);
            	    lv_states_5_0=ruleStateDecl();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getProcBodyRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"states",
            	              		lv_states_5_0, 
            	              		"StateDecl");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            otherlv_6=(Token)match(input,14,FOLLOW_14_in_ruleProcBody2649); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_6, grammarAccess.getProcBodyAccess().getSemicolonKeyword_4());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1231:1: (otherlv_7= 'commit' ( (otherlv_8= RULE_ID ) ) (otherlv_9= ',' ( (otherlv_10= RULE_ID ) ) )* otherlv_11= ';' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==30) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1231:3: otherlv_7= 'commit' ( (otherlv_8= RULE_ID ) ) (otherlv_9= ',' ( (otherlv_10= RULE_ID ) ) )* otherlv_11= ';'
                    {
                    otherlv_7=(Token)match(input,30,FOLLOW_30_in_ruleProcBody2662); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_7, grammarAccess.getProcBodyAccess().getCommitKeyword_5_0());
                          
                    }
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1235:1: ( (otherlv_8= RULE_ID ) )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1236:1: (otherlv_8= RULE_ID )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1236:1: (otherlv_8= RULE_ID )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1237:3: otherlv_8= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      			if (current==null) {
                      	            current = createModelElement(grammarAccess.getProcBodyRule());
                      	        }
                              
                    }
                    otherlv_8=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleProcBody2682); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		newLeafNode(otherlv_8, grammarAccess.getProcBodyAccess().getCommitStatesStateDeclCrossReference_5_1_0()); 
                      	
                    }

                    }


                    }

                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1248:2: (otherlv_9= ',' ( (otherlv_10= RULE_ID ) ) )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==12) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1248:4: otherlv_9= ',' ( (otherlv_10= RULE_ID ) )
                    	    {
                    	    otherlv_9=(Token)match(input,12,FOLLOW_12_in_ruleProcBody2695); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_9, grammarAccess.getProcBodyAccess().getCommaKeyword_5_2_0());
                    	          
                    	    }
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1252:1: ( (otherlv_10= RULE_ID ) )
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1253:1: (otherlv_10= RULE_ID )
                    	    {
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1253:1: (otherlv_10= RULE_ID )
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1254:3: otherlv_10= RULE_ID
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      			if (current==null) {
                    	      	            current = createModelElement(grammarAccess.getProcBodyRule());
                    	      	        }
                    	              
                    	    }
                    	    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleProcBody2715); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      		newLeafNode(otherlv_10, grammarAccess.getProcBodyAccess().getCommitStatesStateDeclCrossReference_5_2_1_0()); 
                    	      	
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);

                    otherlv_11=(Token)match(input,14,FOLLOW_14_in_ruleProcBody2729); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_11, grammarAccess.getProcBodyAccess().getSemicolonKeyword_5_3());
                          
                    }

                    }
                    break;

            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1269:3: (otherlv_12= 'urgent' ( (otherlv_13= RULE_ID ) ) (otherlv_14= ',' ( (otherlv_15= RULE_ID ) ) )* otherlv_16= ';' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==21) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1269:5: otherlv_12= 'urgent' ( (otherlv_13= RULE_ID ) ) (otherlv_14= ',' ( (otherlv_15= RULE_ID ) ) )* otherlv_16= ';'
                    {
                    otherlv_12=(Token)match(input,21,FOLLOW_21_in_ruleProcBody2744); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_12, grammarAccess.getProcBodyAccess().getUrgentKeyword_6_0());
                          
                    }
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1273:1: ( (otherlv_13= RULE_ID ) )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1274:1: (otherlv_13= RULE_ID )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1274:1: (otherlv_13= RULE_ID )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1275:3: otherlv_13= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      			if (current==null) {
                      	            current = createModelElement(grammarAccess.getProcBodyRule());
                      	        }
                              
                    }
                    otherlv_13=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleProcBody2764); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      		newLeafNode(otherlv_13, grammarAccess.getProcBodyAccess().getUrgentStatesStateDeclCrossReference_6_1_0()); 
                      	
                    }

                    }


                    }

                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1286:2: (otherlv_14= ',' ( (otherlv_15= RULE_ID ) ) )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==12) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1286:4: otherlv_14= ',' ( (otherlv_15= RULE_ID ) )
                    	    {
                    	    otherlv_14=(Token)match(input,12,FOLLOW_12_in_ruleProcBody2777); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	          	newLeafNode(otherlv_14, grammarAccess.getProcBodyAccess().getCommaKeyword_6_2_0());
                    	          
                    	    }
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1290:1: ( (otherlv_15= RULE_ID ) )
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1291:1: (otherlv_15= RULE_ID )
                    	    {
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1291:1: (otherlv_15= RULE_ID )
                    	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1292:3: otherlv_15= RULE_ID
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      			if (current==null) {
                    	      	            current = createModelElement(grammarAccess.getProcBodyRule());
                    	      	        }
                    	              
                    	    }
                    	    otherlv_15=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleProcBody2797); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      		newLeafNode(otherlv_15, grammarAccess.getProcBodyAccess().getUrgentStatesStateDeclCrossReference_6_2_1_0()); 
                    	      	
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);

                    otherlv_16=(Token)match(input,14,FOLLOW_14_in_ruleProcBody2811); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_16, grammarAccess.getProcBodyAccess().getSemicolonKeyword_6_3());
                          
                    }

                    }
                    break;

            }

            otherlv_17=(Token)match(input,31,FOLLOW_31_in_ruleProcBody2825); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_17, grammarAccess.getProcBodyAccess().getInitKeyword_7());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1311:1: ( (otherlv_18= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1312:1: (otherlv_18= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1312:1: (otherlv_18= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1313:3: otherlv_18= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getProcBodyRule());
              	        }
                      
            }
            otherlv_18=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleProcBody2845); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_18, grammarAccess.getProcBodyAccess().getInitStateStateDeclCrossReference_8_0()); 
              	
            }

            }


            }

            otherlv_19=(Token)match(input,14,FOLLOW_14_in_ruleProcBody2857); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_19, grammarAccess.getProcBodyAccess().getSemicolonKeyword_9());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1328:1: (otherlv_20= 'trans' ( (lv_transitions_21_0= ruleTransition ) ) (otherlv_22= ',' ( (lv_transitions_23_0= ruleTransition ) ) )* otherlv_24= ';' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1328:3: otherlv_20= 'trans' ( (lv_transitions_21_0= ruleTransition ) ) (otherlv_22= ',' ( (lv_transitions_23_0= ruleTransition ) ) )* otherlv_24= ';'
            {
            otherlv_20=(Token)match(input,32,FOLLOW_32_in_ruleProcBody2870); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_20, grammarAccess.getProcBodyAccess().getTransKeyword_10_0());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1332:1: ( (lv_transitions_21_0= ruleTransition ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1333:1: (lv_transitions_21_0= ruleTransition )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1333:1: (lv_transitions_21_0= ruleTransition )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1334:3: lv_transitions_21_0= ruleTransition
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getProcBodyAccess().getTransitionsTransitionParserRuleCall_10_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleTransition_in_ruleProcBody2891);
            lv_transitions_21_0=ruleTransition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getProcBodyRule());
              	        }
                     		add(
                     			current, 
                     			"transitions",
                      		lv_transitions_21_0, 
                      		"Transition");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1350:2: (otherlv_22= ',' ( (lv_transitions_23_0= ruleTransition ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==12) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1350:4: otherlv_22= ',' ( (lv_transitions_23_0= ruleTransition ) )
            	    {
            	    otherlv_22=(Token)match(input,12,FOLLOW_12_in_ruleProcBody2904); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_22, grammarAccess.getProcBodyAccess().getCommaKeyword_10_2_0());
            	          
            	    }
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1354:1: ( (lv_transitions_23_0= ruleTransition ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1355:1: (lv_transitions_23_0= ruleTransition )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1355:1: (lv_transitions_23_0= ruleTransition )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1356:3: lv_transitions_23_0= ruleTransition
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getProcBodyAccess().getTransitionsTransitionParserRuleCall_10_2_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleTransition_in_ruleProcBody2925);
            	    lv_transitions_23_0=ruleTransition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getProcBodyRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"transitions",
            	              		lv_transitions_23_0, 
            	              		"Transition");
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

            otherlv_24=(Token)match(input,14,FOLLOW_14_in_ruleProcBody2939); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_24, grammarAccess.getProcBodyAccess().getSemicolonKeyword_10_3());
                  
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
    // $ANTLR end "ruleProcBody"


    // $ANTLR start "entryRuleStateDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1384:1: entryRuleStateDecl returns [EObject current=null] : iv_ruleStateDecl= ruleStateDecl EOF ;
    public final EObject entryRuleStateDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateDecl = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1385:2: (iv_ruleStateDecl= ruleStateDecl EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1386:2: iv_ruleStateDecl= ruleStateDecl EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStateDeclRule()); 
            }
            pushFollow(FOLLOW_ruleStateDecl_in_entryRuleStateDecl2976);
            iv_ruleStateDecl=ruleStateDecl();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStateDecl; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleStateDecl2986); if (state.failed) return current;

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
    // $ANTLR end "entryRuleStateDecl"


    // $ANTLR start "ruleStateDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1393:1: ruleStateDecl returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_invariant_2_0= ruleOr ) ) otherlv_3= '}' )? ) ;
    public final EObject ruleStateDecl() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_invariant_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1396:28: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_invariant_2_0= ruleOr ) ) otherlv_3= '}' )? ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1397:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_invariant_2_0= ruleOr ) ) otherlv_3= '}' )? )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1397:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_invariant_2_0= ruleOr ) ) otherlv_3= '}' )? )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1397:2: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '{' ( (lv_invariant_2_0= ruleOr ) ) otherlv_3= '}' )?
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1397:2: ( (lv_name_0_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1398:1: (lv_name_0_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1398:1: (lv_name_0_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1399:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleStateDecl3028); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_name_0_0, grammarAccess.getStateDeclAccess().getNameIDTerminalRuleCall_0_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getStateDeclRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"name",
                      		lv_name_0_0, 
                      		"ID");
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1415:2: (otherlv_1= '{' ( (lv_invariant_2_0= ruleOr ) ) otherlv_3= '}' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==17) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1415:4: otherlv_1= '{' ( (lv_invariant_2_0= ruleOr ) ) otherlv_3= '}'
                    {
                    otherlv_1=(Token)match(input,17,FOLLOW_17_in_ruleStateDecl3046); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_1, grammarAccess.getStateDeclAccess().getLeftCurlyBracketKeyword_1_0());
                          
                    }
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1419:1: ( (lv_invariant_2_0= ruleOr ) )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1420:1: (lv_invariant_2_0= ruleOr )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1420:1: (lv_invariant_2_0= ruleOr )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1421:3: lv_invariant_2_0= ruleOr
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getStateDeclAccess().getInvariantOrParserRuleCall_1_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_ruleOr_in_ruleStateDecl3067);
                    lv_invariant_2_0=ruleOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getStateDeclRule());
                      	        }
                             		set(
                             			current, 
                             			"invariant",
                              		lv_invariant_2_0, 
                              		"Or");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,18,FOLLOW_18_in_ruleStateDecl3079); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getStateDeclAccess().getRightCurlyBracketKeyword_1_2());
                          
                    }

                    }
                    break;

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
    // $ANTLR end "ruleStateDecl"


    // $ANTLR start "entryRuleTransition"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1449:1: entryRuleTransition returns [EObject current=null] : iv_ruleTransition= ruleTransition EOF ;
    public final EObject entryRuleTransition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransition = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1450:2: (iv_ruleTransition= ruleTransition EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1451:2: iv_ruleTransition= ruleTransition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTransitionRule()); 
            }
            pushFollow(FOLLOW_ruleTransition_in_entryRuleTransition3117);
            iv_ruleTransition=ruleTransition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTransition; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleTransition3127); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1458:1: ruleTransition returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '->' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' (otherlv_4= 'guard' ( (lv_guard_5_0= ruleOr ) ) otherlv_6= ';' )? ( (lv_sync_7_0= ruleSync ) )? ( (lv_assign_8_0= ruleAssignments ) )? otherlv_9= '}' ) ;
    public final EObject ruleTransition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_9=null;
        EObject lv_guard_5_0 = null;

        EObject lv_sync_7_0 = null;

        EObject lv_assign_8_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1461:28: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '->' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' (otherlv_4= 'guard' ( (lv_guard_5_0= ruleOr ) ) otherlv_6= ';' )? ( (lv_sync_7_0= ruleSync ) )? ( (lv_assign_8_0= ruleAssignments ) )? otherlv_9= '}' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1462:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '->' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' (otherlv_4= 'guard' ( (lv_guard_5_0= ruleOr ) ) otherlv_6= ';' )? ( (lv_sync_7_0= ruleSync ) )? ( (lv_assign_8_0= ruleAssignments ) )? otherlv_9= '}' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1462:1: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= '->' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' (otherlv_4= 'guard' ( (lv_guard_5_0= ruleOr ) ) otherlv_6= ';' )? ( (lv_sync_7_0= ruleSync ) )? ( (lv_assign_8_0= ruleAssignments ) )? otherlv_9= '}' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1462:2: ( (otherlv_0= RULE_ID ) ) otherlv_1= '->' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' (otherlv_4= 'guard' ( (lv_guard_5_0= ruleOr ) ) otherlv_6= ';' )? ( (lv_sync_7_0= ruleSync ) )? ( (lv_assign_8_0= ruleAssignments ) )? otherlv_9= '}'
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1462:2: ( (otherlv_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1463:1: (otherlv_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1463:1: (otherlv_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1464:3: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getTransitionRule());
              	        }
                      
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTransition3172); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_0, grammarAccess.getTransitionAccess().getSrcStateDeclCrossReference_0_0()); 
              	
            }

            }


            }

            otherlv_1=(Token)match(input,33,FOLLOW_33_in_ruleTransition3184); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getTransitionAccess().getHyphenMinusGreaterThanSignKeyword_1());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1479:1: ( (otherlv_2= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1480:1: (otherlv_2= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1480:1: (otherlv_2= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1481:3: otherlv_2= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getTransitionRule());
              	        }
                      
            }
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTransition3204); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_2, grammarAccess.getTransitionAccess().getDestStateDeclCrossReference_2_0()); 
              	
            }

            }


            }

            otherlv_3=(Token)match(input,17,FOLLOW_17_in_ruleTransition3216); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getTransitionAccess().getLeftCurlyBracketKeyword_3());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1496:1: (otherlv_4= 'guard' ( (lv_guard_5_0= ruleOr ) ) otherlv_6= ';' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==34) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1496:3: otherlv_4= 'guard' ( (lv_guard_5_0= ruleOr ) ) otherlv_6= ';'
                    {
                    otherlv_4=(Token)match(input,34,FOLLOW_34_in_ruleTransition3229); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_4, grammarAccess.getTransitionAccess().getGuardKeyword_4_0());
                          
                    }
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1500:1: ( (lv_guard_5_0= ruleOr ) )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1501:1: (lv_guard_5_0= ruleOr )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1501:1: (lv_guard_5_0= ruleOr )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1502:3: lv_guard_5_0= ruleOr
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTransitionAccess().getGuardOrParserRuleCall_4_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_ruleOr_in_ruleTransition3250);
                    lv_guard_5_0=ruleOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTransitionRule());
                      	        }
                             		set(
                             			current, 
                             			"guard",
                              		lv_guard_5_0, 
                              		"Or");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }

                    otherlv_6=(Token)match(input,14,FOLLOW_14_in_ruleTransition3262); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_6, grammarAccess.getTransitionAccess().getSemicolonKeyword_4_2());
                          
                    }

                    }
                    break;

            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1522:3: ( (lv_sync_7_0= ruleSync ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==35) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1523:1: (lv_sync_7_0= ruleSync )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1523:1: (lv_sync_7_0= ruleSync )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1524:3: lv_sync_7_0= ruleSync
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTransitionAccess().getSyncSyncParserRuleCall_5_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_ruleSync_in_ruleTransition3285);
                    lv_sync_7_0=ruleSync();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTransitionRule());
                      	        }
                             		set(
                             			current, 
                             			"sync",
                              		lv_sync_7_0, 
                              		"Sync");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1540:3: ( (lv_assign_8_0= ruleAssignments ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==38) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1541:1: (lv_assign_8_0= ruleAssignments )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1541:1: (lv_assign_8_0= ruleAssignments )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1542:3: lv_assign_8_0= ruleAssignments
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getTransitionAccess().getAssignAssignmentsParserRuleCall_6_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_ruleAssignments_in_ruleTransition3307);
                    lv_assign_8_0=ruleAssignments();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getTransitionRule());
                      	        }
                             		set(
                             			current, 
                             			"assign",
                              		lv_assign_8_0, 
                              		"Assignments");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }
                    break;

            }

            otherlv_9=(Token)match(input,18,FOLLOW_18_in_ruleTransition3320); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_9, grammarAccess.getTransitionAccess().getRightCurlyBracketKeyword_7());
                  
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


    // $ANTLR start "entryRuleSync"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1570:1: entryRuleSync returns [EObject current=null] : iv_ruleSync= ruleSync EOF ;
    public final EObject entryRuleSync() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSync = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1571:2: (iv_ruleSync= ruleSync EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1572:2: iv_ruleSync= ruleSync EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSyncRule()); 
            }
            pushFollow(FOLLOW_ruleSync_in_entryRuleSync3356);
            iv_ruleSync=ruleSync();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSync; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleSync3366); if (state.failed) return current;

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
    // $ANTLR end "entryRuleSync"


    // $ANTLR start "ruleSync"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1579:1: ruleSync returns [EObject current=null] : (this_Send_0= ruleSend | this_Recv_1= ruleRecv ) ;
    public final EObject ruleSync() throws RecognitionException {
        EObject current = null;

        EObject this_Send_0 = null;

        EObject this_Recv_1 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1582:28: ( (this_Send_0= ruleSend | this_Recv_1= ruleRecv ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1583:1: (this_Send_0= ruleSend | this_Recv_1= ruleRecv )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1583:1: (this_Send_0= ruleSend | this_Recv_1= ruleRecv )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==35) ) {
                int LA27_1 = input.LA(2);

                if ( (LA27_1==RULE_ID) ) {
                    int LA27_2 = input.LA(3);

                    if ( (LA27_2==36) ) {
                        alt27=1;
                    }
                    else if ( (LA27_2==37) ) {
                        alt27=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 2, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1584:5: this_Send_0= ruleSend
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getSyncAccess().getSendParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_ruleSend_in_ruleSync3413);
                    this_Send_0=ruleSend();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Send_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1594:5: this_Recv_1= ruleRecv
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getSyncAccess().getRecvParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleRecv_in_ruleSync3440);
                    this_Recv_1=ruleRecv();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Recv_1; 
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
    // $ANTLR end "ruleSync"


    // $ANTLR start "entryRuleSend"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1610:1: entryRuleSend returns [EObject current=null] : iv_ruleSend= ruleSend EOF ;
    public final EObject entryRuleSend() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSend = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1611:2: (iv_ruleSend= ruleSend EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1612:2: iv_ruleSend= ruleSend EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSendRule()); 
            }
            pushFollow(FOLLOW_ruleSend_in_entryRuleSend3475);
            iv_ruleSend=ruleSend();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSend; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleSend3485); if (state.failed) return current;

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
    // $ANTLR end "entryRuleSend"


    // $ANTLR start "ruleSend"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1619:1: ruleSend returns [EObject current=null] : (otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '!' otherlv_3= ';' ) ;
    public final EObject ruleSend() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1622:28: ( (otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '!' otherlv_3= ';' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1623:1: (otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '!' otherlv_3= ';' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1623:1: (otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '!' otherlv_3= ';' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1623:3: otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '!' otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,35,FOLLOW_35_in_ruleSend3522); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getSendAccess().getSyncKeyword_0());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1627:1: ( (otherlv_1= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1628:1: (otherlv_1= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1628:1: (otherlv_1= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1629:3: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getSendRule());
              	        }
                      
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleSend3542); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_1, grammarAccess.getSendAccess().getChannelChanIdCrossReference_1_0()); 
              	
            }

            }


            }

            otherlv_2=(Token)match(input,36,FOLLOW_36_in_ruleSend3554); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getSendAccess().getExclamationMarkKeyword_2());
                  
            }
            otherlv_3=(Token)match(input,14,FOLLOW_14_in_ruleSend3566); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getSendAccess().getSemicolonKeyword_3());
                  
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
    // $ANTLR end "ruleSend"


    // $ANTLR start "entryRuleRecv"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1656:1: entryRuleRecv returns [EObject current=null] : iv_ruleRecv= ruleRecv EOF ;
    public final EObject entryRuleRecv() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRecv = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1657:2: (iv_ruleRecv= ruleRecv EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1658:2: iv_ruleRecv= ruleRecv EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRecvRule()); 
            }
            pushFollow(FOLLOW_ruleRecv_in_entryRuleRecv3602);
            iv_ruleRecv=ruleRecv();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleRecv; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleRecv3612); if (state.failed) return current;

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
    // $ANTLR end "entryRuleRecv"


    // $ANTLR start "ruleRecv"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1665:1: ruleRecv returns [EObject current=null] : (otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '?' otherlv_3= ';' ) ;
    public final EObject ruleRecv() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1668:28: ( (otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '?' otherlv_3= ';' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1669:1: (otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '?' otherlv_3= ';' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1669:1: (otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '?' otherlv_3= ';' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1669:3: otherlv_0= 'sync' ( (otherlv_1= RULE_ID ) ) otherlv_2= '?' otherlv_3= ';'
            {
            otherlv_0=(Token)match(input,35,FOLLOW_35_in_ruleRecv3649); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getRecvAccess().getSyncKeyword_0());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1673:1: ( (otherlv_1= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1674:1: (otherlv_1= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1674:1: (otherlv_1= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1675:3: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getRecvRule());
              	        }
                      
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRecv3669); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_1, grammarAccess.getRecvAccess().getChannelChanIdCrossReference_1_0()); 
              	
            }

            }


            }

            otherlv_2=(Token)match(input,37,FOLLOW_37_in_ruleRecv3681); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getRecvAccess().getQuestionMarkKeyword_2());
                  
            }
            otherlv_3=(Token)match(input,14,FOLLOW_14_in_ruleRecv3693); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_3, grammarAccess.getRecvAccess().getSemicolonKeyword_3());
                  
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
    // $ANTLR end "ruleRecv"


    // $ANTLR start "entryRuleAssignments"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1702:1: entryRuleAssignments returns [EObject current=null] : iv_ruleAssignments= ruleAssignments EOF ;
    public final EObject entryRuleAssignments() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssignments = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1703:2: (iv_ruleAssignments= ruleAssignments EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1704:2: iv_ruleAssignments= ruleAssignments EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAssignmentsRule()); 
            }
            pushFollow(FOLLOW_ruleAssignments_in_entryRuleAssignments3729);
            iv_ruleAssignments=ruleAssignments();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAssignments; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssignments3739); if (state.failed) return current;

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
    // $ANTLR end "entryRuleAssignments"


    // $ANTLR start "ruleAssignments"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1711:1: ruleAssignments returns [EObject current=null] : (otherlv_0= 'assign' ( (lv_assigns_1_0= ruleAssign ) ) (otherlv_2= ',' ( (lv_assigns_3_0= ruleAssign ) ) )* otherlv_4= ';' ) ;
    public final EObject ruleAssignments() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_assigns_1_0 = null;

        EObject lv_assigns_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1714:28: ( (otherlv_0= 'assign' ( (lv_assigns_1_0= ruleAssign ) ) (otherlv_2= ',' ( (lv_assigns_3_0= ruleAssign ) ) )* otherlv_4= ';' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1715:1: (otherlv_0= 'assign' ( (lv_assigns_1_0= ruleAssign ) ) (otherlv_2= ',' ( (lv_assigns_3_0= ruleAssign ) ) )* otherlv_4= ';' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1715:1: (otherlv_0= 'assign' ( (lv_assigns_1_0= ruleAssign ) ) (otherlv_2= ',' ( (lv_assigns_3_0= ruleAssign ) ) )* otherlv_4= ';' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1715:3: otherlv_0= 'assign' ( (lv_assigns_1_0= ruleAssign ) ) (otherlv_2= ',' ( (lv_assigns_3_0= ruleAssign ) ) )* otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,38,FOLLOW_38_in_ruleAssignments3776); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getAssignmentsAccess().getAssignKeyword_0());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1719:1: ( (lv_assigns_1_0= ruleAssign ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1720:1: (lv_assigns_1_0= ruleAssign )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1720:1: (lv_assigns_1_0= ruleAssign )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1721:3: lv_assigns_1_0= ruleAssign
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getAssignmentsAccess().getAssignsAssignParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleAssign_in_ruleAssignments3797);
            lv_assigns_1_0=ruleAssign();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getAssignmentsRule());
              	        }
                     		add(
                     			current, 
                     			"assigns",
                      		lv_assigns_1_0, 
                      		"Assign");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1737:2: (otherlv_2= ',' ( (lv_assigns_3_0= ruleAssign ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==12) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1737:4: otherlv_2= ',' ( (lv_assigns_3_0= ruleAssign ) )
            	    {
            	    otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleAssignments3810); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_2, grammarAccess.getAssignmentsAccess().getCommaKeyword_2_0());
            	          
            	    }
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1741:1: ( (lv_assigns_3_0= ruleAssign ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1742:1: (lv_assigns_3_0= ruleAssign )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1742:1: (lv_assigns_3_0= ruleAssign )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1743:3: lv_assigns_3_0= ruleAssign
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getAssignmentsAccess().getAssignsAssignParserRuleCall_2_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleAssign_in_ruleAssignments3831);
            	    lv_assigns_3_0=ruleAssign();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getAssignmentsRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"assigns",
            	              		lv_assigns_3_0, 
            	              		"Assign");
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

            otherlv_4=(Token)match(input,14,FOLLOW_14_in_ruleAssignments3845); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getAssignmentsAccess().getSemicolonKeyword_3());
                  
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
    // $ANTLR end "ruleAssignments"


    // $ANTLR start "entryRuleAssign"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1771:1: entryRuleAssign returns [EObject current=null] : iv_ruleAssign= ruleAssign EOF ;
    public final EObject entryRuleAssign() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssign = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1772:2: (iv_ruleAssign= ruleAssign EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1773:2: iv_ruleAssign= ruleAssign EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAssignRule()); 
            }
            pushFollow(FOLLOW_ruleAssign_in_entryRuleAssign3881);
            iv_ruleAssign=ruleAssign();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAssign; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleAssign3891); if (state.failed) return current;

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
    // $ANTLR end "entryRuleAssign"


    // $ANTLR start "ruleAssign"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1780:1: ruleAssign returns [EObject current=null] : ( ( (lv_lhs_0_0= ruleVarAccess ) ) ruleASSIGNMENT ( (lv_rhs_2_0= ruleBitOr ) ) ) ;
    public final EObject ruleAssign() throws RecognitionException {
        EObject current = null;

        EObject lv_lhs_0_0 = null;

        EObject lv_rhs_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1783:28: ( ( ( (lv_lhs_0_0= ruleVarAccess ) ) ruleASSIGNMENT ( (lv_rhs_2_0= ruleBitOr ) ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1784:1: ( ( (lv_lhs_0_0= ruleVarAccess ) ) ruleASSIGNMENT ( (lv_rhs_2_0= ruleBitOr ) ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1784:1: ( ( (lv_lhs_0_0= ruleVarAccess ) ) ruleASSIGNMENT ( (lv_rhs_2_0= ruleBitOr ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1784:2: ( (lv_lhs_0_0= ruleVarAccess ) ) ruleASSIGNMENT ( (lv_rhs_2_0= ruleBitOr ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1784:2: ( (lv_lhs_0_0= ruleVarAccess ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1785:1: (lv_lhs_0_0= ruleVarAccess )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1785:1: (lv_lhs_0_0= ruleVarAccess )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1786:3: lv_lhs_0_0= ruleVarAccess
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getAssignAccess().getLhsVarAccessParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleVarAccess_in_ruleAssign3937);
            lv_lhs_0_0=ruleVarAccess();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getAssignRule());
              	        }
                     		set(
                     			current, 
                     			"lhs",
                      		lv_lhs_0_0, 
                      		"VarAccess");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getAssignAccess().getASSIGNMENTParserRuleCall_1()); 
                  
            }
            pushFollow(FOLLOW_ruleASSIGNMENT_in_ruleAssign3953);
            ruleASSIGNMENT();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1810:1: ( (lv_rhs_2_0= ruleBitOr ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1811:1: (lv_rhs_2_0= ruleBitOr )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1811:1: (lv_rhs_2_0= ruleBitOr )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1812:3: lv_rhs_2_0= ruleBitOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getAssignAccess().getRhsBitOrParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBitOr_in_ruleAssign3973);
            lv_rhs_2_0=ruleBitOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getAssignRule());
              	        }
                     		set(
                     			current, 
                     			"rhs",
                      		lv_rhs_2_0, 
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
    // $ANTLR end "ruleAssign"


    // $ANTLR start "entryRuleVariableDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1836:1: entryRuleVariableDecl returns [EObject current=null] : iv_ruleVariableDecl= ruleVariableDecl EOF ;
    public final EObject entryRuleVariableDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableDecl = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1837:2: (iv_ruleVariableDecl= ruleVariableDecl EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1838:2: iv_ruleVariableDecl= ruleVariableDecl EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVariableDeclRule()); 
            }
            pushFollow(FOLLOW_ruleVariableDecl_in_entryRuleVariableDecl4009);
            iv_ruleVariableDecl=ruleVariableDecl();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVariableDecl; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleVariableDecl4019); if (state.failed) return current;

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
    // $ANTLR end "entryRuleVariableDecl"


    // $ANTLR start "ruleVariableDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1845:1: ruleVariableDecl returns [EObject current=null] : ( ( (lv_type_0_0= ruleBasicType ) ) ( (lv_declid_1_0= ruleDeclId ) ) (otherlv_2= ',' ( (lv_declid_3_0= ruleDeclId ) ) )* otherlv_4= ';' ) ;
    public final EObject ruleVariableDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_type_0_0 = null;

        EObject lv_declid_1_0 = null;

        EObject lv_declid_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1848:28: ( ( ( (lv_type_0_0= ruleBasicType ) ) ( (lv_declid_1_0= ruleDeclId ) ) (otherlv_2= ',' ( (lv_declid_3_0= ruleDeclId ) ) )* otherlv_4= ';' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1849:1: ( ( (lv_type_0_0= ruleBasicType ) ) ( (lv_declid_1_0= ruleDeclId ) ) (otherlv_2= ',' ( (lv_declid_3_0= ruleDeclId ) ) )* otherlv_4= ';' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1849:1: ( ( (lv_type_0_0= ruleBasicType ) ) ( (lv_declid_1_0= ruleDeclId ) ) (otherlv_2= ',' ( (lv_declid_3_0= ruleDeclId ) ) )* otherlv_4= ';' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1849:2: ( (lv_type_0_0= ruleBasicType ) ) ( (lv_declid_1_0= ruleDeclId ) ) (otherlv_2= ',' ( (lv_declid_3_0= ruleDeclId ) ) )* otherlv_4= ';'
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1849:2: ( (lv_type_0_0= ruleBasicType ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1850:1: (lv_type_0_0= ruleBasicType )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1850:1: (lv_type_0_0= ruleBasicType )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1851:3: lv_type_0_0= ruleBasicType
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getVariableDeclAccess().getTypeBasicTypeParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBasicType_in_ruleVariableDecl4065);
            lv_type_0_0=ruleBasicType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getVariableDeclRule());
              	        }
                     		set(
                     			current, 
                     			"type",
                      		lv_type_0_0, 
                      		"BasicType");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1867:2: ( (lv_declid_1_0= ruleDeclId ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1868:1: (lv_declid_1_0= ruleDeclId )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1868:1: (lv_declid_1_0= ruleDeclId )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1869:3: lv_declid_1_0= ruleDeclId
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getVariableDeclAccess().getDeclidDeclIdParserRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleDeclId_in_ruleVariableDecl4086);
            lv_declid_1_0=ruleDeclId();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getVariableDeclRule());
              	        }
                     		add(
                     			current, 
                     			"declid",
                      		lv_declid_1_0, 
                      		"DeclId");
              	        afterParserOrEnumRuleCall();
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1885:2: (otherlv_2= ',' ( (lv_declid_3_0= ruleDeclId ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==12) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1885:4: otherlv_2= ',' ( (lv_declid_3_0= ruleDeclId ) )
            	    {
            	    otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleVariableDecl4099); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_2, grammarAccess.getVariableDeclAccess().getCommaKeyword_2_0());
            	          
            	    }
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1889:1: ( (lv_declid_3_0= ruleDeclId ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1890:1: (lv_declid_3_0= ruleDeclId )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1890:1: (lv_declid_3_0= ruleDeclId )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1891:3: lv_declid_3_0= ruleDeclId
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getVariableDeclAccess().getDeclidDeclIdParserRuleCall_2_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleDeclId_in_ruleVariableDecl4120);
            	    lv_declid_3_0=ruleDeclId();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getVariableDeclRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"declid",
            	              		lv_declid_3_0, 
            	              		"DeclId");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);

            otherlv_4=(Token)match(input,14,FOLLOW_14_in_ruleVariableDecl4134); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_4, grammarAccess.getVariableDeclAccess().getSemicolonKeyword_3());
                  
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
    // $ANTLR end "ruleVariableDecl"


    // $ANTLR start "entryRuleDeclId"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1919:1: entryRuleDeclId returns [EObject current=null] : iv_ruleDeclId= ruleDeclId EOF ;
    public final EObject entryRuleDeclId() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDeclId = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1920:2: (iv_ruleDeclId= ruleDeclId EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1921:2: iv_ruleDeclId= ruleDeclId EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDeclIdRule()); 
            }
            pushFollow(FOLLOW_ruleDeclId_in_entryRuleDeclId4170);
            iv_ruleDeclId=ruleDeclId();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDeclId; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleDeclId4180); if (state.failed) return current;

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
    // $ANTLR end "entryRuleDeclId"


    // $ANTLR start "ruleDeclId"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1928:1: ruleDeclId returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_arrays_1_0= ruleArrayDecl ) )* ( ruleASSIGNMENT ( (lv_init_3_0= ruleInitialiser ) ) )? ) ;
    public final EObject ruleDeclId() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        EObject lv_arrays_1_0 = null;

        EObject lv_init_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1931:28: ( ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_arrays_1_0= ruleArrayDecl ) )* ( ruleASSIGNMENT ( (lv_init_3_0= ruleInitialiser ) ) )? ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1932:1: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_arrays_1_0= ruleArrayDecl ) )* ( ruleASSIGNMENT ( (lv_init_3_0= ruleInitialiser ) ) )? )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1932:1: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_arrays_1_0= ruleArrayDecl ) )* ( ruleASSIGNMENT ( (lv_init_3_0= ruleInitialiser ) ) )? )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1932:2: ( (lv_name_0_0= RULE_ID ) ) ( (lv_arrays_1_0= ruleArrayDecl ) )* ( ruleASSIGNMENT ( (lv_init_3_0= ruleInitialiser ) ) )?
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1932:2: ( (lv_name_0_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1933:1: (lv_name_0_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1933:1: (lv_name_0_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1934:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleDeclId4222); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_name_0_0, grammarAccess.getDeclIdAccess().getNameIDTerminalRuleCall_0_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getDeclIdRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"name",
                      		lv_name_0_0, 
                      		"ID");
              	    
            }

            }


            }

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1950:2: ( (lv_arrays_1_0= ruleArrayDecl ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==26) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1951:1: (lv_arrays_1_0= ruleArrayDecl )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1951:1: (lv_arrays_1_0= ruleArrayDecl )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1952:3: lv_arrays_1_0= ruleArrayDecl
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getDeclIdAccess().getArraysArrayDeclParserRuleCall_1_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleArrayDecl_in_ruleDeclId4248);
            	    lv_arrays_1_0=ruleArrayDecl();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      	        if (current==null) {
            	      	            current = createModelElementForParent(grammarAccess.getDeclIdRule());
            	      	        }
            	             		add(
            	             			current, 
            	             			"arrays",
            	              		lv_arrays_1_0, 
            	              		"ArrayDecl");
            	      	        afterParserOrEnumRuleCall();
            	      	    
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1968:3: ( ruleASSIGNMENT ( (lv_init_3_0= ruleInitialiser ) ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( ((LA31_0>=55 && LA31_0<=56)) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1969:5: ruleASSIGNMENT ( (lv_init_3_0= ruleInitialiser ) )
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getDeclIdAccess().getASSIGNMENTParserRuleCall_2_0()); 
                          
                    }
                    pushFollow(FOLLOW_ruleASSIGNMENT_in_ruleDeclId4266);
                    ruleASSIGNMENT();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              afterParserOrEnumRuleCall();
                          
                    }
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1976:1: ( (lv_init_3_0= ruleInitialiser ) )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1977:1: (lv_init_3_0= ruleInitialiser )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1977:1: (lv_init_3_0= ruleInitialiser )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:1978:3: lv_init_3_0= ruleInitialiser
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getDeclIdAccess().getInitInitialiserParserRuleCall_2_1_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_ruleInitialiser_in_ruleDeclId4286);
                    lv_init_3_0=ruleInitialiser();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getDeclIdRule());
                      	        }
                             		set(
                             			current, 
                             			"init",
                              		lv_init_3_0, 
                              		"Initialiser");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleDeclId"


    // $ANTLR start "entryRuleInitialiser"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2002:1: entryRuleInitialiser returns [EObject current=null] : iv_ruleInitialiser= ruleInitialiser EOF ;
    public final EObject entryRuleInitialiser() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInitialiser = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2003:2: (iv_ruleInitialiser= ruleInitialiser EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2004:2: iv_ruleInitialiser= ruleInitialiser EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getInitialiserRule()); 
            }
            pushFollow(FOLLOW_ruleInitialiser_in_entryRuleInitialiser4324);
            iv_ruleInitialiser=ruleInitialiser();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleInitialiser; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleInitialiser4334); if (state.failed) return current;

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
    // $ANTLR end "entryRuleInitialiser"


    // $ANTLR start "ruleInitialiser"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2011:1: ruleInitialiser returns [EObject current=null] : ( (lv_expr_0_0= ruleBitOr ) ) ;
    public final EObject ruleInitialiser() throws RecognitionException {
        EObject current = null;

        EObject lv_expr_0_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2014:28: ( ( (lv_expr_0_0= ruleBitOr ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2015:1: ( (lv_expr_0_0= ruleBitOr ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2015:1: ( (lv_expr_0_0= ruleBitOr ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2016:1: (lv_expr_0_0= ruleBitOr )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2016:1: (lv_expr_0_0= ruleBitOr )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2017:3: lv_expr_0_0= ruleBitOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getInitialiserAccess().getExprBitOrParserRuleCall_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBitOr_in_ruleInitialiser4379);
            lv_expr_0_0=ruleBitOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElementForParent(grammarAccess.getInitialiserRule());
              	        }
                     		set(
                     			current, 
                     			"expr",
                      		lv_expr_0_0, 
                      		"BitOr");
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
    // $ANTLR end "ruleInitialiser"


    // $ANTLR start "entryRuleArrayDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2041:1: entryRuleArrayDecl returns [EObject current=null] : iv_ruleArrayDecl= ruleArrayDecl EOF ;
    public final EObject entryRuleArrayDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArrayDecl = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2042:2: (iv_ruleArrayDecl= ruleArrayDecl EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2043:2: iv_ruleArrayDecl= ruleArrayDecl EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrayDeclRule()); 
            }
            pushFollow(FOLLOW_ruleArrayDecl_in_entryRuleArrayDecl4414);
            iv_ruleArrayDecl=ruleArrayDecl();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArrayDecl; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleArrayDecl4424); if (state.failed) return current;

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
    // $ANTLR end "entryRuleArrayDecl"


    // $ANTLR start "ruleArrayDecl"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2050:1: ruleArrayDecl returns [EObject current=null] : (otherlv_0= '[' ( (lv_size_1_0= RULE_INT ) ) otherlv_2= ']' ) ;
    public final EObject ruleArrayDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_size_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2053:28: ( (otherlv_0= '[' ( (lv_size_1_0= RULE_INT ) ) otherlv_2= ']' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2054:1: (otherlv_0= '[' ( (lv_size_1_0= RULE_INT ) ) otherlv_2= ']' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2054:1: (otherlv_0= '[' ( (lv_size_1_0= RULE_INT ) ) otherlv_2= ']' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2054:3: otherlv_0= '[' ( (lv_size_1_0= RULE_INT ) ) otherlv_2= ']'
            {
            otherlv_0=(Token)match(input,26,FOLLOW_26_in_ruleArrayDecl4461); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_0, grammarAccess.getArrayDeclAccess().getLeftSquareBracketKeyword_0());
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2058:1: ( (lv_size_1_0= RULE_INT ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2059:1: (lv_size_1_0= RULE_INT )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2059:1: (lv_size_1_0= RULE_INT )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2060:3: lv_size_1_0= RULE_INT
            {
            lv_size_1_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleArrayDecl4478); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(lv_size_1_0, grammarAccess.getArrayDeclAccess().getSizeINTTerminalRuleCall_1_0()); 
              		
            }
            if ( state.backtracking==0 ) {

              	        if (current==null) {
              	            current = createModelElement(grammarAccess.getArrayDeclRule());
              	        }
                     		setWithLastConsumed(
                     			current, 
                     			"size",
                      		lv_size_1_0, 
                      		"INT");
              	    
            }

            }


            }

            otherlv_2=(Token)match(input,27,FOLLOW_27_in_ruleArrayDecl4495); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_2, grammarAccess.getArrayDeclAccess().getRightSquareBracketKeyword_2());
                  
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
    // $ANTLR end "ruleArrayDecl"


    // $ANTLR start "entryRuleBitOr"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2088:1: entryRuleBitOr returns [EObject current=null] : iv_ruleBitOr= ruleBitOr EOF ;
    public final EObject entryRuleBitOr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitOr = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2089:2: (iv_ruleBitOr= ruleBitOr EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2090:2: iv_ruleBitOr= ruleBitOr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitOrRule()); 
            }
            pushFollow(FOLLOW_ruleBitOr_in_entryRuleBitOr4531);
            iv_ruleBitOr=ruleBitOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitOr; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitOr4541); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2097:1: ruleBitOr returns [EObject current=null] : (this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )* ) ;
    public final EObject ruleBitOr() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_BitXor_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2100:28: ( (this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )* ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2101:1: (this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )* )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2101:1: (this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )* )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2102:5: this_BitXor_0= ruleBitXor ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getBitOrAccess().getBitXorParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleBitXor_in_ruleBitOr4588);
            this_BitXor_0=ruleBitXor();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_BitXor_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2110:1: ( () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==39) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2110:2: () ( (lv_op_2_0= '|' ) ) ( (lv_right_3_0= ruleBitXor ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2110:2: ()
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2111:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getBitOrAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2116:2: ( (lv_op_2_0= '|' ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2117:1: (lv_op_2_0= '|' )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2117:1: (lv_op_2_0= '|' )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2118:3: lv_op_2_0= '|'
            	    {
            	    lv_op_2_0=(Token)match(input,39,FOLLOW_39_in_ruleBitOr4615); if (state.failed) return current;
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

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2131:2: ( (lv_right_3_0= ruleBitXor ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2132:1: (lv_right_3_0= ruleBitXor )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2132:1: (lv_right_3_0= ruleBitXor )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2133:3: lv_right_3_0= ruleBitXor
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getBitOrAccess().getRightBitXorParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleBitXor_in_ruleBitOr4649);
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
    // $ANTLR end "ruleBitOr"


    // $ANTLR start "entryRuleBitXor"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2157:1: entryRuleBitXor returns [EObject current=null] : iv_ruleBitXor= ruleBitXor EOF ;
    public final EObject entryRuleBitXor() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitXor = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2158:2: (iv_ruleBitXor= ruleBitXor EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2159:2: iv_ruleBitXor= ruleBitXor EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitXorRule()); 
            }
            pushFollow(FOLLOW_ruleBitXor_in_entryRuleBitXor4687);
            iv_ruleBitXor=ruleBitXor();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitXor; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitXor4697); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2166:1: ruleBitXor returns [EObject current=null] : (this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )* ) ;
    public final EObject ruleBitXor() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_BitAnd_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2169:28: ( (this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )* ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2170:1: (this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )* )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2170:1: (this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )* )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2171:5: this_BitAnd_0= ruleBitAnd ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getBitXorAccess().getBitAndParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleBitAnd_in_ruleBitXor4744);
            this_BitAnd_0=ruleBitAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_BitAnd_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2179:1: ( () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==40) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2179:2: () ( (lv_op_2_0= '^' ) ) ( (lv_right_3_0= ruleBitAnd ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2179:2: ()
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2180:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getBitXorAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2185:2: ( (lv_op_2_0= '^' ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2186:1: (lv_op_2_0= '^' )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2186:1: (lv_op_2_0= '^' )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2187:3: lv_op_2_0= '^'
            	    {
            	    lv_op_2_0=(Token)match(input,40,FOLLOW_40_in_ruleBitXor4771); if (state.failed) return current;
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

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2200:2: ( (lv_right_3_0= ruleBitAnd ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2201:1: (lv_right_3_0= ruleBitAnd )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2201:1: (lv_right_3_0= ruleBitAnd )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2202:3: lv_right_3_0= ruleBitAnd
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getBitXorAccess().getRightBitAndParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleBitAnd_in_ruleBitXor4805);
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
            	    break loop33;
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2226:1: entryRuleBitAnd returns [EObject current=null] : iv_ruleBitAnd= ruleBitAnd EOF ;
    public final EObject entryRuleBitAnd() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitAnd = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2227:2: (iv_ruleBitAnd= ruleBitAnd EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2228:2: iv_ruleBitAnd= ruleBitAnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitAndRule()); 
            }
            pushFollow(FOLLOW_ruleBitAnd_in_entryRuleBitAnd4843);
            iv_ruleBitAnd=ruleBitAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitAnd; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitAnd4853); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2235:1: ruleBitAnd returns [EObject current=null] : (this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )* ) ;
    public final EObject ruleBitAnd() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_BitShift_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2238:28: ( (this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )* ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2239:1: (this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )* )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2239:1: (this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )* )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2240:5: this_BitShift_0= ruleBitShift ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getBitAndAccess().getBitShiftParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleBitShift_in_ruleBitAnd4900);
            this_BitShift_0=ruleBitShift();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_BitShift_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2248:1: ( () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==41) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2248:2: () ( (lv_op_2_0= '&' ) ) ( (lv_right_3_0= ruleBitShift ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2248:2: ()
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2249:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getBitAndAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2254:2: ( (lv_op_2_0= '&' ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2255:1: (lv_op_2_0= '&' )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2255:1: (lv_op_2_0= '&' )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2256:3: lv_op_2_0= '&'
            	    {
            	    lv_op_2_0=(Token)match(input,41,FOLLOW_41_in_ruleBitAnd4927); if (state.failed) return current;
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

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2269:2: ( (lv_right_3_0= ruleBitShift ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2270:1: (lv_right_3_0= ruleBitShift )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2270:1: (lv_right_3_0= ruleBitShift )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2271:3: lv_right_3_0= ruleBitShift
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getBitAndAccess().getRightBitShiftParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleBitShift_in_ruleBitAnd4961);
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
            	    break loop34;
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2295:1: entryRuleBitShift returns [EObject current=null] : iv_ruleBitShift= ruleBitShift EOF ;
    public final EObject entryRuleBitShift() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitShift = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2296:2: (iv_ruleBitShift= ruleBitShift EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2297:2: iv_ruleBitShift= ruleBitShift EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitShiftRule()); 
            }
            pushFollow(FOLLOW_ruleBitShift_in_entryRuleBitShift4999);
            iv_ruleBitShift=ruleBitShift();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitShift; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitShift5009); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2304:1: ruleBitShift returns [EObject current=null] : (this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )* ) ;
    public final EObject ruleBitShift() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_Addition_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2307:28: ( (this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )* ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2308:1: (this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )* )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2308:1: (this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )* )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2309:5: this_Addition_0= ruleAddition ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getBitShiftAccess().getAdditionParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleAddition_in_ruleBitShift5056);
            this_Addition_0=ruleAddition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Addition_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2317:1: ( () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) ) )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( ((LA36_0>=42 && LA36_0<=43)) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2317:2: () ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) ) ( (lv_right_3_0= ruleAddition ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2317:2: ()
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2318:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getBitShiftAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2323:2: ( ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2324:1: ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2324:1: ( (lv_op_2_1= '<<' | lv_op_2_2= '>>' ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2325:1: (lv_op_2_1= '<<' | lv_op_2_2= '>>' )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2325:1: (lv_op_2_1= '<<' | lv_op_2_2= '>>' )
            	    int alt35=2;
            	    int LA35_0 = input.LA(1);

            	    if ( (LA35_0==42) ) {
            	        alt35=1;
            	    }
            	    else if ( (LA35_0==43) ) {
            	        alt35=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 35, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt35) {
            	        case 1 :
            	            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2326:3: lv_op_2_1= '<<'
            	            {
            	            lv_op_2_1=(Token)match(input,42,FOLLOW_42_in_ruleBitShift5085); if (state.failed) return current;
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
            	            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2338:8: lv_op_2_2= '>>'
            	            {
            	            lv_op_2_2=(Token)match(input,43,FOLLOW_43_in_ruleBitShift5114); if (state.failed) return current;
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

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2353:2: ( (lv_right_3_0= ruleAddition ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2354:1: (lv_right_3_0= ruleAddition )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2354:1: (lv_right_3_0= ruleAddition )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2355:3: lv_right_3_0= ruleAddition
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getBitShiftAccess().getRightAdditionParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleAddition_in_ruleBitShift5151);
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
            	    break loop36;
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2379:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2380:2: (iv_ruleAddition= ruleAddition EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2381:2: iv_ruleAddition= ruleAddition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAdditionRule()); 
            }
            pushFollow(FOLLOW_ruleAddition_in_entryRuleAddition5189);
            iv_ruleAddition=ruleAddition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAddition; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleAddition5199); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2388:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_Multiplication_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2391:28: ( (this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2392:1: (this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2392:1: (this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2393:5: this_Multiplication_0= ruleMultiplication ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition5246);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Multiplication_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2401:1: ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( ((LA38_0>=44 && LA38_0<=45)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2401:2: () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ( (lv_right_3_0= ruleMultiplication ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2401:2: ()
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2402:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getAdditionAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2407:2: ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2408:1: ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2408:1: ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2409:1: (lv_op_2_1= '+' | lv_op_2_2= '-' )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2409:1: (lv_op_2_1= '+' | lv_op_2_2= '-' )
            	    int alt37=2;
            	    int LA37_0 = input.LA(1);

            	    if ( (LA37_0==44) ) {
            	        alt37=1;
            	    }
            	    else if ( (LA37_0==45) ) {
            	        alt37=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 37, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt37) {
            	        case 1 :
            	            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2410:3: lv_op_2_1= '+'
            	            {
            	            lv_op_2_1=(Token)match(input,44,FOLLOW_44_in_ruleAddition5275); if (state.failed) return current;
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
            	            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2422:8: lv_op_2_2= '-'
            	            {
            	            lv_op_2_2=(Token)match(input,45,FOLLOW_45_in_ruleAddition5304); if (state.failed) return current;
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

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2437:2: ( (lv_right_3_0= ruleMultiplication ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2438:1: (lv_right_3_0= ruleMultiplication )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2438:1: (lv_right_3_0= ruleMultiplication )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2439:3: lv_right_3_0= ruleMultiplication
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleMultiplication_in_ruleAddition5341);
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
            	    break loop38;
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2463:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2464:2: (iv_ruleMultiplication= ruleMultiplication EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2465:2: iv_ruleMultiplication= ruleMultiplication EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMultiplicationRule()); 
            }
            pushFollow(FOLLOW_ruleMultiplication_in_entryRuleMultiplication5379);
            iv_ruleMultiplication=ruleMultiplication();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMultiplication; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleMultiplication5389); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2472:1: ruleMultiplication returns [EObject current=null] : (this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        Token lv_op_2_3=null;
        EObject this_BitComplement_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2475:28: ( (this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )* ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2476:1: (this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )* )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2476:1: (this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )* )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2477:5: this_BitComplement_0= ruleBitComplement ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getMultiplicationAccess().getBitComplementParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleBitComplement_in_ruleMultiplication5436);
            this_BitComplement_0=ruleBitComplement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_BitComplement_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2485:1: ( () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( ((LA40_0>=46 && LA40_0<=48)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2485:2: () ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) ) ( (lv_right_3_0= ruleBitComplement ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2485:2: ()
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2486:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getMultiplicationAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2491:2: ( ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2492:1: ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2492:1: ( (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2493:1: (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2493:1: (lv_op_2_1= '/' | lv_op_2_2= '*' | lv_op_2_3= '%' )
            	    int alt39=3;
            	    switch ( input.LA(1) ) {
            	    case 46:
            	        {
            	        alt39=1;
            	        }
            	        break;
            	    case 47:
            	        {
            	        alt39=2;
            	        }
            	        break;
            	    case 48:
            	        {
            	        alt39=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 39, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt39) {
            	        case 1 :
            	            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2494:3: lv_op_2_1= '/'
            	            {
            	            lv_op_2_1=(Token)match(input,46,FOLLOW_46_in_ruleMultiplication5465); if (state.failed) return current;
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
            	            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2506:8: lv_op_2_2= '*'
            	            {
            	            lv_op_2_2=(Token)match(input,47,FOLLOW_47_in_ruleMultiplication5494); if (state.failed) return current;
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
            	            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2518:8: lv_op_2_3= '%'
            	            {
            	            lv_op_2_3=(Token)match(input,48,FOLLOW_48_in_ruleMultiplication5523); if (state.failed) return current;
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

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2533:2: ( (lv_right_3_0= ruleBitComplement ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2534:1: (lv_right_3_0= ruleBitComplement )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2534:1: (lv_right_3_0= ruleBitComplement )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2535:3: lv_right_3_0= ruleBitComplement
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getMultiplicationAccess().getRightBitComplementParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleBitComplement_in_ruleMultiplication5560);
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
            	    break loop40;
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2559:1: entryRuleBitComplement returns [EObject current=null] : iv_ruleBitComplement= ruleBitComplement EOF ;
    public final EObject entryRuleBitComplement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBitComplement = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2560:2: (iv_ruleBitComplement= ruleBitComplement EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2561:2: iv_ruleBitComplement= ruleBitComplement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBitComplementRule()); 
            }
            pushFollow(FOLLOW_ruleBitComplement_in_entryRuleBitComplement5598);
            iv_ruleBitComplement=ruleBitComplement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBitComplement; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleBitComplement5608); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2568:1: ruleBitComplement returns [EObject current=null] : ( (otherlv_0= '~' this_Power_1= rulePower () ) | (otherlv_3= '-' this_Power_4= rulePower () ) | this_Power_6= rulePower ) ;
    public final EObject ruleBitComplement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        EObject this_Power_1 = null;

        EObject this_Power_4 = null;

        EObject this_Power_6 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2571:28: ( ( (otherlv_0= '~' this_Power_1= rulePower () ) | (otherlv_3= '-' this_Power_4= rulePower () ) | this_Power_6= rulePower ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2572:1: ( (otherlv_0= '~' this_Power_1= rulePower () ) | (otherlv_3= '-' this_Power_4= rulePower () ) | this_Power_6= rulePower )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2572:1: ( (otherlv_0= '~' this_Power_1= rulePower () ) | (otherlv_3= '-' this_Power_4= rulePower () ) | this_Power_6= rulePower )
            int alt41=3;
            switch ( input.LA(1) ) {
            case 49:
                {
                alt41=1;
                }
                break;
            case 45:
                {
                alt41=2;
                }
                break;
            case RULE_ID:
            case RULE_INT:
            case 11:
                {
                alt41=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }

            switch (alt41) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2572:2: (otherlv_0= '~' this_Power_1= rulePower () )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2572:2: (otherlv_0= '~' this_Power_1= rulePower () )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2572:4: otherlv_0= '~' this_Power_1= rulePower ()
                    {
                    otherlv_0=(Token)match(input,49,FOLLOW_49_in_ruleBitComplement5646); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_0, grammarAccess.getBitComplementAccess().getTildeKeyword_0_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBitComplementAccess().getPowerParserRuleCall_0_1()); 
                          
                    }
                    pushFollow(FOLLOW_rulePower_in_ruleBitComplement5668);
                    this_Power_1=rulePower();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Power_1; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2585:1: ()
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2586:5: 
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
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2592:6: (otherlv_3= '-' this_Power_4= rulePower () )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2592:6: (otherlv_3= '-' this_Power_4= rulePower () )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2592:8: otherlv_3= '-' this_Power_4= rulePower ()
                    {
                    otherlv_3=(Token)match(input,45,FOLLOW_45_in_ruleBitComplement5696); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getBitComplementAccess().getHyphenMinusKeyword_1_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBitComplementAccess().getPowerParserRuleCall_1_1()); 
                          
                    }
                    pushFollow(FOLLOW_rulePower_in_ruleBitComplement5718);
                    this_Power_4=rulePower();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Power_4; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2605:1: ()
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2606:5: 
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
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2613:5: this_Power_6= rulePower
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getBitComplementAccess().getPowerParserRuleCall_2()); 
                          
                    }
                    pushFollow(FOLLOW_rulePower_in_ruleBitComplement5755);
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2629:1: entryRulePower returns [EObject current=null] : iv_rulePower= rulePower EOF ;
    public final EObject entryRulePower() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePower = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2630:2: (iv_rulePower= rulePower EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2631:2: iv_rulePower= rulePower EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPowerRule()); 
            }
            pushFollow(FOLLOW_rulePower_in_entryRulePower5790);
            iv_rulePower=rulePower();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePower; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePower5800); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2638:1: rulePower returns [EObject current=null] : (this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )* ) ;
    public final EObject rulePower() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_Primary_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2641:28: ( (this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )* ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2642:1: (this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )* )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2642:1: (this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )* )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2643:5: this_Primary_0= rulePrimary ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getPowerAccess().getPrimaryParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_rulePrimary_in_rulePower5847);
            this_Primary_0=rulePrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Primary_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2651:1: ( () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==50) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2651:2: () ( (lv_op_2_0= '**' ) ) ( (lv_right_3_0= rulePrimary ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2651:2: ()
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2652:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getPowerAccess().getBinaryIntExpressionLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2657:2: ( (lv_op_2_0= '**' ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2658:1: (lv_op_2_0= '**' )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2658:1: (lv_op_2_0= '**' )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2659:3: lv_op_2_0= '**'
            	    {
            	    lv_op_2_0=(Token)match(input,50,FOLLOW_50_in_rulePower5874); if (state.failed) return current;
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

            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2672:2: ( (lv_right_3_0= rulePrimary ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2673:1: (lv_right_3_0= rulePrimary )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2673:1: (lv_right_3_0= rulePrimary )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2674:3: lv_right_3_0= rulePrimary
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getPowerAccess().getRightPrimaryParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_rulePrimary_in_rulePower5908);
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
            	    break loop42;
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2698:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2699:2: (iv_rulePrimary= rulePrimary EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2700:2: iv_rulePrimary= rulePrimary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPrimaryRule()); 
            }
            pushFollow(FOLLOW_rulePrimary_in_entryRulePrimary5946);
            iv_rulePrimary=rulePrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePrimary; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimary5956); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2707:1: rulePrimary returns [EObject current=null] : (this_VarAccess_0= ruleVarAccess | this_ConstRef_1= ruleConstRef | ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' ) ) | (otherlv_5= '(' this_WrapBoolExpr_6= ruleWrapBoolExpr otherlv_7= ')' ) ) ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject this_VarAccess_0 = null;

        EObject this_ConstRef_1 = null;

        EObject this_BitOr_3 = null;

        EObject this_WrapBoolExpr_6 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2710:28: ( (this_VarAccess_0= ruleVarAccess | this_ConstRef_1= ruleConstRef | ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' ) ) | (otherlv_5= '(' this_WrapBoolExpr_6= ruleWrapBoolExpr otherlv_7= ')' ) ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2711:1: (this_VarAccess_0= ruleVarAccess | this_ConstRef_1= ruleConstRef | ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' ) ) | (otherlv_5= '(' this_WrapBoolExpr_6= ruleWrapBoolExpr otherlv_7= ')' ) ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2711:1: (this_VarAccess_0= ruleVarAccess | this_ConstRef_1= ruleConstRef | ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' ) ) | (otherlv_5= '(' this_WrapBoolExpr_6= ruleWrapBoolExpr otherlv_7= ')' ) ) )
            int alt44=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt44=1;
                }
                break;
            case RULE_INT:
                {
                alt44=2;
                }
                break;
            case 11:
                {
                alt44=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }

            switch (alt44) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2712:5: this_VarAccess_0= ruleVarAccess
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryAccess().getVarAccessParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_ruleVarAccess_in_rulePrimary6003);
                    this_VarAccess_0=ruleVarAccess();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_VarAccess_0; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2722:5: this_ConstRef_1= ruleConstRef
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryAccess().getConstRefParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleConstRef_in_rulePrimary6030);
                    this_ConstRef_1=ruleConstRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_ConstRef_1; 
                              afterParserOrEnumRuleCall();
                          
                    }

                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2731:6: ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' ) ) | (otherlv_5= '(' this_WrapBoolExpr_6= ruleWrapBoolExpr otherlv_7= ')' ) )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2731:6: ( ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' ) ) | (otherlv_5= '(' this_WrapBoolExpr_6= ruleWrapBoolExpr otherlv_7= ')' ) )
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==11) ) {
                        int LA43_1 = input.LA(2);

                        if ( (synpred1_InternalTimedAutomata()) ) {
                            alt43=1;
                        }
                        else if ( (true) ) {
                            alt43=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 43, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 43, 0, input);

                        throw nvae;
                    }
                    switch (alt43) {
                        case 1 :
                            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2731:7: ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' ) )
                            {
                            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2731:7: ( ( ( '(' ruleBitOr ')' ) )=> (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' ) )
                            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2731:8: ( ( '(' ruleBitOr ')' ) )=> (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' )
                            {
                            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2733:5: (otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')' )
                            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2733:7: otherlv_2= '(' this_BitOr_3= ruleBitOr otherlv_4= ')'
                            {
                            otherlv_2=(Token)match(input,11,FOLLOW_11_in_rulePrimary6065); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0_0_0());
                                  
                            }
                            if ( state.backtracking==0 ) {
                               
                                      newCompositeNode(grammarAccess.getPrimaryAccess().getBitOrParserRuleCall_2_0_0_1()); 
                                  
                            }
                            pushFollow(FOLLOW_ruleBitOr_in_rulePrimary6087);
                            this_BitOr_3=ruleBitOr();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {
                               
                                      current = this_BitOr_3; 
                                      afterParserOrEnumRuleCall();
                                  
                            }
                            otherlv_4=(Token)match(input,13,FOLLOW_13_in_rulePrimary6098); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_2_0_0_2());
                                  
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2751:6: (otherlv_5= '(' this_WrapBoolExpr_6= ruleWrapBoolExpr otherlv_7= ')' )
                            {
                            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2751:6: (otherlv_5= '(' this_WrapBoolExpr_6= ruleWrapBoolExpr otherlv_7= ')' )
                            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2751:8: otherlv_5= '(' this_WrapBoolExpr_6= ruleWrapBoolExpr otherlv_7= ')'
                            {
                            otherlv_5=(Token)match(input,11,FOLLOW_11_in_rulePrimary6119); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_5, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_1_0());
                                  
                            }
                            if ( state.backtracking==0 ) {
                               
                                      newCompositeNode(grammarAccess.getPrimaryAccess().getWrapBoolExprParserRuleCall_2_1_1()); 
                                  
                            }
                            pushFollow(FOLLOW_ruleWrapBoolExpr_in_rulePrimary6141);
                            this_WrapBoolExpr_6=ruleWrapBoolExpr();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {
                               
                                      current = this_WrapBoolExpr_6; 
                                      afterParserOrEnumRuleCall();
                                  
                            }
                            otherlv_7=(Token)match(input,13,FOLLOW_13_in_rulePrimary6152); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                  	newLeafNode(otherlv_7, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_2_1_2());
                                  
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


    // $ANTLR start "entryRuleConstRef"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2776:1: entryRuleConstRef returns [EObject current=null] : iv_ruleConstRef= ruleConstRef EOF ;
    public final EObject entryRuleConstRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstRef = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2777:2: (iv_ruleConstRef= ruleConstRef EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2778:2: iv_ruleConstRef= ruleConstRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getConstRefRule()); 
            }
            pushFollow(FOLLOW_ruleConstRef_in_entryRuleConstRef6190);
            iv_ruleConstRef=ruleConstRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleConstRef; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstRef6200); if (state.failed) return current;

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
    // $ANTLR end "entryRuleConstRef"


    // $ANTLR start "ruleConstRef"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2785:1: ruleConstRef returns [EObject current=null] : this_Constant_0= ruleConstant ;
    public final EObject ruleConstRef() throws RecognitionException {
        EObject current = null;

        EObject this_Constant_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2788:28: (this_Constant_0= ruleConstant )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2790:5: this_Constant_0= ruleConstant
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getConstRefAccess().getConstantParserRuleCall()); 
                  
            }
            pushFollow(FOLLOW_ruleConstant_in_ruleConstRef6246);
            this_Constant_0=ruleConstant();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Constant_0; 
                      afterParserOrEnumRuleCall();
                  
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
    // $ANTLR end "ruleConstRef"


    // $ANTLR start "entryRuleVarAccess"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2806:1: entryRuleVarAccess returns [EObject current=null] : iv_ruleVarAccess= ruleVarAccess EOF ;
    public final EObject entryRuleVarAccess() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarAccess = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2807:2: (iv_ruleVarAccess= ruleVarAccess EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2808:2: iv_ruleVarAccess= ruleVarAccess EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVarAccessRule()); 
            }
            pushFollow(FOLLOW_ruleVarAccess_in_entryRuleVarAccess6280);
            iv_ruleVarAccess=ruleVarAccess();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVarAccess; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleVarAccess6290); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2815:1: ruleVarAccess returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleVarAccess() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2818:28: ( ( (otherlv_0= RULE_ID ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2819:1: ( (otherlv_0= RULE_ID ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2819:1: ( (otherlv_0= RULE_ID ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2820:1: (otherlv_0= RULE_ID )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2820:1: (otherlv_0= RULE_ID )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2821:3: otherlv_0= RULE_ID
            {
            if ( state.backtracking==0 ) {

              			if (current==null) {
              	            current = createModelElement(grammarAccess.getVarAccessRule());
              	        }
                      
            }
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleVarAccess6334); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		newLeafNode(otherlv_0, grammarAccess.getVarAccessAccess().getRefFormalDeclarationCrossReference_0()); 
              	
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
    // $ANTLR end "ruleVarAccess"


    // $ANTLR start "entryRuleWrapBoolExpr"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2840:1: entryRuleWrapBoolExpr returns [EObject current=null] : iv_ruleWrapBoolExpr= ruleWrapBoolExpr EOF ;
    public final EObject entryRuleWrapBoolExpr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWrapBoolExpr = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2841:2: (iv_ruleWrapBoolExpr= ruleWrapBoolExpr EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2842:2: iv_ruleWrapBoolExpr= ruleWrapBoolExpr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWrapBoolExprRule()); 
            }
            pushFollow(FOLLOW_ruleWrapBoolExpr_in_entryRuleWrapBoolExpr6369);
            iv_ruleWrapBoolExpr=ruleWrapBoolExpr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleWrapBoolExpr; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleWrapBoolExpr6379); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2849:1: ruleWrapBoolExpr returns [EObject current=null] : ( (lv_value_0_0= ruleOr ) ) ;
    public final EObject ruleWrapBoolExpr() throws RecognitionException {
        EObject current = null;

        EObject lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2852:28: ( ( (lv_value_0_0= ruleOr ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2853:1: ( (lv_value_0_0= ruleOr ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2853:1: ( (lv_value_0_0= ruleOr ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2854:1: (lv_value_0_0= ruleOr )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2854:1: (lv_value_0_0= ruleOr )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2855:3: lv_value_0_0= ruleOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getWrapBoolExprAccess().getValueOrParserRuleCall_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleOr_in_ruleWrapBoolExpr6424);
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2879:1: entryRuleConstant returns [EObject current=null] : iv_ruleConstant= ruleConstant EOF ;
    public final EObject entryRuleConstant() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstant = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2880:2: (iv_ruleConstant= ruleConstant EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2881:2: iv_ruleConstant= ruleConstant EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getConstantRule()); 
            }
            pushFollow(FOLLOW_ruleConstant_in_entryRuleConstant6459);
            iv_ruleConstant=ruleConstant();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleConstant; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstant6469); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2888:1: ruleConstant returns [EObject current=null] : ( (lv_value_0_0= RULE_INT ) ) ;
    public final EObject ruleConstant() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2891:28: ( ( (lv_value_0_0= RULE_INT ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2892:1: ( (lv_value_0_0= RULE_INT ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2892:1: ( (lv_value_0_0= RULE_INT ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2893:1: (lv_value_0_0= RULE_INT )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2893:1: (lv_value_0_0= RULE_INT )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2894:3: lv_value_0_0= RULE_INT
            {
            lv_value_0_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleConstant6510); if (state.failed) return current;
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


    // $ANTLR start "entryRuleOr"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2918:1: entryRuleOr returns [EObject current=null] : iv_ruleOr= ruleOr EOF ;
    public final EObject entryRuleOr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOr = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2919:2: (iv_ruleOr= ruleOr EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2920:2: iv_ruleOr= ruleOr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrRule()); 
            }
            pushFollow(FOLLOW_ruleOr_in_entryRuleOr6550);
            iv_ruleOr=ruleOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOr; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOr6560); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2927:1: ruleOr returns [EObject current=null] : (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* ) ;
    public final EObject ruleOr() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_And_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2930:28: ( (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2931:1: (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2931:1: (this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )* )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2932:5: this_And_0= ruleAnd ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getOrAccess().getAndParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleAnd_in_ruleOr6607);
            this_And_0=ruleAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_And_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2940:1: ( () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==51) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2940:2: () otherlv_2= '||' ( (lv_right_3_0= ruleAnd ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2940:2: ()
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2941:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getOrAccess().getOrLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    otherlv_2=(Token)match(input,51,FOLLOW_51_in_ruleOr6628); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_2, grammarAccess.getOrAccess().getVerticalLineVerticalLineKeyword_1_1());
            	          
            	    }
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2950:1: ( (lv_right_3_0= ruleAnd ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2951:1: (lv_right_3_0= ruleAnd )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2951:1: (lv_right_3_0= ruleAnd )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2952:3: lv_right_3_0= ruleAnd
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleAnd_in_ruleOr6649);
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
            	    break loop45;
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2976:1: entryRuleAnd returns [EObject current=null] : iv_ruleAnd= ruleAnd EOF ;
    public final EObject entryRuleAnd() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnd = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2977:2: (iv_ruleAnd= ruleAnd EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2978:2: iv_ruleAnd= ruleAnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAndRule()); 
            }
            pushFollow(FOLLOW_ruleAnd_in_entryRuleAnd6687);
            iv_ruleAnd=ruleAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnd; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleAnd6697); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2985:1: ruleAnd returns [EObject current=null] : (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* ) ;
    public final EObject ruleAnd() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_Not_0 = null;

        EObject lv_right_3_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2988:28: ( (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2989:1: (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2989:1: (this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )* )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2990:5: this_Not_0= ruleNot ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )*
            {
            if ( state.backtracking==0 ) {
               
                      newCompositeNode(grammarAccess.getAndAccess().getNotParserRuleCall_0()); 
                  
            }
            pushFollow(FOLLOW_ruleNot_in_ruleAnd6744);
            this_Not_0=ruleNot();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               
                      current = this_Not_0; 
                      afterParserOrEnumRuleCall();
                  
            }
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2998:1: ( () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==52) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2998:2: () otherlv_2= '&&' ( (lv_right_3_0= ruleNot ) )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2998:2: ()
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2999:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	              current = forceCreateModelElementAndSet(
            	                  grammarAccess.getAndAccess().getAndLeftAction_1_0(),
            	                  current);
            	          
            	    }

            	    }

            	    otherlv_2=(Token)match(input,52,FOLLOW_52_in_ruleAnd6765); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	          	newLeafNode(otherlv_2, grammarAccess.getAndAccess().getAmpersandAmpersandKeyword_1_1());
            	          
            	    }
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3008:1: ( (lv_right_3_0= ruleNot ) )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3009:1: (lv_right_3_0= ruleNot )
            	    {
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3009:1: (lv_right_3_0= ruleNot )
            	    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3010:3: lv_right_3_0= ruleNot
            	    {
            	    if ( state.backtracking==0 ) {
            	       
            	      	        newCompositeNode(grammarAccess.getAndAccess().getRightNotParserRuleCall_1_2_0()); 
            	      	    
            	    }
            	    pushFollow(FOLLOW_ruleNot_in_ruleAnd6786);
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
            	    break loop46;
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3034:1: entryRuleNot returns [EObject current=null] : iv_ruleNot= ruleNot EOF ;
    public final EObject entryRuleNot() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNot = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3035:2: (iv_ruleNot= ruleNot EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3036:2: iv_ruleNot= ruleNot EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNotRule()); 
            }
            pushFollow(FOLLOW_ruleNot_in_entryRuleNot6824);
            iv_ruleNot=ruleNot();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNot; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleNot6834); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3043:1: ruleNot returns [EObject current=null] : ( (otherlv_0= '!' () ( (lv_value_2_0= rulePrimaryBool ) ) ) | this_PrimaryBool_3= rulePrimaryBool ) ;
    public final EObject ruleNot() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_value_2_0 = null;

        EObject this_PrimaryBool_3 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3046:28: ( ( (otherlv_0= '!' () ( (lv_value_2_0= rulePrimaryBool ) ) ) | this_PrimaryBool_3= rulePrimaryBool ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3047:1: ( (otherlv_0= '!' () ( (lv_value_2_0= rulePrimaryBool ) ) ) | this_PrimaryBool_3= rulePrimaryBool )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3047:1: ( (otherlv_0= '!' () ( (lv_value_2_0= rulePrimaryBool ) ) ) | this_PrimaryBool_3= rulePrimaryBool )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==36) ) {
                alt47=1;
            }
            else if ( ((LA47_0>=RULE_ID && LA47_0<=RULE_INT)||LA47_0==11||LA47_0==45||LA47_0==49||(LA47_0>=53 && LA47_0<=54)) ) {
                alt47=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3047:2: (otherlv_0= '!' () ( (lv_value_2_0= rulePrimaryBool ) ) )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3047:2: (otherlv_0= '!' () ( (lv_value_2_0= rulePrimaryBool ) ) )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3047:4: otherlv_0= '!' () ( (lv_value_2_0= rulePrimaryBool ) )
                    {
                    otherlv_0=(Token)match(input,36,FOLLOW_36_in_ruleNot6872); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_0, grammarAccess.getNotAccess().getExclamationMarkKeyword_0_0());
                          
                    }
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3051:1: ()
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3052:5: 
                    {
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElement(
                                  grammarAccess.getNotAccess().getNotAction_0_1(),
                                  current);
                          
                    }

                    }

                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3057:2: ( (lv_value_2_0= rulePrimaryBool ) )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3058:1: (lv_value_2_0= rulePrimaryBool )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3058:1: (lv_value_2_0= rulePrimaryBool )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3059:3: lv_value_2_0= rulePrimaryBool
                    {
                    if ( state.backtracking==0 ) {
                       
                      	        newCompositeNode(grammarAccess.getNotAccess().getValuePrimaryBoolParserRuleCall_0_2_0()); 
                      	    
                    }
                    pushFollow(FOLLOW_rulePrimaryBool_in_ruleNot6902);
                    lv_value_2_0=rulePrimaryBool();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      	        if (current==null) {
                      	            current = createModelElementForParent(grammarAccess.getNotRule());
                      	        }
                             		set(
                             			current, 
                             			"value",
                              		lv_value_2_0, 
                              		"PrimaryBool");
                      	        afterParserOrEnumRuleCall();
                      	    
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3077:5: this_PrimaryBool_3= rulePrimaryBool
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getNotAccess().getPrimaryBoolParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_rulePrimaryBool_in_ruleNot6931);
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3093:1: entryRulePrimaryBool returns [EObject current=null] : iv_rulePrimaryBool= rulePrimaryBool EOF ;
    public final EObject entryRulePrimaryBool() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryBool = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3094:2: (iv_rulePrimaryBool= rulePrimaryBool EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3095:2: iv_rulePrimaryBool= rulePrimaryBool EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPrimaryBoolRule()); 
            }
            pushFollow(FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool6966);
            iv_rulePrimaryBool=rulePrimaryBool();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePrimaryBool; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulePrimaryBool6976); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3102:1: rulePrimaryBool returns [EObject current=null] : (this_True_0= ruleTrue | this_False_1= ruleFalse | ( ( ruleComparison )=>this_Comparison_2= ruleComparison ) | (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' ) ) ;
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
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3105:28: ( (this_True_0= ruleTrue | this_False_1= ruleFalse | ( ( ruleComparison )=>this_Comparison_2= ruleComparison ) | (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3106:1: (this_True_0= ruleTrue | this_False_1= ruleFalse | ( ( ruleComparison )=>this_Comparison_2= ruleComparison ) | (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3106:1: (this_True_0= ruleTrue | this_False_1= ruleFalse | ( ( ruleComparison )=>this_Comparison_2= ruleComparison ) | (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' ) )
            int alt48=4;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==53) ) {
                alt48=1;
            }
            else if ( (LA48_0==54) ) {
                alt48=2;
            }
            else if ( (LA48_0==49) && (synpred2_InternalTimedAutomata())) {
                alt48=3;
            }
            else if ( (LA48_0==45) && (synpred2_InternalTimedAutomata())) {
                alt48=3;
            }
            else if ( (LA48_0==RULE_ID) && (synpred2_InternalTimedAutomata())) {
                alt48=3;
            }
            else if ( (LA48_0==RULE_INT) && (synpred2_InternalTimedAutomata())) {
                alt48=3;
            }
            else if ( (LA48_0==11) ) {
                int LA48_7 = input.LA(2);

                if ( (synpred2_InternalTimedAutomata()) ) {
                    alt48=3;
                }
                else if ( (true) ) {
                    alt48=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 48, 7, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3107:5: this_True_0= ruleTrue
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryBoolAccess().getTrueParserRuleCall_0()); 
                          
                    }
                    pushFollow(FOLLOW_ruleTrue_in_rulePrimaryBool7023);
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
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3117:5: this_False_1= ruleFalse
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryBoolAccess().getFalseParserRuleCall_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleFalse_in_rulePrimaryBool7050);
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
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3126:6: ( ( ruleComparison )=>this_Comparison_2= ruleComparison )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3126:6: ( ( ruleComparison )=>this_Comparison_2= ruleComparison )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3126:7: ( ruleComparison )=>this_Comparison_2= ruleComparison
                    {
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryBoolAccess().getComparisonParserRuleCall_2()); 
                          
                    }
                    pushFollow(FOLLOW_ruleComparison_in_rulePrimaryBool7083);
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
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3136:6: (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3136:6: (otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3136:8: otherlv_3= '(' this_Or_4= ruleOr otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,11,FOLLOW_11_in_rulePrimaryBool7102); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                          	newLeafNode(otherlv_3, grammarAccess.getPrimaryBoolAccess().getLeftParenthesisKeyword_3_0());
                          
                    }
                    if ( state.backtracking==0 ) {
                       
                              newCompositeNode(grammarAccess.getPrimaryBoolAccess().getOrParserRuleCall_3_1()); 
                          
                    }
                    pushFollow(FOLLOW_ruleOr_in_rulePrimaryBool7124);
                    this_Or_4=ruleOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {
                       
                              current = this_Or_4; 
                              afterParserOrEnumRuleCall();
                          
                    }
                    otherlv_5=(Token)match(input,13,FOLLOW_13_in_rulePrimaryBool7135); if (state.failed) return current;
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3161:1: entryRuleComparison returns [EObject current=null] : iv_ruleComparison= ruleComparison EOF ;
    public final EObject entryRuleComparison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparison = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3162:2: (iv_ruleComparison= ruleComparison EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3163:2: iv_ruleComparison= ruleComparison EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getComparisonRule()); 
            }
            pushFollow(FOLLOW_ruleComparison_in_entryRuleComparison7172);
            iv_ruleComparison=ruleComparison();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleComparison; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleComparison7182); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3170:1: ruleComparison returns [EObject current=null] : ( ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) ) ) ;
    public final EObject ruleComparison() throws RecognitionException {
        EObject current = null;

        EObject lv_left_0_0 = null;

        Enumerator lv_operator_1_0 = null;

        EObject lv_right_2_0 = null;


         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3173:28: ( ( ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3174:1: ( ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3174:1: ( ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3174:2: ( (lv_left_0_0= ruleBitOr ) ) ( (lv_operator_1_0= ruleComparisonOperators ) ) ( (lv_right_2_0= ruleBitOr ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3174:2: ( (lv_left_0_0= ruleBitOr ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3175:1: (lv_left_0_0= ruleBitOr )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3175:1: (lv_left_0_0= ruleBitOr )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3176:3: lv_left_0_0= ruleBitOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getComparisonAccess().getLeftBitOrParserRuleCall_0_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBitOr_in_ruleComparison7228);
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

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3192:2: ( (lv_operator_1_0= ruleComparisonOperators ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3193:1: (lv_operator_1_0= ruleComparisonOperators )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3193:1: (lv_operator_1_0= ruleComparisonOperators )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3194:3: lv_operator_1_0= ruleComparisonOperators
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getComparisonAccess().getOperatorComparisonOperatorsEnumRuleCall_1_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleComparisonOperators_in_ruleComparison7249);
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

            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3210:2: ( (lv_right_2_0= ruleBitOr ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3211:1: (lv_right_2_0= ruleBitOr )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3211:1: (lv_right_2_0= ruleBitOr )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3212:3: lv_right_2_0= ruleBitOr
            {
            if ( state.backtracking==0 ) {
               
              	        newCompositeNode(grammarAccess.getComparisonAccess().getRightBitOrParserRuleCall_2_0()); 
              	    
            }
            pushFollow(FOLLOW_ruleBitOr_in_ruleComparison7270);
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3236:1: entryRuleTrue returns [EObject current=null] : iv_ruleTrue= ruleTrue EOF ;
    public final EObject entryRuleTrue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTrue = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3237:2: (iv_ruleTrue= ruleTrue EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3238:2: iv_ruleTrue= ruleTrue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTrueRule()); 
            }
            pushFollow(FOLLOW_ruleTrue_in_entryRuleTrue7306);
            iv_ruleTrue=ruleTrue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTrue; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleTrue7316); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3245:1: ruleTrue returns [EObject current=null] : ( () otherlv_1= 'true' ) ;
    public final EObject ruleTrue() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3248:28: ( ( () otherlv_1= 'true' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3249:1: ( () otherlv_1= 'true' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3249:1: ( () otherlv_1= 'true' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3249:2: () otherlv_1= 'true'
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3249:2: ()
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3250:5: 
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getTrueAccess().getTrueAction_0(),
                          current);
                  
            }

            }

            otherlv_1=(Token)match(input,53,FOLLOW_53_in_ruleTrue7362); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getTrueAccess().getTrueKeyword_1());
                  
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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3267:1: entryRuleFalse returns [EObject current=null] : iv_ruleFalse= ruleFalse EOF ;
    public final EObject entryRuleFalse() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFalse = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3268:2: (iv_ruleFalse= ruleFalse EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3269:2: iv_ruleFalse= ruleFalse EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFalseRule()); 
            }
            pushFollow(FOLLOW_ruleFalse_in_entryRuleFalse7398);
            iv_ruleFalse=ruleFalse();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFalse; 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleFalse7408); if (state.failed) return current;

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
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3276:1: ruleFalse returns [EObject current=null] : ( () otherlv_1= 'false' ) ;
    public final EObject ruleFalse() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3279:28: ( ( () otherlv_1= 'false' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3280:1: ( () otherlv_1= 'false' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3280:1: ( () otherlv_1= 'false' )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3280:2: () otherlv_1= 'false'
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3280:2: ()
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3281:5: 
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getFalseAccess().getFalseAction_0(),
                          current);
                  
            }

            }

            otherlv_1=(Token)match(input,54,FOLLOW_54_in_ruleFalse7454); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                  	newLeafNode(otherlv_1, grammarAccess.getFalseAccess().getFalseKeyword_1());
                  
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


    // $ANTLR start "entryRuleASSIGNMENT"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3298:1: entryRuleASSIGNMENT returns [String current=null] : iv_ruleASSIGNMENT= ruleASSIGNMENT EOF ;
    public final String entryRuleASSIGNMENT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleASSIGNMENT = null;


        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3299:2: (iv_ruleASSIGNMENT= ruleASSIGNMENT EOF )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3300:2: iv_ruleASSIGNMENT= ruleASSIGNMENT EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getASSIGNMENTRule()); 
            }
            pushFollow(FOLLOW_ruleASSIGNMENT_in_entryRuleASSIGNMENT7491);
            iv_ruleASSIGNMENT=ruleASSIGNMENT();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleASSIGNMENT.getText(); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleASSIGNMENT7502); if (state.failed) return current;

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
    // $ANTLR end "entryRuleASSIGNMENT"


    // $ANTLR start "ruleASSIGNMENT"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3307:1: ruleASSIGNMENT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '=' | kw= ':=' ) ;
    public final AntlrDatatypeRuleToken ruleASSIGNMENT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3310:28: ( (kw= '=' | kw= ':=' ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3311:1: (kw= '=' | kw= ':=' )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3311:1: (kw= '=' | kw= ':=' )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==55) ) {
                alt49=1;
            }
            else if ( (LA49_0==56) ) {
                alt49=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3312:2: kw= '='
                    {
                    kw=(Token)match(input,55,FOLLOW_55_in_ruleASSIGNMENT7540); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getASSIGNMENTAccess().getEqualsSignKeyword_0()); 
                          
                    }

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3319:2: kw= ':='
                    {
                    kw=(Token)match(input,56,FOLLOW_56_in_ruleASSIGNMENT7559); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getASSIGNMENTAccess().getColonEqualsSignKeyword_1()); 
                          
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
    // $ANTLR end "ruleASSIGNMENT"


    // $ANTLR start "ruleComparisonOperators"
    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3332:1: ruleComparisonOperators returns [Enumerator current=null] : ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) | (enumLiteral_5= '!=' ) ) ;
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
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3334:28: ( ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) | (enumLiteral_5= '!=' ) ) )
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3335:1: ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) | (enumLiteral_5= '!=' ) )
            {
            // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3335:1: ( (enumLiteral_0= '>' ) | (enumLiteral_1= '<' ) | (enumLiteral_2= '>=' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '==' ) | (enumLiteral_5= '!=' ) )
            int alt50=6;
            switch ( input.LA(1) ) {
            case 57:
                {
                alt50=1;
                }
                break;
            case 58:
                {
                alt50=2;
                }
                break;
            case 59:
                {
                alt50=3;
                }
                break;
            case 60:
                {
                alt50=4;
                }
                break;
            case 61:
                {
                alt50=5;
                }
                break;
            case 62:
                {
                alt50=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }

            switch (alt50) {
                case 1 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3335:2: (enumLiteral_0= '>' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3335:2: (enumLiteral_0= '>' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3335:4: enumLiteral_0= '>'
                    {
                    enumLiteral_0=(Token)match(input,57,FOLLOW_57_in_ruleComparisonOperators7613); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getGTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_0, grammarAccess.getComparisonOperatorsAccess().getGTEnumLiteralDeclaration_0()); 
                          
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3341:6: (enumLiteral_1= '<' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3341:6: (enumLiteral_1= '<' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3341:8: enumLiteral_1= '<'
                    {
                    enumLiteral_1=(Token)match(input,58,FOLLOW_58_in_ruleComparisonOperators7630); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getLTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_1, grammarAccess.getComparisonOperatorsAccess().getLTEnumLiteralDeclaration_1()); 
                          
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3347:6: (enumLiteral_2= '>=' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3347:6: (enumLiteral_2= '>=' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3347:8: enumLiteral_2= '>='
                    {
                    enumLiteral_2=(Token)match(input,59,FOLLOW_59_in_ruleComparisonOperators7647); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getGEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_2, grammarAccess.getComparisonOperatorsAccess().getGEEnumLiteralDeclaration_2()); 
                          
                    }

                    }


                    }
                    break;
                case 4 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3353:6: (enumLiteral_3= '<=' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3353:6: (enumLiteral_3= '<=' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3353:8: enumLiteral_3= '<='
                    {
                    enumLiteral_3=(Token)match(input,60,FOLLOW_60_in_ruleComparisonOperators7664); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getLEEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_3, grammarAccess.getComparisonOperatorsAccess().getLEEnumLiteralDeclaration_3()); 
                          
                    }

                    }


                    }
                    break;
                case 5 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3359:6: (enumLiteral_4= '==' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3359:6: (enumLiteral_4= '==' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3359:8: enumLiteral_4= '=='
                    {
                    enumLiteral_4=(Token)match(input,61,FOLLOW_61_in_ruleComparisonOperators7681); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = grammarAccess.getComparisonOperatorsAccess().getEQEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                              newLeafNode(enumLiteral_4, grammarAccess.getComparisonOperatorsAccess().getEQEnumLiteralDeclaration_4()); 
                          
                    }

                    }


                    }
                    break;
                case 6 :
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3365:6: (enumLiteral_5= '!=' )
                    {
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3365:6: (enumLiteral_5= '!=' )
                    // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3365:8: enumLiteral_5= '!='
                    {
                    enumLiteral_5=(Token)match(input,62,FOLLOW_62_in_ruleComparisonOperators7698); if (state.failed) return current;
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

    // $ANTLR start synpred1_InternalTimedAutomata
    public final void synpred1_InternalTimedAutomata_fragment() throws RecognitionException {   
        // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2731:8: ( ( '(' ruleBitOr ')' ) )
        // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2731:9: ( '(' ruleBitOr ')' )
        {
        // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2731:9: ( '(' ruleBitOr ')' )
        // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:2731:11: '(' ruleBitOr ')'
        {
        match(input,11,FOLLOW_11_in_synpred1_InternalTimedAutomata6049); if (state.failed) return ;
        pushFollow(FOLLOW_ruleBitOr_in_synpred1_InternalTimedAutomata6053);
        ruleBitOr();

        state._fsp--;
        if (state.failed) return ;
        match(input,13,FOLLOW_13_in_synpred1_InternalTimedAutomata6055); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred1_InternalTimedAutomata

    // $ANTLR start synpred2_InternalTimedAutomata
    public final void synpred2_InternalTimedAutomata_fragment() throws RecognitionException {   
        // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3126:7: ( ruleComparison )
        // ../fr.lip6.move.xta/src-gen/fr/lip6/move/parser/antlr/internal/InternalTimedAutomata.g:3126:9: ruleComparison
        {
        pushFollow(FOLLOW_ruleComparison_in_synpred2_InternalTimedAutomata7067);
        ruleComparison();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalTimedAutomata

    // Delegated rules

    public final boolean synpred2_InternalTimedAutomata() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalTimedAutomata_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalTimedAutomata() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalTimedAutomata_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_ruleXTA_in_entryRuleXTA75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXTA85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDecl_in_ruleXTA132 = new BitSet(new long[]{0x0000000013998010L});
    public static final BitSet FOLLOW_ruleChannelDecl_in_ruleXTA159 = new BitSet(new long[]{0x0000000013998010L});
    public static final BitSet FOLLOW_ruleTypeDecl_in_ruleXTA186 = new BitSet(new long[]{0x0000000013998010L});
    public static final BitSet FOLLOW_ruleProcDecl_in_ruleXTA213 = new BitSet(new long[]{0x0000000013998010L});
    public static final BitSet FOLLOW_ruleInstance_in_ruleXTA236 = new BitSet(new long[]{0x0000000000008010L});
    public static final BitSet FOLLOW_ruleSystem_in_ruleXTA258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInstance_in_entryRuleInstance296 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInstance306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleInstance348 = new BitSet(new long[]{0x0180000000000000L});
    public static final BitSet FOLLOW_ruleASSIGNMENT_in_ruleInstance369 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleInstance388 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_ruleInstance400 = new BitSet(new long[]{0x0002200000002830L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleInstance422 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_12_in_ruleInstance435 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleInstance456 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_13_in_ruleInstance472 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleInstance484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSystem_in_entryRuleSystem520 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSystem530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleSystem567 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleSystem587 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_12_in_ruleSystem600 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleSystem620 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleSystem634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProcDecl_in_entryRuleProcDecl670 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProcDecl680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleProcDecl717 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleProcDecl734 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_ruleProcDecl751 = new BitSet(new long[]{0x0000000013802010L});
    public static final BitSet FOLLOW_ruleParameter_in_ruleProcDecl773 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_12_in_ruleProcDecl786 = new BitSet(new long[]{0x0000000013800010L});
    public static final BitSet FOLLOW_ruleParameter_in_ruleProcDecl807 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_13_in_ruleProcDecl823 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleProcDecl835 = new BitSet(new long[]{0x0000000033880000L});
    public static final BitSet FOLLOW_ruleProcBody_in_ruleProcDecl856 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleProcDecl868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameter_in_entryRuleParameter906 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParameter916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleType_in_ruleParameter962 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameter979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTypeDecl_in_entryRuleTypeDecl1020 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTypeDecl1030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleTypeDecl1067 = new BitSet(new long[]{0x0000000013800010L});
    public static final BitSet FOLLOW_ruleType_in_ruleTypeDecl1088 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTypeDecl1105 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleTypeDecl1122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleType_in_entryRuleType1158 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleType1168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBasicType_in_ruleType1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTypedefRef_in_ruleType1242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBasicType_in_entryRuleBasicType1277 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBasicType1287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIntegerType_in_ruleBasicType1334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBoolType_in_ruleBasicType1361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleClockType_in_ruleBasicType1388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRangeType_in_ruleBasicType1415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTypedefRef_in_entryRuleTypedefRef1450 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTypedefRef1460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTypedefRef1504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleChannelDecl_in_entryRuleChannelDecl1539 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleChannelDecl1549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleChannelType_in_ruleChannelDecl1596 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleChanId_in_ruleChannelDecl1616 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_12_in_ruleChannelDecl1629 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleChanId_in_ruleChannelDecl1650 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleChannelDecl1664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleChanId_in_entryRuleChanId1700 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleChanId1710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleChanId1751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleChannelType_in_entryRuleChannelType1791 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleChannelType1801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_ruleChannelType1847 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_21_in_ruleChannelType1865 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_ruleChannelType1897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIntegerType_in_entryRuleIntegerType1947 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleIntegerType1957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleIntegerType2009 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_ruleIntegerType2035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBoolType_in_entryRuleBoolType2071 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBoolType2081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleBoolType2133 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleBoolType2159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRangeType_in_entryRuleRangeType2195 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRangeType2205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleRangeType2248 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_ruleRangeType2274 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_ruleRangeType2286 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleRangeType2303 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleRangeType2320 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleRangeType2337 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleRangeType2354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleClockType_in_entryRuleClockType2390 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleClockType2400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleClockType2446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProcBody_in_entryRuleProcBody2482 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProcBody2492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDecl_in_ruleProcBody2539 = new BitSet(new long[]{0x0000000033880000L});
    public static final BitSet FOLLOW_ruleTypeDecl_in_ruleProcBody2566 = new BitSet(new long[]{0x0000000033880000L});
    public static final BitSet FOLLOW_29_in_ruleProcBody2580 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleStateDecl_in_ruleProcBody2601 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_12_in_ruleProcBody2614 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleStateDecl_in_ruleProcBody2635 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleProcBody2649 = new BitSet(new long[]{0x00000000C0200000L});
    public static final BitSet FOLLOW_30_in_ruleProcBody2662 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleProcBody2682 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_12_in_ruleProcBody2695 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleProcBody2715 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleProcBody2729 = new BitSet(new long[]{0x0000000080200000L});
    public static final BitSet FOLLOW_21_in_ruleProcBody2744 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleProcBody2764 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_12_in_ruleProcBody2777 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleProcBody2797 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleProcBody2811 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleProcBody2825 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleProcBody2845 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleProcBody2857 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_ruleProcBody2870 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleTransition_in_ruleProcBody2891 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_12_in_ruleProcBody2904 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleTransition_in_ruleProcBody2925 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleProcBody2939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStateDecl_in_entryRuleStateDecl2976 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStateDecl2986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleStateDecl3028 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_17_in_ruleStateDecl3046 = new BitSet(new long[]{0x0062201000000830L});
    public static final BitSet FOLLOW_ruleOr_in_ruleStateDecl3067 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleStateDecl3079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTransition_in_entryRuleTransition3117 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTransition3127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTransition3172 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_ruleTransition3184 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTransition3204 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleTransition3216 = new BitSet(new long[]{0x0000004C00040000L});
    public static final BitSet FOLLOW_34_in_ruleTransition3229 = new BitSet(new long[]{0x0062201000000830L});
    public static final BitSet FOLLOW_ruleOr_in_ruleTransition3250 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleTransition3262 = new BitSet(new long[]{0x0000004800040000L});
    public static final BitSet FOLLOW_ruleSync_in_ruleTransition3285 = new BitSet(new long[]{0x0000004000040000L});
    public static final BitSet FOLLOW_ruleAssignments_in_ruleTransition3307 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleTransition3320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSync_in_entryRuleSync3356 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSync3366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSend_in_ruleSync3413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRecv_in_ruleSync3440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSend_in_entryRuleSend3475 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSend3485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleSend3522 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleSend3542 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_36_in_ruleSend3554 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleSend3566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRecv_in_entryRuleRecv3602 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRecv3612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleRecv3649 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRecv3669 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_ruleRecv3681 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleRecv3693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssignments_in_entryRuleAssignments3729 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssignments3739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_ruleAssignments3776 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleAssign_in_ruleAssignments3797 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_12_in_ruleAssignments3810 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleAssign_in_ruleAssignments3831 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleAssignments3845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAssign_in_entryRuleAssign3881 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAssign3891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVarAccess_in_ruleAssign3937 = new BitSet(new long[]{0x0180000000000000L});
    public static final BitSet FOLLOW_ruleASSIGNMENT_in_ruleAssign3953 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleAssign3973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVariableDecl_in_entryRuleVariableDecl4009 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVariableDecl4019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBasicType_in_ruleVariableDecl4065 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleDeclId_in_ruleVariableDecl4086 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_12_in_ruleVariableDecl4099 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleDeclId_in_ruleVariableDecl4120 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_14_in_ruleVariableDecl4134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDeclId_in_entryRuleDeclId4170 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDeclId4180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleDeclId4222 = new BitSet(new long[]{0x0180000004000002L});
    public static final BitSet FOLLOW_ruleArrayDecl_in_ruleDeclId4248 = new BitSet(new long[]{0x0180000004000002L});
    public static final BitSet FOLLOW_ruleASSIGNMENT_in_ruleDeclId4266 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleInitialiser_in_ruleDeclId4286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInitialiser_in_entryRuleInitialiser4324 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInitialiser4334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleInitialiser4379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayDecl_in_entryRuleArrayDecl4414 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArrayDecl4424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleArrayDecl4461 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleArrayDecl4478 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleArrayDecl4495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitOr_in_entryRuleBitOr4531 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitOr4541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitXor_in_ruleBitOr4588 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_39_in_ruleBitOr4615 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleBitXor_in_ruleBitOr4649 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_ruleBitXor_in_entryRuleBitXor4687 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitXor4697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitAnd_in_ruleBitXor4744 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_40_in_ruleBitXor4771 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleBitAnd_in_ruleBitXor4805 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_ruleBitAnd_in_entryRuleBitAnd4843 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitAnd4853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitShift_in_ruleBitAnd4900 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_ruleBitAnd4927 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleBitShift_in_ruleBitAnd4961 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_ruleBitShift_in_entryRuleBitShift4999 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitShift5009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleBitShift5056 = new BitSet(new long[]{0x00000C0000000002L});
    public static final BitSet FOLLOW_42_in_ruleBitShift5085 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_43_in_ruleBitShift5114 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleAddition_in_ruleBitShift5151 = new BitSet(new long[]{0x00000C0000000002L});
    public static final BitSet FOLLOW_ruleAddition_in_entryRuleAddition5189 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAddition5199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition5246 = new BitSet(new long[]{0x0000300000000002L});
    public static final BitSet FOLLOW_44_in_ruleAddition5275 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_45_in_ruleAddition5304 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleMultiplication_in_ruleAddition5341 = new BitSet(new long[]{0x0000300000000002L});
    public static final BitSet FOLLOW_ruleMultiplication_in_entryRuleMultiplication5379 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMultiplication5389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitComplement_in_ruleMultiplication5436 = new BitSet(new long[]{0x0001C00000000002L});
    public static final BitSet FOLLOW_46_in_ruleMultiplication5465 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_47_in_ruleMultiplication5494 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_48_in_ruleMultiplication5523 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleBitComplement_in_ruleMultiplication5560 = new BitSet(new long[]{0x0001C00000000002L});
    public static final BitSet FOLLOW_ruleBitComplement_in_entryRuleBitComplement5598 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBitComplement5608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_ruleBitComplement5646 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_rulePower_in_ruleBitComplement5668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruleBitComplement5696 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_rulePower_in_ruleBitComplement5718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePower_in_ruleBitComplement5755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePower_in_entryRulePower5790 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePower5800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_rulePower5847 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_50_in_rulePower5874 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_rulePrimary_in_rulePower5908 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_rulePrimary_in_entryRulePrimary5946 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimary5956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVarAccess_in_rulePrimary6003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstRef_in_rulePrimary6030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rulePrimary6065 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleBitOr_in_rulePrimary6087 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_rulePrimary6098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rulePrimary6119 = new BitSet(new long[]{0x0062201000000830L});
    public static final BitSet FOLLOW_ruleWrapBoolExpr_in_rulePrimary6141 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_rulePrimary6152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstRef_in_entryRuleConstRef6190 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstRef6200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstant_in_ruleConstRef6246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVarAccess_in_entryRuleVarAccess6280 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVarAccess6290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleVarAccess6334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleWrapBoolExpr_in_entryRuleWrapBoolExpr6369 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleWrapBoolExpr6379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_ruleWrapBoolExpr6424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstant_in_entryRuleConstant6459 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstant6469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleConstant6510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOr_in_entryRuleOr6550 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOr6560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_ruleOr6607 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_51_in_ruleOr6628 = new BitSet(new long[]{0x0062201000000830L});
    public static final BitSet FOLLOW_ruleAnd_in_ruleOr6649 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_ruleAnd_in_entryRuleAnd6687 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAnd6697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_ruleAnd6744 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_52_in_ruleAnd6765 = new BitSet(new long[]{0x0062201000000830L});
    public static final BitSet FOLLOW_ruleNot_in_ruleAnd6786 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_ruleNot_in_entryRuleNot6824 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNot6834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleNot6872 = new BitSet(new long[]{0x0062201000000830L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_ruleNot6902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_ruleNot6931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePrimaryBool_in_entryRulePrimaryBool6966 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePrimaryBool6976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_rulePrimaryBool7023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_rulePrimaryBool7050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_rulePrimaryBool7083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rulePrimaryBool7102 = new BitSet(new long[]{0x0062201000000830L});
    public static final BitSet FOLLOW_ruleOr_in_rulePrimaryBool7124 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_rulePrimaryBool7135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_entryRuleComparison7172 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComparison7182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleComparison7228 = new BitSet(new long[]{0x7E00000000000000L});
    public static final BitSet FOLLOW_ruleComparisonOperators_in_ruleComparison7249 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleBitOr_in_ruleComparison7270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTrue_in_entryRuleTrue7306 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTrue7316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_ruleTrue7362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFalse_in_entryRuleFalse7398 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFalse7408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_ruleFalse7454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleASSIGNMENT_in_entryRuleASSIGNMENT7491 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleASSIGNMENT7502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_ruleASSIGNMENT7540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_ruleASSIGNMENT7559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_ruleComparisonOperators7613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_ruleComparisonOperators7630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_ruleComparisonOperators7647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_ruleComparisonOperators7664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_ruleComparisonOperators7681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_ruleComparisonOperators7698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_synpred1_InternalTimedAutomata6049 = new BitSet(new long[]{0x0002200000000830L});
    public static final BitSet FOLLOW_ruleBitOr_in_synpred1_InternalTimedAutomata6053 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_synpred1_InternalTimedAutomata6055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComparison_in_synpred2_InternalTimedAutomata7067 = new BitSet(new long[]{0x0000000000000002L});

}