package db61b;

import static db61b.Utils.*;

/** A Column is effectively an index of a specific, named column
 *  in a list of rows.  A Column is created from a sequence [t1,...tn]
 *  of Tables and a name, c, which must be the name of a column in one of
 *  the ti.  Assume that tk is the first of the Tables containing a column
 *  named c.  The resulting Column object can extract (method getFrom)
 *  from a sequence of n rows, one for each table, the value of
 *  of the column named c.
 *  @author Xiaowen Wang
*/
class Column {
    /** Selects column named NAME from a row of one of the given TABLES. */
    Column(String name, Table... tables) {
        _name = name;
        for (_table = 0; _table < tables.length; _table += 1) {
            _column = tables[_table].findColumn(name);
            if (_column != -1) {
                return;
            }
        }
        throw error("unknown column: %s", name);
    }

    /** Return my name. */
    String getName() {
        return _name;
    }

    /** Assuming that for each k, ROWS[k] is the index of a row from my
     *  kth table, return the value of my column from the appropriate
     *  row.  It is assumed that all columns with the same name have
     *  the same value. */
    String getFrom(Row... rows) {
        return rows[_table].get(_column);
    }

    /** Column name denoted by THIS. */
    private String _name;
    /** Index of the table and column from which to extract a value. */
    private int _table, _column;
}
