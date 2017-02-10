//CS3810 Data Structures and Algorithms
//Assignment #4 5/14/2016
//Ahmed B. Qureshi 700636758
//This class represents the HashTable. It has 3 instance variables, a HashArray representing
//where all the Objects (which contains a key) are stored. The next one is Size which just
//represents the Size of the HashTable BEFORE it is set to the next prime number. The last
//instance variable represents a deleted Object. In the constructor, the input Size is
//multiplied by 2 and then 2 methods are acted upon the result to turn it into the next prime
//number. Also, deleted is initialized to an object of a key with "-1". DisplayTable just
//goes through the entire HashTable Array and prints the keys out IF they are NOT null (empty)
//OR -1 (deleted). Otherwise, everything else is printed out as a ** just so the spaces within
//the array are shown. The insert method takes in an Object and then stores the Hashing of its 
//key. Then, there is a while loop which keeps moving until there is a null space. This means 
//that if the first found space is empty, it will go in there right away. But this is made this
//way so that it can use the open addressing algorithm. Also, it also wraps around if it would 
//continue after hitting the last element of the array. Then in the end it just sets the 
//HashArray of the new index into the inputted key. The search method is a bit similar to to 
//insert. Except that there is also another variable storing the initial Hashed key result. 
//Then in the while loop, the key keeps incrementing until there is an object with the key 
//equal to the input. There are no duplicates allowed for this assignment. If it can not find 
//anything, it will simply return -1. The delete method uses the Search method before it moves 
//on. First it stores the result of the Search method and only continues if it actually found 
//the result. Then if it is found, it will set the HashArray[SearchResult] to point to deleted.
//The Hash method first stores a String version of the inputted key. Then it will store the 
//hashCode() result of it within CodeString as a String. Then the Size of the Array is stored 
//as a String also. It should be noted that it is the Prime number version. There is also 
//variables containing the amount of decimal places within the Hashed Key, so 1000 would have 
//3 and 100 would have 2. Then the amount of folds done in one loop and the sum of the folds 
//are also initialized. Then there's a complicated loop in which is hard to explain but easy 
//to do. So a variable increments whenever each decimal place of the key is passed. Then 
//another variable stores numbers as a String until the amount of numbers in a fold is passed. 
//Then it is added to the KeyAdder as an integer. Then the whole process resets. In the end, 
//int index is created in which it is set to KeyAdder%getPrime(Size). It should be noted that 
//the Size was already doubled in the beginning of the program so the load factor has no 
//problems. Then the index is returned. There are also isEmpty and isFull methods, which just 
//check if there are any null spaces in the array or not.
public class HashTable {

	private Object[] HashArray;
	private int Size;
	private Object deleted; //for deleted items

	//Constructor
	HashTable(int length)
	{
		//Size is set to length*2
		//Then the next prime number is found
		//to be set to the size
		Size = length * 2;
		try
		{
			HashArray = new Object[getPrime(Size)];
		}
		catch(NegativeArraySizeException e)
		{
			System.out.println("Number too large for memory. Restart Application.");
		}
		deleted = new Object("-1");
	}

	public void insert(Object object)
	{
		int index = Hash(object.getKeyString());

		//if there's something there, it will search for an empty space
		//or a previously deleted place to input
		while(HashArray[index] != null)
		{
			/**If it's equal to -1, then it will replace the -1*/
			try{
				if(HashArray[index].getKeyString().compareTo("-1") == 0)
				{
					break;
				}
			}
			catch(NullPointerException e){}
			/**To avoid error*/

			//This wraps it around. This happens AFTER
			//the comparison so that the first index
			//to be checked is checked.
			if(index == HashArray.length-1)
			{
				index = -1;
			}
			index++;

		}

		HashArray[index] = new Object(object.getKeyString());
	}

