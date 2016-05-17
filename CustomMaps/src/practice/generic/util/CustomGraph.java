package practice.generic.util;

/**
 * Created by arindam.das on 17/05/16.
 */
// A Java program to find bridges in a given undirected graph
import com.sun.tools.javac.util.Pair;

import java.util.*;
import java.util.LinkedList;

// This class represents a undirected graph using adjacency list
// representation
public class CustomGraph {
    private int n;   // No. of vertices

    // Array  of lists for Adjacency List Representation
    private LinkedList<Integer> adj[];
    int time = 0;
    static final int NIL = -1;

    private boolean[] aps;
    private List<Pair> bdgs;

    // Constructor
    public CustomGraph(int n) {
        this.n = n;
        adj = new LinkedList[n];
        for (int i = 0; i < n; ++i)
            adj[i] = new LinkedList();
    }

    // Function to add an edge into the graph
    public void addEdge(int v, int w) {
        adj[v].add(w);  // Add w to v's list.
        adj[w].add(v);  //Add v to w's list
    }

    // A recursive function that finds and prints bridges
    // using DFS traversal
    // u --> The vertex to be visited next
    // visited[] --> keeps tract of visited vertices
    // disc[] --> Stores discovery times of visited vertices
    // parent[] --> Stores parent vertices in DFS tree
    void bridgeUtil(int u, boolean visited[], int disc[],
                    int low[], int parent[], List<Pair> bridges) {

        // Count of children in DFS Tree
        int children = 0;

        // Mark the current node as visited
        visited[u] = true;

        // Initialize discovery time and low value
        disc[u] = low[u] = ++time;

        // Go through all vertices aadjacent to this
        Iterator<Integer> i = adj[u].iterator();
        while (i.hasNext()) {
            int v = i.next();  // v is current adjacent of u

            // If v is not visited yet, then make it a child
            // of u in DFS tree and recur for it.
            // If v is not visited yet, then recur for it
            if (!visited[v]) {
                parent[v] = u;
                bridgeUtil(v, visited, disc, low, parent, bridges);

                // Check if the subtree rooted with v has a
                // connection to one of the ancestors of u
                low[u] = Math.min(low[u], low[v]);

                // If the lowest vertex reachable from subtree
                // under v is below u in DFS tree, then u-v is
                // a bridge
                if (low[v] > disc[u]){
                    bridges.add(Pair.of(u, v));
                }

            }

            // Update low value of u for parent function calls.
            else if (v != parent[u])
                low[u] = Math.min(low[u], disc[v]);
        }
    }


    // DFS based function to find all bridges. It uses recursive
    // function bridgeUtil()
    public List<Pair> bridges() {
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[n];
        int disc[] = new int[n];
        int low[] = new int[n];
        int parent[] = new int[n];
        List<Pair> bridges = new ArrayList<>();


        // Initialize parent and visited, and ap(articulation point)
        // arrays
        for (int i = 0; i < n; i++) {
            parent[i] = NIL;
            visited[i] = false;
        }

        // Call the recursive helper function to find Bridges
        // in DFS tree rooted with vertex 'i'
        for (int i = 0; i < n; i++)
            if (visited[i] == false)
                bridgeUtil(i, visited, disc, low, parent, bridges);

        return bridges;
    }

    // A recursive function that find articulation points using DFS
    // u --> The vertex to be visited next
    // visited[] --> keeps tract of visited vertices
    // disc[] --> Stores discovery times of visited vertices
    // parent[] --> Stores parent vertices in DFS tree
    // ap[] --> Store articulation points
    void articulationPointsUtil(int u, boolean visited[], int disc[],
                                int low[], int parent[], boolean ap[])
    {

        // Count of children in DFS Tree
        int children = 0;

        // Mark the current node as visited
        visited[u] = true;

        // Initialize discovery time and low value
        disc[u] = low[u] = ++time;

        // Go through all vertices aadjacent to this
        Iterator<Integer> i = adj[u].iterator();
        while (i.hasNext())
        {
            int v = i.next();  // v is current adjacent of u

            // If v is not visited yet, then make it a child of u
            // in DFS tree and recur for it
            if (!visited[v])
            {
                children++;
                parent[v] = u;
                articulationPointsUtil(v, visited, disc, low, parent, ap);

                // Check if the subtree rooted with v has a connection to
                // one of the ancestors of u
                low[u]  = Math.min(low[u], low[v]);

                // u is an articulation point in following cases

                // (1) u is root of DFS tree and has two or more chilren.
                if (parent[u] == NIL && children > 1)
                    ap[u] = true;

                // (2) If u is not root and low value of one of its child
                // is more than discovery value of u.
                if (parent[u] != NIL && low[v] >= disc[u])
                    ap[u] = true;
            }

            // Update low value of u for parent function calls.
            else if (v != parent[u])
                low[u]  = Math.min(low[u], disc[v]);
        }
    }

    // The function to do DFS traversal. It uses recursive function articulationPointsUtil()
    public boolean[] articulationPoints()
    {
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[n];
        int disc[] = new int[n];
        int low[] = new int[n];
        int parent[] = new int[n];
        boolean ap[] = new boolean[n]; // To store articulation points

        // Initialize parent and visited, and ap(articulation point)
        // arrays
        for (int i = 0; i < n; i++)
        {
            parent[i] = NIL;
            visited[i] = false;
            ap[i] = false;
        }

        // Call the recursive helper function to find articulation
        // points in DFS tree rooted with vertex 'i'
        for (int i = 0; i < n; i++)
            if (visited[i] == false)
                articulationPointsUtil(i, visited, disc, low, parent, ap);

        // Now ap[] contains articulation points, print them
        return ap;
    }

    void computeConnectivityUtil(int u, int disc[], int low[], int parent[], boolean[] visited){
        visited[u] = true;
        low[u] = disc[u] = ++time;
        int children = 0;   //AP exclusive
        for(Integer v : adj[u]){
            if(!visited[v]){
                children++; //AP exclusive
                parent[v] = u;
                computeConnectivityUtil(v, disc, low, parent, visited);
                low[u] = Math.min(low[u], low[v]);
                //AP exclusive
                if(parent[u] == NIL && children > 1){
                    aps[u] = true;
                }else if(parent[u] != NIL && low[v] >= disc[u]){
                    aps[u] = true;
                }
                //AP exclusive
                //Bridge exclusive
                if(low[v] > disc[u]){
                    bdgs.add(Pair.of(u, v));
                }
                //Bridge exclusive
            }else if(v!=parent[u]){
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

    void computeConnectivity(){
        aps = new boolean[n];
        bdgs = new ArrayList<>();
        int disc[] = new int[n];
        int low[] = new int[n];
        int parent[] = new int[n];
        boolean[] visited = new boolean[n];
        for(int i = 0; i<n; i++ ){
            parent[i] = NIL;
            visited[i] = false;
            aps[i] = false;
        }
        for(int i = 0; i<n; i++){
            if(!visited[i]){
                computeConnectivityUtil(i, disc, low, parent, visited);
            }
        }
    }

    public boolean[] getArticulationPoints(){
        if(aps == null){
            computeConnectivity();
        }
        return aps;
    }

    public List<Pair> getBridges(){
        if(bdgs == null){
            computeConnectivity();
        }
        return bdgs;
    }
}
