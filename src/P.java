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
    protected double fertilita= 0.90 ;

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
        M marito = corteggiamento();
        int tentativi=5;
        while (marito == null && tentativi>0) { //continua a cercare se non si sono ancora presentati morigerati
            marito = corteggiamento();
            tentativi--;
        }
        if(marito==null){return;} //muore la prudente
        while (fertilita > 0.0) { //la coppia si riproduce finchè la moglie è fertile
            accoppiamento(marito);
        }
        synchronized (marito) { //la prudente e il marito morigerato muoiono insieme dopo aver cresciuto i propri figli
            marito.limiteMor = 0; //muore il marito ...
            marito.notify();
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
