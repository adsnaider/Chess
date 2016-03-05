package chessgame.network.commands;

import java.io.Serializable;

import chessgame.model.ChessModel;

/**
 * @author Adam Snaider
 *
 */
public class ChessCommand implements Serializable {

  private static final long serialVersionUID = 1250501606091987380L;
  protected Object result;
  private NetworkAction networkAction;

  /**
   * Creates a new command with a newtworkAction.
   * 
   * @param networkAction
   *          Action that will be transmited with the command.
   */
  public ChessCommand(NetworkAction networkAction) {
    this.networkAction = networkAction;
  }

  /**
   * Creates a new Command with the default value GAME_COMMAND.
   */
  public ChessCommand() {
    networkAction = NetworkAction.GAME_COMMAND;
  }

  /**
   * executes the command using the model.
   * 
   * @param model
   *          Model of the game that will recieve the command.
   */
  public void execute(ChessModel model) {

  }

  /**
   * Getter of the NetworkAction.
   * 
   * @return The networkAction set by the constructor.
   */
  public NetworkAction getNetworkAction() {
    return networkAction;
  }

  /**
   * Getter of the result.
   * 
   * @return Object containing the result of the command.
   */
  public Object getResult() {
    return result;
  }

  @Override
  public String toString() {
    return this.getClass().getName();
  }
}