	public int Search(String keySearch)
	{
		int key = Hash(keySearch);
		int keyStatic = key;
		int count = 0; //To stop infinite loop in a full array

		//Hardcoded 2 problems here. It didn't delete the first or last
		//elements for some reason before.
		try{
			if(getHashArrayKey(getPrime(Size)-1).equals(keySearch))
			{
				return getPrime(Size)-1;
			}

			if(getHashArrayKey(0).equals(keySearch))
			{
				return 0;
			}
		}
		catch(NullPointerException e)
		{

		}


		try{
			while(!getHashArrayKey(key).equals(keySearch))
			{				
				//Better to do it this way because next commented code
				//actually caused glitches. So it's better to put a counter
				//and just stop it when it goes through an amount of elements
				//equal to the amount of stuff inside the array
				if(count == getPrime(Size))
				{
					return -1;
				}
				//If it came all around and back
				//then it stops the search
				//				if(key == keyStatic)
				//				{
				//					return -1;
				//				}
				//If it hits a null object then 
				//it stops the search, there's nothing
				if(HashArray[key] == null)
				{
					return -1;
				}

				//Wrap Around
				if(key == HashArray.length-1)
				{
					key = -1;
				}
				count++;
				key++;
			}
		}
		catch(NullPointerException e)
		{
			return -1;
		}
		return key;
	}

	//Assumes key exists
	public boolean delete(String key)
	{	//Set it equal to an int in the beginning
		//so that it does not have to search multiple times
		int SearchResult = Search(key);

		if (SearchResult == -1)
		{
			return false;
		}
		else
		{
			HashArray[SearchResult] = deleted;
			return true;
		}
	}

	//Hash function here is to fold
	public int Hash(String key)
	{
		String keyString = key + "";
		int Code = keyString.hashCode();
		String CodeString = Code + "";
		//Also converted to String for the for loop

		//Convert Size to String to count number of decimal places
		String SizeString = getPrime(Size) + "";

		//The number of decimal places will represent
		//the amount of numbers in one fold
		int Folds = CountZeroes(SizeString);

		//countFold tracks whenever loop goes around
		//a fold amount of times to collect info from
		//string
		int countFold = 0;
		//keyAdder is just an int to add the numbers in
		//the folds to each other one by one
		int KeyAdder = 0;
		//The numbers collected one fold at a time
		String Numbers = "";

		int y = 0;
		boolean forloop = true;
		while (y < CodeString.length() && forloop)
		{
			for(int x = 0; x < CodeString.length(); x++, y++)
			{
				countFold++;
				Numbers += CodeString.substring(x);

				if (countFold == Folds)
				{
					try
					{
						KeyAdder += Integer.valueOf(Numbers);
					}
					catch(NumberFormatException e)
					{
						forloop = false;
					}

					countFold = 0;
					Numbers = "";
				}


			}
		}

		//The numbers dangling in the end are added
		//if there are any left that are not accounted for

		try
		{
			if (Numbers != "")
			{
				KeyAdder += Integer.valueOf(Numbers); 
			}
		}
		catch(NumberFormatException e)
		{
			//To prevent error from extremely large length
		}

		//Modding happens the last second to make it even
		//smaller
		int index = KeyAdder%getPrime(Size);
		//We don't multiply Size here because the
		//Size for HashArray was double already

		return index;
	}//End Hash

	//Counts the number of places in a number
	//So 10 returns 1, 100 returns 2... etc.
	public int CountZeroes(String Size)
	{
		int counter = 0;

		for (int x = 0; x < Size.length(); x++)
		{
			counter++;
		}

		return counter-1;
	}

	private int getPrime(int min)
	{
		for(int j = min+1; true; j++)
		{
			if (isPrime(j))
			{
				return j;
			}
		}
	}

	private boolean isPrime(int n)
	{
		for(int j = 2; (j*j <= n); j++)
		{
			if(n%j == 0)
			{
				return false;
			}
		}
		return true;
	}


	public boolean isFull()
	{
		for(int x = 0; x < HashArray.length; x++)
		{
			if (HashArray[x] == null)
			{
				return false;
			}
			//Checked null and key separately because
			//it can cause a null exception if together
			//Nothing can be null and -1 at the same time
			if(getHashArrayKey(x).equals("-1"))
			{
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty()
	{
		for(int x = 0; x < HashArray.length; x++)
		{
			if (HashArray[x] != null)
			{ 
				if (!getHashArrayKey(x).equals("-1"))
				{
					return false;
				}
			}
		}
		return true;
	}

	//Returns the Size of HashTable
	public int getPrimeSize()
	{
		return getPrime(Size);
	}

	public boolean NullChecker(int index){
		try
		{
			if(getHashArrayKey(index) == null)
			{
				return true;
			}
		}
		catch(NullPointerException e){return true;}
		
		return false;
	}

	public String getHashArrayKey(int index) {
		return HashArray[index].getKeyString();
	}


}//End HashTable
