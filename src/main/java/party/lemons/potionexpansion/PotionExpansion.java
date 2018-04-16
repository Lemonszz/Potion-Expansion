package party.lemons.potionexpansion;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import party.lemons.potionexpansion.config.ModConstants;
import party.lemons.potionexpansion.fluid.ModFluids;
import party.lemons.potionexpansion.proxy.IProxy;

/**
 * Created by Sam on 16/04/2018.
 */
@Mod(modid = ModConstants.MODID, name = ModConstants.NAME, version = ModConstants.VERSION)
public class PotionExpansion
{
	@SidedProxy(clientSide = "party.lemons.potionexpansion.proxy.ClientProxy", serverSide = "party.lemons.potionexpansion.proxy.ServerProxy")
	public static IProxy proxy;

	public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(ModConstants.MODID);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.initTESR();
		ModFluids.init();
	}
}
