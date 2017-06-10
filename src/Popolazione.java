import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * E’ la classe principale del simulatore e rappresenta l’insieme degli individui,
 * contiene anche tutte le funzioni per calcolare lo stato della popolazione o i guadagni genetici.
 */
public class Popolazione {
    //un insieme di individui
    public HashSet<Set<? extends Persona>> population = new HashSet<>();
    //insiemi dei vari tipi
    public Set<M> morigerati = Collections.newSetFromMap(new ConcurrentHashMap<M, Boolean>());
    public Set<A> avventurieri =Collections.newSetFromMap(new ConcurrentHashMap<A, Boolean>());
    public Set<P> prudenti = Collections.newSetFromMap(new ConcurrentHashMap<P, Boolean>());
    public Set<S> spregiudicate = Collections.newSetFromMap(new ConcurrentHashMap<S, Boolean>());

    //attributi che rappresentano costi e benefici evolutivi che incontriamo nella battaglia dei sessi
    protected int a;
    protected int b;
    protected int c;

    //code delle richieste di accoppiamento degli uomini
    public SynchroSet<Uomo> ballo = new SynchroSet<>(); //coda sincronizzata per gli accoppiamenti

    private volatile boolean terminato; //segnala la fine di una simulazione

    /**
     * Costruttore di Popolazione, crea la popolazione e inizializza lo stato iniziale
     * a seconda dei parametri forniti in input
     * @param a premio per il successo nella generazione di figli
     * @param b costo del crescere figli
     * @param c costo del corteggiamento
     * @param m numero di morigerati iniziale
     * @param av numero di avventurieri iniziale
     * @param p numero di prudenti iniziale
     * @param s numero di spregiudicate iniziale
     * @throws InvalidPopulationException se un tipo è inizializzato minore di 0
     */
    public Popolazione(int a, int b, int c, int m, int av, int p, int s) throws InvalidPopulationException  {
        //costruttore della popolazione

        if (m<0 || av<0 || p<0 || s<0) throw new InvalidPopulationException();

        this.a=a;
        this.b=b;
        this.c=c;
        //crea l'insieme dei morigerati
        for (int i=0; i < m; i++) {
            M mor = new M(this);
            mor.setName("M"+M.count.incrementAndGet());
            morigerati.add(mor);
        }

        //crea l'insieme degli avventurieri
        for (int i=0; i < av; i++) {
            A avv = new A(this);
            avv.setName("A"+A.count.incrementAndGet());
            avventurieri.add(avv);
        }

        //crea l'insieme delle prudenti
        for (int i=0; i < p; i++) {
            P pru = new P(this);
            pru.setName("P"+P.count.incrementAndGet());
            prudenti.add(pru);
        }

        //crea l'insieme delle spregiudicate
        for (int i=0; i < s; i++) {
            S spr = new S(this);
            spr.setName("S"+S.count.incrementAndGet());
            spregiudicate.add(spr);
        }

        { //crea l'insieme della popolazione
            population.add(morigerati);
            population.add(avventurieri);
            population.add(prudenti);
            population.add(spregiudicate);
        }
    }

    /**
     *  Metodo che da vita al mondo, invocando il metodo run dei vari componenti della popolazione
     */
    public void start(){

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
            //calcolaStato().stampaStato2();
            System.out.println("morigerati: "+ morigerati.size()+ "  avventurieri: "+avventurieri.size()+"  prudenti: "+prudenti.size()+" spregiudicate: "+ spregiudicate.size() );
            //perde tempo per 2 secondi
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 300);
        }
        if(!terminato) {
            //ha trovato uno stato stabile'
            calcolaStato().stampaStato();
        }
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
        prudenti=Collections.newSetFromMap(new ConcurrentHashMap<P, Boolean>()); // le prudenti vanno svuotate manualmente
        this.terminato= true;
    }

    /**
     * permette di vedere se il simulatore è in funzione
     * @return true se il simulatore è in funzione, false se non lo è
     */
    public boolean isRunning() {
        return !terminato;
    }

    /**
     *  Calcola lo stato di una popolazione in un determinato instantee lo ritorna
     * @return Stato
     */
    private Stato calcolaStato() {
        return new Stato();
    }


    /**
     * classe annidata che rappresenta lo stato della popolazione, Chiamiamo stato di una popolazione
     * la percentuale del numero di individui di ciascun tipo rispetto alla popolazione totale
     */
    public class Stato {
        //percentuale del numero di individui di ciascun tipo rispetto alla popolazione totale
        double perMor;
        double perAvv;
        double perPru;
        double perSpr;

        /**
         * Costruttore che calcola lo stato della popolazione
         */
        public Stato() {
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


        /**
         * @return Ritorna true o false a seconda che lo stato della popolazione sia stabile o meno
         */
        public boolean isStabile(){
            if ( Math.abs(guadagnoM()-guadagnoA()) <= 0.01 && Math.abs(guadagnoP()-guadagnoS())<= 0.01 ){return true;} //errore dell 1%
            return false;
        }

        /**Stampa le percentuali della popolazione*/
        public void stampaStato() {
            System.out.println("Ecco le percentuali dei tipi nella popolazione:");
            System.out.println("Morigerati: " + (perMor*100) + "%, " + "Avventurieri: " + (perAvv*100) + "%, " + "Prudenti: " + (perPru*100) + "%, " + "Spregiudicate: " + (perSpr*100) +"%");
        }

        /** Stampa percentuali divise per singoli sessi*/
        public void stampaStato2() {
            System.out.println("Ecco le percentuali dei tipi nella popolazione:");
            System.out.println("Uomini= Morigerati: " + ((perMor/(perMor+perAvv))*100) + "%, " + "Avventurieri: " + ((perAvv/(perAvv+perMor))*100) + "% " + "    Donne= Prudenti: " + ((perPru/(perPru+perSpr))*100) + "%, " + "Spregiudicate: " + ((perSpr/(perSpr+perPru))*100) +"%");
        }

        /**Funzione che calcola il guadagno medio degli uomini morigerati in questo istante*/
        private double guadagnoM(){ return  (double)(a-b/2-c)*perPru + (float)(a - b/2)*perSpr;}

        /**funzione che calcola il guadagno medio degli uomini avventurieri in questo istante*/
        private double guadagnoA(){
            return (double)a*perSpr ;
        }

        /**funzione che calcola il guadagno medio delle donne prudenti in questo istante*/
        private double guadagnoP(){
            return (double)(a-b/2-c)*perMor;
        }

        /**funzione che calcola il guadagno medio delle donne spregiudicate in questo istante*/
        private double guadagnoS(){
            return (double)(a-b/2)*perMor + (float)(a-b)*perAvv;
        }
    }

}
