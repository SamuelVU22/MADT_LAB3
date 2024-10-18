package com.example.madt_lab3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView workingsTV;
    TextView resultsTV;

    String workings = "";
    String formula = "";
    String tempFormula = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initTextViews();
    }

    private void initTextViews() {
        workingsTV = (TextView) findViewById(R.id.workingsTextView);
        resultsTV = (TextView) findViewById(R.id.resultTextView);
    }

    private void setWorkings(String givenValue) {
        workings = workings + givenValue;
        workingsTV.setText(workings);
    }


    public void equalsOnClick(View view) {
        Double result = null;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        checkForSquareRoot();

        try {
            result = (double) engine.eval(formula);
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }

        if (result != null) resultsTV.setText(String.valueOf(result.doubleValue()));

    }


    private void checkForSquareRoot() {
        ArrayList<Integer> indexRoots = new ArrayList<>();
        for (int i = 0; i < workings.length(); i++) {
            if (workings.charAt(i) == '√') indexRoots.add(i);
        }

        formula = workings;
        tempFormula = workings;
        for (Integer index : indexRoots) {
            changeFormulaRoot(index);
        }
        formula = tempFormula;
    }

    public void backspaceOnClick(View view) {
        if (!workings.isEmpty()) {
            workings = workings.substring(0, workings.length() - 1); // Remove last character
            workingsTV.setText(workings); // Update TextView
        }
    }

    private void changeFormulaRoot(Integer index) {
        String numberRight = "";

        for (int i = index + 1; i < workings.length(); i++) {
            if (isNumeric(workings.charAt(i))) numberRight = numberRight + workings.charAt(i);
            else break;
        }

        if (numberRight.isEmpty()) {
            // Handle error: "√" not followed by a number
            return;
        }

        String original = "√" + numberRight;
        String changed = "Math.sqrt(" + numberRight + ")";
        tempFormula = tempFormula.replace(original, changed);
    }


    private boolean isNumeric(char c) {
        if ((c <= '9' && c >= '0') || c == '.') return true;

        return false;
    }

    private boolean isNumericString(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void clearOnClick(View view) {
        workingsTV.setText("");
        workings = "";
        resultsTV.setText("");
    }

    //boolean leftBracket = true;

    public void rootOnClick(View view) {
        setWorkings("√");
    }

    public void signalChngOnClick(View view) {
        // Check if there is something in workings
        if (!workings.isEmpty()) {
            // Split the workings into parts based on operators
            String[] parts = workings.split("(?<=[-+*/])|(?=[-+*/])");
            String lastPart = parts[parts.length - 1]; // Get the last part
            String signal = parts[parts.length - 2];

            // Check if the last part is a number
            if (isNumericString(lastPart)) {
                // If it's numeric, change its sign
                lastPart = signal + lastPart;
                if (lastPart.startsWith("-")) {
                    lastPart = lastPart.substring(1); // Remove the negative sign
                    lastPart = "+" + lastPart;
                } else {
                    lastPart = lastPart.substring(1);
                    lastPart = "-" + lastPart; // Add a negative sign
                }

                // Rebuild the workings string
                StringBuilder newWorkings = new StringBuilder();
                for (int i = 0; i < parts.length - 2; i++) {
                    newWorkings.append(parts[i]);
                }
                newWorkings.append(lastPart); // Append the modified last part

                workings = newWorkings.toString();
                workingsTV.setText(workings);

                // Update TextView
            }
        }
    }

//    public void signalChngOnClick(View view) {
//        if (!workings.isEmpty()) {
//            String[] parts = workings.split("(?<=[-+*/])|(?=[-+*/])");
//            String lastPart = parts[parts.length - 1];
//
//            if (Character.isDigit(lastPart.charAt(0))) {
//                try {
//                    lastPart = lastPart.startsWith("-") ? lastPart.substring(1) : "-" + lastPart;
//                    String newWorkings = String.join("", Arrays.copyOfRange(parts, 0, parts.length - 1)) + lastPart;
//                    workings = newWorkings;
//                    workingsTV.setText(workings);
//                } catch (NumberFormatException e) {
//                    // Handle the exception
//                }
//            }
//        }
//    }


    public void divisionOnClick(View view) {
        setWorkings("/");
    }

    public void sevenOnClick(View view) {
        setWorkings("7");
    }

    public void eightOnClick(View view) {
        setWorkings("8");
    }

    public void nineOnClick(View view) {
        setWorkings("9");
    }

    public void timesOnClick(View view) {
        setWorkings("*");
    }

    public void fourOnClick(View view) {
        setWorkings("4");
    }

    public void fiveOnClick(View view) {
        setWorkings("5");
    }

    public void sixOnClick(View view) {
        setWorkings("6");
    }

    public void minusOnClick(View view) {
        setWorkings("-");
    }

    public void oneOnClick(View view) {
        setWorkings("1");
    }

    public void twoOnClick(View view) {
        setWorkings("2");
    }

    public void threeOnClick(View view) {
        setWorkings("3");
    }

    public void plusOnClick(View view) {
        setWorkings("+");
    }

    public void decimalOnClick(View view) {
        setWorkings(".");
    }

    public void zeroOnClick(View view) {
        setWorkings("0");
    }

}