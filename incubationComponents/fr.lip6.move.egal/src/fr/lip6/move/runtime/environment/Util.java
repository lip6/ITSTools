package fr.lip6.move.runtime.environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public abstract class Util {

	public enum Strategy {
		RANDOM,
		KEYBOARD,
		TRACE
	}

	private static Util instance = null;
	private static Strategy strategy = Strategy.RANDOM;

	public static Util getInstance () {
		if (instance == null) {
			switch (strategy) {
			case KEYBOARD :
				instance = new KeyboardMode();
				break;
			case RANDOM :
				instance = new RandomMode();
				break;
			case TRACE :
				throw new NullPointerException("Trace strategy selected but no trace has been set!");				
			}
		}
		return instance;
	}
	
	/**
	 * Only use with Keyboard interactive or Random strategies.
	 * @param strategy
	 */
	public static void setStrategy(Strategy strategy) {
		Util.strategy = strategy;
	}
	
	public static void setTrace(Iterable<Integer> trace) {
		Util.strategy = Strategy.TRACE;
		instance = new TraceMode(trace);
	}
	

	/** Load a trace file containing integers, one by line
	 * @param fileName :
	 * 			name of the trace file
	 * @return :
	 * 			collection of integers read
	 */
	public static Iterable<Integer> loadTrace(String fileName)
	{
		try 
		{
			BufferedReader r = new BufferedReader(new FileReader(fileName));
			ArrayList<Integer> list = new ArrayList<Integer>() ;
			String line ; 
			
			while((line = r.readLine()) != null)
			{
				list.add(Integer.parseInt(line)) ; 
			}
			r.close();
			return list ;
		} catch (Exception e) {
			e.printStackTrace();
			return null ; 
		}
	}
	
	
	abstract public int random (int min, int max, String why);

	public boolean NDChoice(String why) {
		return random(0,1," NDchoice " + why) == 1;
	}

	public static void recordInto(List<Integer> trace) {
		Util tmp = getInstance();
		instance = new RecorderMode(tmp,trace);
	}

}

/// actual implementations


class KeyboardMode extends Util {

	public int random (int min, int max, String why) {
		System.out.println("Choose an int between "+min+" and "+ max + " for " +why);
		Scanner sc = new Scanner(System.in);

		int read = 0;
		do {
			read = sc.nextInt();
			if (read >= min && read <= max) 
				break;
			System.err.println("Invalid Value, try again");
		} while (true);
		return read;
	}
}


class RandomMode extends Util {

	public int random (int min, int max, String why) {
		int n = (int) Math.floor(Math.random() * (max -min + 1));
		n += min ;
		System.err.println(why+"("+min+","+max+")"+ ": "+n);
		return n;
	}

}

class TraceMode extends Util {
	
	
	private Iterator<Integer> iterator;

	public TraceMode(Iterable<Integer> trace) {
		this.iterator = trace.iterator();
	}

	public int random (int min, int max, String why) {
		if (! iterator.hasNext()) {
			throw new NullPointerException("Reached end of trace");
		}
		int n = iterator.next();
		System.err.println(why+"("+min+","+max+")"+" : "+n);
		if (n > max || n < min) {
			throw new NullPointerException("Trace does not match requested bounds");
		}
		return n;
	}
}


class RecorderMode extends Util {
	
	private List<Integer> trace;
	private Util delegate;

	public RecorderMode(Util tmp, List<Integer> trace) {
		this.trace = trace;
		this.delegate = tmp;
	}

	public int random (int min, int max, String why) {
		int n = delegate.random(min, max, why);
		trace.add(n);
		return n;
	}
}
