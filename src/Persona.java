/**
 * Created by nicolo on 28/04/17.
 */
public abstract class Persona extends Thread {
    //contratto delle persone le persone di dividono in vari tipi

    //attributi
    // tipologie di persone
    protected enum tipo{M,A,P,S}

    /* indica la felicita' di un individuo(in termini di guadagno genetico)
     volatile serve per gestire il possibile aggiornamento concorrente della variabile */
    protected volatile int contentezza=0;

    /* "data" di nascita del nostro individuo( in realta' e' il tempo di processore quando la persona e' creata)
    di default viene inizializzata a 0 per evitare Nullpointexpction in morte()*/
    protected long nascita=0;

    public abstract tipo getType(); //restituisce il tipo della persona


    /*un thread muore se vive per piu' di 1 minuto(tempo che puo' essere modificato) oppure se raggiunge un valore
    di contentezza molto negativo, -20 ad esempio, il metodo e' inserito in persona perche' e' equivalente per tutte
    le sottoclassi*/
    protected boolean morte(){
        if(this.nascita < System.currentTimeMillis()- 5000 || this.contentezza<= -20){
            return true;  // e' tempo di morire
        }
        return false;  // non e' arrivata la sua ora
    }
}
