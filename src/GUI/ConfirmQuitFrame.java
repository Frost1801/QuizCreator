package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static GUI.GUI.addHTML;



public class ConfirmQuitFrame implements ActionListener {

    public static final int WINDOW_HEIGHT = 200;
    public static final int WINDOW_WIDTH = 300;

    public ConfirmQuitFrame(){
        mainFrame  = QuestionFrame.createMainFrame("Confirm Exit");
        yes = createButton("Yes");
        no = createButton("No");
        createConfirmationFrame(mainFrame,yes,no,this);
    }

    public static void createConfirmationFrame (JFrame mainFrame, JButton yes, JButton no, ActionListener ls){
        mainFrame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel centralPanel = QuestionFrame.createCenterPanel(mainFrame);
        JPanel topPanel = QuestionFrame.createTopPanel(centralPanel);
        JPanel lowerPanel = QuestionFrame.createLowerPanel(centralPanel);
        JPanel buttonHolder = new JPanel(new FlowLayout());

        lowerPanel.add(buttonHolder);
        buttonHolder.add(yes);
        buttonHolder.add(no);
        QuestionFrame.addQuestion("Are you sure?", topPanel);
        yes.addActionListener(ls);
        no.addActionListener(ls);
        mainFrame.setVisible(true);
    }
    private static JButton createButton(String text){
        JButton toCreate = new JButton();
        toCreate.setText(addHTML(text));
        toCreate.setFont(new Font("Dialog",Font.BOLD,30));
        toCreate.setFocusable(false);
        return toCreate;
    }

    public void setTitleFrame (TitleFrame toDelete){
        this.toDelete = toDelete;
    }

    public void dispose (){
        mainFrame.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yes){
            toDelete.dispose();
            this.dispose();
        }

        if (e.getSource() ==  no){
           this.dispose();
        }

    }

    private TitleFrame toDelete;

    private final JFrame mainFrame;
    private final JButton yes;
    private final JButton no;
}
