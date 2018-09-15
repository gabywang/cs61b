package qirkat;

import java.util.ArrayList;

import static qirkat.PieceColor.*;

/** A Player that computes its own moves.
 *  @author Xiaowen Wang
 */
class AI extends Player {

    /** Maximum minimax search depth before going to static evaluation. */
    private static final int MAX_DEPTH = 5;
    /** A position magnitude indicating a win (for white if positive, black
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new AI for GAME that will play MYCOLOR. */
    AI(Game game, PieceColor myColor) {
        super(game, myColor);
    }

    @Override
    Move myMove() {
        Main.startTiming();
        Move move = findMove();
        Main.endTiming();
        return move;
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        if (myColor() == WHITE) {
            findMove(b, MAX_DEPTH, true, 1, -INFTY, INFTY);
        } else {
            findMove(b, MAX_DEPTH, true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE.
     *  The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.
     *  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound. */
    private int findMove(Board board, int depth, boolean saveMove, int sense,
                         int alpha, int beta) {
        Move best;
        best = null;
        int bestvalue = sense == 1 ? -INFTY : INFTY;
        ArrayList<Move> allmoves = new ArrayList<>();
        board.getMoves(allmoves, myColor());
        if (allmoves.isEmpty()) {
            return bestvalue;
        }
        if (depth == 0) {
            return bestvalue;
        } else if (depth == 1) {
            return simplefindMove(board, saveMove, sense, alpha, beta);
        }
        for (Move move : allmoves) {
            Board nextboard = new Board(board);
            nextboard.makeMove(move);
            int response = findMove(new Board(nextboard), depth - 1,
                    false, Math.abs(sense), alpha, beta);
            int nextV = staticScore(nextboard), curV = staticScore(board);
            if ((sense == 1 && response >= bestvalue)
                    || (sense == -1 && response <= bestvalue)) {
                if (response >= bestvalue) {
                    alpha = Math.max(alpha, response);
                } else if (response <= bestvalue) {
                    beta = Math.min(beta, response);
                }
                best = move;
                bestvalue = response;
                if (beta <= alpha) {
                    break;
                }
            }
        }
        if (saveMove) {
            _lastFoundMove = best;
        }
        return bestvalue;
    }

    /** simplefindMove.
     * @param board the board
     * @param alpha alpha
     * @param beta beta
     * @param saveMove saveMove
     * @param sense  sense
     * @return int*/
    private int simplefindMove(Board board, boolean saveMove, int sense,
                               int alpha, int beta) {
        Move best = null;
        int bestvalue = sense == 1 ? -INFTY : INFTY;
        ArrayList<Move> allmoves = new ArrayList<>();
        board.getMoves(allmoves, myColor());
        for (Move move : allmoves) {
            Board nextboard = new Board(board);
            nextboard.makeMove(move);
            int nextV = staticScore(nextboard), curV = staticScore(board);
            if ((sense == 1 && nextV >= bestvalue)
                    || (sense == -1 && nextV <= bestvalue)) {
                if (nextV >= bestvalue) {
                    alpha = Math.max(alpha, nextV);
                } else if (nextV <= bestvalue) {
                    beta = Math.min(beta, nextV);
                }
                bestvalue = nextV;
                best = move;
                if (beta <= alpha) {
                    break;
                }
            }
        }
        if (saveMove) {
            _lastFoundMove = best;
        }
        return bestvalue;
    }

    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        return board.count(WHITE) - board.count(BLACK);
    }

    /** Return a heuristic of a move.
     * @param board the board
     * @param move the move
     * @return heuristic value */
    private int moveScore(Board board, Move move) {
        Board tmp = new Board(board);
        tmp.makeMove(move);
        return staticScore(tmp) - staticScore(board);
    }
}
