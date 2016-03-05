package chessgame.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Adam Snaider
 *
 */
public class Board {
  public final static int SIZE = 8;
  private King whiteKing;
  private King blackKing;
  private Map<Position, Piece> squares;
  private Movement lastMove;

  public Board() {
    squares = new HashMap<Position, Piece>();
  }

  public Board(King whiteKing, King blackKing) {
    this();
    this.whiteKing = whiteKing;
    this.blackKing = blackKing;
  }

  /**
   * Getter of the board (squares).
   * 
   * @return Returns a cloned map representing the board.
   */
  @SuppressWarnings("unchecked")
  public Map<Position, Piece> getSquares() {
    return (Map<Position, Piece>) ((HashMap<Position, Piece>) (squares))
        .clone();
  }

  /**
   * @param position
   *          The position of the piece you want.
   * @return Returns the piece that was found on those coordinates. If there
   *         wasn't any piece, it returns null.
   */
  public Piece getPiece(Position position) {
    return squares.get(position);
  }

  /**
   * Sets a specified piece to the board.
   * 
   * @param piece
   *          The piece you want to add to the board.
   * @return Returns true if the piece was succesfully inserted. Returns false
   *         otherwise.
   */
  public boolean setPiece(Piece piece) {
    if (piece != null) {
      squares.put(piece.getPosition(), piece);
      return true;
    }
    return false;
  }

  /**
   * Initializes the board as a regular chess board. White on the bottom.
   */
  public void initialize() {
    // White
    Rook rook = new Rook(7, 0, PieceColor.WHITE, this);
    setPiece(rook);
    Knight knight = new Knight(7, 1, PieceColor.WHITE, this);
    setPiece(knight);
    Bishop bishop = new Bishop(7, 2, PieceColor.WHITE, this);
    setPiece(bishop);
    whiteKing = new King(7, 4, PieceColor.WHITE, this);
    setKing(whiteKing);
    Queen queen = new Queen(7, 3, PieceColor.WHITE, this);
    setPiece(queen);
    bishop = new Bishop(7, 5, PieceColor.WHITE, this);
    setPiece(bishop);
    knight = new Knight(7, 6, PieceColor.WHITE, this);
    setPiece(knight);
    rook = new Rook(7, 7, PieceColor.WHITE, this);
    setPiece(rook);

    // Black
    rook = new Rook(0, 0, PieceColor.BLACK, this);
    setPiece(rook);
    knight = new Knight(0, 1, PieceColor.BLACK, this);
    setPiece(knight);
    bishop = new Bishop(0, 2, PieceColor.BLACK, this);
    setPiece(bishop);
    blackKing = new King(0, 4, PieceColor.BLACK, this);
    setKing(blackKing);
    queen = new Queen(0, 3, PieceColor.BLACK, this);
    setPiece(queen);
    bishop = new Bishop(0, 5, PieceColor.BLACK, this);
    setPiece(bishop);
    knight = new Knight(0, 6, PieceColor.BLACK, this);
    setPiece(knight);
    rook = new Rook(0, 7, PieceColor.BLACK, this);
    setPiece(rook);

    // Pawns
    for (int i = 0; i < SIZE; i++) {
      Pawn pawn = new Pawn(6, i, PieceColor.WHITE, this);
      setPiece(pawn);
      pawn = new Pawn(1, i, PieceColor.BLACK, this);
      setPiece(pawn);
    }
  }

  /**
   * Checks if a particular piece can move to that position.
   * 
   * @param pos
   *          Position of the board you want to check.
   *
   * @param color
   *          The color of the piece.
   * @return Returns true if the space is available (Either the space is blank
   *         or there is a piece of the opposite color).
   */
  public boolean isAvailable(Position pos, PieceColor color) {
    return (isBlank(pos) || isOpposite(pos, color));
  }

  /**
   * Indicates weather the space of the board is blank (Null).
   *
   * @param pos
   *          Position of the board you want to check.
   *
   * @return Returns true if the indicated square is blank (Null).
   */

  public boolean isBlank(Position pos) {
    return squares.get(pos) == null;
  }

  /**
   * Indicates weather a square is occupied by a piece of another color.
   * 
   * @param pos
   *          Position of the board you want to check.
   *
   * @param color
   *          Color of the original piece.
   * @return Returns true if the square is occupied by a piece of another color.
   *         Otherwise it returns false.
   */

  public boolean isOpposite(Position pos, PieceColor color) {
    return (!isBlank(pos) && squares.get(pos).getColor() != color);
  }

  /**
   * Moves one piece in the board using a Movement.
   * 
   * @param move
   *          Movement where the initialPos is the original position of the
   *          piece and the finalPos is the position where the piece is going to
   *          be moved to.
   * @param checkKing
   *          If the movement is "Real" checkKing should be true. If the
   *          movement is only happening to check something, it should be false.
   * @return True if the piece was successfully moved, otherwise, it returns
   *         false.
   */
  public boolean movePiece(Movement move, boolean checkKing) {
    Piece piece = getPiece(move.initialPos);
    if (piece != null && piece.isPossibleMove(move.finalPos, checkKing)) {
      killElPassantPawn(move);
      changePiecePosition(move.finalPos, piece);
      moveCastleRook(move);
      setLastMove(move);
      return true;
    }
    return false;
  }

  /**
   * Last move in the board
   * 
   * @return Returns a Movement representing the last move performed in the
   *         board
   */
  public Movement getLastMove() {
    return lastMove;
  }

  /**
   * Sets the last move into a specified Movement.
   * 
   * @param move
   *          The Movement you want to set.
   */
  private void setLastMove(Movement move) {
    lastMove = move;
  }

