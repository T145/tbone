package T145.tbone.utils;

import javax.annotation.Nullable;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

public class BlockStateUtil {

	private BlockStateUtil() {}

	@Nullable
	private static IProperty<EnumFacing> getFacingProperty(IBlockState state) {
		for (IProperty<?> prop : state.getProperties().keySet()) {
			if ((prop.getName().equals("facing") || prop.getName().equals("rotation")) && prop.getValueClass() == EnumFacing.class) {
				return (IProperty<EnumFacing>) prop;
			}
		}
		return null;
	}

	public static boolean hasFacingProperty(IBlockState state) {
		return getFacingProperty(state) != null;
	}

	@Nullable // may crash if getFacingProperty returns null
	public static EnumFacing getBlockFront(IBlockState state) {
		return state.getValue(getFacingProperty(state));
	}

	public static EnumFacing getBlockFront(EntityPlayer placer, IBlockState state) {
		EnumFacing front = getBlockFront(state);

		if (front == null) {
			front = placer.getHorizontalFacing().getOpposite();
		}

		return front;
	}
}
