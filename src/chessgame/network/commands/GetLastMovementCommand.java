package chessgame.network.commands;

import chessgame.model.ChessModel;

/**
 * @author Adam Snaider
 *
 */
public class GetLastMovementCommand extends ChessCommand {

  private static final long serialVersionUID = 3213629361467379984L;

  @Override
  public void execute(ChessModel model) {
    result = model.getLastMovement();
  }
}
