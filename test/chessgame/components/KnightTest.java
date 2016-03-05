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
public class KnightTest {

  @Mock
  private Board boardMock;
  private Knight knight;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    knight = new Knight(1, 5, PieceColor.WHITE, boardMock);
  }

  @Test
  public void testPossibleMoves() {
    boolean[][] squares = {
        { false, false, false, true, false, false, false, true },
        { false, false, false, false, false, false, false, false },
        { false, false, false, true, false, false, false, true },
        { false, false, false, false, true, false, true, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    Set<Position> expected = booleanArrayToSet(squares);
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> moves = knight.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withKingInCheck() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, true, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    Set<Position> expected = booleanArrayToSet(squares);
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(2, 6), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(2, 2), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(true);
    for (int col = 2; col < 6; col++) {
      when(
          boardMock.willKingBeInCheck(eq(new Movement(knight.getPosition(),
              new Position(2, col))))).thenReturn(false);
    }
    Set<Position> moves = knight.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }
}
