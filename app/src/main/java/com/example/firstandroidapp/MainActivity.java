package com.example.firstandroidapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String[] operators = new String[]{"+","-","*","÷"};
    List<Integer> numbers = new ArrayList<>();
    List<String> operations = new ArrayList<>();

    List<Integer> tempNumbers = new ArrayList<>();
    List<String> tempOperations = new ArrayList<>();
    List<Integer> calculationBuffer = new ArrayList<>();
    TextView textToUpdate;
    String input = "2*3+4+5+6+7+8";
    int _count = 0;
    String numberBuffer = "";
    int result = 0;
    String output = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textToUpdate = findViewById(R.id.text);
        ClearLog();
        SubscribeButtons();
        ParseInput();
        Log("До: " + operations);
        Log("До: " + numbers);
        FirstIteration();
        Log("После 1: " + operations);
        Log("После 1: " + numbers);


//        Handler handler = new Handler();
//        final Runnable r = new Runnable() {
//            public void run() {
//                handler.postDelayed(this, 16);
//                _count++;
//                String newCount = String.valueOf(_count);
//                textToUpdate.setText(newCount);
//            }
//        };
//        handler.postDelayed(r, 16);
    }

    private void ParseInput(){
        int inputLength = input.length();

        for (int i = 0; i < inputLength; i++)
        {
            char sign = input.charAt(i);
            output += sign;
            if(Character.isDigit(sign))
            {
                numberBuffer += Character.toString(sign);
                if(i<input.length()-1){
                    if (!Character.isDigit(input.charAt(i + 1))) {
                        numbers.add(Integer.parseInt(numberBuffer));
                        numberBuffer = "";
                    }
                }
                else{
                    numbers.add(Integer.parseInt(numberBuffer));
                    numberBuffer = "";
                }
            }
            else
            {
                if(IsOperator(sign)){
                    operations.add(Character.toString(sign));
                }
            }
        }
    }

    private void FirstIteration(){

        while(operations.contains("*") || operations.contains("÷")){
           for (int i = 0; i<operations.size(); i++){
                switch (operations.get(i)){
                    case "+":
                    case "-":
                        if(i==0) {
                            tempNumbers.add(numbers.get(i));
                            tempOperations.add(operations.get(i));
                            break;
                        }
                        if(i<operations.size()-1){
                            if(!PrevOpImportant(i) && !CurrentOpImportant(i)){
                                tempNumbers.add(numbers.get(i));
                            }
                            tempOperations.add(operations.get(i));
                            break;
                        }
                        if(i == operations.size()-1)
                        {
                            tempOperations.add(operations.get(i));
                            tempNumbers.add(numbers.get(i+1));
                        }
                        break;
                    case "*":
                    case "÷":
                        int k = i;
                        tempNumbers.add(numbers.get(i) * numbers.get(i+1));
                        while(k<operations.size()-1 && NextOpImportant(i)){
                            if (operations.get(k+1).equals("*")){
                                tempNumbers.set(i, tempNumbers.get(i) * numbers.get(k+1));
                            }
                            if(operations.get(k+1).equals("÷")){
                                tempNumbers.set(i, tempNumbers.get(i) / numbers.get(k+1));
                            }
                            k+=1;
                        }

                        Log(operations.toString());
//                        if (operations.get(k).equals("*")){
//                            numbersBuffer.set(i, numbersBuffer.get(i) * numbers.get(k));
//                            Log(numbersBuffer.get(i).toString());
//
//                            if(operations.size()%2!=0){
//                                numbersBuffer.set(i, numbersBuffer.get(i) * numbers.get(k+1));
//                                Log(numbersBuffer.get(i).toString());
//                            }
//                        }
//                        else{
//                            numbersBuffer.set(i, numbersBuffer.get(i) / numbers.get(k));
//                            Log(numbersBuffer.get(i).toString());
//
//                            if(operations.size()%2!=0){
//                                numbersBuffer.set(i, numbersBuffer.get(i) / numbers.get(k+1));
//                                Log(numbersBuffer.get(i).toString());
//                            }
//                        }

                        i=k;
                        break;
                }
            }
            numbers = new ArrayList<>(tempNumbers);
            operations = new ArrayList<>(tempOperations);
            tempNumbers.clear();
            tempOperations.clear();
        }
    }

    private void SecondIteration(){
        Integer result = numbers.get(0);
        for (int i = 0; i < operations.size(); i++){
            switch (operations.get(i)){
                case "+":
                    result += numbers.get(i+1);
                    break;
                case "-":
                    result -= numbers.get(i+1);
                    break;
            }
        }
        textToUpdate.append("=" + result);
    }

    private boolean IsOperator(Character sign){
        for (String operator : operators) {
            if (sign.toString().equals(operator)) {
                return true;
            }
        }
        return false;
    }

    private boolean PrevOpImportant(int index){
        if(Objects.equals(operations.get(index - 1), "*") || Objects.equals(operations.get(index - 1), "÷")){
            return true;
        }
        return false;
    }
    private boolean CurrentOpImportant(int index){
        if(Objects.equals(operations.get(index), "*") || Objects.equals(operations.get(index), "÷")){
            return true;
        }
        return false;
    }
    private boolean NextOpImportant(int index){
        if(Objects.equals(operations.get(index + 1), "*") || Objects.equals(operations.get(index + 1), "÷")){
            return true;
        }
        return false;
    }

    private void Log(String text){
        textToUpdate.append("\n" + text);
    }

    private void LogAppend(String text){
        textToUpdate.append(text);
    }

    private void ClearLog(){
        textToUpdate.setText("");
    }

    View.OnClickListener listener = new View.OnClickListener(){
        public void onClick(View view) {
            Button b = (Button)view;
            WriteText(b.getText().toString());
        }
    };

    private void SubscribeButtons(){
        Button mClickButton2 = (Button)findViewById(R.id.button2);
        mClickButton2.setOnClickListener(listener);
        Button mClickButton3 = (Button)findViewById(R.id.button3);
        mClickButton3.setOnClickListener(listener);
        Button mClickButton4 = (Button)findViewById(R.id.button4);
        mClickButton4.setOnClickListener(listener);
        Button mClickButton5 = (Button)findViewById(R.id.button5);
        mClickButton5.setOnClickListener(listener);
        Button mClickButton6 = (Button)findViewById(R.id.button6);
        mClickButton6.setOnClickListener(listener);
        Button mClickButton7 = (Button)findViewById(R.id.button7);
        mClickButton7.setOnClickListener(listener);
        Button mClickButton8 = (Button)findViewById(R.id.button8);
        mClickButton8.setOnClickListener(listener);
        Button mClickButton9 = (Button)findViewById(R.id.button9);
        mClickButton9.setOnClickListener(listener);
        Button mClickButton10 = (Button)findViewById(R.id.button10);
        mClickButton10.setOnClickListener(listener);
        Button mClickButton11 = (Button)findViewById(R.id.button11);
        mClickButton11.setOnClickListener(listener);
        Button mClickButton12 = (Button)findViewById(R.id.button12);
        mClickButton12.setOnClickListener(listener);
        Button mClickButton13 = (Button)findViewById(R.id.button13);
        mClickButton13.setOnClickListener(listener);
        Button mClickButton14 = (Button)findViewById(R.id.button14);
        mClickButton14.setOnClickListener(listener);
        Button mClickButton15 = (Button)findViewById(R.id.button15);
        mClickButton15.setOnClickListener(listener);
        Button mClickButton16 = (Button)findViewById(R.id.button16);
        mClickButton16.setOnClickListener(listener);
        Button mClickButton17 = (Button)findViewById(R.id.button17);
        mClickButton17.setOnClickListener(listener);
        Button mClickButton18 = (Button)findViewById(R.id.button18);
        mClickButton18.setOnClickListener(listener);
    }
    private void WriteText(String textToWrite){
        if(Arrays.asList(operators).contains(textToWrite)){
            if(input.length() > 0){
                if(Arrays.asList(operators).contains((Character.toString(input.charAt(input.length()-1))))){
                    input = input.substring(0, input.length()-1) + textToWrite;
                }else{
                    input+=textToWrite;
                }
            }
        }
        else{
            input+=textToWrite;
        }
        UpdateText(input);

    }

    private void UpdateText(String newText){
        textToUpdate.setText(newText);
    }
    public void ClearAll(View view) {
        input = "";
        textToUpdate.setText("");
        numbers.clear();
        operations.clear();
    }
    public void Delete(View view) {
        textToUpdate.setText(textToUpdate.getText().subSequence(0, textToUpdate.getText().length()-1));
        input = input.subSequence(0, input.length()-1).toString();
    }

    public void Calculate(View view){
        if(input == "") return;
        ParseInput();
//        LogAppend(numbers.toString());
//        LogAppend(operations.toString());
        if(input.contains("*") || input.contains("÷")){
            FirstIteration();
        }
        SecondIteration();
    }
}
