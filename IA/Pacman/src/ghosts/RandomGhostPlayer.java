package ghosts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pacman.Game;
import pacman.State;
import pacman.GhostPlayer;
import pacman.Move;
import util.Pair;

public class RandomGhostPlayer extends GhostPlayer {
  
  // seeded with current time...for debugging,
  // pass in a seed of your choice to control
  // the sequence of random numbers generated
  // as follows:
  //
  // private Random random = new Random(long seed);
  private Random random = new Random();

  public Move chooseMove(Game game, int ghostIndex) {
		List<Pair<Move, Double>> distribution = getMoveDistribution(game, game.getCurrentState(), ghostIndex);
		double dart = random.nextDouble();
		double sum = 0.0;
		try {
	    for(Pair<Move, Double> pair : distribution) {
	    		sum += pair.second();
	    		if(sum >= dart) {
	    			return pair.first();
	    		}
	    }
	    throw new RuntimeException("No move selected in" + this.getClass().getName() + " for 'dart'=" + dart);
		} catch(RuntimeException re) {}
		return null;
	}
  
  public List<Pair<Move, Double>> getMoveDistribution(Game game, State state, int ghostIndex) {
		List<Pair<Move, Double>> distribution = new ArrayList<Pair<Move, Double>>();
		List<Move> legalMoves = Game.getLegalGhostMoves(state, ghostIndex);
		double uniformProb = 1.0 / (double)legalMoves.size();
		for(Move move : legalMoves) {
				distribution.add(new Pair<Move, Double>(move, uniformProb));
		}
		return distribution;
  }
}