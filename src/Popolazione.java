import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by nicolo on 28/04/17.
 */
public class Popolazione {
    //un insieme di individui
    public HashSet<HashSet<Persona>> population = new HashSet<HashSet<Persona>>();
    //insiemi dei vari tipi
    public HashSet<Persona> morigerati = new HashSet<Persona>();
    public HashSet<Persona> avventurieri = new HashSet<Persona>();
    public HashSet<Persona> prudenti = new HashSet<Persona>();
    public HashSet<Persona> spregiudicate = new HashSet<Persona>();

    //attributi che rappresentano costi e benefici evolutivi che incontriamo nella battaglia dei sessi
    protected int a;
    protected int b;
    protected int c;

    //coda delle richieste di accoppiamento degli uomini
    public Queue<Persona> mercato = new SynchronousQueue<Persona>(); //coda sincronizzata vedi http://docs.oracle.com/javase/tutorial/collections/implementations/queue.html


    public Popolazione(int a, int b, int c, int m, int av, int p, int s) throws InvalidPopulationException {
        //costruttore della popolazione

        if (m<=0 || av<=0 || p<=0 || s<=0) throw new InvalidPopulationException();

        this.a=a;
        this.b=b;
        this.c=c;
        //crea l'insieme dei morigerati
        for (int i=0; i < m; i++) {
            M mor = new M(this);
            morigerati.add(mor);
        }

        //crea l'insieme degli avventurieri
        for (int i=0; i < av; i++) {
            A avv = new A(this);
            avventurieri.add(avv);
        }

        //crea l'insieme delle prudenti
        for (int i=0; i < p; i++) {
            P pru = new P(this);
            prudenti.add(pru);
        }

        //crea l'insieme delle spregiudicate
        for (int i=0; i < s; i++) {
            S spr = new S(this);
            spregiudicate.add(spr);
        }

        { //crea l'insieme della popolazione
            population.add(morigerati);
            population.add(avventurieri);
            population.add(prudenti);
            population.add(spregiudicate);
        }
    }


    public void start(){
        // metodo che da vita al mondo, invocando il metodo run dei vari componenti della popolazione
    }

    private Stato calcolaStato() {
        return new Stato();
    }


    //classe annidata che rappresenta lo stato della popolazione
    public class Stato {
        //percentuale del numero di individui di ciascun tipo rispetto alla popolazione totale
        double perMor;
        double perAvv;
        double perPru;
        double perSpr;

        public Stato() {
            //costruttore che calcola gli stati
            int numMor = morigerati.size();
            int numAvv = avventurieri.size();
            int numPru = prudenti.size();
            int numSpr = spregiudicate.size();
            int totPop = numMor+numAvv+numPru+numSpr;

            perMor = (double) numMor/totPop;
            perAvv = (double) numAvv/totPop;
            perPru = (double) numPru/totPop;
            perSpr = (double) numSpr/totPop;
        }


        public boolean isStabile(){
            // metodo che torna true o false a seconda che lo stato della popolazione sia stabile o meno
            if ( Math.abs(guadagno_p()-guadagno_s()) <= 0.01 && Math.abs(guadagno_m()-guadagno_av())<= 0.01 ){return true;} //errore dell 1%
            return false;
        }


        public void stampaStato() {
            System.out.println("Ecco le percentuali dei tipi nella popolazione:");
            System.out.println("Morigerati: " + (perMor*100) + "%, " + "Avventurieri: " + (perAvv*100) + "%, " + "Prudenti: " + (perPru*100) + "%, " + "Spregiudicate: " + (perSpr*100) +"%");
        }

        //funzione che calcola il guadagno medio degli uomini morigerati in questo istante
        private double guadagno_m(){ return  (double)(a-b/2-c)*perPru + (float)(a - b/2)*perSpr;}

        //funzione che calcola il guadagno medio degli uomini avventurieri in questo istante
        private double guadagno_av(){
            return (double)a*perSpr ;
        }

        //funzione che calcola il guadagno medio delle donne prudenti in questo istante
        private double guadagno_p(){
            return (double)(a-b/2-c)*perMor;
        }

        //funzione che calcola il guadagno medio delle donne spregiudicate in questo istante
        private double guadagno_s(){
            return (double)(a-b/2)*perMor + (float)(a-b)*perAvv;
        }
    }

    // TODO: 04/05/17 costruttore con percentuali
}
