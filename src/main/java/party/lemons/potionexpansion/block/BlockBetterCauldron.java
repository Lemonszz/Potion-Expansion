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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import party.lemons.potionexpansion.block.te.TileEntityCauldron;
import party.lemons.potionexpansion.misc.IModel;
import party.lemons.potionexpansion.misc.MiscUtil;

import javax.annotation.Nullable;

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
		return new BlockStateContainer(this, new IProperty[] {BOIL});
	}

	public boolean hasTileEntity(IBlockState state)
	{
		return true;
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
