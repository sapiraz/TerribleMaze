package com.daniel.maze;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public class Main extends JavaPlugin {
	public MazeGenerator mazeGenerator;
	public static final Boolean LOGGING_ENABLED = false;
	@Override
	public void onEnable() {
		saveDefaultConfig();
		mazeGenerator = new MazeGenerator();
		new CommandListener(this, mazeGenerator);
		Bukkit.broadcastMessage("Maze generator loaded.");
	}

	public static void main(String[] args) {}

}

