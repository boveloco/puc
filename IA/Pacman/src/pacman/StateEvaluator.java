package pacman;

/**
 * An interface for classes which can estimate the value of a state in a Pac-Man game.
 * @author grenager
 *
 */
public interface StateEvaluator {
  
  /**
   * Computes an estimate of the value of the State.
   * @param s the State to evaluate.
   * @return an estimate of the value of the State.
   */
  double evaluateState(State s);
}
