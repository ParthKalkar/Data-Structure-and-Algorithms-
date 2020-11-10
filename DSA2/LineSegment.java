package DSA2;

import java.util.Scanner;

/* 

Significance of line segment intersection: 

The greater part of the applications identified with this calculation search for those two shapes that are not situated in a similar spot. 
Moreover, there are various uses of this algorithm. 

For example - to extract data from map overlay it is important to discover the convergence of line sections, to break down the Vectors in a picture.
To consider the connection among components in a database. 
In geometry, to see whether a polygon is basic or not. 
Finding the crossing point of 2 line portions is one of the major issues of computational geometry

One of its applications is finding the crossing points of increasingly complex shapes that are produced using line sections
It very well may be applied to take care of geometrical issues where the arrangement is the crossing point of two lines 
A model - finding the hover characterized by 3 uncommitted focuses, the focal point is the convergence of the bisectors 

And furthermore, the issue of finding the convergence of 2 line portions is significant as a result of the quantity of true issues that can be decreased to the crossing point of 2 line fragments issue.


*/


/*
Explanation of the Sweep Line Algorithm: 

Let's consider this algorithm as a vertical straight line moving through the XY-plane. 
At every x we can compare the lines based on their intersection with the sweep line at the present x. 

Assuming no three lines converge at a similar point, each pair of intersecting lines cross the sweep line in focuses that are next by one another in the scope of sweep line 
This is the condition when the sweep line is directly before the section convergence

Now we just check the crossing point for sections that are alongside one another utilizing the intersection with the sweep line 
We consider section endpoints as occasions that move the sweep line, sorting them depending on their x-coordinate, and navigating them individually. 

During the traversal we maintain a self balancing Binary Search tree.
This tree is empty in the start 

What's more, at every x, it will contain the portions converging the sweep line sorted by the respective y of the individual intersection 
At every endpoint we insert the segment in the tree that it is a left endpoint, and we check the point of intersection with its new neighbors (ancestor and successor) in the BST  

In the tree that it is a right endpoint we delete the portion and check the crossing point of its predecessor and successor in the BST
*/


public class LineSegment { // Class Line segment

	int id, x1, x2, y1, y2; // Introducing some integers for the coordinates and id is to maintain the uniqueness
    Point right; // Point right
    Point left;  // Point left

    public LineSegment(Point left, Point right){ // Line Segment constructor
        this.left = left;
        this.right = right;
        x1 = left.x;
        y1 = left.y;
        x2 = right.x;
        y2 = right.y;
    }

    public int compareTo(LineSegment that){  // Function to allow comparison between the objects of the type line segment
        if (this.left.x < that.left.x) return -1;
        else if (this.left.x > that.left.x) return 1;
        else if (this.id == that.id) return 0;
        else return 1;
    }
    
    public String toString(){  // Function to return the output in my our desired form
        return x1 + " " + y1 + " " + x2 + " " + y2;
    }


    public static void Naive(LineSegment LS1 []) { // Function implementing the naive algorithm to find intersection
    	// We pass a parameter array of type line segment 
    	int n = LS1.length; 
    	for(int i = 0; i < n; i++) { // Iterating over the array elements 
    		// Important to remember that the array elements are not points instead they are line segments
    		for(int j = i+1; j < n; j++) { 
    			if(LS1[i].LineIntersection(LS1[i],LS1[j])) {System.out.println("INTERSECTION");System.out.println(LS1[i]);System.out.println(LS1[j]);return;}
    			// If intersection is found we print it and return 
    		}
    		
    	}
    	System.out.println("NO INTERSECTIONS"); // Else no intersections
    	
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
            if ((xA <= xD && xD <= xB || xA >= xD && xD >= xB) &&
                    (yA <= yD && yD <= yB || yA >= yD && yD >= yB) ||
                    (xC <= xB && xB <= xD || xC >= xB && xB >= xD) &&
                            (yC <= yB && yB <= yD || yC >= yB && yB >= yD)) {
                return true;
            } else {
                return false;
            }
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

    
    
    
    public static void main(String[] args) { // The main function to test our implementation
    	Scanner sc = new Scanner(System.in); 
    	int n = sc.nextInt();
    	LineSegment[] ls = new LineSegment[n] ; // Creating an array of type line segments to store our line segments
    	
    	for(int i = 0; i < n; i++) { // Inputting our points and forming the line segment
    		Point p0 = new Point(sc.nextInt(),sc.nextInt());
        	Point q0 = new Point(sc.nextInt(), sc.nextInt());
        	LineSegment LS1 = new LineSegment(p0, q0);
        	ls[i] = LS1; // Storing the line segments
        	
    		
    	}
    	LineSegment.Naive(ls); // Calling the function to test intersection
    	
    	
    	//System.out.print("Hello World");
    }

}

class Point { // Class Point
	
	int x, y; 
    public Point(int x, int y){ // Constructor of the class Point
        this.x = x; 
        this.y = y;
    }
}