
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * gli avventurieri, uomini senza scrupoli: se una donna non gli si concede
 * immediatamente, tentano la sorte altrove senza perdere tempo per corteggiarla;
 * se non gli si concede, partono comunque subito dopo per una nuova avventura,
 * lasciando a lei l’incombenza di crescere la prole;
 */
public class A extends Uomo {

    static AtomicInteger count=new AtomicInteger(0);

    public Popolazione popo;

    protected volatile tipo ultimaDonna=null;

    /**
     * Costruttore del avventuriero
     * @param p Popolazione
     */
    public A(Popolazione p) {
        super();
        this.popo = p;
    }

    /**
     * @return tipo.A rappresenta l'avventuriero
     */
    @Override
    public tipo getType(){
        return tipo.A;
    }

    /**
     * Azioni svolte dall'avventuriero durante la sua vita gloriosa
     */
    @Override
    public synchronized void run() {
        try {
            while (!isInterrupted() && virilita>0.0 && popo.ballo.isAperto()) {
                if (ultimaDonna==null || ultimaDonna==tipo.S) {
                    if(new Random().nextDouble()>virilita){break;}
                }
                this.corteggiamento(); //avventuriero va alla ricerca di una donna al ballo
                this.wait();
            }
        } catch (InterruptedException e) {
            //System.out.println("problema con l accoppiamento dell' avventuriero");
        }
        this.popo.avventurieri.remove(this);
    }

    /**
     * //corteggiamento dell' avventuriero, e' un falso corteggiamento si mette solo nel ballo per accoppiarsi!
     */
    private void corteggiamento(){
        popo.ballo.insert(this);
    }

    // l'avventuriero non necessita di un metodo accoppiamento perche' tale metodo e' gestito dalla spregiudicata
}
