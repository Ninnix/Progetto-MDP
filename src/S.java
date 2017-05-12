import java.util.Random;

/**
 * Created by nicolo on 03/05/17.
 */
public class S extends Persona{
    /**
     * donne spregiudicate, si concedono ad un uomo anche al primo incontro,
     * se cosı̀ credono.
     */

    public Popolazione popo;

    //probabilita' di avere un figlio
    protected double fertilita= 0.95 ;

    public S(Popolazione p) {
        //costruttore delle prudenti
        super();
        this.popo = p;
    }

    @Override
    public Persona.tipo getType(){
        return Persona.tipo.S ;
    }

    @Override
    public void run() {
        int tentativi=20;  // la spregiudicata avra' 10 tentativi a disposizione per trovare un amante, altrimenti morira' di vecchiaia
        while(fertilita > 0.0 && contentezza > (popo.a-popo.b)*4 && tentativi > 0){ //dopo 3 o 4 figli avuti con avventurieri muore per la fatica di crescerli da sola
            Persona amante= corteggiamento();
            if(amante!=null) {
                accoppiamento(amante);
                if (amante.getType() == tipo.M) {
                    synchronized (amante) {
                        ((M) amante).limiteMor--;  // toglie un po di virilita' all'amante morigerato
                        amante.notify();
                    }
                }
                else if(amante.getType() == tipo.A) {
                    synchronized (amante) {
                        amante.notify();
                    }
                }
            }
            tentativi--;
        }
        this.popo.spregiudicate.remove(this);
    }

    private Persona corteggiamento(){
        //corteggiamento della spregiudicata
        try {
            return ((new Random().nextBoolean()) ? popo.ristorante.exctract() : popo.osteria.exctract()); //sceglie de prendere un morigerato o un avventuriero, potrebbe tornare null
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void accoppiamento(Persona m){
        // si stabilisce se la donna spregiudicata concepira' un nuovo figlio
        double random = new Random().nextDouble();
        if (random >= fertilita){   //significa che la donna non concepira' bambini da qui in avanti
            fertilita=0;
            return;
        }

        //a questo punto sara' generato un figlio
        Persona figlio = ((m.getType()== tipo.M)) ? ((new Random().nextBoolean()) ? new S(this.popo) : new M(this.popo) ):
                ((new Random().nextBoolean()) ? new S(this.popo) : new A(this.popo)); // scelta del sesso del nascituro

        if (figlio.getType() == tipo.S) popo.spregiudicate.add((S)figlio);  //aggiunge il figlio alla popolazione
        else {
            if (figlio.getType() == tipo.M) popo.morigerati.add((M)figlio);
            else popo.avventurieri.add((A)figlio);
        }

        figlio.start();   // nasce il figlio
        this.contentezza += ((m.getType()== tipo.M) ? popo.a - popo.b/2 : popo.a - popo.b );  // aggiorniamo il valore di contentezza della spregiudicata
        m.contentezza += ((m.getType()== tipo.M) ? popo.a - popo.b/2 : popo.a); // aggiorniamo il valore di contentezza del marito
        fertilita -= 0.25; // aggiorniamo la probabilita' che la spregiudicata abbia un altro figlio(stare attenti al valore)
                           //la donna spregiudicata non perde appeal verso il suo partner ed in media fara' piu' figli
    }
}

