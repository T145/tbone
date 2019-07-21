package t145.tbone.api;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.ILootContainer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public interface IInventoryLootHandler extends IInventoryHandler, ILockableContainer, ILootContainer {

	@Override
	default boolean hasCustomName() {
		return false;
	}

	@Override
	default ITextComponent getDisplayName() {
		return new TextComponentTranslation(getName(), new Object[0]);
	}

	default void fillWithLoot(@Nullable EntityPlayer player, World world, ResourceLocation lootTable, long lootTableSeed) {
		if (lootTable != null) {
			LootTable table = world.getLootTableManager().getLootTableFromLocation(lootTable);
			lootTable = null;
			Random random;

			if (lootTableSeed == 0L) {
				random = new Random();
			} else {
				random = new Random(lootTableSeed);
			}

			LootContext.Builder builder = new LootContext.Builder((WorldServer) world);

			if (player != null) {
				builder.withLuck(player.getLuck()).withPlayer(player); // Forge: add player to LootContext
			}

			table.fillInventory(this, random, builder.build());
		}
	}

	public void setLootTable(ResourceLocation lootTable, long lootTableSeed);

	@Override
	default boolean isLocked() {
		return false;
	}

	@Override
	default void setLockCode(LockCode code) {}

	@Override
	default LockCode getLockCode() {
		return LockCode.EMPTY_CODE;
	}
}