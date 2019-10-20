public class Node {
    String s;
    int val;

    public Node(String s, int val) {
        this.s = s;
        this.val = val;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Node{" +
                "s='" + s + '\'' +
                ", val=" + val +
                '}';
    }
}
