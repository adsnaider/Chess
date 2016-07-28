package chessgame.components;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Adam Snaider
 * @category This class is mainly to wrap all the chess pieces up into one
 *           object type.
 */
public abstract class Piece implements Serializable {
  private static final long serialVersionUID = -7701612189619741089L;
  private Position position;
  private final int value;
  // "white" or "black"
  private PieceColor color;
  transient protected Board board;

  public PieceColor getColor() {
    return color;
  }

  /**
   * Create a new Piece object with the indicated parameters.
   * 
   * @param row
   *          Row in the board between 0 - 7
   * @param col
   *          Column in the board between 0 - 7
   * @param color
   *          Black or white
   * @param board
   *          Board object where the match is being played
   */
  public Piece(int row, int col, PieceColor color, Board board, int value) {
    this(new Position(row, col), color, board, value);
  }
  
  public Piece(int row, int col, PieceColor color, Board board) {
    this(row, col, color, board, 0);
  }
  
  public Piece(Position pos, PieceColor color, Board board) {
    this(pos, color, board, 0);
  }

  public Piece(Position pos, PieceColor color, Board board, int value) {
    position = pos;
    this.color = color;
    this.board = board;
    this.value = value;
  }
  
  public int getValue() {
    return value;
  }
  
  /**
   * Getter of the row.
   * 
   * @return The row of the piece
   */
  public int getRow() {
    return position.row;
  }

  /**
   * Getter of the column
   * 
   * @return The column of the piece.
   */
  public int getCol() {
    return position.col;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position pos) {
    position = pos;
  }

  public void setPosition(Position pos, boolean realMove) {
    setPosition(pos);
  }
  
  public Set<Movement> toMovementSet(Set<Position> possibleMoves) {
    Set<Movement> moves = new HashSet<>();
    for (Position pos : possibleMoves) {
      moves.add(new Movement(this.position, pos));
    }
    return moves;
  }

  /**
   * The two dimensional array tells the values as a chess board of 8x8 where
   * true means that position is a possible move. Otherwise it's not.
   * 
   * @return A two dimension boolean array with the possible moves of the piece
   */
  public abstract Set<Position> possibleMoves(boolean checkKing);

  /**
   * Indicates if the move for a specific row and a column is possible for the
   * specified piece.
   * 
   * @param row
   *          Position in Y coordinate.
   * @param col
   *          Position in X coordinate.
   * @return a boolean indicating if the selected move is possible or not.
   */
  public boolean isPossibleMove(Position pos, boolean checkKing) {
    return possibleMoves(checkKing).contains(pos);
  }

  /**
   * Getter of the name of the piece Eg: "Knight", "Queen", "Pawn".
   * 
   * @return Name of the piece.
   */
  public abstract String getName();

  @Override
  public boolean equals(Object obj) {
    if (obj != null && obj instanceof Piece) {
      Piece other = (Piece) obj;
      return (other.position == position && other.color == color && other
          .getName().equals(getName()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int intColor = (color == PieceColor.WHITE) ? 47 : 73;
    return position.hashCode() * 11 + intColor + getName().hashCode();
  }

  public String toString() {
    return getColor() + "" + getName() + " Row: " + getRow() + " Col: "
        + getCol();
  }

  /**
   * @param checkKing
   * @param possibleMoves
   */
  protected void filterInCheckPositions(boolean checkKing,
      Set<Position> possibleMoves) {
    if (checkKing) {
      for (Iterator<Position> iterator = possibleMoves.iterator(); iterator
          .hasNext();) {
        Position pos = iterator.next();
        Movement move = new Movement(getPosition(), pos);
        if (board.willKingBeInCheck(move)) {
          iterator.remove();
        }
      }
    }
  }

  /**
   * @param possibleMoves
   * @param pos
   */
  protected boolean addPossibleMove(Set<Position> possibleMoves, int row,
      int col) {
    Position pos = new Position(row, col);
    if (board.isAvailable(pos, getColor())) {
      possibleMoves.add(pos);
    } else {
      return true;
    }
    if (board.isOpposite(pos, getColor())) {
      return true;
    }
    return false;
  }
}
