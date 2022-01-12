package GUI;

import javax.swing.*;
import java.awt.*;


public class CreditsFrame {
    public CreditsFrame ( String creditsDescription, JButton back){
        creditsFrame = new JFrame();
        creditsFrame = QuestionFrame.createMainFrame("Credits");
        createStandardFrame(creditsFrame,"Credits",creditsDescription,back,null,40,0);
    }

    public static void createStandardFrame (JFrame destination, String title, String creditsDescription, JButton functionality, String imagePath, int labelSize, double imageRatio){
        //holds top and lower panel
        JPanel centerPanel = QuestionFrame.createCenterPanel(destination);
        // holds top elements
        JPanel topPanel = QuestionFrame.createTopPanel(centerPanel);
        //holds lower elements
        JPanel lowerPanel = QuestionFrame.createLowerPanel(centerPanel);
        TitleFrame.createTitle(title, topPanel);
        JLabel labelText = QuestionFrame.createLabel(creditsDescription,labelSize);
        if (imagePath != null){
            ImageIcon image = QuestionFrame.getFixedDimensionImage(imagePath,imageRatio);
            labelText.setIcon(image);
        }
        labelText.setHorizontalTextPosition(JLabel.CENTER);
        labelText.setVerticalTextPosition(JLabel.BOTTOM);
        functionality.setSize(new Dimension(200,100));
        topPanel.add(labelText);
        lowerPanel.setLayout(new BoxLayout(lowerPanel,BoxLayout.X_AXIS));
        lowerPanel.add(functionality);
    }
    public void setVisible (boolean visible){
        creditsFrame.setVisible(visible);
    }
    public void dispose (){
        creditsFrame.dispose();
    }



    private  JFrame creditsFrame; //holds center panel
}
