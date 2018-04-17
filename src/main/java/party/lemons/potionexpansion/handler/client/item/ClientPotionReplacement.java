package party.lemons.potionexpansion.handler.client.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.potionexpansion.config.ModConstants;

/**
 * Created by Sam on 17/04/2018.
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ModConstants.MODID)
public class ClientPotionReplacement
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> register)
	{
		/*
		 *This feels like a dumb idea but i'm doing it anyway #yolo #dabonthehaters
		 */
		register.getRegistry().registerAll(
				new ItemPotionClient().setRegistryName("minecraft", "potion"),
				new ItemSplashPotionClient().setRegistryName("minecraft", "splash_potion"),
				new ItemLingeringPotionClient().setRegistryName("minecraft", "lingering_potion")
		);
	}
}
