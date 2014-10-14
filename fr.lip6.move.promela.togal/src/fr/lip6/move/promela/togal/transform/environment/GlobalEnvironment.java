package fr.lip6.move.promela.togal.transform.environment;

import static fr.lip6.move.promela.togal.utils.TransfoUtils.illegal;

import java.util.HashMap;
import java.util.Map;

import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.promela.promela.AtomicRef;
import fr.lip6.move.promela.promela.DefineIntMacro;
import fr.lip6.move.promela.promela.MTypeSymbol;
import fr.lip6.move.promela.promela.Referable;

public class GlobalEnvironment extends LocalEnvironment implements
		PromelaEnvironment {
	// virer héritage peut etre?
	// TODO : check doublon. (Ecore.copy

	// Only Global
	private Map<DefineIntMacro, ConstParameter> macroMap = new HashMap<DefineIntMacro, ConstParameter>();

	private Map<MTypeSymbol, Constant> messageMap = new HashMap<MTypeSymbol, Constant>();
	
	public GlobalEnvironment(){
		macroMap = new HashMap<DefineIntMacro, ConstParameter>();
		messageMap = new HashMap<MTypeSymbol, Constant>();
	}
	

	public ConstParameter getMacroValue(AtomicRef ref) {
		Referable target = ref.getRef();
		if (target instanceof DefineIntMacro) {
			DefineIntMacro macro = (DefineIntMacro) target;
			return macroMap.get(macro);
		}
		throw illegal("This is not a Macro!: " + ref);
	}

	public ParamRef getMacroRef(AtomicRef ref) {
		ConstParameter cp = getMacroValue(ref);
		ParamRef pref = GalFactory.eINSTANCE.createParamRef();
		pref.setRefParam(cp);
		return pref;
	}
	
	public void putMacro(DefineIntMacro dim, ConstParameter cp){
		macroMap.put(dim, cp);		
	}
	

	public Constant getSymbConst(MTypeSymbol ms) {
		Constant tmp = messageMap.get(ms);
		if (tmp == null)
			throw illegal("Message Symbol don't exists: " + ms.getName());
		return tmp;
	}

	public boolean containsSymbConst(MTypeSymbol ms) {
		return messageMap.containsKey(ms);
	}

	public void putSymbConst(MTypeSymbol ms, Constant c) {
		messageMap.put(ms, c);
	}

	public int nbMessages() {
		return messageMap.size();
	}

	@Override
	public void addToGal(GALTypeDeclaration gal){
		for( ConstParameter cp : this.macroMap.values())
		gal.getParams().add(cp);
		
		//note: rien à faire pour Mtype
		
		super.addToGal(gal);
		
	}
}
