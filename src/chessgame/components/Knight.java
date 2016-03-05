package chessgame.components;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
  private static final long serialVersionUID = 1666560898376418949L;
  public static final String NAME = "Knight";

  public Knight(int row, int col, PieceColor color, Board board) {
    super(row, col, color, board);
  }

  public Knight(Position pos, PieceColor color, Board board) {
    super(pos, color, board);
  }

  @Override
  public Set<Position> possibleMoves(boolean checkKing) {
    int posRow = getRow();
    int posCol = getCol();
    Set<Position> possibleMoves = new HashSet<>();
    int[][] moves = { { 2, 1 }, { -2, 1 }, { 2, -1 }, { -2, -1 }, { 1, 2 },
        { -1, 2 }, { 1, -2 }, { -1, -2 } };
    for (int[] move : moves) {
      int tempRow = posRow + move[0];
      int tempCol = posCol + move[1];
      if (tempRow < 0 || tempRow >= Board.SIZE || tempCol < 0
          || tempCol >= Board.SIZE) {
        continue;
      }
      Position pos = new Position(tempRow, tempCol);
      if (board.isAvailable(pos, getColor())) {
        possibleMoves.add(pos);
      }
    }
    // Filter check positions.
    filterInCheckPositions(checkKing, possibleMoves);
    return possibleMoves;
  }

  @Override
  public String getName() {
    return Knight.NAME;
  }
}