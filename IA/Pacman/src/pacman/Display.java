package pacman;

/**
 * An interface for classes which can display the Pac-Man game.
 * @author grenager
 *
 */
public interface Display {
  
  /**
   * Update this display with the current state of the game.
   * @param game
   */
  void updateDisplay(Game game);
  
}
