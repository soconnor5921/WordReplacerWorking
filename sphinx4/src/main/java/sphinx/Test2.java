package sphinx;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

            findWords(words, result);
        }
    }

    public static void findWords(ArrayList<Word> words, SpeechResult result)
    {
        String hypothesis = result.getHypothesis();
        String[] allWords = hypothesis.split(" ");
        for(int i = 0; i < words.size(); i++)
        {
            for(int j = 0; j < allWords.length; j++)
            {
                if(words.get(i).getWord().equalsIgnoreCase(allWords[j]))
                {
                    words.get(i).addOneToCount();
                }
            }
        }
    }
}
