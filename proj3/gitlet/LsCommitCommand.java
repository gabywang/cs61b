/**
 *
 */
package gitlet;

import static gitlet.ReferenceType.HEAD;

/**
 * @author Xiaowen Wang
 */
public class LsCommitCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        Commit commit;
        if (args.length == 0) {
            commit = repo.objects().get(Commit.class,
                    repo.refs().resolve(HEAD));
        } else {
            commit = repo.objects().find(Commit.class, args[0]);
        }
        if (commit == null) {
            throw new IllegalArgumentException("No such commit exists.");
        }

        System.out.println(commit.toString());
        commit.forEach((name, hash) -> {
            System.out.println(name + "\t" + hash);
        });

    }

    /** Check if requires repo. */
    @Override
    public boolean requiresRepo() {
        return true;
    }

    /** Check the operands. */
    @Override
    public boolean checkOperands(String[] args) {
        return args.length == 1 || args.length == 0;
    }

}
