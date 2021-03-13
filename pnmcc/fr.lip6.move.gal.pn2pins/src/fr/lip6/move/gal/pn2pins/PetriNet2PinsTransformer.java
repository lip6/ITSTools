package fr.lip6.move.gal.pn2pins;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.AtomicPropManager;
import fr.lip6.move.gal.structural.expr.CExpressionPrinter;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.util.IntMatrixCol;

public class PetriNet2PinsTransformer {

	private SparsePetriNet net;
	private NecessaryEnablingsolver nes;
	private boolean hasPartialOrder;

	private void buildBodyFile(String path) throws IOException {
		PrintWriter pw = new PrintWriter(path);

		if (!forSpot) {
			pw.println("#include <ltsmin/pins.h>");
			pw.println("#include <ltsmin/pins-util.h>");
			pw.println("#include <ltsmin/ltsmin-standard.h>");
			pw.println("#include <ltsmin/lts-type.h>");
			pw.println("#include \"model.h\"");
			pw.println("#define true 1");
			pw.println("#define false 0");

			pw.println("int initial [" + net.getPlaceCount() + "] ;");

			pw.println("int* get_initial_state() {");
			for (int i = 0, ie = net.getPlaceCount(); i < ie; i++) {
				pw.println("  // " + net.getPnames().get(i));
				pw.println("  initial [" + (i) + "] = " + net.getMarks().get(i) + ";");
			}
			pw.println("  return initial;");
			pw.println("}");

			
		}  else {
			pw.println("#include <cstddef>");
			pw.println("#include <cstring>");

			pw.println("extern \"C\" {");
		}

		pw.println("int get_state_variable_count() {");
		pw.println("  return " + net.getPlaceCount() + " ;");
		pw.println("}");




		if (forSpot) {
			pw.print("void get_initial_state(void* ss)\n");
			pw.print("{\n  int* s = (int*)ss;\n");
			for (int i = 0, ie = net.getPlaceCount(); i < ie; i++) {
				pw.println("  // " + net.getPnames().get(i));
				pw.println("  s[" + (i) + "] = " + net.getMarks().get(i) + ";");
			}
			pw.println("}\n");
			
			pw.print("const char* varnames[" + net.getPlaceCount() + "] = { ");
			for (int i=0, ie = net.getPlaceCount() ; i < ie ; i++) {
				String vname = net.getPnames().get(i);
				pw.print("\""+vname+"\"");
				if (i < ie -1) {
					pw.print(", ");
				}
			}
			pw.println("\n};");
			//		"extern \"C\"
			pw.print("const char* get_state_variable_name(int i)\n");
			pw.println("{\n  return varnames[i];\n}\n");
			
			pw.println("struct transition_info_t;\n");
			pw.println("typedef void (*TransitionCBSPOT)(void* ctx, transition_info_t* transition_info, int* dst);");


			// extern \"C\" 
			pw.println("int get_state_variable_type(int i)\n{\n  return 0;\n}\n");
			// extern \"C\"
			pw.println("int get_state_variable_type_count()\n{\n  return 1;\n}\n");
			// "extern \"C\"
			pw.println("const char* get_state_variable_type_name(int i)\n{\n  return \"int\";\n}\n");
			// "extern \"C\"
			pw.println("int get_state_variable_type_value_count(int i)\n{\n  return 0;\n}\n");
			// "extern \"C\
			pw.println("const char* get_state_variable_type_value(int a, int b)\n{\n  return \"\";\n}\n");
		}

		printDependencyMatrix(pw);

		printNextState(pw);
		if (forSpot) {
			pw.println("int get_successors(void* model, int* in, TransitionCBSPOT cb, void* ctx)\n{");
			pw.println("  int res=0;");
			pw.println("  state_t cur;");
			pw.println("  memcpy(&cur, in, "+ net.getPlaceCount()+" * sizeof(int));");
			for (int tid=0,tide=net.getTransitionCount(); tid<tide; ++tid) {
				pw.println("  if (transition"+tid+"(&cur)) {\n    cb (ctx, NULL, cur.state);");
				pw.println("    memcpy(&cur, in, "+ net.getPlaceCount()+" * sizeof(int));");
				pw.println("    ++res;");
				pw.println("  }");
			}
			pw.println("  return res;");
			pw.println("}");

			pw.println("} // extern C" );
		} else {
			printGB(pw);
		}
		pw.close();
	}

