package T145.tbone.api.network;

import net.minecraft.world.World;

public interface IWorldPositionedMessage extends IPositionedMessage {

	World getWorld();
}
