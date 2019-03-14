package sphinx;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import sample.Controller;

public class Test2
{
    public static int wordCount = 0;

    public static void recognize(String pathname, String word)throws Exception
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

            removeWords(word, result);
        }
    }

    public static void removeWords(String word, SpeechResult result)
    {
        String hypothesis = result.getHypothesis();
        if(hypothesis.contains(word))
        {
            while(hypothesis.contains(word))
            {
                hypothesis = hypothesis.substring(0, hypothesis.indexOf(word)) + "REDACTED" + hypothesis.substring(hypothesis.indexOf(word) + word.length());
                wordCount++;
            }
            System.out.println(hypothesis);
        }
        else
        {
            System.out.println("The hypothesis does not contain the word " + word);
        }
    }
}
