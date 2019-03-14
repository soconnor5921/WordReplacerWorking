package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import sphinx.Test2;

public class Controller
{
    @FXML
    private Button scanWords;

    @FXML
    public Text report;

    public void callRecognizer()throws Exception
    {
        sphinx.Test2.recognize("test.wav", "zero");
        updateReport("zero");
    }

    public void updateReport(String word)
    {
        report.setText(Test2.wordCount + " instances of the word '" + word + "' found.");
    }


}
