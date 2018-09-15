
package gitlet;

/**
 * @author Xiaowen Wang
 */
public class FindCommand implements Command {

    /** Run the command.*/
    @Override
    public void run(final Repository repo, String[] args) {
        final int[] iter = new int[] { 0 };
        repo.objects().forEach(Commit.class, (hash, com) -> {
                if (com.getMessage().equals(args[0])) {
                    iter[0]++;
                    System.out.println(hash);
                }
            });
        if (iter[0] == 0) {
            throw new IllegalArgumentException(
                    "Found no commit with that message.");
        }

    }

    /** check if requires repo. */
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
