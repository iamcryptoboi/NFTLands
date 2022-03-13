package com.gmail.iamcryptoboi.nftlands;

import com.nftworlds.wallet.objects.Network;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandClaimLand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("missing claim radius");
            return false;
        }

        int radius;
        try {
            radius = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            player.sendMessage("invalid radius");
            return false;
        }

        // check plot collisions
        // locations are saved in doubles
        // x, z - floor
        // y - height
        // head rotations are in float since lower accuracy is ok (ticking system)
        double minX = player.getLocation().getX() - radius;
        double minZ = player.getLocation().getZ() - radius;
        double maxX = player.getLocation().getX() + radius;
        double maxZ = player.getLocation().getZ() + radius;
//        x x | x x


        for (double x = minX; x < maxX; x++) {
            for (double z = minZ; z < maxZ; z++) {
                ProtectedRegion region = PlotUtility.getPlotAtLocation(player.getLocation());
                if (region != null) {
                    player.sendMessage("There is already a plot claimed here");
                    return false;
                }
            }
        }


        // create plot vector instances
        BlockVector3 minPos = BlockVector3.at(minX, 0, minZ);
        BlockVector3 maxPos = BlockVector3.at(maxX, 256, maxZ);

        ProtectedCuboidRegion plot = new ProtectedCuboidRegion(getPlotString(player.getLocation()), minPos, maxPos);
        PlayerBuyPlotPayload payload = new PlayerBuyPlotPayload(plot);

        // WRLD payment here
        double price = 1;
        NFTLands.getPayments().getNFTPlayer((Player) sender).requestWRLD(
                price, Network.POLYGON, "Purchasing plot " + plot.getId(), false, payload
        );

        return true;
    }



    private String getPlotString(Location loc) {
        return loc.getWorld().getName() + "-X" + loc.getBlockX() + "-Z" + loc.getBlockZ();
    }
}
