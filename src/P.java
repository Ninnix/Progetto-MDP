import java.util.Random;

/**
 * Created by nicolo on 28/04/17.
 */
public class P extends Persona {
    /**
     * donne prudenti, accettano un compagno con cui fare un figlio solo
     * dopo un congruo periodo di corteggiamento;
     */
    public Popolazione popo;

    //probabilita' di avere un figlio
    protected double fertilita= 0.95 ;

    public P(Popolazione p) {
        //costruttore delle prudenti
        super();
        this.popo = p;
        this.nascita= System.currentTimeMillis(); //imposto la data di nascita
    }

    @Override
    public tipo getType(){
        return tipo.P ;
    }

    @Override
    public void run() {
        while(!morte()){
            // esegui operazione del thread
        }
    }

    private Persona corteggiamento(){
        //corteggiamento della prudente
        Persona marito = popo.mercato.poll(); //marito sara' null se la coda e' vuota
        if(marito.getType()== tipo.A ){
            corteggiamento(); // rischio buffer overflow nel caso di coda con moltissimi avventurieri
            popo.mercato.add(marito);// lo rimette nella coda dando la possibilita' ad un altra donna di accoppiarsi con lui
        }
        if (marito != null){
            // un corteggiamento tra un uomo morigerato e una donna prudente causa un costo in termini genetici
            this.contentezza -= popo.c;
            marito.contentezza -= popo.c;
        }
        return marito;
    }


    private void accoppiamento(Persona m){
        // si stabilisce se la donna prudente concepira' un nuovo figlio
        double random = new Random().nextDouble();
        if (random >= fertilita){   //significa che la donna non concepira' bambini da qui in avanti
            fertilita=0;
            return;
        }

        //a questo punto sara' generato un figlio
        Persona figlio = ((new Random().nextBoolean()) ? new P(this.popo) : new M(this.popo)); // scelta del sesso del nascituro
        figlio.run();   // nasce il figlio
        this.contentezza += (popo.a - popo.b/2 - popo.c);  // aggiorniamo il valore di contentezza della prudente
        m.contentezza += (popo.a - popo.b/2 - popo.c);  // aggiorniamo il valore di contentezza del morigerato
        fertilita -= 0.2; // aggiorniamo la probabilita' che la prudente abbia un altro figlio
    }

    /* TODO: 04/05/17  Crescita figli
    va inserito un tempo per la crescita dei figli, ad un certo punto diventeranno
    indipendenti e non dovranno piu' essere accuditi dai genitori*/
}
