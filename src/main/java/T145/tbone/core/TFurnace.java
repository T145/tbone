package T145.tbone.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shadows.fastfurnace.block.BlockFastFurnace;
import shadows.fastfurnace.block.TileFastFurnace;

@Mod(modid = TFurnace.ID, name = TFurnace.NAME, version = TBone.VERSION, dependencies = "required-after:tbone", canBeDeactivated = true)
@EventBusSubscriber
public class TFurnace {

	public static final String ID = "tfurnace";
	public static final String NAME = "TFurnace";
	public static final Logger LOG = LogManager.getLogger(ID);

	@SubscribeEvent
	public static void tfurnace$registerBlocks(final Register<Block> event) {
		if (Loader.isModLoaded("betterwithmods")) {
			return;
		}

		event.getRegistry().registerAll(new BlockFastFurnace(false).setRegistryName(new ResourceLocation("furnace")), new BlockFastFurnace(true).setRegistryName(new ResourceLocation("lit_furnace")));
		GameRegistry.registerTileEntity(TileFastFurnace.class, Blocks.FURNACE.getRegistryName());
	}

	@SubscribeEvent
	public static void tfurnace$registerItems(final Register<Item> event) {
		if (Loader.isModLoaded("betterwithmods")) {
			return;
		}

		event.getRegistry().register(new ItemBlock(Blocks.FURNACE) {

			@Override
			public String getCreatorModId(ItemStack stack) {
				return ID;
			}

		}.setRegistryName(Blocks.FURNACE.getRegistryName()));
	}
}
