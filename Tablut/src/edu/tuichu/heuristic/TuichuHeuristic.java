package edu.tuichu.heuristic;

import java.util.HashMap;
import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class TuichuHeuristic extends WeightedHeuristic {

	public TuichuHeuristic(Map<FactorType, Integer> weights) {
		super(weights);
	}

	public TuichuHeuristic() {
		super(HeuristicWeights.getInstance());
	}

	@Override
	protected Map<FactorType, Integer> getFactors(State state) {
		Map<FactorType, Integer> map = new HashMap<>();
		map.put(FactorType.WHITEWIN, Integer.valueOf(isWhiteWin(state) ? 1 : 0));
		map.put(FactorType.BLACKWIN, Integer.valueOf(isBlackWin(state) ? 1 : 0));
		map.put(FactorType.KING_IN_CASTLE, Integer.valueOf(isKingInCastle(state) ? 1 : 0));
		map.put(FactorType.WHITE_PAWNS, getPawns(Pawn.WHITE, state)+getPawns(Pawn.KING, state));
		map.put(FactorType.BLACK_PAWNS, getPawns(Pawn.BLACK, state));
		map.put(FactorType.EATEN_WHITE_PAWNS, getEatenPawns(Turn.WHITE, state));
		map.put(FactorType.EATEN_BLACK_PAWNS, getEatenPawns(Turn.BLACK, state));
		map.put(FactorType.MOVES_TO_WIN, getMinManhattanDistanceFromKingToWin(state));
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
		int[] valueholder = state.getKingPosition().clone();
		int x = valueholder[0];
		int y = valueholder[1];
		int min = 50; //max value, since distance can never reach 50
		int distance;
		int[][] winningPos = new int[16][2];
		winningPos = state.getWinningPos().clone();
		for (int i=0; i<16; i++){
			distance = Math.abs(x-winningPos[i][0]) + Math.abs(y-winningPos[i][1]);
			if (min > distance)
				min = distance;
		}

		return min;
	}

	protected int getPawnsInWinCells(Pawn playerOwningThePawns, State state) {
		int count = 0;
		for(Pawn pawn : state.getPawnsInWinCells())
			if(pawn.equals(playerOwningThePawns))
				count++;
		return count;
	}

	protected int getPawnsAdjacentToKing(Pawn playerOwningThePawns, State state) {
		int count = 0, x, y;
		int[] valueholder = state.getKingPosition(); //i saw that you searched for the position of the king, we needed the same code for getMinMovesFromKingToWin, so I created a unique function
		x = valueholder[0];
		y = valueholder[1];
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
		int x,y;
		int count=0;
		int[] valueholder = state.getKingPosition();
		x = valueholder[0];
		y = valueholder[1];
		for (int i=0; i<9; i++){
			if(state.getPawn(i,y).equals(playerOwningThePawns))
				count++;
		}
		return count;
	}

	protected int getPawnsInKingsColumn(Pawn playerOwningThePawns, State state) {
                int x,y;
                int count=0;
                int[] valueholder = state.getKingPosition();
                x = valueholder[0];
                y = valueholder[1];
                for (int i=0; i<9; i++){
                        if(state.getPawn(x,i).equals(playerOwningThePawns))
                                count++;
                }
                return count;
	}
	
}
