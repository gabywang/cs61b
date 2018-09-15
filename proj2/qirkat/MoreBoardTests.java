package qirkat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoreBoardTests {

    /** the string representation of this is
     * "  b b b b b\n  b b b b b\n  b b - w w\n  w w w w w\n  w w w w w"
     * feel free to modify this to however you want to represent your board.
     */
    private final char[][] boardRepr = new char[][]{
        {'b', 'b', 'b', 'b', 'b'},
        {'b', 'b', 'b', 'b', 'b'},
        {'b', 'b', '-', 'w', 'w'},
        {'w', 'w', 'w', 'w', 'w'},
        {'w', 'w', 'w', 'w', 'w'}
    };

    private static final String[] MOVE1 =
    { "c2-c3", "c4-c2",
        "c1-c3", "a3-c1",
        "c3-a3", "c5-c4",
        "a3-c5-c3",
    };

    private static final String MOVE1_BOARD =
            "  b b - b b\n  b - - b b\n  - - w w w\n  w - - w w\n  w w b w w";

    private static void makeMoves(Board b, String[] moves) {
        for (String s : moves) {
            b.makeMove(Move.parseMove(s));
        }
    }

    private final PieceColor currMove = PieceColor.WHITE;

    /**
     * @return the String representation of the initial state. This will
     * be a string in which we concatenate the values from the bottom of
     * board upwards, so we can pass it into setPieces. Read the comments
     * in Board#setPieces for more information.
     * For our current boardRepr, the String returned by getInitial
     * Representation is
     * "  w w w w w\n  w w w w w\n  b b - w w\n  b b b b b\n  b b b b b"
     *
     * We use a StringBuilder to avoid recreating Strings (because Strings
     * are immutable).
     */
    private String getInitialRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int i = boardRepr.length - 1; i >= 0; i--) {
            for (int j = 0; j < boardRepr[0].length; j++) {
                sb.append(boardRepr[i][j] + " ");
            }
            sb.deleteCharAt(sb.length() - 1);
            if (i != 0) {
                sb.append("\n  ");
            }
        }
        return sb.toString();
    }

    /** create a new board with the initial state. */
    private Board getBoard() {
        Board b = new Board();
        b.setPieces(getInitialRepresentation(), currMove);
        return b;
    }

    /** reset board b to initial state. */
    private void resetToInitialState(Board b) {
        b.setPieces(getInitialRepresentation(), currMove);
    }

    @Test
    public void testSomething() {
        Board b = getBoard();
        makeMoves(b, MOVE1);
        assertEquals(MOVE1_BOARD, b.toString());
        resetToInitialState(b);
    }

    @Test
    public void testGetMoves1() {
        char[][] board = new char[][]{
                {'b', 'b', 'b', 'b', 'b'},
                {'b', 'b', 'b', 'b', 'b'},
                {'b', 'b', '-', 'w', 'w'},
                {'w', 'w', 'w', 'w', 'w'},
                {'w', 'w', 'w', 'w', 'w'}
        };
    }
}
