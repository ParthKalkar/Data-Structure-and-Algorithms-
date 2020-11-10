package DSA2;

import java.io.*;
import java.util.*;

public class AVLTree<T extends Comparable> {  // Class for the construction of our special kind of binary search tree 
    Node root = null;
    
    public class Node { // Class Node, The tree consist of a node with some value and a pointer to the successor and predecessor node
        private Node left, right, parent; // We define 3 nodes - left, right and ancestor node (The parent node)
        private int height = 1; // Initially we consider the height of the tree to be 1. This implies that at least one node is present in our tree
        private T value; // Specifies the value of the node

        private Node (T val) { // We set the value of our node 
            this.value = val;
        }
    }
    private int height (Node N) { // We specify the height of our tree
        if (N == null) // If the node is null that is if it's empty the height is 0
            return 0;
        return N.height; // Else return the height
    }
    
    public void insert(T value) { // Overloading insertion
    	root = this.insert(root, value);
    }
    public boolean delete(T value) { // Overloading deletion
    	if(root == null ) {return false;}
    	root = this.DeleteNode(root, value);
    	return true;
    	
    }

    public Node find(T value) { // Function to check if the element exists in the tree
        
    	//The time complexity is O(log N) N - Number of nodes
    	
    	Node current = root; // We start with the root 
        while (current != null) { // Traversing the tree 
            if (current.value.compareTo(value) == 0) { 
                break;
            }
            current = current.value.compareTo(value) < 0 ? current.right : current.left; // Else comparing the value with the value at current node
            // If the value is less then go left, else go right
        }
        return current;
    }
    public T Successor (T N){ // Function to find the successor of the given node
    	
    	//The time complexity is O(log N) N - Number of nodes
    	
    	
        Node y = find(N);     // Finding if the given node exists and using a temporary variable to store it
        if (y.right != null){ // The condition to check if the right subtree exists
            return MinValueNode(y.right).value; 
        }
        Node root = this.root;
        Node x = null;  // Initializing the temporary node 
        while (root != y) { // While the root is not the node we are searching for do
            if (y.value.compareTo(root.value) > 0) { // Traversing the right subtree
                root = root.right;
            } else if (y.value.compareTo(root.value) < 0) { // Traversing the left subtree
                x = root;
                root = root.left;
            }
        }
        if (x != null) { 
            return x.value; //Getting the value of the successor node
        }
        else return null; // If it is empty return null
    } 
 
    public T Predecessor (T ycomp){ // Function to find the predecessor of the given node
    	
    	//The time complexity is O(log N) N - Number of nodes
    	
        Node y = find(ycomp);      // Finding if the given node exists and using a temporary variable to store it
        if (y.left != null){       // The condition to check if the left subtree exists
            return MaxValueNode(y.left).value;
        }
        Node root = this.root;    
        Node x = null;       // Initializing the temporary node 
        while (root != y) {  // While the root is not the node we are searching for do
            if (y.value.compareTo(root.value) < 0) {  // Traversing the left subtree
                root = root.left;
            } else if (y.value.compareTo(root.value) > 0) {  // Traversing the right subtree
                x = root;
                root = root.right; 
            }
        }
        if (x != null) {  
            return x.value;  //Getting the value of the successor node
        }
        else return null;   // If it is empty return null
    } 
 
    
    private Node insert(Node node, T value) { // Our function to insert a node with some given value
    	
    	// Time complexity of insertion is O(log N) N - Number of Nodes
    	
    	// Perform the normal BST rotation 
        
    	if (node == null) {  // Checking if the tree is empty
            return(new Node(value));  // If it is empty we add our first node
        }

    	// We use the property of Binary search tree and compare the values
    	
    	if (value.compareTo(node.value) < 0)  // If the inputed value is less than the value at the given node 
            node.left  = insert(node.left, value); // We repeat the procedure and recursively call our function to check the availability of left node
        else   
            node.right = insert(node.right, value); // We repeat the procedure and recursively call our function to check the availability of right node

        //Updating the height of this ancestor node
        node.height = Math.max(height(node.left), height(node.right)) + 1;

// AVL tree is a self-balancing BST where the difference between heights of left and right subtrees cannot be more than one for all nodes
        
        
        // Therefore we check the balancing factor
        
       
        int balance = getBalance(node);  // Getting the balance factor of this ancestor node to check whether this node became unbalanced 

        // If this node becomes unbalanced, then there are possibly 4 cases

        // 1. Left-Left Case
        if (balance > 1 && value.compareTo(node.left.value)<0 ) // The balance factor is more than 1 and value is less than the value at left node
            return RightRotate(node);  // We call the Right rotate function

        // 2. Right-Right Case
        if (balance < -1 && value.compareTo(node.right.value) >0) // The balance factor is less than -1 and value is more than the value at right node
            return LeftRotate(node);  // We call the Left rotate function
        

        // 3. Left-Right Case
        if (balance > 1 && value.compareTo(node.left.value) >0) // The balance factor is more than 1 and value is greater than the value at left node 
        {
            node.left =  LeftRotate(node.left);  // We change the left node
            return RightRotate(node);  // We call the Right rotate function
        }

        // 4.Right-Left Case
        if (balance < -1 && value.compareTo(node.right.value) <0) // The balance factor is less than -1 and value is less than the value at right node
        {
            node.right = RightRotate(node.right); // We change the right node
            return LeftRotate(node);  // We call the Left rotate function
        }

        // Return the node
        return node;
    }

