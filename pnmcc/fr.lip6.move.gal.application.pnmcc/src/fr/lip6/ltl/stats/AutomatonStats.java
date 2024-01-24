package fr.lip6.ltl.stats;

public class AutomatonStats {

	// Fields for storing automaton statistics
	private String name;
	private String type;
	private boolean universal;
	private boolean empty;
	private int numAP;
	private int numStates;
	private int numEdges;
	private int formulaComplexity;
	private int numAccSets;
	private boolean stutterInsensitive;
	private boolean veryWeak;
	private boolean weak;
	private boolean terminal;
	private boolean deterministic;
	private long time=-1;
	private boolean hasFailed;
	// %c, number of SCC
	private int nbSCC;
	// %[a]c, accepting SCC
	private int nbAccSCC;
	// %[r]c, rejecting SCC
	private int nbRejSCC;
	// %[v]c, trivial SCC
	private int nbTrivSCC;
	// %t, number of transition (with True = 2^AP)
	private int nbTrans;
	// %n, number of non deterministic states
	private int nbNonDet;
	// Number of factoids used to compute this one
	private int nbFacts;
	

	// Default constructor
	public AutomatonStats(String name,String type) {
		this.name = name;
		this.type = type;
	}

	// Build the CSV header line
	public static String buildHeaderLine() {
		return "Name,Type,AP,S,Edges,Formula,Acc,SI,Universal,Empty,VeryWeak,Weak,Terminal,Deterministic,nbSCC,nbAccSCC,nbRejSCC,nbTrivSCC,nbTrans,nbNonDet,nbFact,Time";
	}

	public static String buildLegend() {
		return "| Column      | Description                          |\n"
				+ "|-------------|--------------------------------------|\n"
				+ "| Name        | Name of the automaton                |\n"
				+ "| Type        | Nature of formula                    |\n"
				+ "| AP        	 | Num of atomic propositions           |\n"
				+ "| S         	 | Num of states                        |\n"
				+ "| Edges     	 | Num of edges                         |\n"
				+ "| Formula	 | Syntactic complexity of formulas     |\n"
				+ "| Acc	     | Num of acceptance sets               |\n"
				+ "| SI          | 1 if stutter insensitive, 0 otherwise|\n"
				+ "| Universal   | 1 if universal, 0 otherwise          |\n"
				+ "| Empty       | 1 if empty, 0 otherwise              |\n"
				+ "| VeryWeak    | 1 if very-weak, 0 otherwise          |\n"
				+ "| Weak        | 1 if weak, 0 otherwise               |\n"
				+ "| Terminal    | 1 if terminal, 0 otherwise           |\n"
				+ "| Determinist | 1 if deterministic, 0 otherwise      |\n"
				+ "| nbFact      | number of factoids used              |\n"
		        + "| Time        | run time in ms                       |";
	}

	// Convert the object's data to a CSV line
	@Override
	public String toString() {
		if (!hasFailed) {
			return name + "," + type + ","+ numAP + "," + numStates + "," + numEdges + "," + formulaComplexity + "," + numAccSets + ","
				+ (stutterInsensitive ? "1" : "0") + "," + (universal ? "1" : "0") + "," + (empty ? "1" : "0") + ","
				+ (veryWeak ? "1" : "0") + "," + (weak ? "1" : "0") + "," + (terminal ? "1" : "0") + ","
				+ (deterministic ? "1" : "0")  + "," + nbSCC + "," + nbAccSCC +","+ nbRejSCC + "," + nbTrivSCC + "," + nbTrans 
				+ "," + nbNonDet + "," + nbFacts + "," + time;
		} else {
			return name + "," + type + ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,"+ time ;
		}
	}

	public void setDeterministic(boolean deterministic) {
		this.deterministic = deterministic;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}

	public void setVeryWeak(boolean veryWeak) {
		this.veryWeak = veryWeak;
	}

	public void setWeak(boolean weak) {
		this.weak = weak;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUniversal() {
		return universal;
	}

	public void setUniversal(boolean universal) {
		this.universal = universal;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public int getNumAP() {
		return numAP;
	}

	public void setNumAP(int numAP) {
		this.numAP = numAP;
	}

	public int getNumStates() {
		return numStates;
	}

	public void setNumStates(int numStates) {
		this.numStates = numStates;
	}

	public int getNumEdges() {
		return numEdges;
	}

	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	public int getFormulaComplexity() {
		return formulaComplexity;
	}

	public void setFormulaComplexity(int formulaComplexity) {
		this.formulaComplexity = formulaComplexity;
	}

	public int getNumAccSets() {
		return numAccSets;
	}

	public void setNumAccSets(int numAccSets) {
		this.numAccSets = numAccSets;
	}

	public boolean isStutterInsensitive() {
		return stutterInsensitive;
	}

	public void setStutterInsensitive(boolean stutterInsensitive) {
		this.stutterInsensitive = stutterInsensitive;
	}

	public void setTime(long l) {
		this.time = l;
	}

	public void setFailed(boolean hasFailed) {
		this.hasFailed = hasFailed;		
	}

	
	public void setNbAccSCC(int nbAccSCC) {
		this.nbAccSCC = nbAccSCC;
	}
	public void setNbNonDet(int nbNonDet) {
		this.nbNonDet = nbNonDet;
	}
	public void setNbSCC(int nbSCC) {
		this.nbSCC = nbSCC;
	}
	public void setNbRejSCC(int nbRejSCC) {
		this.nbRejSCC = nbRejSCC;
	}
	public void setNbTrivSCC(int nbTrivSCC) {
		this.nbTrivSCC = nbTrivSCC;
	}
	public void setNbTrans(int nbTrans) {
		this.nbTrans = nbTrans;
	}

	public void setNbFacts(int nbFacts) {
		this.nbFacts = nbFacts;
	}
	
	
}
