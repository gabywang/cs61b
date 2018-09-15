package db61b;

import ucb.junit.textui;

/** The suite of all JUnit tests for the qirkat package.
 *  @author P. N. Hilfinger
 */
public class UnitTest {

    /**
     * Run the JUnit tests in this package. Add xxxTest.class entries to
     * the arguments of runClasses to run other JUnit tests.
     */
    public static void main(String[] args) {
        textui.runClasses(TestTable.class);
        System.exit(ucb.junit.textui.runClasses(TestTable.class));
    }
}
