
package gitlet;

/**
 * @author Xiaowen Wang
 */
public interface Command {
    /** run the command.
     * @param repo the repository
     * @param args the command arguments*/
    void run(Repository repo, String[] args);

    /** return true if requiresRepo. */
    boolean requiresRepo();

    /** Check the operands.
     * @param args the command argument
     * @return if argument are valid. */
    boolean checkOperands(String[] args);
}
