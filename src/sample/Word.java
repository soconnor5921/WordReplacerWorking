package sample;

public class Word
{
    private String word;
    private int count;

    public Word(String word)
    {
        this.word = word;
    }

    public String getWord()
    {
        return word;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int num){count = num;}

    public void addOneToCount()
    {
        count++;
    }
}
