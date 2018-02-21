package prog05;

import java.util.EmptyStackException;

/** Implementation of the interface StackInt<E> using
*   an array.
*   @author Koffman & Wolfgang
*/

public class ArrayStack < E >
    implements StackInt < E > {

  // Data Fields
  /** Storage for stack. */
  E[] theData;
  /** Index to top of stack. */
  int size = 0; // Initially empty stack.
  private static final int INITIAL_CAPACITY = 2;
  
//------------------------------------------------------------------------------

  /** Construct an empty stack with the default initial capacity. */
  @SuppressWarnings("unchecked")
public ArrayStack() {
    theData = (E[])new Object[INITIAL_CAPACITY];
  }
 
//------------------------------------------------------------------------------

  /** Insert a new item on top of the stack.
      post: The new item is the top item on the stack.
            All other items are one position lower.
      @param obj The item to be inserted
      @return The item that was inserted
   */
  public E push(E obj) {
    if (size == theData.length) {
      reallocate();
    }
    theData[size++] = obj;
    return obj;
  }

 //------------------------------------------------------------------------------

  /** Remove and return the top item on the stack.
      pre: The stack is not empty.
      post: The top item on the stack has been
            removed and the stack is one item smaller.
      @return The top item on the stack
      @throws EmptyStackException if the stack is empty
   */
  public E pop() {
    if (empty()) {
      throw new EmptyStackException();
    }
    return theData[--size];
  }
  
//------------------------------------------------------------------------------

  /** Returns the object at the top of the stack
    * without removing it.
  	* post: The stack remains unchanged.
  	* @return The object at the top of the stack
  	* @throws EmptyStackException if stack is empty
    */
  
  public E peek() {
	  if (empty()) {
	      throw new EmptyStackException();
	  } else {
	  E obj = theData[size - 1];
	  return obj;
	  }
  }

//------------------------------------------------------------------------------

  /** Returns true if the stack is empty; otherwise,
	  returns false.
	  @return true if the stack is empty
    */

  public boolean empty() {
	if (size == 0)
		return true;
	if (theData[size - 1] == null)
		return true;
	else
		return false;
  }

//------------------------------------------------------------------------------
	
  @SuppressWarnings("unchecked")
public void reallocate(){
	E[]oldData = theData;
	theData = (E[])new Object[2 * oldData.length];
	System.arraycopy(oldData, 0, theData, 0, oldData.length);
  }

//------------------------------------------------------------------------------

}
