package prog06M;

import prog02.GUI;
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WordGame {
	static GUI ui = new GUI();

	public static void main (String[] args) {
		WordGame game = new WordGame();
		game.loadDictionary(ui.getInfo("Enter dictionary file:"));

		String start = ui.getInfo("Enter starting word:");
		String target = ui.getInfo("Enter target word:");

		String[] commands = { "Computer plays.", "Human plays." };
		int c = ui.getCommand(commands);

		if (c == 1)
			game.play(start, target);
		else
			game.solve(start, target);
	}

	void play (String start, String target) {
		while (true) {
			ui.sendMessage("Current word: " + start + "\n" +
					"Target word: " + target);
			String word = ui.getInfo("What is your next word?");
			if (find(word) == -1)
				ui.sendMessage(word + " is not in the dictionary.");
			else if (!oneDegree(start, word))
				ui.sendMessage("Sorry, but " + word +
						" differs by more than one letter from " + start);
			else if (word.equals(target)) {
				ui.sendMessage("You win!");
				return;
			}
			else
				start = word;
		}
	}    

	static boolean oneDegree (String snow, String slow) {
		if (snow.length() != slow.length())
			return false;
		int count = 0;
		for (int i = 0; i < snow.length(); i++)
			if (snow.charAt(i) != slow.charAt(i))
				count++;
		return count == 1;
	}

	List<String> words = new ArrayList<String>();

	void loadDictionary (String file) {
		try {
			Scanner in = new Scanner(new File(file));
			while (in.hasNextLine())
				words.add(in.nextLine());
		} catch (Exception e) {
			ui.sendMessage("Uh oh: " + e);
		}
	}

	int find (String word) {
		for (int i = 0; i < words.size(); i++)
			if (word.equals(words.get(i)))
				return i;
		return -1;
	}

	void solve (String start, String target) {
		Queue<String> queue = new LinkedList<String>();
		prog02.PhoneDirectory pd = new prog02.SortedPD();

		int iStart = find(start);
		words.set(iStart, null);
		queue.offer(start);
		while (!queue.isEmpty()) {
			String word = queue.poll();
			System.out.println("DEQUEUE: " + word);
			System.out.print("ENQUEUE:");
			for (int i = 0; i < words.size(); i++) {
				String wordI = words.get(i);
				if (wordI != null && oneDegree(word, wordI)) {
					words.set(i, null);
					queue.offer(wordI);
					System.out.print(" " + wordI);
					pd.addOrChangeEntry(wordI, word);
					if (wordI.equals(target)) {
						ui.sendMessage("Got to " + target + " from " + word);
						String s = word + "\n" + target;
						while (!word.equals(start)) {
							word = pd.lookupEntry(word);
							s = word + "\n" + s;
						}
						ui.sendMessage(s);
						return;
					}
				}
			}
			System.out.println();
		}
	}
}