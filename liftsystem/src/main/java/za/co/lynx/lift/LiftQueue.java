package za.co.lynx.lift;

/**
 * Represents a FIFO* style Queue system for a Lift that
 * priorities moving the Lift in one direction
 * for as long as possible.
 * <br><br>
 * <i>* It is not exactly first in first out, as is supports
 * insertion into the middle of the Queue, provided the insertion
 * would keep the Lift traversing in one direction.</i>
 */
public class LiftQueue {

    Stop up;
    Stop down;
    Stop pivot;

    static class Stop {
        int floor;
        Stop next;

        Stop(int floor) {
            this.floor = floor;
            next = null;
        }
    }

    public LiftQueue() {
        up = null;
        down = null;
    }

    /**
     * @return true if the Queue has another Stop.
     */
    public boolean hasNext() {
        return (up != null);
    }

    /**
     * Pops the first Stop out the Queue and
     * retrieves the floor number associated with it.
     *
     * @return the first Stop in the Queue's floor.
     */
    public int popNextStop() {

        if (up == null) throw new RuntimeException("[BAD] Popping empty Queue.");

        int floor = up.floor;
        up = up.next;

        /*
            Garbage collection will remove the previous head
            as there is  no longer any reference to it.
         */

        return floor;
    }

    /**
     * To join ahead of all positions in the Lift Queue.
     * The Stop must be on a floor that would be passed along the way.
     *
     * @param currentFloor     that the Lift is currently at.
     * @param destinationFloor of the Stop to be added.
     * @return true if the Stop should join at the head of the queue.
     */
    private boolean isJump(int currentFloor, int destinationFloor) {
        return (destinationFloor > currentFloor)
                && (destinationFloor < up.floor) ||
                ((destinationFloor < currentFloor)
                        && (destinationFloor > up.floor));
    }

    /**
     * If the destination is not inside the range
     * between the first floor and last floor in the Queue,
     * it is added to the end.
     *
     * @param destinationFloor of the Stop to be added.
     * @return true if the Stop should join at the tail of the queue.
     */
    private boolean isEnqueue(int destinationFloor) {
        return ((destinationFloor < up.floor)
                && (destinationFloor < down.floor)) ||
                ((destinationFloor > up.floor) && (destinationFloor > down.floor));
    }

    /**
     * @param destinationFloor of the Stop to be added.
     * @return true if the Stop would cause the Lift to pivot (switch direction).
     */
    private boolean isPivot(int destinationFloor) {

        if (up.next == null) return false;

        boolean increasing = (up.floor < up.next.floor);

        return (increasing && (destinationFloor < down.floor)) ||
                (!increasing && (destinationFloor > down.floor));
    }

    /**
     * @param currentFloor     that the Lift is currently at.
     * @param destinationFloor of the Stop to be added.
     * @return the style of insertion this Stop should be added with.
     */
    private InsertionStyle getInsertionStyle(int currentFloor, int destinationFloor) {
        if (up == null) return InsertionStyle.CREATE;

        if (isJump(currentFloor, destinationFloor)) return InsertionStyle.JUMP;

        if (isEnqueue(destinationFloor)) {
            if (isPivot(destinationFloor)) return InsertionStyle.PIVOT;

            return InsertionStyle.ENQUEUE;
        }

        /*
            If none of the above conditions are met,
            the destinationFloor is to be inserted
            somewhere inside the Queue.
         */
        return InsertionStyle.INSERT;
    }

    /**
     * The five distinct methods of insertion into a LiftQueue.
     * <ul>
     *      <li>Create - Initialises the Queue.</li>
     *      <li>Enqueue - Traditional FIFO Enqueue.</li>
     *      <li>Insert - Priority based insertion into the Queue.</li>
     *      <li>Jump - Insertion to the head of the Queue.</li>
     *      <li>Pivot - An Enqueue that would change the direction the Queue is moving.</li>
     * </ul>
     */
    enum InsertionStyle {
        CREATE, ENQUEUE, INSERT, JUMP, PIVOT
    }

    /**
     * Adds a Stop to the Queue.
     *
     * @param currentFloor     that the Lift is currently at.
     * @param destinationFloor of the Stop to be added.
     */
    public void addStop(int currentFloor, int destinationFloor) {

        switch (getInsertionStyle(currentFloor, destinationFloor)) {
            case CREATE -> {
                Stop temp = new Stop(destinationFloor);
                up = temp;
                down = temp;
                pivot = temp;
            }
            case ENQUEUE -> {
                Stop temp = new Stop(destinationFloor);
                down.next = temp;
                if (pivot == down) pivot = temp;
                down = temp;
            }
            case INSERT -> {

                boolean increasing = (up.floor < up.next.floor);

                Stop temp;
                // Determine which section of the Queue to insert in.
                if ((increasing && (destinationFloor < down.floor)) ||
                        (!increasing && (destinationFloor > down.floor))) {
                    temp = up;
                } else {
                    temp = pivot;
                    increasing = !increasing;   // The pivot is going in the opposite direction
                }

                while (temp.next != null) {
                    if ((increasing && temp.floor < destinationFloor
                            && destinationFloor < temp.next.floor) ||
                            (!increasing && temp.floor > destinationFloor && destinationFloor > temp.next.floor)) {
                        {
                            Stop insert = new Stop(destinationFloor);
                            insert.next = temp.next;
                            temp.next = insert;
                        }
                    }
                    temp = temp.next;   // Traverse
                }
            }
            case JUMP -> {
                Stop temp = new Stop(destinationFloor);
                temp.next = up;
                up = temp;
            }
            case PIVOT -> {
                Stop temp = new Stop(destinationFloor);
                down.next = temp;
                down = temp;
            }
        }
    }
}
