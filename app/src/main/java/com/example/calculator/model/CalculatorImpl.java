package com.example.calculator.model;

public class CalculatorImpl implements Calculator{
    @Override
    public double performe(double arg1, double arg2, Operator operator) {
        switch (operator){
            case ADD:
                return arg1 + arg2;
            case DIV:
                return arg1 / arg2;
            case SUB:
                return arg1 - arg2;
            case MULT:
                return arg1 * arg2;
            case PERCENT:
                return arg1 % arg2;
        }
        return 0.0;
    }
}
