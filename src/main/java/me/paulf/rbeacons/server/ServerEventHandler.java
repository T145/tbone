package me.paulf.rbeacons.server;

import me.paulf.rbeacons.ResponsiveBeacons;
import me.paulf.rbeacons.server.block.ResponsiveBeaconBlock;
import me.paulf.rbeacons.server.block.ResponsiveStainedGlassBlock;
import me.paulf.rbeacons.server.block.ResponsiveStainedGlassPane;
import me.paulf.rbeacons.server.block.entity.ResponsiveBeaconEntity;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = ResponsiveBeacons.ID)
public final class ServerEventHandler {
	@SubscribeEvent
	public static void onRegisterBlock(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
			new ResponsiveBeaconBlock()
				.setTranslationKey("beacon")
				.setRegistryName(new ResourceLocation("beacon")),
			new ResponsiveStainedGlassBlock()
				.setTranslationKey("stainedGlass")
				.setRegistryName(new ResourceLocation("stained_glass")),
			new ResponsiveStainedGlassPane()
				.setTranslationKey("thinStainedGlass")
				.setRegistryName(new ResourceLocation("stained_glass_pane"))
		);
		GameRegistry.registerTileEntity(ResponsiveBeaconEntity.class, new ResourceLocation("beacon"));
	}
}
