package se.kau.android.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText number1, number2;
    TextView result, operators;
    Button plusButton, minusButton, multButton, divButton, clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.editTextNumber1);
        number2 = findViewById(R.id.editTextNumber2);

        result = findViewById(R.id.textViewResult);
        operators = findViewById(R.id.textViewOperators);

        plusButton = findViewById(R.id.plusButton);
        minusButton = findViewById(R.id.minusButton);
        multButton = findViewById(R.id.multButton);
        divButton = findViewById(R.id.divisionButton);
        clearButton = findViewById(R.id.clearButton);

    }

    public void plusClick(View v){

        operators.setText("+");

        if(!TextUtils.isEmpty(number1.getText().toString()) && !TextUtils.isEmpty(number2.getText().toString())){
            double mynumber1 = Double.parseDouble(number1.getText().toString());
            double mynumber2 = Double.parseDouble(number2.getText().toString());

            double theResult = mynumber1 + mynumber2;

            result.setText("Result = " + theResult );
        } else{
            return;
        }

    }

    public void minusClick(View v){

        operators.setText("-");

        if(!TextUtils.isEmpty(number1.getText().toString()) && !TextUtils.isEmpty(number2.getText().toString())){
            double mynumber1 = Double.parseDouble(number1.getText().toString());
            double mynumber2 = Double.parseDouble(number2.getText().toString());

            double theResult = mynumber1 - mynumber2;

            result.setText("Result = " + theResult );
        } else{
            return;
        }

    }

    public void multClick(View v){

        operators.setText("x");

        if(!TextUtils.isEmpty(number1.getText().toString()) && !TextUtils.isEmpty(number2.getText().toString())){
            double mynumber1 = Double.parseDouble(number1.getText().toString());
            double mynumber2 = Double.parseDouble(number2.getText().toString());

            double theResult = mynumber1 * mynumber2;

            result.setText("Result = " + theResult );
        } else{
            return;
        }

    }

    public void divClick(View v){

        operators.setText("/");

        if(!TextUtils.isEmpty(number1.getText().toString()) && !TextUtils.isEmpty(number2.getText().toString())){
            double mynumber1 = Double.parseDouble(number1.getText().toString());
            double mynumber2 = Double.parseDouble(number2.getText().toString());

            double theResult = mynumber1 / mynumber2;

            result.setText("Result = " + theResult );
        } else{
            return;
        }

    }

    public void clearClick(View v){

        result.setText("");
        number1.setText("");
        number2.setText("");
        operators.setText("");

    }

}