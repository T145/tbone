package T145.tbone.lib;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class ChestHandler extends ItemStackHandler {

	private boolean updateComparator;

	@Override
	protected void onLoad() {
		// Perhaps usable for something?
	}

	@Override
	protected void onContentsChanged(int slot) {
		// update on the next tick, reducing comparator updates to the bare minimum
		if (!updateComparator) {
			updateComparator = true;
		}
	}

	public void tick(World world, BlockPos pos, Block block) {
		if (updateComparator) {
			updateComparator = false;
			world.updateComparatorOutputLevel(pos, block);
		}
	}

	public void tick(TileEntity te) {
		this.tick(te.getWorld(), te.getPos(), te.getBlockType());
	}
}
