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
package t145.tbone.core;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLCommonHandler;

class UpdateChecker {

	private final String modId;
	private final String modName;

	UpdateChecker(String modId, String modName) {
		this.modId = modId;
		this.modName = modName;
	}

	private ForgeVersion.CheckResult getResult() {
		return ForgeVersion.getResult(FMLCommonHandler.instance().findContainerFor(modId));
	}

	public boolean hasUpdate() {
		ForgeVersion.CheckResult result = getResult();

		if (result.status == ForgeVersion.Status.PENDING) {
			TBone.LOG.warn("Cannot check for updates! Found status PENDING!");
			return false;
		}

		return result.status.isAnimated();
	}

	private String getLatestVersion() {
		return getResult().target.toString();
	}

	public ITextComponent getUpdateNotification() {
		ITextComponent prefix = new TextComponentTranslation(modName).setStyle(new Style().setColor(TextFormatting.GREEN));
		ITextComponent base = new TextComponentTranslation("tbone.client.update").setStyle(new Style().setColor(TextFormatting.GOLD));
		ITextComponent postfix = new TextComponentString(String.format("%s%s%s!", TextFormatting.AQUA, getLatestVersion(), TextFormatting.GOLD));
		return prefix.appendSibling(base).appendSibling(postfix);
	}
}
