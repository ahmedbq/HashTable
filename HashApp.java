//CS3810 Data Structures and Algorithms
//Assignment #4 5/14/2016
//Ahmed B. Qureshi 700636758
//This is the app class which the user will interact with. There are a few methods. First, the
//main method has 2 main while loops, one which collects the length and one which works with
//the switch statements and menu. The first while loop checks if the input for the length is
//larger than 0 and a number, because there is no point of making a table of 0 length. The app 
//is also full of null pointer exceptions like the other classes because it deals with empty 
//objects which have keys initialized as null. The second loop has a sentinel which is set to
//false to quit in the case for quitting. Case 0 is made for printing menus for the sake of
//making it easier for the user. For the insertion case, the program asks for an input for a
//key and checks it for duplicates and if it is a negative number. All these methods check
//if they're empty or full for insert, search and delete functions. Case 4 just displays the
//table which is done with a loop. If it's a deleted item or a null object, a ** comes up. The
//default case triggers when a case outside of the ones given are entered. And for that, the
//user is just notified that the input should be inside those values. Other methods just check
//for duplicates, letters in inputs and Enter() was created to make the app smaller because the
//it was one of the most repeated things.

import java.util.InputMismatchException;
import java.util.Scanner;

public class HashApp {

	public static void main (String [] args)
	{
		//Testing
		int length = 0;
		//Initialized it to a letter as part of the first
		//sentinel and ValidNumber method execution
		boolean lengthCheck = true;

		System.out.println("HashTable Application\n\n"
				+ "Enter a length: ");

		int lengthZero = 0;
		//Initial input for length
		while(lengthCheck)
		{		
			Scanner input = new Scanner (System.in);
			//Scanner defined inside to prevent
			//infinite loop
			try
			{
				length = input.nextInt();
				lengthZero = length;

				if(lengthZero > 0)
				{
					lengthCheck = false;
				}
				else
				{
					System.out.println("Length must be larger than 0. Try again."
							+ "\n\nEnter: ");
				}
			}
			catch(InputMismatchException e)
			{
				System.out.println("Either you must use a number for length. "
						+ "\nOr the length is too big. Try again."
						+ "\n\nEnter a length: ");
			}

		}//While Loop End

		HashTable table = new HashTable(length);

		Menu(); Enter();

		boolean sentinel = true;

		while(sentinel)
		{
			int selection = -1;
			Scanner loopInput = new Scanner (System.in);

			try{
				selection = loopInput.nextInt();}
			catch(InputMismatchException e)
			{}


			switch (selection)
			{
			//Display Menu
			case 0: Menu(); Enter(); break;
			//Insert 
			//Assumes not full
			case 1:
				if(table.isFull()){System.out.println("HashTable is full. Can not insert."); 
				Enter(); break;}
				System.out.println("Enter key to insert: ");
				String key = "";
				try
				{
					key = loopInput.next();
					if (duplicate(key, table))
					{
						System.out.println("This is a duplicate key, try again.");
						Enter();
						break;
					}

				}
				catch(InputMismatchException e){System.out.println("Key must be a number(s) or letter(s) only."
						+ "\nOr it is too big for this application. \nTry again.");
				try
				{
					int keyInt = Integer.valueOf(key);
					if(keyInt < 0)
					{
						System.out.println("Key can not be negative."); Enter();
						break;
					}
				}
				catch(Exception b){}

				Enter(); break;}

				String keyString = key + "";

				table.insert(new Object(keyString));
				System.out.println("Key inserted.");

				Enter(); break;

				//Search
				//Assumes not empty
			case 2: 
				if(table.isEmpty()){System.out.println("Nothing to search. HashTable is empty."); 
				Enter(); break;}
				System.out.println("Enter key to search: ");
				String key2;
				try
				{
					key2 = loopInput.next();
				}
				catch(InputMismatchException e){System.out.println("Key must be a number.");
				Enter(); break;}

				try
				{
					int key2Integer = Integer.valueOf(key2);
					if(key2Integer < 0)
					{
						System.out.println("Key can not be negative."); Enter();
						break;
					}
				}
				catch(Exception e){} //Assuming specifying not needed

				try{

					System.out.println("The key is in index: " + table.Search(key2));
					System.out.println("If the result is -1, it was not found.");

				}
				catch(InputMismatchException e){
					System.out.println("Key must be a number.");
				}

				Enter(); break;

				//Delete
				//Assumes not empty
			case 3: 
				if(table.isEmpty()){System.out.println("Nothing to delete. HashTable is empty."); 
				Enter(); break;}
				System.out.println("Enter key to delete: ");
				String key3 = loopInput.next();

				try
				{
					int key3int = Integer.valueOf(key3);
					if(key3int < 0)
					{
						System.out.println("Key can not be negative."); Enter(); break;
					}
				}
				catch(Exception e){}

				try{

					if (table.delete(key3) == false)
					{
						System.out.println("No need to delete. Key not found in HashTable.");
					}
					else
					{
						System.out.println("Key successfully deleted.");	
					}
				}
				catch(InputMismatchException e){System.out.println("Key must be a number."); 
				Enter(); break;}


				Enter(); break;

				//Display
			case 4: displayTable(table);
			System.out.println(); Enter(); break;

			//Quit
			case 5: System.out.println("Quit successful."); 
			sentinel = false; break;

			default: System.out.println("Enter only a number for menu options."); 
			Enter(); break;

			}//End Switch
		}//End Loop


	} //Main End

	public static void Menu()
	{
		System.out.println("\nMenu"
				+ "\n0)Display Menu \n1)Insert \n2)Search "
				+ "\n3)Delete \n4)Display \n5)Quit");
	}

	public static void Enter()
	{
		System.out.println("-----------------\nEnter: ");
	}

	//Checks for letters in length inputs
	public static boolean ValidNumber(String length)
	{
		if (length.matches(".*[a-zA-Z].*")){ 
			System.out.println("Name must contain only numbers"
					+ "\n\nEnter: "); 
			return false;
		} 
		return true;
	}//ValidName End

	//duplicate ID checker
	public static boolean duplicate(String key, HashTable table)
	{	

		for(int x = 0; x < table.getPrimeSize(); x++)
		{
			try
			{
				if (table.getHashArrayKey(x).compareTo(key)==0)
				{ 
					return true;
				}
			}
			catch(Exception e){}

		}

		return false;
	}

	public static void displayTable(HashTable table)
	{
		System.out.println("Table: ");

		for(int x = 0; x < table.getPrimeSize(); x++)
		{
			//It will not display an empty or deleted item
			if(!table.NullChecker(x))
			{
				if(!table.getHashArrayKey(x).equals("-1"))
				{
					System.out.println("index["+x + "]: "+ table.getHashArrayKey(x) + "");
				}
				else{System.out.println("index["+x + "]: **");}
			}
			else
			{
				System.out.println("index["+x + "]: **");
			}

		}//End For Loop

		System.out.println("\nNOTE: ** means either empty or deleted.");

	}//End displayTable
} //App End
