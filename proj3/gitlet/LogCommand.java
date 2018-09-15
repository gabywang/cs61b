/**
 *
 */
package gitlet;

import static gitlet.ReferenceType.HEAD;

/**
 * @author Xiaowen Wang
 * Represents a log command which logs all of the commits
 * starting from the head.
 */
public class LogCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        String commitHash = repo.refs().resolve(HEAD);
        while (commitHash != null && !commitHash.equals("")) {
            Commit commit = repo.objects().get(Commit.class, commitHash);
            System.out.println(commit.toString());
            commitHash = commit.getParent();
        }
    }

    /** check the requires repo.*/
    @Override
    public boolean requiresRepo() {
        return true;
    }

    /** Check the operands. */
    @Override
    public boolean checkOperands(String[] args) {
        return args.length == 0;
    }

}
