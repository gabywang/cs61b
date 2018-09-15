package gitlet;

import static gitlet.ReferenceType.REMOTE;

/**
 * @author Xiaowen Wang
 */
public class AddRemoteCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        String remoteName = args[0];
        String targetDir = args[1];

        repo.refs().add(REMOTE, remoteName, new Reference(targetDir));
    }

    /** check if requires repo. */
    @Override
    public boolean requiresRepo() {
        return true;
    }

    /** Check the operands. */
    @Override
    public boolean checkOperands(String[] args) {
        return args.length == 2;
    }

}
