package prog12;

import java.util.*;



public class MyGoogle implements Google {
	
	private Map<String, Integer> URLtoID = new TreeMap<String, Integer>(); 
	private List<Integer> numRefs = new ArrayList<Integer>();
	private List<String> IDtoURL = new ArrayList<String>();
	
	private Map<String, Integer> WordtoID = new HashMap<String, Integer>();
	private List<List<Integer>> wordPageIDs = new ArrayList<List<Integer>>();
	
	
	public void addPage(String url) {
		URLtoID.put(url, URLtoID.size());
		numRefs.add(0);
		IDtoURL.add(url);
	}
	
	public void addWord (String word) {
		WordtoID.put(word, WordtoID.size());
		wordPageIDs.add(new ArrayList<Integer>());
	}
	
	
	public void gather(Browser browser, List<String> startingURLs) {
		
		int count = 0;
		ArrayDeque<String> urlQ = new ArrayDeque<String>();
		
		// Add startingURLs to Q
		for(int i = 0; i < startingURLs.size(); i++){
			String url = startingURLs.get(i);
			if(!URLtoID.containsKey(url)){
				addPage(url);
				urlQ.offer(url);
			}
		}
		
		while(!urlQ.isEmpty() && count++ < 100){
			
			String url = urlQ.poll();
			System.out.println(url);
			browser.loadPage(url);
			
			
			// Looks through URLs on page. Adds new refs and new pages to Q.
			List<String> pageURLs = browser.getURLs();
			List<String> addedRefs = new ArrayList<String>();
			for(int i = 0; i < pageURLs.size(); i++){
				String pageURL = pageURLs.get(i);
				if(!URLtoID.containsKey(pageURL)){
					addPage(pageURL);
					urlQ.offer(pageURL); 
					}
				if(!addedRefs.contains(pageURL)){
					addedRefs.add(pageURL);
					int pageID = URLtoID.get(pageURL);
					numRefs.set(pageID, numRefs.get(pageID)+1);
				}
			}
			
			// Looks through words on page.
			List<String> pageWords = browser.getWords();
			for(int i = 0; i < pageWords.size(); i++){
				String pageWord = pageWords.get(i);
				if(!WordtoID.containsKey(pageWord)){
					addWord(pageWord);
				}
				int wordID = WordtoID.get(pageWord);
				int pageID = URLtoID.get(url);
				List<Integer> pageIDs = wordPageIDs.get(wordID);
				if (pageIDs.size() == 0 || pageIDs.get(pageIDs.size()-1) != pageID)
					wordPageIDs.get(wordID).add(pageID);
			}
			
		}
		
		// Test print out.
		System.out.println("URLtoID:");
		System.out.println(URLtoID);
		System.out.println("IDtoURL:");
		System.out.println(IDtoURL);
		System.out.println("numRefs:");
		System.out.println(numRefs);
		System.out.println("WordtoID:");
		System.out.println(WordtoID);
		System.out.println("WordPageIDs:");
		System.out.println(wordPageIDs);
		
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[] search(List<String> keyWords, int numResults) {
		
		// Iterator into list of pageIDs for each key word.
		Iterator<Integer>[] pageIDiterators = (Iterator<Integer>[]) new Iterator[keyWords.size()];
		// Current pageID in each list, just behind the iterator.
		int[] currentPageIDs = new int[keyWords.size()];
		// LEAST popular page is at top of heap.
		PriorityQueue<PageID> bestPageIDs = new PriorityQueue<PageID>(numResults);
		
		// Initialize the entries of pageIDiterators.
		for (int i = 0; i < keyWords.size(); i++){
			String word = keyWords.get(i);
			int ID = WordtoID.get(word);
			System.out.println();
			System.out.println(word + "=" + ID);
			System.out.println(word + wordPageIDs.get(ID));
			Iterator iter = wordPageIDs.get(ID).iterator();
			pageIDiterators[i] = iter;
		}
		
		
		System.out.println();
		while(updateSmallest(currentPageIDs, pageIDiterators)){
			if(allEqual(currentPageIDs)){
				int pageID = currentPageIDs[0];
				PageID e = new PageID();
				e.setPageID(pageID);
				int refs = numRefs.get(pageID);
				e.setNumRefs(refs);
				System.out.println(pageID + "," + refs);
				bestPageIDs.add(e);
			}
		}
		
		String[] results = new String[numResults];
		for(int i = results.length - 1; !bestPageIDs.isEmpty(); i--){
			PageID e = bestPageIDs.poll();
			int pageID = e.getPageID();
			String url = IDtoURL.get(pageID);
			results[i] = url;
		}
		
		return results;
		
	}
	
	
	
	/** Look through currentPAgeIDs for all the smallest elements.
	 *  For each smallest element currentPageIDs[i], load the next element from
	 *  pageIDiterators[i]. If pageIDiterators[i] does not have a 
	 *  next element, return false. Otherwise, return true.
	 *  @param currentPageIDs : array of current page IDs.
	 *  @param pageIDiterators : array of iterators with next page IDs.
	 *  @return true if all minimum page IDs updates, false otherwise.
	 */
	private boolean updateSmallest (int[] currentPageIDs, Iterator<Integer>[] pageIDiterators) {
		
		/*
		ArrayList<Integer> smallIndexes = new ArrayList<Integer>();
		smallIndexes.add(0);
		
		for (int i = 1; i < currentPageIDs.length; i++){
			if(currentPageIDs[i] < currentPageIDs[smallIndexes.get(0)]) {
				smallIndexes.clear();
				smallIndexes.add(i); }
			if(currentPageIDs[i] == currentPageIDs[smallIndexes.get(0)])
				smallIndexes.add(i);
		}
		
		for (int i = 0; i < smallIndexes.size(); i++){
			int smallI = smallIndexes.get(i);
			if (pageIDiterators[smallI].hasNext()) 
				currentPageIDs[smallI] = pageIDiterators[smallI].next();
			else 
				return false;
			}
				
		return true;
		*/
		
		//The correct less complicated way...
		
		int smallest = currentPageIDs[0];
		
		for (int i = 0; i < currentPageIDs.length; i++) {
			if(currentPageIDs[i] < smallest) 
				smallest = currentPageIDs[i];
		}
		
		
		for (int i = 0; i < currentPageIDs.length; i++) {
			if(currentPageIDs[i] == smallest){
				if (pageIDiterators[i].hasNext()) 
					currentPageIDs[i] = pageIDiterators[i].next();
				else
					return false;
			}
		}
		return true;
	}
	
	
	
	/** Check if all elements in an array are equal.
	 * @param array : an array of numbers.
	 * @return true if all are equal, false otherwise
	 */
	private boolean allEqual (int[] array) {
		
		int E = array[0];
		
		for(int i = 1; i < array.length; i++){
			if (array[i] != E)
				return false;
		}
		return true;
	}
	
}
