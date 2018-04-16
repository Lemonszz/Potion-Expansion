package party.lemons.potionexpansion.block;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockFire;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.potionexpansion.PotionExpansion;
import party.lemons.potionexpansion.block.te.TileEntityCauldron;
import party.lemons.potionexpansion.block.te.render.CauldronLiquidRender;
import party.lemons.potionexpansion.fluid.ModFluids;
import party.lemons.potionexpansion.handler.PotionHandler;
import party.lemons.potionexpansion.handler.client.RenderUtil;
import party.lemons.potionexpansion.misc.IModel;
import party.lemons.potionexpansion.misc.MiscUtil;
import party.lemons.potionexpansion.particle.ParticleGoodBubble;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;

/**
 * Created by Sam on 16/04/2018.
 */
public class BlockBetterCauldron extends BlockCauldron implements IModel
{
	public static final PropertyBool BOIL = PropertyBool.create("boil");

	public BlockBetterCauldron()
	{
		super();

		this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(0)).withProperty(BOIL, false));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(player.isSneaking())
			return false;

		if(!FluidUtil.interactWithFluidHandler(player, hand, world, pos, side) && side == EnumFacing.UP)
		{
			return mixPotion(player.getHeldItem(hand), world, pos, state, world.rand);
		}
		else
		{
			return true;
		}
	}

	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(entityIn instanceof EntityItem && state.getValue(BOIL))
		{
			EntityItem itemEntity = (EntityItem) entityIn;
			ItemStack stack = itemEntity.getItem();
			Random rand = worldIn.rand;

			if(stack.isEmpty())
				return;

			mixPotion(stack, worldIn, pos, state, rand);

			itemEntity.motionY = -1.5F;
			itemEntity.motionX = (-1 + (rand.nextFloat() * 2)) / 2;
			itemEntity.motionZ = (-1 + (rand.nextFloat() * 2)) / 2;
		}
	}

	public boolean mixPotion(ItemStack toMix, World world, BlockPos pos, IBlockState state, Random rand)
	{
		TileEntityCauldron te = (TileEntityCauldron) world.getTileEntity(pos);
		PotionType type = te.getPotionType();

		if(PotionHandler.canMixPotion(type, toMix))
		{
			type = PotionHandler.mixPotion(type, toMix);
			toMix.shrink(1);
			te.setPotionType(type);
			world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1F, rand.nextFloat());

			for(int i = 0 ; i < 5; i++)
				PotionExpansion.proxy.spawnCauldronSpellParticle(pos, te);

			return true;
		}
		else
		{
			world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.BLOCKS, 0.25F,1F);

			return false;
		}
	}


	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if(pos.down().equals(fromPos))
		{
			checkFire(state, worldIn, pos, fromPos);
		}
	}

	public void checkFire(IBlockState state, World world, BlockPos pos, BlockPos checkPos)
	{
		IBlockState checkState = world.getBlockState(checkPos);
		Block block = checkState.getBlock();

		if(block instanceof BlockFire)
		{
			world.setBlockState(pos, state.withProperty(BOIL, true));
		}
		else
		{
			world.setBlockState(pos, state.withProperty(BOIL, false));
		}
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(LEVEL, 0).withProperty(BOIL, MiscUtil.intToBool(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return MiscUtil.boolToInt(state.getValue(BOIL));
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {LEVEL, BOIL});
	}

	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(stateIn.getValue(BOIL))
		{
			TileEntityCauldron te = (TileEntityCauldron) worldIn.getTileEntity(pos);
			if(te.getTank().getFluid() != null)
			{
				double dX = 0.4F + pos.getX() + (rand.nextFloat() - 0.4F);
				double dZ = 0.4F + pos.getZ() + (rand.nextFloat() - 0.4F);
				double dY = 0.4F + pos.getY() + 0.4F;

				ParticleGoodBubble p = new ParticleGoodBubble(worldIn, dX, dY, dZ, 0, CauldronLiquidRender.getYPos(te) / 2, 0);
				int color = te.getTank().getFluid().getFluid().getColor(te.getTank().getFluid());
				int r = RenderUtil.red(color);
				int g = RenderUtil.green(color);
				int b = RenderUtil.blue(color);

				p.setRBGColorF(r / 255F,g / 255F,b / 255F);
				Minecraft.getMinecraft().effectRenderer.addEffect(p);
			}
		}
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		checkFire(state, worldIn, pos, pos.down());
	}

	@Nullable
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityCauldron();
	}

	@Override
	public ResourceLocation getModelLocation()
	{
		return new ResourceLocation("minecraft", "cauldron");
	}
}
