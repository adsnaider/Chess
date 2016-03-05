package chessgame.components;

import java.io.Serializable;

/**
 * @author Adam Snaider Used to represent a position in a board (8 x 8).
 */
public class Position implements Serializable {
  private static final long serialVersionUID = -5579323452758315507L;
  public final int row;
  public final int col;

  public Position(int row, int col) {
    checkLimit(row, col);
    this.row = row;
    this.col = col;
  }

  public boolean equals(Object obj) {
    if (obj instanceof Position) {
      Position pos = (Position) obj;
      if (row == pos.row && col == pos.col) {
        return true;
      }
    }
    return false;
  }

  public int hashCode() {
    return row * 17 + col * 73;
  }

  /**
   * If the row or the column are out of bounds, it throws and
   * illegalArgumentException.
   * 
   * @param row
   *          Row to be checked.
   * @param col
   *          Column to be checked.
   */
  private void checkLimit(int row, int col) {
    if (row >= Board.SIZE || row < 0 || col >= Board.SIZE || col < 0) {
      throw new IllegalArgumentException("Row or column out of bounds.");
    }
  }

  @Override
  public String toString() {
    return "[" + row + ", " + col + "]";
  }
}
