package com.company;

import javax.swing.*;
import java.util.Random;

/**
 * Class for app. App is created by masterGUI. Runs new games every time with playGame
 */
public class GlorifiedTimer {
    public int min; // minimum time in milliseconds
    public int max; // maximum time in milliseconds
    public int WORDS;
    public int totalWords; // total amount of words
    public JLabel wordsLeft; // the enemy words left JLabel
    public long startTimer = 0; // timer for each time
    public long endTimer = 0; // timer to see if random delay has been met
    public long difference = 0;
    public long randomDelay = 0;
    public Random rand = new Random();

    /**
     * Sets the new values given by app.
     */
    public void set_values(int totalWords, JLabel wordsLeft) {
        this.totalWords = totalWords;
        this.wordsLeft = wordsLeft;
    }

    /**
     * Gets checked while app is waiting for user to input a word
     */
    public void check_timer(){
        endTimer = System.currentTimeMillis(); // sets timer equal to current runtime
        if((endTimer - startTimer) > randomDelay && totalWords !=0){  // gets difference between timers
            totalWords--;  // lowers word by 1 if delay has been met
            wordsLeft.setText(String.valueOf(totalWords));
            run_timer(); // runs timer to reset
        }
    }

    /**
     * RUns the timer each time the bot types a word
     */
    public void run_timer(){
        startTimer = System.currentTimeMillis(); // starts timer
        endTimer = startTimer;                   // makes endTimer equal to start
        randomDelay = rand.nextInt((max - min) + 1) + min; // generates random delay between min and max
        wordsLeft.setText(String.valueOf(totalWords));
    }

    public void reset_bot(){
        totalWords = WORDS;
    }

    /**
     * Sets the difficulty of the bot. Bot goes faster from 1-3
     * @param d The difficulty from the user selection in masterGUI. 1 = Easy, 2 = Medium, 3 = Hard
     */
    public void setDifficulty(int d){
        if (d == 1) {
            max = 3500;
            min = 3000;
        } else if (d == 2) {
            max = 2500;
            min = 2000;
        } else {
            max = 2250;
            min = 1750;
        }
    }
}
