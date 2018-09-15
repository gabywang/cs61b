package qirkat;

import static qirkat.PieceColor.WHITE;
import static qirkat.PieceColor.BLACK;
import static qirkat.PieceColor.EMPTY;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;

import static qirkat.PieceColor.*;
import static qirkat.Move.*;

/** import graph.B;
 *  import java.util.Formatter;
 *  A Qirkat board.   The squares are labeled by column (a char value between
 *  'a' and 'e') and row (a char value between '1' and '5'.
 *
 *  For some purposes, it is useful to refer to squares using a single
 *  integer, which we call its "linearized index".  This is simply the
 *  number of the square in row-major order (with row 0 being the bottom row)
 *  counting from 0).
 *
 *  Moves on this board are denoted by Moves.
 *  @author Xiaowen Wang
 */
class Board extends Observable {
    /** The standard initial configuration. */
    public static final PieceColor[][] INITIAL_PIECES =
    {{BLACK, BLACK, BLACK, BLACK, BLACK},
        {BLACK, BLACK, BLACK, BLACK, BLACK},
        {BLACK, BLACK, EMPTY, WHITE, WHITE},
        {WHITE, WHITE, WHITE, WHITE, WHITE},
        {WHITE, WHITE, WHITE, WHITE, WHITE}};

    /** The size of the board. */
    public static final int SIZE = 5;

    /** The internal contents of the board. */
    private PieceColor[][] _contents;

    /** A new, cleared board at the start of the game. /// */
    Board() {
        clear();
    }

    /** A copy of B. */
    Board(Board b) {
        _contents = new PieceColor[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            _contents[i] = b._contents[i].clone();
        }
        _whoseMove = b._whoseMove;
        _gameOver = b._gameOver;
    }

    /** Return a constant view of me (allows any access method, but no
     *  method that modifies it). */
    Board constantView() {
        return this.new ConstantBoard();
    }

    /** Clear me to my starting state, with pieces in their initial
     *  positions. */
    void clear() {
        _whoseMove = WHITE;
        _gameOver = false;
        this.clear(INITIAL_PIECES);
        setChanged();
        notifyObservers();
    }

