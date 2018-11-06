//@@author ongweekeong
package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.inbox.Inbox;
import seedu.addressbook.inbox.Msg;
import seedu.addressbook.password.Password;
import seedu.addressbook.timeanddate.TimeAndDate;

import java.io.IOException;
import java.util.TreeSet;

/** Prints out all unread notifications ordered by read status, priority, then timestamp
 * (earlier message has higher priority).
 *
 * @return messages to be printed out on the main window.
 */

public class ShowUnreadCommand extends Command {
    public static final String COMMAND_WORD = "showunread";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all unread messages in the application starting from the most urgent.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {

        Inbox myInbox = new Inbox(Password.getID());
        TreeSet<Msg> allMsgs;
        int myUnreadMsgs;
        int messageNum = 1;
        Msg msgToPrint;
        try {
            allMsgs = myInbox.loadMsgs();
            myUnreadMsgs = myInbox.checkNumUnreadMessages();
            if(myUnreadMsgs!=0) {
                String fullPrintedMessage = Messages.MESSAGE_UNREAD_MSG_NOTIFICATION + '\n';
                for(int i=0; i<myUnreadMsgs; i++){
                    msgToPrint = allMsgs.pollFirst();
                    fullPrintedMessage += concatenateMsg(messageNum, msgToPrint);
                    messageNum++;
                }
                allMsgs.clear();
                return new CommandResult(String.format(fullPrintedMessage, myUnreadMsgs));
            }
            else{
                allMsgs.clear();
                return new CommandResult(Messages.MESSAGE_NO_UNREAD_MSGS);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResult("Error loading messages.");
        }
    }

    public static String concatenateMsg(int messageNum, Msg message) throws NullPointerException{
        String concatenatedMsg;
        TimeAndDate dateFormatter = new TimeAndDate();
//        try{
//            concatenatedMsg = String.valueOf(messageNum) + ".\t[UNREAD] Sender: " + message.getSenderId() + " Priority: " + message.getPriority() +
//                    ", Sent: " + dateFormatter.outputDATHrs(message.getTime()) + ",\n\t\tMessage: " + message.getMsg() + ", Coordinates: " +
//                    message.getLatitude() + ", " + message.getLongitude() + ", ETA: " + message.getEta() + ".\n";
//        }
//        catch(Exception e){
            concatenatedMsg = String.valueOf(messageNum) + ".\t[UNREAD] Sender: " + message.getSenderId() + " Priority: " +
                    message.getPriority() + ", Sent: " + dateFormatter.outputDATHrs(message.getTime()) + ",\n\t\tMessage: " + message.getMsg() + "\n";
//        }
        return concatenatedMsg;
    }

}
