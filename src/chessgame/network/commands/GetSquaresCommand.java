/**
 * 
 */
package chessgame.network.commands;

import chessgame.model.ChessModel;

/**
 * @author Adam Snaider
 *
 */
public class GetSquaresCommand extends ChessCommand {

  private static final long serialVersionUID = -6583239997442461483L;

  @Override
  public void execute(ChessModel model) {
    result = model.getSquares();
  }
}
