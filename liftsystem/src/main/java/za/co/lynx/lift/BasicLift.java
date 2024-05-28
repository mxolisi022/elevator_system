package za.co.lynx.lift;

/**
 * A basic Lift, without additional real-world properties (such as velocity, braking, etc.),
 * to simulate the basic principles of a Lift; an object that visits floors in its queue.
 */
public class BasicLift implements Lift {

    Direction direction;
    int floor;
    LiftQueue queue;
    State state;

    public BasicLift() {
        floor = 0;
        direction = Direction.RISE;
        queue = new LiftQueue();
        state = State.STOP;
    }

    /**
     * Adds another stop to the Lift's queue.
     *
     * @param Stopfloor The floor number to stop at.
     */
    @Override
    public void addStop(int Stopfloor) {
        if (this.floor == Stopfloor)
            throw new RuntimeException("!: prevent to instruct the elevator to travel to a floor it is already on.");

        queue.addStop(this.floor, Stopfloor);
    }

    /**
     * Traverses the Lift's Queue of Stops.
     */
    @Override
    public void travel() {
        new Thread(() -> {
            while (queue.hasNext()) {
                int nextStop = queue.popNextStop();
                direction = (nextStop > floor) ? Direction.RISE : Direction.FALL;
                int step = (direction.equals(Direction.RISE)) ? 1 : -1;
                state = State.TRAVELS;

                while (state.equals(State.TRAVELS)) {
                    try {
                        Thread.sleep(500);  // Simulate Travel Time.
                        floor += step;
                    } catch (InterruptedException e) {
                        throw new RuntimeException("!: interrupted.");
                    }

                    state = (floor != nextStop) ? State.TRAVELS : State.STOP;
                    switch (state) {
                        case TRAVELS -> System.out.printf("ELEVATOR: Passed %d floor\n", floor);
                        case STOP -> System.out.printf("ELEVATOR: Arrived at %d floor\n", nextStop);
                    }
                }
            }
        }).start();
    }
}
