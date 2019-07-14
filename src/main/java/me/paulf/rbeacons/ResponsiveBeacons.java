package me.paulf.rbeacons;

import javax.annotation.Nullable;

import me.paulf.rbeacons.server.BeaconNotifier;
import me.paulf.rbeacons.server.event.RegistryAvailableEvent;
import me.paulf.rbeacons.server.level.chunk.BeaconLookup;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod(modid = ResponsiveBeacons.ID, useMetadata = true)
public final class ResponsiveBeacons {
	public static final String ID = "rbeacons";

	@Mod.EventHandler
	public void onInit(final FMLPreInitializationEvent event) {
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

	@Mod.EventHandler
	public void onMap(final FMLModIdMappingEvent event) {
		MinecraftForge.EVENT_BUS.post(RegistryAvailableEvent.create(ForgeRegistries.BLOCKS));
	}
}
