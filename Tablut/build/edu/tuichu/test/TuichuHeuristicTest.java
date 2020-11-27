package edu.tuichu.test;

import edu.tuichu.heuristic.TuichuHeuristic;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;


public class TuichuHeuristicTest {

	public static void main(String[] args) {

		StateTablut state = new StateTablut();
		System.out.println(state);
		
		TuichuHeuristic t = new TuichuHeuristic();
		System.out.println(t.getValue(state));

	}
}
