package lesson7;

// 1. Реализовать программу, в которой задается граф из 10 вершин. Задать ребра и найти кратчайший путь с помощью поиска в ширину.

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Random r = new Random();
        Graph g = new Graph();
        for (char c = 'A'; c <= 'Z'; c++) {
            g.addVertex(c);
        }
        for (int i = 0; i < 2 * g.size; i++) {
            g.addEdge(r.nextInt(g.size), r.nextInt(g.size));
        }
        g.printAdjacency();
        g.widthTraverse();
        Stack<Character> shortWay = g.shortWay('A', 'V');
        if (shortWay != null) {
            while (!shortWay.isEmpty()) {
                System.out.print((char) shortWay.pop());
                System.out.print((shortWay.isEmpty() ? "" : "->"));
            }
        }
    }

    public static class Graph {
        private final int MAX_VERTICES = 27;
        private final Vertex[] vertices;
        private final int[][] adjacency;
        private int size;
        public Graph() {
            vertices = new Vertex[MAX_VERTICES];
            adjacency = new int[MAX_VERTICES][MAX_VERTICES];
        }

        public void addVertex(char label) {
            vertices[size++] = new Vertex(label);
        }

        private void addEdge(int start, int end) {
            adjacency[start][end] = 1;
            adjacency[end][start] = 1;
        }

        public void displayVertex(int index) {
            System.out.println(vertices[index]);
        }

        private int getUnvisited(int ver) {
            for (int i = 0; i < size; i++) {
                if (adjacency[ver][i] == 1 &&
                        !vertices[i].isVisited) {
                    return i;
                }
            }
            return -1;
        }

        public void deepTraverse() {
            Stack<Integer> stack = new Stack<Integer>();
            displayVertex(0);
            stack.push(0);
            while (!stack.isEmpty()) {
                int v = getUnvisited(stack.peek());
                if (v == -1) {
                    stack.pop();
                } else {
                    vertices[v].isVisited = true;
                    displayVertex(v);
                    stack.push(v);
                }
            }
            for (int i = 0; i < size; i++) {
                vertices[i].isVisited = false;
            }
        }

        public void widthTraverse() {
            int v2;
            Queue<Integer> queue = new LinkedList<Integer>();
            vertices[0].isVisited = true;
            displayVertex(0);
            queue.add(0);
            while (!queue.isEmpty()) {
                int v1 = queue.remove();
                while ((v2 = getUnvisited(v1)) != -1) {
                    vertices[v2].isVisited = true;
                    displayVertex(v2);
                    queue.add(v2);
                }
            }
            for (int i = 0; i < size; i++) {
                vertices[i].isVisited = false;
            }
        }

        public void printAdjacency() {
            System.out.printf("%n   ");
            for (int j = 0; j < size; j++) {
                System.out.printf("%-3s", vertices[j].label);
            }
            for (int i = 0; i < size; i++) {
                System.out.printf("%n%-3s", vertices[i].label);
                for (int j = 0; j < size; j++) {
                    System.out.printf("%-3d", adjacency[i][j]);
                }
            }
            System.out.println();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (adjacency[i][j] != 0)
                        System.out.println(vertices[i] + " -> " + vertices[j]);
                }
            }
        }

        public int getIndex(char c) {
//            return (c-'A');
            for (int i = 0; i < vertices.length; i++) {
                if (vertices[i].label == c) {
                    return i;
                }
            }
            return -1;
        }

        public Stack<Character> shortWay(char from, char to) {
            Stack<Character> result = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            int start = getIndex(from);
            int stop = getIndex(to);
            if (start == -1 || stop == -1 || start == stop) {
                return null;
            }
            vertices[start].isVisited = true;
            queue.add(start);
            while (!queue.isEmpty()) {
                int vCur = queue.remove();
                int vNxt;
                while ((vNxt = getUnvisited(vCur)) != -1) {
                    vertices[vNxt].parent = vertices[vCur];
                    vertices[vNxt].isVisited = true;
                    if (vNxt == stop)
                        break;
                    queue.add(vNxt);
                }
                if (vNxt == stop)
                    break;
            }
            if (!vertices[stop].isVisited) return null;
            result.push(vertices[stop].label);
            int current = stop;
            while (vertices[current].parent != null) {
                for (int i = 0; i < vertices.length; i++) {
                    if (vertices[current].parent == vertices[i]) {
                        result.push(vertices[i].label);
                        current = i;
                        break;
                    }
                }
            }
            for (int i = 0; i < size; i++) {
                vertices[i].isVisited = false;
                vertices[i].parent = null;
            }
            return result;
        }

        class Vertex {
            public final char label;
            public boolean isVisited;
            public Vertex parent;

            public Vertex(char label) {
                this.label = label;
                this.isVisited = false;
            }

            @Override
            public String toString() {
                return "V (" + label + ")";
            }
        }
    }
}
