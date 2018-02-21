package prog12;

import java.util.List;

public interface Browser {
	
	/** Load URL page and make a list of words and URLs on the page respectively.
	 *  @param page to load 
	 *  @return true if successful */
    boolean loadPage (String url);
    
    /** @return list of words on URL page loaded */
    List<String> getWords ();
    
    /** @return list of URLs on the page loaded*/
    List<String> getURLs ();
    
}

