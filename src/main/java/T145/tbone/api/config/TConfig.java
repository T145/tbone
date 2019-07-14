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
package T145.tbone.api.config;

import T145.tbone.core.TBone;
import net.minecraftforge.common.config.Config;

@Config(modid = TBone.ID, name = "T145/" + TBone.NAME)
@Config.LangKey(TBone.ID)
public class TConfig {

	private TConfig() {}

	@Config.Comment("Whether or not you want to recieve an in-game notification if updates are available for all supported mods.")
	public static boolean checkForUpdates = true;

	@Config.Comment("If the recipe book and all associated functionality are fully removed.")
	public static boolean removeRecipeBook = true;

	@Config.Comment("If a testing variant of shift-click crafting is enabled.")
	public static boolean experimentalShiftCrafting = true;

	@Config.Comment("If the furnace uses nbt-sensitive output matching.")
	public static boolean useStrictMatching = true;
}
