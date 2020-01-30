
public class SumNums {

	public SumNums(int low, int high) // True is fullsum, false if skipsum
	{
		System.out.println("The fullsum of " +low +" to " +high +" is: "  +Fullsum(low, high));
		System.out.println("The skipsum of " +low +" to " +high +" is: "  +Skipsum(low, high));
	}
	
	private int Fullsum(int low, int high)
	{
		int sum = 0;
		
		for(int i = 150; i <= 450; i++)
			sum = sum + i;
		
		return sum;
	}
	private int Skipsum(int low, int high)
	{
		int sum = 0;
		int itt = 1;
		
		for(int i = 150; i <= 450; i++)
		{
			if(itt%2 != 0)
				sum = sum + i;
			itt++;
		}
		
		return sum;
	}
}
