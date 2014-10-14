package fr.lip6.move.promela.togal.transform.environment;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.promela.promela.ChanVariable;
import fr.lip6.move.promela.promela.MemVariable;
import fr.lip6.move.promela.togal.transform.representations.ChannelRepresentation;

public interface PromelaEnvironment {

	public ArrayPrefix getArray(MemVariable m);

	public Variable getAtomic(MemVariable m);

	public ChannelRepresentation getChannel(ChanVariable m);

	public void putAtomic(MemVariable mv, Variable ap);

	public void putArray(MemVariable mv, ArrayPrefix ap);

	public void putChannel(ChanVariable cv, ChannelRepresentation cr);

	void addToGal(GALTypeDeclaration gal);

}
