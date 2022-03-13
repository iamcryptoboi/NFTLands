package com.gmail.iamcryptoboi.nftlands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlotUtility {
    public static ProtectedRegion getPlotAtLocation(Location l) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(l.getWorld()));
        BlockVector3 position = BlockVector3.at(l.getX(), l.getY(), l.getZ());
        if (regions == null) {
            return null;
        }
        ApplicableRegionSet set = regions.getApplicableRegions(position);

        // If we're at an intersection of multiple plots or not on a plot, abort.
        if (set.size() != 1) {
            return null;
        }

        return set.iterator().next();

    }

    public static void savePlot(Player player, ProtectedCuboidRegion plot) {
        // repository of all plots
        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(player.getWorld()));
        regionManager.addRegion(plot);
        player.sendMessage("Saved plot");
    }
}
