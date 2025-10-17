package DS.AVL;
import Interface.IBST_Key;
import DS.BST.BST_Node;
/** Trieda reprezentujúca vrchol AVL stromu, ktorá rozširuje vrchol BST stromu
 * @param <T> Typ dát implementujúci rozhranie IBST_Key
 */
public class AVL_Node<T extends IBST_Key<T>> extends BST_Node<T> {
    private int height;
    /** Konstruktor pre vrchol AVL
     * @param key Kľúč vrchola implementujúci rozhranie IBST_Key
     * @param data Dáta vrchola
     */
    public AVL_Node(IBST_Key<T> key, T data) {
        super(key, data);
        this.height = 1;
    }
    /** Getter a setter pre atribút height */
    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