	private void buildHeader(String path) throws IOException {
		PrintWriter pw = new PrintWriter(path);
		pw.print(
				"#include <ltsmin/pins.h>\n"+
						"/**\n"+
						" * @brief calls callback for every successor state of src in transition group \"group\".\n"+
						" */\n"+
						"int next_state(void* model, int group, int *src, TransitionCB callback, void *arg);\n"+
						"\n"+
						"/**\n"+
						" * @brief returns the initial state.\n"+
						" */\n"+
						"int* get_initial_state();\n"+
						"\n"+
						"/**\n"+
						" * @brief returns the read dependency matrix.\n"+
						" */\n"+
						"int* read_matrix(int row);\n"+
						"\n"+
						"/**\n"+
						" * @brief returns the write dependency matrix.\n"+
						" */\n"+
						"int* write_matrix(int row);\n"+
						"\n"+
						"/**\n"+
						" * @brief returns the state label dependency matrix.\n"+
						" */\n"+
						"int* label_matrix(int row);\n"+
						"\n"+
						"/**\n"+
						" * @brief returns whether the state src satisfies state label \"label\".\n"+
						" */\n"+
						"int state_label(void* model, int label, int* src);\n"+
						"\n"+
						"/**\n"+
						" * @brief returns the number of transition groups.\n"+
						" */\n"+
						"int group_count();\n"+
						"\n"+
						"/**\n"+
						" * @brief returns the length of the state.\n"+
						" */\n"+
						"int get_state_variable_count();\n"+
						"\n"+
						"/**\n"+
						" * @brief returns the number of state labels.\n"+
						" */\n"+
						"int label_count();\n"
				);
		pw.flush();
		pw.close();
	}

