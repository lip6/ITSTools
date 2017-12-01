package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import fr.lip6.move.gal.ArrayInstanceDeclaration;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.InstanceDeclaration;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.util.GalSwitch;

/**
 * Recursively analyze and build the semantics (INext) for a composite type.
 * Please use factory operation in INextBuilder to instantiate.
 * 
 * @author ythierry
 *
 */
public class CompositeNextBuilder extends DeterministicNextBuilder implements INextBuilder {

	private List<INextBuilder> instances = new ArrayList<INextBuilder>();
	private Map<String, Integer> instanceIndex = new HashMap<>();
	private List<String> varNames = new ArrayList<>();

	private int size = 0;
	private Map<String, List<Synchronization>> labMap;

	public CompositeNextBuilder(CompositeTypeDeclaration ctd, int offset) {
		int iid = 0;
		for (InstanceDecl inst : ctd.getInstances()) {
			if (inst instanceof InstanceDeclaration) {
				InstanceDeclaration id = (InstanceDeclaration) inst;
				INextBuilder nb = null;
				if (id.getType() instanceof GALTypeDeclaration) {
					nb = new GalNextBuilder((GALTypeDeclaration) id.getType(), offset);
				} else if (id.getType() instanceof CompositeTypeDeclaration) {
					nb = new CompositeNextBuilder((CompositeTypeDeclaration) id.getType(), offset);
				}
				offset += nb.size();
				size += nb.size();
				String name = id.getName();
				instanceIndex.put(name, iid++);
				nb.getVariableNames().forEach(e -> varNames.add(name + ":" + e));
				instances.add(nb);
			} else if (inst instanceof ArrayInstanceDeclaration) {
				ArrayInstanceDeclaration aid = (ArrayInstanceDeclaration) inst;
				int sz = Instantiator.evalConst(aid.getSize());
				instanceIndex.put(aid.getName(), iid);

				for (int i = 0; i < sz; i++) {
					INextBuilder nb = null;
					if (aid.getType() instanceof GALTypeDeclaration) {
						nb = new GalNextBuilder((GALTypeDeclaration) aid.getType(), offset);
					} else if (aid.getType() instanceof CompositeTypeDeclaration) {
						nb = new CompositeNextBuilder((CompositeTypeDeclaration) aid.getType(), offset);
					}
					offset += nb.size();
					size += nb.size();
					String name = aid.getName() + "[" + i + "]";
					instanceIndex.put(name, iid++);
					instances.add(nb);
					nb.getVariableNames().forEach(e -> varNames.add(name + ":" + e));
				}
			}
		}
		labMap = ctd.getSynchronizations().stream()
				.collect(Collectors.groupingBy(t -> t.getLabel() != null ? t.getLabel().getName() : ""));
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<INext> getNextForLabel(String lab) {
		List<INext> total = new ArrayList<INext>();
		List<Synchronization> labs = labMap.get(lab);
		if (labs == null) {
			Logger.getLogger("fr.lip6.move.gal").warning("No label :" + lab + ": found for call within composite type");
			return total;
		}
		for (Synchronization t : labs) {
			total.add(new CompositeStatementTranslator().doSwitch(t));
		}
		if ("".equals(lab)) {
			// add private nested behaviors
			for (INextBuilder nb : instances) {
				total.addAll(nb.getNextForLabel(lab));
			}
		}
		return total;
	}

	class CompositeStatementTranslator extends GalSwitch<INext> {

		@Override
		public INext caseSynchronization(Synchronization sync) {
			List<INext> full = new ArrayList<>(sync.getActions().size());
			for (Statement st : sync.getActions()) {
				full.add(doSwitch(st));
			}
			return Sequence.seq(full);
		}

		@Override
		public INext caseSelfCall(SelfCall call) {
			return Alternative.alt(getNextForLabel(call.getLabel().getName()));
		}

		@Override
		public INext caseInstanceCall(InstanceCall call) {
			int index = instanceIndex.get(call.getInstance().getRef().getName());
			if (call.getInstance().getIndex() != null) {
				index += Instantiator.evalConst(call.getInstance().getIndex());
			}
			INextBuilder nb = instances.get(index);

			return Alternative.alt(nb.getNextForLabel(call.getLabel().getName()));
		}

	}

	private List<Integer> init = null;

	@Override
	public List<Integer> getInitial() {
		if (init == null) {
			init = new ArrayList<>();
			for (INextBuilder nb : instances) {
				init.addAll(nb.getInitial());
			}
		}
		return init;
	}

	@Override
	public int getIndex(Reference ref) {
		if (ref instanceof QualifiedReference) {
			QualifiedReference qref = (QualifiedReference) ref;
			VariableReference vref = qref.getQualifier();
			int ind = instanceIndex.get(vref.getRef().getName());
			if (vref.getIndex() != null) {
				ind += Instantiator.evalConst(vref.getIndex());
			}
			return instances.get(ind).getIndex(qref.getNext());
		}
		throw new UnsupportedOperationException("Composite should use qualified access to variables in " + ref);
	}

	@Override
	public List<String> getVariableNames() {
		return varNames;
	}
}
