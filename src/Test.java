/**
 * Created by nicolo on 29/04/17.
 */
public class Test {
    public static void main(String[] args) throws InvalidPopulationException {
        Popolazione p1 = new Popolazione(15, 20 ,3,5, 5, 5, 5);
        //p1.start();
        /*Popolazione.Stato s1 = p1.calcolaStato();
        s1.stampaStato();*/

        A a= new A(p1);
        a.run();
        S s= new S(p1);
        s.run();
    }
}
