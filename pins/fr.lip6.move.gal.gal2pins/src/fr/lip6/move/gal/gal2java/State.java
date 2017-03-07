package fr.lip6.move.gal.gal2java;

import java.util.Arrays;
import java.util.List;

public class State implements IState {

	private int [] state;
	
	public State (int sz) {
		state = new int[sz];
	}
	
	public State (List<Integer> st) {
		state = new int[st.size()];
		for (int i = 0; i < state.length; i++)
			state[i] = st.get(i);
	}
	
	private State(int[] copy) {
		this.state = copy;
	}

	@Override
	public int get(int varIndex) {
		return state[varIndex];
	}

	@Override
	public void set(int varIndex, int varValue) {
		state[varIndex] = varValue;
	}

	@Override
	public IState copy() {
		return new State (Arrays.copyOf(state,state.length));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(state);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (!Arrays.equals(state, other.state))
			return false;
		return true;
	}
	
	

}
