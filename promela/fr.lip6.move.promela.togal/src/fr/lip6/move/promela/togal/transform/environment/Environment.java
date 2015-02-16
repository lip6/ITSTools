package fr.lip6.move.promela.togal.transform.environment;

import static fr.lip6.move.promela.togal.utils.GALUtils.makeArrayAccess;
import static fr.lip6.move.promela.togal.utils.GALUtils.makeRef;
import static fr.lip6.move.promela.togal.utils.TransfoUtils.illegal;
import static fr.lip6.move.promela.togal.utils.TransfoUtils.unsupported;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.promela.promela.AtomicRef; 
import fr.lip6.move.promela.promela.ChanVariable;
import fr.lip6.move.promela.promela.MTypeSymbol;
import fr.lip6.move.promela.promela.MemVariable;
import fr.lip6.move.promela.promela.Reference;
import fr.lip6.move.promela.promela.TabRef;
import fr.lip6.move.promela.togal.transform.Converter;
import fr.lip6.move.promela.togal.transform.representations.ChannelRepresentation;

public class Environment {

	final GlobalEnvironment globals;
	LocalEnvironment locals;

	public Environment(GlobalEnvironment _globals) {
		this.globals = _globals;
	}

	public void setLocal(LocalEnvironment l) {
		locals = l;
	}

	public void unsetLocal() {
		locals = null;
	}

	// TODO Adapt for real var!
	// get Super Type
	// public VarAccess getVar(MemVariable m) {
	// if(m.isArray()) return getArray(m);
	// else return getAtomic(m);
	// }

	public ArrayPrefix getArray(AtomicRef r) {

		if (r.getRef() instanceof MemVariable) {
			MemVariable v = (MemVariable) r.getRef();
			if (v.isArray())
				return getArray(v);
		}

		throw illegal("This is not an Array! " + r.getRef().getName());
	}

	public VariableReference getArrayAccess(MemVariable m, IntExpression i) {
		return makeArrayAccess(getArray(m), i); // CHECK
	}

	// public VarAccess getArrayAccess(Reference r) {
	// //CHECK REFACTOR var access
	// TabRef tr = (TabRef) r.getTargets().get(0);
	// //Need Converter....
	// return makeArrayAccess(getArray(r), conv.convert(tr.getIndex()) );
	// HERE: maybe converter Singleton...
	// }

	public ArrayPrefix getArray(MemVariable m) {
		// TODO: check array!
		ArrayPrefix ap;
		if (locals != null) {
			ap = locals.getArray(m);
			if (ap != null)
				return ap;
		}
		ap = globals.getArray(m);
		if (ap != null)
			return ap;
		throw illegal("Variable " + m.getName() + " n'existe pas!");
	}

	public Variable getAtomic(AtomicRef r) {

		if (r.getRef() instanceof MemVariable) {
			MemVariable v = (MemVariable) r.getRef();
			if (!v.isArray())
				return getAtomic(v);
		}

		throw illegal("This is not an Atomic! " + r.getRef().getName());

	}

	public Variable getAtomic(MemVariable m) {
		Variable v;
		if (locals != null) {
			v = locals.getAtomic(m);
			if (v != null)
				return v;
		}
		v = globals.getAtomic(m);
		if (v != null)
			return v;
		throw illegal("Variable " + m.getName() + " n'existe pas!");
	}

	public ChannelRepresentation getChannel(AtomicRef r) {

		if (r.getRef() instanceof ChanVariable) {
			ChanVariable c = (ChanVariable) r.getRef();
			return getChannel(c);
		}

		throw illegal("This is not an Atomic! " + r.getRef().getName());

	}

	public ChannelRepresentation getChannel(ChanVariable cv) {
		ChannelRepresentation cr;
		if (locals != null) {
			cr = locals.getChannel(cv);
			if (cr != null)
				return cr;
		}
		cr = globals.getChannel(cv);
		if (cr != null)
			return cr;
		throw illegal("Variable " + cv.getName() + " n'existe pas!");
	}

	// / Globals!!

	public ConstParameter getMacroValue(AtomicRef ref) {
		return globals.getMacroValue(ref);
	}

	public ParamRef getMacroRef(AtomicRef ref) {
		return globals.getMacroRef(ref);
	}

	public Constant getSymbConst(MTypeSymbol ms) {
		return globals.getSymbConst(ms);
	}

	public boolean containsSymbConst(MTypeSymbol ms) {
		return globals.containsSymbConst(ms);
	}

	public int nbMessages() {
		return globals.nbMessages();
	}

	public fr.lip6.move.gal.VariableReference getRef(Reference ref, Converter c) {
		// REFACTOR PAsser converter singleton
		if (ref instanceof AtomicRef) {
			AtomicRef ar = (AtomicRef) ref;

			Variable v = this.getAtomic(ar);
			return makeRef(v);

		} else if (ref instanceof TabRef) {
			TabRef tref = (TabRef) ref;
			final Reference base = tref.getRef();
			if (!(base instanceof AtomicRef))
				throw unsupported("Struc are not supported for now");
			final AtomicRef target = (AtomicRef) base;

			if (target.getRef() instanceof MemVariable) {
				 MemVariable var = (MemVariable) target.getRef();
				ArrayPrefix ap = this.getArray(var);
				if (ap == null) {
					throw illegal("Could not find tab ref ");
				}

				return GF2.createArrayVarAccess(ap,c.convertInt(tref.getIndex()));
				// CHECK
			}

			System.out.println(tref);
			System.out.println(target);
			throw illegal("target is not a MemVariable?");
		}
		// HERE
		throw illegal("Neither AtomicRef, neither Tab");
	}

}
