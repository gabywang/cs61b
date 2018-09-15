/**LEC#4 Pointer*/
public class IntList {
  public IntList(int head, IntList tail) {
    this.head = head;
    this.tail = tail;
  }
  /** Nondestructive incrList.*/
  static IntList incrList(IntList P, int n) {
    if (P == null) {
      return null;
    } else {
      return new IntList(P.head+n, incrList(P.tail, n));
    }
  }
  /** Destructive dincrList.*/
  static IntList dincrList(IntList P, int n) {
    if (P == null) {
      return null;
    } else {
      P.head += n;
      P.tail = dincrList(P.tail, n);
      return P;
    }
  }
  public int head;
  public IntList tail;
}
/**LEC#6 Array*/
/** Add source file*/
import static java.lang.System.arraycopy;
/**Insert into an array.*/
public class Array {
  static void insert (String[] arr, int k, String x) {
    for (int i = arr.length-1; i > k; i -= 1) {
      arr[i] = arr[i-1];
    }
    /** Alternative to this loop:
    arraycopy(arr, k, arr, k+1, arr.length-k-1);*/
    arr[k] = x;
  }
  /** grows the array instead of shove "skunk" off the end.*/
  static String[] insert2 (String[] arr, int k, String x) {
    Strint[] result = new String[arr.length + 1];
    arraycopy(arr, 0, result, 0, k);
    arraycopy(arr, k, result, k+1, arr.length-k);
    result[k] = x;
    return result;
  }
  /** Merge */
  public static int[] merge(int[] A, int[] B) {
    return merge(A, 0, B, 0, new int[A.length+B.length], 0);
  }
  /** Tail-Recursive
   * Merge A[L0..] and B[L1..] into C[K..], assuming A and B sorted.*/
  static int[] merge(int[] A, int L0, int[] B, int L1, int[] C, int k) {
    if (L0 >= A.length) {
      arraycopy(B, L1, C, k, B.length-L1);
    } else if (L1 >= B.length) {
      arraycopy(A, L0, C, k, A.length-L0);
    } else if (A[L0] <= b[L1]) {
      C[k] = A[L0];
      merge(A, L0+1, B, L1, C, k+1);
    } else {
      C[k] = B[L1];
      merge(A, L0, B, L1+1, C, k+1);
    }
    return C;
  }
  /** Iterative */
  public static int[] merge2(int[] A, int[] B) {
    int[] C = new int[A.length + B.length];
    int L0, L1;
    L0 = L1 = 0;
    for (int k = 0; k < C.length; k += 1) {
      if (L0 >= A.length) {
        C[k] = B[L1]; L1 += 1;
      } else if (L1 >= B.length) {
        C[k] = A[L0]; L0 += 1;
      } else if (A[L0] < B[L1]) {
        C[k] = A[L0]; L0 += 1;
      } else {
        C[k] = B[L1]; L1 += 1;
      }
      return C;
    }
  }
  /** Multidimensional Arrays */
   int[][] A = new int[3][];
   A[0] = new int[] {2, 3, 4, 5};
}
/** LEC#7 Object-Based Programming
 *  class variables and methods */
public class Account {
  private static int funds = 0;
  /** Instance method behaves sort of like a static method with hidden argument.*/
  public int deposit(int amount) {
    balance += amount; funds += amount;
    return balance;
  }
  /** static method */
  /** false! not allowed to declare 'this' parameter, use final
    static int deposit(final Account this, int amount) {
    this.balance += amount; funds += amount;
    return this.balance;
  }*/
  public static int funds() {
    return funds;
  }
}
/** Instance variables initializations are moved inside all constructors. */
/** LEC#8 Object-Oriented Mechanisms
 *  Overriding toString */
 public String toString() {
   StringBuffer b = new StringBuffer();
   b.append("[")
   for (IntList L = this; L != null; L = L.tail) {
     b.append(" " + L.head)
   }
   b.append("]")
   return b.toString();
 }
/** Extending a class */
class B extends A {...}
/** If f(...) is an instance method, then the call x.f(...)
 *  calls whatever overriding of f applies to the dynamic type of x,
 *  regardless of the static type of x.
 *  Fields hide inherited fields of same name;
 *  static methods hide methods of the same signature.*/
/** LEC#9 Interfaces and Abstract Classes
 *  Instance method can be abstract:
 * No body given; must be supplied in subtypes.*/
 public abstract class Drawable {
   public abstract void scale(double size);
   public abstract void draw();
 }
 void drawAll(Drawable[] thingsToDraw) {
   for (Drawable thing : thingsToDraw) {
     thing.draw();
   }
 }
 /** Concrete Subclasses*/
 public class Rectangle extends Drawable {
   public Rectangle(double w, double h) {this.w = w; this.h = h;}
   public void scale(double size) {w *= size; h *= size;}
   public void draw() {...}
   private double w,h;
 }
/** Interfaces
 *  an abstract class that contains only abstract methods*/
public interface Drawable {
  void scale(double size);
  void draw();
}
public class Rectangle implements Drawable {...}
