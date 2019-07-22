package t145.tbone.lib;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import t145.tbone.api.IInventoryHandler;

public class ChestHelper {

	private ChestHelper() {}

	public static int getFrontAngle(final EnumFacing front) {
		switch (front) {
		case NORTH:
			return 180;
		case WEST:
			return 90;
		case EAST:
			return -90;
		default:
			return 0;
		}
	}

	public static ItemStack tryToInsertStack(final IItemHandler inv, final @Nonnull ItemStack stack) {
		ItemStack result = stack.copy();

		for (short slot = 0; slot < inv.getSlots(); ++slot) {
			if (!inv.getStackInSlot(slot).isEmpty()) {
				result = inv.insertItem(slot, result, false);

				if (result.isEmpty()) {
					return ItemStack.EMPTY;
				}
			}
		}

		for (short slot = 0; slot < inv.getSlots(); ++slot) {
			result = inv.insertItem(slot, result, false);

			if (result.isEmpty()) {
				return ItemStack.EMPTY;
			}
		}

		return result;
	}

	public static void tryToEatItem(final World world, final BlockPos pos, final Entity entity, final Block receiver) {
		TileEntity te = world.getTileEntity(pos);

		if (te instanceof IInventoryHandler && entity instanceof EntityItem && !entity.isDead) {
			EntityItem item = (EntityItem) entity;
			ItemStack stack = item.getItem();
			ItemStack leftovers = tryToInsertStack(((IInventoryHandler) te).getInventory(), stack);

			if (leftovers == null || leftovers.getCount() != stack.getCount()) {
				entity.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.25F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
				world.addBlockEvent(pos, receiver, ChestAnimator.EVENT_CHEST_NOM, 2);
			}

			if (leftovers != null) {
				item.setItem(leftovers);
			} else {
				entity.setDead();
			}
		}
	}
}
