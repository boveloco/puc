package ghosts;

import java.util.ArrayList;
import java.util.List;

import pacman.Game;
import pacman.GhostPlayer;
import pacman.Location;
import pacman.Move;
import pacman.State;

/**
 * A player that has roughly the behavior of the ghosts in the real Pac-Man game:
 * - deterministic as a function of the entire state history
 * - doesn't reverse direction (thus not deterministic conditioned only on current state)
 * - doesn't always pursue Pac-Man
 * - doesn't get stuck in loops
 * - covers much of the board
 * 
 * At any given point in time, it has some part of the board that it wants to be near.
 * Sometimes this is a particular dot, and sometimes it is Pac-Man himself.
 * 
 * @author grenager
 */
public class BasicGhostPlayer extends GhostPlayer {
  
  private static int periodLength = 40;
  private static int numPeriodTypes = 10;
  
  private Location target = null; // if null, then it's pacman himself
  private Move lastMove = null;
  
  public Move chooseMove(Game game, int ghostIndex) {
    State s = game.getCurrentState();
    Location target = getTarget(s, ghostIndex);
    Location oldLoc = s.getGhostLocations().get(ghostIndex);
    List<Move> legalMoves = game.getLegalGhostMoves(ghostIndex);
    Move bestMove = null;
    double minDistance = Double.POSITIVE_INFINITY;
    for (Move m : legalMoves) {
      Location newLoc = Game.getNextLocation(oldLoc, m);
      double distance = Location.euclideanDistance(newLoc, target);
      if (distance<minDistance) {
        minDistance = distance;
        bestMove = m;
      }
    }    
    if (bestMove==null) throw new RuntimeException("Legal moves for ghost "+ghostIndex+": " + legalMoves);
    lastMove = bestMove;
    return bestMove;
  }
  
  private Location getTarget(State s, int ghostIndex) {
    int step = s.getHistory().size()-1;
    int numGhosts = s.getGhostLocations().size();
    if (step%periodLength==(ghostIndex*(periodLength/numGhosts))) { // this makes ghosts change behavior at different points in the period
      // pick a new target
      int period = step/periodLength;
      int type = period%numPeriodTypes;
      if (type==numPeriodTypes-(ghostIndex*2)-1) { // one of the types
        // pick pacman as the target
        target = null; 
      } else if (type==numPeriodTypes-(ghostIndex*2)-2) { // another of the types
        // pick one of the remaining dots as the target
        List<Location> dotList = new ArrayList<Location>(s.getDotLocations());
        target = dotList.get((step+ghostIndex)%dotList.size());
      } else { // the rest of the types
        // otherwise, pick any location to protect
        List<Location> dotList = new ArrayList<Location>(Game.getAllLocations());
        target = dotList.get((step+ghostIndex)%dotList.size());
      }
//      System.err.println("Ghost " + ghostIndex + " picked new target: " + target);
    }
    if (target!=null) return target;
    return s.getPacManLocation();
  }
  
}
