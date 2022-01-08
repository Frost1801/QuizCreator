package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;


import static GUI.GUI.addHTML;
import static GUI.GUI.fitPicToFrame;


public class QuestionFrame extends JFrame {

    public static final int WINDOW_HEIGHT = 700;
    public static final int WINDOW_WIDTH = 700;

    public QuestionFrame(String title, int maxValue, JButton goRight, JButton goLeft, Vector<JButton> options){
        questionFrame = createMainFrame(title);
        centerPanel = createCenterPanel(questionFrame);
        topPanel = createTopPanel(centerPanel);
        lowerPanel = createLowerPanel(centerPanel);
        answersGrid = createAnswersGrid(lowerPanel);
        addMoveButtons(goRight, goLeft);
        addProgressBar(maxValue, lowerPanel);
    }

    public void setVisible (boolean visible){
        this.questionFrame.setVisible(visible);
    }

    public static JFrame createMainFrame(String title){
        JFrame toReturn = new JFrame();
        toReturn.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        toReturn.setResizable(false);
        toReturn.setLocationRelativeTo(null);
        toReturn.setTitle(title);
        toReturn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return toReturn;
    }

    public static JPanel createCenterPanel (JFrame destination){
        JPanel toReturn = new JPanel();
        toReturn.setBackground(Color.BLUE);
        destination.add(toReturn,BorderLayout.CENTER);
        toReturn.setLayout(new BoxLayout(toReturn,BoxLayout.Y_AXIS));
        return toReturn;
    }

    public static JPanel createTopPanel (JPanel destination){
        JPanel toReturn = new JPanel();
        toReturn.setBackground(Color.GREEN);
        destination.add(toReturn);
        toReturn.setLayout(new BorderLayout());
        return toReturn;
    }

    public static JPanel createLowerPanel (JPanel destination){
        JPanel toReturn = new JPanel();
        toReturn.setBackground(Color.RED);
        destination.add(toReturn, BoxLayout.Y_AXIS);
        toReturn.setLayout(new BorderLayout(20,20));
        return toReturn;
    }

    public static JPanel createAnswersGrid(JPanel destination){
        JPanel toReturn = new JPanel();
        toReturn.setBackground(Color.MAGENTA);

        destination.add(toReturn, BorderLayout.CENTER);
        toReturn.setLayout(new GridLayout(0,2,10,10));
        Border emptyBorder = new EmptyBorder(10,10,10,10);
        toReturn.setBorder(emptyBorder);
        return toReturn;
    }

    public static void addQuestion(String questionText, JPanel destination){
        JLabel toReturn = new JLabel(addHTML(questionText));
        toReturn.setHorizontalAlignment(JLabel.CENTER);
        toReturn.setVerticalAlignment(JLabel.TOP);
        toReturn.setFont(new Font("Dialog",Font.BOLD,30));
        JPanel tmp = new JPanel();
        tmp.setLayout(new BorderLayout(10,10));
        tmp.setBackground(new Color(104, 120, 222, 119));
        tmp.add(toReturn);
        Border emptyBorder = new EmptyBorder(20,20,20,20);
        tmp.setBorder(emptyBorder);
        destination.add(tmp,BorderLayout.NORTH);
    }

    public void addImage (String path, JPanel destination){
        JLabel tmp = fitPicToFrame(path, WINDOW_HEIGHT);
        //image inserted
        ImageIcon image = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(tmp.getWidth(), tmp.getHeight(), Image.SCALE_DEFAULT));

        //label of the inserted image
        JLabel labelImage = new JLabel();
        labelImage.setIcon(image);
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        destination.add(labelImage);
    }

    private void addProgressBar(int maxNum, JPanel destination){
        progressBar = new JProgressBar();
        Border emptyBorder = new EmptyBorder(0,10,10,10);
        progressBar.setBorder(emptyBorder);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setBounds(0,0,700,50);
        progressBar.setMaximum(maxNum);
        destination.add(progressBar, BorderLayout.SOUTH);
    }

    public void createGoRight (JButton goRight){
        goRight.setText("-->");
        goRight.setFont(new Font("Dialog",Font.BOLD,20));
    }

    public void addMoveButtons (JButton goRight, JButton goLeft){
        goLeft.setText("<--");
        goLeft.setFont(new Font("Dialog",Font.BOLD,20));
        createGoRight(goRight);

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


    public void addAnswer (String description, Vector<JButton> options, boolean isSelected){
            JButton answer;
            answer = new JButton();
            answer.setText(addHTML(description));
            answer.setFont(new Font("Dialog",Font.BOLD,20));
            answer.setFocusable(false);
            if (isSelected){
                answer.setBackground(Color.green);
            }
            options.add(answer);
            answersGrid.add(answer);
    }

    public void updateProgressBar (int value){
        progressBar.setValue(value);
    }
    public void reset(){
        answersGrid.removeAll();
        topPanel.removeAll();
        revalidate();
        repaint();
    }


    public JPanel getTopPanel() {
        return topPanel;
    }

    private JProgressBar progressBar; //progressbar

    private JLabel question; //question of the quiz

    private JPanel answersGrid; //holds the answers buttons
    private final JPanel lowerPanel; //holds lower elements
    private final JPanel topPanel; // holds top elements
    private JPanel centerPanel; //holds top and lower panel
    private final JFrame questionFrame; //holds center panel

}
