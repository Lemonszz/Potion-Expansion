package party.lemons.potionexpansion.misc;

import net.minecraft.util.ResourceLocation;

/**
 * Created by Sam on 16/04/2018.
 */
public interface IModel
{
	default boolean hasModel(){
		return true;
	}

	ResourceLocation getModelLocation();
}
