//package DSA3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.IOException;
import java.io.InputStream;
 
//Parth Kalkar, BS-19, DSA_Assignment_3_Task_1

// Submission link : https://codeforces.com/group/3ZU2JJw8vQ/contest/276900/submission/78125480

// Submission number = 78125480

// My implementation - It includes the concept of Minimum Spanning Tree. I used the Kruskal's algorithm to tackle this problem. 
// Kruskal's Algorithm gave me the edge of least possible weight. I combined the logic of previous task to find the different vertex components
// This would give me the number of trees in the forest and the minimum weight edges. 

// This is a greedy algorithm as it finds a minimum spanning tree for a connected weighted graph adding increasing cost arcs at each step.

/* Pseudo code - KRUSKAL(G):

A = null
foreach v ∈ G.V:
   MAKE-SET(v)
foreach (u, v) in G.E ordered by weight(u, v), increasing:
   if FIND-SET(u) ≠ FIND-SET(v):
      A = A ∪ {(u, v)}
      UNION(u, v)
return A
*/

//  Time complexity - O ( E l o g V ) (E - Edges and V - Vertices)



public class DSA3_Task3 { // Main class where the executable test code is written
	
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
 
    public static void main(String[] args) throws IOException { // Main function has the standard input and output data
    	
    	// Using IO Exception to avoid any kind of unnecessary error while runtime 
    	
    	Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // Inputting the number of vertices
        int m = in.nextInt(); // Inputting the number of edges
        Integer [] sub = new Integer[n] ; // Creating an Integer array of size number of vertices
        for (int i= 1 ; i <= n; i++ ) {
            sub[i-1] = i; // Initializing the sub
        }
        Graph g = new Graph<Integer,Integer>(n,sub); // Creating a new object of type graph
        for(int i = 1 ; i <= n; i++) {
            g.addVertex(i); // Adding vertex to the graph
        }
        for(int i = 0; i < m; i++) {
            int a = in.nextInt(); 
            int b = in.nextInt();
            int w = in.nextInt();
            g.addEdge(a, b, w); // Adding edge to the graph with its weight
        }
        g.MST(); // Calling the Minimum spanning tree function which has inbuilt print statement
    }
}
 
class Edge<T,W extends Comparable> implements Comparable<Edge<T, W>>{ // CLass edge to create an object of type edge
	
	// The object of type edge is generic and has 2 generic components namely T for edges and W for the weight
	
	// The edge object has the attributes source, destination and weight
	
    T source;
    T destination;
    W weight;
 
    public Edge(T source, T destination, W weight) { // Constructor
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    public Edge(T destination, W weight) { // Creating another constructor to satisfy the needs of the problem
        this.destination = destination;
        this.weight = weight;
    }
 
    @Override
    public int compareTo(Edge<T, W> o) { // Function to compare the objects of type edge
        // TODO Auto-generated method stub
 
        return weight.compareTo(o.weight);
    }
 
}
class Graph<T, W extends Comparable>  { // // Class Graph - Implementation of the graph data structure
    
	// The object of type graph has attributes count and vertices
	
	int Count; // Counter to get the number of trees in the forest
	
    int vertices; // Number of vertices
    
    HashMap<T, ArrayList<Edge<T,W>>> adjacency_list; // Creating an adjacency list with the data structure HashMap
    HashMap<T,T> parent = new HashMap<T,T>(); // Creating a parent with the data structure HashMap
    HashMap<T, HashMap<T, Boolean>> EDGE = new HashMap<>(); // Creating a HashMap to store the edges of the MST
    HashMap<T, HashMap<T, Boolean>> EDGES = new HashMap<>(); // Creating a HashMap to store the edges of the MST according to the output requirements
    HashMap<T, Integer> rank = new HashMap<>(); // Creating a rank to use for the methods of find and union in the Kruskal algorithm
    T [] nodes; // Creating an array to store the nodes
    T[] x; 
 
