
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Donne prudenti, accettano un compagno con cui fare un figlio solo
 * dopo un congruo periodo di corteggiamento;
 */
public class P extends Donna {

    static AtomicInteger count=new AtomicInteger(0);

    public Popolazione popo;

    /**
     * Costruttore delle prudenti
     * @param p Popolazione
     */
    public P(Popolazione p) {
        super();
        this.popo = p;
    }

    /**
     * @return tipo.p rappresenta la prudente
     */
    @Override
    public tipo getType(){
        return tipo.P ;
    }

    /**
     * Azioni svolte dalla prudente durante la sua vita generosa
     */
    @Override
    public void run() {
        try {
            if(isInterrupted()){
                this.popo.prudenti.remove(this);
                return;
            }
            M marito = corteggiamento();
            while (fertilita > 0.0) { //la coppia si riproduce finchè la moglie è fertile
                accoppiamento(marito);
            }
            //la prudente e il marito morigerato muoiono insieme dopo aver cresciuto i propri figli
            marito.virilita = 0; //muore il marito...
            marito.sveglia();

        }catch(InterruptedException e){
            //System.out.println("problema prudente, interruzione coda");
        }
        // ... e muore lei
        this.popo.prudenti.remove(this);
    }


    /**
     * Corteggiamento della prudente cerca finchè non è un morigerato
     * @return il marito
     * @throws InterruptedException
     */
    public M corteggiamento() throws InterruptedException{
        Uomo spasimante;
        while(true) {
            spasimante = popo.ballo.exctract();
            if (spasimante.getType() == tipo.A) {
                ((A)spasimante).ultimaDonna= tipo.P;
                spasimante.virilita -= 0.0005;
                spasimante.sveglia();
            } else {
                return (M) spasimante;
            }
        }
    }


    /**
     * Accopiamento della prudente
     * @param m il marito
     */
    public void accoppiamento(M m) {
        // si stabilisce se la donna prudente concepira' un nuovo figlio
        double random = new Random().nextDouble();
        if (random >= fertilita) {   //significa che la donna non concepira' bambini da qui in avanti
            fertilita = 0.0;
            return;
        }

        //a questo punto sara' generato un figlio
        Persona figlio = ((new Random().nextBoolean()) ? new M(this.popo) : new P(this.popo)); // scelta del sesso del nascituro
        if (figlio.getType() == tipo.P) {
            figlio.setName("P"+P.count.incrementAndGet());
            popo.prudenti.add((P) figlio); //aggiunge il figlio alla popolazione
        }
        else {
            figlio.setName("M"+M.count.incrementAndGet());
            popo.morigerati.add((M) figlio);
        }
        //System.out.println(this.getName()+" si accoppia con "+m.getName()+", e nasce "+figlio.getName());  //Stampa identificativo del figlio
        figlio.start();   // nasce il figlio
        this.contentezza += (popo.a - popo.b / 2 - popo.c);  // aggiorniamo il valore di contentezza della prudente
        m.contentezza += (popo.a - popo.b / 2 - popo.c);  // aggiorniamo il valore di contentezza del morigerato
        fertilita -= 0.2; // aggiorniamo la probabilita' che la prudente abbia un altro figlio
    }
}