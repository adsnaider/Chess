package chessgame.components;

public class MovingScore {
  public final Movement move;
  public final int score;
  
  public MovingScore(Movement move, int score) {
    this.move = move;
    this.score = score;
  }
}
