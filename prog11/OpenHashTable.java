package prog11;

import java.util.*;

@SuppressWarnings("unused")
public class OpenHashTable<K, V> {
    
	private static class Node<K, V> {
	K key;
	V value;

	Node (K key, V value) {
	    this.key = key;
	    this.value = value;
	}
    }
	
//------------------------------------------------------------------------------

    private final static int DEFAULT_CAPACITY = 5;
    @SuppressWarnings("unchecked")
	private Node<K,V>[] table = new Node[DEFAULT_CAPACITY];
    private Node<K,V> DELETED = new Node<K,V>(null, null);
    private int size;
    private int nonNull;

    private int hashIndex (Object key) {
	int index = key.hashCode() % table.length;
	if (index < 0)
	    index += table.length;
	return index;
    }
    
//------------------------------------------------------------------------------

    // Linear probe sequence: start at hashIndex(key) and increment,
    // but roll around to zero at the end of the table.

    // Return the index of the Node with key if it is in the probe
    // sequence.

    // If it is not there, return the index where the Node with key
    // should be inserted.  If there is a deleted Node in the probe
    // sequence, return the index of the *first* deleted Node in the
    // sequence.

    // Otherwise return the index of the first null in the probe
    // sequence.
    private int find (Object key) {
    	
    	int index = hashIndex(key);
    	int firstDeleted = -1;
    	
    	if(table[index] != null && key.equals(table[index].key))
    		return index;
    	
    	
    	while(table[index] != null &&
    		 (table[index].equals(DELETED) || !(key.equals(table[index].key)))){
    			if (table[index].equals(DELETED) && firstDeleted == -1)
    				firstDeleted = index;
    			else
    				index = (index + 1) % table.length;
    	}
    	
    	if(table[index] != null && key.equals(table[index].key) || firstDeleted == -1)
    		return index;
    	else
    		return firstDeleted;
    	
	
    }

//------------------------------------------------------------------------------

    public boolean containsKey (Object key) {
	Node<K,V> node = table[find(key)];
	return node != null && node != DELETED;
    }
    
//------------------------------------------------------------------------------

    public V get (Object key) {
	Node<K,V> node = table[find(key)];
	if (node == null || node == DELETED)
	    return null;
	return node.value;
    }
    
//------------------------------------------------------------------------------

    public V put (K key, V value) {
	System.out.println("put " + key + " " + value);
	int index = find(key);
	System.out.println("index of " + key + " is " + hashIndex(key));
	//System.out.println("find thinks its " + index);
	Node<K,V> node = table[index];
	if (node == null || node == DELETED) {
	    table[index] = new Node<K,V>(key, value);
	    size++;
	    nonNull++;
	    if (nonNull > table.length/2)
		rehash(4 * size);
	    return null;
	}
	V old = node.value;
	node.value = value;
	return old;
    }
    
//------------------------------------------------------------------------------

    public V remove (Object key) {
	System.out.println("remove " + key);
	int index = find(key);
	System.out.println("hashIndex at " + hashIndex(key));
	//System.out.println("find thinks its at " + index);
	Node<K,V> node = table[index];
	if (node == null || node == DELETED)
	    return null;
	table[index] = DELETED;
	size--;
	return node.value;
    }
    
//------------------------------------------------------------------------------

    @SuppressWarnings({"unchecked" })
	private void rehash (int newCapacity) {
    	Node<K, V>[] oldTable = table;
    	Node<K, V>[] newTable = new Node[newCapacity];
    	table = newTable;
    	int i = 0;
    	size = 0;
    	nonNull = 0;
    	while ( i < oldTable.length){
    		Node<K, V> oldie = oldTable[i];
    		if(oldie != null && !oldie.equals(DELETED))
    			put(oldie.key, oldie.value);
    		i++;
    	}
    }
    
//------------------------------------------------------------------------------

    public String toString () {
	String ret = "------------------------------\n";
	for (int i = 0; i < table.length; i++) {
	    ret = ret + i + ": ";
	    Node<K,V> node = table[i];
	    if (node == null)
		ret = ret + "null\n";
	    else if (node == DELETED)
		ret = ret + "DELETED\n";
	    else
		ret = ret + node.key + " " + node.value + "\n";
	}
	return ret;
    }
    
//------------------------------------------------------------------------------

    public static void main (String[] args) {
	OpenHashTable<String, Integer> table =
	    new OpenHashTable<String, Integer>();

	table.put("Brad", 46);
	System.out.println(table);
	table.put("Hal", 10);
	System.out.println(table);
	table.put("Kyle", 6);
	System.out.println(table);
	table.put("Lisa", 43);
	System.out.println(table);
	table.put("Lynne", 43);
	System.out.println(table);
	table.put("Victor", 46);
	System.out.println(table);
	table.put("Zoe", 6);
	System.out.println(table);
	table.put("Zoran", 76);
	System.out.println(table);

	table.remove("Zoe");
	System.out.println(table);
	table.remove("Kyle");
	System.out.println(table);
	table.remove("Brad");
	System.out.println(table);
	table.remove("Zoran");
	System.out.println(table);
	table.remove("Lisa");
	System.out.println(table);
	table.remove("Hal");
	System.out.println(table);
	table.remove("Lynne");
	System.out.println(table);
	table.put("Lynne", 44);
	System.out.println(table);
    }
}
			     
