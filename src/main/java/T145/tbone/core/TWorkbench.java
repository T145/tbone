package T145.tbone.core;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import T145.tbone.api.config.TConfig;
import T145.tbone.net.TPacketHandler;
import T145.tbone.net.client.CacheLastRecipe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import shadows.fastbench.block.BlockFastBench;
import shadows.fastbench.book.DedRecipeBook;
import shadows.fastbench.gui.ClientContainerFastBench;
import shadows.fastbench.gui.ContainerFastBench;
import shadows.fastbench.gui.GuiFastBench;
import shadows.fastbench.proxy.IBenchProxy;

@Mod(modid = TWorkbench.ID, name = TWorkbench.NAME, version = TBone.VERSION, dependencies = "required-after:tbone")
@EventBusSubscriber
public class TWorkbench implements IGuiHandler {

	public static final String ID = "tworkbench";
	public static final String NAME = "TWorkbench";
	public static final Logger LOG = LogManager.getLogger(ID);

	public static final TPacketHandler NETWORK = new TPacketHandler(ID) {

		@Override
		public void registerMessages() {
			this.registerMessage(CacheLastRecipe.class, Side.CLIENT);
		}
	};

	@Instance
	public static TWorkbench instance;

	@SidedProxy(serverSide = "shadows.fastbench.proxy.BenchServerProxy", clientSide = "shadows.fastbench.proxy.BenchClientProxy")
	public static IBenchProxy proxy;

	public static final DedRecipeBook SERVER_BOOK = new DedRecipeBook();

	@Override
	public ContainerFastBench getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFastBench(player, world, x, y, z);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiFastBench getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GuiFastBench(player.inventory, world, new BlockPos(x, y, z));
	}

	public static void temaWtf() {
		try {
			Class<?> c = Class.forName("com.rwtema.extrautils2.items.ItemUnstableIngots");
			Collection<Class<?>> classes = (Collection<Class<?>>) c.getDeclaredField("ALLOWED_CLASSES").get(null);
			classes.add(ClientContainerFastBench.class);
			classes.add(ContainerFastBench.class);
		} catch (Exception noh) {
			LOG.catching(noh);
		}
	}

	@EventHandler
	public void tworkbench$preInit(final FMLPreInitializationEvent event) {
		NETWORK.registerMessages();

		NBTTagCompound t = new NBTTagCompound();
		t.setString("ContainerClass", "shadows.fastbench.gui.ContainerFastBench");
		t.setString("AlignToGrid", "west");
		FMLInterModComms.sendMessage("craftingtweaks", "RegisterProvider", t);

		if (TConfig.removeRecipeBook) {
			proxy.registerButtonRemover();
		}

		if (Loader.isModLoaded("extrautils2")) {
			temaWtf();
		}
	}

	@EventHandler
	public void tworkbench$init(final FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, this);
		OreDictionary.registerOre("workbench", Blocks.CRAFTING_TABLE);
		OreDictionary.registerOre("craftingTableWood", Blocks.CRAFTING_TABLE);
	}

	@EventHandler
	public void tworkbench$serverStart(final FMLServerAboutToStartEvent event) {
		if (TConfig.removeRecipeBook) {
			proxy.replacePlayerList(event.getServer());
		}
	}

	@SubscribeEvent
	public static void tworkbench$registerBlocks(final Register<Block> event) {
		event.getRegistry().register(new BlockFastBench().setRegistryName(new ResourceLocation("crafting_table")));
	}

	@SubscribeEvent
	public static void tworkbench$registerItems(final Register<Item> event) {
		ForgeRegistries.ITEMS.register(new ItemBlock(Blocks.CRAFTING_TABLE) {

			@Override
			public String getCreatorModId(ItemStack stack) {
				return ID;
			}

		}.setRegistryName(Blocks.CRAFTING_TABLE.getRegistryName()));
	}

	@SubscribeEvent
	public static void tworkbench$removeBooks(final EntityJoinWorldEvent event) {
		if (TConfig.removeRecipeBook) {
			proxy.deleteBook(event.getEntity());
		}
	}
}
