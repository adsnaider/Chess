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
public class RookTest {

  @Mock
  private Board boardMock;
  private Rook rook;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    rook = new Rook(3, 5, PieceColor.BLACK, boardMock);
  }

  @Test
  public void testPossibleMoves() {
    boolean[][] squares = {
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { true, true, true, true, true, false, true, true },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = rook.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withOppositePiece() {
    boolean[][] squares = {
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, true, true, true, false, true, true },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(3, 2), PieceColor.BLACK))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = rook.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withAllyPiece() {
    boolean[][] squares = {
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { true, true, true, true, true, false, true, true },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(5, 5), PieceColor.BLACK))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = rook.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withKingInCheck() {
    boolean[][] squares = {
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, true, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false },
        { false, false, false, false, false, false, false, false } };
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.BLACK)))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(5, 5), PieceColor.BLACK))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(1, 5), PieceColor.BLACK))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    for (int col = 0; col < 8; col++) {
      when(
          boardMock.willKingBeInCheck(eq(new Movement(rook.getPosition(),
              new Position(3, col))))).thenReturn(true);
    }
    Set<Position> expected = booleanArrayToSet(squares);
    Set<Position> moves = rook.possibleMoves(true);
    assertTrue(expected.equals(moves));
  }
}
