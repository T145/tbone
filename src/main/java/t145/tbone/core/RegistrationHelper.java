package t145.tbone.core;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import t145.tbone.items.TItemBlock;

public class RegistrationHelper {

	static final ObjectList<UpdateChecker> UPDATES = new ObjectArrayList<>();

	private RegistrationHelper() {}

	public static void registerMod(String modId, String modName) {
		UPDATES.add(new UpdateChecker(modId, modName));
	}

	public static void registerInventoryFixes(DataFixer fixer, FixTypes type, Class clazz) {
		fixer.registerWalker(type, new ItemStackDataLists(clazz, new String[] { "Items" }));
	}

	public static void registerTileEntity(Class tileClass, String modId) {
		GameRegistry.registerTileEntity(tileClass, new ResourceLocation(modId, tileClass.getSimpleName()));
	}

	public static void registerItemBlock(IForgeRegistry<Item> registry, Block block) {
		registry.register(new TItemBlock(block));
	}

	public static void registerItemBlock(IForgeRegistry<Item> registry, Block block, IStringSerializable[] types) {
		registry.register(new TItemBlock(types, block));
	}
}
