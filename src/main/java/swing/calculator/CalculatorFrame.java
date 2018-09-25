package swing.calculator;

import javax.swing.*;
import java.awt.*;

class CalculatorTest{
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new CalculatorFrame();
            frame.setTitle("MyCalculator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

public class CalculatorFrame extends JFrame{
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public CalculatorFrame(){
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        add(new CalculatorPanel());
    }
}
