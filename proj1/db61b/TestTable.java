package db61b;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests basic functionality of the Table class.
*/

public class TestTable {
    /** Testing the table.
    */
    @Test
    public void testColumns() {
        Table a = new Table(new String[]{"SID", "CCN", "Grade"});
        assertEquals(3, a.columns());
    }

    @Test
    public void testGetTitle() {
        Table a = new Table(new String[]
        {"SID", "Lastname", "Firstname", "SemEnter"});
        assertEquals("Firstname", a.getTitle(2));
    }

    @Test
    public void testFindColumn() {
        Table a = new Table(new String[]{"CCN", "Num", "Dept", "Time"});
        assertEquals(2, a.findColumn("Dept"));
        assertEquals(-1, a.findColumn("Sem"));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TestTable.class));
    }
}
