package shadows.fastfurnace.block;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFastFurnace extends BlockFurnace {

	public BlockFastFurnace(boolean isBurning) {
		super(isBurning);
		setHardness(3.5F);
		setSoundType(SoundType.STONE);
		setTranslationKey("furnace");
		setCreativeTab(CreativeTabs.DECORATIONS);

		if (isBurning) {
			setLightLevel(0.875F);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileFastFurnace();
	}
}