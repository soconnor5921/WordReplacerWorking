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
        for (WordResult r : result.getWords())
        {
            System.out.println(r);
            for(int i = 0; i < words.size(); i++)
            {
                if(words.get(i).getWord().equalsIgnoreCase(r.getWord().getSpelling()))
                {
                    words.get(i).addOneToCount();
                }
            }
        }
    }

    /**
     public static void removeWords(ArrayList<Word> words, SpeechResult result)
     {
     String hypothesis = result.getHypothesis();
     String currentWord;
     for(int i = 0; i < words.size(); i++)
     {
     currentWord = words.get(i).getWord();
     if(hypothesis.contains(currentWord))
     {
     while(hypothesis.contains(currentWord))
     {
     hypothesis = hypothesis.substring(0, hypothesis.indexOf(currentWord)) + "REDACTED" + hypothesis.substring(hypothesis.indexOf(currentWord) + currentWord.length());
     words.get(i).addOneToCount();
     }
     System.out.println(hypothesis);
     }
     else
     {
     System.out.println("The hypothesis does not contain the word " + words.get(i).getWord());
     }
     }
     }*/
}
