package lesson6;

import java.util.Random;
// 1. Создать и запустить программу для построения двоичного дерева. В цикле построить двести деревьев из 100 элементов.
// Данные, которыми необходимо заполнить узлы деревьев, представляются в виде чисел типа int.
// Число, которое попадает в узел, должно генерироваться случайным образом в диапазоне от -100 до 100.
// 2. Проанализировать, какой процент созданных деревьев являются несбалансированными.
public class Main {
    private static final int NUMBER_OF_TREES=200;
    private static final int TREE_SIZE=100;
    private static final int UPPER_BOUND = 100;
    private static final int LOWER_BOUND = -100;

    public static void main(String[] args) {
        System.out.println("Проверка работоспособности анализатора сбалансированности");
        Tree t1=new Tree();
        t1.insert(0);
        t1.insert(1);
        t1.insert(-1);
        System.out.println(t1.traversePreOrder(t1.root));
        System.out.println("t is balanced = "+t1.isBalanced());
        Tree t2=new Tree();
        t2.insert(0);
        t2.insert(5);
        t2.insert(-5);
        t2.insert(-4);
        t2.insert(-6);
        t2.insert(6);
        System.out.println(t2.traversePreOrder(t2.root));
        System.out.println("t is balanced = "+t2.isBalanced());
        System.out.println();
        System.out.println("Анализ % несбалансированных деревьев");
        Random r = new Random();
        int unbalancedTreeCounter=0;
        for(int i=0;i<NUMBER_OF_TREES;i++) {
            Tree t= new Tree();
            for(int j=0;j<TREE_SIZE;j++)
                t.insert (LOWER_BOUND+r.nextInt(UPPER_BOUND-LOWER_BOUND));
            unbalancedTreeCounter+=t.isBalanced()?0:1;
        }

        System.out.println("Количество несбалансированных деревьев "+unbalancedTreeCounter+" ( "+(unbalancedTreeCounter/NUMBER_OF_TREES*100)+" % )");
    }


    public static class Tree {
        public static class TreeNode {
            private final Integer value;
            private TreeNode leftChild;
            private TreeNode rightChild;

            public TreeNode(int value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return String.format("TN(%s)", value);
            }
        }
        private TreeNode root;
        public Tree() {
            root = null;
        }
        public void insert(int value) {
            TreeNode node = new TreeNode(value);
            if (root == null) {
                root = node;
            } else {
                TreeNode current = root;
                TreeNode parent;
                while (true) {
                    parent = current;
                    if (value < current.value) {
                        current = current.leftChild;
                        if (current == null) {
                            parent.leftChild = node;
                            return;
                        }
                    } else if (value > current.value){
                        current = current.rightChild;
                        if (current == null) {
                            parent.rightChild = node;
                            return;
                        }
                    } else {
                        return;
                    }
                }

            }
        }
        public Integer find(int value) {
            TreeNode current = root;
            while (current.value != value) {
                if (value < current.value)
                    current = current.leftChild;
                else
                    current = current.rightChild;

                if (current == null)
                    return null;
            }
            return current.value;
        }
        private void inOrderTravers(TreeNode current) {
            if (current != null) {
                System.out.println(current);
                inOrderTravers(current.leftChild);
                inOrderTravers(current.rightChild);
            }
        }
        public void displayTree() {
            inOrderTravers(root);
        }
        public boolean delete(int value) {
            TreeNode curr = root;
            TreeNode prev = root;
            boolean isLeftChild = true;
            while (curr.value != value ) {
                prev = curr;
                if (value < curr.value) {
                    isLeftChild = true;
                    curr = curr.leftChild;
                } else {
                    isLeftChild = false;
                    curr = curr.rightChild;
                }

                if (curr == null)
                    return false;
            }

            if (curr.leftChild == null && curr.rightChild == null) {
                if (curr == root) {
                    root = null;
                } else if (isLeftChild) {
                    prev.leftChild = null;
                } else {
                    prev.rightChild = null;
                }
            } else if (curr.rightChild == null) {
                if (isLeftChild) {
                    prev.leftChild = curr.leftChild;
                } else {
                    prev.rightChild = curr.leftChild;
                }
            } else if (curr.leftChild == null) {
                if (isLeftChild) {
                    prev.leftChild = curr.rightChild;
                } else {
                    prev.rightChild = curr.rightChild;
                }
            } else {
                TreeNode successor = getSuccessor(curr);
                if (curr == root) {
                    root = successor;
                } else if (isLeftChild) {
                    prev.leftChild = successor;
                } else {
                    prev.rightChild = successor;
                }
                successor.leftChild = curr.leftChild;
            }
            return true;
        }
        // по материалам  https://translate.google.com/?sl=ru&tl=en&text=количество%20элементов%20деревьев&op=translate
        private TreeNode getSuccessor(TreeNode deleted) {
            TreeNode successorParent = deleted;
            TreeNode successor = deleted;
            TreeNode flag = deleted.rightChild;

            while (flag != null) {
                successorParent = successor;
                successor = flag;
                flag = flag.leftChild;
            }
            if (successor != deleted.rightChild) {
                successorParent.leftChild = successor.rightChild;
                successor.rightChild = deleted.rightChild;
            }
            return successor;
        }

        public boolean isBalanced() {
            return isBalancedRecursive(root, -1).isBalanced;
        }

        private Result isBalancedRecursive(TreeNode tree, int depth) {
            if (tree == null) {
                return new Result(true, -1);
            }

            Result leftSubtreeResult = isBalancedRecursive(tree.leftChild, depth + 1);
            Result rightSubtreeResult = isBalancedRecursive(tree.rightChild, depth + 1);

            boolean isBalanced = Math.abs(leftSubtreeResult.height - rightSubtreeResult.height) <= 1;
            boolean subtreesAreBalanced = leftSubtreeResult.isBalanced && rightSubtreeResult.isBalanced;
            int height = Math.max(leftSubtreeResult.height, rightSubtreeResult.height) + 1;

            return new Result(isBalanced && subtreesAreBalanced, height);
        }

        private static class Result {
            private final boolean isBalanced;
            private final int height;

            private Result(boolean isBalanced, int height) {
                this.isBalanced = isBalanced;
                this.height = height;
            }
        }

        public String traversePreOrder(TreeNode root) {

            if (root == null) {
                return "";
            }

            StringBuilder sb = new StringBuilder();
            sb.append(root.value);

            String pointerRight = "└──";
            String pointerLeft = (root.rightChild != null) ? "├──" : "└──";

            traverseNodes(sb, "", pointerLeft, root.leftChild, root.rightChild != null);
            traverseNodes(sb, "", pointerRight, root.rightChild, false);

            return sb.toString();
        }
        public void traverseNodes(StringBuilder sb, String padding, String pointer, TreeNode node,
                                  boolean hasRightSibling) {
            if (node != null) {
                sb.append("\n");
                sb.append(padding);
                sb.append(pointer);
                sb.append(node.value);

                StringBuilder paddingBuilder = new StringBuilder(padding);
                if (hasRightSibling) {
                    paddingBuilder.append("│  ");
                } else {
                    paddingBuilder.append("   ");
                }

                String paddingForBoth = paddingBuilder.toString();
                String pointerRight = "└──";
                String pointerLeft = (node.rightChild != null) ? "├──" : "└──";

                traverseNodes(sb, paddingForBoth, pointerLeft, node.leftChild, node.rightChild != null);
                traverseNodes(sb, paddingForBoth, pointerRight, node.rightChild, false);
            }
        }
    }
}
