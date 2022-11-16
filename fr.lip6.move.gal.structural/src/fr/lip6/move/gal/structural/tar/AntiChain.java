package fr.lip6.move.gal.structural.tar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AntiChain {
	//	    using set_t     = std::set<U>;
	//	    using sset_t    = std::vector<U>;
	//	    using smap_t    = std::vector<std::vector<sset_t>>;

	private List<List<List<Integer>>> map=new ArrayList<>();

	private static class node_t {
		int key;
		List<node_t> children;
	};

	void clear() {
		map.clear();
	}

	boolean subsumed(int el, Set<Integer> set)
	{
		boolean exists = false;
		//	            if(map.size() > el)
		//	            {
		//					for(List<List<Integer>> s : map.get(el))
		//	                {
		//	                    
		//						if(std::includes(set.begin(), set.end(), s.begin(), s.end()))
		//	                    {
		//	                        /*std::cout << "SUBSUMBED BY ";
		//	                        for(auto& e : s) std::cout << e << ",";
		//	                        std::cout << std::endl;*/
		//	                        exists = true;
		//	                        break;
		//	                    }
		//	                }
		//	            }
		return exists;
	}

	boolean insert(int el, Set<Integer> set)
	{
		boolean inserted = false;
		//	            if(map.size() <= (size_t)el) map.resize(el + 1);
		//	/*            std::cout << "ANTI (" << (size_t)el << ") -> ";
		//	            for(auto& e : set) std::cout << e << ",";
		//	            std::cout << std::endl;*/
		//	            if(!subsumed(el, set))
		//	            {
		//	                auto& chains = map[el];
		//	                for(int i = chains.size() - 1; i >= 0; --i)
		//	                {
		//	                    if(std::includes(chains[i].begin(), chains[i].end(), set.begin(), set.end()))
		//	                    {
		//	                        chains.erase(chains.begin() + i);
		//	                    }
		//	                }
		//	                chains.emplace_back(sset_t{set.begin(), set.end()});                
		//	                inserted = true;
		//	            }
		//	            else
		//	            {
		//	                inserted = false;
		//	            }

		return inserted;
	}  
}

