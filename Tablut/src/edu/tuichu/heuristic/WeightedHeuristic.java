package edu.tuichu.heuristic;

import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public abstract class WeightedHeuristic implements TablutHeuristic {
	protected Map<FactorType,Float> weights;
	
	/**
	 * Create heuristic with custom weights
	 * 
	 * @param weights Custom weights
	 */
	public WeightedHeuristic(Map<FactorType,Float> weights) {
		if(weights == null)
			throw new IllegalArgumentException("Null weights");
		
		this.weights = weights;
	}

	@Override
	public float getValue(State state) {
		Map<FactorType,Number> factors = getFactors(state);
		Float zero = Float.valueOf(0);
		float value = 0;
		for(FactorType t : factors.keySet())
			value += (Float)factors.get(t) * this.weights.getOrDefault(t, zero);
		return value;
	}
	
	protected abstract Map<FactorType, Number> getFactors(State state);
}
