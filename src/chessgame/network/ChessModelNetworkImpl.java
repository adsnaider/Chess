package chessgame.network;

import java.util.Map;

import chessgame.components.Movement;
import chessgame.components.Piece;
import chessgame.components.PieceColor;
import chessgame.components.Position;
import chessgame.controller.Action;
import chessgame.model.ChessModel;
import chessgame.model.PieceType;
import chessgame.network.commands.ChessCommand;
import chessgame.network.commands.GetLastMovementCommand;
import chessgame.network.commands.GetMyColorCommand;
import chessgame.network.commands.GetSquaresCommand;
import chessgame.network.commands.IsCheckMateCommand;
import chessgame.network.commands.MakeMoveCommand;
import chessgame.network.commands.SetPieceForCrowningCommand;
import chessgame.view.ChessView;

/**
 * @author Adam Snaider
 *
 */
public class ChessModelNetworkImpl implements ChessModel {
  private NetworkClient client;
  private Position initialPos = null;
  private boolean whiteTurn = true;
  public final PieceColor myColor;
  private ChessView view;
  private final int startingTime = 60 * 10; // 10 Minutes.
  private int timeSeconds = startingTime; // Time left.

  /**
   * Creates a new socket.
   * 
   * @param host
   *          Host of the server.
   * @param port
   *          integer representing the port used by the server.
   */
  public ChessModelNetworkImpl(String host, int port) {
    this.client = new NetworkClient(host, port, this);
    this.myColor = getMyColor();
    decreaseTime();
  }

  public void setView(ChessView view) {
    this.view = view;
  }

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
      Piece piece = getSquares().get(pos);
      if (piece == null || piece.getColor() != myColor) {
        if (initialPos != null) {
          Movement move = new Movement(initialPos, pos);
          initialPos = null;
          return move;
        }
      } else if (piece.getColor() == myColor) {
        initialPos = pos;
      }
    }
    return null;
  }

  @Override
  public synchronized void setPieceForCrowning(PieceType pieceType) {
    ChessCommand cmd = new SetPieceForCrowningCommand(pieceType);
    client.send(cmd);
    cmd = client.recieve();
  }

  @Override
  public synchronized Action makeMove(Movement move) {
    ChessCommand cmd = new MakeMoveCommand(move);
    client.send(cmd);
    cmd = client.recieve();
    return (Action) cmd.getResult();
  }

  @SuppressWarnings("unchecked")
  @Override
  public synchronized Map<Position, Piece> getSquares() {
    ChessCommand cmd = new GetSquaresCommand();
    client.send(cmd);
    cmd = client.recieve();
    return (Map<Position, Piece>) cmd.getResult();
  }

  @Override
  public synchronized boolean isCheckMate(PieceColor color) {
    ChessCommand cmd = new IsCheckMateCommand(color);
    client.send(cmd);
    cmd = client.recieve();
    return (Boolean) cmd.getResult();
  }

  @Override
  public synchronized Movement getLastMovement() {
    ChessCommand cmd = new GetLastMovementCommand();
    client.send(cmd);
    cmd = client.recieve();
    return (Movement) cmd.getResult();
  }

  private synchronized PieceColor getMyColorNetwork() {
    ChessCommand cmd = new GetMyColorCommand();
    client.send(cmd);
    cmd = client.recieve();
    PieceColor opposite = (PieceColor) cmd.getResult();
    return (opposite == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
  }

  @Override
  public PieceColor getMyColor() {
    if (myColor == null) {
      return getMyColorNetwork();
    } else {
      return myColor;
    }
  }

  /**
   * Changes the turn.
   */
  public void changeTurn() {
    whiteTurn = !whiteTurn;
    view.update(getSquares());
    Movement lastMove = getLastMovement();
    view.markLastMovement(lastMove);
    view.repaint();
  }

  /**
   * Refreshes the view
   */
  public void refreshView() {
    view.update(getSquares());
    view.repaint();
  }

}
