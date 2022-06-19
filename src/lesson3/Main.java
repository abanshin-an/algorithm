package lesson3;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        System.out.println("Queue ====================");
        Queue queue = new Queue(3);
        queue.insert(1);
        queue.insert(2);
        queue.insert(3);
        queue.insert(4);
        System.out.println("Queue " + queue);
        queue.remove();
        System.out.println("Queue peekHead " + queue.peekHead());
        queue.remove();
        System.out.println("Queue " + queue);

        System.out.println("Deque ====================");
        Deque d = new Deque(3);
        d.insertTail(3);
        d.insertTail(4);
        d.insertHead(2);
        d.insertHead(1);
        d.insertHead(5);
        System.out.println("Deque " + d);
        d.removeHead();
        System.out.println("Deque " + d);
        d.removeTail();
        System.out.println("Deque " + d);
        d.insertTail(5);
        d.insertTail(6);
        d.insertTail(7);
        System.out.println("Deque " + d);
        d.removeHead();
        System.out.println("Deque " + d);
        d.removeTail();
        d.removeTail();
        System.out.println("Deque " + d);
        d.insertHead(2);
        d.insertHead(1);
        d.insertHead(9);
        d.insertHead(10);
        System.out.println("Deque " + d);
        d.removeHead();
        d.removeTail();
        System.out.println("Deque " + d);
        System.out.println("Prority queu ====================");
        MyPriorityQueue pqueue = new MyPriorityQueue();
        pqueue.push(4);
        pqueue.push(3);
        pqueue.push(5);
        System.out.println(pqueue.toString());
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
                if (stack.size() == 0)
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
        public int getCapacity (){
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
            if (isFull()) {
                maxSize *= 2;
                int[] tmpArr = new int[maxSize];
                if (tail >= head) {
                    System.arraycopy(values, 0, tmpArr, 0, values.length);
                } else {
                    System.arraycopy(values, 0, tmpArr, 0, tail + 1);
                    System.arraycopy(values, head, tmpArr,
                            maxSize - (values.length - head), values.length - head);
                    head = maxSize - head - 1;
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
                    sb.append(values[i] + " ");
                }
            else {
                for (int i = head + 1; i < maxSize; i++) {
                    sb.append(values[i] + " ");
                }
                for (int i = 0; i < tail; i++) {
                    sb.append(values[i] + " ");
                }
            }
            return sb.toString();
        }

        public int getHead() {
            return head;
        }

        public void setHead(int head) {
            this.head = (head==-1) ? size-1 : head;
        }

        public int getTail() {
            return tail;
        }

        public void setTail(int tail) {
            this.tail = (tail==-1)? 0 : tail;
        }

        public int[] getValues() {
            return values;
        }

    }

    private static class Deque {
        private Queue queu;

        public Deque(int s) {
            queu = new Queue(s);
        }

        public boolean isEmpty() {
            return queu.isEmpty();
        }

        public boolean isFull() {
            return queu.isFull();
        }

        public int getSize() {
            return queu.getSize();
        }

        public void insertTail(int i) {
            queu.insert(i);
            System.out.println("insertTail " + i + " queu "+queu);
        }

        public void insertHead(int i) {
            queu.grow();
            if (queu.getHead() == 0)
                queu.setTail(getSize());
            queu.setHead(dec(queu.getHead()));
            queu.getValues()[queu.getHead()] = i;
            System.out.println("insertHead " + i+ " queu "+queu);
        }

        public int removeHead() {
            return queu.remove();
        }

        public int removeTail() {
            queu.checkEmpty();
            queu.setTail(queu.getTail()); // exceptional case of enqueue head
            int temp = queu.getValues()[queu.getTail()];
            queu.setTail(dec(queu.getTail()));
            if (queu.getTail() == queu.getSize())
                queu.setTail(0);
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

        private int inc(int i) {
            if (++i >= queu.getCapacity())
                i = 0;
            return i;
        }

        private int dec(int i) {
            if (--i < 0)
                i = queu.getCapacity() - 1;
            return i;
        }
    }

    public static class MyPriorityQueue {
        private final Deque deque = new Deque(3);

        // поиск элемента с наименьшим приоритетом,
        public int findMin() {
            return deque.peekTail();
        }

        // поиск элемента с наибольшим приоритетом,
        public int findMax() {
            return deque.peekHead();
        }

        // вставка нового элемента,
        public void push(int element) {
            int max = element;
            if (!deque.isEmpty()) {
                max = deque.peekHead();
            }
            if (element >= max) {
                deque.insertHead(element);
            } else {
                deque.removeHead();
                deque.insertHead(element);
                deque.insertHead(max);
            }
        }

        // извлечь элемент с наименьшим и наибольшим приоритетом
        public int extractMin() {
            return deque.peekTail();
        }

        public int extractMax() {
            return deque.peekHead();
        }

        // удалить элемент с наибольшим приоритетом,
        public void deleteMin() {
            deque.removeTail();
        }

        public void deleteMax(int element) {
            deque.removeHead();
        }

        @Override
        public String toString() {
            return deque.toString();
        }
    }
}

