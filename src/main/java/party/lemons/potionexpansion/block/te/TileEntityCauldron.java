package party.lemons.potionexpansion.block.te;

import net.minecraft.init.PotionTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import party.lemons.potionexpansion.fluid.ModFluids;

import javax.annotation.Nullable;

/**
 * Created by Sam on 16/04/2018.
 */
public class TileEntityCauldron extends TileEntity
{
	FluidTank tank = new FluidTankCauldron(this,2000);

	@Override
	public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable net.minecraft.util.EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return true;

		return super.hasCapability(capability, facing);
	}

	@Override
	@Nullable
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) tank;

			return super.getCapability(capability, facing);
	}

	public FluidTank getTank()
	{
		return tank;
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound tags = super.getUpdateTag();
		tags.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		return tags;
	}

	public void handleUpdateTag(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		tank.readFromNBT((NBTTagCompound) tag.getTag("tank"));
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		return super.writeToNBT(compound);
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		tank.readFromNBT(compound.getCompoundTag("tank"));
		super.readFromNBT(compound);
	}
}
