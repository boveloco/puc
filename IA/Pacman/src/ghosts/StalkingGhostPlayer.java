package ghosts;

import java.util.List;

import pacman.Game;
import pacman.GhostPlayer;
import pacman.Location;
import pacman.Move;
import pacman.State;
import util.*;


public class StalkingGhostPlayer extends GhostPlayer {
  
  /**
   * Chooses a move deterministically based on the last State in the history.
   * Tries to come after Pac-Man, using straightline distance.
   * This means that this ghost can reverse direction.
   */
  public Move chooseMove(Game game, int ghostIndex) {
    State state = game.getCurrentState();
    List<Move> moves = game.getLegalGhostMoves(ghostIndex);
    if (moves.isEmpty()) return null;
    double[] distances = new double[moves.size()];
    Location pacManLoc = state.getPacManLocation();
    for (int i=0; i<distances.length; i++) {
      Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghostIndex), moves.get(i));
      distances[i] = Location.euclideanDistance(pacManLoc, newLoc);
    }
    int moveIndex = Utils.argmin(distances); // the move that minimizes the distance to PacMan
    return moves.get(moveIndex);
  }

}
