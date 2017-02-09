package fr.lip6.move.gal.semantics;

public interface INext {

	<T> T accept (NextVisitor<T> vis);
	
	INext EMPTY = new Abort() ;

	INext ID = new Identity() ;
}


class Abort implements INext {

	@Override
	public <T> T accept(NextVisitor<T> vis) {
		return vis.visitAbort();
	}		
}

class Identity implements INext {		

		@Override
		public <T> T accept(NextVisitor<T> vis) {
			return vis.visitIdentity();
		}
}


