package chessgame.view;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import chessgame.components.Bishop;
import chessgame.components.Board;
import chessgame.components.King;
import chessgame.components.Knight;
import chessgame.components.Movement;
import chessgame.components.Pawn;
import chessgame.components.PieceColor;
import chessgame.components.Position;
import chessgame.components.Queen;
import chessgame.components.Rook;
import chessgame.controller.ChessController;

/**
 * @author Adam Snaider
 *
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
  private GridLayout layout;
  private Square[][] squares = new Square[8][8];
  private static final String[] piecesNames = { Queen.NAME, Rook.NAME,
      Pawn.NAME, Bishop.NAME, Knight.NAME, King.NAME };
  private Map<String, BufferedImage> images;
  private Movement lastMove;

  // private ChessView view;

  /**
   * Creates a new BoardPanel with the view and the controller.
   * 
   * @param view
   *          ChessView in which the game is taking place.
   * @param controller
   *          Controller in which the game is taking place.
   */
  public BoardPanel(ChessView view, ChessController controller) {
    images = loadImages();
    layout = new GridLayout(8, 8);
    setLayout(layout);
    setBoard(view, controller, PieceColor.WHITE);
  }

  public BoardPanel(ChessView view, ChessController controller, PieceColor color) {
    images = loadImages();
    layout = new GridLayout(8, 8);
    setLayout(layout);
    setBoard(view, controller, color);
  }

  public void setBoard(ChessView view, ChessController controller,
      PieceColor color) {
    if (color == PieceColor.WHITE) {
      for (int row = 0; row < Board.SIZE; row++) {
        for (int col = 0; col < Board.SIZE; col++) {
          squares[row][col] = new Square(new Position(row, col), view, images,
              controller);
          add(squares[row][col]);
        }
      }
    } else {
      for (int row = Board.SIZE - 1; row >= 0; row--) {
        for (int col = Board.SIZE - 1; col >= 0; col--) {
          squares[row][col] = new Square(new Position(row, col), view, images,
              controller);
          add(squares[row][col]);
        }
      }
    }
  }

  /**
   * Loads the data of the pieces needed in the game.
   * 
   * @return Returns a map whose key is the Color + the name of the piece, and
   *         its value is a bufferedImage.
   */
  private Map<String, BufferedImage> loadImages() {
    Map<String, BufferedImage> images = new HashMap<>();
    for (String color : new String[] { "White", "Black" }) {
      for (String name : piecesNames) {
        final String pathString = "/chessgame/data/images/" + color + name + ".png";
        InputStream stream = BoardPanel.class.getResourceAsStream(pathString); 
        BufferedImage image = null;
        
        try {
          image = ImageIO.read(stream);
          images.put(color + name, image);
        } catch (Exception e) {
          e.printStackTrace();
        }
        
      }
    }
    return images;
  }

  public Map<String, BufferedImage> getImages() {
    return images;
  }

  public void markLastMove(Movement move) {
    for (int row = 0; row < squares.length; row++) {
      for (int col = 0; col < squares[row].length; col++) {
        Position squarePos = new Position(row, col);
        if (move != null
            && (move.initialPos.equals(squarePos) || move.finalPos
                .equals(squarePos))) {
          squares[row][col].setMarked(true);
        } else {
          squares[row][col].setMarked(false);
        }
        squares[row][col].repaint();
      }
    }
    lastMove = move;
  }

  public void unmarkButLastMove() {
    markLastMove(lastMove);
  }
}
