package pacman;

import javax.swing.JComponent;

import java.awt.*;

public class PacManGUILivesBoard extends JComponent {

	private int lives = 0;

	private PacManGUIGameBoard board;
	
	private PacManGUILivesBoard() {
		this.setPreferredSize(new Dimension(480, 25));
		this.setMaximumSize(new Dimension(480, 25));
		this.setMinimumSize(new Dimension(240, 25));
	}
	
	public PacManGUILivesBoard(PacManGUIGameBoard board) {
		this();
		this.board = board;
	}

	public void updateDisplay(Game game) {
		lives  = game.getLives();
		repaint();
	}

	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width-1, height-1);
		g.setColor(Color.RED);
		g.drawLine(0, 0, width, 0);
		
		// draw lives
		int pacManWidth = (int)Math.round((double)board.getWidth()/Game.xDim);
		int pacManHeight = (int)Math.round((double)board.getHeight()/Game.yDim);
		g.setColor(Color.YELLOW);
		for(int i = 0; i < lives; i++)
			board.drawPacMan(g, 1+i*(2+pacManWidth),
							height-3-pacManHeight,
							Move.LEFT);
	}
}