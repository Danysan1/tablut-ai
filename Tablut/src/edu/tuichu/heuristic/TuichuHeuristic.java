package edu.tuichu.heuristic;

import java.util.HashMap;
import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class TuichuHeuristic extends WeightedHeuristic {
	private static final int[][] winningPos = {
		{0, 1}, {0, 2},
		{0, 6}, {0, 7},
		{1, 0}, {2, 0},
		{1, 8}, {2, 8},
		{6, 0}, {7, 0}, 
		{6, 8}, {7, 8},
		{8, 1}, {8, 2},
		{8, 6}, {8, 7}
	};

	public TuichuHeuristic(Map<FactorType, Integer> weights) {
		super(weights);
	}

	public TuichuHeuristic() {
		super(HeuristicWeights.getInstance());
	}

	@Override
	protected Map<FactorType, Float> getFactors(State state) {
		Map<FactorType, Float> map = new HashMap<>();
		map.put(FactorType.WHITEWIN, isWhiteWin(state));
		map.put(FactorType.BLACKWIN, isBlackWin(state));
		map.put(FactorType.KING_IN_CASTLE, isKingInCastle(state));
		map.put(FactorType.EATEN_WHITE_PAWNS, getEatenPawns(Pawn.WHITE, state));
		map.put(FactorType.EATEN_BLACK_PAWNS, getEatenPawns(Pawn.BLACK, state));
		map.put(FactorType.DISTANCE_TO_WIN, getMinManhattanDistanceFromKingToWin(state));
		map.put(FactorType.WHITE_PAWNS_IN_WIN_CELLS, getPawnsInWinCells(Pawn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_IN_WIN_CELLS, getPawnsInWinCells(Pawn.BLACK, state));
		map.put(FactorType.WHITE_PAWNS_ADJACENT_TO_KING, getPawnsAdjacentToKing(Pawn.WHITE, state));
		map.put(FactorType.BLACK_PAWNS_ADJACENT_TO_KING, getPawnsAdjacentToKing(Pawn.BLACK, state));
		map.put(FactorType.WHITE_AROUND_KING, getPawnsAroundKing(Pawn.WHITE, state));
		map.put(FactorType.BLACK_AROUND_KING, getPawnsAroundKing(Pawn.BLACK, state));
		return map;
	}
	
	// FUNZIONI DI ANALISI
	
	private Float getPawnsAroundKing(Pawn pawn, State state) {
		double around = (double) (getPawnsInKingsColumn(pawn, state) + getPawnsInKingsRow(pawn, state));
		
		//double a_nrm = around/4.0;
		//return (float)a_nrm;
		return (float)around;
	}

	protected float isWhiteWin(State state) {
		return (state.getTurn().equals(Turn.WHITEWIN) ? 1 : 0);
	}

	protected float isBlackWin(State state) {
		return (state.getTurn().equals(Turn.BLACKWIN) ? 1 : 0);
	}

	protected float isKingInCastle(State state) {
		return (state.getPawn(4,4).equals(Pawn.KING) ? 1 : 0);
	}

	protected float getEatenPawns(Pawn pawn, State state) {
		float total = 8 * (pawn.equals(Pawn.BLACK) ? 2 : 1);
		float eaten = total - (float) state.getNumberOf(pawn);
		//float e_nrm = (eaten)/(float)8;
		//return e_nrm;
		return eaten;
	}

	protected float getMinManhattanDistanceFromKingToWin(State state) {
		int[] valueholder = state.getKingPosition().clone();
		int x = valueholder[0];
		int y = valueholder[1];
		int min = 6; //max value, since distance can never reach 50
		int distance;
		for (int i=0; i<16; i++){
			distance = Math.abs(x-winningPos[i][0]) + Math.abs(y-winningPos[i][1]);
			if (min > distance)
				min = distance;
		}
		return min;
	}

	protected float getPawnsInWinCells(Pawn playerOwningThePawns, State state) {
		int count = 0;
		for(Pawn pawn : state.getPawnsInWinCells())
			if(pawn.equals(playerOwningThePawns))
				count++;
		return count;
	}

	protected float getPawnsAdjacentToKing(Pawn playerOwningThePawns, State state) {
		int count = 0;
		int[] kingPosition = state.getKingPosition();
		final int
			x = kingPosition[0],
			y = kingPosition[1],
			minX = x>1 ? x-1 : 0,
			minY = y>1 ? y-1 : 0,
			maxX = x<8 ? x+1 : 9,
			maxY = y<8 ? y+1 : 9;
		if(x==-1 || y==-1) {
			// This should never happen
			throw new IllegalStateException();
		}
		
		for(int i=minX; i<maxX; i++) {
			for(int j=minY; j<maxY; j++) {
				if(state.getPawn(i, j).equals(playerOwningThePawns))
					count++;
			}
		}
		
		return (float)count;
	}

	protected float getPawnsInKingsRow(Pawn playerOwningThePawns, State state) {
		int x,y;
		int count=0;
		int[] valueholder = state.getKingPosition();
		x = valueholder[0];
		y = valueholder[1];
		for (int i=x+1; i<9; i++){
			if(state.getPawn(i,y).equals(playerOwningThePawns)) {
				count++;
				break;
			}
		}
		for (int i=x-1; i>=0; i--){
			if(state.getPawn(i,y).equals(playerOwningThePawns)) {
				count++;
				break;
			}
		}
		
		
		return (float)count;
	}

	protected float getPawnsInKingsColumn(Pawn playerOwningThePawns, State state) {
        int x,y;
        int count=0;
        int[] valueholder = state.getKingPosition();
        x = valueholder[0];
        y = valueholder[1];
        for (int i=y+1; i<9; i++){
			if(state.getPawn(y,i).equals(playerOwningThePawns)) {
				count++;
				break;
			}
		}
		for (int i=y-1; i>=0; i--){
			if(state.getPawn(y,i).equals(playerOwningThePawns)) {
				count++;
				break;
			}
		}
        return (float) count;
	}
	
}
