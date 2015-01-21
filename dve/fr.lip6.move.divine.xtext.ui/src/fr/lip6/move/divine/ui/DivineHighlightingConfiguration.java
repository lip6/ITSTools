package fr.lip6.move.divine.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class DivineHighlightingConfiguration implements IHighlightingConfiguration { 

 

    /** 

     * the ID of keywords highlighting configuration 

     */ 

    //public static final String KEYWORD_ID = "keyword"; 
    public static final String CHANNEL_ID = "channel"; 
    public static final String PROCESS_ID = "process"; 
    public static final String ARRAY_ID = "array"; 
    public static final String TRANSITION_ID = "trans"; 
    public static final String EFFECT_ID = "effect"; 
    public static final String GUARD_ID = "guard"; 
    public static final String SYNC_ID = "sync"; 
 //   public static final String _ID = "keyword"; 
    

 

    @Override 
    public void configure(IHighlightingConfigurationAcceptor acceptor) { 

	acceptor.acceptDefaultHighlighting(CHANNEL_ID, "channel", keywordTextStyle_channel()); 
	acceptor.acceptDefaultHighlighting(PROCESS_ID, "process", keywordTextStyle_process()); 
	acceptor.acceptDefaultHighlighting(ARRAY_ID, "array", keywordTextStyle_array()); 
	acceptor.acceptDefaultHighlighting(TRANSITION_ID, "transition", keywordTextStyle_transition()); 
	acceptor.acceptDefaultHighlighting(EFFECT_ID, "effect", keywordTextStyle_effect()); 
	acceptor.acceptDefaultHighlighting(GUARD_ID, "guard", keywordTextStyle_guard()); 
	acceptor.acceptDefaultHighlighting(SYNC_ID, "sync", keywordTextStyle_sync()); 
	

    } 

 

    private TextStyle keywordTextStyle_transition() {
    	TextStyle textStyle = new TextStyle(); 

    	textStyle.setColor(new RGB(255, 0, 0)); 

    	textStyle.setStyle(SWT.BOLD); 

    	return textStyle; 

	}



	private TextStyle keywordTextStyle_array() {
		TextStyle textStyle = new TextStyle(); 

    	textStyle.setColor(new RGB(255, 0, 0)); 

    	textStyle.setStyle(SWT.BOLD); 

    	return textStyle; 

	}



	private TextStyle keywordTextStyle_process() {
		TextStyle textStyle = new TextStyle(); 

    	textStyle.setColor(new RGB(255, 0, 0)); 

    	textStyle.setStyle(SWT.BOLD); 

    	return textStyle; 

	}






	private TextStyle keywordTextStyle_effect() {
		TextStyle textStyle = new TextStyle(); 

    	textStyle.setColor(new RGB(255, 0, 0)); 

    	textStyle.setStyle(SWT.BOLD); 

    	return textStyle; 

	}



	private TextStyle keywordTextStyle_guard() {
		TextStyle textStyle = new TextStyle(); 

    	textStyle.setColor(new RGB(255, 0, 0)); 

    	textStyle.setStyle(SWT.NORMAL); 

    	return textStyle; 

	}



	private TextStyle keywordTextStyle_sync() {
		TextStyle textStyle = new TextStyle(); 

    	textStyle.setColor(new RGB(255, 0, 0)); 

    	textStyle.setStyle(SWT.BOLD); 

    	return textStyle; 

	}



	/** 

     * Create the TextStyle for keywords 

     * @return the style for keywords 

     */ 

    public TextStyle keywordTextStyle_channel() { 

	TextStyle textStyle = new TextStyle(); 

	textStyle.setColor(new RGB(255, 0, 0)); 

	textStyle.setStyle(SWT.ITALIC); 

	return textStyle; 

    } 

} 
