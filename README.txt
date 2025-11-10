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

Each list uses the IndexedUnsortedList<T> interface to obligate each of the classes to have the same functions. The main difference between each list is how these functions 
were implemented due to the differening structures of each list type. 

-IUArrayList: 
  The IUArrayList is a list that uses an array in the background to allow for an easily indexable list. Due to the indexable nature of this list 
  methods that start at particular indices such as get or set have an O(1) time complexity and other methods like add(index) and remove(index) do not 
  have to search for the target before doing the rest of their logic. An issue that comes about from this implemetation though is that any methods that 
  add or remove from either the front or middle of the list will require all elements to be shifted which is an O(n) time complexity. One particular 
  benefit of ArrayList is it's ability to help minimize memory usage, if a list of a certain size is needed then the list can be created at exactly that size
  meaning there is no wasted space. If the list needs to expand the ArrayList will double in size resulting in some wasted space in exchange for faster growth and
  minimizing the amount of times growth needs to occur. 
  Shifting is handled by two different helper methods, shiftElementForward(index) and shiftElementBackward(index) that depending on the function will appropriatly shift
  elements in the list forward or backward from the specified elements.

-IUSingleLinkedList:
  The IUSingleLinkedList uses nodes in the background to allow for an easily modifiable and traversable list as each node points to the node in front of it, 
  allowing for forward navigation via the getNextNode() function. From this change no shifting is required to add or remove from the list, but the time complexity
  for add(index), add(element,target), remove(index), and remove(element) remains O(n) as these methods need to navigate to the correct node from the head due to
  this list not being directly indexable. addToFront, addToRear, and removeFirst all become O(1). removeLast() becomes an O(n) operation as it requires navigation 
  through the entire list just to get a reference to the node before the tail node.
  In this list type edge cases need to be carefully considered as in many cases a node reference will become null which if a method is called on it will result in 
  a null pointer exception, handling these edge cases will make sure this does not occur. Common edge cases include removeing the first or last element and adding 
  to an empty list which require many diferent references. Also this list type will use more memory than the ArrayList as each element in the list is attached to 
  a node that has references to not only the held element but also the next node.
  This SingleLinkedList also has an Iterator which can be used to quickly navigate through the contents of the list and remove elements, this iterator ensures
  fail fast behaviour.

-IUDoubleLinkedList: 
  The IUDOubleLinkedList is almost the exact same as the IUSingleLinkedList, but each node now holds a reference to the node before itself. The biggest improvement this allows 
  for is making removeLast() O(1) as the list now knows what the node before the tail is. This means that the previous node can just be made the tail and set the tails next node back 
  to null. The new reference increases memory usage in exchange for lowering the time complexity of the removeLast() method. This also allows for the use of a list iterator, 
  which is an iterator that that has bi-directional movement. It also allows for extra functionality other than moving back and forth, it allow for adding and setting values and getter
  methods for getting the previous and next indexes. 
  Because of the possibility of navigating back from the tail or forward from the head navigation through the list can be reduced for indexed functions by deciding which side
  of to start navigating from as starting at the tail for index 90 out of 100 will be significantly faster than navigating from the head.

Each list type has advantages and disadvantages due to their usage of memory and their methods' time complexity. Due to this not every list is useful for every purpose even if
they are functionaly the same. A list that doesn't need to change will be better suited for an ArrayList while a list that needs to dynamically change will be better implemented 
using one of the LinkedList implementations. Between the two LinkedList implemetations if the priority is saving as much memory as possible a singleLinkedList will be better but 
if removing or adding from the list are expected IUDoubleLinkedList will likely be better due to it's optimized navigation

  

TESTING:

For this assignment a pre-made testing suit was used. As each list and it's associated methods were being added each method that could be used to change the list were tested
using change scenarios and then tests that check to see the end state of the list after a method call compared to the expected result of that change scenario. These tests 
also check for fail fast behavior, iterator functionality, and list iterator functionality. To ensure testing was proper, reading and verifying the expected end results was done 
multiple times throughout the continued development of the list implementations and list tester.
By consistently testing throughout development multiple issues were able to be fixed, such as edge cases that were unproperly handled resulting most of the time in
null pointer exceptions or errors in adding and removeing nodes. For example during development the add() method consistently just used the addToRear method which are functionaly 
the same, but during the creation of IUDOubleLinkedList addToRear was used instead on accident. This issue went unnoticed until the end of adding all the methods, but since there 
were still tests failing and they were from that method it was easy to find the issue and correct it.


DISCUSSION:

The most challenging aspect of development for this project came from translating the IUSingleLinkedList to the IUDOubleLinkedList as making sure to handle all edge cases
and update every reference without losing any data as there is a large increase in the number of references to manage. When updating the IUSingleLinkedList during the add or 
remove methods a minimum of 1 reference gets updated and a possible max of 2 will be updated but in the IUDOubleLinkedList that same procedure would require a minimum of 2 updates
and a possible max of 4 updated references which can cause a lot of confusion. 
----------------------------------------------------------------------------
