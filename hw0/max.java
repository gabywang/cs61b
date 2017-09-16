public class max{
  public static void main(String[] args){
    int array[] = new int[] {1, 2, 3, 4, 5};
    int max = getMax(array);
    System.out.println("Maximum Value is: "+max);
  }
  public static int getMax(int[] inputArray){
    int maxValue = inputArray[0];
    for(int i=1;i<inputArray.length;i++){
      if(inputArray[i]>maxValue){
        maxValue = inputArray[i];
      }
    }return maxValue;
  }
}
