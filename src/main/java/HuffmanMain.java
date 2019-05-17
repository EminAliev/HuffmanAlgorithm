public class HuffmanMain {
    public static void main(String[] args) {
        String msg = "SUSIE SAYS IT IS EASY";
        HuffmanTree tree = HuffmanTree.getTree(msg);
        System.out.println(tree.getEncodedString());
        System.out.println(tree.getDecodedString("10011110110111100100101110100011001100011010001111010101110"));
    }
}
