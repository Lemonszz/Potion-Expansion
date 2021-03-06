package party.lemons.potionexpansion.block.te;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.PotionTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import party.lemons.potionexpansion.fluid.ModFluids;
import party.lemons.potionexpansion.potion.PotionUseType;

import javax.annotation.Nullable;

/**
 * Created by Sam on 16/04/2018.
 */
public class TileEntityCauldron extends TileEntity
{
	FluidTank tank = new FluidTankCauldron(this,2000);
	PotionType type = PotionTypes.WATER;
	PotionUseType useType = PotionUseType.NORMAL;


	@Override
	public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable net.minecraft.util.EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return true;

		return super.hasCapability(capability, facing);
	}

	public void markDirty()
	{
		ResourceLocation resourcelocation = PotionType.REGISTRY.getNameForObject(type);
		if(tank.getFluid() != null && tank.getFluid().getFluid() == ModFluids.POTION)
		{
			if(tank.getFluid().tag == null)
			{
				tank.getFluid().tag = new NBTTagCompound();
			}

			tank.getFluid().tag.setString("Potion", resourcelocation.toString());
		}

		if(tank.getFluid() == null || tank.getFluidAmount() <= 0)
		{
			this.type = PotionTypes.WATER;
			this.useType = PotionUseType.NORMAL;
		}
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

	public PotionType getPotionType()
	{
		return type;
	}

	public PotionUseType getUseType()
	{
		return useType;
	}

	public void setUseType(PotionUseType type)
	{
		this.useType = type;
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound tags = super.getUpdateTag();
		tags.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		tags.setString("Potion", type.getRegistryName().toString());
		tags.setInteger("type", useType.ordinal());
		return tags;
	}

	public void handleUpdateTag(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		tank.readFromNBT((NBTTagCompound) tag.getTag("tank"));
		type = PotionUtils.getPotionTypeFromNBT(tag);
		useType = PotionUseType.values()[tag.getInteger("type")];
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		compound.setString("Potion", type.getRegistryName().toString());
		compound.setInteger("type", useType.ordinal());

		return super.writeToNBT(compound);
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		tank.readFromNBT(compound.getCompoundTag("tank"));
		type = PotionUtils.getPotionTypeFromNBT(compound);
		useType = PotionUseType.values()[compound.getInteger("type")];
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		if(world.isAirBlock(pos) || world.getTileEntity(pos) == null || !(world.getTileEntity(pos) instanceof TileEntityCauldron))
			return true;

		return false;
	}

	public void setPotionType(PotionType potionType)
	{
		if(tank.getFluid() != null && tank.getFluid().getFluid() == FluidRegistry.WATER)
			tank.setFluid(new FluidStack(ModFluids.POTION, tank.getFluidAmount()));

		this.type = potionType;

		this.markDirty();
	}
}
