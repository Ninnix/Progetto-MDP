
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * donne spregiudicate, si concedono ad un uomo anche al primo incontro,
 * se cosı̀ credono.
 */

public class S extends Donna{

    static AtomicInteger count=new AtomicInteger(0);

    public Popolazione popo;

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
        try {
            while (fertilita  > 0.0 && contentezza > ((popo.a - popo.b) * 3 ) && !isInterrupted() ) { //dopo 3 o 4 figli avuti con avventurieri muore per la fatica di crescerli da sola
                Uomo amante = corteggiamento();
                accoppiamento(amante);
                if (amante.getType() == tipo.M) {
                    amante.virilita-=0.15;
                } else if (amante.getType() == tipo.A) {
                    amante.virilita-=0.11;
                    ((A)amante).ultimaDonna= tipo.S;
                }
                amante.sveglia();
            }
        }catch (InterruptedException e){
            //System.out.println("problema spregiudicata, interruzione coda");
        }
        this.popo.spregiudicate.remove(this);
    }

    private Uomo corteggiamento() throws InterruptedException{
        //corteggiamento della spregiudicata
        return popo.ballo.exctract();
    }


    private void accoppiamento(Uomo m){
        // si stabilisce se la donna spregiudicata concepira' un nuovo figlio
        double random = new Random().nextDouble();
        if (random >= fertilita){   //significa che la donna non concepira' bambini da qui in avanti
            fertilita=0;
            //if(m.getType()==tipo.M){((M)m).virilita=0;} //muore il marito morigerato
            return;
        }

        //a questo punto sara' generato un figlio
        /*Persona figlio = ((m.getType()== tipo.M)) ? ((new Random().nextBoolean()) ? new S(this.popo) : new M(this.popo) ):
                ((new Random().nextBoolean()) ? new S(this.popo) : new A(this.popo)); // scelta del sesso del nascituro

        if (figlio.getType() == tipo.S) popo.spregiudicate.add((S)figlio);  //aggiunge il figlio alla popolazione
        else {
            if (figlio.getType() == tipo.M) popo.morigerati.add((M)figlio);
            else popo.avventurieri.add((A)figlio);
        }

        figlio.start();   // nasce il figlio
        this.contentezza += ((m.getType()== tipo.M) ? popo.a - popo.b/2 : popo.a - popo.b );  // aggiorniamo il valore di contentezza della spregiudicata
        m.contentezza += ((m.getType()== tipo.M) ? popo.a - popo.b/2 : popo.a); // aggiorniamo il valore di contentezza del marito
        fertilita -= 0.20; // aggiorniamo la probabilita' che la spregiudicata abbia un altro figlio(stare attenti al valore)
                           //la donna spregiudicata non perde appeal verso il suo partner ed in media fara' piu' figli*/
        else {
            if (m.getType() == tipo.M) {
                Persona figlio = ((new Random().nextDouble()< 0.50) ? new M(this.popo) : new S(this.popo));
                if (figlio.getType() == tipo.S) {
                    figlio.setName("S"+S.count.incrementAndGet());
                    popo.spregiudicate.add((S) figlio);
                } //aggiunge il figlio alla popolazione
                else {
                    figlio.setName("M"+M.count.incrementAndGet());
                    popo.morigerati.add((M) figlio);
                }
                //System.out.println(this.getName()+" si accoppia con "+m.getName()+", e nasce "+figlio.getName());  //da rimuovere
                figlio.start();
                m.contentezza += popo.a - popo.b / 2;
                this.contentezza += popo.a - popo.b / 2;
            } else {
                Persona figlio = ((new Random().nextDouble()< 0.50) ? new A(this.popo) : new S(this.popo));
                if (figlio.getType() == tipo.S) {
                    figlio.setName("S"+S.count.incrementAndGet());
                    popo.spregiudicate.add((S) figlio);  //aggiunge il figlio alla popolazione
                }
                else {
                    figlio.setName("A"+A.count.incrementAndGet());
                    popo.avventurieri.add((A) figlio);
                }
                //System.out.println(this.getName()+" si accoppia con "+m.getName()+", e nasce "+figlio.getName());  //da rimuovere
                figlio.start();
                m.contentezza += popo.a;
                this.contentezza += popo.a - popo.b;
            }
            this.fertilita -= 0.21;
        }
    }
}


