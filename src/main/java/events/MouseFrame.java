package events;

import javax.swing.*;
import java.awt.*;

class MouseFrameTest{
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new MouseFrame();
            frame.setTitle("PlafFrameTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

public class MouseFrame extends JFrame {
    public MouseFrame(){
        add(new MouseComponent());
        pack();
    }
}
