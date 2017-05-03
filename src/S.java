/**
 * Created by nicolo on 28/04/17.
 */
public class S extends Persona {
    /**
     * tipo S, donne spregiudicate, si concedono ad un uomo anche al primo incontro,
     * se cosı̀ credono.
     */
    public Popolazione popo;

    public S(Popolazione p) {
        //costruttore delle spregiudicate
        super();
        this.popo = p;
    }

    @Override
    public void run() {
        super.run();
    }
}
