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
public class PawnTest {

  @Mock
  private Board boardMock;
  private Pawn pawn;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    pawn = new Pawn(1, 3, PieceColor.BLACK, boardMock);
  }

  @Test
  public void testPossibleMoves() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, true, false, false, false, false },
        { false, false, false, true, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(true);
    when(boardMock.isBlank((Position) anyObject())).thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = pawn.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withOppositePiece() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, true, true, true, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(false);
    when(boardMock.isBlank((Position) anyObject())).thenReturn(true);
    when(boardMock.isBlank((Position) anyObject())).thenReturn(true);
    when(boardMock.isBlank(new Position(2, 2))).thenReturn(false);
    when(boardMock.isBlank(new Position(2, 4))).thenReturn(false);
    when(boardMock.isBlank(new Position(3, 3))).thenReturn(false);
    when(boardMock.isOpposite(new Position(2, 2), PieceColor.BLACK))
        .thenReturn(true);
    when(boardMock.isOpposite(new Position(3, 3), PieceColor.BLACK))
        .thenReturn(true);
    when(boardMock.isOpposite(new Position(2, 4), PieceColor.BLACK))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = pawn.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withKingInCheck() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, true, false, false, false, false },
        { false, false, false, true, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(false);
    when(boardMock.isBlank((Position) anyObject())).thenReturn(true);
    when(boardMock.isBlank(new Position(2, 2))).thenReturn(false);
    when(boardMock.isOpposite(new Position(2, 2), PieceColor.BLACK))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(true);
    for (int row = 1; row < 7; row++) {
      when(
          boardMock.willKingBeInCheck(eq(new Movement(pawn.getPosition(),
              new Position(row, 3))))).thenReturn(false);
    }
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = pawn.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }
}
