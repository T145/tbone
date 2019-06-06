package T145.tbone.items;

import T145.tbone.api.ITItem;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TItemBlock extends ItemBlock implements ITItem {

	protected final IStringSerializable[] types;

	public TItemBlock(Block block, IStringSerializable[] types) {
		super(block);
		this.initItem(this, block.getRegistryName(), types, block.getCreativeTab());
		this.types = types;
	}

	public TItemBlock(Block block) {
		this(block, null);
	}

	@Override
	public IStringSerializable[] getTypes() {
		return types;
	}

	@Override
	public String getCreatorModId(ItemStack stack) {
		return this.getCreatorModId(stack, getCreativeTab());
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return this.getTranslationKey(this, stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.getCreativeTab() == tab) {
			if (hasSubtypes) {
				this.prepareCreativeTab(this, items);
			} else {
				items.add(new ItemStack(this));
			}
		}
	}
}
