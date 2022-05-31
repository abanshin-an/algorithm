package lesson2;

public class Lesson2 {

    public static void main(String[] args) {

        MyArray arr0 = new MyArray(new int[]{7, -1, 6, 3, 2, 5, 6, 7, 0});
        System.out.println("deleteAll ========= ");
        arr0.display();
        arr0.deleteAll(7);
        arr0.display();

        arr0 = new MyArray(new int[]{7, -1, 6, 3, 2, 5, 6, 7, 0});
        System.out.println("insert at ========= ");
        arr0.display();
        arr0.insert(9, 1);
        arr0.insert(3, 9);
        arr0.display();

        MyArray arr1 = new MyArray(new int[]{7, -1, 6, 3, 2, 5, 6, 7, 0});
        System.out.println("sortBubbleEncanced ========= ");
        arr1.display();
        arr1.sortBubbleEnhanced();
        arr1.display();

        MyArray arr2 = new MyArray(new int[]{7, -1, 5, 3, 4, 5, 6, 7, 0});
        System.out.println("sortCount ========= ");
        arr2.display();
        arr2.sortCount();
        arr2.display();
    }

    private static class MyArray {
    private int[] arr;
    private int capacity;

    public MyArray(int size) {
        this.capacity = 0;
        this.arr = new int[size];
    }

    public MyArray(int[] init) {
        this.capacity = init.length;
        this.arr = init;
    }

    void display() {
        for (int i = 0; i < this.capacity; ++i) {
            System.out.print(this.arr[i] + " ");
        }
        System.out.println();
    }

    public int get(int idx) {
        return arr[idx];
    }

    public void set(int value, int idx) {
        arr[idx] = value;
    }

    boolean delete(int value) {
        for (int i = 0; i < this.capacity; i++) {
            if (this.arr[i] == value) {
                System.arraycopy(this.arr, i + 1, this.arr, i, this.capacity - i - 1);
                --capacity;
                return true;
            }
        }
        return false;
    }

    void append(int value) {
        if (this.capacity == this.arr.length) {
            int[] old = this.arr;
            this.arr = new int[old.length * 2];
            System.arraycopy(old, 0, arr, 0, old.length);
        }
        this.arr[this.capacity++] = value;
    }

    public boolean isInArray(int value) { // O(n)
        for (int i = 0; i < this.capacity; i++)
            if (this.arr[i] == value)
                return true;
        return false;
    }

    //O(log(N))
    public boolean hasValue(int value) {
        int low = 0;
        int high = this.capacity - 1;
        int mid;
        while (low < high) {
            mid = (low + high) / 2;
            if (value == this.arr[mid]) {
                return true;
            } else {
                if (value < this.arr[mid]) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
        }
        return false;
    }

    private void swap(int a, int b) {
        int tmp = this.arr[a];
        this.arr[a] = this.arr[b];
        this.arr[b] = tmp;
    }

    public void sortBubble() {
        for (int iter = 0; iter < capacity; iter++)
            for (int idx = 0; idx < capacity - 1; idx++)
                if (this.arr[idx] > this.arr[idx + 1])
                    swap(idx, idx + 1);
    }

    public void sortSelect() {
        for (int idx = 0; idx < capacity; idx++) {
            int curr = idx;
            for (int srch = idx + 1; srch < capacity; srch++)
                if (this.arr[srch] < this.arr[curr])
                    curr = srch;
            if (curr != idx)
                swap(idx, curr);
        }
    }

    public void sortInsert() {
        for (int curr = 1; curr < capacity; curr++) {
            int temp = this.arr[curr];
            int move = curr;
            while (move > 0 && this.arr[move - 1] >= temp) {
                this.arr[move] = this.arr[move - 1];
                move--;
            }
            this.arr[move] = temp;
        }
    }

    public boolean deleteAll(int value) {
        boolean flag = false;
        for (int i = 0; i < capacity; i++) {
            if (arr[i] == value) {
                flag = true;
                System.arraycopy(arr, i + 1, arr, i, capacity - i - 1);
                capacity--;
            }
        }
        return flag;
    }

    public boolean deleteAll() {
        capacity = 0;
        return true;
    }

    public boolean insert(int idx, int value) {
        if (idx < 0 || idx > capacity + 1)
            return false; // отрицательные значения и вставка за пределы массива более, чем на 1 элемент не допускается
        if (arr.length == capacity) { // если нужно расширить массив
            int[] arr1 = new int[++capacity];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            arr = arr1;
        }
        if (idx < capacity - 1) { // если нужно раздвинуть значения массива
            System.arraycopy(arr, idx, arr, idx + 1, arr.length - idx - 1);
        }
        arr[idx] = value; // вставить элемент
        return true;
    }

    public void sortBubbleEnhanced() {
        for (int j = 0; j < capacity - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int t = arr[j + 1];
                arr[j + 1] = arr[j];
                arr[j] = t;
                for (int v = j; v > 0; v--) {
                    if (arr[v] < arr[v - 1]) {
                        t = arr[v - 1];
                        arr[v - 1] = arr[v];
                        arr[v] = t;
                    } else break;
                }
            }
        }
    }

    public void sortCount() {
        int min = arr[0];
        int max = arr[0];
        for (int i = 1; i < capacity; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        if (min == max) {
            return;
        }
        int[] count = new int[max - min + 1];
        for (int i = 0; i < capacity; i++) {
            count[arr[i] - min]++;
        }
        int k = 0;
        for (int i = min; i < max - min; i++) {
            for (int j = 0; j < count[i - min]; j++) {
                arr[k++] = i;
            }
        }
    }
}
}


