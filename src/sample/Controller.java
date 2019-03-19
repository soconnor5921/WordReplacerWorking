package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sphinx.Test2;

import java.io.File;
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

    @FXML
    public Label fileLabel;

    @FXML
    public Button playAudio;

    private ArrayList<Word> listOfWords = new ArrayList<>();
    private FileChooser fileChooser = new FileChooser();
    private FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("WAV Files (*.wav)", "*.wav");
    private String filePath;

    public void callRecognizer()throws Exception
    {
        report.setText("");
        for(int i = 0; i < listOfWords.size(); i++)
        {
            listOfWords.get(i).setCount(0);
        }
        sphinx.Test2.recognize(filePath, listOfWords);
        updateReport();
    }

    private void updateReport()
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

        Word newWord = new Word(word);

        listOfWords.add(newWord);
    }

    public void clearWordList()
    {
        wordList.setText("List Of Words");
        listOfWords.clear();
    }

    public void openFileChooser()
    {
        fileChooser.getExtensionFilters().add(extensionFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        fileLabel.setText(selectedFile.getPath());
        filePath = selectedFile.getPath();
        playAudio.setVisible(true);
    }

    public void playAudio()
    {
        Media audio = new Media(new File(filePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(audio);
        mediaPlayer.play();
    }
}
