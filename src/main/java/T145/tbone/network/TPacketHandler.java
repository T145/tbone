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
package T145.tbone.network;

import T145.tbone.api.network.IPositionedMessage;
import T145.tbone.api.network.IWorldPositionedMessage;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public abstract class TPacketHandler {

	protected final String modId;
	protected final SimpleNetworkWrapper network;
	private byte id;

	public TPacketHandler(String modId) {
		this.modId = modId;
		this.network = NetworkRegistry.INSTANCE.newSimpleChannel(modId);
	}

	public SimpleNetworkWrapper getNetwork() {
		return network;
	}

	protected void registerMessage(Class<? extends TMessage> clazz, Side side) {
		network.registerMessage((msg, ctx) -> {
			IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
			thread.addScheduledTask(() -> msg.process(ctx));
			return null;
		}, clazz, id++, side);
	}

	public abstract void registerMessages();

	public TargetPoint getTargetPoint(World world, BlockPos pos) {
		return new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 16D);
	}

	public void sendToAllAround(TMessage msg, World world, BlockPos pos) {
		network.sendToAllAround(msg, getTargetPoint(world, pos));
	}

	public void sendToAllAround(TMessage msg, World world) {
		if (msg instanceof IPositionedMessage) {
			network.sendToAllAround(msg, getTargetPoint(world, ((IPositionedMessage) msg).getPos()));
		}
	}

	public void sendToAllAround(TMessage msg) {
		if (msg instanceof IWorldPositionedMessage) {
			IWorldPositionedMessage m = (IWorldPositionedMessage) msg;
			network.sendToAllAround(msg, getTargetPoint(m.getWorld(), m.getPos()));
		}
	}
}
