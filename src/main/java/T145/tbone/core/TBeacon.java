package T145.tbone.core;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.paulf.rbeacons.server.BeaconNotifier;
import me.paulf.rbeacons.server.block.BlockResponsiveBeacon;
import me.paulf.rbeacons.server.block.BlockResponsiveStainedGlass;
import me.paulf.rbeacons.server.block.BlockResponsiveStainedGlassPane;
import me.paulf.rbeacons.server.block.TileResponsiveBeacon;
import me.paulf.rbeacons.server.event.RegistryAvailableEvent;
import me.paulf.rbeacons.server.level.chunk.BeaconLookup;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = TBeacon.ID, name = TBeacon.NAME, version = TBone.VERSION)
@EventBusSubscriber
public class TBeacon {

	public static final String ID = "tbeacon";
	public static final String NAME = "TBeacon";
	public static final Logger LOG = LogManager.getLogger(ID);

	@EventHandler
	public void tbeacon$preInit(final FMLPreInitializationEvent event) {
		CapabilityManager.INSTANCE.register(BeaconLookup.class, new Capability.IStorage<BeaconLookup>() {

			@Nullable
			@Override
			public NBTBase writeNBT(final Capability<BeaconLookup> capability, final BeaconLookup instance, final EnumFacing side) {
				return null;
			}

			@Override
			public void readNBT(final Capability<BeaconLookup> capability, final BeaconLookup instance, final EnumFacing side, final NBTBase nbt) {}
		}, () -> {
			throw new UnsupportedOperationException();
		});

		MinecraftForge.EVENT_BUS.register(BeaconNotifier.create());
	}

	@EventHandler
	public void tbeacon$onMap(final FMLModIdMappingEvent event) {
		MinecraftForge.EVENT_BUS.post(RegistryAvailableEvent.create(ForgeRegistries.BLOCKS));
	}

	@SubscribeEvent
	public static void tbeacon$registerBlocks(final Register<Block> event) {
		event.getRegistry().registerAll(new BlockResponsiveBeacon(), new BlockResponsiveStainedGlass(), new BlockResponsiveStainedGlassPane());
		GameRegistry.registerTileEntity(TileResponsiveBeacon.class, new ResourceLocation("beacon"));
	}

	@SubscribeEvent
	public static void tbeacon$registerItems(final Register<Item> event) {
		event.getRegistry().register(new ItemBlock(Blocks.BEACON) {

			@Override
			public String getCreatorModId(ItemStack stack) {
				return ID;
			}

		}.setRegistryName(Blocks.BEACON.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void tbeacon$registerModels(final ModelRegistryEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileResponsiveBeacon.class, new TileEntityBeaconRenderer());
	}

	private static Supplier<Capability<BeaconLookup>> accessor = () -> {
		throw new IllegalStateException("Beacon lookup not injected!");
	};

	@CapabilityInject(BeaconLookup.class)
	static void inject(final Capability<BeaconLookup> capability) {
		accessor = () -> capability;
	}

	public static void notifyBelow(final World world, final BlockPos pos) {
		get(world, pos).notifyBelow(world, pos);
	}

	public static void notifyAround(final World world, final BlockPos pos) {
		final int range = 4;
		final int minX = (pos.getX() - range) >> 4;
		final int minZ = (pos.getZ() - range) >> 4;
		final int maxX = (pos.getX() + range) >> 4;
		final int maxZ = (pos.getZ() + range) >> 4;
		final IChunkProvider provider = world.getChunkProvider();
		for (int chunkZ = minZ; chunkZ <= maxZ; chunkZ++) {
			for (int chunkX = minX; chunkX <= maxX; chunkX++) {
				@Nullable final Chunk c = provider.getLoadedChunk(chunkX, chunkZ);
				if (c != null) {
					get(c).notifyAround(world, pos, range);
				}
			}
		}
	}

	public static BeaconLookup get(final World world, final BlockPos pos) {
		return get(world.getChunk(pos));
	}

	public static BeaconLookup get(final Chunk chunk) {
		final BeaconLookup lookup = chunk.getCapability(accessor.get(), null);
		if (lookup == null) {
			throw new IllegalStateException(String.format(
					"Missing capability for chunk at %s of %s in %s from %s",
					chunk.getPos(),
					chunk.getClass().getName(),
					chunk.getWorld().getClass().getName(),
					chunk.getWorld().getChunkProvider().getClass().getName()
					));
		}
		return lookup;
	}

	@SubscribeEvent
	public static void tbeacon$attachCapabilities(final AttachCapabilitiesEvent<Chunk> event) {
		final Chunk chunk = event.getObject();
		final BeaconLookup lookup = new BeaconLookup(chunk.x, chunk.z);

		event.addCapability(new ResourceLocation(TBeacon.ID, "lookup"), new ICapabilityProvider() {
			private final Capability<BeaconLookup> capability = accessor.get();

			@Override
			public boolean hasCapability(final @Nonnull Capability<?> capability, final @Nullable EnumFacing facing) {
				return capability == this.capability;
			}

			@Nullable
			@Override
			public <T> T getCapability(final @Nonnull Capability<T> capability, final @Nullable EnumFacing facing) {
				return capability == this.capability ? this.capability.cast(lookup) : null;
			}
		});
	}
}
