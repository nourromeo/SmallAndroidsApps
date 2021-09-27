package se.kau.android.hangmangame;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class WordsClass extends AppCompatActivity {
    Random rand = new Random();
    // jag fick den från https://www.youtube.com/watch?v=snzuK-UB4qQ
    private static String[] words = {
            "matrix", "microwave", "nightclub", "sweden", "oxygen",
            "subway", "wave", "whiskey", "Nour", "Eritrea", "black",
            "minecraft", "youth", "yummy", "covid", "karlstad", "zombie", "white", "about",
            "account", "acid", "act", "addition", "again", "against",
            "agreement", "argument", "authority","clean", "clear",
            "cloud", "colour", "comfort", "company", "complex",
            "control", "country", "cover", "cow"
    };

    // method for word list
    public String showWord(){
        return words[rand.nextInt(words.length)];
    }

}

