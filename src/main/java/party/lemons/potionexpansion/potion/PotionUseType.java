package party.lemons.potionexpansion.potion;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by Sam on 17/04/2018.
 */
public enum PotionUseType
{
	NORMAL(Items.POTIONITEM),
	SPLASH(Items.SPLASH_POTION),
	LINGERING(Items.LINGERING_POTION);

	private final Item containerItem;
	PotionUseType(Item item)
	{
		this.containerItem = item;
	}

	public Item getContainerItem()
	{
		return containerItem;
	}
}