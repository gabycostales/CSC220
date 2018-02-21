package prog04M;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
  /** Find an entry in the directory.
      @param name The name to be found
      @return The entry with the requested name.
      If the name is not in the directory, returns null
  */
  protected DLLNode find(String name) {
    for (DLLNode entry = head; entry != null; entry = entry.getNext()) {
      int cmp = name.compareTo(entry.getKey());
      if (cmp == 0)
        return entry;
      if (cmp < 0)
        return null;
    }
    
    return null; // Name not found.
  }
  
  /** Add an entry to the directory.
      @param name The name of the new person
      @param number The number of the new person
  */
  protected void add(String name, String number) {
    DLLNode entry = new DLLNode(name, number);

    DLLNode previous = tail;
    DLLNode next = null;
    while (previous != null && name.compareTo(previous.getKey()) < 0) {
      next = previous;
      previous = previous.getPrevious();
    }

    entry.setNext(next);
    entry.setPrevious(previous);

    if (next != null)
      next.setPrevious(entry);
    else
      tail = entry;

    if (previous != null)
      previous.setNext(entry);
    else
      head = entry;

    modified = true;
  }
}
