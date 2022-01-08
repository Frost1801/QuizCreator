package GUI;

import quizComponents.Answer;
import quizComponents.Quiz;
import quizComponents.TextInterpreter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import static GUI.TitleFrame.createCenterGenericButton;


public class GUI implements ActionListener {

    public GUI () throws IOException {
        TextInterpreter textInterpreter = new TextInterpreter();
        quiz = new Quiz();
        textInterpreter.prepareTest(quiz);



        tf = new TitleFrame(quiz.getTitle());
        start = createCenterGenericButton("Start",tf.getOptionsPanel());
        credits = createCenterGenericButton("Credits",tf.getOptionsPanel());
        quit = createCenterGenericButton("Quit",tf.getOptionsPanel());
        addMenuActionListener();
        tf.getOptionsPanel().add(Box.createRigidArea(new Dimension(0,150)));

        tf.setVisible(true);

    }

    //static methods used for the mainframe

    private void createQuizWindow (){
        options = new Vector<>();
        goLeft = new JButton();
        goRight = new JButton();

        goLeft.addActionListener(this);
        goRight.addActionListener(this);

        qf = new QuestionFrame(quiz.getTitle(),quiz.getQuestionsNumber(), goRight,goLeft, options);
        displayFullQuestion(0);
        addOptionsActionListener();
    }

    private static double findRatio(int height, int windowHeight){
        int previous = height;
        double ratio = 0.60;
        int desiredHeight = (int) (windowHeight * ratio);
        while (height > desiredHeight)
            height *= ratio;
        return height/(double)previous;
    }

    public static JLabel fitPicToFrame(String path, int windowHeight){
        JLabel tmp = new JLabel();
        int width;
        int height;
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;

        width = img.getWidth();
        height = img.getHeight();

        double ratio = findRatio(height,windowHeight);
        width *= ratio;
        height *= ratio;
        tmp.setSize(width,height);
        return tmp;
    }

    public static String addHTML (String toReturn){
        return  "<html><body style='text-align: center'><p>" + toReturn + "</p></html>";
    }




    private void addOptionsActionListener(){
        for (int i = 0; i < options.size(); i++){
            options.elementAt(i).addActionListener(this);
        }
    }

    private void addMenuActionListener(){
        start.addActionListener(this);
        credits.addActionListener(this);
        quit.addActionListener(this);
    }



    private void displayAnswers (int questionIndex, int numberOfAnswers) {
        options = new Vector<>();
        Answer ans;
        for (int i = 0; i < numberOfAnswers; i++) {
            ans = quiz.getAnswer(questionIndex,i);
            qf.addAnswer(ans.getDescription(), options, false);
        }
        updateAnswerColor();
        addOptionsActionListener();
    }

    private void updateWindow (){
        int n = quiz.getCurrentQuestion();
        qf.reset();
        displayFullQuestion(n);
    }

    private void displayFullQuestion (int questionIndex){
        int availableOptions = quiz.getQuestionOptionsNumber(questionIndex);
        String description = quiz.getQuestionDescription(questionIndex);
        String imagePath = quiz.getQuestionImagePath(questionIndex);

        displayAnswers(questionIndex,availableOptions);
        displayQuestionImage(imagePath);
        displayQuestionDescription(description);
        updateGoRight();
        qf.setVisible(true);
    }

    //updates the progressbar based on the current answered vs the total number
    private void updateProgressBar (){
        qf.updateProgressBar(quiz.getAnswered());
    }

    //changes goRight button in case we are at the last question
    private void updateGoRight (){
        if (isLastQuestion()){
            String text = addHTML("Complete<br/>Quiz");
            goRight.setText(text);
        }
        else {
            qf.createGoRight(goRight);
        }
    }

    //sets the color of the answers when updating the frame to another question
    private void updateAnswerColor (){
        int n = quiz.getCurrentQuestion();
        int selectedIndex = quiz.getSelectedAnswerIndex(n);
        if (selectedIndex != -1){
            options.elementAt(selectedIndex).setBackground(Color.GREEN);
        }
    }


    //sets the color of the currently selected answer to green
    private void setSelectedColor(int index){
        for (JButton it: options){
            it.setBackground(new JButton().getBackground());
        }
        options.elementAt(index).setBackground(Color.GREEN);
    }

    private void displayQuestionImage (String path){
        qf.addImage(path, qf.getTopPanel());
    }

    private void displayQuestionDescription (String description){
        qf.addQuestion(description, qf.getTopPanel());
    }

    //returns true only if we are on the last question
    private boolean isLastQuestion (){
        int n = quiz.getCurrentQuestion();
        int totAnswered = quiz.getAnswered();
        int totQuestions = quiz.getQuestionsNumber();
        return n == totQuestions - 1 && totQuestions == totAnswered;
    }

    Quiz quiz;
    QuestionFrame qf;
    JButton goRight;
    JButton goLeft;
    Vector<JButton> options;

    TitleFrame tf;
    JButton start;
    JButton credits;
    JButton quit;

    ConfirmQuitFrame cf;


    @Override
    public void actionPerformed(ActionEvent e) {
        int n = quiz.getCurrentQuestion();
        if (e.getSource() == goRight){
            if (quiz.questionForward()){
                updateWindow();
            }
            if (isLastQuestion()){
                quiz.getResult();
            }
        }
        else if (e.getSource() == goLeft){
            if (quiz.questionBackwards()){
                updateWindow();
            }
        }
        if (options != null){
            for (int i = 0; i < options.size(); i++){
                if (e.getSource() == options.elementAt(i)){

                    if (quiz.getSelectedAnswerIndex(n) == -1){ //if no answer is selected and we just selected one increases the number of selected answers
                        quiz.incrementAnswered();
                    }
                    quiz.setIsSelectedAnswer(n,i,true);
                    updateProgressBar();
                    setSelectedColor(i);
                    updateGoRight();
                }
            }
        }

        if (e.getSource() == start){
            createQuizWindow();
            tf.dispose();
        }
        else if (e.getSource() == credits){
            System.out.println("credits");
        }
        else if (e.getSource() == quit){
            if (cf != null) {
                cf.dispose();
            }
            cf = new ConfirmQuitFrame();
            cf.setTitleFrame(tf);
        }

    }
}
