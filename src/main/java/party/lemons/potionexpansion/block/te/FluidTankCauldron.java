package party.lemons.potionexpansion.block.te;

import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import party.lemons.potionexpansion.fluid.ModFluids;

import javax.annotation.Nullable;

/**
 * Created by Sam on 16/04/2018.
 */
public class FluidTankCauldron extends FluidTank
{
	public FluidTankCauldron(TileEntityCauldron tile, int capacity)
	{
		super(capacity);
		this.setTileEntity(tile);
	}

	public FluidTankCauldron(TileEntityCauldron tile, @Nullable FluidStack fluidStack, int capacity)
	{
		super(fluidStack, capacity);
		this.setTileEntity(tile);
	}

	public FluidTankCauldron(TileEntityCauldron tile, Fluid fluid, int amount, int capacity)
	{
		super(fluid, amount, capacity);
		this.setTileEntity(tile);
	}

	public boolean canFillFluidType(FluidStack fluid)
	{
		return super.canFillFluidType(fluid) && (fluid.getFluid() == FluidRegistry.WATER || fluid.getFluid() == ModFluids.POTION);
	}

	public boolean canDrainFluidType(@Nullable FluidStack fluid)
	{
		return super.canDrainFluidType(fluid) && (fluid.getFluid() == FluidRegistry.WATER || fluid.getFluid() == ModFluids.POTION);
	}

	protected void onContentsChanged()
	{
		tile.markDirty();
	}
}
