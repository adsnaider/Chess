package chessgame.components;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Adam
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BishopTest {
  @Mock
  private Board boardMock;
  private Bishop bishop;

  @Before
  public void setUp() throws Exception {
    bishop = new Bishop(4, 4, PieceColor.WHITE, boardMock);
  }

  @Test
  public void testPossibleMoves() {
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> moves = bishop.possibleMoves(true);
    Set<Position> expected = new HashSet<>();
    expected.add(new Position(5, 5));
    expected.add(new Position(6, 6));
    expected.add(new Position(7, 7));
    expected.add(new Position(3, 3));
    expected.add(new Position(2, 2));
    expected.add(new Position(1, 1));
    expected.add(new Position(0, 0));
    expected.add(new Position(5, 3));
    expected.add(new Position(6, 2));
    expected.add(new Position(7, 1));
    expected.add(new Position(3, 5));
    expected.add(new Position(2, 6));
    expected.add(new Position(1, 7));
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withOppositePiece() {
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(2, 6), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> moves = bishop.possibleMoves(true);
    Set<Position> expected = new HashSet<>();
    expected.add(new Position(5, 5));
    expected.add(new Position(6, 6));
    expected.add(new Position(7, 7));
    expected.add(new Position(3, 3));
    expected.add(new Position(2, 2));
    expected.add(new Position(1, 1));
    expected.add(new Position(0, 0));
    expected.add(new Position(5, 3));
    expected.add(new Position(6, 2));
    expected.add(new Position(7, 1));
    expected.add(new Position(3, 5));
    expected.add(new Position(2, 6));
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withAllyPiece() {
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(2, 6), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    Set<Position> moves = bishop.possibleMoves(true);
    Set<Position> expected = new HashSet<>();
    expected.add(new Position(5, 5));
    expected.add(new Position(6, 6));
    expected.add(new Position(7, 7));
    expected.add(new Position(3, 3));
    expected.add(new Position(2, 2));
    expected.add(new Position(1, 1));
    expected.add(new Position(0, 0));
    expected.add(new Position(5, 3));
    expected.add(new Position(6, 2));
    expected.add(new Position(7, 1));
    expected.add(new Position(3, 5));
    assertTrue(expected.equals(moves));
  }

  @Test
  public void testPossibleMoves_withKingInCheck() {
    when(boardMock.isAvailable((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(true);
    when(boardMock.isOpposite((Position) anyObject(), eq(PieceColor.WHITE)))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(0, 0), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(6, 6), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.isAvailable(new Position(7, 7), PieceColor.WHITE))
        .thenReturn(false);
    when(boardMock.isOpposite(new Position(1, 1), PieceColor.WHITE))
        .thenReturn(true);
    when(boardMock.willKingBeInCheck((Movement) anyObject())).thenReturn(false);
    for (int row = 1; row < 8; row++) {
      when(
          boardMock.willKingBeInCheck(eq(new Movement(bishop.getPosition(),
              new Position(row, 8 - row))))).thenReturn(true);
    }
    Set<Position> moves = bishop.possibleMoves(true);
    Set<Position> expected = new HashSet<>();
    expected.add(new Position(1, 1));
    expected.add(new Position(2, 2));
    expected.add(new Position(3, 3));
    expected.add(new Position(5, 5));
    assertTrue(expected.equals(moves));
  }
}
