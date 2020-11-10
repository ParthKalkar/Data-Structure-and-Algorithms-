import java.util.ArrayList;
import java.util.Scanner;

//Parth Kalkar B-19-02 
//http://codeforces.com/group/3ZU2JJw8vQ/contest/269072/submission/71174468
public class Task2 {

	public static void main(String[] args) {
		boolean error = false;
		
		Stack stack = new Stack();
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		input.nextLine();
		// We start the problem with creating an object of class Stack
		
		// We iterate over a user integer and take the input string
		
		// Iterate over the string to find out if it contains any of the given symbols - opening
		
		for(int i = 0; i<n; i++) {
			String a = input.nextLine();
			for(int j = 0; j<a.length(); j++) {
				if((a.charAt(j) == '(') || (a.charAt(j) == '{') || (a.charAt(j) == '[')){
					stack.push(a.charAt(j));
				}
				// If we encounter any such symbol we push it into the stack
				
				// The following if else statements checks which kind of symbol is encountered
				if(a.charAt(j)== ')'){
					
					// This if else clause is to print the different kinds of errors depending upon the symbol type
					
					if (stack.isEmpty()) { //This error tells that the exceution wanted something else but it faced unexpected closing
						
						System.out.print("Error in line " + (i+1) + ", column " + (j+1) + ": unexpected closing ')'.");
						error = true; break;
					}
					else if (stack.Peek() == '(') {
						stack.Pop();
					}
					else if (stack.Peek() == '{'){
						System.out.println("Error in line " + (i+1) + ", column " + (j+1) + ": expected '}', but got ')'.");
						error = true; break;
					}
					else if (stack.Peek() == '['){
						System.out.println("Error in line " + (i+1) + ", column " + (j+1) + ": expected ']', but got ')'.");
						error = true; break;
					}
				}
				if(a.charAt(j)== '}'){
					if (stack.isEmpty()) {
						System.out.print("Error in line " + (i+1) + ", column " + (j+1) + ": unexpected closing '}'.");
						error = true; break;
					}
					else if (stack.Peek() == '{') {
						stack.Pop();
					}
					else if (stack.Peek() == '('){
						System.out.println("Error in line " + (i+1) + ", column " + (j+1) + ": expected ')', but got '}'.");
						error = true; break;
					}
					else if (stack.Peek() == '['){
						System.out.println("Error in line " + (i+1) + ", column " + (j+1) + ": expected ']', but got '}'.");
						error = true; break;
					}
				}
				if(a.charAt(j)== ']'){
					if (stack.isEmpty()) {
						System.out.print("Error in line " + (i+1) + ", column " + (j+1) + ": unexpected closing ']'.");
						error = true; break;
					}
					else if (stack.Peek() == '[') {
						stack.Pop();
					}
					else if (stack.Peek() == '{'){
						System.out.println("Error in line " + (i+1) + ", column " + (j+1) + ": expected '}', but got ']'.");
						error = true; break;
					}
					else if (stack.Peek() == '('){
						System.out.println("Error in line " + (i+1) + ", column " + (j+1) + ": expected ')', but got ']'.");
						error = true; break;
					}
				}
			}
			if (error) break;
			if ( i == n - 1) { //If we do not encounter any problem we print the balanced statement
				if(stack.isEmpty()) {
					System.out.println("Input is properly balanced.");
				}
				else { //This is to display the type of error that occurred due to the sudden end of the input
					if (stack.Peek() == '(')
					System.out.println("Error in line " + (n) + ", column " + (a.length()) + ": expected ')', but got end of input.");
					else if (stack.Peek() == '{')
						System.out.println("Error in line " + (n) + ", column " + (a.length()) + ": expected '}', but got end of input.");
					else if (stack.Peek() == '[')
						System.out.println("Error in line " + (n) + ", column " + (a.length()) + ": expected ']', but got end of input.");
				}
			}
		}
	}
	// Most important thing, we use "error" variable to make sure that we only print one error 
}

class  Stack{  //This is a class Stack with characteristics such as top and features like Pop-Delete, Push-Add, Peek-Get top
	
	int top;
	ArrayList<Character> elements;
	
	
	public Stack() {  //Constructor
		elements = new ArrayList();
		top = -1;
		
	}
	
	public void push(char c) {  //Adds to the top
		elements.add(++top, c); 
	}
	
	public void Pop() {   //Removes from the top
		elements.remove(top--);
	}
	
	public char Peek() {  //Gets top
		return elements.get(top);
	}
	
	public boolean isEmpty() { //Checks if the stack is empty
		return (elements.size() == 0);
	}
}