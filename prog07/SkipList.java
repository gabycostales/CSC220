package prog07;
import java.util.*;

public class SkipList <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private static class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {

    K key;
    V value;
    Node<K, V>[] next;
    
    @SuppressWarnings("unchecked")
	Node () {
      next = (Node<K,V>[]) new Node[1];
    }

    @SuppressWarnings("unchecked")
	Node (K key, V value) {
      this.key = key;
      this.value = value;
      int levels = 1;

      // EXERCISE:
      // Start flipping a coin and increment levels as many times as
      // you get ``heads'' in a row.  Stop when you get ``tails''.
      // while (Math.random() < 0.5)
      // levels++;

      next = (Node<K,V>[]) new Node[levels];
    }
    
    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }

    /**
     * Increase the length of the next array.
     * Copy values from the old array to the new one.
     * @param newLength The desired length of the new next array.
     */
    private void reallocate (int newLength) {
      // EXERCISE
      @SuppressWarnings("unchecked")
	Node<K, V>[] newNext = (Node<K, V>[]) new Node[newLength];
      System.arraycopy(next, 0, newNext, 0, next.length);
      next = newNext;
    }
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
private Node<K, V> dummy = new Node();
  private int size;
  
  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @return The node with that key.
   */
  private Node<K, V> find (K key) {
	// EXERCISE:
    Node<K, V> previous = dummy;
    int level = dummy.next.length-1;
    while (level >= 0) {
      Node<K, V> next = previous.next[level];
      if (next != null && next.key.compareTo(key) == 0)
        return next;
      else if (next != null && next.key.compareTo(key) < 0)
        previous = next;
      else
        level--;
    }
    return null;
  }    
  
  @SuppressWarnings("unchecked")
public V remove (Object keyAsObject) {
	K key = (K) keyAsObject;
    Node<K, V> node = find(key);
    if (node == null)
      return null;

    Node<K, V> previous = dummy;
    int level = dummy.next.length-1;
    while (level >= 0) {
      Node<K, V> next = previous.next[level];
      if (next != null && next.key.compareTo(key) == 0)
        previous.next[level] = next.next[level];
      else if (next != null && next.key.compareTo(key) < 0)
        previous = next;
      else
        level--;
    }
    size--;

    return node.value;
  }      

  @SuppressWarnings("unchecked")
public boolean containsKey (Object key) {
    return find((K) key) != null;
  }
  
  @SuppressWarnings("unchecked")
public V get (Object key) {
    Node<K, V> node = find((K) key);
    if (node != null)
      return node.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  public V put (K key, V value) {
    Node<K, V> node = find(key);
    if (node != null) {
      V oldValue = node.value;
      node.value = value;
      return oldValue;
    }
    
    node = new Node<K, V>(key, value);
    if (dummy.next.length < node.next.length)
      dummy.reallocate(node.next.length);

    Node<K, V> previous = dummy;
    int level = dummy.next.length-1;
    while (level >= 0) {
      Node<K, V> next = previous.next[level];
      if (next != null && next.key.compareTo(key) < 0)
        previous = next;
      else {
        if (level < node.next.length) {
          previous.next[level] = node;
          node.next[level] = next;
        }
        level--;
      }
    }
    
    size++;
    return null;
  }      
  
  private static class Iter<K extends Comparable<K>, V> implements Iterator<Map.Entry<K, V>> {
    Node<K, V> next;
    
    Iter (Node<K, V> dummy) {
      next = dummy.next[0];
    }
    
    public boolean hasNext () { return next != null; }
    
    public Map.Entry<K, V> next () {
      Map.Entry<K, V> ret = next;
      next = next.next[0];
      return ret;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  private class Setter extends AbstractSet<Map.Entry<K, V>> {
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter(dummy);
    }
    
    public int size () { return size; }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  public static void main (String[] args) {
    Map<String, Integer> map = new SkipList<String, Integer>();
    
    map.put("Victor", 50);
    map.put("Irina", 45);
    map.put("Lisa", 47);
    
    for (Map.Entry<String, Integer> pair : map.entrySet())
      System.out.println(pair.getKey() + " " + pair.getValue());
    
    System.out.println(map.get("Irina"));
    map.remove("Irina");
    
    for (Map.Entry<String, Integer> pair : map.entrySet())
      System.out.println(pair.getKey() + " " + pair.getValue());
    
    System.out.println(map.get("Irina"));
  }
}
