package pacman;

import ghosts.BasicGhostPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.awt.Color;

import util.Pair;
import util.Utils;

/**
 * A class representing a Pac-Man game and its current state. By passing
 * different parameters to the constructors it is possible to create modified
 * versions of the game. The key method is playGame().
 * 
 * @author grenager
 */ 
public class Game {

  // static stuff that is true for all games
  public final static int xDim = 26;

  public final static int yDim = 29;

  private final static int dotPointsValue = 10;

  private final static int defaultNumberGhosts = 4;

  private static Set<Location> allLocations = makeAllLocations();

  private static Location pacManLaunch = new Location(12, 6);

  private static Location ghostLaunch = new Location(12, 18);

  private static Location ghostPen = new Location(10, 15);

  private static int ghostLaunchTimeSpacing = 10;

  // stuff about this instance of the game
  private Display display;

  private int moveTime = 300;

  private List<GhostPlayer> ghostPlayers;

  private State currentState;

  private Move pacManMove;

  private int lives;

  private int level;

  private int points;

  private int time;

  public int getLives() {
    return lives;
  }

  public int getLevel() {
    return level;
  }

  public int getPoints() {
    return points;
  }

  public int getTime() {
    return time;
  }

  public List<GhostPlayer> getGhostPlayers() {
    return ghostPlayers;
  }

  public void setCurrentState(State newState) {
    this.currentState = newState;
  }
  
  public State getCurrentState() {
    return currentState;
  }

  public static Set<Location> getAllLocations() {
    return allLocations;
  }

  public static boolean isValidLocation(Location l) {
    return allLocations.contains(l) || isInGhostPen(l);
  }

  public static boolean isInGhostPen(Location l) {
    return l.getY() == 15 && l.getX() > 9 && l.getX() < 16;
  }

  public List<Move> getLegalPacManMoves() {
    return getLegalPacManMoves(getCurrentState());
  }

  public static List<Move> getLegalPacManMoves(State state) {
    List<Move> moves = new ArrayList<Move>();
    Location curr = state.getPacManLocation();
    for (Move m : Move.values()) {
      if (m == Move.NONE)
        continue; // not a valid move
      Location next = getNextLocation(curr, m);
      if (isValidLocation(next))
        moves.add(m);
    }
    return moves;
  }

  public List<Move> getLegalGhostMoves(int ghostIndex) {
    return getLegalGhostMoves(getCurrentState(), ghostIndex);
  }

  public static List<Move> getLegalGhostMoves(State state, int ghostIndex) {
    List<Move> moves = new ArrayList<Move>();
    Location curr = state.getGhostLocations().get(ghostIndex);
    // ghosts in the ghost pen have to wait
    if (isInGhostPen(curr))
      return Collections.singletonList(Move.NONE);
    Move previousMove = state.getPreviousGhostMoves().get(ghostIndex);
    for (Move m : Move.values()) {
      if (m == Move.NONE)
        continue; // not a valid move
      if (m.getOpposite() == previousMove)
        continue; // can't go backwards
      Location next = getNextLocation(curr, m);
      if (isValidLocation(next))
        moves.add(m);
    }
    return moves;
  }

  /**
   * If players has size 0, then this will return a Set with one element, the
   * empty List.
   * 
   * @param s
   * @param players
   * @return
   */
  public static Set<List<Move>> getLegalCombinedGhostMoves(State s) {
    Set<List<Move>> combinedMoves = new HashSet<List<Move>>();
    List<Move> partialMoves = new ArrayList<Move>();
    getLegalCombinedGhostMovesHelper(s, partialMoves, combinedMoves);
    return combinedMoves;
  }

  /**
   * Recursive implementation.
   * 
   * @param s
   * @param ghosts
   * @param index
   * @param ghostMoves
   */
  private static void getLegalCombinedGhostMovesHelper(State s, List<Move> partialMoves,
                                                       Set<List<Move>> ghostMoves) {
    if (partialMoves.size() == s.getGhostLocations().size()) {
      ghostMoves.add(new ArrayList<Move>(partialMoves));
      return;
    }
    // otherwise, try all ways of adding the next move
    int playerIndex = partialMoves.size();
    List<Move> legalMoves = getLegalGhostMoves(s, playerIndex);
    for (Move m : legalMoves) {
      List<Move> newPartialMoves = new ArrayList<Move>(partialMoves); // a copy
      newPartialMoves.add(m); // add the current choice onto it
      getLegalCombinedGhostMovesHelper(s, newPartialMoves, ghostMoves); // continue
    }
  }

