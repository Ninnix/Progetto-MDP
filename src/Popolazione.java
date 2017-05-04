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

            perMor = (double) numMor*100/totPop;
            perAvv = (double) numAvv*100/totPop;
            perPru = (double) numPru*100/totPop;
            perSpr = (double) numSpr*100/totPop;
        }

        public void stampaStato() {
            System.out.println("Ecco le percentuali dei tipi nella popolazione:");
            System.out.println("Morigerati: " + perMor + "%, " + "Avventurieri: " + perAvv + "%, " + "Prudenti: " + perPru + "%, " + "Spregiudicate: " + perSpr +"%");
        }
    }

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

    public Stato calcolaStato() {
        return new Stato();
    }
}
