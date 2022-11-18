package fr.lip6.move.gal.structural.tar;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Stack;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.util.Pair;

public class Solver {

	private ISparsePetriNet net;
	private SparseIntArray initial;
	private Property query;
	private BitSet inq;
	private BitSet dirty;
	private SparseIntArray m = new SparseIntArray();
	private SparseIntArray failm = new SparseIntArray();
	private SparseIntArray mark = new SparseIntArray();
	private int[] use_count;
	
	

	public Solver(ISparsePetriNet pn, SparseIntArray initial, Property property, BitSet support) {
		this.net = pn;
		this.initial = initial.clone();
		this.query = property;
		this.inq = support;
		dirty = new BitSet();
		use_count = new int [pn.getPlaceCount()];
		for (int i = support.nextSetBit(0); i >= 0; i = support.nextSetBit(i+1)) {
			// operate on index i here
			++use_count[i];
			if (i == Integer.MAX_VALUE) {
				break; // or (i+1) would overflow
			}
		}
		for (int tid = 0,tide=net.getTransitionCount() ; tid < tide ; ++tid) {
			SparseIntArray pre = net.getFlowPT().getColumn(tid);
			for (int i=0,ie=pre.size();i<ie;i++) {
				use_count[pre.keyAt(i)] += tide;
			}
		}
	}

	public BitSet getSupport() {
		return inq;
	}

	public boolean check(Stack<State> trace, TraceSet interpolants) {
		dirty.clear();
		
		findFree(trace);
		
		
		return false;
	}
	
	private static class Interpolant {
		PlaceRangeVector prv=new PlaceRangeVector();
		int edges=0;		
	}

	private List<Interpolant> findFree(Stack<State> trace) {
        for(int step = trace.size()-2; step >= 1; --step)
        {
        	List<Interpolant> inter = new ArrayList<>();
        	inter.add(new Interpolant());
        	inter.add(new Interpolant());
        	{
                State s = trace.get(step);
                inter.get(0).edges = s.getEdgeCount();
                int t = s.getEdgeCount() - 1;
                SparseIntArray post = net.getFlowTP().getColumn(t);
                Interpolant back = inter.get(inter.size()-1);
                for (int i=0,ie=post.size();i<ie;i++) {
                	int pid = post.keyAt(i);
					if (inq.get(pid)) {
                		PlaceRange pr = back.prv.findOrAdd(pid);
                		pr.getRange().setLower(post.valueAt(i));
                	}
                }
                back.prv.compact();
                if(back.prv.isTrue()) continue;
            }

            // OK, lets try to forward approximate result from here
            {
                for(int f = step+1; f < trace.size(); ++f)
                {
                    State s = trace.get(f);
                    int t = s.getEdgeCount();
                    if(t == 0)
                    {
                    	Interpolant back = inter.get(inter.size()-1);
                    	back.edges = 0;
                    	
                        RangeEvalContext ctx = new RangeEvalContext (back.prv, net, use_count);
                        
                        if (query.getType() == PropertyType.INVARIANT && query.getBody().getOp() == Op.EF) {
                            ctx.visit(query.getBody());                     	
                        } else if (query.getType() == PropertyType.INVARIANT && query.getBody().getOp() == Op.AG) {
                            ctx.visit(Expression.not(query.getBody()));
                        } else {
                        	throw new IllegalArgumentException("TAR can only be used for INVARIANT formulas currently.");
                        }
//                        
//                        Visitor::visit(ctx, _query);
//                        if(!ctx.satisfied() && !ctx.constraint().is_false(_net.numberOfPlaces()))
//                        {
//
//                            inter.back().first = ctx.constraint();
//                            assert(!ctx.constraint().is_true());
//                            inter.back().first.compact();
//                            assert(inter.back().first.is_compact());
//                            // flush non-used
//                            for(int64_t i = inter.size()-2; i >= 0; --i)
//                            {
//                                auto j = i+1;
//                                auto t = inter[i].second;
//                                inter[i].first = inter[j].first;
//                                assert(t > 0);
//                                --t;
//                                auto post = _net.postset(t);
//                                for(; post.first != post.second; ++post.first)
//                                {
//                                    auto it = inter[i].first[post.first->place];
//                                    if(it == nullptr) continue;
//                                    if(it->_range._upper < post.first->tokens)
//                                    {
//                                        break;
//                                    }
//                                    it->_range -= post.first->tokens;
//                                }
//                                inter[i].first.compact();
//                                if(inter[i].first.is_true())
//                                {
//                                    inter.erase(inter.begin(), inter.begin());
//                                    assert(inter.front().first.is_true());
//                                    break;
//                                }
//                                auto pre = _net.preset(t);
//                                for(; pre.first != pre.second; ++pre.first)
//                                {
//                                    auto it = inter[i].first[pre.first->place];
//                                    if(it == nullptr) continue;
//                                    it->_range += pre.first->tokens;
//                                }
//                                assert(inter[i].first.is_compact());
//                            }
//
//                            assert(!ctx.satisfied());
//                            if(!inter.front().first.is_true())
//                                break;
//                            return inter;
//                        }
//                        break;
//                    }
//                    else
//                    {
//                        inter.emplace_back();
//                        inter[inter.size()-2].second = t;
//                        inter.back().first = inter[inter.size()-2].first;
//                        auto& range = inter.back().first;
//                        --t;
//                        auto pre = _net.preset(t);
//                        for(; pre.first != pre.second; ++pre.first)
//                        {
//                            if(_inq[pre.first->place])
//                            {
//                                auto it = range[pre.first->place];
//                                if(it == nullptr) continue;
//                                if(it->_range._lower <= pre.first->tokens)
//                                {
//                                    // free backwards
//                                    for(auto& tmp : inter)
//                                    {
//                                        auto it = tmp.first[pre.first->place];
//                                        if(it)
//                                        {
//                                            it->_range._lower = 0;
//                                            tmp.first.compact();
//                                        }
//                                    }
//                                }
//                                else
//                                {
//                                    it->_range._lower -= pre.first->tokens;
//                                }
//                            }
//                        }
//
//                        auto post = _net.postset(t);
//                        for(; post.first != post.second; ++post.first)
//                        {
//                            if(_inq[post.first->place])
//                            {
//                                auto& pr = range.find_or_add(post.first->place);
//                                if(pr._range._lower == 0)
//                                    pr._range._lower = post.first->tokens;
//                                else
//                                    pr._range._lower += post.first->tokens;
//                            }
//                        }
//                    }
//                    inter.back().first.compact();
//                }
//            }
//        }
//        return {};
		
	}

	
                }}}
    return null;    
	}
}
