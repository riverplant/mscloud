package com.riverplant.cucumber.demos;

public class Calculator
{
    private final int x;
    private final int y;


    public Calculator(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int add() {
        return x + y;
    }
}
