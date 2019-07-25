package t145.tbone.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TClient {

	private TClient() {}

	public static String getVariantName(IStringSerializable variant) {
		return String.format("variant=%s", variant.getName());
	}

	public static ModelResourceLocation getCustomModel(String modId, Item item, String customDomain, String variantPath) {
		if (StringUtils.isNullOrEmpty(customDomain)) {
			return new ModelResourceLocation(item.getRegistryName(), variantPath);
		} else {
			return new ModelResourceLocation(String.format("%s:%s", modId, customDomain), variantPath);
		}
	}

	public static void registerModel(String modId, Item item, String domain, int meta, String... variants) {
		StringBuilder path = new StringBuilder(variants[0]);

		for (short i = 1; i < variants.length; ++i) {
			path.append(',').append(variants[i]);
		}

		ModelLoader.setCustomModelResourceLocation(item, meta, getCustomModel(modId, item, domain, path.toString()));
	}

	public static void registerModel(String modId, Block block, String domain, int meta, String... variants) {
		registerModel(modId, Item.getItemFromBlock(block), domain, meta, variants);
	}

	public static void registerModel(String modId, Item item, int meta, String... variants) {
		registerModel(modId, item, null, meta, variants);
	}

	public static void registerModel(String modId, Block block, int meta, String... variants) {
		registerModel(modId, block, null, meta, variants);
	}

	public static void registerModel(String modId, Item item, int meta, IStringSerializable type) {
		registerModel(modId, item, meta, getVariantName(type));
	}

	public static void registerModel(String modId, Block block, int meta, IStringSerializable type) {
		registerModel(modId, block, meta, getVariantName(type));
	}

	public static void registerTileRenderer(Class tileClass, TileEntitySpecialRenderer tileRenderer) {
		ClientRegistry.bindTileEntitySpecialRenderer(tileClass, tileRenderer);
	}
}
