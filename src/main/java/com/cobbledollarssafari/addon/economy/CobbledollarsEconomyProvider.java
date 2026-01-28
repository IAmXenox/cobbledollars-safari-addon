package com.cobbledollarssafari.addon.economy;

import com.safari.SafariMod;
import com.safari.economy.EconomyProvider;
import net.minecraft.server.level.ServerPlayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

public class CobbledollarsEconomyProvider implements EconomyProvider {

    private static Class<?> COBBLEDOLLARS_PLAYER_CLASS = null;
    private static Method GET_DOLLARS_METHOD = null;
    private static Method SET_DOLLARS_METHOD = null;

    private static Class<?> callCobbleDollarClass() throws ClassNotFoundException {
        if (COBBLEDOLLARS_PLAYER_CLASS == null) {
            COBBLEDOLLARS_PLAYER_CLASS = Class.forName("fr.harmex.cobbledollars.common.utils.CobbleDollarsPlayer");
        }
        return COBBLEDOLLARS_PLAYER_CLASS;
    }

    private static BigInteger getBalanceRaw(ServerPlayer player) {
        try {
            if (GET_DOLLARS_METHOD == null) {
                GET_DOLLARS_METHOD = callCobbleDollarClass().getMethod("cobbleDollars$getCobbleDollars");
            }
            return (BigInteger) GET_DOLLARS_METHOD.invoke(player);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return BigInteger.ZERO;
        }
    }

    private static boolean setBalanceRaw(ServerPlayer player, BigInteger amount) {
        try {
            if (SET_DOLLARS_METHOD == null) {
                SET_DOLLARS_METHOD = callCobbleDollarClass().getMethod(
                        "cobbleDollars$setCobbleDollars",
                        BigInteger.class
                );
            }
            SET_DOLLARS_METHOD.invoke(player, amount);
            return true;
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public BigDecimal getBalance(ServerPlayer player) {
        BigInteger bi = getBalanceRaw(player);
        return bi == null ? BigDecimal.ZERO : new BigDecimal(bi);
    }

    @Override
    public boolean hasEnough(ServerPlayer player, BigDecimal amount) {
        try {
            BigDecimal current = getBalance(player);
            return current.compareTo(amount) >= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deduct(ServerPlayer player, BigDecimal amount) {
        try {
            // CobbleDollars est en entier → on retire la partie entière
            BigInteger toSub = amount.toBigInteger();

            BigInteger before = getBalanceRaw(player);
            if (before == null) before = BigInteger.ZERO;

            if (before.compareTo(toSub) < 0) {
                return false;
            }

            BigInteger after = before.subtract(toSub);
            boolean success = setBalanceRaw(player, after);

            SafariMod.LOGGER.info(
                    "Safari economy deduct (CobbleDollars): player={}, amount={}, success={}, balanceBefore={}, balanceAfter={}",
                    player.getName().getString(),
                    toSub,
                    success,
                    before,
                    success ? after : before
            );

            // Si ton EconomyProvider du core a un appendTransactionLog(...) statique :
            try {
                EconomyProvider.appendTransactionLog(
                        player,
                        "deduct",
                        toSub.intValue(),
                        success,
                        before.intValue(),
                        (success ? after : before).intValue()
                );
            } catch (Throwable ignored) {
                // au cas où le core n’a pas cette méthode, on ne casse pas l’addon
            }

            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}