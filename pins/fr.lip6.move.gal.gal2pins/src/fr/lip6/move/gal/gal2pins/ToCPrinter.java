package fr.lip6.move.gal.gal2pins;

import java.io.PrintWriter;

import fr.lip6.move.gal.semantics.Alternative;
import fr.lip6.move.gal.semantics.Assign;
import fr.lip6.move.gal.semantics.ExpressionPrinter;
import fr.lip6.move.gal.semantics.INext;
import fr.lip6.move.gal.semantics.NextVisitor;
import fr.lip6.move.gal.semantics.Predicate;
import fr.lip6.move.gal.semantics.Sequence;

/**
 * Visit an INext and produce C functions that simulate their effects.
 * To support Alternative, the API uses a list of states state_t*, just a basic struct with a pointer next.
 * Signature : 2^S -> 2^S
 * state_t * nextStatement"+(index)+" (state_t * current)
 * Predicates filter states, Assign updates, Alternatives copy them into branches, Sequence chains calls 
 * Without alternatives, this 2^S will remain a singleton list (or the empty list if filtered) and never get copies, so is still ok in efficiency.
 * The visit method prints the appropriate function (recursively descending into effects) and returns an integer identifying the relevant nextState function to be called to get that effect.
 * TODO : (optimize) use a free node list, stack alloc etc some C tricks... to replace malloc/free system calls 
 * TODO : (optimize) (harder) find a more efficient way to callback at the right moment despite Alternative.
 * TODO : (optimize) Add an index + unique table, no reason to export the same statement twice. gcc probably deals with it though. Needs hash/equals at INext level which is currently unavailable.
 * @author ythierry
 *
 */
public class ToCPrinter implements NextVisitor<Integer> {
	
	private PrintWriter pw;
	private int index = 0;
	
	public ToCPrinter(PrintWriter pw) {
		this.pw = pw;
	}

	public int getIndex() {
		return index;
	}
	
	@Override
	public Integer visit (Assign ass) {
		pw.append("static inline state_t * nextStatement"+(index++)+" (state_t * current) {\n");
		pw.append("  for (state_t * it = current; it ; it = it->next) {\n");
		pw.append("    int * dst = it->state;  // the next state values\n");
		pw.append(ExpressionPrinter.print(ass.getAssignment(),"dst", ass.getIndexer()));
		pw.append("  }\n");
		pw.append("  return current;\n");
		pw.append("}\n");		
		return index-1;
	}

	@Override
	public Integer visit(Predicate pred) {
		pw.append("static inline state_t * nextStatement"+(index++)+" (state_t * current) {\n");
		pw.append("  state_t ** pcur = & current;\n");
		pw.append("  state_t * next = NULL;\n");
		pw.append("  for (state_t * it = current; it ; it = next) {\n");
		pw.append("    int * src = it->state;\n");
		pw.append("    if (");
		pw.append(         ExpressionPrinter.print(pred.getGuard(),"src", pred.getIndexer()));
		pw.append(         ") {\n");
		pw.append("      *pcur = it; pcur = & it->next ; next = it->next ; *pcur = NULL ;  \n");		
		pw.append("    } else {\n");
		pw.append("      next = it->next;\n");		
		pw.append("      free(it);\n");
		pw.append("    }\n");
		pw.append("  }\n");
		pw.append("  *pcur=NULL;\n");
		pw.append("  return current;\n");
		pw.append("}\n");
		return index-1;
	}

	@Override
	public Integer visit(Alternative alt) {
		int curindex = index ++;
		int max = alt.getAlternatives().size();
		int [] indexes = new int[max];
		int ii=0;
		for (INext act : alt.getAlternatives()) {
			indexes[ii++] = act.accept(this);
		}

		pw.append("static inline state_t * nextStatement"+curindex+" (state_t * current) {\n");
		pw.append("  state_t * copy = NULL;\n");
		pw.append("  state_t * result = NULL;\n");		
		pw.append("  state_t ** it;\n");
		for (int i=0 ; i < max ; i++) {
			pw.append("  /* Alternative "+ i +" */\n");
			pw.append("  copy = NULL; it = &copy; \n");
			pw.append("  for (state_t * jt = current ; jt ; jt = jt->next)  \n"
					+ "    { state_t * new = malloc(sizeof(state_t)); \n * new = *jt ; new->next = NULL ; \n  *it = new ; \n  it = & new->next; }\n");
			pw.append("  copy = nextStatement"+ (indexes[i])+"(copy);\n");
			pw.append("  for (it = &copy ; *it ; it = & (*it)->next) /*NOP*/;\n");
			pw.append("  *it = result; result = copy;\n\n");	
		}
		pw.append("  for (state_t * jt=current ; jt ; ) { state_t * tmp = jt; jt = jt->next ; free(tmp);  }\n");
		pw.append("  return result;\n");
		pw.append("}\n");
		return curindex;
	}
	

	@Override
	public Integer visit(Sequence seq) {
		int curindex = index ++;
		int max = seq.getActions().size();
		int [] indexes = new int[max];
		int ii=0;
		for (INext act : seq.getActions()) {
			indexes[ii++] = act.accept(this);
		}
		pw.append("static inline state_t * nextStatement"+curindex+" (state_t * current) {\n");
		for (int i = 0 ;  i < max ; i++) {
			pw.append("  current = nextStatement"+indexes[i]+"(current);\n");
		}
		pw.append("  return current;\n");
		pw.append("}\n");
		return curindex;
	}

}
