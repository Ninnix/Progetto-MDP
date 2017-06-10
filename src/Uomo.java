
/**
 * Rappresentanzione del sesso maschile
 */
public abstract class Uomo extends Persona {

    protected volatile double virilita = 0.95; //probabilità di inserirsi nel ballo

    /**  metodo che fa risvegliare l'uomo*/
    protected synchronized void sveglia(){notify();}

}
