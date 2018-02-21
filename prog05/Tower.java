package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;
import prog05.Tower.Goal;

@SuppressWarnings("unused")
public class Tower {
  static UserInterface ui = new GUI();

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

//------------------------------------------------------------------------------
  
  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels or enters an empty string.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null || number.length() == 0)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

//------------------------------------------------------------------------------
  
  int nDisks;
  @SuppressWarnings("unchecked")
StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  Tower (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();

    // Initialize game with pile of nDisks disks on peg 'a' which is pegs[0].
    for(int i = nDisks; i > 0; i--){
    	pegs[0].push(i);
    }
  }
  
//------------------------------------------------------------------------------

  static String[] fromChoices = { "FROM a", "FROM b", "FROM c" };
  static String[] toChoices = { "TO a", "TO b", "TO c" };

  void play () {
    while (!(pegs[0].empty() && pegs[1].empty())) {
      displayPegs();
      int from = ui.getCommand(fromChoices);
      int to = ui.getCommand(toChoices);
      move(from, to);
    }

    ui.sendMessage("You win!");
  }
  
//------------------------------------------------------------------------------

  String stackToString (StackInt<Integer> peg) {
    StackInt<Integer> helper = new ArrayStack<Integer>();

    // String to append items to.
    String s = "";

    // Append the items in peg to s from bottom to top.
    while(!peg.empty()){
    	helper.push(peg.pop());
    }
    while(!helper.empty()){
    	s = s + peg.push(helper.pop());
    }
    return s;
  }
  
//------------------------------------------------------------------------------

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + ": " + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }
  
//------------------------------------------------------------------------------

  void move (int from, int to) {
    // EXERCISE:  move one disk from pegs[from] to pegs[to].
    // Don't do illegal moves.  Send a warning message instead, like:
    // Cannot place 2 on top of 1.
	if(!pegs[to].empty()){
	  if(pegs[from].peek() > pegs[to].peek()){
		ui.sendMessage("Cannot place " + pegs[from].peek() + " on top of " + pegs[to].peek() + ".");
		return;
	  }
	}
	if(pegs[from].empty()){
		ui.sendMessage("Cannot take disk from an empty peg.");
		return;
  }
	
	pegs[to].push(pegs[from].pop());
	
  }
 
//==============================================================================

  static String[] pegNames = { "a", "b", "c" };

  class Goal {
    int howMany, fromPeg, toPeg;

    Goal (int howMany, int from, int to) {
      this.howMany = howMany;
      this.fromPeg = from;
      this.toPeg = to;
    }

    public String toString () {
      return ("Move " + howMany + " disk(s) from " +
              pegNames[fromPeg] + " to " + pegNames[toPeg] + ".");
    }
  }

//------------------------------------------------------------------------------
  
  void solve () {
	  
	  StackInt<Goal> goals = new ArrayStack<Goal>();
	  
	  goals.push(new Goal(nDisks, 0, 2));
	  
	  displayPegs();
	  
	  while (!goals.empty()){
		  displayGoals(goals);
		  Goal nextGoal = goals.pop();
		  
		  if (nextGoal.howMany == 1){
			  move((nextGoal.fromPeg), (nextGoal.toPeg));
			  displayPegs();
		  
		  } else {  
			  int n = nextGoal.howMany;
			  int otherPeg = 0;
			  
			  // figure out which is the other peg
			  int test = nextGoal.fromPeg + nextGoal.toPeg;
			  if(test == 1)
				  otherPeg = 2;
			  if(test == 2)
				  otherPeg = 1;
			  if(test == 3)
				  otherPeg = 0;
			  
			  // break next big goal into three subgoals
			  Goal subG1 = new Goal(n-1, nextGoal.fromPeg, otherPeg);
			  Goal subG2 = new Goal(1, nextGoal.fromPeg, nextGoal.toPeg);
			  Goal subG3 = new Goal(n-1, otherPeg, nextGoal.toPeg);
			  
			  // push the subgoals onto stack
			  goals.push(subG3);
			  goals.push(subG2);
			  goals.push(subG1);
			  
		  }
	  }

  }
  
//------------------------------------------------------------------------------

  void displayGoals (StackInt<Goal> goals) {
    
	StackInt<Goal> helper = new ArrayStack<Goal>();
	String s = "";
	
	// displays the contents of the goal stack in order.
	    while(!goals.empty()){
	    	helper.push(goals.pop());
	    	Goal printGoal = helper.peek();
	    	s = s + printGoal.toString() + "\n";
	    }
	    while(!helper.empty()){
	    	  goals.push(helper.pop());
	    }
	    
	    ui.sendMessage(s);

  } 

//------------------------------------------------------------------------------
}