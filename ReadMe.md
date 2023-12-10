# Program Description

This is a Java program that implements the CYK algorithm on a grammar G and string S given by the user. 
The program utilizes a HashMap to keep track of each terminal symbol and non-terminal symbol that produces it. 
Cartesian product is then applied to all possible substrings of S of size x > 1, the result (substring and nonterminal that can produce them) is stored in a HashMap. 
The result is also stored to represent as a row of the CYK table. This same process is repeated while incrementing x until it equals the length of the string and applying Cartesian products on previous results.

Once the process above is finished, the program prints the CYK table, in addition to a statement mentioning whether the string belongs to the language or not.

# How to Run the Program

1. Open a command prompt window and go to the directory where you saved the Java program.
2. Type "javac CYK.java" to compile the program.
3. Type "java CYK" to run the program.
4. Once the program runs, it will ask you to input a list of production rules with comma-separated values for each new nonterminal symbol. Example: `S -> AS | BS | ϵ, A -> a, B -> b`.
5. You will then be asked to input a string to implement the CYK algorithm on.

## Examples of test Grammars:

S0 -> XaZ1 | XaS | a | E, S -> XaZ1 | XaS | a, Z1 -> SXb, Xa -> a, Xb -> b

S0 -> XaZ1 | XaS | a | E, S -> XaZ1 | XaS , Z1 -> SXb, Xa -> a, Xb -> b, S -> a

S -> AB | BC | Ep, A -> BA | a , B -> CC | b, C -> AB | a

S - > AB | BC,A-> BA| a, B-> CC|b, C-> AB | a

S -> AB,A -> CD | CF,B -> c | OB,C -> a,D -> b,O -> c,F -> AD
