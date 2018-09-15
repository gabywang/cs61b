
package gitlet;

import static gitlet.ReferenceType.BRANCH;
import static gitlet.ReferenceType.HEAD;
import static gitlet.ReferenceType.TAG;

/**
 * @author Xiaowen Wang
 */
public class InitRemoteCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        repo.init();
        String initCommit = repo.refs().get(TAG, "iniital").target();
        repo.objects().remove(Commit.class, initCommit);
        repo.refs().get(HEAD).setTarget("");
        repo.refs().remove(BRANCH, "master");

    }

    /** Check the requires repo. */
    @Override
    public boolean requiresRepo() {
        return false;
    }

    /** Check the operands. */
    @Override
    public boolean checkOperands(String[] args) {
        return args.length == 0;
    }

}
