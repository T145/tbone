package T145.tbone.api.net;

import net.minecraft.world.World;

public interface IWorldPositionedMessage extends IPositionedMessage {

	World getWorld();
}
