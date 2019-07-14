package shadows.fastbench.net;

import T145.tbone.core.TWorkbench;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.integrated.IntegratedPlayerList;
import net.minecraft.server.integrated.IntegratedServer;

public class HijackedPlayerList extends IntegratedPlayerList {

	public HijackedPlayerList(IntegratedServer server) {
		super(server);
	}

	@Override
	public void initializeConnectionToPlayer(NetworkManager netManager, EntityPlayerMP playerIn, NetHandlerPlayServer nethandlerplayserver) {
		playerIn.recipeBook = TWorkbench.SERVER_BOOK;
		super.initializeConnectionToPlayer(netManager, playerIn, nethandlerplayserver);
	}
}
