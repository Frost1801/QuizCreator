import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.FileReader;

public class textInterpreter {
    textInterpreter() throws FileNotFoundException { //throws exception, so handle it
        this.rd = new FileReader("/home/sergio/Git Projects/quizCreator/quizComponents/questions.txt");
    }



    public void readFullText () throws IOException {
        int character;
        while ((character = rd.read())!= -1){
            System.out.print((char) character);
        }
    }


    private FileReader rd;
}
