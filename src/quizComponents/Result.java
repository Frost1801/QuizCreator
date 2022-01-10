package quizComponents;

//class represents a possible result
public class Result {
    public Result (String type,String description,String imagePath){
        instances = 0;
        this.type = type;
        this.description = description;
        this.imagePath = imagePath;
    }

    public int getInstances() {
        return instances;
    }
    public void incrementInstances (){
        instances +=1;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    private int instances;
    private final String type;
    private final String description;
    private final String imagePath;
}
