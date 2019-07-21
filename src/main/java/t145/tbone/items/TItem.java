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
package t145.tbone.items;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import t145.tbone.api.ITItem;

public class TItem extends Item implements ITItem {

	protected final IStringSerializable[] types;

	public TItem(IStringSerializable[] types, ResourceLocation resource, CreativeTabs tab) {
		super();
		this.initItem(this, resource, types, tab);
		this.types = types;
	}

	public TItem(List<? extends IStringSerializable> types, ResourceLocation resource, CreativeTabs tab) {
		this(types.toArray(new IStringSerializable[types.size()]), resource, tab);
	}

	public TItem(LinkedList<? extends IStringSerializable> types, ResourceLocation resource, CreativeTabs tab) {
		this(types.toArray(new IStringSerializable[types.size()]), resource, tab);
	}

	public TItem(ResourceLocation resource, CreativeTabs tab) {
		this((IStringSerializable[]) null, resource, tab);
	}

	@Override
	public IStringSerializable[] getTypes() {
		return types;
	}

	@Override
	public String getCreatorModId(ItemStack stack) {
		return this.getRegistryName().getNamespace();
	}

	// NOTE: JEI doesn't like do anything clever w/ this, so just leave it like this.
	@Override
	public String getTranslationKey(ItemStack stack) {
		if (hasSubtypes) {
			return String.format("%s.%s", super.getTranslationKey(), types[stack.getMetadata()].getName());
		}
		return super.getTranslationKey(stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		getSubItems(this, tab, items);
	}
}
