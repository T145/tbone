package T145.tbone.lib;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

class WorldContextProvider {

	private TileEntity tileEntity;
	private Entity entity;

	public WorldContextProvider(TileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	public WorldContextProvider(Entity entity) {
		this.entity = entity;
	}

	public boolean hasTileEntity() {
		return tileEntity != null;
	}

	public boolean hasEntity() {
		return entity != null;
	}

	public boolean hasContext() {
		return hasTileEntity() || hasEntity();
	}

	public World getWorld() {
		if (hasEntity()) {
			return entity.getEntityWorld();
		}
		return tileEntity.getWorld();
	}

	public BlockPos getPos() {
		if (hasEntity()) {
			return entity.getPosition();
		}
		return tileEntity.getPos();
	}

	public void activateChest(int event, int data) {
		if (this.hasTileEntity()) {
			World world = tileEntity.getWorld();
			Block block = tileEntity.getBlockType();
			BlockPos pos = tileEntity.getPos();

			world.addBlockEvent(pos, block, 1, data);
		}
	}

	public void updateChest(boolean trapped) {
		if (this.hasTileEntity()) {
			World world = tileEntity.getWorld();
			Block block = tileEntity.getBlockType();
			BlockPos pos = tileEntity.getPos();

			world.notifyNeighborsOfStateChange(pos, block, false);

			if (trapped) {
				world.notifyNeighborsOfStateChange(pos.down(), block, false);
			}
		}
	}
}