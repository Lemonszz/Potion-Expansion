package party.lemons.potionexpansion.potion;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

/**
 * Created by Sam on 17/04/2018.
 */
public enum PotionUseType
{
	NORMAL(Items.POTIONITEM, null),
	SPLASH(Items.SPLASH_POTION, Items.GUNPOWDER),
	LINGERING(Items.LINGERING_POTION, Items.DRAGON_BREATH);

	private final Item containerItem, reagent;
	PotionUseType(Item item,  @Nullable Item reagent)
	{
		this.containerItem = item;
		this.reagent = reagent;
	}

	public Item getContainerItem()
	{
		return containerItem;
	}

	@Nullable
	public Item getReagent()
	{
		return reagent;
	}
}