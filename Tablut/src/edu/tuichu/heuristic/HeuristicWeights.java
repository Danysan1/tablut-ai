package edu.tuichu.heuristic;

import java.util.HashMap;

/**
 * Singleton for TuichuHeuristic weights.
 * White is max, Black is min.
 */
public class HeuristicWeights extends HashMap<FactorType, Float> {
	private static final long serialVersionUID = 1L;
	private static HeuristicWeights instance = null;
	
	private HeuristicWeights() {
		super();
		Float pos = Float.valueOf(1),
			neg = Float.valueOf(-1),
			neutr = Float.valueOf(0);
		this.put(FactorType.WHITEWIN, pos);
		this.put(FactorType.BLACKWIN, neg);
		this.put(FactorType.KING_IN_CASTLE, pos);
		this.put(FactorType.WHITE_PAWNS, pos);
		this.put(FactorType.BLACK_PAWNS, neg);
		this.put(FactorType.EATEN_WHITE_PAWNS, neg);
		this.put(FactorType.EATEN_BLACK_PAWNS, pos);
		this.put(FactorType.DISTANCE_TO_WIN, pos);
		this.put(FactorType.MOVES_TO_WIN, pos);
		this.put(FactorType.WHITE_PAWNS_IN_WIN_CELLS, neutr);
		this.put(FactorType.BLACK_PAWNS_IN_WIN_CELLS, neg);
		this.put(FactorType.WHITE_PAWNS_ADJACENT_TO_KING, pos);
		this.put(FactorType.BLACK_PAWNS_ADJACENT_TO_KING, neg);
		this.put(FactorType.WHITE_PAWNS_IN_KINGS_ROW, neutr);
		this.put(FactorType.BLACK_PAWNS_IN_KINGS_ROW, neg);
		this.put(FactorType.WHITE_PAWNS_IN_KINGS_COLUMN, neutr);
		this.put(FactorType.BLACK_PAWNS_IN_KINGS_COLUMN, neg);
	}
	
	public static HeuristicWeights getInstance() {
		if(instance == null)
			instance = new HeuristicWeights();
		
		return instance;
	}
}
