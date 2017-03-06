package player;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import util.*;
import pacman.*;

/**
 * A very simple implementation of the PacManPlayer interface created by
 * the course staff. This is an
 * example of what you should create for Project 0 (but you can be more
 * creative!).
 * 
 * @author grenager
 */
public class SimplePacManPlayer implements PacManPlayer {

  static Random random = new Random();

  private Move lastMove = null;

  /**
   * This is the only public method, required by the PacManPlayer interface.
   */
  public Move chooseMove(Game game) {
	//Obtem o estado atual do jogo:  
    State s = game.getCurrentState();
    
    //Obtem a lista de movimentos possíveis e válidos:
    List<Move> legalMoves = game.getLegalPacManMoves(); 
    
    //Cria um vetor de objetos do tipo Counter:
    Counter<Move> scores = new Counter<Move>(); 
    
    //Utiliza um Iterator para percorrer a lista de movimentos possíveis e válidos:
    for (Move m : legalMoves) {
      
      //Obtém uma lista de estados considerando que o pacman siga por um
      //determinado movimento até encontrar uma nova interseção. 
      List<State> stateList = Game.getProjectedStates(s, m); 
      
      //Obtém o último estado da lista do caminho projetado:
      State last = stateList.get(stateList.size() - 1);
      
      //Obtém uma avaliação heurística do último estado da lista do caminho projetado:
      double stateScore = evaluateState(s, last);

      //Adiciona uma penalidade para evitar que o pacman volte pelo caminho que veio.
      double turnaroundPenalty = (lastMove == m.getOpposite() ? -10.0 : 0.0);
      
      //Adiciona ao Counter o último estado e sua respectiva avaliação. 
      scores.setCount(m, stateScore + turnaroundPenalty); 
      
    }
    
    //Utiliza uma função "soft-max" para normalizar os valores:
    scores = scores.exp().normalize(); 
    Move move = scores.sampleFromDistribution(random); 
    
    //Armazena o último movimento feito:
    lastMove = move; 
    return move;
  }

  public double evaluateState(State s, State next) {
    if (Game.isLosing(next))
      return -1000.0; // really bad to get eaten
    if (Game.isWinning(next))
      return 0.0; // really good to win
    double score = 0.0;
    Location pacManLocation = next.getPacManLocation();
    score -= s.getDotLocations().size(); // more dots left on the board is bad
    score -= getMinDistance(pacManLocation, s.getDotLocations()); // being far
                                                                  // from
                                                                  // nearest dot
                                                                  // is bad
    score += getMinDistance(pacManLocation, s.getGhostLocations()); // being far
                                                                    // from
                                                                    // nearest
                                                                    // ghost is
                                                                    // good
    return score;
  }

  /**
   * Returns the distance from the source location to the closest of the target
   * locations.
   * 
   * @param sourceLocation
   * @param targetLocations
   * @return
   */
  private double getMinDistance(Location sourceLocation, Collection<Location> targetLocations) {
    double minDistance = Double.POSITIVE_INFINITY;
    for (Location dotLocation : targetLocations) {
      double distance = Location.manhattanDistance(sourceLocation, dotLocation); // one
                                                                                  // way
                                                                                  // to
                                                                                  // measure
                                                                                  // distance
      if (distance < minDistance) {
        minDistance = distance;
      }
    }
    if (Double.isInfinite(minDistance))
      throw new RuntimeException();
    return minDistance;
  }

}
