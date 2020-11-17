package edu.tuichu.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;


public class Test {

   public static void main(String[] args) {

      	StateTablut state = new StateTablut();
	TuichuHeuristic t = new TuichuHeuristic();
	System.out.println(t.getValue(state));

   }
}
