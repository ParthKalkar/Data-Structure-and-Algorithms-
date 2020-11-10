import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Parth Kalkar B-19-02 
//http://codeforces.com/group/3ZU2JJw8vQ/contest/269072/submission/71209544
public class Task3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList> contacts = new HashMap<String, ArrayList>(); 
		//For solving this task, I imported the Hashmap from the standard Java. 
		// I created a HasMap with keys = String and values = ArrayList
		//My String takes the input from the user 
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		input.nextLine();
		for (int i = 0; i < n; i++) {
			String a = input.nextLine();
			String[] substr = a.split(" ");
			String fullname, phone = "";
			
			//After taking the n - Strings from the user, I used the split function to get an array of individual words in my string
			
			//I introduced 2 new variables fullname - this will extract the name and phone - this will extract the phone number. 
			
			if (substr.length == 3) {
				String[] substr1 = substr[2].split(",");
				fullname = substr[1] + " " + substr1[0];
				if (substr1.length == 2)
					phone = substr1[1];
			} 
			
			// I checked some cases depending on the length of the string and thus modifying the full name and phone
			
			else {
				String[] substr1 = substr[1].split(",");
				fullname = substr1[0];
				if (substr1.length == 2)
					phone = substr1[1];
			}

			//The following if else clauses check the condition for Command statements.
			//They individually check for ADD, DELETE and FIND
			
			
			if (substr[0].equalsIgnoreCase("ADD")) {
				if (!contacts.containsKey(fullname))    // This is the condition to avoid adding the same number
				{
					contacts.put(fullname, new ArrayList()); //We check for the name and add the phone number
					contacts.get(fullname).add(phone);
				}
				else if (!contacts.get(fullname).contains(phone)){
					contacts.get(fullname).add(phone);
				}
			} else if (substr[0].equalsIgnoreCase("DELETE")) {
				if (contacts.containsKey(fullname)) { //First we check if we have some contact for the given name and then delete the whole contact
					if (phone == "") {
						
						contacts.remove(fullname);
					} else { 
						contacts.get(fullname).remove(phone);
						if(contacts.get(fullname).isEmpty()) {  // If the phone is empty after deleting then we delete the whole contact
							contacts.remove(fullname);
						}
					}
				}
			} else if (substr[0].equalsIgnoreCase("FIND")) { 
				if (!contacts.containsKey(fullname)) {  //Condition for checking the presence of the name
					System.out.println("No contact info found for " + fullname);
				} else {
					System.out.print("Found " + contacts.get(fullname).size() + " phone numbers for " + fullname + ":");
					for (int j = 0; j < contacts.get(fullname).size(); j++) {
						System.out.print(" " + contacts.get(fullname).get(j));
					}
					System.out.println();
				}
			}
		}
	}
}
