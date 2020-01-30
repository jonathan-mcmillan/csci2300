
public class homework1_1
{
	public static void main(String arg[])
	{
		double num = 1000.0;
		double pi = 0.0;
		for(int i = 0; i < num; i++)
		{
			double dub = (double)i;
			pi = pi + (Math.pow(-1.0, dub)/(2*dub + 1));
		}
		pi = 4*pi;
		System.out.println(pi);
	}
}
