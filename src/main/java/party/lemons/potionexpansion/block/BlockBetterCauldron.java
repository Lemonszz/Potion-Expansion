package party.lemons.potionexpansion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
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
import party.lemons.potionexpansion.block.te.TileEntityCauldron;
import party.lemons.potionexpansion.block.te.render.CauldronLiquidRender;
import party.lemons.potionexpansion.fluid.ModFluids;
import party.lemons.potionexpansion.handler.PotionHandler;
import party.lemons.potionexpansion.misc.IModel;
import party.lemons.potionexpansion.misc.MiscUtil;
import party.lemons.potionexpansion.particle.ParticleGoodBubble;

import javax.annotation.Nullable;
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
		return !player.isSneaking() && FluidUtil.interactWithFluidHandler(player, hand, world, pos, side);
	}

	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(entityIn instanceof EntityItem && state.getValue(BOIL))
		{
			EntityItem itemEntity = (EntityItem) entityIn;
			ItemStack stack = itemEntity.getItem();

			if(stack.isEmpty())
				return;

			TileEntityCauldron te = (TileEntityCauldron) worldIn.getTileEntity(pos);
			PotionType type = te.getPotionType();

			if(PotionHandler.canMixPotion(type, stack))
			{
				type = PotionHandler.mixPotion(type, stack);
				stack.shrink(1);
				te.setPotionType(type);
				worldIn.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1F, worldIn.rand.nextFloat());
			}
			else
			{
				worldIn.playSound(null, pos, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.BLOCKS, 0.25F,1F);
			}
			itemEntity.motionY = -1.5F;
			itemEntity.motionX = (-1 + (entityIn.world.rand.nextFloat() * 2)) / 2;
			itemEntity.motionZ = (-1 + (entityIn.world.rand.nextFloat() * 2)) / 2;
		}
	}

	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if(pos.down().equals(fromPos))
		{
			if(blockIn.getLightValue(worldIn.getBlockState(fromPos), worldIn, fromPos) > 13)
			{
				worldIn.setBlockState(pos, state.withProperty(BOIL, true));
			}
			else
			{
				worldIn.setBlockState(pos, state.withProperty(BOIL, false));
			}
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

				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleGoodBubble(worldIn, dX, dY, dZ, 0, CauldronLiquidRender.getYPos(te) / 2, 0));
			}
		}
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
