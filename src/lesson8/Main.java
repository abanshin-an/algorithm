package lesson8;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(25);
        hashTable.insert(new Cat("Барсик", 10));
        hashTable.insert(new Cat("Мурзик", 20));
        hashTable.insert(new Cat("Васька", 30));
        hashTable.insert(new Cat("Леопольд", 45));
        hashTable.insert(new Cat("Пират", 40));
        hashTable.insert(new Cat("Пушок", 50));
        hashTable.insert(new Cat("Бегемот", 60));
        hashTable.insert(new Cat("Ученый кот", 75));
        System.out.println(hashTable);
        System.out.println(hashTable.delete(60));
        System.out.println(hashTable);
    }

    static class Cat {
        String name;
        int age;

        public Cat(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cat)) return false;
            Cat cat = (Cat) o;
            return name.equals(cat.name) && (age == cat.age);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Cat{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    static class HashTable {
        private final List<Cat>[] hashArray;
        private final int arrSize;

        public HashTable(int arrSize) {
            this.arrSize = arrSize;
            hashArray = new List[arrSize];
        }

        //        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arrSize; i++) {
                sb.append(hashArray[i] != null ? hashArray[i] : "*");
                if (i < arrSize - 1) sb.append(", ");
            }
            return sb.toString();
        }

        private int hashFunc(int key) {
            return key % arrSize;
        }

        public Cat find(Integer key) {
            int hashVal = hashFunc(key);
            List<Cat> list = hashArray[hashVal];
            if (list!=null)
                return list.stream().filter(cat -> key.equals(cat.getAge())).findAny().orElse(null);
            return null;
        }

        public void insert(Cat cat) {
            int key = cat.getAge();
            int hashVal = hashFunc(key);
            List<Cat> list = hashArray[hashVal];
            if (list == null) {
                list = new LinkedList<>();
                hashArray[hashVal]=list;
            }
            list.add(cat);
        }

        public Cat delete(int key) {
            Cat cat = find(key);
            if (cat != null) {
                int hashVal = hashFunc(key);
                List<Cat> list = hashArray[hashVal];
                list.remove(cat);
            }
            return cat;
        }
    }
}