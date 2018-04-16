package party.lemons.potionexpansion.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSpell;
import net.minecraft.client.particle.ParticleSplash;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.potionexpansion.block.te.TileEntityCauldron;
import party.lemons.potionexpansion.block.te.render.CauldronLiquidRender;
import party.lemons.potionexpansion.particle.ParticleGoodSpell;

import java.util.Random;

/**
 * Created by Sam on 16/04/2018.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy
{
	public void initTESR()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCauldron.class, new CauldronLiquidRender());
	}

	public void spawnCauldronSpellParticle(BlockPos pos, TileEntityCauldron te)
	{
		Random rand = Minecraft.getMinecraft().world.rand;
		Particle p = new ParticleGoodSpell(Minecraft.getMinecraft().world, pos.getX() + rand.nextFloat(),
				pos.getY() + 1,
				pos.getZ() + rand.nextFloat(),
				rand.nextFloat() / 2, - rand.nextFloat() / 2, rand.nextFloat() / 2);

		if(te.getTank().getFluid() != null)
		{
			int colour = 0xFF000000 | te.getTank().getFluid().getFluid().getColor(te.getTank().getFluid());
			colorParticle(p, colour);
		}

		Minecraft.getMinecraft().effectRenderer.addEffect(p);
	}

	public void spawnSplashParticle(World worldIn, double posX, double posY, double posZ, TileEntityCauldron te)
	{
		for(int i = 0; i < 5; i++)
		{
			Particle p = new ParticleGoodSplash(worldIn, posX, posY, posZ, 0, 0, 0);
			int colour = 0xFF000000 | te.getTank().getFluid().getFluid().getColor(te.getTank().getFluid());
			colorParticle(p, colour);
			Minecraft.getMinecraft().effectRenderer.addEffect(p);
		}
	}

	private void colorParticle(Particle p, int colour)
	{
		int r = (colour >> 16) & 0xFF;
		int g = (colour >> 8) & 0xFF;
		int b = colour & 0xFF;
		p.setRBGColorF(r / 255F, g / 255F, b / 255F);
	}
}
