package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Vector;


import static GUI.GUI.addHTML;
import static GUI.GUI.fitPicToFrame;


public class QuestionFrame extends JFrame {

    public static final int WINDOW_HEIGHT = 900;
    public static final int WINDOW_WIDTH = 900;

    public QuestionFrame(String title, int maxValue, JButton goRight, JButton goLeft){
        questionFrame = createMainFrame(title);
        centerPanel = createCenterPanel(questionFrame);
        topPanel = createTopPanel(centerPanel);
        lowerPanel = createLowerPanel(centerPanel);
        answersGrid = createAnswersGrid(lowerPanel);
        addMoveButtons(goRight, goLeft);
        addProgressBar(maxValue, lowerPanel);
    }

    //sets visible
    public void setVisible (boolean visible){
        this.questionFrame.setVisible(visible);
    }

    //creates a main frame with a title and fixed dimensions in the middle of the screen
    public static JFrame createMainFrame(String title){
        JFrame toReturn = new JFrame();
        toReturn.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        toReturn.setResizable(false);
        toReturn.setLocationRelativeTo(null);
        toReturn.setTitle(title);
        toReturn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int dimensions = 5;
        Color borderColor =new Color(23, 29, 34);
        MatteBorder matteBorder = new MatteBorder(dimensions, dimensions, dimensions, dimensions, borderColor);
        toReturn.getRootPane().setBorder(matteBorder);
        return toReturn;
    }

    //creates a border-layout panel
    public static JPanel createCenterPanel (JFrame destination){
        JPanel toReturn = new JPanel();
        toReturn.setBackground(new Color(23, 29, 34));
        destination.add(toReturn,BorderLayout.CENTER);
        toReturn.setLayout(new BoxLayout(toReturn,BoxLayout.Y_AXIS));
        return toReturn;
    }

    //creates a border-layout panel
    public static JPanel createTopPanel (JPanel destination){
        JPanel toReturn = new JPanel();
        toReturn.setBackground(new Color(255, 255, 255));
        destination.add(toReturn);
        toReturn.setLayout(new BorderLayout());
        return toReturn;
    }

    public static JPanel createLowerPanel (JPanel destination){
        JPanel toReturn = new JPanel();
        toReturn.setBackground(new Color(23, 29, 34));
        destination.add(toReturn, BoxLayout.Y_AXIS);
        toReturn.setLayout(new BorderLayout(20,20));
        return toReturn;
    }

    public static JPanel createAnswersGrid(JPanel destination){
        JPanel toReturn = new JPanel();
        toReturn.setBackground(new Color(23, 29, 34));

        destination.add(toReturn, BorderLayout.CENTER);
        toReturn.setLayout(new GridLayout(0,2,10,10));
        Border emptyBorder = new EmptyBorder(10,10,10,10);
        toReturn.setBorder(emptyBorder);
        return toReturn;
    }

    public static void addQuestion(String questionText, JPanel destination){
        JLabel lbl = createLabel(questionText,0);
        lbl.setForeground(new Color(23, 29, 34));
        JPanel tmp = new JPanel();
        tmp.setLayout(new BorderLayout(10,10));
        tmp.setBackground(new Color(193, 8, 24, 255));
        tmp.add(lbl);

        Border emptyBorder = new EmptyBorder(20,20,20,20);
        tmp.setBorder(emptyBorder);
        destination.add(tmp,BorderLayout.NORTH);
    }

    private  static int getRightTextSize (String text){
        int length = text.length();
        if (length > 225){
            return 15;
        }
        else if (length > 150){
            return 20;
        }
        else  if (length > 100 )
            return 25;
        else if (length > 70)
            return 30;
        else {
            return 50;
        }
    }

    public static JLabel createLabel (String text, int size){
        JLabel toReturn = new JLabel(addHTML(text));
        toReturn.setHorizontalAlignment(JLabel.CENTER);
        toReturn.setVerticalAlignment(JLabel.TOP);
        if (size == 0)
            size = getRightTextSize (text);
        toReturn.setFont(new Font("Dialog",Font.BOLD,size));
        return toReturn;
    }

    public static ImageIcon getFixedDimensionImage (String path, double ratio){
        JLabel tmp = fitPicToFrame(path, WINDOW_HEIGHT,ratio);
        //image inserted
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(tmp.getWidth(), tmp.getHeight(), Image.SCALE_DEFAULT));
    }

    public static void addImageToPanel(String path, JPanel destination){
        ImageIcon image = getFixedDimensionImage(path,0);
        //label of the inserted image
        JLabel labelImage = new JLabel();
        labelImage.setIcon(image);
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        destination.add(labelImage);
    }
    public static void addImageToButton (String path, JButton destination,double ratio){
        ImageIcon image = getFixedDimensionImage(path,ratio);
        //label of the inserted imag
        destination.setIcon(image);
    }

    private void addProgressBar(int maxNum, JPanel destination){
        progressBar = new JProgressBar();
        Border emptyBorder = new EmptyBorder(0,10,10,10);
        progressBar.setBorder(emptyBorder);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setBounds(0,0,700,50);
        progressBar.setMaximum(maxNum);
        progressBar.setBackground(new Color(23, 29, 34));
        progressBar.setForeground(new Color(193, 8, 24));
        progressBar.setFont(new Font("Dialog",Font.BOLD,15));
        destination.add(progressBar, BorderLayout.SOUTH);
    }

    public void createGoRight (JButton goRight){
        String rightPath = "/home/sergio/Git-Projects/quizCreator/ImmaginiGUI/Right-arrow.png";
        addImageToButton(rightPath,goRight,0.125);
        goRight.setFocusable(false);
    }
    public void addMoveButtons (JButton goRight, JButton goLeft){

        String leftPath = "/home/sergio/Git-Projects/quizCreator/ImmaginiGUI/Left-arrow.png";
        addImageToButton(leftPath,goLeft,0.125);
        goLeft.setFocusable(false);


        Color borderColor = new Color(193, 8, 24, 255);
        int distance = 10;
        Border leftMatte = new MatteBorder(distance,distance,distance,distance, borderColor );
        Border rightMatte =  new MatteBorder(distance,distance,distance,distance, borderColor);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(rightMatte);
        leftPanel.setBorder(leftMatte);

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

    public void updateProgressBar (int value){
        progressBar.setValue(value);
    }
    public void reset(){
        answersGrid.removeAll();
        topPanel.removeAll();
        revalidate();
        repaint();
    }



    public void dispose (){
        questionFrame.dispose();
    }
    public JPanel getTopPanel() {
        return topPanel;
    }

    private JProgressBar progressBar; //progressbar


    private final JPanel answersGrid; //holds the answers buttons
    private final JPanel lowerPanel; //holds lower elements
    private final JPanel topPanel; // holds top elements
    private final JPanel centerPanel; //holds top and lower panel
    private final JFrame questionFrame; //holds center panel

}
