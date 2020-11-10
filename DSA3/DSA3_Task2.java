//package DSA3;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

// Parth Kalkar, BS-19, DSA_Assignment_3_Task_2

// Submission link : https://codeforces.com/group/3ZU2JJw8vQ/contest/276900/submission/78037551

// Submission number = 78037551

// My implementation - It includes the concept of graph traversing - DFS. I traverse the whole graph and save the visited nodes.
// If all nodes are visited from the 1st node we say that the graph is connected, else the graph has some disjoint part.
// The different number of disjoint components would give us the number of the vertex where it is connected
// This disjoint component number is our solution

// Time Complexity : O(V) (V is the number of vertices)



class Graph<T extends Comparable>  { // Class Graph - Implementation of the graph data structure
    T vertices; // Has attribute vertices
    HashMap<T,LinkedList<T>> adjacency_list; // My graph is a HashMap where keys are the vertices and the values edges stored in the linked list
    Graph(T vertices) { // Constructor
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
        for (T x : adjacency_list.get(v)) { // We iterate over the graph 
            if(!visited.get(x)) DFS(x,visited); // If the given node is not present in visited then we run a DFS over that node
        }

    }
    void DFS(T v, HashMap<T, Integer> map, Integer current_component) { // Overriding the function DFS to solve the given task
    	
    	// Using a HashMap of the vertex and integer counter
    	
        map.put(v, current_component); // We add to the HashMap the vertex and current component
        for (T x : adjacency_list.get(v)) { // Iterating over graph
            if(!map.keySet().contains(x)) DFS(x,map, current_component); // If map does not contain the given vertex then we run DFS over that vertex
        }

    }

    public HashMap<T, Integer> vertexComponents() { // Function to find the vertex components
    	
    	// This function gives us the number of different disjoint components 
    	
    	HashMap<T, Integer> dict = new HashMap<T, Integer>(); // Using a HashMap to store the vertex and the connected component it belongs to
    	int count = 1; // Initializing a counter 
    	for(T i : adjacency_list.keySet()) { // Iterating over the graph
    		if(!dict.keySet().contains(i)) { // If clause to find if the given vertex belongs to any connected component
    			DFS(i,dict,count); // If it does not belong then run DFS over it and increment the counter
    			count++;
    		}
    	}
    	return dict; // Returning the output
    }
}

public class DSA3_Task2 { // Main class where the executable test code is written
	
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
	public static void main(String[] args) { // Main function has the standard input and output data
		Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // Inputting the number of vertices
        int m = in.nextInt(); // Inputting the number of edges
        Graph g = new Graph<Integer>(n); // Creating a new object of type graph
        for(int i = 1 ; i <= n; i++) {
        	g.addVertex(i); // Adding vertex to the graph
        }
        for(int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            g.addEdge(a, b); // Adding edge to the graph
        }
        HashMap<Integer, Integer> dict = new HashMap<Integer, Integer>(); // Creating a HashMap to store the output from the vertex component 
		dict = g.vertexComponents();
		for(int i = 1; i <=n; i++) {
			System.out.print(dict.get(i) + " "); // Printing the output
		}

	}

}
