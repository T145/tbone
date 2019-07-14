package shadows.fastbench.gui;

import T145.tbone.api.config.TConfig;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFastBench extends GuiCrafting {

	public GuiFastBench(InventoryPlayer inv, World world) {
		this(inv, world, BlockPos.ORIGIN);
	}

	public GuiFastBench(InventoryPlayer inv, World world, BlockPos pos) {
		super(inv, world, pos);
		this.inventorySlots = new ClientContainerFastBench(inv.player, world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void initGui() {
		super.initGui();
		if (TConfig.removeRecipeBook) {
			this.buttonList.clear();
			this.recipeButton = null;
			this.recipeBookGui = new GuiDedBook();
		}
	}

	public ContainerFastBench getContainer() {
		return (ContainerFastBench) this.inventorySlots;
	}
}