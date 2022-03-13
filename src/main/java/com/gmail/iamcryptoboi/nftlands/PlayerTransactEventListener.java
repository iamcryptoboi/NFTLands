package com.gmail.iamcryptoboi.nftlands;

import com.nftworlds.wallet.event.PlayerTransactEvent;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTransactEventListener implements Listener {
    @EventHandler
    public void onPlayerTransact(PlayerTransactEvent<?> e) {
        if (e.getPayload() instanceof PlayerBuyPlotPayload) {
            PlayerBuyPlotPayload payload = (PlayerBuyPlotPayload) e.getPayload();
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 15, 0.5F);
            e.getPlayer().sendMessage("Your transaction has been finalized, enjoy your new plot!");
            payload.plotToBuy.getOwners().clear();
            payload.plotToBuy.getOwners().addPlayer(WorldGuardPlugin.inst().wrapPlayer(e.getPlayer()));
            payload.plotToBuy.setFlag(Flags.GREET_MESSAGE, e.getPlayer().getName() + " owns this plot");
            if (payload.plotToBuy instanceof ProtectedCuboidRegion) {
                PlotUtility.savePlot(e.getPlayer(), (ProtectedCuboidRegion) payload.plotToBuy);
            }
        }

        // Send player link for mint and verify website
        String nftLink = "https://nft-lands-g5c8o.ondigitalocean.app";
        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lVIEW HERE: ") + ChatColor.GREEN + nftLink);

    }
}
