import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;
 
public class QuickSort extends RecursiveTask<Integer> {
 
    int start, end;
    int[] array;
 
    private int partition(int start, int end,int[] array)
    {
 
        int i = start, j = end;
        int pivote = start;
        int t = array[j];
        array[j] = array[pivote];
        array[pivote] = t;
        j--;
        while (i <= j) {
 
            if (array[i] <= array[end]) {
                i++;
                continue;
            }
 
            if (array[j] >= array[end]) {
                j--;
                continue;
            }
 
            t = array[j];
            array[j] = array[i];
            array[i] = t;
            j--;
            i++;
        }
        t = array[j + 1];
        array[j + 1] = array[end];
        array[end] = t;
        return j + 1;
    }
    public QuickSort(int start,int end,int[] array)
    {
        this.array = array;
        this.start = start;
        this.end = end;
    }
 

    protected Integer compute()
    {
        if (start >= end)
            return null;
        int p = partition(start, end, array);
        QuickSort left = new QuickSort(start,p - 1,array);
        QuickSort right = new QuickSort(p + 1,end,array);
 
        left.fork();
        right.fork();
 
        
        left.join();
        right.join();

        return null;
    }
 
    // Driver Code
    public static void main(String[] args) throws IOException {
      
		ForkJoinPool pool = new ForkJoinPool();
		Scanner sc = new Scanner(System.in);
        
		System.out.print("Please input the length of the array : ");
		int n = sc.nextInt();
		

		int[] array = new int[n];

		System.out.print("Do you want to generate the random elements automatically (if yes click y else n): ");
		char opt = sc.next().charAt(0);
		if (opt == 'y') {
			// create SIZE random integers between 0 and 999
			java.util.Random rand = new java.util.Random();
			for (int i = 0; i < n; ++ i) 
				array[i] = rand.nextInt(1000);
			System.out.print("The original array: ");
			System.out.println(Arrays.toString(array));
		} else if (opt == 'n') {
				System.out.println("Please input the array elements: ");
			try{
			for (int i = 0; i < n; ++ i)
				array[i] = sc.nextInt();      
			}
			catch(Exception e){
				System.out.println("Please give the Integer value only");
			}
			
		} 
		else {
			System.out.println("Error: invalid input!");
			System.exit(1);
		}
		sc.close();


	    pool.invoke(new QuickSort(0, n - 1, array));

		System.out.print("The array after sorting: ");
		System.out.println(Arrays.toString(array));
	}
}