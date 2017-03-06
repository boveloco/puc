package pacman;

import javax.swing.JComponent;

import java.awt.*;

public class PacManGUIScoreBoard extends JComponent {

	private int level = 1;
	private int points = 0;
	private int time = 0;
	private int dotsRemaining = 0;
	private boolean isPlayer = true;

	private PacManGUIGameBoard board;
	
	private PacManGUIScoreBoard() {
		this.setPreferredSize(new Dimension(160, 480));
		this.setMaximumSize(new Dimension(160, 480));
		this.setMinimumSize(new Dimension(100, 240));
	}
	
	public PacManGUIScoreBoard(PacManGUIGameBoard board) {
		this();
		this.board = board;
	}

	public void updateDisplay(Game game) {
		level  = game.getLevel();
		points = game.getPoints();
		time   = game.getTime();
		State state = game.getCurrentState();
		if(state != null)
			dotsRemaining = state.getDotLocations().size();
		repaint();
	}
	
	public void setIsPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}
	
	public boolean isPlayer() {
		return isPlayer;
	}

	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width-1, height-1);

		// draw time and points
		int fontSize = 20;
		int indentation = 20;
		int lineHeight = Math.max(10, fontSize);
		g.setFont(new Font("MonoSpaced", Font.BOLD, fontSize));
		g.setColor(Color.RED);
		g.drawLine(0, 0, 0, height);
		g.drawString("CS 121", indentation, lineHeight);
		lineHeight += fontSize;
		g.drawString("Pac-Man", indentation, lineHeight);
		lineHeight += 2*fontSize;
		
		g.setColor(Color.WHITE);
		g.drawString("Time", indentation, lineHeight);
		lineHeight += fontSize;
		g.drawString(Integer.toString(time), 2*indentation, lineHeight);
		lineHeight += 2*fontSize;
		
		g.drawString("Points", indentation, lineHeight);
		lineHeight += fontSize;
		g.drawString(Integer.toString(points), 2*indentation, lineHeight);
		lineHeight += 2*fontSize;
		
		// draw level
		g.drawString("Level " + level, indentation, lineHeight);
		lineHeight += 2*fontSize;
		
		// draw dots
		g.setColor(Color.YELLOW);
		board.drawDot(g, indentation, lineHeight-fontSize/2);
		int pacManWidth = (int)Math.round((double)board.getWidth()/Game.xDim);
		g.drawString(Integer.toString(dotsRemaining), pacManWidth+indentation, lineHeight);
		
		// draw instructions
		g.setFont(new Font("MonoSpaced", Font.BOLD, fontSize));
		g.setColor(Color.MAGENTA);
		lineHeight = height-fontSize/2;
		g.drawString("q: quit", indentation, lineHeight);
		if(isPlayer) {
			lineHeight -= 2*fontSize;
			g.drawString("m", 70, lineHeight);
			lineHeight -= fontSize;
			g.drawString("j  k", 52, lineHeight);
			lineHeight -= fontSize;
			g.drawString("i", 70, lineHeight);
		}
		lineHeight -= 2*fontSize;
		g.drawString("controls:", indentation, lineHeight);
	}
}