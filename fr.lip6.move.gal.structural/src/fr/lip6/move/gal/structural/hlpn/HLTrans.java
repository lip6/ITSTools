package fr.lip6.move.gal.structural.hlpn;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Param;

public class HLTrans {
	private String name;
	private List<Param> params;
	private Expression guard;
	
	private List<HLArc> pre= new ArrayList<>();
	private List<HLArc> post=new ArrayList<>();
	
	public HLTrans(String name, List<Param> params, Expression guard) {
		this.name = name;
		this.params = params;
		this.guard = guard;
	}
	@Override
	public String toString() {
		return "HLTrans [name=" + name + ", params=" + params + ", guard=" + guard + ", pre=" + pre + ", post=" + post
				+ "]\n";
	}
	public String getName() {
		return name;
	}
	public Expression getGuard() {
		return guard;
	}
	public void setGuard(Expression guard) {
		this.guard = guard;
	}
	public List<Param> getParams() {
		return params;
	}
	public void setParams(List<Param> params) {
		this.params = params;
	}
	public List<HLArc> getPre() {
		return pre;
	}
	public void setPre(List<HLArc> pre) {
		this.pre = pre;
	}
	public List<HLArc> getPost() {
		return post;
	}
	public void setPost(List<HLArc> post) {
		this.post = post;
	}
	
}