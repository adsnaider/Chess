package chessgame.components;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
  private static final long serialVersionUID = 4824674016813322424L;
  public static final String NAME = "Rook";
  public boolean moved = false;

  public Rook(int row, int col, PieceColor color, Board board) {
    super(row, col, color, board);
  }

  public Rook(Position pos, PieceColor color, Board board) {
    super(pos, color, board);
  }

  public boolean hasMoved() {
    return moved;
  }

  @Override
  public Set<Position> possibleMoves(boolean checkKing) {
    int posRow = getRow();
    int posCol = getCol();
    Set<Position> possibleMoves = new HashSet<>();
    for (int row = posRow + 1; row < Board.SIZE; row++) {
      if (addPossibleMove(possibleMoves, row, posCol)) {
        break;
      }
    }
    for (int row = posRow - 1; row >= 0; row--) {
      if (addPossibleMove(possibleMoves, row, posCol)) {
        break;
      }
    }
    for (int col = posCol + 1; col < Board.SIZE; col++) {
      if (addPossibleMove(possibleMoves, posRow, col)) {
        break;
      }
    }
    for (int col = posCol - 1; col >= 0; col--) {
      if (addPossibleMove(possibleMoves, posRow, col)) {
        break;
      }
    }
    // Filter check positions.
    filterInCheckPositions(checkKing, possibleMoves);
    return possibleMoves;
  }

  @Override
  public String getName() {
    return Rook.NAME;
  }

  @Override
  public void setPosition(Position pos, boolean realMove) {
    super.setPosition(pos);
    if (realMove) {
      moved = true;
    }
  }
}
