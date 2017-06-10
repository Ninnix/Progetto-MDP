
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * gli avventurieri, uomini senza scrupoli: se una donna non gli si concede
 * immediatamente, tentano la sorte altrove senza perdere tempo corteggiarla;
 * se gli si concede, partono comunque subito dopo per una nuova avventura,
 * lasciando a lei l’incombenza di crescere la prole;
 */
public class A extends Uomo {

    static AtomicInteger count=new AtomicInteger(0);

    public Popolazione popo;

    protected volatile tipo ultimaDonna=null;

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
                if (ultimaDonna==null || ultimaDonna==tipo.S)
                    if(new Random().nextDouble()>virilita){break;}

                this.corteggiamento(); //morigerato va alla ricerca di una donna al mercato
                this.wait();
            }
        } catch (InterruptedException e) {
            //System.out.println("problema con l accoppiamento del morigerato");
        }
        this.popo.avventurieri.remove(this);
    }

    private void corteggiamento(){
        //corteggiamento dell' avventuriero
        popo.ballo.insert(this); // in realta' e' un falso corteggiamento si mette solo nel mercato!
    }

    //accoppiamento dell'avventuriero gestito nel run
}
