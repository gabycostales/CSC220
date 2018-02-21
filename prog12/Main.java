package prog12;

import java.util.*;



public class Main {
    public static void main (String[] args) {
	
    Browser browser = new MyBrowser();
	Google google = new MyGoogle();

	List<String> startingURLs = new ArrayList<String>();
	startingURLs.add("http://www.cs.miami.edu/~vjm/csc220/google/mary.html");

	google.gather(browser, startingURLs);

	List<String> keyWords = new ArrayList<String>();
	keyWords.add("mary");
	keyWords.add("jack");
	keyWords.add("jill");

	String[] urls = google.search(keyWords, 10);

	System.out.println();
	System.out.println("Found " + keyWords + " on");
	for (int i = 0; i < urls.length; i++)
		if(urls[i] != null)
	    System.out.println(urls[i]);
	
    }
}
