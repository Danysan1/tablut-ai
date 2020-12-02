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
	
	private static final int[][] borders = {
			{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},{0,8},
			{1,0},{1,8}, {2,0},{2,8}, {3,0},{3,8}, {4,0},{4,8}, {5,0},{5,8}, {6,0},{6,8},{7,0},{7,8},
			{8,0},{8,1},{8,2},{8,3},{8,4},{8,5},{8,6},{8,7},{8,8}
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
	private static final int WHITE_IN_BORDERS = 9;
	
	private double[] weights;
	
	public TuichuHeuristic () {
		initWeights();
	}
	
	private void initWeights() {
		weights = new double[10];
		weights[WHITE_WIN] = 20000;
		weights[BLACK_WIN] = -20000;
		weights[EATEN_BLACK] = 800; // max 6400
		weights[MANHATTAN_DISTANCE] = -500; // max -2400
		weights[WHITE_ADJ_KING] = -10; // max -400
		weights[DRAW] = 0;
		weights[EATEN_WHITE] = -1000;
		weights[BLACK_ADJ_KING] = -4000;
		weights[WHITE_IN_WINNIG_TILES] = -100;
		weights[WHITE_IN_BORDERS] = -5000;
	}
	
	@Override
	public float getValue(State state) {
		double value =
			weights[WHITE_WIN]				* isWhiteWin(state)	+
			weights[BLACK_WIN]				* isBlackWin(state)	+
			weights[EATEN_BLACK]			* getEatenPawns(state, Pawn.BLACK)	+
			weights[MANHATTAN_DISTANCE]		* getMinManhattanDistanceFromKingToWin(state)+
			weights[WHITE_ADJ_KING]			* getPawnsAdjacentToKing(state, Pawn.WHITE) +
			//weights[DRAW]					* isDraw(newState) +
			weights[EATEN_WHITE]			* getEatenPawns(state, Pawn.WHITE)	+
			weights[BLACK_ADJ_KING]			* getPawnsAdjacentToKing(state, Pawn.BLACK) +
			//weights[WHITE_IN_WINNIG_TILES]	* getPawnsInWinCells(newState, Pawn.WHITE) +
			weights[WHITE_IN_BORDERS]		* getPawnsInBorders(state, Pawn.WHITE);
		
		return (float) value;
	}
	
	// FUNZIONI DI ANALISI
	
	private double isDraw(State state) {
		return state.getTurn().equals(Turn.DRAW) ? 1 : 0;
	}
	
	private double getEatenPawns(State state, Pawn color) {
		int total = color.equals(Pawn.WHITE) ? 16 : 8;
		return (double) (total - state.getNumberOf(color));
	}
	
	private double getPawnsAdjacentToKing(State state, Pawn color) {
		int count = 0;
		int[] kingPosition = getKingPosition(state);
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
		int[] valueholder = getKingPosition(state).clone();
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
	
	protected float getPawnsInWinCells(State state, Pawn color) {
		int count = 0;
		for(Pawn pawn : state.getPawnsInWinCells())
			if(pawn.equals(color))
				count++;
		return (float)count;
	}
	
	private float getPawnsInBorders(State state, Pawn color) {
		int count = 0;
		Pawn[][] board = state.getBoard();
		
		for (int[] i : borders) {
			int x = i[0], y = i[1];
			if(board[x][y].equals(color))
				count++;
		}
		return (float)count;
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
		int[] valueholder = getKingPosition(state);
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
        int[] valueholder = getKingPosition(state);
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
	
	private int[] getKingPosition(State state){
		int x=0, y=0;
            for(int i=0; i<9; i++)
                for(int j=0; j<9; j++)
                        if(state.getPawn(i, j).equals(Pawn.KING)) {
                                x = i;
                                y = j;
                                break;
                        }
		int[] valueholder = {x,y}; //return an array containing the position of the king
		return valueholder;
	}
}
