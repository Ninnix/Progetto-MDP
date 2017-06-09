
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Implementazione con LinkedList<E> del tipo di dato astratto
 * dei set in ambiente multithreading con exctract
 * @param <E> Generico
 */
public class SynchroSet<E> {

    List<E> spasimanti= new LinkedList<E>(); //lista con cui si rappresenta l'insieme

    private volatile boolean aperto= true; //abilita o meno l'utilizzo del set

    /**
     * @return true se il SyncroSet è vuoto, altrimenti false
     */
    public synchronized boolean isEmpty() { return spasimanti.isEmpty(); }

    /**
     * Inserisce un elemento nell'insieme
     * @param elem elemento da inserire
     */
    public synchronized void insert(E elem) {

        if (isEmpty()) {
            spasimanti.add(elem);
            notifyAll();
        }
        else {
            spasimanti.add(elem);
        }
    }

    /**
     * Estrae un elemento casuale dall'insieme
     * @return l'elemento estratto
     * @throws InterruptedException
     */
    public synchronized E exctract() throws InterruptedException {

        while (isEmpty()){
            wait();
        }
        int indice= new Random().nextInt(spasimanti.size());
        E result = spasimanti.remove(indice);

        return result;
    }

    /**
     * disattiva l'utilizzo del SynchroSet
     */
    public void chiudi(){
        this.aperto=false;
    }

    /**
     * @return True se l'insieme è attivo, false altrimenti
     */
    public boolean isAperto(){
        return aperto;
    }
}
