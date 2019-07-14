package T145.tbone.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = TLeaves.ID, name = TLeaves.NAME, version = TBone.VERSION)
@EventBusSubscriber
public class TLeaves {

	public static final String ID = "tleaves";
	public static final String NAME = "TLeaves";
	public static final Logger LOG = LogManager.getLogger(ID);

	@SubscribeEvent
	public static void tleaves$notifyLeaves(final NeighborNotifyEvent event) {
		World world = event.getWorld();

		for (EnumFacing facing : event.getNotifiedSides()) {
			BlockPos pos = event.getPos().offset(facing);
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			if (block.isLeaves(state, world, pos)) {
				world.scheduleUpdate(pos, block, 2 + world.rand.nextInt(6));
			}
		}
	}
}
