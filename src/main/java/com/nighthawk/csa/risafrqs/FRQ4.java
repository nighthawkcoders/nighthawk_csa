package com.nighthawk.csa.risafrqs;

import java.util.Random;

public class FRQ4 {
    private int startingCoins; // starting number of coins
    private int maxRounds; // maximum number of rounds played

    public FRQ4(int s, int r)
    {
        startingCoins = s;
        maxRounds = r;
    }

    /** Returns the number of coins (1, 2, or 3) that player 1 will spend.
     */
    public int getPlayer1Move()
    {
        Random rand = new Random();
        int result = rand.nextInt(3) + 1;
        System.out.println("Player 1 spends:" + result);
        return result;
    }

    /** Returns the number of coins (1, 2, or 3) that player 2 will spend, as described in part (a).
     */
    public int getPlayer2Move(int round)
    {
        int result;
        if ((round % 3) == 0) {
            result = 3;
        }
        else if ((round % 2) == 0) {
            result = 2;
        }
        else {
            result = 1;
        }
        System.out.println("Player 2 spends:" + result);
        return result;

    }

    /** Plays a simulated game between two players, as described in part (b).
     */
    public void playGame() {
        /**Notes:
         Both players will start with same number of coins
         Code section to determine winner
         **/
        int p1coins = startingCoins; // don’t use s because it’s attribute
        int p2coins = startingCoins;
        int round = 1;
        int p1spend; // determining how much player 1 spends
        int p2spend; // determining how much player 2 spends
        /** conditions for the game to be played continually
         **/
        while (round <= maxRounds && p1coins >= 3 && p2coins >= 3) {
            p1spend = getPlayer1Move(); // no parameter for this one
            p2spend = getPlayer2Move(round); // round number included
            p1coins -= p1spend; // coins spent in round
            p2coins -= p2spend; // coins spent in round
            System.out.println("Player 1 has:" + p1coins);
            System.out.println("Player 2 has:" + p2coins);
            if (p2spend == p1spend)
                p2coins++;
            else if (Math.abs(p2spend - p1spend) == 1) // difference is 1
                p2coins++;
            else
                p1coins += 2;
            round++; // increment round
            System.out.println("This is round" + round);
        }
        /** determining the winner*/
        if (p1coins == p2coins)
            System.out.println("Tied");
        else if (p1coins > p2coins)
            System.out.println("Player 1 Wins");
        else
            System.out.println("Player 2 Wins");
    }

    public static void main(String[] args) {
        FRQ4 c = new FRQ4(10, 20);
        c.playGame();
    }

}
