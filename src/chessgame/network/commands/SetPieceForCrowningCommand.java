package chessgame.network.commands;

import chessgame.model.ChessModel;
import chessgame.model.PieceType;

/**
 * @author Adam Snaider
 *
 */
public class SetPieceForCrowningCommand extends ChessCommand {
  private static final long serialVersionUID = -7158992003545334807L;
  private PieceType pieceType;

  /**
   * Sets a crowning piece.
   * 
   * @param pieceType
   *          PieceType which will be converted the pawn.
   */
  public SetPieceForCrowningCommand(PieceType pieceType) {
    this.pieceType = pieceType;
  }

  @Override
  public void execute(ChessModel model) {
    model.setPieceForCrowning(pieceType);
  }
}
