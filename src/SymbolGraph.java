/*************************************************************************
 *  Compilation:  javac SymbolGraph.java
 *  Execution:    java SymbolGraph filename.txt delimiter
 *  Dependencies: ST.java Graph.java In.java StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/routes.txt
 *                http://algs4.cs.princeton.edu/41undirected/movies.txt
 *                http://algs4.cs.princeton.edu/41undirected/moviestiny.txt
 *                http://algs4.cs.princeton.edu/41undirected/moviesG.txt
 *                http://algs4.cs.princeton.edu/41undirected/moviestopGrossing.txt
 *  
 *  %  java SymbolGraph routes.txt " "
 *  JFK
 *     MCO
 *     ATL
 *     ORD
 *  LAX
 *     PHX
 *     LAS
 *
 *  % java SymbolGraph movies.txt "/"
 *  Tin Men (1987)
 *     Hershey, Barbara
 *     Geppi, Cindy
 *     Jones, Kathy (II)
 *     Herr, Marcia
 *     ...
 *     Blumenfeld, Alan
 *     DeBoy, David
 *  Bacon, Kevin
 *     Woodsman, The (2004)
 *     Wild Things (1998)
 *     Where the Truth Lies (2005)
 *     Tremors (1990)
 *     ...
 *     Apollo 13 (1995)
 *     Animal House (1978)
 *
 * 
 *  Assumes that input file is encoded using UTF-8.
 *  % iconv -f ISO-8859-1 -t UTF-8 movies-iso8859.txt > movies.txt
 *
 *************************************************************************/


import java.util.*;

/**
 *  The <tt>SymbolGraph</tt> class represents an undirected graph, where the
 *  vertex names are arbitrary strings.
 *  By providing mappings between string vertex names and integers,
 *  it serves as a wrapper around the
 *  {@link Graph} data type, which assumes the vertex names are integers
 *  between 0 and <em>V</em> - 1.
 *  It also supports initializing a symbol graph from a file.
 *  <p>
 *  This implementation uses an {@link ST} to map from strings to integers,
 *  an array to map from integers to strings, and a {@link Graph} to store
 *  the underlying graph.
 *  The <em>index</em> and <em>contains</em> operations take time 
 *  proportional to log <em>V</em>, where <em>V</em> is the number of vertices.
 *  The <em>name</em> operation takes constant time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41undirected">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class SymbolGraph {
    private ST<String, Integer> st;  // string -> index
    private String[] keys;           // index  -> string
    private Graph G;
    private Map<String, List<String>> actorRecord;
    private Set<String> actorPool;
    private Set<String> moviePool;

    public Set<String> getActorPool() {
        return actorPool;
    }

    public Set<String> getMoviePool() {
        return moviePool;
    }

    /**
     * Initializes a graph from a file using the specified delimiter.
     * Each line in the file contains
     * the name of a vertex, followed by a list of the names
     * of the vertices adjacent to that vertex, separated by the delimiter.
     * @param filename the name of the file
     * @param delimiter the delimiter between fields
     */
    public SymbolGraph(String filename, String delimiter) {
        st = new ST<String, Integer>();
        actorRecord = new HashMap<>();
        actorPool = new HashSet<>();
        moviePool = new HashSet<>();


        // First pass builds the index by reading strings to associate
        // distinct strings with an index
        In in = new In(filename);
        // while (in.hasNextLine()) {
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(delimiter);
            String actor = "";
            List<String> movies = new ArrayList<>();
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i]))
                    st.put(a[i], st.size());
                actorPool.add(a[0]);
                actor = nameConversion(a[0]);
                if(i >= 1){
                    String movie = a[i];
                    movies.add(movie);
                    moviePool.add(movie);
                }
                actorRecord.put(actor, movies);
            }

        }
        StdOut.println("Done reading " + filename);

        // inverted index to get string keys in an array
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }

        // second pass builds the graph by connecting first vertex on each
        // line to all others
        G = new Graph(st.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = st.get(a[i]);
                G.addEdge(v, w);
            }
        }
    }

    /**
     * Does the graph contain the vertex named <tt>s</tt>?
     * @param s the name of a vertex
     * @return <tt>true</tt> if <tt>s</tt> is the name of a vertex, and <tt>false</tt> otherwise
     */
    public boolean contains(String s) {
        return st.contains(s);
    }

    /**
     * Returns the integer associated with the vertex named <tt>s</tt>.
     * @param s the name of a vertex
     * @return the integer (between 0 and <em>V</em> - 1) associated with the vertex named <tt>s</tt>
     */
    public int index(String s) {
        return st.get(s);
    }

    /**
     * Returns the name of the vertex associated with the integer <tt>v</tt>.
     * @param v the integer corresponding to a vertex (between 0 and <em>V</em> - 1) 
     * @return the name of the vertex associated with the integer <tt>v</tt>
     */
    public String name(int v) {
        return keys[v];
    }

    /**
     * Returns the graph assoicated with the symbol graph. It is the client's responsibility
     * not to mutate the graph.
     * @return the graph associated with the symbol graph
     */
    public Graph G() {
        return G;
    }

    // return actor with it's movie in the converted name fasion: FirstName LastName
    public Map<String, List<String>> getActorRecord(){ return actorRecord;}

    public String nameConversion(String s){
        String[] retArr = s.split("\\s*,\\s*");
        return retArr.length > 1 ? retArr[1].toLowerCase() + " " + retArr[0].toLowerCase() : retArr[0].toLowerCase();
    }


    //name conversion cannot be done two way
    public List<String> getTopActors(int n){
        List<Node> nodes = new ArrayList<>();
        for (String s : actorPool) {
            Node node = new Node(s, G().degree(st.get(s)));
            nodes.add(node);
        }
        List<String> ret = topK(n, nodes);
        return ret;
    }

    public List<String> getTopMovies(int n){
        List<Node> nodes = new ArrayList<>();
        for (String s : moviePool) {
            Node node = new Node(s, G().degree(st.get(s)));
            nodes.add(node);
        }
        List<String> ret = topK(n, nodes);
        return ret;
    }

    private List<String> topK(int k, List<Node> nodes){
        List<String> ret = new ArrayList<>();
        PriorityQueue<Node> pq = new PriorityQueue<Node>(k, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.getVal(), o2.getVal());
            }
        });
        int cnt = 0;
        for(Node node : nodes){
            if(cnt < k){
                pq.add(node);
                cnt++;
            }else{
                int curPeek = pq.peek().val;
                if(node.val > curPeek){
                    pq.poll();
                    pq.add(node);
                }
            }
        }
        while(!pq.isEmpty()){
            Node cur = pq.poll();
            ret.add(cur.s);
        }
        int left = 0, right = ret.size() - 1;
        while(left < right){
            String temp = ret.get(left);
            ret.set(left, ret.get(right));
            ret.set(right, temp);
            left++;
            right--;
        }
        return ret;
    }

    /**
     * Unit tests the <tt>SymbolGraph</tt> data type.
     */
//    public static void main(String[] args) {
//        String filename  = args[0];
//        String delimiter = args[1];
//        SymbolGraph sg = new SymbolGraph(filename, delimiter);
//        Graph G = sg.G();
//        while (StdIn.hasNextLine()) {
//            String source = StdIn.readLine();
//            if (sg.contains(source)) {
//                int s = sg.index(source);
//                for (int v : G.adj(s)) {
//                    StdOut.println("   " + sg.name(v));
//                }
//            }
//            else {
//                StdOut.println("input not contain '" + source + "'");
//            }
//        }
//    }
}
