package edu.tuichu.algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.tuichu.heuristic.TuichuHeuristic;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.Game;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class MiniMaxAlgorithm implements TablutAlgorithm {
	private Game rules;
	private TuichuHeuristic heuristic;
	private Game game;
	private int depth;
	private State.Turn player;

	public MiniMaxAlgorithm(Game rules) {
		/*this.depth = depth;
		this.player = player;*/
		this.rules = rules;
		heuristic = new TuichuHeuristic();
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


	public float minMax(State state, int depth, float alpha, float beta) {
		Turn turn = state.getTurn();
		if (depth == 0 || turn == Turn.BLACKWIN || turn == Turn.DRAW || turn == Turn.WHITEWIN) {
			return evaluate(state);
		}

		if (player == Turn.WHITE) { // MAXIMIZE
			float maxEval = Float.NEGATIVE_INFINITY;
			for (State s : getSuccessors(state, player)) {
				float eval = minMax(state, depth - 1, alpha, beta);
				maxEval = Float.max(eval, maxEval);
				alpha = Float.max(alpha, eval);
				if (beta <= alpha){
					break;
				}
			}
			return maxEval;
		} else if (player == Turn.BLACK) { // MINIMIZE
			float minEval = Float.POSITIVE_INFINITY;
			for (State s : getSuccessors(state, player)){
				float eval = minMax(state, depth - 1, alpha, beta);
				minEval = Float.min(eval, minEval);
				beta = Float.min(beta, eval);
				if (beta <= alpha){
					break;
				}
			}
			return minEval;
		}
		return 0;
	}

	// TODO remember to change the state after each action
	private List<State> getSuccessors(State state, Turn player){
		List<State> states = new ArrayList<>();

		return states;
	}

	private float evaluate(State state){
		return heuristic.getValue(state);
	}

}
