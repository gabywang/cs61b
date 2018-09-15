/**
 *
 */
package gitlet;

import static gitlet.ReferenceType.BRANCH;
import static gitlet.ReferenceType.HEAD;

/**
 * @author Xiaowen Wang
 */
public class RemoveBranchCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        String branch = args[0];
        if (repo.refs().get(HEAD).target().equals(branch)) {
            throw new IllegalArgumentException(
                    "Cannot remove the current branch.");
        }

        repo.refs().remove(BRANCH, args[0]);
    }

    /** Check if requires repo.*/
    @Override
    public boolean requiresRepo() {
        return true;
    }

    /** Check the operands.*/
    @Override
    public boolean checkOperands(String[] args) {
        return args.length == 1;
    }

}
