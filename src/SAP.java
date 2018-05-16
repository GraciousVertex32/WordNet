import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
public final class SAP
{
    private final Digraph digraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        this.digraph = new Digraph(G);
    }
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        if (v > digraph.V() - 1 || w > digraph.V() - 1 || v < 0 || w < 0)
        {
            throw new IllegalArgumentException();
        }
        if (v == w)
        {
            return 0;
        }
        BFS bfs = new BFS(digraph, v);
        BFS bfs2 = new BFS(digraph, w);
        int length = Integer.MAX_VALUE;
        for (int vertex : bfs.checked())
        {
            if (bfs2.checked().contains(vertex))
            {
                int distance = bfs.distTo(vertex) + bfs2.distTo(vertex);
                if (distance < length)
                {
                    length = distance;
                }
            }
        }
        if (length == Integer.MAX_VALUE)
        {
            return -1;
        }
        return length;
    }
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
        if (v > digraph.V() - 1 || w > digraph.V() - 1 || v < 0 || w < 0)
        {
            throw new IllegalArgumentException();
        }
        if (v == w)
        {
            return v;
        }
        BFS bfs = new BFS(digraph, v);
        BFS bfs2 = new BFS(digraph, w);
        int length = Integer.MAX_VALUE;
        int shortestvertex = Integer.MAX_VALUE;
        for (int vertex : bfs.checked())
        {
            if (bfs2.checked().contains(vertex))
            {
                int distance = bfs.distTo(vertex) + bfs2.distTo(vertex);
                if (distance < length)
                {
                    length = distance;
                    shortestvertex = vertex;
                }
            }
        }
        if (shortestvertex == Integer.MAX_VALUE)
        {
            return -1;
        }
        return shortestvertex;
    }
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null)
        {
            throw new IllegalArgumentException();
        }
        if (v == w)
        {
            return 0;
        }
        BFS bfs = new BFS(digraph, v);
        BFS bfs2 = new BFS(digraph, w);
        int length = Integer.MAX_VALUE;
        for (int vertex : bfs.checked())
        {
            if (bfs2.checked().contains(vertex))
            {
                int distance = bfs.distTo(vertex) + bfs2.distTo(vertex);
                if (distance < length)
                {
                    length = distance;
                }
            }
        }
        if (length == Integer.MAX_VALUE)
        {
            return -1;
        }
        return length;
    }
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null)
        {
            throw new IllegalArgumentException();
        }
        BFS bfs = new BFS(digraph, v);
        BFS bfs2 = new BFS(digraph, w);
        int length = Integer.MAX_VALUE;
        int shortestvertex = Integer.MAX_VALUE;
        for (int vertex : bfs.checked())
        {
            if (bfs2.checked().contains(vertex))
            {
                int distance = bfs.distTo(vertex) + bfs2.distTo(vertex);
                if (distance < length)
                {
                    length = distance;
                    shortestvertex = vertex;
                }
            }
        }
        if (shortestvertex == Integer.MAX_VALUE)
        {
            return -1;
        }
        return shortestvertex;
    }
    // do unit testing of this class
    public static void main(String[] args)
    {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty())
        {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        System.out.println("?");
    }
}
