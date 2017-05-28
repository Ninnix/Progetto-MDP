
/**
 * Created by nicolo on 29/04/17.
 */
public class Test {
    public static void main(String[] args) throws InvalidPopulationException {
        try {
            Popolazione p1 = new Popolazione(15, 20 ,3,50, 50, 50, 50);
            p1.start();
            Thread.currentThread().sleep(200);
            p1.stop();
            //p1.stop();
        }catch (InterruptedException e){}
    }
}
