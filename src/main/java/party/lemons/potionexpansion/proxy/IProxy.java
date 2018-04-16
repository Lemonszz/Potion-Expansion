package party.lemons.potionexpansion.proxy;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.potionexpansion.block.te.TileEntityCauldron; /**
 * Created by Sam on 16/04/2018.
 */
public interface IProxy
{
	void initTESR();

	default void spawnCauldronSpellParticle(BlockPos pos, TileEntityCauldron te)
	{
		//nofu
	}

	default void spawnSplashParticle(World worldIn, double posX, double posY, double posZ, TileEntityCauldron te)
	{
		//nofu
	}
}
