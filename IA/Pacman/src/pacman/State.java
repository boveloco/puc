package pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class representing a state of a Pac-Man board, as defined by the pacman.Game class.
 * Contains the location of Pac-Man and the ghosts, as well as the remaining dots on the board.
 *    
 * Equals and hashCode are defined on all fields EXCEPT the parent.
 * 
 * @author grenager
 *
 */
public class State {
  
  Location pacManLocation;
  
  List<Location> ghostLocations;
  
  LocationSet dotLocations;
  
  List<Move> previousGhostMoves;
  
  State parent;
  
  public Location getPacManLocation() {
    return pacManLocation;
  }
  
  public List<Location> getGhostLocations() {
    return ghostLocations;
  }
  
  public LocationSet getDotLocations() {
    return dotLocations;
  }
  
  public List<Move> getPreviousGhostMoves() {
    return previousGhostMoves;
  }
  
  public State getParent() {
    return parent;
  }
  
  public List<State> getHistory() {
    List<State> history = new ArrayList<State>();
    State s = this;
    while (s!=null) {
      history.add(s);
      s = s.getParent();
    }
    Collections.reverse(history); // now it should end with this
    return history;
  }
  
  /**
   * Equals and hashCode are defined on all fields EXCEPT the parent.
   */
  public boolean equals(Object o) {
    if (! (o instanceof State)) return false;
    State s = (State) o;
    if (s.pacManLocation==null ? pacManLocation!=null : !s.pacManLocation.equals(pacManLocation)) return false;
    if (s.ghostLocations==null ? ghostLocations!=null : !s.ghostLocations.equals(ghostLocations)) return false;
    if (s.dotLocations==null ? dotLocations!=null : !s.dotLocations.equals(dotLocations)) return false;
    if (s.previousGhostMoves==null ? previousGhostMoves!=null : !s.previousGhostMoves.equals(previousGhostMoves)) return false;
    return true;
  }
  
  /**
   * Equals and hashCode are defined on all fields EXCEPT the parent.
   */
  public int hashCode() {
    int result = 17;
    result = result*37 + (pacManLocation==null ? 0 : pacManLocation.hashCode());
    result = result*37 + (ghostLocations==null ? 0 : ghostLocations.hashCode());
    result = result*37 + (dotLocations==null ? 0 : dotLocations.hashCode());
    result = result*37 + (previousGhostMoves==null ? 0 : previousGhostMoves.hashCode());
    return result;
  }
  
  public String toString() {
    String result = "pacman=" + pacManLocation;
    if (!ghostLocations.isEmpty()) result += " ghosts=" + ghostLocations;
    return result;
  }
  
  public State(Location pacManLocation, List<Location> ghostLocations, LocationSet dotLocations, 
               List<Move> previousGhostMoves,
               State parent) {
    this.pacManLocation = pacManLocation;
    this.ghostLocations = ghostLocations;
    this.dotLocations = dotLocations;
    this.previousGhostMoves = previousGhostMoves;
    this.parent = parent;
  }

}