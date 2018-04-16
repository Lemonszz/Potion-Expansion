package party.lemons.potionexpansion.handler;

import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

/**
 * Created by Sam on 16/04/2018.
 */
public class ModPotionHelper
{
	public static int getColorFromType(PotionType type)
	{
		return type == PotionTypes.EMPTY ? 16253176 : PotionUtils.getPotionColorFromEffectList(type.getEffects());
	}
}
