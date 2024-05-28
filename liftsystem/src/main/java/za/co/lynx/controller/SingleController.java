package za.co.lynx.controller;

import za.co.lynx.lift.Lift;

/**
 * Represents a basic Lift Controller that manages a single Lift.
 * In this case, it is simply a wrapper for the Lift classes inner methods.
 */
public class SingleController implements Controller {

    Lift lift;

    public SingleController(Lift lift) {
        this.lift = lift;
    }

    @Override
    public void call(int floorNumber) {
        lift.addStop(floorNumber);
    }

    @Override
    public void travel() {
        lift.travel();
    }
}
