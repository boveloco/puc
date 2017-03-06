package pacman;

import javax.swing.*;
import java.awt.*;

import java.util.Set;
import java.util.List;
import java.lang.Math;


public class PacManGUIGameBoard extends JComponent {

	private Set<Location> board = null;
	private State currentState = null,
					  oldState = null;
	private List<GhostPlayer> ghosts = null;
	private Move direction = Move.LEFT;
	
	public PacManGUIGameBoard() {
		super();
		setMinimumSize(new Dimension(240, 240));
		setPreferredSize(new Dimension(480, 480));
		setMaximumSize(new Dimension(480, 480));
	}
	
	public void updateDisplay(Game game) {
		if(board == null) {
			board = game.getAllLocationsCopy();
			oldState = game.getCurrentState();
			currentState = oldState;
			ghosts = game.getGhostPlayers();
			repaint();
		} else {
			oldState = currentState;
			currentState = game.getCurrentState();
			//compute differences, call proper repaint
			repaint();
		}
	}

	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		double resX = (double)width / Game.xDim;
		double resY = (double)height / Game.yDim;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width-1, height-1);
		g.setColor(Color.RED);
		g.drawLine(0, 0, width-1, 0);
		g.drawLine(width-1, 0, width-1, height-1);
		g.drawLine(0, height-1, width-1, height-1);
		g.drawLine(0, height-1, 0, 0);
		for(int y = Game.yDim-1; y >= 0; y--) {
			for (int x = 0; x < Game.xDim; x++) {
				Location l = new Location(x, y);
		        if(currentState.getPacManLocation().equals(l)) {
		        		if(currentState.getPacManLocation().getX()
		        				< oldState.getPacManLocation().getX())
		        			direction = Move.LEFT;
		        		else if(currentState.getPacManLocation().getX()
		        				> oldState.getPacManLocation().getX())
		        			direction = Move.RIGHT;
		        		else if(currentState.getPacManLocation().getY()
		        				< oldState.getPacManLocation().getY())
		        			direction = Move.DOWN;
		        		else if(currentState.getPacManLocation().getY()
		        				> oldState.getPacManLocation().getY())
		        			direction = Move.UP;
//		        		System.err.println(direction.toString());
		        		drawPacMan(g, (int)Math.round(resX*l.getX()),
		        				   (int)Math.round(resY*(Game.yDim-1-l.getY())),
		        				    direction);
		        } else if (currentState.getGhostLocations().contains(l)) {
		        		int index = currentState.getGhostLocations().indexOf(l);
		        		drawGhost(g, (int)Math.round(resX*l.getX()),
		        				  (int)Math.round(resY*(Game.yDim-1-l.getY())),
		        				  ghosts.get(index));
		        } else if (currentState.getDotLocations().contains(l)) {
		        		drawDot(g, (int)Math.round(resX*l.getX()+resX/3),
		        				(int)Math.round(resY*(Game.yDim-1-l.getY())+resY/3));;
		        } else if (!board.contains(l)) {
		        		g.setColor(Color.BLUE);
		        		g.fillRect((int)Math.round(resX*l.getX()+1),
		        				(int)Math.round(resY*(Game.yDim-1-l.getY())+1),
		        				(int)Math.round(resX-2),
		        				(int)Math.round(resY-2));
		        }
			}
		}
	}
	
	public void drawPacMan(Graphics g, int xc, int yc, Move direction) {
		g.setColor(Color.YELLOW);
		int pacManLeft = xc+1, pacManUp = yc+1,
			pacManWidth = (int)Math.round((double)getWidth() / Game.xDim-3),
			pacManHeight = (int)Math.round((double)getHeight() / Game.yDim-3);
		g.fillOval(pacManLeft, pacManUp, pacManWidth, pacManHeight);
		g.setColor(Color.BLACK);
		int angle = -22;
		switch(direction) {
			case LEFT:
				angle += 180; break;
			case UP:
				angle += 90; break;
			case RIGHT:
				angle += 360; break;
			case DOWN:
				angle += 270; break;
		}
		g.fillArc(pacManLeft, pacManUp, pacManWidth, pacManHeight,
				  angle, 45);
	}
	
	public void drawGhost(Graphics g, int xc, int yc, GhostPlayer ghost) {
		g.setColor(ghost.getColor());
		int width = (int)Math.round((double)getWidth() / Game.xDim);
		int height = (int)Math.round((double)getHeight() / Game.yDim);
		int leftEdge = xc+1, rightEdge = width-2;
		int upEdge = yc+1;
		g.fillArc(leftEdge, upEdge, rightEdge, height*5/3, 0, 180);
		g.setFont(new Font("MonoSpaced", Font.BOLD, 10));
		int fontY = yc-2, fontX = xc+(int)Math.ceil((double)width/2)+2;
		if(fontY < 1) {
			fontY += height;
			fontX -= width;
		}
		g.drawString(ghost.getName().substring(0,1),
					fontX, fontY);
		g.setColor(Color.WHITE);
		g.fillOval(leftEdge+(int)Math.ceil((double)width/5),
				   upEdge+(int)((double)height/4),
				   width/5, height/5);
		g.fillOval(leftEdge+(int)Math.ceil(3*(double)width/5),
				   upEdge+(int)((double)height/4), 
				   width/5, width/5);
		g.setColor(Color.BLUE);
		g.fillOval(leftEdge+(int)Math.ceil(3*(double)width/10),
				   upEdge+(int)(3*(double)height/8),
				   width/10, height/10);
		g.fillOval(leftEdge+(int)Math.ceil(7*(double)width/10),
				   upEdge+(int)(3*(double)height/8), 
				   width/10, width/10);
	}
	
	public void drawDot(Graphics g, int xc, int yc) {
		g.setColor(Color.WHITE);
		g.fillOval(xc, yc,
				   (int)Math.round((double)getWidth() / Game.xDim /5),
				   (int)Math.round((double)getHeight() / Game.yDim /5));
	}
}