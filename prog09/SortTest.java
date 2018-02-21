package prog09;
import java.util.Random;
import prog02.UserInterface;
import prog02.GUI;

public class SortTest<E extends Comparable<E>> {
  public void test (Sorter<E> sorter, E[] array) {
    E[] copy = array.clone();
    sorter.sort(copy);
    if (array.length < 100){
    	System.out.println(sorter);
    	for (int i = 0; i < copy.length; i++)
    		System.out.print(copy[i] + " ");
    	System.out.println();
    }
  }
 
public static void main (String[] args) {
    //Integer[] array = { 3, 1, 4, 1, 5, 9, 2, 6 };
    
    UserInterface ui = new GUI();
	String aSize = ui.getInfo("Enter array size: ");
	int size = Integer.parseInt(aSize);
	
	Integer[] array = new Integer[size];
	//System.out.println(array.length);

    if (array.length > 0) {
      // Print out command line argument if there is one.
      //System.out.println("args[0] = " + args[0]);

      // Create a random object to call random.nextInt() on.
      Random random = new Random(0);

      // Make array.length equal to args[0] and fill it with random
      // integers:
      for(int n = 0; n < array.length; n++){
    	  array[n] = random.nextInt(100);
      }

    }

    SortTest<Integer> tester = new SortTest<Integer>();
    tester.test(new InsertionSort<Integer>(), array);
    tester.test(new HeapSort<Integer>(), array);
    tester.test(new QuickSort<Integer>(), array);
    tester.test(new MergeSort<Integer>(), array);
    
    // Average Times
    tester.time(new InsertionSort<Integer>(), array);
    // n = 1000: 1.56
    // n = 10000: 154.4
    tester.time(new HeapSort<Integer>(), array);
    // n = 1000: 0.1701
    // n = 10000: 2.308
    // n = 10000000: 4191.0
    tester.time(new QuickSort<Integer>(), array);
    // n = 1000: 0.1451
    // n = 10000: 5.476
    // n = 10000000: Stack Overflow?
    tester.time(new MergeSort<Integer>(), array);
    // n = 1000: 0.1263
    // n = 10000: 1.575
    // n = 10000000: 2555.0
  }

public void time (Sorter <E> sorter, E[] array){
	
	int ncalls = 1;
	double averageTime = time(sorter, array, ncalls);
	while(ncalls * averageTime <= 1000){
		ncalls *= 10;
		averageTime = time(sorter, array, ncalls);
	}
	System.out.println(sorter);
	System.out.println("debug " + (ncalls * averageTime));
	System.out.println(averageTime);
}

public double time (Sorter <E> sorter, E[] array, int ncalls){
	
	long start = System.currentTimeMillis();
	
	E[] copy = array.clone();
	for(int i = 0; i < ncalls; i++){
		sorter.sort(copy);
		System.arraycopy(array, 0, copy, 0, array.length);
	}
	long end = System.currentTimeMillis();
	return (double)(end - start) / ncalls;
	}


}


class InsertionSort<E extends Comparable<E>>
  implements Sorter<E> {
  public void sort (E[] array) {
    for (int n = 0; n < array.length; n++) {
      E data = array[n];
      int i = n;
      // while array[i-1] > data move array[i-1] to array[i] and
      // decrement i
      while( i > 0 && array[i-1].compareTo(data) > 0){
          array[i] = array[i-1];
          i--;
      }
      array[i] = data;
    }
  }
}

class HeapSort<E extends Comparable<E>>
  implements Sorter<E> {
 
  private E[] array;
 
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
 
  public void sort (E[] array) {
    this.array = array;
   
    for (int i = parent(array.length - 1); i >= 0; i--)
      swapDown(i, array.length - 1);
   
    for (int n = array.length - 1; n >= 0; n--) {
      swap(0, n);
      swapDown(0, n - 1);
    }
  }
 
  public void swapDown (int root, int end) {
   
    // Calculate the left child of root.
          int leftC = left(root);
          int rightC, biggerC;
   
    // while the left child is still in the array
    //   calculate the right child
    //   if the right child is in the array and
    //      it is bigger than than the left child
    //         bigger child is right child
    //   else
    //     bigger child is left child
    //   if the root is not less than the bigger child
    //     return
    //   swap the root with the bigger child
    //   update root and calculate left child
         
          while(leftC < end) {
              rightC = right(root);
              if (rightC < end &&
                      array[rightC].compareTo(array[leftC]) >= 0)
                  biggerC = rightC;
              else
                  biggerC = leftC;
              if (!(array[root].compareTo(array[biggerC]) <= 0))
                  return;
              swap(root, biggerC);
              root = biggerC;
              leftC = left(root);
          }
  }
 
  private int left (int i) { return 2 * i + 1; }
  private int right (int i) { return 2 * i + 2; }
  private int parent (int i) { return (i - 1) / 2; }
}

class QuickSort<E extends Comparable<E>>
  implements Sorter<E> {
 
  private E[] array;
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
 
  public void sort (E[] array) {
    this.array = array;
    sort(0, array.length-1);
  }
 
  private void sort(int left, int right) {
    if (left >= right)
      return;
   
    swap(left, (left + right) / 2);
   
    E pivot = array[left];
    int a = left + 1;
    int b = right;
    while (a <= b) {
      // Move a forward if array[a] <= pivot
        while(a < array.length && array[a].compareTo(pivot) <= 0) {
            a++; }
      // Move b backward if array[b] > pivot
        while(b > 0 && array[b].compareTo(pivot) > 0){
            b--; }
      // Otherwise swap array[a] and array[b]
        if(a <= b){
            swap(a,b);
            a++;
            b--;
        }
    }
   
    swap(left, b);
   
    sort(left, b-1);
    sort(b+1, right);
  }
}

class MergeSort<E extends Comparable<E>>
  implements Sorter<E> {
 
  private E[] array, array2;
 
  public void sort (E[] array) {
    this.array = array;
    array2 = array.clone();
    sort(0, array.length-1);
  }
 
  private void sort(int left, int right) {
    if (left >= right)
      return;
   
    int middle = (left + right) / 2;
    sort(left, middle);
    sort(middle+1, right);
   
    int i = left;
    int a = left;
    int b = middle+1;
    while (a <= middle && b <= right) {
      // If both a <= middle and b <= right
        // copy the smaller of array[a] or array[b] to array2[i]
            if(array[a].compareTo(array[b]) < 0)
                array2[i++] = array[a++];
            else
                array2[i++] = array[b++];       
    }
      // Otherwise just copy the remaining elements to array2
    while(a <= middle)
        array2[i++] = array[a++];
    while(b <= right)
        array2[i++] = array[b++];
   
         
    System.arraycopy(array2, left, array, left, right - left + 1);
   
  }
}
