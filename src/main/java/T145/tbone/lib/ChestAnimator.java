/*******************************************************************************
 * Copyright 2018-2019 T145
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

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChestAnimator {

	public static final int EVENT_PLAYER_USED = -1;
	public static final int EVENT_CHEST_NOM = -2;

	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;

	private final WorldContextProvider provider;

	public ChestAnimator(TileEntity tileEntity) {
		this.provider = new WorldContextProvider(tileEntity);
	}

	public ChestAnimator(Entity entity) {
		this.provider = new WorldContextProvider(entity);
	}

	public boolean isOpen() {
		return lidAngle > 0.0F;
	}

	public void openInventory(SoundEvent openSound, EntityPlayer player, boolean trapped) {
		if (!player.isSpectator()) {
			if (numPlayersUsing < 0) {
				numPlayersUsing = 0;
			}

			World world = provider.getWorld();
			BlockPos pos = provider.getPos();

			provider.activateChest(EVENT_PLAYER_USED, ++numPlayersUsing);
			world.playSound(player, pos, openSound, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			provider.updateChest(trapped);
		}
	}

	public void openInventory(EntityPlayer player, boolean trapped) {
		this.openInventory(SoundEvents.BLOCK_CHEST_OPEN, player, trapped);
	}

	public void openInventory(EntityPlayer player) {
		this.openInventory(player, false);
	}

	public void closeInventory(SoundEvent closeSound, EntityPlayer player, boolean trapped) {
		if (!player.isSpectator()) {
			World world = provider.getWorld();
			BlockPos pos = provider.getPos();

			provider.activateChest(EVENT_PLAYER_USED, --numPlayersUsing);
			world.playSound(player, pos, closeSound, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			provider.updateChest(trapped);
		}
	}

	public void closeInventory(EntityPlayer player, boolean trapped) {
		this.closeInventory(SoundEvents.BLOCK_CHEST_CLOSE, player, trapped);
	}

	public void closeInventory(EntityPlayer player) {
		this.closeInventory(player, false);
	}

	/**
	 * @param a   The value
	 * @param b   The value to approach
	 * @param max The maximum step
	 * @return the closed value to b no less than max from a
	 */
	private float lerp(float a, float b, float max) {
		return (a > b) ? (a - b < max ? b : a - max) : (b - a < max ? b : a + max);
	}

	public void tick() {
		prevLidAngle = lidAngle;
		lidAngle = lerp(lidAngle, numPlayersUsing > 0 ? 1.0F : 0.0F, 0.1F);
	}

	public void tickTileEntity(BlockPos pos) {
		if (provider.hasTileEntity()) {
			World world = provider.getWorld();

			if (!world.isRemote && ((world.getTotalWorldTime() + pos.getX() + pos.getY() + pos.getZ()) & 0x1F) == 0) {
				provider.activateChest(1, numPlayersUsing);
			}
		}

		tick();
	}

	public boolean receiveClientEvent(int event, int data) {
		switch (event) {
		case EVENT_PLAYER_USED:
			numPlayersUsing = data;
			return true;
		case EVENT_CHEST_NOM:
			if (lidAngle < data / 10F) {
				lidAngle = data / 10F;
			}
			return true;
		default:
			return false;
		}
	}

	public float getRenderAngle(float partialTicks) {
		float f = prevLidAngle + (lidAngle - prevLidAngle) * partialTicks;
		f = 1.0F - f;
		f = 1.0F - f * f * f;
		return (float) -(f * (Math.PI / 2F));
	}
}
