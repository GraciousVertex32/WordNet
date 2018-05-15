import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class WordNet
{
    private final List<Synset> list = new ArrayList<>();
    private final Digraph digraph;
    private final SAP sap;
    private final List<String> allnoun = new ArrayList<>();
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        // store all information in a list
        String[] templine;
        int tempid;
        String tempsynonyms;
        String tempdefinition;
        In in = new In(synsets);
        while (in.hasNextLine())
        {
            templine = in.readLine().split(",");
            tempid = Integer.parseInt(templine[0]);
            tempsynonyms = templine[1];
            tempdefinition = templine[2];
            Synset synset = new Synset(tempid, tempsynonyms, tempdefinition);
            list.add(synset);
            allnoun.addAll(synset.nouns());
        }
        // build structures for synsets
        digraph = new Digraph(list.size());
        In in2 = new In(hypernyms);
        while (in2.hasNextLine())
        {
            String[] temp = in2.readLine().split(",");
            for (int i = 1; i < temp.length; i++)
            {
                digraph.addEdge(Integer.parseInt(temp[0]), Integer.parseInt(temp[i]));
            }
        }
        // build sap for this
        sap = new SAP(digraph);
    }
    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return allnoun;
    }
    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        if (allnoun.contains(word))
        {
            return true;
        }
        return false;
    }
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        int aid = 0;
        int bid = 0;
        boolean geta = false;
        boolean getb = false;
        for (Synset synset : list)
        {
            for (String noun : synset.nouns())
            {
                if (noun.compareTo(nounA) == 0)
                {
                    aid = synset.getId();
                    geta = true;
                }
                if (noun.compareTo(nounB) == 0)
                {
                    bid = synset.getId();
                    getb = true;
                }
            }
            if (geta && getb)
            {
                return sap.length(aid, bid);
            }
        }
        throw new java.lang.IllegalArgumentException();
    }
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        int aid = 0;
        int bid = 0;
        boolean geta = false;
        boolean getb = false;
        for (Synset synset : list)
        {
            for (String noun : synset.nouns())
            {
                if (noun.compareTo(nounA) == 0)
                {
                    aid = synset.getId();
                    geta = true;
                }
                if (noun.compareTo(nounB) == 0)
                {
                    bid = synset.getId();
                    getb = true;
                }
            }
            if (geta && getb)
            {
                break;
            }
        }
        if (!geta || !getb)
        {
            throw new java.lang.IllegalArgumentException();
        }
        int id = sap.ancestor(aid, bid);
        for (Synset s : list)
        {
            if (s.getId() == id)
            {
                String str = "";
                for (String noun : s.nouns())
                {
                    str = str + noun + " ";
                }
                return str.substring(0,str.length()-1);
            }
        }
        throw new IllegalArgumentException("cant find the synset");
    }
    // do unit testing of this class
    public static void main(String[] args)
    {
        WordNet w = new WordNet("testa.txt", "testb.txt");
        System.out.println(w.distance("word4", "word19"));
        System.out.println(w.isNoun("2word0"));
    }
    private final class Synset
    {
        private final int id;
        private final List<String> synonyms = new ArrayList<>();
        private final String definition;
        public Synset(int id, String uss, String definition)
        {
            String[] temp;
            this.id = id;
            this.definition = definition;
            temp = uss.split(" ");
            synonyms.addAll(Arrays.asList(temp));
        }
        public int getId()
        {
            return id;
        }
        public List<String> nouns()
        {
            return synonyms;
        }
        public String getDefinition()
        {
            return definition;
        }
    }
}
