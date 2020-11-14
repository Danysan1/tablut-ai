package edu.tuichu.algorithm;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public interface TablutHeuristic {
	public float getValue(State state, Turn player);
}
