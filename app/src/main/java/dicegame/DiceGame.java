package dicegame;

public class DiceGame {
  private Die die1;
  private Die die2;
  private int numOfWinner;

  public DiceGame(Die die1, Die die2, int numOfWinner) {
    this.die1 = die1;
    this.die2 = die2;
    this.numOfWinner = numOfWinner;
  }
  
  public void play() {
    die1.roll();
    die2.roll();
  }

  public boolean isWon() {
    return (die1.getFaceValue() + die2.getFaceValue()) == numOfWinner;
  }
}
