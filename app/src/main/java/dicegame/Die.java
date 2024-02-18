package dicegame;

public class Die {
  private int faceValue;

  public Die() {
    this.faceValue = -1;
  }

  public Die(int faceValue) {
    this.faceValue = faceValue;
  }

  public void roll() {
    if (faceValue == -1) {
      faceValue = (int) (Math.random() * 6) + 1;
    }
  }

  public int getFaceValue() {
    return faceValue;
  }
}
