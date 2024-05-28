package za.co.lynx.lift;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiftQueueTest {

    /**
     * Tests the Queue's basic insertion
     * and removal of Stops, in ascending order.
     */
    @Test
    void linearAscendingTraversal() {
        LiftQueue queue = new LiftQueue();
        queue.addStop(0, 3);
        queue.addStop(0, 4);
        queue.addStop(0, 6);
        queue.addStop(0, 10);

        assertEquals(3, queue.popNextStop());
        assertEquals(4, queue.popNextStop());
        assertEquals(6, queue.popNextStop());
        assertEquals(10, queue.popNextStop());
    }

    /**
     * Tests the Queue's basic insertion
     * and removal of Stops, in descending order.
     */
    @Test
    void linearDescendingTraversal() {
        LiftQueue queue = new LiftQueue();
        queue.addStop(10, 9);
        queue.addStop(10, 6);
        queue.addStop(10, 4);
        queue.addStop(10, 1);

        assertEquals(9, queue.popNextStop());
        assertEquals(6, queue.popNextStop());
        assertEquals(4, queue.popNextStop());
        assertEquals(1, queue.popNextStop());
    }

    /**
     * Tests an insertion into the middle of the Queue
     * when the traversal is ascending.
     */
    @Test
    void midTraversalAscending() {
        LiftQueue queue = new LiftQueue();
        queue.addStop(0, 3);
        queue.addStop(0, 6);
        queue.addStop(0, 10);
        queue.addStop(0, 4);
        queue.addStop(0, 7);

        assertEquals(3, queue.popNextStop());
        assertEquals(4, queue.popNextStop());
        assertEquals(6, queue.popNextStop());
        assertEquals(7, queue.popNextStop());
        assertEquals(10, queue.popNextStop());
    }

    /**
     * Tests an insertion into the middle of the Queue
     * when the traversal is descending.
     */
    @Test
    void midTraversalFallingDescending() {
        LiftQueue queue = new LiftQueue();
        queue.addStop(10, 9);
        queue.addStop(10, 1);
        queue.addStop(10, 6);
        queue.addStop(10, 4);


        assertEquals(9, queue.popNextStop());
        assertEquals(6, queue.popNextStop());
        assertEquals(4, queue.popNextStop());
        assertEquals(1, queue.popNextStop());
    }

    /**
     * Tests the prioritisation of floors along
     * the current axis of traversal when inserting
     * a new stop.
     */
    @Nested
    class priorityTraversal {

        @Test
        void basicAscending() {
            LiftQueue queue = new LiftQueue();
            queue.addStop(4, 5);
            queue.addStop(4, 6);
            queue.addStop(4, 10);
            queue.addStop(4, 1);

            assertEquals(5, queue.popNextStop());
            assertEquals(6, queue.popNextStop());
            assertEquals(10, queue.popNextStop());
            assertEquals(1, queue.popNextStop());
        }

        @Test
        void basicDescending() {
            LiftQueue queue = new LiftQueue();
            queue.addStop(8, 5);
            queue.addStop(8, 6);
            queue.addStop(8, 7);
            queue.addStop(8, 10);

            assertEquals(7, queue.popNextStop());
            assertEquals(6, queue.popNextStop());
            assertEquals(5, queue.popNextStop());
            assertEquals(10, queue.popNextStop());
        }

        /**
         * When prioritising one axis,
         * the Queue that begins after the last floor in the previous
         * axis should move in the reverse order.
         * <br><br>
         * When prioritising one axis of movement,
         * e.g. Rising from 5-10, and inserting a Stop for 1-3,
         * the order of delivery should be [5-10] - ascending, and [3-1] - descending,
         * because the Lift is moving down from floor 10.
         */
        @Nested
        class secondaryQueue {

            @Test
            void ascending() {
                LiftQueue queue = new LiftQueue();
                queue.addStop(4, 5);
                queue.addStop(4, 6);
                queue.addStop(4, 10);
                queue.addStop(4, 1);
                queue.addStop(4, 2);
                queue.addStop(4, 3);

                assertEquals(5, queue.popNextStop());
                assertEquals(6, queue.popNextStop());
                assertEquals(10, queue.popNextStop());
                assertEquals(3, queue.popNextStop());
                assertEquals(2, queue.popNextStop());
                assertEquals(1, queue.popNextStop());
            }

            @Test
            void descending() {
                LiftQueue queue = new LiftQueue();
                queue.addStop(8, 5);
                queue.addStop(8, 6);
                queue.addStop(8, 7);
                queue.addStop(8, 10);
                queue.addStop(8, 9);

                assertEquals(7, queue.popNextStop());
                assertEquals(6, queue.popNextStop());
                assertEquals(5, queue.popNextStop());
                assertEquals(9, queue.popNextStop());
                assertEquals(10, queue.popNextStop());
            }

            /**
             * Tests that the Queue can handle
             * multiple pivot points, in this case,
             * pivots on floors 8 and 1.
             */
            @Test
            void multiplePivotPoints() {
                LiftQueue queue = new LiftQueue();
                queue.addStop(6, 7);
                queue.addStop(6, 8);
                queue.addStop(6, 1);
                queue.addStop(6, 5);
                queue.addStop(6, 9);

                assertEquals(7, queue.popNextStop());
                assertEquals(8, queue.popNextStop());
                assertEquals(5, queue.popNextStop());
                assertEquals(1, queue.popNextStop());
                assertEquals(9, queue.popNextStop());
            }
        }

    }
}