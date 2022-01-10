package GUI;

import javax.swing.*;
import java.awt.*;

import static GUI.GUI.addHTML;



public class ConfirmFrame {

    public static final int WINDOW_HEIGHT = 200;
    public static final int WINDOW_WIDTH = 300;

    public ConfirmFrame(JButton yes, JButton no, String question){
        mainFrame  = QuestionFrame.createMainFrame("Confirm");
        createConfirmationFrame(mainFrame,yes,no, question);
    }

    public static void createConfirmationFrame (JFrame mainFrame, JButton yes, JButton no, String question){
        mainFrame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel centralPanel = QuestionFrame.createCenterPanel(mainFrame);
        JPanel topPanel = QuestionFrame.createTopPanel(centralPanel);
        JPanel lowerPanel = QuestionFrame.createLowerPanel(centralPanel);
        JPanel buttonHolder = new JPanel(new FlowLayout());
        buttonHolder.setBackground(new Color(255,255,255));

        lowerPanel.add(buttonHolder);
        buttonHolder.add(yes);
        buttonHolder.add(no);
        QuestionFrame.addQuestion(question, topPanel);
        mainFrame.setVisible(true);
    }
    public static JButton createButton(String text){
        JButton toCreate = new JButton();
        toCreate.setText(addHTML(text));
        toCreate.setFont(new Font("Dialog",Font.BOLD,30));
        toCreate.setFocusable(false);
        return toCreate;
    }
    public void dispose (){
        mainFrame.dispose();
    }
    private final JFrame mainFrame;

}
/*
    public void setTitleFrame (TitleFrame toDelete){
        this.toDelete = toDelete;
    }*/



/*    private TitleFrame toDelete;*/


/*    private final JButton yes;
    private final JButton no;*/

