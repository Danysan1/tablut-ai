package edu.tuichu.heuristic;

import java.util.HashMap;
import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class TuichuHeuristic implements TablutHeuristic {
	private static final int[][] winningPos = {
		      {0,1},{0,2},{0,6},{0,7},
		{1,0},                        {1,8},
		{2,0},                        {2,8},
		{6,0},                        {6,8},
		{7,0},                        {7,8},
		      {8,1},{8,2},{8,6},{8,7}
	};

	private static final int[][] borders = {
		{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},{0,8},
		{1,0},                                          {1,8},
		{2,0},                                          {2,8},
		{3,0},                                          {3,8}, 
		{4,0},                                          {4,8}, 
		{5,0},                                          {5,8}, 
		{6,0},                                          {6,8},
		{7,0},                                          {7,8},
		{8,0},{8,1},{8,2},{8,3},{8,4},{8,5},{8,6},{8,7},{8,8}
	};

	private static final float
		WHITE_WIN = 20000,
		BLACK_WIN = -20000,
		DRAW = 0,
		KING_IN_CASTLE = 100,
		EATEN_BLACK_PAWNS = 1000,
		EATEN_WHITE_PAWNS = -1000,
		MANHATTAN_DISTANCE_TO_WIN = -300, // max -2400
		WHITE_ADJACENT_TO_KING = 10, // max 400
		BLACK_ADJACENT_TO_KING = -1000,
		WHITE_PAWNS_IN_WIN_CELLS = -300,
		BLACK_PAWNS_IN_WIN_CELLS = -500,
		WHITE_IN_BORDERS = -5000,
		WHITE_PAWNS_AROUND_KING = -10,
		BLACK_PAWNS_AROUND_KING = -500;
	
	@Override
	public float getValue(State state) {
		float value =
			WHITE_WIN					* isWhiteWin(state)	+
			BLACK_WIN					* isBlackWin(state)	+
			//DRAW						* isDraw(state) +
			KING_IN_CASTLE				* isKingInCastle(state)	+
			EATEN_WHITE_PAWNS			* getEatenPawns(state, Pawn.WHITE)	+
			EATEN_BLACK_PAWNS			* getEatenPawns(state, Pawn.BLACK)	+
			MANHATTAN_DISTANCE_TO_WIN	* getMinManhattanDistanceFromKingToWin(state)+
			WHITE_PAWNS_IN_WIN_CELLS	* getPawnsInWinCells(state, Pawn.WHITE) +
			BLACK_PAWNS_IN_WIN_CELLS	* getPawnsInWinCells(state, Pawn.BLACK) +
			WHITE_ADJACENT_TO_KING		* getPawnsAdjacentToKing(state, Pawn.WHITE) +
			BLACK_ADJACENT_TO_KING		* getPawnsAdjacentToKing(state, Pawn.BLACK)+
			//WHITE_IN_BORDERS			* getPawnsInBorders(state, Pawn.WHITE)+
			WHITE_PAWNS_AROUND_KING		* getPawnsAroundKing(state, Pawn.WHITE)+
			BLACK_PAWNS_AROUND_KING		* getPawnsAroundKing(state, Pawn.BLACK);
		
		return (float) value;
	}
	
	// FUNZIONI DI ANALISI
	
	private float getPawnsAroundKing(State state, Pawn color) {
		float around = getPawnsInKingsColumn(color, state) + getPawnsInKingsRow(color, state);
		return around;
	}
	
	private float isDraw(State state) {
		return state.getTurn().equals(Turn.DRAW) ? 1 : 0;
	}
	

	protected float isKingInCastle(State state) {
		return (state.getPawn(4,4).equals(Pawn.KING) ? 1 : 0);
	}
	
	protected float getEatenPawns(State state, Pawn color) {
		int total = color.equals(Pawn.WHITE) ? 16 : 8;
		return (float) (total - state.getNumberOf(color));
	}
	
	// TODO check if there are pawns in the path or implement function with possible paths
	protected float getMinManhattanDistanceFromKingToWin(State state) {
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

		return (float) min;
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
	
	protected float getPawnsAdjacentToKing(State state, Pawn color) {
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
		
		return (float) count;
	}
	
	protected float getPawnsInBorders(State state, Pawn color) {
		int count = 0;
		Pawn[][] board = state.getBoard();
		
		for (int[] i : borders) {
			int x = i[0], y = i[1];
			if(board[x][y].equals(color))
				count++;
		}
		return (float)count;
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
