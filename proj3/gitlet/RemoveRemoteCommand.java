
package gitlet;

import static gitlet.ReferenceType.REMOTE;

/**
 * @author Xiaowen Wang
 */
public class RemoveRemoteCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        String remote = args[0];
        repo.refs().remove(REMOTE, remote);

    }

    /** Check the operands. */
    @Override
    public boolean requiresRepo() {
        return true;
    }

    /** Check the operands. */
    @Override
    public boolean checkOperands(String[] args) {
        return args.length == 1;
    }

}
