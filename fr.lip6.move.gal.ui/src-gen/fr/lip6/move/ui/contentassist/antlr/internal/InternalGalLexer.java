package fr.lip6.move.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalGalLexer extends Lexer {
    public static final int T__40=40;
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
    public static final int RULE_SL_COMMENT=8;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__19=19;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_STRING=6;
    public static final int T__16=16;
    public static final int T__33=33;
    public static final int T__15=15;
    public static final int T__34=34;
    public static final int T__18=18;
    public static final int T__35=35;
    public static final int T__17=17;
    public static final int T__36=36;
    public static final int T__12=12;
    public static final int T__37=37;
    public static final int T__11=11;
    public static final int T__38=38;
    public static final int T__14=14;
    public static final int T__39=39;
    public static final int T__13=13;
    public static final int RULE_INT=5;
    public static final int RULE_WS=9;

    // delegates
    // delegators

    public InternalGalLexer() {;} 
    public InternalGalLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalGalLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g"; }

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:11:7: ( '<' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:11:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:12:7: ( '>' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:12:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:13:7: ( '>=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:13:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:14:7: ( '<=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:14:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:15:7: ( '==' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:15:9: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:16:7: ( '!=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:16:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:17:7: ( 'GAL' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:17:9: 'GAL'
            {
            match("GAL"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:18:7: ( '{' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:18:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:19:7: ( '}' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:19:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:20:7: ( '=' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:20:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:21:7: ( ';' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:21:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:22:7: ( '[' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:22:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:23:7: ( ']' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:23:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:24:7: ( 'transition' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:24:9: 'transition'
            {
            match("transition"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:25:7: ( 'label' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:25:9: 'label'
            {
            match("label"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:26:7: ( '+' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:26:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:27:7: ( '-' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:27:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:28:7: ( '*' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:28:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:29:7: ( '/' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:29:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:30:7: ( '%' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:30:9: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:31:7: ( '**' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:31:9: '**'
            {
            match("**"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:32:7: ( '(' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:32:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:33:7: ( ')' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:33:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:34:7: ( '||' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:34:9: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:35:7: ( '&&' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:35:9: '&&'
            {
            match("&&"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:36:7: ( '!' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:36:9: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:37:7: ( '.' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:37:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:38:7: ( '.*' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:38:9: '.*'
            {
            match(".*"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:39:7: ( 'True' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:39:9: 'True'
            {
            match("True"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:40:7: ( 'False' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:40:9: 'False'
            {
            match("False"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4199:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4199:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4199:11: ( '^' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='^') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4199:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4199:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4201:10: ( ( '0' .. '9' )+ )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4201:12: ( '0' .. '9' )+
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4201:12: ( '0' .. '9' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4201:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:13: ( ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:15: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:15: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\"') ) {
                alt6=1;
            }
            else if ( (LA6_0=='\'') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:16: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:20: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop4:
                    do {
                        int alt4=3;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0=='\\') ) {
                            alt4=1;
                        }
                        else if ( ((LA4_0>='\u0000' && LA4_0<='!')||(LA4_0>='#' && LA4_0<='[')||(LA4_0>=']' && LA4_0<='\uFFFF')) ) {
                            alt4=2;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:21: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' )
                    	    {
                    	    match('\\'); 
                    	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:66: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:86: '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:91: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop5:
                    do {
                        int alt5=3;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0=='\\') ) {
                            alt5=1;
                        }
                        else if ( ((LA5_0>='\u0000' && LA5_0<='&')||(LA5_0>='(' && LA5_0<='[')||(LA5_0>=']' && LA5_0<='\uFFFF')) ) {
                            alt5=2;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:92: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' )
                    	    {
                    	    match('\\'); 
                    	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4203:137: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4205:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4205:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4205:24: ( options {greedy=false; } : . )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='*') ) {
                    int LA7_1 = input.LA(2);

                    if ( (LA7_1=='/') ) {
                        alt7=2;
                    }
                    else if ( ((LA7_1>='\u0000' && LA7_1<='.')||(LA7_1>='0' && LA7_1<='\uFFFF')) ) {
                        alt7=1;
                    }


                }
                else if ( ((LA7_0>='\u0000' && LA7_0<=')')||(LA7_0>='+' && LA7_0<='\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4205:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4207:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4207:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4207:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='\u0000' && LA8_0<='\t')||(LA8_0>='\u000B' && LA8_0<='\f')||(LA8_0>='\u000E' && LA8_0<='\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4207:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4207:40: ( ( '\\r' )? '\\n' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='\n'||LA10_0=='\r') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4207:41: ( '\\r' )? '\\n'
                    {
                    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4207:41: ( '\\r' )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0=='\r') ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4207:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4209:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4209:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4209:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='\t' && LA11_0<='\n')||LA11_0=='\r'||LA11_0==' ') ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4211:16: ( . )
            // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:4211:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt12=37;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:10: T__11
                {
                mT__11(); 

                }
                break;
            case 2 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:16: T__12
                {
                mT__12(); 

                }
                break;
            case 3 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:22: T__13
                {
                mT__13(); 

                }
                break;
            case 4 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:28: T__14
                {
                mT__14(); 

                }
                break;
            case 5 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:34: T__15
                {
                mT__15(); 

                }
                break;
            case 6 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:40: T__16
                {
                mT__16(); 

                }
                break;
            case 7 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:46: T__17
                {
                mT__17(); 

                }
                break;
            case 8 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:52: T__18
                {
                mT__18(); 

                }
                break;
            case 9 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:58: T__19
                {
                mT__19(); 

                }
                break;
            case 10 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:64: T__20
                {
                mT__20(); 

                }
                break;
            case 11 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:70: T__21
                {
                mT__21(); 

                }
                break;
            case 12 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:76: T__22
                {
                mT__22(); 

                }
                break;
            case 13 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:82: T__23
                {
                mT__23(); 

                }
                break;
            case 14 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:88: T__24
                {
                mT__24(); 

                }
                break;
            case 15 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:94: T__25
                {
                mT__25(); 

                }
                break;
            case 16 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:100: T__26
                {
                mT__26(); 

                }
                break;
            case 17 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:106: T__27
                {
                mT__27(); 

                }
                break;
            case 18 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:112: T__28
                {
                mT__28(); 

                }
                break;
            case 19 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:118: T__29
                {
                mT__29(); 

                }
                break;
            case 20 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:124: T__30
                {
                mT__30(); 

                }
                break;
            case 21 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:130: T__31
                {
                mT__31(); 

                }
                break;
            case 22 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:136: T__32
                {
                mT__32(); 

                }
                break;
            case 23 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:142: T__33
                {
                mT__33(); 

                }
                break;
            case 24 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:148: T__34
                {
                mT__34(); 

                }
                break;
            case 25 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:154: T__35
                {
                mT__35(); 

                }
                break;
            case 26 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:160: T__36
                {
                mT__36(); 

                }
                break;
            case 27 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:166: T__37
                {
                mT__37(); 

                }
                break;
            case 28 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:172: T__38
                {
                mT__38(); 

                }
                break;
            case 29 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:178: T__39
                {
                mT__39(); 

                }
                break;
            case 30 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:184: T__40
                {
                mT__40(); 

                }
                break;
            case 31 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:190: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 32 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:198: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 33 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:207: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 34 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:219: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 35 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:235: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 36 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:251: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 37 :
                // ../fr.lip6.move.gal.ui/src-gen/fr/lip6/move/ui/contentassist/antlr/internal/InternalGal.g:1:259: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA12_eotS =
        "\1\uffff\1\41\1\43\1\45\1\47\1\51\5\uffff\2\51\2\uffff\1\64\1\67"+
        "\3\uffff\2\37\1\76\2\51\1\37\2\uffff\2\37\12\uffff\1\51\6\uffff"+
        "\2\51\16\uffff\2\51\3\uffff\1\111\4\51\1\uffff\2\51\1\120\2\51\1"+
        "\123\1\uffff\1\124\1\51\2\uffff\3\51\1\131\1\uffff";
    static final String DFA12_eofS =
        "\132\uffff";
    static final String DFA12_minS =
        "\1\0\4\75\1\101\5\uffff\1\162\1\141\2\uffff\2\52\3\uffff\1\174\1"+
        "\46\1\52\1\162\1\141\1\101\2\uffff\2\0\12\uffff\1\114\6\uffff\1"+
        "\141\1\142\16\uffff\1\165\1\154\3\uffff\1\60\1\156\2\145\1\163\1"+
        "\uffff\1\163\1\154\1\60\1\145\1\151\1\60\1\uffff\1\60\1\164\2\uffff"+
        "\1\151\1\157\1\156\1\60\1\uffff";
    static final String DFA12_maxS =
        "\1\uffff\4\75\1\101\5\uffff\1\162\1\141\2\uffff\1\52\1\57\3\uffff"+
        "\1\174\1\46\1\52\1\162\1\141\1\172\2\uffff\2\uffff\12\uffff\1\114"+
        "\6\uffff\1\141\1\142\16\uffff\1\165\1\154\3\uffff\1\172\1\156\2"+
        "\145\1\163\1\uffff\1\163\1\154\1\172\1\145\1\151\1\172\1\uffff\1"+
        "\172\1\164\2\uffff\1\151\1\157\1\156\1\172\1\uffff";
    static final String DFA12_acceptS =
        "\6\uffff\1\10\1\11\1\13\1\14\1\15\2\uffff\1\20\1\21\2\uffff\1\24"+
        "\1\26\1\27\6\uffff\1\37\1\40\2\uffff\1\44\1\45\1\4\1\1\1\3\1\2\1"+
        "\5\1\12\1\6\1\32\1\uffff\1\37\1\10\1\11\1\13\1\14\1\15\2\uffff\1"+
        "\20\1\21\1\25\1\22\1\42\1\43\1\23\1\24\1\26\1\27\1\30\1\31\1\34"+
        "\1\33\2\uffff\1\40\1\41\1\44\5\uffff\1\7\6\uffff\1\35\2\uffff\1"+
        "\17\1\36\4\uffff\1\16";
    static final String DFA12_specialS =
        "\1\2\33\uffff\1\1\1\0\74\uffff}>";
    static final String[] DFA12_transitionS = {
            "\11\37\2\36\2\37\1\36\22\37\1\36\1\4\1\34\2\37\1\21\1\25\1\35"+
            "\1\22\1\23\1\17\1\15\1\37\1\16\1\26\1\20\12\33\1\37\1\10\1\1"+
            "\1\3\1\2\2\37\5\32\1\30\1\5\14\32\1\27\6\32\1\11\1\37\1\12\1"+
            "\31\1\32\1\37\13\32\1\14\7\32\1\13\6\32\1\6\1\24\1\7\uff82\37",
            "\1\40",
            "\1\42",
            "\1\44",
            "\1\46",
            "\1\50",
            "",
            "",
            "",
            "",
            "",
            "\1\57",
            "\1\60",
            "",
            "",
            "\1\63",
            "\1\65\4\uffff\1\66",
            "",
            "",
            "",
            "\1\73",
            "\1\74",
            "\1\75",
            "\1\77",
            "\1\100",
            "\32\51\4\uffff\1\51\1\uffff\32\51",
            "",
            "",
            "\0\102",
            "\0\102",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\104",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\105",
            "\1\106",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\107",
            "\1\110",
            "",
            "",
            "",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "",
            "\1\116",
            "\1\117",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\121",
            "\1\122",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\125",
            "",
            "",
            "\1\126",
            "\1\127",
            "\1\130",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            ""
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
            return "1:1: Tokens : ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA12_29 = input.LA(1);

                        s = -1;
                        if ( ((LA12_29>='\u0000' && LA12_29<='\uFFFF')) ) {s = 66;}

                        else s = 31;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA12_28 = input.LA(1);

                        s = -1;
                        if ( ((LA12_28>='\u0000' && LA12_28<='\uFFFF')) ) {s = 66;}

                        else s = 31;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA12_0 = input.LA(1);

                        s = -1;
                        if ( (LA12_0=='<') ) {s = 1;}

                        else if ( (LA12_0=='>') ) {s = 2;}

                        else if ( (LA12_0=='=') ) {s = 3;}

                        else if ( (LA12_0=='!') ) {s = 4;}

                        else if ( (LA12_0=='G') ) {s = 5;}

                        else if ( (LA12_0=='{') ) {s = 6;}

                        else if ( (LA12_0=='}') ) {s = 7;}

                        else if ( (LA12_0==';') ) {s = 8;}

                        else if ( (LA12_0=='[') ) {s = 9;}

                        else if ( (LA12_0==']') ) {s = 10;}

                        else if ( (LA12_0=='t') ) {s = 11;}

                        else if ( (LA12_0=='l') ) {s = 12;}

                        else if ( (LA12_0=='+') ) {s = 13;}

                        else if ( (LA12_0=='-') ) {s = 14;}

                        else if ( (LA12_0=='*') ) {s = 15;}

                        else if ( (LA12_0=='/') ) {s = 16;}

                        else if ( (LA12_0=='%') ) {s = 17;}

                        else if ( (LA12_0=='(') ) {s = 18;}

                        else if ( (LA12_0==')') ) {s = 19;}

                        else if ( (LA12_0=='|') ) {s = 20;}

                        else if ( (LA12_0=='&') ) {s = 21;}

                        else if ( (LA12_0=='.') ) {s = 22;}

                        else if ( (LA12_0=='T') ) {s = 23;}

                        else if ( (LA12_0=='F') ) {s = 24;}

                        else if ( (LA12_0=='^') ) {s = 25;}

                        else if ( ((LA12_0>='A' && LA12_0<='E')||(LA12_0>='H' && LA12_0<='S')||(LA12_0>='U' && LA12_0<='Z')||LA12_0=='_'||(LA12_0>='a' && LA12_0<='k')||(LA12_0>='m' && LA12_0<='s')||(LA12_0>='u' && LA12_0<='z')) ) {s = 26;}

                        else if ( ((LA12_0>='0' && LA12_0<='9')) ) {s = 27;}

                        else if ( (LA12_0=='\"') ) {s = 28;}

                        else if ( (LA12_0=='\'') ) {s = 29;}

                        else if ( ((LA12_0>='\t' && LA12_0<='\n')||LA12_0=='\r'||LA12_0==' ') ) {s = 30;}

                        else if ( ((LA12_0>='\u0000' && LA12_0<='\b')||(LA12_0>='\u000B' && LA12_0<='\f')||(LA12_0>='\u000E' && LA12_0<='\u001F')||(LA12_0>='#' && LA12_0<='$')||LA12_0==','||LA12_0==':'||(LA12_0>='?' && LA12_0<='@')||LA12_0=='\\'||LA12_0=='`'||(LA12_0>='~' && LA12_0<='\uFFFF')) ) {s = 31;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 12, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}