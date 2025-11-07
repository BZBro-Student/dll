****************
* Project: dll
* Class: CS221
* Date: Nov 18, 2025
* Name: Broden Benson
**************** 

OVERVIEW:

This program includes a few different implementations of the IndexedUnsortedList interface 
including IUArrayList, IUSingleLinkedList, and IUDoubleLinkedList and a ListTester file used 
to test each seperate implementation of the IndexedUnsortedList interface inlcuding testing functionality
of the Iterator (IUSingleLinkedList) and the ListIterator (IUDoubleLinkedList).


INCLUDED FILES:

  BadList.java - Contains a 'bad' implementation of a list as a point of comparison, passes 525/2672 possible tests.
  GoodList.java - Contains a 'good' but not perfect implementation of a list as a point of comparison, passes 2659/2672 possible tests.
  IUArrayList.java - Contains an ArrayList implementation of IndexedUnsortedList, passes 2672/2672 possible tests.
  IUSingleLinkedList.java - Contains a Single Linked Node based implementation of IndexedUnsortedList, passes 2672/2672 possible tests.
  IUDoubleLinkedList.java - Contains a Double Linked Node based implementation of IndexedUnsortedList, passes 9724/9724 possible tests.
  ListTester.java - Driver file for the program which tests the selected list implementation.


COMPILING AND RUNNING:

Before attempting to compile, select which list to test by modifying line 24 to follow the following format:
private final static ListToUse LIST_TO_USE = ListToUse.<goodList, badList, singleLinkedList, or doubleLinkedList>;

Using a terminal on a computer that has the Java runtime installed, navigate to the folder in which ListTester and the rest of the source files are located.

Once in the correct directory type: javac ListTester.java
After compiling attempt to run using the commad: java ListTester

* Depending on the version of the Java runtime installed on the device running the program it may be neccesary
to force the device to compile the files in Java 8 using the command: javac --release 8 ListTester.java

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 This is the sort of information someone who really wants to
 understand your program - possibly to make future enhancements -
 would want to know.

 Explain the main concepts and organization of your program so that
 the reader can understand how your program works. This is not a repeat
 of javadoc comments or an exhaustive listing of all methods, but an
 explanation of the critical algorithms and object interactions that make
 up the program.

 Explain the main responsibilities of the classes and interfaces that make
 up the program. Explain how the classes work together to achieve the program
 goals. If there are critical algorithms that a user should understand, 
 explain them as well.
 
 If you were responsible for designing the program's classes and choosing
 how they work together, why did you design the program this way? What, if 
 anything, could be improved? 

TESTING:

 How did you test your program to be sure it works and meets all of the
 requirements? What was the testing strategy? What kinds of tests were run?
 Can your program handle bad input? Is your program  idiot-proof? How do you 
 know? What are the known issues / bugs remaining in your program?


DISCUSSION:
 
 Discuss the issues you encountered during programming (development)
 and testing. What problems did you have? What did you have to research
 and learn on your own? What kinds of errors did you get? How did you 
 fix them?
 
 What parts of the project did you find challenging? Is there anything
 that finally "clicked" for you in the process of working on this project?
 
 
EXTRA CREDIT:

 If the project had opportunities for extra credit that you attempted,
 be sure to call it out so the grader does not overlook it.


----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.
