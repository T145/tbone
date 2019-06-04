package T145.tbone.api.config;

import T145.tbone.core.TBone;
import net.minecraftforge.common.config.Config;

@Config(modid = TBone.ID, name = "T145/" + TBone.NAME)
@Config.LangKey(TBone.ID)
public class ConfigTBone {

	private ConfigTBone() {}

	@Config.Comment("Whether or not you want to recieve an in-game notification if updates are available for all supported mods.")
	public static boolean checkForUpdates = true;
}
