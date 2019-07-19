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
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ChestAnimator {

	public static final int EVENT_PLAYER_USED = -1;
	public static final int EVENT_CHEST_NOM = -2;

	public double lidAngle;
	public double prevLidAngle;
	public int numPlayersUsing;

	public boolean isOpen() {
		return lidAngle > 0.0F;
	}

	public void update(EntityPlayer player, World world, BlockPos pos, SoundEvent sound, boolean opening, boolean trapped) {
		if (!player.isSpectator()) {
			if (opening) {
				++this.numPlayersUsing;
			} else {
				--this.numPlayersUsing;
			}

			world.playSound(player, pos, sound, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

			IBlockState state = world.getBlockState(pos);

			if (state.getMaterial() != Material.AIR) {
				Block block = state.getBlock();

				world.addBlockEvent(pos, block, EVENT_PLAYER_USED, numPlayersUsing);
				world.notifyNeighborsOfStateChange(pos, block, false);

				if (trapped) {
					world.notifyNeighborsOfStateChange(pos.down(), block, false);
				}
			}
		}
	}

	public void tick(World world, BlockPos pos) {
		if (!world.isRemote) {
			IBlockState state = world.getBlockState(pos);

			if (state.getMaterial() != Material.AIR && ((world.getTotalWorldTime() + pos.getX() + pos.getY() + pos.getZ()) & 0x1F) == 0) {
				world.addBlockEvent(pos, state.getBlock(), EVENT_PLAYER_USED, this.numPlayersUsing);
			}
		}

		prevLidAngle = lidAngle;
		lidAngle = MathHelper.clampedLerp(lidAngle, numPlayersUsing > 0 ? 1.0D : 0.0D, 0.1D);
	}

	public boolean receiveClientEvent(int event, int data) {
		switch (event) {
		case EVENT_PLAYER_USED:
			numPlayersUsing = data;
			return true;
		case EVENT_CHEST_NOM:
			if (lidAngle < data / 10D) {
				lidAngle = data / 10D;
			}
			return true;
		default:
			return false;
		}
	}

	public float getLidAngle(float partialTicks) {
		double f = prevLidAngle + (lidAngle - prevLidAngle) * partialTicks;
		f = 1.0D - f;
		f = 1.0D - f * f * f;
		return (float) -(f * (Math.PI / 2));
	}
}
