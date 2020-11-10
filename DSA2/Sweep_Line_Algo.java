package SWA;

// https://codeforces.com/group/3ZU2JJw8vQ/contest/272963/my
// #74566289


import java.io.IOException;
import java.io.InputStream;


public class Sweep_Line_Algo { // Class sweep line algorithm


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


    static void printIntersection(LineSegment a, LineSegment b){ // Function to print the intersection of the lines
        System.out.println("INTERSECTION");
        System.out.println(b);
        System.out.println(a);
        System.exit(0);
    }


    public static void main(String[] args) { // Main function to test our code against the standard input
        // TODO Auto-generated method stub

        Scanner fr = new Scanner(System.in);
        int n = fr.nextInt();

        AVLTree<YComparator> mySegments = new AVLTree<>(); // Creating an object of the class AVL Tree of type Y-comparator

        XComparator [] points = new XComparator [2*n]; // Creating an array of type X-comparator
        for(int i = 0; i < n; i++) { // Inputting the coordinates of our points 
            int x1 = fr.nextInt();
            int y1 = fr.nextInt();
            int x2 = fr.nextInt();
            int y2 = fr.nextInt();
            LineSegment a; // Creating an object of type line segment
            
            // Creating new line segments depending on the position of the coordinates
            
            if (x1 < x2) {
                a = new LineSegment(new Point(x1, y1), new Point(x2, y2),false);
                a.id = i;
            } else {
                if (x1 > x2){
                    a = new LineSegment(new Point(x2, y2), new Point(x1, y1),true);
                    a.id = i;
                } else {
                    if (y1<y2){
                        a = new LineSegment(new Point(x1, y1), new Point(x2, y2),false);
                        a.id = i;
                    } else {
                        a = new LineSegment(new Point(x2, y2), new Point(x1, y1),true);
                        a.id = i;
                    }
                }
            }
            
            // Adding line segments to our array
            
            points[i*2] = new XComparator(x1, a); 
            points[i*2+1] = new XComparator(x2, a);
            

        }
        MergeSort<XComparator> MS1 = new MergeSort<>(); // Creating an object of type Merge Sort
        
        MS1.MergeSort(points, 0, 2*n-1); // Calling the function to sort the points
        for(int i = 0; i <= 2*n-1; i++) { // Iterating over the array that we created

            YComparator lower;
            YComparator higher;
            if(points[i].left) { // If it is a left point

                YComparator y = new YComparator(points[i].seg); // Creating a new object of type Y-comparator
                mySegments.insert(y); // Adding the the object "y" to our AVL tree

             // Setting lower and higher points to predecessor and successor respectively
                
                lower = mySegments.Predecessor(y); 
                higher = mySegments.Successor(y);

                // If predecessor is not null check if the segments intersect, if yes then print intersection
                if(lower != null)
                    if(lower.seg.LineIntersection(lower.seg, y.seg)) {printIntersection(lower.seg, y.seg);return;}


                // If successor is not null check if the segments intersect, if yes then print intersection
                if(higher != null)
                    if(higher.seg.LineIntersection(higher.seg, y.seg)) {printIntersection(higher.seg, y.seg);return;}

            }

            else if(points[i].right) {  // If it is a right point


                YComparator y = new YComparator(points[i].seg);  // Creating a new object of type Y-comparator

             // Setting lower and higher points to predecessor and successor respectively
                
                lower = mySegments.Predecessor(y);
                higher = mySegments.Successor(y);
                
                // If successor and predecessor both are not null and if they intersect print intersection
                if(higher != null && lower!= null)
                    if(lower.seg.LineIntersection(lower.seg, higher.seg) && !higher.equals(lower)) {printIntersection(lower.seg, higher.seg);return;}
                
                
                mySegments.delete(y); // Delete that node from the tree





            }
        }
        System.out.println("NO INTERSECTIONS"); // If no intersection found print No intersections



    }

}
class MergeSort<T extends Comparable<? super T>> { // Class Merge Sort based on Java Generics


    // The function that would sort the given array using merge sort method
    void MergeSort(T[] array, int start, int end)
    {
        // Base case
        if (start < end)
        {
            // Finding the middle point
            int middle = (start + end) / 2;

            // We divide the problem into subtasks

            MergeSort(array, start, middle); // Sorting the first half

            MergeSort(array, middle + 1, end);  // Sorting the second half

            // Merging the sorted halves
            Merge(array, start, middle, end);
        }
    }

