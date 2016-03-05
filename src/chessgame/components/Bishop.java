package chessgame.components;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {
  private static final long serialVersionUID = 3979360406338728712L;
  public static final String NAME = "Bishop";

  public Bishop(int row, int col, PieceColor color, Board board) {
    super(row, col, color, board);
  }

  public Bishop(Position pos, PieceColor color, Board board) {
    super(pos, color, board);
  }

  @Override
  public Set<Position> possibleMoves(boolean checkKing) {
    int posRow = getRow();
    int posCol = getCol();
    Set<Position> possibleMoves = new HashSet<>();
    for (int col = posCol + 1, row = posRow + 1; col < Board.SIZE
        && row < Board.SIZE; col++, row++) {
      if (addPossibleMove(possibleMoves, row, col)) {
        break;
      }
    }
    for (int col = posCol - 1, row = posRow - 1; col >= 0 && row >= 0; col--, row--) {
      if (addPossibleMove(possibleMoves, row, col)) {
        break;
      }
    }
    for (int col = posCol + 1, row = posRow - 1; col < Board.SIZE && row >= 0; col++, row--) {
      if (addPossibleMove(possibleMoves, row, col)) {
        break;
      }
    }
    for (int col = posCol - 1, row = posRow + 1; col >= 0 && row < Board.SIZE; col--, row++) {
      if (addPossibleMove(possibleMoves, row, col)) {
        break;
      }
    }
    filterInCheckPositions(checkKing, possibleMoves);
    return possibleMoves;
  }

  @Override
  public String getName() {
    return Bishop.NAME;
  }
}
