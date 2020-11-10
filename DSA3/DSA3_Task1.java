//package DSA3;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

// Parth Kalkar, BS-19, DSA_Assignment_3_Task_1

// Submission link : https://codeforces.com/group/3ZU2JJw8vQ/contest/276900/submission/78024903

// Submission number = 78024903

// My implementation - It includes the concept of graph traversing - DFS. I traverse the whole graph and save the visited nodes.
// If all nodes are visited from the 1st node we say that the graph is connected, else the graph has some disjoint part.
// This disjoint part can be accessed by the data structure used to store the visited nodes. 

// Time complexity : O(V) (V is the number of vertices)

class Graph<T extends Comparable>  { // Class Graph - Implementation of the graph data structure
    T vertices; // Has attribute vertices
    HashMap<T,LinkedList<T>> adjacency_list; // My graph is a HashMap where keys are the vertices and the values edges stored in the linked list
    Graph(T vertices) {// Constructor
        this.vertices = vertices;
        adjacency_list = new HashMap<T, LinkedList<T>>();

    }
    void addVertex(T ver) { // Function to add a vertex in the graph
    	adjacency_list.put(ver, new LinkedList<T>());
    	
    }
    void addEdge(T src, T dest) { // Function to add an edge in the graph
   
        adjacency_list.get(src).add(dest);
        // We add from both sides because it is a undirected graph
        adjacency_list.get(dest).add(src);
    }

    void DFS(T v, HashMap<T, Boolean> visited) { // Function to traverse the graph
    	
    	// Using the Depth First traversal algorithm
    	
    	// Using HashMap for storing the visited nodes
    	
        visited.replace(v, true); // If a node is visited at the vertex and turn the boolean to true
        
        for (T x : adjacency_list.get(v)) { // We traverse over the graph 
            if(!visited.get(x)) DFS(x,visited); // If the given node is not present in visited then we run a DFS over that node
        }

    }

    public String analyzeConnectivity() // Function to check the connectivity of a graph
    {
        HashMap<T, Boolean> visited = new HashMap<>(); // Using a HashMap to store the visited nodes
        for(T i : adjacency_list.keySet()) { // Traversing over the adjacency list
        	visited.put(i, false);
        }
        T start = adjacency_list.keySet().iterator().next(); // Initializing the variable start
        DFS(start, visited); // DFS with start and visited 

        for (T i : adjacency_list.keySet()) { // Iterating over the key set of the graph
            if (!visited.get(i)) { // If clause to look for the vertices which are not connected
                        return "VERTICES " + start + " AND " + i + " ARE NOT CONNECTED BY A PATH";
                
            }
        }
        return "GRAPH IS CONNECTED"; // If everything is connected print connected
    }
}

public class DSA3_Task1{ // Main class where the executable test code is written
	
	// Using Fast reader to avoid the Time Limit Exceeded error
	
	static class Scanner { // Class Scanner to make our modified scanner
        InputStream in;
        char c;
        Scanner(InputStream in) {
            this.in = in;
            nextChar();
        }

        void asserT(boolean e) {
            if (!e) {
                throw new Error();
            }
        }

        // Inputting new character
        void nextChar() {
            try {
                c = (char)in.read();
            } catch (IOException e) {
                throw new Error(e);
            }
        }

        // Inputting new Long
        long nextLong() {
            while (true) {
                if ('0' <= c && c <= '9' || c == '-') {
                    break;
                }
                asserT(c != -1);
                nextChar();
            }
            long sign=1;
            if(c == '-'){
                sign=-1;
                nextChar();
            }
            long value = c - '0';
            nextChar();
            while ('0' <= c && c <= '9') {
                value *= 10;
                value += c - '0';
                nextChar();
            }
            value*=sign;
            return value;
        }

        // Inputting new integer
        int nextInt() {
            long longValue = nextLong();
            int intValue = (int)longValue;
            asserT(intValue == longValue);
            return intValue;
        }
	}
	public static void main(String[] args){ // Main function has the standard input and output data
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // Inputting the number of vertices
        int m = in.nextInt(); // Inputting the number of edges
        Graph g = new Graph<Integer>(n + 1); // Creating a new object of type graph
        for(int i = 1 ; i <= n; i++) {
        	g.addVertex(i); // Adding vertex to the graph
        }
        for(int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            g.addEdge(a, b); // Adding edge to the graph
        }
        System.out.println(g.analyzeConnectivity()); // Printing the output
    }
}