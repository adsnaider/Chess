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
public class QueenTest {

  @Mock
  private Board boardMock;
  private Queen queen;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    queen = new Queen(3, 3, PieceColor.WHITE, boardMock);
  }

  @Test
  public void testPossibleMoves() {
    boolean[][] squares = {
        { true, false, false, true, false, false, true, false },
        { false, true, false, true, false, true, false, false },
        { false, false, true, true, true, false, false, false },
        { true, true, true, false, true, true, true, true },
        { false, false, true, true, true, false, false, false },
        { false, true, false, true, false, true, false, false },
        { true, false, false, true, false, false, true, false },
        { false, false, false, true, false, false, false, true } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = queen.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withOppositePiece() {
    boolean[][] squares = {
        { true, false, false, true, false, false, true, false },
        { false, true, false, true, false, true, false, false },
        { false, false, true, true, true, false, false, false },
        { true, true, true, false, true, true, true, true },
        { false, false, true, true, true, false, false, false },
        { false, true, false, true, false, true, false, false },
        { true, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(5, 3), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.isOpposite(new Position(5, 5), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = queen.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withAllyPiece() {
    boolean[][] squares = {
        { true, false, false, false, false, false, true, false },
        { false, true, false, false, false, true, false, false },
        { false, false, true, false, true, false, false, false },
        { true, true, true, false, true, true, true, true },
        { false, false, true, true, true, false, false, false },
        { false, true, false, true, false, false, false, false },
        { true, false, false, true, false, false, false, false },
        { false, false, false, true, false, false, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(5, 5), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(2, 3), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = queen.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withKingInCheck() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, true, false, false, false, false, false, false },
        { false, false, true, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, true, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(5, 5), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(1, 1), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(true);
    for (int row = 1; row < 5; row++) {
      when(
          boardMock.willKingBeInCheck(eq(new Movement(queen.getPosition(),
              new Position(row, row))))).thenReturn(false);
    }
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = queen.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }
}
