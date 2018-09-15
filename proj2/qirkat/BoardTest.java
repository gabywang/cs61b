package qirkat;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/** Tests of the Board class.
 *  @author Xiaowen Wang
 */
public class BoardTest {

    private static final String INIT_BOARD =
        "  b b b b b\n  b b b b b\n  b b - w w\n  w w w w w\n  w w w w w";

    private static final String[] GAME1 =
    { "c2-c3", "c4-c2",
      "c1-c3", "a3-c1",
      "c3-a3", "c5-c4",
      "a3-c5-c3",
    };

    private static final String[] GAME2 =
    { "c1-c2", "c1-c2", "d1-c2"};

    private static final String GAME1_BOARD =
        "  b b - b b\n  b - - b b\n  - - w w w\n  w - - w w\n  w w b w w";

    private static void makeMoves(Board b, String[] moves) {
        for (String s : moves) {
            b.makeMove(Move.parseMove(s));
        }
    }

    Board b = new Board();

    private static void printBoard(Board b) {
        System.out.println(b.toString(true));
        System.out.println();
    }

    @BeforeClass
    public static void setUp() {

    }

    @Test
    public void testInit1() {
        Board b0 = new Board();
        assertEquals(INIT_BOARD, b0.toString());
    }

    @Test
    public void testMoves1() {
        Board b0 = new Board();
        makeMoves(b0, GAME1);
        assertEquals(GAME1_BOARD, b0.toString());
    }


    @Test
    public void testUndo() {
        Board b0 = new Board();
        Board b1 = new Board(b0);
        makeMoves(b0, GAME1);
        Board b2 = new Board(b0);
        for (int i = 0; i < GAME1.length; i += 1) {
            b0.undo();
        }
        assertEquals("failed to return to start", b1, b0);
        makeMoves(b0, GAME1);
        assertEquals("second pass failed to reach same position", b2, b0);
    }

    @Test
    public void testMoveIndex() {
        List<Integer> moveIndex = b.moveIndex(0);
        List<Integer> expmoveIndex
                = new ArrayList<Integer>(Arrays.asList(1, 5, 6));
        assertTrue(moveIndex.containsAll(expmoveIndex));
        assertTrue(moveIndex.size() == 3);
    }

    @Test
    public void testJumpIndex() {
        List<Integer> jumpIndex = b.jumpIndex(0);
        List<Integer> expjumpIndex
                = new ArrayList<Integer>(Arrays.asList(2, 10, 12));
        assertTrue(jumpIndex.containsAll(expjumpIndex));
        assertTrue(jumpIndex.size() == 3);
    }

    @Test
    public void testGetMoves1() {
        char[][] cboard1 = new char[][]{
                {'-', '-', '-', '-', 'w'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'b', '-', '-', '-', '-'}
        };
        char[][] cboard2 = new char[][]{
                {'b', 'b', 'b', 'b', 'b'},
                {'b', 'b', '-', 'b', 'b'},
                {'-', '-', 'w', 'w', 'w'},
                {'w', '-', '-', 'w', 'w'},
                {'w', 'w', 'b', 'w', 'w'}
        };
        char[][] cboard = cboard2;
        String str = getHardBoard(cboard);
        b.setPieces(str, PieceColor.WHITE);
        ArrayList<Move> moves = new ArrayList<>();
        assertFalse(b.jumpPossible(18));
        assertFalse(b.jumpPossible());
        b.getMoves(moves);
        assertTrue(b.legalMove(Move.parseMove("b1-b2")));
    }


    /** from easy board to getHardBoard. */
    private String getHardBoard(char[][] boardRepr) {
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

    @Test
    public void testGetMoves2() {
        char[][] cboard1 = new char[][]{
                {'-', '-', '-', '-', 'w'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'b', '-', '-', '-', '-'}
        };
        char[][] cboard = cboard1;
        String str = getHardBoard(cboard);
        b.setPieces(str, PieceColor.WHITE);
        ArrayList<Move> moves = new ArrayList<>();
        b.getMoves(moves);
        b.getMoves(moves, 0);
        b.getMoves(moves, 24);
    }
}
