package se.kau.android.hangmangame;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = "Hangman";
    private final WordsClass wordList = new WordsClass();
    private char[] charArr;
    private static final int MAX_TRIES = 6;
    private TextView txtWord, txtLetterIn, txtResult, txtChancesCounter;
    private String word, letterIn = " ", wordToChar;
    private int chances = 0;
    private EditText userInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "gameActivity onCreate running");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        txtWord = findViewById(R.id.txtWords);
        userInput = findViewById(R.id.userInput);
        txtLetterIn = findViewById(R.id.txtLetterIn);
        txtResult = findViewById(R.id.txtResult);
        txtChancesCounter = findViewById(R.id.txtChancesCounter);

        hangman();

        // här bygger jag lyssnare för användarens input
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.length() != 0){
                    checkWinOrLose(s.charAt(0));
                    userInput.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    public void hangman(){
        txtChancesCounter.setText("7");
        txtChancesCounter.setTextColor(Color.parseColor("#928722"));
        word =  wordList.showWord(); // skapa ett ord från klassen WordClass.
        charArr = word.toCharArray(); // byte ord till list av char

        for (int i = 1; i < charArr.length - 1; i++){
            charArr[i]  = '_';
        }

        wordToChar = String.valueOf(charArr);
        StringBuilder displayedWord = new StringBuilder(" ");
        for (char c : charArr) {
            displayedWord.append(c).append(" ");
        }
        txtWord.setText(displayedWord.toString());
    }

    private void checkWinOrLose(char letter){
        if(word.indexOf(letter ) >= 0){
            Log.d(TAG,"letter was found");
            if(wordToChar.indexOf(letter) == -1){
                Log.d(TAG,"letter is not displayed");
                int letterPosition = word.indexOf(letter); // Position av index (bokstav)
                for(int i = 0; i < letterPosition; i++){
                    charArr[letterPosition] = word.charAt(letterPosition);
                    letterPosition = word.indexOf(letter, letterPosition + 1);
                }
                wordToChar = String.valueOf(charArr);

                StringBuilder displayedWord = new StringBuilder(" ");
                for (char c : charArr) {
                    displayedWord.append(c).append(" ");
                }
                txtWord.setText(displayedWord.toString());

                if(!wordToChar.contains("_")){
                    Log.d(TAG,"Winning");
                    txtResult.setText(R.string.you_win);
                    txtResult.setTextColor(Color.parseColor("#1BBA70"));
                    closeKeyboard();
                }
            }
        }
        else{
            if(wordToChar.contains("_")){
                if(chances >= MAX_TRIES) {
                    Log.d(TAG, "Losing");
                    txtResult.setText(R.string.you_lose);
                    txtResult.setTextColor(Color.parseColor("#FD0000"));
                    txtWord.setText(word);
                    closeKeyboard();
                }
            }
        }

        if(word.indexOf(letter ) < 0){
            Log.d(TAG,"letter was not found");
            if(letterIn.indexOf(letter) < 0){
                letterIn = letterIn + (letter + "- ");
                txtLetterIn.setText(letterIn);
                chances++;
                int result = ((MAX_TRIES + 1) - chances);
                txtChancesCounter.setText(String.valueOf(result));

                if(result < 4){
                    txtChancesCounter.setTextColor(Color.RED);
                }
            }
        }
    }

    public void resetClick(View v) {
        Log.d(TAG,"resetClick button onclick");
        hangman();
        txtResult.setText("");
        userInput.setText("");
        txtLetterIn.setText("");
        letterIn = "";
    }

    // slutligen är denna metoden, jag fick den från https://www.youtube.com/watch?v=CW5Xekqfx3I
    // det är inte viktig men jag ville lära mig hur man kan stänga keyboard :)
    private void closeKeyboard(){
        View v = this.getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}