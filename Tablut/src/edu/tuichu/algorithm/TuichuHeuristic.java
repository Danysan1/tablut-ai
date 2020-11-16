package edu.tuichu.algorithm;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class TuichuHeuristic implements TablutHeuristic {
	/*protected Turn maxPlayer;
	
	public TuichuHeuristic(Turn maxPlayer) {
		if(maxPlayer.equals(Turn.WHITE) || maxPlayer.equals(Turn.BLACK))
			this.maxPlayer = maxPlayer;
		else
			throw new IllegalArgumentException("Invalid maxPlayer");
	}*/

	@Override
	public float getValue(State state) {
		// TODO To be implemented
		return 0;
	}

	protected boolean isWinFor(Turn playerWinning, State state) {
		// TODO To be implemented
		return false;
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

	protected int getPawnsAdjacentToKing(State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getPawnsInKingsRow(State state) {
		// TODO To be implemented
		return 0;
	}

	protected int getPawnsInKingsColumn(State state) {
		// TODO To be implemented
		return 0;
	}

}
