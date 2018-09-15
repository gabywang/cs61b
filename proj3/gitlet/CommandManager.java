
package gitlet;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Xiaowen Wang
 */
public class CommandManager {
    /** A map to store the commands. */
    private HashMap<String, Command> commandMap;
    /** Create a new hashmap. */
    public CommandManager() {
        this.commandMap = new HashMap<>();
    }

    /** Add a new command.
     * @param trigger the command string name
     * @param newCommand the command */
    public void add(String trigger, Command newCommand) {
        this.commandMap.put(trigger, newCommand);
    }

    /**
     * Processes a particular string of arguments to the program.
     * @param localRepo Given a local repository.
     * @param args The arguments.
     * @throws IllegalStateException */
    public void process(Repository localRepo, String args[])
            throws IllegalStateException, IllegalArgumentException {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Please enter a command.");
        }

        String trigger = args[0];
        Command command = this.commandMap.get(trigger);

        if (command == null) {
            throw new IllegalArgumentException(
                    "No command with that name exists.");
        }

        String[] operands = Arrays.copyOfRange(args, 1, args.length);
        if (!command.checkOperands(operands)) {
            throw new IllegalArgumentException("Incorrect operands.");
        }

        if (command.requiresRepo() && !localRepo.isOpen()) {
            throw new IllegalStateException(
                    "Not in an initialized gitlet directory.");
        }

        command.run(localRepo, operands);
    }
}
