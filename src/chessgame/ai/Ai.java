package chessgame.ai;

import chessgame.components.Board;
import chessgame.components.Movement;
import chessgame.components.MovingScore;
import chessgame.components.Piece;
import chessgame.components.PieceColor;
import chessgame.controller.ChessController;

public class Ai {
  // How deep into layers should the computer go to.
  private final int searchDepth = 5;
  private int currentScore = 0;
  private final int checkMateScore = 40;
  private final PieceColor color;
  private final PieceColor opColor;
  private final ChessController controller;

  public Ai(PieceColor color, ChessController controller) {
    this.color = color;
    this.opColor = color.oppositeColor();
    this.controller = controller;
  }

  private MovingScore nextMove(int k, Board currentBoard, boolean myTurn) {
    if (k == searchDepth) {
      if (currentBoard.isCheckMate(color)) {
        return new MovingScore(null, -checkMateScore);
      } else if (currentBoard.isCheckMate(opColor)) {
        return new MovingScore(null, checkMateScore);
      }
      return new MovingScore(null, currentBoard.getScore(color));
    }
    int chosenScore = 0;
    Movement chosenMove = null;
    for (Piece piece : currentBoard.getSquares().values()) {
      if (piece != null) {
        for (Movement move : piece.toMovementSet(piece.possibleMoves(true))) {
          currentBoard.movePiece(move, true);
          int score = nextMove(k + 1, currentBoard, !myTurn).score;
          if (chosenMove == null || score > chosenScore && myTurn || score < chosenScore && !myTurn) {
            chosenScore = score;
            chosenMove = move;
          }
          currentBoard.revertMove();
        }
      }
    }
    return new MovingScore(chosenMove, chosenScore);
  }


  public Movement makeMove(Board currentBoard) {
    return nextMove(0, currentBoard, true).move;
  }
}
