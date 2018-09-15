package gitlet;

import static gitlet.ReferenceType.BRANCH;
import static gitlet.ReferenceType.HEAD;

/**
 * @author Xiaowen Wang
 */
public class BranchCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        String headCommit = repo.refs().resolve(HEAD);
        repo.refs().add(BRANCH, args[0], new Reference(headCommit));

    }

    /** Check if requires repo. */
    @Override
    public boolean requiresRepo() {
        return true;
    }

    /** check the operands. */
    @Override
    public boolean checkOperands(String[] args) {
        return args.length == 1;
    }

}
