package me.paulf.rbeacons.client;

import me.paulf.rbeacons.ResponsiveBeacons;
import me.paulf.rbeacons.server.block.entity.ResponsiveBeaconEntity;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ResponsiveBeacons.ID)
public final class ClientEventHandler {
	@SubscribeEvent
	public static void onModelRegister(final ModelRegistryEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(ResponsiveBeaconEntity.class, new TileEntityBeaconRenderer());
	}
}
