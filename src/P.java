/**
 * Created by nicolo on 28/04/17.
 */
public class P extends Persona {
    /**
     * donne prudenti, accettano un compagno con cui fare un figlio solo
     * dopo un congruo periodo di corteggiamento;
     */
    public Popolazione popo;

    public P(Popolazione p) {
        //costruttore delle prudenti
        super();
        this.popo = p;
    }

    @Override
    public void run() {
        super.run();
        //accoppiamento della prudente
        Persona marito = popo.mercato.poll();
        //controllo se il marito e' null o e' avventurriero
    }
}
