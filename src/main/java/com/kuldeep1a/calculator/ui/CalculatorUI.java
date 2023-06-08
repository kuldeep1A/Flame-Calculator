package com.kuldeep1a.calculator.ui;

import com.kuldeep1a.calculator.theme.ThemeLoader;
import com.kuldeep1a.calculator.theme.properties.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Map;

public class CalculatorUI {
    private static final String DOUBLE_OR_NUMBER_REGEX = "([-]?\\d+[.]\\d*)|(\\d+)|(-\\d+)";
    private static final String APPLICATION_TITLE = "Calculator";
    private static final int WINDOW_WIDTH = 410;
    private static final int WINDOW_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 70;
    private static final int MARGIN_X = 20;
    private static final int MARGIN_Y = 60;
    private final JFrame window;
    private static final String FONT_NAME = "Comic Sans MS";
    private JTextField inputScreen;
    private JComboBox<String> comboCalculatorType;
    private JComboBox<String> comboTheme;
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
    private char selectedOperator = ' ';
    private final Map<String, Theme> themeMap;

    public CalculatorUI() {
        themeMap = ThemeLoader.loadThemes();
        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);

        int[] columns = {MARGIN_X, MARGIN_X + 90, MARGIN_X + 90 * 2, MARGIN_X + 90 * 3, MARGIN_X + 90 * 4};
        int[] rows = {MARGIN_Y, MARGIN_Y + 100, MARGIN_Y + 100 + 80, MARGIN_Y + 100 + 80 * 2, MARGIN_Y + 100 + 80 * 3, MARGIN_Y + 100 + 80 * 4};

        initInputScreen(columns, rows);
        initButtons(columns, rows);
        initCalculatorTypeSelector();

        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }


    public void initInputScreen(int[] columns, int[] rows){
        inputScreen = new JTextField("143");
        inputScreen.setBounds(columns[0], rows[0], 350, 70);
        inputScreen.setEditable(false);
        inputScreen.setBackground(Color.WHITE);
        inputScreen.setFont(new Font(FONT_NAME, Font.PLAIN, 33));
        window.add(inputScreen);
    }

    public void initCalculatorTypeSelector(){
        comboCalculatorType = createComboBox(new String[]{"Standard", "Scientific"}, 20, 30, "Calculator type");
        comboCalculatorType.addItemListener(event ->{
            if (event.getStateChange() != ItemEvent.SELECTED){
                return;
            }
            String selectedItem = (String) event.getItem();
            switch (selectedItem){
                case "Standard":
                    window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                    break;
                case "Scientific":
                    window.setSize(WINDOW_WIDTH + 80, WINDOW_HEIGHT);

                    break;
            }
        });
    }

    public void initButtons(int[] columns, int[] rows){
        btnC = createButton("C", columns[0], rows[1]);
        btnC.addItemListener(event -> {
            inputScreen.setText("0");
        });

        btnBack = createButton("<-", columns[1], rows[1]);
        btnBack.addActionListener(event ->{
            String str = inputScreen.getText();
            StringBuilder str2 = new StringBuilder();
            for(int i = 0; i < (str.length() - 1); i++){
                str2.append(str.charAt(i));
            }
            if (str2.toString().equals("")){
                inputScreen.setText("0");
            } else{
                inputScreen.setText(str2.toString());
            }
        });
    }

    private JComboBox<String> createComboBox(String[] items, int x, int y, String toolTip){
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBounds(x, y, 140, 25);
        combo.setToolTipText(toolTip);
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        window.add(combo);

        return combo;
    }

    private JButton createButton(String label, int x, int y){
        JButton btn = new JButton(label);
        btn.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setFont(new Font(FONT_NAME, Font.PLAIN, 28));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusable(false);
        window.add(btn);

        return btn;
    }
}
