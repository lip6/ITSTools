package fr.lip6.move.gal.structural.tar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import fr.lip6.move.gal.structural.tar.Range.RangeComparison;

public class PlaceRangeVector {
	private List<PlaceRange> ranges;

	public PlaceRange find (int place) {
		int index = Collections.binarySearch(ranges,new PlaceRange(place));
		if (index < 0) {
			return null;
		} else {
			return ranges.get(index);
		}
	}
	public PlaceRange findOrAdd (int place) {
		int index = Collections.binarySearch(ranges,new PlaceRange(place));
		if (index < 0) {
			PlaceRange pr = new PlaceRange(place);
			ranges.add(-(index+1), pr);
			return pr;
		} else {
			return ranges.get(index);
		}
	}
	public int lower(int place) {
		PlaceRange pr = find(place);
		if (pr == null) {
			return Integer.MIN_VALUE;
		} else {
			return pr.getRange().getLower();
		}
	}
	public int upper(int place) {
		PlaceRange pr = find(place);
		if (pr == null) {
			return Integer.MAX_VALUE;
		} else {
			return pr.getRange().getUpper();
		}
	}
	public void compact() {
		ranges.removeIf(p -> p.getRange().isUnbound());
	}
	public boolean isCompact() {
		return ranges.stream().allMatch(p -> !p.getRange().isUnbound());
	}
	void copy(PlaceRangeVector prv) {
		ranges = new ArrayList<>();
		for (PlaceRange pr : prv.ranges) {
			if (! pr.getRange().isUnbound()) {
				ranges.add(pr.copy());
			}
		}
	}
	public int size() {
		return ranges.size();
	}
	
	@Override
	public String toString() {
		return "{" + ranges + "}";
	}

	public RangeComparison compare (PlaceRangeVector other) {
		assert(isCompact());
        assert(other.isCompact());
        Iterator<PlaceRange> sit = ranges.iterator();
        Iterator<PlaceRange> oit = other.ranges.iterator();
        boolean includes = true;
        boolean included = true;

        PlaceRange prsit=null;
        PlaceRange proit=null;

        while (true) {
            if (! sit.hasNext()) {
                included = included && (!oit.hasNext());
                break;
            } else if (! oit.hasNext()) {
                includes = false;
                break;
            } else {
            	if (prsit == null) {
            		prsit = sit.next();
            	}
            	if (proit == null) {
            		proit = oit.next();
            	}

            	if (prsit.getPlace()==proit.getPlace()) {
            		RangeComparison r = prsit.getRange().compare(proit.getRange());
                    includes &= r.includes;
                    included &= r.included;
                    prsit=null;
                    proit=null;
            	} else if (prsit.getPlace()<proit.getPlace()) {
                    includes = false;
                    prsit=null;
                } else {
                	included = false;
                    proit = null;
                }
            }
            if (!includes && !included) {
            	break;
            }
        }

        return new RangeComparison(includes, included);
	}


	public boolean restricts(List<Integer> writes) {
        int ri = 0;
        for (Integer p : writes) {
        	while (ri < ranges.size() &&
                    (ranges.get(ri).getPlace() < p || ranges.get(ri).getRange().isUnbound()))
                ++ri;

            if (ri == ranges.size()) break;
            if (ranges.get(ri).getPlace() == p)
                return true;
        }
        return false;
	}
	public boolean isTrue() {
		return ranges.isEmpty();
	}


}
