package quizComponents;

import java.util.ArrayList;
//rapresents a question in the quiz
public class Question {
    Question (int n, String description, String image){
        this.n = n;
        this.description = description;
        this.image = image;
    }


    private int n;
    private String description;
    private String image; //path of the image to load for the question
    private ArrayList<Option> options; //available options for the question
}
