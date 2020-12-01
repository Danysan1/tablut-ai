package edu.tuichu.heuristic;

import java.util.HashMap;
import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class TuichuHeuristic implements TablutHeuristic {
	
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

	private static final int WHITE_WIN = 0;
	private static final int BLACK_WIN = 1;
	private static final int EATEN_BLACK = 2;
	private static final int MANHATTAN_DISTANCE = 3;
	private static final int WHITE_ADJ_KING = 4;
	private static final int DRAW = 5;
	private static final int EATEN_WHITE = 6;
	private static final int BLACK_ADJ_KING = 7;
	private static final int WHITE_IN_WINNIG_TILES = 8;
	
	private double[] weights;
	
	public TuichuHeuristic () {
		initWeights();
	}
	
	private void initWeights() {
		weights = new double[9];
		weights[WHITE_WIN] = 10000;
		weights[BLACK_WIN] = -10000;
		weights[EATEN_BLACK] = 1000;
		weights[MANHATTAN_DISTANCE] = -1000;
		weights[WHITE_ADJ_KING] = -500;
		weights[DRAW] = 0;
		weights[EATEN_WHITE] = -1000;
		weights[BLACK_ADJ_KING] = -200;
		weights[WHITE_IN_WINNIG_TILES] = -3000;
	}
	
	@Override
	public float getValue(State oldState, State newState) {
		double value =
			weights[WHITE_WIN]				* isWhiteWin(newState)	+
			weights[BLACK_WIN]				* isBlackWin(newState)	+
			weights[EATEN_BLACK]			* getEatenPawns(oldState, newState, Pawn.BLACK)	+
			weights[MANHATTAN_DISTANCE]		* getMinManhattanDistanceFromKingToWin(newState)+
			weights[WHITE_ADJ_KING]			* getPawnsAdjacentToKing(newState, Pawn.WHITE) +
			//weights[DRAW]					* isDraw(newState) +
			weights[EATEN_WHITE]			* getEatenPawns(oldState, newState, Pawn.WHITE)	+
			weights[BLACK_ADJ_KING]			* getPawnsAdjacentToKing(newState, Pawn.BLACK) +
			weights[WHITE_IN_WINNIG_TILES]	* getPawnsInWinCells(newState, Pawn.WHITE);
		
		return (float) value;
	}
	
	// FUNZIONI DI ANALISI
	
	private double isDraw(State state) {
		return state.getTurn().equals(Turn.DRAW) ? 1 : 0;
	}
	
	private double getEatenPawns(State oldState, State newState, Pawn color) {
		if (oldState == null) return 0;		
		return (double) (oldState.getNumberOf(color) - newState.getNumberOf(color));
	}
	
	private double getPawnsAdjacentToKing(State state, Pawn color) {
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
				if(state.getPawn(i, j).equals(color))
					count++;
			}
		}
		
		return (double) count;
	}
	
	// TODO check if there are pawns in the path or implement function with possible paths
	protected double getMinManhattanDistanceFromKingToWin(State state) {
		int[] valueholder = state.getKingPosition().clone();
		int x = valueholder[0];
		int y = valueholder[1];
		int min = 10; //max value, since distance can never reach 50
		int distance;
		for (int i=0; i<16; i++){
			distance = Math.abs(x-winningPos[i][0]) + Math.abs(y-winningPos[i][1]);
			if (min > distance)
				min = distance;
		}

		return (double) min;
	}
	
	protected float isWhiteWin(State state) {
		return (state.getTurn().equals(Turn.WHITEWIN) ? 1 : 0);
	}

	protected float isBlackWin(State state) {
		return (state.getTurn().equals(Turn.BLACKWIN) ? 1 : 0);
	}
	
	protected int getPawnsInWinCells(State state, Pawn color) {
		int count = 0;
		for(Pawn pawn : state.getPawnsInWinCells())
			if(pawn.equals(color))
				count++;
		return count;
	}
	
	// ------------ NOT USED FUNCTIONS -------------
	
	private Float getPawnsAroundKing(Pawn pawn, State state) {
		double around = (double) (getPawnsInKingsColumn(pawn, state) + getPawnsInKingsRow(pawn, state));
		
		//double a_nrm = around/4.0;
		//return (float)a_nrm;
		return (float)around;
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