    /** Clear me to my starting state, with pieces in a certain
     *  positions.
     *  @param newContents The new Pieces table. */
    void clear(PieceColor[][] newContents) {
        _contents = new PieceColor[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                _contents[i][j] = newContents[i][j];
            }
        }
    }

    /** Copy B into me. */
    void copy(Board b) {
        internalCopy(b);
    }

    /** Copy B into me. */
    private void internalCopy(Board b) {
        for (int i = 0; i < SIZE; i++) {
            _contents[i] = b._contents[i].clone();
        }
        _whoseMove = b._whoseMove;
        _gameOver = b._gameOver;
    }

    /** Set my contents as defined by STR.  STR consists of 25 characters,
     *  each of which is b, w, or -, optionally interspersed with whitespace.
     *  These give the contents of the Board in row-major order, starting
     *  with the bottom row (row 1) and left column (column a).
     *  All squares
     *  are initialized to allow horizontal movement in either direction.
     *  NEXTMOVE indicates whose move it is. ///
     */
    void setPieces(String str, PieceColor nextMove) {
        if (nextMove == EMPTY || nextMove == null) {
            throw new IllegalArgumentException("bad player color");
        }
        str = str.replaceAll("\\s", "");
        if (!str.matches("[bw-]{25}")) {
            throw new IllegalArgumentException("bad board description");
        }
        for (int k = 0; k < str.length(); k += 1) {
            switch (str.charAt(k)) {
            case '-':
                set(k, EMPTY);
                break;
            case 'b': case 'B':
                set(k, BLACK);
                break;
            case 'w': case 'W':
                set(k, WHITE);
                break;
            default:
                break;
            }
        }
        _whoseMove = nextMove;
        _pastmoves = new Stack<>();
        _pastboard = new Stack<>();
        if (!isMove()) {
            _gameOver = true;
        }
        setChanged();
        notifyObservers();
    }

    /** Return true iff the game is over: i.e., if the current player has
     *  no moves. */
    boolean gameOver() {
        return _gameOver;
    }

    /** Linearized index to row index.
     * @return row index
     * @param k the Linearized index*/
    public int rowk(int k) {
        return 4 - Math.floorDiv(k, 5);
    }

    /** Linearized index to column index.
     * @return column index
     * @param k the Linearized index*/
    public int colk(int k) {
        return k % 5;
    }


    /** Return the current contents of square C R, where 'a' <= C <= 'e',
     *  and '1' <= R <= '5'.  */
    PieceColor get(char c, char r) {
        assert validSquare(c, r);
        return get(Move.index(c, r));
    }

    /** Return the current contents of the square at linearized index K. */
    PieceColor get(int k) {
        assert validSquare(k);
        return _contents[rowk(k)][colk(k)];
    }

    /** Set get(C, R) to V, where 'a' <= C <= 'e', and
     *  '1' <= R <= '5'. */
    private void set(char c, char r, PieceColor v) {
        assert validSquare(c, r);
        set(index(c, r), v);
    }

    /** Set get(K) to V, where K is the linearized index of a square. */
    private void set(int k, PieceColor v) {
        assert validSquare(k);
        _contents[rowk(k)][colk(k)] = v;
    }

    /** Return true if Pieces can do side work.
     * @param k Linearized index*/
    public boolean canSide(int k) {
        return (k % 2) == 0;
    }

    /** Return true iff MOV is legal on the current board.*/
    boolean legalMove(Move mov) {
        PieceColor start = this.get(mov.fromIndex());
        PieceColor end = this.get(mov.toIndex());
        int k = mov.fromIndex();
        if (start == EMPTY || start != _whoseMove || end != EMPTY) {
            return false;
        }

        if (mov.jumpTail() != null) {
            Move move1 = Move.move(mov.col0(), mov.row0(),
                    mov.col1(), mov.row1());
            set(mov.fromIndex(), EMPTY);
            set(mov.toIndex(), start);
            boolean legalTailMove = legalMove(mov.jumpTail());
            set(mov.fromIndex(), start);
            set(mov.toIndex(), end);
            return legalMove(move1) && legalTailMove;
        }
        MoveList legalmoves = new MoveList();
        getJumps(legalmoves, k);
        if (legalmoves.isEmpty()) {
            for (int i = 0; i <= MAX_INDEX; i += 1) {
                if (get(i) == start && jumpPossible(i)) {
                    return false;
                }
            }
            getMoves(legalmoves, k);
        }
        return legalmoves.contains(mov);

    }

    /** Return a list of all legal moves from the current position. */
    ArrayList<Move> getMoves() {
        ArrayList<Move> result = new ArrayList<>();
        getMoves(result);
        return result;
    }

    /** Add all legal moves from the current position to MOVES. */
    void getMoves(ArrayList<Move> moves) {
        if (gameOver()) {
            return;
        }
        if (jumpPossible()) {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                getJumps(moves, k);
            }
        } else {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                getMoves(moves, k);
            }
        }
    }

    /** Add all legal non-capturing moves from the position
     *  with linearized index K to MOVES. */
    public void getMoves(ArrayList<Move> moves, int k) {
        PieceColor p = get(k);
        List<Integer> moveIndex = moveIndex(k);
        moveIndex.removeIf(curk -> (p == WHITE && curk < k - 1)
                || (p == BLACK && curk > k + 1));
        if (p == WHITE && row(k) >= '5' || p == BLACK && row(k) <= '1') {
            moveIndex.removeAll(new ArrayList<>(Arrays.asList(k - 1, k + 1)));
        }
        for (int i = 0; i < moveIndex.size(); i++) {
            int k1 = moveIndex.get(i);
            if (get(k1).isEmptyPiece()) {
                moves.add(Move.move(Move.col(k), Move.row(k),
                        Move.col(k1), Move.row(k1)));
            }
        }
    }

    /** Add all legal moves for current player.
     * @param moves moves
     * @param p color/// */
    public void getMoves(ArrayList<Move> moves, PieceColor p) {
        if (jumpPossible(p)) {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                if (get(k) == p) {
                    getJumps(moves, k);
                }
            }
        } else {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                if (get(k) == p) {
                    getMoves(moves, k);
                }
            }
        }
    }

    /** Add all legal captures from the position with linearized index K
     *  to MOVES. /// */
    public void getJumps(ArrayList<Move> moves, int k) {
        PieceColor p = get(k);
        for (int k1 = 0; k1 <= MAX_INDEX; k1++) {
            if (jumpPossible(k, k1)) {
                moves.add(Move.move(Move.col(k), Move.row(k),
                        Move.col(k1), Move.row(k1)));
            }
        }
    }

    /** Return true iff MOV is a valid jump sequence on the current board.
     *  MOV must be a jump or null.  If ALLOWPARTIAL, allow jumps that
     *  could be continued and are valid as far as they go. /// */
    boolean checkJump(Move mov, boolean allowPartial) {
        if (mov == null) {
            return false;
        }
        if (jumpPossible(mov.fromIndex(), mov.toIndex())) {
            if (allowPartial) {
                return true;
            } else {
                if (mov.jumpTail() == null
                        && !jumpPossible(mov.toIndex())) {
                    return true;
                }
                return checkJump(mov.jumpTail(), allowPartial);
            }
        }
        return false;
    }

    /** Return true iff a jump is possible for a piece at position C R. */
    boolean jumpPossible(char c, char r) {
        return jumpPossible(index(c, r));
    }

    /** Return true iff a jump is possible for a piece at position with
     *  linearized index K. /// */
    boolean jumpPossible(int k) {
        PieceColor p = get(k);
        if (p.isEmptyPiece() || p != _whoseMove) {
            return false;
        } else {
            List<Integer> oppoIndex = oppoIndex(k);
            List<Integer> desIndex = jumpIndex(k);
            for (int i = 0; i < desIndex.size(); i++) {
                if (get(oppoIndex.get(i)) == p.opposite()
                        && get(desIndex.get(i)).isEmptyPiece()) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Return true iff a jump is possible from a piece at position with
     *  linearized index K0 to K1. /// */
    boolean jumpPossible(int k0, int k1) {
        List<Integer> oppoIndex = oppoIndex(k0);
        List<Integer> desIndex = jumpIndex(k0);
        if (get(k0) == EMPTY || get(k1) != EMPTY) {
            return false;
        }
        int koppo = (k0 + k1) / 2;
        return (k1 >= 0 && k1 < SIZE * SIZE)
                && (oppoIndex.contains(koppo))
                && get(koppo) == get(k0).opposite()
                && (desIndex.contains(k1))
                && get(k1).isEmptyPiece();
    }

    /** Return true iff a jump is possible from the current board. */
    boolean jumpPossible() {
        for (int k = 0; k <= MAX_INDEX; k += 1) {
            if (jumpPossible(k)) {
                return true;
            }
        }
        return false;
    }

    /** Return true iff a jump is possible from the specific player.
     * @param p color*/
    boolean jumpPossible(PieceColor p) {
        for (int k = 0; k <= MAX_INDEX; k += 1) {
            if (get(k) == p && jumpPossible(k)) {
                return true;
            }
        }
        return false;
    }

    /** Return true iff a move is possible for a piece at position with
     *  linearized index K. /// */
    boolean movePossible(int k) {
        if (get(k) != _whoseMove) {
            return false;
        }
        MoveList moves = new MoveList();
        getMoves(moves, k);
        return !moves.isEmpty();
    }

    /** return list of valid moveIndex at position k.
     *  Regardless of what color the Piece is.
     *  @param k index*/
    public List<Integer> moveIndex(int k) {
        List<Integer> moveIndex
                = new ArrayList<>(Arrays.asList(k - 5, k - 1, k + 1, k + 5));
        List<Integer> siedmoveIndex
                = new ArrayList<>(Arrays.asList(k - 6, k - 4, k + 4, k + 6));
        if (canSide(k)) {
            moveIndex.addAll(siedmoveIndex);
        }
        moveIndex.removeIf(curk -> (curk < 0 || curk >= SIZE * SIZE)
                || (col(k) == 'a' && col(curk) == 'e')
                || (col(k) == 'e' && col(curk) == 'a'));
        return moveIndex;
    }

    /** return list of valid moveIndex at position k.
     *  Regardless of what color the Piece is.
     *  @param k index*/
    public List<Integer> oppoIndex(int k) {
        List<Integer> moveIndex
                = new ArrayList<>(Arrays.asList(k - 5, k - 1, k + 1, k + 5));
        List<Integer> siedmoveIndex
                = new ArrayList<>(Arrays.asList(k - 6, k - 4, k + 4, k + 6));
        if (canSide(k)) {
            moveIndex.addAll(siedmoveIndex);
        }
        moveIndex.removeIf(curk -> (curk < 0 || curk >= SIZE * SIZE)
                || (col(k) == 'a' && col(curk) == 'e')
                || (col(k) == 'e' && col(curk) == 'a')
                || corner(curk)
                || (!outside(k) && outside(curk)));
        return moveIndex;
    }

    /** corner.
     * @param k the index
     * @return true if in corner */
    private boolean corner(int k) {
        int c = col(k), r = row(k);
        return (c == 'a' || c == 'e') && (r == '1' || r == '5');
    }

    /** outside.
     * @param k the index
     * @return true if sided. */
    private boolean outside(int k) {
        int c = col(k), r = row(k);
        return (c == 'a' || c == 'e') || (r == '1' || r == '5');
    }

    /** return list of valid jumpIndex at position k.
     *  Regardless of what color the Piece is.
     *  @param k index */
    public List<Integer> jumpIndex(int k) {
        List<Integer> jumpIndex
                = new ArrayList<>(Arrays.asList(k - 10, k - 2, k + 2, k + 10));
        List<Integer> sidejumpIndex
                = new ArrayList<>(Arrays.asList(k - 12, k - 8, k + 8, k + 12));
        if (canSide(k)) {
            jumpIndex.addAll(sidejumpIndex);
        }
        jumpIndex.removeIf(curk -> curk < 0 || curk >= SIZE * SIZE
                || Math.abs(col(k) - col(curk)) > 2);
        return jumpIndex;
    }


    /** Return the color of the player who has the next move.  The
     *  value is arbitrary if gameOver(). */
    PieceColor whoseMove() {
        return _whoseMove;
    }

    /** Perform the move C0R0-C1R1, or pass if C0 is '-'.  For moves
     *  other than pass, assumes that legalMove(C0, R0, C1, R1). */
    void makeMove(char c0, char r0, char c1, char r1) {
        makeMove(Move.move(c0, r0, c1, r1, null));
    }

    /** Make the multi-jump C0 R0-C1 R1..., where NEXT is C1R1....
     *  Assumes the result is legal. */
    void makeMove(char c0, char r0, char c1, char r1, Move next) {
        makeMove(Move.move(c0, r0, c1, r1, next));
    }

    /** Make the Move MOV on this Board, assuming it is legal. /// */
    void makeMove(Move mov) {
        makeMove(mov, false);
    }

    /** Make the Move MOV on this Board,
     * with legalmove assert true or free.
     * @param undomove free assert legal move
     * @param mov the mov */
    void makeMove(Move mov, boolean undomove) {
        if (!legalMove(mov)) {
            return;
        }
        if (!undomove) {
            assert legalMove(mov);
        }
        if (_pastmoves.size() >= 2) {
            Move tmp = _pastmoves.pop();
            Move pre = _pastmoves.peek();
            if ((pre.isLeftMove() && mov.isRightMove())
                    || pre.isRightMove() && mov.isLeftMove()) {
                _pastmoves.push(tmp);
                _pastmoves.push(mov);
                _whoseMove = _whoseMove.opposite();
                return;
            }
            _pastmoves.push(tmp);
        }


        _pastmoves.push(mov);
        _pastboard.push(new Board(this));
        while (mov != null) {
            if (mov.isJump()) {
                int jumpk = mov.jumpedIndex();
                _contents[rowk(jumpk)][colk(jumpk)] = EMPTY;
            }
            int nextk = index(mov.col1(), mov.row1());
            int curk = index(mov.col0(), mov.row0());
            _contents[rowk(nextk)][colk(nextk)] = this.get(curk);
            _contents[rowk(curk)][colk(curk)] = EMPTY;
            mov = mov.jumpTail();
        }
        _whoseMove = _whoseMove.opposite();
        if (!this.isMove()) {
            _gameOver = true;
        }
        setChanged();
        notifyObservers();
    }

    /** Undo the last move, if any.
     *  Undo two times /// */
    void undo() {
        if (_pastboard.empty()) {
            return;
        }
        copy(_pastboard.pop());
        _pastmoves.pop();
        setChanged();
        notifyObservers();
    }

    /** Count the number of Pieces of a color.
     * @param color the PieceColor
     * @return color numbers */
    public int count(PieceColor color) {
        int count = 0;
        for (PieceColor[] rows : _contents) {
            for (PieceColor p : rows) {
                if (p == color) {
                    count += 1;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    /** Return a text depiction of the board.  If LEGEND, supply row and
     *  column numbers around the edges. */
    String toString(boolean legend) {
        StringBuilder sb = new StringBuilder();
        for (char n = '5'; n >= '1'; n -= 1) {
            if (!(n == '5')) {
                sb.append("\n ");
            } else {
                sb.append(" ");
            }
            for (char c = 'a'; c <= 'e'; c += 1) {
                sb.append(" " + get(c, n).shortName());
            }
        }
        if (legend) {
            sb.append("\n  a b c d e");
        }
        return sb.toString();
    }

    /** Returns an external rendition of me, suitable for
     *  human-readable textual display.  This is distinct from the dumped
     *  representation (returned by toString). */
    public String toDisplayString() {
        String output = "";
        output += "===\n" + toString() + "\n===";
        return output;
    }

    /** Return true iff there is a move for the current player. /// */
    public boolean isMove() {
        for (int k = 0; k <= MAX_INDEX; k += 1) {
            if ((jumpPossible(k) || movePossible(k))
                    && get(k) == _whoseMove) {
                return true;
            }
        }
        return false;
    }

    /** Player that is on move. */
    private PieceColor _whoseMove;

    /** Set true when game ends. */
    private boolean _gameOver;

    /** A stack store the previous moves. */
    private Stack<Move> _pastmoves = new Stack<>();

    /** A stack store the previous moves. */
    private Stack<Board> _pastboard = new Stack<>();

    /** Convenience value giving values of pieces at each ordinal position. */
    static final PieceColor[] PIECE_VALUES = PieceColor.values();

    /** One cannot create arrays of ArrayList<Move>, so we introduce
     *  a specialized private list type for this purpose. */
    private static class MoveList extends ArrayList<Move> {
    }

    /** A read-only view of a Board. */
    private class ConstantBoard extends Board implements Observer {
        /** A constant view of this Board. */
        ConstantBoard() {
            super(Board.this);
            Board.this.addObserver(this);
        }

        @Override
        void copy(Board b) {
            assert false;
        }

        @Override
        void clear() {
            assert false;
        }

        @Override
        void makeMove(Move move) {
            assert false;
        }

        /** Undo the last move. */
        @Override
        void undo() {
            assert false;
        }

        @Override
        public void update(Observable obs, Object arg) {
            super.copy((Board) obs);
            setChanged();
            notifyObservers(arg);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Board) {
            Board b = (Board) o;
            return (b.toString().equals(toString())
                    && _whoseMove == b.whoseMove());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
