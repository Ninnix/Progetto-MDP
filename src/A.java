import java.util.Random;

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

    protected Double virilita = 0.4; //indice che indica la probabilita' di inserirsi nella coda mercato

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
        for (int i = 0; i < 9; i++) { //tentativi di inserirsi nella coda
            if(isInterrupted()){break;}
            double random = new Random().nextDouble();
            if (random < virilita) { //probabilita di avere successo nella riproduzione
                try {
                    this.corteggiamento(); //non e' corretto che l' avventurriero corteggia, si mette semplicemente nella coda!
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println("problema con accoppiamento avventuriero");
                }
            }
        }
        this.popo.avventurieri.remove(this);
    }

    protected synchronized void sveglia(){notify();} //metodo che fa risvegliare l avventuriero che e' stato selezionato per l'accoppiamento

    private void corteggiamento(){
        //corteggiamento dell' avventuriero
        popo.osteria.insert(this); // in realta' e' un falso corteggiamento si mette solo nel mercato!
    }

    //accoppiamento dell'avventuriero gestito nel run
}
