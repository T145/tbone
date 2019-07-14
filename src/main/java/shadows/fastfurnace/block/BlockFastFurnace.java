package shadows.fastfurnace.block;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFastFurnace extends BlockFurnace {

	public BlockFastFurnace(boolean isBurning) {
		super(isBurning);
		this.setHardness(3.5F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setTranslationKey("furnace");

		if (isBurning) {
			this.setLightLevel(0.875F);
			this.setRegistryName("minecraft", "lit_furnace");
		} else {
			this.setRegistryName("minecraft", "furnace");
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileFastFurnace();
	}
}