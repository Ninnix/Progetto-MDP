import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nicolo on 28/04/17.
 */
public class A extends Persona {
    /**
     * gli avventurieri, uomini senza scrupoli: se una donna non gli si concede
     * immediatamente, tentano la sorte altrove senza perdere tempo corteggiarla;
     * se gli si concede, partono comunque subito dopo per una nuova avventura,
     * lasciando a lei l’incombenza di crescere la prole;
     */

    static AtomicInteger count=new AtomicInteger(0);

    public Popolazione popo;

    protected volatile double virilita = 0.95; //indice che indica la probabilita' di inserirsi nella coda mercato

    public A(Popolazione p) {
        //costruttore degli avventurieri
        super();
        this.popo = p;
    }

    @Override
    public tipo getType(){
        return tipo.A;
    }

    @Override
    public synchronized void run() {
        try {
            while (!isInterrupted() && virilita>0.0 && popo.ballo.isAperto()) {
                if(new Random().nextDouble()>virilita){break;}
                this.corteggiamento(); //morigerato va alla ricerca di una donna al mercato
                this.wait();
                //virilita-=0.15;
            }
        } catch (InterruptedException e) {
            //System.out.println("problema con l accoppiamento del morigerato");
        }
        this.popo.avventurieri.remove(this);
    }

    protected synchronized void sveglia(){notify();} //metodo che fa risvegliare l avventuriero che e' stato selezionato per l'accoppiamento

    private void corteggiamento(){
        //corteggiamento dell' avventuriero
        popo.ballo.insert(this); // in realta' e' un falso corteggiamento si mette solo nel mercato!
    }

    //accoppiamento dell'avventuriero gestito nel run
}
