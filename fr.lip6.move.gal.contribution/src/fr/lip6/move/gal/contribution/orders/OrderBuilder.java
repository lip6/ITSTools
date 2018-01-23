package fr.lip6.move.gal.contribution.orders;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.semantics.INextBuilder;

public class OrderBuilder {
	
	
	public List<String> buildOrder (Specification spec) {
		INextBuilder inb = INextBuilder.build(spec);
		
		return inb.getVariableNames();		
	}
	
	public void printOrder (String path, List<String> order) throws IOException {
		PrintWriter out = new PrintWriter( new BufferedOutputStream(new FileOutputStream(path)));
		out.println("#ORDER\n");
		for (String var : order) {
			out.println(var);
		}
		out.println("#END");
		out.flush();
		out.close();
	}
}
