package com.insurgencedev.voidchestaddon.listeners;

import com.georgev22.voidchest.api.VoidChestAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.data.BoosterFindResult;
import org.insurgencedev.insurgenceboosters.events.IBoosterStartEvent;

import java.time.Instant;
import java.util.UUID;

public final class VoidChestEventListeners implements Listener {

    @EventHandler
    private void onSell(IBoosterStartEvent event) {
        final String TYPE = "Sell";
        final String NAMESPACE = "VOID_CHEST";
        final double[] totalMulti = {0};
        UUID uuid = event.getPlayer().getUniqueId();

        BoosterFindResult pResult = IBoosterAPI.INSTANCE.getCache(uuid).getBoosterDataManager().findActiveBooster(TYPE, NAMESPACE);
        if (pResult instanceof BoosterFindResult.Success boosterResult) {
            totalMulti[0] += boosterResult.getBoosterData().getMultiplier();
        }

        IBoosterAPI.INSTANCE.getGlobalBoosterManager().findGlobalBooster(TYPE, NAMESPACE, globalBooster -> {
            totalMulti[0] += globalBooster.getMultiplier();
            return null;
        }, () -> null);

        if (totalMulti[0] > 0) {
            VoidChestAPI.getInstance().playerManager().getEntity(uuid).thenAccept(data -> {
                final long result = Instant.now().toEpochMilli() + (1000 * event.getBoosterData().getTimeLeft());
                data.booster().boostTime(result);
                data.booster().booster(totalMulti[0]);
            });         }
    }
}
