package events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EventFrame extends JFrame {
    private JPanel buttonPanel;
    private static final int DEFAULT_WIDTH = 817;
    private static final int DEFAULT_HEIGHT = 600;

    public EventFrame(){
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        buttonPanel = new JPanel();


        Action yellowAction = new ColorAction("Yellow (Ctrl+Y)", Color.YELLOW);
        Action blueAction = new ColorAction("Blue (Ctrl+B)", Color.BLUE);
        Action redAction = new ColorAction("Red (Ctrl+R)", Color.RED);

        InputMap imap = buttonPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        imap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
        imap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.blue");
        imap.put(KeyStroke.getKeyStroke("ctrl R"), "panel.red");

        ActionMap amap = buttonPanel.getActionMap();
        amap.put("panel.yellow", yellowAction);
        amap.put("panel.blue", blueAction);
        amap.put("panel.red", redAction);

        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : infos)
            makeButton(info.getName(), info.getClassName());


        buttonPanel.add(new JButton(yellowAction));
        buttonPanel.add(new JButton(blueAction));
        buttonPanel.add(new JButton(redAction));

        add(buttonPanel);
        pack();
        add(new MouseComponent(), BorderLayout.SOUTH);
        pack();


    }

    public class ColorAction extends AbstractAction{
        public ColorAction(String name, Color c){
            putValue(Action.NAME, name);
            putValue(Action.SHORT_DESCRIPTION, "Set panel color to "+name.toLowerCase());
            putValue("color", c);
        }


        public void actionPerformed(ActionEvent e) {
            Color c = (Color) getValue("color");
            buttonPanel.setBackground(c);
        }
    }

    private void makeButton(String name, String className) {
        JButton button = new JButton(name);
        buttonPanel.add(button);

        button.addActionListener(event -> {
            try{
                UIManager.setLookAndFeel(className);
                SwingUtilities.updateComponentTreeUI(this);
                pack();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }


}
