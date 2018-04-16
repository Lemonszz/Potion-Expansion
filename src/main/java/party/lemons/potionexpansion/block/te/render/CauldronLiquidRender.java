package party.lemons.potionexpansion.block.te.render;

import net.minecraft.client.Minecraft;
import party.lemons.potionexpansion.block.te.TileEntityCauldron;

/**
 * Created by Sam on 16/04/2018.
 */
public class CauldronLiquidRender extends LiquidRenderBase<TileEntityCauldron>
{
	private static final float THICC = 0.1F;

	@Override
	public double getY(TileEntityCauldron te, double x, double y, double z)
	{

		return getYPos(te);
	}

	public static double getYPos(TileEntityCauldron te)
	{
		float scale = Math.min(1.0F, 0.2F + (1.0f - THICC / 2 - THICC) * te.getTank().getFluidAmount() / te.getTank().getCapacity());
		float shake = 0;
		if(scale != 1.0F)
		{
			Long t = Minecraft.getMinecraft().world.getTotalWorldTime();
			float d = t;
			shake = (float) Math.sin(d / 10) / 100;

			scale += shake;
		}

		return scale;
	}
}