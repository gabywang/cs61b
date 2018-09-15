/* NOTE: The file ArrayUtil.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Xiaowen Wang
 */
class Arrays {
    /* C. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int[] catenated= new int[A.length + B.length];
        int i = 0;
        while (i < A.length){
            catenated[i]= A[i];
            i += 1;
        }
        for (int j = 0; j < B.length ; j += 1){
            catenated[i]= B[j];
            i += 1;
        }
        return catenated;
    }

    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        int[] newArray= new int[A.length - len];
        for (int i= 0; i < A.length - len; i+=1){
            if (i >= start){
                newArray[i]= A[i+len];
            }else{
                newArray[i]= A[i];
            }
        }
        return newArray;
    }

    /* E. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        int numLists = 0;

        int k = A[0];

        for (int i = 0; i < A.length; i += 1) {
            if (k > A[i]) {
                numLists += 1;
            }
        }

        int[][] matrix = new int[numLists][];
        int[] indices = new int[numLists];
        int[] lengths = new int[numLists + 1];

        int j = 0;
        for (int i = 0; i < numLists; i += 1) {
            int h = A[j];

            for ( ; j < A.length; j += 1) {
                if (h > A[j]) {
                    indices[i] = j;
                }
            }
        }

        return matrix;
    }
}
