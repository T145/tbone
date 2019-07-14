package me.paulf.rbeacons.server;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatMaps;
import me.paulf.rbeacons.server.event.RegistryAvailableEvent;
import me.paulf.rbeacons.server.level.chunk.BeaconLookups;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class BeaconNotifier {
	private Reference2FloatMap<IBlockState> materials;

	private BeaconNotifier(final Reference2FloatMap<IBlockState> materials) {
		this.materials = materials;
	}

	private float getMaterial(final IBlockState state) {
		return this.materials.getFloat(state);
	}

	@SubscribeEvent
	public void onRegistryAvailable(final RegistryAvailableEvent<Block> event) {
		this.materials = PyramidMaterialMapper.create().get(event.getRegistry());
	}

	@SubscribeEvent
	public void onWorldLoad(final WorldEvent.Load event) {
		event.getWorld().addEventListener(new BeaconNotifier.WorldListener());
	}

	public static BeaconNotifier create() {
		return new BeaconNotifier(Reference2FloatMaps.emptyMap());
	}

	private final class WorldListener implements IWorldEventListener {
		@Override
		public void notifyBlockUpdate(final World world, final BlockPos pos, final IBlockState oldState, final IBlockState newState, final int flags) {
			if (oldState != newState) {
				BeaconLookups.notifyBelow(world, pos);
				if (BeaconNotifier.this.getMaterial(oldState) != BeaconNotifier.this.getMaterial(newState)) {
					BeaconLookups.notifyAround(world, pos);
				}
			}
		}

		//<editor-fold desc="No-op Functions">
		@Override
		public void notifyLightSet(final BlockPos pos) {}

		@Override
		public void markBlockRangeForRenderUpdate(final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {}

		@Override
		public void playSoundToAllNearExcept(final @Nullable EntityPlayer player, final SoundEvent sound, final SoundCategory category, final double x, final double y, final double z, final float volume, final float pitch) {}

		@Override
		public void playRecord(final SoundEvent sound, final BlockPos pos) {}

		@Override
		public void spawnParticle(final int particle, final boolean ignoreRange, final double x, final double y, final double z, final double velocityX, final double velocityY, final double velocityZ, final int... parameters) {}

		@Override
		public void spawnParticle(final int id, final boolean ignoreRange, final boolean alwaysShow, final double x, final double y, final double z, final double velocityX, final double velocityY, final double velocityZ, final int... parameters) {}

		@Override
		public void onEntityAdded(final Entity entity) {}

		@Override
		public void onEntityRemoved(final Entity entity) {}

		@Override
		public void broadcastSound(final int sound, final BlockPos pos, final int data) {}

		@Override
		public void playEvent(final EntityPlayer player, final int type, final BlockPos pos, final int data) {}

		@Override
		public void sendBlockBreakProgress(final int breaker, final BlockPos pos, final int progress) {}
		//</editor-fold>
	}
}
