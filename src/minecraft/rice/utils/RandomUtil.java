package rice.utils;

import java.util.Random;

public class RandomUtil implements MCHook
{
	private static Random RANDOM = new Random();
	
	public static int randomInt(int min, int max) 
	{
		if(max == min)
		{
			return min;
		}
		return RANDOM.nextInt(max - min) + min;
	}
}