package edu.tuichu.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public interface TablutHeuristic {
	/**
	 * Get the heuristic value for a given state
	 * 
	 * @param state The current state
	 * @return The heuristic value
	 */
	public float getValue(State oldState, State newState);
}
