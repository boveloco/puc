package player;

import pacman.*;

/**
 * Use this class for your basic DFS player implementation.
 * @author grenager
 *
 */
public class DFSPacManPlayer implements PacManPlayer, StateEvaluator {

  /**
   * Chooses a Move for Pac-Man in the Game.
   * 
   * @param game
   * @return a Move for Pac-Man
   */
  public Move chooseMove(Game game) {
    return null;
  }

  /**
   * Computes an estimate of the value of the State.
   * @param s the State to evaluate.
   * @return an estimate of the value of the State.
   */
  public double evaluateState(State state) {
    if (Game.isLosing(state))
      return Double.NEGATIVE_INFINITY;
    return -state.getDotLocations().size();
  }

}
