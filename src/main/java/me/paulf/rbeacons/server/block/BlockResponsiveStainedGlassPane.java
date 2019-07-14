package me.paulf.rbeacons.server.block;

import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockResponsiveStainedGlassPane extends BlockStainedGlassPane {

	public BlockResponsiveStainedGlassPane() {
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
		this.setTranslationKey("thinStainedGlass");
		this.setRegistryName("minecraft", "stained_glass_pane");
	}

	@Override
	public void onBlockAdded(final World world, final BlockPos pos, final IBlockState state) {}

	@Override
	public void breakBlock(final World world, final BlockPos pos, final IBlockState state) {}
}
