package prog04;

public class SortedDLLPD extends DLLBasedPD {
	
	/** Add an entry to the directory.
      * @param name The name of the new person
      * @param number The number of the new person
	  */

	protected void add(String name, String number) {
		
		DLLNode entry = new DLLNode(name, number);
		
		DLLNode next;
		DLLNode previous = tail;
		
		//Find right spot using previous
		while (previous != null && name.compareTo(previous.getKey()) < 0){
			previous = previous.getPrevious();
		}
		
		//Set next using previous
		if (previous == null)
			next = head;
		else
			next = previous.getNext();
		
		//Add entry to the list
		//Set Previous of Entry and Next of Previous
		if (previous != null){
			entry.setPrevious(previous);
			previous.setNext(entry);
		} else {
			head = entry;
			entry.setPrevious(null);
		}
		
		//Set Next of Entry and Previous of Next
		if (next != null) {
			entry.setNext(next);
			next.setPrevious(entry);
		} else {
			tail = entry;
			entry.setNext(null);
		}
	
		modified = true;
		
	}

//------------------------------------------------------------------------------

	/** Find an entry in the directory.
      * @param name The name to be found
      * @return The entry with the requested name.
      * If the name is not in the directory, returns null
	  */

	protected DLLNode find(String name) {
	  
		// Stops searching if entry can't be found
		for (DLLNode node = head; node != null; node = node.getNext()){
			if (name.compareTo(node.getKey()) >= 0) {
				if (node.getKey().equals(name))
					return node;
			} else {
				return null;
			}
		}
		
		return null; // Name not found.
  
	}

//------------------------------------------------------------------------------

}
