package quizComponents;
//represents a single answer option

import javax.swing.*;

public class Answer {
    public Answer(int n, String description){
        this.description = description;
        outcome = new Outcome();
    }
    public void setOutcome(Outcome toAdd){
        outcome = toAdd;
    }

    public String getDescription() {
        return description;
    }

    private String description;
    private Outcome outcome;
}
