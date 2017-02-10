//CS3810 Data Structures and Algorithms
//Assignment #4 5/14/2016
//Ahmed B. Qureshi 700636758
//I found it easier to make a class that stores the key. The instance variable is a String
//that represents a key. The default constructor sets the key to null, because null represents
//an empty or unused space in the HashTable. The overloaded constructor takes in a String
//input and sets it to the key. There are two getters. One returns a key in its String form
//and the other one simply returns the key in its int form.

public class Object {

	private String key;
	
	//default should be null, NOT -1 because
	//-1 represents a deleted number. Null means
	//it is empty.
	public Object()
	{
		key = null;
	}
	
	public Object(String keyNum)
	{
		key = keyNum + "";
	}
	
	public String getKeyString()
	{
		return key;
	}
	
}
