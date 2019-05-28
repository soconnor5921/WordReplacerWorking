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
import sphinx.Test;
import sphinx.Test2;

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
    @FXML
    public Button censorAudio;

    private ArrayList<Word> listOfWords = new ArrayList<>();
    private ArrayList<String> timeFrames = new ArrayList<>();
    private FileChooser fileChooser = new FileChooser();
    private FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("WAV Files (*.wav)", "*.wav");
    private String filePath;
    private static MediaPlayer mediaPlayer;
    private static MediaPlayer mediaPlayer2;
    private Duration length;
    private boolean isPlaying = false;
    private boolean paused = false;
    public void callRecognizer()throws Exception
    {
        if(mediaPlayer != null)
        {
            report.setText("");
            for (int i = 0; i < listOfWords.size(); i++)
            {
                listOfWords.get(i).setCount(0);
            }
            sphinx.Test2.recognize(filePath, listOfWords);
            updateReport();
            //censorAudio.setVisible(true);
        }
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
        timeFrames.clear();
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
        if(paused)
        {
            mediaPlayer.setStartTime(length);
            mediaPlayer.play();
            paused = false;
        }
        else if(!isPlaying)
        {
            mediaPlayer.play();
            mediaPlayer.setOnEndOfMedia(this::stopAudio);
        }
        isPlaying = true;
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
            paused = false;
        }
    }

    /**

    public void playCensorSound()
    {
        Media audio = new Media(new File("censor.wav").toURI().toString());
        mediaPlayer2 = new MediaPlayer(audio);
        mediaPlayer2.play();
        mediaPlayer2.setOnEndOfMedia(this::setNextStartTime);
    }

    public void createTimeFrameList()
    {
        ArrayList<String> timeFrames = Test2.timeFrames;
        for(int i = 0; i < timeFrames.size(); i++)
        {
            this.timeFrames.add(timeFrames.get(i).split(":")[0]);
            this.timeFrames.add(timeFrames.get(i).split(":")[1]);
        }

    }

    public void playCensoredAudio()
    {
        Media audio = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(audio);

        Duration duration = new Duration(2000);
        mediaPlayer.setStopTime(duration);

        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::playCensorSound);
    }

    public void playAudioFromPoint(Duration start)
    {
        Media audio = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(audio);
        mediaPlayer.seek(start);
    }

    public void setNextStartTime()
    {
        Duration duration = new Duration(5000);
        mediaPlayer.setStartTime(duration);
        playCensoredAudio();
    }
     */
}