  public static Location getNextLocation(Location l, Move m) {
    Location next = null;
    if (m == Move.LEFT && l.getY() == 15 && l.getX() == 0) {
      next = new Location(25, 15);
    } else if (m == Move.RIGHT && l.getY() == 15 && l.getX() == 25) {
      next = new Location(0, 15);
    } else {
      switch (m) {
        case NONE:
          next = l;
          break;
        case UP:
          next = new Location(l.getX(), l.getY() + 1);
          break;
        case DOWN:
          next = new Location(l.getX(), l.getY() - 1);
          break;
        case LEFT:
          next = new Location(l.getX() - 1, l.getY());
          break;
        case RIGHT:
          next = new Location(l.getX() + 1, l.getY());
          break;
      }
    }
    return next;
  }

  /**
   * State successor function which does not affect any game's current state.
   * 
   * @param s
   * @param pacManMove
   * @param ghostMoves
   * @return
   */
  public static State getNextState(State s, Move pacManMove, List<Move> ghostMoves) {
    List<Location> ghostLocations = s.getGhostLocations();
    if (ghostLocations.size() != ghostMoves.size() && ghostMoves.size() != 0) {
      System.err.println("gl=" + ghostLocations.size() + " gm=" + ghostMoves.size());
      throw new RuntimeException("Wrong number of moves for state.");
    }
    Location newPacManLocation = getNextLocation(s.getPacManLocation(), pacManMove);
    if (!isValidLocation(newPacManLocation))
      throw new RuntimeException("Invalid move.");
    List<Location> newGhostLocations = new ArrayList<Location>();
    List<State> history = s.getHistory();
    for (int i = 0; i < ghostMoves.size(); i++) {
      Location ghostLocation = ghostLocations.get(i);
      Location newGhostLocation = null;
      if (isInGhostPen(ghostLocation) && history.size() > (i * ghostLaunchTimeSpacing)) {
        // bring him out of the ghost pen
        newGhostLocation = ghostLaunch;
      } else {
        newGhostLocation = getNextLocation(ghostLocation, ghostMoves.get(i));
      }
      if (!isValidLocation(newGhostLocation))
        throw new RuntimeException("Invalid move.");
      newGhostLocations.add(newGhostLocation);
    }
    LocationSet newDots = new LocationSet(s.getDotLocations());
    if (newDots.contains(newPacManLocation))
      newDots.remove(newPacManLocation); // eat the dot at that location
    return new State(newPacManLocation, newGhostLocations, newDots, ghostMoves, s);
  }

  /**
   * State successor function which does not affect any game's current state.
   * Only considers PacMan's move.
   * 
   * @param s
   * @param pacManMove
   * @return
   */
  public static State getNextState(State s, Move pacManMove) {
    if (isFinal(s))
      throw new RuntimeException("Can't project a final state.");
    Location newPacManLocation = getNextLocation(s.getPacManLocation(), pacManMove);
    if (!isValidLocation(newPacManLocation))
      throw new RuntimeException("Invalid move.");
    LocationSet newDots = new LocationSet(s.getDotLocations());
    if (newDots.contains(newPacManLocation))
      newDots.remove(newPacManLocation); // eat the dot at that location
    return new State(newPacManLocation, s.getGhostLocations(), newDots, s.getPreviousGhostMoves(),
                     s);
  }

