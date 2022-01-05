package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;


import static GUI.GUI.addHTML;
import static GUI.GUI.fitPicToFrame;


public class MainFrame extends JFrame {

    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_WIDTH = 700;

    public MainFrame(String title, int maxValue, JButton goRight, JButton goLeft, Vector<JButton> options){
        createQuestionFrame(title);
        createCenterPanel();
        createTopPanel();
        createLowerPanel();
        createAnswersGrid();
        addMoveButtons(goRight, goLeft);
        addProgressBar(maxValue);
    }

    public void setVisible (boolean visible){
        this.questionFrame.setVisible(visible);
    }

    public void reset(){
        answersGrid.removeAll();
        topPanel.removeAll();
        validate();
        repaint();
    }

    private void createQuestionFrame(String title){
        questionFrame = new JFrame();
        questionFrame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        questionFrame.setResizable(false);
        questionFrame.setLocationRelativeTo(null);
        questionFrame.setTitle(title);
        questionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createCenterPanel (){
        centerPanel=  new JPanel();
        centerPanel.setBackground(Color.BLUE);
        questionFrame.add(centerPanel,BorderLayout.CENTER);
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
    }

    private void createTopPanel (){
        topPanel=  new JPanel();
        topPanel.setBackground(Color.GREEN);
        centerPanel.add(topPanel);
        topPanel.setLayout(new BorderLayout());
    }

    private void createLowerPanel (){
        lowerPanel=  new JPanel();
        lowerPanel.setBackground(Color.RED);
        centerPanel.add(lowerPanel, BoxLayout.Y_AXIS);
        lowerPanel.setLayout(new BorderLayout(20,20));
    }

    private void createAnswersGrid(){
        answersGrid = new JPanel();
        answersGrid.setBackground(Color.MAGENTA);

        lowerPanel.add(answersGrid, BorderLayout.CENTER);
        answersGrid.setLayout(new GridLayout(0,2,10,10));
        Border emptyBorder = new EmptyBorder(10,10,10,10);
        answersGrid.setBorder(emptyBorder);
    }

    public void addQuestion (String questionText){
        question = new JLabel(addHTML(questionText));
        question.setHorizontalAlignment(JLabel.CENTER);
        question.setVerticalAlignment(JLabel.TOP);
        question.setFont(new Font("Dialog",Font.BOLD,30));
        JPanel tmp = new JPanel();
        tmp.setLayout(new BorderLayout(10,10));
        tmp.setBackground(new Color(104, 120, 222, 119));
        tmp.add(question);
        Border emptyBorder = new EmptyBorder(20,20,20,20);
        tmp.setBorder(emptyBorder);
        topPanel.add(tmp,BorderLayout.NORTH);
    }

    public void addImage (String path){
        JLabel tmp = fitPicToFrame(path, WINDOW_HEIGHT);
        image = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(tmp.getWidth(), tmp.getHeight(), Image.SCALE_DEFAULT));

        labelImage = new JLabel();
        labelImage.setIcon(image);
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(labelImage);
    }
    public void updateProgressBar (int value){
        progressBar.setValue(value);
    }
    private void addProgressBar(int maxNum){
        progressBar = new JProgressBar();
        Border emptyBorder = new EmptyBorder(0,10,10,10);
        progressBar.setBorder(emptyBorder);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setBounds(0,0,700,50);
        progressBar.setMaximum(maxNum);
        lowerPanel.add(progressBar, BorderLayout.SOUTH);
    }

    public void addMoveButtons (JButton goRight, JButton goLeft){
        goLeft.setText("<--");
        goLeft.setFont(new Font("Dialog",Font.BOLD,20));
        goRight.setText("-->");
        goRight.setFont(new Font("Dialog",Font.BOLD,20));

        goRight.setFocusable(false);
        goLeft.setFocusable(false);


        Border leftEmpty = new EmptyBorder(10,10,10,0);
        Border rightEmpty = new EmptyBorder(10,0,10,10);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(rightEmpty);
        leftPanel.setBorder(leftEmpty);

        rightPanel.add (goRight);
        leftPanel.add(goLeft);

        lowerPanel.add(leftPanel,BorderLayout.WEST);
        lowerPanel.add(rightPanel,BorderLayout.EAST);
    }


    public void addAnswer (String description, Vector<JButton> options){
            JButton answer;
            answer = new JButton();
            answer.setText(addHTML(description));
            answer.setFont(new Font("Dialog",Font.BOLD,20));
            answer.setFocusable(false);
            options.add(answer);
            answersGrid.add(answer);
    }



    JLabel labelImage;
    ImageIcon image;

    JProgressBar progressBar;

    JLabel question;

    JPanel answersGrid;
    JPanel lowerPanel;
    JPanel topPanel;
    JPanel centerPanel;
    JFrame questionFrame;

}
