package edu.tuichu.algorithm;

import java.util.HashMap;
import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class TuichuHeuristic implements TablutHeuristic {	
	protected Map<WeightType,Float> weights;
	
	/**
	 * Create heuristic with custom weights
	 * 
	 * @param weights Custom weights
	 */
	public TuichuHeuristic(Map<WeightType,Float> weights) {
		if(weights == null)
			throw new IllegalArgumentException("Null weights");
		
		this.weights = weights;
	}
	
	/**
	 * Create heuristic with default weights
	 */
	public TuichuHeuristic() {
		this.weights = new HashMap<>();
		this.weights.put(WeightType.WHITEWIN, Float.valueOf(1));
		this.weights.put(WeightType.BLACKWIN, Float.valueOf(-1));
	}

	@Override
	public float getValue(State state) {
		return
			weighted(isWhiteWin(state), WeightType.WHITEWIN) +
			weighted(isBlackWin(state), WeightType.BLACKWIN) +
			weighted(isKingInCastle(state), WeightType.KING_IN_CASTLE) +
			weighted(getPawns(Turn.WHITE, state), WeightType.WHITE_PAWNS) +
			weighted(getPawns(Turn.BLACK, state), WeightType.BLACK_PAWNS) +
			weighted(getEatenPawns(Turn.WHITE, state), WeightType.EATEN_WHITE_PAWNS) +
			weighted(getEatenPawns(Turn.BLACK, state), WeightType.EATEN_BLACK_PAWNS) +
			weighted(getMinManhattanDistanceFromKingToWin(state), WeightType.DISTANCE_TO_WIN) +
			weighted(getMinMovesFromKingToWin(state), WeightType.MOVES_TO_WIN) +
			weighted(getPawnsInWinCells(Turn.WHITE, state), WeightType.WHITE_PAWNS_IN_WIN_CELLS) +
			weighted(getPawnsInWinCells(Turn.BLACK, state), WeightType.BLACK_PAWNS_IN_WIN_CELLS) +
			weighted(getPawnsAdjacentToKing(Turn.WHITE, state), WeightType.WHITE_PAWNS_ADJACENT_TO_KING) +
			weighted(getPawnsAdjacentToKing(Turn.BLACK, state), WeightType.BLACK_PAWNS_ADJACENT_TO_KING) +
			weighted(getPawnsInKingsRow(Turn.WHITE, state), WeightType.WHITE_PAWNS_IN_KINGS_ROW) +
			weighted(getPawnsInKingsRow(Turn.BLACK, state), WeightType.BLACK_PAWNS_IN_KINGS_ROW) +
			weighted(getPawnsInKingsColumn(Turn.WHITE, state), WeightType.WHITE_PAWNS_IN_KINGS_COLUMN) +
			weighted(getPawnsInKingsColumn(Turn.BLACK, state), WeightType.BLACK_PAWNS_IN_KINGS_COLUMN);
	}
	
	private float weighted(float value, WeightType type) {
		return value * this.weights.getOrDefault(type, Float.valueOf(0));
	}
	
	private float weighted(boolean value, WeightType type) {
		return weighted(value ? 1 : 0, type);
	}
	
	// FUNZIONI DI ANALISI

	protected boolean isWhiteWin(State state) {
		return state.getTurn().equals(Turn.WHITEWIN);
	}

	protected boolean isBlackWin(State state) {
		return state.getTurn().equals(Turn.BLACKWIN);
	}

	protected boolean isKingInCastle(State state) {
		// TODO To be implemented
		return false;
	}

	protected int getPawns(Turn playerOwningThePawns, State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getEatenPawns(Turn playerOwningThePawns, State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getMinManhattanDistanceFromKingToWin(State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getMinMovesFromKingToWin(State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getPawnsInWinCells(Turn playerOwningThePawns, State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getPawnsAdjacentToKing(Turn playerOwningThePawns, State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getPawnsInKingsRow(Turn playerOwningThePawns, State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getPawnsInKingsColumn(Turn playerOwningThePawns, State state) {
		// TODO To be implemented
		return 0;
	}
	
	public enum WeightType {
		WHITEWIN,
		BLACKWIN,
		KING_IN_CASTLE,
		WHITE_PAWNS,
		BLACK_PAWNS,
		EATEN_WHITE_PAWNS,
		EATEN_BLACK_PAWNS,
		DISTANCE_TO_WIN,
		MOVES_TO_WIN,
		WHITE_PAWNS_IN_WIN_CELLS,
		BLACK_PAWNS_IN_WIN_CELLS,
		WHITE_PAWNS_ADJACENT_TO_KING,
		BLACK_PAWNS_ADJACENT_TO_KING,
		WHITE_PAWNS_IN_KINGS_ROW,
		BLACK_PAWNS_IN_KINGS_ROW,
		WHITE_PAWNS_IN_KINGS_COLUMN,
		BLACK_PAWNS_IN_KINGS_COLUMN
	}
}
