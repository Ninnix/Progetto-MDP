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
        this.nascita= System.currentTimeMillis(); //imposto la data di nascita
    }

    @Override
    public tipo getType(){
        return tipo.P ;
    }

    @Override
    public void run() {
        Persona marito=corteggiamento();
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
        ((M) marito).limiteMor.set(0); //muore il marito ...
        ((M) marito).limiteMor.notify();
        // ... e muore lei
}

    public Persona corteggiamento(){
        //corteggiamento della prudente
        Persona spasimante = popo.mercato.poll();
        if(spasimante==null){return null;}
        if(spasimante.getType()== tipo.A ){
            Persona marito= corteggiamento(); // rischio buffer overflow nel caso di coda con moltissimi avventurieri
            if(spasimante.isAlive()){// un avventuriero potrebbe essere nella coda ma morto
                popo.mercato.add(spasimante);// lo rimette nella coda dando la possibilita' ad un altra donna di accoppiarsi con lui
            }
            return marito; //torno null o un morigerato vivo
        }
        if(!spasimante.isAlive()){ //il morigerato e' morto
            return corteggiamento(); //si scarta e si prosegue nella ricerca
        }
        //e' un morigerato vivo
        // un corteggiamento tra un uomo morigerato e una donna prudente causa un costo in termini genetici
        this.contentezza -= popo.c;
        spasimante.contentezza -= popo.c;
        return spasimante;  //spasimante sara' null se la coda e' vuota o non ci sono morigerati

    }


    public void accoppiamento(Persona m){
        // si stabilisce se la donna prudente concepira' un nuovo figlio
        double random = new Random().nextDouble();
        if (random >= fertilita){   //significa che la donna non concepira' bambini da qui in avanti
            fertilita=0;
            return;
        }

        //a questo punto sara' generato un figlio
        Persona figlio = ((new Random().nextBoolean()) ? new P(this.popo) : new M(this.popo)); // scelta del sesso del nascituro
        figlio.run();   // nasce il figlio
        this.contentezza += (popo.a - popo.b/2 - popo.c);  // aggiorniamo il valore di contentezza della prudente
        m.contentezza += (popo.a - popo.b/2 - popo.c);  // aggiorniamo il valore di contentezza del morigerato
        fertilita -= 0.2; // aggiorniamo la probabilita' che la prudente abbia un altro figlio
    }
}
