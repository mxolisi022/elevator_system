package za.co.lynx.lift;

/**
 * TODO Document
 */
public interface Lift {

    enum State {
        TRAVELS, STOP
    }

    enum Direction {
        RISE, FALL
    }

    void addStop(int Stopfloor);

    void travel();
}
