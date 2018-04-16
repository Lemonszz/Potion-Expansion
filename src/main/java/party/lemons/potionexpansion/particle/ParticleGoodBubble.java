package party.lemons.potionexpansion.particle;

import net.minecraft.client.particle.ParticleBubble;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.potionexpansion.block.BlockBetterCauldron;
import party.lemons.potionexpansion.block.te.TileEntityCauldron;
import party.lemons.potionexpansion.block.te.render.CauldronLiquidRender;

/**
 * Created by Sam on 16/04/2018.
 */
public class ParticleGoodBubble extends ParticleBubble
{

	public ParticleGoodBubble(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.motionZ /= 4;
		this.motionX /= 4;
	}

	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY += 0.002D;
		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.8500000238418579D;
		this.motionY *= 0.8500000238418579D;
		this.motionZ *= 0.8500000238418579D;


		if (this.particleMaxAge-- <= 0)
		{
			this.setExpired();
		}
	}
}
