package chessgame.controller;

import chessgame.components.Movement;
import chessgame.components.PieceColor;
import chessgame.components.Position;
import chessgame.model.ChessModel;
import chessgame.model.PieceType;
import chessgame.view.ChessView;

/**
 * @author Adam Snaider
 *
 */
public class ChessController {

  private ChessView view;
  private ChessModel model;

  public ChessController(ChessModel model) {
    this.model = model;
  }

  /**
   * Setter of the ChessView.
   * 
   * @param view
   *          View you want to use to start the game.
   */
  public void setView(ChessView view) {
    this.view = view;
    view.update(model.getSquares());
  }

  /**
   * Whenever a pawn reaches the other side of the board, it uses this mothod to
   * convert the piece into another one.
   * 
   * @param pieceType
   *          pieceType of the new piece of the pawn/
   */
  public void setPieceForCrowning(PieceType pieceType) {
    model.setPieceForCrowning(pieceType);
    view.update(model.getSquares());
    view.repaint();
  }

  /**
   * Used when a user clicks the board. It sends the Position it was clicked.
   * 
   * @param pos
   *          Position of the board it was clicked.
   */
  public void setData(Position pos) {
    view.update(model.getSquares());
    view.repaint();
    Movement move = model.setData(pos);
    if (move != null) {
      Action action = model.makeMove(move);
      processAction(action);
      view.update(model.getSquares());
      view.repaint();
      if (model.isCheckMate(PieceColor.WHITE)) {
        view.showWinner(PieceColor.BLACK);
      } else if (model.isCheckMate(PieceColor.BLACK)) {
        view.showWinner(PieceColor.WHITE);
      }
    }
  }

  public int getTime() {
    return model.getTime();
  }

  /**
   * Method used to process the chess action.
   * 
   * @param action
   *          Action used in chess which represents a play.
   */
  private void processAction(Action action) {
    switch (action) {
    case SELECT_WHITE_CROWNING:
      view.choosePiece(PieceColor.WHITE);
      break;
    case SELECT_BLACK_CROWNING:
      view.choosePiece(PieceColor.BLACK);
      break;
    default:
      break;
    }
  }
}