  /**
   * Method used to kill the el passant pawn, when the movement called
   * "el passant" is performed.
   * 
   * @param move
   *          The move you want to use to kill the ElPassant pawn.
   */
  private void killElPassantPawn(Movement move) {
    Piece piece = getPiece(move.initialPos);
    if (piece instanceof Pawn) {
      if (move.initialPos.col != move.finalPos.col) {
        if (getPiece(move.finalPos) == null) {
          squares.remove(new Position(move.initialPos.row, move.finalPos.col));
        }
      }
    }
  }

  /**
   * If the movement is a valid castle move, then it changes the rook position.
   * 
   * @param move
   *          The Movement to check.
   */
  private void moveCastleRook(Movement move) {
    // We moved the piece first and then call this method, so we use the final
    // position.
    Piece piece = getPiece(move.finalPos);
    if (piece != null && piece instanceof King) {
      int colDiff = move.finalPos.col - move.initialPos.col;
      // isPossibleMove() was already checked.
      switch (colDiff) {
      case 2:
        changePiecePosition(new Position(move.initialPos.row, 5),
            getPiece(new Position(move.initialPos.row, 7)));
        break;
      case -2:
        changePiecePosition(new Position(move.initialPos.row, 3),
            getPiece(new Position(move.initialPos.row, 0)));
        break;
      }
    }
  }

  /**
   * Setter of any of the kings.
   * 
   * @param king
   *          King already initialized which you want to insert in the board.
   */
  public void setKing(King king) {
    if (king != null) {
      if (king.getColor() == PieceColor.WHITE) {
        whiteKing = king;
      } else {
        blackKing = king;
      }
    }
    setPiece(king);
  }

  /**
   * Method used to change a piece position. It will move it. It doesn't matter
   * if it's not valid.
   * 
   * @param pos
   *          Position where you want to move the piece.
   * @param piece
   *          piece you want to move.
   */
  private void changePiecePosition(Position pos, Piece piece) {
    changePiecePosition(pos, piece, true);
  }

  /**
   * Method used to change a piece position. It will move it. It doesn't matter
   * if it's not valid.
   * 
   * @param pos
   *          Position where you want to move the piece.
   * @param piece
   *          piece piece you want to move.
   * @param realMove
   *          If the move is "real" realMove should be true. If it's called to
   *          check something, realMove should be false.
   */
  private void changePiecePosition(Position pos, Piece piece, boolean realMove) {
    squares.remove(piece.getPosition());
    piece.setPosition(pos, realMove);
    squares.put(pos, piece);
  }

  /**
   * Indicates weather one of the kings is in check.
   * 
   * @param color
   *          Used to know which king to check out.
   * @return True if the king of the specified color is in check.
   */
  public boolean isKingInCheck(PieceColor color) {
    Piece king = (color == PieceColor.WHITE) ? whiteKing : blackKing;
    for (Piece piece : squares.values()) {
      if (piece != null) {
        if (piece.getColor() != king.getColor()
            && piece.isPossibleMove(king.getPosition(), false)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Indicates weather after a move the king will be in check.
   * 
   * @param move
   *          The movement of the piece.
   * @return True if after the performing the movement, the king is in check.
   */
  public boolean willKingBeInCheck(Movement move) {
    Piece piece = getPiece(move.initialPos);
    if (piece != null) {
      Position posTemp = piece.getPosition();
      Map<Position, Piece> boardBackup = getSquares();
      changePiecePosition(move.finalPos, piece, false);
      Piece king = (piece.getColor() == PieceColor.WHITE) ? whiteKing
          : blackKing;
      for (Piece square : squares.values()) {
        if (square != null) {
          if (square.getColor() != king.getColor()
              && square.isPossibleMove(king.getPosition(), false)) {
            piece.setPosition(posTemp, false);
            squares = boardBackup;
            return true;
          }
        }
      }
      piece.setPosition(posTemp, false);
      squares = boardBackup;
    }
    return false;
  }

  /**
   * Moves a piece using the original coordinates and the final coordinates
   * 
   * @param initialPos
   *          initial position of the piece.
   * @param finalPos
   *          where the piece is going to move.
   * @return Returns true if the piece was moved successfully (The new
   *         coordinates were valid).
   */
  public boolean movePiece(Position initialPos, Position finalPos) {
    return movePiece(new Movement(initialPos, finalPos), true);
  }

  /**
   * Checks if is check mate for a specified color.
   * 
   * @param color
   *          The color of the king you want to check
   * @return Returns true only if the king of the specified color was in check
   *         and there are not possible moves for this color.
   */
  public boolean isCheckMate(PieceColor color) {
    if (isKingInCheck(color)) {
      List<Piece> pieceList = new ArrayList<>();
      pieceList.addAll(squares.values());
      for (Piece piece : pieceList) {
        if (piece != null && piece.getColor() == color) {
          if (!piece.possibleMoves(true).isEmpty()) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }

  /**
   * Checks if one of the players has no possible moves.
   * 
   * @param color
   *          The color that the player is playing with.
   * @return Returns true if the player has no possible moves.
   */
  public boolean isDraw(PieceColor color) {
    List<Piece> pieceList = new ArrayList<>();
    pieceList.addAll(squares.values());
    if (isCheckMate(color)) {
      return false;
    }
    for (Piece piece : pieceList) {
      if (piece != null && piece.getColor() == color) {
        Set<Position> possible = piece.possibleMoves(true);
        if (!possible.isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns the board using the piece toString() method.
   */
  public String toString() {
    String result = "";
    for (Piece piece : squares.values()) {
      result += (piece + "\n");
    }
    return result;
  }
}
