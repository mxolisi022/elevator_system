package za.co.lynx;

import za.co.lynx.controller.Controller;
import za.co.lynx.controller.SingleController;
import za.co.lynx.lift.BasicLift;

import java.util.Scanner;

/**
 * Demo of the Lift System.
 */
public class LiftSystem {

    static int lowestFloor = 1;
    static int highestFloor = 10;


    public static void main(String[] args) {
        Controller controlUnit = new SingleController(new BasicLift());

        for (int i = 0; i < 5; i++) {
            controlUnit.call(getFloorNumber());
        }

        controlUnit.travel();

        for (int i = 0; i < 5; i++) {
            controlUnit.call(getFloorNumber());
        }
    }

    /**
     * @return floor number between the lowestFloor and highestFloor.
     */
    protected static int getFloorNumber() {
        int floor = 0;
        boolean valid = false;

        do {
            try {
                System.out.print("Press number: - ");
                floor = Integer.parseInt(getInput());
            } catch (NumberFormatException e) {
                System.out.println("NB: It allow number !.");
                continue;
            }

            valid = ((floor >= lowestFloor)
                    && (floor <= highestFloor));

            if (!valid) {
                System.out.printf("[HELP] Please select a Floor between %d-%d.",
                        lowestFloor, highestFloor);
            }
        } while (!valid);

        return floor;
    }

    /**
     * @return non-empty user input from the terminal.
     */
    protected static String getInput() {
        Scanner sc = new Scanner(System.in);
        String input;

        do {
            input = sc.nextLine();
        } while (input.isBlank());

        return input;
    }
}
