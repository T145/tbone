package me.paulf.rbeacons.server.block;

import T145.tbone.core.TBeacon;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class TileResponsiveBeacon extends TileEntityBeacon {

	@Override
	public void onLoad() {
		this.updateBeacon();
		TBeacon.get(this.world, this.pos).add(this.pos);
	}

	@Override
	public void invalidate() {
		super.invalidate();
		TBeacon.get(this.world, this.pos).remove(this.pos);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		this.updateBeacon();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float shouldBeamRender() {
		return super.shouldBeamRender() == 0.0F ? 0.0F : 1.0F;
	}
}
