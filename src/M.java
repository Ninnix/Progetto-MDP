
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Uomini morigerati, sono disposti a corteggiare la donna amata e
 * contribuiscono al pari di lei a crescere la prole;
 */
public class M extends Uomo {

    static AtomicInteger count=new AtomicInteger(0);

    public Popolazione popo;

    /**
     * Costruttore del morigerato
     * @param p Popolazione
     */
    public M(Popolazione p) {
        super();
        this.popo = p;
    }

    /**
     * @return tipo.M rappresenta il morigerato
     */
    @Override
    public tipo getType(){
        return tipo.M ;
    }

    /**
     * Azioni svolte dal morigerato durante la sua vita generosa
     */
    @Override
    public synchronized void run() {
        try {
            while (!isInterrupted() && virilita>0.0 && popo.ballo.isAperto()) {
                if(new Random().nextDouble()>virilita){break;}
                this.corteggiamento(); //morigerato va alla ricerca di una donna al ballo
                this.wait();
            }
        } catch (InterruptedException e) {
            //System.out.println("problema con l'accoppiamento del morigerato");
        }
        this.popo.morigerati.remove(this);
    }

    /**
     * Corteggiamento del morigerato, si aggiunge alla coda per accoppiarsi!
     */
    private void corteggiamento(){
        popo.ballo.insert(this);
    }

    // il morigerato non necessita di un metodo accoppiamento perche' tale metodo e' gestito dalla moglie
}
