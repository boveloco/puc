package pacman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class KeyboardPacManPlayer implements PacManPlayer {
	
  /**
   * Ignores the game, gets a move from the keyboard
   */
  public Move chooseMove(Game game) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    List<Move> legalMoves = Game.getLegalPacManMoves(game.getCurrentState());
//    System.err.println("Legal moves: " + legalMoves);
    System.out.print("Enter move (i/j/k/m): ");
    while (true) {
      try {
        String s = br.readLine();
        if (s.length() == 0)
          continue;
        char c = s.charAt(0);
        Move m = null;
        switch (c) {
          case 'j':
            m= Move.LEFT; break;
          case 'k':
            m= Move.RIGHT; break;
          case 'i':
            m= Move.UP; break;
          case 'm':
            m= Move.DOWN; break;
          case 'q':
            System.exit(0);
        }
        if (legalMoves.contains(m)) return m;
      } catch (IOException e) {
        // do nothing
      }
      System.out.print("Enter move (i/j/k/m): "); // ask again
    }
  }
    
}
