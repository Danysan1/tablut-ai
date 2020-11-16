package edu.tuichu.heuristic;

import java.util.HashMap;
import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
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
		map.put(FactorType.WHITE_PAWNS, getPawns(Pawn.WHITE, state)+getPawns(Pawn.KING, state));
		map.put(FactorType.BLACK_PAWNS, getPawns(Pawn.BLACK, state));
		map.put(FactorType.EATEN_WHITE_PAWNS, getEatenPawns(Turn.WHITE, state));
		map.put(FactorType.EATEN_BLACK_PAWNS, getEatenPawns(Turn.BLACK, state));
		map.put(FactorType.DISTANCE_TO_WIN, getMinManhattanDistanceFromKingToWin(state));
		map.put(FactorType.MOVES_TO_WIN, getMinMovesFromKingToWin(state));
		map.put(FactorType.WHITE_PAWNS_IN_WIN_CELLS, getPawnsInWinCells(Pawn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_IN_WIN_CELLS, getPawnsInWinCells(Pawn.BLACK, state));
		map.put(FactorType.WHITE_PAWNS_ADJACENT_TO_KING, getPawnsAdjacentToKing(Pawn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_ADJACENT_TO_KING, getPawnsAdjacentToKing(Pawn.BLACK, state));
		map.put(FactorType.WHITE_PAWNS_IN_KINGS_ROW, getPawnsInKingsRow(Pawn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_IN_KINGS_ROW, getPawnsInKingsRow(Pawn.BLACK, state));
		map.put(FactorType.WHITE_PAWNS_IN_KINGS_COLUMN, getPawnsInKingsColumn(Pawn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_IN_KINGS_COLUMN, getPawnsInKingsColumn(Pawn.BLACK, state));
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
		return state.getPawn(4,4).equals(Pawn.KING);
	}

	protected int getPawns(Pawn playerOwningThePawns, State state) {
		return state.getNumberOf(playerOwningThePawns);
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

	protected int getPawnsInWinCells(Pawn playerOwningThePawns, State state) {
		int count = 0;
		for(int i=1; i<8; i++) {
			if(
				state.getPawn(0, i).equals(playerOwningThePawns) ||
				state.getPawn(8, i).equals(playerOwningThePawns) ||
				state.getPawn(i, 0).equals(playerOwningThePawns) ||
				state.getPawn(i, 8).equals(playerOwningThePawns)
			) count++;
		}
		return count;
	}

	protected int getPawnsAdjacentToKing(Pawn playerOwningThePawns, State state) {
		int count = 0, x = -1, y = -1;
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(state.getPawn(i, j).equals(Pawn.KING)) {
					x = i;
					y = j;
					break;
				}
			}
		}		
		if(x==-1 || y==-1) {
			// This should never happen
			throw new IllegalStateException();
		}
		
		for(int i=(x-1); i<x+1; i++) {
			for(int j=(y-1); j<(y+1); j++) {
				if(state.getPawn(i, j).equals(playerOwningThePawns))
					count++;
			}
		}
		
		return count;
	}

	protected int getPawnsInKingsRow(Pawn playerOwningThePawns, State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getPawnsInKingsColumn(Pawn playerOwningThePawns, State state) {
		// TODO To be implemented
		return 0;
	}
	
}
