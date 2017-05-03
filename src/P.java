/**
 * Created by nicolo on 28/04/17.
 */
public class P extends Persona {
    /**
     * donne prudenti, accettano un compagno con cui fare un figlio solo
     * dopo un congruo periodo di corteggiamento;
     */
    public Popolazione popo;

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
    }

    private Persona corteggiamento(){
        //corteggiamento della prudente
        Persona marito = popo.mercato.poll(); //marito sara' null se la coda e' vuota
        if(marito.getType()== tipo.A ){
            corteggiamento(); // rischio buffer overflow nel caso di coda con moltissimi avventurieri
            popo.mercato.add(marito);// lo rimette nella coda dando la possibilita' ad un altra donna di accoppiarsi con lui
        }
        return marito;
    }


    private void accoppiamento(Persona m){
        //metodo per l'accoppiamento
    }
}
