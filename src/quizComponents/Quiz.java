package quizComponents;
//quiz class


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Quiz {
    public Quiz(){
        questions = new Vector<>();
        results = new Vector<>();
        currentQuestion = 0;
        answered = 0;
    }
    //**MOVING THROUGH THE QUIZ**

    public Result getResult (){
        for (Question it: questions){
            for (int i = 0; i < it.getAnswersNumber(); i++){
                if (it.getAnswerIsSelected(i)){
                    String type = it.getAnswerType(i);
                    for (Result rs : results){
                        if (rs.getType().equals(type)){
                            rs.incrementInstances();
                            break;
                        }

                    }
                }
            }
        }
        return getMaximumInstances();
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



    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    //**ANSWER METHODS**

    //methods that change the selected answer
    public void setIsSelectedAnswer (int questionN, int answerN, boolean isSelected ){
        resetSelectedAnswer();
        questions.elementAt(questionN).getAnswer(answerN).setSelected(isSelected);
    }
    public boolean getAnswerIsSelected(int questionN, int answerN){
        return questions.elementAt(questionN).getAnswer(answerN).isSelected();
    }

    public int getSelectedAnswerIndex (int questionN){
        Question current = questions.elementAt(questionN);
        for (int i = 0; i < current.getAnswersNumber(); i++){
            if (current.getAnswerIsSelected(i)){
                return i;
            }
        }
        return -1;
    }

    public void resetSelectedAnswer (){
        int n = getCurrentQuestion();
        for (int i = 0; i < questions.elementAt(n).getAnswersNumber(); i++){
            questions.elementAt(n).setAnswerIsSelected(i,false);
        }
    }
    public void addAnswer (int questionN, Answer toAdd) {
        questions.elementAt(questionN).addAnswers(toAdd);
    }

    public void setAnswerType (int questionN, int answerN, String type){
        questions.elementAt(questionN).setAnswerType(answerN, type);
    }




    //**QUESTION METHODS**
    public void addQuestion (Question q){
        questions.add(q);
    }
    public int getCurrentQuestion() {
        return currentQuestion;
    }
    public String getQuestionDescription (int n){
        return questions.elementAt(n).getDescription();
    }
    public int getQuestionOptionsNumber (int n){
        return questions.elementAt(n).getAnswersNumber();
    }
    public Answer getAnswer (int questionN, int answerN){
        return questions.elementAt(questionN).getAnswer(answerN);
    }
    public int getQuestionsNumber (){
        return questions.size();
    }
    public String getQuestionImagePath (int index){
        return questions.elementAt(index).getImage();
    }

    public void incrementAnswered() {
        this.answered ++;
    }

    public int getAnswered() {
        return answered;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getCredits() {
        return credits;
    }

    public void addResult (Result toAdd){
        results.add(toAdd);
    }

    private Result getMaximumInstances (){
        int maxIndex =0;
        for (int i = 1; i < results.size(); i++){
            if (results.elementAt(i).getInstances() > results.elementAt(maxIndex).getInstances()){
                maxIndex = i;
            }
        }
        return results.elementAt(maxIndex);
    }

    private int answered;
    private int currentQuestion;
    private String title;
    private final Vector<Question> questions;
    private final Vector<Result> results;
    private String credits;

}
