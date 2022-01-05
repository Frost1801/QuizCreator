package quizComponents;

import quizComponents.Answer;
import quizComponents.Outcome;
import quizComponents.Question;

import java.io.IOException;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Vector;

public class TextInterpreter {

    public void prepareTest (Quiz toPrepare) throws IOException {
        Vector <String []> questionData = initializeQuestions("/home/sergio/Git-Projects/quizCreator/quizComponents/questions.txt");
        Vector <String []> answersData = initializeQuestions("/home/sergio/Git-Projects/quizCreator/quizComponents/answers.txt");
        createQuestions(questionData,toPrepare);
        createAnswers(answersData,toPrepare);
    }

    private Vector<String []> initializeQuestions(String filePath) throws IOException{
        //reads the text file
        FileReader rd = new FileReader(filePath);
        //components to fill all the file in a single string
        int charStream;
        StringBuilder combinedElements = new StringBuilder();
        while ((charStream = rd.read())!= -1){ //creates a string with the characters of the file
            combinedElements.append((char)charStream);
        }

        //removes spaces and newlines
        String answersNoSpaces = combinedElements.toString().replaceAll("[\n\r]", "");

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

    //creates the answers based on the input file
    private void createAnswers (Vector<String []> answerData, Quiz quiz){
        for (int i = 0; i< answerData.size(); i++) {
            int n = 0;
            int counter = 0;
            for (int j = 0; j < answerData.elementAt(i).length; j++) {
                String element = answerData.elementAt(i)[j];
                //opcode is everything before the =
                String opCode = element.substring(0, element.lastIndexOf("="));
                //value is everything after the =
                String value = element.substring(element.lastIndexOf("=") + 1);

                Answer answerToPush;
                Outcome outcomeToPush;

                switch (opCode) {
                    case "N" -> n = Integer.parseInt(value) - 1;
                    case "DES" -> {
                        answerToPush = new Answer(quiz.getQuestionOptionsNumber(n) + 1, value);
                        quiz.addAnswer(n, answerToPush);
                    }
                    case "TYP" -> {
                        outcomeToPush = new Outcome(value);
                        quiz.addOutcome(n, counter, outcomeToPush);
                        counter++;
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
            n++;
            quiz.addQuestion(new Question(n,description,image));
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
