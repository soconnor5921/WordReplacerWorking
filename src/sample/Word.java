package sample;

public class Word
{
    private String word;
    private int count;
    private int indexInHypothesis;

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

    public void setIndexInHypothesis(int num){indexInHypothesis = num;}

    public int getIndexInHypothesis(){return indexInHypothesis;}
}
