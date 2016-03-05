package chessgame.components;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Adam Snaider
 *
 */
public class ChessTestUtils {
  static void addPosition(int row, int col, Set<Position> positions) {
    positions.add(new Position(row, col));
  }

  static Set<Position> booleanArrayToSet(boolean[][] squares) {
    Set<Position> positions = new HashSet<>();
    for (int row = 0; row < squares.length; row++) {
      for (int col = 0; col < squares[row].length; col++) {
        if (squares[row][col]) {
          addPosition(row, col, positions);
        }
      }
    }
    return positions;
  }
}
