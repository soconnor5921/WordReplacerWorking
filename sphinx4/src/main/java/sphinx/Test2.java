package sphinx;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import sample.Controller;

public class Test2
{
    public static int wordCount = 0;

    public static void recognize(String pathname, ArrayList<String> words)throws Exception
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

            removeWords2(words, result);
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

    public static void removeWords2(ArrayList<String> words, SpeechResult result)
    {
        String hypothesis = result.getHypothesis();
        for(int i = 0; i < words.size(); i++)
        {
            if(hypothesis.contains(words.get(i)))
            {
                while(hypothesis.contains(words.get(i)))
                {
                    hypothesis = hypothesis.substring(0, hypothesis.indexOf(words.get(i))) + "REDACTED" + hypothesis.substring(hypothesis.indexOf(words.get(i)) + words.get(i).length());
                    wordCount++;
                }
                System.out.println(hypothesis);
            }
            else
            {
                System.out.println("The hypothesis does not contain the word " + words.get(i));
            }
        }
    }
}
