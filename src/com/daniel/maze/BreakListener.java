package com.daniel.maze;

import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
//import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BreakListener implements Listener {
	public MazeGenerator mazeGenerator;
	public BreakListener(JavaPlugin plugin, MazeGenerator mazeGenerator) {
		this.mazeGenerator = mazeGenerator;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	};
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Location location = new Location(e.getPlayer().getWorld(), 130, 70, -29);
		location.setX(Math.floor(location.getX()));
		location.setY(Math.floor(location.getY()));
		location.setZ(Math.floor(location.getZ()));
		mazeGenerator.generate(location);
	}
}