
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * donne spregiudicate, si concedono ad un uomo anche al primo incontro,
 * se cosı̀ credono.
 */
public class S extends Donna{

    static AtomicInteger count=new AtomicInteger(0);

    public Popolazione popo;

    /**
     * Costruttore delle spregiudicate
     * @param p Popolazione
     */
    public S(Popolazione p) {
        super();
        this.popo = p;
    }

    /**
     * @return tipo.s rappresenta la spregiudicata
     */
    @Override
    public Persona.tipo getType(){
        return Persona.tipo.S ;
    }

    /**
     * Azioni svolte dalla spregiudicata durante la sua vita libertina
     */
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

    /**
     *  Corteggiamento della spregiudicata
     * @return un uomo
     * @throws InterruptedException se viene interrotta
     */
    private Uomo corteggiamento() throws InterruptedException{
        return popo.ballo.exctract();
    }

    /**
     * Accopiamento della spregiudicata
     * @param m il marito momentaneo
     */
    private void accoppiamento(Uomo m){
        // si stabilisce se la donna spregiudicata concepira' un nuovo figlio
        double random = new Random().nextDouble();
        if (random >= fertilita){   //significa che la donna non concepira' bambini da qui in avanti
            fertilita=0;
            return;
        }
        else {
            if (m.getType() == tipo.M) {
                Persona figlio = ((new Random().nextBoolean()) ? new M(this.popo) : new S(this.popo));
                if (figlio.getType() == tipo.S) {
                    figlio.setName("S"+S.count.incrementAndGet());
                    popo.spregiudicate.add((S) figlio); //aggiunge la figlia spregiudicata alla popolazione
                }
                else {
                    figlio.setName("M"+M.count.incrementAndGet());
                    popo.morigerati.add((M) figlio);    //aggiunge la figlio morigerato alla popolazione
                }
                //System.out.println(this.getName()+" si accoppia con "+m.getName()+", e nasce "+figlio.getName());  //Stampa identificativo del figlio
                figlio.start();
                m.contentezza += popo.a - popo.b / 2;
                this.contentezza += popo.a - popo.b / 2;
            } else {
                Persona figlio = ((new Random().nextBoolean()) ? new A(this.popo) : new S(this.popo));
                if (figlio.getType() == tipo.S) {
                    figlio.setName("S"+S.count.incrementAndGet());
                    popo.spregiudicate.add((S) figlio);  //aggiunge la figlia spregiudicata alla popolazione
                }
                else {
                    figlio.setName("A"+A.count.incrementAndGet());
                    popo.avventurieri.add((A) figlio);  //aggiunge la figlio avventuriero alla popolazione
                }
                //System.out.println(this.getName()+" si accoppia con "+m.getName()+", e nasce "+figlio.getName());  //Stampa identificativo del figlio
                figlio.start();
                m.contentezza += popo.a;
                this.contentezza += popo.a - popo.b;
            }
            this.fertilita -= 0.21;
        }
    }
}


