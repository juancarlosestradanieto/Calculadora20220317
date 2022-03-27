package com.juancarlosestradanieto.calculadora20220317;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumberA;
    boolean editTextNumberAHasTheFocus;
    EditText editTextNumberB;
    boolean editTextNumberBHasTheFocus;
    int[] numberButtonIds;
    public Button buttonDelete;
    public EditText actualResult;
    public EditText expectedResult;
    public Button additionButton;
    public Button subtractionButton;
    public Button multiplicationButton;
    public Button divisionButton;
    public TextView resultMessage;
    public TextView textTimer;
    public TextView textRightAnswers;
    public TextView textWrongAnswers;
    public int numberOfRightAnswers;
    public int numberOfWrongAnswers;
    CountDownTimer countDownTimer;
    public EditText resultComparison;
    public EditText editTextOperationSymbol;
    int[] operationButtonIds;
    public Button equalsButton;
    int countdownStarter;
    public ImageButton buttonRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expectedResult = (EditText)findViewById(R.id.expectedResult);
        resultComparison = (EditText)findViewById(R.id.resultComparison);
        editTextOperationSymbol = (EditText)findViewById(R.id.editTextOperationSymbol);
        actualResult = (EditText)findViewById(R.id.actualResult);
        resultMessage = (TextView)findViewById(R.id.resultMessage);
        buttonRestart = (ImageButton)findViewById(R.id.buttonRestart);

        //RIGHT AND WRONG ANSWER COUNTER
        numberOfRightAnswers = 0;
        numberOfWrongAnswers = 0;

        //TIMER
        textTimer = (TextView)findViewById(R.id.textTimer);
        textRightAnswers = (TextView)findViewById(R.id.textRightAnswers);
        textWrongAnswers = (TextView)findViewById(R.id.textWrongAnswers);

        countdownStarter = 20;

        //FIELDS THAT THE USER EDITS VIA NUMBER BUTTONS AND DELETE BUTTON
        editTextNumberA = (EditText)findViewById(R.id.editTextNumberA);
        editTextNumberA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editTextNumberAHasTheFocus = hasFocus;
            }
        });
        editTextNumberA.setShowSoftInputOnFocus(false);
        editTextNumberA.requestFocus();

        editTextNumberB = (EditText)findViewById(R.id.editTextNumberB);
        editTextNumberB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editTextNumberBHasTheFocus = hasFocus;
            }
        });
        editTextNumberB.setShowSoftInputOnFocus(false);

        //NUMBER BUTTONS
        numberButtonIds = new int[]{
            R.id.buttonNumber1,
            R.id.buttonNumber2,
            R.id.buttonNumber3,
            R.id.buttonNumber4,
            R.id.buttonNumber5,
            R.id.buttonNumber6,
            R.id.buttonNumber7,
            R.id.buttonNumber8,
            R.id.buttonNumber9,
            R.id.buttonNumber0
        };

        for(int i = 0; i < numberButtonIds.length; i++)
        {
            int currentButtonId = numberButtonIds[i];

            Button currentButtonNumber = (Button) findViewById(currentButtonId);
            currentButtonNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editTextNumberAHasTheFocus)
                    {
                        editTextNumberA.setText(editTextNumberA.getText().toString()+currentButtonNumber.getText().toString());
                        //editTextNumberA.setSelection(editTextNumberA.getText().toString().length());
                        setCursorOfEditTextAtTheEnd(editTextNumberA);
                    }
                    if(editTextNumberBHasTheFocus)
                    {
                        editTextNumberB.setText(editTextNumberB.getText().toString()+currentButtonNumber.getText().toString());
                        //editTextNumberB.setSelection(editTextNumberB.getText().toString().length());
                        setCursorOfEditTextAtTheEnd(editTextNumberB);
                    }
                }
            });
        }

        //DELETE BUTTON
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNumberAHasTheFocus)
                {
                    String editTextNumberAValue = removeLastChar(editTextNumberA.getText().toString());
                    editTextNumberA.setText(editTextNumberAValue);
                    setCursorOfEditTextAtTheEnd(editTextNumberA);
                }
                if(editTextNumberBHasTheFocus)
                {
                    String editTextNumberBValue = removeLastChar(editTextNumberB.getText().toString());
                    editTextNumberB.setText(editTextNumberBValue);
                    setCursorOfEditTextAtTheEnd(editTextNumberB);
                }
            }
        });

        //OPERATIONS BUTTONS - ADDITION, SUBSTRACTION, MULTIPLICATION AND DIVISION
        operationButtonIds = new int[]{
                R.id.additionButton,
                R.id.substractionButton,
                R.id.multiplicationButton,
                R.id.divisionButton
        };

        for(int i = 0; i < operationButtonIds.length; i++)
        {
            int currentButtonId = operationButtonIds[i];
            Button currentButton = (Button) findViewById(currentButtonId);

            currentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    editTextOperationSymbol.setText(b.getText().toString());
                    editTextNumberB.requestFocus();
                }
            });
        }

        //CALCULATE ACTUAL RESULT
        equalsButton = (Button) findViewById(R.id.equalsButton);
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String operationSymbol = editTextOperationSymbol.getText().toString();

                Float editTextNumberAValue = Float.valueOf(editTextNumberA.getText().toString().equals("") ? "0" : editTextNumberA.getText().toString());
                Float editTextNumberBValue = Float.valueOf(editTextNumberB.getText().toString().equals("") ? "0" : editTextNumberB.getText().toString());

                Float actualResultValue = Float.valueOf("0");

                switch (operationSymbol)
                {
                    case "+": {
                        actualResultValue = editTextNumberAValue + editTextNumberBValue;
                    }
                    break;
                    case "-": {
                        actualResultValue = editTextNumberAValue - editTextNumberBValue;
                    }
                    break;
                    case "*": {
                        actualResultValue = editTextNumberAValue * editTextNumberBValue;
                    }
                    break;
                    case "/": {
                        if(editTextNumberBValue == 0)
                        {
                            System.out.println ("Can't be divided by Zero ");
                            actualResultValue = Float.valueOf("0");
                        }
                        else
                        {
                            actualResultValue = editTextNumberAValue / editTextNumberBValue;
                        }
                    }
                    break;
                }

                actualResult.setText(String.valueOf(actualResultValue));

                Float expectedResultValue = Float.valueOf(expectedResult.getText().toString());

                if(Float.compare(actualResultValue, expectedResultValue) == 0)
                {
                    Log.d("myTag", "values are the same");
                    resultMessage.setText(R.string.values_are_the_same);
                    increaseNumberOfRightAnswers();
                    resultComparison.setText(R.string.equals);
                    generateNewOperation();
                }
                else
                {
                    Log.d("myTag", "values are different");
                    resultMessage.setText(R.string.values_are_different);
                    increaseNumberOfWrongAnswers();
                    resultComparison.setText(R.string.not_equals_symbol);
                }

            }
        });

        //RESTART APP
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int numberOfRightAnswers = 0;
                textRightAnswers.setText(String.valueOf(numberOfRightAnswers));

                int numberOfWrongAnswers = 0;
                textWrongAnswers.setText(String.valueOf(numberOfWrongAnswers));

                countdownStarter = 20;

                countDownTimer.cancel();
                generateNewOperation();

            }
        });

        generateNewOperation();

    }

    public void generateNewOperation()
    {
        //RANDOM NUMBER
        // Obtain a number between [5 - 49].
        int min = 5;
        int max = 50;
        Random rand = new Random();
        int expectedResultValue = rand.nextInt(max - min + 1) + min;
        max = expectedResultValue;
        int numberBValue = rand.nextInt(max - min + 1) + min;

        editTextNumberA.setText("");
        editTextOperationSymbol.setText(R.string.question_mark);
        editTextNumberB.setText(String.valueOf(numberBValue));
        //editTextNumberB.setText("");
        actualResult.setText("0");
        resultComparison.setText(R.string.not_equals_symbol);
        expectedResult.setText(String.valueOf(expectedResultValue));

        resultMessage.setText(R.string.guess_the_operation);

        editTextNumberA.requestFocus();

        upateCountDown();

    }

    public void increaseNumberOfRightAnswers()
    {
        numberOfRightAnswers++;
        textRightAnswers.setText(String.valueOf(numberOfRightAnswers));
        countdownStarter--;
        countDownTimer.cancel();
    }

    public void increaseNumberOfWrongAnswers()
    {
        numberOfWrongAnswers++;
        textWrongAnswers.setText(String.valueOf(numberOfWrongAnswers));
        //countDownTimer.cancel();
    }

    public void upateCountDown()
    {
        countDownTimer = new CountDownTimer(countdownStarter * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                textTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                numberOfWrongAnswers++;
                textWrongAnswers.setText(String.valueOf(numberOfWrongAnswers));
                generateNewOperation();
            }
        };
        //countDownTimer.cancel();
        countDownTimer.start();
    }

    public static String removeLastChar(String s)
    {
        return (s == null || s.length() == 0)
                ? ""
                : (s.substring(0, s.length() - 1));
    }

    public static void setCursorOfEditTextAtTheEnd(EditText editText)
    {
        editText.setSelection(editText.getText().toString().length());
    }
}