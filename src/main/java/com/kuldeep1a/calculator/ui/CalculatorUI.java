package com.kuldeep1a.calculator.ui;

import com.kuldeep1a.calculator.theme.ThemeLoader;

import javax.swing.*;

public class CalculatorUI {
    private final JFrame window;
    private static final String FONT_NAME = "Comic Sans MS";
    private JButton btnC;
    private JButton btnBack;
    private JButton btnMod;
    private JButton btnDiv;
    private JButton btnMul;
    private JButton btnSub;
    private JButton btnAdd;
    private JButton btn0;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;
    private JButton btnPoint;
    private JButton btnEqual;
    private JButton btnRoot;
    private JButton btnPower;
    private JButton btnLog;

    public CalculatorUI() {
        themeMap = ThemeLoader.loadThemes();

    }


}
