package chessgame.components;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
  private static final long serialVersionUID = -3012460210088625665L;
  public static final String NAME = "Pawn";

  public Pawn(int row, int col, PieceColor color, Board board) {
    super(row, col, color, board, 1);
  }

  public Pawn(Position pos, PieceColor color, Board board) {
    super(pos, color, board, 1);
  }

  @Override
  public Set<Position> possibleMoves(boolean checkKing) {
    Set<Position> possibleMoves = new HashSet<>();
    int posRow = getRow();
    int posCol = getCol();
    int colorDirection = (getColor() == PieceColor.BLACK) ? 1 : -1;
    // Move two at the starting position.
    if (posRow == 6 && getColor() == PieceColor.WHITE || posRow == 1
        && getColor() == PieceColor.BLACK) {
      Position pos = new Position(posRow + 2 * colorDirection, posCol);
      if (board.isBlank(new Position(posRow + 1 * colorDirection, posCol))
          && board.isBlank(pos)) {
        possibleMoves.add(pos);
      }
    }
    if (posRow + 1 < 8 && posRow - 1 >= 0) {
      Position pos = new Position(posRow + 1 * colorDirection, posCol);
      if (board.isBlank(pos)) {
        possibleMoves.add(pos);
      }
      if (posCol + 1 < 8) {
        pos = new Position(posRow + 1 * colorDirection, posCol + 1);
        if (board.isOpposite(pos, getColor())) {
          possibleMoves.add(pos);
        }
      }
      if (posCol - 1 >= 0) {
        pos = new Position(posRow + 1 * colorDirection, posCol - 1);
        if (board.isOpposite(pos, getColor())) {
          possibleMoves.add(pos);
        }
      }
    }
    if (getColor() == PieceColor.WHITE) {
      if (getRow() == 3) {
        Movement lastMove = board.getLastMove();
        if (lastMove.initialPos.row == 1 && lastMove.finalPos.row == 3) {
          if (lastMove.finalPos.col == getCol() + 1) {
            possibleMoves.add(new Position(getRow() - 1, getCol() + 1));
          } else if (lastMove.finalPos.col == getCol() - 1) {
            possibleMoves.add(new Position(getRow() - 1, getCol() - 1));
          }
        }
      }
    } else if (getColor() == PieceColor.BLACK) {
      if (getRow() == 4) {
        Movement lastMove = board.getLastMove();
        if (lastMove.initialPos.row == 6 && lastMove.finalPos.row == 4) {
          if (lastMove.finalPos.col == getCol() + 1) {
            possibleMoves.add(new Position(getRow() + 1, getCol() + 1));
          } else if (lastMove.finalPos.col == getCol() - 1) {
            possibleMoves.add(new Position(getRow() + 1, getCol() - 1));
          }
        }
      }
    }
    // Filter check positions.
    filterInCheckPositions(checkKing, possibleMoves);
    return possibleMoves;
  }

  public String getName() {
    return Pawn.NAME;
  }
}