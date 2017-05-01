import java.util.HashSet;

/**
 * Created by nicolo on 28/04/17.
 */
public class MyPopolazione {

    //un insieme di individui
    public HashSet<HashSet<Persona>> population = new HashSet<HashSet<Persona>>();
    //insieme dei vari tipi
    public HashSet<Persona> morigerati = new HashSet<Persona>();
    public HashSet<Persona> avventurieri = new HashSet<Persona>();
    public HashSet<Persona> prudenti = new HashSet<Persona>();
    public HashSet<Persona> spregiudicate = new HashSet<Persona>();

    public class Stato {
        //percentuale del numero di individui di ciascun tipo rispetto alla popolazione totale
        float perMor;
        float perAvv;
        float perPru;
        float perSpr;

        public Stato() {
            //costruttore che calcola gli stati
            int numMor = morigerati.size();
            int numAvv = avventurieri.size();
            int numPru = prudenti.size();
            int numSpr = spregiudicate.size();
            int totPop = numMor+numAvv+numPru+numSpr;

            perMor = numMor*100/totPop;
            perAvv = numAvv*100/totPop;
            perPru = numPru*100/totPop;
            perSpr = numSpr*100/totPop;
        }

        public void stampaStato() {
            System.out.println("Ecco le percentuali dei tipi nella popolazione:");
            System.out.println("Morigerati: " + perMor + ", " + "Avventurieri: " + perAvv + ", " + "Prudenti: " + perPru + ", " + "Spregiudicate: " + perSpr);
        }
    }

    public MyPopolazione(int m, int a, int p, int s) throws InvalidPopulationException {
        //costruttore della popolazione

        if (m==0 || a==0 || p==0 || s==0) throw new InvalidPopulationException();

        //crea l'insieme dei morigerati
        for (int i=0; i < m; i++) {
            M mor = new M();
            morigerati.add(mor);
        }

        //crea l'insieme degli avventurieri
        for (int i=0; i < a; i++) {
            A avv = new A();
            avventurieri.add(avv);
        }

        //crea l'insieme delle prudenti
        for (int i=0; i < p; i++) {
            P pru = new P();
            prudenti.add(pru);
        }

        //crea l'insieme delle spregiudicate
        for (int i=0; i < s; i++) {
            S spr = new S();
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
