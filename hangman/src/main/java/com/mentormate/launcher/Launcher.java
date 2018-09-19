package com.mentormate.launcher;


import com.mentormate.game.Game;
import com.mentormate.game.Hangman;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Hangman();
        game.start();
    }
}
