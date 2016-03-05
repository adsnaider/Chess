package chessgame.model;

import java.util.Map;

import chessgame.components.Bishop;
import chessgame.components.Board;
import chessgame.components.Knight;
import chessgame.components.Movement;
import chessgame.components.Pawn;
import chessgame.components.Piece;
import chessgame.components.PieceColor;
import chessgame.components.Position;
import chessgame.components.Queen;
import chessgame.components.Rook;
import chessgame.controller.Action;
import chessgame.network.NetworkServer;
import chessgame.network.commands.NetworkAction;
import chessgame.view.ChessView;

/**
 * @author Adam Snaider
 *
 */
public class ChessModelImpl implements ChessModel {
  private Board board;
  private Position initialPos = null;
  private boolean whiteTurn = true;
  private PieceColor myColor;
  private ChessView view;
  private NetworkServer server;
  private final int startingTime = 60 * 10; // 10 Minutes.
  private int timeSeconds = startingTime; // Time left.

  public int getTime() {
    return timeSeconds;
  }

  private void decreaseTime() {
    Thread decreaseTime = new Thread(new Runnable() {
      @Override
      public void run() {
        PieceColor colorTurn = whiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
        if (colorTurn == myColor) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          timeSeconds--;
        }
      }
    });
    decreaseTime.start();
  }

  public ChessModelImpl(PieceColor color) {
    myColor = color;
    initialize();
  }

  public void setServer(NetworkServer server) {
    this.server = server;
  }

  public PieceColor getMyColor() {
    return myColor;
  }

  /**
   * Creates a board and initializes it.
   */
  public void initialize() {
    board = new Board();
    board.initialize();
    decreaseTime();
  }

  public void setView(ChessView view) {
    this.view = view;
  }

  @Override
  public Map<Position, Piece> getSquares() {
    return board.getSquares();
  }

  /**
   * Sets a position coming from the view.
   * 
   * @param pos
   *          The position received by the view.
   * @return An Action representing what the view should do.
   */
  public Movement setData(Position pos) {
    PieceColor currentColor = whiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
    if (currentColor == myColor) {
      Piece piece = board.getPiece(pos);
      if (piece == null || piece.getColor() != currentColor) {
        if (initialPos != null) {
          Movement move = new Movement(initialPos, pos);
          initialPos = null;
          return move;
        }
      } else if (piece.getColor() == currentColor) {
        initialPos = pos;
      }
    }
    return null;
  }

  @Override
  public Action makeMove(Movement move) {
    Action action = Action.WAIT_FOR_MOVE;
    if (board.movePiece(move, true)) {
      Piece movedPiece = board.getPiece(move.finalPos);
      if (movedPiece instanceof Pawn
          && (move.finalPos.row == 0 || move.finalPos.row == 7)) {
        action = (movedPiece.getColor() == PieceColor.WHITE) ? Action.SELECT_WHITE_CROWNING
            : Action.SELECT_BLACK_CROWNING;
      } else {
        action = Action.MOVE;
      }
      whiteTurn = !whiteTurn;
      server.sendNetworkCommand(NetworkAction.CHANGE_TURN);
    }
    view.markLastMovement(move);
    view.update(getSquares());
    view.repaint();

    return action;
  }

  @Override
  public void setPieceForCrowning(PieceType pieceType) {
    Movement move = board.getLastMove();
    PieceColor lastMovementColor = whiteTurn ? PieceColor.BLACK
        : PieceColor.WHITE;
    Piece chosenPiece;
    switch (pieceType) {
    case QUEEN:
      chosenPiece = new Queen(move.finalPos, lastMovementColor, board);
      break;
    case ROOK:
      chosenPiece = new Rook(move.finalPos, lastMovementColor, board);
      break;
    case BISHOP:
      chosenPiece = new Bishop(move.finalPos, lastMovementColor, board);
      break;
    case KNIGHT:
      chosenPiece = new Knight(move.finalPos, lastMovementColor, board);
      break;
    default:
      chosenPiece = new Queen(move.finalPos, lastMovementColor, board);
    }
    board.setPiece(chosenPiece);
    view.update(getSquares());
    view.repaint();
    server.sendNetworkCommand(NetworkAction.REFRESH);
  }

  @Override
  public boolean isCheckMate(PieceColor color) {
    PieceColor colorTurn = (whiteTurn) ? PieceColor.WHITE : PieceColor.BLACK;
    if (colorTurn == color) {
      return board.isCheckMate(color);
    }
    return false;
  }

  @Override
  public Movement getLastMovement() {
    return board.getLastMove();
  }
}
