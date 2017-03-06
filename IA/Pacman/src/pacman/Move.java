/**
 * 
 */
package pacman;

/**
 * Represents a Move for one of the agents in the Pac-Man game: Pac-Man or any of the ghosts.
 * @author grenager
 *
 */
public enum Move {
  NONE("NONE"), UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT("RIGHT");
  String name;
  public Move getOpposite() {
    if (this==UP) return DOWN;
    if (this==DOWN) return UP;
    if (this==LEFT) return RIGHT;
    if (this==RIGHT) return LEFT;
    throw new RuntimeException();
  }
  private Move(String name) {
    this.name = name;
  }
}