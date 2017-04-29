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
		this.name = name;
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
		return "{"+name + ": "+ children + " }";
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
	
	@Override
	public CompositeGalOrder clone()  {
		List<IOrder> copy = new ArrayList<IOrder>(children.size());
		for (IOrder child : children) {
			copy.add(child.clone());
		}
		return new CompositeGalOrder(copy, name);
	}

}
