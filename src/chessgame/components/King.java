package chessgame.components;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
  private static final long serialVersionUID = -5907164449874734919L;
  public static final String NAME = "King";
  private boolean moved = false;

  public King(int row, int col, PieceColor color, Board board) {
    super(row, col, color, board);
  }

  public King(Position pos, PieceColor color, Board board) {
    super(pos, color, board);
  }

  public boolean hasMoved() {
    return moved;
  }

  @Override
  public Set<Position> possibleMoves(boolean checkKing) {
    Set<Position> possibleMoves = new HashSet<>();
    int posRow = getRow();
    int posCol = getCol();
    int[][] moves = { { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 },
        { -1, -1 }, { 0, -1 }, { 1, -1 } };
    for (int[] move : moves) {
      int tempRow = posRow + move[0];
      int tempCol = posCol + move[1];
      if (tempRow < Board.SIZE && tempRow >= 0 && tempCol < Board.SIZE
          && tempCol >= 0) {
        Position pos = new Position(tempRow, tempCol);
        if (board.isAvailable(pos, getColor())) {
          possibleMoves.add(pos);
        }
      }
    }
    boolean isCastling = false;
    // Castle.
    if (!hasMoved()) {
      // Long castle.
      Piece piece = board.getPiece(new Position(getRow(), 0));
      if (piece != null && piece instanceof Rook) {
        Rook rook = (Rook) piece;
        boolean blankSquares = true;
        // Check that the path is blank.
        for (int i = 1; i < 4; i++) {
          blankSquares &= board.isBlank(new Position(getRow(), i));
        }
        if (blankSquares && !rook.hasMoved()) {
          possibleMoves.add(new Position(getRow(), 2));
          isCastling = true;
        }
      }
      // Short castle.
      piece = board.getPiece(new Position(getRow(), 7));
      if (piece != null && piece instanceof Rook) {
        Rook rook = (Rook) piece;
        boolean blankSquares = true;
        for (int i = 5; i < 7; i++) {
          blankSquares &= board.isBlank(new Position(getRow(), i));
        }
        if (blankSquares && !rook.hasMoved()) {
          possibleMoves.add(new Position(getRow(), 6));
          isCastling = true;
        }
      }
    }
    // Filter check positions.
    filterInCheckPositions(checkKing, possibleMoves);
    // Check that castle is legal or remove it.
    if (isCastling) {
      if (!possibleMoves.contains(new Position(getRow(), 3))) {
        possibleMoves.remove(new Position(getRow(), 2));
      }
      if (!possibleMoves.contains(new Position(getRow(), 5))) {
        possibleMoves.remove(new Position(getRow(), 6));
      }
    }
    return possibleMoves;
  }

  @Override
  public String getName() {
    return King.NAME;
  }

  @Override
  public void setPosition(Position pos, boolean realMove) {
    super.setPosition(pos);
    if (realMove) {
      moved = true;
    }
  }
}