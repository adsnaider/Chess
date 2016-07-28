package chessgame.model;

import java.util.Map;

import chessgame.components.Movement;
import chessgame.components.Piece;
import chessgame.components.PieceColor;
import chessgame.components.Position;
import chessgame.controller.Action;
import chessgame.view.ChessView;

public class ChessModelImplLocal implements ChessModel {
  private PieceColor turn = PieceColor.WHITE;
  private PieceColor playerColor;

  @Override
  public int getTime() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setPieceForCrowning(PieceType pieceType) {
    // TODO Auto-generated method stub

  }

  @Override
  public Action makeMove(Movement move) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<Position, Piece> getSquares() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isCheckMate(PieceColor color) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Movement getLastMovement() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Movement setData(Position pos) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setView(ChessView view) {
    // TODO Auto-generated method stub

  }

  @Override
  public PieceColor getMyColor() {
    // TODO Auto-generated method stub
    return null;
  }

}
