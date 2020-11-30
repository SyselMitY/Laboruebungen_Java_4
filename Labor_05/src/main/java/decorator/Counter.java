package decorator;

public interface Counter {
    /***Liefert den aktuellen Zaehlerstand.**@return aktueller Zaehlerstand.*/
    int read();

    /***Zaehlt weiter.**@return dieser Zaehler.*/
    Counter tick();
}