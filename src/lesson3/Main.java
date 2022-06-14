package lesson3;

import java.util.Stack;

public class Main {

    public static final String DEQUE = "Deque ";

    public static void main(String[] args) {
        System.out.println("Queue ====================");
        Queue queue = new Queue(3);
        queue.insert(1);
        queue.insert(2);
        queue.insert(3);
        queue.insert(4);
        System.out.println("Queue " + queue);
        System.out.println("Queue remove " + queue.remove());
        System.out.println("Queue peekHead " + queue.peekHead());
        System.out.println("Queue remove " + queue.remove());
        System.out.println("Queue " + queue);

        System.out.println("Deque ====================");
        Deque d = new Deque(3);
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
        System.out.println("Prority queue ====================");
        MyPriorityQueue pqueue = new MyPriorityQueue();
        pqueue.push(4);
        pqueue.push(3);
        pqueue.push(5);
        System.out.println("Priority queue push 3 items " + pqueue);
        pqueue.remove();
        System.out.println("Priority queue " + pqueue);
        System.out.println("Check brackets ====================");
        System.out.println(" (() => " + checkBrackets(" (() "));
        System.out.println(" ()) => " + checkBrackets(" ()) "));
        System.out.println(" ([+] (-) [{!}(x)]) => " + checkBrackets(" (() () (()())) "));
    }

    public static boolean checkBrackets(String str) {
        String open = "({[";
        String close = ")}]";
        int bracketLevel = 0;
        var stack = new Stack<Integer>();
        for (int i = 0; i < str.length(); i++) {
            if (bracketLevel < 0) {
                return false;
            }
            char symbol = str.charAt(i);
            int indexOpen = open.indexOf(symbol);
            int indexClose = close.indexOf(symbol);
            if (indexOpen != -1) {
                stack.push(indexOpen);
                bracketLevel++;
            } else if (indexClose != -1) {
                if (stack.isEmpty())
                    return false;
                if (stack.pop() == indexClose) {
                    bracketLevel--;
                } else
                    return false;
            }
        }
        return (bracketLevel == 0);
    }

    private static class Queue {
        private int maxSize; // размер
        private int[] values; // место хранения
        private int head;    // отсюда уходят
        private int tail;    // сюда приходят
        private int size;   // текущее количество

        public Queue(int s) {
            maxSize = s;
            values = new int[maxSize];
            head = 0;
            tail = -1;
            size = 0;
        }

        public boolean isEmpty() {
            return (size == 0);
        }

        public boolean isFull() {
            return (size == maxSize);
        }

        public int getSize() {
            return size;
        }

        public void incSize() {
            size++;
        }

        public void decSize() {
            size--;
        }

        public int getCapacity() {
            return values.length;
        }

        public void checkEmpty() {
            if (isEmpty())
                throw new RuntimeException("Queue is empty");
        }

        public void insert(int i) {
            grow();
            if (tail == maxSize - 1) {
                tail = -1;
            }
            values[++tail] = i;
            ++size;
        }

        public void grow() {
            if (size + 1 > maxSize) {
                maxSize *= 2;
                int[] tmpArr = new int[maxSize];
                if (tail >= head) {
                    System.arraycopy(values, 0, tmpArr, 0, values.length);
                } else {
                    System.arraycopy(values, 0, tmpArr, 0, tail + 1);
                    System.arraycopy(values, head, tmpArr,
                            maxSize - (values.length - head), values.length - head);
                    head = maxSize - head + 1;
                }
                values = tmpArr;
            }
        }

        public int remove() {
            checkEmpty();
            int temp = values[head++];
            head %= maxSize;
            size--;
            return temp;
        }

        public int peekHead() {
            checkEmpty();
            return values[head];
        }

        public int peekTail() {
            checkEmpty();
            if (tail == -1)
                tail = 0;
            return values[tail];
        }

        @Override
        public String toString() {
            if (size == 0)
                return "";
            StringBuilder sb = new StringBuilder();
            if (tail >= head)
                for (int i = head; i <= tail; i++) {
                    sb.append(values[i]).append(' ');
                }
            else {
                int bound = tail == -1 ? size : maxSize;
                for (int i = head; i < bound; i++) {
                    sb.append(values[i]).append(' ');
                }
                for (int i = 0; i <= tail; i++) {
                    sb.append(values[i]).append(' ');
                }
            }
            return sb.toString();
        }

        public int getHead() {
            return head;
        }

        public void setHead(int head) {
            this.head = (head == -1) ? maxSize - 1 : head;
        }

        public int getTail() {
            return tail;
        }

        public void setTail(int tail) {
            this.tail = (tail == -1) ? 0 : tail;
        }

        public int[] getValues() {
            return values;
        }

    }

    private static class Deque {
        private final Queue queu;

        public Deque(int s) {
            queu = new Queue(s);
        }

        public int getSize() {
            return queu.getSize();
        }

        public void insertTail(int i) {
            queu.insert(i);
            System.out.println("insertTail " + i);
        }

        public void insertHead(int i) {
            queu.grow();
            queu.setHead(dec(queu.getHead()));
            queu.getValues()[queu.getHead()] = i;
            queu.incSize();
            System.out.println("insertHead " + i);
        }

        public int removeHead() {
            int temp = queu.remove();
            System.out.println("removeHead " + temp);
            return temp;
        }

        public int removeTail() {
            queu.checkEmpty();
            queu.setTail(queu.getTail()); // exceptional case of enqueue head
            int temp = queu.getValues()[queu.getTail()];
            queu.setTail(dec(queu.getTail()));
            if (queu.getTail() == queu.getSize())
                queu.setTail(0);
            queu.decSize();
            System.out.println("removeTail " + temp);
            return temp;
        }

        public int peekHead() {
            return queu.peekHead();
        }

        public int peekTail() {
            return queu.peekTail();
        }

        @Override
        public String toString() {
            return queu.toString();
        }


        private int dec(int i) {
            if (--i < 0)
                i = queu.getCapacity() - 1;
            return i;
        }
    }

    public static class MyPriorityQueue {
        private final Queue queue = new Queue(3);

        // поиск элемента с наименьшим приоритетом,
        public int findMin() {
            return queue.peekTail();
        }

        // поиск элемента с наибольшим приоритетом,
        public int findMax() {
            return queue.peekHead();
        }

        // вставка нового элемента,
        public void push(int newVal) {
            queue.grow();
            int i = 0;
            if (!queue.isEmpty()) {
                int[] values = queue.getValues();
                for (i = 0; i < queue.getSize(); i++)
                    if (values[i] < newVal)
                        break;
                System.arraycopy(values, i, values, i + 1, queue.getSize() - i);
            }
            queue.getValues()[i] = newVal;
            queue.incSize();
            System.out.println("Push " + newVal + " queue " + queue);
        }

        // извлечь элемент с наименьшим и наибольшим приоритетом
        public int extractMin() {
            return queue.peekTail();
        }

        public int extractMax() {
            return queue.peekHead();
        }

        // удалить элемент с наибольшим приоритетом,
        public int remove() {
            int temp = queue.getValues()[0];
            System.arraycopy(queue.getValues(), 1, queue.getValues(), 0, queue.getSize() - 1);
            queue.decSize();
            System.out.println("remove " + temp);
            return temp;
        }


        @Override
        public String toString() {
            return queue.toString();
        }
    }
}

