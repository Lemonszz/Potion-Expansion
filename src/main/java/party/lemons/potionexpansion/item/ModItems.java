package party.lemons.potionexpansion.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.potionexpansion.config.ModConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 16/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID)
@GameRegistry.ObjectHolder(ModConstants.MODID)
public class ModItems
{
	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event)
	{

	}

	public static void registerItem(IForgeRegistry<Item> registry, Item item, String name)
	{
		item.setUnlocalizedName(ModConstants.MODID + "." + name);
		item.setRegistryName(ModConstants.MODID, name);

		itemList.add(item);
		registry.register(item);
	}

	public static List<Item> itemList = new ArrayList<>();
}
