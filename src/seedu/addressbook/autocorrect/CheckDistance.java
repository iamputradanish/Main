//@@author ShreyasKp
package seedu.addressbook.autocorrect;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.addressbook.commands.Dictionary;
import seedu.addressbook.parser.Parser;

/**
 * Checks the number of single character changes required to convert one string to another.
 */
public class CheckDistance {

    private static final Logger logger = Logger.getLogger(CheckDistance.class.getName());

    private Dictionary dictionary = new Dictionary();

    private ArrayList<String> commandsList = Dictionary.getCommands();

    private ArrayList<String> detailsList = dictionary.getDetails();

    private String prediction = "none";

    private static void setupLogger() {
        Parser.setupLoggerForAll(logger);
    }

    /**
     * Checks distance for invalid commands
     * @param commandInput The invalid input command
     * @return The prediction if found
     */
    public String checkDistance(String commandInput) {
        setupLogger();
        logger.log(Level.INFO, "Checking distance of input command from valid inputs");

        int distance;
        for (String command : commandsList) {
            distance = EditDistance.computeDistance(commandInput, command);
            if (distance == 1) {
                prediction = command;
                break;
            }
        }

        return prediction;
    }

    /**
     * Checks distance for invalid NRICs and finds prediction
     * @param input The invalid input Nric
     * @return The prediction if found
     */
    public String checkInputDistance(String input) {
        logger.log(Level.INFO, "Checking distance of inputted NRIC from existing NRICs");

        int distance;
        for (String nric : detailsList) {
            distance = EditDistance.computeDistance(input, nric);
            if (distance == 1 || distance == 2) {
                prediction = nric;
                break;
            }
        }

        return prediction;
    }

    /**
     * Checks if prediction exists for invalid commands
     * @param commandInput The invalid input command
     * @return True if the command is valid
     */
    public Boolean predictionChecker(String commandInput) {
        int distance;
        boolean check = true;
        for (String command : commandsList) {
            distance = EditDistance.computeDistance(commandInput, command);
            if (distance == 0) {
                check = false;
                break;
            }
        }
        return check;
    }
}
