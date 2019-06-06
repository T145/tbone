/*******************************************************************************
 * Copyright 2019 T145
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
