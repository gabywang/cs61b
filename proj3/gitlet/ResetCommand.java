/**
 *
 */
package gitlet;

/**
 * @author Xiaowen Wang
 */
public class ResetCommand implements Command {

    /** Run the command.*/
    @Override
    public void run(Repository repo, String[] args) {
        reset(repo, args[0]);
    }

    /**
     * Resets the repo to a given commit.
     * @param repo
     *            The repo.
     * @param commitHash
     *            The commit.
     */
    public static void reset(Repository repo, String commitHash) {
        Commit toCheck;
        if (commitHash.length() == FACTOR) {
            toCheck = repo.objects().get(Commit.class, commitHash);
        } else {
            toCheck = repo.objects().find(Commit.class, commitHash);
        }

        if (toCheck == null) {
            throw new IllegalArgumentException(
                    "No commit with that id exists.");
        }

        repo.checkout(toCheck);
        repo.getCurrentBranch().setTarget(toCheck.sha1());
    }

    /** A factor.*/
    private static final int FACTOR = 40;

    /** Check if requires repo.*/
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
