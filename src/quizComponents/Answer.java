package quizComponents;
//represents a single answer option

public class Answer {
    public Answer(String description){
        this.description = description;
        selected = false;
    }

    public String getDescription() {
        return description;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    private String type; //indicates the type of answers group (Result) the answer belong to
    private boolean selected;
    private final String description;
}
