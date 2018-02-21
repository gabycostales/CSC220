package prog06M;

import javax.swing.JOptionPane;
import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Class to maintain a queue of customers.
 * @author Koffman & Wolfgang
 **/
public class MaintainQueue {

    // Data Field
    private Queue<String> customers;

    // Constructor
    /** Create an empty queue. */
    public MaintainQueue() {
        customers = new LinkedList<String>();
    }
    
    public static void main (String[] args) {
    	int[] a={1,2}, b={1,2};
    	System.out.println(a.equals(b));
    	MaintainQueue mq = new MaintainQueue();
    	mq.processCustomers();
    }

    /**
     * Performs the operations selected on queue customers.
     * @pre  customers has been created.
     * @post customers is modified based on user selections.
     */
    public void processCustomers() {
        int choiceNum = 0;
        String[] choices = {
            "add", "peek", "remove", "size", "position", "quit"};

        // Perform all operations selected by user.
        while (choiceNum < choices.length - 1) {
            // Select the next operation.
            choiceNum = JOptionPane.showOptionDialog(null,
                    // "Select an operation on customer queue",
            		this + "",
                    "Queue menu",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    choices, choices[0]);

            // Process the current choice.
            try {
                String name;
                switch (choiceNum) {
                    case 0:
                        name = JOptionPane.showInputDialog("Enter new customer name");
                        if (name != null) {
                          customers.offer(name);
                          JOptionPane.showMessageDialog(null,
                                "Customer " + name
                                + " added to the queue");
                        }
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null,
                                "Customer " + customers.element()
                                + " is next in the queue");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null,
                                "Customer " + customers.remove()
                                + " removed from the queue");
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(null,
                                "Size of queue is " + customers.size());
                        break;
                    case 4:
                        name = JOptionPane.showInputDialog("Enter customer name");
                        int countAhead = 0;
                        for (String nextName : customers) {
                            if (!nextName.equals(name)) {
                                countAhead++;
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "The number of customers ahead of "
                                        + name + " is " + countAhead);
                                break; // Customer found, exit loop.
                            }
                        }

                        // Check whether customer was found.
                        if (countAhead == customers.size()) {
                            JOptionPane.showMessageDialog(null,
                                    name + " is not in queue");
                        }
                        break;
                    case 5:
                        JOptionPane.showMessageDialog(null,
                                "Leaving customer queue. "
                                + "\nNumber of customers in queue is "
                                + customers.size());
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,
                                "Invalid selection");
                        break;
                }
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog(null,
                        "The Queue is empty", "",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

  /**
   * Create a string representation of the queue contents with each
   * entry on its own line
   * @return a String representation of the queue contents
   */
  @Override
    public String toString() {
    String s = "";
    for (String name : customers)
      s = s + name + " ";
    return s;
    /*
      StringBuilder stb = new StringBuilder();
      for (String next : customers) {
      stb.append(next);
      stb.append("\n");
      }
      return stb.toString();
    */
  }
}
