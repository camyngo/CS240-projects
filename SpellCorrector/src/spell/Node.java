package spell;

public class Node implements INode {
    Node[] nodes;
    int count;

    public Node() {
        //create an array of 26 nodes
        nodes = new Node[26];
        count = 0;
    }

    @Override
    public int getValue() {
        return count;
    }

    public Node getNode(char c ) {
        //because char doesnt support tolowercase()
        Node newnode = new Node();
        if (c >= 'a' && c <= 'z') {
            newnode = nodes[c-'a'];
        }
        return newnode;
    }
    public void setNode(char c, Node w) {
        nodes[c-'a'] = w ;
    }
}
