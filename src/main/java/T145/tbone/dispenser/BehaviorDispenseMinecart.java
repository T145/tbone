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
package T145.tbone.dispenser;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase.EnumRailDirection;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BehaviorDispenseMinecart extends BehaviorDefaultDispenseItem {

	private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();

	public static void register(Item item, BehaviorDispenseMinecart behavior) {
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, behavior);
	}

	private EnumRailDirection getRailDirection(IBlockState state, World world, BlockPos pos) {
		Block block = state.getBlock();

		if (block instanceof BlockRailBase) {
			BlockRailBase rail = (BlockRailBase) block;
			return rail.getRailDirection(world, pos, state, null);
		}

		return EnumRailDirection.NORTH_SOUTH;
	}

	public abstract EntityMinecart getMinecartEntity(World world, double x, double y, double z, ItemStack stack);

	public EnumActionResult placeStack(EntityPlayer player, EnumHand hand, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);

		if (!BlockRailBase.isRailBlock(state)) {
			return EnumActionResult.FAIL;
		}

		ItemStack stack = player.getHeldItem(hand);

		if (!world.isRemote) {
			EnumRailDirection dir = getRailDirection(state, world, pos);
			double yOffset = 0.0D;

			if (dir.isAscending()) {
				yOffset = 0.5D;
			}

			world.spawnEntity(getMinecartEntity(world, pos.getX() + 0.5D, pos.getY() + 0.0625D + yOffset, pos.getZ() + 0.5D, stack));
		}

		stack.shrink(1);

		return EnumActionResult.SUCCESS;
	}

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		EnumFacing front = source.getBlockState().getValue(BlockDispenser.FACING);
		BlockPos pos = source.getBlockPos().offset(front);
		World world = source.getWorld();
		IBlockState state = world.getBlockState(pos);
		double yOffset = 0.0D;

		if (BlockRailBase.isRailBlock(state)) {
			yOffset = getRailDirection(state, world, pos).isAscending() ? 0.6D : 0.1D;
		} else {
			if (state.getMaterial() != Material.AIR || !BlockRailBase.isRailBlock(world.getBlockState(pos.down()))) {
				return dispenseBehavior.dispense(source, stack);
			}

			yOffset -= front != EnumFacing.DOWN && getRailDirection(state, world, pos.down()).isAscending() ? 0.4D : 0.9D;
		}

		yOffset += Math.floor(source.getY()) + front.getYOffset();

		double xOffset = source.getX() + front.getXOffset() * 1.125D;
		double zOffset = source.getZ() + front.getZOffset() * 1.125D;
		world.spawnEntity(getMinecartEntity(world, xOffset, yOffset, zOffset, stack));
		stack.shrink(1);

		return stack;
	}

	@Override
	protected void playDispenseSound(IBlockSource source) {
		source.getWorld().playEvent(1000, source.getBlockPos(), 0);
	}
}
