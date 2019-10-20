import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * CS 311 Lab 2: Graphs, Part I
 * <p>
 * This lab reads in a text file containing actors and actresses and the
 * movies they have starred in, and creates an undirected graph representing
 * these relationships.
 * <p>
 * After building the graph, information about it can be obtained by calling
 * the static methods below.  In addition, simple searches may be performed.
 *
 * @author Your Name Here
 */

public class Main {
    /**
     * Entry-point method.  You must call this before invoking any of the other
     * methods.
     */
    public static SymbolGraph symbolGraph;
    public static List<Colleagues> colleagues = new ArrayList<>();


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please pass this program the file to read, like this:");
            System.out.println("\tjava Main movies_2000.txt");
            System.out.println("or\n\t java Main movies_2000.txt print");
            System.out.println("if you want to print a report");
            System.exit(1);
        }

        String infile = args[0];
        System.out.println("Reading actors/actresses/movies from: " + infile);

        // Do what you need to do in order to have everything ready for the
        // methods that follow
        symbolGraph = new SymbolGraph(infile, "[|]");
        // Print a report
        if (args.length == 2 && args[1].equals("print")) {
            System.out.println("----------------------------------");
            System.out.printf("File %s contains:\n", infile);
            System.out.printf("\t%d vertices\n\t%d edges\n", getNumberOfVertices(), getNumberOfEdges());
            System.out.println("Sean Connery has acted in the following movies:");
            for (String movie : getMovies("Sean Connery")) {
                System.out.println("\t" + movie);
            }
            System.out.println("The top 10 performers who have starred in the most movies are:");
            for (String a : getTopPerformers(10)) {
                System.out.println("\t" + a);
            }
            System.out.println("The top 10 movies that have the most performers are:");
            for (String a : getTopMovies(10)) {
                System.out.println("\t" + a);
            }
            System.out.println("----------------------------------");
        }
    }

    private static void readFile(String path) throws FileNotFoundException {
        System.out.println("Reading file!");
        File file = new File(path);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine())
            System.out.println(sc.nextLine());
    }


    /**
     * Get the total number of vertices in the graph.
     *
     * @return |V|, which includes both movies and performers
     */
    public static int getNumberOfVertices() {
        return symbolGraph.G().V();
    }

    /**
     * Get the total number of edges in the graph.
     *
     * @return |E|, for which every edge connnects a performer with a movie
     */
    public static int getNumberOfEdges() {
        return symbolGraph.G().E();
    }

    /**
     * Get a list of all the movies that the given actress or actor has acted in.
     *
     * @param performer A string representing the performer to search for.
     *                  An exact match is NOT required.  Furthermore this
     *                  string is assumed to be in First Last format. e.g.
     *                  "Sean Connery" or "sean connery".  You may use the
     *                  first actor for which this match is found.
     * @return A list of the movies the performer has acted in, sorted alphabetically.
     */
    public static List<String> getMovies(String performer) {
        performer = performer.toLowerCase();
        List<String> ret = symbolGraph.getActorRecord().get(performer);
        Collections.sort(ret, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return ret;
    }

    /**
     * Get a list of the top performers, according to how many movies they have acted in.
     *
     * @param n Run this search for the top n performers.
     * @return A list of performers, ranked (sorted) by how many movies they have acted in.
     * The first entry in this list is the actress or actor who has starred in
     * the most movies.
     */
    public static List<String> getTopPerformers(int n) {
        return symbolGraph.getTopActors(n);
    }

    /**
     * Get a list of the top movies, according to how many performers they have.
     *
     * @param n Run this search for the top n movies.
     * @return A list of movies, ranked (sorted) by how many performers they have.
     * The first entry in this list is the movie with the most performers.
     */
    public static List<String> getTopMovies(int n) {
        return symbolGraph.getTopMovies(n);
    }

    /**
     * Find the Kevin Bacon number for actor2 assuming actor1 is the starting point. * To find the actual KB# for actor
     * Harvey Keitel, for example, you would call: * getKBNumber( "Kevin Bacon", "Harvey Keitel", true );
     * * * To find the DH# for Kate Winslet you would do this: * getKBNumber( "Dennis Hopper", "Kate Winslet", true );
     * * * To use the other name format you would do this: * getKBNumber( "Hopper, Dennis", "Lawrence, Jennifer (III)", false )
     * * * As you can see the latter format allows you to define exactly which person * you mean to search for.
     * * * @param actor1 The starting actor, assumed to be in first last format
     * * @param actor2 The ending actor, assumed to be in first last format
     * * @param commonNameFormat true means that the names passed in for the actors
     * * is in First Last format, false means the names are assumed to be in the original Last,
     * First format * @return A list of movies that connects actor1 to actor2 and that is the *
     * shortest path in the graph that connects such actors. The length of
     * * this list is the KB#. getKBNumber( "Kevin Bacon", "Kevin Bacon" )
     * * should return an empty list giving a KB# of 0
     * * @throws ActorNotFoundException Thrown to indicate that one or both of
     * * the actors could not be found.
     * * @throws PathNotFoundException Thrown to indicate that a path could not * be found between the two actors. *
     */
    public static List<String> getKBNumber(String actor1, String actor2, boolean commonNameFormat) throws ActorNotFoundException, PathNotFoundException {
        if(!(symbolGraph.getActorPool().contains(actor1) && symbolGraph.getActorPool().contains(actor2))){
            throw new ActorNotFoundException("Not both actors are in the records");
        }
        if(actor1.equals(actor2)) return new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        boolean findConn = false;
        List<String> ret = new ArrayList<>();
        Queue<TraceNode> q = new LinkedList<>();
        int startIndex = symbolGraph.index(actor1);
        int endIndex = symbolGraph.index(actor2);
        List<Integer> list = new ArrayList<>();
        list.add(startIndex);
        TraceNode node = new TraceNode(startIndex, new ArrayList<>(list));
        q.offer(node);//start as actor
        visited.add(startIndex);
        TraceNode finalNode = new TraceNode();
        while(!q.isEmpty()){
            int size = q.size();
            for(int i = 0; i < size; i++){
                TraceNode cur = q.poll();//cur: start actor
                if(cur.val == endIndex){
                    findConn = true;
                    finalNode = cur;
                    break;
                }
                for(int nei: symbolGraph.G().adj(cur.val)){
                    if(!visited.contains(nei)){
                        List<Integer> traceList = new ArrayList<>(cur.track);
                        traceList.add(nei);
                        TraceNode traceNode = new TraceNode(nei, new ArrayList<>(traceList));
                        q.offer(traceNode);
                    }//cur actor's all movie
                    visited.add(nei);
                }
            }
        }
        if(!findConn){
            throw new PathNotFoundException("No given connection between the two actor");
        }
        ret = extractFinalNode(finalNode);
        return ret;
    }

    private static List<String> extractFinalNode(TraceNode finalNode) {
        List<String> ret = new ArrayList<>();
        int index = 1;
        List<Integer> trace = finalNode.getTrack();
        while(index < trace.size()){
            ret.add(symbolGraph.name(trace.get(index)));
            index += 2;
        }
        //System.out.println(ret);
        return ret;
    }


    /**
     * Find the two actors who have starred in the most movies together. If there * are ties, return all tying pairs. *
     * * @return A list of Colleagues objects, that represent the pairs of actors * who have the most number of movies in common.
     * The actors must be * distinct (i.e. different). The list is unordered, but will all
     * * have the same count of movies in common.
     */
    public static List<Colleagues> findTopActorPairings() {
        return generatePair();

    }

    private static List<Colleagues> generatePair(){
        Map<Integer, Colleagues> map = new HashMap<>();
        for(String movie: symbolGraph.getMoviePool()){
            Iterable<Integer> actors = symbolGraph.G().adj(symbolGraph.index(movie));
            readPair(actors, map);
        }
        List<Colleagues> colleaguesList = new ArrayList<>();
        for(int key: map.keySet()){
            colleaguesList.add(map.get(key));
        }
        Collections.sort(colleaguesList, Colleagues::compareTo);
        List<Colleagues> ret = new ArrayList<>();
        int max = colleaguesList.get(0).numMoviesShared;
        for(int i = 0; i < colleaguesList.size(); i++){
            if(colleaguesList.get(i).numMoviesShared == max){
                ret.add(colleaguesList.get(i));
            }else break;
        }
        return ret;
    }

    private static void readPair(Iterable<Integer> actors, Map<Integer, Colleagues> map) {
        List<Integer> list = new ArrayList<>();
        for(int i: actors){
            list.add(i);
        }
        for(int i = 0; i < list.size(); i++){
            String actor1 = symbolGraph.name(list.get(i));
            for(int j = i + 1; j < list.size(); j++){
                String actor2 = symbolGraph.name(list.get(j));
                Colleagues colleagues = new Colleagues(actor1, actor2, 0);
                Colleagues colleagueToBeUpdated = map.getOrDefault(colleagues.hashCode(), colleagues);
                colleagueToBeUpdated.numMoviesShared += 1;
                map.put(colleagues.hashCode(), colleagueToBeUpdated);
            }
        }
    }

} // end class Main
