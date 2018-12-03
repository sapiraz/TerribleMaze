package com.daniel.maze;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandListener implements Listener {
    private JavaPlugin javaPlugin;
    private MazeGenerator mazeGenerator;

    public CommandListener(JavaPlugin javaPlugin, MazeGenerator mazeGenerator) {
        this.javaPlugin = javaPlugin;
        this.mazeGenerator = mazeGenerator;
        Bukkit.getPluginManager().registerEvents(this, this.javaPlugin);
    }

    @EventHandler
    private void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().isOp()) {
            String[] args = event.getMessage().replaceAll("^(\\/)", "").split(" ");
            if (args.length > 0 && args[0].equals("maze")) {
                if (args.length <= 4) {
                    onCommandHandler(args, event.getPlayer().getLocation());
                }
                else {
                    event.getPlayer().sendMessage("Usage: /maze <size> <height> <material>");
                }
                event.setCancelled(true);
            }
        }
    }

    private void onCommandHandler(String[] args, Location location) {
        Map<String, Object> params = parseCommand(args);
        Bukkit.getWorlds().get(0).setTime(1600);
        location.setX(Math.floor(location.getX()));
        location.setY(Math.floor(location.getY()));
        location.setZ(Math.floor(location.getZ()));
        mazeGenerator.generate(location, (int)params.get("size"), (int)params.get("height"), (int)params.get("material"));
    }

    private Map<String, Object> parseCommand(String[] args) {
        Map<String, Object> params = new HashMap();
        params.put("size", Integer.parseInt(getParam(args, 1, "4")));
        params.put("height", Integer.parseInt(getParam(args, 2, "5")));
        params.put("material", Integer.parseInt(getParam(args, 3, "4")));
        return params;
    }

    private String getParam(String[] args, int idx, String defaultValue) {
        try {
            return args[idx];
        }
        catch(Exception e) {
            return defaultValue;
        }
    }
}
