package db61b;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collections;

import static db61b.Utils.*;

/** A single table in a database.
 *  @author P. N. Hilfinger
 */
class Table implements Iterable<Row> {
    /** A new Table whose columns are given by COLUMNTITLES, which may
     *  not contain duplicate names. */
    Table(String[] columnTitles) {
        if (columnTitles.length == 0) {
            throw error("table must have at least one column");
        }

        for (int i = columnTitles.length - 1; i >= 1; i -= 1) {
            for (int j = i - 1; j >= 0; j -= 1) {
                if (columnTitles[i].equals(columnTitles[j])) {
                    throw error("duplicate column name: %s",
                                columnTitles[i]);
                }
            }
        }
        _columnlist = columnTitles;
    }

    /** A new Table whose columns are give by COLUMNTITLES. */
    Table(List<String> columnTitles) {
        this(columnTitles.toArray(new String[columnTitles.size()]));
    }

    /** Return the number of columns in this table. */
    public int columns() {
        return _columnlist.length;
    }

    /** Return the title of the Kth column.  Requires 0 <= K < columns(). */
    public String getTitle(int k) {
        if (k < columns() && k >= 0) {
            return _columnlist[k];
        } else {
            throw error("wrong columntitle number");
        }
    }

    /** Return the number of the column whose title is TITLE, or -1 if
     *  there isn't one. */
    public int findColumn(String title) {
        int k = -1;
        for (int i = 0; i < columns(); i++) {
            if (_columnlist[i].equals(title)) {
                k = i;
                break;
            }
        }
        return k;
    }

    /** Return the number of rows in this table. */
    public int size() {
        return _rows.size();
    }

    /** Returns an iterator that returns my rows in an unspecfied order. */
    @Override
    public Iterator<Row> iterator() {
        return _rows.iterator();
    }

    /** Add a new row if no equal row already exists.
     *  @return true if anything was added, false otherwise.
     *  @param row */
    public boolean add(Row row) {
        if (row.size() == columns()) {
            return _rows.add(row);
        } else {
            throw error("inserted row has wrong length");
        }
    }

