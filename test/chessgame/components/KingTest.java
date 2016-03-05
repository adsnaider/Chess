package chessgame.components;

import static chessgame.components.ChessTestUtils.booleanArrayToSet;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Adam Snaider
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class KingTest {

  @Mock
  private Board boardMock;
  private King king;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    king = new King(2, 2, PieceColor.WHITE, boardMock);
  }

  @Test
  public void testPossibleMoves() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, true, true, true, false, false, false, false },
        { false, true, false, true, false, false, false, false },
        { false, true, true, true, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    Set<Position> expected = booleanArrayToSet(squares);
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isBlank((Position) anyObject())).thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> moves = king.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMovesWithOppositeKing() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, true, true, false, false, false, false, false },
        { false, true, false, false, false, false, false, false },
        { false, true, true, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    Set<Position> expected = booleanArrayToSet(squares);
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isBlank((Position) anyObject())).thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(2, 4), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    when(
        boardMock.willKingBeInCheck(new Movement(king.getPosition(),
            new Position(1, 3)))).thenReturn(true);
    when(
        boardMock.willKingBeInCheck(new Movement(king.getPosition(),
            new Position(2, 3)))).thenReturn(true);
    when(
        boardMock.willKingBeInCheck(new Movement(king.getPosition(),
            new Position(3, 3)))).thenReturn(true);
    Set<Position> moves = king.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withOppositePiece() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, true, true, true, false, false, false, false },
        { false, true, false, true, false, false, false, false },
        { false, true, true, true, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    Set<Position> expected = booleanArrayToSet(squares);
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(1, 3), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.isOpposite(new Position(3, 3), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> moves = king.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withAllyPiece() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, true, true, false, false, false, false, false },
        { false, true, false, true, false, false, false, false },
        { false, true, false, true, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    Set<Position> expected = booleanArrayToSet(squares);
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(1, 3), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(3, 2), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> moves = king.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withKingInCheck() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, false, true, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, true, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    Set<Position> expected = booleanArrayToSet(squares);
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(5, 5), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(1, 1), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(true);
    for (int row = 0; row < 8; row++) {
      when(
          boardMock.willKingBeInCheck(eq(new Movement(king.getPosition(),
              new Position(row, 2))))).thenReturn(false);
    }
    Set<Position> moves = king.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }
}
