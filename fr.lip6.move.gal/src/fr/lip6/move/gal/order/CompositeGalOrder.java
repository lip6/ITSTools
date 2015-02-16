package fr.lip6.move.gal.order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompositeGalOrder implements IOrder {

	
	private List<IOrder> children ;
	private String name;
	
	public CompositeGalOrder(List<IOrder> list, String name) {
		children= new ArrayList<IOrder>(list);
	}

	@Override
	public <T> T accept(IOrderVisitor<T> v) {
		return v.visitComposite(this);
	}
	
	public List<IOrder> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return "CompositeGalOrder [children=" + children + "]";
	}

	@Override
	public Set<String> getAllVars() {
		Set<String> res = new HashSet<String>();
		for (IOrder o : getChildren()) {
			res.addAll(o.getAllVars());
		}
		return res;
	}

	@Override
	public String getName() {
		return name;
	}
	
	
}
