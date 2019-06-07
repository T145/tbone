package T145.tbone.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtil {

	private InventoryUtil() {}

	public static IItemHandler getChestInventory(TileEntity te, EnumFacing front) {
		if (te instanceof TileEntityChest) {
			return ((TileEntityChest) te).getSingleChestHandler();
		} else {
			return te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, front);
		}
	}
}
