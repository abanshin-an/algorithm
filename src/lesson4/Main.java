package lesson4;


public class Main {
    private static final String DEQUE = "Deque ";

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.insert(30);
        list.insert(10);
        list.insert(5);
        list.insertLast(25);
        list.display();
        Iterator iter = new Iterator(list);
        System.out.println("Итератор");
        while (!iter.atEnd()) {
            System.out.println(iter.next());
        }
        System.out.println("Удаление элементов списка");
        list.delete(25);
        list.deleteLast();
        list.deleteFirst();
        list.display();

        System.out.println("Deque ====================");
        Deque d = new Deque();
        d.insertTail(3);
        d.insertTail(4);
        d.insertHead(2);
        d.insertHead(1);
        d.insertHead(5);
        System.out.println(DEQUE + d);
        d.removeHead();
        System.out.println(DEQUE + d);
        d.removeTail();
        System.out.println(DEQUE + d);
        d.insertTail(5);
        d.insertTail(6);
        d.insertTail(7);
        System.out.println(DEQUE + d);
        d.removeHead();
        System.out.println(DEQUE + d);
        d.removeTail();
        d.removeTail();
        System.out.println(DEQUE + d);
        d.insertHead(2);
        d.insertHead(1);
        d.insertHead(9);
        d.insertHead(10);
        System.out.println(DEQUE + d);
        d.removeHead();
        d.removeTail();
        System.out.println(DEQUE + d);
    }
}


class Link {
    final Integer value;
    Link next;
    Link prev;

    public Link(Link prev, int value, Link next) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }

    public void display() {
        System.out.println("Value: " + this.value);
    }

    public int getValue() {
        return value;
    }

}

class LinkedList {
    Link first;
    Link last;


    private int size = 0;

    public LinkedList() {
        first = null;
        last = null;
    }

    public Integer getFirst() {
        return first.value;
    }

    public Integer getLast() {
        return last.value;
    }

    public boolean isEmpty() {
        return (first == null);
    }

    public int getSize() {
        return size;
    }

    public void insert(int value) {
        insertBefore(first, value);
    }

    public void insertLast(int value) {
        insertAfter(last, value);
    }

    public boolean delete(Integer value) {
        if (value == null) {
            for (Link x = first; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Link x = first; x != null; x = x.next) {
                if (x.getValue() == value) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    public Integer deleteLast() {
        var res = last.value;
        unlink(last);
        return res;
    }

    public Integer deleteFirst() {
        var res = first.value;
        unlink(first);
        return res;
    }

    public void insertBefore(Link link, Integer value) {
        final Link pred = (link == null) ? null : link.prev;
        final Link newLink = new Link(pred, value, link);
        if (link != null) {
            link.prev = newLink;
        }
        if (pred == null)
            first = newLink;
        else
            pred.next = newLink;
        fixFirstLast(newLink);
        size++;
    }

    public void insertAfter(Link link, Integer value) {
        final Link next = (link == null) ? null : link.next;
        Link newLink = new Link(link, value, next);
        if (this.isEmpty()) {
            first = newLink;
        } else {
            last.next = newLink;
        }
        last = newLink;
        fixFirstLast(newLink);
        size++;
    }

    private void fixFirstLast(Link newLink) {
        if (last == null) {
            last = newLink;
        }
        if (first == null) {
            first = newLink;
        }
    }

    private void unlink(Link x) {
        if (size == 0)
            return;
        final Link next = x.next;
        final Link prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.prev = null;
        }
        size--;
    }

    public void display() {
        Link current = first;
        while (current != null) {
            current.display();
            current = current.next;
        }
    }

    public Link find(Integer value) {
        Link current = first;
        while (current.getValue() != value) {
            if (current.next == null)
                return null;
            else
                current = current.next;
        }
        return current;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Link current = first;
        while (current != null) {
            sb.append(current.value).append(' ');
            current = current.next;
        }
        return sb.toString();
    }
}

/*
reset() - перемещение в начало списка
next() - перемещение к следующему
getCurrent() - получить текущий элемент
previous() - получить предыдущий элемент
atEnd() - последний элемент
insertAfter() - вставка элемента после итератора
insertBefore() - вставка элемента перед итератором
deleteCurrent() - удаление текущего элемента
 */
class Iterator {
    private final LinkedList linkedList;
    private Link current;

    public Iterator(LinkedList linkedList) {
        this.linkedList = linkedList;
        reset();
    }

    public void reset() //- перемещение в начало списка
    {
        current = linkedList.first;
    }

    public Integer next() //- перемещение к следующему
    {
        var res = current.value;
        current = current.next;
        return res;
    }

    public Integer getCurrent() // - получить текущий элемент
    {
        return current.value;
    }

    public Integer previous() // - получить предыдущий элемент
    {
        Integer res = current.value;
        current = current.prev;
        return res;
    }

    public boolean atEnd() // - последний элемент
    {
        return current.next == null;
    }
}

class Deque {
    private final LinkedList linkedList = new LinkedList();


    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    public void insertHead(Integer value) {
        linkedList.insert(value);
    }

    public void insertTail(Integer value) {
        linkedList.insertLast(value);
    }

    public Integer removeHead() {
        return linkedList.deleteFirst();
    }

    public Integer removeTail() {
        return linkedList.deleteLast();
    }

    public Integer peekHead() {
        return linkedList.getFirst();
    }

    public Integer peekTail() {
        return linkedList.getLast();
    }

    @Override
    public String toString() {
        return linkedList.toString();
    }
}