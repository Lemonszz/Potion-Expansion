package party.lemons.potionexpansion.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.potionexpansion.block.te.TileEntityCauldron;
import party.lemons.potionexpansion.block.te.render.CauldronLiquidRender;

/**
 * Created by Sam on 16/04/2018.
 */
public class ClientProxy implements IProxy
{
	@SideOnly(Side.CLIENT)
	public void initTESR()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCauldron.class, new CauldronLiquidRender());
	}
}
