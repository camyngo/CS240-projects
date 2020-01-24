package spell;

public class Trie implements ITrie {
    public Node root;
    int wordCount;
    int nodeCount;
    //create a public TRIE class
    public Trie() {
        root = new Node();
        wordCount = 0;
        nodeCount = 1;
    }
    @Override
    public void add(String word) {
        //create a copy of root
        Node node = root;
        Trie trie = new Trie();
        char c;
        word= word.toLowerCase();
        for (int i = 0; i < word.length(); i++)
        {
            c = word.charAt(i);
            //if the root node doesnt have the character
            if (node.getNode(c) == null)
            {
                // create a new word list after the root
                node.setNode(c, new Node());
                nodeCount ++;
            }
            node = node.getNode(c);
        }
        // if we haven't count the node then count the word
        // reduce repeating case for word count
        if(node.count == 0)
            wordCount++;
        node.count ++;
    }

    @Override
    public INode find(String word) {
        Node node = root;
        word = word.toLowerCase();
        char c;
        for (int i = 0; i < word.length(); i++)
        {
            c = word.charAt(i);
            //if the root node doesnt have the character then return nothing
            if (node.getNode(c) == null)
            {
                return null;
            }
            node = node.getNode(c);
        }
        if (node.count == 0) return null;
        else return node;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }
    public String toString() {
        StringBuilder myWord = new StringBuilder(10);
        StringBuilder res = new StringBuilder(getWordCount()*6);
        rectostring(myWord,res,root);
        return res.toString();
    }

    public void rectostring(StringBuilder word, StringBuilder res, Node node){
        if (node == null ) return;
        else if (node.getValue() > 0)
        {
            res.append(word);
            res.append('\n');
        }

        for ( char i  = 'a'; i <= 'z'; i++)
        {
            word.append(i);
            // need to call the fuction again to make it recursively loops
            rectostring(word,res,node.getNode(i));
            //after printing a word, delete the length by 1
            word.deleteCharAt(word.length()-1);
        }

    }

    public int hashCode() {
        return rechashcode(root);
    }
    public int rechashcode(Node start){
        if (start == null) return 1;
        int num = 0;
        for (int i = 'a'; i<='z'; i++) {
            // creating a unique number depending on length and words
            num += wordCount*nodeCount + i * rechashcode(start.getNode((char) i));
        }
        return num;
    }


    public boolean equals(Object o) {
        // checking if o is a node and not empty before comparing
        if(o!=null && o instanceof Trie)
        {
            Trie w = (Trie) o ;
            if (w.getWordCount() != getWordCount() || w.getNodeCount()!= getNodeCount()){
                return false;
            }
            return recEquals(((Trie) o).root, root);
        }
        else return false;

    }

    public boolean recEquals( Node start, Node start_o ){
        if (start_o == null || start == null) {

            return start_o==null && start == null;
        }
        if (start_o.count != start.count) return false;

        for (char i='a';i<='z';i++) {
            if (!recEquals(start.getNode(i),start_o.getNode(i))) return false;
        }
        return true;
    }
}
