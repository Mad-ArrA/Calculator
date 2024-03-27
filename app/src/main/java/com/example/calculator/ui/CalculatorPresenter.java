package com.example.calculator.ui;



import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.calculator.model.Calculator;
import com.example.calculator.model.Operator;


import java.text.DecimalFormat;

public class CalculatorPresenter implements Parcelable{

    private StringBuilder currentNumber = new StringBuilder(); // Хранит все введенные цифры
    private final DecimalFormat formatterWithDecimals = new DecimalFormat("#.#####");
    private final DecimalFormat formatterWithoutDecimals = new DecimalFormat("#");
    private CalculatorView view;
    private Calculator calculator;
    private double argOne;
    private double argTwo;


    private double argSolve;
    private boolean showDecimals = true;
    private Operator selectedOperator;


    public CalculatorPresenter(CalculatorView view, Calculator calculator ) {
        this.view = view;
        this.calculator = calculator;
    }
    public void setView(CalculatorView view) {
        this.view = view;
    }
    protected CalculatorPresenter(Parcel in) {
        argOne = in.readDouble();
        argTwo = in.readDouble();
        showDecimals = in.readByte() != 0;
    }

    public static final Creator<CalculatorPresenter> CREATOR = new Creator<CalculatorPresenter>() {
        @Override
        public CalculatorPresenter createFromParcel(Parcel in) {
            return new CalculatorPresenter(in);
        }

        @Override
        public CalculatorPresenter[] newArray(int size) {
            return new CalculatorPresenter[size];
        }
    };
    public double getArgOne() {
        return argOne;
    }

    public double getArgTwo() {
        return argTwo;
    }

    public boolean isShowDecimals() {
        return showDecimals;
    }


    public void onDigitPressed(int digit) {
        currentNumber.append(digit);
        if (selectedOperator == null) {
            argOne = Double.parseDouble(currentNumber.toString());
        } else {
            argTwo = Double.parseDouble(currentNumber.toString());
        }
        view.showCurrentNumber(currentNumber.toString()); // Обновление отображения с учетом всех цифр
    }

    public void onOperatorPressed(Operator operator) {
        argTwo = 0.0;
        selectedOperator = operator;
        currentNumber.setLength(0); // Очистка текущего числа для следующего операнда
    }

    public void onDotPressed() {
        if (!currentNumber.toString().contains(".")) {
            currentNumber.append(".");
        }
    }

    public void onSolve() {
        if (selectedOperator != null) {
            argSolve = calculator.performe(argOne, argTwo, selectedOperator);
            String formattedResult;
            if (showDecimals) {
                formattedResult = formatterWithDecimals.format(argSolve);
            } else {
                formattedResult = formatterWithoutDecimals.format(argSolve);
            }
            view.showResult(formattedResult);
            argOne = argSolve;// Сохранение результата для использования в дальнейшем
        }
    }

    public void onDoubleZero() {
        if (selectedOperator == null && currentNumber.length() > 0) {
            currentNumber.append("00");
            argOne = Double.parseDouble(currentNumber.toString());
            view.showCurrentNumber(currentNumber.toString());
        }
        else {
            currentNumber.append("00");
            argTwo = Double.parseDouble(currentNumber.toString());
            view.showCurrentNumber(currentNumber.toString());
        }

    }

    public void onReset() {
        selectedOperator = null;
        currentNumber.setLength(0); // Очистка StringBuilder
        argOne = 0.0;
        argTwo = 0.0;
        view.showCurrentNumber("0"); // Отображение пустой строки
        view.showResult("0"); // Очистка результата
    }

    public void onDeleteLastNumb() {
        if (currentNumber.length() > 1) {
            currentNumber.setLength(currentNumber.length() - 1); // Удаление последнего символа из StringBuilder
            // Обновление argOne или argTwo в зависимости от выбранного оператора
            if (selectedOperator == null) {
                argOne = Double.parseDouble(currentNumber.toString());
            } else {
                argTwo = Double.parseDouble(currentNumber.toString());
            }
            // Обновление отображения текущего числа
            view.showCurrentNumber(currentNumber.toString());
        }
    }
    public void restoreState(double argOne, double argTwo) {
        this.argOne = argOne;
        this.argTwo = argTwo;
        view.showCurrentNumber(currentNumber.toString());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(argOne);
        dest.writeDouble(argTwo);
        dest.writeByte((byte) (showDecimals ? 1 : 0));
    }
}
