package fr.lip6.move.gal.structural.hlpn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A partition represents the subdivision of a set into mutually exclusive subsets.
 * Elements of the set are represented by an integer.
 * @author ythierry
 *
 */
public class Partition {
	private int [] partition;
	private int nbSubs;
	
	/**
	 * A partition composed of a single subset that contains all elements. 
	 * @param size the size of the set
	 */
	public Partition (int size) {
		partition = new int[size];
		nbSubs = 1;
	}
	
	
	/**
	 * A partition into two sets.
	 */
	public Partition (int size, List<Integer> sub) {
		partition = new int[size];
		if (sub.size() == size) {
			nbSubs = 1;
		} else {
			nbSubs = 2;
			for (int i : sub) {
				partition[i]=1;
			}
		}
	}
	
	public Partition (Collection<List<Integer>> parts) {
		int size = parts.stream().mapToInt(List::size).sum();
		partition = new int[size];
		int i=0;
		for (List<Integer> l : parts) {
			if (i != 0) {
				for (Integer elt: l) {
					partition[elt] = i;
				}
			}
			i++;
		}
		nbSubs = parts.size();
	}
	
	public int getNbSubs() {
		return nbSubs;
	}
	
	public static Partition refine (Partition p1, Partition p2) {
		if (p1.partition.length != p2.partition.length) {
			throw new IllegalArgumentException("Partitions need to correspond to the same superset.");
		}
		if (p1.nbSubs==1) 
			return p2;
		if (p2.nbSubs==1)
			return p1;
		Partition p = new Partition(p1.partition.length);
		
		Set<Integer> indexes = new TreeSet<>(); 
		for (int i=0 ; i < p.partition.length ; i++) {
			int ip1 = p1.partition[i];
			int ip2 = p2.partition[i];
			int ip = ip1 * p2.nbSubs + ip2 ;
			indexes.add(ip);
			p.partition[i] = ip;			
		}
		p.nbSubs = indexes.size();
		Map<Integer,Integer> perm = new HashMap<>();
		int id=0;
		for (Integer e : indexes) {
			perm.put(e, id++);
		}
		for (int i=0 ; i < p.partition.length ; i++) {
			p.partition[i] = perm.get(p.partition[i]);
		}
		return p;
	}
	
	/** Gives the list of the index of partition elements that cover this list.
	 * The list is supposed to be compatible with the current partition.
	 * @param list a list of elements
	 * @return indexes of partitions containing the elements
	 */
	public List<Integer> covers (List<Integer> list) {
		Set<Integer> set = new TreeSet<>();
		for (Integer elt : list) {
			set.add(partition[elt]);
		}
		return new ArrayList<>(set);
	}

	@Override
	public String toString() {
		return "Partition [nbSubs=" + nbSubs + ", partition=" + Arrays.toString(partition) + "]\n";
	}

	public int size() {		
		return partition.length;
	}
	
	public static int sumprod(int[] cur, int[] multipliers) {
		int res = 0;
		for (int i=0,ie=cur.length; i < ie ; i++) {
			res += cur[i]*multipliers[i];
		}
		return res;
	}

	public int[] rewriteMarking(HLPlace place, int sortindex) {
		int [] initial = place.getInitial();
		int [] res = new int [ (initial.length / size()) * getNbSubs()];
		
		List<Sort> sort = place.getSort();
		int [] multipliers = new int[sort.size()];		
		multipliers[sort.size()-1]=1;						
		for (int i=sort.size()-2 ; i >= 0 ; i--) {
			if (i+1 == sortindex) {
				multipliers[i] = multipliers[i+1]*nbSubs;
			} else {
				multipliers[i] = multipliers[i+1]*sort.get(i+1).size();
			}
		}

		// start from 0...0
		int [] cur = new int [place.getSort().size()];
		for (int i=0 ; i < initial.length ; i++) {
			
			if (initial[i] > 0) {
				// remap element at sortindex from cur to image in partition
				int old = cur[sortindex];
				cur[sortindex] = partition[cur[sortindex]];
				int target = sumprod(cur, multipliers);
				if (target >= res.length) {
					System.out.println("WTF ??");
				}
				res[target] += initial[i];
				cur[sortindex] = old;
			}
			
			// increment cur
			int j;
			for (j = cur.length - 1; j >= 0; j--) {
				if (cur[j] == place.getSort().get(j).size() - 1) {
					cur[j] = 0;
					continue;
				} else {
					cur[j]++;
					break;								
				}
			}
		}
		
		return res;
	}

	public int getImage(int target) {
		return partition[target];
	}
	

}
