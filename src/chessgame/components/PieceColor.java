package chessgame.components;

/**
 * @author Adam Snaider
 *
 */
public enum PieceColor {
  BLACK("Black"), WHITE("White");

  private final String colorName;

  private PieceColor(String colorName) {
    this.colorName = colorName;
  }

  public String getColorName() {
    return colorName;
  }
  
  public PieceColor oppositeColor() {
    return (this == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
  }
}
