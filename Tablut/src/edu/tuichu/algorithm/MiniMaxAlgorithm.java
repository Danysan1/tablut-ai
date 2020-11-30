package edu.tuichu.algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.tuichu.heuristic.TablutHeuristic;
import edu.tuichu.heuristic.TuichuHeuristic;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class MiniMaxAlgorithm implements TablutAlgorithm {
	private TablutHeuristic heuristic;
	private Utilities utilities;
	private int maxDepth;

	public MiniMaxAlgorithm(int maxDepth, TablutHeuristic heuristic) {
		this.heuristic = heuristic;
		this.utilities = new Utilities();
		this.maxDepth = maxDepth;
	}

	public MiniMaxAlgorithm(int maxDepth) {
		this(maxDepth, new TuichuHeuristic());
	}

	@Override
	public Action getAction(State state) {
		Action a = makeDecision(state);
		return a;
	}

	public Action makeDecision(State state) {
		List<Action> actions = getPossibleMoves(state);
		System.out.println("Total possible muves: " + actions.size());
		Turn turn = state.getTurn();
		Action result = null;
		float evaluation = 0;

		for (int i = 0; i < actions.size(); i++) {
			if (i == 0) {
				result = actions.get(i);
				evaluation = minMax(utilities.performMove(state, result), maxDepth, Float.NEGATIVE_INFINITY,
						Float.POSITIVE_INFINITY);
			} else {
				float temp = minMax(utilities.performMove(state, actions.get(i)), maxDepth, Float.NEGATIVE_INFINITY,
						Float.POSITIVE_INFINITY);
				if (turn.equals(Turn.WHITE) && evaluation <= temp) {
					result = actions.get(i);
					evaluation = temp;
				} else if (turn.equals(Turn.BLACK) && evaluation >= temp) {
					result = actions.get(i);
					evaluation = temp;
				}
			}
		}

		if(result == null)
			throw new RuntimeException("Nessuna azione selezionabile");

		return result;
	}

	public float minMax(State state, int depth, float alpha, float beta) {

		Turn turn = state.getTurn();
		if (depth == 0 || turn.equals(Turn.BLACKWIN) || turn.equals(Turn.DRAW) || turn.equals(Turn.WHITEWIN)) {
			return evaluate(state);
		}

		if (turn.equals(Turn.WHITE)) { // MAXIMIZE
			return maxValue(state, depth, alpha, beta);
		} else if (turn.equals(Turn.BLACK)) { // MINIMIZE
			return minValue(state, depth, alpha, beta);
		}
		return 0;
	}

	public float minValue(State state, int depth, float alpha, float beta) {
		float minEval = Float.POSITIVE_INFINITY;
		for (State s : getSuccessors(state)) {
			float eval = maxValue(state, depth - 1, alpha, beta);
			minEval = Float.min(eval, minEval);
			beta = Float.min(beta, eval);
			if (beta <= alpha) {
				break;
			}
		}
		return minEval;
	}

	public float maxValue(State state, int depth, float alpha, float beta) {
		float maxEval = Float.NEGATIVE_INFINITY;
		for (State s : getSuccessors(state)) {
			float eval = minValue(state, depth - 1, alpha, beta);
			maxEval = Float.max(eval, maxEval);
			alpha = Float.max(alpha, eval);
			if (beta <= alpha) {
				break;
			}
		}
		return maxEval;
	}

	private List<State> getSuccessors(State state) {
		List<Action> actions = getPossibleMoves(state);
		List<State> successors = new ArrayList<>();

		for (Action a : actions) {
			successors.add(utilities.performMove(state, a));
		}

		return successors;
	}

	private List<Action> getPossibleMoves(State state) {
		List<Action> actions = new ArrayList<>();

		Pawn[][] board = state.getBoard();

		/* WHITE */
		if (state.getTurn().equals(Turn.WHITE)) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; i < 9; i++) {
					Pawn pawn = board[i][j];
					if (pawn.equals(Pawn.KING)) {
						actions.addAll(getPossibleMoves(state, i, j));
					} else if (pawn.equals(Pawn.WHITE)) {
						actions.addAll(getPossibleMoves(state, i, j));
					}
				}
			}
		}

		/* BLACK */
		if (state.getTurn().equals(Turn.BLACK)) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; i < 9; i++) {
					Pawn pawn = board[i][j];
					if (pawn.equals(Pawn.BLACK)) {
						actions.addAll(getPossibleMoves(state, i, j));
					}
				}
			}
		}
		return actions;
	}

	private float evaluate(State state) {
		return heuristic.getValue(state);
	}

	private String getBox(int row, int column) {
		String ret;
		char col = (char) (column + 97);
		ret = col + "" + (row + 1);
		return ret;
	}

	private List<Action> getPossibleMoves(State state, int row, int col) {
		List<Action> result = new ArrayList<>();
		String from = getBox(row, col);

		// Check all row moves
		for (int i = 0; i < 9; i++) {
			String to = getBox(row, i);
			try {
				Action a = new Action(from, to, state.getTurn());
				if (utilities.checkMove(state, a)) {
					result.add(a);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Check all column moves
		for (int i = 0; i < 9; i++) {
			String to = getBox(i, col);
			try {
				Action a = new Action(from, to, state.getTurn());
				if (utilities.checkMove(state, a)) {
					result.add(a);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