    // The function to merge the two sub-arrays of the inputed array
    void Merge(T[] array, int start, int middle, int end)
    {
        T[] LeftArray  = (T[]) new Comparable[middle - start + 1];
        T[] RightArray = (T[]) new Comparable[end - middle];

        // Filling the left array
        for (int i = 0; i < LeftArray.length; ++i)
            LeftArray[i] = array[start + i];

        // Filling the right array
        for (int i = 0; i < RightArray.length; ++i)
            RightArray[i] = array[middle + 1 + i];

        // Merging the two temporary arrays

        // Initializing the indexes of first and second sub-arrays
        int leftIndex = 0, rightIndex = 0;

        // The index we would start while adding the sub-arrays back into the main array
        int currentIndex = start;

        // Comparing each index of the sub-arrays and adding the lowest value to the currentIndex
        while (leftIndex < LeftArray.length && rightIndex < RightArray.length)
        {
            if (LeftArray[leftIndex].compareTo(RightArray[rightIndex]) <= 0)
            {
                array[currentIndex] = LeftArray[leftIndex];
                leftIndex++; // Incrementing the Left index
            }
            else
            {
                array[currentIndex] = RightArray[rightIndex];
                rightIndex++; // Incrementing the Right index
            }
            currentIndex++; // Incrementing the Current index
        }

        // Copying the remaining elements of leftArray
        while (leftIndex < LeftArray.length) {
            array[currentIndex++] = LeftArray[leftIndex++];
        }

        // Copying the remaining elements of rightArray
        while (rightIndex < RightArray.length) {
            array[currentIndex++] = RightArray[rightIndex++];
        }
    }
}

class AVLTree<T extends Comparable> {  // Class for the construction of our special kind of binary search tree
    Node root = null;

    public class Node { // Class Node, The tree consist of a node with some value and a pointer to the successor and predecessor node
        private Node left, right, parent; // We define 3 nodes - left, right and ancestor node (The parent node)
        private int height = 1; // Initially we consider the height of the tree to be 1. This implies that at least one node is present in our tree
        private T value; // Specifies the value of the node

        private Node (T val) { // We set the value of our node
            this.value = val;
        }

