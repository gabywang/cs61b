package gitlet;

import static gitlet.ReferenceType.BRANCH;
import static gitlet.ReferenceType.HEAD;
import static gitlet.ReferenceType.TAG;

/**
 * @author Xiaowen Wang
 */
public class CheckoutCommand implements Command {

    /**
     * Run the command.
     */
    @Override
    public void run(Repository repo, String[] args) {
        if (args.length == 1) {
            checkoutBranch(repo, args[0]);
        } else if (args.length == 2) {
            checkoutFile(repo, repo.refs().resolve(HEAD), args[1]);
        } else if (args.length == 3) {
            checkoutFile(repo, args[0], args[2]);
        }

    }

    /**
     * Checks out a file from a commit.
     *
     * @param repo       the repository.
     * @param filename   The file name.
     * @param commitHash The commit.
     */
    public static void checkoutFile(Repository repo, String commitHash,
                                    String filename) {
        Commit toCheck;
        if (commitHash.length() == LENHASH) {
            toCheck = repo.objects().get(Commit.class, commitHash);
        } else {
            toCheck = repo.objects().find(Commit.class, commitHash);
        }

        if (toCheck == null) {
            throw new IllegalArgumentException(
                    "No commit with that id exists.");
        }
        repo.checkout(toCheck, filename, false);
    }

    /**
     * hash length for checkoutfile.
     */
    private static final int LENHASH = 40;

    /**
     * Checks out an entire branch.
     *
     * @param repo       the Repository.
     * @param branchName the branchname.
     */

    public static void checkoutBranch(Repository repo, String branchName) {
        Reference branch = repo.refs().get(BRANCH, branchName);
        if (branch.equals(repo.getCurrentBranch())) {
            throw new IllegalStateException(
                    "No need to checkout the current branch.");
        }

        String commitHash = branch.target();
        repo.checkout(repo.objects().get(Commit.class, commitHash));
        repo.setCurrentBranch(branchName);
        repo.getCurrentBranch().setTarget(commitHash);

    }

    /** check if requires repo.*/
    @Override
    public boolean requiresRepo() {
        return true;
    }

    /**
     * Check the operands.
     */
    @Override
    public boolean checkOperands(String[] args) {
        return args.length == 1 && !args[0].equals("--")
                || args.length == 3 && args[1].equals("--")
                || args.length == 2 && args[0].equals("--");
    }
}