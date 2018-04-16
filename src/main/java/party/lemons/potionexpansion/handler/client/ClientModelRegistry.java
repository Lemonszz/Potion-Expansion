package party.lemons.potionexpansion.handler.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.potionexpansion.block.BlockBetterCauldron;
import party.lemons.potionexpansion.block.ModBlocks;
import party.lemons.potionexpansion.config.ModConstants;
import party.lemons.potionexpansion.fluid.FluidPotion;
import party.lemons.potionexpansion.item.ModItems;
import party.lemons.potionexpansion.misc.IModel;

/**
 * Created by Sam on 16/04/2018.
 */
@Mod.EventBusSubscriber(modid = ModConstants.MODID, value = Side.CLIENT)
public class ClientModelRegistry
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onRegisterModel(ModelRegistryEvent event)
	{
		ModItems.itemList.stream().filter(i -> i instanceof IModel).forEach(i -> registerModel((Item & IModel)i));

		ModelLoader.setCustomStateMapper(ModBlocks.CAULDRON, (new StateMap.Builder()).ignore(BlockBetterCauldron.BOIL).build());
	}

	@SideOnly(Side.CLIENT)
	public static <ModelItem extends Item & IModel> void registerModel(ModelItem item)
	{
		if(item == Items.AIR || !item.hasModel())
			return;

		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getModelLocation(), "inventory"));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onTextureStitch(TextureStitchEvent.Pre event)
	{
		event.getMap().registerSprite(FluidPotion.FLOW_LOC);
		event.getMap().registerSprite(FluidPotion.STILL_LOC);
	}
}
