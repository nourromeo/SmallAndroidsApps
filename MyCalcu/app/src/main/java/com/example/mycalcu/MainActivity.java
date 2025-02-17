package com.example.mycalcu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;

    MaterialButton buttoC, ButtonBrackOpen, ButtonBrackClose;
    MaterialButton buttonZero, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonPlus, buttonMinus, buttonDivide, buttonMultiple, buttonEquals;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttoC, R.id.button_c);
        assignId(ButtonBrackOpen, R.id.button_open_bracket);
        assignId(ButtonBrackClose, R.id.button_close_bracket);

        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiple, R.id.button_multiple);
        assignId(buttonEquals, R.id.button_equals);

        assignId(buttonAC, R.id.button_ac);
        assignId(buttonDot, R.id.button_dot);

        assignId(buttonZero, R.id.button_zero);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);

    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }
        if(buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        } else{
            dataToCalculate =dataToCalculate + buttonText;
        }
        solutionTv.setText(dataToCalculate);

    }

}