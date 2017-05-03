/**
 * Created by nicolo on 29/04/17.
 */
public class Test {
    public static void main(String[] args) throws InvalidPopulationException {
        Popolazione p1 = new Popolazione(15, 20 ,3,500, 600, 100, 300);
        M m1 = new M();
        M m2 = new M();
        System.out.println(m1.getId());
        System.out.println(m2.getId());
        Popolazione.Stato s1 = p1.calcolaStato();
        s1.stampaStato();
    }
}
