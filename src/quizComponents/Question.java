package quizComponents;

import java.util.Vector;

//represents a question in the quiz
public class Question {
    public Question(int n, String description, String image){
        this.description = description;
        this.image = image;
        answers = new Vector<>();
    }

    public int getAnswersNumber(){
        return answers.size();
    }
    public void addAnswers (Answer toAdd){
        answers.add(toAdd);
    }

    public Answer getAnswer (int index){
        return answers.elementAt(index);
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
    public void setAnswerIsSelected (int index, boolean isSelected){
        answers.elementAt(index).setSelected(isSelected);
    }
    public boolean getAnswerIsSelected(int index){
        return answers.elementAt(index).isSelected();
    }

    public void setAnswerType (int index, String type){
        answers.elementAt(index).setType(type);
    }
    public String getAnswerType (int index){
        return answers.elementAt(index).getType();
    }


    private final String description;
    private final String image; //path of the image to load for the question
    private final Vector<Answer> answers; //available options for the question
}
