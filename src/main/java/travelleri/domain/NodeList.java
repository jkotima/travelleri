package travelleri.domain;

public class NodeList {
    int[] path;
    int index;

    NodeList(int nodesCount) {
        this.path = new int[nodesCount];
        this.index = 0;
    }
    public void add(int node) {
        path[index] = node;
        index++;
    }
    public int[] getPath() {
        return path;
    }
}