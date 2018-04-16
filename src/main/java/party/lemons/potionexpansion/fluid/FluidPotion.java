package party.lemons.potionexpansion.fluid;

import net.minecraft.init.PotionTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import party.lemons.potionexpansion.config.ModConstants;

import javax.annotation.Nullable;

/**
 * Created by Sam on 16/04/2018.
 */
public class FluidPotion extends Fluid
{
	public static final ResourceLocation STILL_LOC = new ResourceLocation(ModConstants.MODID, "fluid/potion_still");
	public static final ResourceLocation FLOW_LOC = new ResourceLocation(ModConstants.MODID, "fluid/potion_flow");

	public FluidPotion()
	{
		super("potion", STILL_LOC, FLOW_LOC);
	}

	@Override
	public int getColor() {
		return DEFAULT_BLUE;
	}

	@Override
	public int getColor(FluidStack stack) {

		return getPotionColor(stack);
	}

	public static int getPotionColor(FluidStack stack) {

		if (stack.tag != null && stack.tag.hasKey("CustomPotionColor", 99))
		{
			return stack.tag.getInteger("CustomPotionColor");
		} else
		{
			return getPotionTypeFromNBT(stack.tag) == PotionTypes.EMPTY ? DEFAULT_BLUE : 0xFF000000 | PotionUtils.getPotionColorFromEffectList(PotionUtils.getEffectsFromTag(stack.tag));
		}
	}

	public static PotionType getPotionTypeFromNBT(@Nullable NBTTagCompound tag) {

		return tag == null || !tag.hasKey("Potion") ? PotionTypes.EMPTY : PotionType.getPotionTypeForName(tag.getString("Potion"));
	}

	public static final int DEFAULT_BLUE = 0xFF000000 | 0x3148F4;
}
