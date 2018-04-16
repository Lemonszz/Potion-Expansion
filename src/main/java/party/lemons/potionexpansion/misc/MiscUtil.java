package party.lemons.potionexpansion.misc;

/**
 * Created by Sam on 16/04/2018.
 */
public class MiscUtil
{
	public static boolean intToBool(int i)
	{
		if(i > 0)
			return true;

		return false;
	}

	public static int boolToInt(boolean b)
	{
		return b ? 1 : 0;
	}
}
