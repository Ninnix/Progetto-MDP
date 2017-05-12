/**
 * Implementazione con linked list del tipo di dato astratto delle code sincronizzate
 * @param <E>
 */
public class SynchroCoda <E> {

    protected MyListElem<E> first = null;
    protected MyListElem<E> last =  null;

    public synchronized boolean isEmpty() { return (first == null); }

    public synchronized void insert(E elem) {

        if (isEmpty()) {
            first = last = new MyListElem<E>(elem);
            notifyAll();
        }
        else {
            last.next = new MyListElem<E>(elem);
            last = last.next;
        }
    }

    public synchronized E exctract() throws InterruptedException {

        while (isEmpty()){
            wait();
        }

        E result = first.value;

        first = first.next;
        if (first == null) last = null;
        return result;
    }
}
