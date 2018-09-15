
package gitlet;

/**
 * @author Xiaowen Wang
 */
public class PullCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        FetchCommand.fetch(repo, args[0], args[1]);
        MergeCommand.merge(repo, args[0] + "/" + args[1]);
    }

    /** Check if requires repo. */
    @Override
    public boolean requiresRepo() {
        return true;
    }

    /** Check the operands.*/
    @Override
    public boolean checkOperands(String[] args) {
        return args.length == 2;
    }

}
