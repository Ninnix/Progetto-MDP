/**
 * Created by nicolo on 28/04/17.
 */
public abstract class Persona extends Thread {
    //contratto delle persone le persone di dividono in vari tipi

    //attributi
    protected enum tipo{M,A,P,S} // tipologie di persone
    protected volatile int contentezza=0;  // indica la felicita' di un individuo(in termini di guadagno genetico)
                                           // volatile serve per gestire il possibile aggiornamento concorrente della variabile

    public abstract tipo getType(); //restituisce il tipo della persona
}
