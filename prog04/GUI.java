package prog04;

import javax.swing.*;

/** @author vjm */

public class GUI implements UserInterface {
	
	/** Creates a new instance of GUI */
	public GUI() {
	}
	
	
	//----------------------------------------------------------
	
	/** presents set of commands for user to choose one of
	 * @param commands: the commands to choose from
	 * @return the index of the command in the array
	 */
	public int getCommand (String[] commands){
		return JOptionPane.showOptionDialog(null, // No parent
		                    "Select Command ", //Prompt message
	                        "Linked List Directory", //Title
	                        JOptionPane.YES_NO_CANCEL_OPTION, // Option type
	                        JOptionPane.QUESTION_MESSAGE, //Message type
	                        null, //Icon
	                        commands, //List of commands
	                        commands[commands.length-1]);
	}
	
	//----------------------------------------------------------

	/** tells the user something
	 * @param message string to print out to the user
	 */
	public void sendMessage (String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	//----------------------------------------------------------

	/** prompts the user for a string
	 * @param prompt: the request
	 * @return what the user enters, null if nothing
	 */
	public String getInfo (String prompt){
		return JOptionPane.showInputDialog(prompt);
	}
	
	//----------------------------------------------------------
	
	public static void main (String[] args){
		UserInterface ui = new GUI();
		String[] commands = { "hello", "how", "are", "you" };
		int choice = ui.getCommand(commands);
		ui.sendMessage("You chose " + choice);
		String result = ui.getInfo("say something");
		ui.sendMessage("You said " + result);		
	}

}

//==============================================================