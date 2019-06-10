package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sphinx.Test;
import sphinx.Test2;

import java.io.File;
import java.util.ArrayList;

public class ThreadTest extends Thread
{
    private ArrayList<String> timeFrames;
    private boolean isPlaying = false;
    private String filePath;
    private MediaPlayer mediaPlayer;

    public ThreadTest(ArrayList<String> timeFrames)
    {
        this.timeFrames = timeFrames;
    }

    public void run()
    {
        if(timeFrames.size() > 0)
        {
            timeFrames.clear();
        }
        //timeFrames = Test2.timeFrames;
        for(int i = 0; i < Test2.timeFrames.size(); i++)
        {
            timeFrames.add(Test2.timeFrames.get(i));
        }
        for(int i = 0; i < timeFrames.size(); i++)
        {
            System.out.println(timeFrames.get(i));
        }

        Media audio = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(audio);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::stopAudio);
        isPlaying = true;
        System.out.println("The audio is playing");
    }


    public void setFilePath(String path)
    {
        filePath = path;
    }

    private void stopAudio()
    {
        if(isPlaying)
        {
            mediaPlayer.stop();
            isPlaying = false;
            System.out.println("The audio is not playing");
        }
    }

    //public void clearTimeFrames()
    {
        //timeFrames.clear();
    }

}
