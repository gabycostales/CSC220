package prog06;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import prog02.ArrayBasedPD;

public class WordGame {
	
	static List<String> words = new ArrayList<String>();
	static UserInterface ui = new GUI();
	static String start, target;
	
//------------------------------------------------------------------------------
	
	public static void main (String[] args) {
		
		WordGame game = new WordGame();
		
		// Load the dictionary file.
		String fn = ui.getInfo("Enter the file name of the dictionary (include ext. if on windows): ");
		words = game.loadDictionary(fn);
		if (words == null)
			return;
		
		// Get starting word
		boolean cs = true;
		while (cs){
		start = ui.getInfo("Enter the starting word: ");
			// Check if it is in the dictionary
			int s = find(start);
			if(s == -1)
				ui.sendMessage("That word is not in the dictionary. Try again.");
			else
				cs = false;
		}
			
		// Get Target word
		boolean ct = true;
		while (ct){
		target = ui.getInfo("Enter the target word: ");
			// Check if it is in the dictionary'
			int t = find(target);
			if(t == -1 || target.length() != start.length())
				ui.sendMessage("That word is not in the dictionary " +
							   "or it is not the same length as the starting word." +
							   "\n" + "Try again.");
			else
				ct = false;
		}	
			
		// Run game
		String[] commands = { "Human plays.", "Computer plays." };
		   int c = ui.getCommand(commands);
		   if (c == 0)
		      game.play(start, target);
		   else
		      game.solve(start, target);
		
	}

	
//------------------------------------------------------------------------------
	
private List<String> loadDictionary (String fn) {
	
		try {
			// Create a BufferedReader for the file.
			Scanner in = new Scanner(new File(fn));
			String word;
			// Read each word and add it to the list
			while (in.hasNextLine()) {
				word = in.nextLine();
				words.add(word);	
			}
			// Close the file.
			in.close();
		} catch (FileNotFoundException e) {
			ui.sendMessage("File could not be found."); 
			return null;
		}
		
	return words;
	
	}

//------------------------------------------------------------------------------

	private static int find (String word) {
		return words.indexOf(word);
	}

//------------------------------------------------------------------------------

	private void play (String start, String target) {
		
		String current = start;
		while (true)
		{
			ui.sendMessage("Current word: " + current + "\n" +
						   "Target word: " + target);
			String next = ui.getInfo("Enter the next word: ");
			if (next.equals(target)) {
				ui.sendMessage("You win!");
				return;
			} else {
				boolean check = oneDegree(current, next);
				if (!check) 
					ui.sendMessage("Can't use that word. Try again.");
				else
					current = next;
			}
		}
	}
	
//------------------------------------------------------------------------------
	
	private static boolean oneDegree (String current, String next){
		
		// Checks if words are of equal length
		if (next.length() != current.length()) 
			return false;
		
		// Checks if words are different by 1 char. 
		int n = next.length();
		char [] word1 = next.toCharArray();
		char [] word2 = current.toCharArray();
		int counter = 0;
		for(int i = 0; i < n; i++){
			if (word1[i] != word2[i]) {
				counter++;
			}
		}
		if (counter == 1)
			return true;
		else 
			return false;
	}
	
//------------------------------------------------------------------------------

	private void solve (String start, String target) {
		
		Queue<String> wordQ = new ArrayQueue<String>();
		ArrayBasedPD pd = new ArrayBasedPD();
		String path = "";
		String name, number;
		
		
		wordQ.offer(start);
		int startI = find(start);
		words.set(startI, null);
		
		while (!(wordQ.isEmpty())){
			String current = wordQ.poll();
			for (String wordI : words){
				if(wordI != null && oneDegree(current, wordI)){
					words.set(find(wordI), null);
					wordQ.offer(wordI);	
					pd.addOrChangeEntry(wordI, current);
					if(wordI.equals(target)){
						ui.sendMessage("Success! Got to " + target + " from " + current);
						name = wordI;
						path = target;
						while(name != start){
							number = pd.lookupEntry(name);
							path = number + "\n" + path;
							name = number;
						}
						ui.sendMessage(path);
						return;
					}	
				}
			}
		}
	}

//------------------------------------------------------------------------------

}
