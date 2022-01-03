package quizComponents;
//quiz class

import java.util.ArrayList;
import java.util.Vector;

public class Quiz {
    public Quiz(){
        questions = new Vector<>();
    }

    public void setOptions(int options) {
        this.options = options;
    }

    public int getOptions() {
        return options;
    }
    public void addQuestion (Question q){
        questions.add(q);
    }
    public void addAnswer (int n, Answer toAdd) {
        questions.elementAt(n).addAnswers(toAdd);
    }
    public int getQuestionOptionsNumber (int n){
       return questions.elementAt(n).getOptionsNumber();
    }

    private Vector<Question> questions;
    int options;
}
