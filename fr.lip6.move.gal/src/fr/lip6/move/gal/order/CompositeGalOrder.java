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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CompositeGalOrder))
			return false;
		CompositeGalOrder other = (CompositeGalOrder) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
