package quizComponents;

import java.util.ArrayList;
import java.util.Vector;

//rapresents a question in the quiz
public class Question {
    public Question(int n, String description, String image){
        this.n = n;
        this.description = description;
        this.image = image;
        answers = new Vector<>();
    }

    public int getOptionsNumber (){
        return answers.size();
    }
    public void addAnswers (Answer toAdd){
        answers.add(toAdd);
    }


    private int n;
    private String description;
    private String image; //path of the image to load for the question
    private Vector<Answer> answers; //available options for the question
}
