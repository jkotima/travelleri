package travelleri.domain;

/**
 * Tietorakenne polkujen tallentamiseen lisäämällä solmu taulukon loppuun (BranchTSP ja ApproxTSP)
 */
public class NodeList {
    int[] path;
    int index;

    /**
     * Konstruktori.
     *
     * @param size alustettavan taulukon koko
     */
    NodeList(int size) {
        this.path = new int[size];
        this.index = 0;
    }

    /**
     * Kopiokonstruktori.
     *
     * @param another kopioitava NodeList-olio
     */
    NodeList(NodeList another) {
        this.path = another.path.clone();
        this.index = another.index;
    }

    /**
     * Lisää solmun polkuun.
     *
     * @param node solmu
     */
    public void add(int node) {
        path[index] = node;
        index++;
    }

    /**
     * Palauttaa polun (int[])
     *
     * @return polku
     */
    public int[] getPath() {
        return path;
    }

    /**
     * Palauttaa viimeisimpänä lisätyn solmun
     *
     * @return viimeinen solmu
     */
    public int getLast() {
        return path[index - 1];
    }
}