    /** Read the contents of the file NAME.db, and return as a Table.
     *  Format errors in the .db file cause a DBException. */
    static Table readTable(String name) {
        BufferedReader input;
        Table table;
        input = null;
        table = null;
        try {
            input = new BufferedReader(new FileReader(name + ".db"));
            String header = input.readLine();
            if (header == null) {
                throw error("missing header in DB file");
            }
            String[] columnNames = header.split(",");
            table = new Table(columnNames);
            String next = input.readLine();
            while (next != null) {
                String[] newlinelist = next.split(",");
                Row nextline = new Row(newlinelist);
                table.add(nextline);
                next = input.readLine();
            }
        } catch (FileNotFoundException e) {
            throw error("could not find %s.db", name);
        } catch (IOException e) {
            throw error("problem reading from %s.db", name);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    /* Ignore IOException */
                }
            }
        }
        return table;
    }

    /** Write the contents of TABLE into the file NAME.db. Any I/O errors
     *  cause a DBException. */
    void writeTable(String name) {
        PrintStream output;
        output = null;
        try {
            String sep;
            sep = "";
            output = new PrintStream(name + ".db");
            Row header = new Row(_columnlist);
            for (int i = 0; i < header.size(); i++) {
                sep += ",";
                sep += header.get(i);
            }
            output.append(sep.trim().substring(1) + "\n");
            Iterator rowiterator = iterator();
            while (rowiterator.hasNext()) {
                Row nextrow = (Row) rowiterator.next();
                String nextline = "";
                for (int i = 0; i < nextrow.size(); i++) {
                    nextline += ",";
                    nextline += nextrow.get(i);
                }
                output.append(nextline.trim().substring(1) + "\n");
            }
        } catch (IOException e) {
            throw error("trouble writing to %s.db", name);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /** Print my contents on the standard output, separated by spaces
     *  and indented by two spaces. */
    void print() {
        List<String> unsorted = new ArrayList<String>();
        for (Row row : _rows) {
            String selected = " ";
            if (!row.equals(new Row(_columnlist))) {
                for (int i = 0; i < row.size(); i += 1) {
                    selected += " " + row.get(i);
                }
                unsorted.add(selected);
            }
        }
        Collections.sort(unsorted);
        for (int i = 0; i < unsorted.size(); i += 1) {
            System.out.println(unsorted.get(i));
        }
        System.out.println();
    }

    /** Return a new Table whose columns are COLUMNNAMES, selected from
     *  rows of this table that satisfy CONDITIONS. */
    Table select(List<String> columnNames, List<Condition> conditions) {
        Table result = new Table(columnNames);
        List<Column> listofcolumns = new ArrayList<Column>();
        for (int i = 0; i < columnNames.size(); i++) {
            String columnname = columnNames.get(i);
            Column newcolumn = new Column(columnname, this);
            listofcolumns.add(newcolumn);
        }
        for (Row row : _rows) {
            if (conditions == null) {
                Row unselected = new Row(listofcolumns, row);
                result.add(unselected);
            } else {
                if (Condition.test(conditions, row)) {
                    Row selected = new Row(listofcolumns, row);
                    result.add(selected);
                }
            }
        }
        return result;
    }

    /** Return a new Table whose columns are COLUMNNAMES, selected
     *  from pairs of rows from this table and from TABLE2 that match
     *  on all columns with identical names and satisfy CONDITIONS. */
    Table select(Table table2, List<String> columnNames,
                 List<Condition> conditions) {
        Table result = new Table(columnNames);
        List<Column> listofcolumns = new ArrayList<Column>();
        for (int i = 0; i < columnNames.size(); i++) {
            String columnname = columnNames.get(i);
            Column newcolumn = new Column(columnname, this, table2);
            listofcolumns.add(newcolumn);
        }
        List<Column> column1 = new ArrayList<Column>();
        List<Column> column2 = new ArrayList<Column>();
        for (int i = 0; i < columns(); i++) {
            String c1 = getTitle(i);
            for (int j = 0; j < table2.columns(); j++) {
                String c2 = table2.getTitle(j);
                if (c1.equals(c2)) {
                    Column newcolumn1 = new Column(c1, this);
                    Column newcolumn2 = new Column(c2, table2);
                    column1.add(newcolumn1);
                    column2.add(newcolumn2);
                }
            }
        }
        for (Row row1 : this) {
            for (Row row2: table2) {
                if (conditions == null) {
                    if (equijoin(column1, column2, row1, row2)) {
                        Row added = new Row(listofcolumns, row1, row2);
                        result.add(added);
                    }
                } else {
                    if (Condition.test(conditions, row1, row2)) {
                        if (equijoin(column1, column2, row1, row2)) {
                            Row added = new Row(listofcolumns, row1, row2);
                            result.add(added);
                        }
                    }
                }
            }
        }
        return result;
    }

    /** Return true if the columns COMMON1 from ROW1 and COMMON2 from
     *  ROW2 all have identical values.  Assumes that COMMON1 and
     *  COMMON2 have the same number of elements and the same names,
     *  that the columns in COMMON1 apply to this table, those in
     *  COMMON2 to another, and that ROW1 and ROW2 are indices, respectively,
     *  into those tables. */
    private static boolean equijoin(List<Column> common1, List<Column> common2,
                                    Row row1, Row row2) {
        for (int i = 0; i < common1.size(); i++) {
            Column column1 = common1.get(i);
            Column column2 = common2.get(i);
            String val1 = column1.getFrom(row1);
            String val2 = column2.getFrom(row2);
            if (!val1.equals(val2)) {
                return false;
            }
        }
        return true;
    }

    /** My Rows. */
    private HashSet<Row> _rows = new HashSet<>();
    /** My Columns. */
    private String[] _columnlist;
}