    private Node RightRotate(Node b) { // The function Right rotate for handling the balance factor
        
    	// We introduce 2 arbitrary nodes a and T2
    	
    	// Updating the nodes
    	Node a = b.left;  
        Node T2 = a.right;

        // Performing rotation
        a.right = b;
        b.left = T2;

        // Updating heights
        b.height = Math.max(height(b.left), height(b.right))+1;
        a.height = Math.max(height(a.left), height(a.right))+1;

        // Return new root
        return a;
    }

    private Node LeftRotate(Node a) { // The function Left rotate for handling the balance factor
        
    	// We introduce 2 arbitrary nodes a and T2
    	
    	// Updating the nodes
    	Node b = a.right;
        Node T2 = b.left;

        // Performing rotation
        b.left = a;
        a.right = T2;

        //  Updating heights
        a.height = Math.max(height(a.left), height(a.right))+1;
        b.height = Math.max(height(b.left), height(b.right))+1;

        // Return new root
        return b;
    }

    
    private int getBalance(Node N) { // The function for getting the Balance factor of node N
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    private Node MinValueNode(Node node) { // The function for getting the node with minimum value
        
    	//The time complexity is O(log N) N - Number of nodes
    	
    	Node current = node;
        
        // Looping down to find the leftmost leaf node
        if(current == null) return null;
        while (current.left != null)
            current = current.left;
        return current;
    }
    
    private Node MaxValueNode(Node node) { // The function for getting the node with maximum value
        
    	//The time complexity is O(log N) N - Number of nodes
    	
    	Node current = node;
        
        // Looping down to find the rightmost leaf node
        if(current == null) return null;
        while (current.right != null)
            current = current.right;
        
        return current;
    }


    private Node DeleteNode(Node root, T value) { // The function to delete any node from the tree
        
    	//The time complexity is O(log N) N - Number of nodes
    	
    	// Performing Standard deletion

        if (root == null)
            return root;

        // If the value to be deleted is smaller than the root's value, then it lies in the left subtree
        
        if ( value.compareTo(root.value) < 0 )
            root.left = DeleteNode(root.left, value);

        // If the value to be deleted is greater than the root's value , then it lies in the right subtree
        else if( value.compareTo(root.value) > 0 )
            root.right = DeleteNode(root.right, value);

        // if value is same as root's value, then this is the node to be deleted
        else { 
        	
        	// Checking for the number of children
            
        	
        	// Node with only one child or no child
            if( (root.left == null) || (root.right == null) ) {

                Node temp;
                if (root.left != null)
                        temp = root.left;
                else
                    temp = root.right;

                
                // Node with no child
                if(temp == null) {
                    temp = root;
                    root = null;
                }
                
                // Node with one child
                else 
                    root = temp; // Copying the contents of the non-empty child

                temp = null;
            }
            else {
                // Node with two children 
            	
            	//Getting the inorder successor (smallest in the right subtree)
                Node temp = MinValueNode(root.right);

                // Copying the inorder successor's data to this node
                root.value = temp.value;

                // Deleting the inorder successor
                root.right = DeleteNode(root.right, temp.value);
            }
        }

        // If the tree has only one node then return
        if (root == null)
            return root;

        // Updating the height of the current node
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // Getting the balance factor of this node
        
        // Check whether this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases. The same as in Insertion of a node

        // 1.Left-Left Case
        if (balance > 1 && getBalance(root.left) >= 0) // If the balance factor is greater than 1 and the balance factor of left node is greater equal 0
            return RightRotate(root); // Call the Right rotate function

        // 2.Left-Right Case
        if (balance > 1 && getBalance(root.left) < 0) { // If the balance factor is greater than 1 and the balance factor of left node is less than 0
            root.left =  LeftRotate(root.left);  // Update the left node 
            return RightRotate(root);  // Call the Right rotate function
        }

        // 3.Right-Right Case
        if (balance < -1 && getBalance(root.right) <= 0) // If the balance factor is less than -1 and the balance factor of right node is less equal 0
            return LeftRotate(root); // Call the Left rotate function

        // 4.Right-Left Case
        if (balance < -1 && getBalance(root.right) > 0) { // If the balance factor is less than -1 and the balance factor of right node is greater than 0
            root.right = RightRotate(root.right); // Update the right node
            return LeftRotate(root);  // Call the Left rotate function 
        }

        return root;
    }

    public void print() { // Overloading print
    	print(root);
    }
    public void print(Node root) { // The function to print the node

        if(root == null) { // If the tree is empty it would print the pattern
            System.out.println("(_ _ _ _ _)"); 
            return;
        }

        int height = root.height, // Setting the height 
            width = (int)Math.pow(2, height-1); // Setting the width

        // Preparing variables for looping.
        List<Node> current = new ArrayList<Node>(1),
            next = new ArrayList<Node>(2);
        current.add(root);

        final int maxHalfLength = 4;
        int elements = 1;

        StringBuilder sb = new StringBuilder(maxHalfLength*width);
        for(int i = 0; i < maxHalfLength*width; i++)
            sb.append(' ');
        String textBuffer;

        // Iterating through the height levels.
        for(int i = 0; i < height; i++) {

            sb.setLength(maxHalfLength * ((int)Math.pow(2, height-1-i) - 1));

            // Creating spacer space indicator.
            textBuffer = sb.toString();

            // Printing tree node elements
            for(Node n : current) {

                System.out.print(textBuffer);

                if(n == null) {

                    System.out.print("   ");
                    next.add(null);
                    next.add(null);

                } else {

                    System.out.printf("(%6d)", n.value);
                    next.add(n.left);
                    next.add(n.right);

                }

                System.out.print(textBuffer);

            }

            System.out.println();
            
            // Print tree node extensions for next level.
            
            if(i < height - 1) {

                for(Node n : current) {

                    System.out.print(textBuffer);

                    if(n == null)
                        System.out.print("        ");
                    else
                        System.out.printf("%s   %s", n.left == null ? " " : "/", n.right == null ? " " : "\\");
                    

                    System.out.print(textBuffer);

                }

                System.out.println();

            }

            
            // Renewing indicators for next run.
            
            elements *= 2;
            current = next;
            next = new ArrayList<Node>(elements);

        }

    }
    

    public static void main(String args[]) { // The main function to execute the code written above with some examples
    	AVLTree tree1 = new AVLTree();  // Creating new object of a class AVl Tree
    	tree1.insert(10);  // Inserting 10 
    	tree1.insert(20);  // Inserting 20
    	tree1.insert(30);  // Inserting 30 
    	tree1.insert(40);  // Inserting 40 
    	tree1.insert(50);  // Inserting 50 
    	tree1.insert(60);  // Inserting 60 
    	tree1.insert(70);  // Inserting 70 
    	System.out.print("Predecessor of 30 is "+ tree1.Predecessor(30) + " " ) ; // Testing predecessor
    	System.out.println();
    	System.out.print("Successor of 50 is " + tree1.Successor(50) + " ");   // Testing Successor
    	System.out.println();
    	AVLTree tree = new AVLTree();  // Creating a new object of our class AVLtree
        
        while (true) {  // Menu driven program gives the user the choice to decide whether to insert or delete
            System.out.println("(1) - Insert");
            System.out.println("(2) - Delete");

            
            
                Scanner scr = new Scanner(System.in);
                int s = scr.nextInt();

                if (s == 1) {
                    System.out.print("Value to be inserted: ");
                    int n = scr.nextInt();
                    //System.out.print(n);
                    tree.insert(n);
                    
                    
                }
                else if (s == 2) {
                    System.out.print("Value to be deleted: ");
                    int n = scr.nextInt();
                    boolean b = tree.delete(n);
                    if (b == false) {System.out.println("Cannot delete from the empty tree! "); continue;}
                    
                }
                else {
                    System.out.println("Invalid choice, try again!");
                    continue;
                }

                tree.print();
                
        }
    	
       
 
        
    }
    
    }
    
    
