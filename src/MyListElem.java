
/**
 * I soliti elementi di linked list
 * @param <E>
 */
public class MyListElem<E> {

    public E value;
    public MyListElem<E> next = null;

    /**
     * Semplice costruttore di MylistElem
     * @param value contenuto dell' elemento
     */
    MyListElem (E value) {this.value = value;}
}
