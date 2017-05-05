/**
 * Created by nicolo on 28/04/17.
 */
public class A extends Persona {
    /**
     * gli avventurieri, uomini senza scrupoli: se una donna non gli si concede
     * immediatamente, tentano la sorte altrove senza perdere tempo corteggiarla;
     * se gli si concede, partono comunque subito dopo per una nuova avventura,
     * lasciando a lei l’incombenza di crescere la prole;
     */

    public Popolazione popo;
    private double virilita = 0.4; //indice che indica la probabilita' di inserirsi nella coda mercato
    private int tentativi = 10; //tentativi di inserirsi nella coda

    public A(Popolazione p) {
        //costruttore degli avventurieri
        super();
        this.popo = p;
        this.nascita= System.currentTimeMillis(); //imposto la data di nascita
    }

    @Override
    public tipo getType(){
        return tipo.A;
    }

    @Override
    public void run() {
        while(!morte()){
            // esegui operazione del thread
        }
    }

    private void corteggiamento(){
        //corteggiamento dell' avventuriero
        popo.mercato.add(this); //l'avventuriero si aggiunge alla coda per accoppiarsi
    }

    //gestire accoppiamento avventuriero, dare limite ai figli
}
