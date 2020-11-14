package edu.tuichu.algorithm;

import java.io.IOException;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.Game;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class MiniMaxAlgorithm implements TablutAlgorithm {
	private Game rules;
	
	public MiniMaxAlgorithm(Game rules) {
		this.rules = rules;
	}

	@Override
	public Action getAction(State state, Turn player) {
		Action a;
		try {
			a = new Action("z0", "z0", player); // TODO
		} catch (IOException e) {
			a = null;
			e.printStackTrace();
		}
		return a;
	}

}
