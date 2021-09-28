package se.kau.android.hangmangame;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class WordsClass extends AppCompatActivity {
    Random rand = new Random();
    // jag fick den från https://www.youtube.com/watch?v=snzuK-UB4qQ
    // men klass var min ide
    private static String[] words = {
            "matrix", "microwave", "nightclub", "sweden", "oxygen",
            "subway", "wave", "whiskey", "Nour", "Eritrea", "black",
            "minecraft", "youth", "yummy", "covid", "karlstad", "zombie",
            "white", "about", "account", "acid", "act", "addition", "again",
            "against", "agreement", "argument", "authority","clean", "clear",
            "cloud", "colour", "comfort", "company", "complex", "control",
            "country", "cover", "cow", "joking", "joyful", "kiosk",
            "lucky", "luxury", "crush", "cry", "cup", "current",
            "curtain", "curve", "danger", "dark", "daughter",
            "decision", "detail", "development", "different",
            "direction", "dirty", "division"
    };

    // method for word list
    public String showWord(){
        return words[rand.nextInt(words.length)];
    }

}

