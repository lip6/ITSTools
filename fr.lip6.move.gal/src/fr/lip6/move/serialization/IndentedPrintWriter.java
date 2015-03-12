package fr.lip6.move.serialization;

import java.io.PrintWriter;

public class IndentedPrintWriter {

	private static final String SPACE = "  ";
	private PrintWriter pw;
	private int indent =0;

	public IndentedPrintWriter(PrintWriter printWriter) {
		this.pw = printWriter;
	}

	public void incIndent() {
		indent++;
	}
	public void decIndent() {
		indent--;
	}
	
	public void print(char c) {
		pw.print(c);
	}

	public void print(int i) {
		pw.print(i);
	}

	public void print(long l) {
		pw.print(l);
	}

	public void print(String str) {
		pw.print(str);
	}

	public void println() {
		pw.println();
	}

	public void println(String line) {
		indent();
		pw.println(line);
	}

	public void close() {
		pw.close();
	}

	public void flush() {
		pw.flush();
	}

	public void indent() {
		for (int i = 0 ; i < indent  ; i++) {
			pw.print(SPACE);
		}
	}
	
}
