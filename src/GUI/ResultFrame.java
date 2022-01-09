package GUI;

import javax.swing.*;

public class ResultFrame {

    public ResultFrame ( String description,String path, JButton backToMenu){
        resultFrame = new JFrame();
        resultFrame = QuestionFrame.createMainFrame("Results");
        CreditsFrame.createStandardFrame(resultFrame,"Results", description,backToMenu,path);
    }

    public void setVisible (boolean visible){
        resultFrame.setVisible(visible);
    }
    public void dispose (){
        resultFrame.dispose();
    }

    private JFrame resultFrame;
}
