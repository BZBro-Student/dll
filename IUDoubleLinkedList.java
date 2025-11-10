import java.util.ConcurrentModificationException;
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
    enum WhereStart {
        HEADSTART, TAILSTART
    };

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
        addToRear(element);
    }

    @Override
    public void addAfter(T element, T target) {
        Node<T> currNode = head;
        Node<T> newNode = new Node<T>(element);
        boolean inserted = false;
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
            Node<T> currNode = null;
            WhereStart startPosition = WhereStart.HEADSTART;
            if (index > size / 2) {
                startPosition = WhereStart.TAILSTART;
            }
            switch (startPosition) {
                case HEADSTART:
                    currNode = head;
                    while (currNode != null) {
                        if (currIndex == index - 1) {
                            Node<T> tempNext = currNode.getNextNode();
                            currNode.setNextNode(newNode);
                            newNode.setPrevNode(currNode);
                            tempNext.setPrevNode(newNode);
                            newNode.setNextNode(tempNext);
                            size++;
                            modCount++;
                            break;
                        }
                        currNode = currNode.getNextNode();
                        currIndex++;
                    }
                    break;
                case TAILSTART:
                    currNode = tail;
                    currIndex = size - 1;
                    while (currNode != null) {
                        if (currIndex == index - 1) {
                            Node<T> tempNext = currNode.getNextNode();
                            currNode.setNextNode(newNode);
                            newNode.setPrevNode(currNode);
                            tempNext.setPrevNode(newNode);
                            newNode.setNextNode(tempNext);
                            size++;
                            modCount++;
                            break;
                        }
                        currNode = currNode.getPrevNode();
                        currIndex--;
                    }
                    break;
            }
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T returnValue = head.getElement();
        head = head.getNextNode();
        if (head != null) {
            head.setPrevNode(null);
        }
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
        T returnValue = tail.getElement();
        if (size == 1) {
            returnValue = head.getElement();
            head = null;
            tail = null;
        } else {
            returnValue = tail.getElement();
            tail = tail.getPrevNode();
            tail.setNextNode(null);
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
            if (head == null) {
                tail = null;
            } else {
                head.setPrevNode(null);
            }
            size--;
            modCount++;
        } else {
            Node<T> currNode = head;
            while (currNode != null) {
                if (currNode.getElement().equals(element)) {
                    returnVal = currNode.getElement();
                    Node<T> tempPrevNode = currNode.getPrevNode();
                    Node<T> tempNextNode = currNode.getNextNode();
                    if (tempNextNode != null) {
                        tempPrevNode.setNextNode(tempNextNode);
                        tempNextNode.setPrevNode(tempPrevNode);
                    } else {
                        tail = tempPrevNode;
                        tail.setNextNode(null);
                    }
                    size--;
                    modCount++;
                    break;
                }
                currNode = currNode.getNextNode();
            }
        }
        if (returnVal == null) {
            throw new NoSuchElementException();
        }
        return returnVal;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T returnValue = null;
        if (index == 0) {
            returnValue = removeFirst();
        } else if (index == size - 1) {
            returnValue = removeLast();
        } else {
            int currIndex = 0;
            Node<T> currNode = null;
            WhereStart startPosition = WhereStart.HEADSTART;
            if (index > size / 2) {
                startPosition = WhereStart.TAILSTART;
            }
            switch (startPosition) {
                case HEADSTART:
                    currNode = head;
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
                            break;
                        }

                        currNode = currNode.getNextNode();
                        currIndex++;
                    }
                    break;
                case TAILSTART:
                    currNode = tail;
                    currIndex = size - 1;
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
                            break;
                        }
                        currNode = currNode.getPrevNode();
                        currIndex--;
                    }
                    break;
            }

        }
        return returnValue;
    }

    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        WhereStart startPosition = WhereStart.HEADSTART;
        if (index > size / 2) {
            startPosition = WhereStart.TAILSTART;
        }
        int currIndex = 0;
        Node<T> currNode = null;
        switch (startPosition) {
            case HEADSTART:
                currIndex = 0;
                currNode = head;
                while (currIndex < index) {
                    currNode = currNode.getNextNode();
                    currIndex++;
                }
                break;
            case TAILSTART:
                currIndex = size - 1;
                currNode = tail;
                while (currIndex > index) {
                    currNode = currNode.getPrevNode();
                    currIndex--;
                }
                break;
        }
        currNode.setElement(element);
        modCount++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        WhereStart startPosition = WhereStart.HEADSTART;
        if (index > size / 2) {
            startPosition = WhereStart.TAILSTART;
        }
        int currIndex = 0;
        Node<T> currNode = null;
        switch (startPosition) {
            case HEADSTART:
                currIndex = 0;
                currNode = head;
                while (currIndex < index) {
                    currNode = currNode.getNextNode();
                    currIndex++;
                }
                break;
            case TAILSTART:
                currIndex = size - 1;
                currNode = tail;
                while (currIndex > index) {
                    currNode = currNode.getPrevNode();
                    currIndex--;
                }
                break;
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

    @Override
    public String toString() {
        StringBuilder arrayStringBuilder = new StringBuilder();
        arrayStringBuilder.append("[");

        for (T element : this) {
            arrayStringBuilder.append(element.toString());
            arrayStringBuilder.append(", ");
        }
        if (!isEmpty()) {
            arrayStringBuilder.delete(arrayStringBuilder.length() - 2, arrayStringBuilder.length());
        }
        arrayStringBuilder.append("]");
        return arrayStringBuilder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new IUDoubleLinkedListListIterator();
    }

    /**
     * Private inner class containing all of the functionality of a ListIterator
     * compatible with
     * a double linked list. This class also provides functionality for our basic
     * iterator.
     * 
     */
    private class IUDoubleLinkedListListIterator implements ListIterator<T> {
        private Node<T> currLocation;
        private Node<T> lastReturnedNode;
        private int callsToRemoveOrAdd;
        private int expectedModCount;
        private int currIndex;

        IUDoubleLinkedListListIterator() {
            currLocation = head;
            lastReturnedNode = null;
            callsToRemoveOrAdd = 1;
            expectedModCount = modCount;
            currIndex = 0;
        }

        IUDoubleLinkedListListIterator(int startingIndex) {
            if (startingIndex < 0 || startingIndex > size) {
                throw new IndexOutOfBoundsException();
            }
            lastReturnedNode = null;
            callsToRemoveOrAdd = 1;
            expectedModCount = modCount;
            WhereStart startPosition = WhereStart.HEADSTART;
            if (startingIndex > size / 2) {
                startPosition = WhereStart.TAILSTART;
            }
            switch (startPosition) {
                case HEADSTART:
                    currLocation = head;
                    currIndex = 0;
                    while (currIndex < startingIndex) {
                        currLocation = currLocation.getNextNode();
                        currIndex++;
                    }
                    break;
                case TAILSTART:
                    currLocation = null;
                    currIndex = size;
                    while (currIndex > startingIndex) {
                        if (currLocation == null) {
                            currLocation = tail;
                        } else {
                            currLocation = currLocation.getPrevNode();
                        }
                        currIndex--;
                    }
                    break;
            }
        }

        /**
         * Checks to see if the array has changed using outside methods,
         * to help enforce fail fast behavior
         */
        private void hasChanged() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            hasChanged();
            return currLocation != null;
        }

        @Override
        public T next() {
            T returnValue = null;
            if (hasNext()) {
                lastReturnedNode = currLocation;
                returnValue = currLocation.getElement();
                currLocation = currLocation.getNextNode();
                callsToRemoveOrAdd = 0;
                currIndex++;
            } else {
                throw new NoSuchElementException();
            }
            return returnValue;
        }

        @Override
        public boolean hasPrevious() {
            hasChanged();
            return currIndex > 0;
        }

        @Override
        public T previous() {
            hasChanged();
            T returnValue = null;
            if (hasPrevious()) {
                if (currLocation == null) {
                    currLocation = tail;
                } else {
                    currLocation = currLocation.getPrevNode();
                }
                lastReturnedNode = currLocation;
                returnValue = currLocation.getElement();
                callsToRemoveOrAdd = 0;
            } else {
                throw new NoSuchElementException();
            }
            currIndex--;
            return returnValue;
        }

        @Override
        public int nextIndex() {
            hasChanged();
            return currIndex;
        }

        @Override
        public int previousIndex() {
            hasChanged();
            return currIndex - 1;
        }

        @Override
        public void remove() {
            hasChanged();
            if (callsToRemoveOrAdd >= 1) {
                throw new IllegalStateException();
            }
            Node<T> tempPrevNode = lastReturnedNode.getPrevNode();
            Node<T> tempNextNode = lastReturnedNode.getNextNode();
            if (tempPrevNode == null) {
                head = tempNextNode;
            } else {
                tempPrevNode.setNextNode(tempNextNode);
            }
            if (tempNextNode == null) {
                tail = tempPrevNode;
            } else {
                tempNextNode.setPrevNode(tempPrevNode);
            }

            if (lastReturnedNode == currLocation) {
                currLocation = tempNextNode;
            } else {
                currIndex--;
            }
            lastReturnedNode = null;
            size--;
            modCount++;
            expectedModCount++;
            callsToRemoveOrAdd++;
        }

        @Override
        public void set(T e) {
            hasChanged();
            if (callsToRemoveOrAdd >= 1) {
                throw new IllegalStateException();
            }
            lastReturnedNode.setElement(e);
            modCount++;
            expectedModCount++;
        }

        @Override
        public void add(T e) {
            hasChanged();

            if (currIndex == 0) {
                IUDoubleLinkedList.this.addToFront(e);
            } else if (currIndex == size) {
                IUDoubleLinkedList.this.addToRear(e);
            } else {
                Node<T> newNode = new Node<T>(e);
                Node<T> tempNextNode = currLocation;
                Node<T> tempPrevNode = tempNextNode.getPrevNode();
                newNode.setPrevNode(tempPrevNode);
                newNode.setNextNode(tempNextNode);
                tempPrevNode.setNextNode(newNode);
                tempNextNode.setPrevNode(newNode);
                size++;
                modCount++;

            }
            expectedModCount++;
            currIndex++;
            callsToRemoveOrAdd++;
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        return new IUDoubleLinkedListListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        return new IUDoubleLinkedListListIterator(startingIndex);
    }

}
