package party.lemons.potionexpansion.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.potionexpansion.block.te.TileEntityCauldron;
import party.lemons.potionexpansion.config.ModConstants;
import party.lemons.potionexpansion.item.ModItems;
import party.lemons.potionexpansion.misc.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 16/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
@GameRegistry.ObjectHolder(ModConstants.MODID)
public class ModBlocks
{
	@GameRegistry.ObjectHolder("minecraft:cauldron")
	public static final Block CAULDRON = Blocks.CAULDRON;

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> r = event.getRegistry();

		registerBlock(r, new BlockBetterCauldron(), "cauldron", "minecraft", false);

		GameRegistry.registerTileEntity(TileEntityCauldron.class, ModConstants.MODID + "_caudron");
	}

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{
		blockList.stream().filter(b-> b instanceof IModel).forEach(b -> registerItemBlock(event.getRegistry(), b));
	}

	public static void registerItemBlock(IForgeRegistry<Item> registry, Block block)
	{
		ItemBlock ib = new ItemBlock(block);
		ib.setRegistryName(block.getRegistryName());

		ModItems.itemList.add(ib);
		registry.register(ib);
	}

	public static void registerBlock(IForgeRegistry<Block> registry, Block block, String name)
	{
		registerBlock(registry, block, name, ModConstants.MODID, true);
	}

	public static void registerBlock(IForgeRegistry<Block> registry, Block block, String name, String domain, boolean addDomainToUnloc)
	{
		String unloc = addDomainToUnloc ? domain + "." : "";
		block.setUnlocalizedName(unloc + name);
		block.setRegistryName(domain, name);

		blockList.add(block);
		registry.register(block);
	}

	public static List<Block> blockList = new ArrayList<>();
}
