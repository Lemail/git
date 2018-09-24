package events;

import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new EventFrame();


            frame.setTitle("EventFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
