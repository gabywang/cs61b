import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Xiaowen Wang
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(0) to TO.charAt(0), etc., leaving other characters
     *  unchanged.  FROM and TO must have the same length. */
    Reader reader;
    String trFrom;
    String trTo;
    public TrReader(Reader str, String from, String to) {
        reader = str;
        trFrom = from;
        trTo = to;
    }
    public int read(char[] a, int b, int c) throws IOException {
        int count = b;
        int numChars = 0;
        for (int i = 0; i < c; i++) {
            int num = reader.read();
            if (num == -1) {
                return -1;
            } else {
                char thisChar = (char) num;
                if (trFrom.indexOf(thisChar) == -1) {
                } else {
                    thisChar = trTo.charAt(trFrom.indexOf(thisChar));
                }
                a[count] = thisChar;
                numChars += 1;
                count += 1;
            }
        }
        return numChars;
    }
    public void close() {

    }

    // FILL IN
    // NOTE: Until you fill in the right methods, the compiler will
    //       reject this file, saying that you must declare TrReader
    //     abstract.  Don't do that; define the right methods instead!
}


