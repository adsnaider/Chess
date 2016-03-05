package chessgame.model;

import java.util.Map;

import chessgame.components.Movement;
import chessgame.components.Piece;
import chessgame.components.PieceColor;
import chessgame.components.Position;
import chessgame.controller.Action;
import chessgame.view.ChessView;

/**
 * @author Adam Snaider
 *
 */
public interface ChessModel {

  public int getTime();

  /**
   * Method that makes the logic of crowing.
   * 
   * @param pieceType
   *          The pieceType which will convert the pawn who did crowning.
   */
  public void setPieceForCrowning(PieceType pieceType);

  /**
   * Method used to move a piece once you have a Movement defined.
   * 
   * @param move
   *          Movement used to move the piece.
   * @return An Action specifying the move of a piece.
   */
  public Action makeMove(Movement move);

  /**
   * Getter of the board (not board class, but board squares).
   * 
   * @return A map where the position is the Key and the Piece is the value.
   */
  public Map<Position, Piece> getSquares();

  /**
   * Checks weather is check mate for a specified player.
   * 
   * @param color
   *          The PieceColor of the player that wants to be checked.
   * @return Returns true if the specified color is in check mate position.
   */
  public boolean isCheckMate(PieceColor color);

  /**
   * Get the Movement last played.
   * 
   * @return Returns a Movement with the position last moved.
   */
  public Movement getLastMovement();

  /**
   * Sets the data coming from the view.
   * 
   * @return Returns a movement to process.
   */
  public Movement setData(Position pos);

  public void setView(ChessView view);

  public PieceColor getMyColor();
}
