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
import java.io.File;
import javafx.util.Duration;
import java.util.ArrayList;

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
    @FXML
    public Button pauseButton;
    @FXML
    public Button stopButton;

    private ArrayList<Word> listOfWords = new ArrayList<>();
    private FileChooser fileChooser = new FileChooser();
    private FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("WAV Files (*.wav)", "*.wav");
    private String filePath;
    private MediaPlayer mediaPlayer;
    private Duration length;
    private boolean isPlaying = false;
    private boolean paused = false;

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
        pauseButton.setVisible(true);
        stopButton.setVisible(true);
    }

    public void playAudio()
    {
        Media audio = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(audio);
        isPlaying = true;
        if(paused)
        {
            mediaPlayer.setStartTime(length);
            mediaPlayer.play();
            paused = false;
        }
        else
        {
            mediaPlayer.play();
        }
    }

    public void pauseAudio()
    {
        if(isPlaying)
        {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentTime();
            paused = true;
        }
    }

    public void stopAudio()
    {
        if(isPlaying)
        {
            mediaPlayer.stop();
            isPlaying = false;
        }
    }
}
