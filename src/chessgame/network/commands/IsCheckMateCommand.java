package chessgame.network.commands;

import chessgame.components.PieceColor;
import chessgame.model.ChessModel;

/**
 * @author Adam Snaider
 *
 */
public class IsCheckMateCommand extends ChessCommand {
  private static final long serialVersionUID = -1934843911940329586L;
  private PieceColor color;

  public IsCheckMateCommand(PieceColor color) {
    this.color = color;
  }

  @Override
  public void execute(ChessModel model) {
    result = model.isCheckMate(color);
  }
}
