package chessgame.components;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Adam Snaider
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardTest {

  private Board board;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    board = new Board();
  }

  @Test
  public void testSetPiece() {
    Piece rook = new Rook(2, 4, PieceColor.WHITE, board);
    board.setPiece(rook);
  }

  @Test
  public void testGetSquares() {
    Rook rook = new Rook(3, 5, PieceColor.WHITE, board);
    Bishop bishop = new Bishop(2, 2, PieceColor.BLACK, board);
    board.setPiece(rook);
    board.setPiece(bishop);
    Map<Position, Piece> expected = new HashMap<Position, Piece>();
    expected.put(new Position(2, 2), bishop);
    expected.put(new Position(3, 5), rook);
    Map<Position, Piece> actual = board.getSquares();
    assertTrue(expected.equals(actual));
  }

  @Test
  public void testGetPiece() {
    Queen queen = new Queen(2, 3, PieceColor.WHITE, board);
    board.setPiece(queen);
    assertTrue(queen.equals(board.getPiece(new Position(2, 3))));
  }

  @Test
  public void testIsAvailable() {
    Rook rook = new Rook(5, 6, PieceColor.BLACK, board);
    board.setPiece(rook);
    assertFalse(board.isAvailable(new Position(5, 6), PieceColor.BLACK));
    assertTrue(board.isAvailable(new Position(5, 6), PieceColor.WHITE));
  }

  @Test
  public void testIsBlank() {
    Pawn pawn = new Pawn(4, 7, PieceColor.WHITE, board);
    board.setPiece(pawn);
    assertTrue(board.isBlank(new Position(3, 4)));
    assertFalse(board.isBlank(new Position(4, 7)));
  }

  @Test
  public void testIsOpposite() {
    Knight knight = new Knight(2, 6, PieceColor.WHITE, board);
    Knight knight2 = new Knight(4, 5, PieceColor.BLACK, board);
    board.setPiece(knight);
    board.setPiece(knight2);
    assertTrue(board.isOpposite(new Position(2, 6), PieceColor.BLACK));
    assertFalse(board.isOpposite(new Position(2, 6), PieceColor.WHITE));
    assertTrue(board.isOpposite(new Position(4, 5), PieceColor.WHITE));
    assertFalse(board.isOpposite(new Position(4, 5), PieceColor.BLACK));
  }

  @Test
  public void testMovePiece() {
    Bishop bishop = new Bishop(6, 7, PieceColor.WHITE, board);
    board.setPiece(bishop);
    King king = new King(2, 2, PieceColor.WHITE, board);
    board.setKing(king);
    board.movePiece(new Movement(new Position(2, 3), bishop.getPosition()),
        true);
    Map<Position, Piece> expected = new HashMap<Position, Piece>();
    expected.put(new Position(2, 3), bishop);
    expected.put(new Position(2, 2), king);
    assertTrue(expected.equals(board.getSquares()));
  }

  @Test
  public void testIsKingInCheck() {
    Rook rook = new Rook(1, 3, PieceColor.WHITE, board);
    board.setPiece(rook);
    board.setKing(new King(3, 3, PieceColor.BLACK, board));
    assertTrue(board.isKingInCheck(PieceColor.BLACK));
  }

  @Test
  public void testWillKingBeInCheck() {
    Rook rookWhite = new Rook(6, 3, PieceColor.WHITE, board);
    board.setPiece(rookWhite);
    Queen queen = new Queen(6, 4, PieceColor.BLACK, board);
    board.setPiece(queen);
    board.setKing(new King(3, 3, PieceColor.BLACK, board));
    Map<Position, Piece> expected = board.getSquares();
    assertTrue(board.willKingBeInCheck(new Movement(queen.getPosition(),
        new Position(6, 1))));
    assertTrue(board.getSquares().equals(expected));
    assertTrue(queen.getRow() == 6 && queen.getCol() == 4);
  }

  @Test
  public void testMovePieceWithPosition() {
    Rook rook = new Rook(5, 4, PieceColor.WHITE, board);
    board.setPiece(rook);
    board.setKing(new King(2, 2, PieceColor.WHITE, board));
    board.movePiece(new Position(5, 4), new Position(3, 4));
    assertTrue(board.getPiece(new Position(3, 4)).equals(rook));
  }

  @Test
  public void testIsCheckMate() {
    Rook rook = new Rook(0, 7, PieceColor.WHITE, board);
    King whiteKing = new King(3, 5, PieceColor.WHITE, board);
    King blackKing = new King(3, 7, PieceColor.BLACK, board);
    board.setPiece(rook);
    board.setKing(whiteKing);
    board.setKing(blackKing);
    assertTrue(board.isCheckMate(PieceColor.BLACK));
  }

  @Test
  public void testIsDraw() {
    Rook rook = new Rook(1, 1, PieceColor.WHITE, board);
    King whiteKing = new King(0, 2, PieceColor.WHITE, board);
    King blackKing = new King(0, 0, PieceColor.BLACK, board);
    board.setPiece(rook);
    board.setKing(whiteKing);
    board.setKing(blackKing);
    assertTrue(board.isDraw(PieceColor.BLACK));
    assertFalse(board.isDraw(PieceColor.WHITE));
  }
}
