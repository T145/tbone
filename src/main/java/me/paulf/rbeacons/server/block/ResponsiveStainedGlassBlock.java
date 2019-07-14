package me.paulf.rbeacons.server.block;

import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ResponsiveStainedGlassBlock extends BlockStainedGlass {
	public ResponsiveStainedGlassBlock() {
		super(Material.GLASS);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	public void onBlockAdded(final World world, final BlockPos pos, final IBlockState state) {}

	@Override
	public void breakBlock(final World world, final BlockPos pos, final IBlockState state) {}
}
