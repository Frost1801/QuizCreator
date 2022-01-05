package quizComponents;
//quiz class


import java.io.IOException;
import java.util.Vector;

public class Quiz {
    public Quiz(){
        questions = new Vector<>();
        currentQuestion = 0;
    }

    public void startQuiz (){
        currentQuestion = 0;
    }
    public boolean questionForward (){
        if (currentQuestion + 1 < questions.size()){
            currentQuestion ++;
            return true;
        }
        else {
            return false;
        }

    }
    public boolean questionBackwards (){
        if (currentQuestion> 0){
            currentQuestion --;
            return true;
        }
        else {
            return false;
        }

    }

    public void addQuestion (Question q){
        questions.add(q);
    }
    public void addOutcome (int questionN,int answerN, Outcome toAdd){
        questions.elementAt(questionN).addOutcome(answerN,toAdd);
    }
    public void addAnswer (int n, Answer toAdd) {
        questions.elementAt(n).addAnswers(toAdd);
    }

    public String getQuestionDescription (int n){
        return questions.elementAt(n).getDescription();
    }
    public int getQuestionOptionsNumber (int n){
        return questions.elementAt(n).getOptionsNumber();
    }
    public Answer getAnswer (int questionN, int answerN){
        return questions.elementAt(questionN).getAnswer(answerN);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public String getTitle() {
        return title;
    }
    public int getQuestionsNumber (){
        return questions.size();
    }
    public String getQuestionImagePath (int index){
        return questions.elementAt(index).getImage();
    }
    int currentQuestion;
    private String title;
    private Vector<Question> questions;
}
