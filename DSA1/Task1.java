/**
 * 
 */

/**
 * @author Parth Kalkar, B-19-02
 *  DSA Task 2.1 - Implementation of List ADT using Doubly Linked List and Dynamic Arrays
 *
 */
public class Task1 {      //This is the class named after the task-(Task1). It contains the main function.
						  

	/**
	 * @param args
	 */
	public static void main(String[] args) {   //The main function would implement the class Dynamic Arrays and Doubly Linked List
		DoublyLinkedList <String> my = new DoublyLinkedList <String> (); //Creating a new object of the class DoublyLinkedList
	    DynamicArrays <Integer>  boy = new DynamicArrays<Integer>(); // Creating a new object of the class DynamicArrays
		
		System.out.println(my.isEmpty()); // The function isEmpty would return the boolean.
	      
	      my.addLast("tt"); //The addlast adds the given input at last 
	      my.out();
	      my.addLast("e");
	      my.out();
	      my.add(0, "e"); //The add function adds the given input at the specified index with the given value.
	      my.out();
	      my.addLast("a");
	      my.out();
	      my.addFirst("b");
	      my.addFirst("c");
	      my.out();
	      my.addLast("d");
	      my.add(1, "asd");
	      my.out();
	      my.delete(2); //The delete function deletes the node of the specified index. 
	      my.out();
	      my.delete("e");  // The delete function here deletes the node with the specified value
	      my.out();
	      my.deleteFirst(); //The deleteFirst deletes the first node of the DoublyLinkedList
	      my.out();
	      my.deleteLast(); //The deleteLast deletes the last node of the DoublyLinkedList
	      my.out();
	      my.deleteFirst();
	      my.out();
	      my.deleteLast();
	      my.out();
	      my.deleteLast();
	      
	      
	      
	      System.out.println(boy.isEmpty()); // The function isEmpty would return the boolean.
	      
	      boy.addLast(1); //The addlast adds the given input at last 
	      boy.out();
	      boy.addLast(11);
	      boy.out();
	      boy.add(0, 2); //The add function adds the given input at the specified index with the given value.
	      boy.out();
	      boy.addLast(22);
	      boy.out();
	      boy.addFirst(111);
	      boy.addFirst(222);
	      boy.out();
	      boy.addLast(3);
	      boy.add(1, 12);
	      boy.out();
	      boy.delete(2); //The delete function deletes the node of the specified index. 
	      boy.out();
	      boy.delete(1);  // The delete function here deletes the value with the specified index
	      boy.out();
	      boy.deleteFirst(); //The deleteFirst deletes the first element of the DynamicArrays
	      boy.out();
	      boy.deleteLast(); //The deleteLast deletes the last element 
	      boy.out();
	      boy.deleteFirst();
	      boy.out();
	      boy.deleteLast();
	      boy.out();
	      boy.deleteLast();
	      
		// TODO Auto-generated method stub

	}

}
 interface List<T>{    // As we know the List ADT in Java is an interface. Therefore this interface has several functions
	 boolean isEmpty(); // Checks if the List is empty
	 void add(int i, T e); //Adds the element at index 'i' with value 'e'
	 void addFirst(T e);  //Adds the element at first with value 'e'
	 void addLast(T e);  // Adds the elements at last with value 'e'
	 void delete(T e);   // Deletes the element with value 'e'
	 void delete(int i); // Deletes the element at index 'i'
	 void deleteFirst(); // Deletes the first element
	 void deleteLast();  // Deletes the last element
	 void set(int i, T e); // Replaces the value of the element at index 'i' with new value 'e' 
	 T get(int i);  //Prints the value of the element at index 'i'
	 
 }
 
 class DynamicArrays<T> implements List<T>{ //This class named as DynamicArrays implements the ListADT or the list interface using arrays
	 
	 
	 int size = 0;                       //Main characteristics of any object in my class include size. 
	 T[] arr = (T[]) new Object[2];      // As I cannot create an array of type directly, I create it as an array of type object
	 

	@Override
	public boolean isEmpty() {    // Checks if the given array is empty
		if(size == 0) {          // returns true if the size is 0
			return true;
		}
		return false;
	}
	
	void checksize() {          // Checks the size of the given array
		if(arr.length == size) {          // I use it to double the size of my current array if it runs out of size
			T[] arr1 = (T[]) new Object[2*size];
			for(int i = 0; i < size; i++) {
				arr1[i] = arr[i];
			}
			arr = arr1;  // Finally setting my original array equal to an array of double the size
		}
	}
	
	void checkindex(int i) {    // checkIndex is used to check the index and handle the condition of invalid index
		if(i >= size || i < 0) {
			throw new IndexOutOfBoundsException(); // It throws an exception if the index is bigger than the size or less than zero
		}
	}
	
	public void add(int i, T e) {   // Adds at the index 'i' with value 'e'
 		checkindex(i);   //Before adding just checking the index so as to avoid any IOoB error
		if(size == 0) {  //The condition where the array is empty
 			arr[0] = e;  // Adding the element directly at the front
 			size += 1;  // Adding increases the size
 			checksize(); // Checking the size to maintain the flow
 			return ;
 		}
		for(int j = size; j > i; j--) { // The general case firstly shifting all the elements i place ahead and create an epmty space to add
			arr[j] = arr[j-1];
		}
		size += 1;  //Increasing the size
		checksize(); //Checking the size
		
		arr[i] = e; // Adding the element
	} 
	

	@Override
	public void addFirst(T e) { // Adds at the first place
		add(0, e);   //Jut using the previously written function and replacing the value of index with 0
		 
	}

	@Override
	public void addLast(T e) {  //Adds at the last place
		arr[size] = e;  // Just adding at the last index with value 'e'
		size += 1;    // Increasing the size
		checksize();   // Checking the size
		
	}

	@Override
	public void delete(T e) {  //Deletes the element with value 'e'
		for(int i = 0; i < size; i++) {  //Iterating over the array to find if such element exist.
			if(arr[i].equals(e)) { //If it exists we delete it by using the previously defined delete function 
				delete(i);   
				i--; 
			}
			
		}
		
	}

	@Override
	public void delete(int i) { //Deletes the element index 'i'
		checkindex(i); //Check the index condition
		if(size == 1) { //Base case with the size is 1 that means only element, directly delete it
 			size = 0;	// Decrementing the size
 			return ;
		}
		for(int j = i; j < size; j++) { //For general case we firstly reach the element with given index
			arr[j] = arr[j+1];  //Shift all the elements one place to the left
		}
		size -= 1; //Decrementing the size
	} 

	@Override
	public void deleteFirst() { //Deletes the first element
		
		delete(0); //Using the predefined function delete(i) with i = 0
		
	}

	@Override
	public void deleteLast() { //Deleting the last element
		arr[size] = null; //Setting the last element as null
		size -= 1; //Decrementing the size
		
		
	}

	@Override
	public void set(int i, T e) { //Setting or replacing the value at the given index with the specified value
		checkindex(i); //Checking the index
		arr[i] = e; //Overwriting with the given value
		
	}

	@Override
	public T get(int i) { //Getting the value of the index
		checkindex(i);   // Checking the index
		return arr[i];
	}
	public void out() {  //Function to print the given array
		for(int i =0; i < size; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	 
 }
 
 
class DoublyLinkedList<T> implements List<T>{ //This is the class implementing the ListADT using DoublyLinkedList
	
	class Node{ //The doubly linked list contains 3 things - data, next and previous which points to the next and previous nodes respectively
		T data; 
		Node prev;
		Node next;
		
		public Node(T data, Node prev, Node next) { //Constructor for the class Node
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
		
	}
	 Node Node1 = new Node(null, null, null); // Taking 2 objects Node1 and Node2
	 Node Node2 = new Node(null, null, null);
	 
	 public DoublyLinkedList() { //Initializing the doubly linked list
		 Node1.next = Node2; 
		 Node2.prev = Node1;
		 
	 }
	 int size = 0;
	@Override
	public boolean isEmpty() { //The method returns the boolean
		if(size == 0) { // Returns true if the size is 0
			return true;
		}
		return false;
	}
	void checkindex(int i) { //Checking the index and handling the IOoB error
		if(i >= size || i < 0) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public void add(int i, T e) { //Add at the given index with specified value
		if(i == 0 && size == 0) { //Checking if the linked list is empty 
			Node new2 = new Node(e, Node1, Node2);
			Node1.next = new2;  //Updating the nodes 
			Node2.prev = new2;
			size++; //Incrementing the size
			return;
		}
 		checkindex(i); 
 		Node temp = Node1;
 		Node new1 = new Node(e, null, null);
 		for(int j = 0; j <= i; j++) { //For general case we reach the given index
 			temp = temp.next;
 		}
 		new1.prev = temp.prev;  //Update the nodes
 		new1.next = temp;
 		Node x=temp.prev;
 		x.next = new1;
 		temp.prev = new1;
 		size++; //Increment the size
 		
	}
	

	@Override
	public void addFirst(T e) { //Adds at the start of the Linked List
		add(0, e); //Using the previously defined function
		
	}

	@Override
	public void addLast(T e) { //Adds at the last of the Linked List
 		Node temp = Node1;
 		Node new1 = new Node(e, null, null);
 		if(Node1.next == Node2) { //Checking the condition if the Linked List is empty and working accordingly
 			Node1.next = new1; //Updating the nodes 
 			Node2.prev = new1;
 			new1.prev = Node1;
 			new1.next = Node2;
 			size++; // Incrementing the size
 			return;
 		}
 		new1.prev = Node2.prev; // General scenario, updating nodes and incrementing size
 		Node2.prev = new1;
 		new1.next = Node2;
 		Node x = new1.prev;
 		x.next=new1;
 		size++;
		
		
	}

	@Override
	public void delete(T e) {   //Delete the node with given value
		Node temp = Node1.next;
		int x = 0;
		for(int i = 0; i < size; i++) {  //iterating over the linked list to reach the specified value
			if(temp.data.equals(e)) { // Checking if such node exists and if it does updating the pointers and incrementing x
				temp.prev.next = temp.next;
				temp.next.prev = temp.prev;
				x++;
			}
			temp = temp.next;
		}
		size-=x; //Decrementing the size
		
	}

	@Override
	public void delete(int i) {  //Deleting the node at the given index
		checkindex(i);  //Checking the index
 		Node temp = Node1;
 		for(int j = 0; j <= i; j++) { //Iterating till we reach the specified index
 			temp = temp.next;
 		}
		temp.prev.next = temp.next; //Updating the pointers
		temp.next.prev = temp.prev;

 		size--; //Decrementing the size
	}

	@Override
	public void deleteFirst() {// Deletes the first node
		delete(0); //using the predefined function delete at index 0
		
	}

	@Override
	public void deleteLast() { //Deletes the last node 
		delete(size-1);  // Using the predefined delete function at index size-1
		
		
	}

	@Override
	public void set(int i, T e) { //Setting or replacing a specific value at index i
		checkindex(i);  //Checking the index
		Node temp = Node1;
 		for(int j = 0; j <= i; j++) {  //Iterating the linked list to reach the given index
 			temp = temp.next;
 		}
 		temp.data = e; //Overwriting the value
		 
	}

	@Override
	public T get(int i) { //Getting the value at the index i
		Node temp = Node1;  
 		for(int j = 0; j <= i; j++) { //Iterating to reach the specified index
 			temp = temp.next;
 		}
 		return temp.data;  //Returns the data of the node at the index i
	}
	
	public void out() { //Function for printing the Linked List
		Node temp = Node1.next;
		for(int i =0; i < size; i++) {  //Iterate over the linked list and print the data
			System.out.print(temp.data + " ");
			temp = temp.next;
		}
		System.out.println();
	}
	 
 }
 