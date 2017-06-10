
/**
 * contratto delle persone, le persone si distinguono per sesso e per tipo
 */
public abstract class Persona extends Thread {

    // tipologie di persone
    protected enum tipo{M,A,P,S}

    // indica la felicita' di un individuo(in termini di guadagno genetico)
    protected volatile int contentezza=0; //volatile serve per gestire il possibile aggiornamento concorrente della variabile

    /**
     * metodo astratto
     * @return restituisce il tipo della persona
     */
    public abstract tipo getType();

}
