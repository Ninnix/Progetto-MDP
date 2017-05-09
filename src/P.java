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
    }

    @Override
    public tipo getType(){
        return tipo.P ;
    }

    @Override
    public void run() {
        M marito=corteggiamento();
        int tentativi=10;  // la prudente avra' 10 tentatvi a disposizione per trovare un compagno, altrimenti morira' di vecchiaia
        while(marito == null || tentativi>0){
            marito = corteggiamento(); // la prudente cerca un marito
            tentativi--;
        }
        if (tentativi != 0){ // significa che ha trovato un marito
            while(fertilita != 0){
                accoppiamento(marito);
            }
        }
        //la prudente e il marito morigerato muoiono insieme dopo aver cresciuto i propri figli
        synchronized (marito.limiteMor) {
            marito.limiteMor = 0; //muore il marito ...
            marito.limiteMor.notify();
        }
            // ... e muore lei
        this.popo.prudenti.remove(this);
    }


    public M corteggiamento(){
        //corteggiamento della prudente
        M marito = popo.ristorante.poll();
        if (marito == null) return null;
        if (!marito.isAlive()) {
            return corteggiamento(); //se il marito e' morto lo scarta e ne cerca un altro
        }
        // un corteggiamento tra un uomo morigerato e una donna prudente causa un costo in termini genetici
        this.contentezza -= popo.c;
        marito.contentezza -= popo.c;
        return marito;  //spasimante sara' null se la coda e' vuota o non ci sono morigerati
    }


    public void accoppiamento(M m){
        // si stabilisce se la donna prudente concepira' un nuovo figlio
        double random = new Random().nextDouble();
        if (random >= fertilita){   //significa che la donna non concepira' bambini da qui in avanti
            fertilita=0;
            return;
        }

        //a questo punto sara' generato un figlio
        Persona figlio = ((new Random().nextBoolean()) ? new P(this.popo) : new M(this.popo)); // scelta del sesso del nascituro
        if (figlio.getType() == tipo.P) popo.prudenti.add((P)figlio);  //aggiunge il figlio alla popolazione
        else popo.morigerati.add((M)figlio);
        figlio.start();   // nasce il figlio
        this.contentezza += (popo.a - popo.b/2 - popo.c);  // aggiorniamo il valore di contentezza della prudente
        m.contentezza += (popo.a - popo.b/2 - popo.c);  // aggiorniamo il valore di contentezza del morigerato
        fertilita -= 0.2; // aggiorniamo la probabilita' che la prudente abbia un altro figlio
    }
}