  /**
   * State successor function which does not affect any game's current state.
   * Only considers the ghosts' move.
   * 
   * @param s
   * @param ghostMoves
   * @return
   */
  public static State getNextState(State s, List<Move> ghostMoves) {
    List<Location> ghostLocations = s.getGhostLocations();
    if (ghostLocations.size() != ghostMoves.size())
      throw new RuntimeException("Wrong number of moves for state.");
    List<Location> newGhostLocations = new ArrayList<Location>();
    List<State> history = s.getHistory();
    for (int i = 0; i < ghostMoves.size(); i++) {
      Location ghostLocation = ghostLocations.get(i);
      Location newGhostLocation = null;
      if (isInGhostPen(ghostLocation) && history.size() > (i * ghostLaunchTimeSpacing)) {
        // bring him out of the ghost pen
        newGhostLocation = ghostLaunch;
      } else {
        newGhostLocation = getNextLocation(ghostLocation, ghostMoves.get(i));
      }
      if (!isValidLocation(newGhostLocation))
        throw new RuntimeException("Invalid move.");
      newGhostLocations.add(newGhostLocation);
    }
    LocationSet newDots = new LocationSet(s.getDotLocations());
    return new State(s.getPacManLocation(), newGhostLocations, newDots, ghostMoves, s);
  }
  
  public static Set<State> getPossibleGhostProjections(State s, int distance) {
    Set<Integer> ghosts = new HashSet<Integer>();
    for (int i=0; i<s.getGhostLocations().size(); i++) {
      ghosts.add(i);
    }
    return getPossibleGhostProjections(s, ghosts, distance);
  }
  
  public static Set<State> getPossibleGhostProjections(State s, Set<Integer> whichGhosts, int distance) {
    Set<State> result = new HashSet<State>();
    List<Set<Location>> possibleLocationsByGhost = new ArrayList<Set<Location>>();
    for (int i=0; i<s.getGhostLocations().size(); i++) {
      if (whichGhosts.contains(i)) {
        possibleLocationsByGhost.add(getPossibleGhostLocations(s, i, distance));
      } else {
        possibleLocationsByGhost.add(Collections.singleton(s.getGhostLocations().get(i)));
      }
    }
    getPossibleGhostProjectionsRecursively(s, possibleLocationsByGhost, new LinkedList<Location>(), result);
    return result;
  }

  private static void getPossibleGhostProjectionsRecursively(State s, List<Set<Location>> possibleLocationsByGhost,
                                                      LinkedList<Location> ghostLocations, Set<State> states) {
    if (ghostLocations.size()==s.getGhostLocations().size()) { // we've added locations for all ghosts
      State next = new State(s.getPacManLocation(), new ArrayList<Location>(ghostLocations), s.getDotLocations(), s.getPreviousGhostMoves(), s);
      states.add(next);
      return;
    }
    for (Location loc : possibleLocationsByGhost.get(ghostLocations.size())) {
      ghostLocations.addLast(loc);
      getPossibleGhostProjectionsRecursively(s, possibleLocationsByGhost, ghostLocations, states);
      ghostLocations.removeLast();
    }
  }
  
  public static Set<Location> getPossibleGhostLocations(State s, int ghostIndex, int distance) { 
    Set<Location> result = new HashSet<Location>();
    getPossibleGhostLocationsRecursively(s, ghostIndex, distance, result);
    return result;
  }
  
  private static void getPossibleGhostLocationsRecursively(State s, int ghostIndex, int distance, Set<Location> locations) {
    Location location = s.getGhostLocations().get(ghostIndex);
    if (distance==0 || isFinal(s)) {
      locations.add(location);
      return;
    }
    List<Move> legalMoves = getLegalGhostMoves(s, ghostIndex);
    for (Move m : legalMoves) {
      Location l = getNextLocation(location, m);
      // move this one ghost only
      List<Location> newGhosts = new ArrayList<Location>(s.getGhostLocations());
      newGhosts.set(ghostIndex, l);
      List<Move> newMoves = new ArrayList<Move>(s.getPreviousGhostMoves());
      newMoves.set(ghostIndex, m);
      State next = new State(s.getPacManLocation(), newGhosts, s.getDotLocations(), newMoves, s);
      getPossibleGhostLocationsRecursively(next, ghostIndex, distance-1, locations);
    }
  }
  
