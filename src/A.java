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

    public A(Popolazione p) {
        //costruttore degli avventurieri
        super();
        this.popo = p;
    }

    @Override
    public void run() {
        super.run();
    }
}
