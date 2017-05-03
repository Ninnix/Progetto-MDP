/**
 * Created by nicolo on 28/04/17.
 */
public class M extends Persona {
    /**
     * uomini morigerati, sono disposti a corteggiare la donna amata e
     * contribuiscono al pari di lei a crescere la prole;
     */

    public Popolazione popo;

    public M(Popolazione p) {
        //costruttore dei morigerati
        super();
        this.popo = p;
    }

    @Override
    public tipo getType(){
        return tipo.M ;
    }


    @Override
    public void run() {
    }

    private void corteggiamento(){
        //corteggiamento del morigerato
        popo.mercato.add(this); //il morigerato si aggiunge alla coda per accoppiarsi
    }
}
