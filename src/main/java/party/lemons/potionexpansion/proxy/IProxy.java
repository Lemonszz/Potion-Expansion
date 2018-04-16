package party.lemons.potionexpansion.proxy;

import net.minecraft.util.math.BlockPos;
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
}
