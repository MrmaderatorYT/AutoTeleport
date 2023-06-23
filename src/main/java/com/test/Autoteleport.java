package com.test;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Autoteleport extends JavaPlugin implements Listener {
    private Location spawnLocation;

    @Override
    public void onEnable() {
        // Загрузка конфигурации
        saveDefaultConfig();

        // Получение координат спавна из конфигурации
        World world = Bukkit.getWorld(getConfig().getString("spawn.world"));
        double x = getConfig().getDouble("spawn.x");
        double y = getConfig().getDouble("spawn.y");
        double z = getConfig().getDouble("spawn.z");
        float yaw = (float) getConfig().getDouble("spawn.yaw");
        float pitch = (float) getConfig().getDouble("spawn.pitch");
        spawnLocation = new Location(world, x, y, z, yaw, pitch);

        // Регистрация слушателя событий
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        // Проверка, находится ли игрок на высоте 0
        if (location.getY() <= 0) {
            player.teleport(spawnLocation);
            player.sendMessage("You have been automatically teleported to the spawn.");
        }
    }
}