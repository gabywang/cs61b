// Adapted from Sierra and Bates, Head First Java

// You need not use all of the lines below, and you may use duplicates.
public class LeapYear{
	public void find(int year){
		if(year%100==0){
			if(year%400==0){
				System.out.println (year + " is a leap year.");
			}else{
				System.out.println (year + " is not a leap year.");
			}
		}else{
			if(year%4==0){
				System.out.println (year + " is a leap year.");
			}else{
				System.out.println (year + " is not a leap year.");
			}
		}
	}
}
