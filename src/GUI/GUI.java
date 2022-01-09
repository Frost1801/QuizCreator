package GUI;

import quizComponents.Answer;
import quizComponents.Quiz;
import quizComponents.Result;
import quizComponents.TextInterpreter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import static GUI.TitleFrame.createCenterGenericButton;


public class GUI implements ActionListener {

    public GUI () throws IOException {
        initializeQuiz();
        createTitleWindow();
    }

    //static methods used for the mainframe
    private void initializeQuiz () throws IOException {
        TextInterpreter textInterpreter = new TextInterpreter();
        quiz = new Quiz();
        textInterpreter.prepareTest(quiz);
    }
    private void createTitleWindow (){
        titleFrame = new TitleFrame(quiz.getTitle());
        start = createCenterGenericButton("Start", titleFrame.getOptionsPanel());
        credits = createCenterGenericButton("Credits", titleFrame.getOptionsPanel());
        quit = createCenterGenericButton("Quit", titleFrame.getOptionsPanel());
        addMenuActionListener();
        titleFrame.getOptionsPanel().add(Box.createRigidArea(new Dimension(0,150)));
        titleFrame.setVisible(true);
    }
    private void createQuizWindow (){
        options = new Vector<>();
        goLeft = new JButton();
        goRight = new JButton();



        goLeft.addActionListener(this);
        goRight.addActionListener(this);


        questionFrame = new QuestionFrame(quiz.getTitle(),quiz.getQuestionsNumber(), goRight,goLeft);
        displayFullQuestion(0);
        addOptionsActionListener();
    }

    private void createCreditsWindow (){
        back = new JButton();
        back = TitleFrame.createGenericButton("Back");
        back.addActionListener(this);
        creditsFrame = new CreditsFrame(quiz.getCredits(), back);
        creditsFrame.setVisible(true);
    }

    private void createResultsWindow (){
        backToMainMenu = new JButton();
        backToMainMenu = TitleFrame.createGenericButton("Back to main menu");
        backToMainMenu.addActionListener(this);
        Result rs = quiz.getResult();
        resultFrame = new ResultFrame(rs.getDescription(),rs.getImagePath(), backToMainMenu);
        resultFrame.setVisible(true);
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
            questionFrame.addAnswer(ans.getDescription(), options, false);
        }
        updateAnswerColor();
        addOptionsActionListener();
    }

    private void updateWindow (){
        int n = quiz.getCurrentQuestion();
        questionFrame.reset();
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
        questionFrame.setVisible(true);
    }

    //updates the progressbar based on the current answered vs the total number
    private void updateProgressBar (){
        questionFrame.updateProgressBar(quiz.getAnswered());
    }

    //changes goRight button in case we are at the last question
    private void updateGoRight (){
        if (isLastQuestion()){
            String text = addHTML("Complete<br/>Quiz");
            goRight.setText(text);
        }
        else {
            questionFrame.createGoRight(goRight);
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
        questionFrame.addImage(path, questionFrame.getTopPanel());
    }

    private void displayQuestionDescription (String description){
        QuestionFrame.addQuestion(description, questionFrame.getTopPanel());
    }

    //returns true only if we are on the last question
    private boolean isLastQuestion (){
        int n = quiz.getCurrentQuestion();
        int totAnswered = quiz.getAnswered();
        int totQuestions = quiz.getQuestionsNumber();
        return n == totQuestions - 1 && totQuestions == totAnswered;
    }

    Quiz quiz;
    QuestionFrame questionFrame;
    JButton goRight;
    JButton goLeft;
    Vector<JButton> options;

    TitleFrame titleFrame;
    JButton start;
    JButton credits;
    JButton quit;

    ConfirmQuitFrame confirmQuitFrame;

    JButton back;
    CreditsFrame creditsFrame;

    ResultFrame resultFrame;
    JButton backToMainMenu;

    @Override
    public void actionPerformed(ActionEvent e) {
        int n = quiz.getCurrentQuestion();
        if (e.getSource() == goRight){
            if (quiz.questionForward()){
                updateWindow();
            }
            if (isLastQuestion()){
                createResultsWindow();
                questionFrame.dispose();
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
            titleFrame.dispose();
        }
        else if (e.getSource() == credits){
            createCreditsWindow();
            titleFrame.dispose();
        }
        else if (e.getSource() == quit){
            if (confirmQuitFrame != null) {
                confirmQuitFrame.dispose();
            }
            confirmQuitFrame = new ConfirmQuitFrame();
            confirmQuitFrame.setTitleFrame(titleFrame);
        }

        if (e.getSource() == back){ //to go back to Main menu
            createTitleWindow();
            creditsFrame.dispose();
        }

        if (e.getSource() == backToMainMenu){
            createTitleWindow();
            try {
                initializeQuiz();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            resultFrame.dispose();
        }

    }
}

