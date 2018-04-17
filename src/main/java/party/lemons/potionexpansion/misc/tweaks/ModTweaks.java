package party.lemons.potionexpansion.misc.tweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.potionexpansion.config.ModConstants;

/**
 * Created by Sam on 17/04/2018.
 */

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModTweaks
{
	public static void init()
	{
		Items.POTIONITEM.setMaxStackSize(8);
		Items.LINGERING_POTION.setMaxStackSize(8);
		Items.SPLASH_POTION.setMaxStackSize(8);
	}

	@SubscribeEvent
	public static void onItemUse(PlayerInteractEvent.RightClickItem event)
	{
		if(!event.getItemStack().isEmpty())
		{
			if(event.getItemStack().getItem() == Items.SPLASH_POTION || event.getItemStack().getItem() == Items.LINGERING_POTION)
			{
				event.getEntityPlayer().getCooldownTracker().setCooldown(event.getItemStack().getItem(), 35);
			}
		}
	}

	@SubscribeEvent
	public static void onProjectileThrow(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof EntityPotion)
		{
			EntityPotion potion = (EntityPotion) event.getEntity();
			if(potion.getThrower() instanceof EntityPlayer)
			{
				if(!potion.getThrower().isSneaking())
				{
					potion.motionX *= 2F;
					potion.motionY *= 2F;
					potion.motionZ *= 2F;
				}
			}

		}
	}
}
