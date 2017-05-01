/**
 * Created by nicolo on 29/04/17.
 */
public class Test {
    public static void main(String[] args) throws InvalidPopulationException {
        MyPopolazione p1 = new MyPopolazione(500, 600, 100, 300);
        MyPopolazione.Stato s1 = p1.calcolaStato();
        s1.stampaStato();
    }
}
