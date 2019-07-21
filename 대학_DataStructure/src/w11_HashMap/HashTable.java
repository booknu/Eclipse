package w11_HashMap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class HashTable<K, V> implements KWHashMap<K, V> {
	private LinkedList<Entry<K, V>>[] table;
	private int numKeys;
	private static final int CAPACITY = 101;
	private static final double LOAD_THRESHOLD = 3.0;
	// class Pair
	private static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		@Override
		public K getKey() {
			return key;
		}
		@Override
		public V getValue() {
			return value;
		}
		@Override
		public V setValue(V val) {
			V oldVal = value;
			value = val;
			return oldVal;
		}
		@Override
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
	}//class Entry
	public HashTable() {
		table = new LinkedList[CAPACITY];
	}
	@Override
	public V get(Object key) {
		int index = key.hashCode() % table.length;
		if (index < 0) { index += table.length;}
		if (table[index] == null) {return null;}
		for (Entry<K, V> nextItem : table[index]) {
			if (nextItem.key.equals(key)) { return nextItem.value; }
		}
		return null;
	}
	@Override
	public V put(K key, V value) {
		int index = key.hashCode() % table.length;
		if (index < 0) {
			index += table.length;
		}
		// if table[index] don't have any element
		if (table[index] == null) {
			table[index] = new LinkedList<Entry<K, V>>();
		}
		// iterate LinkedList table[index]
		for (Entry<K, V> nextItem : table[index]) {
			// if list already has a key
			if (nextItem.key.equals(key)) {
				// replace old value to new value
				V oldVal = nextItem.value;
				nextItem.setValue(value);
				return oldVal;
			}
		}
		table[index].addFirst(new Entry<K, V>(key, value));
		numKeys++;
		if (numKeys > (LOAD_THRESHOLD * table.length)) { rehash();}
		return null;
	}
	@Override
	public int size() { return numKeys; }
	public boolean isEmpty() { return numKeys == 0; }
	@Override
	public V remove(Object key) {
		int index = key.hashCode() % table.length;
		if (index < 0) { index += table.length; }
		if (table[index] == null) { return null; }
		Iterator<Entry<K, V>> iter = table[index].iterator();
		while (iter.hasNext()) {
			Entry<K, V> nextItem = iter.next();
			if (nextItem.key.equals(key)) {
				V returnValue = nextItem.value;
				iter.remove();
				return returnValue;
			}
		}
		return null;
	}
	public void rehash() {
		LinkedList<Entry<K, V>>[] oldTable = table;
		table = new LinkedList[2 * oldTable.length + 1];
		numKeys = 0;
		for (int i = 0; i < oldTable.length; i++) {
			if (oldTable[i] != null) {
				for (Entry<K, V> nextEntry : oldTable[i]) { put(nextEntry.key, nextEntry.value); }
			}
		}
	}
	public java.util.Set<Map.Entry<K, V>> entrySet() { return new EntrySet(); }
	private class EntrySet extends java.util.AbstractSet<Map.Entry<K, V>> {
		@Override
		public int size() { return numKeys; }
		@Override
		public Iterator<Map.Entry<K, V>> iterator() { return new SetIterator(); }
	}
	private class SetIterator implements Iterator<Map.Entry<K, V>> {
		int index = 0;
		Iterator<Entry<K, V>> localIterator = null;
		@Override
		public boolean hasNext() {
			if (localIterator != null) {
				if (localIterator.hasNext()) { return true;}
				else {
					localIterator = null;
					index++;
				}
			}
			while (index < table.length && table[index] == null) { index++; }
			if (index == table.length) { return false; }
			localIterator = table[index].iterator();
			return localIterator.hasNext();
		}
		@Override
		public Map.Entry<K, V> next() {return localIterator.next(); }
		@Override
		public void remove() {
			localIterator.remove();
			if (table[index].size() == 0) {table[index] = null; }
			numKeys--;
		}
	}//End of SetIterator

	public void printHashTable()
	{
		for (int i = 0; i < table.length; i++) {
			// if table[i] has element
			if(table[i]!=null){
				System.out.print ("Bucket " + i + ": ");
				// iterate LinkedList table[i]
				Iterator itr = table[i].iterator();
				if(itr.hasNext()){
					while(itr.hasNext())
					{
						Entry<String,String> node = (Entry<String,String>)itr.next();
						System.out.println(node.value);
					}
				}
			}
		}
	}
}
