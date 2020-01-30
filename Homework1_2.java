
public class Homework1_2 
{
	public static void main(String[] args) 
	{
		Homework1_2 hw = new Homework1_2();
		
		
		int sum = 0;
		int sum2 = 0;
		for(int i = 1; i <= 50; i++)
			sum = sum + i;
		
		for(int i = 150; i <= 450; i++)
			sum2 = sum2 + i;
		
		System.out.println("The sum of 1 to 50: " +sum);
		System.out.println("The sum of 150 to 450: " +sum2);
		
		System.out.println("The sum of 25 to 75: " +hw.SumTester(25,75));
		System.out.println("The sum of 125 to 375: " +hw.SumTester(125, 375));
		
		new SumNums(40, 160);
		new SumNums(185, 315);
	}

	public int SumTester(int low, int high)
	{
		int sum = 0;
		
		for(int i = low; i <= high; i++)
			sum = sum + i;

		return sum;
	}
}
