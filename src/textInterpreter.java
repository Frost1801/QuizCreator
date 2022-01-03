
import quizComponents.Answer;
import quizComponents.Question;
import quizComponents.Quiz;

import java.io.IOException;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Vector;

public class textInterpreter {
    textInterpreter()  {
        quiz = new Quiz();

    }

    public void splitQuestions() throws IOException{
        //reads the text file
        FileReader questionsReader = new FileReader("/home/sergio/Git Projects/quizCreator/quizComponents/questions.txt");
        FileReader answersReader = new FileReader("/home/sergio/Git Projects/quizCreator/quizComponents/answers.txt");

        int charStream;
        StringBuilder combinedQuestions = new StringBuilder();
        StringBuilder combinedAnswers = new StringBuilder();
        while ((charStream = questionsReader.read())!= -1){ //creates a string with the characters of the file
            combinedQuestions.append((char)charStream);
        }
        while ((charStream = answersReader.read())!= -1){ //creates a string with the characters of the file
            combinedAnswers.append((char)charStream);
        }

        //removes spaces and newlines
        String questionsNoSpaces = combinedQuestions.toString().replaceAll("[\n\r]", "");
        String answersNoSpaces = combinedAnswers.toString().replaceAll("[\n\r]", "");

        //splits all the questions
        String [] splitQuestions;
        splitQuestions = Arrays.stream(questionsNoSpaces.split("~~")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
        String [] splitAnswers;
        splitAnswers = Arrays.stream(answersNoSpaces.split("~~")).filter(e -> e.trim().length() > 0).toArray(String[]::new);

        Vector<String[]> questionData = new Vector<>();
        Vector<String []> answersData = new Vector<>();
        String [] tmp;
        for (String splitQuestion : splitQuestions) {
            tmp = Arrays.stream(splitQuestion.split("#")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
            questionData.add(tmp);
        }
        for (String splitAnswer : splitAnswers) {
            tmp = Arrays.stream(splitAnswer.split("#")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
            answersData.add(tmp);
        }
        createQuestions(questionData);
        createAnswers(answersData);
    }
    private void createAnswers (Vector<String []> answerData){
        for (int i = 0; i< answerData.size(); i++) {
            int n = 0;
            for (int j = 0; j < answerData.elementAt(i).length; j++) {
                String element = answerData.elementAt(i)[j];
                //opcode is everything before the =
                String opCode = element.substring(0, element.lastIndexOf("="));
                //value is everything after the =
                String value = element.substring(element.lastIndexOf("=") + 1);

                Answer toPush;
                if (opCode.equals("N")){
                    n = Integer.parseInt(value) - 1;
                }

                else if (opCode.equals("DES")){
                    toPush = new Answer(quiz.getQuestionOptionsNumber(n) + 1, value);
                    quiz.addAnswer(n,toPush);
                }
            }
        }
    }

    private void createQuestions (Vector<String []> questionData){
        int n= 0;
        String description = "";
        String image = "";
    //starts at one to ignore number of options
        for (int i = 1; i< questionData.size(); i++){
            for (int j = 0; j < questionData.elementAt(i).length; j++){
                String element =  questionData.elementAt(i)[j];
                //opcode is everything before the =
                String opCode = element.substring(0,element.lastIndexOf("="));
                //value is everything after the =
                String value = element.substring(element.lastIndexOf("=" )+ 1);
                switch (opCode) {
                    case "NUM" -> {
                        n = Integer.parseInt(value);
                    }
                    case "DES" -> {
                        description = value;
                    }
                    case "IMG" -> {
                        image = value;
                    }
                    default -> {
                        throw (new UnsupportedOperationException("Invalid string opcode" + element));
                    }
                }

            }
            quiz.addQuestion(new Question(n,description,image));
        }
    }


    Quiz quiz;

}
