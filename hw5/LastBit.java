/** Bit-twiddling class.
 *  @author Xiaowen Wang
 */
public class LastBit {

    /** Returns the number N!=0 with all but its last (least significant)
     *  1-bit set to 0.
     *
     *  For example
     *      lastBit(3) == lastBit(7) == lastBit(1145) == 1,
     *  and
     *      lastBit(4) == lastBit(12) == lastBit(2052) == 4.
     */
    public static int lastBit(int n) {
        int num = 0;
        if ((n % 2) == 0) {
            while (n % 2 != 1) {
                num += 1;
                n = n >>> 1;
            }
        } else {
            return 1;
        }
        int result = 1 << num;
        return result;
    }

}
