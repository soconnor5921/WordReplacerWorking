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

    public ArrayList<Word> listOfWords = new ArrayList<>();

    public void callRecognizer()throws Exception
    {
        report.setText("");
        for(int i = 0; i < listOfWords.size(); i++)
        {
            listOfWords.get(i).setCount(0);
        }
        sphinx.Test2.recognize("test2.wav", listOfWords);
        updateReport();
    }

    public void updateReport()
    {
        String newReport = "";
        for(int i = 0; i < listOfWords.size(); i++)
        {
            newReport += "-" + listOfWords.get(i).getCount() + " instances of the word '" + listOfWords.get(i).getWord() + "' found.\n";
        }
        report.setText(newReport);
    }

    public void getInputtedWord()
    {
        String word = wordInput.getText();
        String list = wordList.getText();
        list += "\n-" + word;
        wordList.setText(list);
        wordInput.setText("");

        Word newWord = new Word(" "+word+" ");

        listOfWords.add(newWord);
    }

    public void clearWordList()
    {
        wordList.setText("List Of Words");
        listOfWords.clear();
    }


}
