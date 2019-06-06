package T145.tbone.api;

import org.apache.commons.lang3.StringUtils;

import T145.tbone.core.TBone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITItem {

	IStringSerializable[] getTypes();

	default void initItem(Item item, ResourceLocation resource, IStringSerializable[] types, CreativeTabs tab) {
		item.setHasSubtypes(types != null);

		if (item.getHasSubtypes()) {
			// validate the types we'll use to build items out of
			try {
				for (IStringSerializable type : types) {
					if (type == null) {
						throw new NullPointerException(" [ItemMod] Cannot build items out of null objects in \"types\"!");
					}
				}
			} catch (NullPointerException err) {
				TBone.LOG.catching(err);
				TBone.LOG.error(String.format(" [ItemMod] %s", types.toString()));
			}
		}

		item.setRegistryName(resource);
		item.setTranslationKey(resource.toString());
		item.setCreativeTab(tab);
	}

	default String getCreatorModId(ItemStack stack, CreativeTabs tab) {
		return tab.getTabLabel().replace("itemGroup.", StringUtils.EMPTY);
	}

	default String getTranslationKey(Item item, ItemStack stack) {
		if (item.getHasSubtypes()) {
			return String.format("%s.%s", item.getTranslationKey(), getTypes()[stack.getMetadata()].getName());
		}
		return item.getTranslationKey(stack);
	}

	@SideOnly(Side.CLIENT)
	default void prepareCreativeTab(Item item, NonNullList<ItemStack> items) {
		if (item.getHasSubtypes()) {
			for (int meta = 0; meta < getTypes().length; ++meta) {
				items.add(new ItemStack(item, 1, meta));
			}
		} else {
			items.add(new ItemStack(item));
		}
	}
}
