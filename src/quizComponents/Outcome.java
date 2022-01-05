package quizComponents;

public class Outcome {
    Outcome (){
        instances = 0;
    }
    public Outcome(String type){
        this.type = type;
        instances = 0;
    }
    private String type;
    private int instances;
}
