package fr.lip6.move.promela.togal.transform.environment;

import static fr.lip6.move.promela.togal.utils.GALUtils.makeArray;
import static fr.lip6.move.promela.togal.utils.TransfoUtils.illegal;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.promela.promela.ChanVariable;
import fr.lip6.move.promela.promela.DefineIntMacro;
import fr.lip6.move.promela.promela.DefineMacro;
import fr.lip6.move.promela.promela.Expression;
import fr.lip6.move.promela.promela.LiteralConstant;
import fr.lip6.move.promela.promela.MTypeDef;
import fr.lip6.move.promela.promela.MTypeSymbol;
import fr.lip6.move.promela.promela.MemVariable;
import fr.lip6.move.promela.promela.VariableDeclaration;
import fr.lip6.move.promela.togal.transform.Converter;
import fr.lip6.move.promela.togal.transform.representations.ChannelRepresentation;

public abstract class EnvironmentAdapter {

	protected PromelaEnvironment penv;
	final private Converter conv;

	public static LocalAdapter getLocalAdapter(LocalEnvironment le,
			Converter conv, String pfx) {
		return new LocalAdapter(le, conv, pfx);
	}

	private EnvironmentAdapter(Converter conv) {
		this.conv = conv;
	}

	public static GlobalAdapter getGlobalAdapter(GlobalEnvironment ge,
			Converter conv) {
		return new GlobalAdapter(ge, conv);
	}

	public abstract String makeName(VariableDeclaration v);

	public void handleMemVar(MemVariable mvar) {

		// Valeur initiale
		Expression initValue = mvar.getInitValue();

		// Variables Atomiques
		if (!mvar.isArray()) {
			// TODO: Struct (vérifier type avant)
			Variable var = GalFactory.eINSTANCE.createVariable();
			var.setName(makeName(mvar));

			if (initValue == null) {
				// valeur par défault:
				var.setValue(GF2.constant(0));
			} else {
				var.setValue(conv.convertInt(initValue));
			}

			penv.putAtomic(mvar, var);

		} else {
			// Tableau!
			int length = ((LiteralConstant) mvar.getLength()).getValue();
			IntExpression ie = (mvar.getInitValue() != null) ? conv
					.convertInt(mvar.getInitValue()) : GF2.constant(0);
			ArrayPrefix ap = makeArray(makeName(mvar), length, ie);
			penv.putArray(mvar, ap);

		}

	}

	public void handleChan(ChanVariable cv) {
		ChannelRepresentation cr = ChannelRepresentation.createRepresentation(
				cv, makeName(cv));

		penv.putChannel(cv, cr);
	}

	public void handleMacro(DefineMacro macro) {// REFACTOR: two classes?
		if (macro instanceof DefineIntMacro) {
			DefineIntMacro dim = (DefineIntMacro) macro;
			ConstParameter cp = GalFactory.eINSTANCE.createConstParameter();
			cp.setName("$" + dim.getName());
			cp.setValue(dim.getInitValue());

			((GlobalEnvironment) penv).putMacro(dim, cp);
		} else {
			throw illegal("This is not a macro!:" + macro);
		}
	}

	public void handleMType(MTypeDef m) {
		GlobalEnvironment globals = (GlobalEnvironment) penv;
		final int currentSize = globals.nbMessages();
		Constant currentGalInt = null;

		int i = 1;
		for (MTypeSymbol ms : m.getMessages()) {
			if (globals.containsSymbConst(ms))
				continue;
			// Vérification nécessaire pour que makeGAlInt gère nb négatif
			IntExpression tmp = GF2.constant(currentSize + i);
			if (!(tmp instanceof Constant))
				throw illegal("Symb constant should be positive, an Constant:"
						+ tmp);
			currentGalInt = (Constant) tmp;

			globals.putSymbConst(ms, currentGalInt);
			i++;
		}
	}

	// / Variantes Locales et Globales!

	static class LocalAdapter extends EnvironmentAdapter {
		private String prefix;

		private LocalAdapter(LocalEnvironment penv, Converter conv, String pfx) {
			super(conv);
			this.prefix = pfx;
			this.penv = penv;
		}

		@Override
		public void handleMacro(DefineMacro macro) {
			throw illegal("Local Environment don't have macro");
		}

		@Override
		public void handleMType(MTypeDef m) {
			throw illegal("Local Environment don't have message type");
		}

		@Override
		public String makeName(VariableDeclaration v) {
			return prefix + "__" + v.getName();
			//NOTE: here crée nom var
		}

	}

	static class GlobalAdapter extends EnvironmentAdapter {

		private GlobalAdapter(GlobalEnvironment ge, Converter conv) {
			super(conv);
			this.penv = ge;
		}

		@Override
		public String makeName(VariableDeclaration v) {
			return v.getName();
		}

	}

}
