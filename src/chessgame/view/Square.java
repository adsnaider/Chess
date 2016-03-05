package chessgame.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JPanel;

import chessgame.components.Piece;
import chessgame.components.Position;
import chessgame.controller.ChessController;

/**
 * @author Adam Snaider
 *
 */
@SuppressWarnings("serial")
public class Square extends JPanel implements MouseListener {
  public final Position pos;
  private ChessView view;
  private final Map<String, BufferedImage> images;
  private ChessController controller;
  private boolean isMarked = false;

  /**
   * Square of the board which is final and will change as the game changes.
   * 
   * @param pos
   *          Position of the board.
   * @param view
   *          ChessView where the game is taking place.
   * @param data
   *          Map of BufferedImages with the data of the pieces.
   * @param controller
   *          Controller where the game is taking place.
   */
  public Square(Position pos, ChessView view,
      Map<String, BufferedImage> images, ChessController controller) {
    this.pos = pos;
    this.view = view;
    this.images = images;
    this.controller = controller;
    addMouseListener(this);
  }

  public void setMarked(boolean mark) {
    isMarked = mark;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Piece piece = view.getPiece(pos);
    // white
    if ((pos.row + pos.col) % 2 == 0) {
      if (isMarked) {
        g.setColor(new Color(255, 224, 94));
      } else {
        g.setColor(new Color(199, 178, 153));
      }
    } else {
      // black
      if (isMarked) {
        g.setColor(new Color(222, 176, 48));
      } else {
        g.setColor(new Color(140, 98, 57));
      }
    }
    g.fillRect(0, 0, getWidth(), getHeight());

    if (piece != null) {
      String name = piece.getColor().getColorName() + piece.getName();
      BufferedImage image = images.get(name);
      if (image != null) {
        int size = ((getWidth() <= getHeight()) ? getWidth() : getHeight()) * 9 / 10;
        g.drawImage(image, getWidth() / 2 - size / 2, getHeight() / 2 - size
            / 2, size, size, null);
      }
    }
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
    controller.setData(pos);
    view.unmarkButLastMove();
    isMarked = true;
    repaint();
  }

  @Override
  public void mouseReleased(MouseEvent arg0) {

  }
}
