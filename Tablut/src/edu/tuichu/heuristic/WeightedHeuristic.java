package edu.tuichu.heuristic;

import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public abstract class WeightedHeuristic implements TablutHeuristic {
	protected Map<FactorType,Integer> weights;
	
	/**
	 * Create heuristic with custom weights
	 * 
	 * @param weights Custom weights
	 */
	public WeightedHeuristic(Map<FactorType,Integer> weights) {
		if(weights == null)
			throw new IllegalArgumentException("Null weights");
		
		this.weights = weights;
	}

	@Override
	public float getValue(State state) {
		Map<FactorType,Float> factors = getFactors(state);
		Integer zero = Integer.valueOf(0);
		int value = 0;
		for(FactorType t : factors.keySet()){
			value += factors.get(t) * this.weights.getOrDefault(t, zero);
		}
		return value;
	}
	
	protected abstract Map<FactorType, Float> getFactors(State state);
}
