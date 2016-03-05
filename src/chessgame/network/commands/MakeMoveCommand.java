package chessgame.network.commands;

import chessgame.components.Movement;
import chessgame.model.ChessModel;

/**
 * @author Adam Snaider
 *
 */
public class MakeMoveCommand extends ChessCommand {
  private static final long serialVersionUID = 6415818383239115614L;
  private Movement move;

  /**
   * Command used to move a piece.
   * 
   * @param move
   *          Movement of the piece.
   */
  public MakeMoveCommand(Movement move) {
    this.move = move;
  }

  @Override
  public void execute(ChessModel model) {
    result = model.makeMove(move);
  }
}
