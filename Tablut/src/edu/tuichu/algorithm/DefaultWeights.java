package edu.tuichu.algorithm;

import java.util.HashMap;

/**
 * Singleton for default TuichuHeuristic weights.
 * White is max, Black is min.
 */
public class DefaultWeights extends HashMap<TuichuHeuristic.WeightType, Float> {
	private static final long serialVersionUID = 1L;
	private static DefaultWeights instance = null;
	
	private DefaultWeights() {
		super();
		Float pos = Float.valueOf(1),
			neg = Float.valueOf(-1),
			neutr = Float.valueOf(0);
		this.put(TuichuHeuristic.WeightType.WHITEWIN, pos);
		this.put(TuichuHeuristic.WeightType.BLACKWIN, neg);
		this.put(TuichuHeuristic.WeightType.KING_IN_CASTLE, pos);
		this.put(TuichuHeuristic.WeightType.WHITE_PAWNS, pos);
		this.put(TuichuHeuristic.WeightType.BLACK_PAWNS, neg);
		this.put(TuichuHeuristic.WeightType.EATEN_WHITE_PAWNS, neg);
		this.put(TuichuHeuristic.WeightType.EATEN_BLACK_PAWNS, pos);
		this.put(TuichuHeuristic.WeightType.DISTANCE_TO_WIN, pos);
		this.put(TuichuHeuristic.WeightType.MOVES_TO_WIN, pos);
		this.put(TuichuHeuristic.WeightType.WHITE_PAWNS_IN_WIN_CELLS, neutr);
		this.put(TuichuHeuristic.WeightType.BLACK_PAWNS_IN_WIN_CELLS, neg);
		this.put(TuichuHeuristic.WeightType.WHITE_PAWNS_ADJACENT_TO_KING, pos);
		this.put(TuichuHeuristic.WeightType.BLACK_PAWNS_ADJACENT_TO_KING, neg);
		this.put(TuichuHeuristic.WeightType.WHITE_PAWNS_IN_KINGS_ROW, neutr);
		this.put(TuichuHeuristic.WeightType.BLACK_PAWNS_IN_KINGS_ROW, neg);
		this.put(TuichuHeuristic.WeightType.WHITE_PAWNS_IN_KINGS_COLUMN, neutr);
		this.put(TuichuHeuristic.WeightType.BLACK_PAWNS_IN_KINGS_COLUMN, neg);
	}
	
	public static DefaultWeights getInstance() {
		if(instance == null)
			instance = new DefaultWeights();
		
		return instance;
	}
}
