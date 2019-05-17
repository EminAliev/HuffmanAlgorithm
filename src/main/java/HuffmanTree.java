import java.util.PriorityQueue;

public class HuffmanTree implements Comparable<HuffmanTree> {
    private Node root;
    private String stringToEncode;

    private static class Node {
        int frequency;
        char symbol;
        Node left;
        Node right;

        public Node(int frequency, char symbol) {
            this.frequency = frequency;
            this.symbol = symbol;
        }

        public Node(HuffmanTree left, HuffmanTree right) {
            this.frequency = left.root.frequency + right.root.frequency;
            this.left = left.root;
            this.right = right.root;
        }
    }

    private HuffmanTree(Node node) {
        this.root = node;
    }

    
    public static HuffmanTree getTree(String string) {
        int[] freqs = new int[256];
        for (char c : string.toCharArray()) {
            freqs[c]++;
        }

        PriorityQueue<HuffmanTree> trees = new PriorityQueue<>();
        for (int i = 0; i < freqs.length; i++) {
            if (freqs[i] > 0) {
                trees.offer(new HuffmanTree(new Node(freqs[i], (char) i)));
            }
        }
        while (trees.size() > 1) {
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
            trees.offer(new HuffmanTree(new Node(a, b)));
        }
        HuffmanTree retTree = trees.poll();
        retTree.stringToEncode = string;
        return retTree;
    }

    private String[] codeTable() {
        String[] codeTable = new String[256];
        buildCodeTable(root, new StringBuilder(), codeTable);
        return codeTable;
    }

    
    private void buildCodeTable(Node node, StringBuilder code, String[] codeTable) {
        if (node.symbol != Character.MIN_VALUE) {
            codeTable[node.symbol] = code.toString();
            return;
        }
        buildCodeTable(node.left, code.append('0'), codeTable);
        code.deleteCharAt(code.length() - 1);
        buildCodeTable(node.right, code.append('1'), codeTable);
        code.deleteCharAt(code.length() - 1);
    }

   
    public String getEncodedString() {
        String[] codes = codeTable();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stringToEncode.length(); i++) {
            result.append(codes[stringToEncode.charAt(i)]);
            result.append(" ");
        }
        return result.toString();
    }

    
    public String getDecodedString(String stringToDecode) {
        char[] sequence = stringToDecode.toCharArray();
        StringBuilder decoded = new StringBuilder();
        Node currentNode = root;
        for (char seqElem : sequence) {
            if (seqElem == '0') {
                currentNode = currentNode.left;
            } else if (seqElem == '1') {
                currentNode = currentNode.right;
            }
            if (currentNode.symbol != Character.MIN_VALUE) {
                decoded.append(currentNode.symbol);
                currentNode = root;
            }
        }
        return decoded.toString();
    }

    @Override
    public int compareTo(HuffmanTree tree) {
        return root.frequency - tree.root.frequency;
    }
}