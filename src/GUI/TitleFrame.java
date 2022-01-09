package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowListener;


import static GUI.GUI.addHTML;

public class TitleFrame {


    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_WIDTH = 700;

    public TitleFrame(String title){
        titleFrame = QuestionFrame.createMainFrame(title);
        //holds top and lower panel
        JPanel centerPanel = QuestionFrame.createCenterPanel(titleFrame);
        // holds top elements
        JPanel topPanel = QuestionFrame.createTopPanel(centerPanel);
        //holds lower elements
        JPanel lowerPanel = QuestionFrame.createLowerPanel(centerPanel);
        optionsPanel = createOptionsPanel(lowerPanel);
        createTitle(title, topPanel);
    }
    public void setVisible (boolean visible){
        this.titleFrame.setVisible(visible);
    }

    public static JButton createCenterGenericButton(String text, JPanel destination){
       JButton toCreate = createGenericButton(text);
        destination.add(Box.createRigidArea(new Dimension(0,20)));
        destination.add(toCreate);
        destination.add(Box.createRigidArea(new Dimension(0,20)));
        return toCreate;
    }

    public static JButton createGenericButton (String text){
        JButton toCreate = new JButton();
        toCreate.setText(addHTML(text));
        toCreate.setFont(new Font("Dialog",Font.BOLD,30));
        toCreate.setFocusable(false);
        toCreate.setAlignmentX(Component.CENTER_ALIGNMENT);
        toCreate.setMaximumSize(new Dimension(250,200));
        return toCreate;
    }

    private JPanel createOptionsPanel (JPanel destination){
        JPanel toReturn = new JPanel();
        toReturn.setBackground(Color.BLUE);
        destination.add(toReturn,BorderLayout.CENTER);
        toReturn.setLayout(new BoxLayout(toReturn,BoxLayout.Y_AXIS));
        return toReturn;
    }
    public static void createTitle (String questionText, JPanel destination){
        JLabel toReturn = new JLabel(addHTML(questionText));
        toReturn.setHorizontalAlignment(JLabel.CENTER);
        toReturn.setVerticalAlignment(JLabel.TOP);
        toReturn.setFont(new Font("Dialog",Font.BOLD,70));
        Border emptyBorder = new EmptyBorder(60,20,20,20);
        toReturn.setBorder(emptyBorder);
        destination.add(toReturn,BorderLayout.NORTH);
    }

    public void dispose (){
        titleFrame.dispose();
    }

    public JFrame getTitleFrame() {
        return titleFrame;
    }

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    private JLabel title;

    private final JPanel optionsPanel;

    private final JFrame titleFrame;

}
