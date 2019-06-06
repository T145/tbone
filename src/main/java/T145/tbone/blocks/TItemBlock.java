package T145.tbone.blocks;

import org.apache.commons.lang3.StringUtils;

import T145.tbone.core.TBone;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TItemBlock extends ItemBlock {

	protected final IStringSerializable[] types;
	protected final CreativeTabs tab;

	public TItemBlock(Block block, IStringSerializable[] types) {
		super(block);

		setHasSubtypes(types != null);

		if (hasSubtypes) {
			// validate the types we'll use to build items out of
			try {
				for (IStringSerializable type : types) {
					if (type == null) {
						throw new NullPointerException(" [BlockItemMod] Cannot build blocks out of null objects in \"types\"!");
					}
				}
			} catch (NullPointerException err) {
				TBone.LOG.catching(err);
				TBone.LOG.error(String.format(" [BlockItemMod] %s", types.toString()));
			}
		}

		this.types = types;
		this.tab = block.getCreativeTab();

		ResourceLocation resource = block.getRegistryName();
		setRegistryName(resource);
		setTranslationKey(resource.toString());
		setCreativeTab(tab);
	}

	public TItemBlock(Block block) {
		this(block, null);
	}

	public IStringSerializable[] getTypes() {
		return types;
	}

	@Override
	public String getCreatorModId(ItemStack stack) {
		return tab.getTabLabel().replace("itemGroup.", StringUtils.EMPTY);
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (hasSubtypes) {
			return String.format("%s.%s", super.getTranslationKey(), types[stack.getMetadata()].getName());
		}
		return super.getTranslationKey(stack);
	}

	@SideOnly(Side.CLIENT)
	public void prepareCreativeTab(NonNullList<ItemStack> items) {
		if (hasSubtypes) {
			for (int meta = 0; meta < types.length; ++meta) {
				items.add(new ItemStack(this, 1, meta));
			}
		} else {
			items.add(new ItemStack(this));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.tab == tab) {
			if (hasSubtypes) {
				prepareCreativeTab(items);
			} else {
				items.add(new ItemStack(this));
			}
		}
	}
}
