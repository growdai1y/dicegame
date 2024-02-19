package dicegame;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class PlayDiceGameSteps {

    Die die1 = null;
    Die die2 = null;
    DiceGame game = null;

    @Given("Die1 is {int}")
    public void die1_is(Integer int1) {
        die1 = new Die(int1);
    }

    @Given("Die2 is {int}")
    public void die2_is(Integer int1) {
        die2 = new Die(int1);
    }

    @Then("A player wins if sum is 7")
    public void a_player_wins_if_sum_is_7() {
        game = new DiceGame(die1, die2, 7);
        game.play();

        assertTrue(game.isWon());
    }
}
