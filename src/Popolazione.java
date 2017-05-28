import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nicolo on 28/04/17.
 */
public class Popolazione {
    //un insieme di individui
    public HashSet<Set<? extends Persona>> population = new HashSet<>();
    //insiemi dei vari tipi
    public Set<M> morigerati = Collections.newSetFromMap(new ConcurrentHashMap<M, Boolean>());
    public Set<A> avventurieri =Collections.newSetFromMap(new ConcurrentHashMap<A, Boolean>());;
    public Set<P> prudenti = Collections.newSetFromMap(new ConcurrentHashMap<P, Boolean>());;
    public Set<S> spregiudicate = Collections.newSetFromMap(new ConcurrentHashMap<S, Boolean>());;

    private volatile boolean terminato;

    //attributi che rappresentano costi e benefici evolutivi che incontriamo nella battaglia dei sessi
    protected int a;
    protected int b;
    protected int c;

    //code delle richieste di accoppiamento degli uomini
    public SynchroCoda<Persona> ballo = new SynchroCoda<>(); //coda sincronizzata per gli accoppiamenti

    public Popolazione(int a, int b, int c, int m, int av, int p, int s) throws InvalidPopulationException {
        //costruttore della popolazione

        if (m<0 || av<0 || p<0 || s<0) throw new InvalidPopulationException();

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

        //insiemi temporanei per fare lo start della popolazione iniziale
        HashSet<M> morTemp=new HashSet<>();
        morTemp.addAll(morigerati);
        HashSet<A> avvTemp=new HashSet<>();
        avvTemp.addAll(avventurieri);
        HashSet<P> pruTemp=new HashSet<>();
        pruTemp.addAll(prudenti);
        HashSet<S> sprTemp=new HashSet<>();
        sprTemp.addAll(spregiudicate);

        Thread threadMor = new Thread(()->{for (M mor : morTemp){mor.start();}});
        Thread threadAvv = new Thread(()->{for (A avv : avvTemp){ avv.start();}});
        Thread threadPru = new Thread(()->{for (P pru : pruTemp){ pru.start();}});
        Thread threadSpr = new Thread(()->{for (S spr : sprTemp){spr.start();}});
        threadMor.start();
        threadAvv.start();
        threadPru.start();
        threadSpr.start();

        while (!calcolaStato().isStabile() && !terminato) {//potrebbe andare in loop
            //calcolaStato().stampaStato();
            System.out.println("morigerati: "+ morigerati.size()+ "  avventurieri: "+avventurieri.size()+"  prudenti: "+prudenti.size()+" spregiudicate: "+ spregiudicate.size() );
            //calcolaStato().stampaStato2();
            //perde tempo per 2 secondi
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 500);

        }
        // TODO: 11/05/17 controllare la sincronizzazione di start()
        //ha trovato uno stato stabile'
        calcolaStato().stampaStato();
        stop(); //da togliere
    }

    //metodo che blocca la simulazione
    public void stop(){
        ballo.chiudi();  //chiude il ballo


        for (P pru : prudenti) {
            pru.interrupt();
        }
        for (S spr : spregiudicate) {
            spr.interrupt();
        }
        for (M mor : morigerati){
            mor.interrupt();
        }
        for (A avv : avventurieri) {
            avv.interrupt();
        }
        this.terminato= true;
    }

    private Stato calcolaStato() {
        return new Stato();
    }

    public class Stato {
        //classe annidata che rappresenta lo stato della popolazione
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


        public void stampaStato2() {
            //stampa percentuali divise per singoli sessi
            System.out.println("Ecco le percentuali dei tipi nella popolazione:");
            System.out.println("Uomini= Morigerati: " + ((perMor/(perMor+perAvv))*100) + "%, " + "Avventurieri: " + ((perAvv/(perAvv+perMor))*100) + "% " + "    Donne= Prudenti: " + ((perPru/(perPru+perSpr))*100) + "%, " + "Spregiudicate: " + ((perSpr/(perSpr+perPru))*100) +"%");
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
