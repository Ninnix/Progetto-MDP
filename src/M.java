
/**
 * Created by nicolo on 28/04/17.
 */
public class M extends Persona {
    /**
     * uomini morigerati, sono disposti a corteggiare la donna amata e
     * contribuiscono al pari di lei a crescere la prole;
     */

    public Popolazione popo;

    protected Integer limiteMor = 3; //limite morale di un morigerato, se e' sfortunato in amore fa massimo 3 tentativi poi si rassegna
                                     //se e' settato a 0 muore

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
        synchronized (this.limiteMor) {
            while (limiteMor > 0) {
                this.corteggiamento(); //morigerato va alla ricerca di una donna al mercato
                try {
                    limiteMor.wait();
                } catch (InterruptedException e) {
                    System.out.println("problema con l accoppiamento del morigerato");
                }
            }
        }
        this.popo.morigerati.remove(this);
    }

    private void corteggiamento(){
        //corteggiamento del morigerato
        try {
            popo.ristorante.put(this); //il morigerato si aggiunge alla coda per accoppiarsi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // il morigerato non necessita di un metodo accoppiamento perche' tale metodo e' gestito dalla moglie
}
