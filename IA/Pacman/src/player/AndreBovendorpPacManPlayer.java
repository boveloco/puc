package player;
import java.util.List;
import java.util.Random;

import pacman.Game;
import pacman.Move;
import pacman.PacManPlayer;

public class AndreBovendorpPacManPlayer implements PacManPlayer {

	int g = -1;
	@Override
	public Move chooseMove(Game game) {
		List<Move> legalMoves = game.getLegalPacManMoves();
		Random r = new Random();
		int n = r.nextInt(legalMoves.size());
		return legalMoves.get(n);
	}
	
}
