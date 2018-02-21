package prog12;

public class PageID implements Comparable<PageID> {

	private int numRefs;
	private int PageID;
	
	public void setPageID(int pageID) { this.PageID = pageID; }
	public void setNumRefs(int numRefs) { this.numRefs = numRefs; }
	
	public int getPageID() { return this.PageID; }
	public int getNumRefs() { return this.numRefs; }
	
	public int compareTo(PageID that) {
		return this.numRefs - that.numRefs;
	}

}
