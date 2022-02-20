package GUI;

import quizComponents.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;


import static GUI.ConfirmFrame.createButton;
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
        String questionsPath = "/res/TxtFiles/questions.txt";
        String answersPath = "/res/TxtFiles/answers.txt";
        String creditsPath = "/res/TxtFiles/credits.txt";
        String resultsPath = "/res/TxtFiles/results.txt";
        textInterpreter.prepareTest(quiz, questionsPath,answersPath,creditsPath, resultsPath);
    }

    private void createConfirmationFrame (String question){
        yes = createButton("Yes");
        no = createButton("No");
        confirmQuitFrame = new ConfirmFrame(yes,no,question);
        yes.addActionListener(this);
        no.addActionListener(this);
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
        resultFrame = new ResultFrame(rs.getDescription(),rs.getImagePath(), backToMainMenu, rs.getType());
        resultFrame.setVisible(true);
    }

    private static double findRatio(int height, int windowHeight){
        int previous = height;
        double ratio = 0.50;
        int desiredHeight = (int) (windowHeight * ratio);
        while (height > desiredHeight)
            height *= ratio;
        return height/(double)previous;
    }

    public static JLabel fitPicToFrame(String path, int windowHeight, double ratio){
        JLabel tmp = new JLabel();
        int width;
        int height;
        MyReader rd = new MyReader();
        BufferedImage img = rd.readImage(path);
        assert img != null;

        width = img.getWidth();
        height = img.getHeight();
        if (ratio == 0)
            ratio = findRatio(height,windowHeight);
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
            questionFrame.addAnswer(ans.getDescription(), options);
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
            goRight.setFont(new Font("Dialog",Font.BOLD,12));
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
            options.elementAt(selectedIndex).setBackground(new Color(23, 162, 95));
        }
    }


    //sets the color of the currently selected answer to green
    private void setSelectedColor(int index){
        for (JButton it: options){
            it.setBackground(new JButton().getBackground());
        }
        options.elementAt(index).setBackground(new Color(23, 162, 95));
    }

    private void displayQuestionImage (String path){
        QuestionFrame.addImageToPanel(path, questionFrame.getTopPanel());
    }

    private void displayQuestionDescription (String description){
        QuestionFrame.addQuestion(description, questionFrame.getTopPanel());
    }

    //returns true only if we are on the last question
    private boolean isLastQuestion (){
        int currentQuestion = quiz.getCurrentQuestion();
        int totAnswered = quiz.getAnswered();
        int totQuestions = quiz.getQuestionsNumber();
        return currentQuestion == totQuestions - 1 && totQuestions == totAnswered;
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

    ConfirmFrame confirmQuitFrame;
    JButton yes;
    JButton no;


    JButton back;
    CreditsFrame creditsFrame;

    ResultFrame resultFrame;
    JButton backToMainMenu;



    @Override
    public void actionPerformed(ActionEvent e) {
        int n = quiz.getCurrentQuestion();
        if (e.getSource() == goRight){
            if (isLastQuestion()){
               createConfirmationFrame("Confirm?");
            }
            if (quiz.questionForward()){
                updateWindow();
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
            createConfirmationFrame("Exit?");
        }
        if (e.getSource() == yes){
            if (titleFrame != null){
                titleFrame.dispose();
                confirmQuitFrame.dispose();
                titleFrame = null;
            }
            if (questionFrame != null){
                createResultsWindow();
                questionFrame.dispose();
                questionFrame = null;
            }
        }

        if (e.getSource() ==  no){
            confirmQuitFrame.dispose();
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

