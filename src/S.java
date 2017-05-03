/**
 * Created by nicolo on 03/05/17.
 */
public class S extends Persona{
    /**
     * donne spregiudicate, si concedono ad un uomo anche al primo incontro,
     * se cosı̀ credono.
     */

    public Popolazione popo;

    public S(Popolazione p) {
        //costruttore delle prudenti
        super();
        this.popo = p;
        this.nascita= System.currentTimeMillis(); //imposto la data di nascita
    }

    @Override
    public Persona.tipo getType(){
        return Persona.tipo.S ;
    }

    @Override
    public void run() {
        while(!morte()){
            // esegui operazione del thread
        }
    }

    private Persona corteggiamento(){
        //corteggiamento della spregiudicata
        Persona marito = popo.mercato.poll(); //marito sara' null se la coda e' vuota
        return marito;
    }


    private void accoppiamento(Persona m){
        //metodo per l'accoppiamento
    }
}