    Graph(int vertices, T [] add) { // Constructor
        this.vertices = vertices;
        adjacency_list = new HashMap<T, ArrayList<Edge <T,W>>>();
        //  nodes =  (T[]) new Object [vertices];
        nodes = add;
    }
    void addVertex(T ver) { // Function to add a vertex in the graph
        adjacency_list.put(ver, new ArrayList<Edge<T,W>>());
 
    }
    void addEdge(T src, T dest, W weight) { // Function to add an edge in the graph
 
        if(EDGES.get(src) == null) // To avoid the Null pointer exception
            EDGES.put(src, new HashMap<>());
        EDGES.get(src).put(dest, true);
 
        adjacency_list.get(src).add(new Edge<T,W>(src, dest, weight));
        // We add from both sides because it is a undirected graph
        adjacency_list.get(dest).add(new Edge<T,W>(dest, src, weight));
    }
 
    public void makeSet(){ // Function to make a set 
        
    	// Creating a new element with a parent pointer to itself
    	
        for (int i = 0; i <vertices ; i++) {
            rank.put(nodes[i], 1);
            parent.put(nodes[i], nodes[i]);
        }
    }
 
    public T find(T vertex){ // Function to find the given vertex
        
    	// Chain of parent pointers from x upwards through the tree; until an element is reached whose parent is itself
    	
        if(parent.get(vertex)!=vertex)
            return find(parent.get(vertex));
        return vertex;
    }
 
    public void union(T x, T y){ // Function to make union
        
    	// Firstly, finding the x and y
    	
    	T x_set_parent = find(x);
        T y_set_parent = find(y);
        
        // Making x as the parent of y
        
        if(rank.get(x_set_parent) < rank.get(y_set_parent)){
            parent.put(x_set_parent, y_set_parent);
            rank.put(y_set_parent, rank.get(x_set_parent) + rank.get(y_set_parent));
            rank.put(x_set_parent, 0);
        }
        else{
            parent.put(y_set_parent, x_set_parent);
            rank.put(x_set_parent, rank.get(x_set_parent) + rank.get(y_set_parent));
            rank.put(y_set_parent, 0);
        }
    }
 
    public void MST(){ // Function minimum spanning tree 
    	
    	// This is the most crucial part of the Kruskal's Algorithm
    	
        ArrayList<Edge<T,W>> al = new ArrayList<>(); // Creating an array list to store all the edges 
        
        for (int i = 0; i <adjacency_list.size() ; i++) {
            for (int j = 0; j < adjacency_list.get(nodes[i]).size(); j++){
                al.add(new Edge<T,W>(adjacency_list.get(nodes[i]).get(j).source,
                        adjacency_list.get(nodes[i]).get(j).destination,
                        adjacency_list.get(nodes[i]).get(j).weight));
            }
        }
        Collections.sort(al); // Sorting the added edges according to the weights
        
        makeSet(); // Making a set
 
        ArrayList<Edge<T, W>> mst = new ArrayList<Edge<T, W>>(); // Creating an Array list to store the edges
 
        int index = 0; // Initializing the variable index
        int jj = 0; // Initializing the counter
        
        while(jj < al.size()){ // Repeating the cycle till the time the condition holds
            
        	Edge<T,W> edge = al.get(jj++); // Creating a new object of type edge and initializing it 
            
        	// Checking if adding this edge creates a cycle
        	
            // Initializing x_set and y_set
        	
        	T x_set = find(edge.source);
            T y_set = find(edge.destination);
 
            if(x_set==y_set){
                // We will ignore it as it would create a cycle
            }else {
                // Else, adding it to our final output
                mst.add(edge);
                
                // Handling the null pointer exception 
                
                if(EDGE.get(edge.source) == null) 
                    EDGE.put(edge.source, new HashMap<>());
                if(EDGE.get(edge.destination) == null)
                    EDGE.put(edge.destination, new HashMap<>());
                
                // Adding the result to our final HashMap
                
                EDGE.get(edge.source).put(edge.destination, true);
                EDGE.get(edge.destination).put(edge.source, true);
                
                // Creating a union of x_set and y_set
                union(x_set,y_set);
            }
        }
        int count = 0; // Initializing the counter 
        
        HashMap<T, Boolean> vis = new HashMap<>(); // Creating a HashMap which stores the visited nodes
        
        for(int i=0;i<vertices;i++){
            if(vis.get(find(nodes[i])) == null){
                count++;
            }
            vis.put(find(nodes[i]), true);
        }
        System.out.println(count); // Printing the count, the first element of the expected output format
        vis.clear(); // Clearing the visited hashMap
        for(Edge<T,W> i : mst) { // Iterating over the mst
 
            if(vis.get(i.source) != null) // Handling the exception
                continue;
            
            // Creating 2 hashMaps to store the number of trees and edges with weights
            HashMap<T, Integer> dict = new HashMap<T, Integer>(); 
            HashMap<T, HashMap<T, W>> gg = new HashMap<>();
 
            DFS1(i.source, dict, count++, gg, vis); // Calling the DFS1 function
            System.out.println(gg.size() + " " + i.source); // Printing the 2nd element of the output format
            
            for(T x : gg.keySet()){ // Iterating over gg
                for(T y : gg.get(x).keySet()){ 
                    
                	// Printing according to the output format - The sequential format
                	
                	if(EDGES.get(x) != null && EDGES.get(x).get(y) != null && EDGES.get(x).get(y) == true) 
                        System.out.println(x + " " + y + " " + gg.get(x).get(y)); 
                    else System.out.println(y + " " + x + " " + gg.get(x).get(y));
                }
            }
        }
        
        // Finally printing the last element 
        
        for(int i=0;i<vertices;i++){
            if(vis.get(nodes[i]) == null)
                System.out.println(1 + " " + nodes[i]);
        }
    }
 
