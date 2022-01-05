package GUI;

import quizComponents.Answer;
import quizComponents.Quiz;
import quizComponents.TextInterpreter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;


public class GUI implements ActionListener {

    public GUI () throws IOException {
        TextInterpreter textInterpreter = new TextInterpreter();
        quiz = new Quiz();
        textInterpreter.prepareTest(quiz);
        options = new Vector<>();

        goLeft = new JButton();
        goRight = new JButton();

        goLeft.addActionListener(this);
        goRight.addActionListener(this);
        qf = new MainFrame(quiz.getTitle(),quiz.getQuestionsNumber(), goRight,goLeft, options);
        displayFullQuestion(0);

    }

    private void displayFullQuestion (int questionIndex){
        int availableOptions = quiz.getQuestionOptionsNumber(questionIndex);
        String description = quiz.getQuestionDescription(questionIndex);
        String imagePath = quiz.getQuestionImagePath(questionIndex);

        displayAnswers(questionIndex,availableOptions);
        displayQuestionImage(imagePath);
        displayQuestionDescription(description);
        qf.setVisible(true);
    }

    private void addActionListener (){
        for (JButton opt: options){
            opt.addActionListener(this);
        }
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
       // return "<html><p>" + toReturn + "</p></html>";
        return  "<html><body style='text-align: center'><p>" + toReturn + "</p></html>";
    }

    private void displayAnswers (int questionIndex, int numberOfAnswers) {
        Answer ans;
        for (int i = 0; i < numberOfAnswers; i++) {
            ans = quiz.getAnswer(questionIndex,i);
            qf.addAnswer(ans.getDescription(), options);
        }
    }

    private void displayQuestionImage (String path){
        qf.addImage(path);
    }

    private void displayQuestionDescription (String description){
        qf.addQuestion(description);
    }

    Quiz quiz;
    MainFrame qf;
    JButton goRight;
    JButton goLeft;
    Vector<JButton> options;


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goRight){
            if (quiz.questionForward()){
                int n = quiz.getCurrentQuestion();
                qf.reset();
                displayFullQuestion(n);
                qf.updateProgressBar(n);
            }
        }
        if (e.getSource() == goLeft){
            if (quiz.questionBackwards()){
                int n = quiz.getCurrentQuestion();
                qf.reset();
                displayFullQuestion(n);
                qf.updateProgressBar(n);
            }
        }

    }
}
