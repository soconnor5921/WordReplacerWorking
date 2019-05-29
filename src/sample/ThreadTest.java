package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sphinx.Test2;

import java.io.File;
import java.util.ArrayList;

public class ThreadTest extends Thread
{
    private ArrayList<String> timeFrames;
    private String filePath;
    private MediaPlayer mediaPlayer;

    public ThreadTest(ArrayList<String> timeFrames)
    {
        this.timeFrames = timeFrames;
    }

    public void run()
    {
        Media audio = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(audio);
        mediaPlayer.play();
        System.out.println("The audio is playing");
    }


    public void setFilePath(String path)
    {
        filePath = path;
    }

}
