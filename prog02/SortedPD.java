package prog02;

public class SortedPD extends ArrayBasedPD {

    public String removeEntry(String name) {
		int i = find(name);
		if (i == -1)
			return null;
		DirectoryEntry entry = theDirectory[i];
		while (i <= size-1) {
		    theDirectory[i] = theDirectory[++i];
		    theDirectory[i] = null;
		}
		--size;
		modified = true;
		return entry.getNumber();
	}
    
    
   public void add(String name, String number) {
		if (size >= capacity) {
			reallocate();
		}
		 int i = size;
		 while ( i-1 >= 0 && name.compareTo(theDirectory[i-1].getName()) < 0){
			 theDirectory[i] = theDirectory[i-1];
			 i--;			 
		 }
		 theDirectory[i] = new DirectoryEntry(name, number);
		 size++;
	}
   
	
	protected int find(String name) {
		int first = 0;
		int last = size-1;
		while (first <= last){
			int middle = (first + last)/2;
			int cmp = name.compareTo(theDirectory[middle].getName());
			if (cmp == 0){
				return middle;				
			}
			if (cmp < 0){
				last = middle - 1;
			}
			if (cmp > 0){
				first = middle + 1;
			}
		}
		return -1;  //Name not found.
	}

}
