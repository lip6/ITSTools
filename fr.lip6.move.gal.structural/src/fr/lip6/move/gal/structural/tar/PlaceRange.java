package fr.lip6.move.gal.structural.tar;

/**
 * @author ythierry
 *
 */
public class PlaceRange implements Comparable<PlaceRange> {
	private int place;
	private Range range;

	public PlaceRange(int place) {
		this.place = place;
		this.range = new Range();
	}
	public PlaceRange(int place, Range range) {
		this.place = place;
		this.range = range.copy();
	}
	public PlaceRange(int place, int mark) {
		this.place = place;
		this.range = new Range(mark);
	}
	public PlaceRange(int place, int lower, int upper) {
		this.place = place;
		this.range = new Range(lower,upper);
	}
	public PlaceRange copy() {
		return new PlaceRange(place, range);
	}
	@Override
	public String toString() {
		return "<P" + place + "> in " + range;
	}
	@Override
	public int compareTo(PlaceRange other) {
		return Integer.compare(place, other.place);
	}
	public int getPlace() {
		return place;
	}

	public Range getRange() {
		return range;
	}

	/*
	public void intersectWith(PlaceRange other) {
		assert other.place == place;
		range.intersectWith(other.range);
	}
	public void restrictTo(int value) {
		range.restrictTo(value);
	}
	public void shift(int delta) {
		range.shift(delta);
	}
	public void unionToCover(int value) {
		range.unionToCover(value);
	}
	public void unionWith(PlaceRange other) {
		assert other.place == place;
		range.unionWith(other.range);
	}
	public int getLower() {
		return range.getLower();
	}
	public int getUpper() {
		return range.getUpper();
	}
	*/

}
