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
        this.nascita= System.currentTimeMillis(); //imposto la data di nascita
    }

    @Override
    public tipo getType(){
        return tipo.P ;
    }

    @Override
    public void run() {
        while(!morte()){
            // esegui operazione del thread
        }
    }

    private Persona corteggiamento(){
        //corteggiamento della prudente
        Persona marito = popo.mercato.poll(); //marito sara' null se la coda e' vuota
        if(marito.getType()== tipo.A ){
            corteggiamento(); // rischio buffer overflow nel caso di coda con moltissimi avventurieri
            popo.mercato.add(marito);// lo rimette nella coda dando la possibilita' ad un altra donna di accoppiarsi con lui
        }
        if (marito != null){
            // un corteggiamento tra un uomo morigerato e una donna prudente causa un costo in termini genetici
            this.contentezza -= popo.c;
            marito.contentezza -= popo.c;
        }
        return marito;
    }


    private void accoppiamento(Persona m){
        //metodo per l'accoppiamento
    }

    /* TODO: 04/05/17  Crescita figli
    va inserito un tempo per la crescita dei figli, ad un certo punto diventeranno
    indipendenti e non dovranno piu' essere accuditi dai genitori*/
}
