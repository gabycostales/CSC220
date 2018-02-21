package prog04;

/** Entry in doubly linked list
 */
public class DLLNode {
  /** The name of the individual or whatever is used to look up an entry. */
  private String key;

  /** The number of individual or whatever value is stored for that entry. */
  private String value;

  /** The next entry in the list. */
  private DLLNode next;

  /** The previous entry in the list. */
  private DLLNode previous;

  /** Creates a new instance of DLLNode
      @param key name of person
      @param value number of person
  */
  public DLLNode (String key, String value) {
    this.key = key;
    this.value = value;
  }

  /** Retrieves the key
      @return the key, the name of the individual
  */
  public String getKey () {
    return key;
  }
    
  /** Retrieves the value
      @return the value, the number of the individual
  */
  public String getValue () {
    return value;
  }
  
  /** Sets the number to a different value.
      @param value the new number to set it to
  */
  public void setValue (String value) {
    this.value = value;
  }

  /** Gets the next entry in the list.
      @return the next entry
  */
  public DLLNode getNext () {
    return next;
  }

  /** Sets the next entry in the list.
      @param next the new next entry
  */
  public void setNext (DLLNode next) {
    this.next = next;
  }

  /** Gets the previous entry in the list.
      @return the previous entry
  */
  public DLLNode getPrevious () {
    return previous;
  }

  /** Sets the previous entry in the list.
      @param previous the new previous entry
  */
  public void setPrevious (DLLNode previous) {
    this.previous = previous;
  }
}