  /**
   * Creates the sequence of future states which would result from PacMan continuing in
   * this direction without ever turning around.
   * 
   * @param s
   * @param pacManMove
   * @return
   */
  public static List<State> getProjectedStates(State s, Move pacManMove) {
    return projectPacManLocation(s, pacManMove, Integer.MAX_VALUE);
  }
    /**
     * Creates the sequence of future states which would result from PacMan continuing in
     * this direction without ever turning around.
     * 
     * @param s
     * @param pacManMove
     * @return
     */
  public static List<State> projectPacManLocation(State s, Move pacManMove, int maxLength) {
    List<State> projectedStates = new ArrayList<State>();
    State curr = getNextState(s, pacManMove);
    projectedStates.add(curr);
    List<Move> legalMoves = Game.getLegalPacManMoves(curr);
    legalMoves.remove(pacManMove.getOpposite());
    int length = 0;
    while (!isFinal(curr) && legalMoves.size()==1 && length<maxLength) { // repeat this move
      // until the legal moves
      // have some options
      // beside this move and
      // its opposite
      pacManMove = legalMoves.get(0);
      curr = getNextState(curr, pacManMove);
      projectedStates.add(curr);
      legalMoves = getLegalPacManMoves(curr);
      legalMoves.remove(pacManMove.getOpposite());
      length++;
    }
    // System.err.println(s.getPacManLocation() + " was projected to " +
    // projectedStates.get(projectedStates.size()-1).getPacManLocation());
    return projectedStates;
  }

  public static boolean isWinning(State s) {
    return s.getDotLocations().isEmpty();
  }

  public static boolean isLosing(State s) {
    List<Location> ghostLocations = s.getGhostLocations();
    Location pacManLocation = s.getPacManLocation();
    for (int i = 0; i < ghostLocations.size(); i++) {
      if (ghostLocations.get(i).equals(pacManLocation)) {
        // Pac-Man gets eaten
        return true;
      }
    }
    State last = s.getParent();
    if (last != null) { // check whether they crossed between states
      List<Location> oldGhostLocations = last.getGhostLocations();
      Location oldPacManLocation = last.getPacManLocation();
      for (int i = 0; i < ghostLocations.size(); i++) {
        if (ghostLocations.get(i).equals(oldPacManLocation) && // ghost is
            // where pacman
            // was and
            oldGhostLocations.get(i).equals(pacManLocation)) { // pacman is
          // where ghost
          // was
          // Pac-Man gets eaten
          return true;
        }
      }
    }
    return false;
  }

  public static boolean isFinal(State s) {
    return isWinning(s) || isLosing(s);
  }

  /**
   * Modifies the internal state of the game with a simultaneous Pac-Man move
   * and ghost moves.
   * 
   * @param m
   * @return the current state of the game after moving.
   */
  private State move(Move pacManMove, List<Move> ghostMoves) {
    if (isFinal(getCurrentState()))
      throw new RuntimeException(); // no, can't move
    State nextState = getNextState(getCurrentState(), pacManMove, ghostMoves);
    // update time, points, and state
    time++;
    if (nextState.getDotLocations().size() < currentState.getDotLocations().size())
      points += dotPointsValue;
    currentState = nextState;
    // display the new state
    return nextState;
  }

  // eat the dot at that location
  public static boolean eatDot(LocationSet dotLocations, Location pacManLocation) {
    if (dotLocations.contains(pacManLocation)) {
      dotLocations.remove(pacManLocation);
      return true;
    } else
      return false;
  }

