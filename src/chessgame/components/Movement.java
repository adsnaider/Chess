package chessgame.components;

import java.io.Serializable;

/**
 * @author Adam
 *
 */

public class Movement implements Serializable {
  private static final long serialVersionUID = 7870411556209139215L;
  public final Position initialPos;
  public final Position finalPos;
  public final MovementType type;

  public Movement(int initialRow, int initialCol, int finalRow, int finalCol) {
    initialPos = new Position(initialRow, initialCol);
    finalPos = new Position(finalRow, finalCol);
    type = MovementType.NORMAL;
  }

  public Movement(Position initialPos, Position finalPos) {
    this.initialPos = initialPos;
    this.finalPos = finalPos;
    type = MovementType.NORMAL;
  }

  public Movement(Position initialPos, Position finalPos, MovementType type) {
    this.initialPos = initialPos;
    this.finalPos = finalPos;
    this.type = type;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Movement) {
      Movement move = (Movement) obj;
      return (move.initialPos.equals(initialPos) && move.finalPos
          .equals(finalPos));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return initialPos.hashCode() * 101 + finalPos.hashCode() * 103;
  }

  @Override
  public String toString() {
    return "Initial position: " + initialPos + "\nFinal position: " + finalPos;
  }
}
