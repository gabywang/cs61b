public class threesumdistinct{
  public static void main(String[] args){
    int a[] = new int[] {-6,3,10,200};
    System.out.println(threeSum(a));
  }
  public static String threeSum(int[] a){
    for(int f=0;f < a.length;f++){
      for(int g=f+1;g<a.length;g++){
        for(int h=g+1;h<a.length;h++){
          if(a[f]+a[g]+a[h]==0){
            return "true";
          }
        }
      }
    }return "false";
  }
}
