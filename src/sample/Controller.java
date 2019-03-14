package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller
{
    @FXML
    private Button scanWords;

    public void callRecognizer()throws Exception
    {
        sphinx.Test2.recognize("test.wav", "zero");
    }


}
