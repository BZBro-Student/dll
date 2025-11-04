import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implements the IndexedUnsortedList interface using a doubly linked node based
 * list.
 * Supports Iterators and ListIterators.
 * 
 * @author Broden
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
    private Node<T> head, tail;
    private int size;
    private int modCount;

    IUDoubleLinkedList() {
        head = tail = null;
        size = 0;
        modCount = 0;
    }

    @Override
    public void addToFront(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.setNextNode(head);
        if (tail == null) {
            tail = newNode;
        } else {
            head.setPrevNode(newNode);
        }
        head = newNode;
        size++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        Node<T> newNode = new Node<T>(element);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNextNode(newNode);
            newNode.setPrevNode(tail);
            tail = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public void add(T element) {
        addToFront(element);
    }

    @Override
    public void addAfter(T element, T target) {
        Node<T> currNode = head;
        Node<T> newNode = new Node<T>(element);
        boolean inserted = false;
        if (head == null) {
            throw new NoSuchElementException();
        }

        while (currNode != null) {
            if (currNode.getElement().equals(target)) {
                Node<T> tempNext = currNode.getNextNode();
                currNode.setNextNode(newNode);
                newNode.setPrevNode(currNode);
                newNode.setNextNode(tempNext);
                if (tempNext != null) {
                    tempNext.setPrevNode(newNode);
                } else {
                    tail = newNode;
                }
                size++;
                modCount++;
                inserted = true;
                break;
            }
            currNode = currNode.getNextNode();
        }
        if (inserted == false) {
            throw new NoSuchElementException();
        }

    }

    @Override
    // TODO: optimize by checking which end of the list to start
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            addToFront(element);
        } else if (index == size) {
            addToRear(element);
        } else {
            Node<T> newNode = new Node<>(element);
            int currIndex = 0;
            Node<T> currNode = head;
            while (currNode != null) {
                if (currIndex == index - 1) {
                    Node<T> tempNext = currNode.getNextNode();
                    currNode.setNextNode(newNode);
                    newNode.setPrevNode(currNode);
                    tempNext.setPrevNode(newNode);
                    newNode.setNextNode(tempNext);
                    break;
                }
                currNode = currNode.getNextNode();
                currIndex++;
            }
            size++;
            modCount++;
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T returnValue = head.getElement();
        head = head.getNextNode();
        head.setPrevNode(null);
        if (head == null) {
            tail = null;
        }
        size--;
        modCount++;
        return returnValue;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T returnValue = null;
        if (size == 1) {
            returnValue = head.getElement();
            head = null;
            tail = null;
        } else {
            returnValue = tail.getElement();
            tail = tail.getPrevNode();
        }
        size--;
        modCount++;
        return returnValue;
    }

    @Override
    public T remove(T element) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T returnVal = null;

        if (head.getElement().equals(element)) {
            returnVal = head.getElement();
            head = head.getNextNode();
            head.setPrevNode(null);
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> currNode = head;
            while (currNode != null) {
                if (currNode.getElement().equals(element)) {
                    returnVal = currNode.getElement();
                    Node<T> tempPrevNode = currNode.getPrevNode();
                    Node<T> tempNextNode = currNode.getNextNode();
                    currNode.setNextNode(null);
                    currNode.setPrevNode(null);
                    if (tempNextNode != null) {
                        tempPrevNode.setNextNode(tempNextNode);
                    } else {
                        tail = tempPrevNode;
                    }
                    tempNextNode.setPrevNode(tempPrevNode);
                }
                currNode = currNode.getNextNode();
            }
        }
        size--;
        modCount++;
        return returnVal;
    }

    @Override
    //TODO: optimize index navigation
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T returnValue = null;
        if (index == 0) {
            removeFirst();
        } else if (index == size - 1) {
            removeLast();
        } else {
            int currIndex = 0;
            Node<T> currNode = head;
            while (currNode != null) {
                if (currIndex == index) {
                    returnValue = currNode.getElement();
                    Node<T> tempPrevNode = currNode.getPrevNode();
                    Node<T> tempNextNode = currNode.getNextNode();
                    currNode.setNextNode(null);
                    currNode.setPrevNode(null);
                    tempPrevNode.setNextNode(tempNextNode);
                    tempNextNode.setPrevNode(tempPrevNode);
                                size--;
            modCount++;
                }
                if (returnValue != null){
                    break;
                }
                currNode = currNode.getNextNode();
                currIndex++;
            }
        }
        return returnValue;
    }

    @Override
    //TODO: optimize index navigation
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int currIndex = 0;
        Node<T> currNode = head;
        while (currIndex < index) {
            currNode = currNode.getNextNode();
            currIndex++;
        }
        currNode.setElement(element);
        modCount++;
    }        


    @Override
    // TODO: optimize by checking if index is in first half or second half of list
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int currIndex = 0;
        Node<T> currNode = head;
        while (currIndex < index) {
            currNode = currNode.getNextNode();
            currIndex++;
        }
        return currNode.getElement();
    }

    @Override
    public int indexOf(T element) {
        Node<T> currNode = head;
        int currIndex = 0;
        // initialize at -1 since index of returns -1 if the element is never found
        int foundIndex = -1;
        while (currNode != null) {
            // checks to see if equal
            if (currNode.getElement().equals(element)) {
                foundIndex = currIndex;
            }
            // if our index is no longer -1, found the element and break out of the loop
            if (foundIndex != -1) {
                break;
            }
            currNode = currNode.getNextNode();
            currIndex++;
        }
        return foundIndex;
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.getElement();
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        return indexOf(target) > -1;
    }

    @Override
    public boolean isEmpty() {
        boolean isEmpty = false;
        if (head == null && tail == null) {
            isEmpty = true;
        }
        return isEmpty;
    }

    @Override
    public int size() {
        return size;
    }
    /**
     * Contains the constructor and methods for a iterator meant to be used on a
     * double linked list. Implements
     * the Iterator interface for type T.
     * 
     * @Author Broden
     */
    private class IUSDoubleLinkedListIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasNext'");
        }

        @Override
        public T next() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'next'");
        }
        @Override
        public void remove() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }

    }
    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public ListIterator<T> listIterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

}
