package com.example.androidcalculator;
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
    List<Float> numbers = new ArrayList<>();
    List<String> operations = new ArrayList<>();
    List<Float> tempNumbers = new ArrayList<>();
    List<String> tempOperations = new ArrayList<>();
    TextView textToUpdate;
    String input = "";
    String numberBuffer = "";
    Float result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textToUpdate = findViewById(R.id.text);
        ClearAll();
        SubscribeButtons();
        ParseInput();
    }

    private void ParseInput(){
        ClearArrays();
        int inputLength = input.length();

        for (int i = 0; i < inputLength; i++)
        {
            char sign = input.charAt(i);

            if(Character.isDigit(sign) || Character.toString(sign).equals("."))
            {
                numberBuffer += Character.toString(sign);
                if(i<input.length()-1){
                    if (!Character.isDigit(input.charAt(i + 1)) && !Character.toString(input.charAt(i + 1)).equals(".")) {
                        numbers.add(Float.parseFloat(numberBuffer));
                        numberBuffer = "";
                    }
                }
                else{
                    numbers.add(Float.parseFloat(numberBuffer));
                    numberBuffer = "";
                }
            }
            else
            {
                if(IsOperator(sign)){
                    if(i == 0 && Character.toString(input.charAt(0)).equals("-")){
                        numberBuffer += Character.toString(sign);
                    }else{
                        operations.add(Character.toString(sign));
                    }
                }
            }
        }
    }

    private void FirstIteration(){
       for (int i = 0; i<operations.size(); i++){
            switch (operations.get(i)){
                case "+":
                case "-":
                    if(i==0) {
                        tempNumbers.add(numbers.get(i));
                        tempOperations.add(operations.get(i));
                        break;
                    }
                    if(!PrevOpImportant(i)){
                        tempNumbers.add(numbers.get(i));
                    }
                    tempOperations.add(operations.get(i));
                    if(i == operations.size()-1)
                    {
                        tempNumbers.add(numbers.get(i+1));
                    }
                    break;
                case "*":
                case "÷":
                    int k = i;

                    if (operations.get(k).equals("*")){
                        tempNumbers.add(numbers.get(i) * numbers.get(i+1));
                    }
                    if(operations.get(k).equals("÷")){
                        tempNumbers.add(numbers.get(i) / numbers.get(i+1));
                    }

                    while(k < operations.size()-1 && NextOpImportant(k)){
                        int lastIndex = tempNumbers.size()-1;
                        if (operations.get(k+1).equals("*")){
                            tempNumbers.set(lastIndex, tempNumbers.get(lastIndex) * numbers.get(k+1));
                        }
                        if(operations.get(k+1).equals("÷")){
                            tempNumbers.set(lastIndex, tempNumbers.get(lastIndex) / numbers.get(k+1));
                        }
                        k+=1;
                    }
                    i=k;
                    break;
            }
        }
        numbers = new ArrayList<>(tempNumbers);
        operations = new ArrayList<>(tempOperations);
        ClearBuffers();
    }

    private void SecondIteration(){
        result = numbers.get(0);
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
        WriteResult();
    }

    private void WriteResult(){
        if(result%1 == 0 ){
            input += "=" + Math.round(result);
        }else{
            input += ("=" + result);
        }
        UpdateViewText(input);
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


    View.OnClickListener listener = new View.OnClickListener(){
        public void onClick(View view) {
            Button b = (Button)view;
            WriteText(b.getText().toString());
        }
    };

    private void SubscribeButtons(){
//        Button mClickButton2 = (Button)findViewById(R.id.button2);
//        mClickButton2.setOnClickListener(listener);
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
//        Button mClickButton16 = (Button)findViewById(R.id.button16);
//        mClickButton16.setOnClickListener(listener);
        Button mClickButton17 = (Button)findViewById(R.id.button17);
        mClickButton17.setOnClickListener(listener);
        Button mClickButton18 = (Button)findViewById(R.id.button18);
        mClickButton18.setOnClickListener(listener);
    }

    private void WriteText(String textToWrite){
        if(input.contains("=")){
            String resultBuffer = input.split("=")[1];
            ClearAll();
            input = resultBuffer;
        }

        if(Character.isDigit(textToWrite.charAt(0))){
            input+= textToWrite;
        }

        if(textToWrite.equals(".")){
            int lastOpIndex = 0;
            for (int i = input.length()-1; i>-1; i--){
                if(IsOperator(input.charAt(i))){
                    lastOpIndex = i;
                    break;
                }
            }

            for (int i = lastOpIndex; i < input.length()-1; i++){
                if(Character.toString(input.charAt(i)).equals(".")){
                    return;
                }
            }
            if(input.length() >0) {
                if(Character.toString(input.charAt(input.length()-1)).equals(".")){
                    input = input.substring(0, input.length()-1) + textToWrite;
                }else{
                    input+=textToWrite;
                }
            }else{
                input+=textToWrite;
            }
        }

        if(IsOperator(textToWrite.charAt(0))){
            if(input.length() > 0){
                if(IsOperator(input.charAt(input.length()-1))){
                    input = input.substring(0, input.length()-1) + textToWrite;
                }else{
                    input+=textToWrite;
                }
            } else if (textToWrite.equals("-")) {
                input+=textToWrite;
            }
        }

        UpdateViewText(input);
    }

    private void UpdateViewText(String newText){
        textToUpdate.setText(newText);
    }

    private void ClearArrays(){
        operations.clear();
        numbers.clear();
    }

    private void ClearBuffers(){
        tempNumbers.clear();
        tempOperations.clear();
    }

    public void ClearAll(){
        input = "";
        UpdateViewText("");
        numbers.clear();
        operations.clear();
    }

    public void ClearAll(View view) {
        input = "";
        textToUpdate.setText("");
        numbers.clear();
        operations.clear();
    }

    public void Delete(View view) {
        if(input.length()>0){
            String newText = input.subSequence(0, input.length()-1).toString();
            UpdateViewText(newText);
            input = newText;
        }
    }

    public void Calculate(View view){
        String currentViewText = input;
        if(input == ""
                || currentViewText.contains("=")
                || IsOperator(currentViewText.charAt(currentViewText.length()-1))) return;
        ParseInput();
        if(input.contains("*") || input.contains("÷")){
            FirstIteration();
        }
        SecondIteration();
    }
}