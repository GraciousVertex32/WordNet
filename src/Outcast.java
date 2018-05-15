import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class Outcast
{
    private final WordNet wordnet;
    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        this.wordnet = wordnet;
    }
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        int distance[] = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++)
        {
            for (int j = 0; j < nouns.length; j++)
            {
                distance[i] = distance[i] + wordnet.distance(nouns[i], nouns[j]);
            }
        }
        int longest = 0;
        String out = null;
        for (int x = 0; x < distance.length; x ++ )
        {
            if (distance[x] > longest)
            {
                longest = distance[x];
                out = nouns[x];
            }
        }
        return out;
    }
    public static void main(String[] args)
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++)
        {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