    public HashMap<T, Integer> vertexComponents() { // Function to find the vertex components
    	
    	// This function gives us the number of different disjoint components 
        
    	HashMap<T, Integer> dict = new HashMap<T, Integer>(); // Using a HashMap to store the vertex and the connected component it belongs to
        
    	int count = 0; // Initializing a counter 
        
    	for(T i : adjacency_list.keySet()) { // Iterating over the graph
            if(!dict.keySet().contains(i)) { // If clause to find if the given vertex belongs to any connected component
                DFS(i,dict,count); // If it does not belong then run DFS over it and increment the counter
                count++; 
            }
        }
        Count = count; // Setting our Count to the output obtained from the method
        return dict; // Returning the dict
    }
 
    void DFS(T v, HashMap<T, Integer> map, Integer current_component) { // Overriding the function DFS to solve the given task
    	
    	// Using a HashMap of the vertex and integer counter
        map.put(v, current_component); // We add to the HashMap the vertex and current component
        for (Edge<T,W> x : adjacency_list.get(v)) { // Iterating over graph
            
        	// If map does not contain the given vertex then we run DFS over that vertex
        	
        	if(x.source != v && !map.containsKey(x.source)) { 
                DFS(x.source,map, current_component);
            }
            else if(x.destination != v && !map.containsKey(x.destination)){
                DFS(x.destination,map, current_component);
            }
        }
    }
 
    void DFS1(T v, HashMap<T, Integer> map, Integer current_component, HashMap<T, HashMap<T, W>> gg, HashMap<T, Boolean> vis) { // Function DFS1
    	
    	// Creating a new customized DFS to tackle the problem
    	
        map.put(v, current_component); // We add to the HashMap the vertex and current component
        vis.put(v, true); // We add to the HashMap the vertex and pass the boolean true
        for (Edge<T,W> x : adjacency_list.get(v)) { // Iterating over the graph 
            
        	// Handling the possible null pointer exception
        	if(gg.get(v) == null)
                gg.put(v, new HashMap<T, W>());
 
        	// If map does not contain the given vertex then we run DFS over that vertex
        	if(x.source != v && !map.containsKey(x.source) && EDGE.get(v) != null && EDGE.get(v).get(x.source) != null && EDGE.get(v).get(x.source)) {
                gg.get(v).put(x.source, x.weight);
                DFS1(x.source, map, current_component, gg, vis);
            }
            else if(x.destination != v &&
                    !map.containsKey(x.destination) &&
                    EDGE.get(v) != null &&
                    EDGE.get(v).get(x.destination) != null &&
                    EDGE.get(v).get(x.destination) == true){
                gg.get(v).put(x.destination, x.weight);
                DFS1(x.destination,map, current_component, gg, vis);
            }
        }
    }
 
}