package quizComponents;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Vector;

public class TextInterpreter {

    public void prepareTest (Quiz toPrepare) throws IOException {
        Vector <String []> questionData = initializeData("/home/sergio/Git-Projects/quizCreator/quizComponents/TxtFiles/questions.txt");
        Vector <String []> answersData = initializeData("/home/sergio/Git-Projects/quizCreator/quizComponents/TxtFiles/answers.txt");
        String credits = readText("/home/sergio/Git-Projects/quizCreator/quizComponents/TxtFiles/credits.txt");
        toPrepare.setCredits(credits);
        createQuestions(questionData,toPrepare);
        createAnswers(answersData,toPrepare);
    }
    //reads and formats the text file to extract data more easily
    private Vector<String []> initializeData(String filePath) throws IOException{
       String combinedElements = readText(filePath);
        //removes spaces and newlines
        String answersNoSpaces = combinedElements.replaceAll("[\n\r]", "");

        //splits all the questions and answers
        String [] separatedComponents;
        separatedComponents = Arrays.stream(answersNoSpaces.split("~~")).filter(e -> e.trim().length() > 0).toArray(String[]::new);

        Vector<String []> separatedLines = new Vector<>();
        String [] tmp;
        for (String splitAnswer : separatedComponents) {
            tmp = Arrays.stream(splitAnswer.split("#")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
            separatedLines.add(tmp);
        }
        return separatedLines;
    }

    private String readText (String filePath) throws IOException {
        //reads the text file
        FileReader rd = new FileReader(filePath);
        //components to fill all the file in a single string
        int charStream;
        StringBuilder combinedElements = new StringBuilder();
        while ((charStream = rd.read())!= -1){ //creates a string with the characters of the file
            combinedElements.append((char)charStream);
        }
        return combinedElements.toString();
    }

    //creates the answers based on the input file converted into strings
    private void createAnswers (Vector<String []> answerData, Quiz quiz){
        for (int i = 0; i< answerData.size(); i++) {
            int counter = 0; //takes count of how many answers we have pushed
            for (int j = 0; j < answerData.elementAt(i).length; j++) {
                String element = answerData.elementAt(i)[j];
                //opcode is everything before the =
                String opCode = element.substring(0, element.lastIndexOf("="));
                //value is everything after the =
                String value = element.substring(element.lastIndexOf("=") + 1);

                Answer answerToPush;

                switch (opCode) {
                    case "DES" -> {
                        answerToPush = new Answer(value);
                        quiz.addAnswer(i, answerToPush);
                    }
                    case "TYP" -> {
                        quiz.setAnswerType(i,counter,value);
                        counter ++;
                    }
                }
            }
        }
    }

    //creates the questions based on the input file
    private void createQuestions (Vector<String []> questionData, Quiz quiz){
        int n= 0;
        String description = "";
        String image = "";
        //starts at one because the first line is the quiz title
        setQuizTitle(questionData, quiz);
        for (int i = 1; i< questionData.size(); i++){
            for (int j = 0; j < questionData.elementAt(i).length; j++){
                String element =  questionData.elementAt(i)[j];
                //opcode is everything before the =
                String opCode = element.substring(0,element.lastIndexOf("="));
                //value is everything after the =
                String value = element.substring(element.lastIndexOf("=" )+ 1);
                switch (opCode) {
                    case "DES" -> description = value;
                    case "IMG" -> image = value;
                    default -> throw (new UnsupportedOperationException("Invalid string opcode" + element));
                }
            }
            quiz.addQuestion(new Question(n,description,image));
            n++;
        }
    }

    //sets the title of the test
    private void setQuizTitle (Vector<String []> questionData, Quiz quiz){
        String element =  questionData.elementAt(0)[0];
        String opCode = element.substring(0,element.lastIndexOf("="));
        if (opCode.equals("TLT")){
            String value = element.substring(element.lastIndexOf("=" )+ 1);
            quiz.setTitle(value);
        }
    }


}
