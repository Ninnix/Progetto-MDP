import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Samuele on 30/05/2017.
 */
public class SynchroSet<E> {

    List<E> spasimanti= new LinkedList<E>();

    private volatile boolean aperto= true;

    public synchronized boolean isEmpty() { return spasimanti.isEmpty(); }

    public synchronized void insert(E elem) {

        if (isEmpty()) {
            spasimanti.add(elem);
            notifyAll();
        }
        else {
            spasimanti.add(elem);
        }
    }

    public synchronized E exctract() throws InterruptedException {

        while (isEmpty()){
            wait();
        }
        int indice= new Random().nextInt(spasimanti.size());
        E result = spasimanti.get(indice);

        return result;
    }

    public void chiudi(){
        this.aperto=false;
    }

    public boolean isAperto(){
        return aperto;
    }
}
