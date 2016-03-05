package chessgame.view;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import chessgame.components.PieceColor;
import chessgame.model.PieceType;

/**
 * @author Adam Snaider
 *
 */
@SuppressWarnings("serial")
public class PawnToEndScreen extends JDialog {
  private PieceType pieceType;

  /**
   * Creates a new PawnToEndScreen which is used when a pawn reaches the other
   * side and the player has to choose another piece.
   * 
   * @param frame
   *          JFrame where the game is taking place.
   * @param color
   *          PieceColor of the pawn.
   * @param data
   *          Map of data.
   */
  public PawnToEndScreen(JFrame frame, PieceColor color,
      Map<String, BufferedImage> images) {
    super(frame, true);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setSize(frame.getWidth() / 4, frame.getHeight() / 4);
    setLocationRelativeTo(frame);
    setUndecorated(true);
    setAlwaysOnTop(true);
    setLayout(new GridLayout(2, 2));
    add(new ChoosePanel(PieceType.QUEEN, images.get(color.getColorName()
        + "Queen")));
    add(new ChoosePanel(PieceType.ROOK, images.get(color.getColorName()
        + "Rook")));
    add(new ChoosePanel(PieceType.BISHOP, images.get(color.getColorName()
        + "Bishop")));
    add(new ChoosePanel(PieceType.KNIGHT, images.get(color.getColorName()
        + "Knight")));
  }

  /**
   * Getter of the piece the player chose.
   * 
   * @return Returns a PieceType whose piece represents.
   */
  public PieceType getChosenPiece() {
    return pieceType;
  }

  /**
   * @author Adam Snaider
   *
   */
  private class ChoosePanel extends JPanel implements MouseListener {
    private BufferedImage image;
    private PieceType innerPieceType;

    /**
     * Creates a choosePanel which is a user interface where the user can see
     * the pieces to choose.
     * 
     * @param pieceType
     *          PieceType that the panel is going to represent.
     * @param image
     *          BufferedImage o fthe corresponding piece.
     */
    public ChoosePanel(PieceType pieceType, BufferedImage image) {
      this.image = image;
      this.innerPieceType = pieceType;
      addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      int size = ((getWidth() <= getHeight()) ? getWidth() : getHeight()) * 9 / 10;
      g.drawImage(image, getWidth() / 2 - size / 2, getHeight() / 2 - size / 2,
          size, size, null);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
      pieceType = innerPieceType;
      PawnToEndScreen.this.dispose();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }
  }
}
