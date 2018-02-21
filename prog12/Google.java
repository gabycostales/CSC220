package prog12;

import java.util.Set;
import java.util.List;


@SuppressWarnings("unused")
public interface Google {
  
  /** Gather info from all web pages reachable from URLs in startingURLs. */
  void gather (Browser browser, List<String> startingURLs);

  /** Search for up to numResults pages containing all keyWords and
    * return them in an array in order of decreasing importance
    * (number of references). */
  String[] search (List<String> keyWords, int numResults);
  
}

