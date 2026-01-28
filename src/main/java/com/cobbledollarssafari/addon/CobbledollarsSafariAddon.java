package com.cobbledollarssafari.addon;

import com.cobbledollarssafari.addon.economy.CobbledollarsEconomyProvider;
import com.safari.economy.EconomyRegistry;
import net.fabricmc.api.ModInitializer;


public class CobbledollarsSafariAddon implements ModInitializer {
	@Override
	public void onInitialize() {
		EconomyRegistry.register(new CobbledollarsEconomyProvider(), 1000);
	}
}