package za.co.lynx;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiftSystemTest {

    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setIn(standardIn);
    }

    void mockIn(String mockInput) {
        System.setIn(new ByteArrayInputStream(mockInput.getBytes()));
    }

    /**
     * @see LiftSystem#getFloorNumber()
     */
    @Nested
    class getFloorNumber {


        @Test
        void getsNumberOnly() {
            mockIn("\n \nFirst\n3\nSecond\n");

            LiftSystem.lowestFloor = 1;
            LiftSystem.highestFloor = 10;

            int expected = 3;
            int actual = LiftSystem.getFloorNumber();

            assertEquals(expected, actual);
        }

        @Test
        void getsNumberInRange() {
            mockIn("\n \nFirst\n11\nSecond\n4\nThird\n");

            LiftSystem.lowestFloor = 1;
            LiftSystem.highestFloor = 10;

            int expected = 4;
            int actual = LiftSystem.getFloorNumber();

            assertEquals(expected, actual);
        }
    }

    /**
     * @see LiftSystem#getInput()
     */
    @Test
    void getInput() {
        mockIn("\n \nFirst\nSecond\n");

        String expected = "First";
        String actual = LiftSystem.getInput();

        assertEquals(expected, actual);
    }
}