import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nicolo on 28/04/17.
 */
public class M extends Persona {
    /**
     * uomini morigerati, sono disposti a corteggiare la donna amata e
     * contribuiscono al pari di lei a crescere la prole;
     */

    public Popolazione popo;

    protected AtomicInteger limiteMor = new AtomicInteger(3); //limite morale di un morigerato, se e' sfortunato in amore fa massimo 3 tentativi poi si rassegna
                                                                //se e' settato a 0 muore

    public M(Popolazione p) {
        //costruttore dei morigerati
        super();
        this.popo = p;
        this.nascita= System.currentTimeMillis(); //imposto la data di nascita
    }

    @Override
    public tipo getType(){
        return tipo.M ;
    }


    @Override
    public void run() {
        while(limiteMor.get()> 0){
            this.corteggiamento(); //morigerato va alla ricerca di una donna al mercato
            try {
                limiteMor.wait();
            } catch (InterruptedException e) {
                System.out.println("problema con l accoppiamento del morigerato");
            }
        }
    }

    private void corteggiamento(){
        //corteggiamento del morigerato
        popo.mercato.add(this); //il morigerato si aggiunge alla coda per accoppiarsi
    }

    // il morigerato non necessita di un metodo accoppiamento perche' tale metodo e' gestito dalla moglie
}
