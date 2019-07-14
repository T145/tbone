package me.paulf.rbeacons.server;

import java.util.stream.Collector;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatMaps;
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * Block#isBeaconBase introduced here:
 * https://github.com/MinecraftForge/MinecraftForge/commit/31638a069726a697b89c29e91c9e0180eecce135
 */
final class PyramidMaterialMapper {
	private final TestingWorld testbed;

	private PyramidMaterialMapper(final TestingWorld testbed) {
		this.testbed = testbed;
	}

	Reference2FloatMap<IBlockState> get(final Iterable<? extends Block> blocks) {
		return StreamSupport.stream(blocks.spliterator(), false)
			.collect(Collector.of(
				Reference2FloatOpenHashMap::new,
				this::accept,
				(left, right) -> {
					throw new IllegalStateException();
				},
				map -> {
					map.defaultReturnValue(0.0F);
					return Reference2FloatMaps.unmodifiable(map);
				}
			));
	}

	private void accept(final Reference2FloatFunction<IBlockState> map, final Block block) {
		for (final IBlockState state : block.getBlockState().getValidStates()) {
			this.testbed.prepare(state);
			@Nullable Boolean result;
			try {
				result = block.isBeaconBase(this.testbed, this.testbed.material(), this.testbed.beacon());
			} catch (final RuntimeException e) {
				result = null;
			}
			if (result != null && this.testbed.isStateIsolated()) {
				if (result) {
					map.put(state, 1.0F);
				}
			} else {
				map.put(state, Float.NaN);
			}
		}
	}

	private static final class TestingWorld implements IBlockAccess {
		private final BlockPos material;

		private final BlockPos beacon;

		private IBlockState state = Blocks.AIR.getDefaultState();

		private boolean stateIsolated;

		private TestingWorld(final BlockPos material, final BlockPos beacon) {
			this.material = material;
			this.beacon = beacon;
		}

		BlockPos material() {
			return this.material;
		}

		BlockPos beacon() {
			return this.beacon;
		}

		void prepare(final IBlockState state) {
			this.state = state;
			this.stateIsolated = true;
		}

		boolean isStateIsolated() {
			return this.stateIsolated;
		}

		@Nullable
		@Override
		public TileEntity getTileEntity(final BlockPos pos) {
			this.stateIsolated = false;
			return null;
		}

		@SideOnly(Side.CLIENT)
		@Override
		public int getCombinedLight(final BlockPos pos, final int lightValue) {
			throw new NoSuchMethodError();
		}

		@Override
		public IBlockState getBlockState(final BlockPos pos) {
			if (this.material.equals(pos)) {
				return this.state;
			}
			this.stateIsolated = false;
			return Blocks.AIR.getDefaultState();
		}

		@Override
		public boolean isAirBlock(final BlockPos pos) {
			return this.getBlockState(pos).getBlock().isAir(this.state, this, pos);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public Biome getBiome(final BlockPos pos) {
			throw new NoSuchMethodError();
		}

		@Override
		public int getStrongPower(final BlockPos pos, final EnumFacing direction) {
			return this.getBlockState(pos).getStrongPower(this, pos, direction);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public WorldType getWorldType() {
			throw new NoSuchMethodError();
		}

		@Override
		public boolean isSideSolid(final BlockPos pos, final EnumFacing side, final boolean _default) {
			return this.getBlockState(pos).isSideSolid(this, pos, side);
		}
	}

	static PyramidMaterialMapper create() {
		return new PyramidMaterialMapper(
			new TestingWorld(
				new BlockPos(41, 64, 73),
				new BlockPos(41, 65, 73)
			)
		);
	}
}
