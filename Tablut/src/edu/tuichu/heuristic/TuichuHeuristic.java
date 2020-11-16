package edu.tuichu.heuristic;

import java.util.HashMap;
import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class TuichuHeuristic extends WeightedHeuristic {

	public TuichuHeuristic(Map<FactorType, Float> weights) {
		super(weights);
	}

	public TuichuHeuristic() {
		super(HeuristicWeights.getInstance());
	}

	@Override
	protected Map<FactorType, Number> getFactors(State state) {
		Map<FactorType, Number> map = new HashMap<>();
		map.put(FactorType.WHITEWIN, Float.valueOf(isWhiteWin(state) ? 1 : 0));
		map.put(FactorType.BLACKWIN, Float.valueOf(isBlackWin(state) ? 1 : 0));
		map.put(FactorType.KING_IN_CASTLE, Float.valueOf(isKingInCastle(state) ? 1 : 0));
		map.put(FactorType.WHITE_PAWNS, getPawns(Turn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS, getPawns(Turn.BLACK, state));
		map.put(FactorType.EATEN_WHITE_PAWNS, getEatenPawns(Turn.WHITE, state));
		map.put(FactorType.EATEN_BLACK_PAWNS, getEatenPawns(Turn.BLACK, state));
		map.put(FactorType.DISTANCE_TO_WIN, getMinManhattanDistanceFromKingToWin(state));
		map.put(FactorType.MOVES_TO_WIN, getMinMovesFromKingToWin(state));
		map.put(FactorType.WHITE_PAWNS_IN_WIN_CELLS, getPawnsInWinCells(Turn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_IN_WIN_CELLS, getPawnsInWinCells(Turn.BLACK, state));
		map.put(FactorType.WHITE_PAWNS_ADJACENT_TO_KING, getPawnsAdjacentToKing(Turn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_ADJACENT_TO_KING, getPawnsAdjacentToKing(Turn.BLACK, state));
		map.put(FactorType.WHITE_PAWNS_IN_KINGS_ROW, getPawnsInKingsRow(Turn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_IN_KINGS_ROW, getPawnsInKingsRow(Turn.BLACK, state));
		map.put(FactorType.WHITE_PAWNS_IN_KINGS_COLUMN, getPawnsInKingsColumn(Turn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_IN_KINGS_COLUMN, getPawnsInKingsColumn(Turn.BLACK, state));
		return map;
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
	
}
