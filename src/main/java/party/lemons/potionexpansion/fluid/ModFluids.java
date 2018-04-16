package party.lemons.potionexpansion.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Sam on 16/04/2018.
 */
public class ModFluids
{
	public static Fluid POTION;

	public static void init()
	{
		POTION = new FluidPotion();

		FluidRegistry.registerFluid(POTION);
	}
}
