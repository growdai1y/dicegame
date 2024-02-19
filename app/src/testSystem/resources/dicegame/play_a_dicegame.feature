Feature: Play a dicegame

    Scenario: A player plays a dicegame and win.
        Given Die1 is 3
        Given Die2 is 4
        Then A player wins if sum is 7
