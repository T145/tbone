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
package t145.tbone.lib;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class ChestHandler extends ItemStackHandler {

	private boolean updateComparator;

	public ChestHandler(int size) {
		super(size);
	}

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
