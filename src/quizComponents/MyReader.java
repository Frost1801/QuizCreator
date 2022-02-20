package quizComponents;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MyReader {

    public MyReader(){}

    public BufferedImage readImage(String imagePath){
        BufferedImage img;
        URL path = getClass().getResource(imagePath);
        if (path!= null){
            try {
                img = ImageIO.read(path);
                return img;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public String readTxtFile (String filePath) throws IOException {
        InputStream is = MyReader.class.getResourceAsStream(filePath);
        assert is != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder combined = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
        {
            combined.append(line);
        }
        reader.close();
        is.close();
        return combined.toString();
    }

}
