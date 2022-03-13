package com.gmail.iamcryptoboi.nftlands;

import com.nftworlds.wallet.api.WalletAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class NFTLands extends JavaPlugin {
    private static NFTLands plugin;
    private static WalletAPI wallet;

    public static WalletAPI getPayments() {
        return wallet;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        wallet = new WalletAPI();

        getServer().getLogger().info("NFT Worlds plot plugin enabled!");
        getCommand("claim").setExecutor(new CommandClaimLand());

        getServer().getPluginManager().registerEvents(new PlayerTransactEventListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
