package com.example.calculator.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.calculator.R;
import com.example.calculator.model.Calculator;
import com.example.calculator.model.CalculatorImpl;
import com.example.calculator.model.Operator;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CalculatorView{

    private TextView resultText;
    private CalculatorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyThemeUtils.setTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculate_activity);
        if (savedInstanceState != null) {
            presenter = savedInstanceState.getParcelable("parcelable");
            if (presenter != null) {
                presenter.setView(this);
            }
        }
        else {
            presenter = new CalculatorPresenter(this, new CalculatorImpl());
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultText = findViewById(R.id.result);

        Map<Integer, Integer> digits = new HashMap<>();
        digits.put(R.id.key_0, 0);
        digits.put(R.id.key_1, 1);
        digits.put(R.id.key_2, 2);
        digits.put(R.id.key_3, 3);
        digits.put(R.id.key_4, 4);
        digits.put(R.id.key_5, 5);
        digits.put(R.id.key_6, 6);
        digits.put(R.id.key_7, 7);
        digits.put(R.id.key_8, 8);
        digits.put(R.id.key_9, 9);


        View.OnClickListener digitalClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDigitPressed(digits.get(v.getId()));
            }
        };

        findViewById(R.id.key_0).setOnClickListener(digitalClickListener);
        findViewById(R.id.key_1).setOnClickListener(digitalClickListener);
        findViewById(R.id.key_2).setOnClickListener(digitalClickListener);
        findViewById(R.id.key_3).setOnClickListener(digitalClickListener);
        findViewById(R.id.key_4).setOnClickListener(digitalClickListener);
        findViewById(R.id.key_5).setOnClickListener(digitalClickListener);
        findViewById(R.id.key_6).setOnClickListener(digitalClickListener);
        findViewById(R.id.key_7).setOnClickListener(digitalClickListener);
        findViewById(R.id.key_8).setOnClickListener(digitalClickListener);
        findViewById(R.id.key_9).setOnClickListener(digitalClickListener);

        findViewById(R.id.key_00).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDoubleZero();
            }
        });


        Map<Integer, Operator> operators = new HashMap<>();
        operators.put(R.id.key_plus, Operator.ADD);
        operators.put(R.id.key_minus, Operator.SUB);
        operators.put(R.id.key_divide, Operator.DIV);
        operators.put(R.id.key_multi, Operator.MULT);
        operators.put(R.id.key_percent, Operator.PERCENT);

        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOperatorPressed(operators.get(v.getId()));
            }
        };

        findViewById(R.id.key_plus).setOnClickListener(operatorClickListener);
        findViewById(R.id.key_minus).setOnClickListener(operatorClickListener);
        findViewById(R.id.key_divide).setOnClickListener(operatorClickListener);
        findViewById(R.id.key_multi).setOnClickListener(operatorClickListener);
        findViewById(R.id.key_percent).setOnClickListener(operatorClickListener);

        findViewById(R.id.key_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDotPressed();
            }
        });
        findViewById(R.id.key_solve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSolve();
            }
        });

        findViewById(R.id.key_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onReset();
            }
        });

        findViewById(R.id.key_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDeleteLastNumb();
            }
        });

    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("parcelable", presenter);
        outState.putDouble("argOne", presenter.getArgOne());
        outState.putDouble("argTwo", presenter.getArgTwo());
        outState.putDouble("argSolve", presenter.getArgSolve());
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        double argOne = savedInstanceState.getDouble("argOne");
        double argTwo = savedInstanceState.getDouble("argTwo");
        presenter.restoreState(argOne, argTwo);
    }
    public void toggleTheme(View view) {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        boolean isDarkTheme = preferences.getBoolean("isDarkTheme", false);
        preferences.edit().putBoolean("isDarkTheme", !isDarkTheme).apply();
        recreate();
    }

    @Override
    public void showResult(String result) {
        resultText.setText(result);
    }

    @Override
    public void showCurrentNumber(String resultNumb) {
        resultText.setText(resultNumb);
    }

}