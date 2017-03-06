package pacman;

/**
 * An interface for classes that can choose Pac-Man's moves, conditioned
 * on the history and the ghost players.
 * @author grenager
 *
 */
public interface PacManPlayer {
  
  /**
   * Chooses a Move for Pac-Man in the Game.
   * @param game
   * @return a Move for Pac-Man
   */
  Move chooseMove(Game game);
  
}
