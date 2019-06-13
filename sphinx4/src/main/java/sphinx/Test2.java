package sphinx;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;
import edu.cmu.sphinx.util.TimeFrame;
import sample.Word;

public class Test2
{
    public static ArrayList<String> timeFrames = new ArrayList<>();
    public static ArrayList<TimeFrame> allTimeFrames = new ArrayList<>();
    public static void recognize(String pathname, ArrayList<Word> words)throws Exception
    {
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File(pathname));

        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis());

            ArrayList<Integer> indexes = findWords(words, result);
            printTimeFrames(indexes, result);
        }
    }

    public static ArrayList<Integer> findWords(ArrayList<Word> words, SpeechResult result)
    {
        String hypothesis = result.getHypothesis();
        String[] allWords = hypothesis.split(" ");
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0; i < words.size(); i++)
        {
            for(int j = 0; j < allWords.length; j++)
            {
                if(words.get(i).getWord().equalsIgnoreCase(allWords[j]))
                {
                    words.get(i).addOneToCount();
                    indexes.add(j);
                }
            }
        }
        return indexes;
    }

    /*public static void getTimeFrames(ArrayList<Integer> indexes, SpeechResult result)
    {
        //ArrayList<TimeFrame> allTimeFrames = new ArrayList<>();
        for(WordResult r : result.getWords())
        {
            allTimeFrames.add(r.getTimeFrame());
        }
        for(int i = 0; i < indexes.size(); i++)
        {
            timeFrames.add(allTimeFrames.get(indexes.get(i)).toString());
            System.out.println(timeFrames.get(i));
        }
        //printTimeFrames(result);
    }*/

    public static void printTimeFrames(ArrayList<Integer> indexes, SpeechResult result)
    {
        List<WordResult> list = result.getWords();
        for(int i = 0; i < list.size(); i++)
        {
            String str = list.get(i).toString();
            if(str.substring(1,6).equals("<sil>") || str.substring(1,9).equals("[SPEECH]"))
            {
                list.remove(i);
                i--;
            }
            else
            {
                System.out.println(list.get(i).toString());
            }
        }
        for(int i = 0; i < indexes.size(); i++)
        {
            timeFrames.add(list.get(indexes.get(i)).getTimeFrame().toString());
            System.out.println(timeFrames.get(i));
        }

    }
}
