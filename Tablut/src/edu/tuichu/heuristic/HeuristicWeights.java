package edu.tuichu.heuristic;

import java.util.HashMap;

/**
 * Singleton for TuichuHeuristic weights. White is max, Black is min.
 */
public class HeuristicWeights extends HashMap<FactorType, Integer> {
	private static final long serialVersionUID = 1L;
	private static HeuristicWeights instance = null;

	private HeuristicWeights() {
		super();
		int pos = 1;
		int neg = -1;
		int neu = 0;
		
		this.put(FactorType.WHITEWIN, 10000);
		this.put(FactorType.BLACKWIN, -10000);
		this.put(FactorType.KING_IN_CASTLE, 50);
		this.put(FactorType.EATEN_WHITE_PAWNS, -100);
		this.put(FactorType.EATEN_BLACK_PAWNS, 100);
		this.put(FactorType.DISTANCE_TO_WIN, -100);
		this.put(FactorType.WHITE_PAWNS_IN_WIN_CELLS, -200);
		this.put(FactorType.BLACK_PAWNS_IN_WIN_CELLS, -200);
		this.put(FactorType.WHITE_PAWNS_ADJACENT_TO_KING, 50);
		this.put(FactorType.BLACK_PAWNS_ADJACENT_TO_KING, -300);
		this.put(FactorType.WHITE_AROUND_KING, 0);
		this.put(FactorType.BLACK_AROUND_KING, -250);
	}

	public static HeuristicWeights getInstance() {
		if (instance == null)
			instance = new HeuristicWeights();

		return instance;
	}
}
