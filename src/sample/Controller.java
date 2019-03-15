package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sphinx.Test2;

import java.util.ArrayList;
import java.util.List;

public class Controller
{
    @FXML
    public Text report;

    @FXML
    public TextField wordInput;

    @FXML
    public Text wordList;

    public ArrayList<String> listOfWords = new ArrayList<>();

    public void callRecognizer()throws Exception
    {
        sphinx.Test2.recognize("test.wav", listOfWords);
        updateReport("zero");
    }

    public void updateReport(String word)
    {
        report.setText(Test2.wordCount + " instances of the inputted words found.");
    }

    public void getInputtedWord()
    {
        String word = wordInput.getText();
        String list = wordList.getText();
        list += "\n-" + word;
        wordList.setText(list);
        wordInput.setText("");
        listOfWords.add(word);
    }

    public void clearWordList()
    {
        wordList.setText("List Of Words");
        listOfWords.clear();
    }


}
