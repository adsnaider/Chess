package chessgame.network.commands;

import chessgame.model.ChessModel;

/**
 * @author Adam Snaider
 *
 */
public class GetMyColorCommand extends ChessCommand {
  private static final long serialVersionUID = 4278262307581869813L;

  @Override
  public void execute(ChessModel model) {
    result = model.getMyColor();
  }
}
