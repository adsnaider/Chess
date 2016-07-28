package chessgame.components;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {
  private static final long serialVersionUID = 6564918609566749814L;
  public static final String NAME = "Queen";

  public Queen(int row, int col, PieceColor color, Board board) {
    super(row, col, color, board, 9);
  }

  public Queen(Position pos, PieceColor color, Board board) {
    super(pos, color, board, 9);
  }

  @Override
  public Set<Position> possibleMoves(boolean checkKing) {
    int posRow = getRow();
    int posCol = getCol();
    Set<Position> possibleMoves = new HashSet<>();
    // Rook moves.
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
    // Bishop moves.
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

    // Filter check positions.
    filterInCheckPositions(checkKing, possibleMoves);
    return possibleMoves;
  }

  public String getName() {
    return Queen.NAME;
  }
}