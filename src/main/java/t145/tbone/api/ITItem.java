/*******************************************************************************
 * Copyright 2019 T145
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package t145.tbone.api;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import t145.tbone.core.TBone;

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

	@SideOnly(Side.CLIENT)
	default void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> items) {
		if (item.getCreativeTab() == tab) {
			if (item.getHasSubtypes()) {
				this.prepareCreativeTab(item, items);
			} else {
				items.add(new ItemStack(item));
			}
		}
	}
}
