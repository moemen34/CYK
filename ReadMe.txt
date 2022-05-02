PROGRAM DESCRIPTION:
This is a java program that implements the CYK algorithm
on a grammar G and string S given by the user.
The program utilizes a HashMap to keep track of each terminal
Symbol and non-terminal Symbol that produces it.
Cartesian product is then applied to all possible substrings of S of
size x >1, and stores the result (substring and nonterminal that can 
produce them) in a HashMap, the result is also stored to represent as
a row of the CYK table. This same process is repeated while incrementing x 
until x = the length of the string and applying cartesian products on
previous results.
Once the process above is finished the program prints the CYK table, 
in addition to a statement mentioning whether the string belongs to 
the language or not.

HOW TO RUN THE PROGRAM:
1)Open a command prompt window and go to the directory where you saved
the java program
2)Type "javac CYK.java" to Compile the program
3)Type "java CYK" to Run to program
4)Once the program Runs, it will ask you to input A list of production 
rules with comma-separated values for each new nonterminal symbol. 
E.g: S− > AS|BS|ϵ, A− > a, B− > b.
5)You will then be asked to input string to implement the CYK algorithm on.
