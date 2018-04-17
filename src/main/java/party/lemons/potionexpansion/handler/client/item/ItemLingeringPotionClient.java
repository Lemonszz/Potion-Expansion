package party.lemons.potionexpansion.handler.client.item;

import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Sam on 17/04/2018.
 */
public class ItemLingeringPotionClient extends ItemLingeringPotion
{
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return stack.isItemEnchanted();
	}
}
