package w11_HashMap;

public interface KWHashMap<K, V> {
	/**
	 * get value of key in the Map
	 * 
	 * @param key key
	 * @return value
	 */
	 V get(Object key);
	 
	 /**
	  * put pair of (key, value) into the Map
	  * 
	  * @param key key (must be unique. if Map already has a key, value will be replaced) 
	  * @param value value
	  * @return if Map already has a key, return old value. if not, return null
	  */
	 V put(K key, V value);
	 
	 /**
	  * remove element with a key in the Map
	  * 
	  * @param key key
	  * @return return removed value. if Map don't have a key, return null
	  */
	 V remove(Object key);
	 
	 /**
	  * return size of Map
	  * @return size of Map
	  */
	 int size();
	 
	 /**
	  * return Map is empty
	  * @return is Map don't have any elements?
	  */
	 boolean isEmpty();
}