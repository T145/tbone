package shadows.fastbench.block;

import T145.tbone.core.TWorkbench;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFastBench extends BlockWorkbench {

	public BlockFastBench() {
		super();
		this.setHardness(2.5F);
		this.setSoundType(SoundType.WOOD);
		this.setTranslationKey("workbench");
		this.setRegistryName("minecraft", "crafting_table");
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}

		player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
		player.openGui(TWorkbench.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}
}