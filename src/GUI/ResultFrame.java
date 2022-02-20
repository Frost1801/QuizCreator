package GUI;

import javax.swing.*;

public class ResultFrame {

    public ResultFrame ( String description,String path, JButton backToMenu, String result){
        resultFrame = new JFrame();
        resultFrame = QuestionFrame.createMainFrame(result);
        double  ratio = 0;
        int size = 0;

        switch (result){
            case "adran" :  {
                ratio = 0.25;
                size = 20;
                break;
            }
            case "oryn" : {
                ratio = 0.125;
                size = 20;
                break;
            }
            case "knork" : {
                ratio = 0.75;
                break;
            }
            case "kairon" :{
                ratio = 1;
                break;
            }
        }

        CreditsFrame.createStandardFrame(resultFrame,"You got: " + result, description,backToMenu,path,size,ratio);
    }

    public void setVisible (boolean visible){
        resultFrame.setVisible(visible);
    }
    public void dispose (){
        resultFrame.dispose();
    }

    private JFrame resultFrame;
}
