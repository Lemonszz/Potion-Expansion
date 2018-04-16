package party.lemons.potionexpansion.handler;

import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Sam on 16/04/2018.
 */
public class PotionHandler
{

	public static boolean canMixPotion(PotionType type, ItemStack stack)
	{
		for(PotionHelper.MixPredicate<PotionType> mix : getConversionList())
		{
			PotionType input = getInput(mix);
			Ingredient ingred = getIngredient(mix);

			if(input == type && ingred.apply(stack))
			{
				return true;
			}
		}

		return false;
	}

	@Nullable
	public static PotionType mixPotion(PotionType type, ItemStack stack)
	{
		for(PotionHelper.MixPredicate<PotionType> mix : getConversionList())
		{
			PotionType input = getInput(mix);
			Ingredient ingred = getIngredient(mix);
			PotionType out = getOutput(mix);

			if(input == type && ingred.apply(stack))
			{
				return out;
			}
		}

		return null;
	}

	public static List<PotionHelper.MixPredicate<PotionType>> conversionList = null;
	private static List<PotionHelper.MixPredicate<PotionType>> getConversionList()
	{
		if(conversionList == null)
		{
			fieldPotionConversions = ReflectionHelper.findField(PotionHelper.class, "POTION_TYPE_CONVERSIONS","field_185213_a");
			fieldPotionConversions.setAccessible(true);
		}
		try
		{
			return (List<PotionHelper.MixPredicate<PotionType>>) fieldPotionConversions.get(PotionHelper.class);
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private static PotionType getInput(PotionHelper.MixPredicate<PotionType> type)
	{
		if(fieldMixIngredient == null)
		{
			getMixFields();
		}

		try
		{
			return (PotionType) fieldMixInput.get(type);
		}catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private static PotionType getOutput(PotionHelper.MixPredicate<PotionType> type)
	{
		if(fieldMixOutput == null)
		{
			getMixFields();
		}

		try
		{
			return (PotionType) fieldMixOutput.get(type);
		}catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private static Ingredient getIngredient(PotionHelper.MixPredicate<PotionType> type)
	{
		if(fieldMixIngredient == null)
		{
			getMixFields();
		}

		try
		{
			return (Ingredient) fieldMixIngredient.get(type);
		}catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static void getMixFields()
	{
		fieldMixInput = ReflectionHelper.findField(PotionHelper.MixPredicate.class, "input", "field_185198_a");
		fieldMixIngredient = ReflectionHelper.findField(PotionHelper.MixPredicate.class, "reagent", "field_185199_b");
		fieldMixOutput = ReflectionHelper.findField(PotionHelper.MixPredicate.class, "output", "field_185200_c");

		fieldMixOutput.setAccessible(true);
		fieldMixIngredient.setAccessible(true);
		fieldMixInput.setAccessible(true);
	}


	private static Field fieldPotionConversions = null;
	private static Field fieldMixInput = null;
	private static Field fieldMixIngredient = null;
	private static Field fieldMixOutput = null;
}
