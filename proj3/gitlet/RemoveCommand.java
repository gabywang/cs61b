/**
 *
 */
package gitlet;

import static gitlet.ReferenceType.HEAD;

import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Xiaowen Wang
 */
public class RemoveCommand implements Command {

    /** Run the command. */
    @Override
    public void run(Repository repo, String[] args) {
        remove(repo, args[0],
                repo.objects().get(Commit.class, repo.refs().resolve(HEAD)));
    }

    /** Do remove.
     * @param file the file
     * @param head the head
     * @param repo the repository */
    public static void remove(Repository repo, String file, Commit head) {
        Index index = repo.index();

        if (head.containsKey(file)) {
            try {
                index.remove(file, true);
                if (Files.exists(repo.getWorkingDir().resolve(file))) {
                    Files.delete(repo.getWorkingDir().resolve(file));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            index.remove(file, false);
        }
    }

    /** check if requires repo. */
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
