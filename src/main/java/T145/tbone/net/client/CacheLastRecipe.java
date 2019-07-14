package T145.tbone.net.client;

import java.io.IOException;

import T145.tbone.core.TWorkbench;
import T145.tbone.net.TMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import shadows.fastbench.gui.GuiFastBench;

public class CacheLastRecipe extends TMessage {

	private IRecipe recipe;

	public CacheLastRecipe() {
		// DEFAULT CONSTRUCTOR REQUIRED
	}

	public CacheLastRecipe(IRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void serialize(PacketBuffer buf) {
		buf.writeInt(CraftingManager.REGISTRY.getIDForObject(recipe));
	}

	@Override
	public void deserialize(PacketBuffer buf) throws IOException {
		this.recipe = CraftingManager.REGISTRY.getObjectById(buf.readInt());
	}

	@Override
	public void process(MessageContext ctx) {
		GuiScreen gui = Minecraft.getMinecraft().currentScreen;

		if (gui instanceof GuiFastBench) {
			TWorkbench.LOG.info("PACKET SENT!");
			GuiFastBench benchGui = (GuiFastBench) gui;
			benchGui.getContainer().lastRecipe = recipe;
		}
	}
}