  public static Set<Location> makeAllLocations() {
    Set<Location> allLocations = new HashSet<Location>();
    // DRAW THE HORIZONTAL LINES
    // bottom row
    for (int x = 0; x < xDim; x++) {
      allLocations.add(new Location(x, 0));
    }
    for (int x = 0; x < xDim; x++) {
      if (x != 6 && x != 7 && x != 12 && x != 13 && x != 18 && x != 19)
        allLocations.add(new Location(x, 3));
    }
    for (int x = 0; x < xDim; x++) {
      if (x != 3 && x != 4 && x != 21 && x != 22)
        allLocations.add(new Location(x, 6));
    }
    for (int x = 0; x < xDim; x++) { // gap in the middle
      if (x != 12 && x != 13)
        allLocations.add(new Location(x, 9));
    }
    for (int x = 0; x < xDim; x++) {
      if (x >= 8 && x <= 17)
        allLocations.add(new Location(x, 12));
    }
    // middle row
    for (int x = 0; x < xDim; x++) {
      if (x < 9 || x > 16)
        allLocations.add(new Location(x, 15));
    }
    for (int x = 0; x < xDim; x++) {
      if (x >= 8 && x <= 17)
        allLocations.add(new Location(x, 18));
    }
    for (int x = 0; x < xDim; x++) {
      if (x != 6 && x != 7 && x != 12 && x != 13 && x != 18 && x != 19)
        allLocations.add(new Location(x, 21));
    }
    for (int x = 0; x < xDim; x++) { // ok all across
      allLocations.add(new Location(x, 24));
    }
    // top row
    for (int x = 0; x < xDim; x++) { // gap in middle only
      if (x != 12 && x != 13)
        allLocations.add(new Location(x, 28));
    }

    // DRAW THE VERTICAL LINES
    // far left col
    for (int y = 0; y < yDim; y++) {
      if (y != 4 && y != 5 && (y <= 9 || y >= 21))
        allLocations.add(new Location(0, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y >= 3 && y <= 6)
        allLocations.add(new Location(2, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y != 1 && y != 2)
        allLocations.add(new Location(5, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y != 1 && y != 2 && y != 7 && y != 8 && y != 19 && y != 20 && y != 25 && y != 26
          && y != 27)
        allLocations.add(new Location(8, y));
    }
    // mid-left col
    for (int y = 0; y < yDim; y++) {
      if (y != 4 && y != 5 && y != 10 && y != 11 && y != 13 && y != 14 && y != 15 && y != 16
          && y != 17 && y != 22 && y != 23)
        allLocations.add(new Location(11, y));
    }
    // mid-right col
    for (int y = 0; y < yDim; y++) {
      if (y != 4 && y != 5 && y != 10 && y != 11 && y != 13 && y != 14 && y != 15 && y != 16
          && y != 17 && y != 22 && y != 23)
        allLocations.add(new Location(14, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y != 1 && y != 2 && y != 7 && y != 8 && y != 19 && y != 20 && y != 25 && y != 26
          && y != 27)
        allLocations.add(new Location(17, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y != 1 && y != 2)
        allLocations.add(new Location(20, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y >= 3 && y <= 6)
        allLocations.add(new Location(23, y));
    }
    // far right col
    for (int y = 0; y < yDim; y++) {
      if (y != 4 && y != 5 && (y <= 9 || y >= 21))
        allLocations.add(new Location(25, y));
    }
    return allLocations;
  }

  public Set<Location> getAllLocationsCopy() {
    return new HashSet<Location>(allLocations);
  }

  public String toString() {
    return toString(getCurrentState());
  }

  public String toString(State state) {
    if (state == null)
      return "NULL";
    StringBuilder b = new StringBuilder();
    for (int y = 28; y >= 0; y--) {
      for (int x = 0; x < 26; x++) {
        Location l = new Location(x, y);
        if (state.getPacManLocation().equals(l)) {
          b.append('@');
        } else if (state.getGhostLocations().contains(l)) {
          int index = state.getGhostLocations().indexOf(l);
          String s = ghostPlayers.get(index).getName();
          b.append(s.charAt(0)); // first char of ghost name
        } else if (state.getDotLocations().contains(l)) {
          b.append('o');
        } else if (allLocations.contains(l)) {
          b.append('.');
        } else {
          b.append(' ');
        }
        b.append(' '); // to widen it
      }
      b.append('\n');
    }
    return b.toString();
  }

  public static LocationSet makeStandardDots() {
    LocationSet result = new LocationSet();
    // DRAW THE HORIZONTAL LINES
    // bottom row
    for (int x = 0; x < 26; x++) {
      result.add(new Location(x, 0));
    }
    for (int x = 0; x < 26; x++) {
      if (x != 6 && x != 7 && x != 12 && x != 13 && x != 18 && x != 19)
        result.add(new Location(x, 3));
    }
    for (int x = 0; x < 26; x++) {
      if (x != 3 && x != 4 && x != 21 && x != 22)// && x != 12)
        result.add(new Location(x, 6));
    }
    for (int x = 0; x < 26; x++) { // gap in the middle
      if (x != 12 && x != 13)
        result.add(new Location(x, 9));
    }

    for (int x = 0; x < 26; x++) {
      if (x != 6 && x != 7 && x != 12 && x != 13 && x != 18 && x != 19)
        result.add(new Location(x, 21));
    }
    for (int x = 0; x < 26; x++) { // ok all across
      result.add(new Location(x, 24));
    }
    // top row
    for (int x = 0; x < 26; x++) { // gap in middle only
      if (x != 12 && x != 13)
        result.add(new Location(x, 28));
    }

    // DRAW THE VERTICAL LINES
    // far left col
    for (int y = 0; y < yDim; y++) {
      if (y != 4 && y != 5 && (y <= 9 || y >= 21))
        result.add(new Location(0, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y >= 3 && y <= 6)
        result.add(new Location(2, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y != 1 && y != 2)
        result.add(new Location(5, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y != 1 && y != 2 && y != 7 && y != 8 && (y <= 9 || y >= 21) && y != 25 && y != 26
          && y != 27)
        result.add(new Location(8, y));
    }
    // mid-left col
    for (int y = 0; y < yDim; y++) {
      if (y != 4 && y != 5 && (y <= 9 || y >= 24))
        result.add(new Location(11, y));
    }
    // mid-right col
    for (int y = 0; y < yDim; y++) {
      if (y != 4 && y != 5 && (y <= 9 || y >= 24))
        result.add(new Location(14, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y != 1 && y != 2 && y != 7 && y != 8 && (y <= 9 || y >= 21) && y != 25 && y != 26
          && y != 27)
        result.add(new Location(17, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y != 1 && y != 2)
        result.add(new Location(20, y));
    }
    for (int y = 0; y < yDim; y++) {
      if (y >= 3 && y <= 6)
        result.add(new Location(23, y));
    }
    // far right col
    for (int y = 0; y < yDim; y++) {
      if (y != 4 && y != 5 && (y <= 9 || y >= 21))
        result.add(new Location(25, y));
    }
    return result;
  }

  public static LocationSet makeRandomDots(int numDots) {
    List<Location> allDots = new ArrayList<Location>(makeStandardDots());
    Collections.shuffle(allDots);
    LocationSet dots = new LocationSet();
    for (int i = 0; i < numDots && i < allDots.size(); i++) {
      dots.add(allDots.get(i));
    }
    return dots;
  }

  private List<Location> makeGhostLocations(List<GhostPlayer> ghosts) {
    List<Location> result = new ArrayList<Location>(ghosts.size());
    for (int x = 0; x < ghosts.size(); x++) { // how many ghosts
      result.add(new Location(ghostPen.getX() + x, ghostPen.getY()));
    }
    return result;
  }

  /**
   * Plays a single board of this Pac-Man game. Also manages the display.
   * 
   * @return a final State, either winning or losing
   * @param pacMan
   */
  public void playOneBoardOneLife(final PacManPlayer pacMan) {
    if (display != null) {
      display.updateDisplay(this);
      try {
        // so the user can see it
        Thread.sleep(moveTime * 4);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    int iter = 0;
    while (!Game.isFinal(currentState)) {
      // collect moves from PacManPlayer
      // only give him
//      PlayerThread t = new PlayerThread(this, pacMan);
//      t.start();
      Move lastPacManMove = pacManMove;
      pacManMove = null;
      this.setPacManMove(pacMan.chooseMove(this));
      long startTime = System.currentTimeMillis();
//      synchronized (this) {
//        try {
//          wait(moveTime); // don't wait any longer than moveTime for the Thread
//        } catch (InterruptedException e) {
//        }
//      }
      if (pacManMove==null) {
        System.err.println("No move chosen within " + moveTime + "ms. Using previous move: " + lastPacManMove);
        pacManMove = lastPacManMove;
//        t.stop();
      }
      // now check if it returned a legal move
      if (!getLegalPacManMoves().contains(pacManMove)) {
        pacManMove = Move.NONE;
      }
      if (display != null) { // wait some more if there's a display
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        int remainingTime = Math.max(moveTime-elapsedTime, 0);
        try {
          Thread.sleep(remainingTime);
        } catch (InterruptedException e) {
        }
      }
      List<Move> ghostMoves = new ArrayList<Move>();
      for (int i = 0; i < ghostPlayers.size(); i++) {
        GhostPlayer player = ghostPlayers.get(i);
        Move move = player.chooseMove(this, i);
        ghostMoves.add(move);
      }
      // actually do the move
      move(pacManMove, ghostMoves);
      if (display != null) {
        display.updateDisplay(this);
      }
      iter++;
    } // game over
    try {
      // so the user can see it
      Thread.sleep(moveTime * 4);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public synchronized void setPacManMove(Move pacManMove) {
    this.pacManMove = pacManMove;
    notifyAll();
  }

  /**
   * 
   * @param pacMan
   * @param numLives
   * @return the final game statistics represented as an int array, where the
   *         0th element is the number of points accumulated, the 1st element is
   *         the number of levels completed, and the 2nd element is the total
   *         game time.
   */
  public int[] playGame(PacManPlayer pacMan, int numLives, LocationSet dotLocations) {
  		return playGame(pacMan, numLives, dotLocations, Integer.MAX_VALUE);
  }
  	
  	public int[] playGame(PacManPlayer pacMan, int numLives, LocationSet dotLocations, int maxLevel) {
    lives = numLives;
    level = 1;
    points = 0;
    time = 1;
    List<Move> previousGhostMoves = new ArrayList<Move>();
    for (int i=0; i<ghostPlayers.size(); i++) {
      previousGhostMoves.add(null);
    }
    currentState = new State(pacManLaunch, makeGhostLocations(ghostPlayers), dotLocations, previousGhostMoves,
                             null); // no parent, since it's the initial state
    if (eatDot(currentState.getDotLocations(), currentState.getPacManLocation()))
      points += dotPointsValue;
    while (lives > 0) {
      playOneBoardOneLife(pacMan);
      if (isWinning(currentState)) {
        // completely new board
        currentState = new State(pacManLaunch, makeGhostLocations(ghostPlayers), dotLocations,
                                 null, null); // no parent, since it's the
        // initial state
        if (eatDot(currentState.getDotLocations(), currentState.getPacManLocation()))
          points += dotPointsValue;
        level++;
        if(maxLevel != Integer.MAX_VALUE && level > maxLevel) {
        		break;
        }
        System.err.println("Starting level " + level);
      } else if (isLosing(currentState)) {
        // use the old dot locations, but put Pac-Man and the ghosts back in the
        // launch position
        currentState = new State(pacManLaunch, makeGhostLocations(ghostPlayers),
                                 currentState.getDotLocations(), null, null); // no
        // parent,
        // since
        // it's
        // the
        // initial
        // state
        lives--;
        System.err.println("Remaining lives " + lives);
      }
    }
    
    System.out.println("player:" + this.getClass().getName());
    System.out.println("levels:" + level);
		System.out.println("points:" + points);
		System.out.println("  time:" + time);
		System.out.println(" lives:" + lives);
    return new int[] { points, level, time };
  }

  /**
   * Makes new game, which is set to an initial State.
   * 
   * @param dots
   *          the Set of Locations that have dots
   * @param playerNames
   *          a List of String names of the players, where 0 is always Pac-Man,
   *          and other players are ghosts
   */
  public Game(List<GhostPlayer> ghostPlayers, Display display) {
    this.ghostPlayers = ghostPlayers;
    this.display = display;
  }

  /**
   * Usage: java pacman.GameManager [-pacman PLAYERTYPE] [-ghosts
   * PLAYERTYPE1,PLAYERTYPE2,...] [-dots NUMRANDOMDOTS] [-display DISPLAYTYPE]
   * 
   * @param args
   * @throws ClassNotFoundException
   */
  public static void main(String[] args) throws ClassNotFoundException {
    Map<String, Object> argMap = Utils.parseCommandLineArguments(args, true);
    // choose display
    // text is default
    Display display = new TextDisplay();
    // make pacman player, default keyboard
    PacManPlayer pacMan = new KeyboardPacManPlayer();
    int maxLevel = Integer.MAX_VALUE;
    if (argMap.containsKey("-levels")) {
    		maxLevel = ((Double)argMap.get("-levels")).intValue();
    }
    if (argMap.containsKey("-display")) {
      String displayName = (String) argMap.get("-display");
      if (displayName.equals("none")) {
        display = null;
      } else if (displayName.equals("text")) {
        display = new TextDisplay();
      } else if (displayName.equals("gui")) {
        display = new PacManGUI("CS 121 Pac-Man GUI");
        pacMan = (PacManGUI) display;
      }
    }
    if (argMap.containsKey("-pacman")) {
      String pacManType = (String) argMap.get("-pacman");
      pacMan = (PacManPlayer) Utils.getNewObjectByName(pacManType, new Class[0], new Object[0]);
      if (display instanceof PacManGUI)
        ((PacManGUI) display).setIsPlayer(false);
    }
    // make ghost players, defaults to four BasicGhostPlayers
    List<GhostPlayer> ghostPlayers = new ArrayList<GhostPlayer>();
    int ghostIndex = 0;
    if (argMap.containsKey("-ghosts")) {
      String ghostTypes = (String) argMap.get("-ghosts");
      String[] ghostArray = ghostTypes.split(",");
      for (; ghostIndex < ghostArray.length; ghostIndex++) {
        String name = ghostIndex + ghostArray[ghostIndex];
        Color color = Color.LIGHT_GRAY;
        switch (ghostIndex % 7) {
          case 0:
            color = Color.RED;
            break;
          case 1:
            color = Color.PINK;
            break;
          case 2:
            color = Color.CYAN;
            break;
          case 3:
            color = Color.ORANGE;
            break;
          case 4:
            color = Color.MAGENTA;
            break;
          case 5:
            color = Color.GREEN;
            break;
          case 6:
            color = Color.GRAY;
            break;
        }
        GhostPlayer ghost = (GhostPlayer) Utils.getNewObjectByName(ghostArray[ghostIndex],
                                                                   new Class[0], new Object[0]);
        ghost.setColor(color);
        ghost.setName(name);
        ghostPlayers.add(ghost);
      }
    } else {
      for (; ghostIndex < defaultNumberGhosts; ghostIndex++) {
        String name = ghostIndex + "BasicGhostPlayer";
        Color color = Color.LIGHT_GRAY;
        switch (ghostIndex % 7) {
          case 0:
            color = Color.RED;
            break;
          case 1:
            color = Color.PINK;
            break;
          case 2:
            color = Color.CYAN;
            break;
          case 3:
            color = Color.ORANGE;
            break;
          case 4:
            color = Color.MAGENTA;
            break;
          case 5:
            color = Color.GREEN;
            break;
          case 6:
            color = Color.GRAY;
            break;
        }
        GhostPlayer ghost = new BasicGhostPlayer();
        ghost.setColor(color);
        ghost.setName(name);
        ghostPlayers.add(ghost);
      }
    }
    System.err.println("PacMan: " + pacMan);
    System.err.println("Ghosts: " + ghostPlayers);
    // choose dots
    LocationSet dots = Game.makeStandardDots();
    if (argMap.containsKey("-dots")) {
      int numDots = (int) (double) (Double) argMap.get("-dots");
      dots = Game.makeRandomDots(numDots);
    }
    // set up game
    Game game = new Game(ghostPlayers, display);
    // play
    int[] stats = game.playGame(pacMan, 3, dots, maxLevel);
    System.err.println("Points: " + stats[0]);
    System.err.println("Levels: " + stats[1]);
    System.err.println("Time:   " + stats[2]);
  }
}

//class PlayerThread extends Thread {
//
//  Game game;
//  PacManPlayer player;
//
//  public void run() {
//    game.setPacManMove(player.chooseMove(game)); // sets the move in this game
//  }
//
//  public PlayerThread(Game game, PacManPlayer player) {
//    this.game = game;
//    this.player = player;
//  }
//}