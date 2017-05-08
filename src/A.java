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

    public Popolazione popo;

    private double virilita = 0.5; //indice che indica la probabilita' di inserirsi nella coda mercato
    protected Integer conquiste=0; // serve all avventuriero per tenere traccia del numero delle sue conquiste amorose

    public A(Popolazione p) {
        //costruttore degli avventurieri
        super();
        this.popo = p;
        this.nascita= System.currentTimeMillis(); //imposto la data di nascita
    }

    @Override
    public tipo getType(){
        return tipo.A;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) { //tentativi di inserirsi nella coda
            double random = new Random().nextDouble();
            if (random <= virilita){ //probabilita di avere successo nella riproduzione
                this.corteggiamento(); //non e' corretto che l' avventurriero corteggia, si mette semplicemente nella coda!
                synchronized (this.conquiste) {
                    try {
                        conquiste.wait();
                    } catch (InterruptedException e) {
                        System.out.println("problema con accoppiamento avventuriero");
                    }
                }
            }
        }
    }

    private void corteggiamento(){
        //corteggiamento dell' avventuriero
        popo.mercato.add(this); // in realta' e' un falso corteggiamento si mette solo nel mercato!
    }

    //accoppiamento dell'avventuriero gestito nel run
}
