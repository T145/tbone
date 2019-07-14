package shadows.fastfurnace;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shadows.fastfurnace.block.BlockFastFurnace;
import shadows.fastfurnace.block.TileFastFurnace;

@Mod(modid = FastFurnace.MODID, name = FastFurnace.MODNAME, version = FastFurnace.VERSION)
@EventBusSubscriber
public class FastFurnace {

	public static final String MODID = "fastfurnace";
	public static final String MODNAME = "FastFurnace";
	public static final String VERSION = "1.3.1";

	public static final Logger LOG = LogManager.getLogger(MODID);

	public static boolean useStrictMatching = true;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		Configuration c = new Configuration(event.getSuggestedConfigurationFile());
		useStrictMatching = c.getBoolean("Strict Matching", "general", true, "If the furnace uses nbt-sensitive output matching.");

		if (c.hasChanged()) {
			c.save();
		}
	}

	@SubscribeEvent
	public static void blockBois(final Register<Block> event) {
		if (!Loader.isModLoaded("betterwithmods")) {
			event.getRegistry().registerAll(new BlockFastFurnace(false).setRegistryName("minecraft", "furnace"), new BlockFastFurnace(true).setRegistryName("minecraft", "lit_furnace"));
			GameRegistry.registerTileEntity(TileFastFurnace.class, new ResourceLocation("minecraft", "furnace"));
		}
	}

	@SubscribeEvent
	public static void items(final Register<Item> event) {
		if (!Loader.isModLoaded("betterwithmods")) {
			event.getRegistry().register(new ItemBlock(Blocks.FURNACE) {

				@Override
				public String getCreatorModId(ItemStack stack) {
					return MODID;
				}

			}.setRegistryName(Blocks.FURNACE.getRegistryName()));
		}
	}
}
