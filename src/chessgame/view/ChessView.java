package chessgame.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import chessgame.components.Movement;
import chessgame.components.Piece;
import chessgame.components.PieceColor;
import chessgame.components.Position;
import chessgame.controller.ChessController;
import chessgame.model.PieceType;

/**
 * @author Adam Snaider
 *
 */
public class ChessView {
  private BoardPanel boardPanel;
  private JFrame frame;
  private ChessController controller;
  private Map<Position, Piece> pieces;
  private PieceColor myColor;

  /**
   * Creates a new ChessView
   * 
   * @param controller
   *          Controller where the game is taking place.
   */
  public ChessView(ChessController controller, PieceColor myColor) {
    this.controller = controller;
    this.myColor = myColor;
  }

  /**
   * Initializes the view. Creates a JFrame and a BoardPanel
   */
  public void initialize() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int smallest = screenSize.width < screenSize.height ? (int) (screenSize.width * 0.8)
            : (int) (screenSize.height * 0.8);
        frame.setMinimumSize(new Dimension(200, 200));
        frame.setSize(smallest, smallest);
        //frame.setResizable(false);
        boardPanel = new BoardPanel(ChessView.this, controller, myColor);
        frame.add(boardPanel);
        frame.setVisible(true);
      }
    });
  }

  /**
   * Repaints the JFrame.
   */
  public void repaint() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        frame.repaint();
      }
    });
  }

  /**
   * Update the board.
   * 
   * @param pieces
   */
  public void update(Map<Position, Piece> pieces) {
    this.pieces = pieces;
  }

  /**
   * Returns a piece specified by its position.
   * 
   * @param pos
   *          Position of the board.
   * @return The piece that corresponds to that position.
   */
  public Piece getPiece(Position pos) {
    return pieces.get(pos);
  }

  /**
   * Creates a view which is used to convert a pawn who reached the other side.
   * 
   * @param myColor
   *          Color of the pawn that reached the other side.
   */
  public void choosePiece(final PieceColor color) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        PawnToEndScreen pawnToEndScreen = new PawnToEndScreen(frame, color,
            boardPanel.getImages());
        pawnToEndScreen.setVisible(true);
        PieceType chosenPieceType = pawnToEndScreen.getChosenPiece();
        controller.setPieceForCrowning(chosenPieceType);
        repaint();
      }
    });
  }

  /**
   * Shows a JOptionPane with the winner.
   * 
   * @param winner
   *          The myColor of the winner player.
   */
  public void showWinner(PieceColor winner) {
    JOptionPane.showMessageDialog(frame, winner.getColorName() + " wins!");
  }

  public void markLastMovement(Movement move) {
    boardPanel.markLastMove(move);
  }

  public void unmarkButLastMove() {
    boardPanel.unmarkButLastMove();
  }

  /**
   * @return
   */
  public String getTime() {
    int timeSeconds = controller.getTime();
    int minutesLeft = timeSeconds / 60;
    int secondsLeft = timeSeconds % 60;
    return "" + minutesLeft + ":" + secondsLeft;
  }
}
