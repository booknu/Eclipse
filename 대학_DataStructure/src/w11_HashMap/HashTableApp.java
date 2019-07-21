package w11_HashMap;

public class HashTableApp {
	public static void main(String args[]) {
		HashTable<String, String> myHT = new HashTable<String, String>();
		myHT.put("Math", "Score1");
		myHT.put("Math1", "Score1-1");
		myHT.put("Math1", "Score1-2");
		myHT.put("Eng", "Eng-Score");
		myHT.put("Eng1", "Eng-Score1");
		myHT.put("Eng2", "Eng-Score2");
		myHT.printHashTable();
	}
}
