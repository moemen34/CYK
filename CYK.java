import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;


public class CYK {

	//global variables and hashMaps used in CYK
	static HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	static HashMap<String, ArrayList<String>> terminals = new HashMap<String, ArrayList<String>>();
	static ArrayList<ArrayList<ArrayList<String>>> CYKList = new ArrayList<ArrayList<ArrayList<String>>>();
	static String str;
	static String startState;

	/**
	 * main method of the program
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		
		//calling methods to process the language and test string
		processGrammar(getGrammar());
		getString();
		
		//if an empty string was inputed, check if it belongs to the language
		if(str.length() == 0) {
			if(emptyString()) 
				System.out.println("The empty string BELONGS to the language!");
			else
				System.out.println("The empty string DOES NOT BELONG to the language!");		
		}else {
			//validate the string, process it and print its table
			validateString();
			processString();
			printCYK();
		}
		
   }
	
	/**
	 * method that prompts the user to input a set of production rules
	 * @return array of production rules
	 */
	public static String[] getGrammar() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Enter a list of production rules with comma-separated values"
	    		+ " for each new nonterminal symbol\n"+"use 'E' for Epsilon ONLY:\n"+"E.g: S -> AS|BS|E, A ->a, B -> b");
	    
	    String language = scanner.nextLine();  // Read user input
	    String unspacedLanguage = language.replace(" ", "");
	    
	    String[] splitRules = unspacedLanguage.split(",");
	    
	    return splitRules;
	}
	
	
	
	/**
	 * method that processes the language from the file
	 * @param lines list that contains the language
	 * @throws Exception
	 */
	public static void processGrammar(String[] lines) throws Exception {
		
		int n = 0;
		//for loop that iterates for each Production rule
		for(String eachLine :lines){
		
			//split the rule to 2 parts, a part for the LHS of the rule
			//and another for the RHS of the rule
			String[] split = eachLine.split("->");
			if(split.length != 2) {
				throw new Exception("INVALID LANGUAGE! " + eachLine); 
			}
						
			String LHS = split[0]; //LHS of the rule  //was a //was c
			String RHS = split[1]; //RHS of the rule  //was b //was d
			
			if(LHS.equals("E")) {
				throw new Exception("INVALID RULE USE 'E' for Epsilon ONLY!!");
			}
			
			//set the starting state to LHS of the first rule
			if(n == 0) {
				startState = LHS;
				n = 1;
			}
			
			//split RHS by '|'
			String[] parts = RHS.split(Pattern.quote("|"));	
			//
			ArrayList<String> partsToArrayList = new ArrayList<String>();
			
			//copy the array to an ArrayList
			for(String x : parts) {
				partsToArrayList.add(x);
			}
			
			//check if the LHS of the rule was encountered before in order not to put 
			//the same key twice into the map
			if(map.containsKey(LHS)) {
				for(String x : parts) 
					map.get(LHS).add(x);
			}else
				map.put(LHS, partsToArrayList);
			
			/*check if any of the parts in the RHS is a terminal
			if so add it to the terminals hashMap with the LHS that
			produces it*/
			for(String part : parts) 
				if(part.length() == 1) {			
					char x = part.charAt(0);					
					if(Character.isLowerCase(x)) {
						if(!terminals.containsKey(part)) {
							ArrayList<String> list = new ArrayList<String>();
							list.add(LHS);
							terminals.put(part, list);
						}else {
							if(!terminals.get(part).contains(LHS))
								terminals.get(part).add(LHS);
						}
					}
				}						
		}	
	}
	
	
	
	/**
	 * method that gets input string from the user
	 */
	public static void getString() {			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);  // Create a Scanner object
		    System.out.println("Enter Test String:");
		    str = scanner.nextLine();  // Read user input
	}
	
	
	
	/**
	 * check it the language includes epsilon
	 * @return true if yes and false otherwise
	 */
	public static Boolean emptyString() {		
		//List<String> list = new ArrayList<>(Arrays.asList(map.get(startState)));
		
		ArrayList<String> list = new ArrayList<String>();
		list = map.get(startState);
		
		if(list.contains("E")) {
			return true;
		}else
			return false;	
	}
	
	
	/**
	 * method that iterates over each character in the test string 
	 * to ensure that it belongs to the language
	 * @throws Exception if a unknown character is read
	 */
	public static void validateString() throws Exception {
		for(int i =0 ; i < str.length(); i++) 
			if(!terminals.containsKey(Character.toString(str.charAt(i))))
				throw new Exception("INVALID STRING! '"+ str.charAt(i)+"' DOES NOT BELONG TO THE LANGUAGE!");	
	}
	
	
	
	/**
	 * method that processes the test string
	 */
	public static void processString() {
		
		//loop that calls the cartesian product method on all possible
		//substrings and of different lengths of the string
	    for (int size = 1; size <=(str.length()); size++) {	
	    	
	    	//arrayList of arrayList of strings that stores elements that produce a substring
	    	ArrayList<ArrayList<String>> CYKline = new ArrayList<ArrayList<String>>();    	
		    for(int i = 0; i <= (str.length()-size); i++) {	    	
		    	String sub = str.substring(i, i+size);
		    	CYKline.add(cartesianProduct(sub));
		    }	    
		    //add the cyk line list to an arrayList that holds all the lines
		    CYKList.add(CYKline);//used in printing the cyk table	    
		}
	}
	
	
	/**
	 * method that does Cartesian product to find different
	 * combinations of a string x
	 * @param x string to find combinations  of
	 * @return
	 */
	public static ArrayList<String> cartesianProduct(String x) {
		
		if(x.length() == 1) 			
			return terminals.get(x);	
		
		ArrayList<String> list = new ArrayList<String>();
		
		//perform cartesian products on all possible substrings
		//that when concatenated give the string
		for(int i = 0; i < (x.length()-1); i++) {
			
			String x1 = x.substring(0, i+1);         //first substring
			String x2 = x.substring(i+1, x.length());//second substring
				
			/*get the arraylists that store possible
			combinations that give x1 and x2*/ 
			ArrayList<String> y1 = terminals.get(x1);
			ArrayList<String> y2 = terminals.get(x2);		
			
			//loop that performs cartesian froduct on y1 and y2
			for(int a = 0; a < y1.size(); a++)
				for(int j = 0; j < y2.size(); j++ ) {					
					String s = y1.get(a)+y2.get(j);
					String ss = s.replace(" ", "");	
					map.entrySet().forEach(entry -> {
							for(String string : entry.getValue()) 
								if(ss.equals(string)) {
									if(!list.contains(entry.getKey()))
										list.add(entry.getKey());
								}																
					});					
				}	
		}
		//add possible combinations to the terminals hashMap for easy access
		terminals.put(x, list);
		return list;
	}
	
	/**
	 * Method that prints the resulting CYK table
	 */
	public static void printCYK() {
		
		System.out.println("\nCYK TABLE:\n");

		for(int i = (CYKList.size()-1); i>=0; i--) {
			for(ArrayList<String> y : CYKList.get(i))
				System.out.print(String.format("%-20s", y));
		    System.out.println();			
		}
		
		for(int i = 0; i<str.length(); i++)
			System.out.print(String.format("%-20s", str.charAt(i)));
		
		System.out.println();
		System.out.println();
		
		if(CYKList.get(CYKList.size()-1).get(0).contains(startState))
			System.out.println("The string BELONGS to the language!");
		else
			System.out.println("The string DOES NOT BELONG to the language!");		
	}	
}


/*
Test grammars:

S0 -> XaZ1 | XaS | a | E, S -> XaZ1 | XaS | a, Z1 -> SXb, Xa -> a, Xb -> b

S0 -> XaZ1 | XaS | a | E, S -> XaZ1 | XaS , Z1 -> SXb, Xa -> a, Xb -> b, S -> a

S -> AB | BC | Ep, A -> BA | a , B -> CC | b, C -> AB | a

S - > AB | BC,A-> BA| a, B-> CC|b, C-> AB | a

S -> AB,A -> CD | CF,B -> c | OB,C -> a,D -> b,O -> c,F -> AD

*/