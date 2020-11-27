package edu.tuichu.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class MockHeuristic implements TablutHeuristic {

	@Override
	public float getValue(State state) {
		return 0;
	}

}
