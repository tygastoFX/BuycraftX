package net.buycraft.plugin.bukkit.tasks;

import net.buycraft.plugin.bukkit.BuycraftPluginBase;
import net.buycraft.plugin.data.responses.CheckoutUrlResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public class SendCheckoutLink implements Runnable {

    private BuycraftPluginBase plugin;
    private int id;
    private Player player;
    private Boolean isCategory;
    private CommandSender sender;

    public SendCheckoutLink(BuycraftPluginBase plugin, int id, Player p) {
        this.plugin = plugin;
        this.id = id;
        this.player = p;
        this.isCategory = false;
        this.sender = null;
    }

    public SendCheckoutLink(BuycraftPluginBase plugin, int id, Player p, boolean isCategory, CommandSender sender) {
        this.plugin = plugin;
        this.id = id;
        this.player = p;
        this.isCategory = isCategory;
        this.sender = sender;
    }

    @Override
    public void run() {
        CheckoutUrlResponse response;
        try {
            if (!isCategory) {
                response = plugin.getApiClient().getCheckoutUri(player.getName(), id).execute().body();
            } else {
                response = plugin.getApiClient().getCategoryUri(player.getName(), id).execute().body();
            }
        } catch (IOException e) {
            if (sender == null)
                player.sendMessage(ChatColor.RED + plugin.getI18n().get("cant_check_out") + " " + e.getMessage());
            else
                sender.sendMessage(ChatColor.RED + plugin.getI18n().get("cant_check_out") + " " + e.getMessage());
            return;
        }
        if (!isCategory) {
            String message = plugin.getConfiguration().getCheckoutMSG().replace("\n", "").replace("\"", "");
            String url = response.getUrl().replace("\n", "").replace("\"", "");
            player.sendMessage(color(message + url));
        } else {
            player.sendMessage(ChatColor.STRIKETHROUGH + "                                            ");
            player.sendMessage(ChatColor.GREEN + plugin.getI18n().get("to_view_this_category"));
            player.sendMessage(ChatColor.BLUE + ChatColor.UNDERLINE.toString() + response.getUrl());
            player.sendMessage(ChatColor.STRIKETHROUGH + "                                            ");
        }
    }

    public static String color(String message) {

        // Do color stuff

        return message;
    }


}
