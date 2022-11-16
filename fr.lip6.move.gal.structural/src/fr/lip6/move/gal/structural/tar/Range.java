package fr.lip6.move.gal.structural.tar;

public class Range {
	private int lower;
	private int upper;
	public Range() {
		this.lower = Integer.MIN_VALUE;
		this.upper = Integer.MAX_VALUE;
	}
	public Range(int point) {
		this.lower = this.upper = point;
	}
	public Range(int lower, int upper) {
		this.lower = lower;
		this.upper = upper;
	}
	public boolean contains(int val) {
		return lower <= val && val <= upper;
	}
	public Range copy() {
		return new Range(lower,upper);
	}
	public void free() {
		this.lower = Integer.MIN_VALUE;
		this.upper = Integer.MAX_VALUE;
	}
	public int getLower() {
		return lower;
	}
	public int getUpper() {
		return upper;
	}
	public boolean intersects (Range other) {
		return lower <= other.upper && other.lower <= upper;
	}
	public void intersectWith (Range other) {
		lower = Math.max(lower,other.lower);
		upper = Math.min(upper, other.upper);
	}
	public void invalidate() {
		//hack setting range invalid
        lower = 1;
        upper = 0;
	}
	public boolean isSound() {
		return lower <= upper;
	}
	public boolean noLower() {
		return lower == Integer.MIN_VALUE;
	}

	public boolean noUpper() {
		return upper == Integer.MAX_VALUE;
	}

	public void restrictTo(int value) {
		lower = upper = value;
	}

	public void shift (int delta) {
		if (delta < 0 && lower < Integer.MIN_VALUE - delta) {
			lower = Integer.MIN_VALUE;
		} else {
			lower += delta;
		}
		if (delta > 0 && upper >= Integer.MAX_VALUE - delta) {
			upper = Integer.MAX_VALUE;
		} else {
			upper += delta;
		}
	}
	public int size() {
		return 1 + (upper - lower);
	}
	@Override
	public String toString() {
		return (noLower()?"[0":("["+lower))
				+", "
				+(noUpper()?"inf]":(upper+"]"));

	}
	public boolean isUnbound() {
		return noLower() && noUpper();
	}
	public void unionToCover(int value) {
		lower = Math.min(lower,value);
		upper = Math.max(upper,value);
	}

	public void unionWith (Range other) {
		lower = Math.min(lower,other.lower);
		upper = Math.max(upper, other.upper);
	}

	public RangeComparison compare(Range other) {
		return new RangeComparison(
				lower <= other.lower && upper >= other.upper,
				lower >= other.lower && upper <= other.upper);
	}

	public static class RangeComparison {
		public boolean includes;
		public boolean included;

		public RangeComparison(boolean includes, boolean included) {
			this.includes = includes;
			this.included = included;
		}
	}
}
