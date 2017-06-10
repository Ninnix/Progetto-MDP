/**
 * Created by Samuele on 10/06/2017.
 */
public abstract class Uomo extends Persona {
    protected volatile double virilita = 0.95;

    protected synchronized void sveglia(){notify();} // metodo che fa risvegliare l'uomo
}
