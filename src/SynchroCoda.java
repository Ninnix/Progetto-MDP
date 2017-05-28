
/**
 * Implementazione con linked list del tipo di dato astratto delle code sincronizzate
 * @param <E>
 */
public class SynchroCoda <E> {

    protected MyListElem<E> first = null;
    protected MyListElem<E> last =  null;

    private volatile boolean aperto= true;

    /**
     * controlla se la coda e' vuota o no
     * @return true se la coda e' vuota, false altrimenti
     */
    public synchronized boolean isEmpty() { return (first == null); }

    /**
     * versione di inserimento concorrente
     * @param elem
     */
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

    /**
     * versione del pop concorrente, gli elementi in attesa non verranno estratti in ordine di arrivo
     * @return l'elemento  estratto
     * @throws InterruptedException
     */
    public synchronized E exctract() throws InterruptedException {

        while (isEmpty()){
            wait();
        }

        E result = first.value;

        first = first.next;
        if (first == null) last = null;
        return result;
    }

    public void chiudi(){
        this.aperto=false;
    }

    public boolean isAperto(){
        return aperto;
    }
}
