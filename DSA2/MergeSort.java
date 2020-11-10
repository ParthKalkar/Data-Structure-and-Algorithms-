//Merge Sort Implementation

/* 1. Time complexity of Merge sort is O(nLogn). 
It is more efficient as in worst case also the runtime is O(nlogn) The space complexity of Merge sort is O(n).
*/

/* 2. A sorting algorithm is said to be stable if two objects with equal value appear in the same order in sorted output 
 as they appear in the input array to be sorted. 
 Merge Sort is a stable algorithm
*/

/* 3. The standard merge sort algorithm is an example of out-of-place algorithm as it requires O(n) extra space for merging. 
The merging can be done in-place but it increases the time complexity.
*/

package DSA2;

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
	
	public static void main(String[] args)
    {
        // Example using Strings
        String[] arrayOfStrings = {"Indra", "Xeana", "Arina", "Faroud", "Laura", "Quincy", "Milano", "Jessica", "Twan", "Nisha", "Birminsk", "Evgeniya", "Chaaya", "Kareena", "Monty", "Tara", "Dariya", "Polina"};
        MergeSort<String> SortString = new MergeSort<>();
        SortString.MergeSort(arrayOfStrings, 0, arrayOfStrings.length - 1);
        System.out.println(java.util.Arrays.toString(arrayOfStrings));

       
        
        // Example of Integers
        Integer[] arrayOfIntegers = {1,100, 11, 32, 2,0,121};
        MergeSort<Integer> SortInteger = new MergeSort<>();
        SortInteger.MergeSort(arrayOfIntegers, 0, arrayOfIntegers.length - 1);
        System.out.println(java.util.Arrays.toString(arrayOfIntegers));
        
        
        
        // Example using Doubles
        Double[] arrayOfDoubles = {0.135, 0.032, 0.236, 0.823, 0.217, 0.349, 0.1141, 0.27, 0.730, 0.809, 0.307, 0.166, 0.822, 0.317, 0.240, 0.965, 0.618, 0.275, 0.837, 0.529};
        MergeSort<Double> SortDouble = new MergeSort<>();
        SortDouble.MergeSort(arrayOfDoubles, 0, arrayOfDoubles.length - 1);
        System.out.println(java.util.Arrays.toString(arrayOfDoubles));
        

    }

   
}
