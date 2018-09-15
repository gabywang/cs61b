/**
 *
 */
package gitlet;

/**
 * @author Xiaowen Wang
 */
public class GlobalLogCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        repo.objects().forEach(Commit.class, (hash, com) -> {
            System.out.println(com);
        });

    }

    /** Check if requires repo.*/
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
