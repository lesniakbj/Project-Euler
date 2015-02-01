package ProjectEulerRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class EulerUtils 
{
	public static List<Integer> primeFactors(int n)
	{
		List<Integer> retList = new ArrayList<>(0);
		
		for(int i = 2; i <= n; ++i)
		{
			while(n % i == 0)
			{
				n /= i;
				retList.add(i);
			}
		}
		
		return retList;
	}
}