	public int[] convertToLine(BitSet bs) {
		int card = bs.cardinality();
		int[] line = new int[card + 1];
		int index = 0;
		line[index++] = card;
		for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1)) {
			// operate on index i here
			line[index++] = i;
			if (i == Integer.MAX_VALUE) {
				break; // or (i+1) would overflow
			}
		}
		return line;
	}

	public BitSet convertToBitSet(int[] line) {
		BitSet b = new BitSet();
		for (int i = 0; i < line.length; i++) {
			if (line[i] == 1) {
				b.set(i);
			}
		}
		return b;
	}

	private void printDependencyMatrix(PrintWriter pw) {

		List<int[]> rm = new ArrayList<>();
		List<int[]> wm = new ArrayList<>();
		for (int tindex = 0, te = net.getTransitionCount(); tindex < te; tindex++) {
			SparseIntArray sum = SparseIntArray.sumProd(1, net.getFlowPT().getColumn(tindex), 1,
					net.getFlowTP().getColumn(tindex));
			int[] r = convertToLine(sum);
			rm.add(r);

			SparseIntArray effect = SparseIntArray.sumProd(-1, net.getFlowPT().getColumn(tindex), 1,
					net.getFlowTP().getColumn(tindex));
			int[] w = convertToLine(effect);
			wm.add(w);
		}

		printMatrix(pw, "rm", rm);
		pw.print("int* read_matrix(int row) {\n" + "  return rm[row];\n" + "}\n");

		printMatrix(pw, "wm", wm);

		pw.print("int* write_matrix(int row) {\n" + "  return wm[row];\n" + "}\n");

		printLabels(pw, rm, wm);
	}

	private int[] convertToLine(SparseIntArray bs) {
		int card = bs.size();
		int[] line = new int[card + 1];
		int index = 0;
		line[index++] = card;
		for (int i = 0, ie = bs.size(); i < ie; i++) {
			// operate on index i here
			line[index++] = bs.keyAt(i);
		}
		return line;
	}

	private void printGB(PrintWriter pw) {
		// set the name of this PINS plugin
		pw.println("char pins_plugin_name[] = \"GAL\";");

		pw.println("void pins_model_init(model_t m) {");

		pw.println("  // create the LTS type LTSmin will generate");
		pw.println("  lts_type_t ltstype=lts_type_create();");

		pw.println("  // set the length of the state");
		pw.println("  lts_type_set_state_length(ltstype, get_state_variable_count());");

		pw.println("  // add an int type for a state slot");
		pw.println("  int int_type = lts_type_add_type(ltstype, \"int\", NULL);");
		pw.println("  lts_type_set_format (ltstype, int_type, LTStypeDirect);");

		pw.println("  // set state name & type");
		int i = 0;
		for (String vname : net.getPnames()) {
			pw.println("  lts_type_set_state_name(ltstype," + i + ",\"" + vname + "\");");
			pw.println("  lts_type_set_state_typeno(ltstype," + i + ",int_type);");
			i++;
		}

		// edge label types : TODO
		pw.println("  // add an action type for edge labels");
		pw.println("  int action_type = lts_type_add_type(ltstype, \"action\", NULL);");
		pw.println("  lts_type_set_format (ltstype, action_type, LTStypeEnum);");

		pw.println("  lts_type_set_edge_label_count (ltstype, 1);");
		pw.println("  lts_type_set_edge_label_name(ltstype, 0, \"action\");");
		pw.println("  lts_type_set_edge_label_type(ltstype, 0, \"action\");");
		pw.println("  lts_type_set_edge_label_typeno(ltstype, 0, action_type);");

		// state label types : TODO

		pw.println("  // add a bool type for state labels");
		pw.println("  int bool_type = lts_type_put_type (ltstype, \"boolean\", LTStypeBool, NULL);");

		pw.println("  lts_type_set_state_label_count (ltstype, label_count());");
		pw.println("  for (int i =0; i < label_count() ; i++) {");
		pw.println("    lts_type_set_state_label_typeno (ltstype, i, bool_type);");
		pw.println("    lts_type_set_state_label_name (ltstype, i, labnames[i]);");
		pw.println("  }");

		// done with ltstype
		pw.println("  lts_type_validate(ltstype);");

		// make sure to set the lts-type before anything else in the GB
		pw.println("  GBsetLTStype(m, ltstype);");

		// setting all values for all non direct types
		for (int tindex = 0; tindex < net.getTransitionCount(); tindex++) {
			pw.println("  pins_chunk_put(m, action_type, chunk_str(\"tr" + tindex + "\"));");
		}

		// pw.println(" GBchunkPut(m, bool_type, chunk_str(LTSMIN_VALUE_BOOL_FALSE));");
		// pw.println(" GBchunkPut(m, bool_type, chunk_str(LTSMIN_VALUE_BOOL_TRUE));");

		// set state variable values for initial state
		pw.println("  GBsetInitialState(m, get_initial_state());");

		// set function pointer for the next-state function
		pw.println("  GBsetNextStateLong(m, (next_method_grey_t) next_state);");

		// set function pointer for the label evaluation function
		pw.println("  GBsetStateLabelLong(m, (get_label_method_t) state_label);");

		// create combined matrix
		pw.println("  matrix_t *cm = malloc(sizeof(matrix_t));");
		pw.println("  dm_create(cm, group_count(), get_state_variable_count());");

		// set the read dependency matrix
		pw.println("  matrix_t *rm = malloc(sizeof(matrix_t));");
		pw.println("  dm_create(rm, group_count(), get_state_variable_count());");
		pw.println("  for (int i = 0; i < group_count(); i++) {");
		pw.println("    int sz = read_matrix(i)[0];");
		pw.println("    for (int j = 1; j < sz + 1; j++) {");
		pw.println("      int indj = read_matrix(i)[j];");
		pw.println("      dm_set(cm, i, indj);");
		pw.println("      dm_set(rm, i, indj);");
		pw.println("    }");
		pw.println("  }");
		pw.println("  GBsetDMInfoRead(m, rm);");

		// set the write dependency matrix
		pw.println("  matrix_t *wm = malloc(sizeof(matrix_t));");
		pw.println("  dm_create(wm, group_count(), get_state_variable_count());");
		pw.println("  for (int i = 0; i < group_count(); i++) {");
		pw.println("    int sz = write_matrix(i)[0];");
		pw.println("    for (int j = 1; j < sz + 1; j++) {");
		pw.println("      int indj = write_matrix(i)[j];");
		pw.println("      dm_set(cm, i, indj);");
		pw.println("      dm_set(wm, i, indj);");
		pw.println("    }");
		pw.println("  }");
		pw.println("  GBsetDMInfoMustWrite(m, wm);");

		// set the combined matrix
		pw.println("  GBsetDMInfo(m, cm);");

		//	    // set the label dependency matrix
		pw.println("  matrix_t *lm = malloc(sizeof(matrix_t));");
		pw.println("  dm_create(lm, label_count(), get_state_variable_count());");
		pw.println("  for (int i = 0; i < label_count(); i++) {\n");
		pw.println("    int sz = label_matrix(i)[0];");
		pw.println("    for (int j = 1; j < sz + 1; j++) {");
		pw.println(
				"      dm_set(lm, i, label_matrix(i)[j]);\n" + "    }\n" + "  }\n" + "  GBsetStateLabelInfo(m, lm);");

		// set guards
		pw.println("  GBsetGuardsInfo(m,(guard_t**) &guardsPerTrans);");

		pw.println("  int sl_size = label_count();");
		pw.println("  int nguards = " + net.getTransitionCount() + ";");
		// set the label group implementation
		pw.println("  sl_group_t* sl_group_all = malloc(sizeof(sl_group_t) + sl_size * sizeof(int));");
		pw.println("  sl_group_all->count = sl_size;");
		pw.println("  for(int i=0; i < sl_group_all->count; i++) sl_group_all->sl_idx[i] = i;");
		pw.println("  GBsetStateLabelGroupInfo(m, GB_SL_ALL, sl_group_all);");
		pw.println("  if (nguards > 0) {");
		pw.println("    sl_group_t* sl_group_guards = malloc(sizeof(sl_group_t) + nguards * sizeof(int));");
		pw.println("    sl_group_guards->count = nguards;");
		pw.println("    for(int i=0; i < sl_group_guards->count; i++) sl_group_guards->sl_idx[i] = i;");
		pw.println("    GBsetStateLabelGroupInfo(m, GB_SL_GUARDS, sl_group_guards);");
		pw.println("  }");
		// get state labels
		pw.println("  GBsetStateLabelsGroup(m, sl_group);");

		if (hasPartialOrder) {
			// NES
			pw.println("  int ngroups = group_count();");
			pw.println("  matrix_t *gnes_info = malloc(sizeof(matrix_t));");
			pw.println("  dm_create(gnes_info, sl_size, ngroups);");
			pw.println("  for(int i = 0; i < sl_size; i++) {");
			pw.println("    int sz = gal_get_label_nes_matrix(i)[0];");
			pw.println("    for (int j = 1; j < sz + 1; j++) {");
			pw.println("      dm_set(gnes_info, i, gal_get_label_nes_matrix(i)[j]);\n");
			pw.println("    }");
			pw.println("  }");
			pw.println("  GBsetGuardNESInfo(m, gnes_info);");

			// NDS : set guard necessary disabling set info
			pw.println("  matrix_t *gnds_info = malloc(sizeof(matrix_t));");
			pw.println("  dm_create(gnds_info, sl_size, ngroups);");
			pw.println("  for(int i = 0; i < sl_size; i++) {");
			pw.println("    int sz = gal_get_label_nds_matrix(i)[0];");
			pw.println("    for (int j = 1; j < sz + 1; j++) {");
			pw.println("      dm_set(gnds_info, i, gal_get_label_nds_matrix(i)[j]);\n");
			pw.println("    }");
			pw.println("  }");
			pw.println("  GBsetGuardNDSInfo(m, gnds_info);");

			// Co-enabling
			pw.println("  matrix_t *coEnab = malloc(sizeof(matrix_t));");
			pw.println("  dm_create(coEnab, group_count(), group_count());");
			pw.println("  for (int i = 0; i < group_count(); i++) {\n");
			pw.println("    int sz = coEnab_matrix(i)[0];");
			pw.println("    for (int j = 1; j < sz + 1; j++) {");
			pw.println("      dm_set(coEnab, i, coEnab_matrix(i)[j]);\n");
			pw.println("    }");
			pw.println("  }");
			pw.println("  GBsetGuardCoEnabledInfo(m, coEnab);");

			// DNA
			pw.println("  matrix_t *dna = malloc(sizeof(matrix_t));");
			pw.println("  dm_create(dna, group_count(), group_count());");
			pw.println("  for (int i = 0; i < group_count(); i++) {\n");
			pw.println("    int sz = dna_matrix(i)[0];");
			pw.println("    for (int j = 1; j < sz + 1; j++) {");
			pw.println("      dm_set(dna, i, dna_matrix(i)[j]);\n");
			pw.println("    }");
			pw.println("  }");
			pw.println("  GBsetDoNotAccordInfo(m, dna);");

		}

		pw.println("}");
	}

	private void printLabels(PrintWriter pw, List<int[]> rm, List<int[]> wm) {

		List<int[]> guards = new ArrayList<>();

		for (int i = 0; i < net.getTransitionCount(); i++) {
			int[] gl = new int[2];
			gl[0] = 1;
			gl[1] = i;
			guards.add(gl);
		}

		pw.println("int * guardsPerTrans [" + guards.size() + "] =  {");
		for (Iterator<int[]> it = guards.iterator(); it.hasNext();) {
			int[] line = it.next();

			pw.print("  ((int[])");
			printArray(pw, line);
			pw.print(")");
			if (it.hasNext()) {
				pw.print(",");
			}
			pw.println();
		}
		pw.println("};");

		pw.println("int group_count() {");
		pw.println("  return " + net.getTransitionCount() + " ;");
		pw.println("}");

		pw.println("int label_count() {");
		pw.println("  return " + (net.getTransitionCount() + atoms.size()) + " ;");
		pw.println("}");

		pw.println("char * labnames [" + (net.getTransitionCount() + atoms.size()) + "] = {");
		boolean first = true;
		for (int i = 0; i < net.getTransitionCount(); i++) {
			if (!first) {
				pw.print(",");
			} else {
				first = false;
			}
			pw.print("  \"" + "enabled" + i + "\"");
			pw.println();
		}
		for (AtomicProp atom : atoms.getAtoms()) {
			if (!first) {
				pw.print(",");
			} else {
				first = false;
			}
			pw.print("  \"LTLAP" + atom.getName() + "\"");
			pw.println();
		}
		pw.println("};");

		List<int[]> lm = new ArrayList<>(atoms.size());
		for (AtomicProp ap : atoms.getAtoms()) {
			BitSet lr = new BitSet();
			SparsePetriNet.addSupport(ap.getExpression(), lr);
			lm.add(convertToLine(lr));
		}
		if (atoms.size() > 0)
			printMatrix(pw, "lm", lm);
		pw.print("int* label_matrix(int row) {\n" + "  if (row < " + net.getTransitionCount() + ") return rm[row];\n");
		if (atoms.size() > 0) {
			pw.print("  else return lm[row-" + net.getTransitionCount() + "];\n");
		}
		pw.print("}\n");

		if (!hasPartialOrder) {
			return;
		}
		try {
			nes.init(net);

			IntMatrixCol mayEnable = nes.computeAblingMatrix(false);
			// short scopes for less memory peaks
			{
				// invert the logic for ltsmin
				// List<int[]> mayEnableSparse = mayEnable.stream().map(l ->
				// convertToLine(convertToBitSet(l))).collect(Collectors.toList());
				printMatrix(pw, "mayDisable", mayEnable);
			}

			{
				IntMatrixCol mayDisable = nes.computeAblingMatrix(true);
				// List<int[]> mayDisableSparse = mayDisable.stream().map(l ->
				// convertToLine(convertToBitSet(l))).collect(Collectors.toList());
				// logic is inverted
				printMatrix(pw, "mayEnable", mayDisable);
			}

			{
				List<int[]> ones = new ArrayList<>();
				int[] oneA = new int[net.getTransitionCount() + 1];
				oneA[0] = net.getTransitionCount();
				for (int i = 1; i < oneA.length; i++) {
					oneA[i] = i - 1;
				}
				ones.add(oneA);
				printMatrix(pw, "allOnes", ones);
			}

			{
				IntMatrixCol enab = new IntMatrixCol(net.getTransitionCount(), 0);
				IntMatrixCol disab = new IntMatrixCol(net.getTransitionCount(), 0);
				for (AtomicProp ap : atoms.getAtoms()) {
					SparseIntArray[] lines = nes.computeAblingsForPredicate(ap.getExpression());
					disab.appendColumn(lines[0]);
					enab.appendColumn(lines[1]);
				}
				printMatrix(pw, "mayDisableAtom", enab);
				printMatrix(pw, "mayEnableAtom", enab);
			}

			pw.println("const int* gal_get_label_nes_matrix(int g) {");
			pw.println(" if (g <" + net.getTransitionCount() + ") return mayEnable[g];");
			pw.println(" return mayEnableAtom[g-" + net.getTransitionCount() + "];");
			pw.println("}");

			pw.println("const int* gal_get_label_nds_matrix(int g) {");
			pw.println(" if (g <" + net.getTransitionCount() + ") return mayDisable[g];");
			pw.println(" return mayDisableAtom[g-" + net.getTransitionCount() + "];");
			pw.println("}");

			IntMatrixCol coEnabled = nes.computeCoEnablingMatrix();
			printMatrix(pw, "coenabled", coEnabled);

			pw.println("const int* coEnab_matrix(int g) {");
			pw.println(" return coenabled[g];");
			pw.println("}");

			IntMatrixCol doNotAccord = nes.computeDoNotAccord(mayEnable);			
			printMatrix(pw, "dna", doNotAccord);

			pw.println("const int* dna_matrix(int g) {");
			pw.println(" return dna[g];");
			pw.println("}");

		} catch (Exception e) {
			System.err.println("Skipping mayMatrices nes/nds " + e.getMessage());
			e.printStackTrace();

			hasPartialOrder = false;
			//			pw.println("const int* gal_get_label_nes_matrix(int g) {");
			//			pw.println(" return lm[g];");
			//			pw.println("}");
			//
			//			pw.println("const int* gal_get_label_nds_matrix(int g) {");
			//			pw.println(" return lm[g];");
			//			pw.println("}");
		}

	}

	public void printMatrix(PrintWriter pw, String matrixName, IntMatrixCol matrix) {
		pw.println("int *" + matrixName + "[" + matrix.getColumnCount() + "] = {");
		for (int i = 0; i < matrix.getColumnCount(); i++) {
			SparseIntArray line = matrix.getColumn(i);
			pw.print("  ((int[])");
			printArray(pw, line);
			pw.print(")");
			if (i < matrix.getColumnCount() - 1) {
				pw.print(",");
			}
			pw.print("\n");
		}
		pw.println("};");
	}

	private void printArray(PrintWriter pw, SparseIntArray line) {
		int sz = line.size();
		pw.print("{");
		pw.print(sz);
		if (sz != 0) {
			pw.print(",");
		}
		for (int i = 0; i < sz; i++) {
			pw.print(line.keyAt(i));
			if (i < sz - 1)
				pw.print(",");
		}
		pw.print("}");
	}

	private void printMatrix(PrintWriter pw, String matrixName, List<int[]> matrix) {
		pw.println("int *" + matrixName + "[" + matrix.size() + "] = {");
		for (Iterator<int[]> it = matrix.iterator(); it.hasNext();) {
			int[] line = it.next();
			pw.print("  ((int[])");
			printArray(pw, line);
			pw.print(")");
			if (it.hasNext()) {
				pw.print(",");
			}
			pw.print("\n");
		}
		pw.println("};");
	}

	private void printArray(PrintWriter pw, int[] line) {
		pw.print("{");
		for (int i = 0; i < line.length; i++) {
			pw.print(line[i]);
			if (i < line.length - 1)
				pw.print(",");
		}
		pw.print("}");
	}

	private void printNextState(PrintWriter pw) {

		pw.append("typedef struct state {\n");
		pw.append("  int state [" + net.getPlaceCount() + "];\n");
		pw.append("} state_t ;\n");

		for (int ti = 0, tie = net.getTransitionCount(); ti < tie; ti++) {
			SparseIntArray pt = net.getFlowPT().getColumn(ti);
			SparseIntArray tp = net.getFlowTP().getColumn(ti);

			pw.append("static inline int transition" + ti + " (state_t * current) {\n");

			if (pt.size() > 0) {
				pw.append("  if (" + buildGuard(pt, "current->state") + ") { ");
			}
			SparseIntArray eff = SparseIntArray.sumProd(-1, pt, 1, tp);
			for (int i = 0, ie = eff.size(); i < ie; i++) {
				pw.append("   current->state[" + eff.keyAt(i) + "] += " + eff.valueAt(i) + ";");
			}
			pw.append("   return true;\n");
			pw.append("  } else { return false; }\n");
			pw.append("}\n");
		}
		if (!forSpot) {
			pw.println("int next_state(void* model, int group, int *src, TransitionCB callback, void *arg) {");

			pw.println("  state_t cur ;\n");
			pw.println("  memcpy( cur.state, src,  sizeof(int)* " + net.getPlaceCount() + ");\n");

			pw.println("  // provide transition labels and group number of transition.");
			pw.println("  int action[1];");
			// use the same identifier for group and action
			pw.println("  action[0] = group;");
			pw.println("  transition_info_t transition_info = { action, group };");

			pw.println("  int nbsucc = 0;");
			pw.println("  switch (group) {");
			for (int tindex = 0; tindex < net.getTransitionCount(); tindex++) {
				pw.println("  case " + tindex + " : ");
				pw.println("     if (transition" + tindex + "(&cur)) {");
				pw.println("       nbsucc++; ");
				pw.println("       callback(arg, &transition_info, cur.state, wm[group]);");
				// pw.println(" memcpy(& cur->state, src, sizeof(int)*
				// "+net.getPlaceCount()+");");
				pw.println("     }");
				pw.println("     break;");
			}
			pw.println("  default : return 0 ;");
			pw.println("  } // end switch(group) ");
			pw.println("  return nbsucc; // return number of successors");
			pw.println("}");

			CExpressionPrinter printer = new CExpressionPrinter(pw, "src");
			/////// Handle Labels similarly
			pw.println("int state_label(void* model, int label, int* src) {");
			// labels
			pw.println("  if (label >= " + net.getTransitionCount() + ") {");
			pw.println("    switch (label) {");
			List<AtomicProp> alist = new ArrayList<>(atoms.getAtoms());
			for (int tindex = net.getTransitionCount(); tindex < net.getTransitionCount() + atoms.size(); tindex++) {
				pw.println("      case " + tindex + " : ");
				pw.append("        return ");
				alist.get(tindex - net.getTransitionCount()).getExpression().accept(printer);
				pw.println(";");
			}
			pw.println("    }");
			pw.println("  }");

			// guards : reuse firing function

			pw.println("  switch (label) {");
			for (int tindex = 0, tie = net.getTransitionCount(); tindex < tie; tindex++) {
				pw.println("  case " + tindex + " : ");
				pw.println("     return " + buildGuard(net.getFlowPT().getColumn(tindex), "src") + ";");
			}
			pw.println("  default : return 0 ;");
			pw.println("  } // end switch(group) ");

			pw.println("}");

			pw.println("int state_label_many(void* model, int * src, int * label, int guards_only) {");
			pw.println("  (void)model;");
			for (int tindex = 0; tindex < net.getTransitionCount(); tindex++) {
				pw.println("  label[" + tindex + "] = " + buildGuard(net.getFlowPT().getColumn(tindex), "src") + ";");
			}
			pw.println("  if (guards_only) return 0; ");
			for (int tindex = net.getTransitionCount(); tindex < net.getTransitionCount() + atoms.size(); tindex++) {
				pw.println("  label[" + tindex + "] = ");
				alist.get(tindex - net.getTransitionCount()).getExpression().accept(printer);
				pw.println(" ;");
			}
			pw.println("  return 0; // return number of successors");
			pw.println("}");

			pw.println("void sl_group (model_t model, sl_group_enum_t group, int*src, int *label)");
			pw.println("  {");
			pw.println("  state_label_many (model, src, label, group == GB_SL_GUARDS);");
			pw.println("  (void) group; // Both groups overlap, and start at index 0!");
			pw.println("  }");

			pw.println("void sl_all (model_t model, int*src, int *label)");
			pw.println("  {");
			pw.println("  state_label_many (model, src, label, 0);");
			pw.println("  }");
		} else  {
			CExpressionPrinter printer = new CExpressionPrinter(pw, "src");
			// For Spot : generate a state labeling function just for APs
			pw.println("int eval_state_label(int label, const int* src) {");
			// labels
			pw.println("    switch (label) {");
			Iterator<AtomicProp> ap = atoms.getAtoms().iterator();
			for (int tindex = 0, te = atoms.size() ; tindex < te ; tindex++) {
				pw.println("      case " + tindex + " : ");
				pw.append("        return ");
				ap.next().getExpression().accept(printer);
				pw.println(";");
			}
			pw.println("    }");
			pw.println("  }");
			//int (*get_state_size)();
		    //const char* (*get_state_variable_name)(int var);
		    
		    pw.println("int get_state_label_count() { return "+atoms.size() + ";}");
			
		    pw.println("const char * labelnames ["+atoms.size()+"] = {");
		    int ind = 0;
		    for (AtomicProp a : atoms.getAtoms()) {
		    	pw.print("  \"LTLAP" + a.getName()+ "\"");
		    	if (ind < atoms.getAtoms().size()) {
		    		pw.print(",");
		    	}
		    	pw.println();
	    		++ind;
		    }
		    pw.println("};");
			pw.println("const char * get_state_label_name(int label) { return labelnames[label] ;} ");
		
		}
	}

	public String buildGuard(SparseIntArray pt, String prefix) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, ie = pt.size(); i < ie; i++) {
			sb.append(prefix + "[" + pt.keyAt(i) + "] >=" + pt.valueAt(i));
			if (i < ie - 1) {
				sb.append(" && ");
			}
		}
		String guard = sb.toString();
		return guard;
	}

	public String printLTLProperty(Property prop) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(baos);
		PropertyPrinter pp = new PropertyPrinter(pw, "src", forSpot);
		atoms.getAPformula(prop.getName()).accept(pp);
		pw.close();
		return baos.toString();
	}

	private AtomicPropManager atoms = new AtomicPropManager();
	private boolean isSafe;
	private boolean forSpot;

	public void transform(SparsePetriNet spec, String cwd, boolean withPorMatrix, boolean isSafe, boolean forSpot) {
		this.isSafe = isSafe;
		this.forSpot = forSpot;
		//		if ( spec.getMain() instanceof GALTypeDeclaration ) {
		//			Logger.getLogger("fr.lip6.move.gal").fine("detecting pure GAL");
		//		} else {
		//			Logger.getLogger("fr.lip6.move.gal").fine("Error transformation does not support hierarchy yet.");
		//			return;
		//		}
		long time = System.currentTimeMillis();

		net = spec;

		if (spec.getTransitionCount() > 1500 && withPorMatrix) {
			withPorMatrix = false;
			Logger.getLogger("fr.lip6.move.gal").info("Too many transitions (" + net.getTransitionCount()
			+ ") to apply POR reductions. Disabling POR matrices.");
		}

		Map<String, Expression> pmap = atoms.loadAtomicProps(spec.getProperties());

		hasPartialOrder = withPorMatrix;
		nes = new NecessaryEnablingsolver(isSafe);
		try {

			buildBodyFile(cwd + "/model.c");
			if (!forSpot)
				buildHeader(cwd + "/model.h");

			Logger.getLogger("fr.lip6.move.gal").info("Built C files in " + (System.currentTimeMillis() - time)
					+ "ms conformant to PINS "+(forSpot?"(SPOT variant)":"(ltsmin variant)")+"in folder :" + cwd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
