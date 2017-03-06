package pacman;

//import java.awt.GraphicsConfiguration;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

public class PacManGUI extends JFrame implements Display, PacManPlayer {

  private PacManGUIGameBoard gameBoard;

  private PacManGUIScoreBoard scoreBoard;

  private PacManGUILivesBoard livesBoard;

  private Move lastMove = Move.NONE, storedMove = Move.NONE;

  public PacManGUI() throws HeadlessException {
    super();
    setupFrame();
  }

  public PacManGUI(String arg0) throws HeadlessException {
    super(arg0);
    setupFrame();
  }

  public void setIsPlayer(boolean isPlayer) {
    scoreBoard.setIsPlayer(isPlayer);
  }

  public boolean isPlayer() {
    return scoreBoard.isPlayer();
  }

  public Move chooseMove(Game game) {
    if (!Game.isValidLocation(Game.getNextLocation(game.getCurrentState().getPacManLocation(),
                                                   storedMove))) {
      if (!Game.isValidLocation(Game.getNextLocation(game.getCurrentState().getPacManLocation(),
                                                     lastMove))) {
        lastMove = Move.NONE;
      }
      return lastMove;
    } else {
      lastMove = storedMove;
      return storedMove;
    }
  }

  public void updateDisplay(Game game) {
    gameBoard.updateDisplay(game);
    scoreBoard.updateDisplay(game);
    livesBoard.updateDisplay(game);
  }

  private void setupFrame() {
    gameBoard = new PacManGUIGameBoard();
    scoreBoard = new PacManGUIScoreBoard(gameBoard);
    livesBoard = new PacManGUILivesBoard(gameBoard);
    this.setResizable(false);

    // setup layout
    setMaximumSize(new Dimension(480, 480));
    this.setBackground(Color.BLACK);

    Container contentPane = getContentPane();
    SpringLayout layout = new SpringLayout();
    contentPane.setLayout(layout);

    // add in graphical pacman board, constraints
    add(gameBoard);
    add(scoreBoard);
    add(livesBoard);

    layout.putConstraint(SpringLayout.SOUTH, contentPane, 0, SpringLayout.SOUTH, livesBoard);
    layout.putConstraint(SpringLayout.WEST, livesBoard, 0, SpringLayout.WEST, contentPane);
    layout.putConstraint(SpringLayout.EAST, contentPane, 0, SpringLayout.EAST, livesBoard);
    layout.putConstraint(SpringLayout.NORTH, livesBoard, 0, SpringLayout.SOUTH, gameBoard);
    layout.putConstraint(SpringLayout.NORTH, livesBoard, 0, SpringLayout.SOUTH, scoreBoard);

    layout.putConstraint(SpringLayout.WEST, gameBoard, 0, SpringLayout.WEST, contentPane);
    layout.putConstraint(SpringLayout.NORTH, gameBoard, 0, SpringLayout.NORTH, contentPane);
    // layout.putConstraint(SpringLayout.SOUTH, contentPane,
    // 0, SpringLayout.SOUTH, gameBoard);

    // layout.putConstraint(SpringLayout.EAST, contentPane,
    // 0, SpringLayout.EAST, gameBoard);

    layout.putConstraint(SpringLayout.WEST, scoreBoard, 0, SpringLayout.EAST, gameBoard);
    layout.putConstraint(SpringLayout.EAST, contentPane, 0, SpringLayout.EAST, scoreBoard);
    layout.putConstraint(SpringLayout.NORTH, scoreBoard, 0, SpringLayout.NORTH, contentPane);
    // layout.putConstraint(SpringLayout.SOUTH, scoreBoard,
    // 0, SpringLayout.SOUTH, contentPane);

    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
//        System.out.println("Key " + keyCode + " pressed");
        switch (keyCode) {
          case 74:
          case 37:
            storedMove = Move.LEFT;
            break;
          case 75:
          case 39:
            storedMove = Move.RIGHT;
            break;
          case 73:
          case 38:
            storedMove = Move.UP;
            break;
          case 77:
          case 40:
            storedMove = Move.DOWN;
            break;
          case 81:
            System.exit(0);
        }
//        System.err.println("Got Move: " + storedMove);
      }
    });

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  // don't think we need these
  /*
   * public PacManGUI(GraphicsConfiguration arg0) { super(arg0); // TODO
   * Auto-generated constructor stub } public PacManGUI(String arg0,
   * GraphicsConfiguration arg1) { super(arg0, arg1); // TODO Auto-generated
   * constructor stub }
   */

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    PacManGUI pframe = new PacManGUI("CS 121 Pacman");
  }
}