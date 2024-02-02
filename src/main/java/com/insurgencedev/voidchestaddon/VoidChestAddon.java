package com.insurgencedev.voidchestaddon;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.insurgencedev.voidchestaddon.listeners.VoidChestEventListeners;
import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;
import org.insurgencedev.insurgenceboosters.libs.fo.Common;

@IBoostersAddon(name = "VoidChestAddon", version = "1.0.0", author = "InsurgenceDev", description = "VoidChest support")
public class VoidChestAddon extends InsurgenceBoostersAddon {

    @Override
    public void onAddonStart() {
        if (Common.doesPluginExist("VoidChest")) {
            VoidChestAPI.getInstance().eventManager().register(new VoidChestEventListeners());
        }
    }
}
