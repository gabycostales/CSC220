package prog04M;

import prog02.PhoneDirectory;
import java.io.*;
import java.util.Scanner;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class DLLBasedPD implements PhoneDirectory {
  /** The head (first entry) and tail (last entry) of the doubly
   * linked list that stores the directory data */
  protected DLLNode head, tail;
  
  /** The data file that contains the directory data */
  protected String sourceName = null;
    
  /** Boolean flag to indicate whether the directory was
      modified since it was either loaded or saved. */
  protected boolean modified = false;
    
  /** Method to load the data file.
      pre:  The directory storage has been created and it is empty.
      If the file exists, it consists of name-number pairs
      on adjacent lines.
      post: The data from the file is loaded into the directory.
      @param sourceName The name of the data file
  */
  public void loadData(String sourceName) {
    // Remember the source name.
    this.sourceName = sourceName;
    try {
      // Create a Scanner for the file.
      Scanner in = new Scanner(new File(sourceName));

      // Read each name and number and add the entry to the array.
      while (in.hasNextLine()) {
        String name = in.nextLine();
        String number = in.nextLine();
        // Add an entry for this name and number.
        add(name, number);
      }
      // Close the file.
      in.close();
    } catch (FileNotFoundException ex) {
      // Do nothing — no data to load.
      return;
    } catch (Exception ex) {
      System.err.println("Load of directory failed.");
      ex.printStackTrace();
      System.exit(1);
    }
  }
    
  /** Add an entry or change an existing entry.
      @param name The name of the person being added or changed
      @param number The new number to be assigned
      @return The old number or, if a new entry, null
  */
  public String addOrChangeEntry(String name, String number) {
    String oldNumber = null;
    DLLNode entry = find(name);
    if (entry != null) {
      oldNumber = entry.getValue();
      entry.setValue(number);
    } else {
      add(name, number);
    }
    modified = true;
    return oldNumber;
  }
    
  /** Look up an entry.
      @param name The name of the person
      @return The number. If not in the directory, null is returned
  */
  public String lookupEntry(String name) {
    DLLNode entry = find(name);
    if (entry != null)
      return entry.getValue();
    return null;
  }
    
  /** Method to save the directory.
      pre:  The directory has been loaded with data.
      post: Contents of directory written back to the file in the
      form of name-number pairs on adjacent lines.
      modified is reset to false.
  */
  public void save() {
    if (!modified)
      return; // If not modified, do nothing.

    try {
      // Create PrintStream for the file.
      PrintStream out = new PrintStream(new File(sourceName));
      
      // Write each directory entry to the file.
      for (DLLNode entry = head; entry != null; entry = entry.getNext()) {
        // Write the name.
        out.println(entry.getKey());
        // Write the number.
        out.println(entry.getValue());
      }
      
      // Close the file and reset modified.
      out.close();
      modified = false;
    } catch (Exception ex) {
      System.err.println("Save of directory failed");
      ex.printStackTrace();
      System.exit(1);
    }
  }
    
  /** Find an entry in the directory.
      @param name The name to be found
      @return The entry with the requested name.
      If the name is not in the directory, returns null
  */
  protected DLLNode find(String name) {
    for (DLLNode entry = head; entry != null; entry = entry.getNext())
      if (entry.getKey().equals(name))
        return entry;
    
    return null; // Name not found.
  }
  
  /** Add an entry to the directory.
      @param name The name of the new person
      @param number The number of the new person
  */
  protected void add(String name, String number) {
    DLLNode entry = new DLLNode(name, number);
    if (tail == null) {
      head = entry;
      tail = head;
    }
    else {
      tail.setNext(entry);
      entry.setPrevious(tail);
      tail = entry;
    }
    modified = true;
  }
    
  /** Remove an entry from the directory.
      @param name The name of the person to be removed
      @return The current number. If not in directory, null is
      returned
  */
  public String removeEntry(String name) {
    DLLNode entry = find(name);
    if (entry == null)
      return null;

    DLLNode next = entry.getNext();
    DLLNode previous = entry.getPrevious();
    
    if (previous != null)
      previous.setNext(next);
    else
      head = next;
      
    if (next != null)
      next.setPrevious(previous);
    else
      tail = previous;

    modified = true;
    return entry.getValue();
  }
}
