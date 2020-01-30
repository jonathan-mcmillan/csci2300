import java.util.Scanner;
import java.util.*;

public class Homework1_3 {

	public static void main()
	{
		Scanner sc = new Scanner(System.in);
		
		ArrayList<String> arrayList = new ArrayList<String>(1);
		
		while(true)
		{
			System.out.println("Please give me a name");
			String name = sc.nextLine();
			
			if(name == null)
				continue;
			else if(name.equals("quit") || name.equals("done") || name.equals("exit"))
				break;
			else
				arrayList.add(name);
		}
		
		Collections.sort(arrayList);
		
		for(int i = 0; i < arrayList.size(); i++)
		{
			System.out.println(arrayList.get(i) +" ");
		}
		
		sc.close();
	}
}
