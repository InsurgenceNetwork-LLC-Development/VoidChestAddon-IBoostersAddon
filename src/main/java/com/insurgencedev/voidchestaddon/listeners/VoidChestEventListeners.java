package com.insurgencedev.voidchestaddon.listeners;

import com.georgev22.voidchest.api.event.annotations.EventHandler;
import com.georgev22.voidchest.api.event.events.sell.VoidSellChunkItemEvent;
import com.georgev22.voidchest.api.event.events.sell.VoidSellItemEvent;
import com.georgev22.voidchest.api.event.interfaces.EventListener;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.data.BoosterFindResult;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public final class VoidChestEventListeners implements EventListener {

    @EventHandler
    private void onSell(VoidSellChunkItemEvent event) {
        final String TYPE = "Sell";
        final String NAMESPACE = "VOID_CHEST";
        final double[] totalMulti = {0};
        UUID uuid = Objects.requireNonNull(event.getVoidStorage()).ownerUUID();

        BoosterFindResult pResult = IBoosterAPI.INSTANCE.getCache(uuid).getBoosterDataManager().findActiveBooster(TYPE, NAMESPACE);
        if (pResult instanceof BoosterFindResult.Success boosterResult) {
            totalMulti[0] += boosterResult.getBoosterData().getMultiplier();
        }

        IBoosterAPI.INSTANCE.getGlobalBoosterManager().findGlobalBooster(TYPE, NAMESPACE, globalBooster -> {
            totalMulti[0] += globalBooster.getMultiplier();
            return null;
        }, () -> null);

        if (totalMulti[0] > 0) {
            event.setPrice(BigDecimal.valueOf(calculateAmount(event.getPrice().doubleValue(), totalMulti[0])));
        }
    }

    @EventHandler
    private void onSell(VoidSellItemEvent event) {
        final String TYPE = "Sell";
        final String NAMESPACE = "VOID_CHEST";
        final double[] totalMulti = {0};
        UUID uuid = Objects.requireNonNull(event.getVoidStorage()).ownerUUID();

        BoosterFindResult pResult = IBoosterAPI.INSTANCE.getCache(uuid).getBoosterDataManager().findActiveBooster(TYPE, NAMESPACE);
        if (pResult instanceof BoosterFindResult.Success boosterResult) {
            totalMulti[0] += boosterResult.getBoosterData().getMultiplier();
        }

        IBoosterAPI.INSTANCE.getGlobalBoosterManager().findGlobalBooster(TYPE, NAMESPACE, globalBooster -> {
            totalMulti[0] += globalBooster.getMultiplier();
            return null;
        }, () -> null);

        if (totalMulti[0] > 0) {
            event.setPrice(BigDecimal.valueOf(calculateAmount(event.getPrice().doubleValue(), totalMulti[0])));
        }
    }

    public double calculateAmount(double amount, double multi) {
        return amount * (multi < 1 ? 1 + multi : multi);
    }
}
