package com.kuldeep1a.calculator.ui;

import com.kuldeep1a.calculator.theme.ThemeLoader;
import com.kuldeep1a.calculator.theme.properties.Theme;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.Map;
import java.util.regex.Pattern;
import java.awt.Color;
import javax.swing.*;

import static com.kuldeep1a.calculator.util.ColorUtil.hexTwoColor;
public class CalculatorUI {
    private static final String DOUBLE_OR_NUMBER_REGEX = "([-]?\\d+[.]\\d*)|(\\d+)|(-\\d+)";
    private static final String APPLICATION_TITLE = "Flame-Calculator";
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
    private boolean go = true; // For calculate with Opt != (=)
    private boolean addToDisplay = true; // Connect numbers in display
    private double typedValue = 0;
    private final Map<String, Theme> themeMap;

    public CalculatorUI() {
        themeMap = ThemeLoader.loadThemes();
        window = new JFrame(APPLICATION_TITLE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);

        // more helping
        int[] columns = {MARGIN_X, MARGIN_X + 90, MARGIN_X + 90 * 2, MARGIN_X + 90 * 3, MARGIN_X + 90 * 4};
        int[] rows = {MARGIN_Y, MARGIN_Y + 100, MARGIN_Y + 100 + 80, MARGIN_Y + 100 + 80 * 2, MARGIN_Y + 100 + 80 * 3, MARGIN_Y + 100 + 80 * 4};

        initInputScreen(columns, rows);
        initButtons(columns, rows);
        initCalculatorTypeSelector();

        initThemeSelector();

        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public double calculate(double firstNumber, double secondNumber, char operator){
        switch(operator){
            case '+':
                return firstNumber + secondNumber;
            case '-':
                return firstNumber - secondNumber;
            case '/':
                return firstNumber / secondNumber;
            case '%':
                return firstNumber % secondNumber;
            case '*':
                return firstNumber * secondNumber;
            case '^':
                return Math.pow(firstNumber, secondNumber);
            default:
                return secondNumber;
        }
    }

    private void initThemeSelector(){
        comboTheme = createComboBox(themeMap.keySet().toArray(new String[0]), 230, 30, "Theme");
        comboTheme.addItemListener(event -> {
            if (event.getStateChange() != ItemEvent.SELECTED)
                return;
            String selectedItem = (String) event.getItem();
            applyTheme(themeMap.get(selectedItem));
        });

        if (themeMap.entrySet().iterator().hasNext()){
            applyTheme(themeMap.entrySet().iterator().next().getValue());
        }

    }
    private void initInputScreen(int[] columns, int[] rows){
        inputScreen = new JTextField("0");
        inputScreen.setBounds(columns[0], rows[0], 350, 70);
        inputScreen.setEditable(false);
        inputScreen.setBackground(Color.WHITE);
        inputScreen.setFont(new Font(FONT_NAME, Font.PLAIN, 33));
        window.add(inputScreen);
    }

    private void initCalculatorTypeSelector(){
        comboCalculatorType = createComboBox(new String[]{"Standard", "Scientific"}, 20, 30, "Calculator type");
        comboCalculatorType.addItemListener(event ->{
            if (event.getStateChange() != ItemEvent.SELECTED){
                return;
            }
            String selectedItem = (String) event.getItem();
            switch (selectedItem){
                case "Standard":
                    window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                    btnLog.setVisible(false);
                    btnRoot.setVisible(false);
                    btnPower.setVisible(false);
                    break;
                case "Scientific":
                    window.setSize(WINDOW_WIDTH + 80, WINDOW_HEIGHT);
                    btnLog.setVisible(true);
                    btnRoot.setVisible(true);
                    btnPower.setVisible(true);
                    break;
            }
        });
    }

    // Double.parseDouble() It returns e double value represented by the string argument.
    private void initButtons(int[] columns, int[] rows) {
        btnC = createButton("C", columns[0], rows[1]);
        btnC.addActionListener(event -> {
            inputScreen.setText("0");
            selectedOperator = ' ';
            typedValue = 0;
        });

        btnBack = createButton("<-", columns[1], rows[1]);
        btnBack.addActionListener(event -> {
            String str = inputScreen.getText();
            StringBuilder str2 = new StringBuilder();
            for (int i = 0; i < (str.length() - 1); i++) {
                str2.append(str.charAt(i));
            }
            if (str2.toString().equals("")) {
                inputScreen.setText("0");
            } else {
                inputScreen.setText(str2.toString());
            }
        });

        btnMod = createButton("%", columns[2], rows[1]);
        btnMod.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()) || !go)
                return;

            typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
            if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(typedValue))) {
                inputScreen.setText(String.valueOf((int) typedValue));
            } else {
                inputScreen.setText(String.valueOf(typedValue));
            }
            selectedOperator = '%';
            go = false;
            addToDisplay = false;
        });

        btnDiv = createButton("/", columns[3], rows[1]);
        btnDiv.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (go) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = '/';
                go = false;
                addToDisplay = false;
            } else {
                selectedOperator = '/';
            }
        });

        btn7 = createButton("7", columns[0], rows[2]);
        btn7.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("7");
                } else {
                    inputScreen.setText(inputScreen.getText() + "7");
                }
            } else {
                inputScreen.setText("7");
                addToDisplay = true;
            }
            go = true;
        });

        btn8 = createButton("8", columns[1], rows[2]);
        btn8.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("8");
                } else {
                    inputScreen.setText(inputScreen.getText() + "8");
                }
            } else {
                inputScreen.setText("8");
                addToDisplay = true;
            }
            go = true;
        });

        btn9 = createButton("9", columns[2], rows[2]);
        btn9.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("9");
                } else {
                    inputScreen.setText(inputScreen.getText() + "9");
                }
            } else {
                inputScreen.setText("9");
                addToDisplay = true;
            }
            go = true;
        });

        btnMul = createButton("*", columns[3], rows[2]);
        btnMul.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (go) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = '*';
                go = false;
                addToDisplay = false;
            } else {
                selectedOperator = '*';
            }
        });

        btn4 = createButton("4", columns[0], rows[3]);
        btn4.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("4");
                } else {
                    inputScreen.setText(inputScreen.getText() + "4");
                }
            } else {
                inputScreen.setText("4");
                addToDisplay = true;
            }
            go = true;
        });

        btn5 = createButton("5", columns[1], rows[3]);
        btn5.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("5");
                } else {
                    inputScreen.setText(inputScreen.getText() + "5");
                }
            } else {
                inputScreen.setText("5");
                addToDisplay = true;
            }
            go = true;
        });

        btn6 = createButton("6", columns[2], rows[3]);
        btn6.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("6");
                } else {
                    inputScreen.setText(inputScreen.getText() + "6");
                }
            } else {
                inputScreen.setText("6");
                addToDisplay = true;
            }
            go = true;
        });

        btnSub = createButton("-", columns[3], rows[3]);
        btnSub.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (go) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }

                selectedOperator = '-';
                go = false;
                addToDisplay = false;
            } else {
                selectedOperator = '-';
            }
        });

        btn1 = createButton("1", columns[0], rows[4]);
        btn1.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("1");
                } else {
                    inputScreen.setText(inputScreen.getText() + "1");
                }
            } else {
                inputScreen.setText("1");
                addToDisplay = true;
            }
            go = true;
        });

        btn2 = createButton("2", columns[1], rows[4]);
        btn2.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("2");
                } else {
                    inputScreen.setText(inputScreen.getText() + "2");
                }
            } else {
                inputScreen.setText("2");
                addToDisplay = true;
            }
            go = true;
        });

        btn3 = createButton("3", columns[2], rows[4]);
        btn3.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("3");
                } else {
                    inputScreen.setText(inputScreen.getText() + "3");
                }
            } else {
                inputScreen.setText("3");
                addToDisplay = true;
            }
            go = true;
        });

        btnAdd = createButton("+", columns[3], rows[4]);
        btnAdd.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (go) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = '+';
                go = false;
                addToDisplay = false;
            } else {
                selectedOperator = '+';
            }
        });

        btnPoint = createButton(".", columns[0], rows[5]);
        btnPoint.addActionListener(event -> {
            if (addToDisplay) {
                if (!inputScreen.getText().contains(".")) {
                    inputScreen.setText(inputScreen.getText() + ".");
                }
            } else {
                inputScreen.setText("0.");
                addToDisplay = true;
            }
            go = true;
        });

        btn0 = createButton("0", columns[1], rows[5]);
        btn0.addActionListener(event -> {
            if (addToDisplay) {
                if (Pattern.matches("[0]*", inputScreen.getText())) {
                    inputScreen.setText("0");
                } else {
                    inputScreen.setText(inputScreen.getText() + "0");
                }
            } else {
                inputScreen.setText("0");
                addToDisplay = true;
            }
            go = true;
        });

        btnEqual = createButton("=", columns[2], rows[5]);
        btnEqual.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (go) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = '=';
                addToDisplay = false;
            }
        });
        btnEqual.setSize(2 * BUTTON_WIDTH + 10, BUTTON_HEIGHT);

        btnRoot = createButton("√", columns[4], rows[1]);
        btnRoot.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (go) {
                typedValue = Math.sqrt(Double.parseDouble(inputScreen.getText()));
                if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = '√';
                addToDisplay = false;
            }
        });
        btnRoot.setVisible(false);

        btnPower = createButton("pow", columns[4], rows[2]);
        btnPower.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (go) {
                typedValue = calculate(typedValue, Double.parseDouble(inputScreen.getText()), selectedOperator);
                if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = '^';
                go = false;
                addToDisplay = false;
            } else {
                selectedOperator = '^';
            }
        });
        btnPower.setFont(new Font(FONT_NAME, Font.PLAIN, 24));
        btnPower.setVisible(false);

        btnLog = createButton("ln", columns[4], rows[3]);
        btnLog.addActionListener(event -> {
            if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, inputScreen.getText()))
                return;

            if (go) {
                typedValue = Math.log(Double.parseDouble(inputScreen.getText()));
                if (Pattern.matches("[-]?[\\d]+[.][0]*", String.valueOf(typedValue))) {
                    inputScreen.setText(String.valueOf((int) typedValue));
                } else {
                    inputScreen.setText(String.valueOf(typedValue));
                }
                selectedOperator = 'l';
                addToDisplay = false;
            }
        });
        btnLog.setVisible(false);

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

    private void applyTheme(Theme theme){
        window.getContentPane().setBackground(hexTwoColor(theme.getApplicationBackground()));

        comboCalculatorType.setForeground(hexTwoColor(theme.getTextColor()));
        comboTheme.setForeground(hexTwoColor(theme.getTextColor()));
        inputScreen.setForeground(hexTwoColor(theme.getTextColor()));
        btn0.setForeground(hexTwoColor(theme.getTextColor()));
        btn1.setForeground(hexTwoColor(theme.getTextColor()));
        btn2.setForeground(hexTwoColor(theme.getTextColor()));
        btn3.setForeground(hexTwoColor(theme.getTextColor()));
        btn4.setForeground(hexTwoColor(theme.getTextColor()));
        btn5.setForeground(hexTwoColor(theme.getTextColor()));
        btn6.setForeground(hexTwoColor(theme.getTextColor()));
        btn7.setForeground(hexTwoColor(theme.getTextColor()));
        btn8.setForeground(hexTwoColor(theme.getTextColor()));
        btn9.setForeground(hexTwoColor(theme.getTextColor()));
        btnPoint.setForeground(hexTwoColor(theme.getTextColor()));
        btnC.setForeground(hexTwoColor(theme.getTextColor()));
        btnBack.setForeground(hexTwoColor(theme.getTextColor()));
        btnMod.setForeground(hexTwoColor(theme.getTextColor()));
        btnDiv.setForeground(hexTwoColor(theme.getTextColor()));
        btnMul.setForeground(hexTwoColor(theme.getTextColor()));
        btnSub.setForeground(hexTwoColor(theme.getTextColor()));
        btnAdd.setForeground(hexTwoColor(theme.getTextColor()));
        btnRoot.setForeground(hexTwoColor(theme.getTextColor()));
        btnLog.setForeground(hexTwoColor(theme.getTextColor()));
        btnPower.setForeground(hexTwoColor(theme.getTextColor()));
        btnEqual.setForeground(hexTwoColor(theme.getBtnEqualTextColor()));

        comboCalculatorType.setBackground(hexTwoColor(theme.getApplicationBackground()));
        comboTheme.setBackground(hexTwoColor(theme.getApplicationBackground()));
        inputScreen.setBackground(hexTwoColor(theme.getApplicationBackground()));
        btn0.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btn1.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btn2.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btn3.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btn4.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btn5.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btn6.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btn7.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btn8.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btn9.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btnPoint.setBackground(hexTwoColor(theme.getNumbersBackground()));
        btnC.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnBack.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnMod.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnDiv.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnMul.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnSub.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnAdd.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnRoot.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnLog.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnPower.setBackground(hexTwoColor(theme.getOperatorBackground()));
        btnEqual.setBackground(hexTwoColor(theme.getBtnEqualBackground()));
    }
}
