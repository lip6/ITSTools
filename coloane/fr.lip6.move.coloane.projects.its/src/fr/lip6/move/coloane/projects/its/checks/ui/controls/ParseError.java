package fr.lip6.move.coloane.projects.its.checks.ui.controls;

import java.util.List;

public class ParseError {

	private String msg;
	private int charAt;
	private int len;
	private List<String> suggs;

	public ParseError(String msg, int charAt, int len, List<String> suggs) {
		this.msg = msg;
		this.charAt = charAt;
		this.len = len;
		this.suggs = suggs;
	}
	
	public String getMsg() {
		return msg;
	}
	public int getCharAt() {
		return charAt;
	}
	public int getLen() {
		return len;
	}
	
	public List<String> getSuggs() {
		return suggs;
	}
}