        public String toString() { 

            return value.toString();
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



    public T Successor (T ycomp){ // Function to find the successor of the given node
        Node y = find(ycomp); // Finding if the given node exists and using a temporary variable to store it
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
        if (x != null) { //Getting the value of the successor node
            return x.value;
        }
        else return null; // If it is empty return null
    } 

    public T Predecessor (T ycomp){ // Function to find the predecessor of the given node
        Node y = find(ycomp);   // Finding if the given node exists and using a temporary variable to store it
        if (y.left != null){    // The condition to check if the left subtree exists
            return MaxValueNode(y.left).value;
        }
        Node root = this.root; 
        Node x = null; // Initializing the temporary node 
        while (root != y) {  // While the root is not the node we are searching for do
            if (y.value.compareTo(root.value) < 0) { // Traversing the left subtree
                root = root.left;
            } else if (y.value.compareTo(root.value) > 0) { // Traversing the right subtree
                x = root;
                root = root.right;
            }
        }
        if (x != null) {  //Getting the value of the successor node
            return x.value;
        }
        else return null;   // If it is empty return null
    }

    
    private Node insert(Node node, T value) { // Our function to insert a node with some given value
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

    public Node find(T value) { // Function to check if the element exists in the tree
        Node current = root; // We start with the root 
        while (current != null) { // Traversing the tree
            if (current.value.compareTo(value) == 0) { // The condition when we found the node we were looking for
                break;
            }
            current = current.value.compareTo(value) < 0 ? current.right : current.left;
         // If the value is less then go left, else go right
        }
        return current;
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
        Node current = node;

        // Looping down to find the leftmost leaf node

        if(current == null) return null;
        while (current.left != null)
            current = current.left;
        return current;
    }

    private Node MaxValueNode(Node node) { // The function for getting the node with maximum value
        Node current = node;

        // Looping down to find the rightmost leaf node
        if(current == null) return null;
        while (current.right != null)
            current = current.right;

        return current;
    }


    private Node DeleteNode(Node root, T value) { // The function to delete any node from the tree

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

}
class LineSegment implements Comparable<LineSegment> { // Class Line segment

    int id, x1, x2, y1, y2; // Introducing some integers for the coordinates and id is to maintain the uniqueness
    Point right;  // Point right
    Point left;   // Point left
    boolean reversed; 

    public LineSegment(Point left, Point right,boolean reversed){ // Line Segment constructor
        this.left = left;
        this.right = right;
        x1 = left.x;
        y1 = left.y;
        x2 = right.x;
        y2 = right.y;
        this.reversed= reversed;
    }

    public int compareTo(LineSegment that){  // Function to allow comparison between the objects of the type line segment
        if (this.left.x < that.left.x) return -1;
        else if (this.left.x > that.left.x) return 1;
        else if (this.id == that.id) return 0;
        else return 1;
    }

    public String toString(){  // Function to return the output in my our desired form
        if(reversed) return x2 + " " + y2 + " " + x1 + " " + y1;
        return x1 + " " + y1 + " " + x2 + " " + y2;
    }


    static boolean LineIntersection(LineSegment ls1, LineSegment ls2) { // Function to check if the intersection exists
        //Initializing the coordinates of the lines we wanna check the intersection 
    	
        double xA = ls1.left.x;
        double yA = ls1.left.y;
        double xB = ls1.right.x;
        double yB = ls1.right.y;
        double xC = ls2.left.x;
        double yC = ls2.left.y;
        double xD = ls2.right.x;
        double yD = ls2.right.y;
       
     // xP and yP are the coordinates of intersection
        
        double xP;
        double yP;
        
        double denom = (xA - xB) * (yC - yD) - (yA - yB) * (xC - xD);
        
     // The mathematical implementation of intersection
        
        if (denom == 0) {
            return false;
        } else {
            xP = ((xA * yB - yA * xB) * (xC - xD) - (xA - xB) * (xC * yD - yC * xD)) / denom;
            yP = ((xA * yB - yA * xB) * (yC - yD) - (yA - yB) * (xC * yD - yC * xD)) / denom;
            if ((xA <= xP && xP <= xB || xA > xP && xP >= xB) &&
                    (yA <= yP && yP <= yB || yA > yP && yP >= yB) &&
                    (xC <= xP && xP <= xD || xC > xP && xP >= xD) &&
                    (yC <= yP && yP <= yD || yC > yP && yP >= yD)) {
                return true;
            } else {
                return false;
            }
        }
    }
}

class Point { // Class Point

    int x, y;
    public Point(int x, int y){ // Constructor of the class Point
        this.x = x;
        this.y = y;
    }
}
class XComparator implements Comparable<XComparator>{ // Class X-Comparator
    
	
	
	// This class is to sort the points as per their x-coordinates
	
	//Using the booleans to check if the point is a right or a left one
	
	boolean left = false; 
    boolean right = false;
    int x;
    LineSegment seg;
    
    public XComparator(int x, LineSegment a){ // Constructor X-Comparator
        this.x = x;
        this.seg = a;
        if (seg.x1 < seg.x2){
            if (this.x == seg.x1){
                this.left = true;
            }
            else if(this.x == seg.x2){
                this.right = true;
            }
        }
        else if (seg.x2 < seg.x1){
            if (this.x == seg.x2){
                this.left = true;
            }
            else if(this.x == seg.x1){
                this.right = true;
            }
        }
    }
    public int compareTo(XComparator that) { // Function to compare the objects of type XComparator
        if (this.x < that.x) return -1;
        else if (this.x > that.x) return 1;
        else if (this.right && that.left){
            return -1;
        } else return 1;
    }
}
class YComparator implements Comparable<YComparator>{ // Class Y-comparator
    
	// Using this class to sort the points as per the y-coordinate
	
	LineSegment seg;
    public YComparator(LineSegment a){ // Constructor Y-comparator
        this.seg = a;
    }
    public int compareTo(YComparator that) { // Function to compare the objects of the type Y-comparator
        if (this.seg.x1 < this.seg.x2 && that.seg.x1 < that.seg.x2){
            if (this.seg.y1 < that.seg.y1){
                return -1;
            }
            else if (this.seg.y1 > that.seg.y1){
                return 1;
            }
            else return 0;
        }
        else if (this.seg.x1 < this.seg.x2 && that.seg.x1 > that.seg.x2){
            if (this.seg.y1 < that.seg.y2){
                return -1;
            }
            else if (this.seg.y1 > that.seg.y2){
                return 1;
            }
            else return 0;
        }
        else if (this.seg.x1 > this.seg.x2 && that.seg.x1 < that.seg.x2){
            if (this.seg.y2 < that.seg.y1){
                return -1;
            }
            else if (this.seg.y2 > that.seg.y1){
                return 1;
            }
            else return 0;
        }
        else if (this.seg.x1 > this.seg.x2 && that.seg.x1 > that.seg.x2){
            if (this.seg.y2 < that.seg.y2){
                return -1;
            }
            else if (this.seg.y2 > that.seg.y2){
                return 1;
            }
            else return 0;
        }
        return 0;
       
    }

    public String toString(){ // Function to get the desired output
        return seg.x1 + " " + seg.y1 + " " + seg.x2 + " " + seg.y2;
    }


}