/**
 * Created by nicolo on 28/04/17.
 */
public abstract class Persona extends Thread {
    //contratto delle persone le persone di dividono in vari tipi

    protected enum tipo{M,A,P,S}

    public abstract tipo getType(); //restituisce il tipo della persona

}
