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
        mediaPlayer.setOnEndOfMedia(this::stopAudio);
        isPlaying = true;
        System.out.println("The audio is playing");

        while(isPlaying)
        {
            /*time = new Duration(startTimes.get(0));
            //if(mediaPlayer.getCurrentTime().equals(new Duration(startTimes.get(0))))
            if(mediaPlayer.getCurrentTime().compareTo(new Duration(startTimes.get(0).doubleValue())) == 0)
            {
                stopAudio();
                censor.play();
                censor.setOnEndOfMedia(() -> {
                    censor.stop();
                    continueAudio(new Duration(endTimes.get(0)));
                });
            }
            startTimes.remove(0);
            endTimes.remove(0);*/

        }
        for(int i = 0; i < startTimes.size(); i++)
        {
            mediaPlayer.setStopTime(new Duration(startTimes.get(i)));
        }

        for(int i = 0; i < startTimes.size(); i++)
        {
            System.out.println(startTimes.get(i));
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

    public void continueAudio(Duration seek)
    {
        mediaPlayer.seek(seek);
        mediaPlayer.play();
    }

}
