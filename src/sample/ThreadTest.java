package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sphinx.Test;
import sphinx.Test2;


import java.io.File;
import javafx.util.Duration;
import java.util.ArrayList;

public class ThreadTest extends Thread
{
    private ArrayList<String> timeFrames;

    private ArrayList<Double> startTimes;
    private ArrayList<Double> endTimes;

    private boolean isPlaying = false;
    private String filePath;
    private MediaPlayer mediaPlayer;
    private MediaPlayer censor;
    private Duration time;
    private int num = -1;

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

        Media censorSound = new Media(new File("censor.wav").toURI().toString());
        censor = new MediaPlayer(censorSound);

        getTimeList();

        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::censor);
        isPlaying = true;
        System.out.println("The audio is playing");

        for(int i = 0; i < endTimes.size(); i++)
        {
            num++;
            mediaPlayer.setStopTime(new Duration(startTimes.get(i)));
        }
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

    public void getTimeList()
    {
        startTimes = new ArrayList<>();
        endTimes = new ArrayList<>();
        for(int i = 0; i < timeFrames.size(); i++)
        {
            String[] arr = timeFrames.get(i).split(":");
            startTimes.add(new Double(arr[0]));
            endTimes.add(new Double(arr[1]));
        }
    }

    public void censor()
    {
        censor.play();
        censor.setOnEndOfMedia(this::continueCensor);
    }

    public void continueCensor()
    {
        Media audio = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(audio);
        //mediaPlayer.seek(new Duration(endTimes.get(0) + 1000));
        mediaPlayer.setStartTime(new Duration(endTimes.get(num)));
        mediaPlayer.play();
    }

    public void clearNum()
    {
        num = -1;
    }

}
